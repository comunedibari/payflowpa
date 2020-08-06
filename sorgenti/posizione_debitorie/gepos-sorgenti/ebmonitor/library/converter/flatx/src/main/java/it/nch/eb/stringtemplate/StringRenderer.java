package it.nch.eb.stringtemplate;


import it.nch.eb.common.utils.StringUtils;

import org.antlr.stringtemplate.AttributeRenderer;

public class StringRenderer implements AttributeRenderer {

	@Override
	public String toString(Object arg0) {
		// TODO Auto-generated method stub
		return arg0.toString();
	}

	@Override
	public String toString(Object arg0, String format) {
		// TODO Auto-generated method stub
		String integ = (String)arg0;

		StringUtils utils = new StringUtils();

		if (format.equalsIgnoreCase("padString")) {
			   //return String.format("%1$#" + 6+ "s", (String)arg0);
				return utils.leftPad((String)arg0, 6, ' ');
		} if (format.equalsIgnoreCase("padTesta")) {
			   //return String.format("%1$#" + 5+ "s", (String)arg0);
			   return utils.leftPad((String)arg0, 5, ' ');
		} else if (format.equalsIgnoreCase("importo")) {
				//System.out.println(" FORMAT IMPORTO !!! " + format + " / " + arg0 );
				return utils.leftPad((String)arg0, 13, '0');
		} else if (format.equalsIgnoreCase("padSupporto")) {
			   //return String.format("%1$#" + 20+ "s", (String)arg0);
				return utils.rightPad((String)arg0, 20, ' ');
		}		
//		else if (format.equalsIgnoreCase("padBeneficiario") && (integ.length()>0 && integ.length()<=30) ) {
//				//System.out.println(" padding at 30");
//			   //return String.format("%1$#" + 30 + "s", (String)arg0);
//			   return utils.leftPad((String)arg0, 30, ' ');
//		} else if (format.equalsIgnoreCase("padBeneficiario") && (integ.length()>30 && integ.length()<=60)) {
//				//System.out.println(" padding at 60");
//			   //return String.format("%1$#" + 60 + "s", (String)arg0);
//			   return utils.leftPad((String)arg0, 60, ' ');
//		} else if (format.equalsIgnoreCase("padBeneficiario") && (integ.length()>60 && integ.length()<=90)) {
//				//System.out.println(" padding at 90");
//			   //return String.format("%1$#" + 90 + "s", (String)arg0);
//			   return utils.leftPad((String)arg0, 90, ' ');
//		} else if (format.equalsIgnoreCase("padBeneficiario") && (integ.length()>90)) {
//				//System.out.println(" padding /trunc at 30");
//			   //return String.format("%1$#" + 90 + "s", integ.substring(0, 89));
//			   return utils.leftPad(integ.substring(0, 89), 90, ' ');
//		}
		else if (format.equalsIgnoreCase("padBeneficiario")  ) {
			   return utils.rightPad((String)arg0, 90, ' ');
		} else if (format.equalsIgnoreCase("codiceUnivoco")) {
			   //return String.format("%1$#" + 30 + "s", (String)arg0);
			   return utils.rightPad((String)arg0, 30, ' ');
		} else if (format.equalsIgnoreCase("idPagamentoCondizione")) {
				//se e' maggiore di 35... tronco
				if (((String)arg0).length()>35) {
					return ((String)arg0).substring(0,34);
				} else {
				//altrimenti tolgo gli spazi
					return ((String)arg0).trim();					
				}
		} else if (format.equalsIgnoreCase("cfDebitore")) {
			//se e' maggiore di 35... tronco
			if (((String)arg0).length()>16) {
				return ((String)arg0).substring(0,15);
			} else {
			//altrimenti tolgo gli spazi
				return ((String)arg0).trim();					
			}
		} else {
			return (String)arg0;
		}
	}

}
