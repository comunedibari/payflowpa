package it.nch.is.fo.business.core.dao;

import it.nch.fwk.fo.util.Tracer;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.engine.TypedValue;
import org.hibernate.engine.query.HQLQueryPlan;
import org.hibernate.hql.ParameterTranslations;
import org.hibernate.hql.QueryTranslator;
import org.hibernate.impl.AbstractQueryImpl;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.type.Type;

public class TempHibernateQueryLogger {

	public void debugSQL(final SessionFactory sf, final Query query) {
		String hqlQueryString = query.getQueryString();
		// hack - to get to the actual queries..
		SessionFactoryImpl sfimpl = (SessionFactoryImpl) sf;
		HQLQueryPlan plan = new HQLQueryPlan(hqlQueryString, false,
				Collections.EMPTY_MAP, sfimpl);

		StringBuffer str = new StringBuffer();

		// output the hql
		str.append("\n/*---------- hql ---------- */\n");
		str.append(hqlQueryString);
		str.append("\n\n");

		// get parameter values
		Map parameterValues = getParameterValues(query);

		QueryTranslator[] translators = plan.getTranslators();
		for (int i = 0; i < translators.length; i++) {
			QueryTranslator translator = translators[i];

			// get parameter location (in sql)
			Map parameters = getParameters(translator, parameterValues);

			String sqlString = getSqlString(translator, parameters);
			str.append("/*---------- sql ---------- */\n");
			str.append(sqlString);
			str.append("\n\n");

			String returnTypeString = getReturnTypeString(translator);
			str.append("/*---------- returns ---------- */\n");
			str.append(returnTypeString);
			str.append("\n\n");

			String parameterString = getParametersString(parameters);
			str.append("/*---------- parameters ---------- */\n");
			str.append(parameterString);
			str.append("\n\n");

			str.append("\n\n");
		}

		Tracer.info(this.getClass().getName(), "debugSQL", str.toString());
	}

	/**
	 * Returns a string of all the query's parameters. The format is: name
	 * [type]="value"
	 * 
	 * @param parameters
	 * @return
	 */
	private String getParametersString(Map parameters) {
		StringBuffer str = new StringBuffer();
		for (Iterator iter = parameters.values().iterator(); iter.hasNext();) {
			ParameterInfo paramInfo = (ParameterInfo) iter.next();
			str.append(paramInfo.getName());
			if (paramInfo.getType() != null) {
				str.append(" [" + paramInfo.getType().getName() + "]");
			}

			str.append("=");

			if (paramInfo.getValue() != null) {
				str.append("\"" + paramInfo.getValue() + "\"");
			} else {
				str.append("null");
			}
			str.append("\n");
		}
		return str.toString();
	}

	/**
	 * Returns a string of the queries return types.
	 * 
	 * @param translator
	 * @return
	 */
	private String getReturnTypeString(QueryTranslator translator) {
		StringBuffer str = new StringBuffer();
		if (!translator.isManipulationStatement()) {
			Type[] returnTypes = translator.getReturnTypes();
			for (int j = 0; j < returnTypes.length; j++) {
				Type returnType = returnTypes[j];
				str.append(returnType.getName());
				if (j < returnTypes.length - 1) {
					str.append(", ");
				}
			}
		}
		return str.toString();
	}

	/**
	 * Returns the formatted sql query (including the named parameters)
	 * 
	 * @param translator
	 * @param parameters
	 * @return
	 */
	private String getSqlString(QueryTranslator translator, Map parameters) {
		Map paramLocationToName = new TreeMap();
		for (Iterator iter = parameters.values().iterator(); iter.hasNext();) {
			ParameterInfo paramInfo = (ParameterInfo) iter.next();
			for (int j = 0; j < paramInfo.locations.length; j++) {
				paramLocationToName.put(new Integer(paramInfo.locations[j]),
						paramInfo.name);
			}
		}
		String sql = translator.getSQLString();
		StringBuffer sqlBuffer = new StringBuffer(sql);
		for (Iterator iter = paramLocationToName.values().iterator(); iter
				.hasNext();) {
			String paramName = (String) iter.next();
			int index = sqlBuffer.indexOf("?");

			if (index != -1) {
				sqlBuffer.replace(index, index + 1, ":" + paramName);
			}
		}
		String sqlWithNamedParams = StringUtils.remove(sqlBuffer.toString(),"?");

		//sqlWithNamedParams = new Formatter(sqlWithNamedParams).setIndentString("   ").setInitialString("").format();
		

		return sqlWithNamedParams;
	}

	/**
	 * Returns the parameter information, including: the name, the locations in
	 * the query and the value
	 * 
	 * @param translator
	 * @param parameterValues
	 * @return
	 */
	private Map getParameters(QueryTranslator translator, Map parameterValues) {
		Map parameters = new TreeMap();
		ParameterTranslations parameterTranslations = translator
				.getParameterTranslations();
		for (Iterator iter = parameterTranslations.getNamedParameterNames()
				.iterator(); iter.hasNext();) {
			ParameterInfo paramInfo = new ParameterInfo();
			paramInfo.setName((String) iter.next());
			paramInfo.setLocations(parameterTranslations
					.getNamedParameterSqlLocations(paramInfo.getName()));
			paramInfo.setType(parameterTranslations
					.getNamedParameterExpectedType(paramInfo.getName()));

			parameters.put(paramInfo.getName(), paramInfo);
		}

		for (Iterator iter = parameters.values().iterator(); iter.hasNext();) {
			ParameterInfo paramInfo = (ParameterInfo) iter.next();
			TypedValue typeValue = (TypedValue) parameterValues
					.get(paramInfo.name);
			if (typeValue != null) {
				paramInfo.setValue(typeValue.getValue());
			}
		}

		return parameters;
	}

	/**
	 * Access the parameter values bound to the Query.
	 * 
	 * @param query
	 * @return
	 */
	private Map getParameterValues(Query query) {
		try {
			// Wordaround: The only way to access the parameter values is to
			// call a protected method.
			
			Map parameters = null;
			Method m = AbstractQueryImpl.class.getDeclaredMethod(
					"getNamedParams", new Class[0]);
			if (m != null) {
				m.setAccessible(true);
				parameters = (Map) m.invoke(query, new Object[0]);
			}
			
			Map parametersList = null;
			Method mList = AbstractQueryImpl.class.getDeclaredMethod(
					"getNamedParameterLists", new Class[0]);
			if (mList != null) {
				mList.setAccessible(true);
				parametersList = (Map) mList.invoke(query, new Object[0]);
			}
			parameters.putAll(parametersList);
			return parameters;
		} catch (Exception e) {
			Tracer.info(this.getClass().getName(), "getParameterValues",
					"Unable to access parameter values.", e);
		}
		return Collections.EMPTY_MAP;
	}

	private class ParameterInfo {
		private int[] locations;

		private String name;

		private Type type;

		private Object value;

		public int[] getLocations() {
			return locations;
		}

		public void setLocations(int[] locations) {
			this.locations = locations;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}
