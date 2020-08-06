package it.nch.idp.posizionedebitoria;

import java.io.Serializable;


public class AnnoRiferimentoPosizioneDebitoriaVO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3637676862758390097L;
	
	private Integer anno;
	
	public AnnoRiferimentoPosizioneDebitoriaVO() {}
	
	public AnnoRiferimentoPosizioneDebitoriaVO(Integer anno) {
		this.anno = anno;
	}
	
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	

}
