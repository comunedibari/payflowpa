package it.tasgroup.ge.test.client;

import it.tasgroup.ge.bean.GestoreBatchEventiInterface;
import it.tasgroup.ge.bean.GestoreEventiInterface;
import it.tasgroup.ge.enums.EnumTipiEventi;
import it.tasgroup.ge.pojo.CommunicationEvent;
import it.tasgroup.ge.util.JobReport;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.NamingException;
 

 
public class EJBApplicationClient {
    private static final String LOOKUP_STRING = "EBM_eBMonitor/GestoreBatchEventiBean/remote";
 
    public static void main(String[] args) {
    	GestoreBatchEventiInterface bean = doLookup();
    	//CommunicationEvent ce = new CommunicationEvent(EnumTipiEventi.INVIOQUIETANZA.getEventoCode(),"1",null);
    	//bean.eventNotify(ce);
    	
    	//HashMap result = bean.fireCommunicationEvt(ce);
    	JobReport jReport = bean.executeJob();
    	System.out.println("***********************************");
    	System.out.println(jReport);
    	/*
    	HashMap result =null;
		if (result !=null){
			String test = (String)result.get("TEST");
			if (test!=null){
				System.out.println("***********************************");
				System.out.println("CHIAMATA GESTORE EVENTI OK!!!!!!!!!");
				System.out.println("***********************************");
			}
			else
			{
				System.out.println("***********************************");
				System.out.println("CHIAMATA GESTORE EVENTI KO!!!!!!!!!");
				System.out.println("***********************************");
			}
		}*/
        
    }
 
    private static GestoreBatchEventiInterface doLookup() {
        Context context = null;
        GestoreBatchEventiInterface bean = null;
        try {
            // 1. Obtaining Context
            context = JNDILookupClass.getInitialContext();
            // 2. Lookup and cast
            bean = (GestoreBatchEventiInterface) context.lookup(LOOKUP_STRING);
            Object obj = context.lookup(LOOKUP_STRING);
            System.out.println("");
 
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
