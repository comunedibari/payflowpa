package it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni;

public class SegnalazioniFilterDto {
	private String classificazioneCompletezza;
	private String codiceIuv;
	private boolean codiceIuvEnabled;
	private String codiceIuf;
	private boolean codiceIufEnabled;
	private String codiceIud;
	private boolean codiceIudEnabled;
	private String codFedUserId;
	private String dataDa;
	private String dataA;
	private Boolean attivi;
	private Boolean nascosti; 
	private int pageNum; // impostazioni di dft
	private int pageSize; // impostazioni di dft

	public SegnalazioniFilterDto(int pageNum, int pageSize) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public String getClassificazioneCompletezza() {
		return classificazioneCompletezza;
	}

	public void setClassificazioneCompletezza(String classificazioneCompletezza) {
		this.classificazioneCompletezza = classificazioneCompletezza;
	}

	public String getCodiceIuv() {
		return codiceIuv;
	}

	public void setCodiceIuv(String codiceIuv) {
		this.codiceIuv = codiceIuv;
	}

	public String getCodiceIuf() {
		return codiceIuf;
	}

	public void setCodiceIuf(String codiceIuf) {
		this.codiceIuf = codiceIuf;
	}

	public String getCodiceIud() {
		return codiceIud;
	}

	public void setCodiceIud(String codiceIud) {
		this.codiceIud = codiceIud;
	}

	public String getCodFedUserId() {
		return codFedUserId;
	}

	public void setCodFedUserId(String codFedUserId) {
		this.codFedUserId = codFedUserId;
	}

	public Boolean getAttivi() {
		return attivi;
	}

	public void setAttivi(Boolean attivi) {
		this.attivi = attivi;
	}

	public Boolean getNascosti() {
		return nascosti;
	}

	public void setNascosti(Boolean nascosti) {
		this.nascosti = nascosti;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getDataDa() {
		return dataDa;
	}

	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}

	public String getDataA() {
		return dataA;
	}

	public void setDataA(String dataA) {
		this.dataA = dataA;
	}

	public boolean isCodiceIuvEnabled() {
		return codiceIuvEnabled;
	}

	public void setCodiceIuvEnabled(boolean codiceIuvEnabled) {
		this.codiceIuvEnabled = codiceIuvEnabled;
	}

	public boolean isCodiceIufEnabled() {
		return codiceIufEnabled;
	}

	public void setCodiceIufEnabled(boolean codiceIufEnabled) {
		this.codiceIufEnabled = codiceIufEnabled;
	}

	public boolean isCodiceIudEnabled() {
		return codiceIudEnabled;
	}

	public void setCodiceIudEnabled(boolean codiceIudEnabled) {
		this.codiceIudEnabled = codiceIudEnabled;
	}


}
