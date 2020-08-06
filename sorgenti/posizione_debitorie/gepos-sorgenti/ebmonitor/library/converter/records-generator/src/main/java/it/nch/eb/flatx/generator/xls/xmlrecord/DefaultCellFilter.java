/**
 * 21/apr/2009
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.flatx.generator.xls.SheetColumnsMapping;
import it.nch.eb.flatx.generator.xls.XlsUtil;
import jxl.Sheet;

public class DefaultCellFilter implements CellFilter {

	private final SheetColumnsMapping columnsMapping;
	private int descriptionColumnIdx;
	
	public DefaultCellFilter(SheetColumnsMapping columnsMapping) {
		this(columnsMapping, columnsMapping.getDescriptionColumnIdx() );
	}
	
	public DefaultCellFilter(SheetColumnsMapping columnsMapping,
			int descriptionColumnIdx) {
		this.columnsMapping = columnsMapping;
		this.descriptionColumnIdx = descriptionColumnIdx;
	}

	protected SheetColumnsMapping getColumnsMapping() {
		return columnsMapping;
	}
	
	private int getDescriptionColumnIdx() {
		return descriptionColumnIdx;
	}

	public XRowInfo create(Sheet sheet, int ridx) {
		String name = XlsUtil.getContent(sheet, getDescriptionColumnIdx(), ridx);
		String typeName = XlsUtil.getContent(sheet, columnsMapping.getTypeColumnIdx(), ridx);
		boolean optional = XlsUtil.getBooleanContent(sheet, columnsMapping.getOptionalityColumnIdx(), ridx);
		String xp = XlsUtil.getContent(sheet, columnsMapping.getValueColumnIdx(), ridx);
		return XRowInfoUtils.instance.createXRowInfo(name, typeName, optional, xp);
	}

	public boolean match(Sheet sheet, int ridx) {
		String xpd = XlsUtil.getContent(sheet, getDescriptionColumnIdx(), ridx);
		return xpd != null && xpd.trim().length() > 0;
	}
	
}