/**
 * 10/dic/2009
 */
package it.nch.eb.flatx.generator;

import jxl.Sheet;

/**
 * @author gdefacci
 */
public interface SheetFilter {

	boolean apply(Sheet sheet);
}
