/**
 * Created on 01/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;


/**
 * @author gdefacci
 */
public interface Parser {
	
	Object createObject(InputFile inputFile, LinesFactoryBuilder lfBuilder, BeanErrorCollector errorCollector);
	String getName();
	
	Class getProductClass();
	
	boolean isOptional();
	
	public boolean match(InputFile inputFile, LinesFactoryBuilder lfBuilder);
	
}
