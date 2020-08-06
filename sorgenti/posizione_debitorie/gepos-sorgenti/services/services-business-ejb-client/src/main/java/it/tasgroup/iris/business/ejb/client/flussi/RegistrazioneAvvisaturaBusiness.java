package it.tasgroup.iris.business.ejb.client.flussi;

import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.dto.flussi.RichiestaCancellazioneAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RichiestaRegistrazioneAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RichiestaRicercaAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RispostaRicercaAvvisaturaDTO;

public interface RegistrazioneAvvisaturaBusiness {

	Intestatari getIntestatario(String codiceFiscaleDebitore);
	String createIntestatarioRegistrazioneAvvisatura(RichiestaRegistrazioneAvvisaturaDTO datiRegistrazioneAvvisaturaDTO);
	String updateIntestatarioRegistrazioneAvvisatura(Intestatari intestatario, RichiestaRegistrazioneAvvisaturaDTO datiRegistrazioneAvvisaturaDTO);
	String deleteIntestatarioRegistrazioneAvvisatura(Intestatari intestatario, RichiestaCancellazioneAvvisaturaDTO datiCancellazioneAvvisaturaDTO);
	RispostaRicercaAvvisaturaDTO searchIntestatarioRegistrazioneAvvisatura(Intestatari intestatario, RichiestaRicercaAvvisaturaDTO richiestaRicercaAvvisaturaDTO);

}
