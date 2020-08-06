package it.nch.fwk.fo.web.util.cache;

//import it.nch.fwk.fo.exceptions.FrontEndException;
//import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.util.Tracer;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import javax.management.MBeanServer;
//import javax.management.MBeanServerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
//import net.sf.ehcache.management.ManagementService;

public class CacheUtil {

    private CacheManager manager = null;
    private static CacheUtil cacheut = null;
    private static URL url= CacheUtil.class.getResource("cacheConfig.xml"); //default value per le resources

    private CacheUtil() throws Exception {
    			
                init();
    }

    public static CacheUtil getCacheUtil(URL webappurl) {
    	 if (cacheut == null) {
             synchronized (CacheUtil.class) {
                 if (cacheut == null) {
                	 try{
                		 url = webappurl;
                     cacheut = new CacheUtil();
                     
                	 }catch(Exception e) {
                         //throw new FrontEndException(e);
                     }
                 }
             }
         }
         return cacheut;
    }

    public CacheManager getManager() {
        /*if (manager == null){
            try {
                init();
            } catch (CacheException e) {
                //throw new FrontEndException(e);
            }
        }*/
        return manager;
    }

    private void init() throws CacheException {
        //URL url = CacheUtil.class.getResource("cacheConfig.xml");
    	//Load da FE del file di configurazione
    	
    	
    	//Commentato 
       /* manager = CacheManager.create(url);
		List list = MBeanServerFactory.findMBeanServer(null);
        MBeanServer mBeanServer  = (MBeanServer)list.iterator().next();
        ManagementService.registerMBeans(manager, mBeanServer, true, true, true, true);*/


    }

    public Object getValueFromCache(String cacheName, String key) {
        Tracer.info(this.getClass().getName(), "getValueFromCache", "cacheName: '" + cacheName + "' key: '" + key + "'", null);
        key = key != null ? key.toUpperCase() : null;
        Object value = null;
        Cache cache = getManager().getCache(cacheName);
        Element element = null;
        if (cache !=null){
	        try {
	            element = cache.get(key);
	            if (element != null){
	                Tracer.info(this.getClass().getName(), "getValueFromCache", "Value in cache.", null);
	                if (!cache.isExpired(element)){
	                    Tracer.info(this.getClass().getName(), "getValueFromCache", "Value NOT expired.", null);
	                    value = element.getValue();
	                } else {
	                    Tracer.info(this.getClass().getName(), "getValueFromCache", "Value expired.", null);
	                }
	            } else {
	                Tracer.info(this.getClass().getName(), "getValueFromCache", "Value NOT in cache.", null);
	            }
	        } catch (IllegalStateException e) {
	        } catch (CacheException e) {
	        }
        }
        return value;
    }

    public Object getValueFromCache(String cacheName, String key, String locale) {
        return getValueFromCache(cacheName, key + locale);
    }

    public String getStringFromCache(String cacheName, String key) {
        return (String)getValueFromCache(cacheName, key);
    }

    public String getStringFromCache(String cacheName, String key, String locale) {
        return (String)getValueFromCache(cacheName, key + locale);
    }

    public Collection getCollectionFromCache(String cacheName, String key){
        return (Collection)getValueFromCache(cacheName, key);
    }

    public Collection getCollectionFromCache(String cacheName, String key, String locale) {
        return (Collection)getValueFromCache(cacheName, key + locale);
    }

    public void putValueInCache(String cacheName, String key, Serializable value){
        Cache cache = getManager().getCache(cacheName);
        Element element = null;
        key = key != null ? key.toUpperCase() : null;
        element = new Element(key, value);
        cache.put(element);
    }

    public void putValueInCache(String cacheName, String key, String locale, Serializable value) {
        putValueInCache(cacheName, key + locale, value);
    }

    public void resetCache(String cacheName) throws IOException {
        Cache cache = getManager().getCache(cacheName);
        cache.removeAll();
    }

    public String cacheToString(String cacheName) throws Exception {
        StringBuffer sb = new StringBuffer();
        Cache cache = getManager().getCache(cacheName);
        if (cache != null){
            List keys = cache.getKeys();
            Iterator iter = keys.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Element e = cache.get(key);
                Object o = e.getValue();
                if (o instanceof Collection){
                    Collection c = (Collection)o;
                    for (Iterator iterator = c.iterator(); iterator.hasNext();) {
                        Object element = (Object) iterator.next();
                        sb.append(element.toString()).append('\n');
                    }
                } else if (o instanceof Map){
                    Map m = (Map)o;
                    Collection c = m.values();
                    for (Iterator iterator = c.iterator(); iterator.hasNext();) {
                        Object element = (Object) iterator.next();
                        sb.append(element.toString()).append('\n');

                    }
                } else {
                    sb.append(o.toString());
                }

            }
        }

        return sb.toString();
    }

    public void resetAllCache() throws IOException {
//        String[] names = getManager().getCacheNames();
//        Cache cache = null;
//        for (int i = 0; i < names.length; i++) {
//            cache = getManager().getCache(names[i]);
//            cache.removeAll();
//            getManager().removeCache(names[i]);
//        }
    }

    public String[] getCacheNames(){
        return getManager().getCacheNames();
    }

    public long getMemorySize(String cacheName) throws IllegalStateException, CacheException{
        return getManager().getCache(cacheName).calculateInMemorySize(); // In Byte
    }

    public int getSize(String cacheName) throws IllegalStateException, CacheException{
        return getManager().getCache(cacheName).getSize();
    }

    public int getStatus(String cacheName){
        //return getManager().getCache(cacheName).getStatus();
    	return 0;
    }


    public String getStatisticsHTML(){
        String nl = "<br>";
        StringBuffer sb = new StringBuffer();
        String[] names = getManager().getCacheNames();
        Arrays.sort(names);
        Cache cache = null;
        sb.append("<table>");
        for (int i = 0; i < names.length; i++) {
            sb.append("<tr><td>");
            String cacheName = names[i];
            cache = getManager().getCache(cacheName);
            try {
                long memorySize = cache.calculateInMemorySize()/1024; // In K
                int size = cache.getSize();
                sb.append(cacheName + " - Memory size: </td><td>").append(memorySize).append("</td><td>").append(size).append(nl);
                sb.append("</td>");
            } catch (IllegalStateException e) {
                sb.append(cacheName).append(": IllegalStateException: </td><td>" + cache.getStatus()).append("</td><td>").append(nl);
            } catch (CacheException e) {
                sb.append(cacheName).append(": CacheException </td><td>").append(nl);
            }
            sb.append("</td></tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public String getStatistics(String nl){
        StringBuffer sb = new StringBuffer();
        String[] names = getManager().getCacheNames();
        Cache cache = null;
        for (int i = 0; i < names.length; i++) {
            String cacheName = names[i];
            cache = getManager().getCache(cacheName);
            try {
                sb.append(cacheName + " - Memory size: ").append(cache.calculateInMemorySize()).append(nl);
            } catch (IllegalStateException e) {
                sb.append(cacheName).append(": IllegalStateException: " + cache.getStatus()).append(nl);
            } catch (CacheException e) {
                sb.append(cacheName).append(": CacheException").append(nl);
            }
        }
        return sb.toString();
    }

    public void setManager(URL url) throws Exception{
    	manager = CacheManager.create(url);
    }
}
