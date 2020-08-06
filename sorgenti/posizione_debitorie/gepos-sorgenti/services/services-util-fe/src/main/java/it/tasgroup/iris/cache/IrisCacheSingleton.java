/**
 * 
 */
package it.tasgroup.iris.cache;

import it.tasgroup.iris.dto.anagrafica.ContoTecnicoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.ProvinceDTO;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaFacade;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * Cache di IRIS per il FE, o comunque per tutti i progetti che hanno visibilità dei DTO.
 * 
 * @author pazzik
 *
 */
public class IrisCacheSingleton {
	
	private static ContoTecnicoDTO contoTecnico = null;
	
	private static IntestatarioDTO anagraficaRT = null;
	
	private static List<ProvinceDTO> provinceItaliane = null;
	
	private static final Logger LOGGER = LogManager.getLogger(IrisCacheSingleton.class);    
	
	
	static{
		
		try {
			
			AnagraficaFacade anagraficaBean = (AnagraficaFacade) ServiceLocator.getSLSBProxy("anagraficaFacadeBean");
			
			anagraficaRT = anagraficaBean.readAnagraficaRT();
			
			contoTecnico = anagraficaBean.readContoTecnico();
			
			provinceItaliane = anagraficaBean.readListaProvince();
					
		} catch (Exception e) {
			
			LOGGER.error("ERRORE NELLA COSTRUZIONE STATICA DELLA IRIS CACHE SINGLETON", e);
						
		}		
		
		LOGGER.info("SUCCESSO NELLA COSTRUZIONE STATICA DELLA IRIS CACHE SINGLETON");
		
	}
	
	private IrisCacheSingleton(){
		
	}

	public static ContoTecnicoDTO getContoTecnico() {
		return contoTecnico;
	}

	public static IntestatarioDTO getAnagraficaRT() {
		return anagraficaRT;
	}

	public static List<ProvinceDTO> getProvinceItaliane() {
		return provinceItaliane;
	}

}
