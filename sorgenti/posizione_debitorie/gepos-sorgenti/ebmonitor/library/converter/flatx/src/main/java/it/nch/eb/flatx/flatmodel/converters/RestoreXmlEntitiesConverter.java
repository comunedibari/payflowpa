package it.nch.eb.flatx.flatmodel.converters;

import it.nch.eb.flatx.bean.types.TypedConverterImpl;

/**
 * TODO: never really used
 * replace invalid xml characters with respective xml entity 
 */
public class RestoreXmlEntitiesConverter extends TypedConverterImpl {

	private static final long	serialVersionUID	= -8461264924679830615L;

	public RestoreXmlEntitiesConverter() {
		super(String.class);
	}

	public String encode(String stringModel) {
		return XmlEntitiesUtils.restoreEnties(stringModel);
	}

}
