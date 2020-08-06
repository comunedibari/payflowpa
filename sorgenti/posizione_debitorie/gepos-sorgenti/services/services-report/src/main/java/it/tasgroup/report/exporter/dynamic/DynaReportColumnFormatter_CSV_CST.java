/**
 * 
 */
package it.tasgroup.report.exporter.dynamic;

import java.util.List;
import java.util.Map;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * @author pazzik
 *
 */
public class DynaReportColumnFormatter_CSV_CST implements IDynaReportColumnFormatter{
	
	@Override
	public void formatColumns(DynamicReportBuilder drb, List<String[]> intestazione, boolean withIntestazione, Map<String, String> etichetteIntestazione) {
		
		for (String[] campo : intestazione) {
			
			 Style detailStyle = new Style();
			 detailStyle.setBlankWhenNull(true);
			
			AbstractColumn column = null;
			
			if(campo[1] != null && (campo[1].contains("BigDecimal") || campo[1].contains("Decimal"))){
				column = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
						.setColumnProperty(campo[0], campo[1])		//defines the field of the data source that this column will show, also its type
						.setWidth(85)		//the width of the column
						.setPattern("#,###.00")
						.setStyle(detailStyle)      
						.build();		//builds and return a new AbstractColumn
				
			}else if(campo[1] != null && campo[1].contains("Timestamp")){
				
				column = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
						.setColumnProperty(campo[0], campo[1])		//defines the field of the data source that this column will show, also its type
						.setWidth(85)		//the width of the column
						.setPattern("dd/MM/yyyy HH:mm:ss")
						.setStyle(detailStyle)      
						.build();		//builds and return a new AbstractColumn

			}else if(campo[1] != null && campo[1].contains("Date")){
                
                column = ColumnBuilder.getNew()     //creates a new instance of a ColumnBuilder
                        .setColumnProperty(campo[0], campo[1])      //defines the field of the data source that this column will show, also its type
                        .setWidth(85)       //the width of the column
                        .setPattern("dd/MM/yyyy")
                        .setStyle(detailStyle)
                        .build();       //builds and return a new AbstractColumn
            }else{
			         column = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
						.setColumnProperty(campo[0], campo[1])		//defines the field of the data source that this column will show, also its type
						.setShowText(true)
						.setWidth(85)		//the width of the column
						.setStyle(detailStyle)      
						.build();		//builds and return a new AbstractColumn
			}

			if (withIntestazione && column != null){
			    if(etichetteIntestazione != null && !etichetteIntestazione.isEmpty() && etichetteIntestazione.get(campo[0]) != null){
			        column.setTitle(etichetteIntestazione.get(campo[0]));
			    } else {
			        column.setTitle(campo[0]);				        
			    }
			}				
			drb.addColumn(column);
			drb.setWhenResourceMissingLeaveEmptySpace();
		}

	}

}
