/**
 * 
 */
package it.tasgroup.iris.business.ejb;

import it.nch.erbweb.constants.CommonConstants;
import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.business.ejb.client.IrisCacheSingletonLocal;
import it.tasgroup.iris.business.ejb.client.IrisCacheSingletonRemote;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.Province;
import it.tasgroup.iris.persistence.dao.interfaces.ContoTecnicoDAO;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.interfaces.ProvinceDAO;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;




/**
 * Cache di Iris per le Persistence Entities che non dovrebbero variare durante l'utilizzo dell'applicazione.
 * 
 * TODO PAZZIK dopo aver verificato che funziona, propagarne l'uso a tutti i casi di richiesta del ContoTecnico da DB
 * per evitare troppi accessi al DB.
 * 
 * Cache di IRIS per il BE, o comunque per tutti i progetti che hanno visibilità delle Persistence Entities.
 * 
 * @author pazzik
 *
 */
@Singleton
@Startup
public class IrisCacheSingletonBean implements IrisCacheSingletonLocal, IrisCacheSingletonRemote{
	
	private static final long serialVersionUID = 1L;

	private ContoTecnico contoTecnico;
	
	private Intestatari anagraficaRT;
	
	private List<Province> provinceItaliane = null;
	
	private String idAnagrafica = null;
	
	
	@EJB(name = "ContoTecnicoDaoImpl")
	private ContoTecnicoDAO contoTecnicoDAO;
	
	@EJB(name = "IntestatariDaoImpl")
	private IntestatariDAO intestatariDAO;
	
	@EJB(name = "ProvinceDaoImpl")
	private ProvinceDAO provinceDAO;
	
	@PostConstruct
	public void init() {
		
			 
		 try {	 

			idAnagrafica = CommonConstants.ANAGRAFICA_RT;
			 
			provinceItaliane = provinceDAO.readListaProvince();
		
			contoTecnico = contoTecnicoDAO.readUniqueContoTecnicoAttivo(idAnagrafica);

			anagraficaRT = intestatariDAO.retrieveIntestatarioById(idAnagrafica);

			if (anagraficaRT != null ) {
				// TODO PAZZIK AVOID LAZY INIT
				anagraficaRT.getEntiobj();
					 
				anagraficaRT.getIndirizzipostaliobj();
			}
			
			
			 System.out.println("Cache Inizializzata:");
			 System.out.println("---------------------");
			 System.out.println("Province: caricate="+provinceItaliane.size());
			 System.out.println("Conto Tecnico: "+(contoTecnico==null?"<null>: la piattaforma lavora senza conto tecnico centrale.":contoTecnico.getIban()));
			 System.out.println("Intestatario Conto Tecnico: "+(anagraficaRT==null?"<null>: la piattaforma lavora senza conto tecnico centrale.":anagraficaRT.getLapl()));
			 System.out.println("---------------------");

				 
		 } catch (Throwable e) {
			 	System.out.println("WARNING:  ERRORE NEL CARICAMENTO DEL SINGLETON BEAN ");
		        System.out.println("Cause: " + e.getCause());
		        System.out.println("Message: " + e.getMessage());
		        System.out.println("Class: " + e.getClass());
		        System.out.println("StackTrace: " + e.getStackTrace());
		  }
		 
		 
	  }
	

	public ContoTecnico getContoTecnico() {
		return contoTecnico;
	}


	public Intestatari getAnagraficaRT() {
		return anagraficaRT;
	}


	public List<Province> getProvinceItaliane() {
		return provinceItaliane;
	}
	
	

}
