package it.nch.eb.flatx.flatmodel.converters;

import it.nch.eb.flatx.bean.types.TypedConverterImpl;

/**
 * replace invalid xml characters with respective xml entity
 */
public class ReplaceXmlReservedCharsConverter extends TypedConverterImpl {

	private static final long	serialVersionUID	= 876263833847510758L;

	public ReplaceXmlReservedCharsConverter() {
		super(String.class);
	}

	public String encode(String stringModel) {
		return XmlEntitiesUtils.fixup(stringModel);
	}

}
