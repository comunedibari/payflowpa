package it.tasgroup.iris.business.ejb.flussi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.profilo.Indirizzipostali;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.Intestatarioperatori;
import it.nch.is.fo.profilo.IntestatarioperatoriCommon;
import it.nch.is.fo.profilo.IntestatarioperatoriId;
import it.nch.is.fo.profilo.Operatori;
import it.nch.is.fo.util.CodiceFiscale;
import it.nch.is.fo.uuidgen.UUIDGenerator20;
import it.nch.utility.enumeration.Categoria;
import it.nch.utility.enumeration.DenominazioniCanali;
import it.nch.utility.enumeration.FlagEnrollmentAvvisatura;
import it.nch.utility.enumeration.TipoOperatore;
import it.tasgroup.iris.business.ejb.client.flussi.RegistrazioneAvvisaturaBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.RegistrazioneAvvisaturaBusinessRemote;
import it.tasgroup.iris.domain.Canali;
import it.tasgroup.iris.domain.UtentiCanali;
import it.tasgroup.iris.domain.UtentiCanaliPK;
import it.tasgroup.iris.dto.flussi.RichiestaCancellazioneAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RichiestaRegistrazioneAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RichiestaRicercaAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RispostaRicercaAvvisaturaDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CanaliDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.interfaces.OperatoriDAO;
import it.tasgroup.iris.persistence.dao.interfaces.UtentiCanaliDao;


@Stateless(name = "RegistrazioneAvvisaturaBusiness")
public class RegistrazioneAvvisaturaBusinessBean implements RegistrazioneAvvisaturaBusinessLocal, RegistrazioneAvvisaturaBusinessRemote {

    private static final Logger LOGGER = LogManager.getLogger(RegistrazioneAvvisaturaBusinessBean.class);
    
    @EJB(name = "IntestatariDAOService")
    private IntestatariDAO intestatariDAO;
    
    @EJB(name = "OperatoriDAOService")
    private OperatoriDAO operatoriDAO;
    
    @EJB(name = "UtentiCanaliDaoService")
    private UtentiCanaliDao utentiCanaliDao;
    
    @EJB(name = "CanaliDaoService")
    private CanaliDao canaliDao;
    
    
    
    @Override
    public Intestatari getIntestatario(String codiceFiscaleDebitore) {
    	return intestatariDAO.getIntestatarioByLapl(codiceFiscaleDebitore, false);
    }
    
    @Override
    public String updateIntestatarioRegistrazioneAvvisatura(Intestatari intestatario, RichiestaRegistrazioneAvvisaturaDTO richiestaRegistrazioneAvvisaturaDTO) {

		CodiceFiscale codiceFiscale = new CodiceFiscale(richiestaRegistrazioneAvvisaturaDTO.getCodiceFiscaleDebitore());
    	String nazione = codiceFiscale.getNazione();
    	
    	intestatario.setFlagEnrollmentAvvisatura(FlagEnrollmentAvvisatura.Y.toString());
    	intestatario.getIndirizzipostaliobj().setCountryIForm(nazione);
    	
    	// Se ci sono gia' degli UtentiCanali (canale+tipo) per questo intestatario. li cancelliamo...
    	utentiCanaliDao.deleteUtentiCanaliByUtente(intestatario.getCorporate());
    	// ...e poi creiamo quelli con i dati aggiornati
    	for (HashMap<String, String> infoCanale : richiestaRegistrazioneAvvisaturaDTO.getCanali()) {

    		String idUtente = intestatario.getCorporate();
    		
    		for (String denominazioneCanale : DenominazioniCanali.descriptions()) {
    			if (infoCanale.containsKey(denominazioneCanale)) {
        			List<Canali> listaCanali = canaliDao.findCanaliByDenominazione(denominazioneCanale);
        			for (Canali canale : listaCanali) {
        				UtentiCanali utenteCanale = fillUtenteCanale(idUtente, canale.getId(), infoCanale.get(denominazioneCanale));
        				utentiCanaliDao.saveUtentiCanali(utenteCanale);
        			}
        		}
    		}
    	}
    	
    	return richiestaRegistrazioneAvvisaturaDTO.getCodiceFiscaleDebitore(); // questione TIN
    }
    
    @Override
    public String createIntestatarioRegistrazioneAvvisatura(RichiestaRegistrazioneAvvisaturaDTO richiestaRegistrazioneAvvisaturaDTO) {
    	
    	final int AVVISO_PENDENZA_IN_SCADENZA = 2;
    	final int AVVISO_RICEZIONE_RT_NODO_DEI_PAGAMENTI = 7;
    	
    	CodiceFiscale codiceFiscale = new CodiceFiscale(richiestaRegistrazioneAvvisaturaDTO.getCodiceFiscaleDebitore());
    	String nazione = codiceFiscale.getNazione();
    	String codiceFiscaleDebitore = codiceFiscale.getCodiceFiscale();
    	
    	Intestatari intestatario = fillIntestatario(
    			Categoria.CI.toString(), 
    			UUIDGenerator20.generate(), 
    			codiceFiscaleDebitore,
    			nazione,
    			richiestaRegistrazioneAvvisaturaDTO.getNomeDebitore() + " " + richiestaRegistrazioneAvvisaturaDTO.getCognomeDebitore());
    	System.out.println(intestatario.getCorporate()); // TODO
		intestatario = intestatariDAO.createIntestatario(intestatario);
    	
		Operatori operatore = fillOperatore(intestatario, codiceFiscaleDebitore, richiestaRegistrazioneAvvisaturaDTO.getNomeDebitore(), richiestaRegistrazioneAvvisaturaDTO.getCognomeDebitore());
		operatore.setOperatore(UUIDGenerator20.generate());
		
		IntestatarioperatoriId idIntestatariOperatori = new IntestatarioperatoriId();
		idIntestatariOperatori.setIntestatario(intestatario.getCorporate());
		idIntestatariOperatori.setOperatore(operatore.getOperatore());
		
		Intestatarioperatori intestatarioOperatore = new Intestatarioperatori(idIntestatariOperatori);
		intestatarioOperatore.setLockedIForm("0"); // TODO
		intestatarioOperatore.setTipoOperatore(TipoOperatore.OP.toString());
		intestatarioOperatore.setIntestatario(intestatario);
    	
		Set<IntestatarioperatoriCommon> listaIntestatariOperatori = new HashSet<IntestatarioperatoriCommon>();
		listaIntestatariOperatori.add(intestatarioOperatore);

		operatore.setIntestatarioperatori(listaIntestatariOperatori);
		System.out.println(operatore.getOperatore()); // TODO
		operatoriDAO.createOperatore(operatore);
		
    	for (HashMap<String, String> infoCanale : richiestaRegistrazioneAvvisaturaDTO.getCanali()) {

    		String idUtente = intestatario.getCorporate();
    		
    		for (String denominazioneCanale : DenominazioniCanali.descriptions()) {
    			if (infoCanale.containsKey(denominazioneCanale)) {
        			List<Canali> listaCanali = canaliDao.findCanaliByDenominazione(denominazioneCanale);
        			for (Canali canale : listaCanali) {
        				if (canale.getIdTipoMessaggio() == AVVISO_PENDENZA_IN_SCADENZA || canale.getIdTipoMessaggio() == AVVISO_RICEZIONE_RT_NODO_DEI_PAGAMENTI) {
	        				UtentiCanali utenteCanale = fillUtenteCanale(idUtente, canale.getId(), infoCanale.get(denominazioneCanale));
	        				utentiCanaliDao.saveUtentiCanali(utenteCanale);
        				}
        			}
        		}
    		}
    	}

    	return richiestaRegistrazioneAvvisaturaDTO.getCodiceFiscaleDebitore(); // questione TIN
    };

    @Override
    public String deleteIntestatarioRegistrazioneAvvisatura(Intestatari intestatario, RichiestaCancellazioneAvvisaturaDTO richiestaCancellazioneAvvisaturaDTO) {
    	intestatario.setFlagEnrollmentAvvisatura(FlagEnrollmentAvvisatura.N.toString());
    	utentiCanaliDao.deleteUtentiCanaliByUtente(intestatario.getCorporate());
    	return richiestaCancellazioneAvvisaturaDTO.getCodiceFiscaleDebitore(); // questione TIN
    }

    @Override
    public RispostaRicercaAvvisaturaDTO searchIntestatarioRegistrazioneAvvisatura(Intestatari intestatario, RichiestaRicercaAvvisaturaDTO richiestaRicercaAvvisaturaDTO) {

    	RispostaRicercaAvvisaturaDTO rispostaRicercaAvvisaturaDTO = new RispostaRicercaAvvisaturaDTO();
    	
    	Operatori operatore = operatoriDAO.getOperatoreByCodiceFiscale(intestatario.getLapl());
    	rispostaRicercaAvvisaturaDTO.setCodiceFiscaleDebitore(richiestaRicercaAvvisaturaDTO.getCodiceFiscaleDebitore()); // questione TIN - ex: operatore.getCodiceFiscale()
    	rispostaRicercaAvvisaturaDTO.setNomeDebitore(operatore.getName());
    	rispostaRicercaAvvisaturaDTO.setCognomeDebitore(operatore.getSurname());
    	rispostaRicercaAvvisaturaDTO.setCanali(new ArrayList<HashMap<String, String>>());
    	
    	ArrayList<String> listaDenominazioni = new ArrayList<String>(Arrays.asList(DenominazioniCanali.descriptions()));
    	List<UtentiCanali> listaUtentiCanali = utentiCanaliDao.findUtentiCanaliByUtente(intestatario.getCorporate());
    	for (UtentiCanali utenteCanale : listaUtentiCanali) {
    		String denominazione = utenteCanale.getCanale().getDenominazione();
    		HashMap<String, String> infoCanale = new HashMap<String, String>();
    		if (listaDenominazioni.remove(denominazione)) {
    			if (DenominazioniCanali.OPEN_TOSCANA.getDescription().equalsIgnoreCase(denominazione)) {
    				infoCanale.put(denominazione, richiestaRicercaAvvisaturaDTO.getCodiceFiscaleDebitore()); // questione TIN
    			} else {
    				infoCanale.put(denominazione, utenteCanale.getConfigurazione());
    			}
    			rispostaRicercaAvvisaturaDTO.getCanali().add(infoCanale);
    		}
    	}

    	return rispostaRicercaAvvisaturaDTO;
    }
    
    private UtentiCanali fillUtenteCanale(String idUtente, long idCanale, String configurazione) {
    	UtentiCanaliPK utentiCanaliPK = new UtentiCanaliPK();
    	utentiCanaliPK.setIdUtente(idUtente);
    	utentiCanaliPK.setIdCanale(idCanale);
    	UtentiCanali utentiCanali = new UtentiCanali();
    	utentiCanali.setId(utentiCanaliPK);
    	utentiCanali.setConfigurazione(configurazione);
    	utentiCanali.setIsAnonymous(false);
    	utentiCanali.setStato("ATTIVO"); // TODO
    	utentiCanali.setOpInserimento("REGISTRAZIONE_AVVISATURA"); // TODO
    	utentiCanali.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    	return utentiCanali;
    }

    private Intestatari fillIntestatario(String categoria, String corporateId, String codiceFiscale, String nazione, String ragioneSociale) {
		Intestatari intestatario = new Intestatari();
		intestatario.setCategoria(categoria);
		intestatario.setCorporate(corporateId);
		intestatario.setLapl(codiceFiscale);
		intestatario.setRagionesociale(ragioneSociale);
		intestatario.setIndirizzipostaliobj(fillIndirizzo(codiceFiscale, nazione));
		intestatario.setFlagEnrollmentAvvisatura(FlagEnrollmentAvvisatura.Y.toString());
		return intestatario;
	}

	private IndirizzipostaliCommon fillIndirizzo(String codiceFiscale, String nazione) {
		Indirizzipostali indirizzoPostale = new Indirizzipostali();
   		indirizzoPostale.setCountry(nazione);
    	indirizzoPostale.setFiscalCodeIForm(codiceFiscale);
		return indirizzoPostale;
	}
    
	private Operatori fillOperatore(Intestatari intestatario, String codiceFiscale, String nome, String cognome) {
		Operatori operatore = new Operatori();
		operatore.setIntestatariobj(intestatario);
		operatore.setNumFailedlogon(new Integer(0));
		operatore.setLocked(new Integer(0));
		operatore.setPlainPassword(CommonConstant.DEFAULT_PASSWORD);
		operatore.setUsername(codiceFiscale);
		operatore.setCodiceFiscale(codiceFiscale);
		operatore.setName(nome);
		operatore.setSurname(cognome);
		Calendar cal = Calendar.getInstance();
		cal.set(CommonConstant.LONG_EXPIRING_DATE_YYYY, CommonConstant.LONG_EXPIRING_DATE_MM, CommonConstant.LONG_EXPIRING_DATE_DD);
		operatore.setExpirationDate(cal.getTime());
		return operatore;
	}

}
