/**
 * 30/nov/2009
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.common.utils.NamingStrategy;
import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.generator.ConversionGenerationModel;
import it.nch.eb.flatx.generator.JavaGenerator;
import it.nch.eb.flatx.generator.xls.XlsUtil;
import it.nch.eb.flatx.generator.xls.xmlrecord.IXlsGenerator;

import java.io.File;
import java.io.OutputStream;

import jxl.Workbook;

/**
 * @author gdefacci
 */
public class EmptyModelsCompilationUnitGenerator implements IXlsGenerator {
	
	private final ConversionGenerationModel mainGenerator;
	private NamingStrategy namingStrategy;

	public EmptyModelsCompilationUnitGenerator(
			ConversionGenerationModel mainGenerator, NamingStrategy namingStrategy) {
		this.mainGenerator = mainGenerator;
		this.namingStrategy = namingStrategy;
	}

	public void generate(Workbook workbook, String[] processedSheets) {
		File pkgFolder = JavaGenerator.createPackageFolder(
				mainGenerator.getBaseFolder(), 
				mainGenerator.getModelsPackageName());
		for (int i = 0; i < processedSheets.length; i++) {
			String sheet = processedSheets[i];
			String modelNm = StringUtils.capitalized(namingStrategy.apply( mainGenerator.getRecordNamingStrategy().apply(sheet) ));
			if (!modelClassExists(modelNm)) {
				File realf = new File( pkgFolder, modelNm + ".java" );
				OutputStream out = XlsUtil.fileStream( realf );
				EmptyCompilationUnitRenderer renderer = 
					new EmptyCompilationUnitRenderer(mainGenerator.getModelsPackageName(), modelNm);
				renderer.generate(out);
				System.out.println("created " + realf.getAbsolutePath());
			} else {
				System.out.println("skipping " + modelNm + " since it already exists");
			}
		}
	}

	private boolean modelClassExists(String modelNm) {
		String fullNm = mainGenerator.getModelsPackageName() + "." + modelNm;
		try {
			Class.forName(fullNm);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	

}
