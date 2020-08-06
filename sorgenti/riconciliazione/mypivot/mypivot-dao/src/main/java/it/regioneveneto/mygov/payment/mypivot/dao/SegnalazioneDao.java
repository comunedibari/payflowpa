package it.regioneveneto.mygov.payment.mypivot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Segnalazione;

public interface SegnalazioneDao extends JpaRepository<Segnalazione, Long>, JpaSpecificationExecutor<Segnalazione>{
	
	public Segnalazione findById(Long id);
}

