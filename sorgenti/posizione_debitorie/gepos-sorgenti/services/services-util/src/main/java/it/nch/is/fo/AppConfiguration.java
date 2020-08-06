package it.nch.is.fo;

import java.math.BigDecimal;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationFactory;

/**
 * Gestisce l'accesso alle property presenti nel file di configurazione
 * "config.xml".
 * 
 * @author TODO
 * 
 */
public class AppConfiguration {

	static Configuration config = null;

	static {
		try {
			ConfigurationFactory factory = new ConfigurationFactory("config.xml");

			config = factory.getConfiguration();

		} catch (Exception e) {
			// log!

			e.printStackTrace();
		}
	}

	/**
	 * Recupera una stringa avente chiave paramName dal file di configurazione.
	 * Se non c'è lancia una Exception.
	 * 
	 * @param paramName
	 *            il nome della property da ricercare nel file di configurazione
	 * @return il valore nel file di configurazione della property avente la
	 *         chiave specificata in ingresso.
	 * @throws Exception
	 *             Se la property di nome paramName non è nel file di
	 *             configurazione.
	 */
	public static String getStringParameter(String paramName) throws Exception {

		String paramValue = config.getString(paramName);
		if (paramValue == null)
			throw new Exception("Missing parameter name: " + paramName + " into the property files");

		return paramValue;
	}

	public static BigDecimal getBigDecimalParameter(String paramName) throws Exception {

		BigDecimal paramValue = config.getBigDecimal(paramName);
		if (paramValue == null)
			throw new Exception("Missing parameter name: " + paramName + " into the property files");

		return paramValue;
	}

//	public static Double getDoubleParameter(String paramName) throws Exception {
//
//		Double paramValue = config.getDouble(paramName, new Double(0));
//		if (paramValue == null)
//			throw new Exception("Missing parameter name: " + paramName + " into the property files");
//
//		return paramValue;
//	}

//	public static List getList(String paramName) throws Exception {
//
//		List paramValue = config.getList(paramName);
//		if (paramValue == null)
//			throw new Exception("Missing parameter name: " + paramName + " into the property files");
//
//		return paramValue;
//	}

	// /*****************
	// *
	// * @param java.sql.dt
	// * @param flag
	// * @return
	// */
	// public static java.sql.Date getDate( Date dt , boolean flag ){
	//
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(dt);
	// /*
	// if( flag == true){
	// calendar.set(Calendar.HOUR_OF_DAY, 00);
	// calendar.set(Calendar.MINUTE, 00);
	// calendar.set(Calendar.SECOND, 00);
	// calendar.set(Calendar.MILLISECOND,000000);
	// }
	// else if( flag == false ){
	// calendar.set(Calendar.HOUR_OF_DAY, 23);
	// calendar.set(Calendar.MINUTE, 59);
	// calendar.set(Calendar.SECOND, 59);
	// calendar.set(Calendar.MILLISECOND,999999);
	// }
	// //TIMESTAMP ('2002-10-20-12.00.00.000000')
	// */
	// java.text.SimpleDateFormat sf = new
	// java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	// java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
	//
	//
	//
	//
	// return date;
	// }
	//
	// /*****************
	// *
	// * @param java.sql.dt
	// * @param flag
	// * @return
	// */
	// public static java.sql.Date getDate( java.util.Date dt ){
	//
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(dt);
	// java.text.SimpleDateFormat sf = new
	// java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	// java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
	//
	//
	//
	// return date;
	// }

	public static int getIntParameter(String paramName) throws Exception {
		try {
			int paramValue = config.getInt(paramName);
			return paramValue;
		} catch (Exception exc) {
			throw new Exception("Missing parameter name: " + paramName + " into the property files");
		}
	}

	// public static String getDecodedStringParameter(String paramName,String
	// codeKey, String descrKey) throws Exception {
	//
	// String paramValue = null;
	// if(paramName==null || paramName.equals(""))
	// return paramName;
	// Collection codeList = AppConfiguration.getList(codeKey);
	// Collection descList = AppConfiguration.getList(descrKey);
	// if (codeList!=null && descList!=null ){
	// Iterator codeIterator = codeList.iterator();
	// Iterator descIterator = descList.iterator();
	// while(codeIterator.hasNext() && descIterator.hasNext()){
	// String key = (String)codeIterator.next();
	// if (key!=null && key.equals(paramName))
	// paramValue=(String)descIterator.next();
	// break;
	// }
	// }
	// return paramValue;
	// }
	public static String getStringParameterWithNullValue(String paramName) throws Exception {

		return config.getString(paramName);

	}
}// end class
