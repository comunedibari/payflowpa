/**
*
* Classe generata
*
*/

package it.nch.erbweb.common;


import java.io.Serializable;
import java.util.List;

public class PreferenzeVO implements Serializable {

	private String proprieta;
	private String valore;
	private String tiposervizio;
	private String intestatario;
	private String operatore;
	private String tipopreferenza;
	private String listaValori;
	private String lock;
	private List listValori;
	/**
	 * 
	 */
	public PreferenzeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public PreferenzeVO(String proprieta, String valore, String tiposervizio) {
		super();
		this.proprieta = proprieta;
		this.valore = valore;
		this.tiposervizio = tiposervizio;
		this.intestatario = "";
		this.operatore = "";
	}

	/**
	 * @return
	 */
	public String getIntestatario() {
		return intestatario;
	}

	/**
	 * @return
	 */
	public String getOperatore() {
		return operatore;
	}

	/**
	 * @return
	 */
	public String getProprieta() {
		return proprieta;
	}

	/**
	 * @return
	 */
	public String getTiposervizio() {
		return tiposervizio;
	}

	/**
	 * @return
	 */
	public String getValore() {
		return valore;
	}

	/**
	 * @param string
	 */
	public void setIntestatario(String string) {
		intestatario = string;
	}

	/**
	 * @param string
	 */
	public void setOperatore(String string) {
		operatore = string;
	}

	/**
	 * @param string
	 */
	public void setProprieta(String string) {
		proprieta = string;
	}

	/**
	 * @param string
	 */
	public void setTiposervizio(String string) {
		tiposervizio = string;
	}

	/**
	 * @param string
	 */
	public void setValore(String string) {
		valore = string;
	}
	/**
	 * @return
	 */
	public String getListaValori() {
		return listaValori;
	}

	/**
	 * @return
	 */
	public String getTipopreferenza() {
		return tipopreferenza;
	}

	/**
	 * @param string
	 */
	public void setListaValori(String string) {
		listaValori = string;
	}

	/**
	 * @param string
	 */
	public void setTipopreferenza(String string) {
		tipopreferenza = string;
	}

	/**
	 * @return
	 */
	public List getListValori() {
		return listValori;
	}

	/**
	 * @param list
	 */
	public void setListValori(List list) {
		listValori = list;
	}

	/**
	 * @return
	 */
	public String getLock() {
		return lock;
	}

	/**
	 * @param string
	 */
	public void setLock(String string) {
		lock = string;
	}

}
