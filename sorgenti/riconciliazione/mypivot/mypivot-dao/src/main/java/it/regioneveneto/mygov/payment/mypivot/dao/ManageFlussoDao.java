package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Date;
import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ManageFlusso;
import it.regioneveneto.mygov.payment.mypivot.domain.po.TipoFlusso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManageFlussoDao extends JpaRepository<ManageFlusso, Long> {
	
	@Query("select e from ManageFlusso e where " + "e.ente.id = ?1 and " + "e.tipoFlusso.id = ?2 and " + "e.codProvenienzaFile = ?3 and "
			+ "e.anagraficaStato in ?4 and " + "e.deNomeFile like %?5% and " + "e.dtCreazione >= ?6 and " + "e.dtCreazione < ?7")
	Page<ManageFlusso> getManageFlussoPage(Long enteId, Long tipoFlussoId, String codProvenienzaFileWeb, List<AnagraficaStato> states, String fullTextSearch,
			Date dateFrom, Date dateTo, Pageable pageable);

	@Query("select e from ManageFlusso e where " + "e.codRequestToken = ?1 and e.anagraficaStato = ?2")
	ManageFlusso findByCodRequestTokenAndAnagraficaStato(String codRequestToken, AnagraficaStato anagraficaStato);
	
	ManageFlusso findByCodRequestToken(String codRequestToken);
}
