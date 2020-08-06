package it.tasgroup.idp.cart.core.quadratura;

import java.util.List;

import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;

public class Report {

	
	private TipoMessaggio tipo;
	
	private long richiesteRicevute;
	private long richiesteAcquisite;
	private long esitiInviati;
	private long richiesteEsitate;
	private long richiesteDaEsitare;
	private long richiesteNonConsegnate;
	
	private long richiesteRicevuteOTF;
	private long richiesteAcquisiteOTF;
	private long esitiInviatiOTF;
	private long richiesteEsitateOTF;
	private long richiesteDaEsitareOTF;
	private long richiesteNonConsegnateOTF;
	
	private List<MessaggioModel> messaggiNonEsitati;
	private List<MessaggioModel> messaggiNonConsegnati;
	// Distribuzioni Temporali
	private List<Res> tempoMedioGestioneRichieste;
	private List<Res> tempoMedioGestioneEsiti;
	
	private List<Res> richiesteOk;
	private List<Res> richiesteRic;
	private List<Res> esitiInv;
	private List<Res> esitiOk;
	
	private List<Err> erroriFrequenti;
	
	public TipoMessaggio getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoMessaggio tipo) {
		this.tipo = tipo;
	}
	public List<MessaggioModel> getMessaggiNonEsitati() {
		return messaggiNonEsitati;
	}
	public void setMessaggiNonEsitati(List<MessaggioModel> messaggiNonEsitati) {
		this.messaggiNonEsitati = messaggiNonEsitati;
	}
	public List<Res> getTempoMedioGestioneRichieste() {
		return tempoMedioGestioneRichieste;
	}
	public void setTempoMedioGestioneRichieste(List<Res> tempoMedioGestioneRichieste) {
		this.tempoMedioGestioneRichieste = tempoMedioGestioneRichieste;
	}
	public List<Res> getDistribuzioneRichiesteAcquisite() {
		return richiesteOk;
	}
	public void setDistribuzioneRichiesteAcquisite(List<Res> richiesteOk) {
		this.richiesteOk = richiesteOk;
	}
	public List<Res> getTempoMedioGestioneEsiti() {
		return tempoMedioGestioneEsiti;
	}
	public void setTempoMedioGestioneEsiti(List<Res> tempoMedioGestioneEsiti) {
		this.tempoMedioGestioneEsiti = tempoMedioGestioneEsiti;
	}
	public List<Err> getErroriFrequenti() {
		return erroriFrequenti;
	}
	public void setErroriFrequenti(List<Err> erroriFrequenti) {
		this.erroriFrequenti = erroriFrequenti;
	}
	public List<Res> getDistribuzioneEsitiConsegnati() {
		return esitiOk;
	}
	public void setDistribuzioneEsitiConsegnati(List<Res> esitiOk) {
		this.esitiOk = esitiOk;
	}
	public long getRichiesteRicevute() {
		return richiesteRicevute;
	}
	public void setRichiesteRicevute(long richiesteRicevute) {
		this.richiesteRicevute = richiesteRicevute;
	}
	public long getRichiesteAcquisite() {
		return richiesteAcquisite;
	}
	public void setRichiesteAcquisite(long richiesteAcquisite) {
		this.richiesteAcquisite = richiesteAcquisite;
	}
	public long getRichiesteEsitate() {
		return richiesteEsitate;
	}
	public void setRichiesteEsitate(long richiesteEsitate) {
		this.richiesteEsitate = richiesteEsitate;
	}
	public long getRichiesteDaEsitare() {
		return richiesteDaEsitare;
	}
	public void setRichiesteDaEsitare(long richiesteDaEsitare) {
		this.richiesteDaEsitare = richiesteDaEsitare;
	}
	public List<Res> getDistribuzioneRichiesteRicevute() {
		return richiesteRic;
	}
	public void setDistribuzioneRichiesteRicevute(List<Res> richiesteRic) {
		this.richiesteRic = richiesteRic;
	}
	public long getEsitiInviati() {
		return esitiInviati;
	}
	public void setEsitiInviati(long esitiInviati) {
		this.esitiInviati = esitiInviati;
	}
	public List<Res> getDistribuzioneEsitiInviati() {
		return esitiInv;
	}
	public void setDistribuzioneEsitiInviati(List<Res> esitiInv) {
		this.esitiInv = esitiInv;
	}

	public long getRichiesteRicevuteOTF() {
		return richiesteRicevuteOTF;
	}

	public void setRichiesteRicevuteOTF(long richiesteRicevuteOTF) {
		this.richiesteRicevuteOTF = richiesteRicevuteOTF;
	}

	public long getRichiesteAcquisiteOTF() {
		return richiesteAcquisiteOTF;
	}

	public void setRichiesteAcquisiteOTF(long richiesteAcquisiteOTF) {
		this.richiesteAcquisiteOTF = richiesteAcquisiteOTF;
	}

	public long getEsitiInviatiOTF() {
		return esitiInviatiOTF;
	}

	public void setEsitiInviatiOTF(long esitiInviatiOTF) {
		this.esitiInviatiOTF = esitiInviatiOTF;
	}

	public long getRichiesteEsitateOTF() {
		return richiesteEsitateOTF;
	}

	public void setRichiesteEsitateOTF(long richiesteEsitateOTF) {
		this.richiesteEsitateOTF = richiesteEsitateOTF;
	}

	public long getRichiesteDaEsitareOTF() {
		return richiesteDaEsitareOTF;
	}

	public void setRichiesteDaEsitareOTF(long richiesteDaEsitareOTF) {
		this.richiesteDaEsitareOTF = richiesteDaEsitareOTF;
	}

	public long getRichiesteNonConsegnate() {
		return richiesteNonConsegnate;
	}

	public void setRichiesteNonConsegnate(long richiesteNonConsegnate) {
		this.richiesteNonConsegnate = richiesteNonConsegnate;
	}

	public long getRichiesteNonConsegnateOTF() {
		return richiesteNonConsegnateOTF;
	}

	public void setRichiesteNonConsegnateOTF(long richiesteNonConsegnateOTF) {
		this.richiesteNonConsegnateOTF = richiesteNonConsegnateOTF;
	}

	public List<MessaggioModel> getMessaggiNonConsegnati() {
		return messaggiNonConsegnati;
	}

	public void setMessaggiNonConsegnati(List<MessaggioModel> messaggiNonConsegnati) {
		this.messaggiNonConsegnati = messaggiNonConsegnati;
	}
	
	
}
