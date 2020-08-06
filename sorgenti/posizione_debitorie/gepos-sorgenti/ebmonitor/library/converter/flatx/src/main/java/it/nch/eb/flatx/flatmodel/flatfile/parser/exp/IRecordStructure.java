/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;


/**
 * sealed 
 * @author gdefacci
 */
public interface IRecordStructure {
	
	String getName();
	boolean isOptional();
	
	ObjectBuilder getObjectBuilder();
	
	StructureKind getStructureKind();
	
	StructureKind simple = new StructureKind(0, "simple");
	StructureKind seq = new StructureKind(1, "seq");
	StructureKind bean = new StructureKind(2, "bean");

	final class StructureKind {
		private final int ord;
		private final String	toStr;

		private StructureKind(int ord, String toString) {
			super();
			this.ord = ord;
			this.toStr = toString;
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof StructureKind)) return false;
			return ((StructureKind)obj).ord == ord;
		}

		public int hashCode() {
			return ord;
		}

		public String toString() {
			return toStr;
		}
		
	}

}
