/**
 * 13/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps;

public interface TimestampEffect {
	public void apply(TableTimestamps model);
}