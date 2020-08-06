/**
 * 26/nov/2009
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.common.utils.NamingStrategy;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.xmlrecord.IXlsGenerator;

import java.io.File;

/**
 * @author gdefacci
 */
public class ParserRecordslFromXlsGenarator extends RecordImplFromXlsGenarator{

	private NamingStrategy modelNamingStrategy = new NamingStrategy() {

		public String apply(String orig) {
			return orig + "Model";
		}
		
	};

	public ParserRecordslFromXlsGenarator(File baseFolder, 
			SheetColumnsMapping columnMappings, 
			String packageName, 
			String modelsPackageName, 
			String recordsPackageName) {
		super(baseFolder, columnMappings, packageName, modelsPackageName, recordsPackageName);
	}

	protected IRecordSheetXmlRecordImplRenderer createRecordsGenerator(String tipoRecord) {
		return new ParserRecordSheetXmlRecordImplRenderer(getRecordPackageName(), tipoRecord, getModelsPackageName(), getRecordInterfaces(), modelNamingStrategy);
	}

	public NamingStrategy getModelNamingStrategy() {
		return modelNamingStrategy;
	}
	
	public IXlsGenerator[] xlsPostGenerators() {
		return new IXlsGenerator[] { 
			new ModelsGeneratorGenerator(this),
			new EmptyModelsCompilationUnitGenerator(this, getModelNamingStrategy())
		};
	}

	public ParserRecordslFromXlsGenarator setModelNamingStrategy(NamingStrategy modelNamingStrategy) {
		this.modelNamingStrategy = modelNamingStrategy;
		return this;
	}

}
