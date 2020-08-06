package it.tasgroup.idp.billerservices.rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;

public class Commons {

	/**
	 * decodifichiamo la stringa di basic authentication
	 * controlliamo che l'utente/password sia registrato
	 * torniamo la lista dei sil e dell'unico ente se tutto ok
	 * altrimenti torniamo un'eccezione
	 */
    public static List<EnteSil> checkUserAuthentication(String authString, String operazione, EntityManager em, Log logger) throws UserNotFoundException {
        
    	try {
	        String authInfo = authString.split("\\s+")[1]; // togliamo il "Basic " iniziale
	        String decodedAuth = new String(Base64.decodeBase64(authInfo));
	        int firstColonOffset = decodedAuth.indexOf(':'); // lo username nella basic authentication non puo' contenere colon (:)
	    	String username = decodedAuth.substring(0, firstColonOffset);
	        String password = decodedAuth.substring(firstColonOffset + 1);
	        
	        logger.info(operazione + ": controllo autorizzazione per username [" + username + "]");
	        
			Query q = em.createQuery(
					"select enti.cdEnte, sil.id.idSystem from Enti enti, Sil sil " +
							"where enti.idEnte = sil.id.idEnte " +
							"and sil.userId = :userId " +
							"and sil.authId = :authId"
					);
	        q.setParameter("userId", username);
	        q.setParameter("authId", password);
	        List<Object[]> listaEnteSil = (List<Object[]>)q.getResultList();
	        if (listaEnteSil.size() == 0) {
	        	String msg = operazione + ": nessun ente/sil trovato per la coppia username [" + username + "] e password";
	        	throw new Exception(msg.toString());
	        }

	        String ente = (String)(listaEnteSil.get(0)[0]);
	        boolean isEnteOmogeneo = true;
	        List<EnteSil> ret = new ArrayList<EnteSil>();
	        for (Object[] enteSil : listaEnteSil) {
	        	isEnteOmogeneo = isEnteOmogeneo && ente.equals((String)enteSil[0]);
	        	ret.add(new EnteSil((String)enteSil[0], (String)enteSil[1]));
	        }
	        if (!isEnteOmogeneo) {
	        	String msg = operazione + ": trovata disomogeneita' nell'ente per la coppia username [" + username + "] e password";
	        	throw new Exception(msg.toString());
	        }

	        logger.info(operazione + ": operatore [" + username + "] autenticato. Ente [" + ente + "]");
	        return ret;
    	} catch (Exception e) {
    		String msg = operazione + ": Basic authentication in errore. " + e.getMessage();
    		logger.warn(msg, e);
    		throw new UserNotFoundException();
    	}
        
    }
    
    /**
	 * controlliamo che senderId/senderSys sia registrato
	 * torniamo ente e sil se tutto ok
	 * altrimenti torniamo un'eccezione
	 */
    public static List<EnteSil> checkEnteSil(String senderId, String senderSys, String operazione, EntityManager em, Log logger) throws MalformedRequestException, UserNotFoundException {
        
    	try{
    		
    		logger.info(operazione + ": controllo esistenza senderId e senderSys  [" + senderId + " " + senderSys +"]");
    		
    		if( senderId == null || senderId.trim().isEmpty() || senderSys == null || senderSys.trim().isEmpty())
		    	throw new MalformedRequestException();
		    List<EnteSil> listaEnteSil = new ArrayList<EnteSil>();
	    	Query q = em.createQuery(
	    			"select enti.cdEnte, sil.id.idSystem from Enti enti, Sil sil " +
	    					"where enti.idEnte = sil.id.idEnte " +
	    					"and enti.cdEnte = :senderId " +
	    					"and sil.id.idSystem = :systemId"
	    	); 
	    	q.setParameter("senderId", senderId);
	    	q.setParameter("systemId", senderSys);
	    	List<Object[]> listaSil = (List<Object[]>)q.getResultList();
	    	if (listaSil.size() == 0) {
	    		String msg = operazione + ": nessun Ente/Sil trovato per [" + senderId + "] e [" + senderSys + "]";
	    		logger.warn(msg);
	    		throw new UserNotFoundException();
	    	} 
	    	listaEnteSil.add(new EnteSil(senderId, senderSys));
	    	logger.info(operazione + ": controllo senderId e senderSys completato");
	    	return listaEnteSil;
	
    	} catch (MalformedRequestException e) {
    		throw new MalformedRequestException();
    	} catch (Exception e) {
    		String msg = operazione + ": checkEnteSil in errore. " + e.getMessage();
    		logger.warn(msg, e);
    		throw new UserNotFoundException();
    	}
        
    }
    
    public static class UserNotFoundException extends Exception {
    	private static final long serialVersionUID = 1L;
    }
    public static class TrasmissioneNotFoundException extends Exception {
    	private static final long serialVersionUID = 1L;
    }
    public static class MalformedRequestException extends Exception {
    	private static final long serialVersionUID = 1L;
    }
    public static class DataValidationException extends Exception {
		public DataValidationException(String exceptionMsg) {
			super(exceptionMsg);
		}
		private static final long serialVersionUID = 1L;
    }

    public static class EnteSil {

    	private String ente;
    	private String sil;
    	
    	public EnteSil(String ente, String sil) {
			this.ente = ente;
			this.sil = sil;
		}
    	
    	public String getEnte() {return this.ente;}
    	public String getSil() {return this.sil;}
    	
    }
    
}
