/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.updateMassivo;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.AllegatoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.CondizioniPagamentoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.DestinatariDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.AllegatoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampEffect;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampsTablesHelper;

import java.sql.Timestamp;
import java.util.Iterator;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author gdefacci
 */
public class UpdateMassivoPendenzeDao extends KeyProviderIBatisDao implements PendenzeModelEffect {

//	private static final Logger log = it.nch.eb.common.utils.resource.ResourcesUtil.createLogger(InsertPendenzeDao.class);

	private final DestinatariDao destinariDao;
	private final AllegatoDao allegatoDao;
	private final PendenzeModelEffect integratorEffect;
	private final String operatorDefault;

	private final CondizioniPagamentoDao condizioniPagamentoDao;

	public UpdateMassivoPendenzeDao(SqlMapClient sqlMapClient, TablesUidProvider uidProvider, PendenzeModelEffect integratorEffect) {
		this(sqlMapClient, uidProvider, integratorEffect, PendenzeConsts.OPERATOR_DEFAULT);
	}
	public UpdateMassivoPendenzeDao(SqlMapClient sqlMapClient,
			TablesUidProvider uidProvider,
			PendenzeModelEffect integratorEffect,
			String opDflt) {
		super(sqlMapClient, uidProvider);
		this.destinariDao = new DestinatariDao(sqlMapClient, uidProvider);
		this.allegatoDao = new AllegatoDao(sqlMapClient, uidProvider);
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

	public void apply(PendenzeModel pendenza) {

		//FASE II
		if (integratorEffect!=null) integratorEffect.apply(pendenza);

//		Thread thread = Thread.currentThread();
//		StackTraceElement[] stackTraceElements =  thread.getStackTrace();
//		for (StackTraceElement stackTraceElement : stackTraceElements) {
//			System.out.println("stack trace: " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName());
//		}

		final Timestamp now = new Timestamp(System.currentTimeMillis());
		setDatiInserimentoPendenza(pendenza, now, operatorDefault, pendenza.getPrVersione());
		pendenza.setTsDecorrenza(now);

		pendenza.setIdPendenza(newPendenzeId());

		setModalitaPagamento(pendenza);

		insert("insertPendenze", pendenza);
		if (pendenza.getCondizioniDiPagamento()!=null) {
			for (Iterator it = pendenza.getCondizioniDiPagamento().iterator(); it.hasNext();) {
				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
				condizioniPagamentoDao.insertCondizioniDiPagamento(pendenza, cp, now);
			}
		}

		if (pendenza.getDestinatari()!=null){
			for (Iterator it = pendenza.getDestinatari().iterator(); it.hasNext();) {
				DestinatariModel destinatario = (DestinatariModel) it.next();
				destinariDao.insert(destinatario, pendenza);

			}
		}
		if (pendenza.getAllegati()!=null){
			for (Iterator it = pendenza.getAllegati().iterator(); it.hasNext();) {
				AllegatoModel allegato = (AllegatoModel) it.next();
				allegatoDao.insertAllegatoPendenza(pendenza,allegato, now);
			}
		}

//		commit();
	}

	private void setModalitaPagamento(PendenzeModel pendenza) {
		pendenza.setFlModPagamento(getModalitaPagamento(pendenza));
	}

	private String getModalitaPagamento(PendenzeModel pendenza) {
		boolean singolo = false;
		boolean rateale = false;
		if ( pendenza.getCondizioniDiPagamento()!=null){
			for (Iterator it = pendenza.getCondizioniDiPagamento().iterator(); it.hasNext();) {
				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
				if (cp.getTiPagamento().equals(PendenzeConsts.MODALITA_PAGAMENTO_SINGOLO)) singolo = true;
				else if (cp.getTiPagamento().equals(PendenzeConsts.MODALITA_PAGAMENTO_RATEALE)) rateale = true;
				if (singolo && rateale) return PendenzeConsts.MODALITA_PAGAMENTO_ENTRAMBE;
			}
		}
		if (singolo) return PendenzeConsts.MODALITA_PAGAMENTO_SINGOLO;
		else if (rateale) return PendenzeConsts.MODALITA_PAGAMENTO_RATEALE;
		else throw new IllegalStateException("modalita' pagamento: none of ['" +
				PendenzeConsts.MODALITA_PAGAMENTO_SINGOLO +"', '"+
				PendenzeConsts.MODALITA_PAGAMENTO_RATEALE +"', '"+
				PendenzeConsts.MODALITA_PAGAMENTO_ENTRAMBE +"']");
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
