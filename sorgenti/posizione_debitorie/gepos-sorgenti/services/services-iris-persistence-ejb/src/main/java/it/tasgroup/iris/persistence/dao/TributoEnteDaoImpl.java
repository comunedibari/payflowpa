
package it.tasgroup.iris.persistence.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.tributi.CfgTributoEntePlugin;
import it.nch.is.fo.tributi.JltentrId;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.persistence.dao.interfaces.TributoEnteDao;
import it.tasgroup.iris.persistence.dao.util.TributoEnteEntityBuilder;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name="TributoEnteDaoService")
public class TributoEnteDaoImpl extends DaoImplJpaCmtJta<TributoEnte> implements TributoEnteDao {
	
	private static final Logger LOGGER = LogManager.getLogger(TributoEnteDaoImpl.class);	
	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		
		this.em=em;
		
	}

	
	@Override
	public List<TributoEnte> listTributiAbleToAnonymousPaymentByIdEnte(String idEnte){
		try {
			// String querysql = "Select te.* from TRIBUTIENTI te " +
			// "where te.ID_ENTE = :idEnte " +
			// " and te.FL_INIZIATIVA = 'Y' " +
			// " and te.FL_PREDETERM = 'N' " +
			// " and te.STATO = 'A' order by te.DE_TRB asc";
			
			String qlString = "from TributoEnte te where te.idEnte = :idEnte and te.flIniziativa = 'Y' and te.stato = 'A' order by te.deTrb asc";
			TypedQuery<TributoEnte> typedQuery = em.createQuery(qlString, TributoEnte.class);
			typedQuery.setParameter("idEnte", idEnte);
			
			return typedQuery.getResultList();
			
		} catch (Exception e) {
			LOGGER.error("error on listTributiAbleToAnonymousPaymentByIdEnte ", e);
			throw new DAORuntimeException(e);
		}
	}
	
	@Override
	public List<TributoEnte> listTributiAbleToAnonymousPayment(){
		try {
			// String querysql = "Select te.* from TRIBUTIENTI te " +
			// "where te.FL_INIZIATIVA = 'Y' " +
			// " and te.STATO = 'A' order by te.DE_TRB asc";

			String qlString = "from TributoEnte te where te.flIniziativa = 'Y' and te.stato = 'A' order by te.deTrb asc";
			TypedQuery<TributoEnte> typedQuery = em.createQuery(qlString, TributoEnte.class);
			
			return typedQuery.getResultList();

		} catch (Exception e) {
			LOGGER.error("error on listTributiAbleToAnonymousPaymentByIdEnte ", e);
			throw new DAORuntimeException(e);
		}
	}
	
	@Override
    public List<TributoEnte> getIBANTributiEnteNDP(ContainerDTO containerDTO) {
		
        List<TributoEnte> retList = new ArrayList<TributoEnte>();
        
        LOGGER.debug("getIBANTributiEnteNDP - start");
        
        try {

            String querysql = getQueryIBANTributiNDP();

            String idEnte = null;
            
            if(containerDTO.getInputDTOList().size()>0)
            	
            	idEnte = (String) containerDTO.getInputDTO();
            
            if (!StringUtils.isEmpty(idEnte))
                
                querysql = querysql + " and t.ID_ENTE = :idEnte ";
                
            	
            Query query= em.createNativeQuery(querysql);
            
            if (!StringUtils.isEmpty(idEnte))
            	
            	query.setParameter("idEnte", idEnte);
            	
            
            List<Object[]> couples = query.getResultList();
			
			for (Object[] obj : couples) {
				
            	// NB: riempio soltanto i campi necessari per la visualizzazione in base a
            	// quanto recuperato nella query (occhio alle posizioni dei campi)
            	TributoEnte t = new  TributoEnte();
            	t.setIBAN((String)obj[0]);
            	t.setDeTrb(((String)obj[1]));
            	Enti ente = new Enti();
            	ente.setIdEnte(idEnte);
            	t.setEntiobj(ente);
            	retList.add(t);
            	
            }

        } catch (Exception e) {
            LOGGER.error("error on getIBANTributiEnteNDP ", e);
            throw new DAORuntimeException(e);
        }   
        
        if (retList != null) 
        	LOGGER.debug("getIBANTributiEnteNDP - n. risultati: "+retList.size());
        else 
        	LOGGER.debug("getIBANTributiEnteNDP - nessun risultato");
        
        return retList;
    }
	
	@Override
    public List<TributoEnte> getTributiEnteByIBAN(String iban) {
		
        List<TributoEnte> retList = new ArrayList<TributoEnte>();
        
        LOGGER.debug("getTributiEnteByIBAN - start");
        
        try {     
            	
            Query query= em.createNamedQuery("tributiEntiByIBAN", TributoEnte.class);
            
            query.setParameter("iban", iban);       	

            retList = query.getResultList();
            
        } catch (Exception e) {
            LOGGER.error("error on getTributiEnteByIBAN ", e);
            throw new DAORuntimeException(e);
        }   
        
        if (retList != null) 
        	LOGGER.debug("getTributiEnteByIBAN - n. risultati: "+retList.size());
        else 
        	LOGGER.debug("getTributiEnteByIBAN - nessun risultato");
        
        return retList;
    }
	
	@Override
    public TributoEnte getTributiEntiByKey(String idEnte,String idTributo) {
		
        TributoEnte te = null;
        
        LOGGER.debug("getTributiEntiByKey - start");
        
        try {     
            	
            JltentrId pk = new JltentrId();
            pk.setIdEntePk(idEnte);
            pk.setCdTrbEntePk(idTributo);
            te = getById(TributoEnte.class, pk);
            
        } catch (Exception e) {
        	
            LOGGER.error("error on getTributiEntiByKey ", e);
            throw new DAORuntimeException(e);
        }   
        LOGGER.debug("getTributiEntiByKey - end");
        
        return te;
    }
	@Override
    public List<TributoEnte> getTributiEnte(ContainerDTO inputDTO) {
		
        List<TributoEnte> retList = null;
        
        LOGGER.debug("getTributiEnte - start");
        
        String idEnte = (String) inputDTO.getInputDTO();

        try {

            Map<String, Object> parameters = new HashMap<String, Object>();
            // #1049 Export Pagamento
            
            String query = getQueryTributiEnte(idEnte, parameters);

            PagingCriteria pagingCriteria = inputDTO.getPagingCriteria();

            PagingData pagingData = inputDTO.getPagingData();
            
            System.out.println("------------------------------------------------");
            System.out.println("query: " + query);
//          System.out.println("query: " + filteringQuery + whereClause + orderClause);
            System.out.println("------------------------------------------------");

            retList = paginateByQuery(TributoEnte.class, query, pagingCriteria, pagingData, parameters);


        } catch (Exception e) {
            LOGGER.error("error on getTributiEnte ", e);
            throw new DAORuntimeException(e);
        }   
        
        if (retList != null) 
        	LOGGER.debug("getTributiEnte - n. risultati: "+retList.size());
        else 
        	LOGGER.debug("getTributiEnte - nessun risultato");
        
        return moveBLFinFirstPosition(retList);
    }

	private List<TributoEnte> moveBLFinFirstPosition(List<TributoEnte> inputList) {
		Iterator<TributoEnte> iter = inputList.iterator();
		List<TributoEnte> helpList = new ArrayList<TributoEnte>();
		TributoEnte tribFirstPos =null;
		while (iter.hasNext()) {
			TributoEnte t = iter.next();
			if (!t.getCdTrbEnte().equals("RECUPERO_BFL")) {
				helpList.add(t);
			}
			else 
				tribFirstPos = t;
			
		}
		List<TributoEnte> outList = new ArrayList<TributoEnte>();
		if (tribFirstPos!=null) {
		   outList.add(tribFirstPos);
		}
		Iterator<TributoEnte> iter1 = helpList.iterator();
		while (iter1.hasNext())
			outList.add(iter1.next());
		
		
		return outList;
		
	}
	private String getQueryTributiEnte(String idEnte, Map<String, Object> parameters) {
		
		
		String query = "Select t.* from JLTENTR t " +
						"where t.STATO = 'A' "  +
						"and t.ID_ENTE = :ID_ENTE order by t.DE_TRB";
		
		parameters.put("ID_ENTE", idEnte);
		
		return query;
	}
	
	private String getQueryIBANTributiRiversamenti(String idEnte, Map<String, Object> parameters) {
		
		
		String query = "SELECT DISTINCT t.DE_TRB, t.CD_TRB_ENTE, t.ID_ENTE, t.ID_TRIBUTO, t.ID_SYSTEM, "+
					" t.FL_INIZIATIVA, t.FL_PREDETERM, t.FL_ADD_RT_VERIFICA_PAG, t.SOGG_ESCLUSI, t.STATO, t.FL_NASCOSTO_FE, t.FL_NOTIFICA_PAGAMENTO, " +
				    " t.INFO_TRIBUTO, t.ISTRUZIONI_PAGAMENTO, t.FL_RICEVUTA_ANONIMO, t.URL_UPD_SERVICE, t.URL_INFO_SERVICE , "+
					" t.OP_AGGIORNAMENTO, t.OP_INSERIMENTO, t.TS_AGGIORNAMENTO, t.TS_INSERIMENTO, t.PR_VERSIONE, r.IBAN "+
					" FROM TRIBUTIENTI t, RIVERSAMENTI r "+
					" WHERE t.ID_ENTE = :ID_ENTE "+
					" AND r.CD_TRB_ENTE = t.CD_TRB_ENTE ";
		
		parameters.put("ID_ENTE", idEnte);
		
		return query;
	}
	
	private String getQueryIBANTributiNDP() {
		
		
		String query = "SELECT DISTINCT t.IBAN, t.DE_TRB FROM TRIBUTIENTI t WHERE t.STATO = 'A' AND ID_ENTE = '00000000000000000000' ORDER BY t.IBAN ASC";
		
		return query;
	}
	
	
	@Override
    public List<TributoEnte> listaIBANRiversamenti(ContainerDTO inputDTO) {
		
        List<TributoEnte> retList = new ArrayList<TributoEnte>();
        
        LOGGER.debug("listaIBANRiversamenti - start");
        
        String idEnte = (String) inputDTO.getInputDTO();
        
        List< ? extends Object> l = null;

        try {

            Map<String, Object> parameters = new HashMap<String, Object>();
            // #1049 Export Pagamento
            
            String query = getQueryIBANTributiRiversamenti(idEnte, parameters);

            PagingCriteria pagingCriteria = inputDTO.getPagingCriteria();

            PagingData pagingData = inputDTO.getPagingData();
            
            System.out.println("------------------------------------------------");
            System.out.println("query: " + query);
//          System.out.println("query: " + filteringQuery + whereClause + orderClause);
            System.out.println("------------------------------------------------");

            //retList = paginateByQuery(TributoEnte.class, query, pagingCriteria, pagingData, parameters);
            l = paginateByQuery(query, pagingCriteria, pagingData, parameters);
            Iterator<? extends Object> iter = l.iterator();
            while (iter.hasNext()) {
            	// NB: riempio soltanto i campi necessari per la visualizzazione in base a
            	// quanto recuperato nella query (occhio alle posizioni dei campi)
            	Object[] obj= (Object[])iter.next();
            	TributoEnte t = new  TributoEnte();
            	t.setCdTrbEnte((String)obj[1]); 
            	t.setDeTrb(((String)obj[0]));
            	t.setIBAN((String)obj[22]);
            	t.setEntiobj(new Enti());
            	retList.add(t);
            	
            }
        
        } catch (Exception e) {
        	
            LOGGER.error("error on listaIBANRiversamenti ", e);
            
            throw new DAORuntimeException(e);
            
        }   
               
        LOGGER.debug("listaIBANRiversamenti - n. risultati: "+retList.size());
            
        return retList;
    }
	
	@Override
	public TributoEnte updateTributo(TributoEnteDTO tribDTO) {
		
		TributoEnte trib = null;
				
		try {
			
			JltentrId pk = new JltentrId(tribDTO.getEnte().getId(), tribDTO.getCdTrbEnte());
			
			TributoEnte oldTrib = getById(TributoEnte.class, pk);
			
			TributoEnteEntityBuilder.fillTributoEntity(tribDTO, oldTrib);
			
			if (tribDTO.getCdPlugin() != null && !tribDTO.getCdPlugin().isEmpty()) {
				
				CfgTributoEntePlugin cfgPlugin = oldTrib.getCfgTributoEntePlugin();
				
				if (cfgPlugin == null) {
					cfgPlugin = new CfgTributoEntePlugin();
					oldTrib.setCfgTributoEntePlugin(cfgPlugin);

					cfgPlugin.setIdEnte(oldTrib.getIdEnte());
					cfgPlugin.setCdTrbEnte(oldTrib.getCdTrbEnte());
					cfgPlugin.setOpInserimento(tribDTO.getOpAggiornamento());
					cfgPlugin.setTsInserimento(new Date());
					
				} else {
					cfgPlugin.setOpAggiornamento(tribDTO.getOpAggiornamento());
					cfgPlugin.setTsAggiornamento(new Date());
					
				}
				cfgPlugin.setCdPlugin(tribDTO.getCdPlugin());
				cfgPlugin.setDati(tribDTO.getDati());

			} else {
				oldTrib.setCfgTributoEntePlugin(null);
			}
			
			trib = this.update(oldTrib);
			
		} catch (Exception e) {
			
			LOGGER.error("error on updateTributo ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return trib;
	}
	
	@Override
	public TributoEnte createTributo(TributoEnteDTO tribDTO) {
		
		TributoEnte trib = null;
		
		try {
			
			trib = TributoEnteEntityBuilder.fillTributoEntity(tribDTO, new TributoEnte());
			
			if (tribDTO.getCdPlugin() != null && !tribDTO.getCdPlugin().isEmpty()) {
				
				CfgTributoEntePlugin cfgPlugin = new CfgTributoEntePlugin();
				cfgPlugin.setIdEnte(trib.getIdEnte());
				cfgPlugin.setCdTrbEnte(trib.getCdTrbEnte());
				cfgPlugin.setOpInserimento(tribDTO.getOpInserimento());
				cfgPlugin.setTsInserimento(new Date());
				cfgPlugin.setCdPlugin(tribDTO.getCdPlugin());
				cfgPlugin.setDati(tribDTO.getDati());
				
				trib.setCfgTributoEntePlugin(cfgPlugin);
			}

			trib = this.create(trib);
			
			
		} catch (Exception e) {
			
			LOGGER.error("error on createTributo ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return trib;
	}
	
	private String buildQueryListTributoEnte(TributoEnteDTO filterDTO, Map<String, Object> parameters){
		
		String selectFromWhere = "Select trb.* from JLTENTR trb "
				+ "where 1 = 1 ";

		StringBuffer whereConditions = new StringBuffer();
		
		if (!StringUtils.isEmpty(filterDTO.getIdTributo())) {
			
			whereConditions.append(" AND trb.ID_TRIBUTO =:IDTRIBUTO ");
			parameters.put("IDTRIBUTO", filterDTO.getIdTributo());
			
		}
		
		if (!StringUtils.isEmpty(filterDTO.getCdTrbEnte())) {
			
			String cdTrbEnte = filterDTO.getCdTrbEnte().replace("*", "%");
			whereConditions.append(" AND upper(trb.CD_TRB_ENTE) like :CDTRBENTE ");
			parameters.put("CDTRBENTE", cdTrbEnte.toUpperCase());
			
		}
		
		if (!StringUtils.isEmpty(filterDTO.getIdEnte())) {
					
			whereConditions.append(" AND trb.ID_ENTE =:IDENTE ");
			parameters.put("IDENTE", filterDTO.getIdEnte());
			
		}
		
		if (!StringUtils.isEmpty(filterDTO.getDeTrb())) {
			String deTrb = filterDTO.getDeTrb().replace("*", "%");
			whereConditions.append(" AND upper(trb.DE_TRB) LIKE :DETRB ");
			parameters.put("DETRB", deTrb.toUpperCase());
			
		}
		
		if (!StringUtils.isEmpty(filterDTO.getFlIniziativa())) {
			
			whereConditions.append(" AND trb.FL_INIZIATIVA =:FLINIZIATIVA ");
			parameters.put("FLINIZIATIVA", filterDTO.getFlIniziativa());
			
		}
		
		if (!StringUtils.isEmpty(filterDTO.getStato())) {
			
			whereConditions.append(" AND trb.STATO =:STATO ");
			parameters.put("STATO", filterDTO.getStato());
			
		}	
		
		if (!StringUtils.isEmpty(filterDTO.getSIL())) {
			
			whereConditions.append(" AND trb.ID_SYSTEM =:ID_SYSTEM ");
			parameters.put("ID_SYSTEM", filterDTO.getSIL());
			
		}	
		
		whereConditions.append(" ORDER BY DE_TRB ASC");
		
		return selectFromWhere + whereConditions;
		
	}
	
	@Override
	public List<TributoEnte> listTributoEnteByFilterParams(ContainerDTO dtoIn, boolean checkNPendenze) {
		
		try {			
			
			TributoEnteDTO filterDTO = (TributoEnteDTO) dtoIn.getInputDTO();
			
			Map<String, Object> parameters = new HashMap<String, Object>();
            
            String queryString = buildQueryListTributoEnte(filterDTO, parameters);

            PagingCriteria pagingCriteria = dtoIn.getPagingCriteria();

            PagingData pagingData = dtoIn.getPagingData();

			List<TributoEnte> retList = null;
			
			if (pagingCriteria != null) {
				
				retList = paginateByQuery(TributoEnte.class, queryString, pagingCriteria, pagingData, parameters);
				
			} else {
				Query query = em.createNativeQuery(queryString, TributoEnte.class);
				for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				    String key = entry.getKey();
				    Object value = entry.getValue();
				    query.setParameter(key, value);
				}
				retList = query.getResultList();
			}

			return retList;

		} catch (Exception e) {
			
			LOGGER.error("error on listTributoEnteByFilterParams ", e);
			
			throw new DAORuntimeException(e);
		}

	}
	
	@Override
	public List<TributoEnte> getTributiBySysIDEnte(SistemaEnteDTO dtoIn) {
		
			List<TributoEnte> retList = new ArrayList<TributoEnte>();
	        
	        LOGGER.debug("getTributibySysIDEnte - start");
	        
	        try {     
	            	
	            Query query= em.createNamedQuery("tributiEntiByIdSysIdEnte", TributoEnte.class);
	            
	            query.setParameter("sistema", dtoIn.getIdSystem());     
	            
	            query.setParameter("ente", dtoIn.getIdEnte());

	            retList = query.getResultList();
	            
	        } catch (Exception e) {
	        	
	            LOGGER.error("error on getTributibySysIDEnte ", e);
	            
	            throw new DAORuntimeException(e);
	        }   
	              
		return retList;
	}	
	
	@Override
	public void changeStatusTributoEnteList(List<TributoEnteDTO> inputList) {

		try {
			
			for (TributoEnteDTO tributo : inputList) {
				
				TributoEnte trib = getTributiEntiByKey(tributo.getIdEnte(), tributo.getCdTrbEnte());

				trib.setStato(tributo.getStato());

				trib = update(trib);
			
			}

		} catch (Exception e) {
			
			LOGGER.error(" Error on changeStatusTributoEnteList", e);
			
			throw new DAORuntimeException(e);
		}
		
	}
	
	@Override
	public String getNextCategoria() {
        
        String hqlQuery = "select max(t.idTributo) from CategoriaTributo t where t.idTributo != 'CategoriaDefault' ";
        String nextCategoria = "";
        String categoria = null;
        try {
            Query query= em.createQuery(hqlQuery);           
            categoria = (String) query.getSingleResult();
            
        } catch (NoResultException t) {
              categoria="Categoria000";                
        }
        
        if  (categoria == null || categoria.isEmpty())
        	categoria="Categoria000";  
        
        int categoriaInt = Integer.parseInt(categoria.substring(9));
        categoriaInt++;
        nextCategoria = String.format("%03d" , categoriaInt);
        return "Categoria" + nextCategoria;
  }


    
    @Override
    public List<TributoEnte> listaTributiEnti() {
    	
        List<TributoEnte> l = null;        
        
        try {
        	
            l = (List<TributoEnte>) listByQuery("allTributiEnti");
            
        } catch (Exception e) {
        	
        	LOGGER.error("error on listaTributiEnti ", e);
        	
        	throw new DAORuntimeException();
        }
        
        return l;
    }
    
    @Override
	public void deleteTributoEnte(JltentrId key) {
		
		try {
			
			this.deleteByKey(TributoEnte.class,key);
			
		} catch(Exception e){
			
			LOGGER.error("error on deleteTributoEnte, " + key, e);
			
			throw new DAORuntimeException(e);
			
		}
		
	}

	@Override
	public void deleteTributiEnte(List<String> selectedIds) {
		try {
			
			for (String id : selectedIds){
				
				String[] splitId = id.split("[,]");
				
				JltentrId pk = new JltentrId();
				
				pk.setIdEntePk(splitId[0]);
				
				pk.setCdTrbEntePk(splitId[1]);
				
				deleteTributoEnte(pk);
			}
			
		} catch(Exception e){
			
			LOGGER.error("error on deleteTributiEnte, " + selectedIds, e);
			
			throw new DAORuntimeException(e);
			
		}
		
	}

	@Override
	public List<TributoEnte> getTributiEntiIbanCCpNull() {
       
		List<TributoEnte> l = null;        
        
        try {
        	
            l = (List<TributoEnte>) listByQuery("tributiEntiIbanCCpNull");
            
        } catch (Exception e) {
        	
        	LOGGER.error("error on getTributiEntiIbanCCpNull ", e);
        	
        	throw new DAORuntimeException();
        }
        
        return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTributiEntibyEnteAndNdpCdStaz(String idEnte, String ndpCodStazPa) {
		List<String> l = null;        
        try {
            Query query= em.createNamedQuery("allTributiEntiByEnteAndNdpCdStaz", String.class);            
            query.setParameter("ente", idEnte);
            query.setParameter("ndpCodStazPa", ndpCodStazPa);
            l = (List<String>)query.getResultList();
        } catch (Exception e) {
        	LOGGER.error("error on getAllTributiEntibyEnte ", e);
        	throw new DAORuntimeException();
        }
        return l;
	}
	
	@Override
	public List<TributoEnte> getAllTributiEntibyEnte(String idEnte) {
		List<TributoEnte> l = null;        
        try {
            Query query= em.createNamedQuery("allTributiEntiByEnte", TributoEnte.class);            
            query.setParameter("ente", idEnte);
            l = (List<TributoEnte>)query.getResultList();
        } catch (Exception e) {
        	LOGGER.error("error on getAllTributiEntibyEnte ", e);
        	throw new DAORuntimeException();
        }
        return l;
	}
	
	@Override
	public List<TributoEnte> getAllTributiEnteEseguiti(String idEnte) {
		List<TributoEnte> l = null;        
        try {
            Query query= em.createNamedQuery("getAllTributiEnteEseguiti", TributoEnte.class);            
            query.setParameter("ente", idEnte);
            l = (List<TributoEnte>)query.getResultList();
        } catch (Exception e) {
        	LOGGER.error("error on getAllTributiEnteEseguiti", e);
        	throw new DAORuntimeException();
        }
        return l;
	}

	@Override
	public int countTributiEntiIbanCCpNull(String idEnte, String cdTrbEnte) {
		try {
            Query query= em.createNamedQuery("checkTributoEnteIbanCCpNull");            
            query.setParameter("ente", idEnte);
            query.setParameter("cdTrbEnte", cdTrbEnte);
            Object o = query.getSingleResult();
            if (o instanceof Integer) {
            	return ((Integer)o).intValue();
            } else {
            	return ((Long)o).intValue();
            }
        } catch (Exception e) {
        	LOGGER.error("error on countTributiEntiIbanCCpNull ", e);
        	throw new DAORuntimeException();
        }
	}

	@Override
	public int countTributiEntiIbanMyBankNull(String idEnte, String cdTrbEnte) {
		try {
            Query query= em.createNamedQuery("checkTributoEnteIbanMyBankNull");            
            query.setParameter("ente", idEnte);
            query.setParameter("cdTrbEnte", cdTrbEnte);
            Object o = query.getSingleResult();
            if (o instanceof Integer) {
            	return ((Integer)o).intValue();
            } else {
            	return ((Long)o).intValue();
            }
        } catch (Exception e) {
        	LOGGER.error("error on countTributiEntiIbanMyBankNull ", e);
        	throw new DAORuntimeException();
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getNdpIuvStartNumByIdEnte(String idEnte,Long oldNdpIuvStartNum) {
		List<String> listStartNumString = new ArrayList<String>();
		String query = "select te.ndpIuvStartNum from TributoEnte te where te.tribEnId.idEntePk = :idEnte";
		if (oldNdpIuvStartNum != null) {
			query = query + " and te.ndpIuvStartNum != :oldNdpIuvStartNum";
		}
		Query selectQuery = em.createQuery(query);
		selectQuery.setParameter("idEnte", idEnte);
		if (oldNdpIuvStartNum != null) {
			selectQuery.setParameter("oldNdpIuvStartNum", oldNdpIuvStartNum);
		}
		List<Long> listStartNum = selectQuery.getResultList();
		for (Long num : listStartNum) {
			if (num != null) {
				listStartNumString.add(Long.toString(num));
			}
		}
		return listStartNumString;
	} 
	
	@Override
	public void updateFlagPerConfigurazioneCanaliPagamento(String idEnte, TributoEnteDTO tribDTO) {
		
		try {
			String query = "update TributoEnte te " +
	                " set te.ndpCodStazPa = :codStazPa," +
				       " te.ndpAuxDigit = :auxDigit," +
				       " te.ndpCodSegr = :codSegregaz, " +
				       " te.flNdpModello3 = :flNdpModello3, " +
		                
				       " te.flBancaTesoriera = :flBancaTesoriera, " +
					   " te.flBlf = :flBlf, " +
					   " te.flNdp = :flNdp " +
					   " where te.tribEnId.idEntePk = :idEnte ";
			Query selectQuery = em.createQuery(query);
			
			selectQuery.setParameter("idEnte", idEnte);
			selectQuery.setParameter("codStazPa", tribDTO.getNdpCodStazPa());
			selectQuery.setParameter("auxDigit", tribDTO.getNdpAuxDigit());
			selectQuery.setParameter("codSegregaz", tribDTO.getNdpCodSegr());
			selectQuery.setParameter("flNdpModello3", tribDTO.getFlNdpModello3());
			
			selectQuery.setParameter("flBancaTesoriera", tribDTO.getFlBancaTesoriera());
			selectQuery.setParameter("flBlf", tribDTO.getFlBlf());
			selectQuery.setParameter("flNdp", tribDTO.getFlNdp());
			
			selectQuery.executeUpdate();
			
		} catch (Exception e) {
			
			LOGGER.error("error on updateTributo ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		
	}
	
	@Override
	public int countTributiEntiByIban(String idEnte, String iban) {
		try {
            Query query= em.createNamedQuery("checkTributoEntebyIban");            
            query.setParameter("ente", idEnte);
            query.setParameter("iban", iban);
            query.setParameter("iban1", iban);
            query.setParameter("iban2", iban);
            query.setParameter("iban3", iban);
            Object o = query.getSingleResult();
            if (o instanceof Long) {
            	return ((Long)o).intValue();
            } 
            
            return 0;
        } catch (Exception e) {
        	LOGGER.error("error on countTributiEntiByIban ", e);
        	throw new DAORuntimeException();
        }
	}

}
