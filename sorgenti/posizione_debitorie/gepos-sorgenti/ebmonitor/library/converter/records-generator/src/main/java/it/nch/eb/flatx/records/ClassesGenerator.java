/**
 * Created on 07/mar/08
 */
package it.nch.eb.flatx.records;

import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.flatfile.ParserRecord;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.generator.RecordNameStrategy;
import it.nch.eb.flatx.generator.StringModelClassFromRecord;
import it.nch.eb.flatx.generator.flatmodels.FileParserModelGenerator;
import it.nch.eb.flatx.generator.flatmodels.FileParserModelGeneratorFromRecord;
import it.nch.eb.flatx.generator.flatmodels.FileParserModelGeneratorFromXmlRecord;
import it.nch.eb.flatx.generator.xls.xmlrecord.XmlRecordWithExtraFields;

import java.io.File;


/**
 * @author gdefacci
 */
public class ClassesGenerator {
	
	public static final String[]	FILE_POSITION_PROVIDER	= new String[] {"it.nch.eb.flatx.flatmodel.FilePositionProvider"};
	
	public static final Class[]	FILE_POSITION_PROVIDER_CLASS	= 
		new Class[] {
			it.nch.eb.flatx.flatmodel.FilePositionProvider.class,
		};
	
	public static final String[]	TIPO_RECORD_PROVIDER	= new String[] {"it.nch.eb.flatx.flatmodel.FilePositionProvider", "it.nch.eb.flatx.flatmodel.TipoRecordProvider"};
	
	private final String packageName;
	private final File sourceFolder;
//	private final String[] interfaces;
	
	public ClassesGenerator(final File sourceFolder, final String packageName) {
		if (!sourceFolder.exists()) throw new IllegalStateException(sourceFolder.getAbsolutePath() + " does not exist: the target folder should exist");
		if (!sourceFolder.isDirectory()) throw new IllegalStateException(sourceFolder.getAbsolutePath() + " is not a folder: the target folder should be a folder");

		this.sourceFolder = sourceFolder;
		this.packageName = packageName;
	}

	public ClassesGenerator(String sourceFolderPath, String packageName) {
		this(new File(sourceFolderPath), packageName);
	}

	public void generateFromRecord(IRecord record) {
		generateFromRecord(record, FILE_POSITION_PROVIDER_CLASS, null);	
	}
	
	public void generateFromRecord(IRecord record, RecordNameStrategy rns) {
		generateFromRecord(record, FILE_POSITION_PROVIDER_CLASS, rns);
	}
	
	public void generateFromRecord(IRecord record, Class[] interfaces) {
		generateFromRecord(record, interfaces, null);
	}
	public void generateFromRecord(IRecord record, Class[] interfaces, RecordNameStrategy rns) {
		StringModelClassFromRecord generator = new StringModelClassFromRecord(rns);
		File f = getSourceFolder();
		String[] interfacesNames = new String[interfaces.length];
		for (int i = 0; i < interfaces.length; i++) {
			Class interfaceClass = interfaces[i];
			interfacesNames[i] = interfaceClass.getName();
		}
		generator.generate(f, packageName, interfacesNames, record );	
	}
	
	public void generateFromParserRecord(ParserRecord record) {
		generateFromParserRecord(record, FILE_POSITION_PROVIDER);
	}
	
	public void generateFromXmlRecord(XmlRecord record, String[] interfaces) {
		FileParserModelGeneratorFromXmlRecord generator = new FileParserModelGeneratorFromXmlRecord();
		File f = getSourceFolder();
		generator.generate(f, packageName, interfaces, record );
	}
	
	public void generateFromXmlRecord(XmlRecordWithExtraFields record, String[] interfaces) {
		FileParserModelGeneratorFromXmlRecord generator = new FileParserModelGeneratorFromXmlRecord();
		File f = getSourceFolder();
		generator.generate(f, packageName, interfaces, record.getXmlRecord(),  record.getExtraFields());
	}
	
	public void generateFromParserRecord(ParserRecord record, String[] interfaces) {
		FileParserModelGeneratorFromRecord generator = new FileParserModelGeneratorFromRecord();
		File f = getSourceFolder();
		generator.generate(f, packageName, interfaces, record );	
	}
	
	public void generateFromParser(String className, IParser parserDsl) {
		FileParserModelGenerator generator = new FileParserModelGenerator();
		generator.generate(getSourceFolder(), packageName, className, parserDsl, FILE_POSITION_PROVIDER );	
	}
	
	public void generateFromParser(String className, IParser parserDsl, String[] interfaces) {
		FileParserModelGenerator generator = new FileParserModelGenerator();
		generator.generate(getSourceFolder(), packageName, className, parserDsl, interfaces );	
	}
	
	public void generateFromParsers(String className, IParser[] parsers) {
		FileParserModelGenerator generator = new FileParserModelGenerator();
		generator.generate(getSourceFolder(), packageName, className, FILE_POSITION_PROVIDER, parsers );	
	}
	
	public void generateAll(ParserRecord[] records) {
		for (int i = 0; i < records.length; i++) {
			generateFromParserRecord(records[i]);
		}
	}
	
	public void generateAll(ParserRecord[] records, String[] interfaces) {
		for (int i = 0; i < records.length; i++) {
			generateFromParserRecord(records[i], interfaces);
		}
	}
	
	public void generateAll(XmlRecord[] records) {
		generateAll(records, null);
	}
	public void generateAll(XmlRecord[] records, String[] interfaces) {
		for (int i = 0; i < records.length; i++) {
			generateFromXmlRecord(records[i], interfaces);
		}
	}
	
	public void generateAll(XmlRecordWithExtraFields[] records) {
		generateAll(records, null);
	}
	public void generateAll(XmlRecordWithExtraFields[] records, String[] interfaces) {
		for (int i = 0; i < records.length; i++) {
			generateFromXmlRecord(records[i], interfaces);
		}
	}

	private java.io.File getSourceFolder() {
		return this.sourceFolder;
	}
	
	public void generateAll(IRecord[] records) {
		for (int i = 0; i < records.length; i++) {
			generateFromRecord(records[i]);
		}
	}
	
	public void generateAll(IRecord[] records, RecordNameStrategy nmStrategy) {
		for (int i = 0; i < records.length; i++) {
			generateFromRecord(records[i], nmStrategy);
		}
	}	
	
	public void generateAll(IRecord[] records, Class[] interfaces, RecordNameStrategy nmStrategy) {
		for (int i = 0; i < records.length; i++) {
			generateFromRecord(records[i], interfaces, nmStrategy);
		}
	}
	
	public void generateAll(IRecord[] records, Class[] interfaces) {
		for (int i = 0; i < records.length; i++) {
			generateFromRecord(records[i], interfaces, null);
		}
	}

}
