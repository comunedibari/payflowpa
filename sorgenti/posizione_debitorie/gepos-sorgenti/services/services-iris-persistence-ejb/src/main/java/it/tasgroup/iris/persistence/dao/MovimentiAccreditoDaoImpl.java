
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.MovimentiAccredito;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.FilterNDPNonRiconciliatiDTO;
import it.tasgroup.iris.persistence.dao.interfaces.MovimentiAccreditoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumStatoRiconciliazione;
import it.tasgroup.services.util.enumeration.EnumTipoAccredito;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaNDP;

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

@Stateless(name="MovimentiAccreditoDaoService")
public class MovimentiAccreditoDaoImpl extends DaoImplJpaCmtJta<MovimentiAccredito> implements MovimentiAccreditoDao {
	
	private static final Logger LOGGER = LogManager.getLogger(MovimentiAccreditoDaoImpl.class);	
	
	private static final String QUERY_MOVIMENTI_ACCREDITO_BY_ID_RENDICONTAZIONE = "SELECT MA.* "+
			"FROM MOVIMENTI_ACCREDITO MA "+
			"WHERE MA.ID_RENDICONTAZIONE = :idRendicontazione";
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	
	@Override
	public List<MovimentiAccredito> getNDPNonRiconciliati(ContainerDTO containerDTO) {
		
		List<MovimentiAccredito> retList = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query = "select mov.* from MOVIMENTI_ACCREDITO mov where mov.flag_riconciliazione <> 1 "  ;

			FilterNDPNonRiconciliatiDTO filter = (FilterNDPNonRiconciliatiDTO) containerDTO.getInputDTO();
			
			List<String> inConditions = filter.getIBANAccredito();
			
			if (inConditions != null && !inConditions.isEmpty()){
				
					query = query +"and IBAN_ACCREDITO IN (";
					
					StringBuffer querySB = new StringBuffer(query);
					
					appendInCondition(querySB, inConditions,"iban"); 
					
					query = querySB.toString();
					
					addInParameters(params, inConditions, "iban");
				
				}
			
			String idRiconciliazione = (String) filter.getIdRiconciliazione();
            
            if (idRiconciliazione != null && !idRiconciliazione.isEmpty()){
            	
            	if(!idRiconciliazione.contains("*")) {
                
            		params.put("id_riconciliazione", idRiconciliazione);
                
            		query = query + " and ( mov.id_riconciliazione = :id_riconciliazione or mov.id_riconciliazione_orig = :id_riconciliazione)";
            	
            	} else {
            		
            		params.put("id_riconciliazione", idRiconciliazione.replaceAll("[*]", "%"));
                    
            		query = query + " and ( mov.id_riconciliazione like :id_riconciliazione or mov.id_riconciliazione_orig like :id_riconciliazione)";
            	}
            }
            
            String cro = (String) filter.getCodRifOperazione();
            
            if (cro != null && !cro.isEmpty()){
                
                params.put("cro", cro);
                
                query = query + " and mov.riferimento_banca = :cro ";
                
            }
            
            String trn = (String) filter.getTransRefNumber();
            
            if (trn != null && !trn.isEmpty()){
                
                params.put("trn", trn);
                
                query = query + " and mov.trn = :trn ";
                
            }
            
            EnumStatoRiconciliazione stato = (EnumStatoRiconciliazione) filter.getStatoRiconciliazione();
            
            if (stato != null){
                
                params.put("statoRic", stato.getShortValue());
                
                query = query + " and mov.flag_riconciliazione = :statoRic ";
                
            }
            
            EnumTipoAccredito tipoAccredito = (EnumTipoAccredito) filter.getTipoAccredito();
            
            if (tipoAccredito != null){
                
                params.put("tipoAccredito", tipoAccredito.getChiave());
                
                query = query + " and mov.tipo_accredito = :tipoAccredito ";
                
            }
            
            EnumTipoAnomaliaNDP anomalia = (EnumTipoAnomaliaNDP) filter.getTipoAnomalia();
            
            if (anomalia != null){
                
                params.put("codAnomalia", anomalia.getChiave());
                
                query = query + " and mov.cod_anomalia = :codAnomalia ";
                
            }
            
            BigDecimal importoDa = filter.getImportoDa();
            
            if (importoDa != null){
            	
            	 params.put("importoDa", importoDa);
                 
                 query += " and mov.importo >=:importoDa ";
            	
            }
            
            BigDecimal importoA = filter.getImportoA();
            
            if (importoA != null){
            	
            	 params.put("importoA", importoA);
                 
                 query +=  " and mov.importo <=:importoA ";
            	
            }
            
            Date dataDa = filter.getDataAccreditoDa();
            
            if (dataDa != null){
            	
            	 params.put("dataDa", dataDa);
                 
                 query += " and mov.data_valuta_beneficiario >=:dataDa ";
            	
            }
            
            Date dataA = filter.getDataAccreditoA();
            
            
            
            if (dataA != null){
            	
            	 dataA.setTime(dataA.getTime() + 24*60*60*1000 -1);
            	
            	 params.put("dataA", dataA);
                 
                 query +=  " and mov.data_valuta_beneficiario <=:dataA ";
            	
            }
            	
            query +=  " order by mov.ts_inserimento desc ";
            

			retList = paginateByQuery(MovimentiAccredito.class, query, pagingCriteria, pagingData, params);


		} catch (Exception e) {
			LOGGER.error("error on getNDPNonRiconciliati ", e);
			throw new DAORuntimeException(e);
		}
		
		return retList;
	}
	
	@Override
	public MovimentiAccredito retrieveMovimentoAccreditoById(Long id) {
		
		MovimentiAccredito retrieved = null;
		
		try {
			
			retrieved = loadById(MovimentiAccredito.class, id);
			
		} catch (Exception e) {
			
			LOGGER.error("error on  retrieveMovimentiAccreditoById, ID = " + id, e);
			throw new DAORuntimeException(e);
			
		}
		return retrieved;
	}
	
	@Override
	public MovimentiAccredito updateMovimentoAccredito(MovimentiAccredito mov) {
		
		try {
			
			mov = this.update(mov);
			
		} catch (Exception e) {
			
			LOGGER.error("error on updateMovimentoAccredito ", e);
			throw new DAORuntimeException(e);
			
		}
		return mov;
	}

	@Override
	public List<MovimentiAccredito> retrieveMovimentoAccreditoByIdRendicontazione(Long idRendicontazione) {
		
		List<MovimentiAccredito> retrieved = new ArrayList<MovimentiAccredito>();
		
		try {
			
			Query query = em.createNativeQuery(QUERY_MOVIMENTI_ACCREDITO_BY_ID_RENDICONTAZIONE, MovimentiAccredito.class);

	        query.setParameter("idRendicontazione", idRendicontazione);
			
			retrieved = (List<MovimentiAccredito>) query.getResultList();
			
		} catch (Exception e) {
			
			LOGGER.error("error on  retrieveMovimentoAccreditoByIdRendicontazione, IdRendicontazione = " + idRendicontazione, e);
			throw new DAORuntimeException(e);
			
		}
		return retrieved;
	}


}
