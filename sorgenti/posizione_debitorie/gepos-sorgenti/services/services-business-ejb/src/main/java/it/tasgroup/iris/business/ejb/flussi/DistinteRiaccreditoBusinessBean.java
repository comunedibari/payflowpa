package it.tasgroup.iris.business.ejb.flussi;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.tasgroup.iris.business.ejb.client.flussi.DistinteRiaccreditoBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.DistinteRiaccreditoBusinessRemote;
import it.tasgroup.iris.domain.BonificiRiaccredito;
import it.tasgroup.iris.domain.BozzeBonificiRiaccredito;
import it.tasgroup.iris.domain.DistinteRiaccredito;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.BonificiRiaccreditoDao;
import it.tasgroup.iris.persistence.dao.interfaces.BozzeBonificiRiaccreditoDao;
import it.tasgroup.iris.persistence.dao.interfaces.DistinteRiaccreditoDao;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiBonificiRiaccreditoDao;
import it.tasgroup.iris.persistence.dao.interfaces.RendicontazioniDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "DistinteRiaccreditoBusiness")
public class DistinteRiaccreditoBusinessBean implements DistinteRiaccreditoBusinessLocal, DistinteRiaccreditoBusinessRemote {

	private static final Logger LOGGER = LogManager.getLogger(DistinteRiaccreditoBusinessBean.class);

	@EJB(name = "DistinteRiaccreditoDaoService")
	DistinteRiaccreditoDao distinteRiaccreditoDAO;
	
	@EJB(name = "BonificiRiaccreditoDaoService")
	BonificiRiaccreditoDao bonificiRiaccreditoDAO;
	
	@EJB(name = "BozzeBonificiRiaccreditoDaoService")
	BozzeBonificiRiaccreditoDao bozzeBonificiRiaccreditoDAO;

	@EJB(name = "EsitiBonificiRiaccreditoDaoService")
	EsitiBonificiRiaccreditoDao esitiBonificiRiaccreditoDAO;

	@EJB(name = "RendicontazioniDaoService")
	RendicontazioniDao rendicontazioniDAO;
	
	
	@Override
	public List<Object[]> getListDistinteRiaccreditoByFilterParameters(ContainerDTO dto) {
		
		return (List<Object[]>) distinteRiaccreditoDAO.listDistinteRiaccreditoByFilterParameters(dto);
	}
	@Override
	public List<Object[]> getListDistinteRiaccreditoByFilterParametersFull(ContainerDTO dto) {
		
		return (List<Object[]>) distinteRiaccreditoDAO.listDistinteRiaccreditoByFilterParametersFull(dto);
	}

	@Override
    public List<CodiceDescrizioneVO> listaBeneficiari(){
	    return (List<CodiceDescrizioneVO>) distinteRiaccreditoDAO.listaBeneficiari();
    }
    
    @Override
    public Map<String, Map<String, String>> listaDebiti(){
        return (Map<String, Map<String, String>>) distinteRiaccreditoDAO.listaDebiti();
    }

	@Override
	public DistinteRiaccredito getDistinteRiaccreditoById(Long id) {
		
		return distinteRiaccreditoDAO.retrieveDistintaRiaccreditoById(id);
	}



	@Override
	public List<BonificiRiaccredito> getListBonificiRiaccreditoByIdDistinta(ContainerDTO containerDTO) {
	
		return bonificiRiaccreditoDAO.listBonificiRiaccreditoByIdDistinta(containerDTO);
	}
	
	@Override
	public List<BozzeBonificiRiaccredito> getListBozzeBonificiRiaccreditoByIdBonifico(Integer idBonifico){
		return bozzeBonificiRiaccreditoDAO.getListBozzeBonificiRiaccreditoByIdBonifico(idBonifico);
	}

	

	@Override
	public EsitiBonificiRiaccredito getEsitiBonificiRiaccreditoByIdBonifico(Long id) {
		
		return esitiBonificiRiaccreditoDAO.getEsitiBonificiRiaccreditoByIdBonifico(id);
	}

	
	
	@Override
	public Rendicontazioni getRendicontazioniById(Long id) {
		
		return rendicontazioniDAO.retrieveRendicontazioniById(id);
	}
	
	@Override
	public List<DistinteRiaccredito> getDistinteRiaccreditoByIdBozzeBonificiRiaccredito(ContainerDTO containerDTO){

		return distinteRiaccreditoDAO.getDistinteRiaccreditoByIdBozzeBonificiRiaccredito(containerDTO);
	}

	@Override
	public List<DistinteRiaccredito> getDistinteRiaccreditoByIdBonificiRiaccredito(ContainerDTO containerDTO){

		return distinteRiaccreditoDAO.getDistinteRiaccreditoByIdBonificiRiaccredito(containerDTO);
	}
}
