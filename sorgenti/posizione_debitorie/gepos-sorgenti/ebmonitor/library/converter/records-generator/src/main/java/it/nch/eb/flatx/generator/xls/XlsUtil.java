/**
 * Created on 26/mar/08
 */
package it.nch.eb.flatx.generator.xls;

import it.nch.eb.common.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.RGB;


/**
 * @author gdefacci
 */
public class XlsUtil {

	public static Workbook open(File f) {
		try {
			return Workbook.getWorkbook(f);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static String stripLeadingPath(String basePath2, String val) {
		if (!val.startsWith(basePath2)) {
			throw new IllegalStateException("the xpath : " + val 
					+ " isnt prefixed by " + basePath2);
		}
		String xpath = val.substring(basePath2.length());
		while (xpath.startsWith("/")) xpath = xpath.substring(1);
		return xpath;
	}
	
	public static boolean isIgnoreSheet(Sheet sheet) {
		return sheet.getName().startsWith("--");
	}
	
	public static String findCommonXPathPrefix(RowInfo[] aux) {
		String actual = null;
		for (int i = 1; i < aux.length; i++) {
			RowInfo rowInfo = aux[i];
			if (rowInfo.isXPath()) {
				String xpathExpression = rowInfo.getExpression().getValue();
				if (!xpathExpression.endsWith("/")) xpathExpression = xpathExpression + "/";
				if (actual==null) actual = xpathExpression;
				else {
					actual = StringUtils.longestCommonPrefix(actual, xpathExpression);
				}
//				se dopo aver construito actual, actual vale null, beh non c'e alcun common prefix
				if (actual==null || "".equals(actual.trim())) return null;
			}
		}
		int slashIdx = actual.lastIndexOf('/');
		return actual.substring(0, slashIdx);
	}
	
	public static String findCommonXPathPrefix(String[] xpaths) {
		String actual = null;
		for (int i = 0; i < xpaths.length; i++) {
			String xpathExpression = xpaths[i];
			if (!xpathExpression.endsWith("/")) xpathExpression = xpathExpression + "/";
			if (actual==null) actual = xpathExpression;
			else {
				actual = StringUtils.longestCommonPrefix(actual, xpathExpression);
			}
//			se dopo aver construito actual, actual vale null, beh non c'e alcun common prefix
			if (actual==null || "".equals(actual.trim())) return null;
		}
		if (actual!=null) {
			int slashIdx = actual.lastIndexOf('/');
			return actual.substring(0, slashIdx);
		} else 
			return null;
	}

	public static boolean areEmptyCells(Cell[] row) {
		for (int i = 0; i < row.length; i++) {
			if (row[i]!=null) {
				String cont = row[i].getContents();
				if ((cont!=null) && (!"".equals(cont.trim()))) return false;
			}
		}
		return true;
	}
	
	public static String fixNameCase(String part) {
		if (StringUtils.isUpperCase(part)) return part.toLowerCase();
		else return StringUtils.decapitalized(part);
	}

	public static String getLastSubPart(String contents, char sep) {
		int lastSubpartIdx = contents.lastIndexOf(sep);
		if (lastSubpartIdx<0) return null;
		return contents.substring(lastSubpartIdx+1);
	}
	
	public static boolean rgbMatch(RGB c1, RGB c2) {
		return c1.getBlue() == c2.getBlue() && c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen();
	}
	public static boolean colorsMatch(Colour c1, Colour c2) {
		return rgbMatch(c1.getDefaultRGB(), c2.getDefaultRGB());
	}
	
	public static String extractNameFromXPath(String cont) {
		String lastSubPart;
		String indexPart = null;
		String content = cont;
		indexPart = XlsUtil.getLastSubPart(content, '[');
		if (indexPart!=null) {
			indexPart = indexPart.substring(0, indexPart.length()-1);
			int squareBktsIdx = content.indexOf('[');
			content = content.substring(0, squareBktsIdx);
		}
		
		String res = null;
		lastSubPart = XlsUtil.getLastSubPart(content, '@');
		if (lastSubPart!=null) res = XlsUtil.fixNameCase(lastSubPart);
		else {
			lastSubPart = XlsUtil.getLastSubPart(content, '/');
			int colonPos = lastSubPart.indexOf(':');
			if (colonPos<0) res = XlsUtil.fixNameCase(lastSubPart);
			else {
				String[] parts = lastSubPart.split("[:]");
//				res = parts[0].toLowerCase() + "_" + StringUtils.decapitalized(parts[1]);
				res = XlsUtil.fixNameCase(parts[1]);
			}
		}
		if (indexPart!=null) {
			res = res + "_" + indexPart;
		}
		return res;
	}
	
	public static String getStringCellsContent(Cell[] cells) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			sb.append(cell.getContents() + " | ");
		}
		return sb.toString();
	}
	
	public  static String getContent(Sheet sheet, int col, int row) {
		Cell cl = getCell(sheet, col, row);
		return getContent(cl);
	}

	public static Cell getCell(Sheet sheet, int col, int row) {
		Cell cl = null;
		try {
			cl = sheet.getCell(col, row);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return cl;
	}

	public static String getContent(Cell cl) {
		if (cl==null) return null;
		String res = cl.getContents();
		if (res==null) return null;
		else if (res.trim().length() == 0) return null;
		else return res.trim();
	}
	
	public static Colour getTextColor(Sheet sheet, int col, int row) {
		Cell cl = getCell(sheet, col, row);
		return getTextColor(cl);
	}

	public static Colour getTextColor(Cell cl) {
		if (cl==null) return null;
		Colour res = cl.getCellFormat().getFont().getColour();
		if (res==null) return null;
		else return res;
	}
	
	public static boolean getBooleanContent(Sheet sheet, int col, int row) {
		Cell cl = sheet.getCell(col, row);
		return getBooleanContent(cl);
	}

	public static boolean getBooleanContent(Cell cl) {
		String str = cl.getContents().trim();
		return str.length() > 0;
	}

	public static String[] stripLeadingPath(String basePath, String[] args) {
		if (basePath==null) return args;
		String[] res = new String[args.length];
		for (int i = 0; i < args.length; i++) res[i] = stripLeadingPath(basePath, args[i]);
		return res;
	}

	public static boolean match(Pattern pattern, String content) {
		Matcher matcher = pattern.matcher(content);
		return matcher.matches();
	}

	public static final Pattern xpathPattern = Pattern.compile("([/][a-zA-Z0-9]+([:][a-zA-Z0-9]+)?)+([/]?([@][a-zA-Z0-9]+)?)?([\\[][0-9]+[\\]])?");
	static boolean isXPath(String str) {
		Matcher macther = xpathPattern.matcher(str);
		return macther.matches();
	}
	
	static final Pattern symbolPattern = Pattern.compile("([A-Z_\\s0-9]*)+");
	static boolean isGetSymbol(String str) {
		Matcher macther = symbolPattern.matcher(str);
		return macther.matches();
	}
	
	/*
	public static final Pattern singleQuatedPattern = Pattern.compile("[\'][a-zA-Z0-9_ ]*[\']");
	public static final Pattern doubleQuatedPattern = Pattern.compile("[\"][a-zA-Z0-9_ ]*[\"]");
	 */
	public static final Pattern singleQuatedPattern = Pattern.compile("[\'].*[\']");
	public static final Pattern doubleQuatedPattern = Pattern.compile("[\"].*[\"]");
	public static boolean isQuotedExpr(String str) {
		Matcher macther = singleQuatedPattern.matcher(str);
		boolean res = macther.matches();
		if (!res) {
			macther = doubleQuatedPattern.matcher(str);
			res = macther.matches();
		}
		return res;
	}
	
	public static String stripBorders(String bordedString) {
		return bordedString.substring(1, bordedString.length() -1);
	}
	
	public static OutputStream fileStream(File f) {
		try {
			return new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("cant find file " + f, e);
		}
	}

	public static String[] getSheetsName(Workbook wrkBk) {
		List sheetNames = new ArrayList();
		Sheet[] shts = wrkBk.getSheets();
		for (int i = 0; i < shts.length; i++) {
			if (!XlsUtil.isIgnoreSheet(shts[i])) {
				sheetNames.add(shts[i].getName());
			}
		}
		return (String[]) sheetNames.toArray(new String[0]);
	}
	
	public static String[] getAllSheetsName(Workbook wrkBk) {
		List sheetNames = new ArrayList();
		Sheet[] shts = wrkBk.getSheets();
		for (int i = 0; i < shts.length; i++) {
			sheetNames.add(shts[i]);
		}
		return (String[]) sheetNames.toArray(new String[0]);
	}
	
	public static Cell[] getColumn(Sheet sheet, int idx) {
		if (idx > sheet.getColumns()) return null;
		else
			try {
				return sheet.getColumn(idx);
			} catch (ArrayIndexOutOfBoundsException e) {
				return null;
			}
	}

	public static boolean columnsMatchStarting(Sheet sheet, int dscIdx, int colIdx,
			int startRow) {
		Cell[] dsc1Col = getColumn(sheet, colIdx);
		if (dsc1Col==null) return false;
		
		Cell[] dscCol = getColumn(sheet, dscIdx);
		
		int maxRow = dsc1Col.length > dscCol.length ? dsc1Col.length : dscCol.length; 

		boolean areEq = true;
		for (int i = startRow; i < maxRow && areEq; i++) {
			String c1 = (i < dscCol.length) ? XlsUtil.getContent(dscCol[i]) : null;
			String c2 = (i < dsc1Col.length) ? XlsUtil.getContent(dsc1Col[i]) : null;
			if (c1!=null && c2!=null) areEq = c1.equals(c2);
			else if (!(c1==null && c2==null)) areEq = false;
		}
		
		return areEq;
	}
	
	public static int max(int[] arr) {
		int res = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > res) res = arr[i];
		}
		return res;
	}

	public static String getFixedSheetName(String name) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length(); i++) 
			if (!Character.isWhitespace(name.charAt(i))) sb.append(name.charAt(i));
		return sb.toString();
	}
}
