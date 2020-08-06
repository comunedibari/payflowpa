package it.regioneveneto.mygov.payment.mypivot.service.utils;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtil {
	private static final Logger log = Logger.getLogger(ModelMapperUtil.class);

	private ModelMapper modelMapper;

	public ModelMapperUtil() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		log.debug("init model mapper in strict mode");
	}

	public <D> D map(Object source, Class<D> destinationType) {
		return source == null ? null : modelMapper.map(source, destinationType);
	}
}
