/**
 * 
 */
package it.tasgroup.iris.dto.ddp;

import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.report.PrintableDocument;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author PazziK
 *
 */
public class DocumentoDiPagamentoDTO implements Serializable, PrintableDocument{
	
	private static final long serialVersionUID = 1L;
	
	private List<IndirizzoDTO> indirizziRT;
	private List<CondizioneDDPDTO> carrello;
	private List<DettaglioDTO> specificDetails;
	private int numPagamenti;
	private BigDecimal importo;
	private BigDecimal importoCommissioni;
	private BigDecimal importoTotale;
	private DocumentoRepositoryDTO documentoRepository;
	private Timestamp dataPagamento; 
	private Date dtScadenzaDoc; 
	private boolean pagamentoOffline;
	private String nomeFile;
	private String codiceTransazione;
	private String iuv;
	private String nomeIstitutoAttestante;
	private String idIstitutoAttestante;
	private String descrizioneIdIstitutoAttestante;
	private String intestatario;
	private String riferimento;
	private String riscossore;

	private Boolean ndp_1_7 = false;

	private EnumTipoDDP tipoDocumento;

	
	public String getId() {
		return getSpecificDetail().getId();
	}
	
	public void setId(String id) {
		getSpecificDetail().setId(id);
	}
	
	public String getCfIntestatarioPendenza() {
		return getSpecificDetail().getCfIntestatarioPendenza();
	}
	
	public void setCfIntestatarioPendenza(String cfIntestatarioPendenza) {
		getSpecificDetail().setCfIntestatarioPendenza(cfIntestatarioPendenza);
	}
	
	public String getCfPagante() {
		return getSpecificDetail().getCfPagante();
	}
	
	public void setCfPagante(String cfPagante) {
		getSpecificDetail().setCfPagante(cfPagante);
	}
	
	public void setEmailPagante(String emailPagante) {
		getSpecificDetail().setEmailPagante(emailPagante);
	}

	public List<DettaglioDTO> getSpecificDetails() {
		return specificDetails;
	}

	public void setSpecificDetails(List<DettaglioDTO> dettaglio) {
		this.specificDetails = dettaglio;
	}
	
//	public void setSpecificDetail(DettaglioDTO dettaglio) {
//		
//		this.specificDetails = new ArrayList<DettaglioDTO>();
//		
//		this.specificDetails.add(dettaglio);
//	}
	
	public Collection<IndirizzoDTO> getIndirizziRT() {
		return indirizziRT;
	}
	public void setIndirizziRT(List<IndirizzoDTO> indirizziRT) {
		this.indirizziRT = indirizziRT;
	}
	public List<CondizioneDDPDTO> getCarrello() {
		return carrello;
	}
	public void setCarrello(List<CondizioneDDPDTO> carrello) {
		this.carrello = carrello;
	}
	
	public DettaglioDTO getSpecificDetail(){
		return getSpecificDetails().get(0);
	}
	
	public String toString(){
		   StringBuffer sb = new StringBuffer();
		   sb.append("\n]\n");
		   sb.append("id="+this.getId());
	       sb.append("\nindirizzoVO="+this.getIndirizziRT());
	       sb.append("\ncarrello="+this.getCarrello().toString());
	       sb.append("\nimporto commissioni="+this.getImportoCommissioni());
	       sb.append("\nimporto="+this.getImporto());
	       sb.append("\ndataPagamento="+this.getDataPagamento());
	       sb.append("\n"+this.getClass()+"\n");
		   sb.append("[");
	       if(specificDetails != null)
	    	   sb.append(specificDetails.toString());
	       return sb.toString();
	    
	}

	@Override
	public Boolean needWatermark() {
		return ((PrintableDocument)getSpecificDetail()).needWatermark();
	}

	@Override
	public String getWatermarkText(ResourceBundle bundle) {
		return ((PrintableDocument)getSpecificDetail()).getWatermarkText(bundle);
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

	public BigDecimal getImportoTotale() {
		return importoTotale;
	}

	public void setImportoTotale(BigDecimal importoTotale) {
		this.importoTotale = importoTotale;
	}

	public DocumentoRepositoryDTO getDocumentoRepository() {
		return documentoRepository;
	}

	public void setDocumentoRepository(DocumentoRepositoryDTO documentoRepository) {
		this.documentoRepository = documentoRepository;
	}

	public Timestamp getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDtScadenzaDoc() {
		return dtScadenzaDoc;
	}

	public void setDtScadenzaDoc(Date dtScadenzaDoc) {
		this.dtScadenzaDoc = dtScadenzaDoc;
	}
	
	public String getDtScadenzaDocFormatted() {
		String reportDate = null;
		if (dtScadenzaDoc != null) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			reportDate = df.format(dtScadenzaDoc);
		}
		return reportDate;
	}

	
	public boolean isPagamentoOffline() {
		return pagamentoOffline;
	}
	public boolean getPagamentoOffline() {
		return pagamentoOffline;
	}

	public void setPagamentoOffline(boolean pagamentoOffline) {
		this.pagamentoOffline = pagamentoOffline;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public int getNumPagamenti() {
		return numPagamenti;
	}

	public void setNumPagamenti(int numPagamenti) {
		this.numPagamenti = numPagamenti;
	}

	public String getCodiceTransazione() {
		return codiceTransazione;
	}

	public void setCodiceTransazione(String codiceTransazione) {
		this.codiceTransazione = codiceTransazione;
	}

	public String getNomeIstitutoAttestante() {
		return nomeIstitutoAttestante;
	}

	public void setNomeIstitutoAttestante(String nomeIstitutoAttestante) {
		this.nomeIstitutoAttestante = nomeIstitutoAttestante;
	}

	public String getIdIstitutoAttestante() {
		return idIstitutoAttestante;
	}

	public void setIdIstitutoAttestante(String idIstitutoAttestante) {
		this.idIstitutoAttestante = idIstitutoAttestante;
	}

	public String getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}


	public boolean isNdp_1_7() {
		return ndp_1_7;
	}

	public void setNdp_1_7(boolean ndp_1_7) {
		this.ndp_1_7 = ndp_1_7;
	}


	public EnumTipoDDP getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(EnumTipoDDP tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public String getRiscossore() {
		return riscossore;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}

	public String getDescrizioneIdIstitutoAttestante() {
		return descrizioneIdIstitutoAttestante;
	}

	public void setDescrizioneIdIstitutoAttestante(String descrizioneIdIstitutoAttestante) {
		this.descrizioneIdIstitutoAttestante = descrizioneIdIstitutoAttestante;
	}

	
}
