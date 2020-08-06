package it.regioneveneto.mygov.payment.mypivot.domain.dto;

/**
 * 
 * @author Cristiano Perin
 *
 */

public class FlussoExportDto extends FlussoDto {

	private String dimensione;

	private boolean showDownloadButton;

	private String dataPrenotazione;

	private String path;

	private String classificazione;
	
	private String versioneTracciato;

	public FlussoExportDto() {
		super();
	}

	public String getDimensione() {
		return dimensione;
	}

	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}

	public boolean isShowDownloadButton() {
		return showDownloadButton;
	}

	public void setShowDownloadButton(boolean showDownloadButton) {
		this.showDownloadButton = showDownloadButton;
	}

	public String getDataPrenotazione() {
		return dataPrenotazione;
	}

	public void setDataPrenotazione(String dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public String getVersioneTracciato() {
		return versioneTracciato;
	}

	public void setVersioneTracciato(String versioneTracciato) {
		this.versioneTracciato = versioneTracciato;
	}

}
