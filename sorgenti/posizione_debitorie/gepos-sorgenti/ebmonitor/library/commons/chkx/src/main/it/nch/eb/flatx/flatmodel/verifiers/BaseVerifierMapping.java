/**
 * Created on 03/mar/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

/**
 * @author gdefacci
 */
public class BaseVerifierMapping {

	protected final String	propertyName;
	protected final boolean	ignoredWhenNull;
	/**
	 * when a null object is provided for verification, if this field is not null an error with this id is created
	 */
	protected final String	nullErrorId;

	public BaseVerifierMapping(String propertyName, boolean ignoredWhenNull, String ifNullErrorId) {
		this.propertyName = propertyName;
		this.ignoredWhenNull = ignoredWhenNull;
		this.nullErrorId = ifNullErrorId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public boolean isToIgnoreWhenNull() {
		return ignoredWhenNull;
	}

	public String getNullErrorId() {
		return nullErrorId;
	}
}