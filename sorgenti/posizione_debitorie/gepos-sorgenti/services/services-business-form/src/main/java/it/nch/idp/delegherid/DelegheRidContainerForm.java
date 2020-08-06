package it.nch.idp.delegherid;



import it.nch.utility.iban.IbanHelper;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.ValidatorForm;


public class DelegheRidContainerForm extends ValidatorForm{
	
	private String iban;
	private String iban_it;
	private String iban_cd;
	private String iban_cin;
	private String iban_abi;
	private String iban_cab;
	private String iban_cc;
	private String ragSociale;

	private Object delega;
	
	private String[] selectedIds;
	
	public String[] getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public String getIban_it() {
		return iban_it;
	}
	public void setIban_it(String iban_it) {
		this.iban_it = iban_it;
	}
	public String getIban_cd() {
		return iban_cd;
	}
	public void setIban_cd(String iban_cd) {
		this.iban_cd = iban_cd;
	}
	public String getIban_cin() {
		return iban_cin;
	}
	public void setIban_cin(String iban_cin) {
		this.iban_cin = iban_cin;
	}
	public String getIban_abi() {
		return iban_abi;
	}
	public void setIban_abi(String iban_abi) {
		this.iban_abi = iban_abi;
	}
	public String getIban_cab() {
		return iban_cab;
	}
	public void setIban_cab(String iban_cab) {
		this.iban_cab = iban_cab;
	}
	public String getIban_cc() {
		return iban_cc;
	}
	public void setIban_cc(String iban_cc) {
		this.iban_cc = iban_cc;
	}
	public String getRagSociale() {
		return ragSociale;
	}

	public void setRagSociale(String ragSociale) {
		this.ragSociale = ragSociale;
	}

	public Object getDelega() {
		return delega;
	}

	public void setDelega(Object delega) {
		this.delega = delega;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		return super.validate(mapping, request);
		
	}
	
	@Override
		public void reset(ActionMapping mapping, HttpServletRequest request) {
			// TODO Auto-generated method stub
			super.reset(mapping, request);
			this.iban= ""; 
		}
		
}
