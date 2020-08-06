/**
 * 
 */
package it.tasgroup.report.exporter.dynamic;

import java.util.List;
import java.util.Map;

import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;

/**
 * @author pazzik
 *
 */
public interface IDynaReportColumnFormatter {
	
	public void formatColumns(DynamicReportBuilder drb, List<String[]> intestazione, boolean withIntestazione, Map<String, String> etichetteIntestazione);

}
