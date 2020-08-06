package it.tasgroup.idp.autorizzazionepagamento.utils;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import it.tasgroup.idp.autorizzazionepagamento.servlet.AutorizzazionePagamentoVO;


public class AutorizzazionePagamentoVOBuilder {

	
	public static AutorizzazionePagamentoVO buildVO(HttpServletRequest req) throws Exception {
		AutorizzazionePagamentoVO richiesta = new AutorizzazionePagamentoVO();
		
		BufferedReader in = req.getReader();
		String elencoParametri = new String();
		String thisLine;
		while ((thisLine = in.readLine()) != null) { // while loop begins here
			elencoParametri = elencoParametri + thisLine;
	    } 
		
		HashMap tmpMap = new HashMap();
		
		StringTokenizer st = new StringTokenizer(elencoParametri,"&");
		String tmpStr;
		String key;
		String value;
		int index = 0;
		while (st.hasMoreElements()) {
			tmpStr = (String)st.nextElement();
			index = tmpStr.indexOf("=");
			key = tmpStr.substring(0, index);
			value = tmpStr.substring(index +1);
			tmpMap.put(key, value);
		}
		String param = (String)tmpMap.get("TipoOperazione");
		if (param == null)
			throw new Exception("Impossibile recuperare il valore di TipoOperazione dalla Request");
		richiesta.setTipoOperazione(param);
		
		// recupero url per chiamare il webservices di autorizzazione pagamento
		
		param = (String)tmpMap.get("UrlServizio");
		if (param == null)
			throw new Exception("Impossibile recuperare il valore di UrlServizio dalla Request");
		richiesta.setUrlWebServices(param);
		
		param = (String)tmpMap.get("IdentificativoUnicoVersamento");
		if (param == null)
			throw new Exception("Impossibile recuperare il valore di IdentificativoUnicoVersamento dalla Request");
		richiesta.setIdentificativoUnivocoVersamento(param);
		
		param = (String)tmpMap.get("IdentificativoDominio");
		if (param == null)
			throw new Exception("Impossibile recuperare il valore di IdentificativoDominio dalla Request");
		richiesta.setIdentificativoDominio(param);
		
		param = (String)tmpMap.get("CodiceContestoPagamento");
		if (param == null)
			throw new Exception("Impossibile recuperare il valore di CodiceContestoPagamento dalla Request");
		richiesta.setCodiceContestoPagamento(param);
		
		param = (String)tmpMap.get("ImportoPagamento");
		if (param == null){
			if(richiesta.getTipoOperazione().equals("A"))
				throw new Exception("Impossibile recuperare il valore di ImportoPagamento dalla Request");
		}else{
			richiesta.setImportoPagamento(new BigDecimal(param));
		}
		
		
		/*
		try {
			richiesta.setTipoOperazione(XmlUtils.getChildElement(body, "TipoOperazione").getValue());
			
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di TipoOperazione", e);
		}
		try {
			richiesta.setIdentificativoUnivocoVersamento(XmlUtils.getChildElement(body, "IdentificativoUnicoVersamento").getValue());
			
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di IdentificativoUnicoVersamento", e);
		}
		try {
			richiesta.setIdentificativoDominio(XmlUtils.getChildElement(body, "IdentificativoDominio").getValue());
			
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di IdentificativoDominio", e);
		}
		try {
			richiesta.setCodiceContestoPagamento(XmlUtils.getChildElement(body, "CodiceContestoPagamento").getValue());
			
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di CodiceContestoPagamento", e);
		}
		try {
			String importoPagamentoStr=XmlUtils.getChildElement(body, "ImportoPagamento").getValue();
			richiesta.setImportoPagamento(new BigDecimal(importoPagamentoStr));
			
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di ImportoPagamento", e);
		}
		*/
		return richiesta;
		
		
	}
}
