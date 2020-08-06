package it.tasgroup.iris.comunication.ws.client;

import it.tasgroup.iris.comunication.dto.CanaliDTO;
import it.tasgroup.iris.comunication.dto.LogMessaggioDTO;
import it.tasgroup.iris.comunication.dto.MessaggioLogicoDTO;
import it.tasgroup.iris.comunication.dto.TipoMessaggioDTO;
import it.tasgroup.iris.comunication.dto.UtentiCanaliDTO;
import it.tasgroup.iris.comunication.ws.impl.CanaleType;
import it.tasgroup.iris.comunication.ws.impl.ComunicationPortType;
import it.tasgroup.iris.comunication.ws.impl.ComunicazioneServiceImplServiceSoapBindingStub;
import it.tasgroup.iris.comunication.ws.impl.LogMessaggioType;
import it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType;
import it.tasgroup.iris.comunication.ws.impl.MessaggioType;
import it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType;
import it.tasgroup.iris.comunication.ws.impl.SearchLogMessaggiType;
import it.tasgroup.iris.comunication.ws.impl.StatoCanaleType;
import it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType;
import it.tasgroup.iris.comunication.ws.impl.StatoTipoMessaggioType;
import it.tasgroup.iris.comunication.ws.impl.TipoCanaleType;
import it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType;
import it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType;
import it.tasgroup.iris.comunication.ws.impl.UtenteType;
import it.tasgroup.iris.comunication.ws.impl.ValidoType;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class IrisComunicationWSInvoker {
	private Logger LOG = Logger.getLogger(IrisComunicationWSInvoker.class);

	private static  ComunicationPortType service;

	public IrisComunicationWSInvoker() {

		try {
			ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("comunication-ws-client.properties");
			String urlStr = props.getProperty("iris.comunication.services.url");				
			service = new ComunicazioneServiceImplServiceSoapBindingStub(new URL(urlStr), new org.apache.axis.client.Service());
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public List<UtentiCanaliDTO> getCanaliComunicazione(String irisId, boolean isAnonymous) throws MalformedURLException, RemoteException{	
		UtenteType utente = null;
		List<UtentiCanaliDTO> canali = null;
		
		LOG.info("getCanaliComunicazione - chiamata al comunicationws utente["+(utente != null? utente : "null")+"]");
		
		try{
			if(irisId != null){
				utente = setUtenteType(irisId, isAnonymous);
				UtenteCanaleType[] res = service.getCanaliComunicazione(utente);
				canali = getUtentiCanaliDTO(res, utente);
			} else {
				CanaleType[] ct = service.getCanaliConfigurazione();
				canali = new ArrayList<UtentiCanaliDTO>();
				for (CanaleType canaleType : ct) {
						UtentiCanaliDTO uc = new UtentiCanaliDTO();
						uc.setCanale(getCanaleDTO(canaleType));
						canali.add(uc);
				}
			}	
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		if (canali!=null) {
			LOG.info("getCanaliComunicazione - ottenuti ["+canali.size()+"] risultati");
		}	
		
		return canali;
	}

	public List<CanaliDTO> getCanaliConfigurazione(){	
		List<CanaliDTO> canali = null;
		
		LOG.info("getCanaliConfigurazione");
		
		try{
			CanaleType[] ct = service.getCanaliConfigurazione();
			canali = new ArrayList<CanaliDTO>();
			for (CanaleType canaleType : ct) {
					canali.add(getCanaleDTO(canaleType));
			}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("getCanaliConfigurazione - ottenuti ["+canali.size()+"] risultati");
		
		return canali;
	}
	
	public Map<String, CanaliDTO> getCanaliConfigurazioneAbilitatiMap() {
		Map<String, CanaliDTO> canali = null;
		
		LOG.info("getCanaliConfigurazioneAbilitatiMap");
		
		try{
			CanaleType[] ct = service.getCanaliConfigurazione();
			canali = new HashMap<String, CanaliDTO>();
			for (CanaleType canaleType : ct) {
					try{
						TipoCanaleType.fromString(canaleType.getDenominazione());
					} catch(IllegalArgumentException e){
						continue;
					}
					
					canali.put(canaleType.getDenominazione(), getCanaleDTO(canaleType));
			}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("getCanaliConfigurazioneAbilitatiMap - ottenuti ["+canali.size()+"] risultati");
		
		return canali;
	}
	
	public Map<String, CanaliDTO> getCanaliConfigurazioneMap() {
		Map<String, CanaliDTO> canali = null;
		
		LOG.info("getCanaliConfigurazioneMap");
		
		try{
			CanaleType[] ct = service.getCanaliConfigurazione();
			canali = new HashMap<String, CanaliDTO>();
			for (CanaleType canaleType : ct) {
					canali.put(canaleType.getDenominazione(), getCanaleDTO(canaleType));
			}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("getCanaliConfigurazioneMap - ottenuti ["+canali.size()+"] risultati");
		
		return canali;
	}
	
	public Map<Long, CanaliDTO> getCanaliConfigurazioneByIdMap() {
		Map<Long, CanaliDTO> canali = null;
		
		LOG.info("getCanaliConfigurazioneMap");
		
		try{
			CanaleType[] ct = service.getCanaliConfigurazione();
			canali = new HashMap<Long, CanaliDTO>();
			for (CanaleType canaleType : ct) {
					canali.put(canaleType.getId().longValue(), getCanaleDTO(canaleType));
			}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("getCanaliConfigurazioneMap - ottenuti ["+canali.size()+"] risultati");
		
		return canali;
	}
	
	public List<TipoMessaggioDTO> getTipiMessaggio(){	
		List<TipoMessaggioDTO> tipiMessaggio = null;
		
		LOG.info("getTipiMessaggio");
		
		try{
			TipoMessaggioType[] ct = service.getTipoMessaggio();
			tipiMessaggio = new ArrayList<TipoMessaggioDTO>();
			for (TipoMessaggioType tmt : ct) {
					tipiMessaggio.add(getTipoMessaggioDTO(tmt));
			}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("getCanaliConfigurazione - ottenuti ["+tipiMessaggio.size()+"] risultati");
		
		return tipiMessaggio;
	}
	
	public Map<String, TipoMessaggioDTO> getTipiMessaggioAbilitatiMap() {
		Map<String, TipoMessaggioDTO> canali = null;
		
		LOG.info("getTipiMessaggioAbilitatiMap");
		
		try{
			TipoMessaggioType[] ct = service.getTipoMessaggio();
			canali = new HashMap<String, TipoMessaggioDTO>();
			for (TipoMessaggioType tmt : ct) {
					try{
						if(StatoTipoMessaggioType.ATTIVO.equals(tmt.getStato())){
							canali.put(tmt.getDenominazione(), getTipoMessaggioDTO(tmt));
						}
					} catch(IllegalArgumentException e){
						continue;
					}
			}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("getTipiMessaggioAbilitatiMap - ottenuti ["+canali.size()+"] risultati");
		
		return canali;
	}
	
	public Map<String, TipoMessaggioDTO> getTipiMessaggioMap() {
		Map<String, TipoMessaggioDTO> tmMap = null;
		
		LOG.info("getTipiMessaggioMap");
		
		try{
			TipoMessaggioType[] list = service.getTipoMessaggio();
			tmMap = new HashMap<String, TipoMessaggioDTO>();
			for (TipoMessaggioType tmt : list) {
					tmMap.put(tmt.getDenominazione(), getTipoMessaggioDTO(tmt));
			}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("getTipiMessaggioMap - ottenuti ["+tmMap.size()+"] risultati");
		
		return tmMap;
	}
	
	public Map<Long, TipoMessaggioDTO> getTipiMessaggioByIdMap() {
		Map<Long, TipoMessaggioDTO> tmMap = null;
		
		LOG.info("getTipiMessaggioByIdMap");
		
		try{
			TipoMessaggioType[] list = service.getTipoMessaggio();
			tmMap = new HashMap<Long, TipoMessaggioDTO>();
			for (TipoMessaggioType tmt : list) {
					tmMap.put(tmt.getId().longValue(), getTipoMessaggioDTO(tmt));
			}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("getTipiMessaggioByIdMap - ottenuti ["+tmMap.size()+"] risultati");
		
		return tmMap;
	}

	public void subscribeCanali(String irisId, boolean isAnonymous,String[] tipoCanaleResponse, Map<String, String> parametroCanaleMap) throws Exception{
		UtenteType utente = setUtenteType(irisId, isAnonymous);
		TipoCanaleType[] list = setTipoCanaleTypeArray(tipoCanaleResponse);
		Map<TipoCanaleType, ParametroCanaleType> parametroCanaleTypeMap = setParametroCanaleTypeMap(parametroCanaleMap);

		LOG.info("subscribeCanali - chiamata al comunicationws utente["+utente+","+list+","+parametroCanaleTypeMap+"]");
		
		try{
		    for (TipoCanaleType tipoCanaleType : list) {
                ParametroCanaleType param = parametroCanaleTypeMap.get(tipoCanaleType);
		        service.subscribeCanali(utente, tipoCanaleType, param);
            }
			LOG.info("subscribeCanali - ok");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}
	}
	
	public void unsubscribeCanali(String irisId, boolean isAnonymous,String[] tipoCanaleResponse,Map<String, String> parametroCanaleMap) throws Exception{
		UtenteType utente = setUtenteType(irisId, isAnonymous);
		TipoCanaleType[] list = setTipoCanaleTypeArray(tipoCanaleResponse);
		Map<TipoCanaleType, ParametroCanaleType> parametroCanaleTypeMap = setParametroCanaleTypeMap(parametroCanaleMap);
		
		try{
		    for (TipoCanaleType tipoCanaleType : list) {
		        ParametroCanaleType param = parametroCanaleTypeMap.get(tipoCanaleType);
		        
		        String msg = "unsubscribeCanali - chiamata al comunicationws utente["+utente.getId_utente()+","+tipoCanaleType.getValue()+","+param.getParametro()+"]";
		        System.out.println(msg);
		        LOG.info(msg);
		        
    			service.unsubscribeCanali(utente, tipoCanaleType, param);
    			
    			LOG.info("unsubscribeCanali - ok");
		    }
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}

	}
	
	public List<LogMessaggioDTO> elencoLogMessaggi(LogMessaggioDTO logMessDto) throws RemoteException{
		
		SearchLogMessaggiType searchLogMessaggiType = new SearchLogMessaggiType();
		CanaleType canale= null;
		if(logMessDto.getCanale() != null && logMessDto.getCanale().getId() != null){
			canale= new CanaleType();
			canale.setId(BigInteger.valueOf(logMessDto.getCanale().getId()));
			searchLogMessaggiType.setCanale(canale);
		}
		
		StatoMessaggioType statoMessaggio = null;
		if(logMessDto.getStato() != null){
			statoMessaggio = StatoMessaggioType.fromString(logMessDto.getStato());
			searchLogMessaggiType.setStato(statoMessaggio);
		}
		
		Calendar dataDa = null;
		if(logMessDto.getDataDa() != null){
			dataDa = Calendar.getInstance();
			dataDa.setTimeInMillis(logMessDto.getDataDa().getTime()) ;
			searchLogMessaggiType.setDataDa(dataDa);
		}
		
		Calendar dataA = null;
		if(logMessDto.getDataA() != null){
			dataA = Calendar.getInstance();
			dataA.setTimeInMillis(logMessDto.getDataA().getTime()) ;
			searchLogMessaggiType.setDataA(dataA);
		}
		
//		String utente = null;
		if(logMessDto.getUtente()!=null && logMessDto.getUtente().getIdUtente()!=null){
			searchLogMessaggiType.setUtente(logMessDto.getUtente().getIdUtente());
		}
		
		TipoMessaggioType tipoMessaggio = null;
		if(logMessDto.getMessaggioLogico() != null &&
				logMessDto.getMessaggioLogico().getTipoMessaggio() != null &&
				logMessDto.getMessaggioLogico().getTipoMessaggio().getId() != null){
			tipoMessaggio = new TipoMessaggioType();
			tipoMessaggio.setId(BigInteger.valueOf(logMessDto.getMessaggioLogico().getTipoMessaggio().getId()));
			searchLogMessaggiType.setTipoMessaggio(tipoMessaggio);
		}
		
		LogMessaggioType[] logMessaggioArray = service.elencoLogMessaggi(searchLogMessaggiType);
		return getLogMessaggioDTO(logMessaggioArray);
	}
	
	public void sendMessageAgain(Integer[] messaggioTypeResponse) throws RemoteException {
		MessaggioType[] msgsIds = setMessaggioTypeArray(messaggioTypeResponse);
		service.sendMessageAgain(msgsIds);
	}

	/**
	 * TODO
	 * @throws RemoteException 
	 */
	public void updateCanaliComunicazione(CanaleType canale) throws RemoteException{
		service.updateCanaliComunicazione(canale);
	}
	
	/**
	 * TODO
	 * @throws RemoteException 
	 */
	public void setStatoTipiMessaggio(TipoMessaggioDTO[] tipoMessaggio) throws RemoteException{
		TipoMessaggioType[] tipoMessaggioTypeArray = setTipoMessaggioTypeArray(tipoMessaggio);
		for (TipoMessaggioType tmt : tipoMessaggioTypeArray) {
			service.updateTipoMessaggio(tmt);
		}
	}
	
	//TODO aggiungere chiamata al metodo di validazione del canale!!
	
	public boolean validaConfigurazioneCanale(CanaleType canale) throws RemoteException{
		ValidoType valido = service.validaConfigurazioneCanale(canale);		
		
		return valido.isIs_valid();		
	}
	
	private Map<TipoCanaleType, ParametroCanaleType> setParametroCanaleTypeMap(Map<String, String> parametroCanaleMap) {
        if (parametroCanaleMap == null || parametroCanaleMap.isEmpty())
            return null;
        
        Map<TipoCanaleType, ParametroCanaleType> map =  new HashMap<TipoCanaleType, ParametroCanaleType>();

        for (String key : parametroCanaleMap.keySet()) {
            ParametroCanaleType param = setParametroCanaleType(parametroCanaleMap.get(key));
            TipoCanaleType tipoCanale = TipoCanaleType.fromString(key);
            map.put(tipoCanale, param);
        }

        return map;
    }

	private ParametroCanaleType setParametroCanaleType(String parametroCanale) {
		ParametroCanaleType parametroCanaleType = new ParametroCanaleType();
		parametroCanaleType.setParametro(parametroCanale);
		return parametroCanaleType;
	}	

	private CanaliDTO getCanaleDTO(CanaleType tipo) {
		CanaliDTO canale = new CanaliDTO();
		canale.setConfigurazione(tipo.getConfigurazione());
		canale.setDenominazione(tipo.getDenominazione());
		canale.setId(tipo.getId().longValue());
		canale.setNumTentativi(tipo.getNum_tentativi() == null? null : tipo.getNum_tentativi().intValue());
		canale.setStato(tipo.getStato().getValue());
		canale.setTempoRetry(tipo.getTempo_retry() == null? null : tipo.getTempo_retry().intValue());
		canale.setSortingDir("ASC");
		canale.setSortingField("denominazione");
		return canale;
	}
	
	private List<UtentiCanaliDTO> getUtentiCanaliDTO(UtenteCanaleType[] res, UtenteType utente) {
		List<UtentiCanaliDTO> canali = new ArrayList<UtentiCanaliDTO>();
		
		if(res != null){			
			for (UtenteCanaleType tipo: res) {
				UtentiCanaliDTO canale = new UtentiCanaliDTO();
				canale.setIdUtente(utente != null? utente.getId_utente() : null);
				canale.setIsAnonymous(utente != null? utente.isIs_anonymous() : null);
				canale.setConfigurazione(tipo.getConfigurazione());
				// Se res non è vuoto e utente != null, è scontato che quel canale è attivo per l'utente,
				// visto che è stato restituito dalla query
				canale.setStato(utente != null? tipo.getStato() : StatoCanaleType.DISATTIVO.getValue());
				canale.setCanale(getCanaleDTO(tipo.getCanale()));
				canali.add(canale);
			}
		}
		
		return canali;
	}
	
	private TipoMessaggioDTO getTipoMessaggioDTO(TipoMessaggioType tipo) {
		TipoMessaggioDTO tm = new TipoMessaggioDTO();
		if(tipo != null) {
    		tm.setDenominazione(tipo.getDenominazione());
    		tm.setId(tipo.getId() != null? tipo.getId().intValue() : null);
    		tm.setStato(tipo.getStato() != null? tipo.getStato().getValue() : null);
		}
		tm.setSortingDir("ASC");
		tm.setSortingField("denominazione");
		return tm;
	}
	
	private List<LogMessaggioDTO> getLogMessaggioDTO(LogMessaggioType[] logMessaggioArray) {
		if (logMessaggioArray == null)
			return null;
		
		List<LogMessaggioDTO> list =  new ArrayList<LogMessaggioDTO>();
		
		for (LogMessaggioType lmt : logMessaggioArray) {
			LogMessaggioDTO lm = new LogMessaggioDTO();
			lm.setId(lmt.getId().intValue());
			lm.setData(new Timestamp(lmt.getData().getTimeInMillis()));
			lm.setCanale(getCanaleDTO(lmt.getCanale()));
			lm.setMessaggioLogico(getMessaggioLogicoDTO(lmt.getMessaggioLogico()));
			lm.setStato(lmt.getStato().getValue());
			UtentiCanaliDTO utenteDto = new UtentiCanaliDTO();
			utenteDto.setCanale(lm.getCanale());
			utenteDto.setIdUtente(lmt.getUtente().getId_utente());
			utenteDto.setIsAnonymous(lmt.getUtente().isIs_anonymous());
			lm.setUtente(utenteDto);
			lm.setSortingDir("DESC");
			lm.setSortingField("data");
			
			list.add(lm);
		}
		
		return list;
	}
	
	private MessaggioLogicoDTO getMessaggioLogicoDTO(MessaggioLogicoType messaggioLogicoType){
		MessaggioLogicoDTO ml = new MessaggioLogicoDTO();
		ml.setId(messaggioLogicoType.getId().intValue());
		ml.setMessaggio(messaggioLogicoType.getMessaggio());
//		ml.setMittente(messaggioLogicoType.getMittente());
		ml.setOggetto(messaggioLogicoType.getOggetto());
		ml.setSeverity(messaggioLogicoType.getSeverity());
		ml.setTimestamp(new Timestamp(messaggioLogicoType.getTimestamp().getTimeInMillis()));
		ml.setTipoMessaggio(getTipoMessaggioDTO(messaggioLogicoType.getTipo_messaggio()));
		ml.setSortingDir("ASC");
		ml.setSortingField("timestamp");
		
		return ml;
	}

	private UtenteType setUtenteType(String irisId, boolean isAnonymous) {
		UtenteType utente = new UtenteType();
		utente.setId_utente(irisId);
		utente.setIs_anonymous(isAnonymous);
		utente.setScopo_messaggio("");
		return utente;
	}
		
	private TipoCanaleType[] setTipoCanaleTypeArray(String[] tipoCanaleResponse) {
		if (tipoCanaleResponse == null)
			return null;
		
		TipoCanaleType[] array = new TipoCanaleType[tipoCanaleResponse.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = TipoCanaleType.fromString(tipoCanaleResponse[i]);
		}
		return array;
	}
	
	private MessaggioType[] setMessaggioTypeArray(Integer[] messaggioTypeResponse) {
		if (messaggioTypeResponse == null)
			return null;
		
		MessaggioType[] array = new MessaggioType[messaggioTypeResponse.length];
		for (int i = 0; i < array.length; i++) {
			Integer id = messaggioTypeResponse[i];
			MessaggioType t = new MessaggioType();
			t.setId(BigInteger.valueOf(id));
			array[i] = t;
		}
		return array;
	}
	
	private TipoMessaggioType[] setTipoMessaggioTypeArray(TipoMessaggioDTO[] tipoMessaggioResponse) {
		if (tipoMessaggioResponse == null)
			return null;
		
		TipoMessaggioType[] array = new TipoMessaggioType[tipoMessaggioResponse.length];
		for (int i = 0; i < array.length; i++) {
			TipoMessaggioDTO tmr = tipoMessaggioResponse[i];
			TipoMessaggioType t = new TipoMessaggioType();
			t.setId(BigInteger.valueOf(tmr.getId()));
			t.setDenominazione(tmr.getDenominazione());
			t.setStato(StatoTipoMessaggioType.fromString(tmr.getStato()));
			t.setSortingField(tmr.getSortingField());
			t.setSortingDir(tmr.getSortingDir());
			array[i] = t;
		}
		return array;
	}
	
}

