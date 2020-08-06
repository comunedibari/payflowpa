package it.tasgroup.addon.api.manager.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class BaseViewHelper {

	protected Logger logger = LogManager.getLogger(this.getClass());

	/* predictable iteration order impl */
	private Map<String, String> properties = new LinkedHashMap<String, String>();
	private ResourceBundle bundle;

	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private DecimalFormat decimalFormatter = new DecimalFormat("#0.00");
	
	 

	public static ResourceBundle getMessages(Locale locale) {
		return ResourceBundle.getBundle("messages.public.PluginMessages", locale);
	}

	protected final void setLocale(Locale locale) {
		bundle = getMessages(locale);
	}

	protected final Map<String, String> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	protected final void addProperty(String i18nKey, String mlValue) {
		properties.put(bundle.getString(i18nKey), mlValue);
	}
	
	protected final String getLabelProperty(String i18nKey) {
		return bundle.getString(i18nKey);
	}

	protected final void addReadyToDisplayProperty(String name, String value) {
		properties.put(name, value);
	}

	protected String formatDate(Date date) {
		if (date != null) {
			return dateFormatter.format(date);
		} else {
			return null;
		}
	}

	protected String formatImporto(BigDecimal importo) {
		if (importo != null) {
			return decimalFormatter.format(importo);
		} else {
			return null;
		}
	}

	protected String formatNumero(Integer importo) {
		if (importo != null) {
			return importo.toString();
		} else {
			return null;
		}
	}
	
	public Map<String, String> extractViewDetails(String causale, Locale locale) {
		return null;
	}
	
	public Map<String, String> extractReceiptDetails(String causale, Locale locale) {
		return null;
	}


}
