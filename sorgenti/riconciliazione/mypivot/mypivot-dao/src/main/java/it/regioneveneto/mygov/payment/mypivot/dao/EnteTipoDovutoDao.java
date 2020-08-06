package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;

public interface EnteTipoDovutoDao extends JpaRepository<EnteTipoDovuto, Long> {

	@Query("select etd from EnteTipoDovuto etd where etd.ente.codIpaEnte = ?1")
	public List<EnteTipoDovuto> findByCodIpaEnte(String codIpaEnte);
	
	@Query("select etd from EnteTipoDovuto etd where etd.ente.codIpaEnte = ?1 and etd.codTipo = ?2")
	public EnteTipoDovuto findByCodIpaEnteAndCodTipo(String codIpaEnte, String codTipo);
	
	@Query("select etd from EnteTipoDovuto etd where etd.ente.codIpaEnte = ?1 and etd.codTipo in ?2")
	public List<EnteTipoDovuto> findByCodIpaEnteAndCodTipoDovutoList(String codIpaEnte, Collection<String> listaTipiDovuto);
	
	@Query("select etd from EnteTipoDovuto etd where etd.ente.codIpaEnte = ?1")
	public Page<EnteTipoDovuto> findByCodIpaEnte(String codIpaEnte, Pageable pageable);

	public EnteTipoDovuto findById(Long id);
}