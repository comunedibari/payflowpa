/**
 * Created on 08/mag/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;

/**
 * @author gdefacci
 */
public abstract class NotifierParser extends BaseParser {
	
	static final IRecordEventsDispatcher FAKE_ERROR_DISPTCHER = IRecordEventsDispatcher.FAKE;

	private final IRecordEventsDispatcher	eventsDispatcher;

	public NotifierParser(ObjectBuilder objBuilder, IRecordEventsDispatcher eventsDispatcher) {
		super(objBuilder);
		if (eventsDispatcher==null) this.eventsDispatcher = FAKE_ERROR_DISPTCHER;
		else this.eventsDispatcher = eventsDispatcher;
	}

	protected final void setResult(Object container, IParser propertyParser, Object proValue) {
		setPropertyValue(container, propertyParser, proValue);
//		reference comparison: we want to reduce useless memory consumption
		if (eventsDispatcher != FAKE_ERROR_DISPTCHER) eventsDispatcher.fire(createEvent(container, propertyParser, proValue));
	}

	protected RecordEvent createEvent(Object container, IParser propertyParser, Object proValue) {
		return new BaseRecordEvent(container, propertyParser, proValue);
	}

	protected abstract void setPropertyValue(Object container, IParser propertyParser, Object proValue);
}