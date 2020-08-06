/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.insert;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.CondizioniPagamentoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.OtfEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampEffect;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampsTablesHelper;

import java.sql.Timestamp;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author gdefacci
 */
public class InsertOtfDao extends KeyProviderIBatisDao implements OtfEffect {

//	private static final Logger log = it.nch.eb.common.utils.resource.ResourcesUtil.createLogger(InsertPendenzeDao.class);

	private final OtfEffect integratorEffect;
	private final String operatorDefault;

	private final CondizioniPagamentoDao condizioniPagamentoDao;

	public InsertOtfDao(SqlMapClient sqlMapClient, TablesUidProvider uidProvider, OtfEffect integratorEffect) {
		this(sqlMapClient, uidProvider, integratorEffect, PendenzeConsts.OPERATOR_DEFAULT);
	}
	public InsertOtfDao(SqlMapClient sqlMapClient,
			TablesUidProvider uidProvider,
			OtfEffect integratorEffect,
			String opDflt) {
		super(sqlMapClient, uidProvider);
		this.condizioniPagamentoDao = new CondizioniPagamentoDao(sqlMapClient, uidProvider);
		this.integratorEffect = integratorEffect;
		this.operatorDefault = opDflt;
	}

	public static TimestampEffect defaultInsertTimestampEffect(
			final Timestamp now, final String operator, final Integer prVersione) {
		return new TimestampEffect() {

			public void apply(TableTimestamps model) {
				model.setTsInserimento(now);
				model.setOpInserimento(operator);				
				model.setStRiga(PendenzeConsts.ST_RIGA_VALID);
				model.setPrVersione(prVersione);
			}

		};
	}

	public void apply(OtfModel pendenza) {

		if (integratorEffect!=null) integratorEffect.apply(pendenza);

		final Timestamp now = new Timestamp(System.currentTimeMillis());
		
//		setDatiInserimentoPendenza(pendenza, now, operatorDefault, pendenza.getPrVersione());
//		pendenza.setTsDecorrenza(now);
//		pendenza.setIdPendenza(newPendenzeId());
//		setModalitaPagamento(pendenza);
//		
//		//set ST-RIGA in funzione del VISIBILE
//		if (pendenza.isHidden()) {
//			pendenza.setStRiga("H");
//			pendenza.setTsAnnullamento(new Timestamp(System.currentTimeMillis()));
//		} 
//		
//		insert("insertPendenze", pendenza);
//		if (pendenza.getCondizioniDiPagamento()!=null) {
//			for (Iterator it = pendenza.getCondizioniDiPagamento().iterator(); it.hasNext();) {
//				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
//				condizioniPagamentoDao.insertCondizioniDiPagamento(pendenza, cp, now);
//			}
//		}
//
//		if (pendenza.getDestinatari()!=null){
//			for (Iterator it = pendenza.getDestinatari().iterator(); it.hasNext();) {
//				DestinatariModel destinatario = (DestinatariModel) it.next();
//				destinariDao.insert(destinatario, pendenza);
//
//			}
//		}
//		if (pendenza.getAllegati()!=null){
//			for (Iterator it = pendenza.getAllegati().iterator(); it.hasNext();) {
//				AllegatoModel allegato = (AllegatoModel) it.next();
//				allegatoDao.insertAllegatoPendenza(pendenza,allegato, now);
//			}
//		}

//		commit();
	}

	public static void setDatiInserimentoPendenza(PendenzeModel model,
			final Timestamp now, final String operator, Integer prVersione) {
		final TimestampsTablesHelper helper = new TimestampsTablesHelper();

		final TimestampEffect effect = defaultInsertTimestampEffect(now, operator, prVersione);

		helper.set(model, effect);
		helper.set(model.getAllegati(), effect);
		helper.set(model.getCondizioniDiPagamento(), new TimestampEffect() {

			public void apply(TableTimestamps model) {
				effect.apply(model);
				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) model;
				cp.setTsDecorrenza(now);
				helper.set(cp.getAllegati(), effect);
				helper.set(cp.getVociImporto(), effect);
			}

		});
		helper.set(model.getDestinatari(), new TimestampEffect() {

			public void apply(TableTimestamps model) {
				effect.apply(model);
				DestinatariModel destinatari = (DestinatariModel) model;
				helper.set(destinatari.getFlusso(), effect);
			}

		});

	}

}
