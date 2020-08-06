package it.tasgroup.idp.avvisi.digitali.impl;

import javax.persistence.EntityManager;

import it.tasgroup.idp.avvisi.digitali.IAvvisiDigitaliPlugin;
import it.tasgroup.idp.avvisi.digitali.exceptions.AvvisiDigitaliException;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;

public class AvvisiDigitaliNullPlugin implements IAvvisiDigitaliPlugin {

	@Override
	public void deletePendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertPendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePendenza(Pendenze p, EntityManager em) throws AvvisiDigitaliException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCondizione(CondizioniPagamento p, EntityManager em) throws AvvisiDigitaliException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCondizione(CondizioniPagamento c, EntityManager em) throws AvvisiDigitaliException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generaBollettinoCondizione(CondizioniPagamento p, EntityManager em) throws AvvisiDigitaliException {
		// TODO Auto-generated method stub
		
	}

}
