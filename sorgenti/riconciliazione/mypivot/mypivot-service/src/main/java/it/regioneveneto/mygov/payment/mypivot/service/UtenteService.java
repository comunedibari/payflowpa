package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface UtenteService {
	
//	List<Utente> getAll();
//	
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//	void add();
	
	UtenteTO getUtenteWSByCodIpaEnte(final String codIpaEnte);
	
	List<UtenteTO> findByCodIpaEnte(final String codIpaEnte);

	UtenteTO getUtenteByCodFedUserId(String codFedUSerId);
	
	UtenteTO getUtenteByGlobalDefault();
}
