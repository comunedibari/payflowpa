package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;

public interface EnteDao extends JpaRepository<Ente, Long> {

	public Page<Ente> findByDeNomeEnteContainingIgnoreCase(String deNomeEnte, Pageable pageable);

	public Ente findByCodIpaEnte(String codIpaEnte);
	
	public Page<Ente> findAllBy(Pageable pageable);
	
	@Query(value = "SELECT * FROM mygov_ente WHERE cod_ipa_ente = ?1 and de_password IS NULL", nativeQuery = true)
	public List<Ente> findByCodIpaEnteAndNullPassword(String codIpaEnte);
	
	@Query(value = "SELECT * FROM mygov_ente WHERE cod_ipa_ente = ?1 and de_password = ?2", nativeQuery = true)
	public List<Ente> findByCodIpaEnteAndPassword(String codIpaEnte, String password);

	@Query(value = "SELECT de_logo_ente FROM mygov_ente WHERE cod_ipa_ente = ?1", nativeQuery = true)
	public String getLogoEnteByCodIpa(String codIpaEnte);

	public Ente findById(Long id);
}
