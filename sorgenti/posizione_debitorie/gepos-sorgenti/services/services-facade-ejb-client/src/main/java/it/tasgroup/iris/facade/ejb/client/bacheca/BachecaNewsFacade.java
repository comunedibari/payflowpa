package it.tasgroup.iris.facade.ejb.client.bacheca;

import it.nch.idp.backoffice.bacheca.BachecaNewsVO;
import it.tasgroup.iris.dto.ContainerDTO;

import java.util.List;

public interface BachecaNewsFacade {

	public List<BachecaNewsVO>  getBachecaNewsList(ContainerDTO dtoIn);
	public List<BachecaNewsVO>  getBachecaNewsListToDisplay(ContainerDTO dtoIn);
	public BachecaNewsVO  getBachecaNewsById(Long id) throws Exception;
	public boolean deleteBachecaNews(Long idToDelete) throws Exception;
	public BachecaNewsVO updateBachecaNews(BachecaNewsVO voIn);
	public BachecaNewsVO insertBachecaNews(BachecaNewsVO voIn);
	
}
