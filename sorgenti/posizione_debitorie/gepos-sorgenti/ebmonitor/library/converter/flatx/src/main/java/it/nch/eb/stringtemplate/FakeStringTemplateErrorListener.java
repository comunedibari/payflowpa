package it.nch.eb.stringtemplate;

import org.antlr.stringtemplate.StringTemplateErrorListener;

/**
 * @author gdefacci
 */
public final class FakeStringTemplateErrorListener implements StringTemplateErrorListener {

	public void error(String msg, Throwable e) {
	}

	public void warning(String msg) {
	}
}