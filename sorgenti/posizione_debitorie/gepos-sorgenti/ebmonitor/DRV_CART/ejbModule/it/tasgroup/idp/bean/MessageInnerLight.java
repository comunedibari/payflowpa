package it.tasgroup.idp.bean;

import java.io.Serializable;

import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
// TODO Valutare se estendere PrevisitingData
public class MessageInnerLight implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	private PrevisitingData prevData;
	private String serviceNameType;
	private String trtSenderId;
	private String trtSenderSys;
	private MonitoringData monData;
	private String xml;
	
	public MonitoringData getMonData() {
		return monData;
	}
	public void setMonData(MonitoringData monData) {
		this.monData = monData;
	}
	public PrevisitingData getPrevData() {
		return prevData;
	}
	public void setPrevData(PrevisitingData prevData) {
		this.prevData = prevData;
	}
	
	public String getServiceNameType() {
		return serviceNameType;
	}
	public void setServiceNameType(String serviceNameType) {
		this.serviceNameType = serviceNameType;
	}
	
	public String getTrtSenderId() {
		return trtSenderId;
	}
	public void setTrtSenderId(String trtSenderId) {
		this.trtSenderId = trtSenderId;
	}
	public String getTrtSenderSys() {
		return trtSenderSys;
	}
	public void setTrtSenderSys(String trtSenderSys) {
		this.trtSenderSys = trtSenderSys;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}

}
