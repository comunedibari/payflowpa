/**
 * Created on 21/ago/07
 */
package it.nch.fwk.checks.errors;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.errors.processing.BaseErrorProcessor;
import it.nch.fwk.checks.errors.processing.ErrorProcessor;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * @author gdefacci
 */
public class BaseErrorMessageFactory implements Serializable {

	private static final long	serialVersionUID	= 6120027056838002109L;
	private Map	errorsProcessorsMap	= new TreeMap();

	public ErrorProcessor lookupProcessor(String msgId, int errorType) {
		QualifiedError key = createQualifiedError(msgId, errorType);
		return lookupProcessor(key);
	}

	public ErrorProcessor lookupProcessor(QualifiedError key) {
			ErrorProcessor processor = (ErrorProcessor) errorsProcessorsMap.get(key);
			boolean finished = false;
			QualifiedError qe = null;
			Entry entry = null;
			for (Iterator it = errorsProcessorsMap.entrySet().iterator(); it.hasNext() && !finished ;) {
				entry = (Entry) it.next();
				qe = (QualifiedError) entry.getKey();
				if (ComparatorsFactory.QUALIFIED_ERROR_IDENTITITY_COMPARATOR.compare(key, qe)==0) {
					processor = (ErrorProcessor) entry.getValue();
					finished = true;
				}
			}
			if (processor==null) {
				throw new RuntimeException("NO ERROR PROCESSOR REGISTERED FOR ERROR_ID="+key.toString());
	//			log.debug("NO ERROR PROCESSOR REGISTERED FOR ERROR_ID="+key.toString());
	//			return new FakeErrorProcessor();
			}
			return processor;
		}

	/**
	 * @param errorId
	 * @param errorType
	 * @param errorProcessor
	 */
	protected void putErrorProcessor(final String errorId, final int errorType, final ErrorProcessor errorProcessor) {
		QualifiedError key = createQualifiedError(errorId, errorType);
		errorsProcessorsMap.put(key, errorProcessor);
	}
	
	public String createMessage(ErrorInfo error, IBindingManager bindings) {
		ErrorProcessor processor = lookupProcessor(error);
		processor.initDefaultParameters();
		processor.collectParams(bindings);
		processor.collectParams(error);
		return processor.produceErrorMessage();
	}
	
	public String createMessage(ErrorInfo error, IElementCursor elem,IBindingManager bindings) {
		ErrorProcessor processor = lookupProcessor(error);
		processor.initDefaultParameters();
		processor.collectParams(bindings);
		processor.collectParams(elem);
		processor.collectParams(error);
		return processor.produceErrorMessage();
	}
	
	public QualifiedError createQualifiedError(String msgId, int errorType) {
		return new QualifiedErrorImpl(msgId, errorType);
	}

	public void registerElementProcessor(final String errorId, final int errorType, final ErrorProcessor errorProcessor) {
		putErrorProcessor(errorId, errorType, errorProcessor);
	}

	/**
	 * register an error processor which binds a fix error message when the given 
	 * error happens
	 */
	public void registerElementProcessor(final String errorId, final int errorType, final String staticErrorMessage) {
		registerElementProcessor(errorId, errorType, new BaseErrorProcessor() {
			public String produceErrorMessage() {
				return staticErrorMessage;
			}
		});
	}
	
	public Iterator getProcessorsNameIterator() {
		return this.errorsProcessorsMap.keySet().iterator();
	}

	public ErrorProcessor getErrorProcessor(String id) {
		return (ErrorProcessor) this.errorsProcessorsMap.get(id);
	}
}
