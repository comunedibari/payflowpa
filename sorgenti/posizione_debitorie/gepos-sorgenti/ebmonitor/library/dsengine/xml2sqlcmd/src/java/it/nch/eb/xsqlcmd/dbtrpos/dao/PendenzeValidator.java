/**
 * 29/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.common.utils.IbanHelper;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.commands.db.BaseIbatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBErrorsBag;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory.DBErrorsId;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.AllegatoModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.VociBilancioModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.utils.ModelValidationsUtils;
import it.tasgroup.idp.plugin.api.BackEndPlugin;
import it.tasgroup.idp.plugin.api.BackEndPluginFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.springframework.orm.hibernate3.support.IdTransferringMergeEventListener;

import com.ibatis.sqlmap.client.SqlMapClient;

/**  
 * @author gdefacci
 */
public class PendenzeValidator extends BaseIbatisDao {

	private PendenzeDataDecoder decoder;
	private PendenzeDBErrorsFactory errorsFactory;
	private DBErrorsId errIds;
	
	private static final Logger log = ResourcesUtil.createLogger(PendenzeValidator.class);

	public PendenzeValidator(SqlMapClient sqlMapClient, PendenzeDBErrorsFactory errFactory) {
		super(sqlMapClient);
		this.decoder = new PendenzeDataDecoder(sqlMapClient, errFactory);
		this.errorsFactory = errFactory;
		errIds = errFactory.errorIds;
	}
	
	/**
	 * 
	 * @param pendenza
	 * @param errs
	 */
	public void checkPendenzaDates(PendenzeModel pendenza, DBErrorsBag errs) {
		Timestamp crezioneTs = pendenza.getTsCreazioneEnte();
		Timestamp emissioneTs = pendenza.getTsEmissioneEnte();
		Timestamp prescrizioneTs = pendenza.getTsPrescrizione();	
		
		if (emissioneTs!=null) {
			if (crezioneTs!=null && emissioneTs.compareTo(crezioneTs)<0) {
				errs.add(new DBError(
						errIds.dataEmissionePendenzaInvalid, 
						pendenza,
						errorsFactory.xmlErrrorsFactory.dataEmissionePendenzaInvalid(pendenza)));
			}
			
			if (prescrizioneTs!=null && prescrizioneTs.compareTo(emissioneTs)<0) {
				errs.add(new DBError(
						errIds.dataPrescrizionePendenzaInvalid, 
						emissioneTs,
						errorsFactory.xmlErrrorsFactory.dataPrescrizionePendenzaInvalid(pendenza)));
			}
		}
	}
	
	/**
	 * 
	 * @param cp
	 * @return
	 */
	public BigDecimal sumVociImporto(CondizioniPagamentoModel cp) {
		BigDecimal sum = BigDecimal.valueOf(0);
		if (cp.getVociImporto() != null) {
			for (Iterator it = cp.getVociImporto().iterator(); it.hasNext();) {
				VociBilancioModel vb = (VociBilancioModel) it.next();
				sum = sum.add(vb.getImVoce());
			}
		}
		return sum;
	}
	
	/**
	 * 
	 * @param pendenza
	 * @param errs
	 */
	public void checkCondizioniPagamento(PendenzeModel pendenza, DBErrorsBag errs) {
		
		log.debug(" Start controlling condizioni "    );
		Collection condPag = pendenza.getCondizioniDiPagamento();
		log.debug(" Num. condizioni da controllare = " + (condPag==null ? "0" : condPag.size() )   );
	

		if (condPag != null && condPag.size() > 0) {
			
			boolean firstCondition = true;
			Date inizioValidita = null;
			Date fineValidita = null;
			Date scadenza = null;		
			String statoCondizione = null;
			BigDecimal impPendenza = pendenza.getImTotale();
			BigDecimal sum = BigDecimal.valueOf(0);					
			
			for (Iterator it = condPag.iterator(); it.hasNext();) {
				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
				log.debug(" Start controlling condizione (pagamento/importo) = " + cp.getIdPagamento() + "/" + cp.getImPagamento() );
				
				// inizializzazione variabili per controlli di congruenza tra le condizioni
				if (firstCondition) {
					inizioValidita = cp.getDtInizioValidita();
					fineValidita = cp.getDtFineValidita();
					scadenza = cp.getDtScadenza();
					statoCondizione = cp.getStPagamento();
					firstCondition = false;
				}
				
				//controllo se id pagamento esiste gia' ed altre cose
				checkCondizionePagamento(pendenza, cp, errs);  
				
				//controllo gli allegati ed i loro casi particolari rispetto ai tributi CONTICKI
				checkAllegatiCondizione(pendenza, cp, errs);
				
				//in caso di cartella di pagamento
				//devo controllare che tutte le data siano uguali
				log.debug(" CARTELLA = " + pendenza.getCartellaDiPagamento()  );
				if ("1".equals(pendenza.getCartellaDiPagamento())) {
					
					log.debug(" Start controlling cartella di pagamento = " 
							+ cp.getDtInizioValidita() + " / " 
							+ cp.getDtScadenza() + " / " 
							+ cp.getDtFineValidita() + " / " 
							+ cp.getCausalePagamento() + " / " 
							+ cp.getStPagamento() + " / " 
							+ cp.getTiPagamento() + " / " 
							+ cp.getCodiceIBAN() + " / ");
					

					//controllo uguaglianza dello stato
					if (statoCondizione.equals(cp.getStPagamento())) {
						//ok tutto regolare
						//lo stato e' uguale
						statoCondizione = cp.getStPagamento();
					} else {
						//lo stato non e' uguale
						//allora aggiungo un errore
						log.debug(" STATO NON OMOGENEO " );
						errs.add(decoder.getDbErrorsFactory().error(
								errIds.statoCondizioneNonOmogeneoPerCartella,
								cp));
					}		
					
					if (!pendenza.getTipoOperazione().getAbbr().equals("U")) {
					
						//controllo uguaglianza delle date
						if (inizioValidita.compareTo(cp.getDtInizioValidita())==0 &&
							fineValidita.compareTo(cp.getDtFineValidita())==0 &&
							scadenza.compareTo(cp.getDtScadenza())==0) {
							//ok tutto regolare
							//le date sono uguali
							inizioValidita = cp.getDtInizioValidita();
							fineValidita = cp.getDtFineValidita();
							scadenza = cp.getDtScadenza();							
						} else {					
							//le date non sono uguali
							//allora aggiungo un errore
							log.debug(" DATA SCAD, INIZIO, FINE NON OMOGENEE " );
							errs.add(decoder.getDbErrorsFactory().error(
											errIds.dateDifferentiSuCartella,
											cp));
						}
							
						log.debug(" Causale Pagamento = " + cp.getCausalePagamento());
						//controllo della causale
						if (cp.getCausalePagamento()==null) {
							//manca la causale
							//allora aggiungo un errore
							errs.add(decoder.getDbErrorsFactory().error(
											errIds.causalePagamentoMissing,
											cp));						
						}
						
						//controllo del tipo pagamento
						log.debug(" Tipo Pagamento = " + cp.getTiPagamento());
						if (!cp.getTiPagamento().equals(PendenzeConsts.MODALITA_PAGAMENTO_SINGOLO)) {
							//con cartella di pagamento
							//si deve usare solo pagamento singolo
							log.debug(" Tipo Pagamento errato " );
							errs.add(decoder.getDbErrorsFactory().error(
									errIds.cartellaSoloSoluzioneUnica,
									cp));											
						}
						
						//controllo del iban 
						log.debug(" IBAN = " + cp.getCodiceIBAN());
						IbanHelper ibanH = new IbanHelper(cp.getCodiceIBAN());
						if (cp.getCodiceIBAN()!=null && cp.getCodiceIBAN()!="null" && "KO".equals(ibanH.ibanCheck())) {
							//con iban
							//si deve controllare se e' valido
							errs.add(decoder.getDbErrorsFactory().error(
									errIds.ibanErrato,
									cp));											
						}				
						
						//controllo dei dettagli di pagamento 
						//sembra che non si possa fare qui...
						//perche i tag InfoPagamento e DettaglioPagamento
						//sono stati compressi dal binding
						
						//somma degli importi
						log.debug("IMP CONDIZIONE " + cp.getImPagamento());
						log.debug("IMP TOTALE " + cp.getImTotale());
						sum = sum.add(cp.getImTotale());
						
						log.debug("IMP SOMMA " + sum.toString());
					}
				}				
			}
			
			//se esiste la cartella
			if ("1".equals(pendenza.getCartellaDiPagamento()) && !pendenza.getTipoOperazione().getAbbr().equals("U")) {					
				//allora controllo gli importi
				log.debug("IMP PEND " + impPendenza.toString());
				log.debug("IMP UGUALI O DIVERSI " +  impPendenza.compareTo(sum) );
				if (impPendenza.compareTo(sum) != 0) {
					errs.add(decoder.getDbErrorsFactory().error(
							errIds.impTotaleCartellaErrato,
							pendenza));
				}
			}

			log.debug("ERRORI RILEVATI ? = " + !(errs.isEmpty()) ); 
			
		}
	}

	/**
	 * 
	 * @param pendenza
	 * @param cp
	 * @param errs
	 */
	public void checkCondizionePagamento(PendenzeModel pendenza,
			CondizioniPagamentoModel cp, DBErrorsBag errs) {
		
		//controllo data fine validita
		Date dataScadenza = cp.getDtScadenza();
		Date dataInizioValidita = cp.getDtInizioValidita();
		Date dataFineValidita = cp.getDtFineValidita();
		if (dataFineValidita!=null) {
			if ((dataScadenza!=null &&  dataFineValidita.compareTo(dataScadenza) < 0) || 
					(dataInizioValidita!=null && dataFineValidita.compareTo(dataInizioValidita) < 0)) {
				errs.add(new DBError(
						errIds.condizionePagamentoDataFineValiditaInvalid, 
						cp,
						errorsFactory.xmlErrrorsFactory.condizionePagamentoDataFineValiditaInvalid(cp)));
			}
		}
		
		//controllo voci importo
		if (cp.getVociImporto() != null && cp.getVociImporto().size() > 0) {
		
			BigDecimal vociSum = sumVociImporto(cp);
			BigDecimal cpIm = cp.getImTotale();
			if (cpIm != null) {
				if (vociSum.compareTo(cpIm) != 0) {
					errs.add(new DBError(
							errIds.condizionePagamentoImTotaleInvalid, 
							cp,
							errorsFactory.xmlErrrorsFactory.condizionePagamentoImTotaleInvalid(cp, vociSum)));
				}
			}		
		}
		cp.setCdTrbEnte(pendenza.getCdTrbEnte());
		cp.setIdEnte(pendenza.getIdEnte());
	}

	/**
	 * 
	 * @param cp
	 * @param errs
	 */
	public void checkNewCondizionePagamento(PendenzeModel p, CondizioniPagamentoModel cp,
			DBErrorsBag errs) {
		
		log.info(" TipoOperazione CondizionePagamento = " + cp.getTiOperazioneUpdate());
		
		//aggiungo una nuova regola....
		//se l'updateStatus e' di tipo INSERT allora
		//eseguo il controllo sul IdPagamento duplicato...
		//altrimenti non lo eseguo
		if ("Insert".equals(cp.getTiOperazioneUpdate()) || p.getInsert()) {
			if (decoder.checkIdPagamentoExists(cp)) {
				errs.add(new DBError(
						errIds.condizionePagamentoIdPagamentoAlreadyExists, 
						cp, 
						errorsFactory.xmlErrrorsFactory.condizionePagamentoIdPagamentoAlreadyExists(cp) ));
			}
			
		}
	}
	
	/**
	 * 
	 * @param pendenza
	 * @param errs
	 */
	public void checkEuro(PendenzeModel pendenza, DBErrorsBag errs) {
		if (pendenza != null && pendenza.getDvRiferimento() != null && 
				!pendenza.getDvRiferimento().equals("EUR")) {
			errs.add(new DBError(errIds.pendenzaDivisaInvalid  , pendenza.getDvRiferimento()));
		}
	}
	
	/**
	 * 
	 * @param pendenza
	 * @param errs
	 */
	public void checkDestinatari(PendenzeModel pendenza, DBErrorsBag errs) {
		if (pendenza.getDestinatari()!=null) {
			
			boolean delegatoExist = false;
			boolean cittadinoExist = false;
			boolean altroExist = false;
			DestinatariModel destinatario = null;
			for (Iterator it = pendenza.getDestinatari().iterator(); it.hasNext();) {
				destinatario = (DestinatariModel) it.next();

				String idDestinatario = destinatario.getCoDestinatario();
				if (idDestinatario!=null && PendenzeConsts.TIPO_DESTINATARIO_CITTADINO.equals(destinatario.getTiDestinatario() )) {					
					boolean booleanCf = ModelValidationsUtils.checkCodiceFiscale(idDestinatario);
					boolean booleanStp = ModelValidationsUtils.checkCodiceStp(idDestinatario);
					boolean booleanEni = ModelValidationsUtils.checkCodiceEni(idDestinatario);
					boolean booleanPIva = ModelValidationsUtils.checkPartitaIva(idDestinatario);
					boolean booleanAgid = ModelValidationsUtils.checkCodiceFiscaleAnonimoAGID(idDestinatario);
					
					log.info(" CF / STP / ENI / Provv : " + booleanCf + " - " + booleanStp + " - " + booleanEni + " - " + booleanPIva + " - ");					
					
					if (booleanCf || booleanStp || booleanPIva || booleanEni || booleanAgid)
					 {			
						cittadinoExist = true;		
					 } else {
							errs.add(
									decoder.getDbErrorsFactory().error(
											errIds.destinatarioInvalidCodiceFiscale,
											destinatario));		
					 }					
					
					//ONLY CHECKS CF
//					if (!ModelValidationsUtils.checkCodiceFiscale(idDestinatario)
//							) {
//						errs.add(
//							decoder.getDbErrorsFactory().error(
//									errIds.destinatarioInvalidCodiceFiscale,
//									destinatario));
//					} else {
//						cittadinoExist = true;
//					}
					

				}
				
				if (idDestinatario!=null && destinatario.getTiDestinatario().equals(PendenzeConsts.TIPO_DESTINATARIO_ALTRO)) {
					if (!ModelValidationsUtils.checkPartitaIva(idDestinatario)) {
						errs.add(
							decoder.getDbErrorsFactory().error(
									errIds.destinatarioInvalidpartitaiva,
									destinatario));
					} else {
						altroExist = true;
					}															
				}
				
				
				
				if (idDestinatario!=null && destinatario.getTiDestinatario().equals(PendenzeConsts.TIPO_DESTINATARIO_DELEGATO)) {
					delegatoExist = true;
						if (destinatario.getDeDestinatario()==null) {
							//delegato deve avere per forza la descrizione
							errs.add(
									decoder.getDbErrorsFactory().error(
											errIds.delegatoSenzaDescrizione,
											destinatario));							
						}
				}				
				
			}
 

			//in caso di presenza di delegato
			//almeno uno tra cittadino ed altro
			//sono necessari
			if (delegatoExist && cittadinoExist==false && altroExist==false) {
				errs.add(
						decoder.getDbErrorsFactory().error(
								errIds.delegatoSenzaCittadino,
								pendenza));
			}
			
	
			
		}
	} 
	
	/**
	 * 
	 * @param pendenza
	 * @param pendXml
	 * @param condizioniDiPagamento
	 * @param errs
	 */
	public void checkCondizioniPagamentoConsitency(
			it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel pendenza,
			PendenzeModel pendXml,
			Collection condizioniDiPagamento, DBErrorsBag errs) {
		String idPendenza = pendenza.getIdPendenza();
		for (Iterator it = condizioniDiPagamento.iterator(); it
				.hasNext();) {
			CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
			
			if (!"insert".equalsIgnoreCase(cp.getTiOperazioneUpdate())) {
				String idPend = (String) this.queryForObject("selectIdPendenza", cp);
				if (idPend!=null && !idPendenza.equals(idPend)) {
					errs.add(new DBError(
							errIds.condizionePagamentoInconsistency, 
							pendenza,
							errorsFactory.xmlErrrorsFactory.condizionePagamentoInconsistency(cp, idPendenza)));
				}
			} 
		}
		
	}
	
	/**
	 * 
	 * @param pendenza
	 * @param errs
	 */
	public void checkPendenza(PendenzeModel pendenza, DBErrorsBag errs) {
		log.info(" Starting controlling Pendenza = " + pendenza.getIdPendenzaEnte());
		checkDestinatari(pendenza, errs);
		checkEuro(pendenza, errs);
		checkPendenzaDates(pendenza, errs);
		
		// La causale si valida solo se e' valorizzata nel messaggio.
		if (pendenza.getDeCausale()!=null && !"".equals(pendenza.getDeCausale().trim()))  {
			checkPendenzaDescrizioneCausale(pendenza, errs);
		}
		if (errs.isEmpty()) {
			checkCondizioniPagamento(pendenza, errs);
		}
	}

	/**
	 * 
	 * @param pendenza
	 * @param pendXml
	 * @param condizioniDiPagamento
	 * @param errs
	 */
	public void checkPendenzeConPagamentiAssociati(
			it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel pendenza,
			PendenzeModel pendXml,
			Collection condizioniDiPagamento, DBErrorsBag errs) {
		String idPendenza = pendenza.getIdPendenza();
		
//		PendenzeModel pendModel = new PendenzeModel();
//		pendModel.setIdPendenza(idPendenza);
		Integer idPaga = (Integer) this.queryForObject("selectPendenzeConPagamentoAssociato", pendenza);
		
		if (idPaga!=null) {
			errs.add(new DBError(
					errIds.condizionePagamentoNonModificabile, 
					pendenza,
					errorsFactory.xmlErrrorsFactory.condizionePagamentoNonModificabile(idPendenza)));
		}				
	}
	
	
	/**
	 * 
	 * @param pendenza
	 * @param errs
	 */
	public void checkPendenzaDescrizioneCausale(PendenzeModel pendenza, DBErrorsBag errs) {

		String descrizioneCausale=pendenza.getDeCausale();
		
		String codTributo = pendenza.getCdTrbEnte();
		
		//SCOMMENTARE PRIMA DELLA COMMIT
		BackEndPlugin plugin = BackEndPluginFactory.getPlugin(codTributo);
		
		//log.debug("RRREPE:: isSpontaneo="+pendenza.isSpontaneo());
		
		
		// La causale e' validata solo se e' presente un plugin ed e' valorizzata. (In caso di updatestatus non si passa e non si valida). 
		// per il cod tributo ente. 
		if (plugin!=null && pendenza.isSpontaneo()) {  
			//log.debug("BEGIN CAUSALE CHECK");
			//log.debug("===================");
			//log.debug("CAUSALE FOUND ="+descrizioneCausale);
			String esitoValidazioneCausale =plugin.validaCausale(descrizioneCausale); 
			if (esitoValidazioneCausale!=null) {
				errs.add(new DBError(
						errIds.descrizioneCausaleInvalid, 
						pendenza,
						errorsFactory.xmlErrrorsFactory.descrizioneCausalePendenzaInvalid(pendenza,esitoValidazioneCausale)));								
			}
			//log.debug("===================");
			//log.debug("END CAUSALE CHECK");
		}		
	}

	/**
	 * 
	 * @param pendenza
	 * @param cp 
	 * @param errs
	 */
	public void checkAllegatiCondizione(PendenzeModel pendenza, CondizioniPagamentoModel condizioni, DBErrorsBag errs) {
		boolean check = true;
		boolean checkXml = true;
//					Nel caso in cui le posizioni debitorie siano comunicate a IRIS dal sistema Conticki via RFC127 per i debiti
//					TICKET
//					PRONTO_SOCCORSO
//					MANC_DISD_PRENOT
//					MANC_RIT_REFERTO
//					LIBERA_PROFESSIONE
		String trtSys = pendenza.getIdSystemTrt();
		String tributo = pendenza.getCdTrbEnte();
		List listaTributiList = new ArrayList<String>();
		listaTributiList.add(new String("PRONTO_SOCCORSO"));
		listaTributiList.add(new String("MANC_DISD_PRENOT"));
		listaTributiList.add(new String("MANC_RIT_REFERTO"));
		listaTributiList.add(new String("LIBERA_PROFESSIONE"));					
		listaTributiList.add(new String("TICKET"));
		
		//verifichiamo se deve essere eseguito il controllo sugli allegati
		String first = "";
		String second = "";
		if ("SIL_CONTICKI".equals(trtSys) && listaTributiList.contains(tributo)) {
			//eseguo controllo
			AllegatoModel allegatoModel = null;
			//se ci sono allegati...
			if (condizioni.getAllegati()!=null) {
				Iterator itAll = condizioni.getAllegati().iterator();				
				int size = condizioni.getAllegati().size();
				//...almeno un allegato di tipo XML deve esistere
				if (size>=1) {
					boolean xmlFound = false;
					while (itAll.hasNext()) {
						AllegatoModel allegato = (AllegatoModel) itAll.next();
						if ("XML_".equals(allegato.getTiCodificaBody())) {
							xmlFound = true;
							//qui non si ï¿½ capito se si deve controllare il titolo.. deve essere RicevutaSST?
							//ed anche il tipo... deve essere Ricevuta ?

							//come da ticket 1964, controllo solo il titolo
							if (!"RicevutaSST".equals(allegato.getTitolo()) ) 
								checkXml = false;						
						}
					}
					if (!xmlFound) {
						//se non ho trovato nemmeno un allegato di tipo XML... allora errore
						check = false;
					}							
				} else {
					//lista allegati ï¿½ zero.. allora errore
					check = false;
				} 									
			} else {
				//lista allegati ï¿½ vuota.. allora errore
				check = false;
			} 						

			
			
		}
		
		
		
		if (check==false) {
			errs.add(new DBError(errIds.allegatoContickiInvalid  , first + "-" + second));
		}
		if (checkXml==false) {
			errs.add(new DBError(errIds.allegatoContickiXmlInvalid , condizioni));
		}					
				
	}
	
	/**
	 * 
	 * @param pendenza
	 * @param pendXml
	 * @param condizioniDiPagamento
	 * @param errs
	 */
	public void checkCondizioniPagamentoRimborso(it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel pendenza,
			PendenzeModel pendXml,
			Collection condizioniDiPagamento, DBErrorsBag errs) {
		
		for (Iterator it = condizioniDiPagamento.iterator(); it.hasNext();) {
				
				CondizioniPagamentoModel cp = (CondizioniPagamentoModel) it.next();
	
				String stPagamento = cp.getStPagamento();
				
				if (PendenzeConsts.STATO_PAGAMENTO_RIMBORSATO.equalsIgnoreCase(stPagamento)) {
					
					// se lo stato della condizione impostato è "pagamento rimborsato" per proseguire deve essere:
					// --> la posizione deve essere pagata dentro o fuori iris (o già rimborsata)
					// --> lo stato della pendenza deve essere "chiusa" ( o in chiusura con la presente operazione)
					// --> l'operazione deve essere update status
					
					String stPendDB = ((pendenza!=null) ? pendenza.getStPendenza() : null);
					String stPendXML= ((pendXml!=null) ? pendXml.getStPendenza() : null);
					
					it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel cpDB =  (it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel) this.queryForObject("selectCondizionePagamentoToUpdate", cp);
					
					Integer idPaga = (Integer) this.queryForObject("selectCondizioniConPagamentoAssociato", cpDB);
								
					if (!(
						  (idPaga != null || (cpDB != null && (PendenzeConsts.STATO_PAGAMENTO_PAGATO.equals(cpDB.getStPagamento()) || PendenzeConsts.STATO_PAGAMENTO_RIMBORSATO.equals(cpDB.getStPagamento())))) && //posizione pagata su iris o fuori iris o già rimborosata
						  ("update".equalsIgnoreCase(pendXml.getOperationName())) && //update status
						  (cp.getTiOperazioneUpdate() == null || "update".equalsIgnoreCase(cp.getTiOperazioneUpdate())) && //tipo operazione "update o null (che prendiamo per buona
						  (PendenzeConsts.STATO_PENDENZA_CHIUSA.equalsIgnoreCase(stPendXML) ||(stPendXML == null && PendenzeConsts.STATO_PENDENZA_CHIUSA.equalsIgnoreCase(stPendDB))) //pendenza chiusa o in chiusura
						)) {

						errs.add(new DBError(
								errIds.condizionePagamentoRimborsoInvalid, 
								pendenza,
								errorsFactory.xmlErrrorsFactory.condizionePagamentoRimborsoInvalid(pendXml, cp)));
					}			
				}		
		}
		
	}
	
}
