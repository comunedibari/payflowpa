package it.nch.utility;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

//import it.nch.utility.*;

public class GeneratoreIdUnivoci
{
	private long LastNumber;
	private static GeneratoreIdUnivoci Current;
	
	private GeneratoreIdUnivoci()	{
		LastNumber=0;
	}
	
	public static GeneratoreIdUnivoci GetCurrent()	{
		if (Current==null)
			Current = new GeneratoreIdUnivoci();
		return Current;
	}
	
	public synchronized String generaId()	{
		String mac = "IDP";	// default
		
		if (LastNumber == 1000000) 
			LastNumber = 0;	//36 raisedTo: 4 = 1679616
		else LastNumber++;
		String ms = new Long(System.currentTimeMillis()).toString();
		String inc = this.fillNumerico(Long.toString(LastNumber,32).toUpperCase(),4);
		return ms+inc+mac;
	}

	private String fillNumerico(String num, int n)	{
		String x = num;		
		for (int i=x.length(); i<n; i++)
			x="0"+x;
		return x;
	}
	
	public String generaCodiceTransazione(){
		String msgIdCorrente = UUID.randomUUID().toString().toUpperCase();
		return StringUtils.replace(msgIdCorrente, "-", "");
	}

	public String generaCodiceRiferimento(String prefix){
		int len = prefix == null ? 15 : (15 - prefix.length());
		String msgIdCorrente = UUID.randomUUID().toString();
		return prefix + StringUtils.replace(msgIdCorrente, "-", "").substring(0, len);
	}
	
	public static void main(String[] args) {

		System.out.println("TEST ID: " + GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione());

	}
}
