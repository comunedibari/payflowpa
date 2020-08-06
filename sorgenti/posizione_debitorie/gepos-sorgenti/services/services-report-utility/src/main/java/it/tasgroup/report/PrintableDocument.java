package it.tasgroup.report;

import java.util.ResourceBundle;

public interface PrintableDocument {
	
	Boolean needWatermark();
	
	String getWatermarkText(ResourceBundle bundle);
	
}
