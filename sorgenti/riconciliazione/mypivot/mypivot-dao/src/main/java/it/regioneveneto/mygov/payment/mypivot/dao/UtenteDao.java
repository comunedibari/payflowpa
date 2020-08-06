package it.regioneveneto.mygov.payment.mypivot.dao;

import it.regioneveneto.mygov.payment.mypivot.domain.po.Segnalazione;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UtenteDao extends JpaRepository<Utente, Long>{
	
	public Utente findByCodFedUserIdIgnoreCase(String codFedUserId);
	
	@Query("select distinct(u) from Utente u, OperatoreEnteTipoDovuto oetd where oetd.enteTipoDovuto.ente.codIpaEnte = ?1 and u.codFedUserId = oetd.codFedUserId")
	List<Utente> findByCodIpaEnte(String codIpaEnte);

	public Utente findById(Long id);
}
