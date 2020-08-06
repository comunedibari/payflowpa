/*
 * Created on 22-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.ebweb.generate.backend;

import it.nch.ebweb.generate.core.CreatorCostanst;
import it.nch.ebweb.generate.core.Writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author FelicettiA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpringConfigurator {
	
	PrintStream outputStreamShell;
	PrintStream outputStream;
	Writer writer;
	
	Vector fileClientService;
	Vector fileService;
	Vector fileCrossService;

	public void rigenerateConfig() {

		clientConfiguration();
		configuration();
		System.out.println("");		
	}
	
	private void clientConfiguration(){
		
		System.out.println("");		
		System.out.println("RiGenerate Client BeanRefFactory Spring configuration per Front-End....");
		
		String pathClientConf = CreatorCostanst.FOLDER_CLIENT_CONFIGURATION;
		
		File path = new File(pathClientConf);	
		String files[] = path.list();		
		
		fileClientService = new Vector();
						
		for(int i=0; i<files.length;i++){
			
			String fn = files[i];
			
			if (fn.length()>16){		
				
				String t = fn.substring(0,16);
				if (t.equals("businessDelegate")){	
					
					StringTokenizer st = new StringTokenizer(fn,".");
					String lastToken="";
					
					while(st.hasMoreTokens()){
						lastToken=st.nextToken();
					}				
					if (lastToken.equalsIgnoreCase("xml"))
						fileClientService.add(fn);		
				}
			}
		}
		
		File file = new File(pathClientConf+"/client-beanRefFactory.xml");
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
		writer.println("   - Application context definition for \"Business Delegate\".");
		writer.println("-->");	
		writer.println("");	
		writer.println("<beans>");	
		writer.println("");	
		//if
		writer.println("  <bean id=\"businessDelegate\" class=\"org.springframework.context.support.ClassPathXmlApplicationContext\">");
		writer.println("		<constructor-arg>");
		writer.println("    	       <list>");
		
		for(int i=0;i<fileClientService.size();i++){
			
			if   (((((String)fileClientService.get(i)).lastIndexOf("Pojos")>0)||(((String)fileClientService.get(i)).lastIndexOf("Forms")>0))){
				
			}else{
				writer.println("                   <value>"+fileClientService.get(i)+"</value>");
			}
			
		}	
	    
		writer.println("  		       </list>");
	    writer.println(" 		</constructor-arg>");
	    writer.println("  </bean>");
	    
	    
	    
	    writer.println("  <bean id=\"it.nch.orm\" class=\"org.springframework.context.support.ClassPathXmlApplicationContext\">");
	    writer.println(" 		<constructor-arg>");
	    writer.println("       			<list> ");                 
	    writer.println("          			<value>businessDelegate_Forms.xml</value>  ");                 
	    writer.println("          			<value>businessDelegate_Pojos.xml</value> ");                  
	    writer.println("      			</list>"); 
	    writer.println(" 		</constructor-arg>"); 
	    writer.println(" </bean>"); 
	    
	    
	    writer.println("");	
	    writer.println("</beans>");	
	    
	    try {
			fw.flush();
		    fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void configuration(){
		
		
		System.out.println("RiGenerate BeanRefFactory Spring configuration Back-End....");
		
		String pathClientConf = CreatorCostanst.FOLDER_CONFIGURATION;
		
		File path = new File(pathClientConf);	
		String files[] = path.list();		
		
		fileService = new Vector();
		fileCrossService = new Vector();
						
		for(int i=0; i<files.length;i++){
			
			String fn = files[i];
			
			if (fn.length()>18){	
				
				String t = fn.substring(0,18);
				if (t.equals("applicationContext")){
					StringTokenizer st = new StringTokenizer(fn,".");
					String lastToken="";
				
					while(st.hasMoreTokens()){
						lastToken=st.nextToken();
					}
					
					if (lastToken.equalsIgnoreCase("xml"))
						fileService.add(fn);					
				}
				if (t.equals("applicationCrossCo")){
					StringTokenizer st = new StringTokenizer(fn,".");
					String lastToken="";
				
					while(st.hasMoreTokens()){
						lastToken=st.nextToken();
					}
					
					if (lastToken.equalsIgnoreCase("xml"))
						fileCrossService.add(fn);					
				}
			}
		}
		
		File file = new File(pathClientConf+"/server-beanRefFactory.xml");
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
		writer.println("   - Application context definition for \"Business Delegate\".");
		writer.println("-->");	
		writer.println("");
		writer.println("<beans>");	
		writer.println("");
		
		writer.println("  <bean id=\"it.nch.fwk.fo\"");
		writer.println("     class=\"org.springframework.context.support.ClassPathXmlApplicationContext\" lazy-init=\"false\">");
		writer.println("     <constructor-arg>");
		writer.println("       <list>");
		writer.println("          <value>applicationCoreContext.xml</value>");
		writer.println("       </list>");
		writer.println("    </constructor-arg>");
		writer.println("  </bean>");	
		writer.println("");		
		 
		  writer.println("   <bean id=\"it.nch.business.cross\"");
		  writer.println("     class=\"org.springframework.context.support.ClassPathXmlApplicationContext\" lazy-init=\"false\">");
		  writer.println("     <constructor-arg>");
		  writer.println("       <list>");
		  
		  
		  
			for(int j=0;j<fileCrossService.size();j++)
				writer.println("                   <value>"+fileCrossService.get(j)+"</value>");
		  
		  
		  writer.println("       </list>");	
		  writer.println("    </constructor-arg>");
		  writer.println("     <constructor-arg>");
		  writer.println("          <ref bean=\"it.nch.fwk.fo\"/>");
		  writer.println("    </constructor-arg>");
		  
		  writer.println("  </bean>");	
		  
		  
		writer.println("");			
		writer.println("");	
		writer.println("  <bean id=\"it.nch.business\" class=\"org.springframework.context.support.ClassPathXmlApplicationContext\">");
		writer.println("		<constructor-arg>");
		writer.println("    	       <list>");
		
		/*	
		 for(int j=0;j<fileClientService.size();j++){
			
			if   (!((((String)fileClientService.get(j)).lastIndexOf("Pojos")>0)&&(((String)fileClientService.get(j)).lastIndexOf("Forms")>0)))
				writer.println("                   <value>"+fileClientService.get(j)+"</value>");
		}		
		*/
		for(int j=0;j<fileService.size();j++)
			writer.println("                   <value>"+fileService.get(j)+"</value>");
	    
		writer.println("  		       </list>");
	    writer.println(" 		</constructor-arg>");
	    writer.println("     <constructor-arg>");
		writer.println("          <ref bean=\"it.nch.business.cross\"/>");
		writer.println("    </constructor-arg>");
		  
	    writer.println("  </bean>");
	    writer.println("");	
	    
	    	    
	    
	    
	    writer.println("  <bean id=\"it.nch.orm\" class=\"org.springframework.context.support.ClassPathXmlApplicationContext\">");
	    writer.println(" 		<constructor-arg>");
	    writer.println("       			<list> ");                 
	    writer.println("          			<value>businessDelegate_Forms.xml</value>  ");                 
	    writer.println("          			<value>businessDelegate_Pojos.xml</value> ");                  
	    writer.println("      			</list>"); 
	    writer.println(" 		</constructor-arg>"); 
	    writer.println(" </bean>"); 
	    writer.println("");	

	    writer.println("  <bean id=\"it.nch.custom.server\" class=\"org.springframework.context.support.ClassPathXmlApplicationContext\">");
	    writer.println(" 		<constructor-arg>");
	    writer.println("       			<list> ");                 
	    writer.println("          			<value>applicationContext_Server.xml</value> ");                  
	    writer.println("      			</list>"); 
	    writer.println(" 		</constructor-arg>"); 
	    writer.println(" </bean>"); 
	    
	    writer.println("</beans>");	
	    
	    try {
			fw.flush();
		    fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		
	}


}
