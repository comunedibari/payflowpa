package it.tasgroup.idp.util;

/* CLASSE UTILIZZATA PER GENERARE UN ID UNIVOCO DA ATTRIBUIRE AL CAMPO  e2emsgid DELLA TABELLA PENDENZE_CART NEL CASO DI PENDENZA CREATA PER COMUNE DI PRATO*/
public class GeneratoreIDPIdUnivoci
{
	private long LastNumber;
	private static String macDefault=null;
	private static GeneratoreIDPIdUnivoci Current;
	
	private GeneratoreIDPIdUnivoci()	{
		
		LastNumber=0;
		
		// Inizializzazione delle desinenze:
		// 1. Si prova con una property
		String macProperty = System.getProperty("generatoreIdIdpUnivoci.mac");
		
		if (macProperty!=null) {
			if (macProperty.length()>3) {
				throw new IllegalArgumentException("La system property 'generatoreIdUnivoci.mac' usata come radice del generatore id univoci deve essere max 3 caratteri");
			} else {
				macDefault = macProperty;
				System.out.println("GeneratoreIDPIdUnivoci inizializzato da System Property 'generatoreIdUnivoci.mac' ");
			}
				
		} else {
			// 2. Si assegna la desinenza dell'IP della macchina
			
				macDefault="IDP";
				System.out.println("GeneratoreIDPIdUnivoci inizializzato con default");
			
		}	
		
		System.out.println("GeneratoreIDPIdUnivoci, desinenza inizializzata con il valore '"+macDefault+"'");
		
	}
	
	public static GeneratoreIDPIdUnivoci GetCurrent()	{
		if (Current==null)
			Current = new GeneratoreIDPIdUnivoci();
		
				
		
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
		return mac+ms+inc;
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
	
}

