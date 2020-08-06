package it.nch.eb.xsqlcmd.commands.db;

import java.net.InetAddress;

public class IpBasedGeneratoreIdUnivoci {
	
	// Generazione con generatore di Id Univoci in memoria.
	private static long LastNumber=0;
	private static String mac=null;
	
	public static synchronized String generaId(String nomeMacchina)	{
		
		// settaggio della componente macchina 
		// Solo alla prima chiamata. Si prende
		// le ultime tre cifre dell'IP della macchina.
		if (mac==null) {
			if (nomeMacchina !=null) {
				mac = nomeMacchina; 
			} else {
				try {
				InetAddress IP=InetAddress.getLocalHost();
				String ipAddress=IP.getHostAddress();
				mac= fillNumerico(ipAddress.substring(ipAddress.lastIndexOf(".")+1), 3);
				} catch (Exception e ) {
					// default se ci sono problemi
					mac="000";
				}
			}
		}	
		
		if (LastNumber == 1000000) 
			LastNumber = 0;			
		else LastNumber++;
		String ms = new Long(System.currentTimeMillis()).toString();
		String inc = fillNumerico(Long.toString(LastNumber,32).toUpperCase(),4);
		return ms+inc+mac;
	}
	
	private static String fillNumerico(String num, int n)	{
		String x = num;		
		for (int i=x.length(); i<n; i++)
			x="0"+x;
		return x;
	}

}
