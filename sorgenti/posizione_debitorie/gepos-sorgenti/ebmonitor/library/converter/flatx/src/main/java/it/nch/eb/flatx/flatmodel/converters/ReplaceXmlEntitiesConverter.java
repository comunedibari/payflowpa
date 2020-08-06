package it.nch.eb.flatx.flatmodel.converters;

import it.nch.eb.flatx.bean.types.TypedConverterImpl;

/**
 * replace invalid xml characters with respective xml entity 
 */
public class ReplaceXmlEntitiesConverter extends TypedConverterImpl {

	private static final long	serialVersionUID	= -8461264924679830615L;

	public ReplaceXmlEntitiesConverter() {
		super(String.class);
	}

	public String encode(String stringModel) {
		return XmlEntitiesUtils.replaceEnties(stringModel);
	}

}
