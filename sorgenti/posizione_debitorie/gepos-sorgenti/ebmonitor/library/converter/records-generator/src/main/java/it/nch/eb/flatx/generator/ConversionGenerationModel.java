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
public interface ConversionGenerationModel {

	SheetColumnsMapping getColumnMappings();

	int getStartRow();

	String[] getModelsInterfaces();
	String[] getRecordInterfaces();

	File getBaseFolder();

	String getPackageName();
	String getRecordPackageName();
	String getModelsPackageName();

	NamingStrategy getRecordNamingStrategy();

}