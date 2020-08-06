/**
 * 29/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.model;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author gdefacci
 */
public class PendenzeInclusions {
	
	public static final PendenzeInclusions EXCLUDE_NONE = new PendenzeInclusions(new TreeSet(), false);
	public static final PendenzeInclusions EXCLUDE_ALL = new PendenzeInclusions(new TreeSet(), true);
	
	private final Set/*<String>*/ idPendenzaEnteSet;
	private final boolean include;
	
	public PendenzeInclusions(Set/*<String>*/ idPendenzaEnteSet, boolean include) {
		this.idPendenzaEnteSet = idPendenzaEnteSet;
		this.include = include;
	}
	
	public static PendenzeInclusions excludeAll(String[] idPendenzaEneteToExclude) {
		Set exclSet = newSet(idPendenzaEneteToExclude);
		return new PendenzeInclusions(exclSet, false);
	}
	
	public static PendenzeInclusions includeAll(String[] idPendenzaEneteToInclude) {
		Set exclSet = newSet(idPendenzaEneteToInclude);
		return new PendenzeInclusions(exclSet, true);
	}

	private static Set newSet(String[] idPendenzaEneteToExclude) {
		Set exclSet = new TreeSet();
		for (int i = 0; i < idPendenzaEneteToExclude.length; i++) {
			String string = idPendenzaEneteToExclude[i];
			exclSet.add(string);
		}
		return exclSet;
	}

	public boolean include(String idPendenzaEnte) {
		boolean res = idPendenzaEnteSet.contains(idPendenzaEnte);
		if (include) return res;
		else return !res;
	}
	
}
