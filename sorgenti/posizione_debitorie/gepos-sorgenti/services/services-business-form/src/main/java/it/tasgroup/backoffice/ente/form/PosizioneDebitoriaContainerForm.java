package it.tasgroup.backoffice.ente.form;

import it.nch.erbweb.common.PreferenzeEsportazioneForm;
import it.nch.idp.posizionedebitoria.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PosizioneDebitoriaContainerForm extends PreferenzeEsportazioneForm {

	private static final long serialVersionUID = 1L;

	private AvvisoPosizioneDebitoriaVO avvisoVO = new AvvisoPosizioneDebitoriaVO();
	private DistintaPosizioneDebitoriaVO distintaVO = new DistintaPosizioneDebitoriaVO();
	private RicercaAvvisiPosizioneDebitoriaForm ricercaAvvisiForm = new RicercaAvvisiPosizioneDebitoriaForm();
	private RicercaDistintePosizioneDebitoriaForm ricercaDistinteForm = new RicercaDistintePosizioneDebitoriaForm();
	private String filtroEnte;
	private String filtroTipoTributo;
	private String filtroEnteTributo;
	private String filtroStato;
	private String filtroModPagamento;
	private String filtroCodPagamento;
	private String filtroIncludiPagamentiInErrore;
	private String idPagamento;
	
	private String filtroDataDaGG;
	private String filtroDataDaMM;
	private String filtroDataDaYY;
	private String filtroDataAGG;
	private String filtroDataAMM;
	private String filtroDataAYY;
	
	private String filtroAnnoRiferimento;
	
	private String filtroIUV;
	private String riscossore;
	private String riferimento;

	private Collection<EntePosizioneDebitoriaVO> listaEnti;
	private Map<String, Map<String, String>> listaEntiMap = new HashMap<String, Map<String, String>>();
	private Collection listaTipoTributo;
	private Collection listaTributo;
	private Collection listaStati;
	private Collection listaModalitaPag;
	private List<EnteTributoPosizioneDebitoriaVO> listaEntiTributi;
	
	public static String tributiEnteLabel = "Tributi Ente";
	public static String tributiAmmessiLabel = "Tributi Ammessi";
	
	public String getFiltroStato() {
		return filtroStato;
	}

	public void setFiltroStato(String filtroStato) {
		this.filtroStato = filtroStato;
	}

	// CARRELLO
	private String commandSent;
	private String idCondizione;
	private String idPendenza;

	public String getCommandSent() {
		return commandSent;
	}

	public void setCommandSent(String commandSent) {
		this.commandSent = commandSent;
	}

	public PosizioneDebitoriaContainerForm() {
	}

	/**
	 * @return
	 */
	public AvvisoPosizioneDebitoriaVO getAvvisoVO() {
		return avvisoVO;
	}

	/**
	 * @return
	 */
	public RicercaAvvisiPosizioneDebitoriaForm getRicercaAvvisiForm() {
		return ricercaAvvisiForm;
	}

	/**
	 * @param debitoriaVO
	 */
	public void setAvvisoVO(AvvisoPosizioneDebitoriaVO debitoriaVO) {
		avvisoVO = debitoriaVO;
	}

	/**
	 * @param form
	 */
	public void setRicercaAvvisiForm(RicercaAvvisiPosizioneDebitoriaForm form) {
		ricercaAvvisiForm = form;
	}

	/**
	 * @return
	 */
	public String getFiltroEnte() {
		return filtroEnte;
	}

	/**
	 * @param string
	 */
	public void setFiltroEnte(String string) {
		filtroEnte = string;
	}

	public Collection getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(Collection collection) {	
		listaEnti = collection;
	}

	public Collection getListaTipoTributo() {
		return listaTipoTributo;
	}

	public void setListaTipoTributo(Collection collection) {
		listaTipoTributo = collection;
	}

	public void setListaStati(Collection collection) {
		listaStati = collection;
	}

	public Collection getListaStati() {
		return listaStati;
	}

	public Collection getListaTributo() {
		return listaTributo;
	}

	public Collection getListaModalitaPag() {
		return listaModalitaPag;
	}

	public void setListaModalitaPag(Collection listaModalitaPag) {
		this.listaModalitaPag = listaModalitaPag;
	}

	public void setListaTributo(Collection collection) {
		listaTributo = collection;
	}

	public String getFiltroModPagamento() {
		return filtroModPagamento;
	}

	public void setFiltroModPagamento(String filtroModPagamento) {
		this.filtroModPagamento = filtroModPagamento;
	}

	public String getFiltroCodPagamento() {
		return filtroCodPagamento;
	}

	public void setFiltroCodPagamento(String filtroCodPagamento) {
		this.filtroCodPagamento = filtroCodPagamento;
	}

	public String getFiltroTipoTributo() {
		return filtroTipoTributo;
	}

	public void setFiltroTipoTributo(String filtroTipoTributo) {
		this.filtroTipoTributo = filtroTipoTributo;
	}

	public RicercaDistintePosizioneDebitoriaForm getRicercaDistinteForm() {
		return ricercaDistinteForm;
	}

	public void setRicercaDistinteForm(RicercaDistintePosizioneDebitoriaForm form) {
		ricercaDistinteForm = form;
	}

	public DistintaPosizioneDebitoriaVO getDistintaVO() {
		return distintaVO;
	}

	public void setDistintaVO(DistintaPosizioneDebitoriaVO debitoriaVO) {
		distintaVO = debitoriaVO;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	public String getIdCondizione() {
		return this.idCondizione;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	public String getIdPendenza() {
		return idPendenza;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getFiltroIncludiPagamentiInErrore() {
		return filtroIncludiPagamentiInErrore;
	}

	public void setFiltroIncludiPagamentiInErrore(String filtroIncludiPagamentiInErrore) {
		this.filtroIncludiPagamentiInErrore = filtroIncludiPagamentiInErrore;
	}

	public Map<String, Map<String, String>> getListaEntiMap() {
		return listaEntiMap;
	}

	public void setListaEntiMap(Map<String, Map<String, String>> listaEntiMap) {
		this.listaEntiMap = listaEntiMap;
	}

	public List<EnteTributoPosizioneDebitoriaVO> getListaEntiTributi() {
		return listaEntiTributi;
	}

	public void setListaEntiTributi(List<EnteTributoPosizioneDebitoriaVO> listaEntiTributi) {
		this.listaEntiTributi = listaEntiTributi;
	}

	public String getFiltroEnteTributo() {
		return filtroEnteTributo;
	}

	public void setFiltroEnteTributo(String filtroEnteTributo) {
		this.filtroEnteTributo = filtroEnteTributo;
	}
	
	public String getFiltroDataDaGG() {
		return filtroDataDaGG;
	}
	public void setFiltroDataDaGG(String filtroDataDaGG) {
		this.filtroDataDaGG = filtroDataDaGG;
	}
	public String getFiltroDataDaMM() {
		return filtroDataDaMM;
	}
	public void setFiltroDataDaMM(String filtroDataDaMM) {
		this.filtroDataDaMM = filtroDataDaMM;
	}
	public String getFiltroDataDaYY() {
		return filtroDataDaYY;
	}
	public void setFiltroDataDaYY(String filtroDataDaYY) {
		this.filtroDataDaYY = filtroDataDaYY;
	}
	public String getFiltroDataAGG() {
		return filtroDataAGG;
	}
	public void setFiltroDataAGG(String filtroDataAGG) {
		this.filtroDataAGG = filtroDataAGG;
	}
	public String getFiltroDataAMM() {
		return filtroDataAMM;
	}
	public void setFiltroDataAMM(String filtroDataAMM) {
		this.filtroDataAMM = filtroDataAMM;
	}
	public String getFiltroDataAYY() {
		return filtroDataAYY;
	}
	public void setFiltroDataAYY(String filtroDataAYY) {
		this.filtroDataAYY = filtroDataAYY;
	}

	public String getFiltroIUV() {
		return filtroIUV;
	}

	public void setFiltroIUV(String filtroIUV) {
		this.filtroIUV = filtroIUV;
	}

	public String getFiltroAnnoRiferimento() {
		return filtroAnnoRiferimento;
	}

	public void setFiltroAnnoRiferimento(String filtroAnnoRiferimento) {
		this.filtroAnnoRiferimento = filtroAnnoRiferimento;
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

	public static String getTributiEnteLabel() {
		return tributiEnteLabel;
	}

	public static String getTributiAmmessiLabel() {
		return tributiAmmessiLabel;
	}

	public static void setTributiEnteLabel(String tributiEnteLabel) {
		PosizioneDebitoriaContainerForm.tributiEnteLabel = tributiEnteLabel;
	}

	public static void setTributiAmmessiLabel(String tributiAmmessiLabel) {
		PosizioneDebitoriaContainerForm.tributiAmmessiLabel = tributiAmmessiLabel;
	}


}
