package it.tasgroup.idp.util;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneratoreIdUnivoci
{
	private long LastNumber;
	private static String macDefault=null;
	private static GeneratoreIdUnivoci Current;
	
	private static long MILLI_AL_01_01_2018 = 0;
	static {
		try {
			MILLI_AL_01_01_2018 = new SimpleDateFormat("yyyyMMdd").parse("20180101").getTime();
		} catch (ParseException pe) {
			// parsing "sicuro"
		}
	}
	private static int counter;
	
	private GeneratoreIdUnivoci()	{
		
		LastNumber=0;
		
		// Inizializzazione delle desinenze:
		// 1. Si prova con una property
		String macProperty = System.getProperty("generatoreIdUnivoci.mac");
		
		if (macProperty!=null) {
			if (macProperty.length()>3) {
				throw new IllegalArgumentException("La system property 'generatoreIdUnivoci.mac' usata come radice del generatore id univoci deve essere max 3 caratteri");
			} else {
				macDefault = macProperty;
				System.out.println("GeneratoreIdUnivoci inizializzato da System Property 'generatoreIdUnivoci.mac' ");
			}
				
		} else {
			// 2. Si assegna la desinenza dell'IP della macchina
			try {
				InetAddress IP=InetAddress.getLocalHost();
				String ipAddress=IP.getHostAddress();
				macDefault= fillNumerico(ipAddress.substring(ipAddress.lastIndexOf(".")+1), 3);
				System.out.println("GeneratoreIdUnivoci inizializzato con  ultime tre cifre IP del server "+IP.getHostAddress());
			} catch (Exception e ) {
				// 3. default se ci sono problemi
				macDefault="DEF";
				System.out.println("GeneratoreIdUnivoci inizializzato con default");
			}
		}	
		
		System.out.println("GeneratoreIdUnivoci, desinenza inizializzata con il valore '"+macDefault+"'");
		
	}
	
	public static GeneratoreIdUnivoci GetCurrent()	{
		if (Current==null)
			Current = new GeneratoreIdUnivoci();
		
				
		
		return Current;
	}
	
	
	public synchronized String generaId() {
		return generaId(null);
	}
	
	/**
	 * 
	 * @param nomeMacchina
	 * @return
	 */
	public synchronized String generaId(String nomeMacchina)	{
		
		String mac = macDefault;
		
		if (nomeMacchina !=null) {
			mac = nomeMacchina;
		} 
		
		if (LastNumber == 1000000) //32^4 = 1048576 > 1000000
			LastNumber = 0;			
		else LastNumber++;
		String ms = new Long(System.currentTimeMillis()).toString();
		String inc = this.fillNumerico(Long.toString(LastNumber,32).toUpperCase(),4);
		return ms+inc+mac;
	}
	
	/**
	 * 
	 * @param num
	 * @param n
	 * @return
	 */
	private String fillNumerico(String num, int n)	{
		String x = num;		
		for (int i=x.length(); i<n; i++)
			x="0"+x;
		return x;
	}
	
	/*
	 * torna una string di sole cifre numeriche che, come numero, deve essere nel range dei long positivi
	 * il long java e' di 8 byte col segno e quindi il range dei long positivi e': 0 - 9.223.372.036.854.775.807 ("quasi" 19 cifre)
	 */
	public static String longLike() {

		int idMacchina = 0;
		if (macDefault != null) {
			idMacchina = macDefault.charAt(macDefault.length() - 1) % 10;
		}
		
		String milli = Long.toString(System.currentTimeMillis() - MILLI_AL_01_01_2018); // 31.536.000.000 milli all'anno (12 cifre per i prossimi 30 anni)
		String milliFilled = ("0000000000000" + milli).substring(milli.length());
		
		String count = Integer.toString(getCount());
		String countFilled = ("00000" + count).substring(count.length());
		
		return Long.toString(Long.parseLong("" + idMacchina + milliFilled + countFilled));
		
	}
	
	// torna un intero nel range 0 - 99.999
	public static int getCount() {
		int myCounter;
		synchronized (GeneratoreIdUnivoci.class) {
			if (counter == 100000) counter = 0;
			myCounter = counter++;
		}
		return myCounter;
	}
	
}

