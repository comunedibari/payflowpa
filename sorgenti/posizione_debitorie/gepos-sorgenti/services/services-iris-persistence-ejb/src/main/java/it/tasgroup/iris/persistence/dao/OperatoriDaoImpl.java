package it.tasgroup.iris.persistence.dao;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.profilo.IntestatarioperatoriCommon;
import it.nch.is.fo.profilo.Operatori;
import it.tasgroup.iris.persistence.dao.interfaces.OperatoriDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "OperatoriDAO")
public class OperatoriDaoImpl extends DaoImplJpaCmtJta<Operatori> implements OperatoriDAO {

	private static final Logger LOGGER = LogManager.getLogger(OperatoriDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public Operatori getOperatoreByUsername(String username) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", username);

		try {
			return (Operatori) uniqueResultByQuery("getOperatoreByUsername", parameters);

		} catch (Exception e) {
			LOGGER.error("error on  getOperatoreByUsername, username = " + username, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public Operatori getOperatoreByOperatoreAndCorporate(String operatore, String corporate) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("operatore", operatore);
		parameters.put("corporate", corporate);

		try {
			Operatori operator = (Operatori) uniqueResultByQuery("getOperatoreByOperatoreAndCorporate", parameters);
			// TODO: migliorare il mapping. questa cosa dopo deve essere fatta perchè 
			// intestatarioperatoriobj è transient
			if(operator != null) {
				for (IntestatarioperatoriCommon intOper : operator.getIntestatarioperatori()) {
					if (intOper.getIntestatariobjIForm().getCorporateIForm().equals(corporate)){
						operator.setIntestatarioperatoriobj(intOper);
					}
				}
			}
			return operator;

		} catch (Exception e) {
			LOGGER.error("error on  getOperatoreByCorporateAndUsername with parameters: " + parameters, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public Operatori getOperatoreByCodiceFiscale(String codiceFiscale) {
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("codiceFiscale", codiceFiscale);

		try {
			return (Operatori) uniqueResultByQuery("getOperatoreByCodiceFiscale", parameters);

		} catch (Exception e) {
			LOGGER.error("error on  getOperatoreByCodiceFiscale with parameters: " + parameters, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public void resetPasswdOperatore(String operatore, String newPasswd) {
		LOGGER.info("BEGIN resetPasswdOperatore operatore = "+operatore);
		try {
		   Query q=em.createNamedQuery("resetPwdOperatore");
		   q.setParameter("operatore", operatore);
		   q.setParameter("password", newPasswd);
		   int num=q.executeUpdate();
		   LOGGER.info("resetPasswdOperatore operatore = "+operatore + " aggiornate "+num+ " righe");
		} catch (Exception e) {
			LOGGER.error("error on  resetPasswdOperatore ");
			throw new DAORuntimeException(e);
		}
		
	}
	
	@Override
	public Operatori createOperatore(Operatori operatore) {
		try {
			operatore = this.create(operatore);
		} catch (Exception e) {
			LOGGER.error("Error on createOperatore ", e);
			throw new DAORuntimeException(e);
		}
		return operatore;
	}

}
