/**
 * 12/giu/2009
 */
package it.nch.xml2sql.test;
/**
 * @author gdefacci
 */
public class TestMode implements Comparable {
	private final int idx;
	private final String description;
	public TestMode(int idx, String description) {
		this.idx = idx;
		this.description = description;
	}
	public boolean equals(Object obj) {
		if (obj instanceof TestMode) {
			return ((TestMode)obj).idx == idx;
		}
		return false;
	}
	public int hashCode() {
		return idx;
	}
	public String getDescription() {
		return description;
	}
	public int compareTo(Object obj) {
		if (obj instanceof TestMode) {
			int oidx = ((TestMode) obj).idx;
			return (idx > oidx) ? 1 : (idx == oidx) ? 0 : -1;
		}
		return -1;
	}
	
	
}