package it.nch.fwk.fo.core;

import it.nch.fwk.fo.util.Tracer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.type.Type;

public class QueryParams implements Cloneable {

	private HashMap params;

	public QueryParams() {
		super();
		params = new HashMap();
	}

	public QueryParams(HashMap params) {
		this.params = params;
	}

	public void put(String paramName, Object paramValue, Type paramType) {
		if (paramValue != null){
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug("QueryParams", "put", "Aggiunto il parametro " + paramName + " [" + paramValue.getClass().getName() + "]:" + paramValue.toString());
		}
		else {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug("QueryParams", "put", "Il parametro " + paramName + " e' null!!");
		}
			
		this.params.put(paramName, new Param(paramValue, paramType));
	}

	public void put(String paramName, Object paramValue) {
		if (paramValue != null){
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug("QueryParams", "put", "Aggiunto il parametro " + paramName + " [" + paramValue.getClass().getName() + "]:" + paramValue.toString());
		}
		else {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug("QueryParams", "put", "Il parametro " + paramName + " e' null!!");
		}
		this.params.put(paramName, new Param(paramValue, null));
	}

	public Object remove(String paramName) {
		if (this.params.containsKey(paramName))
			return ((Param) this.params.remove(paramName)).getParamValue();
		else 
			return null;
	}

	public Set paramSet() {
		return params.keySet();
	}

	public Object getValue(Object key) {
		return ((Param) params.get(key)).getParamValue();
	}

	public Type getType(Object key) {
		return ((Param) params.get(key)).getParamType();
	}

	public String toString() {
		return params.toString();
	}

	public Object clone() throws CloneNotSupportedException {
		QueryParams newObj = new QueryParams((HashMap)params.clone());
		return newObj;
	}

	private class Param {
		private Object paramValue;

		private Type paramType;

		public Param(Object paramValue, Type paramType) {
			super();
			this.paramValue = paramValue;
			this.paramType = paramType;
		}

		public Type getParamType() {
			return paramType;
		}

		public void setParamType(Type paramType) {
			this.paramType = paramType;
		}

		public Object getParamValue() {
			return paramValue;
		}

		public void setParamValue(Object paramValue) {
			this.paramValue = paramValue;
		}

	}

}
