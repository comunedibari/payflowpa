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
 * 
 * Questa versione genera il BaseImpl - Tenuto per ricordo
 */
public class CreateBusinessLogicWithBaseImpl extends  Creator{

	PrintStream outputStreamShell;
	PrintStream outputStream;
	Writer writer;

	String _package = null;
	String pathClasse = null;
	String service = null;
	Azione[] azioni = null;
	boolean generateTestMethod = false;
	int daoType;
	int categoria;
	String projectPackage="it.nch";
	
	String[] serviceCrossUsed;
	boolean isCrossService=false;
	String packImpl;
	String packDao;
	String packImplInterface; //path delle interfacce degli oggetti di business
	
	public void create(Service service,boolean generateBL) {

		
		
		
		System.out.println("");
		System.out.println("NON Genero BusinessLogic.... SOLO CONFIG (Spring)");
		this.service= Syntax.firstUpperCase( service.getName() );
		this.azioni = service.getAzioni();
		this.daoType = service.getDaoType();
		this.generateTestMethod = service.isGenerateTestMethod();
		this.categoria = service.getCategoria();
		this.serviceCrossUsed = service.getCrossServiceUsed();
		this.isCrossService = service.isCrossService();	
		this.projectPackage=service.getRootPackage();
		
		if (service.isFrameworkInternalService())
			packImpl=CreatorCostanst.FWK_IS_PACKAGE+".business"+CreatorCostanst.getCategoryName(this.categoria);
		else
			packImpl=projectPackage+CreatorCostanst.PACKAGE_BUSINESS_MODEL+CreatorCostanst.getCategoryName(this.categoria);
		
		packDao=packImpl+".dao.";	
		
		//*********************** aggiunte per gestione time log per creare proxy su oggetto di business ***************
		if(service.isFrameworkInternalService()){
			packImplInterface = "it.nch.is.fo.core.interfaces.";
		} else {
			packImplInterface = this.projectPackage + CreatorCostanst.getCategoryName(this.categoria)+CreatorCostanst.INTERFACES_IS_PACKAGE + ".";

		}
		//***************************************************************************************************************		
			
		createConfig();
		System.out.println("Finish Configurazione BusinessLogc.... (Spring)");
		System.out.println("");
	}
	
	private void createBL(Service theService) {
		createImpl(theService);
		createBaseImpl(theService);
	}

	private void createImpl(Service theService) {
		try {

			String _class = pathClasse + "/" + service + "Impl.java";
						
			File file = new File(_class);
			
			if (file.exists()) {
				warn();
				System.out.println("L'Impl esiste gia', sarebbe da avventati sovrascriverlo");
				return;
			}

			FileOutputStream fw = new FileOutputStream(file);
			outputStream = new PrintStream(fw);

			writer = new Writer(System.out, outputStream);
			writer.println(Syntax.packageDefine(_package));
			writer.println("");
			
			//writer.println("import it.nch.fwk.fo.interfaces.FrontEndContext;");
			//writer.println("import it.nch.fwk.fo.cross.AbstractJavaBean;");
			//writer.println("import java.rmi.RemoteException;");
			
			String intImport="";
			// If the root package is null it's supposed to be an internal service 
			if(projectPackage==null)
				intImport="import "+CreatorCostanst.FWK_IS_PACKAGE+ CreatorCostanst.getCategoryName(categoria) + ".interfaces";
			else
				intImport="import "+projectPackage+ CreatorCostanst.getCategoryName(categoria) + ".interfaces";

			if (searchInternalService(azioni)){
				writer.println(intImport+"."+Syntax.firstUpperCase(service)+"InternalService;");
			}
			writer.println(intImport+"."+Syntax.firstUpperCase(service)+"ServiceLocal;");			
			
			writer.println("import "+CreatorCostanst.FWK_PACKAGE+".das.DAODatabase;");
			writer.println("import "+CreatorCostanst.FWK_PACKAGE+".das.exception.DasCheckedException;");
			writer.println("import "+CreatorCostanst.FWK_PACKAGE+".core.BackEndContext;");
			writer.println("import it.nch.fwk.fo.interfaces.FrontEndContext;");
		
			
			if (daoType==0)
				writer.println("import "+_package+".dao."+Syntax.firstUpperCase(service)+"DaoDB;");
			else if (daoType==1)
				writer.println("import "+_package+".dao."+Syntax.firstUpperCase(service)+"DaoNetwork;");
			else if (daoType==2){
				writer.println("import "+_package+".dao."+Syntax.firstUpperCase(service)+"DaoDB;");
			    writer.println("import "+_package+".dao."+Syntax.firstUpperCase(service)+"DaoNetwork;");
			}
			
			
			for(int i=0;i<this.serviceCrossUsed.length;i++){
				
				if(this.serviceCrossUsed[i].equalsIgnoreCase("actionworker"))
					writer.println("import "+CreatorCostanst.FWK_PACKAGE+".core"+".interfaces."+Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService;");
				else
					//writer.println("import "+CreatorCostanst.FWK_IS_PACKAGE+ServiceProperties.getIstance().getCategoriaByServizio(this.serviceCrossUsed[i])+".interfaces."+Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService;");
					writer.println("import " + theService.getRootPackage() + ServiceProperties.getIstance().getCategoriaByServizio(this.serviceCrossUsed[i])+".interfaces."+Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService;");
			}
			
						
			writer.println("import it.nch.fwk.fo.util.Tracer;");


			if (searchDTO(azioni))
				writer.println("import it.nch.fwk.fo.dto.DTO;");
			if (searchDTOCollection(azioni))
				writer.println("import it.nch.fwk.fo.dto.DTOCollection;");

			writer.println("");			
			writer.println("");
			
			writer.println("/**");
			writer.println("* Classe generata la prima volta (servizio non esistente prima). E' modificabile");
			writer.println("* Questa classe estende la classe astratta ([Serviceame]BaseImpl), le implementazioni dei metodi devono essere fornite");
			writer.println("*/");
			
			writer.println(Syntax.classDefine(service, "Impl", Syntax.getClassName(service, "BaseImpl"), null));

			//
			//	Genero i costruttori (COSTRACTOR!) nei vari casi di daoType
			//
			writer.println("// daoType = " + daoType);
			String[] args;
			
			switch (daoType) {
				case 0:	
					if (this.serviceCrossUsed.length>0){						
						args = new String[this.serviceCrossUsed.length+1];
						int j=1;
						for(int i=0;i<this.serviceCrossUsed.length;i++){
							args[j] = Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase();
							j++;
						}
						writer.println("");
						args[0] = Syntax.firstUpperCase(service)+"DaoDB daoDB";															
						writer.println(Syntax.costractorDefine(service,"Impl",args,null));			
						writer.print(" super(daoDB");
						for(int i=0;i<this.serviceCrossUsed.length;i++){						
							writer.print(", ");
							writer.print(this.serviceCrossUsed[i].toLowerCase());
						}
						writer.println(");");
						writer.println("}"); // chiusa graffa
					} else {
						args = new String[1];
						args[0] = Syntax.firstUpperCase(service)+"DaoDB daoDB";
															
						writer.println(Syntax.costractorDefine(service,"Impl",args,null));
						writer.println("  super(daoDB);");	
						writer.println("}"); // chiusa graffa
					}
					break;
					
				case 1:
					if (this.serviceCrossUsed.length>0){
						args = new String[this.serviceCrossUsed.length+1];
						int j=1;
						for(int i=0;i<this.serviceCrossUsed.length;i++){
							args[j] = Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase();
							j++;
						}
						writer.println("");
						
						args[0] = Syntax.firstUpperCase(service)+"DaoNetwork daoNet";
						
						writer.println(Syntax.costractorDefine(service,"Impl",args,null));
						writer.println("");
						writer.print("  super(daoNet");							
						
						for(int i=0;i<this.serviceCrossUsed.length;i++){						
							writer.print(", ");
							writer.print(this.serviceCrossUsed[i].toLowerCase());
						}
						writer.println(");");
						writer.println("}"); // chiusa graffa						
					} else {
						
						args = new String[1];
						args[0] = Syntax.firstUpperCase(service)+"DaoNetwork daoNet";
						
						writer.println(Syntax.costractorDefine(service,"Impl",args,null));
						writer.println("");
						writer.println("  super(daoNet);");		
					}
					break;
				case 2:
					if (this.serviceCrossUsed.length>0){
						args = new String[this.serviceCrossUsed.length+2];
						int j=2;
						for(int i=0;i<this.serviceCrossUsed.length;i++){
							args[j] = Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase();
							j++;
						}
						writer.println("");					
						
						args[0] = Syntax.firstUpperCase(service)+"DaoDB daoDB";					
						args[1] = Syntax.firstUpperCase(service)+"DaoNetwork daoNet";		
						
						writer.println(Syntax.costractorDefine(service,"Impl",args,null));
						writer.println("");
						writer.print("	super(daoDB, daoNet");
						
						for(int i=0;i<this.serviceCrossUsed.length;i++){	
							writer.print(", ");
							writer.print(this.serviceCrossUsed[i].toLowerCase());	
						}
						writer.println(");");
						writer.println("}"); // chiusa graffa
						
					} else {
						args = new String[2];
						writer.println("");					
						
						args[0] = Syntax.firstUpperCase(service)+"DaoDB daoDB";					
						args[1] = Syntax.firstUpperCase(service)+"DaoNetwork daoNet";				
						
						writer.println(Syntax.costractorDefine(service,"Impl",args,null));					
						
						writer.println("");
						writer.println("  super(daoDB, daoNet);");					
						writer.println("}"); // chiusa graffa		
					}
					break;				 
			}		

			writer.println("");
			
			//
			//	Creo i metodi vuoti
			//
			for (int i = 0; i < azioni.length; i++) {				
				writer.println(Syntax.methodDefine(azioni[i]));
				writer.println("");
			}
			if (this.generateTestMethod)
				createMethodTest();
			
			
			//Chiusura BL
			writer.println("");
			writer.printLeftIntend("}", 2);

			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
	}
	
	/**
	 * Questa era vecchia createBL, splittata nella creazione dell'Impl e del BaseImpl
	 * @param theService
	 */
	private void createBaseImpl(Service theService) {

		try {

			//String _class = pathClasse + "/" + service + "Impl.java";
			String _class = pathClasse + "/" + service + "BaseImpl.java";
						
			File file = new File(_class);

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
			
			//writer.println("import it.nch.fwk.fo.core.StatefulSessionManager;");
			writer.println("import it.nch.fwk.fo.interfaces.FrontEndContext;");
			writer.println("import it.nch.fwk.fo.cross.AbstractJavaBean;");
			
			writer.println("import java.rmi.RemoteException;");
			
			String intImport="";
			// If the root package is null it's supposed to be an internal service 
			if(projectPackage==null)
				intImport="import "+CreatorCostanst.FWK_IS_PACKAGE+ CreatorCostanst.getCategoryName(categoria) + ".interfaces";
			else
				intImport="import "+projectPackage+ CreatorCostanst.getCategoryName(categoria) + ".interfaces";

			if (searchInternalService(azioni)){
				writer.println(intImport+"."+Syntax.firstUpperCase(service)+"InternalService;");
			}
			writer.println(intImport+"."+Syntax.firstUpperCase(service)+"ServiceLocal;");			
			
			writer.println("import "+CreatorCostanst.FWK_PACKAGE+".das.DAODatabase;");
			writer.println("import "+CreatorCostanst.FWK_PACKAGE+".das.exception.DasCheckedException;");
			writer.println("import "+CreatorCostanst.FWK_PACKAGE+".core.BackEndContext;");
		
			
			if (daoType==0)
				writer.println("import "+_package+".dao."+Syntax.firstUpperCase(service)+"DaoDB;");
			else if (daoType==1)
				writer.println("import "+_package+".dao."+Syntax.firstUpperCase(service)+"DaoNetwork;");
			else if (daoType==2){
				writer.println("import "+_package+".dao."+Syntax.firstUpperCase(service)+"DaoDB;");
			    writer.println("import "+_package+".dao."+Syntax.firstUpperCase(service)+"DaoNetwork;");
			}
			
			
			for(int i=0;i<this.serviceCrossUsed.length;i++){
				
				if(this.serviceCrossUsed[i].equalsIgnoreCase("actionworker"))
					writer.println("import "+CreatorCostanst.FWK_PACKAGE+".core"+".interfaces."+Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService;");
				else
					//writer.println("import "+CreatorCostanst.FWK_IS_PACKAGE+ServiceProperties.getIstance().getCategoriaByServizio(this.serviceCrossUsed[i])+".interfaces."+Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService;");
					writer.println("import " + theService.getRootPackage() + ServiceProperties.getIstance().getCategoriaByServizio(this.serviceCrossUsed[i])+".interfaces."+Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService;");
			}
			
						
			writer.println("import it.nch.fwk.fo.util.Tracer;");


			if (searchDTO(azioni))
				writer.println("import it.nch.fwk.fo.dto.DTO;");
			if (searchDTOCollection(azioni))
				writer.println("import it.nch.fwk.fo.dto.DTOCollection;");
		
		
			/*
			 * 
			 * gli import writer.println("import java.math.BigDecimal;");
			 */

			writer.println("");
			String[] interf;
			
			if (searchInternalService(azioni)){
				
			  interf=new String[2];
			  interf[0]= service+"ServiceLocal";
			  interf[1]= service+"InternalService";
			  
			}else{
				
				 interf=new String[1];
				 interf[0]= service+"ServiceLocal";
			}
			
			writer.println("");
			
			writer.println("/**");
			writer.println("* Classe generata, NON MODIFICABILE.");
			writer.println("* Questa e' una classe astratta ([Serviceame]BaseImpl), le implementazioni dei metodi devono essere fornite");
			writer.println("* nella sua sottoclasse [Servicename]Impl");
			writer.println("*/");
			
			//writer.println(Syntax.classDefine(service, "Impl", "AbstractJavaBean", interf));
			writer.println(Syntax.classDefine(service, "BaseImpl", "AbstractJavaBean", interf, true)); // La definisco abstract
		
			writer.addIndent();
			writer.addIndent();
			writer.println("");
			writer.println("");
			writer.println("// daoType = " + daoType);

			
			String[] args;
			
			switch(daoType){
			
				case 0:	
					
					if (this.serviceCrossUsed.length>0){
						
						args = new String[this.serviceCrossUsed.length+1];
						int j=1;
						for(int i=0;i<this.serviceCrossUsed.length;i++){
							args[j] = Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase();
							j++;
						}
						
						//writer.println(Syntax.firstUpperCase(service)+"DaoDB daoDB=null;");
						writer.println("protected " + Syntax.firstUpperCase(service)+"DaoDB daoDB=null;");
						
						for(int i=0;i<this.serviceCrossUsed.length;i++){						
							//writer.println(Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase()+"= null;");	
							writer.println("protected " + Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase()+"= null;");	
						}
						writer.println("");
						args[0] = Syntax.firstUpperCase(service)+"DaoDB daoDB";															
						//writer.println(Syntax.costractorDefine(service,"Impl",args,null));
						writer.println(Syntax.costractorDefine(service,"BaseImpl",args,null));
						
						writer.println("");
						writer.println("  this.daoDB=daoDB;");	
						
						for(int i=0;i<this.serviceCrossUsed.length;i++){						
							writer.println("  this."+this.serviceCrossUsed[i].toLowerCase()+"="+this.serviceCrossUsed[i].toLowerCase()+";");	
						}
					}
					else{
												
						args = new String[1];
					
						//writer.println(Syntax.firstUpperCase(service)+"DaoDB daoDB=null;");
						writer.println("protected " + Syntax.firstUpperCase(service)+"DaoDB daoDB=null;");

						writer.println("");
										
						args[0] = Syntax.firstUpperCase(service)+"DaoDB daoDB";
															
						//writer.println(Syntax.costractorDefine(service,"Impl",args,null));
						writer.println(Syntax.costractorDefine(service,"BaseImpl",args,null));
						writer.println("");
						writer.println("  this.daoDB=daoDB;");	
					}
					break;
				case 1:
					
					
					if (this.serviceCrossUsed.length>0){
						
						args = new String[this.serviceCrossUsed.length+1];
						int j=1;
						for(int i=0;i<this.serviceCrossUsed.length;i++){
							args[j] = Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase();
							j++;
						}
						
						//writer.println(Syntax.firstUpperCase(service)+"DaoNetwork daoNet=null;");
						writer.println("protected " + Syntax.firstUpperCase(service)+"DaoNetwork daoNet=null;");
						writer.println("");
						
						args[0] = Syntax.firstUpperCase(service)+"DaoNetwork daoNet";
						
						for(int i=0;i<this.serviceCrossUsed.length;i++){						
							//writer.println(Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase()+"= null;");	
							writer.println("protected " + Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase()+"= null;");	
						}
						
						//writer.println(Syntax.costractorDefine(service,"Impl",args,null));
						writer.println(Syntax.costractorDefine(service,"BaseImpl",args,null));
						writer.println("");
						writer.println("  this.daoNet=daoNet;");							
						
						for(int i=0;i<this.serviceCrossUsed.length;i++){						
							writer.println("  this."+this.serviceCrossUsed[i].toLowerCase()+"="+this.serviceCrossUsed[i].toLowerCase()+";");	
						}
						
					}
					else{
						
					args = new String[1];
					
					//writer.println(Syntax.firstUpperCase(service)+"DaoNetwork daoNet=null;");
					writer.println("protected " + Syntax.firstUpperCase(service)+"DaoNetwork daoNet=null;");
					writer.println("");
					
					args[0] = Syntax.firstUpperCase(service)+"DaoNetwork daoNet";
					
					//writer.println(Syntax.costractorDefine(service,"Impl",args,null));
					writer.println(Syntax.costractorDefine(service,"BaseImpl",args,null));
					writer.println("");
					writer.println("  this.daoNet=daoNet;");		
					}
					break;
				case 2:
					
					if (this.serviceCrossUsed.length>0){
						
						args = new String[this.serviceCrossUsed.length+2];
						int j=2;
						for(int i=0;i<this.serviceCrossUsed.length;i++){
							args[j] = Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase();
							j++;
						}
						
						//writer.println(Syntax.firstUpperCase(service)+"DaoDB daoDB=null;");					
						writer.println("protected " + Syntax.firstUpperCase(service)+"DaoDB daoDB=null;");					
						//writer.println(Syntax.firstUpperCase(service)+"DaoNetwork daoNet=null;");
						writer.println("protected " + Syntax.firstUpperCase(service)+"DaoNetwork daoNet=null;");
						
						for(int i=0;i<this.serviceCrossUsed.length;i++){						
							//writer.println(Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase()+"= null;");	
							writer.println("protected " + Syntax.firstUpperCase(this.serviceCrossUsed[i])+"InternalService "+this.serviceCrossUsed[i].toLowerCase()+"= null;");	
						}
						writer.println("");					
						
						args[0] = Syntax.firstUpperCase(service)+"DaoDB daoDB";					
						args[1] = Syntax.firstUpperCase(service)+"DaoNetwork daoNet";		
						
						//writer.println(Syntax.costractorDefine(service,"Impl",args,null));
						writer.println(Syntax.costractorDefine(service,"BaseImpl",args,null));
						writer.println("");
						writer.println("  this.daoNet=daoNet;");	
						writer.println("  this.daoNet=daoNet;");		
						
						for(int i=0;i<this.serviceCrossUsed.length;i++){						
							writer.println("  this."+this.serviceCrossUsed[i].toLowerCase()+"="+this.serviceCrossUsed[i].toLowerCase()+";");	
						}
						
					}else {
					args = new String[2];
					
					//writer.println(Syntax.firstUpperCase(service)+"DaoDB daoDB=null;");					
					writer.println("protected " + Syntax.firstUpperCase(service)+"DaoDB daoDB=null;");					
					//writer.println(Syntax.firstUpperCase(service)+"DaoNetwork daoNet=null;");
					writer.println("protected " + Syntax.firstUpperCase(service)+"DaoNetwork daoNet=null;");
					writer.println("");					
					
					args[0] = Syntax.firstUpperCase(service)+"DaoDB daoDB";					
					args[1] = Syntax.firstUpperCase(service)+"DaoNetwork daoNet";				
					
					//writer.println(Syntax.costractorDefine(service,"Impl",args,null));					
					writer.println(Syntax.costractorDefine(service,"BaseImpl",args,null));					
					
					writer.println("");
					writer.println("  this.daoDB=daoDB;");					
					writer.println("  this.daoNet=daoNet;");		
					}
					break;				 
			}		
						
			
			writer.println("");
			writer.println("}");
			writer.println("");

			writer.println("");
			
			for (int i = 0; i < azioni.length; i++) {				
				//writer.println(Syntax.methodDefine(azioni[i]));
				writer.println(Syntax.methodDefine_abstract(azioni[i]));
				writer.println("");
			}
			if (this.generateTestMethod)
				createMethodTest();

			//Chiusura BL
			writer.println("");
			writer.printLeftIntend("}", 2);

			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createDao(int type) {
		
		try {

			String _classDao=pathClasse+"/dao" ;
			
			File path = new File(_classDao);
			path.mkdirs();
			
			if (type==0)
				_classDao = _classDao+"/" + service + "DaoDB.java";
			else
				_classDao = _classDao+"/" + service + "DaoNetwork.java";
			
			
			System.out.println("Creo DAO="+_classDao);
			File file = new File(_classDao);
			
			if (file.exists()) {
				warn();

				System.out.println("Il DAO esiste gia', sarebbe da stolti cancellarlo");
				return;
			}

			FileOutputStream fw = null;

			try {

				fw = new FileOutputStream(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			outputStream = new PrintStream(fw);

			writer = new Writer(System.out, outputStream);
			writer.println(Syntax.packageDefine(_package+".dao"));
			writer.println("");
			
			//writer.println("import "+CreatorCostanst.FWK_PACKAGE+".core.StatefulSessionManager;"); Non i� usato da un pezzo!
	    	writer.println("import "+CreatorCostanst.FWK_PACKAGE+".cross.AbstractJavaBean;");
			writer.println("import "+CreatorCostanst.FWK_PACKAGE+".core.BackEndContext;");
	
			writer.println("import java.rmi.RemoteException;");
			
			if (searchDTO(azioni))
				writer.println("import it.nch.fwk.fo.dto.DTO;");
			if (searchDTOCollection(azioni))
				writer.println("import it.nch.fwk.fo.dto.DTOCollection;");

						
			if (type==0){			     
				
				writer.println("import "+CreatorCostanst.FWK_PACKAGE+".das.DAODatabase;");
				
				writer.println("");
				writer.addIndent();
				writer.addIndent();
								
				writer.println(Syntax.classDefine(service, "DaoDB", "DAODatabase", null));
		
				writer.addIndent();	
				
				writer.println("");
				writer.println("");
				writer.println(Syntax.costractorDefine(service, "DaoDB",null,null));
	     	}
			else{	
				
				writer.println("import "+CreatorCostanst.ROOT_PACKAGE+".DAONetwork;");
				writer.println("import it.nch.fwk.fo.proxy.Envelope;");
				writer.println("import it.nch.fwk.fo.proxy.ProxyClient;");
				writer.println("");
				writer.addIndent();
				writer.addIndent();
				
				writer.println(Syntax.classDefine(service, "DaoNetwork", "DAONetwork", null));
				
				writer.addIndent();	
				
				writer.println("");
				writer.println("");
				writer.println(Syntax.costractorDefine(service,  "DaoNetwork",null,null));
	          				
			}          
					
			writer.println("  super();");				
			writer.println("}");
						
			
			if (this.generateTestMethod){
				
				switch(type){
				case 0:
					
				    writer.println("");
					writer.println("        // TEST Method");
					writer.println("");
					writer.println(" public DTO testDB(BackEndContext context, DTO dto) throws RemoteException{");
					writer.println("    return null;");
					writer.println("}") ;	
					
					
				break;	
				case 1:
					
					writer.println("");
					writer.println("        // TEST Method");                   
					
					writer.println(" public DTO testNet(DTO dto) {");
					
					
					writer.println("   return null;");
					writer.println("}") ;	
					
					
				break;				
				}
			}
			
			// Chiusura DAO
			
			writer.println("");
			writer.printLeftIntend("}", 2);

			outputStream.close();
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void warn() {
		System.out.println();
		System.out.println("        ^");
		System.out.println("       / \\");
		System.out.println("      /   \\");
		System.out.println("     /  |  \\");
		System.out.println("    /   |   \\");
		System.out.println("   /    |    \\");
		System.out.println("  /           \\");
		System.out.println(" /      o      \\");
		System.out.println("/_______________\\");
		System.out.println();
	}

	private void createMethodTest(){
		
		writer.println("");
		writer.println("public DTO test(FrontEndContext fec, DTO dto) {");
		writer.println("");
		writer.println("	System.out.println(\"***************** FINE TEST ******************\");");
		writer.println("	return null;");
		writer.println("}");
	}
	

	
	
	private void createConfig(){
		
		try {
			
			
			String pathConf = CreatorCostanst.FOLDER_CONFIGURATION;

		    File path = new File(pathConf);
		    path.mkdirs();
		    
		    System.out.println("");
		    System.out.println("GenerareConfiguration...");
		    System.out.println("Path Config Project=" + pathConf);		   			
		    
		    String _conf ="";
		    
		    if (this.isCrossService)
		    	_conf= pathConf + "/applicationCrossContext_" + service + ".xml";
		    else
		    	_conf= pathConf + "/applicationContext_" + service + ".xml";
							
			File file = new File(_conf);

			FileOutputStream fw = null;

			try {

				fw = new FileOutputStream(file);
			} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}

			outputStream = new PrintStream(fw);
			writer = new Writer(System.out, outputStream);
				
			
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">");
			writer.println("<!--");
			writer.println("   - Application context definition for \"eB.Web\".");
			writer.println("-->");	
			writer.println("");
			writer.println("<beans>");   
			
		  
			switch(daoType){
				case 0:
					writer.println("  <bean id=\""+this.service.toLowerCase()+"DaoDB\" class=\""+packDao+this.service+"DaoDB\">");
					writer.println("  </bean>");
					writer.println("");
					
					//*****************************
					//aggiunte per logger con proxy
					
					//proxy
					writer.println(" <bean id=\""+this.service.toLowerCase()+"Business\" class=\"org.springframework.aop.framework.ProxyFactoryBean\"> ");
					writer.println("    <property name=\"proxyInterfaces\"> ");
					writer.println("         <value>"+packImplInterface+this.service+"ServiceLocal,"+packImplInterface+this.service+"InternalService</value>"); 
					writer.println("    </property>");
					writer.println("    <property name=\"interceptorNames\">");
					writer.println("       <list>");
					writer.println("          <!-- (Temporarily) disabled ArgumentLoggingInterceptor because too heavy -->");
					writer.println("          <value>"+this.service.toLowerCase()+"BusinessLoggerInterceptor</value>");
					writer.println("          <value>"+this.service.toLowerCase()+"PerformanceLoggingInterceptor</value>");
					writer.println("          <value>"+this.service.toLowerCase()+"ArgumentLoggingInterceptor</value>");
					writer.println("       </list>");
					writer.println("    </property>");
					writer.println("    <property name=\"target\">");
					writer.println("       <ref bean=\""+this.service.toLowerCase()+"BusinessTarget\"/>");
					writer.println("    </property>");
					writer.println(" </bean>");
					writer.println("");
					
					//interceptor
					writer.println("  <bean id=\""+this.service.toLowerCase()+"BusinessLoggerInterceptor\" class=\"it.nch.is.fo.delegate.proxy.NchBusinessObjectInterceptor\"/>");
					writer.println("");
					writer.println("  <bean id=\""+this.service.toLowerCase()+"PerformanceLoggingInterceptor\" class=\"it.nch.is.fo.delegate.proxy.LoggingInterceptor\"/>");
					writer.println("");
					writer.println("  <bean id=\""+this.service.toLowerCase()+"ArgumentLoggingInterceptor\" class=\"it.nch.is.fo.delegate.proxy.ArgumentLoggingInterceptor\"/>");
					writer.println("");
					//*****************************
					
					writer.println("  <bean id=\""+this.service.toLowerCase()+"BusinessTarget\" class=\""+packImpl+"."+this.service+"Impl\"> ");
					writer.println("      <constructor-arg><ref bean=\""+this.service.toLowerCase()+"DaoDB\" /></constructor-arg>	");
					
					for(int i=0;i<this.serviceCrossUsed.length;i++){						
						
						writer.println("      <constructor-arg><ref bean=\""+this.serviceCrossUsed[i].toLowerCase()+"Business\" /></constructor-arg>	");
						
					}
					
					writer.println("  </bean>");
					break;
				case 1:
					writer.println("  <bean id=\""+this.service.toLowerCase()+"DaoNetwork\" class=\""+packDao+this.service+"DaoNetwork\">");
					writer.println("  </bean>");
					
					//*****************************
					//aggiunte per logger con proxy
					//proxy
					writer.println(" <bean id=\""+this.service.toLowerCase()+"Business\" class=\"org.springframework.aop.framework.ProxyFactoryBean\"> ");
					writer.println("    <property name=\"proxyInterfaces\"> ");
					writer.println("         <value>"+packImplInterface+this.service+"ServiceLocal,"+packImplInterface+this.service+"InternalService</value>"); 
					writer.println("    </property>");
					writer.println("    <property name=\"interceptorNames\">");
					writer.println("       <list>");
					writer.println("          <value>"+this.service.toLowerCase()+"LoggerInterceptor</value>");
					writer.println("       </list>");
					writer.println("    </property>");
					writer.println("    <property name=\"target\">");
					writer.println("       <ref bean=\""+this.service.toLowerCase()+"BusinessTarget\"/>");
					writer.println("    </property>");
					writer.println(" </bean>");
					writer.println("");
					
					//interceptor
					writer.println("  <bean id=\""+this.service.toLowerCase()+"LoggerInterceptor\" class=\"it.nch.is.fo.delegate.proxy.NchBusinessObjectInterceptor\"/>");
					writer.println("        <bean id=\""+this.service.toLowerCase()+"BusinessTarget\" class=\""+packImpl+"."+this.service+"Impl\"> ");
					writer.println("            <constructor-arg><ref bean=\""+this.service.toLowerCase()+"DaoNetwork\" /></constructor-arg>	");

					//*****************************					
					for(int i=0;i<this.serviceCrossUsed.length;i++){						
						
						writer.println("      <constructor-arg><ref bean=\""+this.serviceCrossUsed[i].toLowerCase()+"Business\" /></constructor-arg>	");
						
					}
					writer.println("  </bean>");
					
					break;
				case 2:
					writer.println("  <bean id=\""+this.service.toLowerCase()+"DaoDB\" class=\""+packDao+this.service+"DaoDB\">");
					writer.println("  </bean>");
					writer.println("");
					writer.println("  <bean id=\""+this.service.toLowerCase()+"DaoNetwork\" class=\""+packDao+this.service+"DaoNetwork\">");
					writer.println("  </bean>");
					writer.println("");
					//*****************************
					//aggiunte per logger con proxy
					//proxy
					writer.println(" <bean id=\""+this.service.toLowerCase()+"Business\" class=\"org.springframework.aop.framework.ProxyFactoryBean\"> ");
					writer.println("    <property name=\"proxyInterfaces\"> ");
					writer.println("         <value>"+packImplInterface+this.service+"ServiceLocal,"+packImplInterface+this.service+"InternalService</value>"); 
					writer.println("    </property>");
					writer.println("    <property name=\"interceptorNames\">");
					writer.println("       <list>");
					writer.println("          <value>"+this.service.toLowerCase()+"LoggerInterceptor</value>");
					writer.println("       </list>");
					writer.println("    </property>");
					writer.println("    <property name=\"target\">");
					writer.println("       <ref bean=\""+this.service.toLowerCase()+"BusinessTarget\"/>");
					writer.println("    </property>");
					writer.println(" </bean>");
					writer.println("");
					
					//interceptor
					writer.println("  <bean id=\""+this.service.toLowerCase()+"LoggerInterceptor\" class=\"it.nch.is.fo.delegate.proxy.NchBusinessObjectInterceptor\"/>");
					writer.println("      <bean id=\""+this.service.toLowerCase()+"BusinessTarget\" class=\""+packImpl+"."+this.service+"Impl\"> ");
					writer.println("         <constructor-arg><ref bean=\""+this.service.toLowerCase()+"DaoDB\" /></constructor-arg>	");			
					writer.println("         <constructor-arg><ref bean=\""+this.service.toLowerCase()+"DaoNetwork\" /></constructor-arg>	");

					//*****************************					
					for(int i=0;i<this.serviceCrossUsed.length;i++){						
						
						writer.println("      <constructor-arg><ref bean=\""+this.serviceCrossUsed[i].toLowerCase()+"Business\" /></constructor-arg>	");
						
					}
					writer.println("  </bean>");
					break;
			}
			writer.println("</beans>");   
			System.out.println("Finish Generare Configuration...");
			  
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/* (non Javadoc)
	 * @see it.nch.ebweb.generate.backend.Creator#create(it.nch.ebweb.generate.backend.service.Service)
	 */
	public void create(Service service) {
		// TODO Stub di metodo generato automaticamente
		
		System.out.println("");
		System.out.println("Generate BusinessLogic....");
		this.service= Syntax.firstUpperCase( service.getName() );
		this.azioni = service.getAzioni();
		this.daoType = service.getDaoType();
		this.generateTestMethod = service.isGenerateTestMethod();
		this.categoria = service.getCategoria();
		this.serviceCrossUsed = service.getCrossServiceUsed();
		this.isCrossService = service.isCrossService();
		this.projectPackage = service.getRootPackage();
		
		_package = projectPackage+CreatorCostanst.PACKAGE_BUSINESS_MODEL+CreatorCostanst.getCategoryName(categoria);		
		
		if (service.isFrameworkInternalService()){
			_package = CreatorCostanst.FWK_IS_PACKAGE+CreatorCostanst.PACKAGE_BUSINESS_MODEL+CreatorCostanst.getCategoryName(categoria);
			pathClasse = CreatorCostanst.FOLDER_FRW_INTERNAL_SERVICE+CreatorCostanst.getPathSrc(_package);
			
		}else{
			pathClasse = CreatorCostanst.FOLDER_BUSINESS_MODEL+CreatorCostanst.getPathSrc(_package);		
		}
		
		
		
		File path = new File(pathClasse);
		path.mkdirs();

		System.out.println("Path Project=" + pathClasse);
		System.out.println("Package=" + _package);

		/**
		 * 
		 * 
		 * il costruire della BL va generato a diversamente a seconda del tipo
		 * dei DAO o se necessari entrambe le tipologie
		 */
		createBL(service);

		/*
		 * 
		 * type DAO 0 DB 1 Network
		 * 2 ENTRAMBI
		 */
		if (daoType==2){
			createDao(0);
			createDao(1);
		}else
			createDao(daoType);
			
			
		createConfig();
		System.out.println("Finish Generate BusinessLogc....");
		System.out.println("");
		
	}



}
