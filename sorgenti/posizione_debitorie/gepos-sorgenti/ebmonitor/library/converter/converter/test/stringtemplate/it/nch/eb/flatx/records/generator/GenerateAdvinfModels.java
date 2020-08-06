/**
 * Created on 30/mag/08
 */
package it.nch.eb.flatx.records.generator;

import java.io.File;

import it.nch.eb.common.converter.BodyRecordsNumberProvider;
import it.nch.eb.common.converter.RecordCountIncTrigger;
import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.converter.TotalRecordsNumberProvider;
import it.nch.eb.common.converter.pmtreq.advinf.parser.AdvinfParserFactory;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.generator.InProjectGenerator;
import it.nch.eb.flatx.records.ClassesGenerator;


/**
 * @author gdefacci
 */
public class GenerateAdvinfModels {

	static final File sourceFolder 			= InProjectGenerator.instance.folder("src/java"); 
	static final String packageName			= "it.nch.eb.common.converter.pmtreq.advinf.models";
	
	static final IRecord[]	records				= new IRecord[] {
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record01(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record02(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record03(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record04(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record05(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record06(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record07(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record08(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record09(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record10(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.Record11(),
	};
	
	static final IRecord[]	bodyBorderRecords				= new IRecord[] {
		new it.nch.eb.common.converter.pmtreq.advinf.records.RecordCodaBody(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.RecordTestaBody(),		
	};
	static final IRecord[]	borderRecords				= new IRecord[] {
		new it.nch.eb.common.converter.pmtreq.advinf.records.RecordCoda(),
		new it.nch.eb.common.converter.pmtreq.advinf.records.RecorddiTesta(),		
	};
	
	
	static AdvinfParserFactory parserFactory = new AdvinfParserFactory();
	
	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolder, packageName);
		generator.generateAll(records,  new Class[] {
				RecordCountProvider.class,
				RecordCountIncTrigger.class,
				FilePositionProvider.class,
			});
		generator.generateAll(borderRecords,  new Class[] {
				RecordCountIncTrigger.class,
				TotalRecordsNumberProvider.class,
				FilePositionProvider.class,
			});
		generator.generateAll(bodyBorderRecords,  new Class[] {
				RecordCountIncTrigger.class,
				BodyRecordsNumberProvider.class,
				RecordCountProvider.class,
				FilePositionProvider.class,
			});
		generator.generateFromParser(AdvinfParserFactory.Names.ADVINF_BODY, parserFactory.createBodyParser());
		generator.generateFromParser(AdvinfParserFactory.Names.ADVINF, parserFactory.createParser());
		
	}

}
