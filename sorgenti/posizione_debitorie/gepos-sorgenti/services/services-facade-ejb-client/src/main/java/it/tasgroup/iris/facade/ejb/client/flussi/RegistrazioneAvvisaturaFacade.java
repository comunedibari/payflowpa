package it.tasgroup.iris.facade.ejb.client.flussi;

import it.tasgroup.iris.dto.flussi.RichiestaCancellazioneAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RichiestaRegistrazioneAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RichiestaRicercaAvvisaturaDTO;
import it.tasgroup.iris.dto.flussi.RispostaRicercaAvvisaturaDTO;

public interface RegistrazioneAvvisaturaFacade {

	String registra(RichiestaRegistrazioneAvvisaturaDTO datiRegistrazioneAvvisaturaDTO);
	String cancella(RichiestaCancellazioneAvvisaturaDTO datiCancellazioneAvvisaturaDTO);
	RispostaRicercaAvvisaturaDTO ricerca(RichiestaRicercaAvvisaturaDTO datiRicercaAvvisaturaDTO);

}
