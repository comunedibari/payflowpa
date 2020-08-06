package it.nch.idp.util;

import static it.tasgroup.iris.shared.util.UtilDate.dateGreaterThanToday;
import static it.tasgroup.iris.web.decorator.MarkupHelper.createClickableIcon;
import static it.tasgroup.iris.web.decorator.MarkupHelper.createStatus;
import static it.tasgroup.iris.web.decorator.MarkupHelper.createTransparentStatus;
import it.nch.idp.backoffice.tavolooperativo.PendenzeCartVO;
import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.CondizionePagamentoForHomePageVO;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.utility.web.WebUtility;
import it.tasgroup.crypt.url.ManageDDPParametersEncrypter;
import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.dto.tavolooperativo.DatiCART_DTO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;
import it.tasgroup.iris.web.decorator.MarkupHelper;
import it.tasgroup.iris.web.decorator.MarkupHelper.IconDimension;
import it.tasgroup.iris.web.decorator.MarkupHelper.StatusLevel;
import it.tasgroup.services.util.enumeration.EnumSeverityLevelTO;
import it.tasgroup.services.util.enumeration.EnumStatoAllineaPendenze;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.decorator.TableDecorator;

public class ElencoPendenzeTableDecorator extends TableDecorator {

	public String getStatoPendenza() {
		String ret = "#";
		AvvisoPosizioneDebitoriaVO pendenza = (AvvisoPosizioneDebitoriaVO) getCurrentRowObject();
		if (pendenza != null) {
			ret = decorateStatoPendenzaHtml(pendenza.getStato(), pendenza.getImportoRiscosso(),
					pendenza.getImportoPagato(), pendenza.getImportoSbf(), pendenza.getImportoNonPagabile(),
					pendenza.getImporto(), pendenza.getCondizioniPagabili(), pendenza.getCondizioniNonPagabili(),
					pendenza.getDataFineValidita(),
					pendenza.getCod_tributo(),
					pendenza.getUrlUpdateService(),
					super.getPageContext());
		}
		return ret;
	}

	public String getPendenzaButton() {

		AvvisoPosizioneDebitoriaVO pendenza = (AvvisoPosizioneDebitoriaVO) getCurrentRowObject();
		if (pendenza == null)
			return "#";

		BigDecimal importoRiscosso = pendenza.getImportoRiscosso() == null ? new BigDecimal("0") : pendenza.getImportoRiscosso();
		BigDecimal importoPagato = pendenza.getImportoPagato() == null ? new BigDecimal("0") : pendenza.getImportoPagato();
		BigDecimal importoTotale = pendenza.getImporto() == null ? new BigDecimal("0") : pendenza.getImporto();
		BigDecimal importoSbf = pendenza.getImportoSbf() == null ? new BigDecimal("0") : pendenza.getImportoSbf();
		
		BigDecimal sommmaRiscossoPagato = importoRiscosso.add(importoPagato);
		BigDecimal sommmaTotale = sommmaRiscossoPagato.add(importoSbf);
		
		
		
		String url = "javascript:submitOperation('PDC/avvisiMain.do?method=visualizzaAvviso&amp;isLente=true&amp;selCheckbox=" + pendenza.getIdPendenza()
				+ "','avvisiForm');";
		if (!"C".equals(pendenza.getStato()) // non � chiusa
				&& (sommmaTotale.doubleValue() < importoTotale.doubleValue()) // non � pagata			
				&& !dateGreaterThanToday(pendenza.getDataFineValidita()) // termini pagamento scaduti
				&& "BOLLO_AUTO".equals(pendenza.getCod_tributo()) // � un bollo auto
				&& StringUtils.isNotEmpty(pendenza.getUrlUpdateService()) // esiste urlUpdateService nella tributienti
		) {
			return createClickableIcon(url, getTextMsg("posizionedebitoria.buttonAggiorna"), "icon-hand-right", IconDimension.X2, true);
		} else {
			return createClickableIcon(url, getTextMsg("posizionedebitoria.buttonDettaglio"), "icon-zoom-in", IconDimension.X2, true);
		}

	}	
	
	
	/**
	 * 
	 * @return
	 */
	public String getDdpStatus() {
		String ret = "";
		AvvisoPosizioneDebitoriaVO pendenza = (AvvisoPosizioneDebitoriaVO) getCurrentRowObject();
		if (pendenza != null)
			ret = decorateDdpStatusHtml(pendenza.getStato());
		return ret;
	}

	/**
	 * 
	 * @param stato
	 * @return
	 */
	private String decorateDdpStatusHtml(String stato) {
		if (stato == null)
			stato = "";
		else
			stato = stato.trim();
		if (!"C".equals(stato))
			return "<img src=\"/images/icona_pdf_24.png\" alt=\"pdf\">";
		return "";
	}

	/**
	 * 
	 * @return
	 */
	public String getModalita() {
		String ret = "";
		AvvisoPosizioneDebitoriaVO pendenza = (AvvisoPosizioneDebitoriaVO) getCurrentRowObject();
		if (pendenza != null) {
			ret = ModalitaPagamentoDecorator.decorateModalitaPagamentoHtml(pendenza.getModalita(),
					super.getPageContext());
		}
		return ret;

	}

	/**
	 * 
	 * @param stato
	 * @param importoRiscosso
	 * @param importoPagato
	 * @param importoSbf
	 * @param importoTotale
	 * @param pageContext
	 * @return
	 */
	public static String decorateStatoPendenzaHtml(String stato, BigDecimal importoRiscosso, BigDecimal importoPagato,
			BigDecimal importoSbf, BigDecimal importoNonPagabile, BigDecimal importoTotale, Integer condizioniPagabili,
			Integer condizioniNonPagabili, Date dataFineValidita, String codiceTributoEnte, String urlUpdService, PageContext pageContext) {
		
		
//		String cd_trb_ente, 
		
		String ret = "#";
		if (stato == null)
			stato = "";
		else
			stato = stato.trim();
		if (importoRiscosso == null)
			importoRiscosso = new BigDecimal("0");
		if (importoPagato == null)
			importoPagato = new BigDecimal("0");
		if (importoTotale == null)
			importoTotale = new BigDecimal("0");
		if (importoSbf == null)
			importoSbf = new BigDecimal("0");
		if (importoNonPagabile == null)
			importoNonPagabile = new BigDecimal("0");
		if (condizioniNonPagabili == null)
			condizioniNonPagabili = new Integer(0);
		if (condizioniPagabili == null)
			condizioniPagabili = new Integer(0);

		BigDecimal sommmaRiscossoPagato = importoRiscosso.add(importoPagato);
		BigDecimal sommmaTotale = sommmaRiscossoPagato.add(importoSbf);

		if ("C".equals(stato)) {
            return MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.chiuso"),StatusLevel.SUCCESS);
        }
		if ("A".equals(stato) || StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getPagaMapping().equals(stato)) {

			if (sommmaTotale.doubleValue() <= 0) {
				if (condizioniPagabili > 0) {

					// N.B. per comptibilit� col vecchio, se dataFineValidita = null o codiceTributoEnte o urlUpdService salto il controllo
					if (dataFineValidita == null || codiceTributoEnte == null /*|| urlUpdService == null*/) {
						// come prima
						return MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.dapagare"),StatusLevel.WARNING);
					} else {
						// if( !dateGreaterThanToday(dataFineValidita) && "BOLLO_AUTO".equals(codiceTributoEnte) && StringUtils.isNotEmpty(urlUpdService)) {
						if( !dateGreaterThanToday(dataFineValidita)) {
							// Termini di pagamento scaduti
							return MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.terminiPagScaduti"),StatusLevel.WARNING);
						} else {
							return MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.dapagare"),StatusLevel.WARNING);
						}
					}

				} else {
					return MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.nonpagabile"), StatusLevel.IMPORTANT);
				}
			}

			// pagamento disposto se tra i pagamenti � presente un pagamento eseguito, cio� l'importo pagato � maggiore di zero
			if (sommmaTotale.doubleValue() > 0 && sommmaTotale.doubleValue() < importoTotale.doubleValue()) {
				if (importoPagato.compareTo(BigDecimal.ZERO) > 0 || importoRiscosso.compareTo(BigDecimal.ZERO) > 0) {
					return MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.parzialmentepagato"),StatusLevel.WARNING);
				} else {
					return MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.pagamentodisposto"),StatusLevel.WARNING);
				}

			}

			if (sommmaTotale.doubleValue() >= importoTotale.doubleValue()) {
				if (importoSbf.compareTo(BigDecimal.ZERO) > 0) {
					return  MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.pagamentodisposto"),StatusLevel.WARNING);
				} else {
                    return MarkupHelper.createIconStatus(getTextMsg(pageContext, "posizionedebitoria.statoPagamento.pagato"),StatusLevel.SUCCESS);
				}
			}

		}
		return ret;
	}

	/**
	 * 
	 * @param pageContext
	 * @param message
	 * @return
	 */
	private static String getTextMsg(PageContext pageContext, String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(pageContext, message);
		} catch (Exception Ex) {
		}
		return msg;
	}
	
	
	/**
	 * 
	 * @param pageContext
	 * @param message
	 * @return
	 */
	private String getTextMsg(String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(super.getPageContext(), message);
		} catch (Exception Ex) {
		}
		return msg;
	}	

	/**
	 * 
	 * @param causale
	 * @param cod_tributo
	 * @param operatore
	 * @return
	 */
	public static String decorateCausalePendenzaHtml(String causale, String cod_tributo, String operatore) {
		String causaleHtml = "";

		if (causale != null && cod_tributo != null) {
			String tipo = "";
			String targa = "";
			causale = causale.trim();
			String[] causalesplit = causale.split(";");

			if (PosizioneDebitoriaConstants.COD_TRIBUTO_BOLLO_AUTO.equalsIgnoreCase(cod_tributo)) {

				if (PosizioneDebitoriaConstants.OPERATORE_CITTADINO.equalsIgnoreCase(operatore)) {
					for (String string : causalesplit) {
						if (string.contains("TIPO")) {
							tipo = string.split("=")[1] + " targa: ";
						}
						if (string.contains("TARGA")) {
							targa = string.split("=")[1];
						}

					}
					causaleHtml = tipo + targa;
				} else {
					causaleHtml = causale;
				}
			} else {
				causaleHtml = causale;
			}
		}
		return causaleHtml;
	}

    public String getCausale() {
        CondizionePagamentoForHomePageVO condizione = (CondizionePagamentoForHomePageVO) getCurrentRowObject();
        
        String causaleDecodificata = CausaleHelper.getCausale(condizione.getCausale(), condizione.getIdEnte(), condizione.getCodiceTributoEnte(), condizione.getCdPlugin(), PosizioneDebitoriaConstants.OPERATORE_CITTADINO);
       
        StringBuffer causaleComposta = new StringBuffer("");
        causaleComposta.append(condizione.getDescrizioneTributo());
        causaleComposta.append(": ");
        causaleComposta.append(causaleDecodificata);
        causaleComposta.append("<br>(").append(condizione.getDenominazioneEnte()).append(")");
        
        if (condizione.getFlagPagamentoInDelega() != 0) 
            causaleComposta.append("<br> <em>Pagamento per conto di ").append(condizione.getCfDestinatario()).append("</em>");        	
        
        if (condizione.getRiscossore() != null) {
	        causaleComposta.append("<br>(");
	        causaleComposta.append(getTextMsg("posizionedebitoria.riscossore") + ": ");
	        causaleComposta.append(condizione.getRiscossore() + " /");
	        causaleComposta.append((condizione.getRiferimento() == null ?  " - )" : (" " + condizione.getRiferimento() + " )")));
        }
        return causaleComposta.toString();
    }
    

    
    public String getCausalePosizioneDebitoria() {
    	AvvisoPosizioneDebitoriaVO avviso = (AvvisoPosizioneDebitoriaVO) getCurrentRowObject();

    	String causaleDecodificata = CausaleHelper.getCausale(avviso.getCausale(), avviso.getIdEnte(), avviso.getCod_tributo(), avviso.getCdPlugin(), PosizioneDebitoriaConstants.OPERATORE_CITTADINO);
       
        StringBuffer causaleComposta = new StringBuffer("");
        causaleComposta.append(avviso.getTributo());
        causaleComposta.append(": ");
        causaleComposta.append(causaleDecodificata);
        causaleComposta.append("<br>(").append(avviso.getEnte()).append(")");
        
        getCausaleRiscossore(avviso, causaleComposta);
       return causaleComposta.toString();    	
    }

    public String getCausalePosizioneDebitoriaListStyle() {
    	AvvisoPosizioneDebitoriaVO avviso = (AvvisoPosizioneDebitoriaVO) getCurrentRowObject();

    	String causaleDecodificata = CausaleHelper.getCausale(avviso.getCausale(), avviso.getIdEnte(), avviso.getCod_tributo(), avviso.getCdPlugin(), PosizioneDebitoriaConstants.OPERATORE_CITTADINO);
       
        StringBuffer causaleComposta = new StringBuffer("");
        causaleComposta.append(getTextMsg("posizionedebitoria.tipoDebito") + ": " + avviso.getTributo());
        causaleComposta.append("<br>" + getTextMsg("posizionedebitoria.ente.creditore") + ": " + avviso.getEnte());
        causaleComposta.append("<br>" + getTextMsg("posizionedebitoria.causale") + ": " + causaleDecodificata);
        
        getCausaleRiscossore(avviso, causaleComposta);
       return causaleComposta.toString();    	
    }

	private void getCausaleRiscossore(AvvisoPosizioneDebitoriaVO avviso, StringBuffer causaleComposta) {
		if (avviso.getRiscossore() != null) {
	        causaleComposta.append("<br>(");
	        causaleComposta.append(getTextMsg("posizionedebitoria.riscossore") + ": ");
	        causaleComposta.append(avviso.getRiscossore() + " /");
	        causaleComposta.append((avviso.getRiferimento() == null ?  " - )" : (" " + avviso.getRiferimento() + " )")));
        }
	}
    /**
	 * Etichette per la tabella "pagamenti in scadenza" della home
	 * 
	 * @return
	 */
	public String getPagamentiScadenzaLabels() {
		CondizionePagamentoForHomePageVO pagamento = (CondizionePagamentoForHomePageVO) getCurrentRowObject();
		StringBuilder sb = new StringBuilder();
		if (pagamento.getIdDocumento() != null) {
			sb.append(createTransparentStatus("icon-file", getTextMsg("home.legenda.stato.documento")))
					.append(" ");
		}
		else if (pagamento.getFlagInCarrello()) { 
			sb.append(
					createTransparentStatus("icon-shopping-cart", getTextMsg("home.legenda.stato.carrello"))).append(" ");
//		} else if (UtilDate.setOrarioEndOfDay(pagamento.getDataScandenza()).before(new Date())) {
//			sb.append(createStatus("Scaduto", StatusLevel.IMPORTANT))
//			.append(" ");
		} else if (!dateGreaterThanToday(pagamento.getDataFineValidita())) {
			sb.append(createStatus(getTextMsg("posizionedebitoria.statoPagamento.terminiPagScaduti"), StatusLevel.IMPORTANT))
			.append(" ");
		} else {
			sb.append(createStatus(getTextMsg("home.legenda.stato.cod"), StatusLevel.INFO)) 
			.append(" ");
		}
	
		return sb.toString();
	}
	
	public String getPagamentiScadenzaButtons(){
		
		CondizionePagamentoForHomePageVO pagamento = (CondizionePagamentoForHomePageVO) getCurrentRowObject();
		
		String docId = pagamento.getIdDocumento();
		String cfDestinatario = pagamento.getCfPaganteDDP();
		String intestatarioDDP = pagamento.getIntestatarioDDP();
		String idPend = pagamento.getIdPendenza();
		boolean isInCarrello = pagamento.getFlagInCarrello();
		Date dataFineValidita = pagamento.getDataFineValidita();
		
		StringBuilder sb = getDDPButtons(docId, cfDestinatario, intestatarioDDP);

		if (isInCarrello) {
			
			sb.append(
					createClickableIcon("PDC/avvisiMain.do?method=vaiAlCarrello&m=", "Vai al Carrello",
                            "icon-shopping-cart", IconDimension.X2,true)).append(" ");
		} else if (docId == null) {
			
			String url = "javascript:submitOperation('PDC/avvisiMain.do?method=visualizzaAvviso&amp;isLente=true&amp;selCheckbox="
					+ idPend + "','homeForm');";

			if (!dateGreaterThanToday(dataFineValidita)) {

				if ("BOLLO_AUTO".equals(pagamento.getCodiceTributoEnte()) && StringUtils.isNotEmpty(pagamento.getUrlUpdService())) {
					sb.append(createClickableIcon(url, getTextMsg("posizionedebitoria.buttonAggiorna"), "icon-hand-right", IconDimension.X2, true)).append(" ");
				} else {
					sb.append(createClickableIcon(url, getTextMsg("posizionedebitoria.buttonDettaglio"), "icon-zoom-in", IconDimension.X2, true)).append(" ");
				}

			} else {
				
				sb.append(createClickableIcon(url, getTextMsg("home.legenda.stato.paga"), "icon-hand-right", IconDimension.X2, true)).append(" ");

			}
			
		}

		return sb.toString();
		

	}

	public StringBuilder getDDPButtons(String docId, String cfDestinatario, String intestatarioDDP) {

		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");
		
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");
		
		
		StringBuilder sb = new StringBuilder();

		if (docId != null) { 
			
			try {
				// LA RICHIESTA DI DOCUMENTO E' PUBBLICA E ANONIMA
				// Applico encryption.
				ManageDDPParametersEncrypter ep = new ManageDDPParametersEncrypter(cfDestinatario,docId,intestatarioDDP);
				
				String openUrl = gwBaseUrl+ "/documentiPagamento.do?method=downloadPublicDDP&amp;"+SharedConstants.CRYPTEDPARAMS+"="+ep.encrypt();
				
				ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
				String authUrl = props.getProperty("iris.cns.auth.path");
				String url_back_annulla = (authUrl.endsWith("/") ? authUrl : authUrl+"/") + "welcome.do?method=home&m=0";
				
				String cancelUrl = gwBaseUrl+"/documentiPagamento.do?method=annullaDocumento&amp;"+SharedConstants.CRYPTEDPARAMS+"="+ep.encrypt()+"&URL_BACK="+URLEncoder.encode(url_back_annulla,"UTF-8");
				
				sb.append("<p>").append(createClickableIcon(openUrl, getTextMsg("home.legenda.stato.apriDoc"), "icon-file", IconDimension.X2, true))
						.append("</p>");
				sb.append("<p>").append(
	                    createClickableIcon(cancelUrl, getTextMsg("home.legenda.stato.annullaDoc"), "icon-remove", IconDimension.X2, true))
						.append("<p>");
			
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace(); // Non dovrebbe mai succedere
				
				throw new RuntimeException(e);
				
			}
			
		}

		return sb;
	}
	
	public String getStatoCART() {
			
			PendenzeCartVO messaggio = (PendenzeCartVO) getCurrentRowObject();
			
			EnumStatoAllineaPendenze statoEnum = EnumStatoAllineaPendenze.getByDescription(messaggio.getStato());
			if (statoEnum==null){
			  statoEnum = EnumStatoAllineaPendenze.valueOf(messaggio.getStato()); // ByDescription(messaggio.getStato());
			}
			
			if (statoEnum.getSeverity().equals(EnumSeverityLevel.ERROR))
			
				return MarkupHelper.createStatusBK_OF(statoEnum.getDescrizione(), StatusLevel.IMPORTANT);
			
			if (statoEnum.getSeverity().equals(EnumSeverityLevel.INFO))
				
				return MarkupHelper.createStatusBK_OF(statoEnum.getDescrizione(), StatusLevel.SUCCESS);
			
			return MarkupHelper.createStatusBK_OF(statoEnum.getDescrizione(), StatusLevel.WARNING);
		}
	
	public String getConcatData() {
		
		DatiCART_DTO datiCART = (DatiCART_DTO) getCurrentRowObject();
		
		EnumSeverityLevelTO severityTO = datiCART.getSeverity();
		
		String severityDeco = null;
		
		if (EnumSeverityLevel.ERROR.equals(severityTO.getSeverity()) || EnumSeverityLevel.FATAL.equals(severityTO.getSeverity()))
		
			severityDeco = MarkupHelper.createStatusBK_OF(severityTO.getDescrizione(), StatusLevel.IMPORTANT);
		
		else if (EnumSeverityLevel.INFO.equals(severityTO.getSeverity()))
			
			severityDeco = MarkupHelper.createStatusBK_OF(severityTO.getDescrizione(), StatusLevel.SUCCESS);
		
		else  
			severityDeco = MarkupHelper.createStatusBK_OF(severityTO.getDescrizione(), StatusLevel.WARNING);
		
		return datiCART.getTimestamp_registrazione()+" [ " + severityDeco + " ] "+ StringEscapeUtils.escapeHtml(datiCART.getMessaggio());
	}

	public static String imageOrText(String fileName, String alternateText,ServletContext servletContext) {
		boolean imagesInPaytasPAEar= false;
		String out = "";
		if (imagesInPaytasPAEar) {
			String realPath = servletContext.getRealPath("");
			String relativeFileName = "/images/loghiPSP/" + fileName + ".png";

			File f = new File(realPath + relativeFileName);
			if (f.exists()) {
				out = "<img src=\"" + servletContext.getContextPath()
						+ relativeFileName + "\"  title=\"" + alternateText
						+ "\" />";
			} else {
				out = alternateText;
			}
		} else {
			// imag in url
			String baseUrl =ConfigurationPropertyLoader.getInstance("iris-fe.properties").getProperty("gateway.prePagamento.iconUrlPath");
			//String baseUrl ="http://localhost:8080/gateway/images/loghiPSP/";
			String urlStr = baseUrl+fileName + ".png";
			try {
			 /*  URL url = new URL(urlStr);
			   URLConnection connection = url.openConnection();
			   java.net.HttpURLConnection httpUrlCon = (java.net.HttpURLConnection)connection;
			   httpUrlCon.setRequestMethod("GET");
			   int responseCode = httpUrlCon.getResponseCode();
			   if (responseCode==HttpURLConnection.HTTP_OK) {*/
				   out = "<img src=" + urlStr + "  title=\"" + alternateText + "\" />";
			 /*  } else {
				   out = "<p>" + alternateText + "</p>"; 
			   }*/
			   
			} catch (Exception e) {
				out = "<p>" + alternateText+ "</p>"; 
			}
			
		}
		return out;
	}
	
}
