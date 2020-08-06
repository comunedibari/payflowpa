/**
 * Created on 30/gen/09
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import it.nch.eb.flatx.flatmodel.AbstractRecord;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.VoidToObjectConverter;


/**
 * @author gdefacci
 */
public class ObjectWrapperRecord extends AbstractRecord implements ObjectConversionInfoRecord {
	
	private ToObjectConversionInfo[] conversionInfos;
	
	public ObjectWrapperRecord() {
		super(ToObjectConversionInfo.class);
	}

	public ObjectWrapperRecord(Class/*<? extends ToObjectConversionInfo>*/ klass) {
		super(klass);
	}

	public synchronized ToObjectConversionInfo[] getConversionInfos() {
		if (conversionInfos==null) {
			lazyInit();
		}
		return conversionInfos;
	}

	private synchronized void lazyInit() {
		conversionInfos = (ToObjectConversionInfo[]) filterConversionInfos( getConversionInfos(this) );
	}
	
	protected boolean isVoid(ToObjectConversionInfo toObjectConversionInfo) {
		return VoidToObjectConverter.isVoid(toObjectConversionInfo.getToObjectConverter());
	}
	
}
