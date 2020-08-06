package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.ListaMonitoraggioFlussiFilterDTO;
import it.tasgroup.iris.dto.flussi.ListaOperazioniPerEsitoFilterDTO;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiOnLineDao;
import it.tasgroup.iris.shared.util.UtilDate;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumStatoRendicontazione;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="PagamentiOnLineDaoService")
public class PagamentiOnLineDaoImpl extends DaoImplJpaCmtJta<PagamentiOnline> implements PagamentiOnLineDao {

	private static final Logger LOGGER = LogManager.getLogger(PagamentiOnLineDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){ 
		this.em=em;
	}	
	
	@Override
	public PagamentiOnline getPagamentoOnLineByCodAutorizzazione(String codAutorizzazione) {
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("codiceAutorizzazione", codAutorizzazione);
		PagamentiOnline pol = null;
		
		try {
			
			pol = (PagamentiOnline) uniqueResultByQuery("getPagamentoOnLineByCodAutorizzazioneAndMaxTsOperazione",parameters);
		
		} catch (Exception e) {
			LOGGER.error("error on  getPagamentiOnLineByCodAutorizzazione, cod = " + codAutorizzazione, e);
			throw new DAORuntimeException(e);
		}

		return pol;
	}
	
	/**
	 * 
	 */
	public PagamentiOnline savePOL(PagamentiOnline pol){
		
		PagamentiOnline polManaged = null;
		
		try {
			
			polManaged = save(pol, em);
			
		} catch (Exception e) {
			
			LOGGER.error("error on savePOL ", e);
			throw new DAORuntimeException(e);
			
		}
		
		return polManaged;
	}

    @Override
    public List<Object[]> readSystemIdsList() {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Object[]> cfs = null;
		try {			
			cfs = (List<Object[]>)listByQuery("readSystemIdsList",params);
		} catch (Exception e) {
			LOGGER.error("error on readSystemIdsList "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
    }

    @Override
    public List<Object[]> readApplicationIdsList() {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Object[]> cfs = null;
		try {			
			cfs = (List<Object[]>)listByQuery("readApplicationIdsList",params);
		} catch (Exception e) {
			LOGGER.error("error on readApplicationIdsList "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
    }

    @Override
    public List<Object[]> readTiOperationsList() {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Object[]> cfs = null;
		try {			
			cfs = (List<Object[]>)listByQuery("readTiOperationsList",params);
		} catch (Exception e) {
			LOGGER.error("error on readTiOperationsList "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
    }

    @Override
    public List<Object[]> listaOperazioniEsitiByFilters(ContainerDTO input) {
		
		List<Object[]>  listObj = null;
		
		Map parameters = new HashMap();
		
		ListaOperazioniPerEsitoFilterDTO inputDTO = (ListaOperazioniPerEsitoFilterDTO) input.getInputDTO();
		
		try {
			String selectFrom = "select ci.ESITO, (case when upper(ci.ESITO) = 'OK' then '' else ci.COD_ERRORE end) as cod_errore, count(*) as numero_operazioni " +
                                            "from PAGAMENTI_ONLINE  ci ";
			String filteringQuery= buildQueryAndParametersForListaOperazioni(selectFrom, inputDTO, parameters);
			
                        String groupingCondition = " group by ci.ESITO, (case when upper(ci.ESITO) = 'OK' then '' else ci.COD_ERRORE end) ";
                        String orderingCondition = " ORDER BY ci.ESITO DESC, (case when upper(ci.ESITO) = 'OK' then '' else ci.COD_ERRORE end) ASC ";
                        
			PagingCriteria pagingCriteria = input.getPagingCriteria();
			
			PagingData pagingData = input.getPagingData();
			
			listObj = (List<Object[]>)paginateByQueryWithResultsetMapping(filteringQuery + groupingCondition + orderingCondition, pagingCriteria, pagingData, parameters, "listaOperazioniPerEsitoResultMapping");
			
		} catch (Exception e) {
			LOGGER.error("error on listaOperazioniEsitiByFilters ", e);
			throw new DAORuntimeException(e);
		}
		return listObj;
    }
    
    @Override
    public List<PagamentiOnline> listaDettaglioOperazioniEsitiByFilters(ContainerDTO input) {
		
		List<PagamentiOnline>  listObj = null;
		
		Map parameters = new HashMap();
		
		ListaOperazioniPerEsitoFilterDTO inputDTO = (ListaOperazioniPerEsitoFilterDTO) input.getInputDTO();
		
		try {
			String selectFrom = "select ci.* " +
                                            "from PAGAMENTI_ONLINE ci ";
                        
			String filteringQuery= buildQueryAndParametersForListaOperazioni(selectFrom, inputDTO, parameters);
			
                        String orderingCondition = " ORDER BY ci.ts_Operazione desc ";
                        
			PagingCriteria pagingCriteria = input.getPagingCriteria();
			
			PagingData pagingData = input.getPagingData();
			
			listObj = (List<PagamentiOnline>)paginateByQuery(PagamentiOnline.class, filteringQuery + orderingCondition, pagingCriteria, pagingData, parameters);
			
		} catch (Exception e) {
                        e.printStackTrace();
			LOGGER.error("error on listaDettaglioOperazioniEsitiByFilters ", e);
			throw new DAORuntimeException(e);
		}
		return listObj;
    }

    private String buildQueryAndParametersForListaOperazioni(String selectClause, ListaOperazioniPerEsitoFilterDTO dto, Map parameters) {

		String selectFrom = selectClause;
                                         
		List<String> whereConditionsList = new ArrayList<String>();
                StringBuilder whereConditions = new StringBuilder("");
                
                String utenteCreatore = dto.getUtenteCreatore();
		String codicePagamento = dto.getCodicePagamento();
                if ((utenteCreatore != null && !utenteCreatore.isEmpty()) || (codicePagamento != null && !codicePagamento.isEmpty())){
                    selectFrom = selectFrom + " join DISTINTE_PAGAMENTO dp on ci.ID_DISTINTE_PAGAMENTO=dp.ID ";
                }
                
		Date dataDa = dto.getTsInserimentoDa();
		if (dataDa != null ){
			whereConditionsList.add(" ci.TS_INSERIMENTO >=:TS_INSERIMENTO_DA ");			
			parameters.put("TS_INSERIMENTO_DA", dataDa);
		}
		
		Date dataA = dto.getTsInserimentoA();
		if (dataA != null ){
			whereConditionsList.add(" ci.TS_INSERIMENTO <=:TS_INSERIMENTO_A ");
			parameters.put("TS_INSERIMENTO_A", UtilDate.setOrarioEndOfDay(dataA));
		}
		
		String systemId = dto.getSystemId();
		if (systemId != null && !systemId.isEmpty()){
			whereConditionsList.add(" upper(ci.SYSTEM_ID) =:SYSTEM_ID ");
			parameters.put("SYSTEM_ID", systemId.toUpperCase());
		}

		String applicationId = dto.getApplicationId();
		if (applicationId != null  && !applicationId.isEmpty()){
			whereConditionsList.add(" upper(ci.APPLICATION_ID) = :APPLICATION_ID ");
			parameters.put("APPLICATION_ID", applicationId.toUpperCase());
		}
		
		String tiOperazione = dto.getTiOperazione();
		if (tiOperazione != null  && !tiOperazione.isEmpty()){
			whereConditionsList.add(" upper(ci.TI_OPERAZIONE) = :TI_OPERAZIONE ");
			parameters.put("TI_OPERAZIONE",tiOperazione.toUpperCase());
		}
		if (codicePagamento != null && !codicePagamento.isEmpty()){
			whereConditionsList.add(" upper(dp.COD_PAGAMENTO) like :CODICE_PAGAMENTO ");
			parameters.put("CODICE_PAGAMENTO", "%" + codicePagamento.toUpperCase() + "%");
		}

		String sessionIdToken = dto.getSessionIdToken();
		if (sessionIdToken != null && !sessionIdToken.isEmpty()){
                        whereConditionsList.add(" upper(ci.SESSION_ID_TOKEN) like :SESSION_ID_TOKEN ");
			parameters.put("SESSION_ID_TOKEN","%" + sessionIdToken.toUpperCase() + "%");
		}
                
                String esito = dto.getEsito();
		if (esito != null && !esito.isEmpty()){
                        whereConditionsList.add(" upper(ci.ESITO) = :ESITO ");
			parameters.put("ESITO", esito.toUpperCase());
		}
                
                String codErrore = dto.getCodErrore();
		if (codErrore != null && !codErrore.isEmpty()){
                        whereConditionsList.add(" upper(ci.COD_ERRORE) = :COD_ERRORE ");
			parameters.put("COD_ERRORE", codErrore.toUpperCase());
		}
                
                if (utenteCreatore != null && !utenteCreatore.isEmpty()){
                        whereConditionsList.add(" upper(dp.UTENTE_CREATORE) like :UTENTE_CREATORE ");
			parameters.put("UTENTE_CREATORE", "%" + utenteCreatore.toUpperCase() + "%");
		}
		
                if(!whereConditionsList.isEmpty()){
                    whereConditions.append("where ");
                    
                    for (String cond : whereConditionsList) {
                        if(!whereConditions.toString().equals("where ")){
                            whereConditions.append(" AND ");
                        }
                        
                        whereConditions.append(cond);
                    }
                }
                
		return selectFrom + whereConditions;	
	}

}
