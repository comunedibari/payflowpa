/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.generator.modelsgenerator;

import it.nch.eb.common.converter.pmtreq.dbtr.parser.PaymentRequestDbtrParsersFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.generator.flatmodels.FileParserModelGenerator;

import java.io.File;


/**
 * @author gdefacci
 */
public class GenerateFileparserModel {
	
	private static final String	targetPackage	= "it.nch.eb.common.converter.pmtreq.dbtr.models";
	//	final static String packageFolder = "D:/java/projects/maps/cbi2/flat-file-parser/src/java/it/nch/eb/flatx/flatmodel/usecases/bonifico/models1";
	final static String packageFolder = "D:/temp/flat";
	final static File sourceFolder = new File("D:/java/projects/flattener/conversions-project/src/java");
	
	public static void generate(String pkgName, String className, IParser parserDsl) {
		FileParserModelGenerator generator = new FileParserModelGenerator();
		System.out.println(
		generator.generate(pkgName, className, parserDsl ) 
		);	
	}
	
	public void generateBonordDbtrBlocco01_80Model() throws Exception {
		FileParserModelGenerator generator = new FileParserModelGenerator();
		generator.generate(sourceFolder, targetPackage, PaymentRequestDbtrParsersFactory.Names.DBTR_ENVELOPE, 
				new PaymentRequestDbtrParsersFactory().create_01_80_FileParser());	
	}
	
	public void generateBonordDbtrBlocco20_22Model() throws Exception {
		FileParserModelGenerator generator = new FileParserModelGenerator();
		generator.generate(sourceFolder, targetPackage, PaymentRequestDbtrParsersFactory.Names.DBTR_TRX_INFO, 
				new PaymentRequestDbtrParsersFactory().create_20_22_FileParser());	
	}
	
	public void generateBonordDbtrBlocco30_80Model() throws Exception {
		FileParserModelGenerator generator = new FileParserModelGenerator();
		generator.generate(sourceFolder, targetPackage, PaymentRequestDbtrParsersFactory.Names.DBTR_STATUS_INFO, 
				new PaymentRequestDbtrParsersFactory().create_30_80_FileParser());	
	}
	
	public void generateBonordEsitiParserModelModel() throws Exception {
		FileParserModelGenerator generator = new FileParserModelGenerator();
		generator.generate(sourceFolder, targetPackage, PaymentRequestDbtrParsersFactory.Names.DBTR_PMTREQ, 
				new PaymentRequestDbtrParsersFactory().createParser());	
	}
	
}