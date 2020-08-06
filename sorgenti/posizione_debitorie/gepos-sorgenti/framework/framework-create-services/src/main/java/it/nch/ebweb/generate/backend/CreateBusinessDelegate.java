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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateBusinessDelegate extends Creator{
	
	PrintStream outputStreamShell;

	PrintStream outputStream;

	Writer writer;

	String _package = null;

	String pathClasse = null;

	String service = null;
	Azione[] azioni = null;
	boolean generateTestMethod = false;
	int daoType;
	int typeRef;
	int categoria;
	String projectPackage="it.nch";
	String[] serviceCrossUsed;
	
	/**
	 * 
	 *  Stringhe di appoggio.
	 */
	String interf;
	String servImpl;
	String intImport;
	
	String _class;
	String JNDI="";
	
	
	public void create(Service service) {

		System.out.println("");
		System.out.println("Generate BusinessDelegate....");
		
		this.service= Syntax.firstUpperCase( service.getName() );
		this.azioni = service.getAzioni();
		this.daoType = service.getDaoType();
		this.generateTestMethod = service.isGenerateTestMethod();
		this.categoria = service.getCategoria();
		this.typeRef = service.getTypeReference();
		this.projectPackage=service.getRootPackage();
		this.serviceCrossUsed = service.getCrossServiceUsed();
		
		if (service.isFrameworkInternalService()){
			
			_package = CreatorCostanst.FWK_IS_PACKAGE+".delegate"+CreatorCostanst.getCategoryName(categoria);
			
			//System.out.println("************ _package ="+_package);
			
			pathClasse = CreatorCostanst.FOLDER_BUSINESS_DELEGATOR+CreatorCostanst.getPathSrc(_package);
			
			//System.out.println("************ pathClasse ="+pathClasse);

			intImport="import "+CreatorCostanst.FWK_IS_PACKAGE+CreatorCostanst.getCategoryName(categoria) + ".interfaces";
			 _class =  CreatorCostanst.FWK_IS_PACKAGE+".delegate"+ CreatorCostanst.getCategoryName(categoria)+"."+this.service+"BusinessDelegate";
			
			interf=CreatorCostanst.FWK_IS_PACKAGE+ CreatorCostanst.getCategoryName(categoria) + ".interfaces";
			 JNDI="ejb/it/nch/is/fo/ejbs/";
		}else{

			_package = projectPackage+CreatorCostanst.PACKAGE_BUSINESS_DELEGATOR+CreatorCostanst.getCategoryName(categoria);
			
			//System.out.println("2222222************ _package ="+_package);
			
			pathClasse = CreatorCostanst.FOLDER_BUSINESS_DELEGATOR+CreatorCostanst.getPathSrc(_package);
			
			//System.out.println("2222************ pathClasse ="+pathClasse);
			
			intImport="import "+projectPackage+CreatorCostanst.getCategoryName(categoria) + ".interfaces";
			 _class = projectPackage+CreatorCostanst.PACKAGE_BUSINESS_DELEGATOR+ CreatorCostanst.getCategoryName(categoria)+"."+this.service+"BusinessDelegate";
			
			interf=projectPackage+ CreatorCostanst.getCategoryName(categoria) + ".interfaces";
			String prjPath = CreatorCostanst.getPathSrc(projectPackage);
		    JNDI="ejb/"+prjPath+"/ejbs/";
		}
		
		//System.out.println("<><><><><><><><><><><><><>final ="+pathClasse);
		
		File path = new File(pathClasse);
		path.mkdirs();

		System.out.println("Path Project=" + pathClasse);
		System.out.println("Package=" + _package);

	
		createBD();
		createConfig();


		System.out.println("Finish Generate BusinessDelegate....");
		System.out.println("");
	}
	
	
	private void createBD(){
		
	
		try {

			String _class = pathClasse + "/" + service + "BusinessDelegate.java";
			
			//System.out.println("<0><0><0><0><0><0><0><0><0><0><0><0><0>final ="+_class);
			
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
			
			writer.println("import org.springframework.beans.BeansException;");
			writer.println("import org.springframework.beans.factory.BeanFactory;");
			writer.println("import org.springframework.beans.factory.access.BeanFactoryLocator;");			
			writer.println("import org.springframework.beans.factory.access.BeanFactoryReference;");
			writer.println("import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;");	
			writer.println("import org.springframework.remoting.RemoteAccessException;");	
			//writer.println("import java.rmi.RemoteException;");
			writer.println("import it.nch.fwk.fo.cross.AbstractBusinessDelegate;");
			writer.println("import it.nch.fwk.fo.interfaces.FrontEndContext;");
			writer.println("import it.nch.fwk.fo.util.Tracer;");			
			writer.println("import it.nch.fwk.fo.locator.ServiceLocatorException;");
			writer.println("import it.nch.erbweb.client.FrontEndMessage;");
			writer.println("import it.nch.fwk.fo.exceptions.ManageFrontEndException;");
			writer.println("import it.nch.fwk.fo.exceptions.BusinessDelegateRuntimeException;");
			writer.println("import it.nch.fwk.fo.dto.DTOInfoList;");
			writer.println("import it.nch.fwk.fo.dto.DTOInfoInterface;");

			
			if (searchDTO(azioni))
				writer.println("import "+CreatorCostanst.FWK_PACKAGE+".dto.DTO;");
			if (searchDTOCollection(azioni))
				writer.println("import "+CreatorCostanst.FWK_PACKAGE+".dto.DTOCollection;");
		
			
			//System.out.println("$$$$$$$$$$$$$$ intImport="+ intImport);
			
			
			writer.println(intImport+"."+service+"ServiceLocal;");
			//MODIFICA PER LA SINGOLA INTERFACCIA
			//writer.println(intImport+"."+service+"ServiceLocal;");
			writer.println("");
			
			String[] interf = new String[1];
			//MODIFICA PER LA SINGOLA INTERFACCIA (tolto Local da ServiceLocal)
			interf[0]=service+"ServiceLocal";
			
			writer.println(Syntax.classDefine(service, "BusinessDelegate", "AbstractBusinessDelegate", interf));
			
			writer.println("");
			//MODIFICA PER IL RECUPERO DELLA SINGOLA INTERFACCIA
			writer.println("   private "+this.service+"ServiceLocal "+this.service.toLowerCase()+"Service;");
			//writer.println("   private "+this.service+"Service "+this.service.toLowerCase()+"Service;");
			writer.println("   private BeanFactoryLocator bfl;");
			writer.println("   private BeanFactoryReference bfr;");
			

		    writer.addIndent();
		    writer.addIndent();
		    writer.println("");
		    String[] _throws = new String[1];
		    _throws[0]="ServiceLocatorException";
		    writer.println(Syntax.costractorDefine(service,"BusinessDelegate",null,_throws));
		    writer.println("");
		    
		    writer.addIndent();
		    writer.addIndent();
		    
		    writer.println("  Tracer.info(this.getClass().toString(),\"Costruttore\",\"Inizio \",null);");
		    writer.println("");
		    writer.println("  bfl = SingletonBeanFactoryLocator.getInstance(\"client-beanRefFactory.xml\");");		
		    writer.println("  bfr = bfl.useBeanFactory(\"businessDelegate\");");
		    writer.println("  Tracer.info(this.getClass().toString(),\"costruttore\",\"Retrieve della bean factory \",null);");
		    writer.println("  BeanFactory bf=bfr.getFactory();");
		    writer.println("  Tracer.info(this.getClass().toString(),\"costruttore\",\"Retrieve del bean \",null);");
		    writer.println("");
		    writer.println("  try {");				
//		    writer.println("	if (bf.containsBean(\""+this.service.toLowerCase()+"Local\"))"); 
//		    writer.println("		"+this.service.toLowerCase()+"ServiceLocal=("+this.service+"ServiceLocal) bf.getBean(\""+this.service.toLowerCase()+"Local\");");
//		    writer.println("	else if (bf.containsBean(\""+this.service.toLowerCase()+"\"))"); 
//		    writer.println("		"+this.service.toLowerCase()+"Service=("+this.service+"Service) bf.getBean(\""+this.service.toLowerCase()+"\");");
//		    writer.println("	else");
		    //START MODIFICHE PER IL RECUPERO DEL SERVIZIO UNICO (LOCAL-REMOTE-IMPL)
		    writer.println("	if (bf.containsBean(\""+this.service.toLowerCase()+"\"))"); 
		    writer.println("		"+this.service.toLowerCase()+"Service=("+this.service+"ServiceLocal) bf.getBean(\""+this.service.toLowerCase()+"\");");
		    //END MODIFICHE PER IL RECUPERO DEL SERVIZIO UNICO (LOCAL-REMOTE-IMPL)
		    
		    writer.println("	else throw new ServiceLocatorException(\"Nessun Servizio "+this.service+" Trovato nella configurazione\");");
		    writer.println("	} catch (BeansException e) {");
		    writer.println("		throw new ServiceLocatorException(\"Nessun Servizio "+this.service+" Configurato Correttamente\",e);");
		    writer.println("    }");
		    writer.subtractIndent();
		    writer.subtractIndent();
		    
		    writer.println("}");
		    writer.println("");
		    writer.println("");
		    //MODIFICA PER LA SINGOLA INTERFACCIA, TOLGO IL COSTRUTTORE SERVICELOCAL
//		    writer.println("// COSTRUCTOR");
//		    writer.println("");
//		    String[] args = new String[1];
//		    args[0]= service+"ServiceLocal obj";
//		    writer.println(Syntax.costractorDefine(service,"BusinessDelegate",args,null));
//		    writer.println("  Tracer.info(this.getClass().toString(),\"costruttore\",\"LOCAL\",null);");
//		    writer.println("  "+service.toLowerCase()+"ServiceLocal = obj;");
//		    writer.println("}");
//		    writer.println("");
		    
		    writer.println("// COSTRUCTOR");
		    writer.println("");
		    String[] args = new String[1];
		    args = new String[1];
		    args[0]= service+"ServiceLocal obj";
		    writer.println(Syntax.costractorDefine(service,"BusinessDelegate",args,null));
		    writer.println("  Tracer.info(this.getClass().toString(),\"costruttore\",\"INTERFACCIA SERVIZIO\",null);");
		    writer.println("  "+service.toLowerCase()+"Service = obj;");
		    writer.println("}");
		    writer.println("");		   
			  
		    
		    writer.println("");
		    writer.println("");
		    writer.println("");
		    
		    
		    for (int i=0;i<azioni.length;i++)
		    	this.addBDMethod(azioni[i]);
				
		    writer.println("");
		    
		    if (this.generateTestMethod){
		    	Azione test = new Azione();
		    	test.setInternalService(0);
		    	test.setNome("test");
		    	test.setParamType(1);
		    	test.setReturnType(1);
		    	
		    	this.addBDMethod(test);
		    }
				
		    
		    writer.subtractIndent();
		    writer.subtractIndent();
		    
		    writer.println("}");		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	  
	  
	
	
	private  void createConfig(){
				
		try {
			
			
			String pathConf = CreatorCostanst.FOLDER_CLIENT_CONFIGURATION;

		    File path = new File(pathConf);
		    path.mkdirs();
		    
		    System.out.println("");
		    System.out.println("Generare ClientConfiguration...");
		    System.out.println("Path Config Project=" + pathConf);		   			
		    
		    String _conf = pathConf + "/businessDelegate_" + service + ".xml";
							
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
			// if the root package is null it's supposed to be an internal service
			if(projectPackage==null)
				servImpl=CreatorCostanst.FWK_IS_PACKAGE+ CreatorCostanst.PACKAGE_BUSINESS_MODEL+ CreatorCostanst.getCategoryName(categoria);
			else
				servImpl=projectPackage + CreatorCostanst.PACKAGE_BUSINESS_MODEL+ CreatorCostanst.getCategoryName(categoria);
			
			
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">");
			writer.println("<!--");
			writer.println("   - Application context definition for \"Business Delegate\".");
			writer.println("-->");	
			writer.println("");
			
			
			
			writer.println("<beans>");  

			switch(typeRef){
			
				case 0:
					writer.println("");
					remoteLoggerRef();
					writer.println("");
					writer.println("   <!--  Chiamata EJB Locale");
					localLoggerRef();
					writer.println("   -->");
					writer.println("");
					writer.println("   <!--  Chiamata Locale senza EJB");
					localnoEjbRef();
					writer.println("   -->");
					writer.println("");
				break;
				case 1:
					writer.println("      <!-- Chiamata Ejb Remote ");
					remoteLoggerRef();
					writer.println("      -->");					
					localLoggerRef();					
					writer.println("");
					writer.println("   <!--  Chiamata Locale senza EJB");
					localnoEjbRef();
					writer.println("   -->");
					writer.println("");
				break;
				case 2:
					writer.println("");
					writer.println("   <!--  Chiamata EJB Remota");
					remoteLoggerRef();
					writer.println("   -->");
					writer.println("");
					writer.println("   <!--  Chiamata EJB Locale");
					localLoggerRef();
					writer.println("   -->");
					writer.println("");				
					localnoEjbRef();					
					writer.println("");
				break;
			}
			
			
			
			
			
			
			
			/*writer.println(" <bean id=\""+_class+"\"");
			writer.println("      class=\""+_class+"\">");
			writer.println("  	  <constructor-arg><ref bean=\""+this.service.toLowerCase()+"\"/></constructor-arg>");
			writer.println("  </bean>");*/

			
			writer.println("</beans>");	
			
			System.out.println("Finish Generare ClientConfiguration...");
			  
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	 private void remoteLoggerRef(){
		  writer.println("  <bean id=\""+this.service.toLowerCase()+"\"");
		  writer.println("      class=\"it.nch.is.fo.delegate.proxy.SimpleRemoteStatelessSessionLoggerProxyFactoryBean\">");
		  writer.println("      <property name=\"jndiTemplate\">");
		  writer.println("          <ref bean=\"jndiTemplate\"/>");
		  writer.println("      </property>");  
		  writer.println("    <property name=\"jndiName\" value=\""+JNDI+CreatorCostanst.getCategoryName(this.categoria).substring(1).toLowerCase()+"/"+this.service+"Home\"/>");
		  writer.println("    <property name=\"businessInterface\" value=\""+interf+"."+this.service+"ServiceLocal\"/>");
		  writer.println("    <property name=\"resourceRef\" value=\"false\"/>");
		  writer.println("  </bean>");
		 }
		 
		 //aggiunto per la generazione con utilizzo del proxy per la gestione del time log
		 private void localLoggerRef(){
		  writer.println("   <bean id=\""+this.service.toLowerCase()+"\"");
		  writer.println("      class=\"it.nch.is.fo.delegate.proxy.LocalStatelessSessionLoggerProxyFactoryBean\">");
		  writer.println("      <property name=\"jndiTemplate\">");
		  writer.println("          <ref bean=\"jndiTemplate\"/>");
		  writer.println("      </property>");  
		  writer.println("    <property name=\"jndiName\" value=\""+JNDI+CreatorCostanst.getCategoryName(this.categoria).substring(1).toLowerCase()+"/"+this.service+"LocalHome\"/>");
		  writer.println("    <property name=\"businessInterface\" value=\""+interf+"."+this.service+"ServiceLocal\"/>");
		  writer.println("    <property name=\"resourceRef\" value=\"true\"/>");
		  writer.println("   </bean>");
		 }	
	
	private void localnoEjbRef(){
		
		writer.println("   <bean id=\""+this.service.toLowerCase()+"\"");
		writer.println("      class=\""+servImpl+"."+this.service+"Impl\">");
		writer.println("      <constructor-arg><ref bean=\""+this.service.toLowerCase()+"DaoDB\" /></constructor-arg>	");
		
		//per ora li tolgo 
		for(int i=0;i<this.serviceCrossUsed.length;i++){						
			
			writer.println("      <constructor-arg><ref bean=\""+this.serviceCrossUsed[i].toLowerCase()+"Business\" /></constructor-arg>	");
			
		}		
		writer.println("   </bean>");		
	}
	
	private void addBDMethod(Azione a){
		
		if ((a.getInternalService()==0)||(a.getInternalService()==1)){
			
			
			writer.println("");		
			String param = CreatorCostanst.resolveDTOType(a.getParamType());
			String ret = CreatorCostanst.resolveDTOType(a.getReturnType());
			String retInstance = CreatorCostanst.resolveDTOTypeInstance(a.getReturnType());
			
			writer.println("public "+ret+" "+a.getNome()+"(FrontEndContext fec,"+ param+" "+param.toLowerCase()+") {");
			writer.println("");
			writer.println("       "+ ret + " " + retInstance + " = null;");
			writer.println("	    try {");
			writer.println("		   Tracer.info(this.getClass().toString(),\""+a.getNome()+"\",\"esecuzione "+this.service+"Service\",null);");
			writer.println("		   " + retInstance + " = "+this.service.toLowerCase()+"Service."+a.getNome()+"(fec, "+param.toLowerCase()+");");
			writer.println("	    } catch (Throwable re) {");
			if  (ret.equals(CreatorCostanst.DTO_C)){
				writer.println("		       Tracer.error(this.getClass().toString(),\""+a.getNome()+"\",\"errore durante l'esecuzione "+this.service+"Service\",re);");			
    	        writer.println("               " + retInstance + " =  ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);");		
		    }else if (ret.equals(CreatorCostanst.DTO)){
				writer.println("		       Tracer.error(this.getClass().toString(),\""+a.getNome()+"\",\"errore durante l'esecuzione "+this.service+"Service\",re);");			
	    	    writer.println("               " + retInstance + " =  ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);");		
		    }
			writer.println("	    }");
			if (CreatorCostanst.isDTOInfoList(a.getReturnType())){
				writer.println("      DTOInfoList infoList = " + retInstance + ".getInfoList();");
				writer.println("      if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){");      
				writer.println("		               Tracer.error(this.getClass().toString(),\""+a.getNome()+"\",\"errore durante l'esecuzione "+this.service+"Service\",null);");			
				writer.println("           throw new BusinessDelegateRuntimeException(infoList);");
				writer.println("      }");
			}
			writer.println("      return " + retInstance + ";");		
			writer.println("}");		
		}	
	}
	

	
}