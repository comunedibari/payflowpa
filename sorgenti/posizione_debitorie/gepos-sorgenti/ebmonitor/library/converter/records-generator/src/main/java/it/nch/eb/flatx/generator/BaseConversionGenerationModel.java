/**
 * 26/nov/2009
 */
package it.nch.eb.flatx.generator;

import it.nch.eb.common.utils.NamingStrategy;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;

import java.io.File;

/**
 * @author gdefacci
 */
public class BaseConversionGenerationModel implements ConversionGenerationModel{

	private final File baseFolder;
	private final SheetColumnsMapping columnsMappings;
	private NamingStrategy recordNamingStrategy;
	
	private String[] modelsInterfaces;
	private String[] recordInterfaces;
	
	private String packageName;
	private String modelsPackageName;
	private String recordPackageName;
	
	private final int startRow;
	
	static NamingStrategy IDENTITY_NAMING_STRATEGY = new NamingStrategy() {

		public String apply(String orig) {
			return orig;
		}
		
	};
	
	public BaseConversionGenerationModel(File baseFolder,
			SheetColumnsMapping columnsMappings, String packageName) {
		this(baseFolder, columnsMappings, IDENTITY_NAMING_STRATEGY, 
				new String[0], new String[0],
				packageName,
				packageName + ".model",
				packageName + ".record",
				1);
	}

	public BaseConversionGenerationModel(File baseFolder,
			SheetColumnsMapping columnsMappings,
			NamingStrategy javaRecordNamingStrategy, String[] modelsInterfaces, String[] recordInterfaces,
			String packageName, 
			String modelsPackageName,
			String recordPackageName, 
			int startRow) {
		super();
		this.baseFolder = baseFolder;
		this.columnsMappings = columnsMappings;
		this.recordNamingStrategy = javaRecordNamingStrategy;
		this.modelsInterfaces = modelsInterfaces;
		this.recordInterfaces = recordInterfaces;
		this.packageName = packageName;
		this.modelsPackageName = modelsPackageName;
		this.recordPackageName = recordPackageName;
		this.startRow = startRow;
	}

	public BaseConversionGenerationModel(File baseFolder,
			SheetColumnsMapping columnsMappings, 
			String packageName,
			String modelsPackageName, 
			String recordPackageName) {
		this(baseFolder, columnsMappings, 
				IDENTITY_NAMING_STRATEGY, new String[0], new String[0],
				packageName, 
				modelsPackageName, 
				recordPackageName, 
				1);
	}

	public File getBaseFolder() {
		return baseFolder;
	}

	public SheetColumnsMapping getColumnMappings() {
		return columnsMappings;
	}

	public NamingStrategy getRecordNamingStrategy() {
		return recordNamingStrategy;
	}

	public String[] getModelsInterfaces() {
		return modelsInterfaces;
	}

	public String getModelsPackageName() {
		return modelsPackageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getRecordPackageName() {
		return recordPackageName;
	}

	public int getStartRow() {
		return startRow;
	}

	public SheetColumnsMapping getColumnsMappings() {
		return columnsMappings;
	}

	public void setModelsPackageName(String modelsPackageName) {
		this.modelsPackageName = modelsPackageName;
	}

	public void setRecordNamingStrategy(NamingStrategy javaRecordNamingStrategy) {
		this.recordNamingStrategy = javaRecordNamingStrategy;
	}

	public void setModelsInterfaces(String[] modelsInterfaces) {
		this.modelsInterfaces = modelsInterfaces;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setRecordPackageName(String recordPackageName) {
		this.recordPackageName = recordPackageName;
	}

	public String[] getRecordInterfaces() {
		return recordInterfaces;
	}

	public void setRecordInterfaces(String[] recordInterfaces) {
		this.recordInterfaces = recordInterfaces;
	}

}
