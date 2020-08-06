package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExport;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExportId;

public interface FlussoExportDao
		extends JpaRepository<FlussoExport, FlussoExportId>, JpaSpecificationExecutor<FlussoExport> {

	@Query("select exp from FlussoExport exp where exp.ente.codIpaEnte = ?1 and exp.id.codRpSilinviarpIdUnivocoVersamento = ?2 and exp.id.indiceDatiSingoloPagamento = ?3")
	List<FlussoExport> findByCodIpaEnteIUVAndIndiceDatiSingoloPagamento(final String codIpaEnte, final String iuv,
			final int indiceDatiSingoloPagamento);
	
	@Query("select exp from FlussoExport exp where exp.ente.codIpaEnte = ?1 and exp.id.codRpSilinviarpIdUnivocoVersamento = ?2")
	List<FlussoExport> findByCodIpaEnteIUV(final String codIpaEnte, final String iuv);
	
	@Query("select exp from FlussoExport exp where exp.ente.codIpaEnte = ?1 and exp.codIud = ?2")
	FlussoExport findByCodIpaEnteIUD(final String codIpaEnte, final String iud);
	
	public List<FlussoExport> findAllById(Iterable<?> ids);
	
	FlussoExport findOneById(FlussoExportId flussoExportId);
	
//	FlussoExport findByCodEDatiPagDatiSingPagIdUnivocoRiscoss(final String identificativoUnivocoRiscossione);
//	@Query("select exp from FlussoExport exp where exp.codEDatiPagDatiSingPagIdUnivocoRiscoss = ?1")
//	FlussoExport findByCodEDatiPagDatiSingPagIdUnivocoRiscoss(final String identificativoUnivocoRiscossione);
}