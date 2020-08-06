/*
 * Creato il 6-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db.util;

import it.nch.ebweb.generate.core.Syntax;

import java.util.Collection;
import java.util.Vector;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class BusinessObject {

	private String _packageCommon;
	private String nameService;
	private Collection commonAttributes = (Collection)new Vector();
	private Collection formAttributes = (Collection)new Vector();
	
	/**
	 * @return Restituisce il valore attributes.
	 */
	public Collection getCommonAttributes() {
		return commonAttributes;
	}
	/**
	 * @param attributes Il valore attributes da impostare.
	 */
	public void setCommonAttributes(Collection commonAttributes) {
		this.commonAttributes = commonAttributes;
	}

	/**
	 * @return Restituisce il valore nameService.
	 */
	public String getNameService() {
		return nameService;
	}
	/**
	 * @param nameService Il valore nameService da impostare.
	 */
	public void setNameService(String nameService) {
		this.nameService = nameService;
	}
	

	
	/**
	 * @return Restituisce il valore _packageCommon.
	 */
	public String get_packageCommon() {
		return _packageCommon;
	}
	/**
	 * @param common Il valore _packageCommon da impostare.
	 */
	public void set_packageCommon(String common) {
		_packageCommon = common;
	}
	
	
	
	public String getCopyFormMethod(String nameAttributeCollection){
		
		String code="";
		String var = Syntax.onlyFirstUpperCase(nameAttributeCollection);
		
		code+="\n         /*";
		code+="\n          *";
		code+="\n          *  Code copy "+var+" attribute";
		code+="\n          *";
		code+="\n          */ \n";
		code+=
		code+="\n         if (_form.get"+var+"()!=null){";
		code+="\n 		  		Collection dest"+var+" = new HashSet();";	
		code+="\n 		  		Iterator src"+var+" = _form.get"+var+"().iterator();";
		
		code+="\n 		  		while (src"+var+".hasNext()){";
		code+="\n	 				CommonBusinessObject _innerForm =  (CommonBusinessObject)src"+var+".next();";
		code+="\n	 				dest"+var+".add(_innerForm.copy());";
		code+="\n		  		}";		
		code+="\n		      	_pojo.set"+var+"(dest"+var+");";
		code+="\n		  }";
		//code+="\n		  }else System.err.println(\"[\"+this.getClass().getName()+\"]Attenzione one-to-many vuoto oggetto null!!!\");";
		
		return code;
	}
	
	/**
	 * @return Restituisce il valore formAttributes.
	 */
	public Collection getFormAttributes() {
		return formAttributes;
	}
	/**
	 * @param formAttributes Il valore formAttributes da impostare.
	 */
	public void setFormAttributes(Collection formAttributes) {
		this.formAttributes = formAttributes;
	}
	
}
