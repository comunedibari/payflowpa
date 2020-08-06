/**
 * 
 */
package it.tasgroup.iris.payment.helper;

import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.exception.IncompleteRegistrationException;

import org.apache.commons.lang.StringUtils;

/**
 * @author pazzik
 * 
 */
public class IntestatarioValidator {

	public static void validateVersanteFESP(IntestatarioDTO versante, OperatoreDTO operatore) throws IncompleteRegistrationException {

		String codFiscale = versante.getIdFiscale();

		if (StringUtils.isEmpty(codFiscale) || codFiscale.length() > 16)
			throw new IncompleteRegistrationException("Identificativo Fiscale", codFiscale);

		IndirizzoDTO indirizzoVersante = versante.getIndirizzo();

		String nazioneVersante = indirizzoVersante.getNazione();

		if (StringUtils.isEmpty(nazioneVersante) || nazioneVersante.length() < 2)			
			throw new IncompleteRegistrationException("Nazione", nazioneVersante);

		nazioneVersante = nazioneVersante.substring(0, 2);

		if (!"IT".equals(nazioneVersante))
			throw new IncompleteRegistrationException("Nazione", nazioneVersante);

		// TODO PAZZIK VALIDARE LA PROVINCIA CONFRONTANDOLA CON LA TABELLA

		String provinciaVersante = indirizzoVersante.getProvincia();

		if (StringUtils.isEmpty(provinciaVersante) || provinciaVersante.length() > 2)
			throw new IncompleteRegistrationException("Provincia",provinciaVersante);

		String capVersante = indirizzoVersante.getCap();

		if (StringUtils.isEmpty(capVersante) || capVersante.length() > 5)
			throw new IncompleteRegistrationException("CAP", capVersante);

		String civicoVersante = indirizzoVersante.getNumeroCivico();

		if (StringUtils.isEmpty(civicoVersante) || civicoVersante.length() > 16)
			throw new IncompleteRegistrationException("Numero Civico", civicoVersante);

		String viaVersante = indirizzoVersante.getVia();

		if (StringUtils.isEmpty(viaVersante))
			throw new IncompleteRegistrationException("Via", viaVersante);

		String cittaVersante = indirizzoVersante.getCitta();

		if (StringUtils.isEmpty(cittaVersante))
			throw new IncompleteRegistrationException("Citta'", cittaVersante);

		String emailVersante = versante.getEmail();

		if (StringUtils.isEmpty(emailVersante))
			throw new IncompleteRegistrationException("EMail", emailVersante);

	}

}
