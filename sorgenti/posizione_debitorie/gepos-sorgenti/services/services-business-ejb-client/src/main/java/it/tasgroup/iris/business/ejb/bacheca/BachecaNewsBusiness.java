package it.tasgroup.iris.business.ejb.bacheca;

import it.tasgroup.iris.domain.BachecaNews;
import it.tasgroup.iris.dto.ContainerDTO;

import java.util.List;

public interface BachecaNewsBusiness {

	public List<BachecaNews> getBachecaNewsList(ContainerDTO containerDTO);
	public boolean deleteBachecaNews(Long idPrenotazione) throws Exception;
	public List<BachecaNews> getBachecaNewsListToDisplay(ContainerDTO containerDTO);
	public BachecaNews getBachecaNewsById(Long id) throws Exception;
	public BachecaNews updateBachecaNews(BachecaNews bachecaNews);
	public BachecaNews insertBachecaNews(BachecaNews bachecaNews);

}
