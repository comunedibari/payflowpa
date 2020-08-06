package it.nch.is.fo.web.cache;

import it.nch.fwk.fo.exceptions.FrontEndException;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.dto.menu.ApplicazioniMenu;
import it.tasgroup.iris.gateway.facade.ejb.client.authentication.AuthenticationFacade;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import net.sf.ehcache.CacheException;

/**
 * Cache dell'applicazione in cui vengono memorizzate informazioni come la struttura di base dei menu
 * per ogni applicazione.
 *
 * @author TODO
 *
 */
public class IrisCache extends AbstractCache{


  public Object getFlowDescription(String fileName, String cacheName, String key, String codeName, String descName) throws Exception, CacheException{
    	HashMap ret = (HashMap) getCachedObject("propertyCache",cacheName);
    	Object value = null;
        if (ret == null ){
            Tracer.info(this.getClass().getName(), "getFlowDescription", "Dato NON presente in cache.", null);

        	ret = populateDescriptionHashMap(codeName,descName,fileName);
        	Tracer.info(this.getClass().getName(), "getFlowDescription", "inserita lista descrizioni flusso in cache", null);
        	store("propertyCache", cacheName, ret);
        	if (ret !=null)
        		value =  ret.get(key);

        } else {
            Tracer.info(this.getClass().getName(), "getFlowDescription", "Dato presente in cache.", null);
            value =  ret.get(key);
        }
        return value;
    }


 /**
 * Estrae dalla cache, se c'è, il menu per l'applicazione il cui codice viene specificato
 * in ingresso.
 * Se tale menu non è presente in cache, lo crea ex novo con le informazioni estratte
 * dalle tabelle di DB (APPLICAZIONI,AREE,SERVIZI,FUNZIONI).
 *
 * @param fec il FrontEndContext
 * @param applicationCode il codice dell'applicazione di cui si cerca il menu
 * @return un Object di classe ApplicazioniMenu che rappresenta la radice del menu recuperato dalla cache, se c'era,
 * o creato ex novo dalle configurazioni presenti sulle tabelle di DB (APPLICAZIONI,AREE,SERVIZI,FUNZIONI)
 * per il codice dell'applicazione indicato in ingresso.
 * @throws Exception
 * @throws CacheException
 */
public ApplicazioniMenu getMenu(FrontEndContext fec, String applicationCode) throws Exception, CacheException{
    	ApplicazioniMenu ret = (ApplicazioniMenu) getCachedObject("menu",applicationCode);
        if (ret == null ){
            Tracer.info(this.getClass().getName(), "getMenu", "Dato NON presente in cache.", null);

//        	ret = listaMenu(fec,applicationCode);
        	ret = listaMenu(applicationCode);
        	
        	Tracer.info(this.getClass().getName(), "getMenu", "inserita lista menu in cache", null);
        	store("menu", applicationCode, ret);
        	return ret;
        } else {
            Tracer.info(this.getClass().getName(), "getMenu", "Dato presente in cache.", null);
        }
        return ret;
    }



    /**
     * Crea un menu per l'applicazione il cui codice è passato in ingresso con le informazioni estratte
	 * dalle tabelle di DB (APPLICAZIONI,AREE,SERVIZI,FUNZIONI).
	 *
     * @param fec il FrontEndContext
     * @param applicationCode il codice dell'applicazione di cui si cerca il menu
     * @return il menu per l'applicazione il cui codice è passato in ingresso con le informazioni estratte
	 * dalle tabelle di DB (APPLICAZIONI,AREE,SERVIZI,FUNZIONI).
     * @throws Exception
     */
//    private ApplicazioniMenu listaMenu(FrontEndContext fec, String applicationCode) throws Exception {
//		Tracer.info(getClass().getName(),"listaMenu","inizio",null);
//
//
//		LoginBusinessDelegate loginBD = new LoginBusinessDelegate();
//		DTO dto = new DTOImpl();
//		Applicazioni apl = new Applicazioni();
//		apl.setApplicationCodeIForm(applicationCode);
//		dto.setBusinessObject(apl);
//		try {
//			DTO dtoMenu= loginBD.estraiMenuPerCache(fec,dto);
//			return (ApplicazioniMenu)dtoMenu.getPojo();
//
//
//		} catch (Exception e) {
//			throw new FrontEndException(e);
//
//		}
//
//    }
    
    private ApplicazioniMenu listaMenu(String applicationCode) throws Exception {
		Tracer.info(getClass().getName(),"listaMenu","inizio",null);
		try {
			AuthenticationFacade authBean = (AuthenticationFacade) ServiceLocator.getSLSBProxy("authenticationFacadeBean");
			return authBean.estraiMenuPerCache(applicationCode);

		} catch (Exception e) {
			throw new FrontEndException(e);
		}
    }

    private HashMap populateDescriptionHashMap(String paramNameCode, String parameNameDesc, String fileName){
		HashMap propertyMap = new HashMap();
		Collection codeList = getList(paramNameCode, fileName);
		Collection descList = getList(parameNameDesc, fileName);
		if (codeList != null && descList !=null && codeList.size()== descList.size()){
			Iterator codeIterator = codeList.iterator();
			Iterator descIterator = descList.iterator();
			while(codeIterator.hasNext() && descIterator.hasNext()){
				String key = (String)codeIterator.next();
				String value = (String)descIterator.next();
				propertyMap.put(key, value);
			}
		}
		return propertyMap;
	}

	private Collection getList(String name, String fileName){
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance(fileName);

		String prop = cpl.getProperty(name);
		ArrayList propList = new ArrayList();
		if (prop != null){
			StringTokenizer st = new StringTokenizer(prop,",");
			while (st.hasMoreTokens()) {
				propList.add(st.nextToken());
			}
		}
		return propList;
	}


}
