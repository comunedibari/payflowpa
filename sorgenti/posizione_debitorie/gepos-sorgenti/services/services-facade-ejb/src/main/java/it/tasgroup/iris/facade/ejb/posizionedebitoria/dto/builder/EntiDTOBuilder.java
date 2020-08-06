/**
 *
 */
package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IbanEnti;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.sistemienti.SistemaEnte;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.iris.domain.CfgEntiTema;
import it.tasgroup.iris.domain.CfgNotificaPagamento;
import it.tasgroup.iris.dto.anagrafica.CategoriaTributoDTO;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IbanEnteDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgEntiLogoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgEntiTemaDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class EntiDTOBuilder {

	public static List<EnteDTO> populateListEntiTributiDTO(List<Enti> listaEnti, boolean isBackOffice) {

		List<EnteDTO> returnDTOList = new ArrayList<EnteDTO>();

		for (Enti ente : listaEnti) {

			EnteDTO dto = populateEntiTributiDTO(ente, isBackOffice);

			if(dto != null)
				returnDTOList.add(dto);

		}

		return returnDTOList;
	}

	private static EnteDTO populateEntiTributiDTO(Enti ente, boolean isBackOffice) {

		EnteDTO enteDto = new EnteDTO();

		enteDto.setCodice(ente.getIdEnte());
		enteDto.setId(ente.getIdEnte());

		enteDto.setCodiceCart(ente.getCodiceEnte());
		enteDto.setDescrizione(ente.getDenominazione());
		enteDto.setDenominazione(getDenominazioneEnte(ente));
		enteDto.setGln(ente.getGln());
		
		enteDto.setFlBancaTesoriera(ente.getFlBancaTesoriera());
		enteDto.setFlBlf(ente.getFlBlf());
		enteDto.setFlNdp(ente.getFlNdp());
		enteDto.setFlNdpModello3(ente.getFlNdpModello3());
		
		enteDto.setAuxDigit(ente.getAuxDigit());
		enteDto.setCodiceStazionePA(ente.getCodiceStazionePA());
		enteDto.setNdpCodSegr(ente.getNdpCodSegr());
		
		enteDto.setInfoHomeBO(ente.getInfoHomeBO());
		enteDto.setAutorizzStampaDDP(ente.getAutorizzStampaDDP());

		for (TributoEnte tributo : ente.getTributiEnte()) {
			if (isBackOffice || !"Y".equals(tributo.getFlNascostoFe())) {

				TributoEnteDTO tribDto = new TributoEnteDTO();
				tribDto.setCdTrbEnte(tributo.getCdTrbEnte());
				tribDto.setDeTrb(tributo.getDeTrb());
				tribDto.setInfoTributo(tributo.getInfoTributo());
				tribDto.setStato(tributo.getStato());
				tribDto.setSIL(tributo.getSIL());
				tribDto.setIBAN(tributo.getIBAN());
				tribDto.setIBANContoTecnico(tributo.getIBANContoTecnico());
				tribDto.setUoaCompetente(tributo.getUoaCompetente());
				tribDto.setIntestazioneCCP(tributo.getIntestazioneCCP());
				tribDto.setAutorizzStampaBP(tributo.getAutorizzStampaBP());
				enteDto.getTributi().add(tribDto);
			}
		}

		if (enteDto.getTributi() == null || enteDto.getTributi().isEmpty())
			return null;

		return enteDto;

	}

	private static EnteDTO populateEntiDTO(Enti ente, boolean isBackOffice) {

		EnteDTO enteDto = new EnteDTO();

		enteDto.setCodice(ente.getIdEnte());
		enteDto.setId(ente.getIdEnte());

		enteDto.setCodiceCart(ente.getCodiceEnte());
		enteDto.setDescrizione(ente.getDenominazione());
		enteDto.setDenominazione(getDenominazioneEnte(ente));
		enteDto.setGln(ente.getGln());
		
		enteDto.setFlBancaTesoriera(ente.getFlBancaTesoriera());
		enteDto.setFlBlf(ente.getFlBlf());
		enteDto.setFlNdp(ente.getFlNdp());
		enteDto.setFlNdpModello3(ente.getFlNdpModello3());
		
		enteDto.setAuxDigit(ente.getAuxDigit());
		enteDto.setCodiceStazionePA(ente.getCodiceStazionePA());
		enteDto.setNdpCodSegr(ente.getNdpCodSegr());
		
		enteDto.setInfoHomeBO(ente.getInfoHomeBO());
		enteDto.setAutorizzStampaDDP(ente.getAutorizzStampaDDP());

		for (TributoEnte tributo : ente.getTributiEnte()) {
			if (isBackOffice || !"Y".equals(tributo.getFlNascostoFe())) {

				TributoEnteDTO tribDto = new TributoEnteDTO();
				tribDto.setCdTrbEnte(tributo.getCdTrbEnte());
				tribDto.setDeTrb(tributo.getDeTrb());
				tribDto.setInfoTributo(tributo.getInfoTributo());
				tribDto.setStato(tributo.getStato());
				tribDto.setSIL(tributo.getSIL());
				tribDto.setIBAN(tributo.getIBAN());
				tribDto.setIBANContoTecnico(tributo.getIBANContoTecnico());
				tribDto.setUoaCompetente(tributo.getUoaCompetente());
				tribDto.setIntestazioneCCP(tributo.getIntestazioneCCP());
				tribDto.setAutorizzStampaBP(tributo.getAutorizzStampaBP());
				enteDto.getTributi().add(tribDto);
			}
		}

		
		return enteDto;

	}
	private static String getDenominazioneEnte(Enti ente) {

		String denomEnte = "";

		Intestatari intest = (Intestatari) ente.getIntestatarioobj();
		denomEnte = intest.getRagionesociale();
		if ((denomEnte == null) || (denomEnte.equals(""))) {
			denomEnte = ente.getDenominazione();
			if ((denomEnte == null || denomEnte.equals(""))) {
				denomEnte = ente.getIdEnte();
			}
		}
		return denomEnte;
	}


	public static List<TributoEnteDTO> fillListEntiTributiDTO(List<TributoEnte> listaTributiEnti, boolean isBackOffice) {

		List<TributoEnteDTO> returnDTOList = new ArrayList<TributoEnteDTO>();

		for (TributoEnte tributoEnte : listaTributiEnti) {
			//if (isBackOffice || !"Y".equals(tributoEnte.getFlNascostoFe())*) {
				TributoEnteDTO dto = fillTributoEnteDTO(tributoEnte);
				returnDTOList.add(dto);
			//}
		}
		return returnDTOList;
	}

	public static List<EnteDTO> fillListEntiDTO(List<Enti> enti) {

		List<EnteDTO> returnDTOList = new ArrayList<EnteDTO>();

		for (Enti ente : enti) {

				EnteDTO dto = fillEnteDTO(ente);
				returnDTOList.add(dto);
		}

		return returnDTOList;
	}


	public static EnteDTO fillEnteDTO(Enti e) {
		return populateEntiDTO(e,true);
	}



	private static TributoEnteDTO fillTributoEnteDTO(TributoEnte tributoEnte){

		TributoEnteDTO tributoDTO = new TributoEnteDTO();

		Enti ente = tributoEnte.getEntiobj();
		tributoDTO.setCdTrbEnte(tributoEnte.getCdTrbEnte());
		tributoDTO.setDeTrb(tributoEnte.getDeTrb());
		tributoDTO.getEnte().setDenominazione(ente.getDenominazione());
		tributoDTO.setIdEnte(ente.getIdEnte());

		if (ente.getIntestatarioobj()!= null)
			tributoDTO.getEnte().getIntestatario().setIdFiscale(ente.getIntestatarioobj().getLaplIForm());
		tributoDTO.setIBAN(tributoEnte.getIBAN());
		tributoDTO.setIBANContoTecnico(tributoEnte.getIBANContoTecnico());
		tributoDTO.setNumPendenze(tributoEnte.getNumPendenze());
		if (tributoEnte.getCategoriaobj() != null)
			tributoDTO.setDesCategoriaTributo(tributoEnte.getCategoriaobj().getDeTrb());

		tributoDTO.setNdpAuxDigit(tributoEnte.getNdpAuxDigit());
		tributoDTO.setNdpCodStazPa(tributoEnte.getNdpCodStazPa());
		tributoDTO.setFlNdpIuvGenerato(tributoEnte.getFlNdpIuvGenerato());
		tributoDTO.setNdpIuvStartNum(tributoEnte.getNdpIuvStartNum());
		tributoDTO.setUoaCompetente(tributoEnte.getUoaCompetente());
		tributoDTO.setIntestazioneCCP(tributoEnte.getIntestazioneCCP());
		tributoDTO.setAutorizzStampaBP(tributoEnte.getAutorizzStampaBP());

		return tributoDTO;
	}

	public static List<TributoEnteDTO> fillListEntiTributiDTOFull(List<TributoEnte> listaTributiEnti) {

		List<TributoEnteDTO> returnDTOList = new ArrayList<TributoEnteDTO>();

		for (TributoEnte tributoEnte : listaTributiEnti) {

				TributoEnteDTO dto = fillTributoEnteDTOLight(tributoEnte);
				returnDTOList.add(dto);

		}

		return returnDTOList;
	}

	public static TributoEnteDTO fillTributoEnteDTOLight(TributoEnte tributoEnte){

		TributoEnteDTO tributoDTO = new TributoEnteDTO();

		Enti ente = tributoEnte.getEntiobj();
		tributoDTO.setCdTrbEnte(tributoEnte.getCdTrbEnte());
		if (tributoEnte.getCfgTributoEntePlugin() != null) {
			tributoDTO.setCdPlugin(tributoEnte.getCfgTributoEntePlugin().getCdPlugin());
		}
		tributoDTO.setDeTrb(tributoEnte.getDeTrb());
		tributoDTO.getEnte().setDenominazione(ente.getDenominazione());
		tributoDTO.setIdEnte(ente.getIdEnte());
		if (ente.getIntestatarioobj()!= null)
			tributoDTO.getEnte().getIntestatario().setIdFiscale(ente.getIntestatarioobj().getLaplIForm());
		tributoDTO.setIBAN(tributoEnte.getIBAN());
		tributoDTO.setIBAN_CCP(tributoEnte.getIBAN_CCP());
		tributoDTO.setIBAN_MYBANK(tributoEnte.getIBAN_MYBANK());
		tributoDTO.setNumPendenze(tributoEnte.getNumPendenze());
		tributoDTO.setIBANContoTecnico(tributoEnte.getIBANContoTecnico());
		tributoDTO.setSIL(tributoEnte.getSistemaEnteobj().getIdSystem());
		tributoDTO.getSistemaEnte().setDeSystem(tributoEnte.getSistemaEnteobj().getDeSystem());
		tributoDTO.getSistemaEnte().setIdSystem(tributoEnte.getSistemaEnteobj().getIdSystem());
		tributoDTO.setStato(tributoEnte.getStato());
		tributoDTO.setIdTributo(tributoEnte.getIdTributo());
		tributoDTO.setDesCategoriaTributo(tributoEnte.getCategoriaobj().getDeTrb());
		tributoDTO.setFlIniziativa(tributoEnte.getFlIniziativa());
		tributoDTO.setFlPredeterm(tributoEnte.getFlPredeterm());

		tributoDTO.setNdpAuxDigit(tributoEnte.getNdpAuxDigit());
		tributoDTO.setNdpCodStazPa(tributoEnte.getNdpCodStazPa());
		tributoDTO.setFlNdpIuvGenerato(tributoEnte.getFlNdpIuvGenerato());
		tributoDTO.setNdpIuvStartNum(tributoEnte.getNdpIuvStartNum());
		
		tributoDTO.setFlBancaTesoriera(tributoEnte.getFlBancaTesoriera());
		tributoDTO.setFlBlf(tributoEnte.getFlBlf());
		tributoDTO.setFlNdp(tributoEnte.getFlNdp());
		tributoDTO.setFlNdpModello3(tributoEnte.getFlNdpModello3());
		tributoDTO.setUoaCompetente(tributoEnte.getUoaCompetente());
		tributoDTO.setInfoTributo(tributoEnte.getInfoTributo());
		tributoDTO.setIntestazioneCCP(tributoEnte.getIntestazioneCCP());
		tributoDTO.setAutorizzStampaBP(tributoEnte.getAutorizzStampaBP());


		return tributoDTO;
	}

	public static TributoEnteDTO fillTributoEnteDTOFull(TributoEnte tributoEnte){

		TributoEnteDTO tributoDTO = new TributoEnteDTO();

		tributoDTO.setCdTrbEnte(tributoEnte.getCdTrbEnte());
		if (tributoEnte.getCfgTributoEntePlugin() != null) {
			tributoDTO.setCdPlugin(tributoEnte.getCfgTributoEntePlugin().getCdPlugin());
			tributoDTO.setDati(tributoEnte.getCfgTributoEntePlugin().getDati());
		}

		tributoDTO.setDeTrb(tributoEnte.getDeTrb());

		if (tributoEnte.getCategoriaobj() != null)
			tributoDTO.getCategoriaobj().setDeTrb(tributoEnte.getCategoriaobj().getDeTrb());

		if (tributoEnte.getSistemaEnteobj() != null) {

			tributoDTO.setSIL(tributoEnte.getSistemaEnteobj().getIdSystem());
			tributoDTO.getSistemaEnte().setDeSystem(tributoEnte.getSistemaEnteobj().getDeSystem());
			tributoDTO.getSistemaEnte().setIdSystem(tributoEnte.getSistemaEnteobj().getIdSystem());
		}

		if (tributoEnte.getEntiobj() != null){
			tributoDTO.getEnte().setDenominazione(tributoEnte.getEntiobj().getDenominazione());
			tributoDTO.setIdEnte(tributoEnte.getEntiobj().getIdEnte());
			tributoDTO.getEnte().getIntestatario().setIdFiscale(tributoEnte.getEntiobj().getIntestatarioobj().getLaplIForm());
			tributoDTO.getEnte().setCodice(tributoEnte.getEntiobj().getCodiceEnte());
			tributoDTO.getEnte().setId(tributoEnte.getEntiobj().getIdEnte());
			tributoDTO.getEnte().setFlNdpModello3(tributoEnte.getEntiobj().getFlNdpModello3());
			tributoDTO.getEnte().setNdpCodSegr(tributoEnte.getEntiobj().getNdpCodSegr());
			tributoDTO.getEnte().setAuxDigit(tributoEnte.getEntiobj().getAuxDigit());
			tributoDTO.getEnte().setCodiceStazionePA(tributoEnte.getEntiobj().getCodiceStazionePA());
		}

		tributoDTO.setIBAN(tributoEnte.getIBAN());
		tributoDTO.setIBANContoTecnico(tributoEnte.getIBANContoTecnico());
		tributoDTO.setIBAN_CCP(tributoEnte.getIBAN_CCP());
		tributoDTO.setIBAN_MYBANK(tributoEnte.getIBAN_MYBANK());
		tributoDTO.setStato(tributoEnte.getStato());
		tributoDTO.setIdTributo(tributoEnte.getIdTributo());
		if (tributoEnte.getCategoriaobj() != null)
			tributoDTO.setDesCategoriaTributo(tributoEnte.getCategoriaobj().getDeTrb());
		tributoDTO.setFlIniziativa(tributoEnte.getFlIniziativa());
		tributoDTO.setTipoGestioneRichiestaRevoca(tributoEnte.getTipoGestioneRichiestaRevoca());
		
		tributoDTO.setFlNotificaPagamento(tributoEnte.getFlNotificaPagamento());
		tributoDTO.setFlVerificaPagamento(tributoEnte.getFlVerificaPagamento());
		tributoDTO.setFlNascostoFe(tributoEnte.getFlNascostoFe());
		tributoDTO.setFlPredeterm(tributoEnte.getFlPredeterm());
		tributoDTO.setInfoTributo(tributoEnte.getInfoTributo());
		tributoDTO.setIstruzioniPagamento(tributoEnte.getIstruzioniPagamento());
		tributoDTO.setUrlInfoService(tributoEnte.getUrlInfoService());
		tributoDTO.setUrlUpdService(tributoEnte.getUrlUpdService());

		CfgNotificaPagamento cfgEseguito = tributoEnte.getCfgNotificaEseguito();

		if(cfgEseguito != null){
			tributoDTO.getCfgNotificaEseguito().setFormatoNotifica(cfgEseguito.getFormatoNotifica());
			tributoDTO.getCfgNotificaEseguito().setFreqNotifica(cfgEseguito.getFreqNotifica());
			tributoDTO.getCfgNotificaEseguito().setConsegnaNotifica(cfgEseguito.getConsegnaNotifica());

		}

		CfgNotificaPagamento cfgIncassato = tributoEnte.getCfgNotificaIncassato();

		if(cfgIncassato != null){

			tributoDTO.getCfgNotificaIncassato().setFormatoNotifica(cfgIncassato.getFormatoNotifica());
			tributoDTO.getCfgNotificaIncassato().setFreqNotifica(cfgIncassato.getFreqNotifica());
			tributoDTO.getCfgNotificaIncassato().setConsegnaNotifica(cfgIncassato.getConsegnaNotifica());

		}

		CfgNotificaPagamento cfgRegolato = tributoEnte.getCfgNotificaRegolato();

		if(cfgRegolato != null){

			tributoDTO.getCfgNotificaRegolato().setFormatoNotifica(cfgRegolato.getFormatoNotifica());
			tributoDTO.getCfgNotificaRegolato().setFreqNotifica(cfgRegolato.getFreqNotifica());
			tributoDTO.getCfgNotificaRegolato().setConsegnaNotifica(cfgRegolato.getConsegnaNotifica());

		}
		tributoDTO.setNdpAuxDigit(tributoEnte.getNdpAuxDigit());
		tributoDTO.setNdpCodStazPa(tributoEnte.getNdpCodStazPa());
		tributoDTO.setFlNdpIuvGenerato(tributoEnte.getFlNdpIuvGenerato());
		tributoDTO.setNdpIuvStartNum(tributoEnte.getNdpIuvStartNum());

		
		tributoDTO.setFlBancaTesoriera(tributoEnte.getFlBancaTesoriera());
		tributoDTO.setFlBlf(tributoEnte.getFlBlf());
		tributoDTO.setFlNdp(tributoEnte.getFlNdp());
		tributoDTO.setFlNdpModello3(tributoEnte.getFlNdpModello3());
		tributoDTO.setNdpCodSegr(tributoEnte.getNdpCodSegr());
		
		tributoDTO.setUoaCompetente(tributoEnte.getUoaCompetente());
		tributoDTO.setInfoTributo(tributoEnte.getInfoTributo());
		tributoDTO.setIntestazioneCCP(tributoEnte.getIntestazioneCCP());
		tributoDTO.setAutorizzStampaBP(tributoEnte.getAutorizzStampaBP());

		tributoDTO.setCfgTsrCodiceEnte(tributoEnte.getCfgTsrCodiceEnte());
		tributoDTO.setCfgTsrContoEnte(tributoEnte.getCfgTsrContoEnte());
		
		return tributoDTO;
	}

	public static List<CategoriaTributoDTO> fillListCategorieTributiDTO(List<CategoriaTributo> listaCategorie) {

		List<CategoriaTributoDTO> returnDTOList = new ArrayList<CategoriaTributoDTO>();

		for (CategoriaTributo categoria : listaCategorie) {

			CategoriaTributoDTO dto = fillCategoriaTributoDTO(categoria);

			returnDTOList.add(dto);

		}

		return returnDTOList;
	}


	public static CategoriaTributoDTO fillCategoriaTributoDTO(CategoriaTributo categoria) {

		CategoriaTributoDTO dto = new CategoriaTributoDTO();

		dto.setIdTributo(categoria.getIdTributo());

		dto.setDeTrb(categoria.getDeTrb());

		dto.setCdAde(categoria.getCdAde());

		dto.setFlPredeterm(categoria.getFlPredeterm());

		dto.setStato(categoria.getStato());

		dto.setTpEntrata(categoria.getTpEntrata());
		
		dto.setTassonomia(categoria.getTassonomia()); 

		if (categoria.getEntiTributi() != null)
			dto.setEntiTributiSize(""+categoria.getEntiTributi().size());

		return dto;
	}

	public static List<SistemaEnteDTO> fillListSistemaEntiDTO(List<SistemaEnte> listaSistemaEnti) {

		List<SistemaEnteDTO> returnDTOList = new ArrayList<SistemaEnteDTO>();

		for (SistemaEnte silEnte : listaSistemaEnti) {

				SistemaEnteDTO dto = fillSistemaEnteDTO(silEnte);

				returnDTOList.add(dto);

		}

		return returnDTOList;
	}

	public static SistemaEnteDTO fillSistemaEnteDTO(SistemaEnte sil) {

		SistemaEnteDTO dto = new SistemaEnteDTO();

		dto.setDeSystem(sil.getDeSystem());

		dto.setDescEnte(sil.getDescEnte());

		dto.setIdEnte(sil.getIdEnte());

		dto.setIdSystem(sil.getIdSystem());

		dto.setTrtId(sil.getTrtId());

		dto.setTrtSystem(sil.getTrtSystem());

		dto.setCdEnte(sil.getCdEnte());

		dto.setIsSSilEnabled(sil.getsSilEnabledAsString());
		
		dto.setUserId(sil.getUserId());
		
		dto.setAuthId(sil.getAuthId());
		
		return dto;
	}

	public static CategoriaTributo fillCategoriaTributoEntity(CategoriaTributoDTO catDTO) {

		CategoriaTributo cat = new CategoriaTributo();

		cat.setCdAde(catDTO.getCdAde());

		cat.setCdPagamentoSpontaneo(catDTO.getCdPagamentoSpontaneo());

		cat.setDeTrb(catDTO.getDeTrb());

		cat.setFlIniziativa(catDTO.getFlIniziativa());

		cat.setFlPredeterm(catDTO.getFlPredeterm());

		cat.setIdTributo(catDTO.getIdTributo());

		cat.setStato(catDTO.getStato());

		cat.setTassonomia(catDTO.getTassonomia()); 
		
		cat.setTpEntrata(catDTO.getTpEntrata());

		cat.setOpInserimento(catDTO.getOpInserimento());

		return cat;
	}

	public static CfgEntiLogoDTO fillCfgEntiLogoDTO(CfgEntiLogo logo) {

		CfgEntiLogoDTO dto = new CfgEntiLogoDTO();

		dto.setFileContent(logo.getLogoBlob());

		dto.setNomeFile(logo.getNomeFileLogo());

		dto.setIdEnte(logo.getIdEnte());

		return dto;
	}

	public static CfgEntiTemaDTO fillCfgEntiTemaDTO(CfgEntiTema tema) {
		CfgEntiTemaDTO dto = new CfgEntiTemaDTO();
		dto.setIdEnte(tema.getCdEnte());
		dto.setInformazioni(tema.getInformazioni());
		dto.setNomeImgHeader(tema.getNomeImgHeader());
		dto.setNomeImgLogo(tema.getNomeImgLogo());
		dto.setHeaderBlob(tema.getHeaderBlob());
		dto.setLogoBlob(tema.getLogoBlob());
		dto.setHeaderBlob(tema.getHeaderBlob());
		dto.setIdTema(tema.getIdTema());
		dto.setTsAggiornamento(tema.getTsAggiornamento());
		dto.setTsInserimento(tema.getTsInserimento());
		dto.setOpAggiornamento(tema.getOpAggiornamento());
		dto.setOpInserimento(tema.getOpInserimento());
		return dto;
	}

	public static CfgEntiTema fillCfgEntiTemaEntity(CfgEntiTemaDTO entiTemaDTO) {
		CfgEntiTema cfgEntiTema = new CfgEntiTema();
		cfgEntiTema.setCdEnte(entiTemaDTO.getIdEnte());
		cfgEntiTema.setIdTema(entiTemaDTO.getIdTema());
		cfgEntiTema.setLogoBlob(entiTemaDTO.getLogoBlob());
		cfgEntiTema.setNomeImgLogo(entiTemaDTO.getNomeImgLogo());
		cfgEntiTema.setNomeImgHeader(entiTemaDTO.getNomeImgHeader());
		cfgEntiTema.setHeaderBlob(entiTemaDTO.getHeaderBlob());
		cfgEntiTema.setInformazioni(entiTemaDTO.getInformazioni());
		cfgEntiTema.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		cfgEntiTema.setOpInserimento(entiTemaDTO.getOpInserimento());
		cfgEntiTema.setOpAggiornamento(entiTemaDTO.getOpAggiornamento());
		return cfgEntiTema;
	}

	public static CfgEntiLogo fillCfgEntiLogoEntity(CfgEntiLogoDTO entiLogoDTO) {

		CfgEntiLogo logo = new CfgEntiLogo();

		logo.setIdEnte(entiLogoDTO.getIdEnte());

		logo.setLogoBlob(entiLogoDTO.getFileContent());

		logo.setNomeFileLogo(entiLogoDTO.getNomeFile());

		logo.setTsInserimento(new Timestamp(System.currentTimeMillis()));

		logo.setOpInserimento(entiLogoDTO.getOpInserimento());

		return logo;
	}
	
	
	
	public static IbanEnteDTO fillIbanEnteDTO(IbanEnti ibanEnte) {

		IbanEnteDTO dto = new IbanEnteDTO();

		dto.setDataAttivazione(ibanEnte.getDataAttivazione());
		dto.setDataCensimento(ibanEnte.getDataCensimento());
		dto.setIban(ibanEnte.getIban());
		dto.setId(ibanEnte.getId());
		dto.setIdEnte(ibanEnte.getIdEnte());
		dto.setOpAggiornamento(ibanEnte.getOpAggiornamento());
		dto.setOpInserimento(ibanEnte.getOpInserimento());
		dto.setStRiga(ibanEnte.getStRiga());
		dto.setTsAggiornamento(ibanEnte.getTsAggiornamento());
		dto.setTsInserimento(ibanEnte.getTsInserimento());
		return dto;
	}

}
