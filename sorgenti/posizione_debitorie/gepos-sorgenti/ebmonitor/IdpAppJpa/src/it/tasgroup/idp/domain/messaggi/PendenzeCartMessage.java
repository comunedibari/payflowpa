package it.tasgroup.idp.domain.messaggi;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
	
@Entity
@Table(name="PENDENZE_CART")
@NamedQueries({
	@NamedQuery(
			name="findBlobByPk", 
			query=
			"SELECT PEND.messaggioXml " +
			" FROM PendenzeCartMessage as PEND " 
			+ " WHERE pend.pk.e2emsgid = :e2emsgid"
			+ " AND pend.pk.senderid= :ente "
			+ " AND pend.pk.sendersys = :sil ")			
	})
public class PendenzeCartMessage extends PendenzeCartAbstract {
	
	private static final long serialVersionUID = 1L;

	private byte[] messaggioXml;

	 
	@Column(name="MESSAGGIO_XML")
	@Lob
	@Basic(fetch=FetchType.LAZY)
	public byte[] getMessaggioXml() {
		return this.messaggioXml;
	}

	public void setMessaggioXml(byte[] messaggioXml) {
		this.messaggioXml = messaggioXml;
	}

	
}
