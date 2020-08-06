/**
 * Created on 21/ago/07
 */
package it.nch.fwk.checks.errors;

import java.util.Comparator;

public abstract class SafeComparator implements Comparator {
	
	public final int compare(Object arg1, Object arg2) {
		return typeSafeCompare(arg1, arg2);
	}
	
	protected boolean areComparable(Object arg1, Object arg2) {
		return arg1.getClass().isAssignableFrom(arg2.getClass());
	}
	
	int typeSafeCompare(Object arg1, Object arg2) {
		if (areComparable(arg2, arg1)) {
			return nullSafeCompare(arg1, arg2);
		}
		return ComparatorsFactory.NOT_COMPARABLE;
	}
	
	public ComparationThanState less(Object qe) {
		return new ComparationThanState(qe, this, ComparatorsFactory.LESS);
	}
	public ComparationThanState great(Object qe) {
		return new ComparationThanState(qe, this, ComparatorsFactory.LESS);
	}
	public ComparationToState isEquals(Object qe) {
		return new ComparationToState(qe, this);
	}
	
	public int nullSafeCompare(Object qe, Object qe2) {
		if (qe == null && qe2 == null) return ComparatorsFactory.EQUAL;
		if (qe == null) return ComparatorsFactory.LESS;
		if (qe2 == null) return ComparatorsFactory.GREATER;
		return typeSpecificCompare(qe, qe2);
	}
	protected abstract int typeSpecificCompare(Object arg1, Object arg2);

	static class ComparationThanState {
		private Object	qe1;
		private Comparator		comp;
		private int				comparationType;
		public ComparationThanState(Object qe1, Comparator comp, int comparationType) {
			this.qe1 = qe1;
			this.comp = comp;
			this.comparationType = comparationType;
		}
		public boolean than(Object qe2) {
			return comp.compare(qe1, qe2) == comparationType;
		}
	}

	static class ComparationToState {
		private Object qe1;
		private Comparator		comp;
		public ComparationToState(Object qe1, Comparator comp) {
			this.qe1 = qe1;
			this.comp = comp;
		}
		public boolean to(Object qe2) {
			return comp.compare(qe1, qe2) == ComparatorsFactory.EQUAL;
		}
	}
}