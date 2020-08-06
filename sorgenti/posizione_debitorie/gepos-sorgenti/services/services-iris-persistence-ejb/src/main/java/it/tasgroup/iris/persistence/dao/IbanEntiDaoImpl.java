package it.tasgroup.iris.persistence.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.is.fo.profilo.IbanEnti;
import it.nch.is.fo.tributi.JltentrId;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.IbanEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.persistence.dao.interfaces.IbanEntiDao;
import it.tasgroup.iris.persistence.dao.util.TributoEnteEntityBuilder;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "IbanEntiDaoService")
public class IbanEntiDaoImpl extends DaoImplJpaCmtJta<IbanEnti> implements IbanEntiDao {

	private static final Logger LOGGER = LogManager.getLogger(IbanEntiDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
    public List<IbanEnti> getListIbanByEnte(ContainerDTO inputDTO) {
		
        List<IbanEnti> retList = new ArrayList<IbanEnti>();
        
        LOGGER.debug("getListIbanByEnte - start");
        
        try {     
            	
        	Map<String, Object> parameters = new HashMap<String, Object>();
        	String idEnte = (String) inputDTO.getInputDTO();
        	
    		String query = "select ID, IBAN, ID_ENTE, VERSION, OP_INSERIMENTO, OP_AGGIORNAMENTO, DATA_CENSIMENTO, DATA_ATTIVAZIONE, "
    				+ " TS_INSERIMENTO, TS_AGGIORNAMENTO, ST_RIGA from IBAN_ENTI a where a.ID_ENTE = :idEnte and a.ST_RIGA= :valriga order by IBAN ASC";
    		
    		parameters.put("idEnte", idEnte);
    		parameters.put("valriga", "V");
    		
    	    PagingCriteria pagingCriteria = inputDTO.getPagingCriteria();

            PagingData pagingData = inputDTO.getPagingData();
    		
            retList = paginateByQuery(IbanEnti.class, query, pagingCriteria, pagingData, parameters);
             
            
        } catch (Exception e) {
            LOGGER.error("error on getListIbanByEnte ", e);
            throw new DAORuntimeException(e);
        }   
        
        if (retList != null) 
        	LOGGER.debug("getListIbanByEnte - n. risultati: "+retList.size());
        else 
        	LOGGER.debug("getListIbanByEnte - nessun risultato");
        
        return retList;
    }
	
	@Override
	public void updateIbanEnte(IbanEnteDTO ibanDTO) {
		
				
		try {
			
			IbanEnti oldIban= getById(IbanEnti.class, ibanDTO.getId());
			

			oldIban.setDataAttivazione(ibanDTO.getDataAttivazione());
			oldIban.setDataCensimento(ibanDTO.getDataCensimento());
			oldIban.setOpAggiornamento(ibanDTO.getOpAggiornamento());
			oldIban.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			oldIban.setStRiga(ibanDTO.getStRiga());
			
			this.update(oldIban);
			
		} catch (Exception e) {
			
			LOGGER.error("error on updateIbanEnte ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		
	}
	
	@Override
	public int countIbanEnti(String idEnte, String iban) {
		String countQuery = "select count(*) from IBAN_ENTI where IBAN=:iban and ID_ENTE=:idEnte and ST_RIGA=:st_riga";
		
		Query q = em.createNativeQuery(countQuery);

		q.setParameter("iban", iban);
		q.setParameter("idEnte", idEnte);
		q.setParameter("st_riga", "V");
		
		Number result = (Number)q.getSingleResult();
		int count = result.intValue();
		
		return count;
	}
	
	@Override
	public void insertIbanEnte(IbanEnti ibanEnti) {
		
				
		try {
			String query = "select a.ID, a.IBAN, a.ID_ENTE, a.DATA_CENSIMENTO, a.DATA_ATTIVAZIONE, a.ST_RIGA, a.OP_INSERIMENTO, a.TS_INSERIMENTO, a.OP_AGGIORNAMENTO,"
					+ " a.TS_AGGIORNAMENTO, a.VERSION "
					+ "  from IBAN_ENTI a where a.ID_ENTE = :idEnte and a.IBAN= :iban ";
    		
			Query q = em.createNativeQuery(query, IbanEnti.class);
			
			q.setParameter("idEnte", ibanEnti.getIdEnte());
			q.setParameter("iban", ibanEnti.getIban());
			
		
			 
			IbanEnti retList = null;
			try {
				retList = (IbanEnti)q.getSingleResult();
			}catch (NoResultException e) {
				this.save(ibanEnti);
			}
			if (retList !=null){
				// probabilmente è stata fatta una cancellazione logica
				retList.setStRiga("V");
				this.update(retList);
				
			}
		} catch (Exception e) {
			
			LOGGER.error("error on insertIbanEnte ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		
	}
	
}
