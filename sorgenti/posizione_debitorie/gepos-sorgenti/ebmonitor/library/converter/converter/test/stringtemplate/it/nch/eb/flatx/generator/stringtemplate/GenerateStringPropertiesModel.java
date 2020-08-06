/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.generator.stringtemplate;

import it.nch.eb.common.converter.pmtreq.records.Record20;
import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.generator.StringModelClassFromRecord;

import java.io.File;


/**
 * @author gdefacci
 */
public class GenerateStringPropertiesModel {
	
	public static void generate(IRecord record, String sourceFolderPath, String pkgName) {
		StringModelClassFromRecord generator = new StringModelClassFromRecord();
		File f = new File(sourceFolderPath);
		generator.generate(f, pkgName, record );	
	}
	
	public static void main(String[] args) {
//		String sourceFolderPath = "D:/java/projects/flattener/conversions-project/test/java";
		String sourceFolderPath = "D:/temp/fuckyou/";
		File resF = new File(sourceFolderPath);
		
		IRecord rec = new Record20();
		generate(rec, 
				sourceFolderPath, 
				"it.nch.flatfile.parser" );
		
		String outFile = StringUtils.concatPaths(sourceFolderPath, "it/nch/flatfile/parser/Record20Model.java");
		resF = new File(outFile);
		
		System.out.println(resF.getAbsolutePath());
		if (resF.exists()) System.out.println(" exist ");
		else System.out.println("dont exist ");
		
	}

}
