
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.FilterDettaglioNDPDTO;
import it.tasgroup.iris.persistence.dao.interfaces.RendicontazioniDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

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

@Stateless(name="RendicontazioniDaoService")
public class RendicontazioniDaoImpl extends DaoImplJpaCmtJta<Rendicontazioni> implements RendicontazioniDao {
	
	private static final Logger LOGGER = LogManager.getLogger(RendicontazioniDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	

	@Override
	public Rendicontazioni retrieveRendicontazioniById(Long id) {
		Rendicontazioni retrieved = null;
		try {
			retrieved = loadById(Rendicontazioni.class,id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveRendicontazioniById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}
	
	@Override
	public List<Rendicontazioni> retrieveRendicontazioniRiversamento(ContainerDTO containerDTO) {
		
		List<Rendicontazioni> retList = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select r.* FROM RENDICONTAZIONI r, CASELLARIO_INFO c where r.id_casellario_info = c.id "+
			" and not r.id_flusso in (select id_riconciliazione from MOVIMENTI_ACCREDITO mc where mc.id_riconciliazione is not null and mc.tipo_accredito = 'C')";

			FilterDettaglioNDPDTO filter = (FilterDettaglioNDPDTO) containerDTO.getInputDTO();

			
			String idRiconciliazione = (String) filter.getIuv();
            
            if (idRiconciliazione != null && !idRiconciliazione.isEmpty()){
            	
            	if(!idRiconciliazione.contains("*")) {
                
            		params.put("id_riconciliazione", idRiconciliazione);
                
            		query = query + " and ( r.id_flusso = :id_riconciliazione)";
            	
            	} else {
            		params.put("id_riconciliazione", idRiconciliazione.replaceAll("[*]", "%"));
                    
            		query = query + " and ( r.id_flusso like :id_riconciliazione)";
            	}
            }
			
			String trn = (String) filter.getTransRefNumber();
            
            if (trn != null && !trn.isEmpty()){
            	
            	if(!trn.contains("*")) {
                    
            		params.put("trn", trn);
                
            		query = query + " and ( r.id_regolamento = :trn)";
            	
            	} else {
            		
            		params.put("trn", trn.replaceAll("[*]", "%"));
                    
            		query = query + " and ( r.id_regolamento like :trn)";
            	}
                
            }
            
            String idPSP = (String) filter.getIdPSP();
            
            if (idPSP != null && !idPSP.isEmpty()){
                
                params.put("idPSP", idPSP);
                
                query = query + " and c.mittente = :idPSP ";
                
            }
            
            String cfContoTecnico = (String) filter.getCfContoTecnico();
            
            if (cfContoTecnico != null && !cfContoTecnico.isEmpty()){
                
                params.put("cfContoTecnico", cfContoTecnico);
                
                query = query + " and c.ricevente = :cfContoTecnico ";
                
            }
            
            BigDecimal importoDa = filter.getImportoDa();
            
            if (importoDa != null){
            	
            	 params.put("importoDa", importoDa);
                 
                 query += " and r.importo >=:importoDa ";
            	
            }
            
            BigDecimal importoA = filter.getImportoA();
            
            if (importoA != null){
            	
            	 params.put("importoA", importoA);
                 
                 query +=  " and r.importo <=:importoA ";
            	
            }
            
            Date dataDa = filter.getDataDa();
            
            if (dataDa != null){
            	
            	 params.put("dataDa", dataDa);
                 
                 query += " and r.data_regolamento >=:dataDa ";
            	
            }
            
            Date dataA = filter.getDataA();
            
            if (dataA != null) {
            	
            	 dataA.setTime(dataA.getTime() + 24*60*60*1000 - 1);
            	
            	 params.put("dataA", dataA);
                 
                 query +=  " and r.data_regolamento <=:dataA ";
            	
            }
            	
            query +=  " order by r.ts_inserimento desc ";
            

			retList = paginateByQuery(Rendicontazioni.class, query, pagingCriteria, pagingData, params);


		} catch (Exception e) {
			LOGGER.error("error on getNDPNonRiconciliati ", e);
			throw new DAORuntimeException(e);
		}
		
		return retList;
	}
}
