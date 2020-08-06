/**
 * 
 */
package it.tasgroup.iris.payment.helper;

import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.IntestatariCommon;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;

/**
 * @author pazzik
 *
 */
public class AnagraficaDTOBuilder {
	
/**
 * 
 * Popola un IntestatarioDTO contenente i dati dell'intestatario di riferimento 
 * associato all'operatore passato in ingresso.
 * 
 * @param operatore
 * @param email
 * @return
 */
public static IntestatarioDTO fillIntestatarioDTO(OperatoriPojo operatore, String email) {
		
		IntestatarioDTO versanteDTO = new IntestatarioDTO();
		
		versanteDTO.setEmail(email);

        // TODO PAZZIK, TESTARE L'INDIRIZZO PER I DIVERSI OPERATORI DI UNA STESSA AZIENDA
        // se questo non dovesse essere corretto, riestrarlo da DB filtrando per profileManager.getLapl()
        IndirizzoDTO indirizzo = new IndirizzoDTO();
        
        IntestatariCommon intestatario = operatore.getIntestatariobj();
        
        IndirizzipostaliCommon indirizzoOperatore =  intestatario.getIndirizzipostaliobjIForm();
        
        versanteDTO.setRagioneSociale(intestatario.getRagionesocialeIForm());
        
        versanteDTO.setIdFiscale(operatore.getIntestatariobj().getLaplIForm());
        
        indirizzo.setVia(indirizzoOperatore.getAddressIForm());
        
        indirizzo.setCitta(indirizzoOperatore.getCityIForm());
        
        indirizzo.setNazione(indirizzoOperatore.getCountryIForm());
        
        indirizzo.setProvincia(indirizzoOperatore.getProvinceIForm());
        
        indirizzo.setCap(indirizzoOperatore.getCapCodeIForm());
        
        indirizzo.setNumeroCivico(indirizzoOperatore.getNumeroCivicoIForm());
        
        versanteDTO.setIndirizzo(indirizzo);
        
        return versanteDTO;
				
	}

}
