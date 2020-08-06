package it.regioneveneto.mygov.payment.mypivot.dao;

import it.regioneveneto.mygov.payment.mypivot.domain.po.RendicontazioneSubset;
import it.regioneveneto.mygov.payment.mypivot.domain.po.RendicontazioneSubsetId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RendicontazioneSubsetDao extends JpaRepository<RendicontazioneSubset, RendicontazioneSubsetId>,
		JpaSpecificationExecutor<RendicontazioneSubset> {
}
