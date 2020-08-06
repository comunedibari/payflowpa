package it.regioneveneto.mygov.payment.mypivot.dao;

import it.regioneveneto.mygov.payment.mypivot.domain.po.TipoFlusso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoFlussoDao extends JpaRepository<TipoFlusso, Long>{
	
	TipoFlusso findByCodTipo(Character codTipo);

}
