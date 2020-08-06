package it.nch.eb.flatx.generator.xls.xmlrecord;

import jxl.Sheet;
import jxl.format.Colour;
import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.XlsUtil;

/**
 * @author gdefacci
 */
public class UpdateColumnsCellFilter extends DefaultCellFilter {

	private final int columnIdx;
	
	public UpdateColumnsCellFilter(SheetColumnsMapping columnsMapping, int colIdx) {
		super(columnsMapping);
		this.columnIdx = colIdx;
	}
	
	public UpdateColumnsCellFilter(SheetColumnsMapping columnsMapping) {
		this(columnsMapping,  columnsMapping.getDescriptionColumnIdx());
	}

	public boolean match(Sheet sheet, int ridx) {
		String xpd = XlsUtil.getContent(sheet, columnIdx, ridx);
		if (xpd==null || xpd.trim().length()  == 0) return false;
		else {
			Colour col = XlsUtil.getTextColor(sheet, columnIdx, ridx);
			return XlsUtil.colorsMatch(col, Colour.RED);
		}
	}

}
