/*
 * Creato il 6-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db.form;



import it.nch.ebweb.generate.core.MalformedAttribute;
import it.nch.ebweb.generate.core.MalformedConfigurationException;
import it.nch.ebweb.generate.core.Syntax;
import it.nch.ebweb.generate.core.Writer;
import it.nch.ebweb.generate.db.util.Attribute;
import it.nch.ebweb.generate.db.util.BusinessForm;
import it.nch.ebweb.generate.db.util.BusinessObject;

import java.util.Iterator;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class CreateForm {
	
	Writer writerI;
	Writer writerC;
	
	public CreateForm(Writer writerI,Writer writerC){
		this.writerI = writerI;
		this.writerC = writerC;
	}
	
		
	
	public void generaFormFile(BusinessObject bo,BusinessForm bof) throws MalformedConfigurationException{
		this.generaInterface(bo,bof);
		this.generaClass(bo,bof);
	}
	
	private void generaInterface(BusinessObject bo,BusinessForm bof){
		
		writerI.println("/**");
		writerI.println("*");
		writerI.println("* Interfaccia generata");
		writerI.println("*");
		writerI.println("*/");
		writerI.println("");
		writerI.println("package "+bo.get_packageCommon()+";");
		writerI.println("");
		
		if (bof.isCommonReference())
			writerI.println("import "+bo.get_packageCommon()+"."+bo.getNameService()+"Common;");
	
		if (bof.isCommonReference())
			writerI.println("import it.nch.fwk.fo.common.IBaseForm;");
				
		writerI.println("");	
		
		if (bof.isCommonReference())
			writerI.println("public interface "+bo.getNameService()+"Form extends "+bo.getNameService()+"Common, IBaseForm {");
		else
			writerI.println("public interface "+bo.getNameService()+"Form {");
		
		writerI.println("");		
		
		if(bof!=null){
			Iterator _it = bof.getAttributes().iterator();
			while(_it.hasNext()){
				
				Attribute a =(Attribute)_it.next();
				
				writerI.println("    "+a.getIGetterMethod("Form"));
				writerI.println("    "+a.getISetterMethod("Form"));
			}
		}

		writerI.println("");	
		
		writerI.println("	  public void reset();");
		writerI.println("");	
		writerI.println("}");
	}
	
	private void generaClass(BusinessObject bo,BusinessForm bof) throws MalformedConfigurationException{
		writerC.println("/**");
		writerC.println("*");
		writerC.println("* Classe generata");
		writerC.println("*");
		writerC.println("*/");
		writerC.println("");
		writerC.println("package "+bo.get_packageCommon()+";");
		writerC.println("");
		
		if (bof.isCommonReference())
			writerC.println("import "+bo.get_packageCommon()+"."+bo.getNameService()+"Common;");
		
		
		if (bof.isCommonReference())
			writerC.println("import it.nch.fwk.fo.base.BaseForm;");
		else
			writerC.println("import it.nch.fwk.fo.base.Form;");
		
		writerC.println("import it.nch.fwk.fo.common.CommonBusinessObject;");
		writerC.println("import it.nch.fwk.fo.dto.*;");
		
		
		Iterator it = bo.getCommonAttributes().iterator();
		
		if (bof.isCommonReference()){
			writerC.println("");
			writerC.println("import org.springframework.beans.BeansException;");
			writerC.println("import org.springframework.beans.factory.BeanFactory;");
			writerC.println("import org.springframework.beans.factory.access.BeanFactoryLocator;");
			writerC.println("import org.springframework.beans.factory.access.BeanFactoryReference;");
			writerC.println("import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;");
			writerC.println("");
		}
		
		//aggiunte per la gestione del metodo toString
		writerC.println("import it.nch.fwk.fo.util.Tracer;");
		writerC.println("import java.beans.BeanInfo;");
		writerC.println("import java.beans.Introspector;");
		writerC.println("import java.beans.PropertyDescriptor;");
		writerC.println("import java.lang.reflect.Method;");
		writerC.println("");
		
		
		while(it.hasNext()){
			Attribute a =(Attribute)it.next();
			try {
				if (a.getImportOfType(false)!=null){
					writerC.println(a.getImportOfType(false));
					
					if  (a.getType().equals("Collection")){
						writerC.println("import java.util.Iterator;");
						writerC.println("import java.util.*;");					
					}
				}
			} catch (MalformedAttribute e) {
				// TODO Blocco catch generato automaticamente
				throw new MalformedConfigurationException("[FOLDER FORM] Attenzione il file "+bo.getNameService()+".properties "+e.getMessage());
				
			}
		}
		writerC.println("");		
		if (bof.isCommonReference())
			writerC.println("public class "+bo.getNameService()+"FormImpl extends BaseForm implements "+bo.getNameService()+"Form {");
		else
			writerC.println("public class "+bo.getNameService()+"FormImpl extends Form {");
		writerC.println("");	
		
		
		Iterator _it_ = bo.getCommonAttributes().iterator();
		while(_it_.hasNext()){
			
			Attribute a =(Attribute)_it_.next();			
			//writerC.println(" private "+a.getType()+" "+a.getName()+";");
			if (a.getDefaulValue()!=null){
				writerC.println("    private " + a.getType() + " "+ a.getName() + "="+a.getDefaulValue()+";");			
			}else{			
				writerC.println("    private " + a.getType() + " "+ a.getName() + ";");			
			}
			
		}
		writerC.println("");
		writerC.println("");	
		writerC.println("// TYPE SOLO PER LA FORM");			
		Iterator _it_f = bof.getAttributes().iterator();
		while(_it_f.hasNext()){
			
			Attribute a =(Attribute)_it_f.next();			
			//writerC.println(" private "+a.getType()+" "+a.getName()+";");
			if (a.getDefaulValue()!=null){
				writerC.println("    private " + a.getType() + " "+ a.getName() + "="+a.getDefaulValue()+";");			
			}else{			
				writerC.println("    private " + a.getType() + " "+ a.getName() + ";");			
			}
			
		}
		writerC.println("");
		writerC.println("");	
		
		if (bof.isCommonReference()){
			
			writerC.println(" private transient BeanFactoryLocator bfl;");
			writerC.println(" private transient BeanFactoryReference bfr;");
			writerC.println(" private transient BeanFactory bf;");

		}
		writerC.println("");
		writerC.println("");	
		
		
		writerC.println("	public "+bo.getNameService()+"FormImpl(){");
		writerC.println("");
		
		if (bof.isCommonReference()){
			writerC.println("		bfl = SingletonBeanFactoryLocator.getInstance(\"client-beanRefFactory.xml\");");
			writerC.println("		bfr = bfl.useBeanFactory(\"it.nch.orm\");");
			writerC.println("		bf=bfr.getFactory();");

		}
		
		Iterator _it_ff = bo.getCommonAttributes().iterator();
		while(_it_ff.hasNext()){
			
			Attribute a =(Attribute)_it_ff.next();
			int index = a.getType().indexOf("Common");
			if (index>0){
				//writerC.println("SPRING!!!");
				writerC.println("		"+a.getName()+"=("+a.getType()+")bf.getBean(\""+Syntax.onlyFirstUpperCase(a.getType().substring(0,index))+"Form\");");
			}			
		}
		
		writerC.println("	}");
		
		writerC.println("");
		writerC.println("");
		writerC.println("");
		
		
		
		if (bof.isCommonReference()){
		
			writerC.println("	public void setNativePojo(Object nativePojo) {");
			
			Iterator _it_fff = bo.getCommonAttributes().iterator();
			while(_it_fff.hasNext()){
				
				Attribute a =(Attribute)_it_fff.next();
				int index = a.getType().indexOf("Common");
				if (index>0){				
					
					writerC.println("		((BaseForm)this."+a.getName()+").setNativePojo((("+bo.getNameService()+"Common)nativePojo).get"+Syntax.onlyFirstUpperCase(a.getName())+"());");
				}			
			}
			writerC.println("    	this.nativePojo = nativePojo;");
			writerC.println("	}");

		}	
		
		writerC.println("");
		writerC.println("");
		
		
		
		Iterator _it = bo.getCommonAttributes().iterator();
		
		while(_it.hasNext()){
			
			Attribute a =(Attribute)_it.next();
			if (a.isFormRef()){
				writerC.println("    "+a.getCGetterMethod("Common",false));
				writerC.println("    "+a.getCSetterMethod("Common",false));
			}else{
				writerC.println("    "+a.getCGetterMethod("Form",false));
				writerC.println("    "+a.getCSetterMethod("Form",false));
			}
			
		}
		writerC.println("/**");
		writerC.println(" *");
		writerC.println(" * METODI SOLO FORM");
		writerC.println(" *");
		writerC.println(" */");
		
		Iterator _it_FF = bof.getAttributes().iterator();
		while(_it_FF.hasNext()){
			
			Attribute a =(Attribute)_it_FF.next();
			
			writerC.println("    "+a.getCGetterMethod("Form",false));
			writerC.println("    "+a.getCSetterMethod("Form",false));
		}
		
		writerC.println("");
		writerC.println("");
		
		
		writerC.println("    public void show() {");
		writerC.println("       System.out.println(\"Class=\"+this.getClass());");		
		Iterator at = bo.getCommonAttributes().iterator();
		
		while(at.hasNext()){
			
			Attribute a =(Attribute)at.next();		
			writerC.println("       "+a.getPrintShow());
		}
		
		Iterator at_F = bof.getAttributes().iterator();
		
		while(at_F.hasNext()){
			
			Attribute a =(Attribute)at_F.next();		
			writerC.println("       "+a.getPrintShow());
		}
		
		writerC.println("    }");
		writerC.println("");
		writerC.println("");
		
		if (bof.isCommonReference()){
			
			writerC.println("    /**");
			writerC.println("     *");
			writerC.println("     *COPY/MERGE Method Form Vs Pojo");
			writerC.println("     *");
			writerC.println("     **/");
			writerC.println("");
			writerC.println("      public CommonBusinessObject copy(){");
			writerC.println("");
			writerC.println("         "+bo.getNameService()+"Form _form = this;");
			writerC.println("         "+bo.getNameService()+"Common _pojo=("+bo.getNameService()+"Common)this.nativePojo;");
			writerC.println("");
			
			
			//String className,String methodName,String msg,Object optionalObject
			writerC.println("         if (_pojo == null){ ");
			writerC.println("");
			writerC.println("         	if (Tracer.isDebugEnabled(this.getClass().getName())){ ");
			writerC.println("				Tracer.debug(this.getClass().getName(),\"\",\"\",null);");
			writerC.println("				Tracer.debug(this.getClass().getName(),\"copy()\",\"---------------------------------------------------------------------\",null);");
			writerC.println("				Tracer.debug(this.getClass().getName(),\"copy()\",\"Attenzione nativeObject dentro FORM vuoto provvedo ad istanziare POJO\",null);");
			writerC.println("				Tracer.debug(this.getClass().getName(),\"copy()\",\"---------------------------------------------------------------------\",null);");
			writerC.println("				Tracer.debug(this.getClass().getName(),\"\",\"\",null);");
			writerC.println("         	}");
			writerC.println("");
			writerC.println("			bfl = SingletonBeanFactoryLocator.getInstance(\"client-beanRefFactory.xml\");");
			writerC.println("			bfr = bfl.useBeanFactory(\"it.nch.orm\");");
			writerC.println("			bf=bfr.getFactory();");
			writerC.println("         	_pojo=("+bo.getNameService()+"Common) bf.getBean(\""+Syntax.onlyFirstUpperCase(bo.getNameService())+"\");");
			writerC.println("");
			writerC.println("         }");
			
			
			
			writerC.println("");
			writerC.println("");
			
			Iterator _at = bo.getCommonAttributes().iterator();
			
			while(_at.hasNext()){
				
				Attribute a =(Attribute)_at.next();		
				if (a.getType().equals("Collection")){
					writerC.println("");
					writerC.println(bo.getCopyFormMethod(a.getName()));
					writerC.println("");
				}else{
					
					if (a.isFormRef()){
						
						String fupper = Syntax.onlyFirstUpperCase(a.getName());
						writerC.println("         // Oggetto innestato copio in modo ricorso relativamente");
						writerC.println("         // alla definizione delle dipendenze del properties Common");
					
						writerC.println("  		  if (_form.get"+fupper+"()!=null)");					
						writerC.println("             _pojo.set"+fupper+"(("+a.getType()+")_form.get"+fupper+"().copy());");
						//writerC.println("  		  else System.err.println(\"ATTENZIONE nel pojo l'oggetto innestato è null non lo copio!!!\");");
				
						//writerC.println("             _pojo.set"+fupper+"(("+a.getType()+")_form.get"+fupper+"().copy());");
						//writerC.println("  		  else System.err.println(\"ATTENZIONE nel pojo l'oggetto innestato è null non lo copio!!!\");");
				
						
					}else{
						String fupper = Syntax.onlyFirstUpperCase(a.getName());
						
						if (!fupper.equals("IdIForm"))
						    writerC.println("         _pojo.set"+fupper+"(_form.get"+fupper+"());");
					}
				
				}
				
				
			}
			writerC.println("");	
			writerC.println("         return _pojo;");	
			writerC.println("	  }");
			
		}
		
		
		if (bof.isCommonReference()){
				writerC.println("");
				writerC.println("	  public DTO incapsulateBO() {");
				// TODO Stub di metodo generato automaticamente
				writerC.println("	  	return new DTOImpl(this);");
				writerC.println("	  }");
				writerC.println("");
		}
		
		
		
		
		writerC.println("");		
		writerC.println("// Metodo di RESET");	
		writerC.println("");
		writerC.println("	  public void reset() {");
		Iterator _it_reset  = bof.getAttributes().iterator();
		Iterator _it_reset2 = bo.getCommonAttributes().iterator();
		
		
		while(_it_reset2.hasNext()){
			
			Attribute a =(Attribute)_it_reset2.next();	
			
			if (a.getDefaulValue()!=null){
				writerC.println("	   		//Reset Con default Value");
				writerC.println("           this."+ a.getName() + "="+a.getDefaulValue()+";");				
			}else{				
				if (a.getType().equalsIgnoreCase("String"))
					writerC.println("	   		this."+a.getName()+"=\"\";");
				else if (a.getType().equalsIgnoreCase("int"))
					writerC.println("	   		this."+a.getName()+"=0;");				
			}
			
		}	
		

		writerC.println("");		
		writerC.println("	   		// DATI FORM NON COMMON");	
		writerC.println("");
		
		while(_it_reset.hasNext()){
			
			Attribute a =(Attribute)_it_reset.next();			
					
			if (a.getDefaulValue()!=null){
				writerC.println("	   		//Reset Con default Value");	
				writerC.println("            this."+ a.getName() + "="+a.getDefaulValue()+";");					
			}else{				
				if (a.getType().equalsIgnoreCase("String"))
					writerC.println("	   		this."+a.getName()+"=\"\";");
				else if (a.getType().equalsIgnoreCase("int"))
					writerC.println("	   		this."+a.getName()+"=0;");				
			}
			
			
			
		}			
		
		writerC.println("	  }");
		writerC.println("");
				
		//************************** INIZIO METODO CHE RITORNA LA RAPPRESENTAZIONE IN STRINGA DELL'OGGETTO *****************************
		
		//writerC.println("");
		//writerC.println("    /**");
		//writerC.println("     * ridefinizione del metodo toString");
		//writerC.println("     **/");
		//writerC.println("    public String toString() { ");
		//writerC.println("      StringBuffer buf = new StringBuffer(); ");
		//writerC.println("        try { ");
		//writerC.println("          BeanInfo binfo = Introspector.getBeanInfo(this.getClass()); ");
		//writerC.println("          PropertyDescriptor[] properties = binfo.getPropertyDescriptors(); ");
		//writerC.println("          if (properties != null) {");
		//writerC.println("             for (int i = 0; i < properties.length; i++) { ");
		//writerC.println("               Method readMethod = properties[i].getReadMethod(); ");
		//writerC.println("               if (readMethod != null) {");
		//writerC.println("                  buf.append(properties[i].getName()); ");
		//writerC.println("                  buf.append(\" = \"); ");
		//writerC.println("                  Object obj = readMethod.invoke(this, null); ");
		//writerC.println("                  if (obj != null) { ");
		//writerC.println("                     buf.append(obj.toString()); ");
		//writerC.println("                  } else { ");
		//writerC.println("                     buf.append(\"<empty>\"); ");
		//writerC.println("                  } ");
		//writerC.println("                  buf.append(\"\\n\"); ");
		//writerC.println("               } ");
		//writerC.println("             } ");
		//writerC.println("          } ");
		//writerC.println("        } catch (Exception e) { ");
		//writerC.println("        } ");
		//writerC.println("      return buf.toString(); ");
		//writerC.println("    }");
		writerC.println("");
		writerC.println("");
		//************************** FINE METODO CHE RITORNA LA RAPPRESENTAZIONE IN STRINGA DELL'OGGETTO *****************************
		
		writerC.println("}");
	}
	
	/*
	_pojo.setAbi(_form.getAbi());
	_pojo.setCab(_form.getCab());
	_pojo.setImporto(_form.getImporto());
	_pojo.setIntestatario(_form.getIntestatario());
	
	
	
	Collection collection2 = new Vector();
	Iterator collection1 = _form.getCausali().iterator();
	
	while (collection1.hasNext()){
		CommonBusinessObject pojo =  (CommonBusinessObject)collection1.next();
		collection2.add(pojo.copy());
	}		
	
	_pojo.setCausali(collection2);
	
	return _pojo;
	*/
	

}
