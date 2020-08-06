/**
 * Created on 08/mag/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

/**
 * @author gdefacci
 */
public interface IRecordEventsDispatcher {

	void fire(RecordEvent event);
	
	IRecordEventsDispatcher FAKE = new IRecordEventsDispatcher() {

		public void fire(RecordEvent event) {
		}
		
	};

}