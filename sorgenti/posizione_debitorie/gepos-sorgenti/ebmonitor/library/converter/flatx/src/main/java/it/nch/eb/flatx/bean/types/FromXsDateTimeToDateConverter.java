/**
 * Created on 09/gen/08
 */
package it.nch.eb.flatx.bean.types;

import java.text.SimpleDateFormat;


/**
 * FIXME: rename
 * @author gdefacci
 */
public class FromXsDateTimeToDateConverter extends DateToDateConverter {
	
	public static final String	XS_DATE_FORMAT_STRING	= "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
	private static final long	serialVersionUID	= 5146627694606492235L;
//	private FromXsDateTimeToDateConverter(DateFormat dateformat) {
//		super(new SimpleDateFormat( XS_DATE_FORMAT_STRING ), dateformat);
//	}

	public FromXsDateTimeToDateConverter(String dateRegexp) {
		super(XS_DATE_FORMAT_STRING , dateRegexp);
	}

	public String encode(String stringModel) {
		if (stringModel==null || "".equals(stringModel.trim())) return null;
		String realValue = null;
		if (stringModel.length()>20) realValue = stringModel.substring(0, 20);
		else realValue = stringModel;
		return super.encode(realValue);
	}
	
}
