package it.tasgroup.iris.facade.ejb.flussi;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.util.CodiceFiscale;
import it.nch.utility.enumeration.FlagEnrollmentAvvisatura;
import it.tasgroup.iris.business.ejb.client.flussi.RegistrazioneAvvisaturaBusinessLocal;
import it.tasgroup.iris.dto.flussi.RichiestaCancellazioneAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RichiestaRegistrazioneAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RichiestaRicercaAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RispostaRicercaAvvisaturaDTO;
import it.tasgroup.iris.facade.ejb.client.flussi.RegistrazioneAvvisaturaFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.flussi.RegistrazioneAvvisaturaFacadeRemote;

@Stateless(name = "RegistrazioneAvvisaturaFacade")
public class RegistrazioneAvvisaturaFacadeBean implements RegistrazioneAvvisaturaFacadeLocal, RegistrazioneAvvisaturaFacadeRemote {

//	private static final Logger LOGGER = LogManager.getLogger(RegistrazioneAvvisaturaFacadeBean.class);

	@EJB(name = "RegistrazioneAvvisaturaBusiness")
	private RegistrazioneAvvisaturaBusinessLocal registrazioneAvvisaturaBusinessBean;
	
	@Override
	public String registra(RichiestaRegistrazioneAvvisaturaDTO richiestaRegistrazioneAvvisaturaDTO) {
		
		CodiceFiscale codiceFiscale = new CodiceFiscale(richiestaRegistrazioneAvvisaturaDTO.getCodiceFiscaleDebitore());
    	String codiceFiscaleDebitore = codiceFiscale.getCodiceFiscale();
		
		Intestatari intestatario = registrazioneAvvisaturaBusinessBean.getIntestatario(codiceFiscaleDebitore);

		String cfAsIs = null;
		if (intestatario == null) {
			cfAsIs = registrazioneAvvisaturaBusinessBean.createIntestatarioRegistrazioneAvvisatura(richiestaRegistrazioneAvvisaturaDTO);
		} else {
			cfAsIs = registrazioneAvvisaturaBusinessBean.updateIntestatarioRegistrazioneAvvisatura(intestatario, richiestaRegistrazioneAvvisaturaDTO);
		}
		
		return cfAsIs;
		
	}
	
	@Override
	public String cancella(RichiestaCancellazioneAvvisaturaDTO richiestaCancellazioneAvvisaturaDTO) {

		CodiceFiscale codiceFiscale = new CodiceFiscale(richiestaCancellazioneAvvisaturaDTO.getCodiceFiscaleDebitore());
    	String codiceFiscaleDebitore = codiceFiscale.getCodiceFiscale();
		
		Intestatari intestatario = registrazioneAvvisaturaBusinessBean.getIntestatario(codiceFiscaleDebitore);

		String cfAsIs = null;
		if (intestatario == null) {
			return null;
		} else {
			cfAsIs = registrazioneAvvisaturaBusinessBean.deleteIntestatarioRegistrazioneAvvisatura(intestatario, richiestaCancellazioneAvvisaturaDTO);
		}
		
		return cfAsIs;

	}
	
	@Override
	public RispostaRicercaAvvisaturaDTO ricerca(RichiestaRicercaAvvisaturaDTO richiestaRicercaAvvisaturaDTO) {

		CodiceFiscale codiceFiscale = new CodiceFiscale(richiestaRicercaAvvisaturaDTO.getCodiceFiscaleDebitore());
    	String codiceFiscaleDebitore = codiceFiscale.getCodiceFiscale();
		
		Intestatari intestatario = registrazioneAvvisaturaBusinessBean.getIntestatario(codiceFiscaleDebitore);
		RispostaRicercaAvvisaturaDTO rispostaRicercaAvvisaturaDTO = null;

		if (intestatario == null || FlagEnrollmentAvvisatura.N.toString().equals(intestatario.getFlagEnrollmentAvvisatura())) {
			return null;
		} else {
			rispostaRicercaAvvisaturaDTO = registrazioneAvvisaturaBusinessBean.searchIntestatarioRegistrazioneAvvisatura(intestatario, richiestaRicercaAvvisaturaDTO);
			
		}
		
		return rispostaRicercaAvvisaturaDTO;
	}
	
}
