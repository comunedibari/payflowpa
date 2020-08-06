package it.tasgroup.iris.dto.anonymous.payment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * DTOper la gestione dei tributi anonimi letti da file di configurazione XML
 *
 */
public class AnonymousCustomTributoEnteDTO extends AnonymousTributoEnteDTO {
   private String type;
   private int    order=-1;
   private String label;
   private String titolo;
   private String url;
   private String img;
   private int carouselOrder = -1;
   private List<AnonymousCustomTributoEnteDTO> listCustomTrib;
   
   public static String LINK="link";
   public static String TRIBUTO="tributo";
   
   public AnonymousCustomTributoEnteDTO() {
	   listCustomTrib = new ArrayList<AnonymousCustomTributoEnteDTO>();
   }
   
   public int getOrder() {
	  return order;
   }
   public void setOrder(int order) {
	  this.order = order;
   }
   public String getLabel() {
	  return label;
   }
   public void setLabel(String label) {
	   this.label = label;
   }
   public String getTitolo() {
	   return titolo;
   }
   public void setTitolo(String titolo) {
	   this.titolo = titolo;
   }
   public String getUrl() {
	   return url;
   }
   public void setUrl(String url) {
	   this.url = url;
   }
   public String getImg() {
	   return img;
   }
   public void setImg(String img) {
	   this.img = img;
   }
   
   public String getType() {
	return type;
   }
	
   public void setType(String type) {
		this.type = type;
   }
   
   public int getCarouselOrder() {
	   return carouselOrder;
   }
   public void setCarouselOrder(int carouselOrder) {
	   this.carouselOrder = carouselOrder;
   }
   public List<AnonymousCustomTributoEnteDTO> getListCustomTrib() {
	   return listCustomTrib;
   }
   public void setListCustomTrib(List<AnonymousCustomTributoEnteDTO> listCustomTrib) {
	   this.listCustomTrib = listCustomTrib;
   }
   @Override
   public boolean isSpontaneo() {
	return order!=-1;
   }
   @Override
   public boolean isShowInCarousel() {
	 return carouselOrder!=-1;
   }

@Override
   public String toString(){
	   String s ="[ type = " + type +", "
	   + "order = "+ order + ", "
	   + " label  = "+  label+ ", "
	   + " titolo = "+ titolo+ ", "
	   + " url = "+url+ ", "
	   + " img = "+img+ ", "
	   + " cdTributoEnte = "+getCdTrbEnte()+ ", "
	   + " carouselOrder = "+carouselOrder +
	   (listCustomTrib.isEmpty()?"":", listCustomTrib = \n "+ toStringList(listCustomTrib)+"\n") +"]\n ";
	 return s;   
   }
   private String toStringList(List s){
	   String str ="[\n";
	   Iterator iter =s.iterator();
	   while (iter.hasNext()){
		   str=str+"\t" +iter.next();
	   }
	   
	   return str+"]";
   }
}
