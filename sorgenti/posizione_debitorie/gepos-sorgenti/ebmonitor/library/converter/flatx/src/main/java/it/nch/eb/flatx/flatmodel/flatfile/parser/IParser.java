/**
 * Created on 01/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;


/**
 * @author gdefacci
 */
public interface IParser extends IRecordStructure {
	
	/**
	 * create an unitialized container instance
	 */
	Object createObject(InputFile inputFile, LinesFactoryBuilder lfBuilder, BeanErrorCollector errorCollector);
	ObjectBuilder getObjectBuilder();
	String getName();
	boolean isOptional();
	boolean match(InputFile inputFile, LinesFactoryBuilder lfBuilder);
	
}
