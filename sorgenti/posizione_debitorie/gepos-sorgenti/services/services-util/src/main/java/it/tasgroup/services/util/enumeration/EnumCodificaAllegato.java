/**
 * 
 */
package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

/**
 * @author pazzik
 *
 */
public enum EnumCodificaAllegato implements MessageDescription {
	
	/**
	 * Vedi RFC 127
	 * 
	<xs:simpleType name="MIMETypeCode">
		<xs:restriction base="Max4Text">
			<xs:enumeration value="GIF_"/>
			<xs:enumeration value="HTML"/>
			<xs:enumeration value="JPEG"/>
			<xs:enumeration value="LNK_"/>
			<xs:enumeration value="MSWD"/>
			<xs:enumeration value="MSEX"/>
			<xs:enumeration value="MSPP"/>
			<xs:enumeration value="PDF_"/>
			<xs:enumeration value="PNG_"/>
			<xs:enumeration value="TEXT"/>
			<xs:enumeration value="XML_"/>
		</xs:restriction>
	</xs:simpleType>
	 */
	
	GIF("GIF_","GIF", "allegato.codifica.GIF"), 
	HTML("HTML","HTML", "allegato.codifica.HTML"), 
	JPEG("JPEG","JPEG", "allegato.codifica.JPEG"), 
	LNK("LNK_","LNK", "allegato.codifica.LNK"), 
	MSWD("MSWD","MSWD", "allegato.codifica.MSWD"), 
	MSEX("MSEX","MSEX", "allegato.codifica.MSEX"),
	MSPP("MSPP","MSPP", "allegato.codifica.MSPP"),
	PDF("PDF_", "PDF", "allegato.codifica.PDF"),
	PNG("PNG_", "PNG", "allegato.codifica.PNG"),
	TEXT("TEXT","TEXT", "allegato.codifica.TEXT"),
	XML("XML_", "XML", "allegato.codifica.XML");
    
    private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
    
    private EnumCodificaAllegato(String chiave, String descrizione, String chiaveBundle) {
    	
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
	
	public static EnumCodificaAllegato fromValue(String v) {
	        for (EnumCodificaAllegato c: EnumCodificaAllegato.values()) {
	            if (c.getChiave().equals(v)) {
	                return c;
	            }
	        }
	        throw new IllegalArgumentException(v);
	 }

}
