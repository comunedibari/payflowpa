/**
 * Created on 18/giu/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.InputFile;


/**
 * responsabilities: consume an input file unless the predicate is match
 * @author gdefacci
 */
public interface InputFileConsumer {
	
	void consumeUntill(InputFile file, IParser parser, LinesFactoryBuilder lfBuilder);
}