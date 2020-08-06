package it.regioneveneto.mygov.payment.mypivotsb.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BaseMapper {
	
	public Object convertToDTO(Object entity, Class<?> dto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(entity, dto);
	}

	public Object convertToDTO(List<?> entities, Class<?> dto) {
		List<Object> objs = new ArrayList<>();
		for(Object o: entities) {
			objs.add(convertToDTO(o,dto));
		}
		return objs;
	}	

}