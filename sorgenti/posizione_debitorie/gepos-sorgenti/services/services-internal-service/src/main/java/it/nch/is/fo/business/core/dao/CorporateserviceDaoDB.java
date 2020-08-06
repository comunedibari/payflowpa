package it.nch.is.fo.business.core.dao;

import it.nch.fwk.fo.core.BackEndContext;
import it.nch.fwk.fo.core.SessionManagerHibernate;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.das.DAODatabase;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.Funzioniintestatari;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.IntestatariPojo;
import it.nch.is.fo.profilo.OperatoriFormImpl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


@SuppressWarnings("rawtypes")
public class CorporateserviceDaoDB extends DAODatabase{
   
   
   public CorporateserviceDaoDB(){
     super();
   }
   
   // TEST Method
   
    public DTO testDB(BackEndContext context, DTO dto) throws RemoteException{
       return null;
   }
    
    
    
    public DTO getCorporateByCode(BackEndContext context,DTO dto) throws Exception {
    	try{
	 	    StatelessSessionManager sm = context.getStatelessSessionManager();	
	 	    
		 	IntestatariPojo intestatariPojo = (IntestatariPojo)dto.getBusinessObject();
		 	String pk = intestatariPojo.getCorporate();
		 	    
		 	if(intestatariPojo != null && pk !=null){
		 		Intestatari returnIntestatariPojo;  
//			    returnIntestatariPojo = (Intestatari)sm.load(returnIntestatariPojo.getClass(),pk);
			    
				Query q = sm.createHibernateQuery("select INT from Intestatari INT " +
					"left outer join INT.intestatariOperatori IOP " +
					"left outer join IOP.operatore " +
					"where INT.corporate = :corpo");
				q.setString("corpo", pk);
				returnIntestatariPojo = (Intestatari)q.uniqueResult();
			    
				dto.setBusinessObject(returnIntestatariPojo);
			 	
		 	}
		 	    		
		    return dto;
    	}
   		catch(Exception e){
   		    	Tracer.debug(this.getClass().getName(), "getCorporateByCode", e.getMessage(),e);
   		    	throw e;
   		}
   
    }
    
    /**
     * 
     * @param context
     * @param dto
     * @return
     * @throws Exception
     */
    public DTO getEnteByIntestCode(BackEndContext context,DTO dto) throws Exception {
    	try{
	 	    StatelessSessionManager sm = context.getStatelessSessionManager();	
	 	    
	 	   IntestatariPojo intestatariPojo = (IntestatariPojo)dto.getBusinessObject();
		 	String intes = intestatariPojo.getCorporate();
		 	    
		 	if(intestatariPojo != null && !intes.trim().equals("")){
		 		Enti returnEntiPojo = new Enti();  
		 		Query entiQuery = sm.createHibernateQuery("from Enti e where intestatario=:intes");
		 		entiQuery.setParameter("intes", intes);
		 		List entiList = entiQuery.list();
		 		
		 		if (!entiList.isEmpty()){
		 			returnEntiPojo = (Enti)entiList.get(0);
		 		}
			    	
			 	dto.setBusinessObject(returnEntiPojo);
		 	}
		 	    		
		    return dto;
    	}
   		catch(Exception e){
   		    	Tracer.debug(this.getClass().getName(), "getCorporateByCode", e.getMessage(),e);
   		    	throw e;
   		}
   
    }    
    
    public DTO getCorporateByID(BackEndContext context, DTO dto) throws Exception {
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();

			Enti vo = (Enti) dto.getVO();

			Enti returnEntiPojo = new Enti();
			Query entiQuery = sm
					.createHibernateQuery("from Enti e where id=:idEnte");
			entiQuery.setParameter("idEnte", vo.getIdEnte());

			Iterator it = entiQuery.iterate();

			if (it.hasNext()) {
				vo = (Enti) it.next();
			}
			dto.setVO(vo);

			return dto;
		} catch (Exception e) {
			Tracer.debug(this.getClass().getName(), "getCorporateByCode", e
					.getMessage(), e);
			throw e;
		}

	}    
    
    
    public DTO updateCorporate(BackEndContext context, DTO dto) throws Exception {
    	try {
    		
    		StatelessSessionManager sm = context.getStatelessSessionManager();
    		
    		Intestatari intestatariPojo = (Intestatari) dto.getBusinessObject();
    		
			Tracer.debug("updateCorporate", "corporate Id: " + intestatariPojo.getCorporate(), null);
			
			if (intestatariPojo.getFunzioniIntestatari()!=null){
				Iterator funIter = intestatariPojo.getFunzioniIntestatari().iterator();
				while (funIter.hasNext()){
					Funzioniintestatari fun = (Funzioniintestatari)funIter.next();
					Tracer.debug("updateCorporate", "funzioniIntestatari -- codfunzione: " + fun.getCodfunzione(), null);
					Tracer.debug("updateCorporate", "funzioniIntestatari -- corporate: " + fun.getCorporate(), null);
					if (fun.getIntestatariobj()!=null){
						if (fun.getIntestatariobj().getFunzioniIntestatari()!=null){
							Iterator fIter = fun.getIntestatariobj().getFunzioniIntestatari().iterator();
							while (fIter.hasNext()){
								Funzioniintestatari f = (Funzioniintestatari)fIter.next();
		    					Tracer.debug("updateCorporate", "funzioniIntestatari -- INTESTATARIO.FUN: " + f.getCodfunzione(), null);
		    					Tracer.debug("updateCorporate", "funzioniIntestatari -- INTESTATARIO.CORPORATE: " + f.getCorporate(), null);
							}
						}
					}
				}
			}
			
			if (intestatariPojo.getEntiobj()!=null && intestatariPojo.getEntiobj().getCodiceEnteIForm()!=null && intestatariPojo.getEntiobj().getCodiceEnteIForm().length()>0){
				Enti ente = ((Enti)intestatariPojo.getEntiobj());
				ente.setIntestatarioobj(intestatariPojo);
				ente.setPrVersione(1);
				ente.setDenominazione(intestatariPojo.getRagionesociale());
				ente.setOpInserimento("saveCorporate");
				ente.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				
			} else {
				intestatariPojo.setEntiobj(null);
			}

			
			sm.saveOrUpdate(intestatariPojo);
			sm.flush();
			
			
			if (intestatariPojo.getEntiobj()!=null && intestatariPojo.getEntiobj().getCodiceEnteIForm()!=null && intestatariPojo.getEntiobj().getCodiceEnteIForm().length()>0){

				/*****************************************************
				 * 
				 * Riallineamento DB SmartProxy (solo se previsto)
				 * Da fare dopo la insert e la flush dell'ente
				 * 
				 * *****************************************************/
				SPXAlignHelper.AlignSPX(intestatariPojo.getEntiobj().getCodiceEnteIForm(), null, null, sm);

			}			

			
			sm.refresh( intestatariPojo );
			    			
			dto.setBusinessObject(intestatariPojo);

    		return dto;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Tracer.debug(this.getClass().getName(), "updateCorporate", e.getMessage(), e);    		
    		throw e;
    	}
    }



	
	public DTOCollection listCorporateByCriteria(BackEndContext context, DTO dto) throws Exception{
		Tracer.debug(this.getClass().getName(), "listCorporateByCriteria", null);
		StatelessSessionManager sm = context.getStatelessSessionManager();	
				
		Intestatari searchCriteria=(Intestatari)dto.getBusinessObject();
		String corporateCode = searchCriteria.getCorporate();
		
		Criteria criteria = sm.createHibernateCriteria(Intestatari.class);
		criteria.createAlias("enti", "enti",Criteria.LEFT_JOIN);		
		criteria.createAlias("indirizzipostaliobj", "indirizzipostali");		
		criteria.createAlias("enti.tipoEnteobj", "tipologieEnti",Criteria.LEFT_JOIN);

		
		if( corporateCode != null && !corporateCode.equals("")){
 	    	addStandardCriteria(criteria, "corporate", corporateCode);
 	    }

 	   String categoria = searchCriteria.getCategoria();		
 	   addStandardCriteria(criteria, "categoria", categoria);
	    
 	   String ragioneSociale = searchCriteria.getRagionesociale();		
 	   addStandardCriteria(criteria, "ragionesociale", ragioneSociale);
 	     	    	 	   	   
 	   String codiceFiscale = searchCriteria.getIndirizzipostaliobj().getFiscalCodeIForm();
 	   addStandardCriteria(criteria, "fiscalCode", codiceFiscale, "indirizzipostali");
 	  
 	   String partitaIva = searchCriteria.getIndirizzipostaliobj().getVatCodeIForm();
	   addStandardCriteria(criteria, "vatCode", partitaIva, "indirizzipostali");
	    
	   String codiceEnte = searchCriteria.getEntiobj().getCodiceEnteIForm();
 	   addStandardCriteria(criteria, "codiceEnte", codiceEnte, "enti");
 	  
	   String tipoEnte = searchCriteria.getEntiobj().getTipoEnteobjIForm().getTipoIForm();
 	   addStandardCriteria(criteria, "tipoEnte", tipoEnte, "tipologieEnti");
 	   
 	   String lapl = searchCriteria.getLapl();		
	   addStandardCriteria(criteria, "lapl", lapl);
	   
 	   	DTOCollection collDto = null;
		if (dto.getPagingCriteria()!=null){
			
			collDto = getPagedQueryByCriteria(sm, dto.getPagingCriteria(), criteria); //, paramsQueryCount
		}else{
			 collDto = new DTOCollectionImpl(criteria.list(), true);
		}
		
		//List list = criteria.list();
		//return new DTOCollectionImpl(list, false);
		return collDto;
	}
	
	private void addStandardCriteria(Criteria criteria, String fieldName, String fieldValue){
		addStandardCriteria(criteria, fieldName, fieldValue, "");
	}
	
	private void addStandardCriteria(Criteria criteria, String fieldName, String fieldValue, String aliasName) {
		if( fieldValue != null && !fieldValue.equals("")){			
			//fieldValue=fieldValue.toUpperCase();
			String fullFieldName= null;
			if (aliasName!=null && aliasName.length()>0){
				fullFieldName=aliasName+"."+fieldName;
			} else {
				fullFieldName=fieldName;
			}
	    	if(fieldValue.startsWith("*") || fieldValue.endsWith("*")){	    		
	    		criteria.add( Restrictions.ilike(fullFieldName, replaceLikeChar(fieldValue)));
	    	}
	 	    else{	 	    
	 	    	criteria.add( Restrictions.eq(fullFieldName, replaceLikeChar(fieldValue)).ignoreCase());
	 	    }
	    }
	}
	
	public DTOCollection listEntiByCriteria(BackEndContext context, DTO dto) throws Exception{
		Tracer.debug(this.getClass().getName(), "listEntiByCriteria", null);
		StatelessSessionManager sm = context.getStatelessSessionManager();	
				
		Intestatari searchCriteria=(Intestatari)dto.getBusinessObject();
										
		Query entiQuery = sm.createHibernateQuery("from Enti e where codiceEnte=:codiceEnte");
		
		String codiceEnte = searchCriteria.getEntiobj().getCodiceEnteIForm();
		entiQuery.setParameter("codiceEnte", codiceEnte);
				 	   	 	  	   
 	   	DTOCollection collDto = new DTOCollectionImpl(entiQuery.list(), true);
					
		return collDto;
	}
	
	
//	/**
//	 * 
//	 * @param context
//	 * @return
//	 * @throws Exception
//	 */
//	public DTOCollection listAllEnti(BackEndContext context) throws Exception{
//		Tracer.debug(this.getClass().getName(), "listAllEnti", null);
//		StatelessSessionManager sm = context.getStatelessSessionManager();	
//
//		Query entiQuery = sm.createHibernateQuery("from Enti as e where e.stato='A' order by e.intestatarioobj.ragionesociale");
// 	   	DTOCollection collDto = new DTOCollectionImpl(entiQuery.list(), true);
//					
//		return collDto;
//	}	
	
	private String replaceLikeChar(String str){
		String rep = str;
		
		if(rep.startsWith("*")){
			rep = "%" + rep.substring(1, rep.length());
		}
		if(str.endsWith("*")){
			rep = rep.substring(0, rep.length()-1) + "%";
		}
		
		return rep;
	}
	
	 
//	 public DTOCollection getAllTipologieEnti(BackEndContext context, DTO dto) throws Exception{
//			Tracer.debug(this.getClass().getName(), "getAllTipologieEnti", null);
//			StatelessSessionManager sm = context.getStatelessSessionManager();	
//							
//			Query getAllTipologieEntiQuery = sm.createHibernateQuery("from TipologiaEnti");				 	  	 	  
//			DTOCollectionImpl collDto = new DTOCollectionImpl(getAllTipologieEntiQuery.list(), true);
//
//			return collDto;
//		}
	 
	 
	 
//	/**
//	 * listAllSubjectsByOperator
//	 * @param BackEndContext context, Operatori operator
//	 * @return Collection of Subjects, all of them having the operator as a member.
//	 * @throws Exception
//	 */
//	public DTOCollection listAllSubjectsByOperator(BackEndContext context, DTO dto) throws Exception{
//		Tracer.debug(this.getClass().getName(), "listAllSubjectsByOperator", null);
//		StatelessSessionManager sm = context.getStatelessSessionManager();	
//		HashMap map = new HashMap();
//		OperatoriFormImpl operatore = (OperatoriFormImpl) dto.getBusinessObject();
//		map.put("operatore", operatore.getOperatoreIForm());
//		
//		//FA CAGARE La query cablata.
//		//String sql = "select T1.INTESTATARIO, T1.OPERATORE from INTEST_OPER T1, INTESTATARI T2 where T1.OPERATORE = T2.OPERATORE AND T2.CATEGORIA <> 'CI' AND T1.OPERATORE = :operatore"; 
//		//Collection result = sm.createSQLQuery(sql, map);
//		//DTOCollection collDto = new DTOCollectionImpl(result);
//		
//		Query query = sm.createHibernateQuery("from Intestatari");
//		DTOCollectionImpl collDto = new DTOCollectionImpl(query.list(), true);
//		
//		return collDto;
//		
//	}	
	
	public void updateFlagComunicazioni(BackEndContext context, DTO dto) throws Exception {

		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			Intestatari input = (Intestatari) dto.getBusinessObject();

			String sql = "update Intestatari set flagComunicazioni=:comunicazioni where corporate=:intestatario";
			Query entiQuery = sm.createHibernateQuery(sql);
			entiQuery.setParameter("comunicazioni", input.getFlagComunicazioni());
			entiQuery.setParameter("intestatario", input.getCorporate());
			Tracer.debug(this.getClass().getName(), "updateFlagComunicazioni", "queryStr : " + sql, null);
			int numRowsUpdated = entiQuery.executeUpdate();
			sm.flush();
			if (numRowsUpdated == 0)
				Tracer.error("updateFlagComunicazioni", "nuessun record updatato", null);
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "updateFlagComunicazioni", e.getMessage(), e);
			throw e;
		} finally {
		
			Tracer.debug(this.getClass().toString(), "updateFlagComunicazioni", "FINE");
		}
	}
		
 } //end class