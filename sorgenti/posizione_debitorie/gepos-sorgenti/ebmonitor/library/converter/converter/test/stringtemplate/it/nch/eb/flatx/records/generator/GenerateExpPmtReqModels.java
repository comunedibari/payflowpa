/**
 * Created on 23/apr/08
 */
package it.nch.eb.flatx.records.generator;

import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.converter.common.records.RecordCoda;
import it.nch.eb.common.converter.common.records.RecordTesta;
import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.flatfile.parser.ParserDsl;
import it.nch.eb.flatx.records.ClassesGenerator;

/**
 * @author gdefacci
 */
public class GenerateExpPmtReqModels {

//	static final String		packageName			= "it.nch.eb.common.converter.pmtreq.models";
	static final String		packageName			= "it.nch.flatfile.xls.generate.models";
	static final String		sourceFolderPath	= "D:/java/projects/flattener/conversions-project/test/stringtemplate";

	static final IRecord[]	records				= new IRecord[] {
		new it.nch.eb.common.converter.pmtreq.records.Record00(),
		new it.nch.eb.common.converter.pmtreq.records.Record01(),
		new it.nch.eb.common.converter.pmtreq.records.Record20(),
		new it.nch.eb.common.converter.pmtreq.records.Record30(),
		new it.nch.eb.common.converter.pmtreq.records.Record40(),
		new it.nch.eb.common.converter.pmtreq.records.Record50(),
		new it.nch.eb.common.converter.pmtreq.records.Record65(),
		new it.nch.eb.common.converter.pmtreq.records.Record70(),
		new it.nch.eb.common.converter.pmtreq.records.Record80(),
		new it.nch.eb.common.converter.pmtreq.records.RecordCodaBody(),
		new it.nch.eb.common.converter.pmtreq.records.RecordFI(),
		new it.nch.eb.common.converter.pmtreq.records.RecordTestaBody(),
		
	};
	
	static final IRecord[]	borderRecords				= new IRecord[] {
		new RecordTesta(),
		new RecordCoda(),		
	};
	
	static ParserDsl parserDsl = new ParserDsl();
	
	static final PmtreqParsersFactory parser = new PmtreqParsersFactory(); 

	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolderPath, packageName);
		generator.generateAll(records, new Class[] {
			RecordCountProvider.class,
			FilePositionProvider.class,
		});
		generator.generateAll(borderRecords);
		generator.generateFromParsers(PmtreqParsersFactory.ModelsNames.PMTREC_BODY, parser.createBodyItemParsers());
		generator.generateFromParsers(PmtreqParsersFactory.ModelsNames.PMTREC, parser.createMainIParsers());
	}

}
