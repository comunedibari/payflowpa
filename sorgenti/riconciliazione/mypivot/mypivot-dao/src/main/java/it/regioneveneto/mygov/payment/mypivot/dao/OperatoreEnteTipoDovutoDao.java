package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.regioneveneto.mygov.payment.mypivot.domain.po.OperatoreEnteTipoDovuto;

public interface OperatoreEnteTipoDovutoDao extends JpaRepository<OperatoreEnteTipoDovuto, Long> {

	List<OperatoreEnteTipoDovuto> findByCodFedUserId(final String codFedUserId);

}
