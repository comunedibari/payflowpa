package it.nch.idp.util;

import java.util.Locale;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.api.manager.helper.AddOnViewHelper;
import it.tasgroup.addon.api.manager.helper.BaseViewHelper;
import it.tasgroup.addon.internal.AddOnManagerFactory;

public class PluginViewUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(PluginViewUtil.class);

	public static boolean exists_plugin(String cdPlugin) {
		return AddOnManagerFactory.exists(cdPlugin);
	}

	public static <T extends TributoStrutturato> Map<String, String> getDetails(T tributo, String cdTrbEnte, Locale locale) {
		if (tributo != null) {
			AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(tributo.getDettaglioStrutturato().getIdEnte(), cdTrbEnte, tributo.getTipoTributo()); // per es CONFERIMENTO_DISCARICA
			AddOnViewHelper<T> viewHelper = manager.getViewHelper();
			Map<String, String> viewDetailsMap = viewHelper.extractViewDetails(tributo, locale); // per es Locale.ITALY
			return viewDetailsMap;
		}
		return null;
	}
	
	public static <T extends TributoStrutturato> Map<String, String> getDetails(String causale, String idEnte, String cdTrbEnte, String tipoTributo, Locale locale) {
		AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(idEnte, cdTrbEnte, tipoTributo); 
		AddOnViewHelper<T> viewHelper = manager.getViewHelper();
		Map<String, String> viewDetailsMap = ((BaseViewHelper)viewHelper).extractViewDetails(causale, locale); // per es Locale.ITALY
		return viewDetailsMap;
	}

	public static <T extends TributoStrutturato> String getCausale(String properties, String idEnte, String cdTributo, String cdPlugin) {
		if (cdTributo != null && !cdTributo.equals("")) {
			AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(idEnte, cdTributo, cdPlugin); // per es CONFERIMENTO_DISCARICA
			AddOnViewHelper<T> viewHelper = manager.getViewHelper();
			String viewCausale = properties;
			try {
				viewCausale = viewHelper.getCausale(properties);
			} catch (Throwable t) {
				LOGGER.warn("Impossibile ricostruire la causale", t);
			}
			return viewCausale;
		}
		return null;
	}

}
