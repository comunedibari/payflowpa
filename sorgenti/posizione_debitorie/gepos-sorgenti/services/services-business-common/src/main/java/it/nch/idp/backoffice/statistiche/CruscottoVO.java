package it.nch.idp.backoffice.statistiche;

import java.io.Serializable;
import java.math.BigDecimal;


public class CruscottoVO implements Serializable {
	

	//	private static DateFormatSymbols dfs = DateFormatSymbols(locale);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int  mese;
	private Integer  occorrenze;
	private BigDecimal importo;
	private boolean isNotEmpty;
	
	
	public Integer getOccorrenze() {
		return occorrenze;
	}
	public void setOccorrenze(Integer occorrenze) {
		this.occorrenze = occorrenze;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}

	public boolean isNotEmpty() {
		return isNotEmpty;
	}
	public void setNotEmpty(boolean isNotEmpty) {
		this.isNotEmpty = isNotEmpty;
	}

	public static CruscottoVO build(Object[] row) {
		CruscottoVO item = new CruscottoVO();
//		new DateFormatSymbols().getMonths()[month-1];
		item.setMese((Integer) row[0]);
		item.setOccorrenze(((Long) row[1]).intValue());
		item.setImporto(((BigDecimal) row[2]));
		return item;
	}
	
	public static CruscottoVO buildEmpty(Integer m) {
		CruscottoVO item = new CruscottoVO();
		item.setMese(m);
		return item;
	}
	
}
