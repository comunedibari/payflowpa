package it.tasgroup.iris.navigation;

import java.io.Serializable;

public class CarouselItem implements Serializable,Comparable<CarouselItem> {

	String carouselIndex;
	
	String img;
	
	String linkId;
	
	String idEnte;
	
	String codTributo;
	
	String caption;
	
	String linkCaption;
	@Deprecated
	String viewType;
	
	public String getIdEnte() {
		return idEnte;
	}
	
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	public String getCodTributo() {
		return codTributo;
	}
	
	public void setCodTributo(String codTributo) {
		this.codTributo = codTributo;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getLinkCaption() {
		return linkCaption;
	}
	
	public void setLinkCaption(String linkCaption) {
		this.linkCaption = linkCaption;
	}
	
	public String getViewType() {
		return viewType;
	}
	
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	public String getLinkId() {
		return linkId;
	}
	
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	
	public String getCarouselIndex() {
		return carouselIndex;
	}

	public void setCarouselIndex(String carouselIndex) {
		this.carouselIndex = carouselIndex;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public int compareTo(CarouselItem o) {
		return this.carouselIndex.compareTo(o.carouselIndex);
	}
	
	
	
}
