package it.regioneveneto.mygov.payment.mypivot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoTesoreria;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface FlussoTesoreriaDao extends JpaRepository<FlussoTesoreria, Long>, JpaSpecificationExecutor<FlussoTesoreria> {

	@Query("select tes from FlussoTesoreria tes where tes.ente.codIpaEnte = ?1 and tes.deAnnoBolletta = ?2 and tes.codBolletta = ?3")
	FlussoTesoreria findByCodIpaEnteDeAnnoBollettaAndCodBolletta(final String codIpaEnte, final String deAnnoBolletta, final String codBolletta);
}
