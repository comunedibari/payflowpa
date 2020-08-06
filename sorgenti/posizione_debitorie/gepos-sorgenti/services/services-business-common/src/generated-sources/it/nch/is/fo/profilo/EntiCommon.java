package it.nch.is.fo.profilo;

import java.util.Set;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.is.fo.enti.TipologiaEntiCommon;
import it.nch.is.fo.tributi.ITributoEnte;

public interface EntiCommon extends CommonBusinessObject {

	public TipologiaEntiCommon getTipoEnteobjIForm();

	public void setTipoEnteobjIForm(TipologiaEntiCommon tipoEnteobjIForm);

	public String getCodiceEnteIForm();

	public void setCodiceEnteIForm(String codiceEnteIForm);
	
	public String getStatoEnteIForm();

	public void setStatoEnteIForm(String statoIForm);
	
	public String getIdEnteIForm();

	public void setIdEnteIForm(String idEnteIForm);
	
	public IntestatariCommon getIntestatarioIForm();
	
	public void setIntestatarioIForm(IntestatariCommon intestatarioIForm);
	
	public String getDenominazioneIForm();
	
	public void setDenominazioneIForm(String denominazioneIForm);
	
	public String getProvinciaIForm();
	
	public void setProvinciaIForm(String provinciaIForm);

	public String getCdAziendaSanitariaIForm();
	
	public void setCdAziendaSanitariaIForm(String cdAziendaSanitariaIForm);

	public String getMaxNumTributiIForm();

	public void setMaxNumTributiIForm(String maxNumTributiIForm);

    public Set<ITributoEnte> getTributiIForm();
	
    public void setTributiIForm(Set<ITributoEnte> tributiIForm);
    
    public String getGlnIForm();

	public void setGlnIForm(String glnIForm);
	
	public String getFlNdpIForm();
	
	public void setFlNdpIForm(String flNdp);
	
	public String getFlNdpModello3IForm();

	public void setFlNdpModello3IForm(String flNdpModello3);
	
	public String getFlBancaTesorieraIForm();

	public void setFlBancaTesorieraIForm(String flBancaBT);
	
	public String getFlBlfIForm();
	
	public void setFlBlfIForm(String flBlf);
	
	public String getAuxDigitIForm();
	
	public void setAuxDigitIForm(String auxDigit);
	
	public String getCodiceStazionePAIForm();
	
	public void setCodiceStazionePAIForm(String codiceStazionePA);
	
	public String getCodSegregazIForm();
	
	public void setCodSegregazIForm(String codSegregaz);
	
	public String getAutorizzStampaDDPIForm();
	
	public void setAutorizzStampaDDPIForm(String autorizzStampaDDP);

}
