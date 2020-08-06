package it.tasgroup.iris.business.ejb.bacheca;

import it.tasgroup.iris.domain.BachecaNews;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.BachecaNewsDao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Stateless(name = "BachecaNewsBusiness")
public class BachecaNewsBusinessBean implements BachecaNewsBusinessLocal, BachecaNewsBusinessRemote {
	
	private static final Logger log = LogManager.getLogger(BachecaNewsBusinessBean.class);

    @EJB(name = "BachecaNewsDaoService")
    private BachecaNewsDao bachecaNewsDao;

	@Override
	public List<BachecaNews> getBachecaNewsList(ContainerDTO containerDTO) {
		return (List<BachecaNews>) bachecaNewsDao.getBachecaNewsList(containerDTO);
	}
	
	@Override
	public List<BachecaNews> getBachecaNewsListToDisplay(ContainerDTO containerDTO) {
		return (List<BachecaNews>) bachecaNewsDao.getBachecaNewsListToDisplay(containerDTO);
	}
	
	@Override
	public BachecaNews getBachecaNewsById(Long id) throws Exception {
		return (BachecaNews)bachecaNewsDao.getById(BachecaNews.class, id);
	}
	
	@Override
	public boolean deleteBachecaNews(Long idToDelete) throws Exception {
		boolean deleted = true;
        try {
        	bachecaNewsDao.deleteBachecaNews(idToDelete);
        } catch (Exception e) {
            log.error("errore durante la cancellazione della notizia: ", e);
            deleted = false;
        }
        return deleted;
    }
	
	@Override
	public BachecaNews updateBachecaNews(BachecaNews bachecaNews) {
		return bachecaNewsDao.updateBachecaNews(bachecaNews);
	}

	@Override
	public BachecaNews insertBachecaNews(BachecaNews bachecaNews) {
		return bachecaNewsDao.insertBachecaNews(bachecaNews);
	}
	
}
