package it.nch.idp.util;

import it.nch.utility.web.WebUtility;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTOLight;

import java.text.SimpleDateFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;

public class ElencoDocumentiPagamentoTableDecorator extends TableDecorator {

	public String getStatoDDP() {
		try {
			DocumentoDiPagamentoDTOLight ddpDTO = (DocumentoDiPagamentoDTOLight) getCurrentRowObject();
			String chiaveBundle = ddpDTO.getStatoDDP().getChiaveBundle();
			return getTextMsg(getPageContext(), chiaveBundle);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getStatoDDP(PageContext pageContext, DocumentoDiPagamentoDTOLight ddpDTO) {
		try {			
			String chiaveBundle = ddpDTO.getStatoDDP().getChiaveBundle();
			return getTextMsg(pageContext, chiaveBundle);
		} catch (Exception e) {
			return "";
		}
	}

	public String getTipoDDP() {
		try {
			DocumentoDiPagamentoDTOLight ddpDTO = (DocumentoDiPagamentoDTOLight) getCurrentRowObject();
			return getTextMsg(getPageContext(), ddpDTO.getTipoDDP()
					.getChiaveBundle());
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getTipoDDP(PageContext pageContext, DocumentoDiPagamentoDTOLight ddpDTO) {
		try {
			
			return getTextMsg(pageContext, ddpDTO.getTipoDDP()
					.getChiaveBundle());
		} catch (Exception e) {
			return "";
		}
	}

	public String getDataAnnullamento() {
		String dataAnnullamentoStr = "";
		try {
			DocumentoDiPagamentoDTOLight ddpDTO = (DocumentoDiPagamentoDTOLight) getCurrentRowObject();
			
			if(ddpDTO.getDataAnnullamento() != null){
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				dataAnnullamentoStr = sdf.format(ddpDTO.getDataAnnullamento());
			}
		} catch (Exception e) {
			return "";
		} finally{
			return dataAnnullamentoStr;
		}
	}

	public String getDataEmissione() {
		String dataEmissioneStr = "";
		try {
			DocumentoDiPagamentoDTOLight ddpDTO = (DocumentoDiPagamentoDTOLight) getCurrentRowObject();
			if (ddpDTO.getDataEmissione() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				dataEmissioneStr = sdf.format(ddpDTO.getDataEmissione());
			}
		} catch (Exception e) {
			return "";
		}finally{
			return dataEmissioneStr;
		}
	}

	private static String getTextMsg(PageContext pageContext, String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(pageContext, message);
		} catch (Exception Ex) {
		}
		return msg;
	}
}
