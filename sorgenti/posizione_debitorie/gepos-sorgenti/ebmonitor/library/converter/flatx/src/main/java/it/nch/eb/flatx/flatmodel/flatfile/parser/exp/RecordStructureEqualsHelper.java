/**
 * Created on 02/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.StringUtils;


/**
 * @author gdefacci
 */
public class RecordStructureEqualsHelper {
	
	public static final RecordStructureEqualsHelper instance =
		new RecordStructureEqualsHelper();
	
	public boolean areEquals(IRecordStructure recStruct, Object othrObj) {
		if (!(othrObj instanceof IRecordStructure)) return false;
		else {
			if (recStruct instanceof BeanStructure) return areEquals((BeanStructure)recStruct, othrObj);
			else if (recStruct instanceof SeqRecordStructure) return areEquals((SeqRecordStructure)recStruct, othrObj);
			else if (recStruct instanceof SimpleRecordStructure) return areEquals((SimpleRecordStructure)recStruct, othrObj);
			else throw new IllegalStateException("not recognized record structure " + recStruct);
		}
	}
	
	public boolean areCommonAttributesEquals(IRecordStructure recStruct, IRecordStructure othr) {
		boolean res = othr.getName().equals(recStruct.getName());
		if (res) res = othr.isOptional() == recStruct.isOptional();
		if (res) res = othr.getStructureKind().equals(recStruct.getStructureKind());
		if (res) res = othr.getObjectBuilder().equals(recStruct.getObjectBuilder());
		return  res;
	}
	
	public boolean areEquals(SeqRecordStructure recStruct, Object othrObj) {
		if (!(othrObj instanceof SeqRecordStructure)) return false;
		else {
			SeqRecordStructure othr = (SeqRecordStructure)othrObj;
			boolean res = areCommonAttributesEquals(recStruct, othr);
			if (res) {
				IRecordStructure item1 = recStruct.getItemStrucuture();
				IRecordStructure item2 = othr.getItemStrucuture();
				res = areEquals(item1, item2);
			}
			return res;
		}
	}
	public boolean areEquals(SimpleRecordStructure recStruct, Object othrObj) {
		if (!(othrObj instanceof SimpleRecordStructure)) return false;
		else return areCommonAttributesEquals(recStruct, (SimpleRecordStructure)othrObj); 
	}
	
	public boolean areEquals(BeanStructure recStruct, Object othrObj) {
		if (!(othrObj instanceof BeanStructure)) return false;
		else {
			BeanStructure othr = (BeanStructure) othrObj;
			boolean res = areCommonAttributesEquals(recStruct, othr);
			if (res) {
				IRecordStructure[] rItems = recStruct.getItemStructures();
				IRecordStructure[] oItems = othr.getItemStructures();
				res = areEquals(rItems, oItems);
			}
			return  res;
		}
		
	}
	
	private boolean areEquals(IRecordStructure[] items1, IRecordStructure[] items2) {
		boolean res = (items1.length == items2.length);
		int idx = 0;
		while (res && idx < items1.length) {
			res = areEquals(items1[idx], items2[idx]);
			idx++;
		}
		return res;
	}

	public int hashCode(IRecordStructure recStruct) {
		int hash = 0;
		hash += recStruct.getName().hashCode();
		hash += (recStruct.isOptional()) ? 11 : 17;
		hash += (recStruct.getStructureKind().hashCode() * 7);
		hash += recStruct.getObjectBuilder().hashCode();
		return hash;
	}
	
	public String toString(IRecordStructure rstruct) {
		StringBuffer sb = new StringBuffer();
		sb.append(StringUtils.getSimpleName(rstruct.getClass()));
		sb.append("(");
		sb.append("name: ");
		sb.append(rstruct.getName());
		sb.append(", objectBuilder: ");
		sb.append(rstruct.getObjectBuilder());
		sb.append(", kind: ");
		sb.append(rstruct.getStructureKind());
		sb.append(", optional: ");
		sb.append(rstruct.isOptional());
		sb.append(")");
		return sb.toString();
	}

}
