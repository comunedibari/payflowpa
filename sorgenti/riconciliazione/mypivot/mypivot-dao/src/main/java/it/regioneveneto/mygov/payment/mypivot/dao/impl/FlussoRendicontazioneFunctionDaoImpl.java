package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import it.regioneveneto.mygov.payment.mypivot.dao.FlussoRendicontazioneFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDistinctDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

@Repository
public class FlussoRendicontazioneFunctionDaoImpl implements FlussoRendicontazioneFunctionDao {

	private static final Logger LOG = Logger.getLogger(FlussoRendicontazioneFunctionDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	public PageDto<FlussoRendicontazioneDistinctDto> callFlussoRendicontazioneFunction(final Long mygovEnteId,
			final Date dt_data_regolamento_da, final Date dt_data_regolamento_a, final String iuf,
			final String identificativoUnivocoRegolamento, final int page, final int pageSize) {
		
		BigInteger totalRecords = callCountFlussoRendicontazioneFunction(mygovEnteId, dt_data_regolamento_da, dt_data_regolamento_a, iuf, identificativoUnivocoRegolamento);
		
		List<Object[]> listRecords = callPrivateFlussoRendicontazioneFunction(mygovEnteId, dt_data_regolamento_da, dt_data_regolamento_a, iuf, identificativoUnivocoRegolamento, page, pageSize);
		
		PageDto<FlussoRendicontazioneDistinctDto> pageDto = new PageDto<FlussoRendicontazioneDistinctDto>();
		
		long totalRecordsLong = totalRecords.longValue();

		pageDto.setList(mapResultInDto(listRecords));
		
		boolean hasNextPage = totalRecordsLong - (pageSize * page) > 0 ? true : false;
		pageDto.setNextPage(hasNextPage);
		pageDto.setPage(page);
		pageDto.setPageSize(pageSize);
		boolean hasPreviousPage = page > 1 ? true : false;
		pageDto.setPreviousPage(hasPreviousPage);
		Double totalPages = new Double((totalRecordsLong / pageSize)) + (totalRecordsLong % pageSize == 0 ? 0 : 1);
		pageDto.setTotalPages(totalPages.intValue());
		pageDto.setTotalRecords(totalRecordsLong);
		
		return pageDto;
	}

	@SuppressWarnings("unchecked")
	private List<Object[]> callPrivateFlussoRendicontazioneFunction(final Long mygovEnteId,
			final Date dt_data_regolamento_da, final Date dt_data_regolamento_a, final String iuf,
			final String identificativoUnivocoRegolamento, final int page, final int pageSize) {
		LOG.debug("Chiamata al metodo callFlussoRendicontazioneFunction del DAO FlussoRendicontazioneFunctionDao");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_flusso_rendicontazione_function(");
		if (mygovEnteId!=null)	sqlQuery.append(mygovEnteId + ","); else sqlQuery.append("null,");
		if (dt_data_regolamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_regolamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_regolamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_regolamento_a) + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(iuf))	sqlQuery.append("'" + iuf + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(identificativoUnivocoRegolamento))	sqlQuery.append("'" + identificativoUnivocoRegolamento + "',"); else sqlQuery.append("null,");
		sqlQuery.append(page + ",");
		sqlQuery.append(pageSize);
		sqlQuery.append(")");
		
		Query query = entityManager.createNativeQuery(sqlQuery.toString());
		
		List<Object[]> listResult = query.getResultList();
		
		return listResult;
	}

	private BigInteger callCountFlussoRendicontazioneFunction(final Long mygovEnteId, final Date dt_data_regolamento_da,
			final Date dt_data_regolamento_a, final String iuf, final String identificativoUnivocoRegolamento) {
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_count_flusso_rendicontazione_function(");
		if (mygovEnteId!=null)	sqlQuery.append(mygovEnteId + ","); else sqlQuery.append("null,");
		if (dt_data_regolamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_regolamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_regolamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_regolamento_a) + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(iuf))	sqlQuery.append("'" + iuf + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(identificativoUnivocoRegolamento))	sqlQuery.append("'" + identificativoUnivocoRegolamento + "'"); else sqlQuery.append("null");
		sqlQuery.append(")");
		
		Query queryCount = entityManager.createNativeQuery(sqlQuery.toString());
		
		BigInteger total = (BigInteger) queryCount.getSingleResult();
		
		return total;
	}
	
	private List<FlussoRendicontazioneDistinctDto> mapResultInDto(List<Object[]> rows) {
		List<FlussoRendicontazioneDistinctDto> dtoList = new ArrayList<FlussoRendicontazioneDistinctDto>();
		
		for (Object[] row : rows) {
			FlussoRendicontazioneDistinctDto dto = new FlussoRendicontazioneDistinctDto();
			
			dto.setCodIdentificativoFlusso(String.valueOf(row[0]));
			dto.setMygovEnteId(Long.valueOf(String.valueOf(row[1])));
			dto.setManageFlussoId(Long.valueOf(String.valueOf(row[2])));
			dto.setIdentificativoPsp(String.valueOf(row[3]));
			dto.setDtDataOraFlusso(row[4] != null ? (Date) row[4] : null);
			dto.setCodIdentificativoUnivocoRegolamento(String.valueOf(row[5]));
			dto.setDtDataRegolamento(row[6] != null ? (Date) row[6] : null);
			dto.setCodIstMittIdUnivMittTipoIdentificativoUnivoco(row[7] != null ? (char) row[7] : null);
			dto.setCodIstMittIdUnivMittCodiceIdentificativoUnivoco(String.valueOf(row[8]));
			dto.setDeIstMittDenominazioneMittente(row[9] != null ? (String) row[9] : null);
			dto.setCodIstRicevIdUnivRicevTipoIdentificativoUnivoco(row[10] != null ? (char) row[10] : null);
			dto.setCodIstRicevIdUnivRicevCodiceIdentificativoUnivoco(row[11] != null ? (String) row[11] : null);
			dto.setDeIstRicevDenominazioneRicevente(row[12] != null ? (String) row[12] : null);
			dto.setNumNumeroTotalePagamenti(row[13] != null ? ((BigDecimal) row[13]).longValue() : null);
			dto.setNumImportoTotalePagamenti(row[14] != null ? (BigDecimal) row[14] : null);
			dto.setDeImportoTotalePagamenti(row[14] != null ? Utilities.parseImportoString((BigDecimal) row[14]) : null);
			dto.setDtAcquisizione(row[15] != null ? (Date) row[15] : null);
			dto.setCodiceBicBancaDiRiversamento(row[16] != null ? (String) row[16] : null);
			
			dtoList.add(dto);
		}
		
		return dtoList;
	}
}
