/**
 * 25/mag/2009
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdefacci
 */
public class IBatisResultMapModel {
	
	private final String id;
	private final String className;
	private final List/*<IBatisResultPropertyModel>*/ resultPropertiesList = new ArrayList();
	
	public IBatisResultMapModel(String id, String className) {
		super();
		this.id = id;
		this.className = className;
	}

	public String getId() {
		return id;
	}

	public String getClassName() {
		return className;
	}
	
	public IBatisResultPropertyModel[] getResultProperties() {
		return (IBatisResultPropertyModel[]) resultPropertiesList.toArray(new IBatisResultPropertyModel[0]);
	}
	
	public void add(IBatisResultPropertyModel item) {
		this.resultPropertiesList.add(item);
	}

}
