package it.nch.is.fo.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NavigationPath implements Serializable {
	
	public String action;
	public String method;
	public String fwdName;
	public String fwdPath;
	
	public int hitCount;
	
	public HashMap jspsFromHere;
	
	public NavigationPath previousPath;
	
	public NavigationPath(String action, String method, String fwdName, String fwdPath) {
		super();

		this.action = action;
		this.method = method;
		this.fwdName = fwdName;
		this.fwdPath = fwdPath;
	}

	public String getKey() {
		return action + "." + method + "." + fwdName + "." + fwdPath;
	}
	
	public void addJsp(String jsp) {
		if (jspsFromHere == null) {
			jspsFromHere = new HashMap();
		}
		Object o = jspsFromHere.get(jsp);
		if (o == null) {
			jspsFromHere.put(jsp, jsp);
		}
	}
	
	public String[] getJspsFromHere() {
		String[] jspList = null;
		if (jspsFromHere != null) {
			Set keys = jspsFromHere.keySet();
			if (keys.size() > 0) {
				jspList = new String[keys.size()];
				int i = 0;
				for (Iterator kIt = keys.iterator(); kIt.hasNext(); ) {
					String key = (String)kIt.next();
					String jsp = (String)jspsFromHere.get(key);
					jspList[i] = jsp;
					i++;
				}
				Arrays.sort(jspList);
			}
		}
		return jspList;
	}

	public NavigationPath cloneNavPath() {
		NavigationPath cloned = new NavigationPath(this.action, this.method, this.fwdName, this.fwdPath);
		
		cloned.hitCount = this.hitCount;
		
		return cloned;
	}
	
	public boolean hasChanged() {
		if (previousPath == null) {
			return true;
		} else {
			return (hitCount > previousPath.hitCount);
		}
	}
	
	public int getHitCountRelatedToJsp() {
		int nrOfJsp = jspsFromHere.size();
		return hitCount / nrOfJsp;
	}

}
