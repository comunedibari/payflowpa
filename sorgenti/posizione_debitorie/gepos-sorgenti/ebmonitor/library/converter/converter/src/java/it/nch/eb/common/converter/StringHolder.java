/**
 * Created on 20/gen/09
 */
package it.nch.eb.common.converter;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.Reader;
import java.io.Serializable;


/**
 * @author gdefacci
 */
public class StringHolder implements Serializable {
	
	private static final long	serialVersionUID	= -560737031062503088L;
	private final String value;

	public StringHolder(final Reader content) {
		this(ResourcesUtil.asString(content));
	}
	public StringHolder(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
