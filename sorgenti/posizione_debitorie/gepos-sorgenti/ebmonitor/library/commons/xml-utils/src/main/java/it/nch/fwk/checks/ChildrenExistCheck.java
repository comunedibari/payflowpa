/**
 * Created on 16/mag/07
 */
package it.nch.fwk.checks;



/**
 * TODO: actually only used in tests
 * @author gdefacci
 */
public class ChildrenExistCheck extends BaseElementCheck {
	
	public static final String MANDATORY_ELEMENT_MISSING = "MANDATORY_ELEMENT_MISSING"; 
	
	private String[]	children;

	/**
	 * children contains the names of the elements which should be children
	 * of current(); 
	 */
	public ChildrenExistCheck(IElementCursor cursor, String[] children) {
		super(cursor);
		this.children =  children;
	}

	/**
	 * collect an <code>MANDATORY_ELEMENT_MISSING</code> error for each missing 
	 * child
	 */
	public boolean check() {
		for (int i = 0; i < this.children.length; i++) {
			String elemName = this.children[i];
			if (current().child(elemName)==null) {
				log.warn("the given child " + elemName + " does not appear inside " + current());
				collectError(MANDATORY_ELEMENT_MISSING, current());
			}
		}
		return isOk();
	}

}
