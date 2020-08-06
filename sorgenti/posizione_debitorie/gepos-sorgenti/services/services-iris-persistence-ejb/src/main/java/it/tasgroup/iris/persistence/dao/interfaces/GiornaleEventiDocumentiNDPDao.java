package it.tasgroup.iris.persistence.dao.interfaces;


import it.tasgroup.iris.gde.GiornaleEventiDocumentiNDP;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface GiornaleEventiDocumentiNDPDao extends Dao<GiornaleEventiDocumentiNDP>{
	
	public GiornaleEventiDocumentiNDP getGiornaleEventiDocumenti(String codContesto, String idDominio, String iuv, String tipo,String idPsp);

	public Long readVersion(String codContesto, String idDominio, String iuv, String tipo);

	public void updateAckDownload(String ackDownload, String codContesto, String idDominio, String iuv, String tipo, Long version);
	
}