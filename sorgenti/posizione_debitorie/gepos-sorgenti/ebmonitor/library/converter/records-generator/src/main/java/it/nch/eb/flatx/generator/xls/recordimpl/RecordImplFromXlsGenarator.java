/**
 * 
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.generator.xls.GenTypeModel;
import it.nch.eb.flatx.generator.xls.GenTypesMap;
import it.nch.eb.flatx.generator.xls.RecordSheet;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.SimpleType;
import it.nch.eb.flatx.generator.xls.XlsCustomsFactory;
import it.nch.eb.flatx.generator.xls.xmlrecord.AbstractFromXlsGenerator;
import it.nch.eb.flatx.generator.xls.xmlrecord.IXlsSheetGenerator;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdefacci
 */
public class RecordImplFromXlsGenarator extends AbstractFromXlsGenerator implements IXlsSheetGenerator { 
	
//	private final Map/*<String>*/ defineTypes = new HashMap();
	private final GenTypesMap defineTypes = new GenTypesMap();
	private final Map generatorsMap = new HashMap();
	private final Map/*<String, String>*/ recordTipologiaMap = new HashMap();
	private final Map/*<String, String>*/ recordBasePrefix = new HashMap();
	private boolean ignoreNamesColumn = false;
	private final Map toRenameFields = new HashMap();
	private boolean optionality;
	private String defaultBasePathPrefix;
	
	public RecordImplFromXlsGenarator(File baseFolder, 
			SheetColumnsMapping columnMappings,
			String packageName,
			String modelsPackageName,
			String recordPackageName) {
		super(baseFolder, columnMappings, packageName,  modelsPackageName, recordPackageName);
	}
	
	public RecordImplFromXlsGenarator(File baseFolder, 
			SheetColumnsMapping columnMappings,
			String recordPackageName) {
		this(baseFolder, columnMappings, null,  null, recordPackageName);
	}
	
	public RecordImplFromXlsGenarator(File baseFolder, 
			String recordPackageName) {
		this(baseFolder, RecordSheet.DEFAULT_COLUMN_MAPPINGS, null,  null, recordPackageName);
	}

	public RecordImplFromXlsGenarator addTipoRecord(String recordName, String tipoRec) {
		if (recordTipologiaMap.get(recordName)!=null) {
			String oldTipoRec = (String) recordTipologiaMap.get(recordName);
			throw new IllegalStateException("record " + recordName 
					+ " has been alredy set to " + oldTipoRec 
					+ " cant set to " + tipoRec );
		}
		recordTipologiaMap.put(recordName, tipoRec);
		return this;
	}
	
	public RecordImplFromXlsGenarator setIgnoreNamesColumn(boolean toIgnore) {
		this.ignoreNamesColumn = toIgnore;
		return this;
	}
	
	public RecordImplFromXlsGenarator renameFieldsNamed(String originalName, String newName) {
		this.toRenameFields.put(originalName, newName);
		return this;
	}
	
	public RecordImplFromXlsGenarator addType(String nm, String typ) {
		return addType(nm, new SimpleType(typ));
	}
	public RecordImplFromXlsGenarator addType(String nm, GenTypeModel typ) {
		this.defineTypes.put(nm, typ);
		return this;
	}
	
	public RecordImplFromXlsGenarator addTypesContainer(Class klass) {
		Field[] fields = ReflectionUtils.getClassFields(klass, Converter.class);
		String[] fieldNames = getFieldNamesList( fields );
		for (int i = 0; i < fieldNames.length; i++) {
			String fldNm = fieldNames[i];
			addType(fldNm, fldNm);
		}
		return this;
	}

	/* op e' memorizzato uppercase	 */
	public RecordImplFromXlsGenarator addCustomGenerator(String op, XlsCustomsFactory generatorFactory) {
		this.generatorsMap.put(op.toUpperCase(), generatorFactory);
		return this;
	}
	
	public RecordImplFromXlsGenarator setOptionalitySemantic(boolean optionality) {
		this.optionality = optionality;
		return this;
	}
	
	public String getTipoRecord(String recordName) {
		Object res = recordTipologiaMap.get(recordName);
		String tabPrefix = "Record";
		if (res ==null && recordName.startsWith(tabPrefix)) {
			res = recordName.substring(tabPrefix.length());
		}
		if (res == null) {
			System.out.println("cant find tipo record for " + recordName+ " setting tipo_record = '**'");
			res = "**";
		}
		return (String) res;
	}
	
	public IXlsSheetGenerator[] xlsGenerators() {
		return new IXlsSheetGenerator[] {
				new RecordImplXlsSheetGenerator(this),
		};
	}
	
	public Map getFieldsToRename() {
		return toRenameFields;
	}
	
	public boolean getOptionalitySemantic() {
		return optionality;
	}

	public void setOptionality(boolean optionality) {
		this.optionality = optionality;
	}

	protected IRecordSheetXmlRecordImplRenderer createRecordsGenerator(String tipoRecord) {
		return new RecordSheetXmlRecordImplRenderer( getRecordPackageName(), tipoRecord, getRecordInterfaces());
	}
	
	public GenTypesMap getDefineTypes() {
		return defineTypes;
	}

	public Map getGeneratorsMap() {
		return generatorsMap;
	}

	public boolean isIgnoreNamesColumn() {
		return ignoreNamesColumn;
	}

	public String[] getFieldNamesList(Field[] fields) {
		String[] res = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			res[i] = fields[i].getName();
		}
		return res;
	}

	public RecordImplFromXlsGenarator setBasePathPrefix(String record, String basePathPrefix) {
		this.recordBasePrefix.put(record, basePathPrefix);
		return this;
	}
	
	public String getDefaultBasePathPrefix() {
		return defaultBasePathPrefix;
	}

	public RecordImplFromXlsGenarator setDefaultBasePathPrefix(String defaultBasePathPrefix) {
		this.defaultBasePathPrefix = defaultBasePathPrefix;
		return this;
	}

	public String getBasePathPrefix(String recName) {
		String res = (String) this.recordBasePrefix.get(recName);
		if (res == null) {
			return defaultBasePathPrefix;
		}
		return res;
	}
}
