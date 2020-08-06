/*
 * Creato il 7-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db.util;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class OrmEreditato {
	
	private String nameInterface;
	private String packageInterface;
	private String nameClass;
	private String packageClass;
	
	/**
	 * @return Restituisce il valore nameClass.
	 */
	public OrmEreditato (String attribute){
		
		packageClass = attribute.trim().substring(0,attribute.lastIndexOf("."));
		packageInterface = packageClass;
		nameClass = attribute.substring(attribute.lastIndexOf(".")+1,attribute.lastIndexOf("("));
		nameInterface = attribute.substring(attribute.lastIndexOf("(")+1,attribute.lastIndexOf(")"));

	}
	
	
	public String getNameClass() {
		return nameClass.trim();
	}
	
	/**
	 * @return Restituisce il valore nameInterface.
	 */
	public String getNameInterface() {
		return nameInterface.trim();
	}
	
	/**
	 * @return Restituisce il valore packageClass.
	 */
	public String getPackageClass() {
		return packageClass.trim();
	}
	/**
	 * @param packageClass Il valore packageClass da impostare.
	 */
	public void setPackageClass(String packageClass) {
		this.packageClass = packageClass;
	}
	
	public String getStringCodeImport(){
		return " extends "+nameClass.trim();
	}
	

}
