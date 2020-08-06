package it.nch.is.fo.profilo;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.enti.TipologiaEntiCommon;
import it.nch.is.fo.tributi.ITributoEnte;

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class EntiFormImpl extends BaseForm implements EntiForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2739442750952238954L;
	private String idEnteIForm;
	private String codiceEnteIForm;
    private TipologiaEntiCommon tipoEnteobjIForm;
    private String statoEnteIForm;
    private String denominazioneIForm;
    private IntestatariCommon intestatariobjIForm;
    private String provinciaIForm;
    private String maxNumTributiIForm;
    private String cdAziendaSanitariaIForm;
    private String glnIForm;
    private Set<ITributoEnte> tributiIForm;
    private FormFile logoFile;
    
    private String logoFileName;
    
    // TYPE SOLO PER LA FORM
    private String adminPasswordIForm;
    
    private String flNdpIForm;
	private String flNdpModello3IForm;
	private String flBancaTesorieraIForm;
	private String flBlfIForm;
	private String auxDigitIForm;
	private String codiceStazionePAIForm;
	private String codSegregazIForm;
	private String autorizzStampaDDPIForm;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;
    
    protected Logger logger = LogManager.getLogger(this.getClass());

	public EntiFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
		tipoEnteobjIForm=(TipologiaEntiCommon)bf.getBean("TipologiaEntiForm");		
	}



	public void setNativePojo(Object nativePojo) {				
		((BaseForm)this.tipoEnteobjIForm).setNativePojo(((EntiCommon)nativePojo).getTipoEnteobjIForm());
    	this.nativePojo = nativePojo;
	}


    public TipologiaEntiCommon getTipoEnteobjIForm() {
 		return this.tipoEnteobjIForm;
 	} 

    public void setTipoEnteobjIForm(TipologiaEntiCommon tipoEnteobjIForm){
 		this.tipoEnteobjIForm=tipoEnteobjIForm;
    } 
    
    public String getCodiceEnteIForm() {
 		return this.codiceEnteIForm;
 	} 

    public void setCodiceEnteIForm(String codiceEnteIForm){
 		this.codiceEnteIForm=codiceEnteIForm;
    } 
    
    public String getAdminPasswordIForm() {
 		return this.adminPasswordIForm;
 	} 

    public void setAdminPasswordIForm(String adminPasswordIForm){
 		this.adminPasswordIForm=adminPasswordIForm;
    } 

    @Override
	public String getStatoEnteIForm() {		
		return this.statoEnteIForm;
	}

	@Override
	public void setStatoEnteIForm(String statoEnteIForm) {
		this.statoEnteIForm = statoEnteIForm;
		
	}
	
	@Override
	public String getIdEnteIForm() {
		return this.idEnteIForm;
	}


	@Override
	public void setIdEnteIForm(String idEnteIForm) {
		this.idEnteIForm = idEnteIForm;		
	}

	
	@Override
	public String getDenominazioneIForm() {
		return this.denominazioneIForm;
	}

	@Override
	public void setDenominazioneIForm(String denominazioneIForm) {		
		this.denominazioneIForm = denominazioneIForm;
	}

	@Override
	public IntestatariCommon getIntestatarioIForm() {
		return this.intestatariobjIForm;
	}

	@Override
	public void setIntestatarioIForm(IntestatariCommon intestatarioIForm) {
		this.intestatariobjIForm = intestatarioIForm;
	}
	
	@Override
	public String getProvinciaIForm() {
		return this.provinciaIForm;
	}



	@Override
	public void setProvinciaIForm(String provinciaIForm) {
		this.provinciaIForm = provinciaIForm;
		
	}


	public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("codiceEnteIForm="+this.getCodiceEnteIForm());       
       System.out.println("adminPasswordIForm="+this.getAdminPasswordIForm());       
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         EntiForm _form = this;
         EntiCommon _pojo=(EntiCommon)this.nativePojo;

         if (_pojo == null){ 

         	if (Tracer.isDebugEnabled(this.getClass().getName())){ 
				Tracer.debug(this.getClass().getName(),"","",null);
				Tracer.debug(this.getClass().getName(),"copy()","---------------------------------------------------------------------",null);
				Tracer.debug(this.getClass().getName(),"copy()","Attenzione nativeObject dentro FORM vuoto provvedo ad istanziare POJO",null);
				Tracer.debug(this.getClass().getName(),"copy()","---------------------------------------------------------------------",null);
				Tracer.debug(this.getClass().getName(),"","",null);
         	}

			bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
			bfr = bfl.useBeanFactory("it.nch.orm");
			bf=bfr.getFactory();
         	_pojo=(EntiCommon) bf.getBean("Enti");

         }

         _pojo.setIdEnteIForm(_form.getIdEnteIForm());
         if (_form.getTipoEnteobjIForm()!=null)
             _pojo.setTipoEnteobjIForm((TipologiaEntiCommon)_form.getTipoEnteobjIForm().copy());         
         _pojo.setCodiceEnteIForm(_form.getCodiceEnteIForm());
         _pojo.setStatoEnteIForm(_form.getStatoEnteIForm());
         _pojo.setDenominazioneIForm(_form.getDenominazioneIForm());
         /*if (_form.getIntestatarioIForm()!=null)
        	 _pojo.setIntestatarioIForm((IntestatariCommon)_form.getIntestatarioIForm().copy());*/
         _pojo.setProvinciaIForm(_form.getProvinciaIForm());
         _pojo.setMaxNumTributiIForm(_form.getMaxNumTributiIForm());
         _pojo.setCdAziendaSanitariaIForm(_form.getCdAziendaSanitariaIForm());
         _pojo.setGlnIForm(_form.getGlnIForm());
         
         _pojo.setFlNdpIForm(_form.getFlNdpIForm());
         _pojo.setFlNdpModello3IForm(_form.getFlNdpModello3IForm());
         _pojo.setFlBancaTesorieraIForm(_form.getFlBancaTesorieraIForm());
         _pojo.setFlBlfIForm(_form.getFlBlfIForm());
         _pojo.setAuxDigitIForm(_form.getAuxDigitIForm());
         _pojo.setCodSegregazIForm(_form.getCodSegregazIForm());
         _pojo.setCodiceStazionePAIForm(_form.getCodiceStazionePAIForm());
         _pojo.setAutorizzStampaDDPIForm(_form.getAutorizzStampaDDPIForm());
         return _pojo;
	  }

	  @SuppressWarnings("rawtypes")
	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {	   		
	   		this.codiceEnteIForm="";
	   		this.denominazioneIForm="";
	   		this.provinciaIForm="";

	   		// DATI FORM NON COMMON

	   		this.adminPasswordIForm="";
	  }


	public String getMaxNumTributiIForm() {
		return this.maxNumTributiIForm;
	}
	
	public void setMaxNumTributiIForm(String maxNumTributiIForm) {
			this.maxNumTributiIForm = maxNumTributiIForm;
	}

	@Override
	public String getCdAziendaSanitariaIForm() {
		return cdAziendaSanitariaIForm;
	}

	@Override
	public void setCdAziendaSanitariaIForm(String cdAziendaSanitariaIForm) {
		this.cdAziendaSanitariaIForm = cdAziendaSanitariaIForm;
	}

	public String getGlnIForm() {
		return glnIForm;
	}

	public void setGlnIForm(String glnIForm) {
		this.glnIForm = glnIForm;
	}

//
//	@Override
//	public void setMaxNumTributi(Integer maxNumTributi) {
//		if(maxNumTributi == null)
//			maxNumTributiIForm = "";
//		else	
//			maxNumTributiIForm = maxNumTributi.toString();
//		
//	}
//	
//	@Override
//	public Integer getMaxNumTributi() {
//		if(maxNumTributiIForm != null || !maxNumTributiIForm.equals(""))
//			return Integer.valueOf(maxNumTributiIForm);
//		else return null;
//	}
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
				
				FormFile logoFile = (FormFile) getLogoFile();
			    ActionErrors errors = new ActionErrors();
		 
			    if(logoFile.getFileSize() == 0) {
			    	
			       errors.add("ERRORI", new ActionMessage("error.common.file.required"));
			       return errors;
			    }
		 
			    //only allow to upload image files
			    if(!"image/jpeg".equals(logoFile.getContentType()) 
			    		&& !"image/gif".equals(logoFile.getContentType())){
			        errors.add("ERRORI",
			    	new ActionMessage("error.common.file.imagefile.only"));
			        return errors;
			    }
			    
			    try {
			    
				    byte[] fileData = logoFile.getFileData();
		            ByteArrayInputStream bais = new ByteArrayInputStream(fileData);
		            BufferedImage inputImage = ImageIO.read(bais);
				    if( inputImage.getColorModel().getTransparency() != Transparency.OPAQUE) {
				    	errors.add("ERRORI",
						    	new ActionMessage("error.common.file.imagefile.transparency"));
						return errors;
				    }
			    } catch (Exception e) {
			    	logger.error(e); // continue...
			    }

			    if(logoFile.getFileSize() > 1024*100){ //100Kb
			       errors.add("ERRORI",
				    new ActionMessage("error.common.file.size.limit", 1024*100));
			       return errors;
			    }
		 
			    return errors;
			}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	
	public FormFile getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(FormFile logoFile) {
		this.logoFile = logoFile;
	}

	public Set<ITributoEnte> getTributiIForm() {
		return tributiIForm;
	}
	
    public void setTributiIForm(Set<ITributoEnte> tributiIForm) {
		this.tributiIForm = tributiIForm;
	}
    
	public String getFlNdpIForm() {
		return flNdpIForm;
	}

	public void setFlNdpIForm(String flNdp) {
		this.flNdpIForm = flNdp;
	}

	
	public String getFlNdpModello3IForm() {
		return flNdpModello3IForm;
	}

	public void setFlNdpModello3IForm(String flNdpModello3) {
		this.flNdpModello3IForm = flNdpModello3;
	}

	
	public String getFlBancaTesorieraIForm() {
		return flBancaTesorieraIForm;
	}

	public void setFlBancaTesorieraIForm(String flBancaTesoriera) {
		this.flBancaTesorieraIForm = flBancaTesoriera;
	}

	
	public String getFlBlfIForm() {
		return flBlfIForm;
	}

	public void setFlBlfIForm(String flBlf) {
		this.flBlfIForm = flBlf;
	}



	public String getAuxDigitIForm() {
		return auxDigitIForm;
	}



	public void setAuxDigitIForm(String auxDigitIForm) {
		this.auxDigitIForm = auxDigitIForm;
	}



	public String getCodiceStazionePAIForm() {
		return codiceStazionePAIForm;
	}



	public void setCodiceStazionePAIForm(String codiceStazionePAIForm) {
		this.codiceStazionePAIForm = codiceStazionePAIForm;
	}

	@Override
	public String getAutorizzStampaDDPIForm() {
		
		return autorizzStampaDDPIForm;
	}

	@Override
	public void setAutorizzStampaDDPIForm(String autorizzStampaDDP) {
		autorizzStampaDDPIForm=autorizzStampaDDP;		
	}

	@Override
	public String getCodSegregazIForm() {
		return codSegregazIForm;
	}

	@Override
	public void setCodSegregazIForm(String codSegregaz) {
		codSegregazIForm = codSegregaz;
	}

}
