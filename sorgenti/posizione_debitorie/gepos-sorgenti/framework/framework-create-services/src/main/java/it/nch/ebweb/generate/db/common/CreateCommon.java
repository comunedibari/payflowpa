/*
 * Creato il 6-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db.common;

import it.nch.ebweb.generate.core.MalformedAttribute;
import it.nch.ebweb.generate.core.MalformedConfigurationException;
import it.nch.ebweb.generate.core.Writer;
import it.nch.ebweb.generate.db.util.Attribute;
import it.nch.ebweb.generate.db.util.BusinessObject;

import java.util.Iterator;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class CreateCommon {
	
	Writer writerI;
	
	
	public CreateCommon(Writer writerI){
		this.writerI = writerI;
		
	}
	
	public void generaCommonFile(BusinessObject bo) throws MalformedConfigurationException{
		
		writerI.println("/**");
		writerI.println("*");
		writerI.println("* Interfaccia generata");
		writerI.println("*");
		writerI.println("*/");
		writerI.println("");
		writerI.println("package "+bo.get_packageCommon()+";");
		writerI.println("");
		writerI.println("import it.nch.fwk.fo.common.CommonBusinessObject;");
			
		Iterator it = bo.getCommonAttributes().iterator();
		
		while(it.hasNext()){
			Attribute a =(Attribute)it.next();
			try {
				if (a.getImportOfType(true)!=null)
					writerI.println(a.getImportOfType(true));
			} catch (MalformedAttribute e) {
				// TODO Blocco catch generato automaticamente
				throw new MalformedConfigurationException("[FOLDER COMMON] Attenzione il file "+bo.getNameService()+".properties "+e.getMessage());
				
			}
		}
		
		writerI.println("");
		
		writerI.println("public interface "+bo.getNameService()+"Common extends CommonBusinessObject {");
		writerI.println("");
		
		Iterator _it = bo.getCommonAttributes().iterator();
		while(_it.hasNext()){
			
			Attribute a =(Attribute)_it.next();
			
			
			//writerI.println("    "+a.getIGetterMethod("Common"));
			//writerI.println("    "+a.getISetterMethod("Common"));
			
			
			if (a.isFormRef()){
				
				writerI.println("    "+a.getIGetterMethod("Common"));
				writerI.println("    "+a.getISetterMethod("Common"));
			}else{
				writerI.println("    "+a.getIGetterMethod("Common"));
				writerI.println("    "+a.getISetterMethod("Common"));
			}
			//*/
			
		}
		writerI.println("");
		writerI.println("}");
	}

}
