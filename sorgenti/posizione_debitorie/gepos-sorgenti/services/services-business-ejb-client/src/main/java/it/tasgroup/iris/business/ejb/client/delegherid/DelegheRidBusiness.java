/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.delegherid;

import it.nch.profile.IProfileManager;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.ContainerDTO;

import java.util.List;



/**
 * @author FabriziE
 *
 */
public interface DelegheRidBusiness
{	
	public List<AllineamentiElettroniciArchivi> readRichiestaDelegheByProfile(ContainerDTO containerDTO) throws Exception;

	public List<AllineamentiElettroniciArchivi> readDelegheAccettateByProfile(String sottoscrittore, String intestatario) throws Exception;
	
	public AllineamentiElettroniciArchivi verificaIbanDuplicato(String id,String sottoscrittore) throws Exception;

	public AllineamentiElettroniciArchivi verificaAbiDuplicato(String id,String sottoscrittore) throws Exception;

	public AllineamentiElettroniciArchivi readDelegaById(String iban) throws Exception;

	public AllineamentiElettroniciArchivi createRichiestaDelega(IProfileManager profile, AllineamentiElettroniciArchiviDTO dto) throws Exception;

	public AllineamentiElettroniciArchivi revocaDelega(IProfileManager profile, AllineamentiElettroniciArchiviDTO dto) throws Exception;
	
}
