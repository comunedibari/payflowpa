/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.Funzioniintestatari;
import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.spx.SPXAligner;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

/**
 * @author PazziK
 * 
 */
@Stateless(name="IntestatariDaoService")
public class IntestatariDaoImpl extends DaoImplJpaCmtJta<Intestatari> implements IntestatariDAO {

	private static final Logger LOGGER = LogManager.getLogger(IntestatariDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	
	@Override
	public Intestatari retrieveIntestatarioById(String id) {
		
		Intestatari retrieved = null;
		
		try {
			
			retrieved = loadById(Intestatari.class,id,em,null);
			
		} catch (Exception e) {
			
			LOGGER.error("error on  retrieveIntestatarioById, ID = " + id, e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return retrieved;
	}
	
	@Override
	public Intestatari getIntestatarioByLapl(String lapl, boolean addressRequired) {
		
		Intestatari intestatario = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("LAPL", lapl);
			
			intestatario = (Intestatari)uniqueResultByQuery("getIntestatarioByLapl",params,em);
			
			
			//per estrarre da DB anche l'indirizzo solo quando strettamente necessario
			// sfrutto il lazy loading
			if (intestatario != null && addressRequired )
				intestatario.getIndirizzipostaliobj();
		
		} catch (Exception e) {
			
			LOGGER.error("error on  getIntestatarioByLapl, LAPL = " + lapl, e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return intestatario;
	}
	


	@Override
	public Intestatari getIntestatarioBySessionId(String sessionId) {
		
		Intestatari intestatario = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();	
			
			params.put("SESSION_ID", sessionId);			
			
			intestatario = (Intestatari)uniqueResultByQuery("getIntestatarioBySessionId",params,em);			
		
		} catch (Exception e) {
			
			LOGGER.error("error on  getIntestatarioByLapl, SESSION_ID = " + sessionId, e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return intestatario;
	}
	
	@Override
	public String getLAPLEnteByCdAziendaSanitaria(String cdAziendaSanitaria){
		
		String result = null;

		try {
			
			Query query = null;
			
			String querysql = "SELECT * FROM INTESTATARI " +
							  " WHERE INTESTATARIO = ( "  +
							  "SELECT INTESTATARIO FROM ENTI WHERE CD_AZIENDA_SANITARIA =:cdAziendaSanitaria) ";
			
			query= em.createNativeQuery(querysql, Intestatari.class);
			
			query.setParameter("cdAziendaSanitaria", cdAziendaSanitaria);
			
			Intestatari intestatario = (Intestatari) query.getSingleResult();
			
			result = intestatario.getLapl();
			
		} catch (Exception e) {
			
			LOGGER.error("error on getLAPLEnteByCdAziendaSanitaria ", e);
			
			throw new DAORuntimeException(e);
		}
		
		return result;
	}

	@Override
	public Intestatari createIntestatario(Intestatari intestatario) {
		try {
			intestatario = this.create(intestatario);
		} catch (Exception e) {
			LOGGER.error("Error on createIntestatario ", e);
			throw new DAORuntimeException(e);
		}
		return intestatario;
	}
	
	@Override
	public Intestatari updateCorporate(Intestatari intestatario) {
		try {

			Tracer.debug("updateCorporate", "corporate Id: " + intestatario.getCorporate(), null);

			if (intestatario.getFunzioniIntestatari() != null) {
				Iterator<Funzioniintestatari> funIter = intestatario.getFunzioniIntestatari().iterator();
				while (funIter.hasNext()) {
					Funzioniintestatari fun = funIter.next();
					Tracer.debug("updateCorporate", "funzioniIntestatari -- codfunzione: " + fun.getCodfunzione(), null);
					Tracer.debug("updateCorporate", "funzioniIntestatari -- corporate: " + fun.getCorporate(), null);
					if (fun.getIntestatariobj() != null) {
						if (fun.getIntestatariobj().getFunzioniIntestatari() != null) {
							@SuppressWarnings("unchecked")
							Iterator<Funzioniintestatari> fIter = fun.getIntestatariobj().getFunzioniIntestatari().iterator();
							while (fIter.hasNext()) {
								Funzioniintestatari f = fIter.next();
								Tracer.debug("updateCorporate", "funzioniIntestatari -- INTESTATARIO.FUN: " + f.getCodfunzione(), null);
								Tracer.debug("updateCorporate", "funzioniIntestatari -- INTESTATARIO.CORPORATE: " + f.getCorporate(), null);
							}
						}
					}
				}
			}

			if (intestatario.getEntiobj() != null && intestatario.getEntiobj().getCodiceEnteIForm() != null
					&& intestatario.getEntiobj().getCodiceEnteIForm().length() > 0) {
				Enti ente = ((Enti) intestatario.getEntiobj());
				ente.setIntestatarioobj(intestatario);
				ente.setPrVersione(1);
				ente.setDenominazione(intestatario.getRagionesociale());
				ente.setOpInserimento("saveCorporate");
				ente.setTsInserimento(new Timestamp(System.currentTimeMillis()));

			} else {
				intestatario.setEntiobj(null);
			}

			this.save(intestatario); // TODO: siamo sicuri che non bisogna distinguere tra create e save???
			em.flush();

			if (intestatario.getEntiobj() != null && intestatario.getEntiobj().getCodiceEnteIForm() != null
					&& intestatario.getEntiobj().getCodiceEnteIForm().length() > 0) {

				/*****************************************************
				 * 
				 * Riallineamento DB SmartProxy (solo se previsto) Da fare dopo
				 * la insert e la flush dell'ente
				 * 
				 * *****************************************************/
				SPXAligner.AlignSPX(intestatario.getEntiobj().getCodiceEnteIForm(), null, null, em);

			}

			em.refresh(intestatario);

			
		} catch (Exception e) {

			LOGGER.error("error on updateCorporate ", e);

			throw new DAORuntimeException(e);
		}
		return intestatario;
	}

	@Override
	public Intestatari getIntestatarioByCorporateAndOperatore(String corporate, String operatore) {
		
		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("corporate", corporate);			
		params.put("operatore", operatore);			

		Intestatari intestatario = null;
		try {
			intestatario = (Intestatari) uniqueResultByQuery("getIntestatarioByCorporateAndOperatore", params, em);
			
		} catch (Exception e) {
			LOGGER.error("error on  getIntestatarioByCorporateAndOperatore, params = " + params, e);
			throw new DAORuntimeException(e);
		}
		return intestatario;

	}


	@Override
	public Intestatari find(String corporate) {
		
		try {
			return getById(Intestatari.class, corporate);
			
		} catch (Exception e) {
			LOGGER.error("error on  find, corporate = " + corporate, e);
			throw new DAORuntimeException(e);
		}
	}


	@Override
	public boolean checkIdFiscaleEnte(String idFiscaleEnte) {     
		try {			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("LAPL", idFiscaleEnte);			
			uniqueResultByQuery("checkIdFiscaleEnte",params,em);	
		} catch (Exception e) {			
			LOGGER.error("error on  checkIdFiscaleEnte, LAPL = " + idFiscaleEnte, e);			
			return false;			
		}
		return true;
	}



	

	

}
