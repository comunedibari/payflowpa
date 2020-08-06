package it.tasgroup.ge.bean;

import it.tasgroup.ge.pojo.CommunicationEvent;

import java.util.HashMap;

import javax.ejb.Local;

@Local
public interface GestoreEventiInterface {
	public HashMap<String, String> fireCommunicationEvt(CommunicationEvent e);
	public void eventNotify(CommunicationEvent e);
	public void eventUpdate(String status, String descr, Long id, Boolean aggiornaData, int numeroTent);
}
