package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface OperatoreEnteTipoDovutoService {
	
	List<OperatoreEnteTipoDovutoTO> findActiveByCodFedUserIdAndCodIpaEnte(final String codFedUserId, final String codIpaEnte);
	List<EnteTipoDovutoTO> getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(final String codFedUserId, final String codIpaEnte);
	List<EnteTipoDovutoTO> getListaEnteTipoDovutoForOperatoreAndCodIpaEnteNoSession(final String codFedUserId, final String codIpaEnte);
	List<String> getListaCodTipoDovutoForOperatoreAndCodIpaEnte(final String codFedUserId, final String codIpaEnte);
	boolean isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(final String codFedUserId, final String codIpaEnte, final String codTipoDovuto);
}
