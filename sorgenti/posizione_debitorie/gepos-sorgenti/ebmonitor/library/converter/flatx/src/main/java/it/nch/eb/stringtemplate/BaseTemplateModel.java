/**
 * Created on 30/apr/08
 */
package it.nch.eb.stringtemplate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * @author gdefacci
 */
public class BaseTemplateModel implements TemplateModel, Cloneable, Serializable {

	private static final long	serialVersionUID	= 1L;

	private final Map map = new HashMap();
	
	public BaseTemplateModel() {
	}
	
	public BaseTemplateModel(TemplateModel other) {
		for (Iterator ir = other.names(); ir.hasNext();) {
			String name = (String) ir.next();
			set(name, other.get(name));
		}
	}
	
	public Object get(String name) {
		return map.get(name);
	}

	public Iterator names() {
		return map.keySet().iterator();
	}

	public void set(String name, Object obj) {
		if (map.put(name, obj)!=null) {
			throw new IllegalStateException("entry named " + name + " has already been inserted");
		}
	}
	
	protected Object clone() {
		TemplateModel res = new BaseTemplateModel();
		for (Iterator ir = names(); ir.hasNext();) {
			String name = (String) ir.next();
			res.set(name, get(name));
		}
		return res;
	}
	
	public BaseTemplateModel cloned() {
		return (BaseTemplateModel) clone();
	}


	public static final TemplateModel EMPTY = new EmptyTemplateModel();

	private static class EmptyTemplateModel implements TemplateModel, Serializable{

		private static final long	serialVersionUID	= -5589276600405884086L;

		public Object get(String name) {
			return null;
		}

		public Iterator names() {
			return EMPTY_ITERATOR;
		}

		public void set(String name, Object obj) {
			throw new UnsupportedOperationException("empty template model");
		}
	}
	
	private static final Iterator	EMPTY_ITERATOR	= new Iterator() {

		public boolean hasNext() {
			return false;
		}

		public Object next() {
			throw new NoSuchElementException();
		}

		public void remove() {
			throw new UnsupportedOperationException("empty iterator");
		}
		
	};
}
