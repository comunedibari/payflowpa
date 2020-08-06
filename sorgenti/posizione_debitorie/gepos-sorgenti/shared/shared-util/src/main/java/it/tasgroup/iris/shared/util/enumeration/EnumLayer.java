/**
 * 
 */
package it.tasgroup.iris.shared.util.enumeration;


/**
 * @author PazziK
 *
 */
public enum EnumLayer implements MessageDescription{
	
	MODEL("","",""),
	DAO("","",""),
	EJB("","",""),
	ACTION("","","");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumLayer(String chiave, String descrizione, String chiaveBundle){
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		
	}	

	@Override
	public String getChiave() {
		
		return chiave;
	}

	@Override
	public String getDescrizione() {
		
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		
		return chiaveBundle;
	}
	
}
