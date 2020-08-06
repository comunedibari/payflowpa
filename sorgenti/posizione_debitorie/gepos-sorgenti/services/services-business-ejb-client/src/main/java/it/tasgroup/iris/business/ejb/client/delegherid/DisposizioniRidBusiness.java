/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.delegherid;

import it.nch.profile.IProfileManager;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.PagamentoRidDTO;


/**
 * @author FabriziE
 *
 */
public interface DisposizioniRidBusiness
{	

//	public List<DelegheRidDTO> readDRIDs(IProfileManager profile, DTO dto) throws Exception;
	
	public GestioneFlussi createDisposizioneRid(IProfileManager profile, PagamentoRidDTO riddto, AllineamentiElettroniciArchiviDTO delegadto, ContoTecnico contoTecnico) throws Exception;

	public GestioneFlussi readDistintePagamentoById(String id) throws Exception;
	
}
