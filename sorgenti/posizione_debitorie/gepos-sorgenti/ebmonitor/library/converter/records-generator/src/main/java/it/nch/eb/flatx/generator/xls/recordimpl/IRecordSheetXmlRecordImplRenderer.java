/**
 * 26/nov/2009
 */
package it.nch.eb.flatx.generator.xls.recordimpl;

import it.nch.eb.flatx.generator.xls.RecordSheet;

import java.io.OutputStream;

/**
 * @author gdefacci
 */
public interface IRecordSheetXmlRecordImplRenderer {

	void generate(OutputStream os, RecordSheet sheet);

}