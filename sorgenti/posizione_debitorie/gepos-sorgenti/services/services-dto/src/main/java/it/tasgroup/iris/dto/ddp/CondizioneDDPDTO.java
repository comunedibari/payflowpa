/*
 * Created on 5-giu-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.tasgroup.iris.dto.ddp;

import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author PazziK
 * 
 */
public class CondizioneDDPDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date scadenza;
	private BigDecimal importo;
	
	private String tributo;
	private String cdTrbEnte;
	private String descrizioneFreccia;
	private String causale;
	private String causale_condizioni;
	private Map<String, String> map_causale;
	private String note;
	private String hashMBD;
	
	private List<String> debitori;
	private List<IntestatarioDTO> debitoriDTO;
	private String idCondizione;
	private String idPagamento;
	private Date tsDecorrenzaPagamento;

	private boolean allegatoRicevuta;
	private boolean pagamentoPredeterminato;
	private List<ChiaveValoreDTO> vociPagamento;
	private String idRiscossionePSP;
	private String codiceFiscaleCreditore; // intestatari_lapl intestatari ente
	private String riscossore;
	private String riferimento;
	
	private EnteDTO ente;
	
	private ArrayList<IntestatarioDTO> intestatari;
	
	public String getDescrizioneFreccia() {
		return descrizioneFreccia;
	}

	public void setDescrizioneFreccia(String descrizioneFreccia) {
		this.descrizioneFreccia = descrizioneFreccia;
	}

	public Date getScadenza() {
		return scadenza;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getTributo() {
		return tributo;
	}

	public void setTributo(String tributo) {
		this.tributo = tributo;
	}

	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("\n" + this.getClass() + "\n");
		sb.append("[");
		sb.append("Ente=" + this.getEnte());
		sb.append(", Tributo=" + this.getTributo());
		sb.append(", Importo=" + this.getImporto());
		sb.append(", scadenza=" + this.getScadenza());
		sb.append(", causale=" + this.getCausale());
		sb.append(", note=" + this.getNote());
		sb.append("]\n");
		return sb.toString();
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getHashMBD() {
		return hashMBD;
	}

	public void setHashMBD(String hashMBD) {
		this.hashMBD = hashMBD;
	}

	public Map<String, String> getMap_causale() {
		return map_causale;
	}

	public void setMap_causale(Map<String, String> map_causale) {
		this.map_causale = map_causale;
	}

	public List<String> getDebitori() {
		return debitori;
	}

	public void setDebitori(List<String> debitori) {
		this.debitori = debitori;
	}
	
	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public IndirizzoDTO getIndirizzoEnte() {
		return ente.getIndirizzoDTO();
	}

	public Date getTsDecorrenzaPagamento() {
		return tsDecorrenzaPagamento;
	}

	public void setTsDecorrenzaPagamento(Date tsDecorrenzaPagamento) {
		this.tsDecorrenzaPagamento = tsDecorrenzaPagamento;
	}

	public String getCausale_condizioni() {
		return causale_condizioni;
	}

	public void setCausale_condizioni(String causale_condizioni) {
		this.causale_condizioni = causale_condizioni;
	}

	public boolean isAllegatoRicevuta() {
		return allegatoRicevuta;
	}

	public void setAllegatoRicevuta(boolean allegatoRicevuta) {
		this.allegatoRicevuta = allegatoRicevuta;
	}

	public String getDebitori_formattato() {
		StringBuilder sb = new StringBuilder();
		if(debitori != null) {
			
			int i = 1;
			for (String debitore : debitori) {
				sb.append(debitore);
				if(i++ < debitori.size())
					sb.append(" - "); //.append("\n");
			}
		}
		return sb.toString();
	}
	
	public String getCausaleNDP() {
		
		String res = "";
		int rows = 0, maxRows = 6, maxLen = 80, maxChars = 140;
		// tributo
		if (tributo != null && tributo.length() > 0) {
			res += tributo;
			rows ++;
		}
		// if causale strutturata -> prime 5 righe ciascuna max maxLen
		if (map_causale != null) {
			Set<String> chiavi = map_causale.keySet();
			Iterator<String> it = chiavi.iterator();
			while (it.hasNext() && rows < maxRows) {
				String chiave = it.next();
				String valore = map_causale.get(chiave);
				String tmp = (!chiave.matches("\\s*") ? chiave + ": " : "")
						+ (valore != null ? valore : "");
				res += "\n" + (tmp.length() > maxLen ? tmp.substring(0, maxLen) : tmp);
				rows ++;
			}
		} else {
			// max 140
			if (causale != null && causale.length() > 0) {
				res += " - " + causale;
				if (res.length() > maxChars)
					res = res.substring(0, maxChars);
			}
		}
		return res;

	}
	
	public String getCausaleNDPAvviso21() {
		
		String res = "CAUSALE: ";

		// if causale strutturata 
		if (map_causale != null) {
			Set<String> chiavi = map_causale.keySet();
			Iterator<String> it = chiavi.iterator();
			while (it.hasNext()) {
				res += ", ";
				String chiave = it.next();
				String valore = map_causale.get(chiave);
				String tmp = (!chiave.matches("\\s*") ? chiave + ": " : "")
						+ (valore != null ? valore : "");
				res += tmp;
			}
			res = res.replaceFirst(", ", "");
		} else {
			// causale non strutturata
			if (causale != null && causale.length() > 0) {
				res +=causale;
			}
		}

		return res;

	}
	
	public String getCausale_formattata() {

		StringBuilder sb = new StringBuilder();
		// tributo
		if (tributo != null && tributo.length() > 0) {
			sb.append(tributo);
		}
		// causale
		if (map_causale != null) {
			for (String chiave : map_causale.keySet()) {
				sb.append("\n");
				sb.append(!chiave.matches("\\s*") ? chiave + ": " : "");
				String valore = map_causale.get(chiave);
				if (valore != null && valore.length() > 0) {
					sb.append(valore);
				}
			}
		} else if (causale != null && causale.length() > 0) {
			sb.append("\n").append(causale);
		}
		// note
		if (note != null && note.length() > 0) {
			sb.append("\n").append(note);
		}
		// causale condizioni (per cartelle pagamento)
		if (causale_condizioni != null && causale_condizioni.length() > 0) {
			sb.append("\n").append(causale_condizioni);
		}

		return sb.toString();
	}
	
	public List<ChiaveValoreDTO> getListaRigheCausale() {
		List<ChiaveValoreDTO> righe = new ArrayList<ChiaveValoreDTO>();

		// causale
		if (map_causale != null) {
			for (String chiave : map_causale.keySet()) {
				righe.add(new ChiaveValoreDTO(chiave,  map_causale.get(chiave)));
			}
		} else if (causale != null && causale.length() > 0) {
			righe.add(new ChiaveValoreDTO("",  causale));
		}
		// hash marca da bollo digitale
		if (hashMBD != null) {
			righe.add(new ChiaveValoreDTO("Hash Documento", hashMBD));
		}
		// note
		if (note != null && note.length() > 0) {
			righe.add(new ChiaveValoreDTO("",  note));
		}
		// causale condizioni (per cartelle pagamento)
		if (causale_condizioni != null && causale_condizioni.length() > 0) {
			righe.add(new ChiaveValoreDTO("",  causale_condizioni));
		}
		
		return righe;
		
	}

	public String getAnagraficaEnte_formattata() {
		StringBuilder sb = new StringBuilder();
		if(ente.getDenominazione() != null) {
			sb.append("<b>").append(ente.getDenominazione()).append("</b><br/>");
		}
		
		IndirizzoDTO indirizzoEnte = ente.getIndirizzoDTO(); 
		
		if (indirizzoEnte != null) {
			sb.append(indirizzoEnte.getVia());
			if(indirizzoEnte.getNumeroCivico() != null && indirizzoEnte.getNumeroCivico().length() > 0) {
				sb.append(" ").append(indirizzoEnte.getNumeroCivico());
			}
			sb.append("<br/>");
			sb.append(indirizzoEnte.getCap()).append(" ").append(indirizzoEnte.getCitta());
			sb.append(" (").append(indirizzoEnte.getProvincia()).append(")");
			
			if (ente.getCodice().equals("ASL8Arezzo")) {
				/* TODO da eliminare ad avvenuta migrazione di ASL 8 (#2886)*/
				sb.append("<br/>Codice Fiscale: ").append(indirizzoEnte.getPiva());
			} else {
				sb.append("<br/>Codice Fiscale: ").append(indirizzoEnte.getCodiceFiscale());
			}
		}
		return sb.toString();
	}

	public List<ChiaveValoreDTO> getVociPagamento() {
		return vociPagamento;
	}

	public void setVociPagamento(List<ChiaveValoreDTO> vociPagamento) {
		this.vociPagamento = vociPagamento;
	}

	public boolean isPagamentoPredeterminato() {
		return pagamentoPredeterminato;
	}

	public void setPagamentoPredeterminato(boolean pagamentoPredeterminato) {
		this.pagamentoPredeterminato = pagamentoPredeterminato;
	}

	public String getIdRiscossionePSP() {
		return idRiscossionePSP;
	}

	public void setIdRiscossionePSP(String idRiscossionePSP) {
		this.idRiscossionePSP = idRiscossionePSP;
	}

	public String getCodiceFiscaleCreditore() {
		return codiceFiscaleCreditore;
	}

	public void setCodiceFiscaleCreditore(String codiceFiscaleCreditore) {
		this.codiceFiscaleCreditore = codiceFiscaleCreditore;
	}

	public ArrayList<IntestatarioDTO> getIntestatari() {
		return intestatari;
	}

	public void setIntestatari(ArrayList<IntestatarioDTO> intestatari) {
		this.intestatari = intestatari;
	}

	public EnteDTO getEnte() {
		return ente;
	}

	public void setEnte(EnteDTO ente) {
		this.ente = ente;
	}

	public String getRiscossore() {
		return riscossore;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	
	public String getRiscossore_formattato() {
		if(riscossore == null && riferimento == null)
			return null;
		else {
			return (riscossore != null ? riscossore :  " - ") + 
					"/" + 
					(riferimento != null ? riferimento :  " - ");
		}
	}

	public List<IntestatarioDTO> getDebitoriDTO() {
		return debitoriDTO;
	}

	public void setDebitoriDTO(List<IntestatarioDTO> debitoriDTO) {
		this.debitoriDTO = debitoriDTO;
	}

	public String getCdTrbEnte() {
		return cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}
	
	
}
