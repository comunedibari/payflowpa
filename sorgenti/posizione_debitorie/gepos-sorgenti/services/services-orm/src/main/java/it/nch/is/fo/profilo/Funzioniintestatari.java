/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;


import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="FUN_INTEST")
@NamedQueries({
	@NamedQuery(name="getFunzioniIntestatari", 
			query="select funIntest from Funzioniintestatari funIntest where funIntest.funzId.corporate = :corporate")
})
public class Funzioniintestatari extends PojoImpl implements FunzioniintestatariPojo {

	private static final long serialVersionUID = 1L;

	/*** PK Reference ***/
    private FunzioniintestatariId funzId;
    
    /*** Persistent References ***/
    private Funzioni funzioniobj;
    private Intestatari intestatariobj;

    public Funzioniintestatari (){
    	this.funzId = new FunzioniintestatariId();
    }
    
    public Funzioniintestatari (FunzioniintestatariId funzId){
    	this.funzId = funzId;
    }
    
    @Id
	public FunzioniintestatariId getFunzId() {
		return funzId;
	}
	public void setFunzId(FunzioniintestatariId funzId) {
		this.funzId = funzId;
	}

    @ManyToOne(targetEntity=it.nch.is.fo.profilo.Funzioni.class)
    @JoinColumn(name="CODFUNZIONE", nullable=false, insertable=false, updatable=false)
    public FunzioniPojo getFunzioniobj() {
 		return this.funzioniobj;
 	} 
    public void setFunzioniobj(FunzioniPojo funzioniobj){
 		this.funzioniobj=(Funzioni)funzioniobj;
    } 

    @ManyToOne(targetEntity=it.nch.is.fo.profilo.Intestatari.class)
    @JoinColumn(name="INTESTATARIO", nullable=false, insertable=false, updatable=false)
    public IntestatariPojo getIntestatariobj() {
 		return this.intestatariobj;
 	} 
    public void setIntestatariobj(IntestatariPojo intestatariobj){
 		this.intestatariobj=(Intestatari)intestatariobj;
    } 

	@Override
    @Transient
	public String getCodfunzione() {
 		return this.funzId.getCodfunzione();
 	} 
	@Override
    public void setCodfunzione(String codfunzione){
 		this.getFunzId().setCodfunzione(codfunzione);
    } 
    
	@Override
    @Transient
    public String getCorporate() {
 		return this.funzId.getCorporate();
 	} 
	@Override
    public void setCorporate(String corporate){
 		this.getFunzId().setCorporate(corporate);
    } 


    @Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("codfunzione="+this.getCodfunzione());
       System.out.println("corporate="+this.getCorporate());
       System.out.println("funzioniobj="+this.getFunzioniobj());
       System.out.println("intestatariobj="+this.getIntestatariobj());
    }


    /**
     *
     * NON NECESSITA del Metodo COPY
     *
     **/
    @Transient
	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }

    /**
     *
     * Metodo Clone richiesto
     *
     **/
    @Override
    public Object clone() {

         Funzioniintestatari _pojoCurrent = this;
         Funzioniintestatari _pojo = new Funzioniintestatari();

         _pojo.setCodfunzione(_pojoCurrent.getCodfunzione());
         _pojo.setCorporate(_pojoCurrent.getCorporate());
         _pojo.setFunzioniobj(_pojoCurrent.getFunzioniobj());
         _pojo.setIntestatariobj(_pojoCurrent.getIntestatariobj());
         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funzId == null) ? 0 : funzId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funzioniintestatari other = (Funzioniintestatari) obj;
		if (funzId == null) {
			if (other.funzId != null)
				return false;
		} else if (!funzId.equals(other.funzId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funzioniintestatari [funzId=" + funzId + ", funzioniobj="
				+ funzioniobj + ", intestatariobj=" + intestatariobj + "]";
	}


}
