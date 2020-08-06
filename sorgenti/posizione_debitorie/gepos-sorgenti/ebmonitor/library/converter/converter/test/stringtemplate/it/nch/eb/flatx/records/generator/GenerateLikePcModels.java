/**
 * Created on 23/apr/08
 */
package it.nch.eb.flatx.records.generator;

import it.nch.eb.common.converter.BodyRecordsNumberProvider;
import it.nch.eb.common.converter.RecordCountIncTrigger;
import it.nch.eb.common.converter.pmtreq.likepc.parser.LikePcParsersFactroy;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.records.ClassesGenerator;

/**
 * @author gdefacci
 */
public class GenerateLikePcModels {

	static final String		packageName			= "it.nch.eb.common.converter.pmtreq.likepc.models";
	static final String		sourceFolderPath	= "D:/java/projects/flattener/conversions-project/src/java";

	static final IRecord[]	records				= new IRecord[] {
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record10(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record16(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record17(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record20(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record30(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record40(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record50(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record60(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.Record70(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.RecordXF1(),
			new it.nch.eb.common.converter.pmtreq.likepc.records.RecordXF2(), };
	
	static final IRecord[]	recordPc	= new IRecord[] {
		new it.nch.eb.common.converter.pmtreq.likepc.records.RecordPC(),		
	};
	
	static final IRecord[]	recordEf	= new IRecord[] {
		new it.nch.eb.common.converter.pmtreq.likepc.records.RecordEF(),
	};
	
	public static void main(String[] args) {
		LikePcParsersFactroy pf = new LikePcParsersFactroy();
		
		ClassesGenerator generator = new ClassesGenerator(sourceFolderPath, packageName);
		generator.generateAll(records, new Class[] {
				FilePositionProvider.class,
				RecordCountIncTrigger.class,
			});
		
		generator.generateAll(recordEf, new Class[] {
				FilePositionProvider.class,
				BodyRecordsNumberProvider.class,
			});
		
		generator.generateAll(recordPc, new Class[] {
				FilePositionProvider.class,
			});
		
		generator.generateFromParsers(LikePcParsersFactroy.SNames.BODY, pf.createBodyParsers());
		generator.generateFromParsers(LikePcParsersFactroy.SNames.MAIN, pf.createMainParsers());
	}

}
