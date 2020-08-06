package it.tasgroup.idp.cart.core.dao;

import java.util.List;

import javax.ejb.Local;

import it.tasgroup.idp.cart.core.model.GestioneModel;
import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.MessaggioNonGestitoModel;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;

@Local
public interface IIdpCartDbManager {

	public Long inserisciMessaggioNonGestito(MessaggioNonGestitoModel messaggio,String esecutoreOperazione) throws Exception;
	
	public Long inserisciGestione(MessaggioModel messaggio, GestioneModel gestione,String esecutoreOperazione) throws Exception;
	
	public void aggiornaGestione(MessaggioModel messaggio, GestioneModel gestione,TipoGestione tipoGestione, String esecutoreOperazione,boolean sincrono) throws Exception;
		
	public GestioneModel getUltimaGestione(String soggetto, String sil, String msgId, TipoMessaggio tipoMessaggio,
			TipoGestione tipoSpedizione) throws Exception;
	
	public List<GestioneModel> getGestioni(String soggetto, String sil, String msgId, TipoMessaggio tipoMessaggio,
			TipoGestione tipoSpedizione) throws Exception;
	
	public MessaggioModel getMessaggioUltimaGestione(String soggetto, String sil, String msgId, TipoMessaggio tipoMessaggio,
			TipoGestione tipoSpedizione) throws Exception;
}
