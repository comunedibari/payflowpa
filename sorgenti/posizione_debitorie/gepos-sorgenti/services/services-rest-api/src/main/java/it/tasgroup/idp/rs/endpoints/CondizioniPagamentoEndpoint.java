package it.tasgroup.idp.rs.endpoints;

import static it.tasgroup.iris.shared.util.UtilDate.dateGreaterThanToday;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.idp.rs.model.CondizionePagamento;
import it.tasgroup.idp.rs.model.CondizionePagamento.InfoDebitori;
import it.tasgroup.idp.rs.model.Profilo;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.idp.rs.util.GWRestProfile;
import it.tasgroup.iris.dto.exception.InvalidInputException;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacadeLocal;
import it.tasgroup.iris.facade.ejb.restapi.PaymentAPIFacadeBeanLocal;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;
import it.toscana.rete.cart.www.servizi.iris.attualizzatore.AttualizzaPendenza;
import it.toscana.rete.cart.www.servizi.iris.attualizzatore.AttualizzaPendenzaCodFscDebitore;
import it.toscana.rete.cart.www.servizi.iris.attualizzatore.AttualizzaPendenzaResponse;
import it.toscana.rete.cart.www.servizi.iris.attualizzatore.AttualizzatoreBolloAutoInvoker;
import it.toscana.rete.cart.www.servizi.iris.attualizzatore.TipoDebitore;
import it.toscana.rete.cart.www.servizi.iris.attualizzatore.TipoOperazione;

/**
 * Condizioni di Pagamento.
 */
@Path("/")
public class CondizioniPagamentoEndpoint {

/*    @EJB
    CondizioniPagamentoController controller;*/

    @EJB
    PaymentAPIFacadeBeanLocal paymentAPI;

    @EJB
    DistintePagamentoFacadeLocal distintePagamentoFacade;
    

    /**
     * Ritorna una lista di condizioni di pagamento che soddisfano i criteri di ricerca impostati. <br>
     * Deve essere passato obbligatoriamente l'identificativo del pagamento (<em>idPagamentoEnte</em>) ed il
     * codiceFiscale del debitore (<em>codiceFiscaleDebitore</em>) ed un criterio a scelta tra
     * codice dell'ente (<em>codEnte</em>) e codice categoria del tributo (<em>codCategoriaTributo</em>).<br>
     * Se viene passato anche  il codice identificativo dell'ente (<em>codEnte</em>) il sistema garantisce l'univocita'
     * della risposta.<br>
     * Se invece viene passato il codice identificativo della categoria del tributo (<em>codCategoriaTributo</em>)
     * l'univocita' della risposta e' tale se e solo se la piattaforma assicura l'univocita' del campo <em>idPagamentoEnte</em> indipendentemente dall'ente.<br>
     *
     * @param codCategoriaTributo   [Opzionale]: Codice categoria tributo assegnato dalla piattaforma (e.g. Categoria013, "PrestazioniSanitarie")
     * @param codEnte               [Opzionale]: Codice dell'ente sulla piattaforma (e.g. ASL8Arezzo)
     * @param codiceFiscaleDebitore [Obbligatorio]: Codice Fiscale del debitore, intestatario del debito (e.g. "RPTSFN70M14A390N" )
     * @param idPagamentoEnte       [Obbligatorio]: Identificativo del pagamento assegnato dall'ente creditore (e.g il codice IUV assegnato  alla posizione dall'ente, il numero prenotazione di un ticket sanitario, etc.)
     *                              E' il codice che viene riportato nel bollettino rilasciato all'utente per il pagamento.
     * @return Array di <a href="el_ns0_condizionePagamento.html">CondizionePagamento</a>
     */
    @GET
    @Produces("application/json")
    @Path("condizioni_pagamento")
    public List<CondizionePagamento> getCondizioni(@QueryParam("codCategoriaTributo") String codCategoriaTributo,
                                                   @QueryParam("codEnte") String codEnte,
                                                   @QueryParam("codiceFiscaleDebitore") String codiceFiscaleDebitore,
                                                   @QueryParam("idPagamentoEnte") String idPagamentoEnte) throws Exception {

        if (codiceFiscaleDebitore == null || idPagamentoEnte == null) {
          throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String[] {"codiceFiscaleDebitore","idPagamentoEnte"}, "Parametri Obbligatori");
        } else if (codEnte == null && codCategoriaTributo == null) {
           throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String [] {"codCategoriaTributo", "codEnte"}, "I parametri sono ALTERNATIVI (almemo uno deve essere specificato)");
        }

        //return paymentAPI.getCondizioni(idPagamentoEnte, codEnte, codCategoriaTributo, codiceFiscaleDebitore);
   		// #3623 attualizzazione risultato
		List<CondizionePagamento> condizioni = paymentAPI.getCondizioni(idPagamentoEnte, codEnte, codCategoriaTributo, codiceFiscaleDebitore);
		Map<String, String> esitoAttualizzazione = attualizzaBolliAuto(condizioni);
		if (esitoAttualizzazione.values().contains("OK")) {
			// ricarico perche' una o piu' pendenze sono state attualizzate
			condizioni = paymentAPI.getCondizioni(idPagamentoEnte, codEnte, codCategoriaTributo, codiceFiscaleDebitore);
		}
		// aggiorno i risultati con gli esiti
		return aggiungiEsitoAttualizzazioneAlleCondizioni(condizioni, esitoAttualizzazione);

    }
    
    /**
     * Restituisce la lista dei pagamenti eseguiti per la condizione di pagamento data in input.
     * idCondizione va a formare l'url della chiamata.
     * Attualmente non attiva, pronta per sviluppi futuri.
     *
     * @param idCondizione       [Obbligatorio]: Identificativo della condizione di pagamento da cercare tra i pagamenti eseguiti
     * @return List di oggetti Pagamento.
     */
    
//    @SuppressWarnings("unchecked")
	@GET
    @Produces({"application/json","application/xml"})
    @Path("condizioni_pagamento/{idCondizione}/pagamenti")
    public List<Pagamento> getPagamento(@PathParam("idCondizione") String idCondizione) throws Exception {

    	throw new UnsupportedOperationException("API REST non attiva");
    	
//    	List<Pagamento> listaPagamenti = new ArrayList<Pagamento>();
//    	if (idCondizione == null || idCondizione.isEmpty()) {
//            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String[] {"idCondizione"}, "Parametro Obbligatoro");
//        }
//        //checkPagination(limit, offset);
//        
//        ContainerDTO outputDTO = distintePagamentoFacade.getPagamentoEseguito(idCondizione);
//        listaPagamenti = outputDTO.getOutputDTOList();
//        
//        return listaPagamenti;
    }


    /**
     * Ritorna un pagamento dati i tre parametri (obbligatori) codFiscaleCreditore, idPagamento e importo.
     * @param codFiscaleCreditore [Obbligatorio]: Codice Fiscale del creditore
     * @param idPagamentoEnte [Obbligatorio]: Identificativo del pagamento assegnato dall'ente creditore (e.g il codice IUV assegnato  alla posizione dall'ente, il numero prenotazione di un ticket sanitario, etc.)
     * @param importo [Obbligatorio]: importo del pagamento
     * @return
     * @throws Exception
     */
    @GET
    @Produces("application/json")
    @Path("condizione_pagamento")
    public CondizionePagamento getCondizioneVerified(@QueryParam("codFiscaleCreditore") String codFiscaleCreditore,
                                                     @QueryParam("idPagamentoEnte") String idPagamentoEnte,
                                                     @QueryParam("importo") BigDecimal importo) throws Exception {
        if (codFiscaleCreditore == null || idPagamentoEnte == null || importo == null) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String[] {"codFiscaleCreditore","idPagamentoEnte", "importo"}, "Parametri Obbligatori");
        }
        return paymentAPI.getCondizioneQRCode(codFiscaleCreditore, idPagamentoEnte, importo);

    }




    /**
     * Ritorna una lista di condizioni di pagamento in scadenza. <br>
     * Funzione da rendere disponibile solo per utenti autenticati.
     * @param codiceFiscaleDebitore [Obbligatorio]: Codice Fiscale del debitore, intestatario del debito (e.g. "RPTSFN70M14A390N" )
     * @param codFiscaleCreditore [Opzionale]: Codice Fiscale ente creditore (e.g. "01386030488" )
     * @param catTributo [Opzionale]: codice categoria del tributo, per filtrare le condizioni (e.g. "Categoria013", si ottengono solo le prestazioni sanitarie )
     * @return Array di <a href="el_ns0_condizionePagamento.html">CondizionePagamento</a>. ordinata per data scadenza crescente 
     */
    @GET
    @Produces({"application/json","application/xml"})
    @Path("auth/condizioni_pagamento/scadenze")
    public List<CondizionePagamento> getPagamentiScadenza(@QueryParam("codiceFiscaleDebitore") String codiceFiscaleDebitore, 
    		@QueryParam("codFiscaleCreditore") String codFiscaleCreditore,
			@QueryParam("catTributo") String catTributo) throws Exception {
    	if (codiceFiscaleDebitore == null) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String[] {"codiceFiscaleDebitore"}, "Parametro Obbligatorio");
        }
		/* Note per l'implementazione : in pratica va chiamato il metodo: DistintePagamentoFacade.pagamentiInScadenzaHP */
		GWRestProfile profile = new GWRestProfile();
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		boolean checkUser = cpl.getBooleanProperty("rest.api.auth.check.sso.user");
		profile.setCodFiscalePagante(codiceFiscaleDebitore);
		//profile.setCatTributo(catTributo);
		
		//Recupero dati dell'utente loggato e controllo che siano congruenti
		//Con quelli passati come parametro dell'api rest
		
		//Attivare in base a properties rest.api.auth.check.sso.user
		Profilo loggedUser = new Profilo();
		ProfiloEndPoint profiloRest = new ProfiloEndPoint();
		loggedUser = profiloRest.getProfilo();
		
		if(checkUser) {
			if(!(codiceFiscaleDebitore.equals(loggedUser.getCodiceFiscale())))
				throw new Exception("User error"); 
		}

		//	return distintePagamentoFacade.pagamentiInScadenzaHP(profile, catTributo);
		// #3623 attualizzazione risultato
		List<CondizionePagamento> condizioni = distintePagamentoFacade.pagamentiInScadenzaHP(profile, catTributo);
		if (codFiscaleCreditore != null) {
			List<CondizionePagamento> result = new ArrayList<CondizionePagamento>();
			for (CondizionePagamento condizionePagamento : condizioni) {
				if (condizionePagamento.getCodFiscaleEnte().equalsIgnoreCase(codFiscaleCreditore)) {
					result.add(condizionePagamento);
				}
			}
			condizioni = result;
		}
		Map<String, String> esitoAttualizzazione = attualizzaBolliAuto(condizioni);
		if (esitoAttualizzazione.values().contains("OK")) {
			// ricarico perche' una o piu' pendenze sono state attualizzate
			condizioni = distintePagamentoFacade.pagamentiInScadenzaHP(profile, catTributo);
		}
		// aggiorno i risultati con gli esiti
		return aggiungiEsitoAttualizzazioneAlleCondizioni(condizioni, esitoAttualizzazione);
		
	}
    
    
    private List<CondizionePagamento> aggiungiEsitoAttualizzazioneAlleCondizioni(List<CondizionePagamento> condizioni, Map<String, String> esitoAttualizzazione) {
    	for (CondizionePagamento condizionePagamento : condizioni) {
    		condizionePagamento.setEsitoAttualizzazione(esitoAttualizzazione.get(getChiaveCondizione(condizionePagamento)));
		}
    	return condizioni;
    }
    
    
    
	private Map<String, String> attualizzaBolliAuto(List<CondizionePagamento> condizioni) {

		
		Map<String, String> mapEsiti = new HashMap<String, String>();
//		boolean ricaricaListaPagamentiInScadenza = false;

		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		boolean attualizzaBolli = Boolean.parseBoolean(cpl.getProperty("homepage.attualizzatore.bolloauto.attiva", "true").trim());
		int maxBolliAttualizzabili = Integer.parseInt(cpl.getProperty("homepage.attualizzatore.bolloauto.nrMax", "3").trim());

		if (attualizzaBolli) {

			int bolliAutoScadutiAggiornati = 0;

			for (CondizionePagamento cond : condizioni) {

				if ("BOLLO_AUTO".equals(cond.getCodTributo()) && !dateGreaterThanToday(cond.getFineValidita())) {

					if (bolliAutoScadutiAggiornati < maxBolliAttualizzabili) {
						// AGGIORNO
						bolliAutoScadutiAggiornati++;

						AttualizzaPendenza pend = new AttualizzaPendenza();

						pend.setDataOraOperazione(Calendar.getInstance());
						pend.setTipoOperazione(TipoOperazione.UPDATE);
						pend.setIdEnte(cond.getIdEnte());
						pend.setTipoDebito(cond.getCodTributo());

						AttualizzaPendenzaCodFscDebitore[] codFscDebitore = new AttualizzaPendenzaCodFscDebitore[cond.getDebitori().size()];
						int i = 0;
						for (InfoDebitori infoDebitore : cond.getDebitori()) {
							AttualizzaPendenzaCodFscDebitore cf = new AttualizzaPendenzaCodFscDebitore(infoDebitore.getCodFiscaleDebitore());
							cf.setTipoDebitore(TipoDebitore.CI);
							codFscDebitore[i++] = cf;

						}
						pend.setCodFscDebitore(codFscDebitore);
						pend.setCausaleDebito(cond.getCausalePendenzaGrezza());
						pend.setIdDebitoEnte(cond.getIdPendenzaEnte());
						pend.setIdPagamentoEnte(cond.getIdPagamentoEnte());
						pend.setCodiceCARTEnte("RegioneToscana");
						pend.setCodiceSILEnte("SIL_RTOSCANA_ITR");
						pend.setNote(cond.getNote());

						if (cond.getDataEmissione() != null) {
							Calendar dataEmissione = Calendar.getInstance();
							dataEmissione.setTimeInMillis(cond.getDataEmissione().getTime());
							pend.setDataEmissione(dataEmissione);
						}
						if (cond.getDataPrescrizione() != null) {
							Calendar dataPrescrizione = Calendar.getInstance();
							dataPrescrizione.setTimeInMillis(cond.getDataPrescrizione().getTime());
							pend.setDataPrescrizione(dataPrescrizione);
						}
						pend.setAnnoRiferimento(new org.apache.axis.types.Year(cond.getAnnoRiferimento()));
						pend.setTotalePagamentiParziali(0D);

						try {
							Tracer.info(getClass().getName(), "home", "chiamo attualizzaPendenza per il bollo " + cond.getIdPagamentoEnte());
							AttualizzaPendenzaResponse resp = new AttualizzatoreBolloAutoInvoker().attualizzaPendenza(pend);
							Tracer.info(getClass().getName(), "home", "esito: " + resp.getCodice().getValue() + " - " + resp.getDescrizione());
							if ("OK".equals(resp.getCodice().getValue())) {
//								ricaricaListaPagamentiInScadenza = true;
								Tracer.info(getClass().getName(), "home", "bollo attualizzato. Devo ricaricare la lista");
								mapEsiti.put(getChiaveCondizione(cond), "OK");
							} else {
								Tracer.info(getClass().getName(), "home", "bollo NON attualizzato");
								mapEsiti.put(getChiaveCondizione(cond), "KO");
							}
						} catch (Exception e) {
							Tracer.error(getClass().getName(), "home", "ERRORE ATTUALIZZAZIONE BOLLO", e);
							mapEsiti.put(getChiaveCondizione(cond), "KO");
						}

					} else {
						Tracer.info(getClass().getName(), "home", "superato numero massimo bolli attualizzabili per home page: " + maxBolliAttualizzabili);
					}
				}
			}

		} // fine if attualizzaBolli

		return mapEsiti;
	}
	
	
	private static String getChiaveCondizione(CondizionePagamento cp) {
		return cp.getCodTributo() + cp.getCodEnte() + cp.getIdPendenzaEnte();
	}

}
