package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.PrenotazioneFlussoRiconciliazione;
import it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface PrenotazioneFlussoRiconciliazioneService {

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	PrenotazioneFlussoRiconciliazioneTO insertPrenotazioneFlussoRiconciliazione(String codIpaEnte, String codFedUserId, String codTipoStato, String deTipoStato,
			String codCodiceClassificazione, String deTipoDovuto, String codIdUnivocoVersamento, String codIdUnivocoRendicontazione,
			Date dtDataUltimoAggiornamentoDa, Date dtDataUltimoAggiornamentoA, Date dtDataEsecuzioneDa, Date dtDataEsecuzioneA, Date dtDataEsitoDa,
			Date dtDataEsitoA, Date dtDataRegolamentoDa, Date dtDataRegolamentoA, Date dtDataContabileDa, Date dtDataContabileA, Date dtDataValutaDa,
			Date dtDataValutaA, String codIdUnivocoDovuto, String codIdUnivocoRiscossione, String codIdUnivocoPagatore, String deAnagraficaPagatore,
			String codIdUnivocoVersante, String deAnagraficaVersante, String deDenominazioneAttestante, String deOrdinante, String codIdRegolamento,
			String codContoTesoreria, String deImportoTesoreria, String deCausale, String versioneTracciato);
	
	PrenotazioneFlussoRiconciliazioneTO getByCodRequestToken(String codRequestToken);
	PageDto<FlussoExportDto> getPrenotazioneByCodRequestToken(String codRequestToken);
	
	PrenotazioneFlussoRiconciliazioneTO getByCodIdFlusso(Long idFlusso);
	
	PageDto<FlussoExportDto> getFlussoPage(String codFedUserId, String codIpaEnte, String fullTextSearch, Date dateFrom, Date dateTo,
			int page, int pageSize, Direction direction, String orderBy);

	PageDto<FlussoExportDto> getFlussoPage(String codFedUserId, String codIpaEnte, String fullTextSearch, Date dateFrom, Date dateTo,
			Pageable pageable);

}
