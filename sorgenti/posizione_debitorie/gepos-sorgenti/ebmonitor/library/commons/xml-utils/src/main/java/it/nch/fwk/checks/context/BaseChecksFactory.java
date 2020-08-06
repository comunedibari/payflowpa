/**
 * Created on 21/ago/07
 */
package it.nch.fwk.checks.context;

import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.errors.ErrorCollectorCheck;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author gdefacci
 */
public class BaseChecksFactory implements Serializable {
	
	private static final long	serialVersionUID	= 2751078032494348880L;
	private Map/*<String, Class<ElementCheck>>*/ checksMap = new TreeMap();

	public ErrorCollectorCheck createUCheck(Class checkClass, IElementCursor element) {
		ErrorCollectorCheck check = null;
		try {
			Constructor constructor = checkClass.getConstructor(new Class[] { IElementCursor.class });
			IElementCursor[] constructorArgs = new IElementCursor[] { element };
			check = (ErrorCollectorCheck) constructor.newInstance(constructorArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (check==null) {
			throw new UnsupportedOperationException("cant find a check for class "+ checkClass.getName());
		}
		return check;
	}
	
	/**
	 * return the old element if present
	 */
 	protected Class putCheck(String id, Class checkClass) {
 		return (Class) checksMap.put(id, checkClass);
 	}

	protected Class getCheckClass(String id) {
		return (Class) checksMap.get(id);
	}


}
