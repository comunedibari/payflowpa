/**
 * 
 */
package it.tasgroup.backoffice.ente.form;

import it.nch.idp.posizionedebitoria.PreferenzaEsportazioneVO;
import org.apache.struts.validator.ValidatorActionForm;

import java.util.Collection;

/**
 * @author pazzik
 *
 */
public class PreferenzeEsportazioneForm extends ValidatorActionForm{
	
	private PreferenzaEsportazioneVO preferenzeVo = new PreferenzaEsportazioneVO();
	
	protected Collection listaTipoTributo;

	public Collection getListaTipoTributo() {
		return listaTipoTributo;
	}

	public void setListaTipoTributo(Collection listaTipoTributo) {
		this.listaTipoTributo = listaTipoTributo;
	}

	public PreferenzaEsportazioneVO getPreferenzeVo() {
		return preferenzeVo;
	}

	public void setPreferenzeVo(PreferenzaEsportazioneVO preferenzeVo) {
		this.preferenzeVo = preferenzeVo;
	}

}
