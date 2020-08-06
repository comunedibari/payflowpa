package it.nch.idp.util;

import org.apache.log4j.Logger;
import org.displaytag.decorator.TableDecorator;

import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.nch.utility.web.WebUtility;

/**
 *
 */
public class StatisticheDecorator extends TableDecorator {
	
	static Logger logger = Logger.getLogger(StatisticheDecorator.class);
	public String getCodicepippo() {
		final Object riepilogoStatisticheVO = getCurrentRowObject();
		return "";
	}
	public String getNomeMese() {
		final RiepilogoStatisticheVO riepilogoStatisticheVO = (RiepilogoStatisticheVO) getCurrentRowObject();
		String nomeMese = getTextMsg("statistiche.cruscotto.meseRif." + riepilogoStatisticheVO.getMese());
		return nomeMese;
	}
	
	
	private String getTextMsg(String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(super.getPageContext(), message);
		} catch (Exception Ex) {
		}
		return msg;
	}	

	
}
