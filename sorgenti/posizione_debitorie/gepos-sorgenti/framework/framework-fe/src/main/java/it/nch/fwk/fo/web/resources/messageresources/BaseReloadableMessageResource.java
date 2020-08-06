package it.nch.fwk.fo.web.resources.messageresources;

import it.nch.fwk.fo.web.resources.cache.Cache;
import it.nch.fwk.fo.web.resources.cache.CacheManager;
import it.nch.fwk.fo.web.resources.cache.exception.CacheCreateException;
import it.nch.fwk.fo.web.resources.cache.timeout.impl.TimeOutCacheConfiguration;
import it.nch.fwk.fo.web.resources.util.MessageNode;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

public abstract class BaseReloadableMessageResource extends
		PropertyMessageResources {

	private boolean circular;

	private static Log log = LogFactory
			.getLog(BaseReloadableMessageResource.class);

	private static String SUBMESSAGE_START = "$(";

	private static String SUBMESSAGE_END = ")";

	protected static PropertyMessageResourcesFactory factory = null;

	protected static boolean returnNull = false;

	protected static long timeout = 120000L;

	public BaseReloadableMessageResource(
			PropertyMessageResourcesFactory resourceFactory, String config)
			throws MessageResourcesException {
		this(resourceFactory, config, false);
	}

	public BaseReloadableMessageResource(
			PropertyMessageResourcesFactory resourceFactory, String config,
			boolean isNull) throws MessageResourcesException {
		super(resourceFactory, config, isNull);
		factory = resourceFactory;
		returnNull = isNull;
	}

	public MessageResourcesFactory getFactory() {
		return (MessageResourcesFactory) factory;
	}

	public String getMessage(Locale locale, String key, Object args[]) {
		if (locale == null) {
			locale = defaultLocale;
		}

		MessageFormat format = null;
		String formatKey = messageKey(locale, key);

		if (format == null) {
			String formatString = getMessage(locale, key);

			if (formatString == null) {
				return returnNull ? null : ("???" + formatKey + "???");
			}

			format = new MessageFormat(escape(formatString));
			format.setLocale(locale);
		}
		return format.format(args);
	}

	public String getMessage(Locale locale, String key) {
		// Check the cache for it and just return it if it is there
		String message = null;
		if (locale == null) {
			locale = Locale.getDefault();
		}
		// Get a cache for our locale
		try {
			Cache cache = CacheManager.getCache(localeKey(locale));
			if (cache != null) {
				if (log.isTraceEnabled()) {
					log.trace("Getting message from cache for key" + key);
				}
				message = (String) cache.get(key);
				if (log.isTraceEnabled()) {
					log.trace("Message for key " + key + " is " + message);
				}
			}
		} catch (Exception e) {
			log.error("Caught exception in getMessage: "
					+ e.getLocalizedMessage());
		}
		if (message == null) {
			try {
				populateCache(locale, getMessagesAsProperties(locale));
				MessageNode node = new MessageNode(key, null, null);
				message = getMessage(locale, key, node);
			} catch (Exception e) {
				message = null;
			}
		}
		if (message == null && returnNull == false) {
			StringBuffer sb = new StringBuffer("???");
			sb.append(key);
			sb.append("???");
			message = sb.toString();
		}
		return message;
	}

	public String getMessage(Locale locale, String key, MessageNode node) {
		String message = replaceInnerMessages(locale, getLocaleMessage(locale,
				key), node);
		return message;
	}

	public String getLocaleMessage(Locale locale, String key) {
		// This is only here so we don't check the cache for
		// timeout after the initial check. That is there is no timout once
		// we start getting a message.
		String message = null;
		try {
			if (log.isTraceEnabled()) {
				log.trace("getting message from cache in getLocaleMessage");
			}
			Cache cache = CacheManager.getCache(localeKey(locale));
			message = (String) cache.get(key);
			if (log.isTraceEnabled()) {
				log.trace("Message for key " + key + " is " + message);
			}
		} catch (Exception e) {
			message = null;
		}
		// A future expansion is to check the default locale if this is not the
		// default
		return message;
	}

	private void populateCache(Locale locale, Properties props)
			throws CacheCreateException {
		// we now have all the messages for this locale so lets populate a new
		// cache for them.
		Cache cache = null;
		if (log.isTraceEnabled()) {
			log.trace("Populating cache");
		}
		try {
			TimeOutCacheConfiguration tocc = new TimeOutCacheConfiguration();
			tocc.setCacheName(localeKey(locale));
			tocc.setTimeout(timeout);
			cache = CacheManager.createCache(tocc);
		} catch (Exception e) {
			log.error("Unable to create cache: " + e.getLocalizedMessage());
			throw new CacheCreateException(e.getLocalizedMessage());
		}
		Enumeration enumer = props.keys();
		while (enumer.hasMoreElements()) {
			String key = (String) enumer.nextElement();
			String val = props.getProperty(key);
			cache.put(key, val);
		}
	}

	protected synchronized String replaceInnerMessages(Locale locale,
			String message, MessageNode node) {
		String retVal = message;
		circular = false;
		while (circular == false
				&& retVal != null
				&& retVal.indexOf(SUBMESSAGE_START) >= 0
				&& retVal.lastIndexOf(SUBMESSAGE_END) > retVal
						.indexOf(SUBMESSAGE_START)) {
			int index1 = retVal.indexOf(SUBMESSAGE_START);
			int index2 = retVal.indexOf(SUBMESSAGE_END, index1);
			String newKey = retVal.substring(index1 + 2, index2);
			MessageNode childNode = new MessageNode(newKey, node, null);
			if (node.isCircular()) {
				circular = true;
				// return null and let our returnNull value take over
				retVal = null;
				break;
			} else {
				String newVal = getMessage(locale, newKey, childNode);
				if (newVal != null) {
					StringBuffer sb = new StringBuffer(SUBMESSAGE_START);
					sb.append(newKey);
					sb.append(SUBMESSAGE_END);
					retVal = replace(retVal, sb.toString(), newVal);
				}
			}
		}
		return retVal;
	}

	protected String replace(String orig, String sub, String with) {
		int pos = 0;
		int index = orig.indexOf(sub, pos);
		if (index == -1) {
			return orig;
		}
		StringBuffer buf = new StringBuffer(orig.length() + with.length());
		synchronized (buf) {
			do {
				buf.append(orig.substring(pos, index));
				buf.append(with);
				pos = index + sub.length();
			} while ((index = orig.indexOf(sub, pos)) != -1);

			if (pos < orig.length()) {
				buf.append(orig.substring(pos, orig.length()));
			}
			return buf.toString();
		}
	}

	protected abstract void parseConfig(String myConfig)
			throws MessageResourcesException;

	protected abstract Properties getMessagesAsProperties(Locale locale);
}