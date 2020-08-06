/**
*
* Classe generata
*
*/

package it.nch.erbweb.profilo;


import java.io.Serializable;


public class RicercaElencoAziendeOperatoreOutputVOImpl implements Serializable{

	private String denominazione;
    private String intestatario;
    private String ragionesociale;
    private String sia;
    private String lapl;
    private String tipointestatario;
    private String gruppo;


	public RicercaElencoAziendeOperatoreOutputVOImpl(String denominazione, String intestatario,String ragionesociale,String sia,String lapl,String tipointestatario,String gruppo){
		super();
		this.denominazione = denominazione;
		this.intestatario=intestatario;
		this.ragionesociale=ragionesociale;
		this.sia=sia;
		this.lapl=lapl;
		this.tipointestatario=tipointestatario;
		this.gruppo=gruppo;
	}

	public RicercaElencoAziendeOperatoreOutputVOImpl( ){
		
	}

    public String getIntestatario() {
 		return this.intestatario;
 	} 

    public void setIntestatario(String intestatario){
 		this.intestatario=intestatario;
    } 

    public String getRagionesociale() {
 		return this.ragionesociale;
 	} 

    public void setRagionesociale(String ragionesociale){
 		this.ragionesociale=ragionesociale;
    } 

    public String getSia() {
 		return this.sia;
 	} 

    public void setSia(String sia){
 		this.sia=sia;
    } 

    public String getLapl() {
 		return this.lapl;
 	} 

    public void setLapl(String lapl){
 		this.lapl=lapl;
    } 

    public String getTipointestatario() {
 		return this.tipointestatario;
 	} 

    public void setTipointestatario(String tipointestatario){
 		this.tipointestatario=tipointestatario;
    } 

    public String getGruppo() {
 		return this.gruppo;
 	} 

    public void setGruppo(String gruppo){
 		this.gruppo=gruppo;
    } 

	/**
	 * @return
	 */
	public String getDenominazione() {
		return denominazione;
	}

	/**
	 * @param string
	 */
	public void setDenominazione(String string) {
		denominazione = string;
	}

}
