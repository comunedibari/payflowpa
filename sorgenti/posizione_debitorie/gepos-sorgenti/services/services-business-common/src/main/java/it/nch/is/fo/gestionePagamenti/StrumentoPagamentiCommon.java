package it.nch.is.fo.gestionePagamenti;
import it.nch.fwk.fo.common.CommonBusinessObject;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;


public interface StrumentoPagamentiCommon extends CommonBusinessObject {
	
	public abstract String getStrPagamento();
	public abstract void setStrPagamento(String strPagamento);
	public abstract String getDeStrPagamento();
	public abstract void setDeStrPagamento(String deStrPagamento);
	public abstract String getFlStato();
	public abstract void setFlStato(String flStato);
	public abstract String getStRiga();
	public abstract void setStRiga(String stRiga);
	public String getTipoStrumento();
	public void setTipoStrumento(String tipoStrumento);
}
