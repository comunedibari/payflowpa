package it.tasgroup.iris.payment.helper;

import it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale;
import it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT;
import it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT;
import it.gov.digitpa.schemas.x2011.pagamenti.CtDominio;
import it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario;
import it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG;
import it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaG;
import it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico;
import it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore;
import it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante;
import it.gov.digitpa.schemas.x2011.pagamenti.RPTDocument;
import it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto;
import it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta;
import it.gov.digitpa.schemas.x2011.pagamenti.StISODate;
import it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo;
import it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivocoPersFG;
import it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivocoPersG;
import it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento;
import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.comunication.dto.UtentiCanaliDTO;
import it.tasgroup.iris.comunication.ws.client.IrisComunicationWSInvoker;
import it.tasgroup.iris.comunication.ws.impl.StatoCanaleType;
import it.tasgroup.iris.comunication.ws.impl.TipoCanaleType;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaFacade;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlCalendar;
import org.apache.xmlbeans.XmlOptions;
import org.apache.commons.codec.binary.Base64;

public class RPTBuilder {

	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");
	private static final ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
	public static final String ANONYMOUS_CF = "ANONIMO";

	// uso locale "ENGLISH" perch� il separatore decimale deve essere il "."
	private static final DecimalFormat nFormatter = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));

	private static String getEmailDebitore(String idFiscaleDebitore) {
		//
		// recupero indirizzo email debitore
		// potrebbe non essere in anagrafica ma almeno ci provo - il dato �
		// facoltativo
		//
		String emailDebitore = null;

		try {

	        AnagraficaFacade anagraficaBean = (AnagraficaFacade) ServiceLocator.getSLSBProxy("anagraficaFacadeBean");

	        String chiaveGestoreComunicazioni = anagraficaBean.getChiaveOperatoreGestoreComunicazioni(idFiscaleDebitore, "OP");
			
			if (StringUtils.isNotEmpty(chiaveGestoreComunicazioni)) {
				// emailDebitore = IProfileManager.getEmail(irisId);
				IrisComunicationWSInvoker comunication = new IrisComunicationWSInvoker();

				boolean isAnonymous = false;

				// Recupero tutti i canali associati all'utente
				List<UtentiCanaliDTO> myCanali = comunication.getCanaliComunicazione(chiaveGestoreComunicazioni, isAnonymous);

				for (UtentiCanaliDTO utentiCanaliDTO : myCanali) {
					// se il canale � E-MAIL (value1) ed � attivo allora prendo la configurazione
					if (TipoCanaleType.value1.getValue().equals(utentiCanaliDTO.getDenominazioneCanale())
							&& StatoCanaleType.ATTIVO.getValue().equals(utentiCanaliDTO.getStato())) {
						emailDebitore = utentiCanaliDTO.getConfigurazione();

						break;
					}
				}
			}
		} catch (Exception e) {
			Tracer.warn(RPTBuilder.class.getName(), "preparaRichiesta", "ERRORE nel recupero email - proseguo", e.getMessage());
			// TODO: cancellare questa stampa
			e.printStackTrace();
		}

		return emailDebitore;
	}

	public static String buildRPTXmlString(IntestatarioDTO pagante, boolean isAnonymous, OperatoreDTO operatore, List<SessionShoppingCartItemDTO> cart,
			String tipoVersamento, BigDecimal importoTotale, String codPagamento, String codTransazione, String codTransazionePSP, Date dataPagamento, String ibanAddebito) {
		
		
		RPTDocument rptDoc = buildRPT(pagante, isAnonymous, operatore, cart, tipoVersamento, importoTotale, codPagamento, codTransazione, codTransazionePSP, dataPagamento, ibanAddebito);

		String xmlString;
        XmlOptions opt = new XmlOptions();
        opt.setUseDefaultNamespace();
        // opt.setSavePrettyPrint();
        try {
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	rptDoc.save(os, opt);
            xmlString = os.toString();
        } catch (IOException e) {
        	// a questo punto proviamo senza xml declaration ... 
			xmlString = rptDoc.toString();
		}
        return xmlString;
		
	}
	
	private static boolean isDefaultCF(String codiceFiscale)  {
		String cfNonDisponibile = props.getProperty("iris.codice.fiscale.anonymous");
		return codiceFiscale.equalsIgnoreCase(cfNonDisponibile);
	}

	public static RPTDocument buildRPT(IntestatarioDTO pagante, boolean isAnonymous, OperatoreDTO operatore, List<SessionShoppingCartItemDTO> cart,
			String tipoVersamento, BigDecimal importoTotale, String codPagamento, String codTransazione, String codTransazionePSP, Date dataPagamento, String ibanAddebito) {
		
		// TODO:MINO da eliminare. Solo per evitare problemi con distinte create senza codTransazione PSP
		if (codTransazionePSP == null || codTransazionePSP.isEmpty()) {
			codTransazionePSP = conf.getProperty("nodopagamentispc.codiceContestoPagamento");
		}
		
		
		CtRichiestaPagamentoTelematico rpt = CtRichiestaPagamentoTelematico.Factory.newInstance();
		// recupero idFiscaleEnte da inserire nel campo identificativoDominio  
		SessionShoppingCartItemDTO cartItem = cart.get(0);
		String idFiscaleEnte = cartItem.getIdFiscaleEnte();
		String codFiscaleVersante = isDefaultCF(pagante.getIdFiscale()) ? ANONYMOUS_CF : pagante.getIdFiscale();
		String codFiscaleDebitore = isDefaultCF(cart.get(0).getDebtor()) ? ANONYMOUS_CF : cart.get(0).getDebtor();

		
		rpt.setVersioneOggetto(conf.getProperty("nodopagamentispc.versioneRPT"));
		rpt.setDominio(buildDominio(idFiscaleEnte));
		rpt.setIdentificativoMessaggioRichiesta(codTransazione);
		
		Calendar dataRPT =  new XmlCalendar(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dataPagamento));
		rpt.setDataOraMessaggioRichiesta(dataRPT);
		
// TODO: eliminare dopo i test
// versione originale:
//		rpt.setAutenticazioneSoggetto(isAnonymous ? StAutenticazioneSoggetto.N_A : StAutenticazioneSoggetto.CNS);
		if (ibanAddebito != null) {
			rpt.setAutenticazioneSoggetto(StAutenticazioneSoggetto.CNS);
		} else {
			rpt.setAutenticazioneSoggetto(isAnonymous ? StAutenticazioneSoggetto.N_A : StAutenticazioneSoggetto.CNS);
		}

		boolean isPaganteUgualeVersante  = codFiscaleDebitore.equalsIgnoreCase(pagante.getIdFiscale());
		boolean isPaganteVersanteAnonimi = codFiscaleDebitore.equals(ANONYMOUS_CF) && codFiscaleVersante.equals(ANONYMOUS_CF);
		
		if (!isPaganteUgualeVersante || isPaganteVersanteAnonimi) { // Pagante Versante diversi o entrambi anonimi
			String email = (isPaganteVersanteAnonimi ? pagante.getEmail() : getEmailDebitore(codFiscaleDebitore)); // se anonimi mail dal form
			rpt.setSoggettoVersante(buildSoggettoVersante(pagante, isAnonymous, operatore, codFiscaleVersante));
			rpt.setSoggettoPagatore(buildSoggettoPagatore(codFiscaleDebitore, email));
		} else { // Pagante Versante uguali - email del form
			rpt.setSoggettoPagatore(buildSoggettoPagatore(codFiscaleDebitore, pagante.getEmail()));
		}
		
		rpt.setEnteBeneficiario(buildEnteBeneficiario(idFiscaleEnte, cartItem.getEnte(), cartItem.getIdMittentePend(), cartItem.getDeMittentePend()));
		rpt.setDatiVersamento(buildDatiVersamento(codPagamento, codTransazionePSP, tipoVersamento, importoTotale, cart, dataRPT, ibanAddebito));

		RPTDocument rptDocument = RPTDocument.Factory.newInstance();
		rptDocument.setRPT(rpt);

		return rptDocument;
	}

	private static CtDominio buildDominio(String idFiscaleEnte) {
		
		CtDominio dominio = CtDominio.Factory.newInstance();
		//dominio.setIdentificativoDominio(conf.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
		dominio.setIdentificativoDominio(idFiscaleEnte);
		dominio.setIdentificativoStazioneRichiedente(conf.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));

		return dominio;
	}

	private static CtSoggettoVersante buildSoggettoVersante(IntestatarioDTO pagante, boolean isAnonymous, OperatoreDTO operatore,String idFiscaleDebitore) {
		
		CtIdentificativoUnivocoPersonaFG idUnivocoVersante = CtIdentificativoUnivocoPersonaFG.Factory.newInstance();
		CtSoggettoVersante soggettoVersante = CtSoggettoVersante.Factory.newInstance();
		
		boolean soloCodiceFiscaleDebitore = props.getBooleanProperty("gateway.prePagamento.soloCodiceFiscale.debitore");
		boolean overwriteCFanonymous = props.getBooleanProperty("gateway.prePagamento.overwriteCFanonymous");
		boolean isDefaultCF = idFiscaleDebitore != null && idFiscaleDebitore.equals(RPTBuilder.ANONYMOUS_CF);
		
		if ((soloCodiceFiscaleDebitore && overwriteCFanonymous) || isDefaultCF) {
			idUnivocoVersante.setCodiceIdentificativoUnivoco("ANONIMO");
			idUnivocoVersante.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersFG.F);
			if (isAnonymous) 
				soggettoVersante.setAnagraficaVersante("ANONIMO");
		} else {
			if (overwriteCFanonymous) {
			   idUnivocoVersante.setCodiceIdentificativoUnivoco(pagante.getIdFiscale());
			   idUnivocoVersante.setTipoIdentificativoUnivoco((pagante.getIdFiscale().length() == 16) ? StTipoIdentificativoUnivocoPersFG.F : StTipoIdentificativoUnivocoPersFG.G);
			   if (isAnonymous) 
				   soggettoVersante.setAnagraficaVersante(pagante.getIdFiscale()); // TODO: se possibile recuperare
			   		// la ragionesociale/nomecognome
		
			} else {
				// se non overwriteCFanonymous 
				idUnivocoVersante.setCodiceIdentificativoUnivoco(idFiscaleDebitore);
				idUnivocoVersante.setTipoIdentificativoUnivoco((idFiscaleDebitore.length() == 16) ? StTipoIdentificativoUnivocoPersFG.F : StTipoIdentificativoUnivocoPersFG.G);
				if (isAnonymous) 
					soggettoVersante.setAnagraficaVersante(idFiscaleDebitore);
			}
		}	
		soggettoVersante.setIdentificativoUnivocoVersante(idUnivocoVersante);
		if (StringUtils.isNotEmpty(pagante.getEmail())) 
			soggettoVersante.setEMailVersante(pagante.getEmail());
		if (!isAnonymous)
			soggettoVersante.setAnagraficaVersante(operatore.getNome() + " " + operatore.getCognome()); 

		return soggettoVersante;
	}

	private static CtSoggettoPagatore buildSoggettoPagatore(String idFiscaleDebitore, String emailDebitore) {

		CtIdentificativoUnivocoPersonaFG idUnivocoPagatore = CtIdentificativoUnivocoPersonaFG.Factory.newInstance();
		idUnivocoPagatore.setCodiceIdentificativoUnivoco(idFiscaleDebitore);
		if(idFiscaleDebitore.equals(RPTBuilder.ANONYMOUS_CF))
			idUnivocoPagatore.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersFG.F);
		else
			idUnivocoPagatore.setTipoIdentificativoUnivoco(idFiscaleDebitore.length() == 16 ? StTipoIdentificativoUnivocoPersFG.F: StTipoIdentificativoUnivocoPersFG.G);

		CtSoggettoPagatore soggettoPagatore = CtSoggettoPagatore.Factory.newInstance();
		soggettoPagatore.setIdentificativoUnivocoPagatore(idUnivocoPagatore);
		soggettoPagatore.setAnagraficaPagatore(idFiscaleDebitore);

		if (StringUtils.isNotEmpty(emailDebitore))
			soggettoPagatore.setEMailPagatore(emailDebitore);

		return soggettoPagatore;
	}

	private static CtEnteBeneficiario buildEnteBeneficiario(String idFiscaleBeneficiario, String denominazioneBeneficiario, String idMittentePend, String deMittentePend) {

		CtIdentificativoUnivocoPersonaG idUnivocoBeneficiario = CtIdentificativoUnivocoPersonaG.Factory.newInstance();
		idUnivocoBeneficiario.setCodiceIdentificativoUnivoco(idFiscaleBeneficiario);
		idUnivocoBeneficiario.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersG.G);

		CtEnteBeneficiario enteBeneficiario = CtEnteBeneficiario.Factory.newInstance();
		enteBeneficiario.setIdentificativoUnivocoBeneficiario(idUnivocoBeneficiario);
		enteBeneficiario.setDenominazioneBeneficiario(denominazioneBeneficiario);
		//
		if (conf.getBooleanProperty("nodopagamentispc.rpt.valorizzaDatiEstesiMittente")){
		    enteBeneficiario.setCodiceUnitOperBeneficiario(idMittentePend);
		    if (deMittentePend!=null) {
		      enteBeneficiario.setDenomUnitOperBeneficiario(deMittentePend);
		    }
	    }
		return enteBeneficiario;
	}

	private static CtDatiVersamentoRPT buildDatiVersamento(String codPagamento, String codTransazionePSP, String tipoVersamento, BigDecimal importoTotale,
			List<SessionShoppingCartItemDTO> pagamentiWIP, Calendar dataRPT, String ibanAddebito) {
		CtDatiVersamentoRPT datiVersamento = CtDatiVersamentoRPT.Factory.newInstance();
 
		
		StISODate xDate = StISODate.Factory.newInstance();
		Date date = dataRPT.getTime();             
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(date);  
		xDate.setStringValue(date1);
		datiVersamento.xsetDataEsecuzionePagamento(xDate);
		    	
		datiVersamento.setImportoTotaleDaVersare(importoTotale.setScale(2)); // forzatura per avere sempre due decimali nell'xml

		StTipoVersamento.Enum stTipoVersamento;

		if (StTipoVersamento.AD.toString().equals(tipoVersamento))
		{
			// IBAN ADDEBITO Obbligatorio per ID_CFG_MODALITA_PAGAMENTO 3 (AD
			// aka RID-ONLINE)
			if (ibanAddebito == null || ibanAddebito.trim().length() == 0) {
				throw new UnsupportedOperationException("IBAN ADDEBITO MANCANTE");
			}
			stTipoVersamento = StTipoVersamento.AD;
		}
		
		if (StTipoVersamento.BBT.toString().equals(tipoVersamento)) {
			stTipoVersamento = StTipoVersamento.BBT;
		} else if (StTipoVersamento.BP.toString().equals(tipoVersamento)) {
			stTipoVersamento = StTipoVersamento.BP;
		} else if (StTipoVersamento.CP.toString().equals(tipoVersamento)) {
			stTipoVersamento = StTipoVersamento.CP;
		} else if (StTipoVersamento.PO.toString().equals(tipoVersamento)) {
			stTipoVersamento = StTipoVersamento.PO;
		} else if (StTipoVersamento.OBEP.toString().equals(tipoVersamento)) {
			stTipoVersamento = StTipoVersamento.OBEP;
		} else if (StTipoVersamento.OTH.toString().equals(tipoVersamento)) {
			stTipoVersamento = StTipoVersamento.OTH;
		} else
			throw new UnsupportedOperationException("TIPO VERSAMENTO NON VALIDO: " + tipoVersamento);
		

		datiVersamento.setTipoVersamento(stTipoVersamento);
		datiVersamento.setIdentificativoUnivocoVersamento(codPagamento);
		datiVersamento.setCodiceContestoPagamento(codTransazionePSP);

		if (ibanAddebito != null)
			datiVersamento.setIbanAddebito(ibanAddebito);
		

		// datiVersamento.setBicAddebito(null);
		datiVersamento.setFirmaRicevuta(StFirmaRicevuta.Enum.forString(conf.getProperty("nodopagamentispc.firmaRicevuta")));

		CtDatiSingoloVersamentoRPT[] dati = new CtDatiSingoloVersamentoRPT[pagamentiWIP.size()];
		int i = 0;

		for (SessionShoppingCartItemDTO elementoCarrello : pagamentiWIP) 
			dati[i++] = buildDatiSingoloVersamento(elementoCarrello,codPagamento);
		

		datiVersamento.setDatiSingoloVersamentoArray(dati);

		return datiVersamento;
	}

	private static CtDatiSingoloVersamentoRPT buildDatiSingoloVersamento(SessionShoppingCartItemDTO elementoCarrello,String iuv) {
        //TODO in ogni singolo versamento viene settato sia l'IBAN di accredito che l'IBAN di appoggio (conto tecnico)
		CtDatiSingoloVersamentoRPT datiSingoloVersamento = CtDatiSingoloVersamentoRPT.Factory.newInstance();
		boolean isMarcaBollo = false;
        if ("E_BOLLO".equals(elementoCarrello.getIdTributoEnte())){
        	isMarcaBollo = true;
        }
		
        BigDecimal importo = elementoCarrello.getImporto();   	
		datiSingoloVersamento.setImportoSingoloVersamento(importo.setScale(2)); // forzatura per avere sempre due decimali nell'xml
		if (!isMarcaBollo){
		   datiSingoloVersamento.setIbanAccredito(elementoCarrello.getIbanBeneficiario());
		}
		if (elementoCarrello.getIbanAppoggio() != null) 
			datiSingoloVersamento.setIbanAppoggio(elementoCarrello.getIbanAppoggio());
		

		// formato causaleVersamento: /RFB/<codice versamento
		// ente>/<importo>/TXT/DEBITORE/<codice fiscale debitore>
		datiSingoloVersamento.setCausaleVersamento("/RFB/"
				+ iuv + "/"
				+ nFormatter.format(elementoCarrello.getImporto())
				+ "/TXT/DEBITORE/" + elementoCarrello.getCodFiscaleDebitore());
		datiSingoloVersamento.setDatiSpecificiRiscossione(conf.getProperty("nodopagamentispc.datiSpecificiRiscossione"));

		if (isMarcaBollo) {
			CtDatiMarcaBolloDigitale marcaBolloDigitale = CtDatiMarcaBolloDigitale.Factory.newInstance();
			marcaBolloDigitale.setTipoBollo(StTipoBollo.X_01);
			marcaBolloDigitale.setProvinciaResidenza(elementoCarrello.getMarcaBolloProvResidenzaDebitore());

			marcaBolloDigitale.setHashDocumento(elaboraHashDoc(elementoCarrello.getMarcaBolloHashDocumento()));

			datiSingoloVersamento.setDatiMarcaBolloDigitale(marcaBolloDigitale);
		}
		return datiSingoloVersamento;
	}
	private static String elaboraHashDoc(String hash256 ){
		
		String base64_sha = null;
		try {
			
				
				int len = hash256.length();
				byte[] bytearray_sha = new byte[len / 2];
				for (int i = 0; i < len; i += 2) {
					bytearray_sha[i / 2] = (byte) ((Character.digit(hash256.charAt(i), 16) << 4) + Character.digit(hash256.charAt(i+1), 16));
				}

				base64_sha = Base64.encodeBase64String(bytearray_sha); 
				Tracer.debug(RPTBuilder.class.getName(), "elaboraHashDoc", "HASH convertito: "+ base64_sha);
				
		} catch (Exception e){
			Tracer.error(RPTBuilder.class.getName(), "elaboraHashDoc", "ERRORE CONVERSIONE HASH ", e.getMessage());
		}
		
		return base64_sha;

	}
	
	
	public static void main(String[] args) {

		CtRichiestaPagamentoTelematico rpt = CtRichiestaPagamentoTelematico.Factory.newInstance();

		rpt.setVersioneOggetto("6.6.6");
		rpt.setIdentificativoMessaggioRichiesta("666");

		CtDominio dominio = CtDominio.Factory.newInstance();
		dominio.setIdentificativoDominio("intermediaorio pa");
		dominio.setIdentificativoStazioneRichiedente("id stazione");
		rpt.setDominio(dominio);

		RPTDocument rptDocument = RPTDocument.Factory.newInstance();
		rptDocument.setRPT(rpt);

		XmlOptions opt = new XmlOptions();
		opt.setUseDefaultNamespace();
		opt.setSavePrettyPrint();

		String xmlString;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			rptDocument.save(os, opt);
			xmlString = os.toString();
		} catch (IOException e) {
			xmlString = "err";
		}

		System.out.println("--------------------------------------------------------");
		System.out.println(rptDocument.xmlText());
		System.out.println("--------------------------------------------------------");
		System.out.println(rptDocument.xmlText(opt));
		System.out.println("--------------------------------------------------------");
		System.out.println(xmlString);
		System.out.println("--------------------------------------------------------");

	}
}
