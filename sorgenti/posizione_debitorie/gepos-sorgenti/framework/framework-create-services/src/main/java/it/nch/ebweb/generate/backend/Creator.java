/*
 * Created on 21-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.ebweb.generate.backend;

import it.nch.ebweb.generate.backend.service.Azione;
import it.nch.ebweb.generate.backend.service.Service;

/**
 * @author FelicettiA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Creator {
	
	
	public abstract void create(Service service);
	
	
	
	protected boolean searchInternalService(Azione[] azioni){
		
		for(int i=0;i<azioni.length;i++){
			Azione a=azioni[i];
			if ((a.getInternalService()==0)||(a.getInternalService()==1))
				return true;
		}
		
		return false;
	}
	
	protected boolean searchDTO(Azione[] azioni){
		
		for(int i=0;i<azioni.length;i++){
			Azione a=azioni[i];
			if ((a.getReturnType()==1)||(a.getParamType()==1))
				return true;
		}
		
		return false;
	}
	
	protected boolean searchDTOCollection(Azione[] azioni){
		
		for(int i=0;i<azioni.length;i++){
			Azione a=azioni[i];
			if ((a.getReturnType()==2)||(a.getParamType()==2))
				return true;
		}
		
		return false;
	}


}
