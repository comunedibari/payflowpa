/**
 * 
 */
package it.tasgroup.iris.facade.ejb.anagrafica;

import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.domain.AnagraficaCorsiDottorato;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.NazioneCittadinanza;
import it.tasgroup.iris.domain.Province;
import it.tasgroup.iris.dto.anagrafica.ContoTecnicoDTO;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.ProvinceDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnagraficaCorsiDottoratoDTO;
import it.tasgroup.iris.dto.anonymous.payment.NazioneCittadinanzaDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class AnagraficaDTOBuilder {

	public static IntestatarioDTO fillIntestatarioIndirizzoDTO(Intestatari intestatario) {
		
		if(intestatario == null) 
			return null;
		
		IntestatarioDTO intestatarioDTO = new IntestatarioDTO();
		
		intestatarioDTO.setIdFiscale(intestatario.getLapl());
		
		intestatarioDTO.setRagioneSociale(intestatario.getRagionesociale());
		
        IndirizzoDTO indirizzoDTO = new IndirizzoDTO();
        
        IndirizzipostaliCommon indirizzo =  intestatario.getIndirizzipostaliobjIForm();
        
        indirizzoDTO.setVia(indirizzo.getAddressIForm());
        
        indirizzoDTO.setCitta(indirizzo.getCityIForm());
        
        indirizzoDTO.setNazione(indirizzo.getCountryIForm());
        
        indirizzoDTO.setProvincia(indirizzo.getProvinceIForm());
        
        indirizzoDTO.setCap(indirizzo.getCapCodeIForm());
        
        indirizzoDTO.setNumeroCivico(indirizzo.getNumeroCivicoIForm());
        
        intestatarioDTO.setIndirizzo(indirizzoDTO);
        
		return intestatarioDTO;
	}

	public static ContoTecnicoDTO fillContoTecnicoDTO(ContoTecnico contoTecnico) {
		
		if(contoTecnico == null)
			return null;
		
		ContoTecnicoDTO contoTecnicoDTO = new ContoTecnicoDTO();
		
		contoTecnicoDTO.setDescrizione(contoTecnico.getDescrizione());
		
		contoTecnicoDTO.setIban(contoTecnico.getIban());
		
		contoTecnicoDTO.setIntestazione(contoTecnico.getIntestazione());
		
		contoTecnicoDTO.setIntestatario(fillIntestatarioIndirizzoDTO(contoTecnico.getIntestatario()));
		
		return contoTecnicoDTO;
	}
	
	public static AnagraficaCorsiDottoratoDTO fillAnagraficaCorsiDottoratoDTO(AnagraficaCorsiDottorato anagraficaCorsiDottorato) {
		
		AnagraficaCorsiDottoratoDTO anagraficaCorsiDottoratoDTO = new AnagraficaCorsiDottoratoDTO();
		
		anagraficaCorsiDottoratoDTO.setCodCorso(anagraficaCorsiDottorato.getCodCorso());
		anagraficaCorsiDottoratoDTO.setCodTassa(anagraficaCorsiDottorato.getCodTassa());
		anagraficaCorsiDottoratoDTO.setDataScadenza(anagraficaCorsiDottorato.getDataScadenza());
		anagraficaCorsiDottoratoDTO.setDescrizione(anagraficaCorsiDottorato.getDescrizione());
		anagraficaCorsiDottoratoDTO.setImporto(anagraficaCorsiDottorato.getImporto());
		
		return anagraficaCorsiDottoratoDTO;
	}

	public static NazioneCittadinanzaDTO fillNazioniCittadinanzaDTO(NazioneCittadinanza nazioniCittadinanza) {
		
		NazioneCittadinanzaDTO nazioniCittadinanzaDTO = new NazioneCittadinanzaDTO();
		
		nazioniCittadinanzaDTO.setCittadinanza(nazioniCittadinanza.getCittadinanza());
		nazioniCittadinanzaDTO.setCodMinister(nazioniCittadinanza.getCodMinister());
		nazioniCittadinanzaDTO.setCodNazione(nazioniCittadinanza.getCodNazione());
		nazioniCittadinanzaDTO.setComunitario(nazioniCittadinanza.getComunitario());
		nazioniCittadinanzaDTO.setDescrizione(nazioniCittadinanza.getDescrizione());
		nazioniCittadinanzaDTO.setSigla(nazioniCittadinanza.getSigla());
		nazioniCittadinanzaDTO.setSviluppo(nazioniCittadinanza.getSviluppo());
		
		return nazioniCittadinanzaDTO;
	}
	public static ProvinceDTO fillProvinciaDTO(Province provincia) {
		
		ProvinceDTO provinciaDTO = new ProvinceDTO();
		
		provinciaDTO.setCodProvincia(provincia.getCodProvincia());
		
		provinciaDTO.setDescProvincia(provincia.getDescProvincia());
		
		provinciaDTO.setLabelProvincia(provincia.getDescProvincia() + " (" +provincia.getCodProvincia() + ")");
		
		return provinciaDTO;
	}

	public static List<ProvinceDTO> fillListaProvinceDTO(List<Province> province) {
		
		if(province == null) 
			return null;
		
		List<ProvinceDTO> provinceDTO = new ArrayList<ProvinceDTO>();
		
		for(Province provincia : province){
			
			ProvinceDTO provinciaDTO = fillProvinciaDTO(provincia);
			
			provinceDTO.add(provinciaDTO);
			
		}
		
		return provinceDTO;
	}
	
	
	public static List<AnagraficaCorsiDottoratoDTO> fillListaAnagraficaCorsiDottoratoDTO(List<AnagraficaCorsiDottorato> anagraficaCorsiDottorato) {
		
		if(anagraficaCorsiDottorato == null) 
			return null;
		
		List<AnagraficaCorsiDottoratoDTO> corsiDTO = new ArrayList<AnagraficaCorsiDottoratoDTO>();
		
		for(AnagraficaCorsiDottorato corso : anagraficaCorsiDottorato){
			
			AnagraficaCorsiDottoratoDTO anagraficaCorsiDottoratoDTO = fillAnagraficaCorsiDottoratoDTO(corso);
			
			corsiDTO.add(anagraficaCorsiDottoratoDTO);
			
		}
		
		return corsiDTO;
	}
	
	public static List<NazioneCittadinanzaDTO> fillListaNazioneCittadinanzaDTO(List<NazioneCittadinanza> nazioneCittadinanza) {
		
		if(nazioneCittadinanza == null) 
			return null;
		
		List<NazioneCittadinanzaDTO> nazioniCittaDTO = new ArrayList<NazioneCittadinanzaDTO>();
		
		for(NazioneCittadinanza nazioniCitta : nazioneCittadinanza){
			
			NazioneCittadinanzaDTO nazioniCittadinanzaDTO = fillNazioniCittadinanzaDTO(nazioniCitta);
			nazioniCittaDTO.add(nazioniCittadinanzaDTO);
			
		}
		
		return nazioniCittaDTO;
	}
	
	
}
