/**
 * 
 */
package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import it.nch.is.fo.profilo.EntiCommon;
import java.util.ArrayList;
import java.util.List;

import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;

/**
 * @author PazziK
 *
 */
public class IndirizzoDTOBuilder {
	
	public static IndirizzoDTO populateIndirizzoDTO(Intestatari intestatario) {
		
		IndirizzoDTO returnAddress = new IndirizzoDTO();
                       
		IndirizzipostaliCommon indirizzoEntity = intestatario
				.getIndirizzipostaliobj();
		returnAddress.setRagioneSociale(intestatario.getRagionesociale());
		returnAddress.setProvincia(indirizzoEntity.getProvinceIForm());
		returnAddress.setCap(intestatario.getIndirizzipostaliobj()
				.getCapCodeIForm());
		returnAddress.setCitta(intestatario.getIndirizzipostaliobj()
				.getCityIForm());
		returnAddress.setNumeroCivico(intestatario.getIndirizzipostaliobj()
				.getNumeroCivicoIForm());
		returnAddress.setVia(intestatario.getIndirizzipostaliobj()
				.getAddressIForm());
		returnAddress.setPiva(intestatario.getIndirizzipostaliobj()
				.getVatCodeIForm());
		returnAddress.setCodiceFiscale(indirizzoEntity.getFiscalCodeIForm());
		return returnAddress;
	}
	
	public static void populateIndirizzoList(Intestatari intestatario,
			DocumentoDiPagamentoDTO documentoDiPagamentoDTO) {
		if (intestatario!=null) {
			IndirizzoDTO dtoAddress = populateIndirizzoDTO(intestatario);
			List<IndirizzoDTO> indirizziRT = new ArrayList<IndirizzoDTO>();
			indirizziRT.add(dtoAddress);
			documentoDiPagamentoDTO.setIndirizziRT(indirizziRT);
		}	
	}


}
