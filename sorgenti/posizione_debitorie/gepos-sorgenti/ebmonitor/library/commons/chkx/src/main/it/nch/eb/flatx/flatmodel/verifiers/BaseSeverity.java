/**
 * Created on 14/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;


/**
 * @author gdefacci
 */
public class BaseSeverity implements Severity, Comparable {

	private final String	description;
	private final int	value;

	public BaseSeverity(String description, int value) { // this class should be instantiated by subclasses or same package
		this.description = description;
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public int getValue() {
		return value;
	}

	public int compareTo(Object o) {
		if (!(o instanceof Severity)) 
			throw new ClassCastException("expecting a " + Severity.class.getName() 
					+ " instance but got a " + o.getClass().getName());
		BaseSeverity other = (BaseSeverity) o;
		return new Integer(getValue()).compareTo(new Integer(other.getValue()));
	}

}
