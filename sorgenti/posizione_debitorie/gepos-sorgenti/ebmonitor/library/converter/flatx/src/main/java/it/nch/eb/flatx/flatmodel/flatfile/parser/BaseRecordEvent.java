/**
 * Created on 08/mag/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.common.utils.resource.ResourcesUtil;


/**
 * @author gdefacci
 */
public class BaseRecordEvent implements RecordEvent {
	
	private static final org.slf4j.Logger log = ResourcesUtil.createLogger(BaseRecordEvent.class);

	final Object container; 
	final IParser propertyParser; 
	final Object proertyValue;
	
	public BaseRecordEvent(Object container, IParser propertyParser, Object propertyValue) {
		this.container = container;
		this.propertyParser = propertyParser;
		this.proertyValue = propertyValue;
		if (!propertyValue.getClass().getName().equals(propertyParser.getClass().getName())) {
			log.warn("the property value " + propertyValue + " doesnt match the parser product" + 
					propertyParser.getObjectBuilder().getProductClass().getName());
		}
	}
	
	public Object getContainer() {
		return container;
	}
	
	public IParser getPropertyParser() {
		return propertyParser;
	}

	public Object getPropertyValue() {
		return proertyValue;
	}

	public String getPropertyName() {
		return propertyParser.getName();
	}
}
