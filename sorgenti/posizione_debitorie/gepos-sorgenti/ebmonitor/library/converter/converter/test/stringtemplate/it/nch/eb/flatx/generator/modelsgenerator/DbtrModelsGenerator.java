/**
 * Created on 04/gen/08
 */
package it.nch.eb.flatx.generator.modelsgenerator;

import it.nch.eb.common.converter.common.records.RecordTesta;
import it.nch.eb.common.converter.pmtreq.advinf.records.RecordCoda;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record01;
import it.nch.eb.common.converter.pmtreq.records.Record20;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.RecordImpl;
import it.nch.eb.flatx.generator.StringModelClassFromRecord;

import java.io.File;


/**
 * @author gdefacci
 */
public class DbtrModelsGenerator {
	
	public static void generate(IRecord record, String sourceFolderPath, String pkgName) {
		StringModelClassFromRecord generator = new StringModelClassFromRecord();
		File f = new File(sourceFolderPath);
		generator.generate(f, pkgName, record );	
	}
	
	static final String packageName = "it.nch.eb.common.converter.pmtreq.dbtr.models";
	static String sourceFolderPath = "D:/java/projects/flattener/conversions-project/src/java";
	
	public static void main(String[] args) {
		IRecord[] records = new IRecord[] { new it.nch.eb.common.converter.pmtreq.dbtr.records.Record01(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.Record20(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.Record22(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.Record30(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.Record32(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.Record34(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.Record36(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.Record40(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.Record80(),
				new RecordCoda(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.RecordCodaBody(),
				new RecordTesta(),
				new it.nch.eb.common.converter.pmtreq.dbtr.records.RecordTestaBody(), };
		
		for (int i = 0; i < records.length; i++) {
			IRecord record = records[i];
			generate(record, sourceFolderPath, packageName);
		}
	}
	
	public void _testGen5() {
		StringModelClassFromRecord generator = new StringModelClassFromRecord();
		Record20 r = new Record20();
		String cu = generator.generate( "it.nch.eb.flatx.flatmodel.stringtemplate", r.getName(), r );
		System.out.println(	cu );
	}
	
	public void _testGen3() {
		StringModelClassFromRecord generator = new StringModelClassFromRecord();
		RecordImpl r = new Record01();
		String cu = generator.generate( "it.nch.eb.flatx.flatmodel.stringtemplate", "Pippo", r );
		System.out.println(	cu );
	}
	
	public void _testGen4() {
		StringModelClassFromRecord generator = new StringModelClassFromRecord();
//		String folder = "D:/java/projects/flattener/flat-framework-xom/target/gen";
//		String packageDir = "/it/nch/eb/flatx/flatmodel/usecase";
		IRecord r = new Record01();
		File f = new File("D:/java/projects/flattener/flat-framework-xom/src/main/java/it/nch/eb/flatx/flatmodel/usecase", r.getName() + "Model" + ".java" );
		generator.generate( f, "it.nch.eb.flatx.flatmodel,usecase", r );
	}
	
}
