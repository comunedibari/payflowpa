/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.update;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.commands.db.BaseIbatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.AllegatoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.CondizioniPagamentoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.FlussoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.dao.insert.InsertPendenzeDao;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.AllegatoModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.PrenotazioneAvvisiDigitaliModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.VociBilancioModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampEffect;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampsTablesHelper;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author gdefacci
 */
public class UpdatePendenzeDao extends BaseIbatisDao implements PendenzeModelEffect {
	
	private final AllegatoDao allegatoDao;
	private final PendenzeModelEffect dataIntegrator;

	private final String operatorDefault;
	private final CondizioniPagamentoDao condizioniPagamentoDao;
	private final FlussoDao flussoDao; 
	
	private static final Logger log = ResourcesUtil.createLogger(UpdatePendenzeDao.class);
	
	/**
	 * 
	 * @param sqlMapClient
	 * @param tableUidProvider
	 * @param dataIntegrator
	 */
	public UpdatePendenzeDao(SqlMapClient sqlMapClient, 
			TablesUidProvider tableUidProvider, 
			PendenzeModelEffect dataIntegrator) {
		this(sqlMapClient, tableUidProvider, dataIntegrator, PendenzeConsts.OPERATOR_DEFAULT);
	}
	
	/**
	 * 
	 * @param sqlMapClient
	 * @param tableUidProvider
	 * @param dataIntegrator
	 * @param opDflt
	 */
	public UpdatePendenzeDao(SqlMapClient sqlMapClient, 
			TablesUidProvider tableUidProvider, 
			PendenzeModelEffect dataIntegrator, String opDflt) {
		super(sqlMapClient);
		this.allegatoDao = new AllegatoDao(sqlMapClient, tableUidProvider);
		this.flussoDao = new FlussoDao(sqlMapClient, tableUidProvider);
		this.condizioniPagamentoDao = new CondizioniPagamentoDao(sqlMapClient, tableUidProvider );
		this.dataIntegrator = dataIntegrator;
		this.operatorDefault = opDflt;
	}
	
	/**
	 * 
	 */
	public void apply(PendenzeModel pendenza) {
		
		if (dataIntegrator!=null) dataIntegrator.apply(pendenza);
		
		log.info(" UpdStatus(idPendenza ="+ pendenza.getIdPendenza() +") ");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		pendenza.setTsDecorrenza(now);
		pendenza.setTsAggiornamento(now);
		pendenza.setOpAggiornamento(operatorDefault);
		
		Collection allegati = pendenza.getAllegati();
		if (allegati!=null && !allegati.isEmpty())
			for (Iterator it = allegati.iterator(); it.hasNext();) {
				AllegatoModel allegato = (AllegatoModel) it.next();
				allegatoDao.insertAllegatoPendenza(pendenza, allegato, now);
		}
		
		
		Collection destinatari = pendenza.getDestinatari();
		if (destinatari!=null && !destinatari.isEmpty())
			for (Iterator it = destinatari.iterator(); it.hasNext();) {
				DestinatariModel destinatario = (DestinatariModel) it.next();
				FlussiModel flusso = destinatario.getFlusso();
				
				TimestampEffect efct = InsertPendenzeDao.defaultInsertTimestampEffect(now, operatorDefault, pendenza.getPrVersione());
				TimestampsTablesHelper.instance.set(flusso, efct);
				
				//inserire il flusso non serve a niente, non si usa mai	
				//flussoDao.insertNewFlusso(pendenza, flusso);
			}

		update("updatePendenze", pendenza);
		
		Collection condizioniDiPagamento = pendenza.getCondizioniDiPagamento();
		if (condizioniDiPagamento!=null && !condizioniDiPagamento.isEmpty()) {
			for (Iterator it = condizioniDiPagamento.iterator(); it.hasNext();) {
				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
				
				log.info(" UpdStatus(tipoOperazione ="+ cp.getTiOperazioneUpdate() +") ");
				
				if ("insert".equalsIgnoreCase(cp.getTiOperazioneUpdate())) {
					
					setDatiInserimentoCondizione(cp,now);						
					condizioniPagamentoDao.insertCondizioniDiPagamento(pendenza, cp, now);
					
				} else {
					
					//siamo nel caso update
					if (cp.getStPagamento().equals(PendenzeConsts.STATO_PAGAMENTO_IRREGOLARE)) {
						
						cp.setTsAnnullamento(pendenza.getTsAggiornamento());
						cp.setOpAnnullamento(pendenza.getOpAggiornamento());		
																					
					} 
					
					PrenotazioneAvvisiDigitaliModel pren = new PrenotazioneAvvisiDigitaliModel();

					pren.setIdCondizione(cp.getIdCondizione());
					pren.setIdEnte(cp.getIdEnte());
					pren.setIdPagamento(cp.getIdPagamento());
					pren.setNumTentativiAvviso(0L);
					pren.setCodiceAvviso(cp.getIdPagamento());
					pren.setStatoAvviso("I");
					pren.setTipoProcesso("RT");
					pren.setOpInserimento("OTF");
					pren.setTsInserimento(new Timestamp(System.currentTimeMillis()));
					pren.setTipoOperazioneOriginale("U");
					
					insert("insertPrenotazioneAvvisiDigitali", pren);
					
					condizioniPagamentoDao.updateCondizioniDiPagamento(pendenza, cp, now);
					
					//gestione annullamento allegati (tipo_ricevuta)
					//per le condizioni pagate fuori da Iris
					//questo presuppone che quando vengano pagate da Iris
					//allora l'ente stesso non mandi mai RFC127 di tipo UpdStatus con stato = PAGATO
					log.info(" Stato Pendenza = " + pendenza.getStPendenza());
					if ("C".equals(pendenza.getStPendenza()) &&
							cp.getStPagamento().equals(PendenzeConsts.STATO_PAGAMENTO_PAGATO)) {
					
						log.info(" Pendenza Chiusa (idPend/idCond= " + cp.getIdPendenza() + "/" + cp.getIdCondizione() );
						//ricerca i pagamenti
						Integer idPaga = (Integer) this.queryForObject("selectCondizioniConPagamentoAssociato", cp);
						
						//annulla gli allegati
						if (idPaga!=null) {
							log.info(" UpdStatus(stato=Chiusa) , La Condizione ha un pagamento associato ");						
							
						} else {
							log.info(" UpdStatus(stato=Chiusa) , La Condizione NON ha un pagamento associato ");
							
							//setting dei campi
							pendenza.setStRiga(PendenzeConsts.ST_RIGA_CANCELLED);
							Integer newVersion = new Integer( pendenza.getPrVersione().intValue() + 1);
							pendenza.setPrVersione(newVersion);
							pendenza.setOpAnnullamento(operatorDefault);
							pendenza.setTsAnnullamento(now);							
							//invalidate allegati
							update("invalidateAllegatiTipoRicevuta", pendenza);
							//invalidate destinatari
							//??
							
							log.info(" Ricevuta PDF annullata ");
						}
						
					}
					

				}	
				
				
			}
		}
		
	}
	
	
	/**
	 * Setta, nel modo piu' antanico possibile, i timestamp ed i valori di 
	 * default per i campi di tracking per le entita' appena inserite.
	 */
	private static void setDatiInserimentoCondizione(CondizioniPagamentoModel cp, final Timestamp now) {
		
		final TimestampsTablesHelper helper = new TimestampsTablesHelper();
		final TimestampEffect effect = InsertPendenzeDao.defaultInsertTimestampEffect(now, "Idp", 0);

		helper.set(cp, new TimestampEffect() {

			public void apply(TableTimestamps model) {
				effect.apply(model);
				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) model;
				cp.setTsDecorrenza(now);
				helper.set(cp.getAllegati(), effect);
				helper.set(cp.getVociImporto(), effect);
			}

		});
	}

}
