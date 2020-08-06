/**
 * Created on 06/giu/08
 */
package it.nch.eb.common.converter;

import java.io.Reader;


/**
 * @author gdefacci
 */
public class EitherReaderOrString {
	
	private final Reader reader;
	private final String string;
	
	public EitherReaderOrString(Reader reader) {
		this.reader = reader;
		this.string = null;
	}

	public EitherReaderOrString(String string) {
		this.reader = null;
		this.string = string;
	}

	public Reader getReader() {
		return reader;
	}
	
	public String getString() {
		return string;
	}
	
	public boolean isReader() {return this.reader!=null;}
	public boolean isString() {return this.string!=null;}
	

}
