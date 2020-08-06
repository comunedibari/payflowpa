package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.PagamentoRidDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;
import it.tasgroup.services.util.enumeration.EnumFlagStornoAEA;
import it.tasgroup.services.util.enumeration.EnumTipoIncassoRid;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FabriziE
 * 
 */
public class DRIDDTOBuilder {


	public static PagamentoRidDTO populatePRidDTO(Rid prid){
		PagamentoRidDTO dto = new PagamentoRidDTO();

		dto.setIdPagamentoRid(prid.getId());
		
		return dto;
	}
	
	public static DistintePagamentoDTO populateDPDTO(GestioneFlussi dp){
		DistintePagamentoDTO dto = new DistintePagamentoDTO();

		dto.setCodTransazione(dp.getCodTransazione());
		dto.setCodPagamento(dp.getCodPagamento());
		dto.setId(dp.getId().toString());
//		dto.setCodDistinta(dp.getCodDistinta());
//		dto.setDataCreazione(dp.getDataCreazione());
		dto.setDataCreazione(dp.getTmbcreazione());
		dto.setStato(dp.getStato());
		dto.setImporto(dp.getTotimportipositivi());
		dto.setImportoCommissione(dp.getImportoCommissioni());
		
		return dto;
	}
	
	public static AllineamentiElettroniciArchiviDTO populateAEADTO(
			AllineamentiElettroniciArchivi aea) {
		if (aea == null) 
			return null;
		AllineamentiElettroniciArchiviDTO dto = new AllineamentiElettroniciArchiviDTO();
		dto.setId(aea.getId());
		dto.setStato(aea.getStato());
		dto.setDescrizioneStato(aea.getDescrizioneStato());
		dto.setDataRichiesta(aea.getDataCreazione());
		dto.setDataAttivazione(aea.getDataAttivazione());
		dto.setDataCessazione(aea.getDataCessazione());
		
		
		if (aea.getTipoIncassoRid() != null)
			dto.setTipoIncassoRid(EnumTipoIncassoRid.getByKey(aea.getTipoIncassoRid()));
		dto.setFlagStorno(EnumFlagStornoAEA.getByKey(aea.getFlagStorno()));
		dto.setFlagIniziativa(aea.getFlagIniziativa());
		
//		dto.setCittadino(aea.getFlagIniziativa().equals("C")?true:false);
		
		dto.setRagSocSottoscrittore(aea.getRagSocialeSottoscrittore());
		dto.setCodificaFiscaleSottoscrittore(aea.getIdFiscaleSottoscrittore());
		dto.setIndirizzoSottoscrittore(aea.getIndirizzoSottoscrittore());
		dto.setIbanAddebito(aea.getIbanAddebito());
		dto.setRagSocIntAddebito(aea.getRagSocialeIntAddebito());

		//dati del creditore
		dto.setAbiBancaAllineamento(aea.getAbiBancaAllineamento());
		dto.setRagSocCreditore(aea.getRagSocialeCreditore());
		dto.setCodificaFiscaleCreditore(aea.getIdFiscaleCreditore());
		dto.setSiaCreditore(aea.getSiaCreditore());

		dto.setDataRichiestaRevoca(aea.getRevoca()!= null ? aea.getRevoca().getDataCreazione():null);

		dto.setStatoRevoca(aea.getRevoca()!= null ? aea.getRevoca().getStato():null);
		
		return dto;
	}

public static List<AllineamentiElettroniciArchiviDTO> populateAEADTOList(List<AllineamentiElettroniciArchivi> aeaList) {
		
		List<AllineamentiElettroniciArchiviDTO> returnDTOList = new ArrayList<AllineamentiElettroniciArchiviDTO>();

		for (AllineamentiElettroniciArchivi aea : aeaList) {
			AllineamentiElettroniciArchiviDTO aeaDTO = populateAEADTO(aea);
			returnDTOList.add(aeaDTO);
		}

		return returnDTOList;
	}

}
