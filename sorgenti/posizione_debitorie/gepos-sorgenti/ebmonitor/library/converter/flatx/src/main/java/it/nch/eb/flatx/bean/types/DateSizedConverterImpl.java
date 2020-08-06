package it.nch.eb.flatx.bean.types;

public class DateSizedConverterImpl extends SizedConverterImpl {

	private static final long serialVersionUID = -2923792042735011215L;
	
	private final DateTryConverter dateConverter;

	public DateSizedConverterImpl(String filler, int alignment,
			DateTryConverter converter) {
		super(converter.getLength(), filler, alignment, converter);
		this.dateConverter = converter;
	}

	public DateTryConverter getDateConverter() {
		return dateConverter;
	}

}
