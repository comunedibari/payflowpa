package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.CasellarioDispo;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.ListaMonitoraggioFlussiFilterDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CasellarioDispoDao;
import it.tasgroup.iris.shared.util.UtilDate;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumStatoDistintePagamento;
import it.tasgroup.services.util.enumeration.EnumTipoFlussoCasellarioDispo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CasellarioDispoDaoService")
public class CasellarioDispoDaoImpl extends DaoImplJpaCmtJta<CasellarioDispo> implements CasellarioDispoDao {
	
	private static final Logger LOGGER = LogManager.getLogger(CasellarioDispoDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	
	@Override
	public List<CasellarioDispo> listCasellarioDispoByFilterParameters(ContainerDTO input) {
		
		List<CasellarioDispo> retList = null;
			
		Map parameters = new HashMap();
		
		ListaMonitoraggioFlussiFilterDTO inputDTO = (ListaMonitoraggioFlussiFilterDTO) input.getInputDTO();
		
		try {
			
			String filteringQuery= buildQueryAndParameters(inputDTO, parameters);
			
			PagingCriteria pagingCriteria = input.getPagingCriteria();
			
			PagingData pagingData = input.getPagingData();
			
			retList = paginateByQuery(CasellarioDispo.class, filteringQuery, pagingCriteria, pagingData, parameters);
			
		} catch (Exception e) {
			LOGGER.error("error on listCasellarioDispoByFilterParameters ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	private String buildQueryAndParameters(ListaMonitoraggioFlussiFilterDTO dto, Map parameters) {
		
		String selectFromWhere = "Select cd.* from CASELLARIO_DISPO cd ";
		StringBuffer whereConditions = new StringBuffer("");
		String orderingCondition = "";
//		TIPO FLUSSO
		String tipoFlusso = dto.getTipoflusso();
		if (tipoFlusso != null) {
			
			if (!tipoFlusso.equals("")) {
				
				whereConditions.append(" AND cd.TIPO_FLUSSO =:TIPO_FLUSSO");
				parameters.put("TIPO_FLUSSO",tipoFlusso);

				if (tipoFlusso.equals(EnumTipoFlussoCasellarioDispo.PC.getChiave()) || tipoFlusso.equals(EnumTipoFlussoCasellarioDispo.CB.getChiave())) {
					selectFromWhere = selectFromWhere + ", DISTINTE_RIACCREDITO dr where cd.ID_DISTINTE_RIACCREDITO = dr.ID ";
				} else {
					selectFromWhere = selectFromWhere + ", DISTINTE_PAGAMENTO d where cd.ID_DISTINTE_PAGAMENTO = d.ID ";
				}

			} else {
				
				selectFromWhere = selectFromWhere + " left join DISTINTE_PAGAMENTO d on cd.ID_DISTINTE_PAGAMENTO = d.ID left join DISTINTE_RIACCREDITO dr on cd.ID_DISTINTE_RIACCREDITO = dr.ID where (cd.TIPO_FLUSSO <> '' or cd.TIPO_FLUSSO is null) ";

			}																						
		}
		
		String stato = dto.getStato();
		if (stato != null && !stato.equals("")) {
			if (stato.equals(EnumStatoDistintePagamento.DA_ELABORARE.getChiave())) {
				selectFromWhere = selectFromWhere
						+ " AND cd.FLAG_ELABORAZIONE is not null AND cd.FLAG_ELABORAZIONE = 0 ";
			}
			
			if (tipoFlusso.equals(EnumTipoFlussoCasellarioDispo.PC.getChiave())) {
				whereConditions.append(" AND dr.STATO =:STATO");
				parameters.put("STATO", stato);
			} else if (!tipoFlusso.equals("")) {
				whereConditions.append(" AND d.STATO =:STATO");
				parameters.put("STATO", stato);
			} else if (tipoFlusso.equals("")) {
				whereConditions.append(" AND ( d.STATO =:STATO OR dr.STATO =:STATO ) ");
				parameters.put("STATO", stato);
			}
		}

		BigDecimal importoDa = dto.getImportoDa();
		if (importoDa != null && importoDa.compareTo(BigDecimal.ZERO)>=0){
			
			if (tipoFlusso.equals(EnumTipoFlussoCasellarioDispo.PC.getChiave())) {
				whereConditions.append(" AND dr.IMPORTO >=:IMPORTO_DA");
				parameters.put("IMPORTO_DA",importoDa);
			} else if (!tipoFlusso.equals("")) {
				whereConditions.append(" AND d.IMPORTO >=:IMPORTO_DA");
				parameters.put("IMPORTO_DA",importoDa);
			} else if (tipoFlusso.equals("")) {
				whereConditions.append(" AND ( d.IMPORTO >=:IMPORTO_DA OR dr.IMPORTO >=:IMPORTO_DA ) ");
				parameters.put("IMPORTO_DA",importoDa);
			}
		}
		
		BigDecimal importoA = dto.getImportoA(); 
		if (importoA != null && importoA.compareTo(BigDecimal.ZERO)>=0){
			
			if (tipoFlusso.equals(EnumTipoFlussoCasellarioDispo.PC.getChiave())) {
				whereConditions.append(" AND dr.IMPORTO <=:IMPORTO_A");
				parameters.put("IMPORTO_A",importoA);
			} else if (!tipoFlusso.equals("")) {
				whereConditions.append(" AND d.IMPORTO <=:IMPORTO_A");
				parameters.put("IMPORTO_A",importoA);
			} else if (tipoFlusso.equals("")) {
				whereConditions.append(" AND ( d.IMPORTO >=:IMPORTO_A OR dr.IMPORTO >=:IMPORTO_A ) ");
				parameters.put("IMPORTO_A",importoA);
			}
		}

		Date dataDa = dto.getDataCreazioneDa();
		if (dataDa != null ){
			whereConditions.append(" AND cd.DATA_ELABORAZIONE >=:DATA_CREAZIONE_DA");			
			parameters.put("DATA_CREAZIONE_DA", dataDa);
			
		}
		
		Date dataA = dto.getDataCreazioneA();
		if (dataA != null ){
			whereConditions.append(" AND cd.DATA_ELABORAZIONE <=:DATA_CREAZIONE_A");
			parameters.put("DATA_CREAZIONE_A", UtilDate.setOrarioEndOfDay(dataA));
			
		}
		
		String nomeSupporto = dto.getNomeSupporto();
		if (nomeSupporto != null  && nomeSupporto.equals("") == false){
			nomeSupporto = nomeSupporto.replace("*", "%");
			whereConditions.append(" AND cd.NOME_SUPPORTO like :NOME_SUPPORTO");
			parameters.put("NOME_SUPPORTO", nomeSupporto);
			
		}
		
		Integer dimensioneDa = dto.getDimensioneDa();
		if (dimensioneDa != null && dimensioneDa>0){
			whereConditions.append(" AND cd.DIMENSIONE >=:DIMENSIONE_DA");
			parameters.put("DIMENSIONE_DA",dimensioneDa);
			
		}
		Integer dimensioneA = dto.getDimensioneA();
		if (dimensioneA != null && dimensioneDa>0){
			whereConditions.append(" AND cd.DIMENSIONE <=:DIMENSIONE_A");
			parameters.put("DIMENSIONE_A",dimensioneA);
			
		}
		
		orderingCondition = " ORDER BY cd.DATA_ELABORAZIONE DESC ";

		
		return selectFromWhere + whereConditions + orderingCondition;	
	}
	
	@Override
	public CasellarioDispo retrieveCasellarioDispoById(Long id) {
		CasellarioDispo retrieved = null;
		try {
			retrieved = loadById(CasellarioDispo.class,id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveCasellarioDispoById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}
}
