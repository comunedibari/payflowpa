/*
 * Creato il 13-mar-2009
 *
 */
package it.tasgroup.dse.service;

import it.nch.eb.common.utils.loaders.InputStreamFactory;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.tasgroup.dse.exception.ReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;

/**
 * @author agostino
 *
 */
public class HandlerReaderFactory implements it.nch.eb.common.utils.loaders.InputsFactory {

	/** encoding used when the encoding has not been specified */
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private static Logger log = ResourcesUtil.createLogger(HandlerReaderFactory.class);
	private final InputStreamFactory datInput;
	private final String charset;
	
	public HandlerReaderFactory(InputStreamFactory dtInput, String charset) {
		super();
		this.datInput = dtInput;
		this.charset = charset;
	}
	
	public String getName() {
		return datInput.toString();
	}

	public Reader createReader() {
		try {
			return getReader();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param datInput
	 * @return
	 * @throws IOException
	 */
	public Reader getReader() throws ReaderException, IOException{
		if (charset!=null) {
			return new InputStreamReader( createStream(), charset );
		} else {
			return new InputStreamReader( createStream(), DEFAULT_ENCODING );
		}
	}
	
	public InputStream createStream() {
		log.debug("HandlerReaderFactory.getReader[INIT]");
		InputStream out = null;
		
		try {
			if(datInput!=null){
				return datInput.createStream();
			} else {
				log.error("HandlerReaderFactory.getReader - DataInput nullo");
			}
		} catch (Exception e){
			log.error("HandlerReaderFactory.getReader - Exception - ", e);
			throw new RuntimeException(e);
		}
		
		log.debug("HandlerReaderFactory.getReader[END]");
		return out;
	}

	
}
