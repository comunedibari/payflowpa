/**
 * Created on 16/ott/08
 */
package it.nch.eb.flatx.flatmodel;

import java.io.Writer;

import it.nch.eb.common.utils.loaders.ReaderFactory;


/**
 * @author gdefacci
 */
public interface MultiPassConversionService {
	
	ConversionService getConversionService();
	void convert(ReaderFactory readerFactory, Writer wr);

}
