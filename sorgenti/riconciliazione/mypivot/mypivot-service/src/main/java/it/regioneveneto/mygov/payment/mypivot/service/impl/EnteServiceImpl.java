package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.EnteDao;
import it.regioneveneto.mygov.payment.mypivot.dao.EnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.OperatoreEnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.EnteDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.OperatoreEnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;

@Service
public class EnteServiceImpl implements EnteService {

	@Autowired
	private EnteTipoDovutoDao enteTipoDovutoDao;
	
	@Autowired
	private EnteDao enteDao;
	
	@Autowired
	private OperatoreEnteTipoDovutoDao operatoreEnteTipoDovutoDao;
	
	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Resource
	private Environment env;

//	@Autowired
//	private UtenteService utenteService;
	
//	@Autowired
//	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

//	@Value("${myPivot.enableGlobalProfile}")
//	private Boolean enableGlobalProfile;

	public Boolean verificaPassword(final String codIpaEnte, final String password) {

		List<Ente> entes = enteDao.findByCodIpaEnteAndNullPassword(codIpaEnte);
		
		if (entes.size() > 1) {
			throw new DataIntegrityViolationException("mypivot.ente.enteDuplicato");
		}
		
		//se la password in database e' NULL autorizzo 
		if (entes.size() == 1)
			return true;
		
		if (password == null) {
			return false;
		}
		
		//altrimenti controllo che password in input corrisponda con password sul database
		entes = enteDao.findByCodIpaEnteAndPassword(codIpaEnte, password);

		if (entes.size() > 1) {
			throw new DataIntegrityViolationException("mypivot.ente.enteDuplicato");
		}
		if (entes.size() == 0)
			return false;
		else
			return true;
	}
	
	public List<EnteTO> getAllEnti() {

		List<Ente> entes = enteDao.findAll();
		List<EnteTO> enteTOs = mapEntitiesListToDtosList(entes);

		return enteTOs;
	}

	public PageDto<EnteTO> getEnteListPage(
			Pageable pageable) {
		
		/*
		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}
		*/
		//Pageable pageable = new PageRequest(pageToGet, size);

		Page<Ente> entePage = enteDao.findAllBy(pageable);

		List<EnteTO> enteTOs = mapEntitiesListToDtosList(entePage
				.getContent());

		PageDto<EnteTO> enteDtoPage = new PageDto<EnteTO>();
		enteDtoPage.setList(enteTOs);
		enteDtoPage.setNextPage(entePage.getNumber() + 1 < entePage.getTotalPages());
		enteDtoPage.setPage(entePage.getNumber() + 1);
		enteDtoPage.setPageSize(entePage.getSize());
		enteDtoPage.setPreviousPage(entePage.getNumber() > 0);
		enteDtoPage.setTotalPages(entePage.getTotalPages());
		enteDtoPage.setTotalRecords(entePage.getTotalElements());

		return enteDtoPage;
	}
	
	public PageDto<EnteTO> getEntePage(final String fullTextSearch,
			final int page, final int size, final Direction direction,
			final String properties) {

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, size, direction,
				properties);

		Page<Ente> entePage = enteDao.findByDeNomeEnteContainingIgnoreCase(
				fullTextSearch, pageable);

		List<EnteTO> enteTOs = mapEntitiesListToDtosList(entePage
				.getContent());

		PageDto<EnteTO> enteDtoPage = new PageDto<EnteTO>();
		enteDtoPage.setList(enteTOs);
		enteDtoPage.setNextPage(entePage.hasNextPage());
		enteDtoPage.setPage(entePage.getNumber() + 1);
		enteDtoPage.setPageSize(entePage.getSize());
		enteDtoPage.setPreviousPage(entePage.hasPreviousPage());
		enteDtoPage.setTotalPages(entePage.getTotalPages());
		enteDtoPage.setTotalRecords(entePage.getTotalElements());

		return enteDtoPage;
	}

	/**
	 * @param entities
	 * @return
	 */
	private List<EnteTO> mapEntitiesListToDtosList(List<Ente> entities) {
		List<EnteTO> dtos = new ArrayList<EnteTO>();
		for (Ente ente : entities) {
			EnteTO enteTO = modelMapperUtil.map(ente, EnteTO.class);
			dtos.add(enteTO);
		}
		return dtos;
	}
	
	public EnteTO getByCodIpaEnte(String codIpaEnte){
		Ente ente = enteDao.findByCodIpaEnte(codIpaEnte);
		
		if (ente == null) {
			return null;
		}
		EnteTO enteTO = modelMapperUtil.map(ente, EnteTO.class);

		return enteTO;
	}

	@Override
	public String getLogoEnteByCodIpaEnte(String codIpaEnte){
		Ente ente = enteDao.findByCodIpaEnte(codIpaEnte);
		if (ente == null) {
			return null;
		}		
		String logoEnte = enteDao.getLogoEnteByCodIpa(codIpaEnte);
		if (StringUtils.isNotBlank(logoEnte))
			return logoEnte;
		else {
			return env.getProperty("mypivot.logoDefault");
		}
	}
	

	public Ente createEnte(EnteDto enteDto) {
		Ente newEnte = new Ente();
		
		UtenteTO utenteTO = null;
		
//		if(enableGlobalProfile) 
//			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		newEnte.setCodiceFiscaleEnte(enteDto.getCodFiscale());
		newEnte.setCodIpaEnte(enteDto.getCodIpa());
		newEnte.setDeNomeEnte(enteDto.getNomeEnte());
		
		newEnte.setEmailAmministratore(enteDto.getEmailAmministratore());
		
		newEnte.setDtCreazione(new Date());
		newEnte.setDtUltimaModifica(new Date());
		
		String myboxClientKey = UUID.randomUUID().toString();
		String myboxClientSecret = UUID.randomUUID().toString();
//		String dePassword = RandomStringUtils.randomAlphanumeric(12).toUpperCase();
		
		newEnte.setMyboxClientKey(myboxClientKey);
		newEnte.setMyboxClientSecret(myboxClientSecret);
		
		Ente newE = enteDao.save(newEnte);
		
//		updateOperatoreEnteTipoDovuto(newE, utenteTO.codFedUserId);
		
		return newEnte;
	}

	
	private void updateOperatoreEnteTipoDovuto(Ente newEnte, String codFedUserId) {
		List<Ente> listEnte = enteDao.findAll();
		if (!listEnte.isEmpty() && listEnte.size()> 1 && !listEnte.get(0).getCodIpaEnte().contentEquals(newEnte.getCodIpaEnte()) ){
			
			Ente ente = listEnte.get(0);	
			List<EnteTipoDovuto> listEnteTipDovuto = enteTipoDovutoDao.findByCodIpaEnte(ente.getCodIpaEnte());
			OperatoreEnteTipoDovuto newOperatore = null;
			EnteTipoDovuto newEnteTipoDovuto = null;
			
			for (EnteTipoDovuto item : listEnteTipDovuto) {
				newOperatore = new OperatoreEnteTipoDovuto();
				newEnteTipoDovuto = new EnteTipoDovuto();
					
				newEnteTipoDovuto.setEnte(newEnte);
				newEnteTipoDovuto.setCodTipo(item.getCodTipo());
				newEnteTipoDovuto.setDeTipo(item.getDeTipo());
				EnteTipoDovuto newETD = enteTipoDovutoDao.save(newEnteTipoDovuto);

				newOperatore.setCodFedUserId(codFedUserId);
				newOperatore.setEnteTipoDovuto(newETD);
				newOperatore.setFlgAttivo(true);
				operatoreEnteTipoDovutoDao.save(newOperatore);
			}
		}
	}
	
	
	public Ente updateEnte(EnteDto enteDto) {
		Ente ente = enteDao.findById(enteDto.getId());
		if (ente == null) {
			throw new EntityNotFoundException("Ente non trovato con codIpa " + enteDto.getCodIpa());
		}
		else {
			ente.setCodiceFiscaleEnte(enteDto.getCodFiscale());
			ente.setCodIpaEnte(enteDto.getCodIpa());
			ente.setDeNomeEnte(enteDto.getNomeEnte());
			
			ente.setEmailAmministratore(enteDto.getEmailAmministratore());
			ente.setDtUltimaModifica(new Date());
			
			enteDao.saveAndFlush(ente);
		}
		return ente;
	}

	public void deleteEnte(String codIpa) {
		
		Ente ente = enteDao.findByCodIpaEnte(codIpa);				
		if (ente == null) {
			throw new EntityNotFoundException("Ente non trovato con codIpa " + codIpa);
		}
		else {
			enteDao.delete(ente);
		}
		
	}

}
