/**
 * Created on 02/apr/07
 */
package it.nch.fwk.checks.context.xom;

import it.nch.fwk.checks.Check;
import it.nch.fwk.checks.context.BaseChecksFactory;
import it.nch.fwk.checks.errors.ErrorCollectorCheck;
import nu.xom.Element;


/**
 * manage ElementCheck with a one argument constructor taking a Element instance:
 * <code>
 * 		class MyCheck extends ElementCheck {
 * 			public MyCheck(nu.xom.Element element) {..code..}
 * 			public boolean check() {..code..}
 * 			..further declarations..
 * 		}
 * 		..
 * 		ChecksFactory cf = new ChecksFactory();
 * 		cf.registerCheck("1.3.4.7",MyCheck.class);
 * 		..
 * 		ErrorCollector errorBag1347 = cf.check("1.3.4.7", elem);
 * </code>
 * @author gdefacci
 */
public class ChecksFactory extends BaseChecksFactory {
	
	private static final long	serialVersionUID	= 2429025054575975043L;

	public ErrorCollectorCheck createCheck(String id, Element element) {
		Class checkClass = getCheckClass(id);
		if (checkClass==null) {
			throw new IllegalArgumentException("no check exist with id : " + id);
		}
		ErrorCollectorCheck check = null;
		try {
			check = (ErrorCollectorCheck) checkClass.getConstructor(new Class[] { Element.class }).newInstance(new Element[] { element });
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (check==null) {
			throw new UnsupportedOperationException("cant find a check for class "+ checkClass.getName() + " assigned to id " + id);
		}
		return check;
	}

	public ChecksFactory registercheck(String id, Class checkClass) {
		putCheck(id, checkClass);
		return this;
	}
	
	public Check check(String id, Element element) {
		try {
			Check check = createCheck(id, element);
			check.check();
			return check;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void checkSequentially(String[] ids, Element element) throws Exception {
		createSequentialCheck(ids, element).check();
	}
	
	public Check createSequentialCheck(final String[] ids, final Element element) {
		return new Check() {

			public boolean check() {
				boolean[] res = new boolean[ids.length];
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					Check ckr = createCheck(id, element);
					res[i] = ckr.check();
				}
				return foldBooleanArray( res );
			}

		};
	}
	
	private boolean foldBooleanArray(boolean[] values) {
		for (int i = 0; i < values.length; i++) {
			boolean b = values[i];
			if (!b) return false;
		}
		return true;
	}

}