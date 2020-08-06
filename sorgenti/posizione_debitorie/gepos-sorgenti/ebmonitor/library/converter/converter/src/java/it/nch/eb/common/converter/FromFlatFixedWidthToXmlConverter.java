/**
 * Created on 03/giu/08
 */
package it.nch.eb.common.converter;

import java.io.Reader;

import it.nch.eb.flatx.files.model.FileCaches;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory;

import org.antlr.stringtemplate.StringTemplateErrorListener;


/**
 * @author gdefacci
 */
public class FromFlatFixedWidthToXmlConverter extends FromFlatToXmlConverter {

	private static final long	serialVersionUID	= -3511092026361660845L;

	public static final int	DEFAULT_LINESIZE = 120;
	
	private int	lineSize;
	
	public FromFlatFixedWidthToXmlConverter(String templateClasspathLocation, 
			ParsersFactory parsersFactory, boolean strictMode, int lineSize, StringTemplateErrorListener errorListener) {
//		super(templateClasspathLocation, parsersFactory, strictMode, errorListener);
		super.setTemplateClasspathLocation(templateClasspathLocation);
		super.setParsersFactory(parsersFactory);
		super.setStrict(strictMode);
		super.setErrorListener(errorListener);
		this.lineSize = lineSize;
	}

	public FromFlatFixedWidthToXmlConverter(String templateClasspathLocation, 
			ParsersFactory parsersFactory, boolean strictMode, StringTemplateErrorListener errorListener) {
		this(templateClasspathLocation, parsersFactory, strictMode, DEFAULT_LINESIZE, errorListener);
	}

	public FromFlatFixedWidthToXmlConverter(String templateClasspathLocation, ParsersFactory parsersFactory, boolean strictMode) {
		this(templateClasspathLocation, parsersFactory, strictMode, null);
	}
	
	public FromFlatFixedWidthToXmlConverter(String templateClasspathLocation, ParsersFactory parsersFactory) {
		this(templateClasspathLocation, parsersFactory, true);
	}
	
	protected InputFile createInputFile(Reader input) {
		return new InputFileImpl(input, FileCaches.sharedInstance.fixedWidth(lineSize));
	}
	
}
