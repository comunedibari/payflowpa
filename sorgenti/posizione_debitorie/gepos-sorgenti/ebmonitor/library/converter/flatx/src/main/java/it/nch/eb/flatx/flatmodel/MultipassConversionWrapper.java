/**
 * Created on 13/feb/2009
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.common.utils.loaders.ReaderFactory;

import java.io.Writer;


/**
 * @author gdefacci
 */
public class MultipassConversionWrapper implements SerializableMultiPassConversionService {

	private static final long	serialVersionUID	= 5740767830000395930L;
	private final ConversionService	conversionService;
	
	public MultipassConversionWrapper(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	public void convert(ReaderFactory readerFactory, Writer wr) {
		conversionService.convert(readerFactory.createReader(), wr);
	}

	public ConversionService getConversionService() {
		return conversionService;
	}

}
