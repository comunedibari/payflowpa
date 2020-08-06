package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import static it.tasgroup.iris.domain.helper.PersistenceUtils.materialize;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.EnumDemanioIdricoCanoni;
import it.tasgroup.addon.api.domain.EnumDemanioIdricoIstruttoria;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.api.manager.helper.AddOnReceiptHelper;
import it.tasgroup.addon.api.manager.helper.BaseViewHelper;
import it.tasgroup.addon.internal.AddOnManagerFactory;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgEntiLogoDTO;
import it.tasgroup.iris.dto.ddp.ChiaveValoreDTO;
import it.tasgroup.iris.dto.ddp.CondizioneDDPDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaFacade;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.locator.ServiceLocatorException;
import it.tasgroup.services.util.enumeration.EnumTipoAllegato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CondizioniDocumentoDTOBuilder {
	
	private static final Logger LOGGER = LogManager.getLogger(CondizioniDocumentoDTOBuilder.class);

	// usato per i Documenti di Pagamento
	public static List<CondizioneDDPDTO> populateCarrello(DocumentoDiPagamento ddp, CfgEntiLogo logoEnte, Locale locale) {

		List<CondizioneDDPDTO> carrello = new ArrayList<CondizioneDDPDTO>();
		
		Set<CondizioneDocumento> condizioni = ddp.getCondizioni();

		for (CondizioneDocumento condizioneDocumento : condizioni) {

			CondizioneDDPDTO condizioneDDPDTO = new CondizioneDDPDTO();
			condizioneDDPDTO.setIdCondizione(condizioneDocumento.getCondizionePagamento().getIdCondizione());

			String codiceFiscaleCreditore = condizioneDocumento.getCondizionePagamento().getEnte().getIntestatarioIForm().getLaplIForm();
			
			Enti ente = condizioneDocumento.getCondizionePagamento().getEnte();
						
			EnteDTO enteDTO = new EnteDTO();
			
			enteDTO.setCodice(ente.getCodiceEnte());
			enteDTO.setDenominazione(ente.getDenominazione());
			enteDTO.setId(ente.getIdEnte());
			
			IndirizzipostaliCommon indirizzo = ente.getIntestatarioobj().getIndirizzipostaliobjIForm();
			enteDTO.setCodiceFiscale(indirizzo.getFiscalCodeIForm());
			enteDTO.setIndirizzo(indirizzo.getAddressIForm() != null ? indirizzo.getAddressIForm() : "");
			enteDTO.setNumeroCivico(indirizzo.getNumeroCivicoIForm() != null ? indirizzo.getNumeroCivicoIForm() : "");
			enteDTO.setCap(indirizzo.getCapCodeIForm() != null ? indirizzo.getCapCodeIForm() : "");
			enteDTO.setCitta(indirizzo.getCityIForm() != null ? indirizzo.getCityIForm() : "");
			enteDTO.setProvincia(indirizzo.getProvinceIForm() != null ? indirizzo.getProvinceIForm() : "");
			enteDTO.setTelefono(indirizzo.getPhoneNumberIForm() != null ? indirizzo.getPhoneNumberIForm() : "");
			enteDTO.setSiaIForm(ente.getIntestatarioobj().getSiaIForm());
			enteDTO.setSiaCbiIForm(ente.getIntestatarioobj().getSiaCbiIForm());
			enteDTO.setAutorizzStampaDDP(ente.getAutorizzStampaDDP());
			if (logoEnte != null){
				
				CfgEntiLogoDTO dto = EntiDTOBuilder.fillCfgEntiLogoDTO(logoEnte);
				
				enteDTO.setLogo(dto);
			}
			
			condizioneDDPDTO.setEnte(enteDTO);

			BigDecimal importo = condizioneDocumento.getCondizionePagamento().getImTotale();
			condizioneDDPDTO.setImporto(importo);

			Date scadenza = condizioneDocumento.getCondizionePagamento().getDtScadenza();
			condizioneDDPDTO.setScadenza(scadenza);

			TributoEnte tributo = condizioneDocumento.getCondizionePagamento().getPendenza().getTributoEnte();
			condizioneDDPDTO.setTributo(tributo.getDeTrb());
			condizioneDDPDTO.setCdTrbEnte(tributo.getCdTrbEnte());
			condizioneDDPDTO.setPagamentoPredeterminato("Y".equals(tributo.getFlPredeterm()));

			Pendenza pendenza = condizioneDocumento.getCondizionePagamento().getPendenza();
			
			List<IntestatarioDTO> debitoriDTO = new ArrayList<IntestatarioDTO>();
			List<String> debitori = new ArrayList<String>();
			AnagraficaFacade anagraficaBean = null;
			try {
				anagraficaBean = (AnagraficaFacade) ServiceLocator.getSLSBProxy("anagraficaFacadeBean");
			} catch (ServiceLocatorException e) {
				// do nothing
				LOGGER.error("error retrieving anagraficaFacadeBean. Continue...", e);
			}
			for (DestinatariPendenza destinatarioPendenza : pendenza.getDestinatari()) {
				if(!DestinatariPendenza.TIPO_DEST_DELEGATO.equals(destinatarioPendenza.getTiDestinatario())) {
					debitori.add(destinatarioPendenza.getCoDestinatario()); // cod fiscale
					if (anagraficaBean != null) {
						IntestatarioDTO debitoreDTO = anagraficaBean.readIntestatario(destinatarioPendenza.getCoDestinatario());
						if (debitoreDTO == null) {
							debitoreDTO = new IntestatarioDTO();
							debitoreDTO.setIdFiscale(destinatarioPendenza.getCoDestinatario());
						}
						debitoriDTO.add(debitoreDTO);
					}
				}
			}
			
			condizioneDDPDTO.setDebitori(debitori);
			condizioneDDPDTO.setDebitoriDTO(debitoriDTO);

            condizioneDDPDTO.setCodiceFiscaleCreditore(codiceFiscaleCreditore);
            
			String codTr = pendenza.getTributoEnte().getCdTrbEnte();
			String causale = pendenza.getDeCausale().trim();

			TributoStrutturato tributoStrutturato = pendenza.getTributoStrutturato();
			if (tributoStrutturato == null || !AddOnManagerFactory.exists(tributoStrutturato.getTipoTributo())) {
			    if ("E_BOLLO".equals(tributo.getCdTrbEnte())) {
					condizioneDDPDTO.setMap_causale(getCausaleFormattedMarcaBolloDigitale(ddp.getDistinta().getPagamenti(),null));
					condizioneDDPDTO.setCausale("");
				} else if (tributo.getCfgTributoEntePlugin() != null) {
					Map<String, String> mapCausale = getCausaleFormattedByPlugin(causale, tributo.getCfgTributoEntePlugin().getCdPlugin(), ente.getIdEnte(), tributo.getCdTrbEnte(), locale);
					if (mapCausale == null || mapCausale.isEmpty()) {
						condizioneDDPDTO.setMap_causale(null);
						condizioneDDPDTO.setCausale(causale);
					} else {
						condizioneDDPDTO.setMap_causale(mapCausale);
						condizioneDDPDTO.setCausale("");
					}
				} else {
					condizioneDDPDTO.setCausale(getCausaleFormatted(codTr, causale));
					condizioneDDPDTO.setMap_causale(null);
				}
				
			} else {
				condizioneDDPDTO.setMap_causale(getCausaleFormattedByPlugin(tributoStrutturato, ente.getIdEnte(), tributo.getCdTrbEnte(), locale));
				condizioneDDPDTO.setCausale("");
			}
			condizioneDDPDTO.setNote(pendenza.getNote());
			condizioneDDPDTO.setCausale_condizioni(condizioneDocumento.getCondizionePagamento().getCausalePagamento());

			String tipoTributoEnte = pendenza.getTributoEnte().getCategoriaobj().getDeTrb();
			String tipoTributoEnteNoNulls = tipoTributoEnte != null ? tipoTributoEnte : "";
			String causalePend = pendenza.getDeCausale();
			String causalePendNoNulls = causalePend != null ? causalePend : "";
			String notePend = pendenza.getNote();
			String notePendNoNulls = notePend != null ? notePend : "";
			String descrizioneFreccia = tipoTributoEnteNoNulls + " " + causalePendNoNulls + " " + notePendNoNulls;

			condizioneDDPDTO.setDescrizioneFreccia(descrizioneFreccia);
			condizioneDDPDTO.setImporto(importo);

			carrello.add(condizioneDDPDTO);
		}

		return carrello;

	}

	// usato per Ricevute/Quietanza
	public static void populateCarrello(Set<Pagamenti> pagamentiCarrello, DocumentoDiPagamentoDTO documentoDiPagamentoDTO, Locale locale) {

		List<CondizioneDDPDTO> carrello = new ArrayList<CondizioneDDPDTO>();

		for (Pagamenti pagamento : pagamentiCarrello) {
			
			CondizionePagamento condizionePagamento = pagamento.getCondPagamento();
			
			CondizioneDDPDTO condizioneDDPDTO = new CondizioneDDPDTO();
			

			if ("E_BOLLO".equals(condizionePagamento.getPendenza().getTributoEnte().getCdTrbEnte())) {
				AllegatiPendenza allegato = condizionePagamento.getAllegatiPendenza().iterator().next();

				String datiMDB = new String(Base64.decodeBase64(allegato.getDatiBody()));
				String[] coppieChiaveValore = datiMDB.split(";");
				for (int i = 0; i < coppieChiaveValore.length; i++) {
					String cc = coppieChiaveValore[i];
					String dd[] = cc.split("=");
					// TIPO, PROV, HASH
					if ("HASH".equals(dd[0])) {
						condizioneDDPDTO.setHashMBD(dd[1]);
						break;
					}
				}
			}			

			Pendenza pendenza = condizionePagamento.getPendenza();
			
			Enti ente = condizionePagamento.getEnte();
			
			Intestatari anagraficaEnte = (Intestatari)ente.getIntestatarioobj();
			
			EnteDTO enteDTO = new EnteDTO();
			
			enteDTO.setCodice(ente.getCodiceEnte());
			
			enteDTO.setDenominazione(ente.getDenominazione());
			
			enteDTO.setId(ente.getIdEnte());
			
			IntestatarioDTO intestDTO = new IntestatarioDTO();
			
			intestDTO.setIndirizzo(IndirizzoDTOBuilder.populateIndirizzoDTO(anagraficaEnte));
			
			enteDTO.setIntestatario(intestDTO);
			
			condizioneDDPDTO.setEnte(enteDTO);
			
			condizioneDDPDTO.setImporto(pagamento.getImPagato());
			condizioneDDPDTO.setScadenza(condizionePagamento.getDtScadenza());
			condizioneDDPDTO.setTributo(pendenza.getTributoEnte().getDeTrb());
			condizioneDDPDTO.setCdTrbEnte(pendenza.getTributoEnte().getCdTrbEnte());
			condizioneDDPDTO.setPagamentoPredeterminato("Y".equals(pendenza.getTributoEnte().getFlPredeterm()));

			String codTr = pendenza.getTributoEnte().getCdTrbEnte();
			String causale = pendenza.getDeCausale().trim();

			TributoStrutturato tributoStrutturato = pendenza.getTributoStrutturato();
			if (tributoStrutturato == null || !AddOnManagerFactory.exists(tributoStrutturato.getTipoTributo())) {
				if (EnumDemanioIdricoCanoni.TIPO_TRIBUTO_KEYS.contains(condizionePagamento.getCdTrbEnte())){
					condizioneDDPDTO.setMap_causale(getDetails(causale, "DEMANIO_IDRICO_CANONI", ente.getIdEnte(), condizionePagamento.getCdTrbEnte(), locale));
					condizioneDDPDTO.setCausale("");
				} else if (EnumDemanioIdricoIstruttoria.TIPO_TRIBUTO_KEYS.contains(condizionePagamento.getCdTrbEnte())){
					condizioneDDPDTO.setMap_causale(getDetails(causale, "DEMANIO_IDRICO_ISTRUTTORIA", ente.getIdEnte(), condizionePagamento.getCdTrbEnte(), locale));
					condizioneDDPDTO.setCausale("");
				} else if (!"E_BOLLO".equals(condizionePagamento.getCdTrbEnte())) {
					condizioneDDPDTO.setCausale(getCausaleFormatted(codTr, causale));
					condizioneDDPDTO.setMap_causale(null);
				} else {
					Set<Pagamenti> pagaments = new HashSet<Pagamenti>();
					pagaments.add(pagamento);
					condizioneDDPDTO.setMap_causale(getCausaleFormattedMarcaBolloDigitale(pagaments, null));
					condizioneDDPDTO.setCausale("");
				}
			} else {
				condizioneDDPDTO.setMap_causale(getCausaleFormattedByPlugin(tributoStrutturato, ente.getIdEnte(), pendenza.getTributoEnte().getCdTrbEnte(), locale));
				condizioneDDPDTO.setCausale("");
			}
			condizioneDDPDTO.setNote(pendenza.getNote());
			condizioneDDPDTO.setCausale_condizioni(condizionePagamento.getCausalePagamento());

			condizioneDDPDTO.setRiscossore(condizionePagamento.getPendenza().getCoRiscossore());
			condizioneDDPDTO.setRiferimento(condizionePagamento.getPendenza().getRiferimento());

			condizioneDDPDTO.setDebitori(condizionePagamento.getDebitori());
			condizioneDDPDTO.setCodiceFiscaleCreditore(condizionePagamento.getEnte().getIntestatarioIForm().getLaplIForm());
			
			condizioneDDPDTO.setIdCondizione(condizionePagamento.getIdCondizione());
			condizioneDDPDTO.setIdPagamento(condizionePagamento.getIdPagamento());
			if(pagamento.getTsDecorrenza() != null)
				condizioneDDPDTO.setTsDecorrenzaPagamento(new Date(pagamento.getTsDecorrenza().getTime()));
			
			List<VocePagamento> vociPagam = condizionePagamento.getVociPagamento();
			if(vociPagam != null && !vociPagam.isEmpty()) {
				List<ChiaveValoreDTO> listaVociPagamento = new ArrayList<ChiaveValoreDTO>();
				for (VocePagamento vocePagamento : vociPagam) {
					listaVociPagamento.add(new ChiaveValoreDTO(vocePagamento.getDeVoce(), vocePagamento.getImVoce()));
				}
				condizioneDDPDTO.setVociPagamento(listaVociPagamento);
			}
			condizioneDDPDTO.setIdRiscossionePSP(pagamento.getIdRiscossionePSP());
			
			carrello.add(condizioneDDPDTO);
		}

		documentoDiPagamentoDTO.setCarrello(carrello);

	}

	private static <T extends TributoStrutturato> Map<String, String> getDetails(T tributo, String idEnte, String cdTrbEnte, Locale locale) {
		if (tributo != null) {
			AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(idEnte, cdTrbEnte, tributo.getTipoTributo());
			AddOnReceiptHelper<T> receiptHelper = manager.getReceiptHelper();
			Map<String, String> receiptDetailsMap = receiptHelper.extractReceiptDetails(materialize(tributo), locale);
			return receiptDetailsMap;
		}
		return null;
	}
	
	private static <T extends TributoStrutturato> Map<String, String> getDetails(String causale, String tipoTributo, String idEnte, String cdTrbEnte, Locale locale) {
		AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(idEnte, cdTrbEnte, tipoTributo);
		AddOnReceiptHelper<T> receiptHelper = manager.getReceiptHelper();
		Map<String, String> receiptDetailsMap = ((BaseViewHelper)receiptHelper).extractReceiptDetails(causale, locale);
		return receiptDetailsMap;
	}

	private static String getCausaleFormatted(String codTributo, String causale) {

		String causaleFormatted = "";
		String tipo = "";
		String targa = "";

		if (codTributo.equals(PosizioneDebitoriaConstants.COD_TRIBUTO_BOLLO_AUTO)) {

			String[] causalesplit = causale.split(";");

			for (String string : causalesplit) {
				if (string.contains("TIPO")) {
					tipo = string.split("=")[1] + " targa: ";
				}
				if (string.contains("TARGA")) {
					targa = string.split("=")[1];
				}

			}
			causaleFormatted += tipo + targa;

//		} else if (codTributo.equals(PosizioneDebitoriaConstants.COD_TRIBUTO_MULTA)) {
//
//			String[] causalesplit = causale.split(";");
//
//			for (String string : causalesplit) {
//				if (string.contains("Targa")) {
//					causaleFormatted += string + "\n";
//				} else if (string.contains("Data Verbale")) {
//					causaleFormatted += "N." + string + "\n";
//				} else if (string.contains("Verbale")) {
//					causaleFormatted += string + "\n";
//				}
//
//			}
		} else {
			causaleFormatted = causale;
		}

		return causaleFormatted;
	}

	private static <T extends TributoStrutturato> Map<String, String> getCausaleFormattedByPlugin(T tributo, String idEnte, String cdTrbEnte, Locale locale) {
		Map<String, String> map = null;
		if (tributo != null) {
			map = getDetails(tributo, idEnte, cdTrbEnte, locale);
		}
		return map;
	}
	
	private static <T extends TributoStrutturato> Map<String, String> getCausaleFormattedByPlugin(String causale, String tipoTributo, String idEnte, String cdTrbEnte, Locale locale) {
		Map<String, String> map = null;
		
			map = getDetails(causale, tipoTributo, idEnte, cdTrbEnte, locale);
		
		return map;
	}
	
	private static Map<String, String> getCausaleFormattedMarcaBolloDigitale(Set<Pagamenti> pagams,Locale locale) {
		Map<String, String> map = new HashMap<String, String>();
		String notePagamento="";
		for (Pagamenti p:pagams){
			if (p.getNotePagamento()!=null && !"".equals(p.getNotePagamento())){
				notePagamento = p.getNotePagamento();
				break;
			}
		}
		try {
			String[] arr = notePagamento.split(";");
			int i = 0;
			while (i<arr.length){
				String[] arr1 = arr[i].split("=");
				String key = arr1[0];
				String value = arr1[1];
				if ("IUBD".equals(key.trim())){
					map.put("IUBD", value.trim());
				}
				if ("DATA_ORA_ACQUISTO".equals(key.trim())){
					map.put("Data Ora Acquisto", value.trim().replace('T', ' '));
				}
				if ("PSP_EMITTENTE".equals(key.trim())){
					map.put("Psp Emittente", value.trim());
				}				
				i++;
			}
		} catch (Throwable t){}
		return map;
	}

}
