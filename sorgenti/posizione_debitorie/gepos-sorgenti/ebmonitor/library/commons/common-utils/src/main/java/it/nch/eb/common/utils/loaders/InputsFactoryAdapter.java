/**
 * 28/ago/2009
 */
package it.nch.eb.common.utils.loaders;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;


/**
 * @author gdefacci
 */
public class InputsFactoryAdapter implements InputsFactory {

	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String DEFAULT_NAME = "unnamed inputsFactory";
	
	private final InputStreamFactory inputsFactory;
	private final String charset;
	private String name;
	
	public InputsFactoryAdapter(InputStreamFactory inputsFactory) {
		this(DEFAULT_NAME, inputsFactory, DEFAULT_ENCODING);
	}
	
	public InputsFactoryAdapter(InputStreamFactory inputsFactory, String charset) {
		this(DEFAULT_NAME, inputsFactory, charset);
	}
	
	public InputsFactoryAdapter(String name, InputStreamFactory inputsFactory, String charset) {
		this.inputsFactory = inputsFactory;
		this.charset = charset;
		this.name = name;
	}
	public Reader createReader() {
		try {
			return new InputStreamReader(createStream(), charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	public String getName() {
		return name;
	}
	public InputStream createStream() {
		return inputsFactory.createStream();
	}
	
	
	
	
}
