/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.flatmodel.objectconverters.converters.LongAsStringToDateConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.LongAsStringToSqlDateConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.LongAsStringToTimestampConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.ToBigDecimalConveter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.ToDoubleConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.ToIntConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.ToLongConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.ToYearConverter;


/**
 * @author gdefacci
 */
public interface ObjectConverters {
	
	ToObjectConverter toString = new TypeSafeToObjectConverter(String.class) {

		private static final long serialVersionUID = -209483969809969171L;

		public Object safeConvert(String str) {
			return str;
		}
		
	};

	ToObjectConverter toYear = new ToYearConverter();
	ToObjectConverter toInt = new ToIntConverter();
	ToObjectConverter toLong = new ToLongConverter();
	ToObjectConverter toDate = new LongAsStringToDateConverter();
	ToObjectConverter toSqlDate = new LongAsStringToSqlDateConverter();
	ToObjectConverter toSqlTimestamp = new LongAsStringToTimestampConverter();
	ToObjectConverter toDouble = new ToDoubleConverter();
	ToObjectConverter toBigDecimal = new ToBigDecimalConveter();
	
	ToObjectConverter toBytes = new ToBytesObjectConverter();
//	/**
//	 * convert a numeric String, where 2 last digits are the implicit decimal part to the corresponing 
//	 * BigDecimal:
//	 * es:
//	 * 		"2234" => 22.34
//	 * 		"2200" => 22
//	 */
	ToObjectConverter fromImporto2ToBigDecimal = new ToBigDecimalConveter(2);
	ToObjectConverter fromImporto2DecimalsToBigDecimal = fromImporto2ToBigDecimal; // TODO: alias remove
//	
//	ToObjectConverter fromImporto8ToBigDecimal = new ToBigDecimalConveter(8);
	
	
	

}
