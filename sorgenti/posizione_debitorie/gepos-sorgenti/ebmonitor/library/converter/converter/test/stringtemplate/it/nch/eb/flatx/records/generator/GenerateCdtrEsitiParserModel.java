/**
 * Created on 07/mar/08
 */
package it.nch.eb.flatx.records.generator;

import java.io.File;

import it.nch.eb.common.converter.pmtreq.cdtr.parser.PaymentRequestCdtrParsersFactory;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.generator.InProjectGenerator;
import it.nch.eb.flatx.records.ClassesGenerator;


/**
 * @author gdefacci
 */
public class GenerateCdtrEsitiParserModel {
	
	static final String packageName = "it.nch.eb.common.converter.pmtreq.cdtr.models";
	static final File sourceFolderPath = InProjectGenerator.instance.folder("src/java");
	
	static final IRecord[] records = new IRecord[] { 
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record01(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record30(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record32(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record40(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record60(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record62(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record65(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record66(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record67(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record68(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record69(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record80(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record82(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record84(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.Record86(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.RecordCodaBody(),
		new it.nch.eb.common.converter.pmtreq.cdtr.records.RecordTestaBody(), };
	
	public static void main(String[] args) {
		ClassesGenerator generator = new ClassesGenerator(sourceFolderPath, packageName);
		generator.generateAll(records);
		
		PaymentRequestCdtrParsersFactory pf = new PaymentRequestCdtrParsersFactory();
		
		generator.generateFromParser(PaymentRequestCdtrParsersFactory.Names.CDTR_PMTREC, 
				pf.createParser());		
				
		generator.generateFromParser(PaymentRequestCdtrParsersFactory.Names.CDTR_BODY,
				pf.createParserBody());

		generator.generateFromParser(PaymentRequestCdtrParsersFactory.Names.XML_RECORD_30,
				pf.createParser30s());

		generator.generateFromParser(PaymentRequestCdtrParsersFactory.Names.XML_RECORD_40,
				pf.createParser40s());

		generator.generateFromParser(PaymentRequestCdtrParsersFactory.Names.XML_RECORD_62,
				pf.createParser62s());
		
		generator.generateFromParser(PaymentRequestCdtrParsersFactory.Names.XML_RECORD_66,
				pf.createParser66s());

		generator.generateFromParser(PaymentRequestCdtrParsersFactory.Names.XML_RECORD_68,
				pf.createParser68s());		
	}

}
