package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.UtenteDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;

@Service
public class UtenteServiceImpl implements UtenteService {
	
	@Autowired
	private ModelMapperUtil modelMapperUtil;
	@Autowired
	private UtenteDao utenteDao;

	public UtenteTO getUtenteWSByCodIpaEnte(final String codIpaEnte) {
		Utente utentePo = utenteDao.findByCodFedUserIdIgnoreCase(codIpaEnte + "-WS_USER");
		UtenteTO utente = modelMapperUtil.map(utentePo, UtenteTO.class);

		return utente;
	}

	@Override
	public List<UtenteTO> findByCodIpaEnte(final String codIpaEnte) {
		List<Utente> utenti = utenteDao.findByCodIpaEnte(codIpaEnte);
		List<UtenteTO> utentiTO = new ArrayList<UtenteTO>();
		if (CollectionUtils.isNotEmpty(utenti)) {
			for (Utente utente : utenti) {
				UtenteTO utenteTO = modelMapperUtil.map(utente, UtenteTO.class);
				utentiTO.add(utenteTO);
			}
		}
		return utentiTO;
	}

	public UtenteTO getUtenteByCodFedUserId(String codFedUSerId) {
		Utente utentePo = utenteDao.findByCodFedUserIdIgnoreCase(codFedUSerId);
		UtenteTO utente = modelMapperUtil.map(utentePo, UtenteTO.class);

		return utente;
	}

	
	public UtenteTO getUtenteByGlobalDefault() {

		Utente utentePo = utenteDao.findByCodFedUserIdIgnoreCase("utente.globale");
		UtenteTO utente = modelMapperUtil.map(utentePo, UtenteTO.class);

		return utente;
	}
}
