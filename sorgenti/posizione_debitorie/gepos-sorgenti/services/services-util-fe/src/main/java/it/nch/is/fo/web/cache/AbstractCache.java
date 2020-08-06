package it.nch.is.fo.web.cache;

import it.nch.fwk.fo.web.util.cache.CacheUtil;

import java.io.Serializable;
import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;

/**
 * Classe base astrazione delle cache di applicazione.
 *
 * @author TODO
 *
 */
public abstract class AbstractCache {

    /**
     * Ricerca nella cache di nome name l'oggetto di chiave key
     * e lo restituisce se esiste, null altrimenti.
     *
     * @param name il nome della cache in cui effettuare la ricerca
     * @param key la chiave dell'oggetto ricercato nella cache di nome name
     * @return l'oggetto di chiave key se è contenuto nella cache di nome name, null altrimenti.
     * @throws Exception
     * @throws CacheException
     */
    public Object getCachedObject(String name,String key) throws Exception, CacheException{

    	Object ret = null;
    	URL url = CacheUtil.class.getResource("cacheConfig.xml");
        CacheUtil cacheUtil = CacheUtil.getCacheUtil(url);
        CacheManager manager = cacheUtil.getManager();
        if (manager==null)
        	cacheUtil.setManager(url);
        if (!cacheUtil.getManager().cacheExists(name)){
            Cache cache = new Cache(name, 1000, false, false, 2*60*60, 2*60*60);
            cacheUtil.getManager().addCache(cache);
        }
        ret = cacheUtil.getValueFromCache(name, key);
        return ret;

    }

    /**
     * Memorizza nella cache di nome cacheName il valore value associandogli la chiave key.
     *
     * @param cacheName il nome della cache in cui effettuare la memorizzazione
     * @param key la chiave da associare all'oggetto value nella cache di nome cacheName
     * @param value l'oggetto da memorizzare nella cache di nome cacheName
     * @throws Exception
     */
    public void store(String cacheName, String key, Serializable value) throws Exception{

    	CacheUtil cacheUtil = CacheUtil.getCacheUtil(getClass().getResource("cacheConfig.xml"));
        cacheUtil.putValueInCache (cacheName,key, value);

    }

}
