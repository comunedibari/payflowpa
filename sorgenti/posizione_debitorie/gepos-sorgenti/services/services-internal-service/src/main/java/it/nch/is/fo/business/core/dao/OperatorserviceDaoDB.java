package it.nch.is.fo.business.core.dao;

import it.nch.fwk.fo.core.BackEndContext;
import it.nch.fwk.fo.core.SessionManagerHibernate;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.das.DAODatabase;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.AppConfiguration;
import it.nch.is.fo.BackEndConstant;
import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.login.rt.LoginRTPojo;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.Funzioni;
import it.nch.is.fo.profilo.Funzionioperatori;
import it.nch.is.fo.profilo.FunzionioperatoriVOImpl;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.Intestatarioperatori;
import it.nch.is.fo.profilo.IntestatarioperatoriCommon;
import it.nch.is.fo.profilo.Operatori;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.is.fo.profilo.Password;
import it.nch.is.fo.profilo.TributiOperatoriCommon;
import it.nch.is.fo.tributi.TributiOperatore;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings({"rawtypes", "deprecation", "unchecked"})
public class OperatorserviceDaoDB extends DAODatabase {


	public OperatorserviceDaoDB() {
		super();
	}

	
	/**
	 * @param context
	 *            il BackEndContext
	 * @param dto
	 *            DTO contenente come BusinessObject un oggetto di tipo
	 *            Operatori.
	 * @return DTOCollection contenente
	 * @throws Exception
	 */
	public DTOCollection listOperatorsByCriteria(BackEndContext context, DTO dto) throws Exception {

		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "listOperatorsByCriteria", "", "");

			StatelessSessionManager sm = context.getStatelessSessionManager();
			Operatori operatori = (Operatori) dto.getBusinessObject();

			Criteria criteria = sm.createHibernateCriteria(Operatori.class);
			criteria.createAlias("intestatarioperatori.intestatario", "intest", Criteria.FULL_JOIN);

			if (operatori.getIntestatariobj().getRagionesocialeIForm() != null && operatori.getIntestatariobj().getRagionesocialeIForm().trim().length() > 0) {
				criteria.add(Restrictions.ilike("intest.ragionesociale", "%" + operatori.getIntestatariobj().getRagionesocialeIForm() + "%"));
			}

			if (operatori.getName() != null && operatori.getName().trim().length() > 0) {
				criteria.add(Restrictions.ilike("name", "%" + operatori.getName() + "%"));
			}

			if (operatori.getSurname() != null && operatori.getSurname().trim().length() > 0) {
				criteria.add(Restrictions.ilike("surname", "%" + operatori.getSurname() + "%"));
			}

			if (operatori.getCodiceFiscale() != null && operatori.getCodiceFiscale().trim().length() > 0) {
				criteria.add(Restrictions.ilike("codiceFiscale", "%" + operatori.getCodiceFiscale() + "%"));
			}

			if (operatori.getCorporateIForm() != null && operatori.getCorporateIForm().trim().length() > 0) {
				String corporate = operatori.getCorporateIForm().toUpperCase();
				corporate = corporate.replace('*', '%');
				criteria.add(Restrictions.ilike("intestatarioperatori.intestatario.corporate", "%" + corporate + "%"));
			}

			/*
			 * if (operatori.getUsername() != null &&
			 * operatori.getUsername().trim().length() > 0) { String username =
			 * operatori.getUsername().toUpperCase(); username =
			 * username.replace('*', '%');
			 * criteria.add(Restrictions.sqlRestriction(
			 * "upper({alias}.username)like ?", username,new StringType())); }
			 * 
			 * if (operatori.getSignerCode() != null &&
			 * operatori.getSignerCode().trim().length() > 0) {
			 * criteria.add(Restrictions.eq("signerCode",
			 * operatori.getSignerCode())); }
			 */

			DTOCollection collDto = null;
			if (dto.getPagingCriteria() != null)
				collDto = getPagedQueryByCriteria(sm, dto.getPagingCriteria(), criteria); // ,
																							// paramsQueryCount
			else
				collDto = new DTOCollectionImpl(criteria.list(), true);

			return collDto;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "listOperatorsByCriteria", e.getMessage(), e);
			throw e;
		}
	}

	/******************
	 * Tested ok by Goutam on 1st Sep 2006 Insert/Update an Operator
	 * 
	 * @param context
	 * @param obj
	 * @return
	 * @throws Exception
	 */

	public Operatori insertOrUpdate(BackEndContext context, Operatori obj) throws Exception {

		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();

			if (obj.getIntestatariobj() == null) {
				Tracer.debug(this.getClass().getName(), "", "");
				Intestatari intestatari = (Intestatari) sm.load(Intestatari.class, obj.getCorporate());
				obj.setIntestatariobj(intestatari);
			}
			// aggiornare l'oggetto intestatario per tutti gli
			// intestatarioperatori
			if (obj.getIntestatarioperatori() != null) {
				for (IntestatarioperatoriCommon elem : obj.getIntestatarioperatori()) {
					
					if (elem.getIntestatariobjIForm() == null) {
						Intestatarioperatori intOper = (Intestatarioperatori) elem;
						Intestatari intestatari = (Intestatari) sm.load(Intestatari.class, intOper.getOpeId().getIntestatario());
						elem.setIntestatariobjIForm(intestatari);
					}
				}
			}

			obj.setUsername(obj.getUsername().toUpperCase());
			sm.saveOrUpdate(obj);
			sm.flush();
			sm.refresh(obj);

			return obj;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "insertOrUpdate", "Insert/Update Operatori", null);
			throw e;
		}

	}
	
	public void deleteTributiOperatoriAndInsertNew(BackEndContext context, Collection<TributiOperatore> objs, List<String> allCdTrbEntePk) throws Exception {
		try {
			TributiOperatore first = objs.iterator().next();
			deleteTributiOperatore(context, first.getIntestatarioPk(), first.getOperatorePk(), first.getIdEnte(), allCdTrbEntePk);
			insertTributiOperatore(context, objs);
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "updateTributioperatore", "update Tributi Operatore", null);
			throw e;
		}
	}
	
	public void deleteTributiOperatoriAndInsertNew(BackEndContext context, Collection<TributiOperatore> objsToInsert, String tipoOperatore, String intestatario, String operatore, String idEnte, ArrayList<String> cdTrbOperatore) throws Exception {
		try {
			deleteTributiOperatore(context, intestatario, operatore, idEnte, cdTrbOperatore);
			if (!objsToInsert.isEmpty())
				insertTributiOperatore(context, objsToInsert);
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "updateTributioperatore", "update Tributi Operatore", null);
			throw e;
		}
	}
	
	private boolean insertTributiOperatore(BackEndContext context, Collection<TributiOperatore> objs) throws Exception {
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			
			for(TributiOperatore obj: objs) {
				sm.merge(obj);
			}
			sm.flush();
			return true;
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "insertOrUpdate", "Insert/Update Operatori", null);
			throw e;
		}
	}

	public void insertIntestatariOperatori(BackEndContext context, Intestatarioperatori intoper, String tipoOperatore) throws Exception {
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();

//			IntestatarioperatoriId intOperId = new IntestatarioperatoriId();
//			intOperId.setIntestatario(intoper.getId());
//			intOperId.setOperatore(operatori.getOperatore());
			Intestatarioperatori intOper = new Intestatarioperatori(intoper.getOpeId());
			intOper.setTipoOperatore(tipoOperatore);
			if (intOper.getLocked()==null){
			  intOper.setLocked(0);
			}

			sm.saveOrUpdate(intOper);
			sm.flush();
			sm.refresh(intOper);

//			Set<IntestatarioperatoriCommon> setInOp = new LinkedHashSet<IntestatarioperatoriCommon>();
//			setInOp.add(intOper);
//			operatori.setIntestatarioperatori(setInOp);

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "insertOrUpdate", "Insert/Update Operatori", null);
			throw e;
		}
//		return operatori;
	}

	/******************
	 * Tested ok by Goutam on 1st Sep 2006
	 * 
	 * @param context
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Operatori load(BackEndContext context, Operatori obj) throws Exception {
		Operatori operatore = null;
		
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			
			
			Query q = sm.createHibernateQuery("Select OP from Operatori OP where OP.operatore = :oper");
			q.setString("oper", obj.getOperatore());
			operatore = (Operatori)q.uniqueResult();

//			obj = (Operatori) sm.load(obj.getClass(), obj.getOperatore());
			
			sm.flush();
			return operatore;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "load", "load Operatori", null);
			throw e;
		}
	}

	/****************
	 * Tested ok by Goutam on 1st Sep 2006 Delete a single Operator
	 * 
	 * @param context
	 * @param obj
	 * @return
	 * @throws Exception
	 */

	public boolean delete(BackEndContext context, Operatori obj) throws Exception {
		try {

			StatelessSessionManager sm = context.getStatelessSessionManager();
			deleteOperatorFunctions(context, obj.getCorporate(), obj.getOperatore());
			sm.delete(obj);
			sm.flush();

			return true;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "Delete", "Delete Operatori", null);
			throw e;
		}
	}

	/*****************
	 * Delete Operator Functions
	 * 
	 * @param context
	 * @param corporate
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOperatorFunctions(BackEndContext context, String corporate, String operatore) throws Exception {
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "deleteOperatorFunctions", "", "");
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();

			Query q = sm.createHibernateQuery("delete it.nch.is.fo.profilo.Funzionioperatori where INTESTATARIO=:corporate AND OPERATORE=:operatore");

			q.setString("corporate", corporate);
			q.setString("operatore", operatore);
			q.executeUpdate();
			sm.flush();
			return true;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "Delete", "Delete Operator Functions", null);
			throw e;
		}
	}
	
	
	
	private boolean deleteTributiOperatore(BackEndContext context, String idIntestatario, String idOperatore, String idEnte, List<String> allCdTrbEntePk) throws Exception {
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "delete Tributi Operatore", "", "");
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			String query = "delete it.nch.is.fo.tributi.TributiOperatore where idEnte=:idEnte AND idOperatore=:idOperatore AND idIntestatario=:idIntestatario";
			if (allCdTrbEntePk != null && !allCdTrbEntePk.isEmpty()) {
				query +=  " and cdTrbEnte NOT IN (:objs) ";
			}
			Query q = sm.createHibernateQuery(query);
			// intestatario, operatore, idente

			q.setString("idIntestatario", idIntestatario);
			q.setString("idOperatore", idOperatore);
			q.setString("idEnte", idEnte);
			
			if (allCdTrbEntePk != null && !allCdTrbEntePk.isEmpty()) {
				q.setParameterList("objs", allCdTrbEntePk);
			}
			
			q.executeUpdate();
			sm.flush();
			return true;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "Delete", "delete Tributi Operatore", null);
			throw e;
		}
	}

	private boolean deleteTributiOperatore(BackEndContext context, String idIntestatario, String idOperatore, String idEnte) throws Exception {
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "delete Tributi Operatore", "", "");
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();

			Query q = sm.createHibernateQuery("delete it.nch.is.fo.tributi.TributiOperatore where idEnte=:idEnte AND idOperatore=:idOperatore AND idIntestatario=:idIntestatario");
			// intestatario, operatore, idente

			q.setString("idIntestatario", idIntestatario);
			q.setString("idOperatore", idOperatore);
			q.setString("idEnte", idEnte);
			
			q.executeUpdate();
			sm.flush();
			return true;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "Delete", "delete Tributi Operatore", null);
			throw e;
		}
	}
	



	/*****************
	 * 
	 * @param context
	 * @param funzionioperatoriCollection
	 * @param corporate
	 * @param operatore
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOperatorFunctionsAndInsertNewFunctions(BackEndContext context, Collection funzionioperatoriCollection, String corporate, String operatore) throws Exception {
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "deleteOperatorFunctionsAndInsertNewFunctions", "", "");
		try {
			// funzionioperatori ..
			deleteOperatorFunctions(context, corporate, operatore);
			InsertOperatorFunctions(context, funzionioperatoriCollection, corporate, operatore);

			return true;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "Delete", "deleteOperatorFunctionsAndInsertNewFunctions", null);
			throw e;
		}
	}



	/*************************
	 * 
	 * @param context
	 * @param funzionioperatori
	 * @return
	 * @throws Exception
	 */
	private boolean InsertOperatorFunctions(BackEndContext context, Collection funzionioperatoriCollection, String corporate, String operatore) throws Exception {
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "InsertOperatorFunctions", "", "");
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			Iterator it = funzionioperatoriCollection.iterator();
			while (it.hasNext()) {

				Funzionioperatori funzionioperatori = (Funzionioperatori) it.next();

				Funzioni funzione = (Funzioni) sm.load(Funzioni.class, funzionioperatori.getFunctionCode());
				funzionioperatori.setFunzioniobj(funzione);
				funzionioperatori.setCorporate(corporate);
				funzionioperatori.setOperatore(operatore);
				if (Tracer.isDebugEnabled(getClass().getName())) {
					Tracer.debug(this.getClass().getName(), "InsertOperatorFunctions", "codfunzione", funzione.getFunctionCode());
					Tracer.debug(this.getClass().getName(), "InsertOperatorFunctions", "corporate", corporate);
					Tracer.debug(this.getClass().getName(), "InsertOperatorFunctions", "operatore", operatore);
				}
				sm.saveOrUpdate(funzionioperatori);
			}
			sm.flush();
			return true;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "insertOrUpdate", "Insert/Update OperatorFunction", null);
			e.printStackTrace();
			throw e;
		}
	}


	/******************************
	 * Tested ok by Goutam on 1st Sep 2006 retun all the functions(irrespective
	 * of any right for that operator) with a mark whether the operator have
	 * right in that function or not.If Funzionioperatori.operatore identify the
	 * mark
	 * 
	 * @param fec
	 * @param dto
	 * @return
	 */
//	public DTOCollection listOperatorFunctionsByOperatorAndCorporate(BackEndContext fec, DTO dto) throws Exception {
//
//		try {
//			Operatori operatori = (Operatori) dto.getBusinessObject();
//			StatelessSessionManager sm = fec.getStatelessSessionManager();
//
//			/*
//			 * select FUNZIONI.CODFUNZIONE,FUNZIONI.DESCRIZIONE,
//			 * FUNZIONIOPERATORI.OPERATORE,FUNZIONIOPERATORI.INTESTATARIO from
//			 * FUNZIONI left outer join FUNZIONIOPERATORI ON
//			 * FUNZIONI.CODFUNZIONE=FUNZIONIOPERATORI.CODFUNZIONE AND
//			 * FUNZIONIOPERATORI.OPERATORE='goutam' AND
//			 * FUNZIONI.ABILITAZIONEGLOBALE='S'
//			 */
//
//			// ///////////////////////////////
//			// This is the temporary solution but the output is correct..tested
//			// ok.
//			// /////////////////////////////
//			String sql = "select new it.nch.is.fo.profilo.FunzionioperatoriVOImpl(funzioni.functionCode,funzioni.description,funzionioperatori.operatore,funzioniintestatari.corporate,funzioni.serviziobj.serviceCode )";
//			String sql_others = sql + " from it.nch.is.fo.profilo.Funzioni as funzioni, " + " it.nch.is.fo.profilo.Funzionioperatori as funzionioperatori, "
//					+ " it.nch.is.fo.profilo.Funzioniintestatari as funzioniintestatari " + " WHERE funzioni.functionCode=funzioniintestatari.codfunzione "
//					+ " AND funzionioperatori.functionCode=funzioni.functionCode " + " AND funzioniintestatari.corporate=:corporate " + " AND funzionioperatori.operatore=:operatore "
//					+ " AND funzioni.enabled=:enabled ";
//
//			Query q = sm.createHibernateQuery(sql_others);
//
//			q.setString("corporate", operatori.getCorporate());
//			q.setString("operatore", operatori.getOperatore());
//			q.setString("enabled", BackEndConstant.ABILITAZIONE_GLOBALE);
//
//			Collection result = q.list();
//
//			int result_size = result.size();
//
//			Iterator it = result.iterator();
//			String not_in = "(";
//			int ctr = 0;
//			while (it.hasNext()) {
//				ctr++;
//				FunzionioperatoriVOImpl op = (FunzionioperatoriVOImpl) it.next();
//				not_in = not_in + "'" + op.getFunctionCode().toString() + "'";
//				if (ctr < result_size) {
//					not_in = not_in + ",";
//				}
//			}
//
//			sql = "select new it.nch.is.fo.profilo.FunzionioperatoriVOImpl(funzioni.functionCode,funzioni.description,'',funzioniintestatari.corporate,funzioni.serviziobj.serviceCode )";
//			sql_others = sql + " from it.nch.is.fo.profilo.Funzioni as funzioni, " + " it.nch.is.fo.profilo.Funzioniintestatari as funzioniintestatari "
//					+ " WHERE funzioni.functionCode=funzioniintestatari.codfunzione " + " AND funzioniintestatari.corporate=:corporate " + " AND funzioni.enabled=:enabled "
//					+ " AND funzioni.serviziobj.areaobj.applicazioneobj NOT IN ('APL_0002')" + " AND funzioni.serviziobj.areaobj.areaCode NOT IN ('AR_0001','AR_0002','AR_0003')";
//
//			if (ctr > 0) {
//				sql_others = sql_others + " AND funzioni.functionCode NOT IN " + not_in + ")";
//			}
//
//			q = sm.createHibernateQuery(sql_others);
//
//			q.setString("corporate", operatori.getCorporate());
//			q.setString("enabled", BackEndConstant.ABILITAZIONE_GLOBALE);
//
//			Collection finalresult = q.list();
//
//			finalresult.addAll(result);
//
//			return new DTOCollectionImpl(finalresult, true);
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listOperatorFunctionsByOperatorAndCorporate", e.getMessage(), e);
//			throw e;
//		}
//
//	}//

//	/*******************
//	 * Tested ok by Goutam on 1st Sep 2006
//	 * 
//	 * @param fec
//	 * @param corporate
//	 * @return
//	 * @throws Exception
//	 */
//	public DTOCollection listSignersByCorporateCode(BackEndContext fec, String corporate) throws Exception {
//		try {
//			StatelessSessionManager sm = fec.getStatelessSessionManager();
//			/*
//			 * Criteria criteria = sm.createHibernateCriteria(Operatori.class);
//			 * 
//			 * criteria.add(Restrictions.eq("corporate", corporate));
//			 * criteria.add(Restrictions.eq("flagOperatorType",
//			 * BackEndConstant.TYPE_GENERAL_OPERATOR));
//			 * //criteria.add(Restrictions
//			 * .eq("locked",BackEndConstant.VALID_OPERATOR));
//			 * criteria.add(Restrictions.isNotNull("signerCode"));
//			 * 
//			 * 
//			 * Collection result = criteria.list(); int size=result.size(); if
//			 * (size>0){ ArrayList list=new ArrayList(result); for(int
//			 * i=size;i>0;i--){ Operatori opr=(Operatori)list.get(i-1); if
//			 * (opr.getSignerCode().trim().length()==0){ list.remove(i-1); }
//			 * }//end for list.trimToSize(); return new DTOCollectionImpl(list,
//			 * true); }//end if
//			 */
//			Connection conn = null;
//			PreparedStatement ps = null;
//			Collection listOperator = new ArrayList();
//			// TODO PAZZIK CONTROLLARE
//			try {
//				String sql = "select USERNAME,INTESTATARIO,CODICEFIRMATARIO,PASSWORD" + " from OPERATORI " + " where INTESTATARIO=? "
//						+ " AND CODICEFIRMATARIO IS NOT NULL AND CODICEFIRMATARIO<>? ORDER BY USERNAME ";
//				SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
//				if (Tracer.isDebugEnabled(getClass().getName()))
//					Tracer.debug(this.getClass().getName(), "listSignersByCorporateCode", "query = " + sql);
//				SessionFactory sef = sessionManager.sef;
//				if (Tracer.isDebugEnabled(getClass().getName()))
//					Tracer.debug(this.getClass().getName(), "listSignersByCorporateCode", "Session Factory is null? " + (sef == null));
//
//				if (sef != null) {
//					Session se = sef.getCurrentSession();
//					// Session se = sef.openSession();
//					conn = se.connection();
//					ps = conn.prepareStatement(sql);
//					ps.setString(1, corporate);
//					ps.setString(2, "");
//
//					ResultSet result = ps.executeQuery();
//
//					Operatori operatore = null;
//					Intestatari intest = new Intestatari();
//					if (result != null) {
//						while (result.next()) {
//							operatore = new Operatori();
//							operatore.setUsername(new String(result.getString(1)));
//							operatore.setCorporate(new String(result.getString(2)));
//							operatore.setSignerCode(new String(result.getString(3)));
//							operatore.setPassword(new String(result.getString(4)));
//							operatore.setIntestatariobj(intest);
//							// aggiungo l'elemento alla collection
//							listOperator.add(operatore);
//
//						}
//
//					}
//
//				}
//
//			} catch (Exception e) {
//				Tracer.error(this.getClass().getName(), "listSignersByCorporateCode", e.getMessage(), e);
//				throw e;
//			} finally {
//				ps.close();
//				conn.close();
//			}
//			return new DTOCollectionImpl(listOperator, true);
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listSignersByCorporateCode", e.getMessage(), e);
//			throw e;
//		}
//	}
//
	/**
	 * Il metodo controlla se esiste un operatore con quella username nella
	 * corporate impostata.
	 * 
	 * @return true se ci sono username in quella corporate, altrimenti false.
	 */
	public boolean checkOperatorByUsernameAndCorporate(BackEndContext context, DTO dto) throws Exception {
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			Operatori operatore = (Operatori) dto.getBusinessObject();

			String corporate = operatore.getCorporateIForm();
			String username = operatore.getUsernameIForm().toUpperCase();

			HashMap map = new HashMap();
			map.put("corporate", corporate);
			map.put("username", username);

			String sql = "select * from OPERATORI where INTESTATARIO = :corporate and USERNAME = :username";

			Collection result = sm.createSQLQuery(sql, map);

			if (result != null && result.isEmpty()) {
				return true;
			}

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "checkOperatorByUsernameAndCorporate", e.getMessage(), e);
			throw e;
		}
		return false;
	}


	/**
	 * Recupera l'operatore in base al codice fiscale. Non � legato ad alcuna
	 * fase di login
	 * 
	 * @param context
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public OperatoriPojo getOperatorByCodiceFiscaleInternal(BackEndContext context, DTO dto) throws Exception {
		StatelessSessionManager sm = context.getStatelessSessionManager();

		Operatori login = (Operatori) dto.getBusinessObject();

		String upperCodiceFiscale = login.getCodiceFiscale().toUpperCase();

		if (Tracer.isDebugEnabled(getClass().getName())) {
			// Tracer.debug("OperatorserviceDaoDB",
			// "getOperatorByCodiceFiscaleInternal", "upperCodiceFiscale = " +
			// upperCodiceFiscale);
		}
		// Recupera da DB l'operatore avente il codice fiscale
		// upperCodiceFiscale
		OperatoriPojo oper = selectFromOperatoreByUpperCodiceFiscaleWithUR(context, upperCodiceFiscale);

		return oper;

	}

	/**
	 * Recupera da DB l'operatore avente le credenziali in ingresso.
	 * 
	 * @param context
	 *            il BackEndContext
	 * @param dto
	 *            LoginRTDTO contenente codice fiscale e pwd
	 * @return OperatoriPojo dell'operatore avente le credenziali in ingresso
	 * @throws Exception
	 */
	public OperatoriPojo getOperatorByCodiceFiscale(BackEndContext context, DTO dto, boolean isAdministratorLoggedIn) throws Exception {
		try {
			boolean isSSOLogin = ((FrontEndContext) context).isSSOLogin();

			StatelessSessionManager sm = context.getStatelessSessionManager();

			LoginRTPojo login = (LoginRTPojo) dto.getPojo();

			String upperCodiceFiscale = login.getCodiceFiscale().toUpperCase();

			if (Tracer.isDebugEnabled(getClass().getName())) {
				Tracer.debug("OperatorserviceDaoDB", "getOperatorByCodiceFiscale", "upperCodiceFiscale = " + upperCodiceFiscale);
			}
			//
			// Recupera da DB l'operatore avente il codice fiscale = upperCodiceFiscale
			//
			OperatoriPojo oper = selectFromOperatoreByUpperCodiceFiscaleWithUR(context, upperCodiceFiscale);

			if (oper != null) {
				// Esegue il controllo delle credenziali per l'operatore in ingresso 				
				checkLogin(sm, login.getPassword(), oper, isAdministratorLoggedIn, isSSOLogin);
			}

			return oper;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getOperatorByLoginInfo", e.getMessage());
			throw e;
		}

	} //

	/**
	 * Esegue il controllo delle credenziali per l'operatore in ingresso 
	 * 
	 * @param sm
	 *            lo StatelessSessionManager
	 * @param password
	 *            la password
	 * @param oper
	 *            OperatoriPojo estratto da DB corrispondente all'operatore che
	 *            si sta autenticando
	 * @throws Exception
	 */

	private void checkLogin(StatelessSessionManager sm, String password, OperatoriPojo oper, boolean isAdministratorLoggedIn, boolean isSSOLogin) throws Exception {
		
		// TODO PAZZIK RIPENSARE LA SCELTA O ALMENO LA PWD CABLATA
		if ((password != null) && !password.equals("GATEWAY") || isSSOLogin ){
			
		
			
			if ((password != null) && (oper.getPassword() != null) || isSSOLogin) {
				//
				// carico i parametri di configurazione
				//
				Date now = new Date();
				long nowTime = now.getTime();
				int maxLoginFailed;
				int lockDurationMinutes;
				int resetLogonFailedMinutes;
				boolean isOperatore = false;
				boolean isAmministratore = false;
				if (isAdministratorLoggedIn) {
					isAmministratore = true;
					maxLoginFailed = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_MAX_LOGON_FAILED);
					lockDurationMinutes = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_LOCK_DURATION_MINUTES);
					resetLogonFailedMinutes = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_RESET_LOGON_FAILED_NUMBER_MINUTES);
				} else {
					isOperatore = true;
					maxLoginFailed = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_MAX_LOGON_FAILED);
					lockDurationMinutes = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_LOCK_DURATION_MINUTES);
					resetLogonFailedMinutes = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_RESET_LOGON_FAILED_NUMBER_MINUTES);
				}
	
				boolean isAlreadyLocked = BackEndConstant.BLOCKED_OPERATOR.equals(oper.getLocked());
				int failedLogonNumber = oper.getNumFailedlogon() != null ? oper.getNumFailedlogon().intValue() : 0;
	
				//
				// Se sono gi� loccato e non sono un amministratore esco subito
				// (non resetto
				// n� incremento il conteggio dei login falliti)
				//
				if (isOperatore && isAlreadyLocked) {
					Tracer.error(this.getClass().getName(), "checkLogin", "Operator (OP) is already locked, exiting. Username = " + oper.getUsername() + ", # of failures = " + failedLogonNumber);
					return;
				}
	
				//
				// verifico se resettare il conteggio dei logon falliti
				//
				if (!isAlreadyLocked && oper.getFailedLogonDate() != null && nowTime - oper.getFailedLogonDate().getTime() > resetLogonFailedMinutes * 60 * 1000) {
					oper.setFailedLogonDate(null);
					oper.setNumFailedlogon(new Integer(0));
					failedLogonNumber = 0;
				}
				// TODO: chiedere - i records gestiti ancora alla vecchia maniera
				// con lock permanente devono essere sbloccati?
				// else if( oper.getFailedLogonDate() == null && failedLogonNumber
				// >= maxLoginFailed) {
				// oper.setFailedLogonDate(null);
				// oper.setNumFailedlogon(new Integer(0));
				// failedLogonNumber = 0;
				// }
	
				//
				// verifico se resettare il blocco dell'account
				//
				if (lockDurationMinutes > 0) {
					//
					// Sono probabilmente un amministratore
					//
					if (oper.getLockDate() != null && nowTime - oper.getLockDate().getTime() > lockDurationMinutes * 60 * 1000) {
						oper.setLockDate(null);
						oper.setLocked(BackEndConstant.VALID_OPERATOR);
						oper.setFailedLogonDate(null);
						oper.setNumFailedlogon(new Integer(0));
						failedLogonNumber = 0;
					}
				}
	
				//
				// controllo password
				//
	
				// se la password non � valida per l'operatore in ingresso
				if (!isSSOLogin){// && !LoginServiceImpl.checkPwd(oper.getPassword(), password)) {
	
					failedLogonNumber++;
					oper.setNumFailedlogon(new Integer(failedLogonNumber));
					oper.setFailedLogonDate(new Timestamp(now.getTime()));
					if (failedLogonNumber >= maxLoginFailed) {
						oper.setLocked(BackEndConstant.BLOCKED_OPERATOR);
						oper.setLockDate(new Timestamp(now.getTime()));
						Tracer.error(this.getClass().getName(), "checkLogin", "Operator has inserted a wrong password too many times. Username = " + oper.getUsername() + ", # of failures = "
								+ failedLogonNumber + " (more or equal than max allowed = " + maxLoginFailed + ")");
					} else {
						Tracer.error(this.getClass().getName(), "checkLogin", "Operator has inserted a wrong password. Username = " + oper.getUsername() + ", # of failures = " + failedLogonNumber
								+ " (less than max allowed = " + maxLoginFailed + ")");
					}
	
				} else {
					//
					// password check OK - now update Operatore with the last login
					// datetime for valid operator
					//
					oper.setFailedLogonDate(null);
					oper.setNumFailedlogon(new Integer(0));
					oper.setLastLogon(new Timestamp(now.getTime()));
				}
				sm.saveOrUpdate(oper);
				sm.flush();
				// sm.refresh(oper);
	
			} else {
				throw new Exception("login password or operator password is null");
			}
		}
	}

	
	OperatoriPojo selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR(StatelessSessionManager sm, String upperCorporate, String upperUsername) throws Exception {

		OperatoriPojo operatore = null;
	
		try {

			Query q = sm.createHibernateQuery("Select OP from Operatori OP " +
					"inner join OP.intestatarioperatori IO " +
					"where IO.operatore = :operatore" +
					" and IO.intestatario = :intestatario");
			
			q.setString("operatore", upperUsername);
			q.setString("intestatario", upperCorporate);
//			List<Operatori> operatori = (List<Operatori>)q.list();
			operatore = (Operatori) q.uniqueResult();
			
			Iterator it = operatore.getIntestatarioperatori().iterator();
			for (IntestatarioperatoriCommon intOper : operatore.getIntestatarioperatori()) {
				if (intOper.getIntestatariobjIForm().getCorporateIForm().equals(upperCorporate)){
					operatore.setIntestatarioperatoriobj(intOper);
				}
			}
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Got exception of kind " + e.getClass().getName());
			throw e;
		}

		if (Tracer.isDebugEnabled(getClass().getName())) {
			if (operatore != null) {
				Tracer.debug(OperatorserviceDaoDB.class.getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.username = " + operatore.getUsername());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.password = " + operatore.getPassword());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.bloccato = " + operatore.getLocked());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.numtentfalliti = " + operatore.getNumFailedlogon());
			} else {
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Non esiste un operatore avente codiceFiscale = " + upperUsername);
			}

		}
		return operatore;
	}

	/**
	 * @param sm
	 * @param upperCorporate
	 * @param upperUsername
	 * @return
	 * @throws Exception
	 */
/*
	private OperatoriPojo selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR(StatelessSessionManager sm, String upperCorporate, String upperUsername) throws Exception {
		OperatoriPojo operatore = null;
		Connection conn = null;
		PreparedStatement ps = null;
		Session se = null;
		try {
			String sql = "SELECT op.OPERATORE, USERNAME, op.INTESTATARIO, PASSWORD, io.BLOCCATO, NULL_COLL_FALL, "
					+ "CELLULARE, EMAIL, DESCRIZIONE, NOME, CODICEFIRMATARIO, DATASCADENZA, DATA_COLL_FALL, DATABLOCCO, ULTIMOCOLLEGAMENTO, COGNOME, CODICEFISCALE, io.TP_OPERATORE "
					+ "FROM OPERATORI op INNER " +
							"JOIN INTEST_OPER io ON op.OPERATORE=io.OPERATORE " + 
							"WHERE io.INTESTATARIO = ? " + "AND io.OPERATORE = ? " + "WITH UR ";

			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
			if (Tracer.isDebugEnabled(getClass().getName())) {
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "query = " + sql);
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "upperCorporate = " + upperCorporate);
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "upperUsername = " + upperUsername);
			}
			SessionFactory sef = sessionManager.sef;

			if (sef != null) {
				se = sef.getCurrentSession();
				conn = se.connection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, upperCorporate);
				ps.setString(2, upperUsername);

				ResultSet result = ps.executeQuery();

				if (result != null && result.next()) {
					operatore = new Operatori();
					Intestatari intestatario = new Intestatari();
					
					operatore.setOperatore(result.getString("OPERATORE"));
					operatore.setUsername(result.getString("USERNAME"));
					operatore.setCorporate(result.getString("INTESTATARIO"));
					operatore.setPassword(result.getString("PASSWORD"));
					operatore.setLocked(new Integer(result.getInt("BLOCCATO")));
					operatore.setNumFailedlogon(new Integer(result.getInt("NULL_COLL_FALL")));
					operatore.setDescription(result.getString("DESCRIZIONE"));
					operatore.setEmail(result.getString("EMAIL"));
					operatore.setMobile(result.getString("CELLULARE"));
					operatore.setName(result.getString("NOME"));
					operatore.setSignerCode(result.getString("CODICEFIRMATARIO"));
					operatore.setExpirationDate(result.getDate("DATASCADENZA"));
					operatore.setFailedLogonDate(result.getTimestamp("DATA_COLL_FALL"));
					operatore.setLockDate(result.getTimestamp("DATABLOCCO"));
					operatore.setLastLogon(result.getTimestamp("ULTIMOCOLLEGAMENTO"));
					operatore.setSurname(result.getString("COGNOME"));
					operatore.setCodiceFiscale(result.getString("CODICEFISCALE"));

					intestatario.setCorporate(operatore.getCorporate());
					operatore.setIntestatariobj(intestatario);
					
					IntestatarioperatoriId intestatarioperatoreCorrenteId = new IntestatarioperatoriId();
					intestatarioperatoreCorrenteId.setOperatore(operatore.getOperatore());
					intestatarioperatoreCorrenteId.setIntestatario(upperCorporate);
					Intestatarioperatori intestatarioperatoreCorrente = new Intestatarioperatori(intestatarioperatoreCorrenteId);
					intestatarioperatoreCorrente.setTipoOperatore(result.getString("TP_OPERATORE"));
					operatore.setIntestatarioperatoriobj(intestatarioperatoreCorrente);
				}
			}

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Got exception of kind " + e.getClass().getName());
			throw e;
		} finally {
			ps.close();
			conn.close();
			se.close();
		}
		if (Tracer.isDebugEnabled(getClass().getName())) {
			if (operatore != null) {
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.username = " + operatore.getUsername());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.corporate = " + operatore.getCorporate());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.password = " + operatore.getPassword());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.bloccato = " + operatore.getLocked());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Operatore.numtentfalliti = " + operatore.getNumFailedlogon());
			} else {
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCorporateAndUpperUsernameWithUR", "Non esiste un operatore avente username = " + upperUsername);
			}

		}
		return operatore;
	}
*/
	/**
	 * Recupera da DB l'operatore avente il codice fiscale in ingresso.
	 * 
	 * @param context
	 *            il BackEndContext
	 * @param upperCodiceFiscale
	 *            il codice fiscale maiuscolo di un operatore
	 * @return OperatoriPojo dell'operatore avente il codice fiscale in
	 *         ingresso.
	 * @throws Exception
	 */
	private OperatoriPojo selectFromOperatoreByUpperCodiceFiscaleWithUR(BackEndContext context, String upperCodiceFiscale) throws Exception {

		StatelessSessionManager sm = context.getStatelessSessionManager();
		OperatoriPojo operatore = null;
	
		try {

			Query q = sm.createHibernateQuery("select OP from Operatori OP " +
					"inner join OP.intestatarioperatori IOP " +
					"inner join IOP.intestatario INTEST " +
					"where OP.codiceFiscale = :codiceFiscale");
			q.setString("codiceFiscale", upperCodiceFiscale);
			operatore = (Operatori) q.uniqueResult();
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "selectFromOperatoreByUpperCodiceFiscaleWithUR", "Got exception of kind " + e.getClass().getName());
			throw e;
		}

		if (Tracer.isDebugEnabled(getClass().getName())) {
			if (operatore != null) {
				Tracer.debug(OperatorserviceDaoDB.class.getName(), "selectFromOperatoreByUpperCodiceFiscaleWithUR", "Operatore.username = " + operatore.getUsername());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCodiceFiscaleWithUR", "Operatore.password = " + operatore.getPassword());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCodiceFiscaleWithUR", "Operatore.bloccato = " + operatore.getLocked());
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCodiceFiscaleWithUR", "Operatore.numtentfalliti = " + operatore.getNumFailedlogon());
			} else {
				Tracer.debug(this.getClass().getName(), "selectFromOperatoreByUpperCodiceFiscaleWithUR", "Non esiste un operatore avente codiceFiscale = " + upperCodiceFiscale);
			}

		}
		return operatore;
	}

	/**********************
	 * Tested ok by Goutam on 1st Sep 2006
	 * 
	 * @param fec
	 * @param corporate
	 * @return
	 * @throws Exception
	 */
	public Collection getAdminOperator(BackEndContext fec, String corporate) throws Exception {

		try {
			StatelessSessionManager sm = fec.getStatelessSessionManager();
			Criteria criteria = sm.createHibernateCriteria(Operatori.class);
			if (corporate != null && !corporate.equals(""))
				criteria.add(Restrictions.eq("corporate", corporate));
			// TODO PAZZIK CONTROLLARE
			// criteria.add(Restrictions.eq("flagOperatorType",
			// BackEndConstant.TYPE_ADMIN_OPERATOR));
			Collection result = criteria.list();
			return result;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getAdminOperator", null);
			throw e;
		}

	}//

	/*******************
	 * Tested ok by Goutam on 1st Sep 2006 This will return only those rapportis
	 * that are associated with an Operator
	 * 
	 * @param fec
	 * @param corporate
	 * @param rapporto
	 * @return
	 * @throws Exception
	 */
	public DTOCollection listOperatorByRapportiAndCorporate(BackEndContext fec, String corporate, String rapporto) throws Exception {
		try {
			StatelessSessionManager sm = fec.getStatelessSessionManager();

			String hql = " select ope from  " + " it.nch.is.fo.profilo.Rapporti rapporti," + " it.nch.is.fo.profilo.Operatorirapporti operatorirapporti," + " it.nch.is.fo.profilo.Operatori ope"
					+ " where rapporti.rapporto=operatorirapporti.rapporto and" + " ope.operatore=operatorirapporti.operatore and" + " :rapporto = rapporti.rapporto ";// and"
			// + " :corporate = ope.corporate ";

			Query query = sm.createHibernateQuery(hql);

			query.setString("rapporto", rapporto);
			// query.setString("corporate", corporate);

			Collection result = query.list();
			return new DTOCollectionImpl(result, true);
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "listOperatorByRapporti", "daodb");
			throw e;
		}
	} // /

	/*************************
	 * Probably this is not required NOW
	 * 
	 * @param context
	 * @param funzionioperatori
	 * @return
	 * @throws Exception
	 */
	/*
	 * public Collection loadOperatorFunctions(BackEndContext context, String
	 * corporate,String operatore) throws Exception { try {
	 * StatelessSessionManager sm = context.getStatelessSessionManager(); Query
	 * q=sm.createHibernateQuery(
	 * "Select funzionioperatori from it.nch.is.fo.profilo.Funzionioperatori " +
	 * "where funzionioperatori.corporate=:corporate AND funzionioperatori.operatore=:operatore"
	 * ); q.setString("corporate",corporate);
	 * q.setString("operatore",operatore); Collection result=q.list(); return
	 * result; }catch(Exception e) { Tracer.error(this.getClass().getName(),
	 * "load ","loadOperatorFunction", null); throw e; } }//end
	 */
	/***********************
	 * Probably This is not required Now
	 * 
	 * @param context
	 * @param operatorirapporti
	 * @return
	 * @throws Exception
	 */
	/*
	 * public Collection loadOperatorRapporti(BackEndContext context, String
	 * corporate,String operatore) throws Exception { try {
	 * StatelessSessionManager sm = context.getStatelessSessionManager(); Query
	 * q=sm.createHibernateQuery(
	 * "Select operatorirapporti from it.nch.is.fo.profilo.Operatorirapporti " +
	 * "where operatorirapporti.corporate=:corporate AND operatorirapporti.operatore=:operatore"
	 * ); q.setString("corporate",corporate);
	 * q.setString("operatore",operatore); Collection result=q.list(); return
	 * result; }catch(Exception e) { Tracer.error(this.getClass().getName(),
	 * "insertOrUpdate","loadoperatorirapporti", null); throw e; } }//end
	 */

	// TEST Method
	public DTO testDB(BackEndContext context, DTO dto) throws RemoteException {
		return null;
	}

	/**
	 * 
	 * @param fec
	 * @param dto
	 * @param operatorType
	 * @return
	 * @throws Exception
	 */
	public DTOCollection listFunctionsByCorporate(BackEndContext fec, DTO dto, String operatorType) throws Exception {
		Collection listFunction = new ArrayList();
		Operatori operatori = (Operatori) dto.getBusinessObject();
		Connection conn = null;
		try {
			// TODO PAZZIK TESTARE
			String sql = "select t1.codfunzione,t1.descrizione,t1.codservizio,t1.intestatario,t2.operatore, t1.priority from "
					+ "(SELECT f.codfunzione as codfunzione,g.descrizione as descrizione, f.intestatario as intestatario,g.codservizio as codservizio, g.priority"
					+ " FROM FUN_INTEST  f, FUNZIONI g where f.intestatario=? and g.tipoperatore like \'%?%\' and f.codfunzione=g.codfunzione ) t1"
					+ " left outer join (select operatore,codfunzione from funzionioperatori  where operatore=?"
					+ " AND INTESTATARIO=?) t2 on t1.codfunzione=t2.codfunzione order by t1.codservizio, t1.priority";

			StatelessSessionManager sm = fec.getStatelessSessionManager();
			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
			Tracer.debug(this.getClass().getName(), "getFilteredFunz", "query = " + sql);
			SessionFactory sef = sessionManager.sef;
			Tracer.debug(this.getClass().getName(), "getFilteredServFunz", "Session Factory is null? " + (sef == null));

			if (sef != null) {
				Session se = sef.getCurrentSession();
				// Session se = sef.openSession();
				conn = se.connection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, operatori.getCorporate());
				ps.setString(2, operatorType);
				ps.setString(3, operatori.getOperatore());
				ps.setString(4, operatori.getCorporate());
				ResultSet result = ps.executeQuery();

				FunzionioperatoriVOImpl fun = null;
				if (result != null) {
					while (result.next()) {
						fun = new FunzionioperatoriVOImpl();
						fun.setFunctionCode(new String(result.getString(1)));
						fun.setDescription(new String(result.getString(2)));
						fun.setServiceCode(new String(result.getString(3)));
						fun.setCorporate(new String(result.getString(4)));
						String op = result.getString(5);
						if (op != null)
							fun.setOperatore(op);
						// aggiungo l'elemento alla collection
						listFunction.add(fun);

					}

				}
				ps.close();
				conn.close();
				se.close();
			}

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getFilteredServFunz", e.getMessage(), e);
			conn.close();
			throw e;
		}
		return new DTOCollectionImpl(listFunction, true);
	}

	/**
	 * Restiruisce le funzioni menu e pulsanti dell'operatore di BackOffice
	 * 
	 * @param fec
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DTOCollection listFunctionsByBackOfficeOperator(BackEndContext fec, DTO dto) throws Exception {
		List totalFunctions = new ArrayList();

		Operatori operatore = (Operatori) dto.getBusinessObject();

		try {
			StatelessSessionManager sm = fec.getStatelessSessionManager();

			HashMap enabledParameters = new HashMap();
			enabledParameters.put("applicazione", CommonConstant.APPLICAZIONE_BACKOFFICE);
			enabledParameters.put("tipoperatorefunzioni", CommonConstant.TIPOPERATORE_BACKOFFICE_FUNZIONI);
			enabledParameters.put("tipoperatorepulsanti", CommonConstant.TIPOPERATORE_BACKOFFICE_FUNZIONIPULSANTI);
			enabledParameters.put("corporate", operatore.getCorporate());
			enabledParameters.put("operatore", operatore.getOperatore());

			// Ottengo prima tutte le funzioni abilitate
			// in modo che quando andr� a costruire la lista ordinata
			// se la funzione � presente in questa list allora valorizzo
			// l'operatore
			String enabledSQL = "" + "select fu.CODFUNZIONE, ar.DESCRIZIONE, se.DESCRIZIONE, fu.DESCRIZIONE, fu.CODSERVIZIO, fu.TIPOPERATORE, fu.PRIORITY, op.OPERATORE "
					+ "  from APPLICAZIONI ap, AREA ar, SERVIZI se, FUNZIONI fu, FUN_INTEST fi, FUNZIONIOPERATORI fo, OPERATORI op " + " where ap.DESCRIZIONE = :applicazione "
					+ "   and ap.CODAPPLICAZIONE = ar.APPLICAZIONE " + "   and ar.CODAREA = se.CODAREA " + "   and se.CODSERVIZIO = fu.CODSERVIZIO " + "   and fu.CODFUNZIONE = fi.CODFUNZIONE "
					+ "   and (fi.CODFUNZIONE = fo.CODFUNZIONE and fi.INTESTATARIO = fo.INTESTATARIO) " + "   and (fo.INTESTATARIO = op.INTESTATARIO and fo.OPERATORE = op.OPERATORE) "
					+ "   and fu.TIPOPERATORE in (:tipoperatorefunzioni, :tipoperatorepulsanti) " + "   and fu.CODFUNZIONE not in (" + CommonConstant.DEFAULT_BACKOFFICE_FUNCTION_EXCLUDED_RANGE + ") "
					+ "   and fi.INTESTATARIO = :corporate " + "   and fo.OPERATORE = :operatore " + " order by fu.CODSERVIZIO, fu.CODFUNZIONE, fu.PRIORITY ASC ";

			Collection enabledResult = sm.createSQLQuery(enabledSQL, enabledParameters);

			// Travaso il contenuto della lista in una mappa per avere accesso
			// pi� rapido durante il controllo della lista totale
			HashMap enabledMap = new HashMap();
			if (enabledResult != null && !enabledResult.isEmpty()) {
				Iterator iter = enabledResult.iterator();
				while (iter.hasNext()) {
					Object[] obj = (Object[]) iter.next();
					enabledMap.put(obj[0], obj);
				}
			}

			// Ottengo la lista completa delle funzioni per l'intestatario
			// ordinate
			HashMap totalParameters = new HashMap();
			totalParameters.put("applicazione", BackEndConstant.APPLICAZIONE_BACKOFFICE);
			totalParameters.put("tipoperatorefunzioni", CommonConstant.TIPOPERATORE_BACKOFFICE_FUNZIONI);
			totalParameters.put("tipoperatorepulsanti", CommonConstant.TIPOPERATORE_BACKOFFICE_FUNZIONIPULSANTI);
			totalParameters.put("corporate", operatore.getCorporate());

			String totalSQL = "" + "select fu.CODFUNZIONE, ar.DESCRIZIONE as AREA, se.DESCRIZIONE as SERVIZIO, fu.DESCRIZIONE as FUNZIONE, fu.CODSERVIZIO, fu.TIPOPERATORE, fu.PRIORITY "
					+ "  from APPLICAZIONI ap, AREA ar, SERVIZI se, FUNZIONI fu, FUN_INTEST fi " + " where ap.DESCRIZIONE = :applicazione " + "   and ap.CODAPPLICAZIONE = ar.APPLICAZIONE "
					+ "   and ar.CODAREA = se.CODAREA " + "   and se.CODSERVIZIO = fu.CODSERVIZIO " + "   and fu.CODFUNZIONE = fi.CODFUNZIONE "
					+ "   and fu.TIPOPERATORE in (:tipoperatorefunzioni, :tipoperatorepulsanti) " + "   and fu.CODFUNZIONE not in (" + CommonConstant.DEFAULT_BACKOFFICE_FUNCTION_EXCLUDED_RANGE + ") "
					+ "   and fi.INTESTATARIO = :corporate " + " order by fu.CODSERVIZIO, fu.CODFUNZIONE, fu.PRIORITY ASC ";

			Collection totalResult = sm.createSQLQuery(totalSQL, totalParameters);

			FunzionioperatoriVOImpl fun = null;

			int INDEX_CODICEFUNZIONE = 0;
			int INDEX_DESCAREA = 1;
			int INDEX_DESCSERVIZIO = 2;
			int INDEX_DESCFUNZIONE = 3;
			int INDEX_CODSERVIZIO = 4;
			int INDEX_TIPOPERATORE = 5;
			int INDEX_PRIORITY = 6;
			int INDEX_OPERATORE = 7; // solo per le funzioni abilitate

			if (totalResult != null) {
				Iterator iter = totalResult.iterator();
				while (iter.hasNext()) {
					Object[] obj = (Object[]) iter.next();
					fun = new FunzionioperatoriVOImpl();

					fun.setFunctionCode((String) obj[INDEX_CODICEFUNZIONE]);
					fun.setCorporate(operatore.getCorporate());
					fun.setServiceCode((String) obj[INDEX_CODSERVIZIO]);
					fun.setTipoperatore((String) obj[INDEX_TIPOPERATORE]);
					// Compongo la descrizione nel formato
					// AREA - SERVIZIO - Funzione
					String descrizione = createBackOfficeFunctionDescription((String) obj[INDEX_DESCAREA], (String) obj[INDEX_DESCSERVIZIO], (String) obj[INDEX_DESCFUNZIONE], fun.getTipoperatore());
					fun.setDescription(descrizione);

					// Controllo se la funzione � tra le abilitate
					if (enabledMap.get(obj[INDEX_CODICEFUNZIONE]) != null) {
						// La funzione � abilitata, quindi imposto l'operatore.
						Object[] tmp = (Object[]) enabledMap.get(obj[INDEX_CODICEFUNZIONE]);
						fun.setOperatore((String) tmp[INDEX_OPERATORE]);
					} else {
						fun.setOperatore(null);
					}

					// aggiungo l'elemento alla collection
					totalFunctions.add(fun);
				}
			}

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getFilteredServFunz", e.getMessage(), e);
			throw e;
		}
		return new DTOCollectionImpl(totalFunctions, true);
	}

	/**
	 * 
	 * @param area
	 * @param servizio
	 * @param funzione
	 * @param tipoperatore
	 * @return
	 */
	private String createBackOfficeFunctionDescription(String area, String servizio, String funzione, String tipoperatore) {
		String descrizione = "";
		descrizione += area.toUpperCase() + " - ";
		if (tipoperatore.equals(CommonConstant.TIPOPERATORE_BACKOFFICE_FUNZIONIPULSANTI)) {
			// Devo mettere il nome del serivizio
			descrizione += capitalize(servizio, true) + " - ";
		}
		descrizione += capitalize(funzione, true);
		return descrizione;
	}

	/**
	 * 
	 * @param string
	 * @param capitalize
	 * @return
	 */
	private String capitalize(String string, boolean capitalize) {
		if (string.length() == 0) {
			return "";
		}
		String c = string.substring(0, 1);

		if (capitalize) {
			return c.toUpperCase() + capitalize(string.substring(1), c.equals(" "));
		} else {
			return c.toLowerCase() + capitalize(string.substring(1), c.equals(" "));
		}
	}

	/**
	 * Restituisce le funzioni dei pulsanti abilitate dell'operatore di
	 * BackOffice in ingreso cio� quelle aventi TIPOPERATORE uguale a BK_BU
	 * sulla tabella FUNZIONI.
	 * 
	 * @param bec
	 *            il BackEndContext
	 * @param dto
	 *            DTO contenente un oggetto di tipo Operatori
	 * @return una DTOCollectionImpl contenente la lista di
	 *         FunzionioperatoriVOImpl
	 * @throws Exception
	 */
	public DTOCollection listEnabledFunctionsButtonByBackOfficeOperator(BackEndContext bec, DTO dto) throws Exception {
		Operatori operatore = null;
		FrontEndContext fec = (FrontEndContext) bec;

		if (dto.getBusinessObject() != null)
			operatore = (Operatori) dto.getBusinessObject();
		if (operatore == null)
			if (dto.getPojo() != null)
				operatore = (Operatori) dto.getPojo();
		if (operatore == null)
			if (fec.getOperatore() != null && fec.getOperatore().getBusinessObject() != null)
				operatore = (Operatori) fec.getOperatore().getBusinessObject();
		if (operatore == null)
			if (fec.getOperatore() != null && fec.getOperatore().getPojo() != null)
				operatore = (Operatori) fec.getOperatore().getPojo();

		if (operatore == null)
			return null;

		Collection enabledResult = null;
		try {
			StatelessSessionManager sm = ((BackEndContext) fec).getStatelessSessionManager();

			HashMap enabledParameters = new HashMap();
			enabledParameters.put("applicazione", CommonConstant.APPLICAZIONE_BACKOFFICE);
			enabledParameters.put("tipoperatorepulsanti", CommonConstant.TIPOPERATORE_BACKOFFICE_FUNZIONIPULSANTI);
			enabledParameters.put("corporate", operatore.getCorporate());
			enabledParameters.put("operatore", operatore.getOperatore());

			// Ottengo prima tutte le funzioni abilitate
			// in modo che quando andr� a costruire la lista ordinata
			// se la funzione � presente in questa list allora valorizzo
			// l'operatore
			String enabledSQL = ""
					+ "select fu.CODFUNZIONE, ar.DESCRIZIONE as AREA, se.DESCRIZIONE as SERVIZIO, fu.DESCRIZIONE as FUNZIONE, fu.CODSERVIZIO, fu.TIPOPERATORE, fu.PRIORITY, op.OPERATORE "
					+ "  from APPLICAZIONI ap, AREA ar, SERVIZI se, FUNZIONI fu, FUN_INTEST fi, FUNZIONIOPERATORI fo, OPERATORI op "
					+ " where ap.DESCRIZIONE = :applicazione " // TODO PAZZIK
																// VERIFICARE SE
																// SERVE ANCORA
					+ "   and ap.CODAPPLICAZIONE = ar.APPLICAZIONE " + "   and ar.CODAREA = se.CODAREA " + "   and se.CODSERVIZIO = fu.CODSERVIZIO " + "   and fu.CODFUNZIONE = fi.CODFUNZIONE "
					+ "   and (fi.CODFUNZIONE = fo.CODFUNZIONE and fi.INTESTATARIO = fo.INTESTATARIO) " + "   and (fo.INTESTATARIO = op.INTESTATARIO and fo.OPERATORE = op.OPERATORE) "
					+ "   and fu.TIPOPERATORE = :tipoperatorepulsanti " + "   and fu.CODFUNZIONE not in (" + CommonConstant.DEFAULT_BACKOFFICE_FUNCTION_EXCLUDED_RANGE + ") "
					+ "   and fi.INTESTATARIO = :corporate " + "   and fo.OPERATORE = :operatore " + " order by fu.CODSERVIZIO, fu.CODFUNZIONE, fu.PRIORITY ASC ";

			Collection result = sm.createSQLQuery(enabledSQL, enabledParameters);

			int INDEX_CODICEFUNZIONE = 0;
			int INDEX_DESCAREA = 1;
			int INDEX_DESCSERVIZIO = 2;
			int INDEX_DESCFUNZIONE = 3;
			int INDEX_CODSERVIZIO = 4;
			int INDEX_TIPOPERATORE = 5;
			int INDEX_PRIORITY = 6;
			int INDEX_OPERATORE = 7;

			if (result != null) {
				FunzionioperatoriVOImpl fun = null;
				enabledResult = new ArrayList();
				Iterator iter = result.iterator();
				while (iter.hasNext()) {
					Object[] obj = (Object[]) iter.next();
					fun = new FunzionioperatoriVOImpl();

					fun.setFunctionCode((String) obj[INDEX_CODICEFUNZIONE]);
					fun.setCorporate(operatore.getCorporate());
					fun.setServiceCode((String) obj[INDEX_CODSERVIZIO]);
					fun.setTipoperatore((String) obj[INDEX_TIPOPERATORE]);
					// Compongo la descrizione nel formato
					// AREA - SERVIZIO - Funzione
					String descrizione = createBackOfficeFunctionDescription((String) obj[INDEX_DESCAREA], (String) obj[INDEX_DESCSERVIZIO], (String) obj[INDEX_DESCFUNZIONE], fun.getTipoperatore());
					fun.setDescription(descrizione);

					fun.setOperatore((String) obj[INDEX_OPERATORE]);

					// aggiungo l'elemento alla collection
					enabledResult.add(fun);
				}
			}

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getFilteredServFunz", e.getMessage(), e);
			throw e;
		}
		return new DTOCollectionImpl(enabledResult, true);
	}

	/**
	 * Il metodo controlla se esiste un operatore con quella username nella
	 * corporate impostata.
	 * 
	 * @return true se ci sono username in quella corporate, altrimenti false.
	 */
//	public String getOperatorKeyByUsernameAndCorporate(BackEndContext context, DTO dto) throws Exception {
//
//		DTOCollection dtocoll = null;
//		StatelessSessionManager sm = context.getStatelessSessionManager();
//		SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
//		Connection conn = null;
//		String operatorekey = null;
//		PreparedStatement ps = null;
//		ResultSet result = null;
//		try {
//
//			StringBuffer sql = new StringBuffer("SELECT OPERATORE FROM OPERATORI WHERE USERNAME = ? AND INTESTATARIO = ? ");
//			SessionFactory sef = sessionManager.sef;
//
//			if (sef != null) {
//				Operatori operatore = (Operatori) dto.getBusinessObject();
//				String corporate = operatore.getCorporateIForm();
//				String username = operatore.getUsernameIForm();
//
//				Session se = sef.getCurrentSession();
//				// Session se = sef.openSession();
//				conn = se.connection();
//				String query = sql.toString();
//				ps = conn.prepareStatement(query);
//				ps.setString(1, username);
//				ps.setString(2, corporate);
//
//				result = ps.executeQuery();
//
//				if (result != null && result.next()) {
//					operatorekey = result.getString(1);
//				}
//
//			}
//			/*
//			 * StatelessSessionManager sm =
//			 * context.getStatelessSessionManager(); Operatori operatore =
//			 * (Operatori) dto.getBusinessObject();
//			 * 
//			 * String corporate = operatore.getCorporateIForm(); String username
//			 * = operatore.getUsernameIForm();
//			 * 
//			 * String hql = " select ope from  " +
//			 * " it.nch.is.fo.profilo.Operatori ope" +
//			 * " where :username=ope.username and" +
//			 * " :corporate = ope.corporate ";
//			 * 
//			 * 
//			 * Query query = sm.createHibernateQuery(hql);
//			 * 
//			 * query.setString("username", username);
//			 * query.setString("corporate", corporate);
//			 * 
//			 * 
//			 * Collection result = query.list(); dtocoll = new
//			 * DTOCollectionImpl(result, true); return dtocoll;
//			 */
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "checkOperatorByUsernameAndCorporate", e.getMessage(), e);
//			dtocoll = null;
//			throw e;
//		} finally {
//			if (ps != null) {
//				ps.close();
//			}
//			if (conn != null) {
//				conn.close();
//			}
//			if (result != null) {
//				result.close();
//			}
//		}
//		return operatorekey;
//
//	}

	/**
	 * Estrae dalla tabella FUNZIONIOPERATORI tutte le funzioni a cui �
	 * abilitata la coppia intestatario/operatore in ingresso.
	 * 
	 * @param context
	 *            il BackEndContext
	 * @param dto
	 *            un DTO di tipo Operatori
	 * @return la DTOCollection degli oggetti Funzionioperatori che
	 *         rappresentano le funzioni a cui � abilitata la coppia
	 *         intestatario/operatore in ingresso.
	 * @throws Exception
	 */
// FATTO	
//	public DTOCollection listEnabledFunctions(BackEndContext context, DTO dto) throws Exception {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		DTOCollection dtocoll = null;
//		Collection listaAbilitazioni = new ArrayList();
//		try {
//			StatelessSessionManager sm = context.getStatelessSessionManager();
//
//			Operatori oper = (Operatori) dto.getBusinessObject();
//
//			String corporate = oper.getCorporateIForm();
//			String operatore = oper.getOperatoreIForm();
//
//			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
//
//			SessionFactory sef = sessionManager.sef;
//			String sql = " select CODFUNZIONE from  " + " FUNZIONIOPERATORI " + " where intestatario = ? and" + " OPERATORE = ? with ur ";
//
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "listEnabledFunctions", "query = " + sql);
//
//			if (sef != null) {
//				Session se = sef.getCurrentSession();
//				conn = se.connection();
//				ps = conn.prepareStatement(sql);
//				ps.setString(1, corporate);
//				ps.setString(2, operatore);
//
//				ResultSet result = ps.executeQuery();
//
//				Funzionioperatori fun = null;
//				Funzioni f = new Funzioni();
//				if (result != null) {
//					while (result.next()) {
//						fun = new Funzionioperatori();
//						fun.setFunctionCode(new String(result.getString(1).trim()));
//						fun.setFunzioniobj(f);
//						// aggiungo l'elemento alla collection
//						listaAbilitazioni.add(fun);
//
//					}
//
//				}
//
//				// Aggiungo la voce MANUALE ONLINE tra le funzioni abilitate
//				/*
//				 * fun = new Funzionioperatori();
//				 * fun.setFunctionCode("SE_0109"); fun.setFunzioniobj(f);
//				 * listaAbilitazioni.add(fun); // SE_0058 fun = new
//				 * Funzionioperatori(); fun.setFunctionCode("SE_0058");
//				 * fun.setFunzioniobj(f); listaAbilitazioni.add(fun);
//				 */
//
//			}
//
//			dtocoll = new DTOCollectionImpl(listaAbilitazioni, true);
//			return dtocoll;
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listEnabledFunctions", e.getMessage(), e);
//			throw e;
//		}
//
//		finally {
//			ps.close();
//			conn.close();
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "listEnabledFunctions", "FINE", null);
//		}
//
//	}

	/**
	 * Estrae dalla tabella FUNZIONIOPERATORI tutti i CODFUNZIONE a cui �
	 * abilitata la coppia intestatario/operatore. Legge dalla tabella OPERATORI
	 * la data di scadenza della password dell'operatore e verifica se ad oggi �
	 * scaduta. Se la password � scaduta aggiungo alla lista delle funzioni da
	 * restituire solo la funzione cambio password (sempre che sia tra le
	 * funzioni abilitate). Se la password non � scaduta aggiungo alla lista
	 * delle funzioni da restituire tutte le funzioni a cui � abilitata da DB la
	 * coppia intestatario/operatore. In ogni caso aggiunge alla lista delle
	 * funzioni da restituire le funzioni MANUALE ONLINE che non sono censite su
	 * DB.
	 * 
	 * @param context
	 *            il BackEndContext
	 * @param dto
	 *            DTO contenente un oggetto di tipo Operatori
	 * @return una DTOCollection contenente una lista di Funzionioperatori per
	 *         cui la coppia intestatario/operatore � abilitata.
	 * @throws Exception
	 */
// FATTO	
//	public DTOCollection newListEnabledFunctions(BackEndContext context, DTO dto) throws Exception {
//
//		Connection conn = null;
//		PreparedStatement ps = null;
//		DTOCollection dtocoll = null;
//		Session se = null;
//		Collection<Funzionioperatori> listaAbilitazioni = new ArrayList<Funzionioperatori>();
//		try {
//			SessionManagerHibernate sessionManager = (SessionManagerHibernate) context.getStatelessSessionManager();
//
//			Operatori oper = (Operatori) dto.getBusinessObject();
//			String corporate = oper.getCorporateIForm();
//			String operatore = oper.getOperatoreIForm();
//
//			SessionFactory sef = sessionManager.sef;
//
//			String sql_expDate = "SELECT DATASCADENZA FROM OPERATORI WHERE OPERATORE = ?  ";
//			String sql = "select CODFUNZIONE from  FUNZIONIOPERATORI where intestatario = ? and OPERATORE = ?  ";
//
//			if (Tracer.isDebugEnabled(getClass().getName())) {
//				Tracer.debug(this.getClass().getName(), "listEnabledFunctions", "query1 = " + sql_expDate);
//				Tracer.debug(this.getClass().getName(), "listEnabledFunctions", "query2 = " + sql);
//			}
//
//			if (sef != null) {
//				se = sef.getCurrentSession();
//				conn = se.connection();
//
//				ps = conn.prepareStatement(sql_expDate);
//				ps.setString(1, operatore);
//
//				ResultSet rSet = ps.executeQuery();
//
//				boolean isPasswordExpired = true;
//				if (rSet != null && rSet.next()) {
//					Date expDate = rSet.getDate(1);
//					isPasswordExpired = expDate == null || expDate.before(new Date());
//				}
//
//				ps = conn.prepareStatement(sql);
//				ps.setString(1, corporate);
//				ps.setString(2, operatore);
//
//				ResultSet result = ps.executeQuery();
//
//				Funzionioperatori fun = null;
//				Funzioni f = new Funzioni();
//				if (result != null) {
//					String codFunzione;
//					if (isPasswordExpired) {
//						while (result.next()) {
//							codFunzione = result.getString(1).trim();
//							// se la password � scaduta aggiungo alla lista
//							// abilitazioni solo
//							// la funzione cambio password (sempre che sia tra
//							// le funzioni abilitate)
//							//
//							if (codFunzione.equals("XA3") || codFunzione.equals("XA4") || codFunzione.equals("CPW")) {
//								fun = new Funzionioperatori();
//								fun.setFunctionCode(codFunzione);
//								fun.setFunzioniobj(f);
//								// aggiungo l'elemento alla collection
//								listaAbilitazioni.add(fun);
//							}
//						}
//					} else {
//						while (result.next()) {
//							codFunzione = result.getString(1).trim();
//							fun = new Funzionioperatori();
//							fun.setFunctionCode(codFunzione);
//							fun.setFunzioniobj(f);
//							// aggiungo l'elemento alla collection
//							listaAbilitazioni.add(fun);
//						}
//					}
//				}
//
//				// Aggiungo la voce MANUALE ONLINE tra le funzioni abilitate
//				/*
//				 * fun = new Funzionioperatori();
//				 * fun.setFunctionCode("SE_0109"); fun.setFunzioniobj(f);
//				 * listaAbilitazioni.add(fun); // SE_0058 fun = new
//				 * Funzionioperatori(); fun.setFunctionCode("SE_0058");
//				 * fun.setFunzioniobj(f); listaAbilitazioni.add(fun);
//				 */
//
//			}
//
//			dtocoll = new DTOCollectionImpl(listaAbilitazioni, true);
//			return dtocoll;
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listEnabledFunctions", e.getMessage(), e);
//			throw e;
//		} finally {
//			ps.close();
//			conn.close();
//			se.close();
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "listEnabledFunctions", "FINE", null);
//		}
//	}

	/**
	 * 
	 * @param context
	 * @param oper
	 * @param isAdministratorLoggedIn
	 * @return
	 * @throws Exception
	 */
	public List<String> listHistoryPassword(BackEndContext context, Operatori oper, boolean isAdministratorLoggedIn) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		List<String> listaPassword = new ArrayList<String>();

		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			// Operatori oper = (Operatori) dto.getBusinessObject();
			// String operatore = oper.getOperatoreIForm();
			int fetchSize;
			if (isAdministratorLoggedIn) {
				fetchSize = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_PASSWORD_HISTORY_SIZE);
			} else {
				fetchSize = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_PASSWORD_HISTORY_SIZE);
			}

			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
			SessionFactory sef = sessionManager.sef;
			String sql = "SELECT ID, ID_OPERATORE, PASSWORD, DATACAMBIO " + "FROM STORICOPASSWORD " + "WHERE ID_OPERATORE = ? " + "ORDER BY DATACAMBIO DESC ";
			// + "FETCH FIRST ? ROW ONLY"; // solo su DB2

			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "listHistoryPassword", "query = " + sql);

			if (sef != null) {
				Session se = sef.getCurrentSession();
				conn = se.connection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, oper.getOperatore());
				// ps.setInt(2, fetchSize);

				ResultSet result = ps.executeQuery();

				String password;
				if (result != null) {
					while (result.next()) {
						password = result.getString("PASSWORD");
						// TODO: commentare la stampa della password
						if (Tracer.isDebugEnabled(getClass().getName())) {
							Tracer.debug(this.getClass().getName(), "listHistoryPassword", "aggiungo: " + result.getString("ID_OPERATORE") + " " + result.getTimestamp("DATACAMBIO") + " "
									+ result.getString("PASSWORD"));
						}
						// aggiungo l'elemento alla collection
						listaPassword.add(password);
					}
				}
			}
			return listaPassword;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "listHistoryPassword", e.getMessage(), e);
			throw e;
		} finally {
			ps.close();
			conn.close();
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "listHistoryPassword", "FINE", null);
		}

	}

	//
	// metodo alternativo a insertOrUpdate (che utilizza hibernate)
	//
//	public void updateOperatorPassword(BackEndContext context, Operatori oper) throws Exception {
//
//		Connection conn = null;
//		PreparedStatement ps = null;
//
//		try {
//			StatelessSessionManager sm = context.getStatelessSessionManager();
//
//			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
//			SessionFactory sef = sessionManager.sef;
//			String sql = "update OPERATORI set PASSWORD=?, DATASCADENZA=? where OPERATORE=?";
//
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "updatePassword", "query = " + sql);
//
//			if (sef != null) {
//				Session se = sef.getCurrentSession();
//				conn = se.connection();
//				ps = conn.prepareStatement(sql);
//				ps.setString(1, oper.getPassword());
//				ps.setDate(2, new java.sql.Date(oper.getExpirationDate().getTime())); // //
//																						// <----------------------------
//				ps.setString(3, oper.getOperatore());
//
//				int updCount = ps.executeUpdate();
//				if (Tracer.isDebugEnabled(getClass().getName()))
//					Tracer.debug(this.getClass().getName(), "updatePassword", "updated " + updCount + " rows.");
//			}
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "updatePassword", e.getMessage(), e);
//			throw e;
//		} finally {
//			ps.close();
//			conn.close();
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "updatePassword", "FINE", null);
//		}
//
//	}

//	public Password insertOrUpdatePassword(BackEndContext context, Password obj) throws Exception {
//
//		try {
//			
//			StatelessSessionManager sm = context.getStatelessSessionManager();
//
//			sm.saveOrUpdate(obj);
//			sm.flush();
//			sm.refresh(obj);
//
//			return obj;
//			
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "insertOrUpdatePassword", "Insert/Update Password", null);
//			throw e;
//		}
//
//	}

//	public DTOCollection listEnabledSubholdigs(BackEndContext context, DTO dto) throws Exception {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		DTOCollection dtocoll = null;
//		Collection listaSubholdings = new ArrayList();
//		try {
//			StatelessSessionManager sm = context.getStatelessSessionManager();
//
//			Operatori oper = (Operatori) dto.getBusinessObject();
//
//			String corporate = oper.getCorporateIForm();
//			String operatore = oper.getOperatoreIForm();
//
//			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
//
//			SessionFactory sef = sessionManager.sef;
//			String sql = "select t1.INTESTATARIO,t1.RAGIONESOCIALE,t1.DENOMINAZIONE,t2.OPERATORE from  "
//					+ "INTESTATARI t1 left outer join (select intestatario,operatore from intest_oper where operatore=?)t2 on t1.intestatario=t2.intestatario "
//					+ "where t1.gruppo=? and t1.tipointestatario=" + "'" + CommonConstant.TYPE_SUBHOLDING_COMPANY + "'";
//
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "listEnabledSubholdigs", "query = " + sql);
//
//			if (sef != null) {
//				Session se = sef.getCurrentSession();
//				conn = se.connection();
//				ps = conn.prepareStatement(sql);
//				ps.setString(1, operatore);
//				ps.setString(2, corporate);
//
//				ResultSet result = ps.executeQuery();
//
//				Intestatarioperatori operint = null;
//				IntestatarioperatoriId operintId = null;
//				Intestatari i = null;
//				if (result != null) {
//					while (result.next()) {
//						operintId = new IntestatarioperatoriId();
//						operintId.setIntestatario(result.getString(1));
//						String op = result.getString(4);
//						if (op != null) {
//							operintId.setOperatore(op);
//						}
//						operint = new Intestatarioperatori(operintId);
//						i = new Intestatari();
//						i.setId(new Long(result.getLong(1)));
//						i.setRagionesociale(result.getString(2));
//						i.setDenominazione(result.getString(3));
//						operint.setIntestatariobj(i);
//
//						listaSubholdings.add(operint);
//					}
//				}
//
//			}
//			dtocoll = new DTOCollectionImpl(listaSubholdings, true);
//			return dtocoll;
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listEnabledSubholdigs", e.getMessage(), e);
//			throw e;
//		}
//
//		finally {
//			ps.close();
//			conn.close();
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "listEnabledSubholdigs", "FINE", null);
//		}
//	}

//	public void insertIntestatariOperatoriSubholding(BackEndContext context, String operatore, String intestatario) throws Exception {
//		try {
//			StatelessSessionManager sm = context.getStatelessSessionManager();
//			IntestatarioperatoriId intOperId = new IntestatarioperatoriId();
//			intOperId.setIntestatario(intestatario);
//			intOperId.setOperatore(operatore);
//			Intestatarioperatori intOper = new Intestatarioperatori(intOperId);
//			sm.saveOrUpdate(intOper);
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "insertIntestatariOperatoriSubholding", "insertIntestatariOperatoriSubholding", null);
//			throw e;
//		}
//
//	}

//	public void insertRapportiSubholding(BackEndContext context, String operatore, String intestatario) throws Exception {
//		Connection conn = null;
//		PreparedStatement ps = null;
//
//		try {
//			StatelessSessionManager sm = context.getStatelessSessionManager();
//			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
//
//			SessionFactory sef = sessionManager.sef;
//			String sql = "insert into OPERATORIRAPPORTI (INTESTATARIO,OPERATORE,RAPPORTO) select INTESTATARIO," + "'" + operatore + "'" + ",RAPPORTO from RAPPORTI where INTESTATARIO=?";
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), " insertRapportiSubholding", "query = " + sql);
//
//			if (sef != null) {
//				Session se = sef.getCurrentSession();
//				conn = se.connection();
//				ps = conn.prepareStatement(sql);
//				// ps.setString(1, operatore);
//				ps.setString(1, intestatario);
//
//				ps.execute();
//			}
//		}
//
//		catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "insertRapportiSubholding", "insertRapportiSubholding", null);
//			throw e;
//		}
//
//	}
//
//	public void insertFunzioniServiziSubholding(BackEndContext context, String operatore, String intestatario) throws Exception {
//		Connection conn = null;
//		PreparedStatement ps = null;
//
//		try {
//			StatelessSessionManager sm = context.getStatelessSessionManager();
//			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
//
//			SessionFactory sef = sessionManager.sef;
//			String sql = "insert into FUNZIONIOPERATORI (CODFUNZIONE,INTESTATARIO,OPERATORE) select CODFUNZIONE," + "'" + intestatario + "'" + "," + "'" + operatore + "'"
//					+ " from FUN_INTEST where INTESTATARIO=?";
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "insertFunzioniServiziSubholding", "query = " + sql);
//
//			if (sef != null) {
//				Session se = sef.getCurrentSession();
//				conn = se.connection();
//				ps = conn.prepareStatement(sql);
//				/* ps.setString(1, intestatario); */
//				/* ps.setString(2, operatore); */
//				ps.setString(1, intestatario);
//				ps.execute();
//			}
//
//			String sql1 = "insert into SERVIZIOPERATORI (CODSERVIZIO,INTESTATARIO,OPERATORE) " + "select distinct t2.CODSERVIZIO," + "'" + intestatario + "'"
//					+ ",t1.OPERATORE from FUNZIONIOPERATORI T1 INNER JOIN FUNZIONI T2 ON T1.CODFUNZIONE=T2.CODFUNZIONE  " + "where t1.INTESTATARIO=? and T1.OPERATORE=?";
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "insertFunzioniServiziSubholding", "query = " + sql1);
//
//			if (sef != null) {
//				Session se = sef.getCurrentSession();
//				conn = se.connection();
//				ps = conn.prepareStatement(sql1);
//				ps.setString(1, intestatario);
//				ps.setString(2, operatore);
//				// ps.setString(1,intestatario);
//				ps.execute();
//			}
//
//		}
//
//		catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "insertIntestatariOperatoriSubholding", "insertIntestatariOperatoriSubholding", null);
//			throw e;
//		}
//
//	}

	public void deleteIntestatariOperatori(BackEndContext context, String operatore, String intestatario) throws Exception {
		
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "deleteIntestatariOperatori", "", "");
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			String ente = selectEnteByIntestatario(context,intestatario);
			
			if (ente !=null){
				// cancello gli eventuali tributi operatori
				deleteTributiOperatore(context, intestatario, operatore, ente);
			}

			Query q = sm.createHibernateQuery("delete it.nch.is.fo.profilo.Intestatarioperatori where intestatario=:intestatario AND operatore=:operatore");

			q.setString("intestatario", intestatario);
			q.setString("operatore", operatore);
			
			q.executeUpdate();
			
			sm.flush();
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "Delete", "Delete IntestatariOperatori", null);
			throw e;
		}

	}

	public DTOCollection listOperatorsByCorporateCriteria(BackEndContext context, Intestatari intestatario) throws Exception {

		try {

			StatelessSessionManager sm = context.getStatelessSessionManager();
//			Criteria criteria = sm.createHibernateCriteria(Operatori.class);
//			criteria.createAlias("intestatari", "intestatari");
//			criteria.add(Restrictions.eq("corporate", intestatario.getCorporate()));
//			return new DTOCollectionImpl(criteria.list(), false);
			
			Query q = sm.createHibernateQuery("select OP from Operatori OP " +
					"inner join OP.intestatarioperatori i where i.intestatario.corporate = :corp");
			q.setString("corp", intestatario.getCorporate());
			return new DTOCollectionImpl(q.list(), false);
			

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "insertIntestatariOperatoriSubholding", "insertIntestatariOperatoriSubholding", null);
			throw e;
		}

	}

	//
	// metodo alternativo a insertOrUpdate (che utilizza hibernate)
	//
	public void updateOperator(BackEndContext context, Operatori oper) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		Session se=null;
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();

			SessionManagerHibernate sessionManager = (SessionManagerHibernate) sm;
			SessionFactory sef = sessionManager.sef;

			// refs #1500 - quando cambio password resetto i campi di blocco dell'operatore
			String sql = "update OPERATORI set PASSWORD=?, NULL_COLL_FALL=0, DATA_COLL_FALL=NULL, BLOCCATO=0, DATABLOCCO=NULL where OPERATORE=?";

			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "updatePassword", "query = " + sql);

			if (sef != null) {
				se = sef.getCurrentSession();
				conn = se.connection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, oper.getPassword());
//				ps.setDate(2, new java.sql.Date(oper.getExpirationDate().getTime()));
				ps.setString(2, oper.getOperatore());

				int updCount = ps.executeUpdate();
				if (Tracer.isDebugEnabled(getClass().getName()))
					Tracer.debug(this.getClass().getName(), "updateOperator", "updated " + updCount + " rows.");
			}
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "updateOperator", e.getMessage(), e);
			throw e;
		} finally {
			ps.close();
			se.close();
			conn.close();
			
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "updateOperator", "FINE", null);
		}

	}
	
	private String selectEnteByIntestatario(BackEndContext context, String intestatario) throws Exception {
		String idEnte = null;
		try {
			StatelessSessionManager sm = context.getStatelessSessionManager();
			String queryString = "select ID_ENTE from JLTENTI where INTESTATARIO=:intes";
			HashMap map = new HashMap();
			map.put("intes", intestatario);
			Collection result = sm.createSQLQuery(queryString, map);
			if (!result.isEmpty()) {
				idEnte  = (String)result.iterator().next();
			}
			return idEnte;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "selectEnteByIntestatario", e.getMessage(), e);
			throw e;
		} 
	}

}