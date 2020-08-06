/**
 * 
 */
package it.tasgroup.iris.shared.util.shoppingcart;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author pazzik
 *
 */
public interface ISessionShoppingCartItem extends Serializable{

	public String getItemId();

	public void setItemId(String itemId);

	public BigDecimal getItemAmount();

	public void setItemAmount(BigDecimal itemAmount);
	
	public String getDebtor();
	
	public void setDebtor(String debtor);
	
	public String getIdTributo();
	
	public void setIdTributo(String idTributo);
	
	public String getItemOpposizione730();
	
	public void setItemOpposizione730(String itemOpposizione730);

	public String getEmailVers();

	public void setEmailVers(String emailVers);

	public String getCodFiscaleVers();

	public void setCodFiscaleVers(String codFiscaleVers);

	public String getTipoSoggettoVers();

	public void setTipoSoggettoVers(String tipoSoggettoVers);
	
	public String getAnagraficaVers();

	public void setAnagraficaVers(String anagraficaVers);
	
	public String getIndirizzoVers();

	public void setIndirizzoVers(String indirizzoVers);
	
	public String getNumeroCivicoVers();


	public void setNumeroCivicoVers(String numeroCivicoVers);


	public String getCapVers();


	public void setCapVers(String capVers);

	public String getLocalitaVers();


	public void setLocalitaVers(String localitaVers);

	
	public String getProvinciaVers();


	public void setProvinciaVers(String provinciaVers);

	public String getNazioneVers();


	public void setNazioneVers(String nazioneVers);
}
