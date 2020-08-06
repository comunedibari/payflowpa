/**
 * Created on 23/apr/08
 */
package it.nch.eb.flatx.records.generator;

import java.io.File;

import it.nch.eb.common.converter.BodyRecordsNumberProvider;
import it.nch.eb.common.converter.RecordCountIncTrigger;
import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.flatfile.parser.ParserDsl;
import it.nch.eb.flatx.generator.InProjectGenerator;
import it.nch.eb.flatx.records.ClassesGenerator;

/**
 * @author gdefacci
 */
public class GeneratePmtReqModels {

	static final String		packageName			= "it.nch.eb.common.converter.pmtreq.models.pippo";
//	static final String		packageName			= "it.nch.eb.common.converter.pmtreq.models.gen";
	
//	static final File		sourceFolderPath	= "D:/java/projects/flattener/conversions-project/src/java";
	static final File		sourceFolderPath	= InProjectGenerator.instance.folder("src/java");

	static final IRecord	recordCodaBody = new it.nch.eb.common.converter.pmtreq.records.RecordCodaBody();
	static final IRecord	recordTestaBody = new it.nch.eb.common.converter.pmtreq.records.RecordTestaBody();
	
	static final IRecord[]	records				= new IRecord[] {
		new it.nch.eb.common.converter.pmtreq.records.Record00(),
		new it.nch.eb.common.converter.pmtreq.records.Record01(),
		new it.nch.eb.common.converter.pmtreq.records.Record11(),
		new it.nch.eb.common.converter.pmtreq.records.Record20(),
		new it.nch.eb.common.converter.pmtreq.records.Record30(),
		new it.nch.eb.common.converter.pmtreq.records.Record40(),
		new it.nch.eb.common.converter.pmtreq.records.Record50(),
		new it.nch.eb.common.converter.pmtreq.records.Record60(),
		new it.nch.eb.common.converter.pmtreq.records.Record65(),
		new it.nch.eb.common.converter.pmtreq.records.Record70(),
		new it.nch.eb.common.converter.pmtreq.records.Record80(),
		new it.nch.eb.common.converter.pmtreq.records.RecordFI(),
	};
	
	static ParserDsl parserDsl = new ParserDsl();
	
	static final PmtreqParsersFactory parser = new PmtreqParsersFactory(); 

	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolderPath, packageName);
		generator.generateAll(records, new Class[] {
			RecordCountProvider.class,
			RecordCountIncTrigger.class,
			FilePositionProvider.class,
		});
		generator.generateFromRecord(recordTestaBody, new Class[] {
			RecordCountProvider.class,
			BodyRecordsNumberProvider.class,
			RecordCountIncTrigger.class,
			FilePositionProvider.class,
		});
		generator.generateFromRecord(recordCodaBody, new Class[] {
			RecordCountProvider.class,
			BodyRecordsNumberProvider.class,
			RecordCountIncTrigger.class,
			FilePositionProvider.class,
		});
		
		generator.generateFromParsers(PmtreqParsersFactory.ModelsNames.PMTREC_BODY_ITEM, parser.createBodyItemParsers());
		generator.generateFromParsers(PmtreqParsersFactory.ModelsNames.PMTREC_BODY, parser.createBodyParsers());
		generator.generateFromParsers(PmtreqParsersFactory.ModelsNames.PMTREC, parser.createMainIParsers());
	}

}
