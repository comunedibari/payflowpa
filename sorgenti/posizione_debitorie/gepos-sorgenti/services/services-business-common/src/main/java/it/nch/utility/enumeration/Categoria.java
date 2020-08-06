/**
 *
 */
package it.nch.utility.enumeration;

import java.io.Serializable;

/**
 * @author PazziK
 *
 */
public enum Categoria  implements Serializable{
	CI("Cittadino"),
	EN("Ente"),
	AZ("Azienda"),
	BO("Backoffice");

	private final String description;

	Categoria(String description){
		this.description = description;
	}
	public String getUpperDescription(){
		return description.toUpperCase();
	}
	public String getDescription(){
		return description;
	}
	public String getShortDescription(){
		return toString();
	}

	public boolean isCittadino(){
		return this.equals(Categoria.CI);
	}

	public boolean isAzienda(){
		return this.equals(Categoria.AZ);
	}

	public boolean isEnte(){
		return this.equals(Categoria.EN);
	}

	public boolean isBackOffice(){
		return this.equals(Categoria.BO);
	}
}
