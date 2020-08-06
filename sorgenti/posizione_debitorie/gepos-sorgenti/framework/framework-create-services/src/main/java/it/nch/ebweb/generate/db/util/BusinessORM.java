/*
 * Creato il 6-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db.util;

import it.nch.ebweb.generate.core.Syntax;

import java.util.Collection;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class BusinessORM {

	private String _packageCommon;
	private String ormClient;	
	private String orm;	
	public boolean commonReference=false;
	public boolean clonable = false;
	private Collection attributes;
	private Collection commonAttributes;
	private OrmEreditato extendOrm=null;
	private Collection compositeId=null;
	//private String nameAttributeCompositeID ="";
	private boolean isVista=false;
	private boolean isVistaWithCostrEmpty=false;
	private String otherCostructor=null;
	private boolean fakedObj=false;
	private String refFakeName=null;
	
		
	public String getCopyPojoMethod(String nameAttributeCollection){
		
		String var = Syntax.onlyFirstUpperCase(nameAttributeCollection);
		String code="";
		
		code+="\n         /*";
		code+="\n          *";
		code+="\n          *  Code copy "+var+" attribute";
		code+="\n          *";
		code+="\n          */ \n";
		code+="\n 		  Collection dest"+var+" = new Vector();";
		code+="\n          if (_pojo.get"+var+"()!=null){";
		code+="\n 		  	  Iterator src"+var+" = _pojo.get"+var+"().iterator();";
		
		code+="\n 		  	  while (src"+var+".hasNext()){";
		code+="\n	 			  CommonBusinessObject _innerPojo =  (CommonBusinessObject)src"+var+".next();";
		code+="\n	 			  PojoImpl source = (PojoImpl)_innerPojo;";
		code+="\n	 			  source.setFlagDoNotCopy(true); // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)";
		code+="\n	 			  dest" + var + ".add(_innerPojo.copy());";
		code+="\n		  	   }";		
		code+="\n		      _form.set"+var+"(dest"+var+");";
		code+="\n		  }";
		//code+="\n		  }else System.err.println(\"Attenzione one-to-many vuoto oggetto null!!!\");";
		//code+="\n		  }else System.err.println(\"[\"+this.getClass().getName()+\"]Attenzione one-to-many vuoto oggetto null!!!\");";
		
		
		return code;
	}
	
	
	public String getClonePojoMethod(String nameAttributeCollection){
		
		String var = Syntax.onlyFirstUpperCase(nameAttributeCollection);
		String code="";		
		
		code+="\n         /*";
		code+="\n          *";
		code+="\n          *  Code clone "+var+" attribute";
		code+="\n          *";
		code+="\n          */ \n";
		code+="\n 		  Collection dest"+var+" = new Vector();";
		code+="\n 		  Iterator src"+var+" = _currentPojo.get"+var+"().iterator();";
		
		code+="\n 		  while (src"+var+".hasNext()){";
		code+="\n	 		CommonBusinessObject _innerPojo =  (CommonBusinessObject)src"+var+".next();";
		code+="\n	 		dest"+var+".add(_innerPojo.copy());";
		code+="\n		  }";		
		code+="\n		  _pojo.set"+var+"(dest"+var+");";
		
		
		return code;
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
	/**
	 * @return Restituisce il valore attributes.
	 */
	public Collection getAttributes() {
		return attributes;
	}
	/**
	 * @param attributes Il valore attributes da impostare.
	 */
	public void setAttributes(Collection attributes) {
		this.attributes = attributes;
	}
	/**
	 * @return Restituisce il valore extendsOrm.
	 */
	public OrmEreditato getExtendOrm() {
		return extendOrm;
	}
	/**
	 * @param extendsOrm Il valore extendsOrm da impostare.
	 */
	public void setExtendOrm(OrmEreditato extendOrm) {
		this.extendOrm = extendOrm;
	}
	/**
	 * @return Restituisce il valore orm.
	 */
	public String getOrm() {
		return orm;
	}
	/**
	 * @param orm Il valore orm da impostare.
	 */
	public void setOrm(String orm) {
		this.orm = orm;
	}
	/**
	 * @return Restituisce il valore ormClient.
	 */
	public String getOrmClient() {
		return ormClient;
	}
	/**
	 * @param ormClient Il valore ormClient da impostare.
	 */
	public void setOrmClient(String ormClient) {
		this.ormClient = ormClient;
	}
	/**
	 * @return Restituisce il valore commonReference.
	 */
	public boolean isCommonReference() {
		return commonReference;
	}
	/**
	 * @param commonReference Il valore commonReference da impostare.
	 */
	public void setCommonReference(boolean commonReference) {
		this.commonReference = commonReference;
	}
	/**
	 * @return Restituisce il valore commonAttributes.
	 */
	public Collection getCommonAttributes() {
		return commonAttributes;
	}
	/**
	 * @param commonAttributes Il valore commonAttributes da impostare.
	 */
	public void setCommonAttributes(Collection commonAttributes) {
		this.commonAttributes = commonAttributes;
	}
	/**
	 * @return Restituisce il valore clonable.
	 */
	public boolean isClonable() {
		return clonable;
	}
	/**
	 * @param clonable Il valore clonable da impostare.
	 */
	public void setClonable(boolean clonable) {
		this.clonable = clonable;
	}
	/**
	 * @return Restituisce il valore compositeId.
	 */
	public Collection getCompositeId() {
		return compositeId;
	}
	/**
	 * @param compositeId Il valore compositeId da impostare.
	 */
	public void setCompositeId(Collection compositeId) {
		this.compositeId = compositeId;
	}
	/**
	 * @return Restituisce il valore isVista.
	 */
	public boolean isVista() {
		return isVista;
	}
	/**
	 * @param isVista Il valore isVista da impostare.
	 */
	public void setVista(boolean isVista) {
		this.isVista = isVista;
	}

	/**
	 * @return Restituisce il valore isVistaWithCostrEmpty.
	 */
	public boolean isVistaWithCostrEmpty() {
		return isVistaWithCostrEmpty;
	}
	/**
	 * @param isVistaWithCostrEmpty Il valore isVistaWithCostrEmpty da impostare.
	 */
	public void setVistaWithCostrEmpty(boolean isVistaWithCostrEmpty) {
		this.isVistaWithCostrEmpty = isVistaWithCostrEmpty;
	}
	/**
	 * @return Restituisce il valore fakedObj.
	 */
	public boolean isFakedObj() {
		return fakedObj;
	}
	/**
	 * @param fakedObj Il valore fakedObj da impostare.
	 */
	public void setFakedObj(boolean fakedObj) {
		this.fakedObj = fakedObj;
	}
	/**
	 * @return Restituisce il valore refFakeName.
	 */
	public String getRefFakeName() {
		return refFakeName;
	}
	/**
	 * @param refFakeName Il valore refFakeName da impostare.
	 */
	public void setRefFakeName(String refFakeName) {
		this.refFakeName = refFakeName;
	}
	/**
	 * @return Restituisce il valore otherCostructor.
	 */
	public String getOtherCostructor() {
		return otherCostructor;
	}
	/**
	 * @param otherCostructor Il valore otherCostructor da impostare.
	 */
	public void setOtherCostructor(String otherCostructor) {		
		this.otherCostructor = otherCostructor;
	}
}
