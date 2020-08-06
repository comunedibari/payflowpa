/**
 * Created on 11/mar/08
 */
package it.nch.eb.flatx.exceptions;

import java.lang.reflect.Member;
import java.lang.reflect.Method;


/**
 * @author gdefacci
 */
public class PropertyAccessException extends RuntimeException {
	
	private final String propertyName;
	private final Class propertyType;
	private final Object container;
	private final Object propertyValue;
	private final Member	member;
	private final String	originalMessage;
	
	public PropertyAccessException(String message, Throwable cause) {
		this(message, cause, null, null, null, null);
	}

	public PropertyAccessException(String string, 
			String propertyName, 
			Object propValue,
			Object container) {
		this(string, propertyName, propValue.getClass(), propValue, container, null);		
	}
	
	public PropertyAccessException(String string, Throwable e, String name, Object target) {
		this(string, e, name, null, target);
	}
	
	public PropertyAccessException(String string, Throwable e, String name, Class setterParamClass, Object target) {
		this(string, e, name, setterParamClass, target, null, null);
	}
	
	public PropertyAccessException(String string, Throwable e, String name, Object container, Object propValue) {
		this(string, e, null, name, container, propValue);
	}
	
	public PropertyAccessException(String string, Throwable e, Method mthd, String name, Object container, Object propValue) {
		this(string, e, name, propValue.getClass(), container, propValue, null);
	}
	
	public PropertyAccessException(String string, Method mthd, String name, Object container, Object propValue) {
		this(string, name, propValue.getClass(), container, propValue, mthd);
	}
	
	public PropertyAccessException(String string, 
			Throwable e, 
			String propertyName, 
			Class propertyType, 
			Object container, 
			Object propValue,
			Method method) {
		super(string, e);
		this.originalMessage = string;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.container = container;
		this.propertyValue = propValue;
		this.member = method;
	}
	
	public PropertyAccessException(PropertyAccessException pae) {
		super(pae.getOriginalMessage(), pae);
		this.originalMessage = pae.getOriginalMessage();
		this.propertyName = pae.getPropertyName();
		this.propertyType = pae.getPropertyType();
		this.container = pae.getContainer();
		this.propertyValue = pae.getPropertyValue();
		this.member = pae.getMember();
	}
	
	public PropertyAccessException(String string, 
			String propertyName, 
			Class propertyType, 
			Object propValue,
			Object container, 
			Method method) {
		super(string);
		this.originalMessage = string;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.container = container;
		this.propertyValue = propValue;
		this.member = method;
	}
	

//	@Nullable
	public String getPropertyName() {
		return propertyName;
	}

//	@Nullable	
	public Class getPropertyType() {
		return propertyType;
	}

//	@Nullable
	public Object getContainer() {
		return container;
	}
	
//	@Nullable
	public Member getMember() {
		return member;
	}
	
	public String getOriginalMessage() {
		return originalMessage;
	}
	
//	@Nullable
	public Object getPropertyValue() {
		return propertyValue;
	}

	private void appendIfNotNull(StringBuffer sb, Object value, String label) {
		if (value != null) {
			sb.append(label);
			sb.append(value);
		}
	}

	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getMessage());
		appendIfNotNull(sb, propertyName, "\nproperty :");
		appendIfNotNull(sb, member, "\nmember :");
		appendIfNotNull(sb, propertyValue, "\nvalue :");
		appendIfNotNull(sb, propertyType, "\nproperty type :");
		appendIfNotNull(sb, getCause(), "\ncause :");
		appendIfNotNull(sb, container, "\ntarget object :");
		return sb.toString();
	}

	private static final long	serialVersionUID	= 1698005069390419452L;

}
