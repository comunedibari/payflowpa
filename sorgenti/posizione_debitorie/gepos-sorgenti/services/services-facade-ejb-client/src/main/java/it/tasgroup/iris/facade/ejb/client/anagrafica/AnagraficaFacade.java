/**
 * 
 */
package it.tasgroup.iris.facade.ejb.client.anagrafica;

import it.tasgroup.iris.dto.anagrafica.ContoTecnicoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.ProvinceDTO;

import java.util.List;

/**
 * @author pazzik
 *
 */
public interface AnagraficaFacade {
	
	public IntestatarioDTO readIntestatario(String idFiscale);
	
	public IntestatarioDTO readAnagraficaRT();
	
	public ContoTecnicoDTO readContoTecnico();
	
	public List<ProvinceDTO> readListaProvince();
	
	public String getChiaveOperatoreGestoreComunicazioni(String codiceFiscaleOperatore, String tipoOperatore);

	public IntestatarioDTO readIntestatarioById(String intestatario); 

}
