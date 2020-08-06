/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.is.fo.profilo.FunzionioperatoriCollectionVOForm;
import it.nch.fwk.fo.dto.*;
import it.nch.fwk.fo.dto.business.PojoImpl;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class FunzionioperatoriCollectionVOImpl extends PojoImpl implements FunzionioperatoriCollectionVOCommon, FunzionioperatoriCollectionVOPojo {

    private String operatore;
    private String corporate;
    private Collection funzionioperatori;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;


    public String getOperatore() {
 		return this.operatore;
 	} 

    public void setOperatore(String operatore){
 		this.operatore=operatore;
    } 

    public String getCorporate() {
 		return this.corporate;
 	} 

    public void setCorporate(String corporate){
 		this.corporate=corporate;
    } 

    public Collection getFunzionioperatori() {
 		return this.funzionioperatori;
 	} 

    public void setFunzionioperatori(Collection funzionioperatori){
 		this.funzionioperatori=funzionioperatori;
    } 




    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("operatore="+this.getOperatore());
       System.out.println("corporate="+this.getCorporate());
       System.out.println("funzionioperatori="+this.getFunzionioperatori());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    public String getCorporateIForm() {
 		return this.corporate;
 	} 

    public void setCorporateIForm(String corporateIForm){
 		this.corporate=corporateIForm;
    } 

    public String getOperatoreIForm() {
 		return this.operatore;
 	} 

    public void setOperatoreIForm(String operatoreIForm){
 		this.operatore=operatoreIForm;
    } 

    public Collection getFunzionioperatoriIForm() {
 		return (this.funzionioperatori);
 	} 

    public void setFunzionioperatoriIForm(Collection funzionioperatoriIForm){
 		this.funzionioperatori=(funzionioperatoriIForm);
    } 

    /**
     *
     *COPY Method Pojo Vs Form
     *
     **/

      public CommonBusinessObject copy(){


         bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
         bfr = bfl.useBeanFactory("it.nch.orm");
         bf=bfr.getFactory();
         FunzionioperatoriCollectionVOForm _form =(FunzionioperatoriCollectionVOForm) bf.getBean("FunzionioperatoriCollectionVOForm");

          _form.setNativePojo(this);

         FunzionioperatoriCollectionVOCommon _pojo = this;

         _form.setOperatoreIForm(_pojo.getOperatoreIForm());
         _form.setCorporateIForm(_pojo.getCorporateIForm());


         /*
          *
          *  Code copy FunzionioperatoriIForm attribute
          *
          */ 

 		  Collection destFunzionioperatoriIForm = new Vector();
          if (_pojo.getFunzionioperatoriIForm()!=null){
 		  	  Iterator srcFunzionioperatoriIForm = _pojo.getFunzionioperatoriIForm().iterator();
 		  	  while (srcFunzionioperatoriIForm.hasNext()){
	 			  CommonBusinessObject _innerPojo =  (CommonBusinessObject)srcFunzionioperatoriIForm.next();
	 			  PojoImpl source = (PojoImpl)_innerPojo;
	 			  source.setFlagDoNotCopy(true); // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
	 			  destFunzionioperatoriIForm.add(_innerPojo.copy());
		  	   }
		      _form.setFunzionioperatoriIForm(destFunzionioperatoriIForm);
		  }


         return _form;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl((CommonBusinessObject)this);
	  }



}
