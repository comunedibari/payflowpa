package it.nch.is.fo.web;

import it.nch.fwk.fo.web.menu.MenuCreator;
import it.nch.is.fo.CommonConstant;
import it.tasgroup.iris.dto.menu.ApplicazioniMenu;
import it.tasgroup.iris.dto.menu.AreeMenu;
import it.tasgroup.iris.dto.menu.FunzioniMenu;
import it.tasgroup.iris.dto.menu.ServiziMenu;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonUtility {

	public String amountFormatterForBackend(String frontendAmount) {
		if (frontendAmount == null || frontendAmount.trim().length() == 0)
			return "";
		if (frontendAmount != null && frontendAmount.indexOf(",") < 0)
			return frontendAmount;
		frontendAmount = frontendAmount.trim();
		String backendAmount = "";
		int commaIndex = frontendAmount.indexOf(",");
		String firstPart = null;

		String fractionStr = null;
		if (commaIndex > -1) {
			fractionStr = "." + frontendAmount.substring(commaIndex + 1, frontendAmount.length());
			firstPart = frontendAmount.substring(0, commaIndex);
		} else {
			firstPart = frontendAmount.substring(0, frontendAmount.length());
			fractionStr = ".00";
		}

		String[] digitStrs = null;
		if (firstPart != null)
			digitStrs = firstPart.split("\\.");

		if (digitStrs != null && digitStrs.length > 0) {
			for (int i = 0; i < digitStrs.length; i++) {
				backendAmount = backendAmount + digitStrs[i];
			}
			backendAmount = backendAmount + fractionStr;
		} else {
			//backendAmount = "0" + fractionStr;
			backendAmount = frontendAmount + fractionStr;
		}

		/*
		 * int begin = 0; int end = frontendAmount.indexOf('.');
		 * 
		 * if(end == -1) { backendAmount = backendAmount +
		 * frontendAmount.substring(0,commaIndex) + fractionStr; } else {
		 * for(;end != -1;) { backendAmount = backendAmount +
		 * frontendAmount.substring(begin,end); begin = end + 1; end =
		 * frontendAmount.indexOf('.',begin); }
		 * 
		 * backendAmount = backendAmount +
		 * frontendAmount.substring(begin,lastIndex) + fractionStr; }
		 */

		return backendAmount;
	}

	/**
	 * Restituisce l'importo con il formato del backend.<br>
	 * Se <code>nullable</code> è true in caso di importo con valore zero
	 * restituisce una string vuota.
	 * 
	 * @param frontendAmount
	 * @param nullable
	 * @return
	 */
	public String amountFormatterForBackend(String frontendAmount, boolean nullable) {
		String result = amountFormatterForBackend(frontendAmount);
		if (nullable) {
			if ("0.00".equals(result) || "0.0".equals(result) || "0".equals(result)) {
				return "";
			}
		}
		return result;
	}

	/**
	 * @deprecated Use instead the STATIC 'formatAmountForFrontend'
	 * @param str
	 * @return
	 */
	public String amountFormatterForFrontend(String str) {
		return CommonUtility.formatAmountForFrontend(str);
	}
	
	/*
	 * In questo metodo STATICO è stato travasato il corpo del vecchio 
	 * metodo (ora deprecato) amountFormatterForFrontend
	 */
	/**
	 * 
	 */
	public static String formatAmountForFrontend(String str) {
		// Se l'importo passato è null o una stringa vuota restituisco un importo con valore zero
		if (str == null || str.trim().length() == 0)
			return "0,00";
		if ((str.indexOf(",") > -1) && !(str.indexOf("E") > -1))
			return str;
		else if (str.indexOf("E") > -1) {
			// L'importo passato ha un esponente, passo quindi a convertire l'esponente
			String exponentStr = str.substring(str.indexOf("E") + 1);

			if (exponentStr.startsWith("+") && exponentStr.length() >= 2) {
				exponentStr = exponentStr.substring(1);
				str = str.substring(0, str.indexOf("+")) + str.substring(str.indexOf("+") + 1);
			}

			int exponent = Integer.parseInt(exponentStr);

			if (str.indexOf(",") > -1) {
				str = str.substring(0, str.indexOf(",")) + str.substring(str.indexOf(",") + 1, str.indexOf(",") + 1 + exponent) + "."
						+ ((str.indexOf("E") > str.indexOf(",") + 1 + exponent) ? str.substring(str.indexOf(",") + 1 + exponent, str.indexOf("E")) : "00");
			} else if (str.indexOf(".") > -1) {
				String fractionStr = str.substring(str.indexOf(".") + 1, str.indexOf("E"));
				String appendedZeroes = "";
				if (exponent > fractionStr.length()) {
					for (int i = fractionStr.length(); i <= exponent; i++) {
						appendedZeroes = appendedZeroes + "0";
					}
				}

				str = str.substring(0, str.indexOf("E")) + appendedZeroes + "E" + exponentStr;

				str = str.substring(0, str.indexOf(".")) + str.substring(str.indexOf(".") + 1, str.indexOf(".") + 1 + exponent) + "."
						+ ((str.indexOf("E") > str.indexOf(".") + 1 + exponent) ? str.substring(str.indexOf(".") + 1 + exponent, str.indexOf("E")) : "00");
			}
		}
		String resultStr = "";
		// Prima di convertire l'importo nel formato per il FrontEnd devo arrotondare i decimali.
		// DEVE essere fatto prima della conversione perchè dopo non è un numero riconosciuto dal sistema 
		// in quanto ha virgole e punti invertiti, punto per le centinaia e virgola per i decimali
		// l'esatto opposto di come li utilizza il sistema.
		str = roundAmount(str);
		
		if (str.startsWith(".")) {
			str = str.substring(1);
		}
		int commaIndex = str.indexOf(",");

		String fractionStr = null;
		int lastPointIndex = str.lastIndexOf(".");
		int end = 0;
		if (commaIndex > -1) {
			end = commaIndex;
			fractionStr = str.substring(commaIndex, str.length());

			while (fractionStr.length() < 3) {
				fractionStr = fractionStr + "0";
			}
		} else if (lastPointIndex < 0) {
			end = str.length();
			fractionStr = ",00";
		} else {
			end = lastPointIndex;
			fractionStr = "," + str.substring(lastPointIndex + 1, str.length());
			while (fractionStr.length() < 3) {
				fractionStr = fractionStr + "0";
			}
		}

		int begin = -1;
		if (end != 0)
			begin = end - 3;

		for (; begin >= 0;) {
			resultStr = str.substring(begin, end) + resultStr;

			if (begin != 0)
				resultStr = "." + resultStr;

			end = begin;
			begin = end - 3;
		}

		resultStr = str.substring(0, end) + resultStr + fractionStr;

		return resultStr;
	}

	/**
	 * Restituisce l'importo con il formato del frontend.<br>
	 * Se <code>nullable</code> è true in caso di importo con valore zero
	 * restituisce una string vuota.<br>
	 * Se <code>centinaia</code> è false in caso di importo con valore che
	 * supera le centinaia verrà rimossa ogni ricorrenza del punto delimitatore.
	 * 
	 * @param frontendAmount
	 * @param nullable
	 * @param centinaia
	 * @return
	 */
	public String amountFormatterForFrontend(String str, boolean nullable, boolean centinaia) {
		String result = formatAmountForFrontend(str);
		if (nullable) {
			if ("0,00".equals(result) || "0,0".equals(result) || "0".equals(result)) {
				return "";
			}
		}
		if (!centinaia) {
			result = result.replaceAll("[.]", "");
		}
		return result;
	}

	public String amountFormatterForRidirect(String redirectAmount) {
		String rAmount = "";
		if (redirectAmount.indexOf(".") == -1)
			return redirectAmount;

		int commaIndex = redirectAmount.indexOf(",");
		String firstPart = null;
		String secondPart = null;
		if (commaIndex > -1) {
			secondPart = "," + redirectAmount.substring(commaIndex + 1, redirectAmount.length());
			firstPart = redirectAmount.substring(0, commaIndex);
		}

		String[] digitStrs = null;
		if (firstPart != null)
			digitStrs = firstPart.split("\\.");

		if (digitStrs != null && digitStrs.length > 0) {
			for (int i = 0; i < digitStrs.length; i++) {
				rAmount = rAmount + digitStrs[i];
			}
			rAmount = rAmount + secondPart;
		}

		return rAmount;

	}

	public static HashMap getParentMenuSubMenuCodeFromFunctionCode(HttpServletRequest httpServletRequest) {
		HashMap hm = new HashMap();
		try {

			HttpSession session = httpServletRequest.getSession();
			if (session != null) {
				// retrive the functionCode

				String functionCode = (String) session.getAttribute("FUNCTIONCODESELECTED");
				if (!(functionCode != null && functionCode.trim().length() != 0 )) {
					functionCode = (String) session.getAttribute("FUNCTIONCODE");
				}
				
				if (functionCode != null && functionCode.trim().length() != 0 ) {
					// retrive the MenuCreator object from the http session 
					MenuCreator menuCreator = (MenuCreator) session.getAttribute(FrontEndConstant.MENU_CREATOR);
					if (menuCreator != null) {
						//collection of MenuTree
						Collection collMenuTree = menuCreator.getMenuTree();
						if (collMenuTree != null && collMenuTree.size() > 0) {
							Iterator itrApplicazioniMenu = collMenuTree.iterator();
							if (itrApplicazioniMenu != null) {

								boolean matched = false;
								ApplicazioniMenu selectedApplicazioniMenu = null;
								AreeMenu selectedAreeMenu = null;
								ServiziMenu selectedServiziMenu = null;
								FunzioniMenu selectedFunzioniMenu = null;

								while (itrApplicazioniMenu.hasNext() && !matched) {
									selectedApplicazioniMenu = (ApplicazioniMenu) itrApplicazioniMenu.next();
									ApplicazioniMenu applicazioniMenu = selectedApplicazioniMenu;
									Collection collAreeMenu = applicazioniMenu.getChildren();
									if (collAreeMenu != null && collAreeMenu.size() > 0) {
										Iterator itrAreeMenu = collAreeMenu.iterator();
										if (itrAreeMenu != null) {
											while (itrAreeMenu.hasNext() && !matched) {
												selectedAreeMenu = (AreeMenu) itrAreeMenu.next();
												AreeMenu areeMenu = selectedAreeMenu;
												Collection collServiziMenu = areeMenu.getChildren();
												if (collServiziMenu != null && collServiziMenu.size() > 0) {
													Iterator itrServiziMenu = collServiziMenu.iterator();
													if (itrServiziMenu != null) {
														while (itrServiziMenu.hasNext() && !matched) {
															selectedServiziMenu = (ServiziMenu) itrServiziMenu.next();
															ServiziMenu serviziMenu = selectedServiziMenu;
															if (serviziMenu != null) {
																String serviziMenuCode = serviziMenu.getCode();
																if (serviziMenuCode != null && serviziMenuCode.trim().length() != 0) {
																	serviziMenuCode = serviziMenuCode.trim();
																	if (serviziMenuCode.equals(functionCode))
																		matched = true;
																	else {
																		Collection collFunzioniMenu = serviziMenu.getChildren();
																		if (collFunzioniMenu != null && collFunzioniMenu.size() > 0) {
																			Iterator itrFunzioniMenu = collFunzioniMenu.iterator();
																			if (itrFunzioniMenu != null) {
																				while (itrFunzioniMenu.hasNext() && !matched) {
																					selectedFunzioniMenu = (FunzioniMenu) itrFunzioniMenu.next();
																					FunzioniMenu funzioniMenu = selectedFunzioniMenu;
																					if (funzioniMenu != null && funzioniMenu.getCode() != null
																							&& funzioniMenu.getCode().trim().length() != 0) {
																						if (funzioniMenu.getCode().trim().equals(functionCode))
																							matched = true;
																					}
																				}
																			}
																		}

																	}
																}

															}
														}
													}

												}
											}
										}
									}
								}

								if (selectedAreeMenu != null)
									hm.put("parentMenuId", selectedAreeMenu.getCode());
								if (selectedServiziMenu != null)
									hm.put("subMenuId", selectedServiziMenu.getCode());
							}
						}

					}
				}

			}
		} catch (Exception exc) {
			exc.printStackTrace();
			hm = null;
		}

		return hm;
	}

//	public static void formatAmountsForFlowServiceBackEnd(FlowForm flowVOFormImpl) {
//		CommonUtility commonUtility = new CommonUtility();
//		String posDispAmtForBE = commonUtility.amountFormatterForBackend(flowVOFormImpl.getSumOfPositiveDispositionAmountIForm());
//		String negDispAmtForBE = commonUtility.amountFormatterForBackend(flowVOFormImpl.getSumOfNegativeDispositionAmountIForm());
//		flowVOFormImpl.setSumOfPositiveDispositionAmountIForm(posDispAmtForBE);
//		flowVOFormImpl.setSumOfNegativeDispositionAmountIForm(negDispAmtForBE);
//
//	}
//
//	public static void formatAmountsForFlowServiceFrontendEnd(FlowForm flowFormImpl) {
//		CommonUtility commonUtility = new CommonUtility();
//		String posAmountForFrontEnd = commonUtility.amountFormatterForFrontend(flowFormImpl.getSumOfPositiveDispositionAmountIForm());
//		String negAmountForFrontEnd = commonUtility.amountFormatterForFrontend(flowFormImpl.getSumOfNegativeDispositionAmountIForm());
//		flowFormImpl.setSumOfPositiveDispositionAmountIForm(posAmountForFrontEnd);
//		flowFormImpl.setSumOfNegativeDispositionAmountIForm(negAmountForFrontEnd);
//
//	}

	/*
	 * public static HashMap populateCodeDescrMap(String codeKey, String
	 * descrKey) throws Exception { if (Tracer.isDebugEnabled("CommonUtility")) {
	 * Tracer.debug("CommonUtility", "populateCodeDescrMap", "BEGIN");
	 * Tracer.debug("CommonUtility", "populateCodeDescrMap", "codeKey .... " +
	 * codeKey); Tracer.debug("CommonUtility", "populateCodeDescrMap", "descrKey
	 * ...." + descrKey); } HashMap map = new HashMap();
	 * 
	 * if (codeKey == null || descrKey == null) return map;
	 * 
	 * Collection codeList = AppConfiguration.getList(codeKey); Collection
	 * descList = AppConfiguration.getList(descrKey);
	 * 
	 * if (Tracer.isDebugEnabled("CommonUtility")) { if (codeList != null)
	 * Tracer.debug("CommonUtility", "populateCodeDescrMap", "codeList has " +
	 * codeList.size() + " element(s)"); else Tracer.debug("CommonUtility",
	 * "populateCodeDescrMap", "codeList is null");
	 * 
	 * if (descList != null) Tracer.debug("CommonUtility",
	 * "populateCodeDescrMap", "descList has " + descList.size() + "
	 * element(s)"); else Tracer.debug("CommonUtility", "populateCodeDescrMap",
	 * "descList is null"); }
	 * 
	 * if (codeList!=null && descList!=null ){ Iterator codeIterator =
	 * codeList.iterator(); Iterator descIterator = descList.iterator();
	 * while(codeIterator.hasNext() && descIterator.hasNext()){ String key =
	 * (String)codeIterator.next(); if (Tracer.isDebugEnabled("CommonUtility")) {
	 * Tracer.debug("CommonUtility", "populateCodeDescrMap", "key = " + key); }
	 * if(!map.containsKey( key )) { String value = (String)descIterator.next();
	 * if (Tracer.isDebugEnabled("CommonUtility")) {
	 * Tracer.debug("CommonUtility", "populateCodeDescrMap", key + " --> " +
	 * value); } map.put(key, value); } } } return map; }
	 */
	public static boolean checkFunctionCode(String functionCode) {

		boolean result = false;

		HashMap hm = new HashMap();
		hm.put(CommonConstant.INCASSI_RID, CommonConstant.INCASSI_RID);
		hm.put(CommonConstant.RIDFILES_ALIGNMENT, CommonConstant.RIDFILES_ALIGNMENT);
		hm.put(CommonConstant.DIRECT_DEBIT, CommonConstant.DIRECT_DEBIT);

		if (hm.containsKey(functionCode) && hm.get(functionCode) != null) {
			result = true;
		}

		return result;

	}
	
	public static String amountFormatterWithoutDecimalPart(String amount) {
		//
		//	Se sono in questo metodo è perché mi aspetto di avere un numero
		//	senza cifre decimali significative, ad esempio 1234.00
		//
		String formattedWithoutDecimalPart = "";
		if (amount != null) {
			//
			//	Ottengo la formattazione desiderata: 1.234,00
			//
			String formattedWithDecimalPart = formatAmountForFrontend(amount);
			
			//
			//	Elimino la parte decimale, troncandola di brutto
			//	(ma assumo di entrare in questo metodo solo con decimali 
			//	non significativi.
			//	Il risultato atteso è 1.234
			//
			formattedWithDecimalPart = formattedWithDecimalPart.trim();
			if (formattedWithDecimalPart.length() > 0) {
				int indexOfDecimalSeparator = formattedWithDecimalPart.indexOf(",");
				if (indexOfDecimalSeparator > -1) {
					formattedWithoutDecimalPart = formattedWithDecimalPart.substring(0, indexOfDecimalSeparator);
				}
			}
		}
		return formattedWithoutDecimalPart;
	}


	/**
	 * @deprecated 
	 * 
	 * (vedi bug#78). In realtà questo metodo non va bene perchè toglie solo la
	 * parte decimale senza formattare la parte intera (mettendo gli eventuali separatori delle migliaia).
	 * 
	 * Use instead amountFormatterWithoutDecimalPart
	 */
	public static String amountFormatWithoutDecimalPart(String amount) {
		String str = "";
		if (amount != null) {
			amount = amount.trim();
			if (amount.length() > 0) {
				if (amount.indexOf(".") > -1) {
					amount = amount.substring(0, amount.indexOf("."));
				}
				str = amount;
			}

		}
		return str;
	}

	public static Method getMethod(Object obj, String mthdName) throws Exception {
		if (obj == null || mthdName == null)
			return null;
		try {
			Class cls = obj.getClass();
			Method[] mthds = cls.getMethods();
			for (int i = 0; i < mthds.length; i++) {
				if (mthds[i].getName().equals(mthdName))
					return mthds[i];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method getMethod(Object obj, String mthdName, Class[] partypes) throws Exception {
		if (obj == null || mthdName == null)
			return null;
		try {
			Class cls = obj.getClass();
			return cls.getMethod(mthdName, partypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object invoveMethod(Object object, Method method, Object[] arglist) {
		if (object == null || method == null)
			return null;
		try {
			return method.invoke(object, arglist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Restituisce l'importo arrotondato a due decimali.<br>
	 * Nel caso in cui l'importo passato è null o una string vuota restituisce la stringa stessa senza eseguire alcun calcolo.<br>
	 * 
	 * @throws NumberFormatException Nel caso in cui l'importo non sia un numero.
	 * @param amount Importo da arrotondare
	 * @return Una stringa con l'importo arrotondato (2 decimali)
	 */
	public static String roundAmount(String amount){
		if(amount == null || "".equals(amount)) return amount;
		BigDecimal bd = new BigDecimal(amount);		
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	public static void main(String[] args) {
		try {
			test1();
			System.out.println("Test 1 OK");
		} catch (CommonUtilityException e) {
			e.printStackTrace();
		}
	}
	
	public static void test1() throws CommonUtilityException {
		assertResultEqualsExpected(amountFormatterWithoutDecimalPart("1234.00"), "1.234");
		assertResultEqualsExpected(amountFormatterWithoutDecimalPart("1234"),    "1.234");
	}
	
	private static void assertResultEqualsExpected(String result, String expected) throws CommonUtilityException {
		if (!result.equals(expected)) {
			throw new CommonUtilityException("Result (" + result + ") doesn't match with expected value (" + expected + ")");
		}
	}

	static class CommonUtilityException extends Exception {

		public CommonUtilityException(String string) {
			super(string);
		}

	}
	
	public static String arrayToCsvString(String[] array) {
		StringBuffer buffer = new StringBuffer();
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				buffer.append(array[i]).append(", ");
			}
			buffer.delete(buffer.length()-2 , buffer.length());
		}
		return buffer.toString();
	}
	
}
