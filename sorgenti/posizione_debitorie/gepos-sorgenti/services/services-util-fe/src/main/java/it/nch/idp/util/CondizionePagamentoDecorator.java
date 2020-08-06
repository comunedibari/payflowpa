package it.nch.idp.util;

import it.tasgroup.idp.rs.model.creditore.CondizionePagamento;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import org.displaytag.decorator.TableDecorator;

/**
 *
 */
public class CondizionePagamentoDecorator  extends TableDecorator  {
	
	public String getAttestante() {
		final CondizionePagamento condizionePagamento = (CondizionePagamento) getCurrentRowObject();
		final Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso = condizionePagamento.getInformazioniTransazioneIncasso();
		
		if (informazioniTransazioneIncasso == null || informazioniTransazioneIncasso.getTipoIdentificativoAttestatante() == null || "".equals(informazioniTransazioneIncasso.getTipoIdentificativoAttestatante().trim())) {
			return "";
		}
		
		final String tipoIdentificativoAttestatante = informazioniTransazioneIncasso.getTipoIdentificativoAttestatante();
		final String descrizioneAttestante = informazioniTransazioneIncasso.getDescrizioneAttestante();
		final String identificativoAttestante = informazioniTransazioneIncasso.getIdentificativoAttestante();
		
		String expandedDesc = "";
		if ("B".equals(tipoIdentificativoAttestatante)) {
			expandedDesc = "BIC";
		} else if ("G".equals(tipoIdentificativoAttestatante)) {
			expandedDesc = "C.F";
		} else if ("A".equals(tipoIdentificativoAttestatante)) {
			expandedDesc = "ABI";
		}
		
		
		return descrizioneAttestante + " ( " + expandedDesc + ": " + identificativoAttestante + " )";
	}
}
