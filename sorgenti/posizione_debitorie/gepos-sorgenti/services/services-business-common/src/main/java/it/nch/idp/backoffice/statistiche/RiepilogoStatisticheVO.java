package it.nch.idp.backoffice.statistiche;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class RiepilogoStatisticheVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer  mese;
	private String cdTrbEnte;
	private String sil;
	private Integer numeroOccorrenze;
	private BigDecimal importo;
	
	private String tipo;
	private String modalita;
	
	public RiepilogoStatisticheVO() {}
	
	public RiepilogoStatisticheVO(String sil, String modalita, Integer numeroOccorrenze, BigDecimal importo) {
		this.sil = sil;
		this.modalita = modalita;
		this.numeroOccorrenze = numeroOccorrenze;  
		this.importo = importo; 
	}
	
	public String getModalita() {
		return modalita;
	}
	public void setModalita(String modalita) {
		this.modalita = modalita;
	}
	public Integer getMese() {
		return mese;
	}
	public void setMese(Integer mese) {
		this.mese = mese;
	}
	public String getSil() {
		return sil;
	}
	public void setSil(String sil) {
		this.sil = sil;
	}
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}
	public Integer getNumeroOccorrenze() {
		return numeroOccorrenze;
	}
	public void setNumeroOccorrenze(Integer numeroOccorrenze) {
		this.numeroOccorrenze = numeroOccorrenze;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public static RiepilogoStatisticheVO buildTotale(Integer totNumeroOccorrenze, BigDecimal totImporto) {
		RiepilogoStatisticheVO tot = new RiepilogoStatisticheVO();
		tot.setSil("Totali");
		tot.setNumeroOccorrenze(totNumeroOccorrenze);
		tot.setImporto(totImporto);
		return tot;
	}

	public static RiepilogoStatisticheVO buildTotaleParziale(Integer totNumeroOccorrenze, BigDecimal totImporto) {
		RiepilogoStatisticheVO tot = new RiepilogoStatisticheVO();
		tot.setModalita("Totale");
		tot.setNumeroOccorrenze(totNumeroOccorrenze);
		tot.setImporto(totImporto);
		return tot;
	}
	
	public static RiepilogoStatisticheVO buildStatisticaCircuito(Object[] row) {
		RiepilogoStatisticheVO item = new RiepilogoStatisticheVO();
		item.sil = (String) row[0];
		item.modalita = ((String) row[1]);
		item.numeroOccorrenze = ((Long) row[2]).intValue();
		item.importo = (BigDecimal) row[3];
		return item;
	}

	public static RiepilogoStatisticheVO build(Object[] row) {
		RiepilogoStatisticheVO item = new RiepilogoStatisticheVO();
		item.setSil((String) row[0]); //ID_SYSTEM
		item.setCdTrbEnte((String) row[1]); //CD_TRB_ENTE
		item.setNumeroOccorrenze(((Long) row[2]).intValue()); //SUM(NUMERO_POSIZIONI)
		item.setImporto(((BigDecimal) row[3])); // SUM(IMPORTO) 
		return item;
	}
	
	public void setNumeroOccorrenzeFormatted(String value) {
//		do nothing.
	}
	
	public String getNumeroOccorrenzeFormatted() {
		return NumberFormat.getNumberInstance(Locale.ITALIAN).format(getNumeroOccorrenze());
	}

	public void setImportoFormatted(String value) {
//		do nothing.
	}

	public String getImportoFormatted() {
		NumberFormat df = NumberFormat.getNumberInstance(Locale.ITALIAN);
		df.setMinimumFractionDigits(2);
		return df.format(getImporto()) + "&nbsp;&euro;";
	}
	
}


