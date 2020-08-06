/**
 * Created on 08/mag/08
 */
package it.nch.eb.common.converter;

import java.io.Serializable;

import org.antlr.stringtemplate.StringTemplateErrorListener;


/**
 * @author gdefacci
 */
public class ThrowRuntimeExceptionStringTemplateErrorListener 
	implements StringTemplateErrorListener, Serializable {

	private static final long	serialVersionUID	= -7278752667565539946L;

	public void error(String msg, Throwable e) {
		throw new RuntimeException(msg, e);
	}

	public void warning(String msg) {
	}

}
