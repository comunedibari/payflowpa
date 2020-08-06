/**
 * Created on 14/dic/07
 */
package it.nch.eb.flatx.flatmodel.converters;


/**
 * @author gdefacci
 */
public class XmlEntitiesUtils {

	/**
	 * mapping between xml entities and their relative string rapresentation
	 */
	public final static String[][]	replacements = new String[][] {
		{ "&quot;", "\"" },
		{ "&amp;",	"&" },
		{ "&apos;", "\'" },
		{ "&lt;", 	"<" },
		{ "&gt;", 	">" },
	};

	public static String fixup(String s) {
		StringBuffer sb = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			switch (c) {
			default:
				sb.append(c);
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * replace the mapped xmlentities with their relative string value
	 * @param s
	 * @return
	 */
	public static String replaceEnties(String s) {
		return replaceXmlEntities(s, replacements);
	}
	public static String restoreEnties(String s) {
		return restoreXmlEntities(s, replacements);
	}
	

	public static String replaceXmlEntities(String s, String[][] map) {
		String buff = s;
		for (int i = 0; i < map.length; i++) {
			String[] pair = map[i];
			buff = buff.replaceAll(pair[0], pair[1]);
		}
		return buff;
	}
	
	public static String restoreXmlEntities(String s, String[][] map) {
		String buff = s;
		for (int i = 0; i < map.length; i++) {
			String[] pair = map[i];
			buff = buff.replaceAll(pair[1],pair[0]);
		}
		return buff;
	}


}
