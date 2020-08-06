package it.nch.is.fo.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NavigationInfo implements Serializable {
	public int hitCount;
	public String jspName;
	public HashMap pathsToHere;
	
	public NavigationInfo previousInfo;

	public String toString() {
		return
		"\r\nJSP name ..... " + jspName +
		"\r\nhit count .... " + hitCount;
	}

	public NavigationInfo cloneNavInfo() {
		NavigationInfo cloned = new NavigationInfo();
		
		cloned.hitCount = this.hitCount;
		cloned.jspName = this.jspName;
		
		return cloned;
	}
	
	public boolean hasChanged() {
		if (previousInfo == null) {
			return true;
		} else {
			return (hitCount > previousInfo.hitCount);
		}
	}
	
	public void addNavigationPath(NavigationPath navPath) {
		if (pathsToHere == null) {
			pathsToHere = new HashMap();
		}
		String key = navPath.getKey();
		if (!pathsToHere.containsKey(key)) {
			pathsToHere.put(key, navPath);
		}
	}
	
	public NavigationPath[] getPathsToHere() {
		NavigationPath[] pathList = null;
		if (pathsToHere != null) {
			Set keys = pathsToHere.keySet();
			if (keys.size() > 0) {
				pathList = new NavigationPath[keys.size()];
				int i = 0;
				for (Iterator kIt = keys.iterator(); kIt.hasNext(); ) {
					String key = (String)kIt.next();
					NavigationPath navPath = (NavigationPath)pathsToHere.get(key);
					pathList[i] = navPath;
					i++;
				}
				Arrays.sort(pathList, new NavPathComparator());
			}
		}
		return pathList;
	}
	
	class NavPathComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			if (o1 != null && o2 != null) {
				String k1 = ((NavigationPath)o1).getKey();
				String k2 = ((NavigationPath)o2).getKey();
				return k1.compareTo(k2);
			}
			return 0;
		}
		
	}

}
