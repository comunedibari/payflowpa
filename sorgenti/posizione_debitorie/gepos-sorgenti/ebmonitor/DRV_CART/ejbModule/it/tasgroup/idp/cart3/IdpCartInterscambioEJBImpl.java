package it.tasgroup.idp.cart3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.CommandExecutorSemplificationLocal;
import it.tasgroup.idp.cart.core.IdpCartInterscambioEJB;
import it.tasgroup.idp.cart.core.exception.IrisException;
import it.tasgroup.idp.cart.core.exception.MessaggioDuplicatoException;
import it.tasgroup.idp.cart.core.exception.MsgIdDuplicatoException;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTFEsito;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.ObjectFactory;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamenti;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamentiEsito;

@Stateless
public class IdpCartInterscambioEJBImpl implements IdpCartInterscambioEJB {
	
	public static final String PENDENZE_QUERY = "selectPendenzeIdempotenza";
	public static final String CONFERME_QUERY = "selectConfermeIdempotenza";
	
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpAppJpa)
	private EntityManager manager;
	
	@EJB(beanName = "DataStorageManagerSemplificationImpl")
	private CommandExecutorSemplificationLocal dataStorageManager;
	
	@EJB(beanName = "EsitoNotificaPagamentoManager")
	private CommandExecutorLocal esitoNotificaPagamentoManager;

	@Override
	public void processAP(String ente, String sil, String enteIntermediario, String silIntermediario, String msgId, String hash, String xml) throws MessaggioDuplicatoException, MsgIdDuplicatoException, IrisException {
		try {
			boolean pendenzaPresente = checkDuplication(ente, sil, msgId,PENDENZE_QUERY);
			if (pendenzaPresente) {
				throw new MessaggioDuplicatoException("Messaggio duplicato");
			}
			// future implementation (compare hash got by select to hash arrived here, if are equals MessaggioDuplicatoException, if not MsgIdDuplicatioException
			// add column hash to table and change selectPendenzeIdempotenza to return the hash
			dataStorageManager.executeApplicationTransaction(xml);			
		} catch (PersistenceException pe) {
			boolean pendenzaPresente = checkDuplication(ente, sil, msgId,PENDENZE_QUERY);
			if (pendenzaPresente) {
				throw new MessaggioDuplicatoException("Messaggio duplicato");
			} else {
				throw new IrisException("Internal error",pe);
			}
		} catch (RuntimeException re) {
			throw new IrisException("Internal error",re);
		}
	}


	

	@Override
	public void processIPE(String ente, String sil, String enteIntermediario, String silIntermediario, String msgId, String hash, String xml) throws MessaggioDuplicatoException, MsgIdDuplicatoException, IrisException {
		try {
			boolean confermaPresente = checkDuplication(ente, sil, msgId,CONFERME_QUERY);
			if (confermaPresente) {
				throw new MessaggioDuplicatoException("Messaggio duplicato");
			}
			// future implementation (compare hash got by select to hash arrived here, if are equals MessaggioDuplicatoException, if not MsgIdDuplicatioException
			// add column hash to table and change selectPendenzeIdempotenza to return the hash
			esitoNotificaPagamentoManager.executeApplicationTransaction(xml);			
		} catch (PersistenceException pe) {
			boolean confermaPresente = checkDuplication(ente, sil, msgId,CONFERME_QUERY);
			if (confermaPresente) {
				throw new MessaggioDuplicatoException("Messaggio duplicato");
			} else {
				throw new IrisException("Internal error",pe);
			}
		} catch (RuntimeException re) {
			throw new IrisException("Internal error",re);
		}
		
	}

	
	
	private boolean checkDuplication(String ente, String sil, String msgId,String tableQuery) throws MessaggioDuplicatoException {
		boolean presente = true;
		Query selectIdemPotenza = manager.createNamedQuery(tableQuery);
		selectIdemPotenza.setParameter("e2emsgid", msgId);
		selectIdemPotenza.setParameter("sil", sil);
		selectIdemPotenza.setParameter("ente", ente);
		try {
			selectIdemPotenza.getSingleResult();
			// Future Implementation: get the hash here => String hash = selectIdemPotenza.getSingleResult();
		} catch (NoResultException nre) {
			presente = false;
		}
		return presente;
		
	}




	@Override
	public String processAPOTF(String ente, String sil,  String enteIntermediario, String silIntermediario, String msgId, String hash,String xml) throws MessaggioDuplicatoException, MsgIdDuplicatoException, IrisException {
		try {
			boolean pendenzaPresente = checkDuplication(ente, sil, msgId,PENDENZE_QUERY);
			if (pendenzaPresente) {
				throw new MessaggioDuplicatoException("Messaggio duplicato");
			}
			// future implementation (compare hash got by select to hash arrived here, if are equals MessaggioDuplicatoException, if not MsgIdDuplicatioException
			// add column hash to table and change selectPendenzeIdempotenza to return the hash
			InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			StreamSource ss = new StreamSource(is);
			JAXBContext jaxbContext;
			IdpAllineamentoPendenzeEnteOTF apOTF = null;
			jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<IdpAllineamentoPendenzeEnteOTF> jaxbElement = (JAXBElement<IdpAllineamentoPendenzeEnteOTF>) jaxbUnmarshaller.unmarshal(ss, IdpAllineamentoPendenzeEnteOTF.class);
			apOTF = (IdpAllineamentoPendenzeEnteOTF) jaxbElement.getValue();
			CartWsImpl cartWsImpl = new CartWsImpl();
			
			IdpAllineamentoPendenzeEnteOTFEsito esitoObject = cartWsImpl.idpAllineamentoPendenzeEnteOTF(apOTF);
			
			jaxbContext = JAXBContext.newInstance(IdpAllineamentoPendenzeEnteOTFEsito.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));		
			ByteArrayOutputStream output = new ByteArrayOutputStream();	
			marshaller.marshal(esitoObject,output);
			return new String(output.toByteArray());
		} catch (UnsupportedEncodingException e) {
			throw new IrisException("Internal Error. Encoding not supported",e);
		} catch (JAXBException e) {
			throw new IrisException("Internal Error. Input message is not parsable",e);
		} catch (PersistenceException pe) {
			boolean pendenzaPresente = checkDuplication(ente, sil, msgId,PENDENZE_QUERY);
			if (pendenzaPresente) {
				throw new MessaggioDuplicatoException("Messaggio duplicato");
			} else {
				throw new IrisException("Internal error",pe);
			}
		} catch (RuntimeException re) {
			throw new IrisException("Internal error. Generic error",re);
		}
	}




	@Override
	public String processIPOTF(String ente, String sil,  String enteIntermediario, String silIntermediario, String msgId, String hash,String xml) throws MessaggioDuplicatoException, MsgIdDuplicatoException, IrisException {
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			StreamSource ss = new StreamSource(is);
			JAXBContext jaxbContext;
			IdpVerificaStatoPagamenti vspOTF = null;
			jaxbContext = JAXBContext.newInstance(IdpVerificaStatoPagamenti.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<IdpVerificaStatoPagamenti> jaxbElement = (JAXBElement<IdpVerificaStatoPagamenti>) jaxbUnmarshaller.unmarshal(ss, IdpVerificaStatoPagamenti.class);
			vspOTF = (IdpVerificaStatoPagamenti) jaxbElement.getValue();
			VerificaStatoPagamentoOTFCommonUse vspocu = new VerificaStatoPagamentoOTFCommonUse();
			
			IdpVerificaStatoPagamentiEsito esitoObject = vspocu.idpVerificaStatoPagamenti(vspOTF);
			
			jaxbContext = JAXBContext.newInstance(IdpVerificaStatoPagamentiEsito.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));		
			ByteArrayOutputStream output = new ByteArrayOutputStream();	
			marshaller.marshal(esitoObject,output);
			return new String(output.toByteArray());
		} catch (UnsupportedEncodingException e) {
			throw new IrisException("Internal Error. Encoding not supported",e);
		} catch (JAXBException e) {
			throw new IrisException("Internal Error. Input message is not parsable",e);
		} catch (RuntimeException re) {
			throw new IrisException("Internal error. Generic error",re);
		}
	}
}
