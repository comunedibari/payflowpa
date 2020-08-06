package it.regioneveneto.mygov.payment.mypivotsb.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.EnteTipoDovutoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;

@Component
public class EnteTipoDovutoMapper extends BaseMapper {
	

//	@Autowired
//	EnteMapper enteMapper;
	
	@Autowired
	private ModelMapperUtil modelMapperUtil;
	
	EnteTipoDovutoDto getEntity(EnteTipoDovuto enteTipoDovuto){
		return this.convertToDTO(enteTipoDovuto);
	}
	
	public EnteTipoDovutoDto convertToDTO(EnteTipoDovuto enteTipoDovuto) {
//		EnteTipoDovutoDto enteTipoDovutoDto = (EnteTipoDovutoDto) super.convertToDTO(enteTipoDovuto, EnteTipoDovutoDto.class);
		EnteTipoDovutoDto enteTipoDovutoDto = (EnteTipoDovutoDto) modelMapperUtil.map(enteTipoDovuto, EnteTipoDovutoDto.class);
		
//		enteTipoDovutoDto.setEnte(enteMapper.convertToDTO(enteTipoDovuto.getEnte()));		
		enteTipoDovutoDto.setEnte(modelMapperUtil.map(enteTipoDovuto.getEnte(), EnteTO.class));
		
		return enteTipoDovutoDto;
	}
	
}