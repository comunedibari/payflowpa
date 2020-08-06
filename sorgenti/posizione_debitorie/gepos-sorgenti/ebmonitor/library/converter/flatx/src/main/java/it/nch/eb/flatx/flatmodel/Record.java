/**
 * Created on 03/gen/08
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;


/**
 * @author gdefacci
 */
public abstract class Record extends AbstractRecord {
	
	private final ObjectBuilder	objectBuilder;
	
	public Record(ObjectBuilder objectBuilder) {
		this.objectBuilder = objectBuilder;
	}
	
	public Record(Class klass, ObjectBuilder objectBuilder) {
		super(klass);
		this.objectBuilder = objectBuilder;
	}
	
	public ObjectBuilder getObjectBuilder() {
		return objectBuilder;
	}
	
	public Object createContainer() {
		return getObjectBuilder().create();
	}

//	protected String setValue(Object target, Converter cnv, String value) {
//		if (!(cnv instanceof ConversionInfo)) return null;
//		ConversionInfo converter = (ConversionInfo) cnv;
//		Setter setter = Utils.getSetterForStringProperty(target, converter.getName());
//		String realValue = converter.encode(value);
//		setter.setValue(realValue);
//		return realValue;
//	}
	
//	protected String setValue(Object target, IConversionInfo converter, String value) {
////		ConversionInfo converter = (ConversionInfo) cnv;
//		Setter setter = Utils.getSetterForStringProperty(target, converter.getName());
//		String realValue = converter.encode(value);
//		setter.setValue(realValue);
//		return realValue;
//	}
//	
////	TODO never used
//	protected Object createFrom(String[] arr) {
//		IConversionInfo[] cis = getConversionInfos(this);
//		Object res = createContainer();		
//		for (int i = 0; i < cis.length; i++) {
//			setValue(res, cis[i], arr[i]);
//		}
//		return res;
//	}
//	
////	TODO never used
//	protected Object createFrom(Map props) {
//		return createFrom(new StringMap.FromMapAdapter(props));
//	}
//	
////	TODO never used
//	protected Object createFrom(StringMap props) {
//		IConversionInfo[] cis = getConversionInfos(this);
//		Object res = createContainer();		
//		for (int i = 0; i < cis.length; i++) {
//			String key = cis[i].getName();
//			setValue(res, cis[i], props.get(key));
//		}
//		return res;
//	}
//	
	public Class getProductClass() {
		return objectBuilder.getProductClass();
	}


}
