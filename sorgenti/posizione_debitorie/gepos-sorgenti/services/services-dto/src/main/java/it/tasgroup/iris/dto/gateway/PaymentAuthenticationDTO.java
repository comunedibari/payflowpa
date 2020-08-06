/**
 * 
 */
package it.tasgroup.iris.dto.gateway;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author pazzik
 *
 */
public class PaymentAuthenticationDTO implements Serializable{
	
	String token;
	BigDecimal amountToCheck; 
	String url_ok; 
	String url_ko; 
	String url_annulla;
	String flagOpposizione730;
	String redirectOnly;
	
	/**
	 * @return the url_annulla
	 */
	public String getUrl_annulla() {
		return url_annulla;
	}
	/**
	 * @param url_annulla the url_annulla to set
	 */
	public void setUrl_annulla(String url_annulla) {
		this.url_annulla = url_annulla;
	}
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
	 * @return the decimalAmountToCheck
	 */
	public BigDecimal getAmountToCheck() {
		return amountToCheck;
	}
	/**
	 * @param decimalAmountToCheck the decimalAmountToCheck to set
	 */
	public void setAmountToCheck(BigDecimal decimalAmountToCheck) {
		this.amountToCheck = decimalAmountToCheck;
	}
	/**
	 * @return the url_ok
	 */
	public String getUrl_ok() {
		return url_ok;
	}
	/**
	 * @param url_ok the url_ok to set
	 */
	public void setUrl_ok(String url_ok) {
		this.url_ok = url_ok;
	}
	/**
	 * @return the url_ko
	 */
	public String getUrl_ko() {
		return url_ko;
	}
	/**
	 * @param url_ko the url_ko to set
	 */
	public void setUrl_ko(String url_ko) {
		this.url_ko = url_ko;
	}
	public String getFlagOpposizione730() {
		return flagOpposizione730;
	}
	public void setFlagOpposizione730(String flagOpposizione730) {
		this.flagOpposizione730 = flagOpposizione730;
	}
	public String getRedirectOnly() {
		return redirectOnly;
	}
	public void setRedirectOnly(String redirectOnly) {
		this.redirectOnly = redirectOnly;
	}
	
}
