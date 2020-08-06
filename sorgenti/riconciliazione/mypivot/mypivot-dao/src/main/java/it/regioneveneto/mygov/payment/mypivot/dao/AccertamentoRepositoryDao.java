package it.regioneveneto.mygov.payment.mypivot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import it.regioneveneto.mygov.payment.mypivot.domain.po.Accertamento;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface AccertamentoRepositoryDao extends JpaRepository<Accertamento, Long>, JpaSpecificationExecutor<Accertamento> {
	Accertamento findById( Long accertamentoId);
}
