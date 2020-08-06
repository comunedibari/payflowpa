/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.insert;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;


import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.AllegatoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.CondizioniPagamentoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.DestinatariDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.AllegatoModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.TributoStrutturatoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampEffect;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampsTablesHelper;
import it.nch.eb.xsqlcmd.utils.DseProperties;
import it.tasgroup.idp.plugin.api.BackEndPlugin;
import it.tasgroup.idp.plugin.api.BackEndPluginFactory;
import it.tasgroup.idp.plugin.api.DettaglioStrutturatoModel;


/**
 * @author gdefacci
 */
public class InsertPendenzeDao extends KeyProviderIBatisDao implements PendenzeModelEffect {

//	private static final Logger log = it.nch.eb.common.utils.resource.ResourcesUtil.createLogger(InsertPendenzeDao.class);
	private static final Logger log = ResourcesUtil.createLogger(InsertPendenzeDao.class);

	private static String DB_DIALECT = "it.tasgroup.dse.DBDialect";
	private static String MYSQL = "MySql";
	private static String DB2 = "DB2";
	
	private final DestinatariDao destinariDao;
	private final AllegatoDao allegatoDao;
	private final PendenzeModelEffect integratorEffect;
	private final String operatorDefault;

	private final CondizioniPagamentoDao condizioniPagamentoDao;

	/**
	 * 
	 * @param sqlMapClient
	 * @param uidProvider
	 * @param integratorEffect
	 */
	public InsertPendenzeDao(SqlMapClient sqlMapClient, TablesUidProvider uidProvider, PendenzeModelEffect integratorEffect) {
		this(sqlMapClient, uidProvider, integratorEffect, PendenzeConsts.OPERATOR_DEFAULT);
	}
	
	/**
	 * 
	 * @param sqlMapClient
	 * @param uidProvider
	 * @param integratorEffect
	 * @param opDflt
	 */
	public InsertPendenzeDao(SqlMapClient sqlMapClient,
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

	/**
	 * 
	 * @param now
	 * @param operator
	 * @param prVersione
	 * @return
	 */
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

	/**
	 * 
	 */
	public void apply(PendenzeModel pendenza) {

		if (integratorEffect!=null) integratorEffect.apply(pendenza);

		final Timestamp now = new Timestamp(System.currentTimeMillis());
		setDatiInserimentoPendenza(pendenza, now, operatorDefault, pendenza.getPrVersione());
		pendenza.setTsDecorrenza(now);

		pendenza.setIdPendenza(newPendenzeId());

		setModalitaPagamento(pendenza);
		
		//set ST-RIGA in funzione del VISIBILE
		if (pendenza.isHidden()) {
			pendenza.setStRiga("H");
			pendenza.setTsAnnullamento(new Timestamp(System.currentTimeMillis()));
		} 	
		
		//commentare la chiamata a questo metodo
		//quando si eseguono i test unitari
		manageBackendPlugin(pendenza);		
		//altrimenti si verifica un ClassNotFound		
		
		insert("insertPendenze", pendenza);
		
		if (pendenza.getCondizioniDiPagamento()!=null) {
			for (Iterator it = pendenza.getCondizioniDiPagamento().iterator(); it.hasNext();) {
				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
				log.debug("CondizPagam, IDPagamento " + cp.getIdPagamento());
				log.debug("CondizPagam, IBAN " + cp.getCodiceIBAN());
				log.debug("CondizPagam, BENEFICIARIO " + cp.getBeneficiario());
				log.debug("CondizPagam, CO CIP " + cp.getCoCip());
				log.debug("CondizPagam, TI CIP " + cp.getTiCip());
				log.debug("CondizPagam, Causale " + cp.getCausalePagamento());
				condizioniPagamentoDao.insertCondizioniDiPagamento(pendenza, cp, now);
			}
		}

		if (pendenza.getDestinatari()!=null){
			for (Iterator it = pendenza.getDestinatari().iterator(); it.hasNext();) {
				DestinatariModel destinatario = (DestinatariModel) it.next();
				log.debug("TIPO DEST" + destinatario.getTiDestinatario());
				destinariDao.insert(destinatario, pendenza);

			}
		}
		if (pendenza.getAllegati()!=null){
			for (Iterator it = pendenza.getAllegati().iterator(); it.hasNext();) {
				AllegatoModel allegato = (AllegatoModel) it.next();
				log.debug("Allegato, CODIFICA" + allegato.getTiCodificaBody());
				log.debug("Allegato, BODY" + allegato.getDatiBody());
				allegatoDao.insertAllegatoPendenza(pendenza,allegato, now);
			}
		}

		
		
		
//		commit();
	}

	private void manageBackendPlugin(PendenzeModel pendenza) {
		BackEndPlugin plugin = BackEndPluginFactory.getPlugin(pendenza.getCdTrbEnte());		
		log.info("RRREPE (during insert):: isSpontaneo="+pendenza.isSpontaneo());		
		boolean isStrutturato = pendenza.isSpontaneo() && pendenza.getDeCausale()!=null && plugin!=null && pendenza.getDestinatari()!=null && pendenza.getDestinatari().iterator().hasNext(); 

		if(isStrutturato) {
			//plugin.saveSpecificTable(pendenza.getDeCausale());
			log.info("INSERT TRIBUTO STRUTTURATO BEGIN");
			log.info("--------------------------------");
			try {				
				// Inserisco nella tabella "Tributo Strutturato"
				// Recupero l'ID dalla sequence o lascio che si autoincrementi
				
				String dbDialect = DseProperties.getProperty(DB_DIALECT, null);
				
				Integer nextId = null;
				if (MYSQL.equals(dbDialect) || DB2.equals(dbDialect)) {
					; // lasciamo nextId a null ed in automatico verra' autoincrementato dal db
				} else {
					nextId = new Integer((int)newTributoStrutturatoId());  
				}
				TributoStrutturatoModel tributoStrutturato = new TributoStrutturatoModel();
				tributoStrutturato.setId(nextId);
				tributoStrutturato.setCfSoggettoVersante(null); //Non ha senso nei predeterminati
				tributoStrutturato.setIdPendenza(pendenza.getIdPendenza());
				tributoStrutturato.setNoteVersante(null); //Non ha senso nei predeterminati
				tributoStrutturato.setTipoTributo(pendenza.getCdTrbEnte());
				tributoStrutturato.setTsInserimento(pendenza.getTsInserimento());
				tributoStrutturato.setOpInserimento(pendenza.getOpInserimento());
				tributoStrutturato.setVersion(0);

				if (MYSQL.equals(dbDialect)) {
					getSqlMapClient().insert("insertTributoStrutturatoMySql",tributoStrutturato);
				} else if (DB2.equals(dbDialect)) {
						getSqlMapClient().insert("insertTributoStrutturatoDB2",tributoStrutturato);
				} else {
					getSqlMapClient().insert("insertTributoStrutturato",tributoStrutturato);
				}
				
				// Inserisco nella tabella specifica del tributo
				DettaglioStrutturatoModel dettaglioStrutturato = plugin.getDettaglioStrutturato(pendenza.getDeCausale());
				dettaglioStrutturato.setId(tributoStrutturato.getId());
				Collection destinatari = pendenza.getDestinatari();
				DestinatariModel d = (DestinatariModel)pendenza.getDestinatari().iterator().next();
				dettaglioStrutturato.setCfSoggettoPassivo(d.getCoDestinatario()); //TODO: che si fa in caso di cointestazioni?
				dettaglioStrutturato.setIdEnte(pendenza.getIdEnte());
				dettaglioStrutturato.setImporto(pendenza.getImTotale());
				dettaglioStrutturato.setVersion(0);
				dettaglioStrutturato.setOpInserimento(pendenza.getOpInserimento());
				dettaglioStrutturato.setTsInserimento(tributoStrutturato.getTsInserimento());
				dettaglioStrutturato.setAnnoRiferimento(pendenza.getAnnoRiferimento());
				// Aggiorno la pendenza con l'id del tributo strutturato
				pendenza.setIdTributoStrutturato(tributoStrutturato.getId());
				
				getSqlMapClient().insert(dettaglioStrutturato.getQueryName(),dettaglioStrutturato);
				
				log.info("--------------------------------");
				log.info("INSERT TRIBUTO STRUTTURATO END");							
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}			
		}
	}

	private void setModalitaPagamento(PendenzeModel pendenza) {
		pendenza.setFlModPagamento(getModalitaPagamento(pendenza));
	}

	/**
	 * 
	 * @param pendenza
	 * @return
	 */
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

	/**
	 * 
	 * @param model
	 * @param now
	 * @param operator
	 * @param prVersione
	 */
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
