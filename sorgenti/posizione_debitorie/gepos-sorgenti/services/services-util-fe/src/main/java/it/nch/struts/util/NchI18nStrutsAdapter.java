package it.nch.struts.util;

import it.nch.is.fo.web.FrontEndConstant;
import it.nch.utility.web.WebUtility;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.Globals;
import org.displaytag.localization.I18nStrutsAdapter;

public class NchI18nStrutsAdapter extends I18nStrutsAdapter
{
	public NchI18nStrutsAdapter()
	{}

	public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext)
	{
		Object args[] = {null, null, null, null, null};
		String key = resourceKey == null ? defaultValue : resourceKey;		
		String s_code = (String)pageContext.getRequest().getAttribute(FrontEndConstant.CODICE_SERVIZIO);
		String title = "";
		
		Locale userLocale = resolveLocale((HttpServletRequest)pageContext.getRequest());
		
		String userLocaleKey = Globals.LOCALE_KEY;
		
		try
		{
			//title = WebUtility.getResourceMessage(pageContext, s_code, userLocale.toString(), key, s_code, args);
			//questo metodo necessita del localeKey inteso come attributo di struts 
			title = WebUtility.getResourceMessage(pageContext, s_code, userLocaleKey, key, s_code, args);
		}
		catch(Exception ex)
		{
			title = "?#"+resourceKey+"#?";
			ex.printStackTrace();
		}
		
		return title;
	}
}

