package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoRendicontazione;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoRendicontazioneId;

public interface FlussoRendicontazioneDao extends JpaRepository<FlussoRendicontazione, FlussoRendicontazioneId>,
		JpaSpecificationExecutor<FlussoRendicontazione> {

	@Query("select rend from FlussoRendicontazione rend where rend.ente.codIpaEnte = ?1 and upper(rend.codIdentificativoFlusso) = upper(?2)")
	List<FlussoRendicontazione> findByCodIpaEnteAndIUF(final String codIpaEnte, final String iuf);
}