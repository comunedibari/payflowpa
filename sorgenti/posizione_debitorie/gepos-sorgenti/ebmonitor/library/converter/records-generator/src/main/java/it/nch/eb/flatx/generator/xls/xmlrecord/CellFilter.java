/**
 * 21/apr/2009
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import jxl.Sheet;

public interface CellFilter {
	boolean match(Sheet sheet, int ridx);
	XRowInfo create(Sheet sheet, int ridx);
}