package it.tasgroup.iris.payment.helper;

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
import it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivocoPersFG;
import it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivocoPersG;
import it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

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

/**
 * 
 * RPTBuilder specifico per il pagamento mediante Poste FESP.
 * 
 * @author pazzik
 *
 */
public class FESP_RPTBuilder {

	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");

	// uso locale "ENGLISH" perch� il separatore decimale deve essere il "."
	private static final DecimalFormat nFormatter = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));

	public static RPTDocument buildRPT(IntestatarioDTO profilo, OperatoreDTO operatore, List<SessionShoppingCartItemDTO> cart,
			String modalitaPagamento, BigDecimal importoTotale, String codTransazione, Date dataPagamento, String ibanAddebito, IntestatarioDTO debitoreDTO) {

		CtRichiestaPagamentoTelematico rpt = CtRichiestaPagamentoTelematico.Factory.newInstance();

		rpt.setVersioneOggetto(conf.getProperty("nodopagamentispc.versioneRPT"));
		rpt.setDominio(buildDominio());
		rpt.setIdentificativoMessaggioRichiesta(codTransazione);

		Calendar dataRPT =  new XmlCalendar(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dataPagamento));
		rpt.setDataOraMessaggioRichiesta(dataRPT);
		rpt.setAutenticazioneSoggetto(StAutenticazioneSoggetto.CNS);

		// SONO OMOGENEE PER DEBITORE/BENEFICIARIO quindi va bene prendere anche
		// solo la prima
		SessionShoppingCartItemDTO cartItem = cart.get(0);
		
		//String idFiscaleDebitore = debitoreDTO.getIdFiscale();

		//if (!idFiscaleDebitore.equals(profilo.getIdFiscale())) {
			
			// il pagante (o soggetto versante) � diverso dal debitore
			// aggiungo le informazioni sul pagante (soggetto versante)
			rpt.setSoggettoVersante(buildSoggettoVersante(profilo, operatore));
			
			// 18-03-2014 PAZZIK PER ORA SI RICHIEDE DI DISABILITARE I PAGAMENTI IN DELEGA
			//rpt.setSoggettoPagatore(buildSoggettoPagatore(debitoreDTO));
			rpt.setSoggettoPagatore(buildSoggettoPagatore(profilo));
		
		//} else 
			// versante e pagatore (pagante e debitore) coincidono (uso la mail del form)
			//rpt.setSoggettoPagatore(buildSoggettoPagatore(debitoreDTO));
		

		rpt.setEnteBeneficiario(buildEnteBeneficiario(cartItem.getIdFiscaleEnte(), cartItem.getEnte()));
		rpt.setDatiVersamento(buildDatiVersamento(codTransazione, modalitaPagamento, importoTotale, cart, dataRPT, ibanAddebito));

		RPTDocument rptDocument = RPTDocument.Factory.newInstance();
		rptDocument.setRPT(rpt);
		
		return rptDocument;
	}

	private static CtDominio buildDominio() {
		
		CtDominio dominio = CtDominio.Factory.newInstance();
		
		dominio.setIdentificativoDominio(conf.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
		
		dominio.setIdentificativoStazioneRichiedente(conf.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));

		return dominio;
	}

	private static CtSoggettoVersante buildSoggettoVersante(IntestatarioDTO versante, OperatoreDTO operatore) {
		
		CtIdentificativoUnivocoPersonaFG idUnivocoVersante = CtIdentificativoUnivocoPersonaFG.Factory.newInstance();
		
		String codFiscale = versante.getIdFiscale();
		
		idUnivocoVersante.setCodiceIdentificativoUnivoco(codFiscale);

		idUnivocoVersante.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersFG.F);

		CtSoggettoVersante soggettoVersante = CtSoggettoVersante.Factory.newInstance();
		
		soggettoVersante.setIdentificativoUnivocoVersante(idUnivocoVersante);
		
		String anagraficaVersante = operatore.getNome() + " " + operatore.getCognome();
		
		if (anagraficaVersante.length() > 50)
			anagraficaVersante = anagraficaVersante.substring(0,50);
				
		soggettoVersante.setAnagraficaVersante(anagraficaVersante);
		
		IndirizzoDTO indirizzoVersante = versante.getIndirizzo();
		
		// siccome l'anagrafica del versante ha gi� superato il controllo su IntestatarioValidator
		// posso troncarla senza controllare altro
		String nazioneVersante = indirizzoVersante.getNazione().substring(0,2);
			
		soggettoVersante.setNazioneVersante(nazioneVersante);
		
		String provinciaVersante = indirizzoVersante.getProvincia();
				
		soggettoVersante.setProvinciaVersante(provinciaVersante);
		
		String capVersante = indirizzoVersante.getCap();
			
		soggettoVersante.setCapVersante(capVersante);
		
		String civicoVersante = indirizzoVersante.getNumeroCivico();
		
		soggettoVersante.setCivicoVersante(civicoVersante);
		
		String viaVersante = indirizzoVersante.getVia();

		if (viaVersante.length() > 50)
			
			viaVersante = viaVersante.substring(0, 50);
		
		soggettoVersante.setIndirizzoVersante(viaVersante);
		
		String cittaVersante = indirizzoVersante.getCitta();
		
		if (cittaVersante.length() > 35)
			
			cittaVersante = cittaVersante.substring(0, 35);
		
		soggettoVersante.setLocalitaVersante(cittaVersante);
			
		String emailVersante = versante.getEmail();
		
		soggettoVersante.setEMailVersante(emailVersante);
		
		return soggettoVersante;
	}

	private static CtSoggettoPagatore buildSoggettoPagatore(IntestatarioDTO debitore) {
		
		String idFiscaleDebitore = debitore.getIdFiscale();

		CtIdentificativoUnivocoPersonaFG idUnivocoPagatore = CtIdentificativoUnivocoPersonaFG.Factory.newInstance();
		
		// TODO PAZZIK GESTIRE PIVA
		idUnivocoPagatore.setCodiceIdentificativoUnivoco(idFiscaleDebitore);
		
		idUnivocoPagatore.setTipoIdentificativoUnivoco(idFiscaleDebitore.length() == 16 ? StTipoIdentificativoUnivocoPersFG.F: StTipoIdentificativoUnivocoPersFG.G);

		CtSoggettoPagatore soggettoPagatore = CtSoggettoPagatore.Factory.newInstance();
		
		soggettoPagatore.setIdentificativoUnivocoPagatore(idUnivocoPagatore);
		
		soggettoPagatore.setAnagraficaPagatore(idFiscaleDebitore); 
		
		IndirizzoDTO indirizzoDebitore = debitore.getIndirizzo();
		
		String viaDebitore = indirizzoDebitore.getVia();
		
		if (StringUtils.isEmpty(viaDebitore))
			throw new IllegalStateException("Via del Debitore obbligatoria per Poste Italiane");
		
		if (viaDebitore.length() > 70)
			
			viaDebitore = viaDebitore.substring(0, 70);
		
		soggettoPagatore.setIndirizzoPagatore(viaDebitore);
		
		String civicoDebitore = indirizzoDebitore.getNumeroCivico();
		
		if (StringUtils.isEmpty(civicoDebitore) || civicoDebitore.length() > 16)
			throw new IllegalStateException("Numero Civico del Debitore non valido per Poste Italiane");
				
		soggettoPagatore.setCivicoPagatore(civicoDebitore);
		
		String capDebitore = indirizzoDebitore.getCap();
		
		if (StringUtils.isEmpty(capDebitore) || capDebitore.length() > 5)
			throw new IllegalStateException("CAP del Debitore non valido per Poste Italiane");
				
		soggettoPagatore.setCapPagatore(capDebitore);
		
		String cittaDebitore = indirizzoDebitore.getCitta();
		
		if (StringUtils.isEmpty(cittaDebitore))
			throw new IllegalStateException("Citta' del Debitore obbligatoria per Poste Italiane");
		
		if (cittaDebitore.length() > 35)
			
			cittaDebitore = cittaDebitore.substring(0, 35);
				
		soggettoPagatore.setLocalitaPagatore(cittaDebitore);
		
		String provinciaDebitore = indirizzoDebitore.getProvincia();
		
		if (StringUtils.isEmpty(provinciaDebitore) || provinciaDebitore.length() > 2)
			throw new IllegalStateException("Provincia del Debitore non valida per Poste Italiane");
		
		soggettoPagatore.setProvinciaPagatore(provinciaDebitore);
		
		String nazioneDebitore = indirizzoDebitore.getNazione();
		
		if (nazioneDebitore != null && nazioneDebitore.length() > 2)
			nazioneDebitore = nazioneDebitore.substring(0,2);
		
		// TODO PAZZIK: VERIFICARE SE NECESSARIO QUESTO CONTROLLO PER IL PAGATORE COME PER IL VERSANTE
		if (!"IT".equals(nazioneDebitore))
			throw new IllegalStateException("Nazione del Debitore non valido per Poste Italiane");
		
		soggettoPagatore.setNazionePagatore(nazioneDebitore);
		
		//String emailDebitore = IRISComunicationHelper.getEmail(idFiscaleDebitore);
				
		soggettoPagatore.setEMailPagatore(debitore.getEmail());

		return soggettoPagatore;
	}

	private static CtEnteBeneficiario buildEnteBeneficiario(String idFiscaleBeneficiario, String denominazioneBeneficiario) {

		CtIdentificativoUnivocoPersonaG idUnivocoBeneficiario = CtIdentificativoUnivocoPersonaG.Factory.newInstance();
		
		idUnivocoBeneficiario.setCodiceIdentificativoUnivoco(idFiscaleBeneficiario);
		
		idUnivocoBeneficiario.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersG.G);

		CtEnteBeneficiario enteBeneficiario = CtEnteBeneficiario.Factory.newInstance();
		
		enteBeneficiario.setIdentificativoUnivocoBeneficiario(idUnivocoBeneficiario);
		
		enteBeneficiario.setDenominazioneBeneficiario(denominazioneBeneficiario);

		return enteBeneficiario;
	}

	private static CtDatiVersamentoRPT buildDatiVersamento(String codTransazione, String modalitaPagamento, BigDecimal importoTotale,
			List<SessionShoppingCartItemDTO> pagamentiWIP, Calendar dataRPT, String ibanAddebito) {
		
		CtDatiVersamentoRPT datiVersamento = CtDatiVersamentoRPT.Factory.newInstance();

		datiVersamento.setDataEsecuzionePagamento(dataRPT);
		
		datiVersamento.setImportoTotaleDaVersare(importoTotale.setScale(2)); // forzatura per avere sempre due decimali nell'xml

		datiVersamento.setTipoVersamento(StTipoVersamento.BP);
		
		datiVersamento.setIdentificativoUnivocoVersamento(codTransazione);
		
		datiVersamento.setCodiceContestoPagamento("n/a");

		if (ibanAddebito != null)
			datiVersamento.setIbanAddebito(ibanAddebito);
		

		// datiVersamento.setBicAddebito(null);
		datiVersamento.setFirmaRicevuta(StFirmaRicevuta.Enum.forString(conf.getProperty("nodopagamentispc.firmaRicevuta")));

		CtDatiSingoloVersamentoRPT[] dati = new CtDatiSingoloVersamentoRPT[pagamentiWIP.size()];
		
		int i = 0;

		for (SessionShoppingCartItemDTO elementoCarrello : pagamentiWIP) 
			dati[i++] = buildDatiSingoloVersamento(elementoCarrello);
		

		datiVersamento.setDatiSingoloVersamentoArray(dati);

		return datiVersamento;
	}

	private static CtDatiSingoloVersamentoRPT buildDatiSingoloVersamento(SessionShoppingCartItemDTO elementoCarrello) {

		CtDatiSingoloVersamentoRPT datiSingoloVersamento = CtDatiSingoloVersamentoRPT.Factory.newInstance();

		 BigDecimal importo = elementoCarrello.getImporto();   	
		datiSingoloVersamento.setImportoSingoloVersamento(importo.setScale(2)); // forzatura per avere sempre due decimali nell'xml
			
		datiSingoloVersamento.setIbanAccredito(elementoCarrello.getIbanBeneficiario());

		if (elementoCarrello.getIbanAppoggio() != null) 
			datiSingoloVersamento.setIbanAppoggio(elementoCarrello.getIbanAppoggio());
		

		// formato causaleVersamento: /RFB/<codice versamento
		// ente>/<importo>/TXT/DEBITORE/<codice fiscale debitore>
		datiSingoloVersamento.setCausaleVersamento("/RFB/"
				+ elementoCarrello.getIdPagamentoEnte() + "/"
				+ nFormatter.format(elementoCarrello.getImporto())
				+ "/TXT/DEBITORE/" + elementoCarrello.getCodFiscaleDebitore());
		
		datiSingoloVersamento.setDatiSpecificiRiscossione(conf.getProperty("nodopagamentispc.datiSpecificiRiscossione"));

		return datiSingoloVersamento;
	}
}
