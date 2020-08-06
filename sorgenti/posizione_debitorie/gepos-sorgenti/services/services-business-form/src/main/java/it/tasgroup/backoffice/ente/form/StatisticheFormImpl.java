/**
 *
 */
package it.tasgroup.backoffice.ente.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.validator.ValidatorForm;

import it.nch.idp.backoffice.statistiche.RiepilogoCruscottoVO;
import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.nch.is.fo.common.CodiceDescrizioneVO;

public class StatisticheFormImpl extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String idEnte;
	private String denominazioneEnte;
	private HashMap<Integer, Integer> listaAnnoRiferimento;
	private HashMap<Integer, String>  listaMesiRiferimento;
	private Collection<CodiceDescrizioneVO> listaTributo;
	private Collection<CodiceDescrizioneVO> listaSIL;
	private Collection<CodiceDescrizioneVO> listaTipoPagamento;
	
	private Integer filtroAnnoRiferimento = ANNO_DEFAULT;
	private int filtroMeseRiferimento;
	private String filtroTipoTributo;
	private String filtroTipoPagamento;
	private String filtroSil;


	List<RiepilogoCruscottoVO> riepilogo;
	LinkedHashMap<String, ArrayList<RiepilogoStatisticheVO>> riepilogoPagamentiCircuito;
	List<RiepilogoStatisticheVO> riepilogoPagamenti;
	List<RiepilogoStatisticheVO> riepilogoPosizioni;
	
	private static Integer ANNO_DEFAULT = init();
	
	public List<RiepilogoStatisticheVO> getRiepilogoPosizioni() {
		return riepilogoPosizioni;
	}


	public void setRiepilogoPosizioni(List<RiepilogoStatisticheVO> riepilogoPosizioni) {
		this.riepilogoPosizioni = riepilogoPosizioni;
	}


	public List<RiepilogoStatisticheVO> getRiepilogoPagamenti() {
		return riepilogoPagamenti;
	}


	public void setRiepilogoPagamenti(List<RiepilogoStatisticheVO> riepilogoPagamenti) {
		this.riepilogoPagamenti = riepilogoPagamenti;
	}


	public LinkedHashMap<String, ArrayList<RiepilogoStatisticheVO>> getRiepilogoPagamentiCircuito() {
		return riepilogoPagamentiCircuito;
	}


	public void setRiepilogoPagamentiCircuito(
			LinkedHashMap<String, ArrayList<RiepilogoStatisticheVO>> riepilogoPagamentiCircuito) {
		this.riepilogoPagamentiCircuito = riepilogoPagamentiCircuito;
	}


	public String getFiltroTipoPagamento() {
		return filtroTipoPagamento;
	}


	public void setFiltroTipoPagamento(String filtroTipoPagamento) {
		this.filtroTipoPagamento = filtroTipoPagamento;
	}


	public Collection<CodiceDescrizioneVO> getListaTipoPagamento() {
		return listaTipoPagamento;
	}


	public void setListaTipoPagamento(Collection<CodiceDescrizioneVO> listaTipoPagamento) {
		this.listaTipoPagamento = listaTipoPagamento;
	}


	private static Integer init() {
		return new GregorianCalendar().get(GregorianCalendar.YEAR);
	}
	
	
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getDenominazioneEnte() {
		return denominazioneEnte;
	}
	public void setDenominazioneEnte(String denominazioneEnte) {
		this.denominazioneEnte = denominazioneEnte;
	}
	public HashMap<Integer, Integer> getListaAnnoRiferimento() {
		return listaAnnoRiferimento;
	}
	public void setListaAnnoRiferimento(HashMap<Integer, Integer> listaAnnoRiferimento) {
		this.listaAnnoRiferimento = listaAnnoRiferimento;
	}
	public HashMap<Integer, String> getListaMesiRiferimento() {
		return listaMesiRiferimento;
	}
	public void setListaMesiRiferimento(HashMap<Integer, String> listaMesiRiferimento) {
		this.listaMesiRiferimento = listaMesiRiferimento;
	}
	public Integer getFiltroAnnoRiferimento() {
		return filtroAnnoRiferimento;
	}
	public void setFiltroAnnoRiferimento(Integer filtroAnnoRiferimento) {
		this.filtroAnnoRiferimento = filtroAnnoRiferimento;
	}
	public int getFiltroMeseRiferimento() {
		return filtroMeseRiferimento;
	}
	public void setFiltroMeseRiferimento(int filtroMeseRiferimento) {
		this.filtroMeseRiferimento = filtroMeseRiferimento;
	}
	public List<RiepilogoCruscottoVO> getRiepilogo() {
		return riepilogo;
	}
	public void setRiepilogo(List<RiepilogoCruscottoVO> riepilogo) {
		this.riepilogo = riepilogo;
	}
	public Collection<CodiceDescrizioneVO> getListaTributo() {
		return listaTributo;
	}
	public void setListaTributo(Collection<CodiceDescrizioneVO> listaTributo) {
		this.listaTributo = listaTributo;
	}


	public Collection<CodiceDescrizioneVO> getListaSIL() {
		return listaSIL;
	}


	public void setListaSIL(Collection<CodiceDescrizioneVO> listaSIL) {
		this.listaSIL = listaSIL;
	}

	public String getFiltroTipoTributo() {
		return filtroTipoTributo;
	}


	public void setFiltroTipoTributo(String filtroTipoTributo) {
		this.filtroTipoTributo = filtroTipoTributo;
	}


	public String getFiltroSil() {
		return filtroSil;
	}


	public void setFiltroSil(String filtroSil) {
		this.filtroSil = filtroSil;
	}
	
	private String getDescrizioneFiltro(String codice, Collection<CodiceDescrizioneVO> filtro) {
		for (CodiceDescrizioneVO elem : filtro) {
	        if (elem.getCodice().equals(codice)) {
	            return elem.getDescrizione();
	        }
	    }
		return codice;
	}
	
	public String getResultsTitle() {
		
		return " " + 
				(filtroMeseRiferimento != 0 ? listaMesiRiferimento.get(filtroMeseRiferimento) + " ": "") +
				filtroAnnoRiferimento + "";
	}
	
	public String getResultsFiltersTitle() {
		String res = "";
		
		if(StringUtils.isNotEmpty(filtroTipoTributo))  {
			res += "<b>Tipo Debito:</b> " +  getDescrizioneFiltro(filtroTipoTributo, listaTributo);
		}
		
		if(StringUtils.isNotEmpty(filtroSil))  {
			if(StringUtils.isNotEmpty(res))
				res += " -- ";
			res += "<b>SIL:</b> " + getDescrizioneFiltro(filtroSil, listaSIL);
		}
		
		if(StringUtils.isNotEmpty(filtroTipoPagamento)) {
			if(StringUtils.isNotEmpty(res))
				res += " -- ";
			res = "<b>Tipo pagamento:</b> " + getDescrizioneFiltro(filtroTipoPagamento, listaTipoPagamento);
		}
		
		return res;
	}
	
	public void setResultsTitle(String resultsTitle) {
		// do nothing
	}
	
	public void reset() {
		filtroMeseRiferimento=0;
		filtroTipoTributo="";
		filtroTipoPagamento="";
		filtroSil="";
	}
}
