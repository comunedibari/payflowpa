package it.nch.is.fo.util;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;

public class NavigationLogger {

	public static final String IRIS = "iris";
	public static final String BACKOFFICE = "backoffice";
	public static HashMap jspMap;
	public static HashMap lastJspMap;
	public static HashMap navigationPaths;
	public static HashMap lastNavigationPaths;
	public static String currentAction;
	public static String currentMethod;
	public static String currentForwardName;	
	public static String currentForwardPath;	

	private static Logger nav = null;

	private static Logger getNav() {
		if (nav == null) {
			nav = Logger.getLogger("it.nch.is.fo.navigation");
			Enumeration enAppenders = nav.getAllAppenders();
			int a = 0;
			while (enAppenders.hasMoreElements()) {
				a++;
				Appender app = (Appender)enAppenders.nextElement();
				System.out.println("[NavigationLogger::getNav] Found appender of kind " + app.getClass().getName());
				if (app instanceof FileAppender) {
					System.out.println("[NavigationLogger::getNav] Appender is a file appender. File = " + ((FileAppender)app).getFile() + ", level = " + ((FileAppender)app).getThreshold());
				}
			}
			System.out.println("[NavigationLogger::getNav] There are " + a + " appenders from configuration file");
			if (isLocalEnv()) {
				//
				//	Nelle installazioni 'local' aggiungo l'appender a mano
				//
				if (isIris()) {
					ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
					String value = cpl.getProperty("navigation.log.file");
					System.out.println("[NavigationLogger::getNav] navigation.log.file = " + value);
					if (value != null && ! value.equals("")) {
						final String appenderName = "LocalFileAppender";
						Appender app = nav.getAppender(appenderName);
						if (app == null) {
							FileAppender fApp = new FileAppender(); 
							fApp.setName(appenderName);
							fApp.setFile(value);
							fApp.setLayout(new PatternLayout("NAVIGATION_INFO - %m%n"));
							fApp.setThreshold(Priority.DEBUG);
							fApp.activateOptions();
							nav.addAppender(fApp);
						}
					}
				} else if (isBackOffice()) {
					ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-bo.properties");
					String value = cpl.getProperty("navigation.backoffice.log.file");
					final String appenderName = "LocalFileAppender";
					Appender app = nav.getAppender(appenderName);
					if (app == null) {
						FileAppender fApp = new FileAppender(); 
						fApp.setName(appenderName);
						fApp.setFile(value);
						fApp.setLayout(new PatternLayout("BACKOFFICE_NAVIGATION_INFO - %m%n"));
						fApp.setThreshold(Priority.DEBUG);
						fApp.activateOptions();
						nav.addAppender(fApp);
					}
				}
			}
		}
		return nav;
	}

	private static boolean isIris() {
		return (IRIS.equalsIgnoreCase(getWebAppName()));
	}

	private static boolean isBackOffice() {
		return (BACKOFFICE.equalsIgnoreCase(getWebAppName()));
	}
	
	private static String getWebAppName() {
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("who-am-i.properties");
		String value = cpl.getProperty("this.webapp");
		System.out.println("[NavigationLogger::getWebAppName] who-am-i.properties, this.webapp = " + value);
		return value;
	}
	
	public static void debug(String arg) {
		getNav().debug(arg);
	}

	public static boolean isDebugEnabled() {
		return getNav().isDebugEnabled();
	}
	
	public static boolean isLocalEnv() {
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("who-am-i.properties");
		String value = cpl.getProperty("this.env");
		System.out.println("[NavigationLogger::isLocalEnv] who-am-i.properties, this.env = " + value);
		return "local".equalsIgnoreCase(value);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NavigationLogger.debug("Hello world");
	}

	public static void addToJspMap(String jsp) {
		//System.out.println("[NavigationLogger::addToJspMap] jsp = " + jsp);
		if (jspMap == null) {
			jspMap = new HashMap();
		}
		Object o = jspMap.get(jsp);
		NavigationPath navPath = new NavigationPath(currentAction, currentMethod, currentForwardName, currentForwardPath);
		addNavigationPath(navPath, jsp);
		if (o == null) {
			NavigationInfo navInfo = new NavigationInfo();
			navInfo.jspName = jsp;
			navInfo.hitCount = 1;
			navInfo.addNavigationPath(navPath);
			//System.out.println("[NavigationLogger::addToJspMap] 1st time: " + navInfo);
			jspMap.put(jsp, navInfo);
		} else {
			NavigationInfo navInfo = (NavigationInfo)o;
			navInfo.hitCount++;
			navInfo.addNavigationPath(navPath);
			//System.out.println("[NavigationLogger::addToJspMap] n-th time: " + navInfo);
			jspMap.put(jsp, navInfo);
		}
	}
	
	public static void addNavigationPath(NavigationPath navPath, String jsp) {
		if (navigationPaths == null) {
			navigationPaths = new HashMap();
		}
		String key = navPath.getKey();
		NavigationPath existing = (NavigationPath)navigationPaths.get(key);
		if (existing != null) {
			existing.addJsp(jsp);
			existing.hitCount++;
		} else {
			navPath.addJsp(jsp);
			navPath.hitCount++;
			navigationPaths.put(key, navPath);
		}
	}
	
	public static ArrayList getJspWithCount() {
		ArrayList out = new ArrayList();
		
		if (jspMap != null) {
			Set keys = jspMap.keySet();
			if (keys.size() > 0) {
				NavigationInfo[] navInfos = new NavigationInfo[keys.size()];
				int ni = 0;
				for (Iterator kIt = keys.iterator(); kIt.hasNext(); ) {
					String key = (String)kIt.next();
					NavigationInfo navInfo = (NavigationInfo)jspMap.get(key);
					if (lastJspMap != null) {
						NavigationInfo lastNavInfo = (NavigationInfo)lastJspMap.get(key);
						navInfo.previousInfo = lastNavInfo;
					}
					//System.out.println("[NavigationLogger::getJspWithCount] Index: " + ni + ", key: " + key + ", info: " + navInfo);
					navInfos[ni] = navInfo;
					ni++;
				}
				NavInfoComparator cmp = new NavInfoComparator();
				Arrays.sort(navInfos, cmp);
				for (int i = 0; i < navInfos.length; i++) {
					out.add(navInfos[i]);
				}
			}
			lastJspMap = cloneMap(jspMap);
		}
		
		return out;
	}
	
	public static NavigationPath[] getNavigationPaths() {
		NavigationPath[] pathList = null;
		if (navigationPaths != null) {
			Set keys = navigationPaths.keySet();
			if (keys.size() > 0) {
				pathList = new NavigationPath[keys.size()];
				int i = 0;
				for (Iterator kIt = keys.iterator(); kIt.hasNext(); ) {
					String key = (String)kIt.next();
					NavigationPath navPath = (NavigationPath)navigationPaths.get(key);
					if (lastNavigationPaths != null) {
						NavigationPath lastNavPath = (NavigationPath)lastNavigationPaths.get(key);
						navPath.previousPath = lastNavPath;
					}
					pathList[i] = navPath;
					i++;
				}
				Arrays.sort(pathList, new NavPathComparator());
			}
			lastNavigationPaths = cloneMap2(navigationPaths);
		}
		return pathList;
	}

	private static HashMap cloneMap(HashMap jspMap) {
		HashMap cloned = new HashMap();
		Set keys = jspMap.keySet();
		if (keys.size() > 0) {
			for (Iterator kIt = keys.iterator(); kIt.hasNext(); ) {
				String key = (String)kIt.next();
				Object value = jspMap.get(key);
				NavigationInfo niCloned = ((NavigationInfo)value).cloneNavInfo();
				cloned.put(key, niCloned);
			}
		}
		return cloned;
	}
	
	private static HashMap cloneMap2(HashMap map) {
		HashMap cloned = new HashMap();
		Set keys = map.keySet();
		if (keys.size() > 0) {
			for (Iterator kIt = keys.iterator(); kIt.hasNext(); ) {
				String key = (String)kIt.next();
				Object value = map.get(key);
				NavigationPath npCloned = ((NavigationPath)value).cloneNavPath();
				cloned.put(key, npCloned);
			}
		}
		return cloned;
	}

	static class NavInfoComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			if (o1 != null && o2 != null) {
				NavigationInfo ni1 = (NavigationInfo)o1;
				NavigationInfo ni2 = (NavigationInfo)o2;
				String s1 = ni1.jspName;
				String s2 = ni2.jspName;
				if (s1 == null) s1 = "";
				if (s2 == null) s2 = "";
				return s1.compareTo(s2);
			}
			return 0;
		}
		
	}

	static class NavPathComparator implements Comparator {

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
