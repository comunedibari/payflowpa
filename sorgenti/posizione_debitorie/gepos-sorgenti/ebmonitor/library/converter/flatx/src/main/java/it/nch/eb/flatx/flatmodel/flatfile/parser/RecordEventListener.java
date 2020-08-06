/**
 * Created on 08/mag/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;


/**
 * @author gdefacci
 */
public interface RecordEventListener {
	
	boolean isInterestedIn(RecordEvent recEvent);
	void perform(RecordEvent recEvent);

}
