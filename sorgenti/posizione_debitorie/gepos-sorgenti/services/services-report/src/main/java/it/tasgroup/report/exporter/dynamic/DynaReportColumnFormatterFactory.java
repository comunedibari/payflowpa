/**
 * 
 */
package it.tasgroup.report.exporter.dynamic;

import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;

/**
 * @author pazzik
 *
 */
public class DynaReportColumnFormatterFactory {
	
	public static IDynaReportColumnFormatter createColumnFormatter(EnumDynaReportFormat reportFormat, EnumExportSTDFormat enumExportSTDFormat){
		
		switch(reportFormat){
		
			case CSV_CUSTOM: return new  DynaReportColumnFormatter_CSV_CST();
		
			case CSV_STANDARD:
				
				switch(enumExportSTDFormat){
				
					case CSV_BASIC_1: return new  DynaReportColumnFormatter_CSV_STD();
				
					case CSV_BASIC_3: return new  DynaReportColumnFormatter_CSV_STD2();
				}
				
				
		
		}
		
		return null;
		
	}

}
