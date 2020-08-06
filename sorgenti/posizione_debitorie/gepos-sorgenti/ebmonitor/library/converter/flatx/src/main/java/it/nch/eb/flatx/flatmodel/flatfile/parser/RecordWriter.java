/**
 * Created on 03/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.FillerConverter;
import it.nch.eb.flatx.bean.types.ReversibleConverter;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.exceptions.ParserException;
import it.nch.eb.flatx.flatmodel.conversioninfo.ConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.ObjectCallback;
import it.nch.eb.flatx.flatmodel.converters.Conversions;
import it.nch.eb.flatx.flatmodel.converters.NullConverter;
import it.nch.eb.flatx.flatmodel.flatfile.LineTerminatedStringAction;
import it.nch.eb.flatx.flatmodel.objectconverters.ReversibleObjectConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.ToStringObjectConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.VoidToObjectConverter;
import it.nch.eb.flatx.flatmodel.verifiers.GetterStrategy;


/**
 * @author gdefacci
 */
public class RecordWriter extends BaseParserWriter {

	private ConversionInfo[]			cis;
	private ObjectCallback				beforeBean;
	private String						parserName;
	
	public RecordWriter(String name, ConversionInfo[] conversionInfos, LineTerminatedStringAction laction,
			ObjectCallback beforeEachBean) {
		super(laction, beforeEachBean);
		this.cis = conversionInfos;
		this.beforeBean = beforeEachBean;
		if (beforeBean==null) throw new IllegalStateException();
		this.parserName = name;
	}

	public void write(Object model, GetterStrategy getter) {
		if (beforeBean!=null) beforeBean.apply(model);
		for (int i = 0; i < cis.length; i++) {
			ConversionInfo conversionInfo = cis[i];
//			if (!NullConverter.isNull(conversionInfo.getConverter())) {
				String rawValue;
				
				try {
					rawValue = getPropertyValue(model, getter, conversionInfo);
				} catch (Exception e) {
					throw new RuntimeException("error getting property " + conversionInfo + " value from " + model , e);
				}

				if (rawValue == null) rawValue = "";
				writeField(conversionInfo, rawValue);
				
//			}
		}
		getAction().printLineTerminator();
	}

	public void writeField(ConversionInfo conversionInfo, String str) {
		FillerConverter strConverter = null;
		try {
			SizedConverter converter = conversionInfo.getConverter();
				if (!NullConverter.isNull(converter)) {
				strConverter = getReverseFillerConverter(converter);
				String realStr = strConverter.encode(str);
				getAction().execute(realStr);
			} else {
				getAction().execute(RegexpUtils.fill(" ", conversionInfo.getLength().intValue()));
			}
		} catch (Exception e) {
			throw new ParserException("error writing " + str + "(property " + conversionInfo.getName()
					+ (strConverter == null ? "" : ") using converter  " + strConverter.toString()), e);
		}
	}

	private String getPropertyValue(Object model, GetterStrategy getter, ToObjectConversionInfo conversionInfo) {
		ToObjectConverter objConverter = conversionInfo.getToObjectConverter();
		String propertyName = conversionInfo.getName();
		String str = null;
		if (!isVoid(conversionInfo)) {
			Object value = null;
			try {
				value = getter.getValueFrom(model, propertyName);
			} catch (Exception e1) {
				String klass = model == null ? "null" : model.getClass().getName();
				throw new ParserException("error converting property " + propertyName + " of object " + klass
						+ ") using conversionInfo  " + conversionInfo);
			}
			ToStringObjectConverter toStringConverter = getReverseToObjectConverter(objConverter);
			str = toStringConverter.convert(value);
		}
		return str;
	}

	private String getPropertyValue(Object model, GetterStrategy getter, ConversionInfo conversionInfo) {
		if (conversionInfo instanceof ToObjectConversionInfo) {
			return getPropertyValue(model, getter, (ToObjectConversionInfo) conversionInfo);
		}

		String propertyName = conversionInfo.getName();
		SizedConverter converter = conversionInfo.getConverter();
		String str = null;
		if (!NullConverter.isNull(converter)) {
			Object value = null;
			try {
				value = getter.getValueFrom(model, propertyName);
			} catch (Exception e1) {
				String klass = model == null ? "null" : model.getClass().getName();
				throw new ParserException("error converting property " + propertyName + " of object " + klass
						+ ") using conversionInfo  " + conversionInfo, e1);
			}
			if (value == null) str = "";
			else if (value instanceof String) str = (String) value;
			else
				str = value.toString();
		}
		return str;
	}

	private boolean isVoid(ToObjectConversionInfo converter) {
		return VoidToObjectConverter.isVoid(converter.getToObjectConverter());
	}

	private ToStringObjectConverter getReverseToObjectConverter(ToObjectConverter objConverter) {
		if (objConverter instanceof ReversibleObjectConverter) {
			return ((ReversibleObjectConverter) objConverter).getReverse();
		} else {
			return ToStringObjectConverter.DEFAULT;
		}
	}

	private FillerConverter getReverseFillerConverter(SizedConverter converter) {
		if (converter instanceof ReversibleConverter) {
			String filler = " ";
			int align = Alignments.LEFT;
			Converter reverse = ((ReversibleConverter) converter).getReverse();
			if (converter instanceof FillerConverter) {
				FillerConverter fc = (FillerConverter) converter;
				filler = fc.getFiller();
				align = fc.getAlignment();
			}
			return new Conversions().createSizedType(reverse, converter.getLength().intValue(), filler, align);
		}
		if (converter instanceof FillerConverter) return (FillerConverter) converter;
		return new FillerConverter.SizedConverterAdapter(converter);
	}

	public String toString() {
		return "record writer for " + parserName;
	}

}
