package it.tasgroup.idp.util;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class JAXBContextProvider {
	private static final JAXBContextProvider instance = new JAXBContextProvider();
	private static final int MAX_CACHE_DEPTH = 20;
	
	private final LRUCache<Set<String>, JAXBContext> cache;

	private JAXBContextProvider() {
		this.cache = new LRUCache<Set<String>, JAXBContext>(MAX_CACHE_DEPTH);
	}
	
	public static JAXBContextProvider getInstance() {
		return instance;
	}
	
	public JAXBContext getJAXBContext (String[] packages) throws JAXBException {
		if (packages == null) throw new NullPointerException("packages");
		Set<String> packagesSet = new HashSet<String>();
		for (String pckg : packages) packagesSet.add(pckg);
		return getJAXBContext(packagesSet);
	}
	
	private synchronized JAXBContext getJAXBContext(Set<String> packages) throws JAXBException {
		if (cache.containsKey(packages)) return cache.get(packages);
		String packagesAsString = packagesToStringRepresentation(packages);
		JAXBContext jaxbContext = JAXBContext.newInstance(packagesAsString);
		cache.put(packages, jaxbContext);
		return jaxbContext;
	}
	
	private String packagesToStringRepresentation(Set<String> packages) {
		StringBuilder outBuffer = new StringBuilder(); 
		boolean firstItem = true; 
		for (String pckg : packages) {
			if (firstItem) {
				outBuffer.append(pckg);
				firstItem = false;
			} else {
				outBuffer.append(":").append(pckg);
			}
		}
		return outBuffer.toString();
	}
}
