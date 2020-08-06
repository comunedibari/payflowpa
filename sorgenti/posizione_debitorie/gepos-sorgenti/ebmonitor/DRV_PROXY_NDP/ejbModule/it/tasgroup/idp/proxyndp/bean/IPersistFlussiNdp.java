package it.tasgroup.idp.proxyndp.bean;

import javax.ejb.Local;

import it.tasgroup.idp.gateway.FlussiNdp;

@Local
public interface IPersistFlussiNdp {

	void insertFlussoNdp(FlussiNdp flusso);

}