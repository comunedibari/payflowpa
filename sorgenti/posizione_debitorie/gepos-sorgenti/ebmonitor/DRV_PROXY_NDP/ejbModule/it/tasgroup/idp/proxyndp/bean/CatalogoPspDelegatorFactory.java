package it.tasgroup.idp.proxyndp.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta;

public class CatalogoPspDelegatorFactory {

	private Object catalogoPspContent = null;
	private final Log logger = LogFactory.getLog(this.getClass());

	public ICatalogoPspDelegator createCatalogoPsp(NodoChiediInformativaPSPRisposta risposta) throws Exception {

		CatalogoPspV1012MYBKDelegator V1012mybk = new CatalogoPspV1012MYBKDelegator();
		CatalogoPspV1012Delegator V1012 = new CatalogoPspV1012Delegator();

		try {
			catalogoPspContent = V1012mybk.readResult(risposta);
			if (catalogoPspContent != null)
				return V1012mybk;
		} catch (Exception e) {
			logger.warn("CatalogoPspV1010Delegator V1012mybk " + e);
		}
		try {
			catalogoPspContent = V1012.readResult(risposta);
			if (catalogoPspContent != null)
				return V1012;
		} catch (Exception e) {
			logger.error("CatalogoPspV1010Delegator V1012 " + e);
			throw new Exception("Formato non corretto");
		}
		return null;
	}

	public Object getCatalogoPspContent() {
		return catalogoPspContent;
	}

}
