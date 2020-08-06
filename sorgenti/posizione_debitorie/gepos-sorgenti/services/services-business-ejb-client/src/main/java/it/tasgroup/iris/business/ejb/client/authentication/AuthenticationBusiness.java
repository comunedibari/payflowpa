package it.tasgroup.iris.business.ejb.client.authentication;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.is.fo.profilo.Applicazioni;
import it.nch.is.fo.profilo.ApplicazioniCommon;
import it.nch.is.fo.profilo.IntestatariCommon;
import it.tasgroup.iris.dto.FunzioniPropDTO;
import it.tasgroup.iris.dto.autentication.SessionDTO;
import it.tasgroup.iris.dto.menu.ApplicazioniMenu;

import java.io.Serializable;
import java.util.List;

public interface AuthenticationBusiness extends Serializable {

	public void disconnectSession(String sessionId);

	public void insertOrUpdateSession(SessionDTO dto);

	public void updateCorporateInSession(String sessionId, String corporate);

	public String getDbInfo();

	public ApplicazioniMenu estraiMenuPerCache(String codApplicazione);

	public FrontEndContext getCurrentCorporateForLogin(FrontEndContext fec, String corporate, String operator);
	
	public List<String> listEnabledFunctions(String corporate, String operator);
	
	public FrontEndContext login(FrontEndContext fec, DTO dto);
	
	public FrontEndContext loginRT(FrontEndContext fec, DTO dto);
	
	public String getCodApplicazioneByCategoria(String categoria);
	
	public IntestatariCommon getAziendaByCode(String corporate);

	public void synchTemplate(String operatore, String corporate, String codApplicazione);
	
	public List<Applicazioni> getListaApplicazioni();
	
	public List<String> getListaClassiByApplicazione(String applicazione);

	public List<FunzioniPropDTO> getListaFunzioniByApplicazioneClasse(String applicazione, String classe);
	
	public void abilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione, String user);

	public void disabilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione);


}
