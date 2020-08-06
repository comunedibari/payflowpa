package it.nch.idp.gestionetipimessaggio;



import it.nch.utility.iban.IbanHelper;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.ValidatorForm;


public class GestioneTipiMessaggioContainerForm extends ValidatorForm{
	
	// selezione da checkbox
	private Long[] ids;
	private Long[] selectedIds;
	private Integer isAllUnsubsribed;
	
	public Long[] getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(Long[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
		
	public Integer getIsAllUnsubsribed() {
		return isAllUnsubsribed;
	}
	
	public void setIsAllUnsubsribed(Integer isAllUnsubsribed) {
		this.isAllUnsubsribed = isAllUnsubsribed;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		return super.validate(mapping, request);
		
	}
		
	@Override
		public void reset(ActionMapping mapping, HttpServletRequest request) {
			// TODO Auto-generated method stub
			super.reset(mapping, request);
		}
	
		
}
