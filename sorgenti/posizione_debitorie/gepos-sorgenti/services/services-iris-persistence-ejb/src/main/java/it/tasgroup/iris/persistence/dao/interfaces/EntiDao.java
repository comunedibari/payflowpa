package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.is.fo.profilo.Enti;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EntiDao extends Dao<Enti>{
	
	public List<Enti> listEntiAbleToAnonymousPayment();
	
	public List<Enti> listEntiAbleToAnonymousPayment(String predeterminatoValue);
	
	public Enti getEntiAbleToAnonymousPaymentByIdEnte(String idEnte);
	
	public Enti getEntiAbleToAnonymousPaymentByCdEnte(String idEnte);
	
	public Enti getEntiAbleToAnonymousPaymentByLapl(String lapl);

    public String getIdEnteFromIntestatario(String intestatario);
    
    public String getIdEnte(String siaEnte, String laplEnte);
    
	public Enti readEnteByIntestatario(String intestatario);

	public List<Enti> listEntiAbleForTributo(String idTributo);
	
	public List<Enti> listEntiAbleForCdTrbEnte(String cdTrbEnte);

	public Enti readEnte(String codEnte);
	
	public Enti readEnteByCodFiscale(String codFiscaleEnte);
	
	public List<Enti> listTuttiEnti();
	
	public List<Enti> listaEnti();
}
