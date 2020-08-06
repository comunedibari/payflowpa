package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import it.regioneveneto.mygov.payment.mypivot.dao.FlussoExportFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExport;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreria;

@Repository
public class FlussoExportFunctionDaoImpl implements FlussoExportFunctionDao {

	private static final Logger LOG = Logger.getLogger(ImportExportRendicontazioneTesoreriaFunctionDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public List<FlussoExport> get_flusso_export_to_send_gepos_function(Long id_ente){

		List<FlussoExport> result = new ArrayList<FlussoExport>();

		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_flusso_export_to_send_gepos_function("+ id_ente + ")");

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), FlussoExport.class);
		result = query.getResultList();

		return result;
	}

}