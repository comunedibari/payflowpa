package it.nch.fwk.fo.web.util.text;


public class IbanFormat {

	public static String ibanFormat(String s)
	{
		int length=s.length();
		System.out.print("length=" + length);
		if (length<14) return s;
		String country=s.substring(0,2);
		//System.out.print("country=" + country);
		String codice=s.substring(2,3);
		String codice1=s.substring(3,4);
		String abi=s.substring(4,9);
		//System.out.print("abi=" + abi);
		String cab=s.substring(9,14);
		//System.out.print("cab=" + cab);
		String cc=s.substring(14);
		
		String out=country + " " + codice + " " + codice1 + " " + abi + " " + cab + " " + cc; 
		//System.out.print("out=" + out);
		return out;
	}
	
	public static String ibanNoFormat(String s)
	{
		if (s==null) return "";
		int length=s.length();
		if (length<14) return s;
		String[] sp=  s.split(" ");
	    int i=0;
	    String out="";
	    //System.out.print("sp.length=" + sp.length);
		while(i<sp.length)
		{
			out=out + sp[i];
			 //System.out.print("word=" + sp[i]);
			i++;
		}
		
		//System.out.print("out=" + out);
		return out;
	}
}
