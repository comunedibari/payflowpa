/*
 * Created on 21-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.ebweb.generate.backend;

import it.nch.ebweb.generate.backend.service.Azione;
import it.nch.ebweb.generate.backend.service.Service;
import it.nch.ebweb.generate.core.CreatorCostanst;
import it.nch.ebweb.generate.core.Syntax;
import it.nch.ebweb.generate.core.Writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author FelicettiA
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreateInterfaces extends Creator{

	PrintStream outputStreamShell;
	PrintStream outputStream;
	Writer writer;
	
	String _package=null;
	String pathClasse=null;
	String service=null;
	String projectPackage="it.nch";
	Azione[] azioni=null;
	boolean generateTestMethod=false;

	public void create(Service service) {
		
		System.out.println("");
		System.out.println("Generate Interfaces....");
		this.service= Syntax.firstUpperCase( service.getName() );
		this.azioni=service.getAzioni();
		this.generateTestMethod=service.isGenerateTestMethod();
		this.projectPackage=service.getRootPackage();
		
		

			
		
		if (service.isFrameworkInternalService()){
			
			//_package = CreatorCostanst.FWK_PACKAGE + CreatorCostanst.getCategoryName(service.getCategoria())+ ".interfaces";
			_package = CreatorCostanst.FWK_IS_PACKAGE+ CreatorCostanst.getCategoryName(service.getCategoria())+ ".interfaces";
			pathClasse = CreatorCostanst.FOLDER_FRW_INTERNAL_SERVICE_INTERFACES + CreatorCostanst.getPathSrc(_package);
			
			//System.out.println("Path Project="+pathClasse);
			//System.out.println("Package="+_package);
			
			//System.err.print("RRRRRRR");
			//System.exit(0);
			
		}else{
			_package = projectPackage+CreatorCostanst.getCategoryName(service.getCategoria()) + ".interfaces";			
			pathClasse = CreatorCostanst.FOLDER_SERVICE_MODEL_INTERFACE	+ CreatorCostanst.getPathSrc(_package);
		}
		
		
		//System.out.println("========== categoria="+CreatorCostanst.getCategoryName(service.getCategoria()));
		
			
		
		
		
		
		File path = new File(pathClasse);
		path.mkdirs();
		
		System.out.println("Path Project="+pathClasse);
		System.out.println("Package="+_package);
		/*
		 * 
		 * 
		 * Da qui vanno create le interfacce
		 */
		
		createInterface(0);
		createInterface(1);
		if (searchInternalService(azioni))
			createInterface(2);	

		
		System.out.println("Finish Generate Interfaces....");
		System.out.println("");
	}

	private void createInterface(int type) {

		try {
			
			String interf=null;

			switch(type){
			case 0:
				interf = pathClasse + "/" + service + "ServiceLocal.java";
				break;
			case 1:
				interf = pathClasse + "/" + service + "Service.java";
				break;
			case 2:
				interf = pathClasse + "/" + service + "InternalService.java";
				break;
			}	
			
			
			File file = new File(interf);

			FileOutputStream fw = null;

			try {

				fw = new FileOutputStream(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			outputStream = new PrintStream(fw);

			writer = new Writer(System.out, outputStream);
			writer.println(Syntax.packageDefine(_package));
			writer.println("");
			
			
			if (searchDTO(azioni))
				//writer.println("import "+CreatorCostanst.ROOT_PACKAGE+".dto.DTO;");
				writer.println("import it.nch.fwk.fo.dto.DTO;");
			if (searchDTOCollection(azioni))
				//writer.println("import "+CreatorCostanst.ROOT_PACKAGE+".dto.DTOCollection;");
				writer.println("import it.nch.fwk.fo.dto.DTOCollection;");
			
			writer.println("import it.nch.fwk.fo.interfaces.FrontEndContext;");
			
			if (type==1)
				writer.println("import java.rmi.RemoteException;");
			
			/*
			 * 
			 * gli import writer.println("import java.math.BigDecimal;");
			 */

			
			
					
			switch(type){
			case 0:
				
				writer.println("");
				writer.println(Syntax.interfaceDefine(service,"ServiceLocal", null));
				
				writer.addIndent();			
				writer.addIndent();
				
				
				writer.println("");
				for(int i=0;i<azioni.length;i++){
					if ((azioni[i].getInternalService()==0)||(azioni[i].getInternalService()==1))
						writer.println(Syntax.methodLocalInterface(azioni[i]));
				}	
				if (this.generateTestMethod)
					writer.println(testMethodLocalInterface());
				
				break;
			case 1:
				
				writer.println("");
				writer.println(Syntax.interfaceDefine(service,"Service", null));
				
				writer.addIndent();			
				writer.addIndent();
				
				
				writer.println("");
				for(int i=0;i<azioni.length;i++){
					if ((azioni[i].getInternalService()==0)||(azioni[i].getInternalService()==1))
						writer.println(Syntax.methodInterface(azioni[i]));
				}	
				if (this.generateTestMethod)
					writer.println(testMethodInterface());
			
				break;
			case 2:
				
				writer.println("");
				writer.println(Syntax.interfaceDefine(service,"InternalService", null));
				
				writer.addIndent();			
				writer.addIndent();
				
				
				writer.println("");
				for(int i=0;i<azioni.length;i++){
					if ((azioni[i].getInternalService()==1)||(azioni[i].getInternalService()==2))
						writer.println(Syntax.methodLocalInterface(azioni[i]));
				}	
				break;
			}	
			
			//Chiusura interfaccia
			writer.println("");
			writer.printLeftIntend("}", 2);

			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	String commento="/** \n    * Metodo di Test Autogenerato \n    **/ \n";
	
	public String testMethodLocalInterface(){
		return commento+"  public DTO test(FrontEndContext fec,DTO dto);";
	}
	
	public String testMethodInterface(){
		return commento+"  public DTO test(FrontEndContext fec,DTO dto) throws RemoteException;";		
	}
	
	
	

}
