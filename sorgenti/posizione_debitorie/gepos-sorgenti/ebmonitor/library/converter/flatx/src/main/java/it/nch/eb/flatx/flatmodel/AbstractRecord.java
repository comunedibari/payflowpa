/**
 * Created on 04/gen/08
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.OffsetsLinesFactory;
import it.nch.eb.flatx.flatmodel.conversioninfo.ConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConversionInfo;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author gdefacci
 */
public abstract class AbstractRecord {

	private final Class	klass;
	
	public AbstractRecord() {
		this(ConversionInfo.class);
	}
	
	public AbstractRecord(Class/*<? extends IConversionInfo>*/ klass) {
		if (!IConversionInfo.class.isAssignableFrom(klass)) throw new IllegalStateException("only Class<" + ConversionInfo.class.getName() + "> allowed ");
		this.klass = klass;
	}
	
	protected IConversionInfo asConversionInfo(IConversionInfo ci) {
		if (klass.isInstance(ci)) {
			return ci;
		} else {
			return null;
		}
	}
	
	/* array in java are covariant so we can return ConversionInfo[] that can be downcasted to the proper
	 * ConversionInfo array*/
	protected IConversionInfo[] newConversionInfoArray(int len) {
		return (IConversionInfo[]) Array.newInstance(klass, len);
	}
	
	/**
	 * create an array of <code>klass</code>, including all elements that are assignable from
	 * <code>klass</code>. When a element, that is not a <code>klass</code> instance, is found
	 * a exception is raised 
	 * @param conversionInfos
	 * @return
	 */
	protected IConversionInfo[] filterConversionInfos(IConversionInfo[] conversionInfos) {
		IConversionInfo[] res = newConversionInfoArray(conversionInfos.length);
		for (int i = 0; i < conversionInfos.length; i++) {
			IConversionInfo conversionInfo = conversionInfos[i];
			ToObjectConversionInfo toObjectConversionInfo  = (ToObjectConversionInfo) asConversionInfo(conversionInfo);
			if (toObjectConversionInfo!=null) {
				res[i] = toObjectConversionInfo;
			} else {
				throw new IllegalStateException("null ToObjectConversionInfo");	// should never happen
			}
		}
		Arrays.sort(res);
		return res;
	}

	/**
	 * return an ConversionInfo array tha containing all <code>klass</code> instances from object <code>from</code>
	 * @param from
	 * @return
	 */
	protected IConversionInfo[] getConversionInfos(Object from) {
		List res = new ArrayList();
		Field[] flds = getConversionInfoFields(from);
		Set keySet = new TreeSet/*<Integer>*/();
		try {
			for (int j = 0; j < flds.length; j++) {
				Field field = flds[j];
				Object val = field.get(from);
				IConversionInfo pc = (IConversionInfo) val;
				Integer position = new Integer( pc.getPosition() );
				if (keySet.contains(position)) {
					throw new IllegalStateException("the conversion info[" + pc.getName() 
							+ "]position[" + pc.getPosition() 
							+"] uses an already used position in record [" + this.toString()+ "]");
				} else {
					keySet.add(position);
				}
				
				if (pc.getName()==null) {
					pc.setName(field.getName());
				}
				res.add(pc);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Collections.sort(res);
		return (IConversionInfo[]) res .toArray(new IConversionInfo[0]);
	}

	/**
	 * return all fields that are <code>klass</code> instances from object <code>from</code>
	 */
	public Field[] getConversionInfoFields(Object from) {
		return ReflectionUtils.getFields(from, klass);
	}

	public Field[] getConversionInfoFields() {
		return getConversionInfoFields(this);
	}
	
	public int[] getRecordOffsetsArray(ConversionInfo[] cis) {
		return calculateRecordOffsetsArray(cis);
	}
	
	private static int[] calculateRecordOffsetsArray(ConversionInfo[] converters) {
		int[] cols = new int[converters.length];
		for (int i = 0; i < converters.length; i++)
			cols[i] = converters[i].getLength().intValue();
		return cols;
	}
	
	protected OffsetsLinesFactory getLinesFactory(ConversionInfo[] converters, LinesFactoryBuilder lfBuilder) {
		int[] cols = getRecordOffsetsArray(converters);
		return createFixedColumnsLineFactory(cols, lfBuilder);
	}

	protected OffsetsLinesFactory createFixedColumnsLineFactory(int[] cols, LinesFactoryBuilder lfBuilder) {
		return lfBuilder.create(cols);
	}

}