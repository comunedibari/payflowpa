/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.anagrafica;

import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IbanEnti;
import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.IbanEnteDTO;

import java.util.List;


/**
 * @author PazziK
 *
 */
public interface IntestatariBusiness
{	
	public Intestatari readIntestatarioById(String id);

	public Intestatari readIntestatarioByLapl(String lapl, boolean addressRequired);

	public Enti readEnteByIntestatario(String intestatario);

	public String readLAPLEnteByCdAziendaSanitaria(String cdAziendaSanitaria);

	public Enti readEnte(String codEnte);

	public Enti getEnteByCodiceFiscale(String codFiscaleEnte);

	public List<Enti> getTuttiEnti();
	
	public boolean checkIdFiscaleEnte(String idFiscaleEnte);

	public Enti readEnteByIdEnte(String idEnte);
	
	public List<IbanEnti> getListIbanByEnte(ContainerDTO inputDTO);
	
	public void updateIbanByEnte(IbanEnteDTO ibanDTO);
	
}	
