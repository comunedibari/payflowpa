/**
 * 27/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.ClassIdentityConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author gdefacci
 */
public class OperationKinds {

	public static final OperationKinds instance = new OperationKinds();

	public static class OperationKind implements Comparable, Serializable {
		private static final long serialVersionUID = 6730928518264974388L;
		private final String name;
		private final String abbr;

		private OperationKind(String name, String abbrv, OperationKinds kinds) {
			this.name = name;
			this.abbr = abbrv;
			kinds.add(this);
		}

		public String getName() {
			return name;
		}

		public String getAbbr() {
			return abbr;
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof OperationKind)) return false;
			OperationKind opk = (OperationKind) obj;
			return this.name.equals(opk.getName());
		}

		public int hashCode() {
			return this.name.hashCode();
		}

		public String toString() {
			return "OperationKind(" + name + ")";
		}

		/* @Override */
		public int compareTo(Object o) {
			if (o instanceof OperationKind) {
				return this.name.compareTo(((OperationKind) o).getName());
			}
			return -1;
		}

	}

	private final Set operationsSet = new TreeSet();

	public final  OperationKind insert = new OperationKind("Insert", "I", this);
	public final  OperationKind update = new OperationKind("UpdateStatus", "U", this);
	public final  OperationKind updateMassivo = new OperationKind("UpdateMassivo", "M", this);
	public final  OperationKind replace = new OperationKind("Replace", "R", this);
	public final  OperationKind delete = new OperationKind("Delete", "D", this);

	private void add(OperationKind kind) {
		this.operationsSet.add(kind);
	}

	public OperationKind valueOf(String opName) {
		for (Iterator it = this.operationsSet.iterator(); it.hasNext();) {
			OperationKind knd = (OperationKind) it.next();
			if (knd.getName().equals(opName)) return knd;
		}
		return null;
	}

	public Iterator/*<OperationKind>*/ all() {
		return operationsSet.iterator();
	}

	public final ToObjectConverter toObjectConverter =
		new ToObjectConverter() {

			private static final long serialVersionUID = -1006747745503950455L;

			public Object convert(String str) {
				OperationKind res = valueOf(str);
				if (res==null) throw new IllegalStateException("Invalid tipoOperazione : " + str);
				return res;
			}

			public Class getConversionTargetClass() {
				return OperationKind.class;
			}

			public String toString() {
				return "tipoOperazioneConverter";
			}

		};

	public Converter toAbbrConverter =
		new ClassIdentityConverter() {

			private static final long serialVersionUID = -2591445105640704371L;

			public String encode(String stringModel) {
				OperationKind op = valueOf(stringModel);
				if (op==null) throw new IllegalArgumentException("invalid tipoOperazione:" + stringModel);
				return op.getAbbr();
			}

			public String toString() {
				return "tipoOperazioneAbbrConverter";
			}

	};


}
