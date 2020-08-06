package it.tasgroup.idp.billerservices.api.plugin;

import it.tasgroup.idp.billerservices.api.GestorePendenze.EnumTipoAllineamento;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

public interface ILoaderPlugin {

	// Strutture dati nelle signature dei metodi.
	
	// Struttura dati esito validazione
	public static class EsitoValidazione {
		public boolean ok;
		public List<ValidationException> errori;
		public String msgEsito;
	}

	
	public static class DatiAllineamento implements Serializable {
		public Pendenze pendenzaDaAllineare;  		//la pendenza da allineare
		public EnumTipoAllineamento tipoAllineamento;
		
	}

	public static class EsitoCaricamentoPendenza {
		public String statoEsito;
		public String descrizioneEsito;
		public String codiceErrore;
		public int progressivo;
		public String idDebito;
		public String iuv;
		public String idPagamento;
	}
	
	public DatiPiazzaturaFlusso piazzaturaFlusso () throws ValidationException; 
	public EsitoValidazione validazioneFlusso(DatiPiazzaturaFlusso datiPiazzaturaFlusso,int numeroMassimoErrori, EntityManager em) ;
	public void  moveFirst();
	public DatiAllineamento elaboraNextPendenza(DatiPiazzaturaFlusso datiPiazzaturaFlusso, Long progressivo) throws ValidationException, IOException ;
	public void marshallEsitoOnStream(List<EsitoCaricamentoPendenza> esitiPendenze, OutputStream os);
    public String getTipoFile();
}
