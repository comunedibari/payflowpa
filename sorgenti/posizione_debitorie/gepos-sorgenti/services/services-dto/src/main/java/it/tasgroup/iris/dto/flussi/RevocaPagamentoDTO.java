package it.tasgroup.iris.dto.flussi;


import java.io.Serializable;
import java.sql.Timestamp;

import it.tasgroup.services.util.enumeration.EnumStatoRevoca;

public class RevocaPagamentoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
    private String statoRevoca; //-	STATO_REVOCA: VARCHAR (1) Valori R=Richiesta, A=Accettata, N=Negata, W=In attesa decisione
    private String esitoRevoca;	//	-	ESITO_REVOCA_INVIATO:  VARCHAR (1) Valori = 0, 1.
    private String tipoRevocaRichiesta; // TIPO_REVOCA_RICHIESTA :  VARCHAR (1) - Valori: 0,1,2  Default Null (come da messaggio RR)
    private String causaleRevocaRichiesta; 
    private String datiAggRevocaRichiesta; 
    private String idMessaggioRevoca; 
    private Timestamp tsMessaggioRevoca; 
    private String causaleEsitoRevoca; 	
    private String datiAggiuntiviEsitoRevoca;
    
    private String causaleEsito;
    private String datiAggiuntiviEsito;
    
	public String getDatiAggiuntiviEsito() {
		return datiAggiuntiviEsito;
	}
	public void setDatiAggiuntiviEsito(String datiAggiuntiviEsito) {
		this.datiAggiuntiviEsito = datiAggiuntiviEsito;
	}
	public String getCausaleEsito() {
		return causaleEsito;
	}
	public void setCausaleEsito(String causaleEsito) {
		this.causaleEsito = causaleEsito;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStatoRevoca() {
		return statoRevoca;
	}
	public void setStatoRevoca(String statoRevoca) {
		this.statoRevoca = statoRevoca;
	}
	public String getEsitoRevoca() {
		return esitoRevoca;
	}
	public void setEsitoRevoca(String esitoRevoca) {
		this.esitoRevoca = esitoRevoca;
	}
	public String getTipoRevocaRichiesta() {
		return tipoRevocaRichiesta;
	}
	public void setTipoRevocaRichiesta(String tipoRevocaRichiesta) {
		this.tipoRevocaRichiesta = tipoRevocaRichiesta;
	}
	public String getCausaleRevocaRichiesta() {
		return causaleRevocaRichiesta;
	}
	public void setCausaleRevocaRichiesta(String causaleRevocaRichiesta) {
		this.causaleRevocaRichiesta = causaleRevocaRichiesta;
	}
	public String getDatiAggRevocaRichiesta() {
		return datiAggRevocaRichiesta;
	}
	public void setDatiAggRevocaRichiesta(String datiAggRevocaRichiesta) {
		this.datiAggRevocaRichiesta = datiAggRevocaRichiesta;
	}
	public String getIdMessaggioRevoca() {
		return idMessaggioRevoca;
	}
	public void setIdMessaggioRevoca(String idMessaggioRevoca) {
		this.idMessaggioRevoca = idMessaggioRevoca;
	}
	public Timestamp getTsMessaggioRevoca() {
		return tsMessaggioRevoca;
	}
	public void setTsMessaggioRevoca(Timestamp tsMessaggioRevoca) {
		this.tsMessaggioRevoca = tsMessaggioRevoca;
	}
	public String getCausaleEsitoRevoca() {
		return causaleEsitoRevoca;
	}
	public void setCausaleEsitoRevoca(String causaleEsitoRevoca) {
		this.causaleEsitoRevoca = causaleEsitoRevoca;
	}
	public String getDatiAggiuntiviEsitoRevoca() {
		return datiAggiuntiviEsitoRevoca;
	}
	public void setDatiAggiuntiviEsitoRevoca(String datiAggiuntiviEsitoRevoca) {
		this.datiAggiuntiviEsitoRevoca = datiAggiuntiviEsitoRevoca;
	} 
	
	public boolean isDaValutare() {
		return statoRevoca.equals(EnumStatoRevoca.ACCETTATA.getChiave());
	}

	public void setDaRevocare(boolean daRevocare) {
		// do nothing
	}

	
}
