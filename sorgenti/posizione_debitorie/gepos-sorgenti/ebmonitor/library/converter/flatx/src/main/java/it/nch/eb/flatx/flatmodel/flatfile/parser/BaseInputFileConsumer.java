/**
 * Created on 18/giu/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.InputFile;


/**
 * @author gdefacci
 */
public class BaseInputFileConsumer implements InputFileConsumer {

	public void consumeUntill(InputFile file, IParser parser, LinesFactoryBuilder lfBuilder) {
		consumeUntillRecursive(file, parser, lfBuilder);
	}

	private InputFile consumeUntillRecursive(InputFile file, IParser parser, LinesFactoryBuilder lfBuilder) {
		if (!file.hasNext()) return null;
		if (parser.match(file, lfBuilder)) {
			return file;
		} else {
			file.nextLine();
			return consumeUntillRecursive(file, parser, lfBuilder);
		}
	}

}
