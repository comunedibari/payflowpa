/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.common.utils.NamingStrategy;
import it.nch.eb.flatx.generator.ConversionGenerationModel;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping.Base;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

/**
 * This class is not thread safe
 * @author gdefacci
 */
public class Xml2SqlCmdFullRecordGenerator extends AbstractFromXlsGenerator implements ConversionGenerationModel{
	
	private static final Base XML_SHEET_COLUMN_MAPPINGS = new SheetColumnsMapping.Base(0, 2, 1, 3);

	public static final NamingStrategy RECORD_NAMING_STRATEGY = new NamingStrategy() {

		public String apply(String orig) {
			return orig + "Record";
		}
		
	};
	
	private Xml2SqlCmdConvertersMap typesMap;
//	private String[] recordInterfaces;
	
//	private NamingStrategy recordNamingStrategy;
	
	private File ibatisTargetFolder = null;
	
	private final XmlIndexSheetMetaData indexSheetMetaData = new XmlIndexSheetMetaData();
	private IndexSheet indexSheet;

//	private String[] modelInterfaces;

	public Xml2SqlCmdFullRecordGenerator(Xml2SqlCmdConvertersMap typesMap, File baseFolder, String packageName) {
		this(typesMap, baseFolder, packageName, new String[] {});
	}
	
	public Xml2SqlCmdFullRecordGenerator(Xml2SqlCmdConvertersMap typesMap, File baseFolder, String packageName, String[] interfacesFullName) {
		this(typesMap, baseFolder, packageName, interfacesFullName, RECORD_NAMING_STRATEGY);
	}
	
	public Xml2SqlCmdFullRecordGenerator(Xml2SqlCmdConvertersMap typesMap, 
			File baseFolder, String packageName, 
			String[] interfacesFullName, 
			NamingStrategy recNamingStrategy) {
		this(typesMap, baseFolder, packageName, interfacesFullName, new String[0], recNamingStrategy);
	}
	
	public Xml2SqlCmdFullRecordGenerator(Xml2SqlCmdConvertersMap typesMap, 
			File baseFolder, String packageName, 
			String[] interfacesFullName, 
			String[] modelInterfacesFullName) {
		this(typesMap, baseFolder, packageName, interfacesFullName, 
				modelInterfacesFullName, RECORD_NAMING_STRATEGY);
	}
	
	public Xml2SqlCmdFullRecordGenerator(Xml2SqlCmdConvertersMap typesMap, 
			File baseFolder, String packageName, 
			String[] interfacesFullName, 
			String[] modelInterfacesFullName, 
			NamingStrategy recNamingStrategy) {
		super(baseFolder, XML_SHEET_COLUMN_MAPPINGS, packageName);
		this.typesMap = typesMap;
		setRecordInterfaces(interfacesFullName);
		setModelsInterfaces( modelInterfacesFullName );
		setRecordNamingStrategy( recNamingStrategy );
	}
	
//	public String getRecordPackageName() {
//		return super.getPackageName() + ".record";
//	}
//	
//	public String getModelsPackageName() {
//		return super.getPackageName() + ".model";
//	}
	
	public XmlIndexSheetMetaData getIndexSheetMetaData() {
		return indexSheetMetaData;
	}
	
	public IndexSheet getIndexSheet() {
		return indexSheet;
	}

	private void findIndexSheet(Workbook workbook) {
		Sheet[] sheets = workbook.getSheets();
		for (int i = 0; i < sheets.length; i++) {
			Sheet sheet = sheets[i];
			String shtName = sheet.getName();
			if (shtName.equals("-- index")) {
				indexSheet = getIndexSheetMetaData().create(workbook, sheet);
			}
		}
	}
	
	/**
	 * @Override
	 */
	public final void generate(Workbook workbook) {
		findIndexSheet(workbook);
		super.generate(workbook);
	}
	
	public IXlsGenerator[] xlsPostGenerators() {
		return new IXlsGenerator[] {
			new XmlModelsGeneratorGenerator(this, typesMap),	
		};
	}
	
	public IXlsSheetGenerator[] xlsGenerators() {
		return new IXlsSheetGenerator[] {
			new XmlRecordGenerator(this),
			new IBatisSqlMapGenerator(this, this.typesMap),
		};
	}
	
	
	
	public File getIbatisTargetFolder() {
		return ibatisTargetFolder;
	}

	public void setIbatisTargetFolder(File ibatisTargetFolder) {
		this.ibatisTargetFolder = ibatisTargetFolder;
	}

//	public String[] getModelsInterfaces() {
//		return modelInterfaces;
//	}
	
//	public NamingStrategy getRecordNamingStrategy() {
//		return recordNamingStrategy;
//	}

	public Xml2SqlCmdConvertersMap getTypesMap() {
		return typesMap;
	}

}
