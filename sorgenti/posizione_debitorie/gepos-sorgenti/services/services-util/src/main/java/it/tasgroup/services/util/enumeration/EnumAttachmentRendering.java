/**
 * 
 */
package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

/**
 * @author pazzik
 *
 */
public enum EnumAttachmentRendering implements MessageDescription{
	
	
	ASIS			("0", "Immutato", ""),
	IMAGE 			("1", "Immagine", ""),
	PDFA 			("2", "PDFA", "");
	
	private String chiave;
	
	private String descrizione;
	
	private String chiaveBundle;


	private EnumAttachmentRendering(String chiave, String descrizione, String chiaveBundle) {
		
		this.chiave = chiave;
		
		this.descrizione = descrizione;
		
		this.chiaveBundle = chiaveBundle;

	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}
	
	public Boolean isIMAGE(){
		return IMAGE.equals(name());
	}
	
	public Boolean isPDFA(){
		return PDFA.equals(name());
	}
	
	public Boolean isASIS(){
		return ASIS.equals(name());
	}
	
	public static EnumAttachmentRendering getByKey(String chiave) {
		
		EnumAttachmentRendering desiredItem = null; // Default   
		
		for (EnumAttachmentRendering item : EnumAttachmentRendering.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
					desiredItem = item;   
				
					break;            
				
				}             
			}        
		
		return desiredItem; 
	}

}
