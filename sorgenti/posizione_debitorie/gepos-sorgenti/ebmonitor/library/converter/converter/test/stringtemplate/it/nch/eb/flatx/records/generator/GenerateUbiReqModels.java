/**
 * Created on 23/apr/08
 */
package it.nch.eb.flatx.records.generator;

import it.nch.eb.common.converter.TotalRecordsNumberProvider;
import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.records.ClassesGenerator;
import it.nch.eb.ubi.converter.pmtreq.parser.UbiPmtreqParserFactory;

/**
 * @author gdefacci
 */
public class GenerateUbiReqModels {

	static final String		packageName			= "it.nch.eb.ubi.converter.pmtreq.models";
	static final String		sourceFolderPath	= "D:/java/projects/flattener/conversions-project/src/java";

	static final IRecord[]	records				= new IRecord[] {
		new it.nch.eb.ubi.converter.pmtreq.records.RecorddiTesta(),
	};
	
	static final UbiPmtreqParserFactory parser = new UbiPmtreqParserFactory(); 

	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolderPath, packageName);
		generator.generateAll(records, new Class[] {
				TotalRecordsNumberProvider.class,
				FilePositionProvider.class,
			} );
		generator.generateFromParsers(PmtreqParsersFactory.ModelsNames.PMTREC_BODY, parser.createBodyParsers());
		generator.generateFromParsers(PmtreqParsersFactory.ModelsNames.PMTREC, parser.createMainIParsers());
		
	}

}
