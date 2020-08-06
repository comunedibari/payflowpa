/**
 * 
 */
package it.tasgroup.iris.facade.ejb.client.delegherid;

import it.nch.fwk.fo.dto.DTO;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;

import java.util.List;


/**
 * @author FabriziE
 *
 */
public interface DRIDFacade
{

	/**
	 * @author FabriziE
	 * @param profile
	 * @param inputVO
	 * @return
	 * @throws Exception
	*/ 
	public AllineamentiElettroniciArchiviDTO initDelegaByProfile(IProfileManager profile) throws Exception;

	/**
	 * @author FabriziE
	 * @param profile
	 * @param inputVO
	 * @return
	 * @throws Exception
	*/ 
	public ContainerDTO readRichiestaDelegheListByProfile(IProfileManager profile, ContainerDTO containerDTO) throws Exception;

	/**
	 * @author FabriziE
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	 */ 
	public AllineamentiElettroniciArchiviDTO verificaIbanDuplicato(IProfileManager profile, DTO inputVO) throws Exception;
	/**
	 * @author FabriziE
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	 */ 
	public AllineamentiElettroniciArchiviDTO verificaAbiDuplicato(IProfileManager profile, DTO inputVO) throws Exception;
	/**
	 * @author FabriziE
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	 */ 
	public AllineamentiElettroniciArchiviDTO readDelegaById(IProfileManager profile, DTO inputVO) throws Exception;

	/**
	 * @author FabriziE
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	 */ 
	public AllineamentiElettroniciArchiviDTO createDelega(IProfileManager profile, DTO inputVO) throws Exception;
	
	/**
	 * @author FabriziE
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	 */ 
	public AllineamentiElettroniciArchiviDTO revocaDelega(IProfileManager profile, DTO inputVO) throws Exception;

	/**
	 * @author FabriziE
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	 */ 

	public List<AllineamentiElettroniciArchiviDTO> readDelegheAccettateListByProfile(IProfileManager profile,DTO inputVO) throws Exception;

	/**
	 * @author FabriziE
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	 */ 
	public DistintePagamentoDTO createDisposizioneRid(IProfileManager profile, DTO inputVO) throws Exception;

	/**
	 * @author FabriziE
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	 */ 
	public DistintePagamentoDTO readDistentePagamentoById(IProfileManager profile, DTO inputVO) throws Exception;
	
}
