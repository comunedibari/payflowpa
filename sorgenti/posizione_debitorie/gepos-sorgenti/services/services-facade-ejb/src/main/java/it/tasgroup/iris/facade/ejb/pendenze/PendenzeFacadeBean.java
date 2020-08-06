package it.tasgroup.iris.facade.ejb.pendenze;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.AllegatoAvvisoPosDeb;
import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaDettaglioVO;
import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.CondizionePagamentoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.CondizionePagamentoVO;
import it.nch.idp.posizionedebitoria.DebitorePosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.ImportoCondizionePagamentoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.PagamentoDettaglioIncassoVO;
import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaInputVO;
import it.nch.idp.posizionedebitoria.PrenotaAvvisiDigitaliVO;
import it.nch.idp.posizionedebitoria.VocePagamentoVO;
import it.nch.is.fo.BackEndMessage;
import it.nch.is.fo.profilo.Enti;
import it.nch.profile.IProfileManager;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.internal.AddOnManagerFactory;
import it.tasgroup.iris.business.ejb.client.pendenze.PendenzeBusinessLocal;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CfgModalitaPagamento;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.PrenotazioneAvvisiDigitali;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.domain.helper.BillInspector;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.domain.helper.PersistenceUtils;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.facade.ejb.authorization.VisibilityChecker;
import it.tasgroup.iris.facade.ejb.client.pendenze.PendenzeFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.pendenze.PendenzeFacadeRemote;
import it.tasgroup.services.util.IVocePagamento;

@Stateless(name = "PendenzeFacade")
public class PendenzeFacadeBean implements PendenzeFacadeLocal, PendenzeFacadeRemote {

	private static final Logger LOGGER = LogManager.getLogger(PendenzeFacadeBean.class);
	
	private String idTributo;
	
	@EJB(name = "PendenzeBusiness")
	private PendenzeBusinessLocal pendenzeBusiness;
	
	
	@Override
	public ContainerDTO exportAvvisi(String method, IProfileManager profilo,
			ContainerDTO dto) {
		return ricercaAvvisiInner(method, profilo, dto, /*isExport=*/true);
	}
	
	@Override
	public ContainerDTO exportAvvisiStorico(String method, IProfileManager profilo,
			ContainerDTO dto) {
		return ricercaAvvisiInnerStorico(method, profilo, dto, /*isExport=*/true);
	}
	
	@Override
	public ContainerDTO ricercaAvvisi(String method, IProfileManager profilo, ContainerDTO dto) {
		return ricercaAvvisiInner(method, profilo, dto, /*isExport=*/false);
		
	}
	
	@Override
	public ContainerDTO ricercaAvvisiStorico(String method, IProfileManager profilo, ContainerDTO dto) {
		return ricercaAvvisiInnerStorico(method, profilo, dto, /*isExport=*/false);
		
	}
	
	@Override
	public List<AvvisoPosizioneDebitoriaVO> ricercaAvvisiByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException {
		List<Pendenza> pendenze = pendenzeBusiness.ricercaAvvisiByCodPagamento(codPagamento, codiceFiscale, idEnte);
		List<AvvisoPosizioneDebitoriaVO> resultList = new ArrayList<AvvisoPosizioneDebitoriaVO>();
		for (Pendenza p: pendenze) {
			AvvisoPosizioneDebitoriaVO a = new AvvisoPosizioneDebitoriaVO();
        	this.fillAvvisoPosizioneDebitoriaVO(p, a, false, true);
        	resultList.add(a);
        }
		return resultList;

	}
	
	private ContainerDTO ricercaAvvisiInner(String method, IProfileManager profilo, ContainerDTO dto, boolean isExport) {
		
		Tracer.debug(getClass().getName(), "", " PendenzeFacadeBean >>> " + method + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		List<Pendenza> pendenze = pendenzeBusiness.switchRicercaAvvisi(method, profilo, dto);
		
		PosizioneDebitoriaInputVO filterParameters = (PosizioneDebitoriaInputVO) dto.getInputDTO();
		
		List<AvvisoPosizioneDebitoriaVO> resultList = new ArrayList<AvvisoPosizioneDebitoriaVO>();
		
		for (Pendenza p: pendenze) {
			
        	AvvisoPosizioneDebitoriaVO a = new AvvisoPosizioneDebitoriaVO();
        	
        	this.fillAvvisoPosizioneDebitoriaVO(p, a, filterParameters.isDebitoriRich(), isExport /* include i dati di condizione e pagamento, solo in caso di export */);
        	
        	resultList.add(a);
        }
		
		dto.setOutputDTOList(resultList);
		
		return dto;

		
	}
	
	private ContainerDTO ricercaAvvisiInnerStorico(String method, IProfileManager profilo, ContainerDTO dto, boolean isExport) {
		
		Tracer.debug(getClass().getName(), "", " PendenzeFacadeBean >>> " + method + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		List<Pendenza> pendenze = pendenzeBusiness.switchRicercaAvvisiStorico(method, profilo, dto);
		
		PosizioneDebitoriaInputVO filterParameters = (PosizioneDebitoriaInputVO) dto.getInputDTO();
		
		List<AvvisoPosizioneDebitoriaVO> resultList = new ArrayList<AvvisoPosizioneDebitoriaVO>();
		
		for (Pendenza p: pendenze) {
			
        	AvvisoPosizioneDebitoriaVO a = new AvvisoPosizioneDebitoriaVO();
        	
        	this.fillAvvisoPosizioneDebitoriaVO(p, a, filterParameters.isDebitoriRich(), isExport /* include i dati di condizione e pagamento, solo in caso di export */);
        	
        	resultList.add(a);
        }
		
		dto.setOutputDTOList(resultList);
		
		return dto;

		
	}
	
	/**
	 * Popolamento di un AvvisoPosizioneDebitoriaVO a partire da una pendenza. 
	 * Il metodo non crea direttamente il VO ma lo popola ricevendolo per parametro 
	 * Questo per sfruttare il polimorfismo (il new � nel chiamante).
	 * @param c
	 * @return un AvvisoPosizioneDebitoriaVO
	 */	
	private void fillAvvisoPosizioneDebitoriaVO(Pendenza p, AvvisoPosizioneDebitoriaVO a, boolean isDebitoriRich, boolean isCondizioniRich) { 
		
    	a.setIdPendenza(p.getIdPendenza());
    	a.setAnnoRif(p.getAnnoRiferimento());
    	a.setCodicePendenza(p.getIdPendenzaente());
    	a.setDataEmissione(p.getTsEmissioneente());
    	a.setEnte(p.getTributoEnte().getEntiobj().getDenominazione());
    	a.setCodEnte(p.getTributoEnte().getEntiobj().getCodiceEnte());
    	a.setTributo(p.getTributoEnte().getDeTrb());
    	a.setCod_tributo(p.getTributoEnte().getCdTrbEnte());
    	
    	a.setIdEnte(p.getTributoEnte().getEntiobj().getIdEnte());
    	a.setCdPlugin(p.getTributoEnte().getCfgTributoEntePlugin() != null ? p.getTributoEnte().getCfgTributoEntePlugin().getCdPlugin() : null);
    	
    	//
    	a.setTipoTributo(p.getCategoriaTributo().getDeTrb());
    	a.setIdTributo(p.getCategoriaTributo().getIdTributo());
    	a.setDescrizioneMittente((p.getIdMittente()!=null?p.getIdMittente():" - ")+"/"+(p.getDeMittente()!=null?p.getDeMittente():" - "));
    	a.setRiscossore(p.getCoRiscossore());
    	a.setRiferimento(p.getRiferimento());
    	a.setDataCreazione(p.getTsCreazioneente());
    	
    	a.setIdPendenzaEnte(p.getIdPendenzaente());
    	
    	String debitori = null;
    	 
    	if (isDebitoriRich)
    		debitori = BillInspector.getDebitoriRich(p,";");
    	else
    		debitori = BillInspector.getDebitori(p,";");
    	
    	a.setCoDestinatario(debitori);
    	a.setCodiceFiscale(debitori);
    	
    	a.setCausale(p.getDeCausale());
    	
    	a.setModalita(p.getFlModPagamento());
    	a.setImporto(p.getImTotale());
    	a.setStato(p.getStPendenza());
    	a.setImportoNonPagabile(BigDecimal.ZERO);
    	a.setImportoRiscosso(BigDecimal.ZERO);
    	a.setImportoSbf(BigDecimal.ZERO);
    	a.setImportoPagato(BillInspector.calcolaImportoPagato(p)); 
    	a.setCondizioniNonPagabili(BillInspector.calcolaCondizioniNonPagabili(p)); 
    	a.setCondizioniPagabili(BillInspector.calcolaCondizioniPagabili(p));
    	a.setDataScadenza(BillInspector.calcolaDataScadenza(p));
    	a.setDataFineValidita(BillInspector.calcolaDataFineValidita(p));
    	
    	a.setUrlUpdateService(p.getTributoEnte().getUrlUpdService());
    	a.setNote(p.getNote());
    	
    	if (isCondizioniRich) {
	    	List<CondizionePagamentoVO> condVOList = new  ArrayList<CondizionePagamentoVO>();
	    	
	    	for(CondizionePagamento cond : p.getCondizioni()){
	    		
	    		CondizionePagamentoVO condVO = fillCondizionePagamentoVO(cond);
	    		
	    		condVOList.add(condVO);
	    		
	    	}
	    	
	    	a.setCondizioni(condVOList);
    	}    	
    	
	}
	
		
	private CondizionePagamentoVO fillCondizionePagamentoVO(CondizionePagamento cond) {
		
		CondizionePagamentoVO condVO = new CondizionePagamentoVO();
		
		condVO.setIbanBeneficiario(cond.getIbanBeneficiario());
		
		condVO.setRagSocBeneficiario(cond.getEnte().getDenominazione());
		
		condVO.setIdPagamento(cond.getIdPagamento());
		
		condVO.setCausale(cond.getCausalePagamento());
		
		fillVociPagamento(condVO, cond);
		
		condVO.setIdPendenza(cond.getPendenza().getIdPendenzaente());
		
		condVO.setDataFineValidita(cond.getDtFinevalidita());
		
		condVO.setDataInizioValidita(cond.getDtIniziovalidita());
		
		condVO.setStatoPagamento(cond.getStPagamento());
		
		condVO.setDataPagamento(cond.getDtPagamento());
		
		condVO.setDescrizioneCanaleDiPagamento(cond.getDeCanalepag());
		
		condVO.setNotePagamento(cond.getDeNotePagamento());
		
		condVO.setImportoPagamento(cond.getImPagamento());
		
		condVO.setImportoTotale(cond.getImTotale());
		
		condVO.setIdCondizione(cond.getIdCondizione());
		
		Pagamenti pagam = null;
		
		try {
			pagam = BillItemInspector.getUniqueValidPayment(cond);
		} catch (IllegalStateException e) {
			
			// In caso di pagamento multiplo si gestisce. Questo per ora si vede solo in EXPORT CSV.
			return gestisciPagamentoMultiplo(condVO,cond);
		
		}
			
			
				
		if (pagam != null){
			
			PagamentoPosizioneDebitoriaVO pagamentoVO = new PagamentoPosizioneDebitoriaVO();
			
			pagamentoVO.setIdPagamento(pagam.getId().intValue());
			
			pagamentoVO.setDataValutaAccredito(pagam.getDataAccreditoEnte());
			
			pagamentoVO.setIdPendenza(pagam.getIdPendenzaente());
			
			pagamentoVO.setStato(pagam.getStPagamento());
			
			pagamentoVO.setNote(pagam.getNotePagamento());
			
			pagamentoVO.setImporto(pagam.getImPagato());

			pagamentoVO.setAssociatedDocAvailable(pagam.isAssociatedDocumentAvailable());
			
			PagamentoDettaglioIncassoVO dettaglioIncasso = new PagamentoDettaglioIncassoVO();
			
			GestioneFlussi distinta = pagam.getFlussoDistinta();
			
			dettaglioIncasso.setIdPsp(distinta.getCfgGatewayPagamento().getSystemId());
			dettaglioIncasso.setDescrizionePSP(distinta.getCfgGatewayPagamento().getSystemName());
			dettaglioIncasso.setIstitutoAttestante(distinta.getDescrizioneAttestante());
			dettaglioIncasso.setIdAttestante(distinta.getIdentificativoAttestante());
			dettaglioIncasso.setTipoIdAttestante(distinta.getTipoIdentificativoAttestante());
			dettaglioIncasso.setIuv(distinta.getIuv());
			dettaglioIncasso.setIur(pagam.getIdRiscossionePSP());
//			dettaglioIncasso.setRiferimentoContabile(pagam.getRRiferimentoContabile());
			dettaglioIncasso.setTrnRiversamento(pagam.getTRN());
			dettaglioIncasso.setDataRegolamento(pagam.getDataRegolamento());
			dettaglioIncasso.setIdFlussoRiversamento(pagam.getIdentificativoFlusso());
			dettaglioIncasso.setTotaleMovimentoAccredito(pagam.getTotaleRendicontazioneIncasso());

		
			pagamentoVO.setDettaglioIncasso(dettaglioIncasso);
			
			condVO.setDataPagamento(pagam.getTsDecorrenza());
			
			condVO.setDescrizioneCanaleDiPagamento(descrizioneModalitaPagamento(pagam));
			
			condVO.setPagamento(pagamentoVO);
							
		}
		
		return condVO;
	}


	private String descrizioneModalitaPagamento(Pagamenti pagam) {
        CfgModalitaPagamento modalitaPagamento = pagam.getFlussoDistinta().getCfgGatewayPagamento().getCfgModalitaPagamento();
        String descrizioneModalitaPagamento = modalitaPagamento != null ? modalitaPagamento.getDescrizione() : "";
		return descrizioneModalitaPagamento;
	}

	
	private CondizionePagamentoVO gestisciPagamentoMultiplo(CondizionePagamentoVO condVO, CondizionePagamento cond) {
		
		PagamentoPosizioneDebitoriaVO fakeP = new PagamentoPosizioneDebitoriaVO();
		 
		/* Gestione dell'informazione di eventuale pagamento multiplo, (ai fini al momento dell'export) 
		 * Metto i dati del primo pagamento che trovo, 
		 *   Nelle note metto "ATTENZIONE: PAGAMENTO MULTIPLO" 
		 */
		
		int numPagamentiEseguiti=0;
		BigDecimal importoTotalePagato = BigDecimal.ZERO.setScale(2);
		String descrizioneCanalePagamentoMultiplo = null;
		
		for (Pagamenti p:cond.getPagamenti()) {
			
			if (p.isExecutedPayment()) {
				
				numPagamentiEseguiti = numPagamentiEseguiti+1;
									
				fakeP.setDataValutaAccredito(p.getDataAccreditoEnte());
				
				fakeP.setIdPendenza(p.getIdPendenzaente());
				
				fakeP.setStato(p.getStPagamento());
				
				fakeP.setNote(p.getNotePagamento());
				
				fakeP.setImporto(p.getImPagato());
				
				fakeP.setIdPagamento(p.getId().intValue());

				// Gestisco l'importo totale come somma dell'importo dei singoli pagamenti.
				importoTotalePagato=importoTotalePagato.add(p.getImPagato());   
				
				condVO.setDataPagamento(p.getTsDecorrenza());
							
		        String descrizioneModalitaPagamento = descrizioneModalitaPagamento(p);

				condVO.setDescrizioneCanaleDiPagamento(descrizioneModalitaPagamento);
				
				 // Gestisco il canale di pagamento come lista  dei canali (nel caso multiplo..,).
				if (descrizioneCanalePagamentoMultiplo==null) {
					descrizioneCanalePagamentoMultiplo=descrizioneModalitaPagamento;
				} else {
					if (!descrizioneCanalePagamentoMultiplo.equals(descrizioneModalitaPagamento)) {
						descrizioneCanalePagamentoMultiplo = descrizioneCanalePagamentoMultiplo+", "+descrizioneModalitaPagamento;
					}
				}
				
			}
		}
		
		if (numPagamentiEseguiti>1) {
				// Se ho pi� pagamenti eseguiti allora sbianco alcuni campi (che non hanno pi� senso)
				// se dal For precedente si esce con un solo pagamento eseguito .. esco con quel pagamento.
				
				fakeP.setDataValutaAccredito(null);
				fakeP.setNote("ATTENZIONE: PAGAMENTO MULTIPLO (trovati "+numPagamentiEseguiti+" pagamenti validi per la stessa posizione)");
				fakeP.setImporto(importoTotalePagato);   //Nell'importo metto l'importo totale dei pagamenti eseguiti..
				condVO.setDataPagamento(null);
				condVO.setDescrizioneCanaleDiPagamento(descrizioneCanalePagamentoMultiplo);
				 
			}
			
		condVO.setPagamento(fakeP);
		
		return condVO;

	}
	
	private void fillVociPagamento(CondizionePagamentoVO condVO, CondizionePagamento cond) {
		
		condVO.setVociPagamento(new ArrayList<IVocePagamento>());
		
		for(VocePagamento voce: cond.getVociPagamento()){
			
			VocePagamentoVO voceVO = fillVocePagamentoVO(voce);
			
			condVO.getVociPagamento().add(voceVO);
		}		
		
	}
	
	private VocePagamentoVO fillVocePagamentoVO(VocePagamento voce) {
		
		VocePagamentoVO voceVO = new VocePagamentoVO();
		
		voceVO.setCoVoce(voce.getCoVoce());
		
		voceVO.setDeVoce(voce.getDeVoce());
		
		voceVO.setImVoce(voce.getImVoce());
		
		return voceVO;
	}

	@Override
	public AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso(
			String idPendenza, IProfileManager profilo) {
		
		Tracer.debug("PosizioneDebitoriaServiceImpl", "dettaglioAvvisoNew", "Start");
		
		AvvisoPosizioneDebitoriaDettaglioVO posdeb = new AvvisoPosizioneDebitoriaDettaglioVO();
		
		try {
			
			if (idPendenza!=null) {
				
				//Pendenza p = (Pendenza)outSearchPendenza.getVO();
				Pendenza p = pendenzeBusiness.getPendenzaById(idPendenza);
				
				this.fillAvvisoPosizioneDebitoriaVO(p,posdeb, false, true);
				// Settaggi specifici		
				posdeb.setCartellaPagamento(p.getCartellaPagamento());
				posdeb.setUltimaModifica(p.getTsAggiornamento());


				if (posdeb.getIdPendenza() != null) {

					// recupero gli allegati dell'avviso
					
					List<AllegatiPendenza> allegati = pendenzeBusiness.listaAllegatiPendenza(posdeb.getIdPendenza());
										
					List<AllegatoAvvisoPosDeb> allegatiPendenzaVo = new ArrayList<AllegatoAvvisoPosDeb>();

					for (AllegatiPendenza allegato:allegati) {
											
						AllegatoAvvisoPosDeb  allegatoVO = buildAllegatoAvvisoPosDeb(allegato,false);
						
						allegatiPendenzaVo.add(allegatoVO);
						
					}	
					
					
					posdeb.setAllegati(allegatiPendenzaVo);

					List<DebitorePosizioneDebitoriaVO> listaDebitoriVo = new  ArrayList<DebitorePosizioneDebitoriaVO> ();
					
					// recupero l'elenco debitori
					for(DestinatariPendenza destinatario:p.getDestinatari()) {
						
						if (!"DE".equals(destinatario.getTiDestinatario())) {
							DebitorePosizioneDebitoriaVO destinatarioVO = new DebitorePosizioneDebitoriaVO();
							destinatarioVO.setCoDestinatario(destinatario.getCoDestinatario());
							destinatarioVO.setDesDestinatario(destinatario.getDeDestinatario());
							listaDebitoriVo.add(destinatarioVO);
						}
					}
					
					posdeb.setDebitori(listaDebitoriVo);

					
					// recupero le condizioni di pagamento in unica soluzione (S=singola, E=singola o a rate)
					List<CondizionePagamentoPosizioneDebitoriaVO> condPagamentoUnica = null;

					if ("S".equals(posdeb.getModalita()) || "E".equals(posdeb.getModalita())) {
					
						for (CondizionePagamento c:p.getCondizioni()) {
							if ("S".equals(c.getTiPagamento())) {
								if (condPagamentoUnica==null) {
									condPagamentoUnica = new ArrayList<CondizionePagamentoPosizioneDebitoriaVO>();
								}
								condPagamentoUnica.add(buildCondizionePagamentoPosizioneDebitoriaVO(c));
							}
						}
					}	
							
//						if (condPagamentoUnica != null && !condPagamentoUnica.isEmpty()) {
//
//							Iterator<CondizionePagamentoPosizioneDebitoriaVO> iter = condPagamentoUnica.iterator();
//							while (iter.hasNext()) {
//								CondizionePagamentoPosizioneDebitoriaVO tmp_cppd = (CondizionePagamentoPosizioneDebitoriaVO) iter.next();
//								
//								List<AllegatiPendenza> allegatiCondPagRate = pendenzeBusiness.listaAllegatiCondizionePagamento(posdeb.getIdPendenza(), tmp_cppd.getIdCondizione()); 
//								
//								List<AllegatoAvvisoPosDeb> allegatiCondPagRateVo = new ArrayList<AllegatoAvvisoPosDeb> ();
//								
//								for(AllegatiPendenza allegato:allegatiCondPagRate) {
//									AllegatoAvvisoPosDeb allegatoVO  = buildAllegatoAvvisoPosDeb(allegato);
//									allegatiCondPagRateVo.add(allegatoVO);
//								}
//														
////								if (allegatiCondPagRate != null) {
////									// imposto gli allegati per questa rata
////									tmp_cppd.setAllegati(allegatiCondPagRate);
////								}
//								
//								tmp_cppd.setAllegati(allegatiCondPagRateVo);
//								
//								// recupero la lista col dettaglio dell'importo per questa rata
//								List<ImportoCondizionePagamentoPosizioneDebitoriaVO> listaDettImporto_tmp = daoDB.elencoDettaglioImporto(posdeb.getIdPendenza(), tmp_cppd.getIdCondizione());
//								
//								
//								if (listaDettImporto_tmp != null)
//									tmp_cppd.setDettaglioImporto(listaDettImporto_tmp);
//							}
//						}
//					}
					
					posdeb.setSoluzionePagUnica(condPagamentoUnica);


					// recupero la condizione di pagamento a rate (R=a rate, E=singola o a rate)
					List<CondizionePagamentoPosizioneDebitoriaVO> listaCondPagamentoRateale = null;
					
					if ("R".equals(posdeb.getModalita()) || "E".equals(posdeb.getModalita())) {

						for (CondizionePagamento c:p.getCondizioni()) {
							if ("R".equals(c.getTiPagamento())) {
								if (listaCondPagamentoRateale==null) {
									listaCondPagamentoRateale = new ArrayList<CondizionePagamentoPosizioneDebitoriaVO>();
								}								
								listaCondPagamentoRateale.add(buildCondizionePagamentoPosizioneDebitoriaVO(c));
							}
						}
					}	

						
//						if (listaCondPagamentoRateale != null && !listaCondPagamentoRateale.isEmpty()) {
//							Iterator<CondizionePagamentoPosizioneDebitoriaVO> iter = listaCondPagamentoRateale.iterator();
//							while (iter.hasNext()) {
//								CondizionePagamentoPosizioneDebitoriaVO tmp_cppd = (CondizionePagamentoPosizioneDebitoriaVO) iter.next();
//								
//								List<AllegatoAvvisoPosDeb> allegatiCondPagRate = daoDB.listaAllegatiCondPagamento(posdeb.getIdPendenza(), tmp_cppd.getIdCondizione());
//								if (allegatiCondPagRate != null) {
//									// imposto gli allegati per questa rata
//									tmp_cppd.setAllegati(allegatiCondPagRate);
//								}
//								// recupero la lista col dettaglio dell'importo per questa rata
//								List<ImportoCondizionePagamentoPosizioneDebitoriaVO> listaDettImporto_tmp = daoDB.elencoDettaglioImporto(posdeb.getIdPendenza(), tmp_cppd.getIdCondizione());
//								if (listaDettImporto_tmp != null) {
//									tmp_cppd.setDettaglioImporto(listaDettImporto_tmp);
//								}
//							}
//
//						}
//					}
					posdeb.setRatePagamento(listaCondPagamentoRateale);
					
					if (p.getTributoStrutturato()!=null) {
						posdeb.setTributo_strutturato(new Long(p.getTributoStrutturato().getId()).intValue());
						
						if (AddOnManagerFactory.exists(p.getTributoStrutturato().getTipoTributo())) {
							TributoStrutturato ts = (TributoStrutturato)pendenzeBusiness.getTributoStrutturatoByIdPendenza(posdeb.getIdPendenza());
							ts = PersistenceUtils.materialize(ts);
							ts.getDettaglioStrutturato().getAnnoRiferimento(); // evita lazy init errorr.
							posdeb.setTributoStrutturato(PersistenceUtils.materialize(ts));
						}
						
					}
					
				}
				
				final Enti ente = p.getTributoEnte().getEntiobj(); // get pendenza owner
				VisibilityChecker.checkVisibilitaDatiEnte(profilo,ente);
			}		
			
		} catch (Exception e) { 
			e.printStackTrace();
			Tracer.error(this.getClass().getName(), "dettaglioAvviso", e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
		Tracer.debug("PosizioneDebitoriaServiceImpl", "dettaglioAvviso", "End");
		return posdeb;
	}
	
	
	@Override
	public AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvvisoStorico(
			String idPendenza, IProfileManager profilo) {
		
		Tracer.debug("PosizioneDebitoriaServiceImpl", "dettaglioAvvisoNew", "Start");
		
		AvvisoPosizioneDebitoriaDettaglioVO posdeb = new AvvisoPosizioneDebitoriaDettaglioVO();
		
		try {
			
			if (idPendenza!=null) {
				
				//Pendenza p = (Pendenza)outSearchPendenza.getVO();
				Pendenza p = pendenzeBusiness.getPendenzaByIdStorico(idPendenza);
				
				this.fillAvvisoPosizioneDebitoriaVO(p,posdeb, false, true);
				// Settaggi specifici		
				posdeb.setCartellaPagamento(p.getCartellaPagamento());
				posdeb.setUltimaModifica(p.getTsAggiornamento());


				if (posdeb.getIdPendenza() != null) {

					// recupero gli allegati dell'avviso
					
					List<AllegatiPendenza> allegati = pendenzeBusiness.listaAllegatiPendenzaStorico(posdeb.getIdPendenza());
										
					List<AllegatoAvvisoPosDeb> allegatiPendenzaVo = new ArrayList<AllegatoAvvisoPosDeb>();

					for (AllegatiPendenza allegato:allegati) {
											
						AllegatoAvvisoPosDeb  allegatoVO = buildAllegatoAvvisoPosDeb(allegato,false);
						
						allegatiPendenzaVo.add(allegatoVO);
						
					}	
					
					
					posdeb.setAllegati(allegatiPendenzaVo);

					List<DebitorePosizioneDebitoriaVO> listaDebitoriVo = new  ArrayList<DebitorePosizioneDebitoriaVO> ();
					
					// recupero l'elenco debitori
					for(DestinatariPendenza destinatario:p.getDestinatari()) {
						
						if (!"DE".equals(destinatario.getTiDestinatario())) {
							DebitorePosizioneDebitoriaVO destinatarioVO = new DebitorePosizioneDebitoriaVO();
							destinatarioVO.setCoDestinatario(destinatario.getCoDestinatario());
							destinatarioVO.setDesDestinatario(destinatario.getDeDestinatario());
							listaDebitoriVo.add(destinatarioVO);
						}
					}
					
					posdeb.setDebitori(listaDebitoriVo);

					
					// recupero le condizioni di pagamento in unica soluzione (S=singola, E=singola o a rate)
					List<CondizionePagamentoPosizioneDebitoriaVO> condPagamentoUnica = null;

					if ("S".equals(posdeb.getModalita()) || "E".equals(posdeb.getModalita())) {
					
						for (CondizionePagamento c:p.getCondizioni()) {
							if ("S".equals(c.getTiPagamento())) {
								if (condPagamentoUnica==null) {
									condPagamentoUnica = new ArrayList<CondizionePagamentoPosizioneDebitoriaVO>();
								}
								condPagamentoUnica.add(buildCondizionePagamentoPosizioneDebitoriaVOStorico(c));
							}
						}
					}	
							
//						if (condPagamentoUnica != null && !condPagamentoUnica.isEmpty()) {
//
//							Iterator<CondizionePagamentoPosizioneDebitoriaVO> iter = condPagamentoUnica.iterator();
//							while (iter.hasNext()) {
//								CondizionePagamentoPosizioneDebitoriaVO tmp_cppd = (CondizionePagamentoPosizioneDebitoriaVO) iter.next();
//								
//								List<AllegatiPendenza> allegatiCondPagRate = pendenzeBusiness.listaAllegatiCondizionePagamento(posdeb.getIdPendenza(), tmp_cppd.getIdCondizione()); 
//								
//								List<AllegatoAvvisoPosDeb> allegatiCondPagRateVo = new ArrayList<AllegatoAvvisoPosDeb> ();
//								
//								for(AllegatiPendenza allegato:allegatiCondPagRate) {
//									AllegatoAvvisoPosDeb allegatoVO  = buildAllegatoAvvisoPosDeb(allegato);
//									allegatiCondPagRateVo.add(allegatoVO);
//								}
//														
////								if (allegatiCondPagRate != null) {
////									// imposto gli allegati per questa rata
////									tmp_cppd.setAllegati(allegatiCondPagRate);
////								}
//								
//								tmp_cppd.setAllegati(allegatiCondPagRateVo);
//								
//								// recupero la lista col dettaglio dell'importo per questa rata
//								List<ImportoCondizionePagamentoPosizioneDebitoriaVO> listaDettImporto_tmp = daoDB.elencoDettaglioImporto(posdeb.getIdPendenza(), tmp_cppd.getIdCondizione());
//								
//								
//								if (listaDettImporto_tmp != null)
//									tmp_cppd.setDettaglioImporto(listaDettImporto_tmp);
//							}
//						}
//					}
					
					posdeb.setSoluzionePagUnica(condPagamentoUnica);


					// recupero la condizione di pagamento a rate (R=a rate, E=singola o a rate)
					List<CondizionePagamentoPosizioneDebitoriaVO> listaCondPagamentoRateale = null;
					
					if ("R".equals(posdeb.getModalita()) || "E".equals(posdeb.getModalita())) {

						for (CondizionePagamento c:p.getCondizioni()) {
							if ("R".equals(c.getTiPagamento())) {
								if (listaCondPagamentoRateale==null) {
									listaCondPagamentoRateale = new ArrayList<CondizionePagamentoPosizioneDebitoriaVO>();
								}								
								listaCondPagamentoRateale.add(buildCondizionePagamentoPosizioneDebitoriaVOStorico(c));
							}
						}
					}	

						
//						if (listaCondPagamentoRateale != null && !listaCondPagamentoRateale.isEmpty()) {
//							Iterator<CondizionePagamentoPosizioneDebitoriaVO> iter = listaCondPagamentoRateale.iterator();
//							while (iter.hasNext()) {
//								CondizionePagamentoPosizioneDebitoriaVO tmp_cppd = (CondizionePagamentoPosizioneDebitoriaVO) iter.next();
//								
//								List<AllegatoAvvisoPosDeb> allegatiCondPagRate = daoDB.listaAllegatiCondPagamento(posdeb.getIdPendenza(), tmp_cppd.getIdCondizione());
//								if (allegatiCondPagRate != null) {
//									// imposto gli allegati per questa rata
//									tmp_cppd.setAllegati(allegatiCondPagRate);
//								}
//								// recupero la lista col dettaglio dell'importo per questa rata
//								List<ImportoCondizionePagamentoPosizioneDebitoriaVO> listaDettImporto_tmp = daoDB.elencoDettaglioImporto(posdeb.getIdPendenza(), tmp_cppd.getIdCondizione());
//								if (listaDettImporto_tmp != null) {
//									tmp_cppd.setDettaglioImporto(listaDettImporto_tmp);
//								}
//							}
//
//						}
//					}
					posdeb.setRatePagamento(listaCondPagamentoRateale);
					
					if (p.getTributoStrutturato()!=null) {
						posdeb.setTributo_strutturato(new Long(p.getTributoStrutturato().getId()).intValue());
						
						if (AddOnManagerFactory.exists(p.getTributoStrutturato().getTipoTributo())) {
							TributoStrutturato ts = (TributoStrutturato)pendenzeBusiness.getTributoStrutturatoByIdPendenzaStorico(posdeb.getIdPendenza());
							ts = PersistenceUtils.materialize(ts);
							ts.getDettaglioStrutturato().getAnnoRiferimento(); // evita lazy init errorr.
							posdeb.setTributoStrutturato(PersistenceUtils.materialize(ts));
						}
						
					}
					
				}
				
				final Enti ente = p.getTributoEnte().getEntiobj(); // get pendenza owner
				VisibilityChecker.checkVisibilitaDatiEnte(profilo,ente);
			}		
			
		} catch (Exception e) { 
			e.printStackTrace();
			Tracer.error(this.getClass().getName(), "dettaglioAvviso", e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
		Tracer.debug("PosizioneDebitoriaServiceImpl", "dettaglioAvviso", "End");
		return posdeb;
	}
	
	private CondizionePagamentoPosizioneDebitoriaVO buildCondizionePagamentoPosizioneDebitoriaVOStorico(CondizionePagamento c) {
		CondizionePagamentoPosizioneDebitoriaVO result = fromCondizionePagamento(c);
		// ----------------------
		// Allegati
		// ----------------------
		List<AllegatiPendenza> allegatiCondPag = pendenzeBusiness.listaAllegatiCondizionePagamentoStorico(c.getPendenza().getIdPendenza(), c.getIdCondizione()); 
		List<AllegatoAvvisoPosDeb> allegatiCondPagVo = new ArrayList<AllegatoAvvisoPosDeb> ();
		for(AllegatiPendenza allegato:allegatiCondPag) {
			AllegatoAvvisoPosDeb allegatoVO  = buildAllegatoAvvisoPosDeb(allegato,false);
			allegatiCondPagVo.add(allegatoVO);
		}
		result.setAllegati(allegatiCondPagVo);
		
		// ------------------------- 
		// PrenotaAvvisiDigitali 
		// ------------------------- 
		List<PrenotazioneAvvisiDigitali> prenotazioneAvvDigitali = pendenzeBusiness.getPrenotazioneAvvisiDigitaliByCondizioneStorico(c.getIdCondizione()); 
		List<PrenotaAvvisiDigitaliVO> prenotazioneAvvisiDigitVO = new ArrayList<PrenotaAvvisiDigitaliVO>(); 
		for(PrenotazioneAvvisiDigitali pren:prenotazioneAvvDigitali){ 
			PrenotaAvvisiDigitaliVO prenVO = buildPrenotazioneAvvisiDigitali(pren); 
			prenotazioneAvvisiDigitVO.add(prenVO); 
		} 
		result.setPrenotaAvvisiDigitali(prenotazioneAvvisiDigitVO); 
		return result;
	}

	/**
	 * Popolamento di un VO a partire da una condizione pagamento  
	 * @param c
	 * @return un CondizionePagamentoPosizioneDebitoriaVO
	 */
	private CondizionePagamentoPosizioneDebitoriaVO buildCondizionePagamentoPosizioneDebitoriaVO(CondizionePagamento c) {
		CondizionePagamentoPosizioneDebitoriaVO result = fromCondizionePagamento(c);
		

		// ----------------------
		// Allegati
		// ----------------------
		List<AllegatiPendenza> allegatiCondPag = pendenzeBusiness.listaAllegatiCondizionePagamento(c.getPendenza().getIdPendenza(), c.getIdCondizione()); 
		List<AllegatoAvvisoPosDeb> allegatiCondPagVo = new ArrayList<AllegatoAvvisoPosDeb> ();
		for(AllegatiPendenza allegato:allegatiCondPag) {
			AllegatoAvvisoPosDeb allegatoVO  = buildAllegatoAvvisoPosDeb(allegato,false);
			allegatiCondPagVo.add(allegatoVO);
		}						
		result.setAllegati(allegatiCondPagVo);
		
		 
	 		                 
		// ------------------------- 
		// PrenotaAvvisiDigitali 
		// ------------------------- 
		List<PrenotazioneAvvisiDigitali> prenotazioneAvvDigitali = pendenzeBusiness.getPrenotazioneAvvisiDigitaliByCondizione(c.getIdCondizione()); 

		List<PrenotaAvvisiDigitaliVO> prenotazioneAvvisiDigitVO = new ArrayList<PrenotaAvvisiDigitaliVO>(); 

		for(PrenotazioneAvvisiDigitali pren:prenotazioneAvvDigitali){ 
			PrenotaAvvisiDigitaliVO prenVO = buildPrenotazioneAvvisiDigitali(pren); 
			prenotazioneAvvisiDigitVO.add(prenVO); 
		} 
		result.setPrenotaAvvisiDigitali(prenotazioneAvvisiDigitVO); 


		return result;
	}

	private CondizionePagamentoPosizioneDebitoriaVO fromCondizionePagamento(CondizionePagamento c) {
		CondizionePagamentoPosizioneDebitoriaVO result = new CondizionePagamentoPosizioneDebitoriaVO();
		result.setIdCondizione(c.getIdCondizione());
		result.setIdPagamento(c.getIdPagamento());
		result.setDataScadenza(c.getDtScadenza());
		result.setDataPagamento(c.getDtPagamento());
		result.setCanalePagamento(c.getDeCanalepag());
		result.setDataInizio(c.getDtIniziovalidita());
		result.setDataFine(c.getDtFinevalidita());
		result.setTotale(c.getImTotale());
		result.setStatoCondizione(c.getStPagamento());
		result.setCodCIP(c.getCoCip());
		result.setIdPendenza(c.getPendenza().getIdPendenza());
		result.setTipoPagamento(c.getTiPagamento());
		result.setCausaleCondizione(c.getCausalePagamento());
		result.setImportoPagamento(c.getImPagamento());
		result.setMezzoPagamento(c.getDeMezzoPagamento());
		result.setNotePagamento(c.getDeNotePagamento());
		
		Pagamenti p = BillItemInspector.getMainPayment(c);
		
		if (p!=null) {
			
			result.setStatoPagamento(p.getStPagamento());
			result.setStatoIncasso(p.getFlagIncasso());
			result.setDataDecorrenza(p.getTsDecorrenza());
			result.setIdPagamentoAssociato(p.getId().intValue());
			result.setIdDistintaAssociata(p.getFlussoDistinta().getId().intValue());
			result.setFornitoreGateway(p.getFlussoDistinta().getCfgGatewayPagamento().getCfgFornitoreGateway().getBundleKey());
			result.setTsInserimentoDistinta(p.getFlussoDistinta().getTsInserimento());
			result.setCodPagamento(p.getFlussoDistinta().getCodPagamento());
			result.setCodPagante(p.getFlussoDistinta().getUtentecreatore());
			result.setImportoPagamentoIris(p.getImPagato());
		}
		result.setNumPagamenti(c.getPagamenti().size());
		
		// Conto i documenti attivi associati e trovo l'ultimo emesso
		int numDocumenti=0;
		String idDocumento = null;
		String intestatarioDDP = null;
		String cfPaganteDDP = null;
		if (c.getCondizioniDocumento()!=null ) {
			Timestamp lastInsertedTimestamp=new Timestamp(0L);
			for (CondizioneDocumento cd : c.getCondizioniDocumento()) {
				if (cd.getTsAnnullamento() == null) {
					numDocumenti=numDocumenti+1;
					if (cd.getTsInserimento().after(lastInsertedTimestamp)) {
						lastInsertedTimestamp=cd.getTsInserimento();
						idDocumento=cd.getDocumento().getId();
						intestatarioDDP = cd.getDocumento().getIntestatario();
						cfPaganteDDP = cd.getDocumento().getOpInserimento();
					}
				}
			}
		} 
		result.setNumDocumenti(numDocumenti);
		result.setIdDocumento(idDocumento);
		result.setIntestatarioDDP(intestatarioDDP);
		result.setCfPaganteDDP(cfPaganteDDP);

		c.updateStatoPagamentoCalcolato();
		result.setStatoPagamentoCondizione(c.getStatoPagamentoCalcolato());
		
		// --------------------------------------------------
		//  Voci Pagamento
		// --------------------------------------------------
		
		// recupero la lista col dettaglio dell'importo per questa condizione
		
		List<ImportoCondizionePagamentoPosizioneDebitoriaVO> listaDettImporto_tmp = new ArrayList<ImportoCondizionePagamentoPosizioneDebitoriaVO>();
				
		for(VocePagamento voce:c.getVociPagamento()) {
			ImportoCondizionePagamentoPosizioneDebitoriaVO voceVO = new ImportoCondizionePagamentoPosizioneDebitoriaVO();
			voceVO.setDeVoce(voce.getDeVoce());
			voceVO.setImVoce(voce.getImVoce());
			listaDettImporto_tmp.add(voceVO);
		}
		
		result.setDettaglioImporto(listaDettImporto_tmp);

		return result;
	}
	 
	private PrenotaAvvisiDigitaliVO buildPrenotazioneAvvisiDigitali(PrenotazioneAvvisiDigitali pren) { 
		PrenotaAvvisiDigitaliVO prenVO = new PrenotaAvvisiDigitaliVO(); 
		prenVO.setId(pren.getId()); 
		prenVO.setIdCondizione(pren.getIdCondizione());             
		prenVO.setIdEnte(pren.getIdEnte());                     
		prenVO.setIdPagamento(pren.getIdPagamento());                
		prenVO.setCodiceAvviso(pren.getCodiceAvviso());              
		prenVO.setTipoOperazioneOriginale(pren.getTipoOperazioneOriginale());   
		prenVO.setTipoOperazioneAvviso(pren.getTipoOperazioneAvviso());    
		prenVO.setTipoProcesso(pren.getTipoProcesso());            
		prenVO.setIdRichiestaAvviso(pren.getIdRichiestaAvviso());       
		prenVO.setStatoAvviso(pren.getStatoAvviso());              
		prenVO.setDescrStatoAvviso(pren.getDescrStatoAvviso());           
		prenVO.setNumTentativiAvviso(pren.getNumTentativiAvviso());         
		prenVO.setIdFileAvvisatura(pren.getIdFileAvvisatura());          
		return prenVO; 
	} 

	public AllegatoAvvisoPosDeb buildAllegatoAvvisoPosDeb(AllegatiPendenza allegato, boolean includiBody) {

		AllegatoAvvisoPosDeb  allegatoVO = new AllegatoAvvisoPosDeb();
		allegatoVO.setIdallegato(allegato.getIdAllegato());
		allegatoVO.setIdpendenza(allegato.getIdPendenza());
		allegatoVO.setIdcondizione(allegato.getIdCondizione());
		allegatoVO.setTsDecorrenza(allegato.getTsDecorrenza());
		allegatoVO.setFlContesto(allegato.getFlContesto());
		allegatoVO.setTiAllegato(allegato.getTiAllegato());
		allegatoVO.setTitolo(allegato.getTitolo());
		allegatoVO.setTiCodificaBody(allegato.getTiCodificaBody());
		allegatoVO.setStRiga(allegato.getStRiga());
		allegatoVO.setVersione(allegato.getPrVersione());
	
		// Nel DTO avr� il contenuto  solo  se estratto da allegato.
		if (includiBody)  {
			allegatoVO.setDatiBodyAsBytes(allegato.getDatiBody());
		}
	
		return allegatoVO;

	}

	@Override
	public AllegatoAvvisoPosDeb getAllegatoAvvisoById(String idAllegato) {
		AllegatiPendenza allegato = pendenzeBusiness.getAllegatoPendenzaById(idAllegato);
		AllegatoAvvisoPosDeb allegatoVo = buildAllegatoAvvisoPosDeb(allegato,true);
		return  allegatoVo;
	}

	@Override
	public String getIdPendenzaByChiaveSemantica(String cdTrbEnte,
			String idEnte, String idPendenzaEnte) {
		Pendenza p = pendenzeBusiness.getPendenzaByChiaveSemantica(cdTrbEnte, idEnte, idPendenzaEnte); 
		if (p==null) {
			return  null;
		}
		
		return p.getIdPendenza();
	}
	
	@Override 
	public void resettaPrenotaAvvisiDigitali(Long id, String tipo) { 
		pendenzeBusiness.resettaPrenotaAvvisiDigitali(id, tipo);  

	} 

	

}
