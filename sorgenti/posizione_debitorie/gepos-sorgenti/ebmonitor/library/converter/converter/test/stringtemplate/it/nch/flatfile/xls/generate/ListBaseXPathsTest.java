/**
 * Created on 26/mar/08
 */
package it.nch.flatfile.xls.generate;

import it.nch.eb.flatx.generator.xls.RecordSheet;
import it.nch.eb.flatx.generator.xls.XlsUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import jxl.Sheet;
import jxl.Workbook;


/**
 * @author gdefacci
 */
public class ListBaseXPathsTest extends TestCase {
	
	private File	folder = new File("D:/java/projects/flattener/conversions-project");
	File	file = new File(folder, "docs/esiti-cdtr.xls" );
	Workbook workbook = XlsUtil.open(file);

	Set toSkipSet = new HashSet(Arrays.asList(new String[] { "Record65" }));
	
	public void testPrintBasePaths() throws Exception {
		Sheet[] sheets = workbook.getSheets();
		for (int i = 0; i < sheets.length; i++) {
			Sheet sheet = sheets[i];
			String name = sheet.getName();		
			if (!toSkipSet.contains(name)) {
				RecordSheet rs = new RecordSheet(sheet);
				System.out.println( rs.getName() + " : /MSG:CBICdtrPmtStatusReportMsg/MSG:CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport" + rs.getBasePath() );
			}
		}
	}
	
}
