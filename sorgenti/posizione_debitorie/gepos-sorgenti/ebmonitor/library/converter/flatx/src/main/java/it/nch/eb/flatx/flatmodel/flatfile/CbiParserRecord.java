/**
 * Created on 13/feb/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import it.nch.eb.flatx.bean.types.FillerConverter;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.bean.types.StripperConverter;
import it.nch.eb.flatx.files.model.LineMatcher;
import it.nch.eb.flatx.files.model.TokenizedLine;
import it.nch.eb.flatx.flatmodel.objectconverters.ObjectConverters;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;


/**
 * @author gdefacci
 */
public class CbiParserRecord extends ParserRecord implements ObjectConverters {
	
//	public static final String TIPO_RECORD_BINDINGS_ID = "tipoRecord";

	public CbiParserRecord(ObjectBuilder objectBuilder, LineMatcher matcher) {
		super(objectBuilder, matcher);
	}

	public CbiParserRecord(ObjectBuilder objectBuilder, int n, final String tipoRecord) {
		this(objectBuilder, createTipoRecordMatcher(n, tipoRecord));
	}
	
	public CbiParserRecord(ObjectBuilder objectBuilder, final String tipoRecord) {
		this(objectBuilder, 1, tipoRecord);
	}
	
	/**
	 * create a matcher that checks if token n.<code>idx</code> match <code>tipoRecord</code>
	 * @param tipoRecord
	 * @return
	 */
	public static LineMatcher createTipoRecordMatcher(final int idx, final String tipoRecord) {
		return new LineMatcher() {
	
			public boolean match(TokenizedLine tokenizedLine) {
				return tokenizedLine.get(idx).equals(tipoRecord);
			}

			public String toString() {
				return "line matcher for tipo record [" + tipoRecord + "]";
			}
			
		};
	}
	
	public static ObjectBuilder createObjectBuilder(Class klass) {
		return new NewInstanceObjectBuilder(klass);
	}
	
	private int pos = 1;
	protected int getPosAndInc() {
		int res = pos;
		pos ++;
		return res;
	}
	
	public ToObjectConversionInfo create(FillerConverter delegate) {
		return new ToObjectConversionInfo(getPosAndInc(), delegate);
	}

	private SizedConverter adaptConverter(FillerConverter delegate) {
		return new StripperConverter(delegate);
	}
	
	public ToObjectConversionInfo create(FillerConverter delegate, ToObjectConverter toObjectConverter) {
		return new ToObjectConversionInfo(getPosAndInc(), delegate, toObjectConverter);
	}

}
