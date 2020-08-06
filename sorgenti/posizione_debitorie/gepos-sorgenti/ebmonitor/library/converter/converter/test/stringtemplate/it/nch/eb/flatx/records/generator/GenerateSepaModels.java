/**
 * Created on 30/mag/08
 */
package it.nch.eb.flatx.records.generator;

import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.records.ClassesGenerator;
import it.nch.eb.ubi.converter.sepa2000.parser.Sepa2000ParserFactory;


/**
 * @author gdefacci
 */
public class GenerateSepaModels {

	static final String sourceFolderPath = "src/java";
	static final String packageName		 = "it.nch.eb.converter.cbiinftrfreq.models";
	
	static final IRecord[]	records				= new IRecord[] {
		
	};
	
	static Sepa2000ParserFactory parserFactory = new Sepa2000ParserFactory();
	
	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolderPath, packageName);
		generator.generateAll(records,  new Class[] {
				FilePositionProvider.class,
			});
		
	}

}
