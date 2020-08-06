package it.tasgroup.idp.billerservices.api.plugin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatiPiazzaturaFlusso implements Serializable {

	public String senderId;
	public String idEnte;
	public String descrizioneEnte;
	public String abiEnte;
	public String senderSys;
	public String tipoDebito;
	public String idTributo;  //Id Categoria
	public String e2eMsgId;
	public String tipoFile;
	public int numeroPosizioni;
	public boolean smartReplace=false;
}
