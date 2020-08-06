/*
 * Created on 12-gen-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.utility;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Amministratore
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AttributeValueType implements Serializable {
	
	protected static final String NUMBER = "number";
	protected static final String STRING = "string";
	protected static final String DATE = "date";
	protected static final String DATETIME = "datetime";
	protected static final String UNDEFINED = "undefined";

	private String type=UNDEFINED;
	private String attributeName;
	private Object value;
	private boolean isNull=false;
	
	
	public AttributeValueType() {
		
		}

	public AttributeValueType(String type, Object value, String attributeName) {
		this.type = type;
		this.value = value;
		this.attributeName = attributeName;
	}
	
	public boolean isNumero() {
		return NUMBER.equals(type);
	}
	
	public boolean isString() {
			return type.startsWith(STRING);
	}
	
	public boolean isDate() {
			return DATE.equals(type);
	}
	
	public boolean isDateTime() {
			return DATETIME.equals(type);
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setType(String string) {
		type = string;
	}

	/**
	 * @param object
	 */
	public void setValue(Object object) {
		value = object;
	}

	/**
	 * @return
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param string
	 */
	public void setAttributeName(String string) {
		attributeName = string;
	}

	/**
	 * @return
	 */
	public boolean isNull() {
		return isNull;
	}

	/**
	 * @param b
	 */
	public void setNull(boolean b) {
		isNull = b;
	}
	
	public static void addDateElement(Vector a, Object el){
					if (el == null)
					{
						AttributeValueType r= new AttributeValueType();
						r.setNull(true);
						r.setType(DATE);
						a.add(r);
					}else {
						a.add(el);
					}
					}
	public static void addDateTimeElement(Vector a, Object el){
						if (el == null)
						{
							AttributeValueType r= new AttributeValueType();
							r.setNull(true);
							r.setType(DATETIME);
							a.add(r);
						}else {
							a.add(el);
						}
						}
	public static void addNumericElement(Vector a, Number el){
							if (el == null)
							{
								AttributeValueType r= new AttributeValueType();
								r.setNull(true);
								r.setType(NUMBER);
								a.add(r);
							}else {
								a.add(el);
							}
							}
	public static void addStringElement(Vector a, Object el){
								if (el == null)
								{
									AttributeValueType r= new AttributeValueType();
									r.setNull(true);
									r.setType(STRING);
									a.add(r);
								}else {
									a.add(el.toString());
								}
								}
	public static void addAttributeStringElement(Vector a, Object el,String attributeName){
									if (el == null)
									{
										AttributeValueType r= new AttributeValueType();
										r.setNull(true);
										r.setType(STRING);
										r.setAttributeName(attributeName);
																	
										a.add(r);
									}else {
										AttributeValueType r= new AttributeValueType();
										r.setAttributeName(attributeName);
										r.setType(STRING);
										r.setValue(el.toString());
										a.add(r);
									}
									}

}
