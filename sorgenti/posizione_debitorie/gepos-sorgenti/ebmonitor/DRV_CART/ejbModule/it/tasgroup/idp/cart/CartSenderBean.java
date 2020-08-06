package it.tasgroup.idp.cart;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.IDataStorageManager;
import it.tasgroup.idp.bean.ObjectCommandExecutorLocal;
import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.domain.enti.Sil;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.esiti.EsitiModel;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

/**
 * Questo componente e' responsabile della acquisizione da database e spedizione degli esiti
 * pronti per essere spediti. A seconda della presenza o meno dello SmartSil si interfaccia
 * con una coda interna o con una coda esterna
 * 
 * @author tasgroup
 *
 */

@Interceptors(MonitoringInterceptor.class)
@Stateless
@Remote(CommandExecutor.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CartSenderBean implements CommandExecutor, CommandExecutorLocal, ICartSenderBean {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;
	
	@EJB(beanName = "CartSenderBeanTx")
	private ICartSenderBeanTx cartSenderBeanTx;

	private final Log logger = LogFactory.getLog(this.getClass());	
 	

	@Override
	public MonitoringData executeApplicationTransaction() {
		return executeApplicationTransaction((EsitiModel) null);
	}	
	
	
	/**
	 * A seconda della presenza di SmartSil, chiama CartEsitoInternalSenderBeanProxy o CartEsitoSenderBeanProxy
	 */    
	public MonitoringData executeApplicationTransaction(EsitiModel esitiModel) {
		MonitoringData monData = new MonitoringData();
		
		List<EsitiCart> listaEsitiDaSpedire = null;
		if (esitiModel == null) {
			listaEsitiDaSpedire = listaEsitiProntiDaSpedire();
		} else {
			listaEsitiDaSpedire = listaEsitoProntoDaSpedire(esitiModel);
		}

		logger.info(" ho trovato num. esiti da spedire " + listaEsitiDaSpedire.size());

		monData.setNumRecord(listaEsitiDaSpedire.size());			
		
		for (EsitiCart esitiCart : listaEsitiDaSpedire) {
			EsitiCartPK esitiCartPk = esitiCart.getPk();
			try {
				if (!isSSilActive(esitiCartPk.getSendersys(),esitiCartPk.getSenderid())) {
					// Passaggio attraverso Proxy Cart
					logger.info(" calling CartSenderBeanTx (Proxy Cart interface)");
					cartSenderBeanTx.sendJmsAndUpdateEsito(esitiCart);
				} else { 
					// Passaggio attraverso Smart Sil
					logger.info(" calling CartSenderBeanTx (SmartSil interface)");
					logger.info(" Cambio stato IN_SPEDIZIONE "+esitiCartPk.toString());
					int updated = cartSenderBeanTx.updateStatoEsitiCart(esitiCartPk, StatoEnum.IN_SPEDIZIONE); // Metto in spedizione (IN TRANSAZIONE SEPARATA)
					logger.info(" Cambio stato IN_SPEDIZIONE "+esitiCartPk.toString()+" (record updated="+updated+")");
					
					try {
						if (updated!=0) { // Procedo al Put sulla coda se e solo se sono riuscito a cambiare stato. 
							logger.info(" PUT su coda SSIL: "+esitiCartPk.toString());
							cartSenderBeanTx.sendJmsObjectMessage("JmsXA", "ClusteredXAConnectionFactory", "JmsPosizioneDebitoriaOutputInternalSSil", esitiCartPk);
							logger.info(" PUT su coda SSIL: "+esitiCartPk.toString()+" esito=OK ");
						}
					} catch (Exception e) {
						// L'eccezione nasce in cartSenderBeanTx: li' la transazione viene interrotta. Qui si segnala sul log e si procede con il prossimo esito
						//  Se ho un problema nella gestione del put sulla coda.
						logger.error("Errore nel processing dell'esito " + esitiCartPk.toString(), e);
						updated= cartSenderBeanTx.updateStatoEsitiCart(esitiCartPk, StatoEnum.DA_SPEDIRE);  //Provo a rimettere l'esito "DA SPEDIRE" (senza incremento tentativi)
						logger.error("Rimesso esito in stato DA_SPEDIRE: "+ esitiCartPk.toString() +" (record updated="+updated+")");						
					}
				}			
			} catch (Exception e) {
				// L'eccezione nasce in cartSenderBeanTx: li' la transazione viene interrotta. Qui si segnala sul log e si procede con il prossimo esito
				//  Se ho un problema nella gestione del put sulla coda.
				logger.error("Errore nel processing dell'esito " + esitiCartPk.toString(), e);
			}
		}			
		return monData;
	}

    /**
     * Acquisisce da database gli esiti pronti per essere spediti
     * 
     * @return esiti da spedire
     */
	private List<EsitiCart> listaEsitiProntiDaSpedire() {
		Query queryEsiti = em.createQuery ("SELECT esitiCart FROM EsitiCart esitiCart WHERE esitiCart.stato = :stato ");
		queryEsiti.setParameter("stato", StatoEnum.DA_SPEDIRE);
		
		/* 
		 * NOTA
		 * 
		 *  In caso si voglia gestire il reinvio degli esiti che non sono andati a buon fine
		 *   occorre filtrare anche per un nuovo stato: StatoEnum.DA_REINVIARE
		 *  
		 */
		
		List<EsitiCart> lins = queryEsiti.getResultList();
		return lins;
	}
    
	private List<EsitiCart> listaEsitoProntoDaSpedire(EsitiModel esitiModel) {
		EsitiCartPK esitiCartPK = new EsitiCartPK();
		esitiCartPK.setE2emsgid(esitiModel.getE2emsgid());
		esitiCartPK.setSenderid(esitiModel.getSenderId());
		esitiCartPK.setSendersys(esitiModel.getSenderSys());
		
		Query queryEsiti = em.createQuery ("SELECT esitiCart FROM EsitiCart esitiCart WHERE esitiCart.pk = :esitiCartPK ");
		queryEsiti.setParameter("esitiCartPK", esitiCartPK);
		List<EsitiCart> lins = queryEsiti.getResultList();

		return lins;
	}
    
	private boolean isSSilActive(String senderSys,String senderId) {
		try {
			Sil sil = (Sil) em.createNamedQuery("checkSIL")
                               .setParameter("idSystem", senderSys)
                               .setParameter("idMittente",senderId)
                                   .getSingleResult();
			return sil.isSSilEnabled();
		} catch (NoResultException e) {
			return false;
		}
	}
	
	/**
	 * 
	 */
    public String executeHtml() throws Exception  {			
		
		String table = "";
		try {				
			//calcolo la lista delle pendenze per cui gli esiti sono pronti
			//per essere creati e spediti
			List<EsitiCart> lins = listaEsitiProntiDaSpedire();	
				 
				System.out.println("executeHtml, found " + lins.size());	
				table = "<br><br>";
				table+="<TABLE border=\"\1\">";
				
				for (EsitiCart object : lins) {
					table+="<TR>";
						table+="<TD>";
							table+=(object.getPk().getE2emsgid());
						table+="</TD>";
						table+="<TD>";
							table+=(object.getPk().getSenderid());
						table+="</TD>";
						table+="<TD>";
							table+=(object.getPk().getSendersys());
						table+="</TD>";						
						table+="<TD>";
							table+=(object.getStato());
						table+="</TD>";
						table+="<TD>";
							table+=(object.getTsInserimento());
						table+="</TD>";										
						table+="<TD>";
							table+=(object.getEsitoXml().toString());
						table+="</TD>";										
					table+="</TR>";				
				}		
				
				table+="</TABLE>";
			} catch (Exception e) {
				logger.error(" Error lista " + e.getMessage() );
			}
	//		table+="</PRE>";
		return table;
	}

	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}

}
