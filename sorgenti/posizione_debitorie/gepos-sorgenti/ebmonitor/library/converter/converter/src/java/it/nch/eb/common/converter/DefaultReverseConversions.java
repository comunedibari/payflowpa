/**
 * Created on 11/giu/08
 */
package it.nch.eb.common.converter;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.NumberConverterInsertDecimalSeparator;
import it.nch.eb.flatx.bean.types.SizedConverterImpl;
import it.nch.eb.flatx.flatmodel.ConverterFactory;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DefaultReverseConversions extends ConvertersMapper {
	
	private static final long	serialVersionUID	= 2513575542050309987L;

	/**
	 * @author gdefacci
	 */
	public static class TrimConvertersFactory implements ConverterFactory {

		private static final long	serialVersionUID	= -8601034277299190265L;

		public Converter createConverter(final Converter converter) {
			return new Converter() {

				private static final long	serialVersionUID	= 8477066259196585112L;

				public String encode(String stringModel) {
					String res = converter.encode(stringModel).trim();
					if ("".equals(res)) return null;
					return res;
				}
				
			};
		}
	}

	public static class FromFilledNumberToDecimalNumber implements ConverterFactory {

		private static final long	serialVersionUID	= 4564996073084553599L;
		private final int decimalPos;
		
		public FromFilledNumberToDecimalNumber(int decimalPos) {
			this.decimalPos = decimalPos;
		}

		public Converter createConverter(Converter converter) {
			return new NumberConverterInsertDecimalSeparator( decimalPos, "0");
		}
	}
	
	public static class StripLeadingConverterFactory implements ConverterFactory {

		private static final long	serialVersionUID	= 3529708977436350709L;
		private final String toStrip;
		
		public StripLeadingConverterFactory (String toStripStr) {
			this.toStrip = toStripStr;
		}

		public Converter createConverter(Converter converter) {
			return new Converter() {

				private static final long	serialVersionUID	= 2400871898433401849L;

				public String encode(String stringModel) {
					return StringUtils.removeLeading(stringModel, toStrip);
				}
				
			};
		}
	}


	public static final ConverterFactory rev5Decimals = new FromFilledNumberToDecimalNumber(5);
	public static final ConverterFactory rev3Decimals = new FromFilledNumberToDecimalNumber(3);
	public static final ConverterFactory rev2Decimals = new FromFilledNumberToDecimalNumber(2);
	public static final ConverterFactory rev10Decimals = new FromFilledNumberToDecimalNumber(10);
	
	public static final ConverterFactory trimmersFactory = new TrimConvertersFactory();
	public static final ConverterFactory stripLeading0s = new StripLeadingConverterFactory("0");

	public DefaultReverseConversions() {
		this.put(SizedConverterImpl.class, trimmersFactory);
		
		DateFormat outputFormat = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.0Z'");
		this.put(BaseConverters.dateTime19ToXml, new DateToDateConverterFactory(outputFormat));
		
		this.put(BaseConverters.fd_number21_10  , rev10Decimals );
		this.put(BaseConverters.fd_number11_10  , rev10Decimals );
		this.put(BaseConverters.fd_number13_2 	, rev2Decimals );
		this.put(BaseConverters.fd_number15_2 	, rev2Decimals );
		this.put(BaseConverters.fd_number17_2 	, rev2Decimals );
		this.put(BaseConverters.fd_number18_3 	, rev3Decimals );
		this.put(BaseConverters.fd_number18_5 	, rev5Decimals );
		this.put(BaseConverters.fd_number20_2 	, rev2Decimals );
		this.put(BaseConverters.fd_number23_5 	, rev5Decimals );
		this.put( BaseConverters.numb2			, stripLeading0s );
		this.put( BaseConverters.numb3			, stripLeading0s );
		this.put( BaseConverters.numb4 			, stripLeading0s );
		this.put( BaseConverters.numb5 			, stripLeading0s );
		this.put( BaseConverters.numb7 			, stripLeading0s );
		this.put( BaseConverters.numb9 			, stripLeading0s );
		this.put( BaseConverters.numb15 		, stripLeading0s );
		this.put( BaseConverters.numb20 		, stripLeading0s );
		this.put( BaseConverters.numb35 		, stripLeading0s ); 		
	}
}