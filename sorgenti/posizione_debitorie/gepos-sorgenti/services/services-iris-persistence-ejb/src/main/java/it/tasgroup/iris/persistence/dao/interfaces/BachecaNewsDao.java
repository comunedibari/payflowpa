package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.BachecaNews;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

public interface BachecaNewsDao extends Dao<BachecaNews>{
	
	public List<BachecaNews> getBachecaNewsList(ContainerDTO containerDTO);
	public List<BachecaNews> getBachecaNewsListToDisplay(ContainerDTO containerDTO);
	public boolean deleteBachecaNews(Long idToDelete);
	public BachecaNews retrieveBachecaNewsById(Long id);
	public BachecaNews updateBachecaNews(BachecaNews bachecaNews);
	public BachecaNews insertBachecaNews(BachecaNews bachecaNews);

}
