/*
 * Creato il 26-gen-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.profilo.menu;

import java.io.Serializable;
import java.util.Collection;

/**
 * Interfaccia che rappresenta un nodo del menu rappresentato secondo
 * il pattern Composite come nodo di un albero.
 *
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface MenuNode extends Serializable {

	public void setCode(String code);

	public String getCode();

	public void setStatoDB(Integer stato);

	public Integer getStatoDB();

	public void setLabel(String label);

	public String getLabel();

	public void setUrl(String url);

	public String getUrl();

	public void setStato(boolean stato);

	public boolean getStato();

	public void setChildren(Collection children);

	public Collection getChildren();

}
