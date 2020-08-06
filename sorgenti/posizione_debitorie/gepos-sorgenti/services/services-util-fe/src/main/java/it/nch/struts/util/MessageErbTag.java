package it.nch.struts.util;
/*
 * Created on 13-feb-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
 
import it.nch.erbweb.constants.CommonConstants;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.is.fo.web.FrontEndConstant;
import it.nch.utility.web.WebUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;
public class MessageErbTag extends TagSupport
{
	public MessageErbTag()
	{
		arg0 = null;
		arg1 = null;
		arg2 = null;
		arg3 = null;
		arg4 = null;
		bundle = null;
		key = null;
		name = null;
		property = null;
		scope = null;
		localeKey = Globals.LOCALE_KEY;
		
		serviziAbilitati = new ArrayList();
		serviziAbilitati.add(CommonConstants.COD_FUN_BONIFICOITALIA);// bonifico
		serviziAbilitati.add(PosizioneDebitoriaConstants.CODICE_SERVIZIO);// posizione debitoria
	}
	public String getArg0()
	{
		return arg0;
	}
	public void setArg0(String arg0)
	{
		this.arg0 = arg0;
	}
	public String getArg1()
	{
		return arg1;
	}
	public void setArg1(String arg1)
	{
		this.arg1 = arg1;
	}
	public String getArg2()
	{
		return arg2;
	}
	public void setArg2(String arg2)
	{
		this.arg2 = arg2;
	}
	public String getArg3()
	{
		return arg3;
	}
	public void setArg3(String arg3)
	{
		this.arg3 = arg3;
	}
	public String getArg4()
	{
		return arg4;
	}
	public void setArg4(String arg4)
	{
		this.arg4 = arg4;
	}
	public String getBundle()
	{
		return bundle;
	}
	public void setBundle(String bundle)
	{
		this.bundle = bundle;
	}
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getProperty()
	{
		return property;
	}
	public void setProperty(String property)
	{
		this.property = property;
	}
	public String getScope()
	{
		return scope;
	}
	public void setScope(String scope)
	{
		this.scope = scope;
	}
	public String getLocale()
	{
		return localeKey;
	}
	public void setLocale(String localeKey)
	{
		this.localeKey = localeKey;
	}
	public int doStartTag()	throws JspException
	{
		String key = this.key;
		
		String s_code = (String)super.pageContext.getRequest().getAttribute(FrontEndConstant.CODICE_SERVIZIO);
		if(key == null)
		{
			Object value = TagUtils.getInstance().lookup(super.pageContext, name, property, scope);
			if(value != null && !(value instanceof String))
			{
				value = "";
			}
			key = (String)value;
		}
		Object args[] = {
			arg0, arg1, arg2, arg3, arg4
		};
		String message = null;
		if(s_code!=null)// && serviziAbilitati.contains(s_code))
		{
			//  nuova logica
			message = WebUtility.getResourceMessage(super.pageContext, bundle, localeKey, key, s_code, args);
		}
		else
		{
			message = WebUtility.getResourceMessage(super.pageContext, bundle, localeKey, key, null, args);
		}
		
		TagUtils.getInstance().write(pageContext, message);
		return 0;

	}
	public void release()
	{
		super.release();
		arg0 = null;
		arg1 = null;
		arg2 = null;
		arg3 = null;
		arg4 = null;
		bundle = Globals.MESSAGES_KEY;
		key = null;
		name = null;
		property = null;
		scope = null;
		localeKey = Globals.LOCALE_KEY;
	}
	protected String arg0;
	protected String arg1;
	protected String arg2;
	protected String arg3;
	protected String arg4;
	protected String bundle;
	/**
	 * @deprecated Field defaultLocale is deprecated
	 */
	protected static final Locale defaultLocale = Locale.getDefault();
	protected String key;
	protected String name;
	protected String property;
	protected String scope;
	protected String localeKey;
	protected List serviziAbilitati = null;
	protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.bean.LocalStrings");
}
