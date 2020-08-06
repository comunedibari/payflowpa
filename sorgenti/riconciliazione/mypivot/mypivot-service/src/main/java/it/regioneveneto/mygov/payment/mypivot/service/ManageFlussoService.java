/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.service;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.UploadEsitoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ManageFlusso;

import java.text.ParseException;
import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Giorgio Vallini
 * @author Cristiano Perin
 *
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ManageFlussoService {

	PageDto<FlussoDto> getFlussoPage(Character codTipoFlusso, String codFedUserId, String codIpaEnte, String fullTextSearch, Date dateFrom, Date dateTo,
			int page, int pageSize, Direction desc, String string);

	PageDto<FlussoDto> getFlussoPage(Character codTipoFlusso, String codFedUserId, String codIpaEnte, String fullTextSearch, Date dateFrom, Date dateTo,
			Pageable pageable);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	ManageFlusso insertFlusso(Character codTipoFlusso, String codIpaEnte, String codFedUserId, String requestToken, String deTipoStato, String codTipoStato);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	ManageFlusso cambiaStatoFlusso(String codRequestToken, String deTipoStato, String precCodTipoStato, String codTipoStato);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void completaFlusso(String codRequestToken, String uploadPath, String nomeFile, Long dimensioneFile);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void completaFlussoPoste(UploadEsitoDto uploadEsitoDto, String uploadPath) throws ParseException;

	ManageFlusso getByRequestToken(String requestToken);
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void completaFlussoGiornaleDiCassaCsv(UploadEsitoDto uploadEsitoDto, String uploadPath) throws Exception;

}
