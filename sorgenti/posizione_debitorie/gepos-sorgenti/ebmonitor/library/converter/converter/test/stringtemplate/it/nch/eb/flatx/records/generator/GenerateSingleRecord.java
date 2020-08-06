/**
 * Created on 22/apr/08
 */
package it.nch.eb.flatx.records.generator;

import it.nch.eb.common.converter.TotalRecordsNumberProvider;
import it.nch.eb.common.converter.common.records.RecordCoda;
import it.nch.eb.common.converter.common.records.RecordTesta;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.records.ClassesGenerator;


/**
 * @author gdefacci
 */
public class GenerateSingleRecord {

	static final String packageName = "it.nch.eb.common.converter.common.models";
	static final String sourceFolderPath = "D:/java/projects/flattener/conversions-project/src/java";
	
	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolderPath, packageName);
		generator.generateFromRecord(new RecordTesta(), new Class[] {
//			RecordCountIncTrigger.class,
			TotalRecordsNumberProvider.class,
			FilePositionProvider.class,
		});
		generator.generateFromRecord(new RecordCoda(), new Class[] {
//			RecordCountIncTrigger.class,
			TotalRecordsNumberProvider.class,
			FilePositionProvider.class,
		});	
	}
	
	
}
