/**
 * 18/set/2009
 */
package it.nch.gen.xsqlcmd;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.generator.xls.xmlrecord.Xml2SqlCmdConvertersMap;
import it.nch.eb.flatx.generator.xls.xmlrecord.Xml2SqlCmdFullRecordGenerator;

import java.io.File;

import jxl.Workbook;

/**
 * @author gdefacci
 */
public class Gen_1_00_03 {

	static final File srcGenerators = new File("src/generators");
	static final File srcConfiguration = new File("configuration");
	
	
	static final File xlsFile = new File("xls/pendenze-01.03-00.xls");
	
	static final GenInfo testInfo = new GenInfo(
			srcGenerators, 
			xlsFile,
			"it.nch.eb.test.xsqlcmd.dbtrpos.gen",
			new File(srcGenerators, "it/nch/sample")
			);
	
	static final File srcJava = new File("src/java");
	
	static final GenInfo prodInfo = new GenInfo(
			srcJava, 
			xlsFile,
			"it.nch.eb.xsqlcmd.dbtrpos.gen",
			new File(srcConfiguration, "ibatis/gen")
			); 

	
	public static void main(String[] args) throws Exception {
		GenInfo genInfo = testInfo;
		Workbook workBoook = Workbook.getWorkbook(genInfo.getWorkBookFile());
		ResourcesUtil.mkDirs( genInfo.getIbatisFolder() );
		Xml2SqlCmdConvertersMap typeMapping = IdpXml2SqlCmdConvertersMapFactory.instance.create();
		
		Xml2SqlCmdFullRecordGenerator gen = new Xml2SqlCmdFullRecordGenerator(typeMapping ,
				genInfo.getSrcFolder(), 
				genInfo.getTargetPackage(),
				new String[] {
					"it.nch.eb.xsqlcmd.dbtrpos.record.PendenzeConverters",
					"it.nch.eb.flatx.flatmodel.conversioninfo.ConstXPathToObjectConversionInfos",
				},
				new String[] {
					"it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps",
					"java.io.Serializable",
				});
		
		gen.setIbatisTargetFolder(genInfo.getIbatisFolder());
		
		gen.skipSheets(new String[] {"FLUSSI", "DESTINATARI", "CONDIZIONI_PAGAMENTO", "VOCI_BILANCIO", "ALLEGATO"}).generate(workBoook);
//		gen.skipSheets(new String[] {"PENDENZE", "DESTINATARI", "CONDIZIONI_PAGAMENTO", "VOCI_BILANCIO", "ALLEGATO"}).generate(workBoook);
	}
}
