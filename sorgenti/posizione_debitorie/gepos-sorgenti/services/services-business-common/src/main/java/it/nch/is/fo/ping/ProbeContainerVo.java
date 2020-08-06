package it.nch.is.fo.ping;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class ProbeContainerVo extends ProbeVo {
	
	public Collection IRIS_ServletContainer;
	public Collection PDP_ServletContainer;
	public Collection IRIS_WebContainer;
	public ProbeVo FlowManager_IMPORT_Chain;
	public ProbeVo FlowManager_SPED_Chain;

	public int noOfOpenSessions;
	public Date currentDateTime;
	
	private HashMap messages;
	
	public HashMap getMessages() {
		if (messages == null) {
			loadMessages();
		}
		return messages;
	}
	
	public String getMessageByString(String key) {
		HashMap msgs = getMessages();
		return (String)msgs.get(key);
	}

	private void loadMessages() {
		messages = new HashMap();
		
		Collection servletContainer = this.IRIS_ServletContainer;
		Collection webContainer = this.IRIS_WebContainer;
		Collection servletContainer_PDP = this.PDP_ServletContainer;
		
		Collection importProbes = null;
		Collection spedProbes = null;

		//
		//	Recupero il primo probe tra i WebContainer che ha fatto il 'deep ping'
		//
		for (Iterator it = webContainer.iterator(); it.hasNext(); ) {
			ProbeVo irisProbe = (ProbeVo)it.next();
			if (irisProbe.fmImportProbes != null || irisProbe.fmSpedProbes != null) {
				importProbes = irisProbe.fmImportProbes;
				spedProbes = irisProbe.fmSpedProbes;
				break;
			}
		}
		
		messages = addToMap(messages, servletContainer);
		messages = addToMap(messages, webContainer);
		messages = addToMap(messages, servletContainer_PDP);
		messages = addToMap(messages, importProbes);
		messages = addToMap(messages, spedProbes);
		
	}

	private HashMap addToMap(HashMap msgs, Collection coll) {
		if (coll != null) {
			for (Iterator it = coll.iterator(); it.hasNext(); ) {
				ProbeVo p = (ProbeVo)it.next();
				String key = buildKey(p);
				msgs.put(key, p.message);
			}
		}
		return msgs;
	}

	private String buildKey(ProbeVo p) {
		return p.testType + "." + p.serverName;
	}

}
