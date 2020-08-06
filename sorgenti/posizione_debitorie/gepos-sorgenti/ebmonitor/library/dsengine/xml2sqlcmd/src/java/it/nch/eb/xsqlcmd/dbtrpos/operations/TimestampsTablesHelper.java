/**
 * 13/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author gdefacci
 */
public class TimestampsTablesHelper {
	
	public static final TimestampsTablesHelper instance = new TimestampsTablesHelper();
	
	public void set(Collection coll, TimestampEffect effect) {
		if (coll!=null && !coll.isEmpty()) {
			for (Iterator it = coll.iterator(); it.hasNext();) {
				TableTimestamps model = (TableTimestamps) it.next();
				effect.apply(model);
			}
		}
	}
	
	public void set(TableTimestamps model, TimestampEffect effect) {
		if (model!=null) {
			effect.apply(model);
		}
	}
	
	
}
