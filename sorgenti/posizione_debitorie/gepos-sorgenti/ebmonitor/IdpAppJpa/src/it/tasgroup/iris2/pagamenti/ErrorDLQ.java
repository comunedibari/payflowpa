package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ERROR_DLQ")
public class ErrorDLQ extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable, Comparable<ErrorDLQ> {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String msgId;
	private String msgCorrelationId;
	private String msgDestination;
	private String msgDeliveryMode;
	private String msgFlow;
	private String msgErrDesc;
	private Timestamp msgExpiration;
	private byte[] msgErrDetail;
	
	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="error_dlq_pk_gen")	
	@SequenceGenerator(
	    name="error_dlq_pk_gen",
	    sequenceName="ERROR_DLQ_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}
	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="ID_MSG", updatable=false, insertable=true, nullable = false)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name="DESTINATION", updatable=false, insertable=true, nullable = false)
	public String getMsgDestination() {
		return msgDestination;
	}

	public void setMsgDestination(String msgDestination) {
		this.msgDestination = msgDestination;
	}


	@Column(name="FLOW", updatable=false, insertable=true, nullable = false)
	public String getMsgFlow() {
		return msgFlow;
	}

	public void setMsgFlow(String msgFlow) {
		this.msgFlow = msgFlow;
	}

	
	@Column(name="ERROR_DESC", updatable=false, insertable=true, nullable = false)
	public String getMsgErrDesc() {
		return msgErrDesc;
	}

	public void setMsgErrDesc(String msgErrDesc) {
		this.msgErrDesc = msgErrDesc;
	}


	@Column(name="ERROR_DETAIL", updatable=false, insertable=true, nullable = false)
	@Lob
	public byte[] getMsgErrDetail() {
		return msgErrDetail;
	}

	public void setMsgErrDetail(byte[] msgErrDetail) {
		this.msgErrDetail = msgErrDetail;
	}
	
	@Column(name="ID_CORRELATION", updatable=false, insertable=true)
	public String getMsgCorrelationId() {
		return msgCorrelationId;
	}

	public void setMsgCorrelationId(String msgCorrelationId) {
		this.msgCorrelationId = msgCorrelationId;
	}

	
	@Column(name="DELIVERY_MODE", updatable=false, insertable=true)
	public String getMsgDeliveryMode() {
		return msgDeliveryMode;
	}

	public void setMsgDeliveryMode(String msgDeliveryMode) {
		this.msgDeliveryMode = msgDeliveryMode;
	}

	
	@Column(name="EXPIRATION", updatable=false, insertable=true)
	public Timestamp getMsgExpiration() {
		return msgExpiration;
	}

	public void setMsgExpiration(Timestamp msgExpiration) {
		this.msgExpiration = msgExpiration;
	}

	
	@Override
	public int compareTo(ErrorDLQ other) {
		int retValue = 0;
		if (!(this.equals(other))) {
			retValue = this.msgId.compareTo(other.getMsgId());
			if (retValue==0) {
				retValue = this.getTsInserimento().compareTo(other.getTsInserimento());
			}
		}
		return retValue;
	}

		

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorDLQ [msgId=");
		builder.append(msgId);
		builder.append(", msgCorrelationId=");
		builder.append(msgCorrelationId);
		builder.append(", msgDestination=");
		builder.append(msgDestination);
		builder.append(", msgDeliveryMode=");
		builder.append(msgDeliveryMode);
		builder.append(", msgFlow=");
		builder.append(msgFlow);
		builder.append(", msgErrDesc=");
		builder.append(msgErrDesc);
		builder.append(", msgExpiration=");
		builder.append(msgExpiration);
		builder.append(", msgErrDetail=");
		builder.append(Arrays.toString(msgErrDetail));
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append("]");
		return builder.toString();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((msgDestination == null) ? 0 : msgDestination.hashCode());
		result = prime * result + ((msgFlow == null) ? 0 : msgFlow.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result + ((this.getTsInserimento() == null) ? 0 : this.getTsInserimento().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ErrorDLQ))
			return false;
		ErrorDLQ other = (ErrorDLQ) obj;
		if (msgDestination == null) {
			if (other.msgDestination != null)
				return false;
		} else if (!msgDestination.equals(other.msgDestination))
			return false;
		if (msgFlow == null) {
			if (other.msgFlow != null)
				return false;
		} else if (!msgFlow.equals(other.msgFlow))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		if (this.getTsInserimento() == null) {
			if (other.getTsInserimento() != null)
				return false;
		} else if (!this.getTsInserimento().equals(other.getTsInserimento()))
			return false;		
		return true;
	}
	

}
