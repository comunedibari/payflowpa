package it.nch.is.fo.util.xml;

public class XMLIndenter {
		
	static final String TAB = "\t";
	static final String NEWLINE = "\r\n";
	
	public static String format(String xml) {
		return format(xml, false);
	}
	
	public static String format(String xml, boolean forHTML) {
		StringBuffer sb = new StringBuffer();
		
		if (isAnXML(xml)) {
			int level = -1;
			int cursor = 0;
			int prot = 0;
			while (cursor >= 0 && prot < 10000) {
				prot ++;
				cursor ++;
				int beginIndex = xml.indexOf("<", cursor - 1);
				if (beginIndex >= 0) {
					int endIndex;
					String charAfter = xml.substring(beginIndex + 1, beginIndex + 2);
					if (charAfter.equals("/")) {
						level --;
						endIndex = xml.indexOf(">", cursor);
						if (endIndex > cursor) {
							int endValue = xml.indexOf("</", cursor - 1);
							String value = xml.substring(cursor - 1, endValue);
							String endTag = xml.substring(endValue, endIndex + 1);
							if (value != null && !value.equals("")) {
								addLine(sb, value, level + 2);
							}
							addLine(sb, endTag, level + 1);
							cursor = endIndex + 1;
						}
					} else {
						level ++;
						endIndex = xml.indexOf(">", beginIndex);
						String charBefore = xml.substring(endIndex - 1, endIndex);
						if (charBefore.equals("/")) {
							level --;
						}
						if (endIndex > beginIndex) {
							String line = xml.substring(beginIndex, endIndex + 1);
							addLine(sb, line, level);
							cursor = endIndex + 1;
						}
					}
				} 
			}
			if (forHTML) {
				String tmp = sb.toString();
				tmp = replaceAll(tmp, "<", "&lt;");
				tmp = replaceAll(tmp, ">", "&gt;");
				sb = new StringBuffer(tmp);
			}
		} else {
			//
			// Not an XML...
			//
			if (xml == null) {
				xml = "";
			}
			sb.append(xml);
		}
		
		return sb.toString();
	}

	private static String replaceAll(String source, String what, String withWhat) {
		int a = 0;
		while (a >= 0) {
			a = source.indexOf(what);
			if (a >= 0) {
				source = replace(source, what, withWhat);
				//System.out.println(source);
			}
		}
		return source;
	}

	private static String replace(String source, String what, String withWhat) {
		int a = source.indexOf(what);
		if (a >= 0) {
			String before = source.substring(0, a);
			String after = source.substring(a + what.length());
			source = before + withWhat + after;
			return source;
		}
		return null;
	}

	private static boolean isAnXML(String xml) {
		if (xml == null) {
			return false;
		}
		int gt = xml.indexOf("<");
		return (gt >= 0);
	}

	private static void addLine(StringBuffer sb, String line, int level) {
		for (int l = 0; l < level; l++) {
			sb.append(TAB);
		}
		sb.append(line);
		sb.append(NEWLINE);
		
	}

	public static void main(String[] args) {
		String xml = null;
		xml = "<messages><info><type>BUSINESS</type><id>IMPORTAZIONE_ESITO_OK</id><params><param>DISPOSIZIONE_ERRATE=0</param><param>DISPOSIZIONE_INSERITE=3</param></params></info></messages>";
		
		System.out.println(replaceAll("Il baco del calo del malo", "a", "u"));
		
		String formatted = format(xml, true); 
		System.out.println(formatted);
		
		String doubleFormatted = format(formatted); 
		//	La doppia formattazione aggiunge degli a capo, ma lo posso tollerare
		System.out.println("=================================");
		System.out.println(doubleFormatted);
		
	}
}


