package it.tasgroup.iris.facade.ejb.bacheca;

import it.nch.idp.backoffice.bacheca.BachecaNewsVO;
import it.tasgroup.iris.business.ejb.bacheca.BachecaNewsBusinessLocal;
import it.tasgroup.iris.domain.BachecaNews;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.facade.ejb.client.bacheca.BachecaNewsFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.bacheca.BachecaNewsFacadeRemote;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "BachecaNewsFacade")
public class BachecaNewsFacadeBean implements BachecaNewsFacadeLocal, BachecaNewsFacadeRemote {

	@EJB(name = "BachecaNewsBusiness")
	private BachecaNewsBusinessLocal bachecaNewsBusinessBean;
	
	@Override
	public List<BachecaNewsVO> getBachecaNewsListToDisplay(ContainerDTO dtoIn) {
		List<BachecaNews> newsList = bachecaNewsBusinessBean.getBachecaNewsListToDisplay(dtoIn);
		return BachecaNewsVOBuilder.bachecaNewsVOFromBachecaNews(newsList);
	}

	@Override
	public List<BachecaNewsVO> getBachecaNewsList(ContainerDTO dtoIn) {
		List<BachecaNews> newsList = bachecaNewsBusinessBean.getBachecaNewsList(dtoIn);
		return BachecaNewsVOBuilder.bachecaNewsVOFromBachecaNews(newsList);
	}
	
	@Override
	public BachecaNewsVO getBachecaNewsById(Long id) throws Exception {
		BachecaNews bn = bachecaNewsBusinessBean.getBachecaNewsById(id);
		return BachecaNewsVOBuilder.bachecaNewsVOFromBachecaNews(bn);
	}
	
	@Override
	public boolean deleteBachecaNews(Long idToDelete) throws Exception {
		return bachecaNewsBusinessBean.deleteBachecaNews(idToDelete);
	}
	
	@Override
	public BachecaNewsVO updateBachecaNews(BachecaNewsVO voIn) {
		BachecaNews bnIn = BachecaNewsVOBuilder.bachecaNewsVOToBachecaNews(voIn);
		BachecaNews bnOut = bachecaNewsBusinessBean.updateBachecaNews(bnIn);
		BachecaNewsVO voOut = BachecaNewsVOBuilder.bachecaNewsVOFromBachecaNews(bnOut);
		return voOut;
	}

	@Override
	public BachecaNewsVO insertBachecaNews(BachecaNewsVO voIn) {
		BachecaNews bnIn = BachecaNewsVOBuilder.bachecaNewsVOToBachecaNews(voIn);
		BachecaNews bnOut = bachecaNewsBusinessBean.insertBachecaNews(bnIn);
		BachecaNewsVO voOut = BachecaNewsVOBuilder.bachecaNewsVOFromBachecaNews(bnOut);
		return voOut;
	} 

}

class BachecaNewsVOBuilder {
	
	public static List<BachecaNewsVO> bachecaNewsVOFromBachecaNews(List<BachecaNews> bnList) {
		ArrayList<BachecaNewsVO> res = new ArrayList<BachecaNewsVO>();
		for(BachecaNews bn : bnList) {
			BachecaNewsVO bnVO = bachecaNewsVOFromBachecaNews(bn);
			res.add(bnVO);
		}
		return res;
	}
	
	public static BachecaNewsVO bachecaNewsVOFromBachecaNews(BachecaNews bn) {
		BachecaNewsVO res = new BachecaNewsVO();
		res.setId(bn.getId());
		res.setTitolo(bn.getTitolo());
		res.setPriorita(bn.getPriorita());
		res.setMessaggio(bn.getMessaggio());
		res.setDecorrenza(bn.getDataDecorrenza());
		res.setScadenza(bn.getDataScadenza());
		res.setImgExtContent(bn.getImgExtBlob());
		res.setImgExtFileName(bn.getNomeFileImgExt());
		res.setImgIntContent(bn.getImgIntBlob());
		res.setImgIntFileName(bn.getNomeFileImgInt());
		res.setOpAggiornamento(bn.getOpAggiornamento());
		res.setOpInserimento(bn.getOpInserimento());
		return res;
	}
	
	public static BachecaNews bachecaNewsVOToBachecaNews(BachecaNewsVO bn) {
		BachecaNews res = new BachecaNews();
		res.setId(bn.getId());
		res.setTitolo(bn.getTitolo());
		res.setPriorita(bn.getPriorita());
		res.setMessaggio(bn.getMessaggio());
		res.setDataDecorrenza(new java.sql.Date(bn.getDecorrenza().getTime()));
		res.setDataScadenza(new java.sql.Date(bn.getScadenza().getTime()));
		res.setImgExtBlob(bn.getImgExtContent());
		res.setNomeFileImgExt(bn.getImgExtFileName());
		res.setImgIntBlob(bn.getImgIntContent());
		res.setNomeFileImgInt(bn.getImgIntFileName());
		res.setOpAggiornamento(bn.getOpAggiornamento());
		res.setOpInserimento(bn.getOpInserimento());
		return res;
	}
}

