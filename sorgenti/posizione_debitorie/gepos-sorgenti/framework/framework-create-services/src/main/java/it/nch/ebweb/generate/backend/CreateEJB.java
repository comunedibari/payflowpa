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
public class CreateEJB extends Creator{
	
	
	PrintStream outputStreamShell;
	PrintStream outputStream;

	Writer writer;

	String _package = null;
	String pathClasse = null;
	String pathIf = null;
	String service = null;
	Azione[] azioni = null;
	boolean generateTestMethod = false;
	int daoType;
	int categoria;
	String projectPackage="it.nch";
	String _packInterface;
	boolean frwInternal=false;
	

	public void create(Service service) {

		System.out.println("");
		System.out.println("Generate EJB....");
		
		this.service= Syntax.firstUpperCase( service.getName() );
		this.azioni = service.getAzioni();
		this.daoType = service.getDaoType();
		this.generateTestMethod = service.isGenerateTestMethod();
		this.categoria = service.getCategoria();
		this.projectPackage=service.getRootPackage();
		
		//_package = CreatorCostanst.PACKAGE_SERVICE_MODEL + CreatorCostanst.getCategoryName(categoria);
		
		
		
		if (service.isFrameworkInternalService()){
			
			
			this._packInterface = CreatorCostanst.FWK_IS_PACKAGE + CreatorCostanst.getCategoryName(this.categoria) + ".interfaces";		
			
			//_package = CreatorCostanst.FWK_PACKAGE + CreatorCostanst.getCategoryName(categoria)+".ejbs";
			_package = CreatorCostanst.FWK_IS_PACKAGE + ".ejbs"+ CreatorCostanst.getCategoryName(categoria);
			
			//System.out.println("^^^^^^^^^^^^^^^="+_package);
			
			pathClasse = CreatorCostanst.FOLDER_FRW_INTERNAL_EJB + CreatorCostanst.getPathSrc(_package);	
			
			//System.out.println("^^^^^^^^^^^^^^^="+pathClasse);
			
			pathIf = CreatorCostanst.FOLDER_FRW_INTERNAL_EJB_INTERFACE + CreatorCostanst.getPathSrc(_package);
			frwInternal=true;
			
			
			
		}else{
	
			
			this._packInterface = projectPackage + CreatorCostanst.getCategoryName(this.categoria) + ".interfaces";		
			
			_package = projectPackage + CreatorCostanst.PACKAGE_SERVICE_MODEL + CreatorCostanst.getCategoryName(categoria);
			pathClasse = CreatorCostanst.FOLDER_SERVICE_MODEL + CreatorCostanst.getPathSrc(_package);
			pathIf = CreatorCostanst.FOLDER_SERVICE_MODEL_INTERFACE + CreatorCostanst.getPathSrc(_package);
			
		}
		
		//System.out.println("^^^^^^^^^^^^^^^final ="+pathClasse);
		
		
		File path = new File(pathClasse);
		path.mkdirs();
		path = new File(pathIf);
		path.mkdirs();

		System.out.println("Path Project=" + pathClasse);
		System.out.println("Package=" + _package);		
		
		//Remote
		createEJBs(0, pathIf);
		//Home
		createEJBs(1, pathIf);
		//Local
		createEJBs(2, pathIf);
		//LocalHome
		createEJBs(3, pathIf);
		//Bean
		createEJBs(4, pathClasse);
		

		
		/**
		 * 
		 * 
		 * Bisogna creare file di configurazione
		 * 
		 */
		
		System.out.println("Finish Generate EJB....");
		System.out.println("");
	}
	
	
	
	private void createEJBs(int type, String path){
		
		try {
			
			String interf=null;

			switch(type){
			case 0:
				interf = path + "/" + this.service + ".java";
				break;
			case 1:
				interf = path + "/" + this.service + "Home.java";
				break;
			case 2:
				interf = path + "/" + this.service + "Local.java";
				break;
			case 3:
				interf = path + "/" + this.service + "LocalHome.java";
				break;
			case 4:
				interf = path + "/" + this.service + "Bean.java";
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
			writer.println("");
			
			/*
			 * 
			 * import
			 */
					
			String exts = "";
						 
			switch(type){
			case 0:
				writer.println("import "+_packInterface+"."+this.service+"Service;");
				writer.println("");
				exts =this.service+"Service, javax.ejb.EJBObject";				
				writer.println(Syntax.interfaceDefine(this.service,null,exts));
				writer.println("}");
				break;
				
			case 1:
				exts =" javax.ejb.EJBHome";	
				writer.println(Syntax.interfaceDefine(this.service,"Home",exts));								
				writer.println("");
				writer.println("  public "+_package+"."+this.service+" create()throws javax.ejb.CreateException,	java.rmi.RemoteException;");				
				writer.println("}");
				break;
				
			case 2:				
				writer.println("import "+_packInterface+"."+this.service+"ServiceLocal;");
				writer.println("");
				exts =this.service+"ServiceLocal, javax.ejb.EJBLocalObject";	
				writer.println(Syntax.interfaceDefine(this.service,"Local",exts));								
				writer.println("}");
				break;
				
			case 3:
				exts =" javax.ejb.EJBLocalHome";	
				writer.println(Syntax.interfaceDefine(this.service,"LocalHome",exts));
				writer.println("");
				writer.println("  public "+_package+"."+this.service+"Local create()throws javax.ejb.CreateException;");				
				writer.println("}");
				break;
			case 4:
				createBean();
				break;
				
			}	
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
    private void createBean(){
    
       	
       	writer.println("import org.springframework.beans.factory.BeanFactory;");
       	writer.println("import org.springframework.beans.factory.access.BeanFactoryLocator;");
       	writer.println("import org.springframework.beans.factory.access.BeanFactoryReference;");
       	writer.println("import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;");
       	writer.println("");

		writer.println("import it.nch.fwk.fo.interfaces.FrontEndContext;");		
       	writer.println("import it.nch.fwk.fo.cross.AbstractEjb;");
		writer.println("import it.nch.fwk.fo.common.constants.FrameworkMessage;");			
       	
       	writer.println("import "+_packInterface+"."+this.service+"ServiceLocal;");
      
       	writer.println("import it.nch.fwk.fo.util.Tracer;");
       	writer.println("import it.nch.fwk.fo.cross.exception.DAORuntimeException;");
       	writer.println("import it.nch.fwk.fo.cross.exception.BusinessImplRuntimeException;");
		writer.println("import it.nch.fwk.fo.core.exception.ManageBackEndException;");

		if (searchDTO(azioni))
				//writer.println("import "+CreatorCostanst.ROOT_PACKAGE+".dto.DTO;");
			writer.println("import it.nch.fwk.fo.dto.DTO;");
		if (searchDTOCollection(azioni))
				//writer.println("import "+CreatorCostanst.ROOT_PACKAGE+".dto.DTOCollection;");
			writer.println("import it.nch.fwk.fo.dto.DTOCollection;");
	      	
       	
		writer.println("import it.nch.fwk.fo.dto.DTOInfoList;");
		writer.println("import it.nch.fwk.fo.dto.DTOInfoInterface;");
       	writer.println("");
    	writer.println("");
    	writer.println("public class "+this.service+"Bean extends AbstractEjb implements javax.ejb.SessionBean {");
    	writer.println("");
    	writer.println("");
    	
    	writer.println("   private BeanFactoryReference bfr;");
    	writer.println("   private BeanFactoryLocator bfl;");
    	writer.println("   private BeanFactory bf;"); 
    	
    	writer.println("   private "+this.service+"ServiceLocal "+this.service.toLowerCase()+"ServiceLocal;");
    	writer.println("   private javax.ejb.SessionContext mySessionCtx;");
    	
    	writer.println("");
    	writer.println("");
    	
    	writer.println("   public javax.ejb.SessionContext getSessionContext() { ");
    	writer.println("       return mySessionCtx;");
    	writer.println("   }");
    	
    	
    	writer.println("   public void setSessionContext(javax.ejb.SessionContext ctx) { ");
    	writer.println("       	mySessionCtx = ctx;");
    	writer.println("   }");
    	
    	writer.addIndent();
    	writer.addIndent();
    	writer.addIndent();
    	
    	writer.println("");
    	
    	addActiveCycleBody(true);
    	addPassiveCycleBody(true);
		addActiveCycleBody(false);
    	addPassiveCycleBody(false);
    	
    	for(int i=0;i<azioni.length;i++)
    		addBusinessMethod(azioni[i]);
    	writer.println("");
    	
    	if (this.generateTestMethod)
    		this.testMethod();
    	
    	writer.subtractIndent();
    	writer.subtractIndent();
    	writer.subtractIndent();   	
    	
    	writer.println("}");
    
    
    	
    }
	
	
	private void addBusinessMethod(Azione a){
	
		if ((a.getInternalService()==0)||(a.getInternalService()==1)){
			
			writer.println("");		
			String param = CreatorCostanst.resolveDTOType(a.getParamType());
			String ret = CreatorCostanst.resolveDTOType(a.getReturnType());
		
			writer.println("public "+ret+" "+a.getNome()+"(FrontEndContext fec,"+ param+" "+param.toLowerCase()+") {");
			writer.println("");
			writer.println("      "+ret + " ret = null;");
			if (a.getRetryNum()>0) {
				writer.println("      int N="+ a.getRetryNum()+ ";");
				writer.println("      int i=0;");
				writer.println("  while (i<N) {");
			     
			}
			writer.println("  try{");
			writer.println("      if (Tracer.isDebugEnabled(\" "+this.service+"Bean\"))");
			writer.println("         Tracer.debug(\" "+this.service+"Bean\",\""+a.getNome()+"\",\"Guscio EJB\", null);");
			writer.println("      ret = "+this.service.toLowerCase()+"ServiceLocal."+a.getNome()+"(fec, this.copyBusinessObject("+param.toLowerCase()+"));");
			
			/**
			 * 
			 * METODO STANDART CON DTO!!!!!!!!!!!
			 * 
			 */
			
			writer.println("	  return this.copyBusinessObject(ret);");
				
			if (CreatorCostanst.isDTOInfoList(a.getReturnType())){
				writer.println("  }catch(DAORuntimeException drte){");
				writer.println("      Tracer.error(\" "+this.service+"Bean\",\""+a.getNome()+"\",\"\", drte);");
				writer.println("      mySessionCtx.setRollbackOnly();");
				writer.println("      ret = drte.get" + ret + "();");
				writer.println("      clearBeanData(ret);");
				writer.println("      return ret;");
				writer.println("  }catch(BusinessImplRuntimeException birte){");
				writer.println("      Tracer.error(\" "+this.service+"Bean\",\""+a.getNome()+"\",\"\", birte);");
				writer.println("      mySessionCtx.setRollbackOnly();");
				writer.println("      ret = birte.get" + ret + "();");
				writer.println("      clearBeanData(ret);");
				writer.println("      return ret;");
			}	
			writer.println("  }catch(RuntimeException rte){");
			if (a.getRetryNum()==0)
				writer.println("      Tracer.error(\" "+this.service+"Bean\",\""+a.getNome()+"\",\"\", rte);");
			if (a.getRetryNum()>0) {
				writer.println("      if (i==N) {");
				writer.println("      Tracer.error(\" "+this.service+"Bean\",\""+a.getNome()+"\",\"\", rte);");				
			}
			writer.println("      mySessionCtx.setRollbackOnly();");
			 
			if (CreatorCostanst.isDTOInfoList(a.getReturnType())){
				writer.println("      return new ManageBackEndException().getBusiness" + ret + "ByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);");
			}
			else
				addProcessException(writer,a,param ,ret );	
			if (a.getRetryNum()>0) {
				writer.println("      }");
			}
			writer.println("  }");	
			if (a.getRetryNum()>0) {
				writer.println("  i++;");
				writer.println("   }");
        		writer.println("return null;");
			}
			writer.println("}");	
		}	
	}

	private void addProcessException(Writer writer,Azione a,String param ,String ret ){
			if  ((ret.equals(CreatorCostanst.DTO_C))&&((param.equals(CreatorCostanst.DTO))) ){
				writer.println("      return this.dto2Collection(this.processException("+param.toLowerCase()+", rte));");				
			}else if ((ret.equals(CreatorCostanst.DTO))&&((param.equals(CreatorCostanst.DTO_C))) ){
				System.err.println("ATTENZIONE!!!!");
				System.err.println("trovato metodo che ha param DTOCollection e deve ritornare DTO impossibile risolvere");
			}else{
				writer.println("      return this.processException("+param.toLowerCase()+", rte);");		
			}	
	}
	
	
	private void addActiveCycleBody(boolean create){
	
		if (create)
			writer.println("public void ejbCreate() throws javax.ejb.CreateException {");
		else
			writer.println("public void ejbActivate() {");
		
		writer.println("");
		writer.println("  bfl = SingletonBeanFactoryLocator.getInstance(\"server-beanRefFactory.xml\");");
		writer.println("  bfr = bfl.useBeanFactory(\"it.nch.business\");");
		
		if (create)
			writer.println("  Tracer.info(\" "+this.service+"Bean \", \"ejbCreate\",\"BeanFactory per oggetti business.........\", null);");
		else
			writer.println("  Tracer.info(\" "+this.service+"Bean \", \"ejbActivate\",\"BeanFactory per oggetti business.........\", null);");
		
		writer.println("  bf = bfr.getFactory();");		
		writer.println("  "+service.toLowerCase()+"ServiceLocal = ("+this.service+"ServiceLocal) bf.getBean(\""+service.toLowerCase()+"Business\");");
		
		if (create)			
			writer.println("  Tracer.info(\" "+this.service+"Bean \", \"ejbCreate\",\"Instanziato OK!\", null);");
		else
			writer.println("  Tracer.info(\" "+this.service+"Bean \", \"ejbActivate\",\"Instanziato OK!\", null);");
		
		writer.println("");
		writer.println("}");
		writer.println("");
		
	}
	
	private void addPassiveCycleBody(boolean remove){
		
		if (remove)
			writer.println("public void ejbRemove() {");
		else
			writer.println("public void ejbPassivate() {");
			
		writer.println("");		
		writer.println("  bfr = null;");
		writer.println("  bf = null;");
		writer.println("  "+service.toLowerCase()+"ServiceLocal = null;");
		
		if (remove)
			writer.println("  Tracer.info(\" "+this.service+"Bean\", \"ejbRemove\",\"Rimozione dal ciclo di vita del riferimento al Bean\", null);");
		else
			writer.println("  Tracer.info(\" "+this.service+"Bean\", \"ejbPassivate\",\"Rimozione dal ciclo di vita del riferimento al Bean\", null);");
		
		writer.println("");
		writer.println("}");
		writer.println("");		
	}
		
	private void testMethod(){
		
		writer.println("");
		writer.println("public DTO test(FrontEndContext fec,DTO dto) {");
		writer.println("   try{");
		writer.println("      Tracer.debug(\""+this.service+"Bean\",\"test\",\"Guscio EJB\", null);");
		writer.println("      return "+this.service.toLowerCase()+"ServiceLocal.test(fec,dto);");		
		writer.println("   }catch(RuntimeException rte){");
		writer.println("      return this.processException(dto, rte);");
		writer.println("   }");
		writer.println("}");
	
	}

}
