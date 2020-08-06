package it.tasgroup.iris.facade.ejb.anonymous.dto.builder;

import it.tasgroup.iris.domain.BachecaNews;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousBachecaNewsDTO;

import java.util.ArrayList;
import java.util.List;

public class AnonymousBachecaNewsDTOBuilder {
	
	public static List<AnonymousBachecaNewsDTO> populateAnonymousBachecaNewsDTO(List<BachecaNews> bachecaNewsList) {
		List<AnonymousBachecaNewsDTO> bnDTOList = new ArrayList<AnonymousBachecaNewsDTO>();
		for (BachecaNews bachecaNews : bachecaNewsList) {
			AnonymousBachecaNewsDTO dto = populateAnonymousBachecaNewsDTO(bachecaNews);	
			bnDTOList.add(dto);
		}
		return bnDTOList;
	}
	
	public static AnonymousBachecaNewsDTO populateAnonymousBachecaNewsDTO(BachecaNews bachecaNews) {
		AnonymousBachecaNewsDTO res = new AnonymousBachecaNewsDTO();
		res.setTitolo(bachecaNews.getTitolo());
		res.setContenuto(bachecaNews.getMessaggio());
		res.setId(String.valueOf(bachecaNews.getId()));
		res.setImgExtName(bachecaNews.getNomeFileImgExt());
		res.setImgIntName(bachecaNews.getNomeFileImgInt());
		res.setImgExt(bachecaNews.getImgExtBlob());
		res.setImgInt(bachecaNews.getImgIntBlob());
		return res;
	}
	
	
}
