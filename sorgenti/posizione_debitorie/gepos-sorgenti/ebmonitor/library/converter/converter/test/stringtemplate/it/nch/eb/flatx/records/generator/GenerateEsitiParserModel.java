/**
 * Created on 07/mar/08
 */
package it.nch.eb.flatx.records.generator;

import it.nch.eb.common.converter.BodyRecordsNumberProvider;
import it.nch.eb.common.converter.RecordCountIncTrigger;
import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.converter.TotalRecordsNumberProvider;
import it.nch.eb.common.converter.common.records.RecordCoda;
import it.nch.eb.common.converter.pmtreq.dbtr.parser.PaymentRequestDbtrParsersFactory;
import it.nch.eb.common.converter.pmtreq.dbtr.records.EsitiRecordTesta;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.records.ClassesGenerator;


/**
 * @author gdefacci
 */
public class GenerateEsitiParserModel {
	
	static final String packageName = "it.nch.eb.common.converter.pmtreq.dbtr.models";
	static final String sourceFolderPath = "D:/java/projects/flattener/conversions-project/src/java";
	
	static final IRecord[] borders = new IRecord[] {
		new RecordCoda(),
		new EsitiRecordTesta(),		
	};
	
	static final IRecord[] bodyBorders = new IRecord[] {
		new it.nch.eb.common.converter.pmtreq.dbtr.records.RecordCodaBody(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.RecordTestaBody(), 	
	};
	
	static final IRecord[] records = new IRecord[] { 
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record01(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record20(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record22(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record30(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record32(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record34(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record36(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record40(),
		new it.nch.eb.common.converter.pmtreq.dbtr.records.Record80(),
	};
	
	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolderPath, packageName);
		generator.generateAll(records,  new Class[] {
				RecordCountProvider.class,
				RecordCountIncTrigger.class,
				FilePositionProvider.class,
			});
		
		generator.generateAll(bodyBorders,  new Class[] {
				RecordCountIncTrigger.class,
				BodyRecordsNumberProvider.class,
				RecordCountProvider.class,
				FilePositionProvider.class,
			});
		
		generator.generateAll(borders,  new Class[] {
				TotalRecordsNumberProvider.class,
				FilePositionProvider.class,
			});
		
		
		generator.generateFromParser(PaymentRequestDbtrParsersFactory.Names.DBTR_ENVELOPE, 
				new PaymentRequestDbtrParsersFactory().create_01_80_FileParser());	
	
		generator.generateFromParser(PaymentRequestDbtrParsersFactory.Names.DBTR_TRX_INFO, 
				new PaymentRequestDbtrParsersFactory().create_20_22_FileParser());	
	
		generator.generateFromParser(PaymentRequestDbtrParsersFactory.Names.DBTR_STATUS_INFO, 
				new PaymentRequestDbtrParsersFactory().create_30_80_FileParser());	
		
		generator.generateFromParser(PaymentRequestDbtrParsersFactory.Names.DBTR_PMTREQ, 
				new PaymentRequestDbtrParsersFactory().createParser());	
	}

}
