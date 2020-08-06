/**
 * Created on 05/giu/07
 */
package it.nch.eb.flatx.files;

import it.nch.eb.flatx.exceptions.ParserException;
import it.nch.eb.flatx.files.model.TokenizedLine;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;


/**
 * @author gdefacci
 */
public class FixedColumnsLineFactory implements OffsetsLinesFactory {

	private final int[]	columns;
	private final int[] offsets;
	
	public static final LinesFactoryBuilder builder = new LinesFactoryBuilder() {

		public OffsetsLinesFactory create(int[] cols) {
			return new FixedColumnsLineFactory(cols);
		}
		
	};

	public FixedColumnsLineFactory(int[] columns) {
		this.columns = columns;
		this.offsets = calculateOffsets(columns);
	}

	private int[] calculateOffsets(int[] columns2) {
		int[] ofsts = new int[columns2.length];
		int partial = 0;
		for (int i = 0; i < columns2.length; i++) {
			partial += columns2[i];
			ofsts[i] = partial;
		}
		return ofsts;
	}

	public TokenizedLine create(String iineContent) {
		return TokenizationUtil.createFixedSizeTokenizer(iineContent, columns);
	}
	
	public int getLineLength() {
		return this.offsets[this.offsets.length-1];
	}

	public int getStartOffset(int pos) {
		if (pos<0) return -1;
		if (pos==0) return 0;
		else return  this.offsets[pos-1];
	}
	
	public int getEndOffset(int pos) {
		if (pos<1) return -1;
		return  this.offsets[pos];
	}
	
	protected int[] getColumns() {
		return columns;
	}
	
	protected int[] getOffsets() {
		return offsets;
	}

	//	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			sb.append(columns[i] + ",");
		}
		StringBuffer ofsts = new StringBuffer();
		for (int i=0; i<offsets.length; i++) {
			ofsts.append(offsets[i]);
			ofsts.append(", ");
		}
		return "columns[" + sb.toString() + "]offsets[" + ofsts.toString() + "]";
	}

	public String[] splitLine(String line, IConversionInfo[] converters) {
		if (getLineLength() != line.length()) {
			String msg = "line length[" + line.length() 
					+ "]does not match the proper length[" + getLineLength() 
					+ "]line content \n" + line + "\n";
			ParserException exc = new ParserException(msg);
			exc.setLinePositionStart(getLineLength());
			exc.setLinePositionStart(getLineLength());
			throw exc;
		}
		
		String[] values = create(line).all();
		if (converters.length!=values.length) {
			String msg = "PositionalConverter'[" + getClass().getName() 
				+ "]s n.[" + converters.length 
				+ "]values n.[" + values.length + "]";
			throw new ParserException(msg);
		}
		return values;
	}
	
	

}
