package it.nch.idp.posizionedebitoria;

import it.nch.erbweb.profilo.ProfiloInputVO;
import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;
import it.tasgroup.services.util.enumeration.EnumTipoExport;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PreferenzaEsportazioneVO implements Serializable
{ 
	private String mySelectCampi;
	
	private String myValoreSelezionato;
	
	private String mySelectSeparatore;
	
	private String rigaIntestazione;
	
	private String numRisultati;
	
	private EnumExportSTDFormat exportStandardType;

	private EnumDynaReportFormat export;
	
	private String tipoServizio;

	private EnumTipoExport tipoEsportazione;
	
	private ProfiloInputVO profilo;
	
	private Map<String, String> mapListaCampi = new HashMap<String, String>();
	
	protected Collection<CodiceDescrizioneVO> listaCampi;
	
	protected Collection listaSeparatori;

	public Collection getListaSeparatori() {
		return listaSeparatori;
	}
	
	public void setListaSeparatori(Collection listaSeparatori) {
		this.listaSeparatori = listaSeparatori;
	}
	
	public Collection<CodiceDescrizioneVO> getListaCampi() {
		return listaCampi;
	}
	
	public void setListaCampi(Collection<CodiceDescrizioneVO> listaCampi) {
		this.listaCampi = listaCampi;
	}
	
	public String getMySelectCampi() {
		return mySelectCampi;
	}
	
	public void setMySelectCampi(String mySelectCampi) {
		this.mySelectCampi = mySelectCampi;
	}
	
	public String getMyvaloreselezionato() {
		return myValoreSelezionato;
	}
	
	public void setMyvaloreselezionato(String myvaloreselezionato) {
		this.myValoreSelezionato = myvaloreselezionato;
	}
	
	public String getMySelectSeparatore() {
		return mySelectSeparatore;
	}
	
	public void setMySelectSeparatore(String mySelectSeparatore) {
		this.mySelectSeparatore = mySelectSeparatore;
	}
	
	public String getRigaIntestazione() {
		return rigaIntestazione;
	}
	
	public void setRigaIntestazione(String rigaIntestazione) {
		this.rigaIntestazione = rigaIntestazione;
	}
	
	public EnumDynaReportFormat getExport() {
		return export;
	}
	
	public void setExport(EnumDynaReportFormat export) {
		this.export = export;
	}
	
	public String getTipoServizio() {
		return tipoServizio;
	}
	
	public void setTipoServizio(String tipoServizio) {
		this.tipoServizio = tipoServizio;
	}
	
	public Map<String, String> getMapListaCampi() {
		return mapListaCampi;
	}
	
	public void setMapListaCampi(Map<String, String> mapListaCampi) {
		this.mapListaCampi = mapListaCampi;
	}
	
	public String getNumRisultati() {
		return numRisultati;
	}
	
	public void setNumRisultati(String numRisultati) {
		this.numRisultati = numRisultati;
	}
	
	public EnumTipoExport getTipoEsportazione() {
		return tipoEsportazione;
	}
	
	public void setTipoEsportazione(EnumTipoExport tipoEsportazione) {
		this.tipoEsportazione = tipoEsportazione;
	}
	
	public ProfiloInputVO getProfilo() {
		return profilo;
	}
	
	public void setProfilo(ProfiloInputVO profilo) {
		this.profilo = profilo;
	}
	
	public boolean listaCampiContains(String codice){
		
		for(CodiceDescrizioneVO vo: listaCampi){
			
			if (codice.equals(vo.getCodice()))
				return true;
		}
		
		return false;
	}

	public EnumExportSTDFormat getExportStandardType() {
		return exportStandardType;
	}

	public void setExportStandardType(String exportStandardType) {
		this.exportStandardType = EnumExportSTDFormat.valueOf(exportStandardType);
	}

	
	
}
