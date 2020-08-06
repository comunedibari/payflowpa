package it.regioneveneto.mygov.payment.mypivotsb.mapper;

import org.springframework.stereotype.Component;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.EnteDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;

@Component
public class EnteMapper extends BaseMapper {
	
	
	EnteDto getEntity(Ente ente){
		return this.convertToDTO(ente);
	}
	
	public EnteDto convertToDTO(Ente ente) {
		EnteDto enteDto = (EnteDto) super.convertToDTO(ente, EnteDto.class);
		
		return enteDto;
	}

	public EnteDto convertToDTO(EnteTO ente) {
		EnteDto enteDto = (EnteDto) super.convertToDTO(ente, EnteDto.class);
		
		return enteDto;
	}
}