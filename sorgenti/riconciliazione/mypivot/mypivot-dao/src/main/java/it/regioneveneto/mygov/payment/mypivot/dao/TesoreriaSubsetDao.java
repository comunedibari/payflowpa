package it.regioneveneto.mygov.payment.mypivot.dao;

import it.regioneveneto.mygov.payment.mypivot.domain.po.TesoreriaSubset;
import it.regioneveneto.mygov.payment.mypivot.domain.po.TesoreriaSubsetId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TesoreriaSubsetDao extends JpaRepository<TesoreriaSubset, TesoreriaSubsetId>,
		JpaSpecificationExecutor<TesoreriaSubset> {
}
