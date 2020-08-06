package it.tasgroup.backoffice.ente.form;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class RevochePagamentoForm extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;
	
	private String tipoRichiesta;
	private String iuv;
	
	private String causale;
	private String datiAggiuntivi;
	


	public void resetFields(ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
    	super.reset(mapping, request);
    	iuv = "";
    	tipoRichiesta = "";
    	causale = "";
    	datiAggiuntivi = "";
    }

	public String getTipoRichiesta() {
		return tipoRichiesta;
	}

	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	
	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getDatiAggiuntivi() {
		return datiAggiuntivi;
	}

	public void setDatiAggiuntivi(String datiAggiuntivi) {
		this.datiAggiuntivi = datiAggiuntivi;
	}


}
