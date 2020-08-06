/**
 * 
 */
package it.tasgroup.iris.dto.gateway;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author pazzik
 * 
 * TODO: questo DTO dovrebbe prendere il posto di PagamentoPosizioneDebitoriaVO 
 * per evitare di far dipendere services-dto da services-business-common e gateway-webapp dai vecchi VO
 *
 */
public class CartItemDTO implements Serializable{
	private String token;
	private String idCondizione;	
	private String ente;
	private String tributo;
	private Date scadenza;
	private String causale;
	private String idTributoEnte;
	private BigDecimal imTotale;
	private String opposizioneInvio730;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the idCondizione
	 */
	public String getIdCondizione() {
		return idCondizione;
	}
	/**
	 * @param idCondizione the idCondizione to set
	 */
	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}
	
	/**
	 * @return the imTotale
	 */
	public BigDecimal getImTotale() {
		return imTotale;
	}
	/**
	 * @param imTotale the imTotale to set
	 */
	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}
	
	/**
	 * @return the ente
	 */
	public String getEnte() {
		return ente;
	}
	/**
	 * @param ente the ente to set
	 */
	public void setEnte(String ente) {
		this.ente = ente;
	}
	/**
	 * @return the tributo
	 */
	public String getTributo() {
		return tributo;
	}
	/**
	 * @param tributo the tributo to set
	 */
	public void setTributo(String tributo) {
		this.tributo = tributo;
	}
	/**
	 * @return the scadenza
	 */
	public Date getScadenza() {
		return scadenza;
	}
	/**
	 * @param scadenza the scadenza to set
	 */
	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}
	/**
	 * @return the causale
	 */
	public String getCausale() {
		return causale;
	}
	/**
	 * @param causale the causale to set
	 */
	public void setCausale(String causale) {
		this.causale = causale;
	}
	/**
	 * @return the idTributoEnte
	 */
	public String getIdTributoEnte() {
		return idTributoEnte;
	}
	/**
	 * @param idTributoEnte the idTributoEnte to set
	 */
	public void setIdTributoEnte(String idTributoEnte) {
		this.idTributoEnte = idTributoEnte;
	}

	/**
	 * @return the opposizioneInvio730
	 */
	public String getOpposizioneInvio730() {
		return opposizioneInvio730;
	}
	/**
	 * @param opposizioneInvio730 the opposizioneInvio730 to set
	 */
	public void setOpposizioneInvio730(String opposizioneInvio730) {
		this.opposizioneInvio730 = opposizioneInvio730;
	}

}
