/**
 * Created on 03/lug/2008
 */
package it.nch.eb.flatx.xml;


/**
 * @author gdefacci
 */
class ItemRecordModel {
	String docName;
	String itemsName;
	String product;
	String priceOrQuantity ;
	
	public String getDocName() {
		return docName;
	}
	
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	public String getItemsName() {
		return itemsName;
	}
	
	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}
	
	public String getProduct() {
		return product;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	public String getPriceOrQuantity() {
		return priceOrQuantity;
	}
	
	public void setPriceOrQuantity(String priceOrQuantity) {
		this.priceOrQuantity = priceOrQuantity;
	}
	
}