package it.regioneveneto.mygov.payment.mypivot.dao;

import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreria;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreriaId;
import it.regioneveneto.mygov.payment.mypivot.domain.po.PrenotazioneFlussoRiconciliazione;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface ImportExportRendicontazioneTesoreriaDao extends JpaRepository<ImportExportRendicontazioneTesoreria, ImportExportRendicontazioneTesoreriaId>, JpaSpecificationExecutor<ImportExportRendicontazioneTesoreria>, ImportExportRendicontazioneTesoreriaCustomDao {
	
	
	@Query("select e from PrenotazioneFlussoRiconciliazione e where " + "e.ente.id = ?1 and " + "e.utente.id = ?2 and " + "e.anagraficaStato in ?3 and "
			+ "e.dtCreazione >= ?4 and " + "e.dtCreazione < ?5")
	Page<PrenotazioneFlussoRiconciliazione> findFiltered(Long enteId, Long utenteId, List<AnagraficaStato> states, Date dateFrom,
			Date dateTo, Pageable pageable);

}
