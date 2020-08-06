package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.CasellarioInfo;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.ListaMonitoraggioFlussiFilterDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CasellarioInfoDao;
import it.tasgroup.iris.shared.util.UtilDate;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumStatoRendicontazione;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CasellarioInfoDaoService")
public class CasellarioInfoDaoImpl extends DaoImplJpaCmtJta<CasellarioInfo> implements CasellarioInfoDao {
	
	private static final Logger LOGGER = LogManager.getLogger(CasellarioInfoDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	
	@Override
	public List<CasellarioInfo> listCasellarioInfoByFilterParameters(ContainerDTO input) {
		
		List<CasellarioInfo> retList = null;
		
		Map parameters = new HashMap();
		
		ListaMonitoraggioFlussiFilterDTO inputDTO = (ListaMonitoraggioFlussiFilterDTO) input.getInputDTO();
		
		try {
			
			String filteringQuery= buildQueryAndParameters(inputDTO, parameters);
			
			PagingCriteria pagingCriteria = input.getPagingCriteria();
			
			PagingData pagingData = input.getPagingData();
			
			retList = paginateByQuery(CasellarioInfo.class, filteringQuery, pagingCriteria, pagingData, parameters);
			
		} catch (Exception e) {
			
			LOGGER.error("error on listCasellarioInfoByFilterParameters ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return retList;
	}
	
	private String buildQueryAndParameters(ListaMonitoraggioFlussiFilterDTO dto, Map parameters) {

		String query = "Select ci.* ";
        String from = " from CASELLARIO_INFO ci ";
        String where = " where ";
		String orderingCondition = "";
		StringBuffer whereConditions = new StringBuffer("");
		
		String stato = dto.getStato();
                if (stato != null){
                    
                    if(stato.equals("") == false && stato.equals(EnumStatoRendicontazione.RIFIUTATO.getChiave())){
                        
                        if(dto.getRicercaAnomalie() != null){
                            from = addJoinTableRendicontazioni(from);
                            where = addJoinConditionRendicontazioni(where); 
                        }
                        
                        where = where + " ci.TIPO_ERRORE is not null AND ci.TIPO_ERRORE <> 0 AND ci.FLAG_ELABORAZIONE = 1 ";
                        
                    } else if(stato.equals("") == false && stato.equals(EnumStatoRendicontazione.DA_ELABORARE.getChiave())){
                        
                         if(dto.getRicercaAnomalie() != null){
                            from = addJoinTableRendicontazioni(from);
                            where = addJoinConditionRendicontazioni(where);                           
                        }
                        
                        where = where + " ci.FLAG_ELABORAZIONE is not null AND ci.FLAG_ELABORAZIONE = 0 ";
                        
                    } else {
                        
                        if(stato.equals("") == false ){
                            
                            from = addJoinTableRendicontazioni(from);
                            where = addJoinConditionRendicontazioni(where); 
                            
                            whereConditions.append(" r.STATO =:STATO ");
                            
                            parameters.put("STATO",stato);
                            
                        } else {
                            from = from + " left join RENDICONTAZIONI r on ci.ID = r.ID_CASELLARIO_INFO";
//                            where = where + " (1=1) ";   //Prende tutti gli stati
                            
                        }
                        
                        BigDecimal importoDa = dto.getImportoDa();
                        if (importoDa != null && importoDa.compareTo(BigDecimal.ZERO)>=0){
                            whereConditions.append(" AND r.IMPORTO >=:IMPORTO_DA ");
                            parameters.put("IMPORTO_DA",importoDa);
                        }
                        
                        BigDecimal importoA = dto.getImportoA();
                        if (importoA != null && importoA.compareTo(BigDecimal.ZERO)>=0){
                            whereConditions.append(" AND r.IMPORTO <=:IMPORTO_A ");
                            parameters.put("IMPORTO_A",importoA);
                        }
                        
                    }
                    
                }
                
                if(dto.getRicercaAnomalie() != null){
                    whereConditions.append(" AND r.STATO in (:statianomali) ");
                    parameters.put("statianomali",dto.getRicercaAnomalie());
                }
                
                Date dataDa = dto.getDataCreazioneDa();
                
		if (dataDa != null ){
			
			whereConditions.append(" AND ci.DATA_ELABORAZIONE >=:DATA_CREAZIONE_DA ");			
			parameters.put("DATA_CREAZIONE_DA", dataDa);
		}
		
		Date dataA = dto.getDataCreazioneA();
		if (dataA != null ){
			whereConditions.append(" AND ci.DATA_ELABORAZIONE <=:DATA_CREAZIONE_A ");
			parameters.put("DATA_CREAZIONE_A", UtilDate.setOrarioEndOfDay(dataA));
		}
		
		String tipoFlusso = dto.getTipoflusso();
		if (tipoFlusso != null && tipoFlusso.equals("") == false){
			whereConditions.append(" AND ci.TIPO_FLUSSO =:TIPO_FLUSSO");
			parameters.put("TIPO_FLUSSO", tipoFlusso);
		}

		String nomeSupporto = dto.getNomeSupporto();
		if (nomeSupporto != null  && nomeSupporto.equals("") == false){
			nomeSupporto = nomeSupporto.replace("*", "%");
			whereConditions.append(" AND ci.NOME_SUPPORTO like :NOME_SUPPORTO");
			parameters.put("NOME_SUPPORTO", nomeSupporto);
		}
		
		Integer dimensioneDa = dto.getDimensioneDa();
		if (dimensioneDa != null && dimensioneDa>0){
			whereConditions.append(" AND ci.DIMENSIONE >=:DIMENSIONE_DA");
			parameters.put("DIMENSIONE_DA",dimensioneDa);
		}
		Integer dimensioneA = dto.getDimensioneA();
		if (dimensioneA != null && dimensioneDa>0){
			whereConditions.append(" AND ci.DIMENSIONE <=:DIMENSIONE_A");
			parameters.put("DIMENSIONE_A",dimensioneA);
		}

		String mittente = dto.getMittente();
		if (mittente != null && !mittente.isEmpty()){
			String abiCodeCIPBITMM = "";
			if(mittente.equals("CIPBITMM")) {
				abiCodeCIPBITMM = " OR ci.MITTENTE =:ABICODECIPBITMM ";
				parameters.put("ABICODECIPBITMM","05000");
			}
			if(!"01030".equals(mittente)){
				whereConditions.append(" AND (ci.MITTENTE =:MITTENTE" + abiCodeCIPBITMM + ")");
			} else {
				whereConditions.append(" AND (ci.MITTENTE =:MITTENTE" + abiCodeCIPBITMM + " or ci.MITTENTE IS NULL)");
			}

			parameters.put("MITTENTE",mittente);
		}

		String ricevente = dto.getRicevente();
        StringTokenizer stringhe = new StringTokenizer(ricevente,"|");
        int howMany2 = stringhe.countTokens();
//        for (int i = 0; i < howMany2; i++) {
//        	//nuovo value, sicuramente della forma abi/value
//        	String value = stringhe.nextToken();
//        	LOGGER.info("OUTPUT> " + value);
//		}			
		
		
		if (ricevente != null && !ricevente.isEmpty()){
			if(!"IRIS".equalsIgnoreCase(ricevente)){
//				whereConditions.append(" AND ci.RICEVENTE =:RICEVENTE");
				if (howMany2 == 1) {
					whereConditions.append(" AND (upper(ci.RICEVENTE) =:RICEVENTE or ci.RICEVENTE IS NULL)");
					parameters.put("RICEVENTE",stringhe.nextToken());
				} else if (howMany2 == 2) {
					whereConditions.append(" AND (upper(ci.RICEVENTE) =:RICEVENTE1 "
														+ "or upper(ci.RICEVENTE) =:RICEVENTE2 " 
														+ "or ci.RICEVENTE IS NULL) ");
					parameters.put("RICEVENTE1",stringhe.nextToken());
					parameters.put("RICEVENTE2",stringhe.nextToken());
				}	
				
			} else {
				whereConditions.append(" AND (upper(ci.RICEVENTE) =:RICEVENTE or ci.RICEVENTE IS NULL)");
				parameters.put("RICEVENTE",ricevente.toUpperCase());							
			}
			
			//prima era così
//			if(!"IRIS".equalsIgnoreCase(ricevente)){
//				whereConditions.append(" AND ci.RICEVENTE =:RICEVENTE");
//			} else {
//				whereConditions.append(" AND (upper(ci.RICEVENTE) =:RICEVENTE or ci.RICEVENTE IS NULL)");
//			}			
//			parameters.put("RICEVENTE",ricevente.toUpperCase());			
			
			
			
		}
		
		orderingCondition = " ORDER BY ci.DATA_ELABORAZIONE DESC ";
		
		if (whereConditions.toString().isEmpty())
			whereConditions.append("1=1");
		else if (whereConditions.indexOf("AND") < 2)
			whereConditions = whereConditions.delete(0, whereConditions.indexOf("AND") + 3);
		
		return query + from + where + whereConditions + orderingCondition;	
	}

    private String addJoinTableRendicontazioni(String from) {
        return from + ", RENDICONTAZIONI r ";
    }
	
    private String addJoinConditionRendicontazioni(String where) {
        return where + " ci.ID = r.ID_CASELLARIO_INFO AND ";
    }
	
	@Override
	public CasellarioInfo retrieveCasellarioInfoById(Long id) {
		CasellarioInfo retrieved = null;
		try {
			retrieved = loadById(CasellarioInfo.class,id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveDistintaRiaccreditoById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}

	@Override
	public List<Object[]> getListMittenti() {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Object[]> cfs = null;
		try {			
			cfs = (List<Object[]>)listByQuery("getListMittenti",params);
		} catch (Exception e) {
			LOGGER.error("error on getListMittenti "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
	}
	
	@Override
	public List<Object[]> getListMittenti(String ricevente) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ricevente", ricevente);
		List<Object[]> cfs = null;
		try {			
			cfs = (List<Object[]>)listByQuery("getListMittentiPerRicevente",params);
		} catch (Exception e) {
			LOGGER.error("error on getListMittenti "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
	}
	
	

	@Override
	public List<Object[]> getListRiceventi() {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Object[]> cfs = null;
		try {			
//			params.put("STATO", "A");
			cfs = (List<Object[]>)listByQuery("getListRiceventi",params); 
//			ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
//			if(cpl.getProperty("theme").equalsIgnoreCase("tuscany")) {
//				String[] irisAtLeast = new String[] {"IRIS","IRIS"};
//				cfs.add(irisAtLeast);
//			}

		} catch (Exception e) {
			LOGGER.error("error on getListRiceventi "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
	}
}
