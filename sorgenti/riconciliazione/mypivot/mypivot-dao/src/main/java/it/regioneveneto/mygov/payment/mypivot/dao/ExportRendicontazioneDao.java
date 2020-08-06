package it.regioneveneto.mygov.payment.mypivot.dao;

import it.regioneveneto.mygov.payment.mypivot.domain.po.ExportRendicontazione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExportRendicontazioneDao extends JpaRepository<ExportRendicontazione, Long>, JpaSpecificationExecutor<ExportRendicontazione> {

}
