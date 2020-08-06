
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.tasgroup.iris.domain.DistinteRiaccredito;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.ListaDistinteRiaccreditoFilterDTO;
import it.tasgroup.iris.persistence.dao.interfaces.DistinteRiaccreditoDao;
import it.tasgroup.iris.shared.util.UtilDate;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="DistinteRiaccreditoDaoService")
public class DistinteRiaccreditoDaoImpl extends DaoImplJpaCmtJta<DistinteRiaccredito> implements DistinteRiaccreditoDao {
	
	private static final Logger LOGGER = LogManager.getLogger(DistinteRiaccreditoDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public DistinteRiaccredito retrieveDistintaRiaccreditoById(Long id) {
		DistinteRiaccredito retrieved = null;
		try {
			retrieved = loadById(DistinteRiaccredito.class,id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveDistintaRiaccreditoById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}
	
	@Override
	public List<Object[]> listDistinteRiaccreditoByFilterParameters(ContainerDTO containerDTO) {	
		
	    List<Object[]> retList = null;
		
		Map parameters = new HashMap();
		
		ListaDistinteRiaccreditoFilterDTO inputDTO = (ListaDistinteRiaccreditoFilterDTO) containerDTO.getInputDTO();
				
		try {
			
			String filteringQuery= buildQueryAndParameters(inputDTO, parameters);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			retList = (List<Object[]>)paginateByQueryWithResultsetMapping(filteringQuery, pagingCriteria, pagingData, parameters, "ricercaRiaccreditoEntiMapping");
		} catch (Exception e) {
		    e.printStackTrace();
			LOGGER.error("error on listDistinteRiaccreditoByFilterParameters ", e);
			throw new DAORuntimeException(e);
		}
		
		return retList;
	}
	
	@Override
	public List<Object[]> listDistinteRiaccreditoByFilterParametersFull(ContainerDTO containerDTO) {	
		
		List<Object[]> retList = null;
		
		Map parameters = new HashMap();
		
		ListaDistinteRiaccreditoFilterDTO inputDTO = (ListaDistinteRiaccreditoFilterDTO) containerDTO.getInputDTO();
		
		try {
			
			String filteringQuery= buildQueryAndParameters(inputDTO, parameters);
			
			Query q = em.createNativeQuery(filteringQuery);
			
			
			for(Object key : parameters.keySet()){
                q.setParameter((String)key, parameters.get(key));
            }
            
//            q.unwrap(SQLQuery.class).addEntity("p", Pagamenti.class);
//            q.unwrap(SQLQuery.class).addScalar("data_ordinamento", StandardBasicTypes.DATE);
			retList = q.getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("error on listDistinteRiaccreditoByFilterParameters ", e);
			throw new DAORuntimeException(e);
		}
		
		return retList;
	}
	
	@Override
	public List<CodiceDescrizioneVO> listaBeneficiari() {
	    List<CodiceDescrizioneVO> retList = new ArrayList<CodiceDescrizioneVO>();
        
        try {
            
            String sqlQuery = "select distinct IBAN_BENEFICIARIO, RAG_SOCIALE_BENEFICIARIO  from BOZZE_BONIFICI_RIACCREDITO";
            
            Query query= em.createNativeQuery(sqlQuery);
            
            List<Object[]> res = query.getResultList();
            
            for (Object[] row : res) {
                CodiceDescrizioneVO item = new CodiceDescrizioneVO();
                item.setCodice((String)row[0]);
                item.setDescrizione((String)row[0]+ " ("+(String)row[1]+") ");
                retList.add(item);
            }
            
        } catch (Exception e) {
            LOGGER.error("error on listDistinteRiaccreditoByFilterParameters ", e);
            throw new DAORuntimeException(e);
        }
        return retList;
    }
	
	@Override
	public Map<String, Map<String, String>> listaDebiti() {
	    Map<String, Map<String, String>> retList = new HashMap<String, Map<String, String>>();
        
        try {            
            String sqlQuery = "select distinct id_ente, CD_TRB_ENTE, de_trb from TRIBUTIENTI order by id_ente, CD_TRB_ENTE, de_trb";
            
            Query query= em.createNativeQuery(sqlQuery);
            
            List<Object[]> res = query.getResultList();
            
            for (Object[] row : res) {
                String idEnte = (String)row[0];
                String cdTrbEnte = (String)row[1];
                String deTributo = (String)row[2];
                
                Map<String, String> map = retList.get(idEnte);
                
                if(map == null) {
                    map = new HashMap<String, String>();
                }
                
                map.put(cdTrbEnte, deTributo);
                retList.put(idEnte, map);
            }
            
        } catch (Exception e) {
            LOGGER.error("error on listDistinteRiaccreditoByFilterParameters ", e);
            throw new DAORuntimeException(e);
        }
        return retList;
    }
	
	private String buildQueryAndParameters(ListaDistinteRiaccreditoFilterDTO dto, Map parameters) {
		
		String selectFromWhere = "SELECT DR.DATA_SPEDIZIONE," +
				"EBR.TS_INSERIMENTO," +
				"EBR.CODICE_RIFERIMENTO," +
				"BBR.RAG_SOCIALE_BENEFICIARIO," +
				"BBR.IBAN_BENEFICIARIO, " +
				"BBR.IMPORTO," +
				"BBR.ID_PAG_COND_ENTE," +
				"BR.STATO," +
				"TE.DE_TRB," +
				"P.TS_DECORRENZA, " +
				"DP.COD_PAGAMENTO, " +			
				"('-') AS CAUSALE "+
				// non mostriamo più questa roba qui ....
				//"('/RFB/' || BBR.ID_PAG_COND_ENTE || '/' || (TRIM(CHAR(INTEGER(BBR.IMPORTO))) ||'.' ||TRIM(CHAR(SUBSTR(CHAR(DECIMAL(BBR.IMPORTO,13,2)), 13)))) || '/TXT/DEBITORE/' || COALESCE(BBR.CODFISC_DEBITORE,'')) AS CAUSALE " +
    				"FROM BOZZE_BONIFICI_RIACCREDITO BBR " +
    				"JOIN BONIFICI_RIACCREDITO BR ON BBR.ID_BONIFICI_RIACCREDITO = BR.ID " +
    				"JOIN DISTINTE_RIACCREDITO DR ON BR.ID_DISTINTE_RIACCREDITO = DR.ID " +
    				"JOIN ESITI_BONIFICI_RIACCREDITO EBR ON BBR.ID_BONIFICI_RIACCREDITO = EBR.ID_BONIFICI_RIACCREDITO " +
    				"JOIN CONDIZIONI C ON BBR.ID_CONDIZIONE = C.ID_CONDIZIONE " +
    				"JOIN TRIBUTIENTI TE ON BBR.ID_ENTE = TE.ID_ENTE " +
    				"JOIN PAGAMENTI P ON BBR.ID_CONDIZIONE = P.ID_CONDIZIONE " +
    				"JOIN DISTINTE_PAGAMENTO DP ON P.ID_DISTINTE_PAGAMENTO = DP.ID " +
				"WHERE TE.CD_TRB_ENTE = C.CD_TRB_ENTE " +
				    "AND  P.ST_PAGAMENTO = 'ES' ";
    	 
		StringBuffer whereConditions = new StringBuffer("");
		
		Date dataDa = dto.getDataDisposizioneDa();
		if (dataDa != null ){
			whereConditions.append(" AND DR.DATA_SPEDIZIONE >=:DATA_SPEDIZIONE_DA");
			parameters.put("DATA_SPEDIZIONE_DA", dataDa);
		}
		
		Date dataA = dto.getDataDisposizioneA();
		if (dataA != null ){
			whereConditions.append(" AND DR.DATA_SPEDIZIONE <=:DATA_SPEDIZIONE_A");
			parameters.put("DATA_SPEDIZIONE_A", UtilDate.setOrarioEndOfDay(dataA));
		}
		
		Date dataRicezioneEsitoDa = dto.getDataRicezioneEsitoDa();
        if (dataRicezioneEsitoDa != null ){
            whereConditions.append(" AND BR.TS_INSERIMENTO >=:DATA_RICEZIONE_ESITO_DA");
            parameters.put("DATA_RICEZIONE_ESITO_DA", dataRicezioneEsitoDa);
        }
        
        Date dataRicezioneEsitoA = dto.getDataRicezioneEsitoA();
        if (dataRicezioneEsitoA != null ){
            whereConditions.append(" AND BR.TS_INSERIMENTO <=:DATA_RICEZIONE_ESITO_A");
            parameters.put("DATA_RICEZIONE_ESITO_A", UtilDate.setOrarioEndOfDay(dataRicezioneEsitoA));
        }
		
		BigDecimal importoDa = dto.getImportoDa();
		if (importoDa != null && importoDa.compareTo(BigDecimal.ZERO)>=0){
			whereConditions.append(" AND BBR.IMPORTO >=:IMPORTO_DA");
			parameters.put("IMPORTO_DA",importoDa);
		}
		BigDecimal importoA = dto.getImportoA();
		if (importoA != null && importoA.compareTo(BigDecimal.ZERO)>=0){
			whereConditions.append(" AND BBR.IMPORTO <=:IMPORTO_A");
			parameters.put("IMPORTO_A",importoA);
		}
		
		String stato = dto.getStato();
		if (stato != null && !stato.equals("")){
			whereConditions.append(" AND BR.STATO =:STATO");
			parameters.put("STATO",stato);
			
			if ("ESEGUITO".equals(stato))
				
				whereConditions.append(" AND P.FLAG_INCASSO = '2'");
				
		}
		
		String idEnte = dto.getRagioneSocialeBeneficiario();
        if (idEnte != null && !idEnte.equals("")){
            whereConditions.append(" AND BBR.IBAN_BENEFICIARIO = :IBAN_BENEFICIARIO");
            parameters.put("IBAN_BENEFICIARIO",idEnte);
        }
        
        String tipoDebito = dto.getTipoDebito();
        if (tipoDebito != null && !tipoDebito.equals("")){
            whereConditions.append(" AND TE.CD_TRB_ENTE = :TIPO_DEBITO");
            parameters.put("TIPO_DEBITO",tipoDebito);
        }
        
        String idPagamentoEnte = dto.getIdPagamentoEnte();
        
        if (idPagamentoEnte != null && !idPagamentoEnte.equals("")){
        	
        	if(!idPagamentoEnte.contains("*")) {
				
        		whereConditions.append(" AND BBR.ID_PAG_COND_ENTE = :ID_PAGAMENTO_ENTE");
                parameters.put("ID_PAGAMENTO_ENTE",idPagamentoEnte);
				
			} else {
			
				whereConditions.append(" AND BBR.ID_PAG_COND_ENTE like :ID_PAGAMENTO_ENTE");
				parameters.put("ID_PAGAMENTO_ENTE", idPagamentoEnte.replaceAll("[*]", "%"));
			}
            
        }
        
        String codPagamentoIris = dto.getCodicePagamentoIris();
        if (codPagamentoIris != null && !codPagamentoIris.equals("")){
            whereConditions.append(" AND DP.COD_PAGAMENTO = :COD_PAGAMENTO_IRIS");
            parameters.put("COD_PAGAMENTO_IRIS",codPagamentoIris);
        }
        
		String orderingCondition = " ORDER BY DR.DATA_SPEDIZIONE DESC";
		
		return selectFromWhere + whereConditions + orderingCondition;
	}

	
	@Override
	public List<DistinteRiaccredito> getDistinteRiaccreditoByIdBozzeBonificiRiaccredito(ContainerDTO containerDTO) {	
		
		List<DistinteRiaccredito> retList = null;
		
		Long id = (Long) containerDTO.getInputDTOList().get(0);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("idBozze", id);

		try {
			

			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();

			String query="select distinct d.* from DISTINTE_RIACCREDITO d inner join BONIFICI_RIACCREDITO b on d.ID = b.ID_DISTINTE_RIACCREDITO "+
							"where b.ID_DISTINTE_RIACCREDITO = (select br.ID_DISTINTE_RIACCREDITO from BONIFICI_RIACCREDITO br inner join BOZZE_BONIFICI_RIACCREDITO bo on br.ID = bo.ID_BONIFICI_RIACCREDITO where bo.id =:idBozze ) ";
			
			retList = paginateByQuery(DistinteRiaccredito.class, query, pagingCriteria, pagingData, params);
				
	
		} catch (Exception e) {
			LOGGER.error("error on getDistinteRiaccreditoByIdBozzeBonificiRiaccredito ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	
	@Override
	public List<DistinteRiaccredito> getDistinteRiaccreditoByIdBonificiRiaccredito(ContainerDTO containerDTO) {	
		
		List<DistinteRiaccredito> retList = null;
		
		Long id = (Long) containerDTO.getInputDTOList().get(0);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("idBonifico", id);

		try {
			

			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();

			String query="select distinct d.* from DISTINTE_RIACCREDITO d inner join BONIFICI_RIACCREDITO b on d.ID = b.ID_DISTINTE_RIACCREDITO "+
							"where b.ID_DISTINTE_RIACCREDITO = (select br.ID_DISTINTE_RIACCREDITO from BONIFICI_RIACCREDITO br where br.id =:idBonifico ) ";
			
			retList = paginateByQuery(DistinteRiaccredito.class, query, pagingCriteria, pagingData, params);
				
	
		} catch (Exception e) {
			LOGGER.error("error on getDistinteRiaccreditoByIdBonificiRiaccredito ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}

}
