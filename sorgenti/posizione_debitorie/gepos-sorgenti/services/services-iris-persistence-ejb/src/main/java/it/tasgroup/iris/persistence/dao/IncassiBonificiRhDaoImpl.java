
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.IncassiBonificiRh;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.FilterBFLNonRiconciliatiDTO;
import it.tasgroup.iris.persistence.dao.interfaces.IncassiBonificiRhDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumFlagRiconciliazioneEsiti;
import it.tasgroup.services.util.enumeration.EnumStatoChiusuraRiversamento;
import it.tasgroup.services.util.enumeration.EnumStatoRiversamento;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaIncasso;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="IncassiBonificiRhDaoService")
public class IncassiBonificiRhDaoImpl extends DaoImplJpaCmtJta<IncassiBonificiRh> implements IncassiBonificiRhDao {
	
	private static final Logger LOGGER = LogManager.getLogger(IncassiBonificiRhDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<IncassiBonificiRh> listIncassiBonificiRhByIdRendicontazione(ContainerDTO containerDTO) {

		List<IncassiBonificiRh> retList = null;
		
		Long id = (Long) containerDTO.getInputDTOList().get(0);
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", id);

			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select esiti.* from INCASSI_BONIFICI_RH esiti " +
							"where ID_RENDICONTAZIONE=:idRendicontazione ";

                        String esitoanomalo = (String) containerDTO.getInputDTOList().get(2);
                        if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                            List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                            params.put("flags", flags);
                            query = query + " and FLAG_RICONCILIAZIONE in (:flags) ";
                        }

			retList = paginateByQuery(IncassiBonificiRh.class, query, pagingCriteria, pagingData, params);

		} catch (Exception e) {
			LOGGER.error("error on listIncassiBonificiRhByIdRendicontazione ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}

	@Override
	public IncassiBonificiRh getIncassiBonificiRhById(Long id) {
		
		IncassiBonificiRh esito = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			esito = (IncassiBonificiRh)uniqueResultByQuery("getIncassiBonificiRhById",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getIncassiBonificiRhById ", e);
			throw new DAORuntimeException(e);
		}
		
		return esito;
	}
	
	@Override
	public List<IncassiBonificiRh> getBFLNonRiconciliati(ContainerDTO containerDTO) {
		
		List<IncassiBonificiRh> retList = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select incassi.* from INCASSI_BONIFICI_RH incassi "+
				"left outer join RIVERSAMENTI riv "+
				"on riv.ID_INCASSI_BONIFICI_RH = incassi.ID "+
				"where ";
			
			String queryFlagRiconciliazione= " ";
			
			String queryFlagRincoliazione2="incassi.flag_riconciliazione=2 ";
			String queryFlagRincoliazione3="incassi.flag_riconciliazione=3 ";
			String queryFlagRincoliazione2o3="(incassi.flag_riconciliazione=2 or incassi.flag_riconciliazione=3) ";
			
		
			boolean flagRiconciliazione2=false;
			boolean flagRiconciliazione3=false;
			boolean flagRiconciliazione2o3=false;
			
			String queryFilters="";
			
		
			FilterBFLNonRiconciliatiDTO filter = (FilterBFLNonRiconciliatiDTO) containerDTO.getInputDTO();
			
//filtri che utlizzano flag riconciliazione 2
			    
	            BigDecimal importoDa = filter.getImportoDa();
	            
	            if (importoDa != null){
	            	
	            	 params.put("importoDa", importoDa);
	                 
	            	 queryFilters += " and incassi.importo >=:importoDa ";
	            	 flagRiconciliazione2=true;
	            	
	            }
	            
	            BigDecimal importoA = filter.getImportoA();
	            
	            if (importoA != null){
	            	
	            	 params.put("importoA", importoA);
	                 
	            	 queryFilters +=  " and incassi.importo <=:importoA ";
	            	 flagRiconciliazione2=true;
	            	
	            }
	            
	            Date dataDa = filter.getDataAccreditoDa();
	            
	            if (dataDa != null){
	            	
	            	 params.put("dataDa", dataDa);
	                 
	            	 queryFilters += " and incassi.data_valuta >=:dataDa ";
	            	 flagRiconciliazione2=true;
	            	
	            }
	            
	            Date dataA = filter.getDataAccreditoA();
	            
	            if (dataA != null){
	            	
	            	 params.put("dataA", dataA);
	                 
	            	 queryFilters +=  " and incassi.data_valuta <=:dataA ";
	            	 flagRiconciliazione2=true;
	            	
	            }
	            
	            EnumTipoAnomaliaIncasso anomalia = (EnumTipoAnomaliaIncasso) filter.getTipoAnomalia();
	            
	            if (anomalia != null){
	                
	                params.put("anomalia", anomalia.name());
	                
	                queryFilters = queryFilters + " and incassi.anomalia = :anomalia ";
	                flagRiconciliazione2=true;
	                
	            }
	            		
//filtri che utlizzano flag riconciliazione 3
			
            EnumStatoChiusuraRiversamento chiusura = (EnumStatoChiusuraRiversamento) filter.getStatoChiusura();
            
            if (chiusura != null){
                
                params.put("chiusura", chiusura.getValue());
                
                queryFilters = queryFilters + " and riv.flag_chiusura = :chiusura ";
                flagRiconciliazione3=true;
                
            }
            
           
            String IBAN = (String) filter.getIBAN();
            
            
            if (!StringUtils.isEmpty(IBAN)){
                
                params.put("IBAN", IBAN);
                
                queryFilters = queryFilters + " and riv.IBAN = :IBAN ";
                flagRiconciliazione3=true;
                
            }
            
            String codTribEnte = (String) filter.getCodTribEnte();
            
            if (!StringUtils.isEmpty(codTribEnte)){
                
                params.put("codTribEnte", codTribEnte);
                
                queryFilters = queryFilters + " and riv.cd_trb_ente = :codTribEnte ";
                flagRiconciliazione3=true;
                
            }
            
//filtri che utlizzano flag riconciliazione 2 o 3
            String idRiconciliazione = (String) filter.getIdRiconciliazione();
            
            if (idRiconciliazione != null && !idRiconciliazione.isEmpty()){
            	
            	if(!idRiconciliazione.contains("*")) {
                
            		params.put("idRiconciliazione", idRiconciliazione);
                
            		queryFilters = queryFilters + " and ( incassi.id_riconciliazione = :idRiconciliazione or incassi.id_riconciliazione_orig = :idRiconciliazione)";
            	
            	} else {
            		params.put("idRiconciliazione", idRiconciliazione.replaceAll("[*]", "%"));
                    
            		queryFilters = queryFilters + " and ( incassi.id_riconciliazione like :idRiconciliazione or incassi.id_riconciliazione_orig like :idRiconciliazione)";
            	}
            	flagRiconciliazione2o3=true;
            }
            
            String cro = (String) filter.getCodRifOperazione();
            
            if (cro != null && !cro.isEmpty()){
                
                params.put("cro", cro);
                
                queryFilters = queryFilters + " and incassi.riferimento_banca = :cro ";
                flagRiconciliazione2o3=true;
                
            }
            
            String trn = (String) filter.getTransRefNumber();
            
            if (trn != null && !trn.isEmpty()){
                
                params.put("trn", trn);
                
                queryFilters = queryFilters + " and incassi.trn = :trn ";
                flagRiconciliazione2o3=true;
                
            }

            String ibanAccredito = (String) filter.getIbanAccredito();
            if (ibanAccredito != null && !ibanAccredito.isEmpty()){
                params.put("ibanAccredito", ibanAccredito);
                queryFilters = queryFilters + " and incassi.iban_accredito = :ibanAccredito ";
            }
            
            
//filtro misto, messo appositamente per ultimo
            EnumStatoRiversamento stato = (EnumStatoRiversamento) filter.getStatoRiversamento();
            
            if (stato != null){
            	
            	if (EnumStatoRiversamento.NON_RIVERSATO.equals(stato)) {
            			 queryFilters = queryFilters + " and riv.stato is null ";
            	} else {
                
            		params.put("statoRiv", stato.name());
                
            		queryFilters = queryFilters + " and riv.stato = :statoRiv ";
            		if (EnumStatoRiversamento.GIA_RIVERSATO.equals(stato))
            			flagRiconciliazione2=true;
            		else
            			flagRiconciliazione3=true;
            	}
  
            } else {
            		flagRiconciliazione2o3=true;
            }
            
            queryFilters +=  " order by incassi.ts_inserimento desc ";
            
            if (flagRiconciliazione2)
            	queryFlagRiconciliazione=queryFlagRincoliazione2;
                  
            if (flagRiconciliazione3)
            	queryFlagRiconciliazione=queryFlagRincoliazione3;
            
            if (flagRiconciliazione2o3)
            	queryFlagRiconciliazione=queryFlagRincoliazione2o3;
            
            query=query+queryFlagRiconciliazione+queryFilters;
            		
			retList = paginateByQuery(IncassiBonificiRh.class, query, pagingCriteria, pagingData, params);


		} catch (Exception e) {
			LOGGER.error("error on getBFLNonRiconciliati ", e);
			throw new DAORuntimeException(e);
		}
		
		return retList;
	}
	
	@Override
	public IncassiBonificiRh updateIncassi(IncassiBonificiRh inc) {
		try {
			inc = this.update(inc);
		} catch (Exception e) {
			LOGGER.error("error on updateIncassi ", e);
			throw new DAORuntimeException(e);
		}
		return inc;
	}

	@Override
	public List<String> listIbanAccredito() {
		try {
			return em.createQuery("select distinct(ibanAccredito) from IncassiBonificiRh where ibanAccredito is not NULL order by ibanAccredito", String.class).getResultList();

		} catch (Exception e) {
			LOGGER.error("error on listIbanAccredito ", e);
			throw new DAORuntimeException(e);
		}
	}



}
