/**
 * Created on 19/mar/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

public class OffsetRange {
	final int start;
	final int end;
	public OffsetRange(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}

	public String toString() {
		return getStart() + "..." + getEnd();
	}
	
}