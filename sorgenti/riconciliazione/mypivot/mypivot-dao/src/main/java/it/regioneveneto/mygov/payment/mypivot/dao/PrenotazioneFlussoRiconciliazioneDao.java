package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;
import it.regioneveneto.mygov.payment.mypivot.domain.po.PrenotazioneFlussoRiconciliazione;

public interface PrenotazioneFlussoRiconciliazioneDao extends JpaRepository<PrenotazioneFlussoRiconciliazione, Long> {

	PrenotazioneFlussoRiconciliazione findByCodRequestToken(String codRequestToken);

	PrenotazioneFlussoRiconciliazione findById(Long idFlusso);
	
	@Query("select e from PrenotazioneFlussoRiconciliazione e where " + "e.ente.id = ?1 and " + "e.utente.id = ?2 and " + "e.anagraficaStato in ?3 and "
			+ "e.dtCreazione >= ?4 and " + "e.dtCreazione < ?5")
	Page<PrenotazioneFlussoRiconciliazione> getPrenotazioneFlussoRiconciliazionePage(Long enteId, Long utenteId, List<AnagraficaStato> states, Date dateFrom,
			Date dateTo, Pageable pageable);

	@Query("select e from PrenotazioneFlussoRiconciliazione e where " + "e.ente.id = ?1 and " + "e.utente.id = ?2 and " + "e.anagraficaStato in ?3 and "
			+ "e.deNomeFileGenerato like %?4% and " + "e.dtCreazione >= ?5 and " + "e.dtCreazione < ?6")
	Page<PrenotazioneFlussoRiconciliazione> getPrenotazioneFlussoRiconciliazionePage(Long enteId, Long utenteId, List<AnagraficaStato> states,
			String fullTextSearch, Date dateFrom, Date dateTo, Pageable pageable);
}
