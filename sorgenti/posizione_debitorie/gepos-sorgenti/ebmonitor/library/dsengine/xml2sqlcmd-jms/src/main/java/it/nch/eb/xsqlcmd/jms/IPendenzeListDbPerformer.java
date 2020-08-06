/**
 * 10/set/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import java.util.List;

/**
 * @author gdefacci
 */
public interface IPendenzeListDbPerformer extends 
	Function1<List<PendenzeModel>, PendenzeOperationOutcome> {
	
	void flush();

}
