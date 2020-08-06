/*
 * Creato il 5-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author sberisso
 *
 * Classe che espone i metodi per accedere ad una lista di oggetti DTOInfo.
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class DTOInfoList implements Serializable {

	/**
	 * variabili
	 */
	private ArrayList infoList = null;

	/**
	 * costruttori
	 */
	public DTOInfoList() {
		infoList = new ArrayList();
	}

	/**
	 * Costruttore che facilita la costruzione
	 * di una DTOInfoList con un'unico elemento DTOInfo
	 */
	public DTOInfoList(DTOInfo info) {
		infoList = new ArrayList();
		infoList.add(info);
	}

	/**
	 * metodi
	 */
	public ArrayList getInfoArray() {
		return infoList;
	}

	public void setInfoArray(ArrayList infoList) {
		this.infoList = infoList;
	}

	public boolean addInfo(DTOInfo info) {
		try {
			infoList.add(info);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean addInfo(int index, DTOInfo info) {
		try {
			infoList.add(index, info);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public DTOInfo getInfo(int index) {
		try {
			DTOInfo di = (DTOInfo) infoList.get(index);
			return di;
		} catch (Exception ex) {
			return null;
		}
	}

	public DTOInfo removeInfo(int index) {
		try {
			DTOInfo di = (DTOInfo) infoList.remove(index);
			return di;
		} catch (Exception ex) {
			return null;
		}
	}

	public DTOInfo setInfo(int index, DTOInfo info) {
		try {
			DTOInfo di = (DTOInfo) infoList.set(index, info);
			return di;
		} catch (Exception ex) {
			return null;
		}
	}

	public void clear() {
		infoList.clear();
	}

	public boolean contains(DTOInfo info) {
		return infoList.contains(info);
	}

	public boolean isEmpty() {
		return infoList.isEmpty();
	}

	public int size() {
		return infoList.size();
	}

	public boolean hasSeverity(int severity){
	   	 Iterator iterMap = infoList.iterator();
	  	 while(iterMap.hasNext()){
	  		if (((DTOInfo)iterMap.next()).getSeverity()==severity) {
	  			return true;
	  		}
	  	 }
		return false;
	}

	public boolean hasErrorGESeverity(int severity){
      	 Iterator iterMap = infoList.iterator();
      	 while(iterMap.hasNext()){
      		if (((DTOInfo)iterMap.next()).getSeverity()>=severity) {
      			return true;
      		}
      	 }
		return false;
	}

}
