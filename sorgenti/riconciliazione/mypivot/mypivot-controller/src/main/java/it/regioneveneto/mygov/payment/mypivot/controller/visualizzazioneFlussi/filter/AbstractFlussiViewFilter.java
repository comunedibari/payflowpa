package it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

public abstract class AbstractFlussiViewFilter {

	public void forceClear(String action) {
		String codIpaEnte = SecurityContext.getEnte().getCodIpaEnte();
		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, action);
		if (filtersMap != null) {
			SecurityContext.setEnteViewFilters(codIpaEnte, action, null);
		}
	}

	protected void setSingleFilter(HttpServletRequest request, String key, Object value, String action) {
		String codIpaEnte = SecurityContext.getEnte().getCodIpaEnte();
		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, action);
		if (filtersMap == null) {
			filtersMap = new HashMap<String, Object>();
		}
		filtersMap.put(key, value);
		SecurityContext.setEnteViewFilters(codIpaEnte, action, filtersMap);
	}
}
