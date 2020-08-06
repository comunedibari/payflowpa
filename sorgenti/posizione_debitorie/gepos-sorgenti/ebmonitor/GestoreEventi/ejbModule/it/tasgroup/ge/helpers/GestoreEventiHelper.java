package it.tasgroup.ge.helpers;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.pojo.CommunicationEvent;

public interface GestoreEventiHelper {

	public String fireCommunicationEvt(CommunicationEvent e, CfgEventi cfgEvento) throws Exception;
	
}
