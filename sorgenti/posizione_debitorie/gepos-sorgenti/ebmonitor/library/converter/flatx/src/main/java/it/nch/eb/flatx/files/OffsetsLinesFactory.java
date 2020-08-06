/**
 * Created on 06/mar/08
 */
package it.nch.eb.flatx.files;

import it.nch.eb.flatx.files.model.LinesFactory;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;

/**
 * a lines factory whcuh know in advance the exact tokenization size.
 * @author gdefacci
 */
public interface OffsetsLinesFactory extends LinesFactory {

	int getLineLength();
	int getStartOffset(int pos);
	int getEndOffset(int pos);
	
	String[] splitLine(String line, IConversionInfo[] cis);

}