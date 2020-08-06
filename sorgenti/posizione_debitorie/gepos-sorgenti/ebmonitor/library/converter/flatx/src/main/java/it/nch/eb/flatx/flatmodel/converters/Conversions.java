package it.nch.eb.flatx.flatmodel.converters;

import java.io.Serializable;
import java.util.regex.Pattern;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.CoreConverters;
import it.nch.eb.flatx.bean.types.DateSizedConverterImpl;
import it.nch.eb.flatx.bean.types.DateTryConverter;
import it.nch.eb.flatx.bean.types.DateValidateConverter;
import it.nch.eb.flatx.bean.types.FillerConverter;
import it.nch.eb.flatx.bean.types.DateToDateConverter;
import it.nch.eb.flatx.bean.types.NumberConverterNoDecimalSeparator;
import it.nch.eb.flatx.bean.types.SizedConverterImpl;
import it.nch.eb.flatx.bean.types.TryConverter;
import it.nch.eb.flatx.bean.types.TypedConverterImpl;

public class Conversions implements Serializable {
	
	private static final long	serialVersionUID	= -8931918699275782765L;
	public static final String	DEFAULT_FILLER	= " ";
	public static final int		DEFAULT_ALIGN	= Alignments.LEFT;
	
	
	public static final class TrimConverter extends ClassIdentityConverter implements Converter {

		private static final long	serialVersionUID	= 2866105866368131872L;

		public String encode(String stringModel) {
			return stringModel.trim();
		}
		
		public String toString() {
			return "TrimConverter()";
		}
	}
	


	public static final class StringAsIsConverter extends ClassIdentityConverter implements Converter {

		private static final long	serialVersionUID	= 2866105866368131873L;

		public String encode(String stringModel) {
			return stringModel;
		}
		
		public String toString() {
			return "StringAsIsConverter()";
		}
	}

	
	public static abstract class NumberConverter extends TypedConverterImpl {

		private static final Pattern	numberPattern = Pattern.compile("[\\d]*"); 
		private static final long	serialVersionUID	= -4205839657064800266L;

		public NumberConverter(Class propertyClass) {
			super(propertyClass);
		}

		public String encode(String rawValue) {
			String res = rawValue.trim();
			if (numberPattern.matcher(res).matches()) return res;
			else throw new NumberFormatException(rawValue);
		}
		
		public String toString() {
			return "NumberConverter(" + getPropertyClass().getName() + ")";
		}
		
	}

	public static Converter TRIMMER = new TrimConverter();
	public static Converter AS_IS = new StringAsIsConverter();
	
	public final Converter LONG = new NumberConverter(Long.class) {

		private static final long	serialVersionUID	= -5054454907858994334L;

		public String toString() {
			return "LongConverter";
		}
	};

	public final Converter INTEGER =  new NumberConverter(Integer.class) {

		private static final long	serialVersionUID	= 5165744528471377719L;

		public String toString() {
			return "IntegerConverter";
		}
		
	};

	public final Converter TRIMMED_STRING = TRIMMER;

	public FillerConverter createSizedType(int len) {
		return new SizedConverterImpl(len, DEFAULT_FILLER, DEFAULT_ALIGN, TRIMMED_STRING);
	}
	
	public FillerConverter createSizedType(final Converter cnv, int len) {
		return new SizedConverterImpl(len, DEFAULT_FILLER, DEFAULT_ALIGN, cnv);
	}

	public FillerConverter createSizedType(Converter cnv, int len, String filler, int alignment) {
		return new SizedConverterImpl(len,filler, alignment, cnv);
	}
	
	public FillerConverter createSizedType(int len, String filler, int alignment) {
		return new SizedConverterImpl(len,filler, alignment, TRIMMED_STRING);
	}

	public FillerConverter createDateSizedType(DateTryConverter dateCnv) {
		return new DateSizedConverterImpl(" ", Alignments.LEFT, dateCnv);
	}
	
	
	/**
	 * handle null value parameters replacing them with default values
	 */
	public FillerConverter createSizedType(Converter type, int len, String filler, Integer alignment) {
		return createSizedType(	type == null ? CoreConverters.IDENTITY : type, 
								len, 
								filler == null ? DEFAULT_FILLER : filler, 
								alignment == null ? DEFAULT_ALIGN : alignment.intValue());
	}
	
	public Converter fromXsDateTo(String dateFormat) {
		return new DateToDateConverter("yyyy'-'MM'-'dd'T'HH':'mm':'ss", dateFormat);
	}
	
	public Converter fromSimpleDateTo(String dateFormat) {
		return new DateToDateConverter("yyyy-MM-dd", dateFormat);
	}

	public final NumberConverterNoDecimalSeparator[] NUMBER_FIXED_DECIMALS = new NumberConverterNoDecimalSeparator [] {
			new NumberConverterNoDecimalSeparator(0),
			new NumberConverterNoDecimalSeparator(1),
			new NumberConverterNoDecimalSeparator(2),
			new NumberConverterNoDecimalSeparator(3),
			new NumberConverterNoDecimalSeparator(4),
			new NumberConverterNoDecimalSeparator(5),
			new NumberConverterNoDecimalSeparator(6),
			new NumberConverterNoDecimalSeparator(7),
			new NumberConverterNoDecimalSeparator(8),
			new NumberConverterNoDecimalSeparator(9),
			new NumberConverterNoDecimalSeparator(10),
//			new NumberConverterNoDecimalSeparator(11),
//			new NumberConverterNoDecimalSeparator(12),
//			new NumberConverterNoDecimalSeparator(13),
//			new NumberConverterNoDecimalSeparator(14),
			new NumberConverterNoDecimalSeparator(15),
//			new NumberConverterNoDecimalSeparator(16),
//			new NumberConverterNoDecimalSeparator(17),
//			new NumberConverterNoDecimalSeparator(18),
	};
	
//	TODO: following fields are never used
	public final Converter REPLACE_XML_ENTITIES = new ReplaceXmlEntitiesConverter();
	public final Converter REPLACE_XML_RESERVED_CHARS_CONVERTER = new ReplaceXmlReservedCharsConverter();
	
	public final Converter ISO_DATE_TIME = new TryConverter( new Converter[] {
			new DateValidateConverter("yyyy'-'MM'-'dd'T'HH':'mm':'ss"),
			new DateValidateConverter("yyyy'-'MM'-'dd'T'HH'.'mm'.'ss"),
	}) {
		private static final long serialVersionUID = 1L;
		public String toString() {
			return "data in formato 'yyyy-MM-ddTHH:mm:ss'";
		}
	};
	
	public final DateTryConverter ISO_DATE_TIME_TO_XML = new DateTryConverter( new String[] {
			"yyyy'-'MM'-'dd'T'HH':'mm':'ss",
			"yyyy'-'MM'-'dd'T'HH'.'mm'.'ss",
		}) {
		private static final long serialVersionUID = 1L;
		public String toString() {
			return "data in formato 'yyyy-MM-ddTHH:mm:ss'";
		}
	};
	
	public final Converter DATE10 		= new DateToDateConverter("yyyy-MM-dd");
	public final Converter DATE10_IT 	= new DateToDateConverter("dd-MM-yyyy");
	public final Converter DATE8		= new DateToDateConverter("ddMMyyyy");
	public final Converter DATE6		= new DateValidateConverter("ddMMyy");
	
	
}
