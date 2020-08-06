/**
 * Created on 30/mag/08
 */
package it.nch.eb.flatx.generator.xls;



/**
 * @author gdefacci
 */
public interface SheetColumnsMapping {

	class Base implements SheetColumnsMapping {
		private final int	descrIdx;
		private final int	typeIdx;
		private final int	valueIdx;
		private final int	optIdx;
		private final int colMaxVal;
	
		public Base(int valueIdx, int typeIdx, int descrIdx) {
			this(valueIdx, typeIdx, descrIdx, -1);
		}
		public Base(int valueIdx, int typeIdx, int descrIdx, int opt) {
			this.descrIdx = descrIdx;
			this.typeIdx = typeIdx;
			this.valueIdx = valueIdx;
			this.optIdx = opt;
			this.colMaxVal = XlsUtil.max(new int[] {valueIdx,typeIdx , descrIdx, opt});
		}

		public int getDescriptionColumnIdx() {
			return descrIdx;
		}

		public int getTypeColumnIdx() {
			return typeIdx;
		}
		
		public int getValueColumnIdx() {
			return valueIdx;
		}

		public int getOptionalityColumnIdx() {
			return optIdx;
		}
		
		public int getColumnMaxValue() {
			return colMaxVal;
		}
	}
	
	public int getDescriptionColumnIdx();
	public int getTypeColumnIdx();
	public int getValueColumnIdx();
	public int getOptionalityColumnIdx();
	
	public int getColumnMaxValue();
	
	
}
