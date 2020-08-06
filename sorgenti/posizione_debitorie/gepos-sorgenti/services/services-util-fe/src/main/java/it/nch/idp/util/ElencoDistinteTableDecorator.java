package it.nch.idp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.utility.web.WebUtility;
import it.tasgroup.crypt.url.DownloadQuietanzaParametersEncrypter;
import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.MessageDescription;
import it.tasgroup.services.util.enumeration.EnumStatoIncasso;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;
import it.tasgroup.services.util.enumeration.EnumUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;

public class ElencoDistinteTableDecorator extends TableDecorator {
	private static String erroretocolor = "testonormalerosso";
	private static String verdecolor = "testonormaleverde";
	private static String giallocolor = "testonormalegiallo";

	public String getStatoDistinta() {
		String ret = "#";
		DistintaPosizioneDebitoriaVO tmp_dist = (DistintaPosizioneDebitoriaVO) getCurrentRowObject();
		if (tmp_dist != null) {
			String stato = tmp_dist.getStato();

			ret = getStatoDistintaHtml(stato, super.getPageContext());
		}
		return ret;
	}

	public static String getStatoDistintaHtml(String stato, PageContext pageConext) {
		if (stato == null || stato.trim().equals("")) {
			return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.aperto") + "</span>";
		} else if (stato.trim().equals(StatiPagamentiIris.ESEGUITO.getFludMapping())) {
			return "<span class='" + verdecolor + "'>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.eseguito") + "</span>";
		} else if (stato.trim().equals(StatiPagamentiIris.ESEGUITO_SBF.getFludMapping())) {
			return "<span class='" + giallocolor + "'>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.eseguitosbf") + "</span>";
		} else if (stato.trim().equals(StatiPagamentiIris.STORNATO.getFludMapping())) {
			return "<span class='" + giallocolor + "'>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.stornato") + "</span>";
		} else if (stato.trim().equals(StatiPagamentiIris.IN_CORSO.getFludMapping())) {
			return "<span class='" + giallocolor + "'>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.incorso") + "</span>";
		} else if (stato.trim().equals(StatiPagamentiIris.NON_ESEGUITO.getFludMapping())) {
			return "<span class='" + erroretocolor + "'>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.noneseguito") + "</span>";
		} else if (stato.trim().equals(StatiPagamentiIris.IN_ERRORE.getFludMapping())) {
			return "<span class='" + erroretocolor + "'>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.errore") + "</span>";
		} else if (stato.trim().equals(StatiPagamentiIris.ANNULLATO.getFludMapping())) {
			return "<span class='" + erroretocolor + "'>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.annullato") + "</span>";
		} else if (stato.trim().equals(StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getFludMapping())) {
			return "<span class='" + erroretocolor + "'>" + getTextMsg(pageConext, "posizionedebitoria.distinte.ricerca.stato.annullatoDaOperatore") + "</span>";
		} else {
			return "<span>" + stato + "</span>";
		}

	}

	public String getTracciatoDistinta() {
		String ret = "?";
		DistintaPosizioneDebitoriaVO tmp_dist = (DistintaPosizioneDebitoriaVO) getCurrentRowObject();
		if (tmp_dist != null) {
			String tracciato = tmp_dist.getTracciato();

			return getTracciatoDistintaHtml(tracciato, super.getPageContext());
		}

		return ret;
	}

	public static String getTracciatoDistintaHtml(String tracciato, PageContext pageConext) {
		if (tracciato != null) {
			if (tracciato.trim().equals(PosizioneDebitoriaConstants.CARTADICREDITO)) {
				return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.mezzoPagamento.cartacredito") + "</span>";
			} else if (tracciato.trim().equals(PosizioneDebitoriaConstants.RIDONLINE)) {
				return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.mezzoPagamento.ridonline") + "</span>";
			} else if (tracciato.trim().equals(PosizioneDebitoriaConstants.BOLLETTINO)) {
				return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.mezzoPagamento.bollettino") + "</span>";
			} else if (tracciato.trim().equals(PosizioneDebitoriaConstants.BONIFICO)) {
				return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.mezzoPagamento.bonifico") + "</span>";
			} else {
				return "<span>" + tracciato + "</span>";
			}
		}

		return "";
	}

	public String getTipoStrumentoDistinta() {
		String ret = "?";
		DistintaPosizioneDebitoriaVO tmp_dist = (DistintaPosizioneDebitoriaVO) getCurrentRowObject();
		if (tmp_dist != null) {
			String tracciato = tmp_dist.getModalitaPagamento();

			return getTracciatoDistintaHtml(tracciato, super.getPageContext());
		}

		return ret;
	}

	public static String getTipoStrumentoDistintaHtml(String tipoStrumento, PageContext pageConext) {
		
		if (tipoStrumento != null) {
			MessageDescription message = EnumUtils.findByChiave(tipoStrumento, EnumTipoModalitaPagamento.class);
			if (message != null){
				return "<span>" + getTextMsg(pageConext,message.getChiaveBundle(),"PDC") + "</span>";
			}
			else{
				return "<span>" + tipoStrumento + "</span>";
			}
		}
		
		
//		if (tipoStrumento != null) {
//			if (tipoStrumento.trim().equals(PosizioneDebitoriaConstants.CARTADICREDITO)) {
//				return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.mezzoPagamento.cartacredito", "PDC") + "</span>";
//			}
//			if (tipoStrumento.trim().equals(PosizioneDebitoriaConstants.RIDONLINE)) {
//				return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.mezzoPagamento.ridonline", "PDC") + "</span>";
//			} else {
//				return "<span>" + tipoStrumento + "</span>";
//			}
//		}

		return "";
	}

	private static String getTextMsg(PageContext pageContext, String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(pageContext, message);
		} catch (Exception Ex) {
		}
		return msg;
	}

	private static String getTextMsg(PageContext pageContext, String message, String s_code) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(pageContext, message, s_code);
		} catch (Exception Ex) {
		}
		return msg;
	}

	public String getTipologiaPagamento() {
		String ret = "?";
		DistintaPosizioneDebitoriaVO tmp_dist = (DistintaPosizioneDebitoriaVO) getCurrentRowObject();
		if (tmp_dist != null) {
			String tipoSpontaneo = tmp_dist.getTipoSpontaneo();

			return getTipologiaPagamentoHtml(tipoSpontaneo, super.getPageContext());
		}

		return ret;
	}

	public static String getTipologiaPagamentoHtml(String tipoSpontaneo, PageContext pageConext) {
		if (tipoSpontaneo != null) {
			return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.tipologia.spontaneo") + "</span>";
		} else {
			return "<span>" + getTextMsg(pageConext, "posizionedebitoria.distinte.tipologia.posdebitoria") + "</span>";

		}
	}

    public String getLinkQuietanza() {

    	DistintaPosizioneDebitoriaVO tmp_dist = (DistintaPosizioneDebitoriaVO) getCurrentRowObject();
        
		Integer id = tmp_dist.getIdPagamento().intValue();
		
 //       EnumStatoIncasso flagIncasso = EnumStatoIncasso.getByKey(tmp_dist.getFlagIncasso());

        String linkQuietanza = new String("");
        String imgURL = "/images/icona_pdf_32.png";
        String testoAlternativo = getTextMsg(getPageContext(), "posizionedebitoria.distinte.quietanza");
        
		DownloadQuietanzaParametersEncrypter dp = new DownloadQuietanzaParametersEncrypter(tmp_dist.getCodPagamento(),tmp_dist.getCoPagante(),id.toString());		
		
		String cParam=dp.encrypt();

		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");
		
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");

        
        String link = gwBaseUrl+"/documentiPagamento.do?method=downloadQuietanza&" + SharedConstants.CRYPTEDPARAMS + "=";

        //ora l'attesatazione del pagamento viene rilasciata 
    	//a fronte del pagamento in stato ES
        if ("ES".equals(tmp_dist.getStPagamento()) &&tmp_dist.isAssociatedDocAvailable()) {
            
        	linkQuietanza = "<a href='" + link + cParam + "'><img src='" + WebUtility.getContextPath(getPageContext()) + imgURL + "' alt='" + testoAlternativo
                    + "' /></a>";
        } else
            linkQuietanza = "";
        
        return linkQuietanza;
    }
    
    public static String getLinkStorico(String urlOk, String codTransazione) {
    	Properties publicProperties = ConfigurationPropertyLoader.getProperties("public/paytas-public.properties");
		String publicBaseUrl = publicProperties.getProperty("paytas.public.context.root");
		String link = publicBaseUrl + "/ricercaPagamentoAnonimo.jsf?codPagamento=" + codTransazione + "&faces-redirect=true";

    	String[] params = urlOk.split("&"); 
		String sessionId = null;
		boolean found = false;
		for (int i = 0; i < params.length && !found; i++) {
			String name = params[i].split("=")[0];
	        System.out.println("");
			if (name.equals("sessionId")) {
	        	sessionId = params[i].split("=")[1];
	        	found = true;
	        }
	    }
		if (sessionId != null) {
			try {
				sessionId = URLEncoder.encode(sessionId, "ISO-8859-1");
				link = publicBaseUrl + "/enterFromIris.jsf?sessionId=" + 
						sessionId +"&faces-redirect=true&target=ricercaPagamentoAnonimo.jsf?codPagamento=" + codTransazione;

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return link;
    }

}
