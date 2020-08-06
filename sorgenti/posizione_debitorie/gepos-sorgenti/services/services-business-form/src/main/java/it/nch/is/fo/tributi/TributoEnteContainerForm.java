package it.nch.is.fo.tributi;

import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.RicercaAvvisiPosizioneDebitoriaForm;
import it.nch.idp.posizionedebitoria.RicercaDistintePosizioneDebitoriaForm;

import java.util.Collection;

import org.apache.struts.validator.ValidatorForm;

public class TributoEnteContainerForm  extends ValidatorForm
{

//	private AvvisoPosizioneDebitoriaVO avvisoVO = new AvvisoPosizioneDebitoriaVO();
//	private DistintaPosizioneDebitoriaVO distintaVO = new DistintaPosizioneDebitoriaVO();
//                    
//    private RicercaAvvisiPosizioneDebitoriaForm ricercaAvvisiForm = new RicercaAvvisiPosizioneDebitoriaForm();
//	private RicercaDistintePosizioneDebitoriaForm ricercaDistinteForm = new RicercaDistintePosizioneDebitoriaForm();
//	
//	protected String filtroEnte;
//	protected String filtroTipoTributo;

	protected Collection listaEnti;
	protected Collection listaTipoTributo;
	protected Collection listaTributo;
	
	public TributoEnteContainerForm(){}

	
//	/**
//	 * @return
//	 */
//	public AvvisoPosizioneDebitoriaVO getAvvisoVO() {
//		return avvisoVO;
//	}
//
//	/**
//	 * @return
//	 */
//	public RicercaAvvisiPosizioneDebitoriaForm getRicercaAvvisiForm() {
//		return ricercaAvvisiForm;
//	}
//
//	/**
//	 * @param debitoriaVO
//	 */
//	public void setAvvisoVO(AvvisoPosizioneDebitoriaVO debitoriaVO) {
//		avvisoVO = debitoriaVO;
//	}
//
//	/**
//	 * @param form
//	 */
//	public void setRicercaAvvisiForm(RicercaAvvisiPosizioneDebitoriaForm form) {
//		ricercaAvvisiForm = form;
//	}
//
//	/**
//	 * @return
//	 */
//	public String getFiltroEnte() {
//		return filtroEnte;
//	}
//
//	/**
//	 * @param string
//	 */
//	public void setFiltroEnte(String string) {
//		filtroEnte = string;
//	}

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

	public Collection getListaTributo() {
		return listaTributo;
	}

	public void setListaTributo(Collection collection) {
		listaTributo = collection;
	}

//	public String getFiltroTipoTributo() {
//		return filtroTipoTributo;
//	}
//
//	public void setFiltroTipoTributo(String string) {
//		filtroTipoTributo = string;
//	}
//
//	public RicercaDistintePosizioneDebitoriaForm getRicercaDistinteForm() {
//		return ricercaDistinteForm;
//	}
//
//	public void setRicercaDistinteForm(RicercaDistintePosizioneDebitoriaForm form) {
//		ricercaDistinteForm = form;
//	}
//
//	public DistintaPosizioneDebitoriaVO getDistintaVO() {
//		return distintaVO;
//	}
//
//	public void setDistintaVO(DistintaPosizioneDebitoriaVO debitoriaVO) {
//		distintaVO = debitoriaVO;
//	}

}
