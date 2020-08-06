/**
 * Created on 06/mar/08
 */
package it.nch.eb.flatx.files;

import it.nch.eb.flatx.files.model.TokenizedLine;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;


/**
 * @author gdefacci
 */
public class IgnoreErrorsLinesFactory extends FixedColumnsLineFactory {

	public static final LinesFactoryBuilder builder = new LinesFactoryBuilder() {

		public OffsetsLinesFactory create(int[] cols) {
			return new IgnoreErrorsLinesFactory(cols);
		}
		
	};
	
	public IgnoreErrorsLinesFactory(int[] columns) {
		super(columns);
	}

	public TokenizedLine create(String iineContent) {
		return TokenizationUtil.createIgnoreMissingTokensTokenizer(iineContent, getColumns(), null);
	}
	
	public String[] splitLine(String line, IConversionInfo[] converters) {
		
		String[] values = create(line).all();
		
		if (values.length > converters.length) {
			throw new IllegalStateException("this is probably a bug");
		}
		
		if (converters.length!=values.length) {
			String[] newValues = new String[converters.length];
			System.arraycopy(values, 0, newValues, 0, values.length);
			return newValues;
		} else {
			return values;			
		}
	}
	
}
