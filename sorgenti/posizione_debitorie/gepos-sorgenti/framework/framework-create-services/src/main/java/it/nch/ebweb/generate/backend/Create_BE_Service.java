
package it.nch.ebweb.generate.backend;
import it.nch.ebweb.generate.backend.service.Service;

/*
 * Created on 19-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author ee10057
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Create_BE_Service {
			
	private CreateInterfaces  ci;
	private CreateBusinessLogic  cbl;
	private CreateEJB cEJB;
	private CreateBusinessDelegate cbd;
	private SpringConfigurator sc;
	
	
	
	public Create_BE_Service(){
		
		
		
		
		 ci = new CreateInterfaces();
		 cbl = new CreateBusinessLogic();
		 cEJB = new CreateEJB();
		 cbd = new CreateBusinessDelegate();
		 sc = new SpringConfigurator();
		 
			
	}
	
    
    
	public void createServiceBackEnd(Service service, boolean generateBL) {
		
		System.out.println("======== START PROCESS ==========");
				
		
		ci.create(service);
		
		if (!generateBL)
			cbl.create(service,generateBL);
		else
			cbl.create(service);
		
		
		cEJB.create(service);		
		cbd.create(service);
		sc.rigenerateConfig();
		
		System.out.println("======== END PROCESS ==========");
	}
	
	
	
}
