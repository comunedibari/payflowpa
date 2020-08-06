package it.tasgroup.idp.domain.messaggi;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
	
@Entity
@NamedQueries({
@NamedQuery(
	name="DeleteByDate", 
	query=
		"delete FROM PendenzeCart pend " +
			" WHERE pend.timestampRicezione >= :start and " 
			+ " pend.timestampRicezione <= :end and "
			+ " pend.stato = 'RISPOSTA INVIATA' "),
@NamedQuery(
		name="Last25Pendenze", 
		query=
		"SELECT PEND " +
		" FROM PendenzeCart as PEND " 
//		+ (e2emsgid!=null ? filtro  : "") 
		+ " order by PEND.timestampRicezione desc ")			
,
@NamedQuery(
		name="selectPendenzeIdempotenza", 
		query=
		"SELECT PEND.stato " +
		" FROM PendenzeCart as PEND " 
		+ " WHERE pend.pk.e2emsgid = :e2emsgid"
		+ " AND pend.pk.senderid= :ente "
		+ " AND pend.pk.sendersys = :sil ")			
})
@Table(name="PENDENZE_CART")
public class PendenzeCart extends PendenzeCartAbstract {
	
	
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("PendenzeCart [pk=");
//		builder.append(pk);
//		builder.append(", receiverid=");
//		builder.append(receiverid);
//		builder.append(", receiversys=");
//		builder.append(receiversys);
//		builder.append(", messaggioXml=");
//		builder.append(Arrays.toString(messaggioXml));
//		builder.append(", timestampRicezione=");
//		builder.append(timestampRicezione);
//		builder.append(", stato=");
//		builder.append(stato);
//		builder.append(", prVersione=");
//		builder.append(prVersione);
//		builder.append(", opInserimento=");
//		builder.append(opInserimento);
//		builder.append(", tsInserimento=");
//		builder.append(tsInserimento);
//		builder.append(", numPendenze=");
//		builder.append(numPendenze);
//		builder.append(", numPendDeleted=");
//		builder.append(numPendDeleted);
//		builder.append(", tipoMessaggio=");
//		builder.append(tipoMessaggio);
//		builder.append(", tipoTributo=");
//		builder.append(tipoTributo);
//		builder.append("]");
//		return builder.toString();
//	}
	
	
}
