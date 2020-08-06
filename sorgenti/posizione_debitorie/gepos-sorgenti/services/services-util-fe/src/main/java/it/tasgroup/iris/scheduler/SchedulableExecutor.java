/**
 * 
 */
package it.tasgroup.iris.scheduler;

import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
 * @author pazzik
 *
 */
public abstract class SchedulableExecutor implements Runnable {	
	
	/**
	 * durata massima della schedulazione (in minuti)
	 * @return durata massima
	 */
	public abstract long getDurationTime();

	/**
	 * ritardo di partenza della prima esecuzione (in minuti)
	 * @return ritardo di partenza
	 */
	public abstract long getInitialDelay();

	/**
	 * intervallo tra due partenze del task (in minuti)
	 * @return periodo
	 */
	public abstract long getPeriod();
	
	
	@Override
	public void run() {
		Tracer.debug(SchedulableExecutor.class.getName(), "run", "START");
		// verifico se il server è abilitato all'esecuzione dei job schedulati
		if (isCurrentServerEnabledForRunning()) {
			execute();
		}
		Tracer.debug(SchedulableExecutor.class.getName(), "run", "END");
	}

	public abstract void execute();

	
	/**
	 * Verifica se il nome del server che sta eseguendo questo metodo è nella lista degli "enabled.servers".
	 * La lista dei server abilitati viene recuperata dalla properties (LETTA A CALDO) enabled.servers del file iris-gateway.properties
	 * Il nome dei server abilitato deve essere separato dalla virgola.
	 * Il valore * o vuoto o nullo equivale a "tutti i server abilitati"
	 * @return
	 * @throws Exception
	 */
	private static boolean isCurrentServerEnabledForRunning() {
		boolean isEnabled = false;
		try {
			String currentServerName = "";
			String currentServerIp = "";
			try {
				InetAddress addr = InetAddress.getLocalHost();
				currentServerName = addr.getHostName();
				currentServerIp = addr.getHostAddress();
			} catch (UnknownHostException e) {
				Tracer.error(SchedulableExecutor.class.getName(), "getServerName",  "Exception: " + e.getClass().getName());
			}	
			
			String enabledServersAsCommaSeparatedList = ConfigurationPropertyLoader.getProperties("iris-gateway.properties", true).getProperty("enabled.servers");
			
			Tracer.info(SchedulableExecutor.class.getName(), "isCurrentServerEnabledForRunning",  "enabled servers (as comma-separated list) = " + enabledServersAsCommaSeparatedList);
			if (enabledServersAsCommaSeparatedList == null || enabledServersAsCommaSeparatedList.equals("") || enabledServersAsCommaSeparatedList.equals("*")) {
				Tracer.info(SchedulableExecutor.class.getName(), "isCurrentServerEnabledForRunning",  "all servers are enabled");
				isEnabled = true;
			} else {
				StringTokenizer st = new StringTokenizer(enabledServersAsCommaSeparatedList, ",");
				while (st.hasMoreTokens()) {
					String cur = st.nextToken().trim();
					if (cur.equalsIgnoreCase(currentServerName) || cur.equals(currentServerIp)) {
						Tracer.debug(SchedulableExecutor.class.getName(), "isCurrentServerEnabledForRunning",  currentServerName + " found");
						isEnabled = true;
						break;
					}
				}
			}
			Tracer.info(SchedulableExecutor.class.getName(), "isCurrentServerEnabledForRunning",  "Current server (" + currentServerName + " [" + currentServerIp + "]) is enabled for running? " + isEnabled);
			
		} catch (RuntimeException re) {
			Tracer.error(SchedulableExecutor.class.getName(), "isCurrentServerEnabledForRunning",  "Exception: " + re.getClass().getName(), re);
		}
		return isEnabled;
	}

	
}
