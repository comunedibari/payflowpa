/**
 * 
 */
package it.tasgroup.iris.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author FabriziE
 *
 */
public class PagamentoRidDTO implements Serializable{

	private String abiBancaDomiciliaria;
	private String codDebitore;
	private String tipoIncassoRid;
	private String ragSocialeDebitore;
	private String riferimentoDebito;
	private String flagIniziativa;
	private String flagStorno;
	private String indirizzoDebitore;

	private String codRiferimento;
	
	private Long idPagamentoRid;
	private Integer idCfgGateway;
	private Long idDistintaPagamento;
	private String intestatario;
	private BigDecimal importo;
	private BigDecimal importoCommissioni;
	private Timestamp dataScadenza;
	
	private AllineamentiElettroniciArchiviDTO delega;
	private Object distintaCartaCreditoVO;

	public Long getIdPagamentoRid() {
		return idPagamentoRid;
	}

	public void setIdPagamentoRid(Long idPagamentoRid) {
		this.idPagamentoRid = idPagamentoRid;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public BigDecimal getImportoCommissioni() {
		return importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}

	public Long getIdDistintaPagamento() {
		return idDistintaPagamento;
	}

	public void setIdDistintaPagamento(Long idDistintaPagamento) {
		this.idDistintaPagamento = idDistintaPagamento;
	}

	public Timestamp getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public AllineamentiElettroniciArchiviDTO getDelega() {
		return delega;
	}

	public void setDelega(AllineamentiElettroniciArchiviDTO delega) {
		this.delega = delega;
	}

	public String getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	public String getAbiBancaDomiciliaria() {
		return abiBancaDomiciliaria;
	}

	public void setAbiBancaDomiciliaria(String abiBancaDomiciliaria) {
		this.abiBancaDomiciliaria = abiBancaDomiciliaria;
	}

	public String getCodDebitore() {
		return codDebitore;
	}

	public void setCodDebitore(String codDebitore) {
		this.codDebitore = codDebitore;
	}

	public String getTipoIncassoRid() {
		return tipoIncassoRid;
	}

	public void setTipoIncassoRid(String tipoIncassoRid) {
		this.tipoIncassoRid = tipoIncassoRid;
	}

	public String getRagSocialeDebitore() {
		return ragSocialeDebitore;
	}

	public void setRagSocialeDebitore(String ragSocialeDebitore) {
		this.ragSocialeDebitore = ragSocialeDebitore;
	}

	public String getRiferimentoDebito() {
		return riferimentoDebito;
	}

	public void setRiferimentoDebito(String riferimentoDebito) {
		this.riferimentoDebito = riferimentoDebito;
	}

	public String getFlagIniziativa() {
		return flagIniziativa;
	}

	public void setFlagIniziativa(String flagIniziativa) {
		this.flagIniziativa = flagIniziativa;
	}

	public String getFlagStorno() {
		return flagStorno;
	}

	public void setFlagStorno(String flagStorno) {
		this.flagStorno = flagStorno;
	}

	public String getIndirizzoDebitore() {
		return indirizzoDebitore;
	}

	public void setIndirizzoDebitore(String indirizzoDebitore) {
		this.indirizzoDebitore = indirizzoDebitore;
	}

	public String getCodRiferimento() {
		return codRiferimento;
	}

	public void setCodRiferimento(String codRiferimento) {
		this.codRiferimento = codRiferimento;
	}

	public Object getDistintaCartaCreditoVO() {
		return distintaCartaCreditoVO;
	}

	public void setDistintaCartaCreditoVO(Object distintaCartaCreditoVO) {
		this.distintaCartaCreditoVO = distintaCartaCreditoVO;
	}

	public Integer getIdCfgGateway() {
		return idCfgGateway;
	}

	public void setIdCfgGateway(Integer idCfgGateway) {
		this.idCfgGateway = idCfgGateway;
	}
	
//	private Collection<IndirizzoDTO> indirizziRT;
//	private List<CondizioneDDPDTO> carrello;
//	private String id;
//	private BigDecimal commissione = new BigDecimal(0);
//	private Collection<DettaglioDTO> specificDetails;
//	
//	public BigDecimal getCommissione() {
//		return commissione;
//	}
//
//	public void setCommissione(BigDecimal commissione) {
//		this.commissione = commissione;
//	}
//
//	public Collection<DettaglioDTO> getSpecificDetails() {
//		return specificDetails;
//	}
//
//	public void setSpecificDetails(Collection<DettaglioDTO> dettaglio) {
//		this.specificDetails = dettaglio;
//	}
//	
//	public void setSpecificDetail(DettaglioDTO dettaglio) {
//		
//		this.specificDetails = new ArrayList<DettaglioDTO>();
//		
//		this.specificDetails.add(dettaglio);
//	}
//	
//	public Collection<IndirizzoDTO> getIndirizziRT() {
//		return indirizziRT;
//	}
//	public void setIndirizziRT(Collection<IndirizzoDTO> indirizziRT) {
//		this.indirizziRT = indirizziRT;
//	}
//	public List<CondizioneDDPDTO> getCarrello() {
//		return carrello;
//	}
//	public void setCarrello(List<CondizioneDDPDTO> carrello) {
//		this.carrello = carrello;
//	}
//
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	
//	public String toString(){
//		   StringBuffer sb = new StringBuffer();
//		   sb.append("\n]\n");
//		   sb.append("id="+this.getId());
//	       sb.append("\nindirizzoVO="+this.getIndirizziRT().toString());
//	       sb.append("\ncarrello="+this.getCarrello().toString());
//	       sb.append("\ncommissione="+this.getCommissione());
//	       sb.append("\n"+this.getClass()+"\n");
//		   sb.append("[");
//	       if(specificDetails != null)
//	    	   sb.append(specificDetails.toString());
//	       return sb.toString();
//	    
//	}

}
