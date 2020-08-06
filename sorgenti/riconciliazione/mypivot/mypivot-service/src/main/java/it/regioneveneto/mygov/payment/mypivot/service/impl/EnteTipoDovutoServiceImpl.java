package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.EnteDao;
import it.regioneveneto.mygov.payment.mypivot.dao.EnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.OperatoreEnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.EnteTipoDovutoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.OperatoreEnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;

/**
 * @author giorgiovallini
 *
 */
@Service("enteTipoDovutoService")
public class EnteTipoDovutoServiceImpl implements EnteTipoDovutoService {

	private static final Logger LOG = Logger.getLogger(EnteTipoDovutoServiceImpl.class);
	
	@Autowired
	private EnteTipoDovutoDao enteTipoDovutoDao;

	@Autowired
	private ModelMapperUtil modelMapperUtil;
	
	@Autowired
	private EnteDao enteDao;

	@Autowired
	private UtenteService utenteService;
	
	
//	@Autowired
//	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private OperatoreEnteTipoDovutoDao operatoreEnteTipoDovutoDao;
	
	
	@Override
	public List<EnteTipoDovuto> getByCodIpaEnte(String codIpaEnte) {
		return enteTipoDovutoDao.findByCodIpaEnte(codIpaEnte);
	}

	@Override
	public EnteTipoDovutoTO getById(long id){
		EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findById(id);
		
		if (enteTipoDovuto == null) {
			return null;
		}
		EnteTipoDovutoTO enteTipoDovutoTO = modelMapperUtil.map(enteTipoDovuto, EnteTipoDovutoTO.class);

		return enteTipoDovutoTO;
	}
	
	public List<EnteTipoDovutoTO> getByCodIpaEnteAndCodTipoDovutoList(final String codIpaEnte, final Collection<String> listaCodiciTipoDovuto) {
		List<EnteTipoDovuto> lista = enteTipoDovutoDao.findByCodIpaEnteAndCodTipoDovutoList(codIpaEnte, listaCodiciTipoDovuto);
		
		List<EnteTipoDovutoTO> listaTO = new ArrayList<EnteTipoDovutoTO>();
		
		for (EnteTipoDovuto etd : lista) {
			EnteTipoDovutoTO to = modelMapperUtil.map(etd, EnteTipoDovutoTO.class);
			listaTO.add(to);
		}
		
		return listaTO;
	}

	public EnteTipoDovuto getByCodIpaEnteAndCodTipoDovuto(final String codIpaEnte, final String codiceTipoDovuto) {
		
		EnteTipoDovuto item = enteTipoDovutoDao.findByCodIpaEnteAndCodTipo(codIpaEnte, codiceTipoDovuto);
		
		return item;
	}

	
	@Override
	@SuppressWarnings("null")
	public EnteTipoDovuto createEnteTipoDovuto(EnteTipoDovutoDto enteTipoDovutoDto, Boolean flagGlobal) {
		
		EnteTipoDovuto newEnteTipoDovuto = new EnteTipoDovuto();
		
		UtenteTO utenteTO = null;
		
		if(flagGlobal) 
			utenteTO = utenteService.getUtenteByGlobalDefault();
		
		EnteTO enteTO = enteTipoDovutoDto.getEnte();
		System.out.println("enteDto: " + enteTO);
		LOG.debug("enteDto: " + enteTO);
		if(enteTO!=null) {
			Ente ente = enteDao.findByCodIpaEnte(enteTO.getCodIpaEnte());
			newEnteTipoDovuto.setEnte(ente);
			LOG.debug("ente: " + ente);
			System.out.println("ente: " + ente);
		}
		
		newEnteTipoDovuto.setCodTipo(enteTipoDovutoDto.getCodTipo());
		newEnteTipoDovuto.setDeTipo(enteTipoDovutoDto.getDeTipo());
		
		EnteTipoDovuto newETD = enteTipoDovutoDao.save(newEnteTipoDovuto);
		
		if(flagGlobal) {
				OperatoreEnteTipoDovuto newOperatore = new OperatoreEnteTipoDovuto();
				newOperatore.setCodFedUserId(utenteTO.getCodFedUserId());
				newOperatore.setEnteTipoDovuto(newETD);
				newOperatore.setFlgAttivo(true);
				operatoreEnteTipoDovutoDao.save(newOperatore);		
				
		//		updateOperatoreEnteTipoDovuto(newETD, utenteTO.codFedUserId);
		}
		
		return newEnteTipoDovuto;
	}


	private void updateOperatoreEnteTipoDovuto(EnteTipoDovuto newETD, String codFedUserId) {
		
		List<Ente> listEnte = enteDao.findAll();
		if (!listEnte.isEmpty() && listEnte.size()> 1){
			
			OperatoreEnteTipoDovuto newOperatore = null;
			EnteTipoDovuto newEnteTipoDovuto = null;
			
			for (Ente item : listEnte) {
				if(!item.getCodIpaEnte().contentEquals(newETD.getEnte().getCodIpaEnte())) {
					newOperatore = new OperatoreEnteTipoDovuto();
					newEnteTipoDovuto = new EnteTipoDovuto();
						
					newEnteTipoDovuto.setEnte(item);
					newEnteTipoDovuto.setCodTipo(newETD.getCodTipo());
					newEnteTipoDovuto.setDeTipo(newETD.getDeTipo());
					EnteTipoDovuto savedETD = enteTipoDovutoDao.save(newEnteTipoDovuto);
	
					newOperatore.setCodFedUserId(codFedUserId);
					newOperatore.setEnteTipoDovuto(savedETD);
					newOperatore.setFlgAttivo(true);
					operatoreEnteTipoDovutoDao.save(newOperatore);
				}
			}
		}
	}
	
	@Override
	public EnteTipoDovuto updateEnteTipoDovuto(EnteTipoDovutoDto enteTipoDovutoDto) {
		
		EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findById(enteTipoDovutoDto.getId());
		if (enteTipoDovuto == null) {
			throw new EntityNotFoundException("Tipo Dovuto non trovato con id " + enteTipoDovutoDto.getCodTipo());
		}
		else {
			enteTipoDovuto.setCodTipo(enteTipoDovutoDto.getCodTipo());
			enteTipoDovuto.setDeTipo(enteTipoDovutoDto.getDeTipo());
			
			enteTipoDovutoDao.saveAndFlush(enteTipoDovuto);
		}
		return enteTipoDovuto;	
	
	}

	@Override
	public PageDto<EnteTipoDovutoTO> getByCodIpaEntePage(
			Pageable pageable,
			String codIpaEnte) {
		
		/*
		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}
		*/
		//Pageable pageable = new PageRequest(pageToGet, size);

		Page<EnteTipoDovuto> entePage = enteTipoDovutoDao.findByCodIpaEnte(codIpaEnte, pageable);

		List<EnteTipoDovutoTO> enteTipoDovutoTOs = mapEntitiesListToDtosList(entePage
				.getContent());

		PageDto<EnteTipoDovutoTO> enteTipoDovutoDtoPage = new PageDto<EnteTipoDovutoTO>();
		
		enteTipoDovutoDtoPage.setList(enteTipoDovutoTOs);
		enteTipoDovutoDtoPage.setNextPage(entePage.getNumber() + 1 < entePage.getTotalPages());
		enteTipoDovutoDtoPage.setPage(entePage.getNumber() + 1);
		enteTipoDovutoDtoPage.setPageSize(entePage.getSize());
		enteTipoDovutoDtoPage.setPreviousPage(entePage.getNumber() > 0);
		enteTipoDovutoDtoPage.setTotalPages(entePage.getTotalPages());
		enteTipoDovutoDtoPage.setTotalRecords(entePage.getTotalElements());

		return enteTipoDovutoDtoPage;
	}

	
	/**
	 * @param entities
	 * @return
	 */
	private List<EnteTipoDovutoTO> mapEntitiesListToDtosList(List<EnteTipoDovuto> entities) {
		List<EnteTipoDovutoTO> dtos = new ArrayList<EnteTipoDovutoTO>();
		for (EnteTipoDovuto enteTipoDovuto : entities) {
			EnteTipoDovutoTO enteTipoDovutoTO = modelMapperUtil.map(enteTipoDovuto, EnteTipoDovutoTO.class);
			dtos.add(enteTipoDovutoTO);
		}
		return dtos;
	}

	@Override
	public void deleteEnteTipoDovuto(long idEnteTipoDovuto) {
		EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findById(idEnteTipoDovuto);
		if (enteTipoDovuto == null) {
			throw new EntityNotFoundException("Tipo Dovuto non trovato con id " + enteTipoDovuto);
		}
		else {
			enteTipoDovutoDao.delete(enteTipoDovuto);
		}
		
	}

}
