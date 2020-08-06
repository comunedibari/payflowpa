package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;

public class AnonymousBachecaNewsDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7172777700215772160L;
	
	private String id;
	private String titolo;
	private String contenuto;
	private String dataLink;
	
	private byte[] imgExt;
	private byte[] imgInt;
	private String imgExtName;
	private String imgIntName;

	
	public String getTitolo() {
		return titolo;
	}
	public String getContenuto() {
		return contenuto;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public String getDataLink() {
		return dataLink;
	}
	public void setDataLink(String dataLink) {
		this.dataLink = dataLink;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getImgExt() {
		return imgExt;
	}
	public void setImgExt(byte[] imgExt) {
		this.imgExt = imgExt;
	}
	public byte[] getImgInt() {
		return imgInt;
	}
	public void setImgInt(byte[] imgInt) {
		this.imgInt = imgInt;
	}
	public String getImgExtName() {
		return imgExtName;
	}
	public void setImgExtName(String imgExtName) {
		this.imgExtName = imgExtName;
	}
	public String getImgIntName() {
		return imgIntName;
	}
	public void setImgIntName(String imgIntName) {
		this.imgIntName = imgIntName;
	}
	
	

}
