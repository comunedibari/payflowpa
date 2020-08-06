package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EnumUtils {

	// private static final Log LOGGER = LogFactory.getLog(EnumUtils.class);
	private static final Logger LOGGER = LogManager.getLogger(EnumUtils.class);

	/**
	 * Verifica se esiste una costante enum corrispondente alla chiave
	 * chiaveValue specificata
	 * 
	 * @param <T>
	 * @param chiaveValue
	 * @param e
	 * @return true se esiste, false altrimenti
	 */
	public static <T extends Enum & MessageDescription> boolean existsChiave(String chiaveValue, Class<T> e) {
		return findByMethod(chiaveValue, e, "getChiave") != null;
	}

	/**
	 * Ritorna la costante enum corrispondente del tipo specificato
	 * 
	 * @param <T>
	 * @param chiave
	 * @param e
	 *            la classe del tipo enum
	 * @return la costante enum del tipo specificato
	 */
	public static <T extends Enum & MessageDescription> T byChiave(String chiave, Class<T> e) {
		return byMethod(chiave, e, "getChiave");
	}

	public static <T extends Enum & MessageDescription> T findByChiave(String chiave, Class<T> e) {
		return findByMethod(chiave, e, "getChiave");
	}

	public static <T extends Enum & MessageDescription> boolean matchByChiave(String chiaveValue, T... t) {
		return matchOne(chiaveValue, "getChiave", t);
	}

	public static <T extends Enum> boolean exists(String value, String methodName, Class<T> e) {
		return findByMethod(value, e, "getChiave") != null;
	}

	public static <T extends Enum> boolean matchOne(Object value, String methodName, T... t) {
		boolean contains = false;
		for (int i = 0; !contains && i < t.length; i++) {
			contains = t[i] == findByMethod(value, t[i].getClass(), methodName);
		}
		return contains;
	}

	public static <T extends Enum> T findByMethod(Object value, Class<T> e, String methodName) {
		for (T v : e.getEnumConstants()) {
			try {
				Method method = e.getMethod(methodName);
				if (method.invoke(v).equals(value)) {
					return v;
				}
			} catch (Exception exc) {
				LOGGER.error(exc.getMessage(), exc);
				throw new IllegalArgumentException(value.toString(), exc);
			}

		}
		return null;
	}

	public static <T extends Enum> T byMethod(Object value, Class<T> e, String methodName) {
		T t = findByMethod(value, e, methodName);
		if (null == t) {
			throw new IllegalArgumentException("Illegal value " + (value != null ? value.toString() : value) + ". Possible values : " + toString(e, methodName));
		}
		return t;
	}

	public static <T extends Enum> T byNamedAttr(String attrName, Object value, Class<T> e) {
		for (T v : e.getEnumConstants()) {
			try {
				if (e.getField(attrName).get(e).equals(attrName)) {
					return v;
				}
			} catch (Exception exc) {
				throw new IllegalArgumentException(value.toString(), exc);
			}

		}
		throw new IllegalArgumentException("Illegal value " + value.toString() + ". Possible values : " + toString(e, attrName));
	}

	public static <T extends Enum & MessageDescription> String toString(Class<T> e) {
		return toString(e, "getChiave");
	}

	public static <T extends Enum> String toString(Class<T> e, String methodName) {
		StringBuilder rappr = new StringBuilder();

		try {
			T[] consts = e.getEnumConstants();
			int len = consts.length;
			for (int i = 0; i < len; i++) {
				T v = consts[i];
				Method method = e.getMethod(methodName);
				rappr.append("'").append(method.invoke(v)).append("'").append(i < len - 1 ? "," : "");
			}
		} catch (Exception exc) {
			LOGGER.error(exc.getMessage(), exc);
		}
		return rappr.toString();
	}

	public static <T extends Enum & MessageDescription> List<T> asList(Class<T> e, T... xcludeConstants) {
		T[] consts = e.getEnumConstants();
		List<T> lrem = new ArrayList<T>(Arrays.asList(consts)); // because
																// asList is
																// fixed-size
		lrem.removeAll(Arrays.asList(xcludeConstants));
		return lrem;
	}

}
