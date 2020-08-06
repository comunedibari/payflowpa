/**
*
* Classe generata
*
*/

package it.nch.erbweb.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class DistintaVO implements Serializable {

	private String mnumerocc;
	private String distinta;
	private String mabi;
	private String mcab;
	private String mtiporapporto;
	private String mdivisarapporto;
	private String descrizione;
	private Date datavalutaord;
	private BigDecimal importo;
	private Integer numdisposizioni;
	private String ibanadd;
	private Integer errate;
	private Integer warning;
	private String stato;
	private Timestamp dataModifica;
	private Timestamp lastUpdate;
	private String riferimentopagamento1;
	private String riferimentopagamento2;
	private Date valutaaccredito;
	private String miban;
	private String minIban;
	private String maxIban;

	/**
	 * @return
	 */
	public Date getDatavalutaord() {
		return datavalutaord;
	}

	/**
	 * @return
	 */
	public String getDistinta() {
		return distinta;
	}

	/**
	 * @return
	 */
	public String getIbanadd() {
		return ibanadd;
	}

	/**
	 * @return
	 */
	public String getMabi() {
		return mabi;
	}

	/**
	 * @return
	 */
	public String getMcab() {
		return mcab;
	}

	/**
	 * @return
	 */
	public String getMnumerocc() {
		return mnumerocc;
	}

	/**
	 * @return
	 */
	public String getMtiporapporto() {
		return mtiporapporto;
	}

	/**
	 * @param date
	 */
	public void setDatavalutaord(Date date) {
		datavalutaord = date;
	}

	/**
	 * @param string
	 */
	public void setDistinta(String string) {
		distinta = string;
	}

	/**
	 * @param string
	 */
	public void setIbanadd(String string) {
		ibanadd = string;
	}

	/**
	 * @param string
	 */
	public void setMabi(String string) {
		mabi = string;
	}

	/**
	 * @param string
	 */
	public void setMcab(String string) {
		mcab = string;
	}

	/**
	 * @param string
	 */
	public void setMnumerocc(String string) {
		mnumerocc = string;
	}

	/**
	 * @param string
	 */
	public void setMtiporapporto(String string) {
		mtiporapporto = string;
	}

	/**
	 * @return
	 */
	public BigDecimal getImporto() {
		return importo;
	}

	/**
	 * @param double1
	 */
	public void setImporto(BigDecimal double1) {
		importo = double1;
	}

	/**
	 * @return
	 */
	public Integer getErrate() {
		return errate;
	}

	/**
	 * @return
	 */
	public Integer getNumdisposizioni() {
		return numdisposizioni;
	}

	/**
	 * @return
	 */
	public Integer getWarning() {
		return warning;
	}

	/**
	 * @param double1
	 */
	public void setErrate(Integer double1) {
		errate = double1;
	}

	/**
	 * @param integer
	 */
	public void setNumdisposizioni(Integer integer) {
		numdisposizioni = integer;
	}

	/**
	 * @param double1
	 */
	public void setWarning(Integer double1) {
		warning = double1;
	}

	/**
	 * @return
	 */
	public String getMdivisarapporto() {
		return mdivisarapporto;
	}

	/**
	 * @param string
	 */
	public void setMdivisarapporto(String string) {
		mdivisarapporto = string;
	}

	/**
	 * @return
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param string
	 */
	public void setDescrizione(String string) {
		descrizione = string;
	}

	/**
	 * @return
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param string
	 */
	public void setStato(String string) {
		stato = string;
	}

	/**
	 * @return
	 */
	public Timestamp getDataModifica() {
		return dataModifica;
	}

	/**
	 * @param timestamp
	 */
	public void setDataModifica(Timestamp timestamp) {
		dataModifica = timestamp;
	}

	/**
	 * @return
	 */
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param timestamp
	 */
	public void setLastUpdate(Timestamp timestamp) {
		lastUpdate = timestamp;
	}

	/**
	 * @return
	 */
	public String getRiferimentopagamento1() {
		return riferimentopagamento1;
	}

	/**
	 * @return
	 */
	public String getRiferimentopagamento2() {
		return riferimentopagamento2;
	}

	/**
	 * @param string
	 */
	public void setRiferimentopagamento1(String string) {
		riferimentopagamento1 = string;
	}

	/**
	 * @param string
	 */
	public void setRiferimentopagamento2(String string) {
		riferimentopagamento2 = string;
	}

	/**
	 * @return
	 */
	public Date getValutaaccredito() {
		return valutaaccredito;
	}

	/**
	 * @param date
	 */
	public void setValutaaccredito(Date date) {
		valutaaccredito = date;
	}

	/**
	 * @return
	 */
	public String getMiban() {
		return miban;
	}

	/**
	 * @param string
	 */
	public void setMiban(String string) {
		miban = string;
	}

	/**
	 * @return
	 */
	public String getMaxIban() {
		return maxIban;
	}

	/**
	 * @return
	 */
	public String getMinIban() {
		return minIban;
	}

	/**
	 * @param string
	 */
	public void setMaxIban(String string) {
		maxIban = string;
	}

	/**
	 * @param string
	 */
	public void setMinIban(String string) {
		minIban = string;
	}

	private String idSpedizione;
	/**
			 * @return
			 */
	public String getIdSpedizione() {
		return idSpedizione;
	}

	/**
			 * @param string
			 */
	public void setIdSpedizione(String string) {
		idSpedizione = string;
	}

}
