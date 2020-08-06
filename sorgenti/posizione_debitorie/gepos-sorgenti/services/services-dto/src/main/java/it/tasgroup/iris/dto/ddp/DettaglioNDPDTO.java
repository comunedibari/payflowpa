package it.tasgroup.iris.dto.ddp;

import it.tasgroup.avvisi.NumeroAvvisoUtils;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;


public class DettaglioNDPDTO extends DettaglioDDPDTO{

	//metto qui?
	private static final long serialVersionUID = 1L;
	
	private static final EnumTipoDDP tipo = EnumTipoDDP.NDP;
	        
    private String descrizionePagamento;
    
    private String cfCreditore;
    
	private String  ibanCCP;
    
    private String xmlQRcode;
    
    private IndirizzoDTO indirizzo;

    private String auxDigit = "0";
    
	private String applicationCode = "01";
	
	private String infoTributo;
	private String uoaCompetente;
	private String autorizzStampaBP;
	private String intestazioneCCP;
	private String deTrb;;
	
	
        
	public String toString() {
		   StringBuffer sb = new StringBuffer();
		   sb.append("\n"+this.getClass()+"\n");
	       sb.append("[");
	       sb.append(super.toString());
	       sb.append("]\n");
	       return sb.toString();
	}

	@Override
	public EnumTipoDDP getTipo() {
		
		return tipo;
	}
	
	
//	public String getFormattedID() {
//		
//		StringBuffer buf = new StringBuffer();
//		
//		String compactId = getIdPagamento();
//		
//		for (int i=0; i<compactId.length(); i++){
//			
//			buf.append(compactId.charAt(i));
//			
//			if ((i+1)%4==0)
//				buf.append(' ');
//		}
//			
//		
//		return buf.toString();
//		
//	}
	
	public String calculateNumeroAvviso() {
		return NumeroAvvisoUtils.calculateNumeroAvviso(auxDigit, applicationCode, getIdPagamento(), false);
    }

	public String getFormattedID() {
		return NumeroAvvisoUtils.calculateNumeroAvviso(auxDigit, applicationCode, getIdPagamento(), true);
	}
	
	public String getIuv() {
		return getIdPagamento();
	}
	
    public String getDescrizionePagamento() {
        return descrizionePagamento;
    }

    public void setDescrizionePagamento(String descrizionePagamento) {
        this.descrizionePagamento = descrizionePagamento;
    }

    public String getXmlQRcode() {
        return xmlQRcode;
    }
    
    public void setXmlQRcode(String xmlQRcode) {
        this.xmlQRcode = xmlQRcode;
    }

    public IndirizzoDTO getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(IndirizzoDTO indirizzo) {
        this.indirizzo = indirizzo;
    }
    
    public String getIdFiscaleCreditore(){
    	
    	return indirizzo.getPiva();
    }

	public String getCfCreditore() {
		return cfCreditore;
	}

	public void setCfCreditore(String cfCreditore) {
		this.cfCreditore = cfCreditore;
	}
	
	
    public String getAuxDigit() {
		return auxDigit;
	}

	public void setAuxDigit(String auxDigit) {
		this.auxDigit = auxDigit;
	}

    public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	public String getIbanCCP() {
		return ibanCCP;
	}

	public void setIbanCCP(String ibanCCP) {
		this.ibanCCP = ibanCCP;
	}

	public String getInfoTributo() {
		return infoTributo;
	}

	public void setInfoTributo(String infoTributo) {
		this.infoTributo = infoTributo;
	}

	public String getUoaCompetente() {
		return uoaCompetente;
	}

	public void setUoaCompetente(String uoaCompetente) {
		this.uoaCompetente = uoaCompetente;
	}

	public String getAutorizzStampaBP() {
		return autorizzStampaBP;
	}

	public void setAutorizzStampaBP(String autorizzStampaBP) {
		this.autorizzStampaBP = autorizzStampaBP;
	}

	public String getIntestazioneCCP() {
		return intestazioneCCP;
	}

	public void setIntestazioneCCP(String intestazioneCCP) {
		this.intestazioneCCP = intestazioneCCP;
	}

	public String getDeTrb() {
		return deTrb;
	}

	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}


    
}
