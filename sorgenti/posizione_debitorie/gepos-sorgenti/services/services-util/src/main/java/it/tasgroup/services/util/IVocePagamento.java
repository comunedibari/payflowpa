/**
 * 
 */
package it.tasgroup.services.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author pazzik
 *
 */
public interface IVocePagamento extends Serializable{
	
	public String getIdPendenza();

	public void setIdPendenza(String idPendenza);


	public String getIdCondizione();

	public void setIdCondizione(String idCondizione);

	
	public String getIdVoce();

	public void setIdVoce(String idVoce);

	
	public Timestamp getTsDecorrenza();

	public void setTsDecorrenza(Timestamp tsDecorrenza);

	
	public String getTiVoce();

	public void setTiVoce(String tiVoce);

	
	public String getCoVoce();

	public void setCoVoce(String coVoce);

	
	public String getDeVoce();

	public void setDeVoce(String deVoce);

	
	public BigDecimal getImVoce();

	public void setImVoce(BigDecimal imVoce);

	
	public String getCoCapBilancio();

	public void setCoCapBilancio(String coCapBilancio);

	
	public String getCoAccertamento();

	public void setCoAccertamento(String coAccertamento);

	
	public String getStRiga();

	public void setStRiga(String stRiga);

}
