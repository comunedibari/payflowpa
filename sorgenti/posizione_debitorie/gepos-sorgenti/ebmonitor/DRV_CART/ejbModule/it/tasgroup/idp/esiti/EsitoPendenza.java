package it.tasgroup.idp.esiti;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class EsitoPendenza implements Serializable {

	private EsitoPendenza(){
	}
	/**
	 * @param e2emsgid
	 * @param idPendenza
	 * @param idPendenzaEnte
	 * @param esito
	 * @param descrizioneEsito
	 */
	public EsitoPendenza(String serviceNameType, String e2emsgid, String senderId, String senderSys, String receiverId, String receiverSys,
			String idPendenza,
			String idPendenzaEnte,
			String esito,
			String descrizioneEsito,
			List erroriEsiti,
			String trtReceiverId, 
			String trtReceiverSys) {
		super();
		this.serviceNameType = serviceNameType;
		this.e2emsgid = e2emsgid;
		this.senderId = senderId;
		this.senderSys = senderSys;
		this.receiverId = receiverId;
		this.receiverSys = receiverSys;
		this.idPendenza = idPendenza;
		this.idPendenzaEnte = idPendenzaEnte;
		this.esito = esito;
		this.descrizioneEsito = descrizioneEsito;
		this.listErroriEsiti = erroriEsiti;
		this.trtReceiverId = trtReceiverId;
		this.trtReceiverSys = trtReceiverSys;
	}

	public String getEsito() {
		return esito;
	}
	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}
	public String getIdPendenza() {
		return idPendenza;
	}
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}

	public String getE2emsgid() {
		return e2emsgid;
	}
	public String getSenderId() {
		return senderId;
	}
	public String getSenderSys() {
		return senderSys;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public String getReceiverSys() {
		return receiverSys;
	}
	public List getListErroriEsiti() {
		return listErroriEsiti;
	}
	public String getServiceNameType() {
		return serviceNameType;
	}
	public void setServiceNameType(String serviceNameType) {
		this.serviceNameType = serviceNameType;
	}

	String serviceNameType;
	String e2emsgid;
	String senderId;
	String senderSys;
	String receiverId;
	String receiverSys;
	String trtReceiverId;
	String trtReceiverSys;	

	String idPendenza;
	String idPendenzaEnte;
	String esito;
	String descrizioneEsito;

	List listErroriEsiti;

	/* (non Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("EsitoPendenza[");
		buf.append(" e2emsgid: " + e2emsgid);
		buf.append(" senderId: " + senderId);
		buf.append(" senderSys: " + senderSys);
		buf.append(" receiverId: " + receiverId);
		buf.append(" receiverSys: " + receiverSys);
		buf.append(", idPendenza: " + idPendenza);
		buf.append(", idPendenzaEnte: " + idPendenzaEnte);
		buf.append(", esito: " + esito);
		buf.append(", descrizioneEsito: " + descrizioneEsito);
		String listErrori = "";
		if (listErroriEsiti!=null) {
			Iterator iter = listErroriEsiti.iterator();
			while (iter.hasNext()) {
				ErroreEsitoPendenza object = (ErroreEsitoPendenza) iter.next();
				listErrori += object.getCodice() + " -- " + object.getDescrizione();
			}
//			buf.append(", listErroriEsiti: " + ((listErroriEsiti!=null) ? listErroriEsiti.toString() : "null"));
			buf.append(", listErroriEsiti: " + ((listErroriEsiti!=null) ? listErrori : "null"));
		}
		buf.append("]");
		return buf.toString();
	}

	/* (non Javadoc)
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		EsitoPendenza out = new EsitoPendenza();

		if(e2emsgid!=null)
			out.e2emsgid = this.e2emsgid;
		if(idPendenza!=null)
			out.idPendenza = this.idPendenza;
		if(idPendenzaEnte!=null)
			out.idPendenzaEnte = this.idPendenzaEnte;
		if(esito!=null)
			out.esito = this.esito;
		if(descrizioneEsito!=null)
			out.descrizioneEsito = this.descrizioneEsito;

		return out;
	}

	public String getTrtReceiverId() {
		return trtReceiverId;
	}

	public String getTrtReceiverSys() {
		return trtReceiverSys;
	}
	
	
}
