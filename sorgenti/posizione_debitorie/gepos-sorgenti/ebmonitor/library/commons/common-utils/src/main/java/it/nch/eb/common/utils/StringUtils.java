package it.nch.eb.common.utils;

import it.nch.eb.common.utils.bindings.IBindingManager;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {

//	public static void main(String[] args) {
//		StringUtils util = new StringUtils();
//		String prePad = "ABCDE";
//		String prePad2 = "ABCDE";
//		System.out.println("prePad = " + prePad + ", size = " + prePad.length());
//		String afterPad = util.rightPad(prePad, 10, ' ');
//		System.out.println("after right Pad = " + afterPad + ", size = " + afterPad.length());
//
//		String afterLeftPad = util.leftPad(prePad, 10, ' ');
//		System.out.println("afterLeft Pad = " + afterLeftPad + ", size = " + afterLeftPad.length());
//	}

	public static final char	PATH_SEGMENT_SEPARATOR	= '/';

	public static String truncate(String input, int len) {
		if (input == null)
			return null;
		return input.substring(0, java.lang.Math.min(input.length(), len));
	}

	public synchronized String rightPad(String str, int size, char padChar)
	{
	  StringBuffer padded = new StringBuffer(str);
	  while (padded.length() < size)
	  {
	    padded.append(padChar);
	  }
	  return padded.toString();
	}

	public synchronized String leftPad(String str, int size, char padChar)
	{
	  StringBuffer toBepadded = new StringBuffer(str);
	  int missPad = size-str.length();
	  StringBuffer pad = new StringBuffer(missPad);
	  while (pad.length() < missPad)
	  {
	    pad.append(padChar);
	  }
	  return pad.append(toBepadded).toString();
	}


	public static String xmlWrap(String tagName, String tagValue) {
		return xmlWrap(tagName, tagValue, true);
	}

	public static String getStringValue(IBindingManager bm, String key) {
		if (key==null || bm==null) throw new IllegalStateException();
		Object res = bm.get(key);
		if (res==null) return null;
		if (res instanceof String) return (String) res;
		return res.toString();
	}

	public static String xmlWrap(String tagName, String tagValue, boolean noElementOnEmptyValue) {
		if (tagName==null) throw new IllegalStateException();
		if (tagValue==null && noElementOnEmptyValue) return "";
		else if (tagValue==null) return "<"+tagName+"/>";
		else return "<"+tagName+">"+tagValue+"</"+tagName+">";
	}

	public static String findReplace(String input,String sfind,String sreplace){
		if ((input==null) || (sfind==null) || (sreplace==null)) {
			String msg = "something is null :\ninput " + input + "\nsfind : " + sfind + "\nsreplace : " + sreplace;
			throw new NullPointerException(msg);
		}
		Pattern pattern  = Pattern.compile(sfind);
		Matcher matcher =  pattern.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, sreplace);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String nullSafeReplaceAll(String input, String what, String with) {
		boolean withIsNull = with==null;
		if ((input==null) || (what==null) || withIsNull) return input;
		if (input.indexOf(what)<0) return input;

		String replacement = withIsNull ? "" : with;
		return input.replaceAll(what, replacement);
	}

	public static String capitalized(String str) {
		if (str==null) return str;
		String strAux = str.trim();
		if (strAux.length()<1) return strAux;
		if (strAux.length()==1) return strAux.substring(0, 1).toUpperCase();
		return Character.toUpperCase(strAux.charAt(0)) + strAux.substring(1);
	}

	public static String decapitalized(String str) {
		if (str==null) return str;
		String strAux = str.trim();
		if (strAux.length()<1) return strAux;
		if (strAux.length()==1) return strAux.substring(0, 1).toLowerCase();
		return Character.toLowerCase(strAux.charAt(0)) + strAux.substring(1);
	}

	public static String concatPaths(String pre, String realPath) {
		return concatPathsSB(new StringBuffer(pre), realPath, PATH_SEGMENT_SEPARATOR ).toString();
	}

	public static String getSimpleName(Class klass) {
		String simpleName = klass.getName();
		return simpleName.substring(simpleName.lastIndexOf(".")+1);
	}

	public static StringBuffer concatPathsSB(StringBuffer pre, String suffix, String pathSegmentSep) {
		return concatPathsSB(pre, suffix, PATH_SEGMENT_SEPARATOR);
	}

	public static StringBuffer concatPathsSB(StringBuffer pre, String suffix, char pathSegmentSeparator) {
		char lastChar = pre.charAt(pre.length()-1);
		char firstSuffixChar = suffix.charAt(0);
		if ((lastChar == pathSegmentSeparator && firstSuffixChar == pathSegmentSeparator)) {
			pre.append(suffix.substring(1));
		} else if ((lastChar != pathSegmentSeparator && firstSuffixChar!=pathSegmentSeparator)) {
			pre.append( pathSegmentSeparator );
			pre.append( suffix );
		} else {
			pre.append(suffix);
		}
		return pre;
	}

	public static String concatPaths(String[] paths) {
		if (paths==null || paths.length == 0) return "";
		StringBuffer pre = new StringBuffer(paths[0]);
		for (int i = 1; i < paths.length; i++) {
			concatPathsSB(pre, paths[i], PATH_SEGMENT_SEPARATOR);
		}
		return pre.toString();
	}

	public static String[] stringArrayConcat(String[] array, String newTail) {
		int newTailPosition = array.length;
		String[] newArray = new String[newTailPosition+1];
		System.arraycopy(array, 0, newArray, 0, newTailPosition);
		newArray[newTailPosition] = newTail;
		return newArray;
	}

	static Set defaultExcludeSet = new TreeSet(Arrays.asList(new String[] {"class"}));

	public static String toString(Object bean) {
		return toStringExcluding(bean, defaultExcludeSet);
	}

	public static String toString(Iterator it) {
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			Object obj = it.next();
			sb.append(obj.toString());
		}
		return sb.toString();
	}

	public static String toStringExcluding(Object bean, Set/*<String>*/ propertiesToExclude) {
		StringBuffer result = new StringBuffer();
		try {
			BeanInfo infos = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] descriptors = infos.getPropertyDescriptors();
			for (int i = 0; i < descriptors.length; i++) {
				PropertyDescriptor descriptor = descriptors[i];
				String attributeName = descriptor.getName();
				if (!propertiesToExclude.contains(attributeName)) {
					String value;
					try {
						Object val = descriptor.getReadMethod().invoke(bean, new Object[] {});
						if (val!=null) {
							value = val.toString();
						} else {
							value = "NULL";
						}
					} catch (Exception e) {
						value = "COULD-NOT-COMPUTE" ;
					}
					result.append( attributeName +  "[" + value + "]\n" );
				}
			}
		} catch (Exception e) { /*IGNORE*/ }
		return result.toString();
	}

	public static String toString(Map map) {
		StringBuffer result = new StringBuffer();
		result.append("map :\n");
		try {
			for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				result.append("key[" + key + "] value[" + val + "]\n");
			}
		} catch (Exception e) { /*IGNORE*/ }
		return result.toString();
	}

	public static String toString(Object[] objects) {
		StringBuffer result = new StringBuffer("array[ ");
		for (int i = 0; i < objects.length; i++) result.append(objects[i]).append(", ");
		return result.toString() + " ]";
	}

	public static String toString(Collection objects) {
		StringBuffer result = new StringBuffer("collection[ ");
		for (Iterator it = objects.iterator(); it.hasNext();) result.append(it.next()).append(", ");
		return result.toString() + " ]";
	}

	public static boolean find(String source, String toFind) {
		Pattern pattern  = Pattern.compile(toFind);
		Matcher matcher =  pattern.matcher(source);
		return matcher.find();
	}

	 public static final String longestCommonPrefix(String a, String b) {
		int max = Math.min(a.length(), b.length());
		for (int i = 0; i < max; ++i) {
			if (a.charAt(i) != b.charAt(i)) {
				return a.substring(0, i);
			}
		}
		return a.length() == max ? a : b;
	}

	public static boolean isUpperCase(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isLetter(ch))
				if (Character.isLowerCase(ch))
					return false;
		}
		return true;
	}

	public static boolean isLowerCase(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isLetter(ch))
				if (Character.isUpperCase(ch))
					return false;
		}
		return true;
	}

	public static String removeTrailing(String value, String sfx) {
		if (!value.endsWith(sfx)) return value;
		else {
			int idx = value.lastIndexOf(sfx);
			String sub1 = value.substring(0,idx);
			return removeTrailing(sub1, sfx);
		}
	}

	public static String removeLeading(String value, String prfx) {
		if (!value.startsWith(prfx)) return value;
		else {
			String sub1 = value.substring(prfx.length(), value.length());
			return removeLeading(sub1, prfx);
		}
	}

	public static boolean isEmptyString(String str) {
		if (str==null) return true;
		else if (str.trim().equals("")) return true;
		else return false;
	}
	public static boolean areEquals(String s1, String s2) {
		if (s1==null && s2==null) return true;
		else if (s1!=null && s2!=null) return s1.equals(s2);
		else return false;
	}

	/**
	 * concatena n. len xpaths segments in paths a partire dal i-esimo segmento a xpath, ritornando il risultato della
	 * concatenazione
	 */
	public static String concatPaths(final String[] paths, int startIdx, int len, String xpath) {
		StringBuffer res = new StringBuffer();
		int flen = ((len + startIdx) < paths.length) ? startIdx + len : paths.length;
		for (int i = startIdx; i < flen; i++) {
			String pth = paths[i];
			if (pth.length()>0) {
				concatPathsSB(res, pth, PATH_SEGMENT_SEPARATOR);
			}
		}
		concatPathsSB(res, xpath, PATH_SEGMENT_SEPARATOR);
		return res.toString();
	}

	public static String concatPaths(String[] paths, String xpath) {
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < paths.length; i++) {
			String pth = paths[i];
			if (pth.length()>0) {
				concatPathsSB(res, pth, PATH_SEGMENT_SEPARATOR);
			}
		}
		concatPathsSB(res, xpath, PATH_SEGMENT_SEPARATOR);
		return res.toString();
	}

	public static String packageToFolder(String packageName) {
		String res = packageName.replaceAll("\\.", "/");
		return res;
	}

	public static String toConstName(String camelCaseName) {
		StringBuffer sb = new StringBuffer();
		int i=0;
		while ( i<camelCaseName.length()) {
			char ch = camelCaseName.charAt(i);
			if (Character.isUpperCase( ch )) {
				if (i>0) {
					sb.append("_");
				}
				sb.append(ch);
				i++;
				char ch1 = camelCaseName.charAt(i);
				while (i <camelCaseName.length() && Character.isUpperCase(ch1)) {
					if (Character.isLowerCase(camelCaseName.charAt(i+1))) {
						sb.append("_");
					}
					sb.append(ch1);
					i++;
					if (i < camelCaseName.length()) ch1 = camelCaseName.charAt(i);
				}
			} else {
				sb.append(Character.toUpperCase(ch));
				i++;
			}
		}
		return sb.toString();
	}

	public static String trimTrailing(String value) {
		return trimTrailing(value, ' ');
	}

	public static String trimTrailing(String value, String trailing) {
		int idx = value.indexOf(trailing);
		if (idx>-1) {
			String nw = value.substring(0, idx);
			return trimTrailing(nw, trailing);
		} else {
			return value;
		}
	}

    public static String trimTrailing(String value, char trimChar) {
        int endIdx = 0;
        int len = value.length();
        int i = len -1;
        boolean found = false;
        while (i >= 0 && !found) {
            if (value.charAt(i) != trimChar) {
                endIdx = i;
                found=true;
            }
            i --;
        }
        return value.substring(0, endIdx+1);
    }

    public static String concatPackages(String[] pkgParts) {
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < pkgParts.length; i++) {
			String pkg = pkgParts[i];
			if (pkg.startsWith(".")) {
				pkg = pkg.substring(1);
			}
			if (pkg.endsWith(".")) {
				pkg = pkg.substring(0, pkg.length()-1);
			}
			sb.append(pkg.toLowerCase());
			if (i < pkgParts.length - 1) {
				sb.append(".");
			}
		}

    	return sb.toString();
    }

    public static String[] splitWords(String words[], String sep) {
		List/* <String> */res = new ArrayList();
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			String[] parts = word.split(sep);
			for (int j = 0; j < parts.length; j++) {
				String prt = parts[j];
				if (prt != null && prt.trim().length() > 0)
					res.add(prt.trim());
			}
		}
		return (String[]) res.toArray(new String[0]);
	}

	public static String[] splitWord(String word, String[] seps) {
		String[] parts = new String[] { word };
		for (int i = 0; i < seps.length; i++) {
			String sep = seps[i];
			parts = splitWords(parts, sep);
		}
		return parts;
	}

	public static String[] camelSplitWords(String words[]) {
		List/* <String> */res = new ArrayList();
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			String[] parts = camelSplit(word);
			for (int j = 0; j < parts.length; j++) {
				String prt = parts[j];
				if (prt != null && prt.trim().length() > 0)
					res.add(prt.trim());
			}
		}
		return (String[]) res.toArray(new String[0]);
	}

	public static String[] camelSplit(String camelCaseName) {
		if (camelCaseName == null || camelCaseName.trim().length() == 0)
			return new String[0];

		if (isUpperCase(camelCaseName)) return new String[] {camelCaseName.toLowerCase() };
		else if (isLowerCase(camelCaseName)) return new String[] { camelCaseName };

		StringBuffer sb = new StringBuffer();
		List/* <String> */res = new ArrayList();
		int i = 0;
		while (i < camelCaseName.length()) {
			char ch = camelCaseName.charAt(i);
			if (Character.isUpperCase(ch)) {
				if (i > 0) {
					res.add(sb.toString());
					sb = new StringBuffer();
				}
				sb.append(ch);
				i++;
				if (i < (camelCaseName.length()-1)) {
					char ch1 = camelCaseName.charAt(i);
					while (i < (camelCaseName.length()-1) && Character.isUpperCase(ch1)) {
						if (Character.isLowerCase(camelCaseName.charAt(i + 1))) {
							res.add(sb.toString());
							sb = new StringBuffer();
						}
						sb.append(ch1);
						i++;
						if (i < camelCaseName.length())
							ch1 = camelCaseName.charAt(i);
					}
				}
			} else {
				sb.append(ch);
				i++;
			}
		}
		if (sb.length() > 0)
			res.add(sb.toString());
		return (String[]) res.toArray(new String[0]);
	}

	public static boolean pathStartsWith(String xpath, String chldXPth) {
		String t1 = xpath.trim();
		String t2 = chldXPth.trim();
		t1 = removeLeading(t1, "/");
		t2 = removeLeading(t2, "/");
		return t1.startsWith(t2);
	}

	public static String removeLeadingPath(String xpath, String prfx) {
		String t1 = xpath.trim();
		String t2 = prfx.trim();
		t1 = removeLeading(t1, "/");
		t2 = removeLeading(t2, "/");
		return removeLeading(t1, t2);
	}

	public static String join(String[] arr, String sep) {
		StringBuffer sb = new StringBuffer();
		if (arr.length > 0) {
			sb.append(arr[0]);
			for (int i=1; i < arr.length; i++) {
				sb.append(sep);
				sb.append(arr[i]);
			}
		}
		return sb.toString();
	}

}
