package it.tasgroup.idp.generazioneiuv;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.tasgroup.idp.domain.bonifici.Intestatari;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.util.EnumTipiErrori;
import it.tasgroup.idp.util.IrisProperties;


public class GestoreGenerazioneIUV {
	
	private static List AUX_DIGIT_VALIDI = Arrays.asList(new String[] {"0", "1", "2", "3"});
	
	public static void validaRichiestaGenerazioneIUV(String codiceEnte,String codiceTributo,int dimensioneLotto, EntityManager em) throws ValidationGenerazioneIUVException {
		// Obbligatori
		checkNotNull("Codice Ente", codiceEnte);
		checkNotNull("Codice Tributo", codiceTributo);
		checkNotNull("Numero Richieste IUV", dimensioneLotto);
		
		// controllo che il numero di richieste di generazione IUV non sia maggiore del massimo consentito
		// da leggere da properties
		String dimensioneMassimaLotto=null;
		try {
			dimensioneMassimaLotto = IrisProperties.getProperty("generazioneiuv.dimensione.lotto", true);
		} catch (IOException e1) {
			
		}
		if (dimensioneMassimaLotto==null ){
			dimensioneMassimaLotto = new String("1000");
		}
		
		
		if (dimensioneLotto >Integer.parseInt(dimensioneMassimaLotto))
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.ERRORE_DIMENSIONE_LOTTO);
	}

	
	
	public static TributiEnti getTributoEnte(String senderId, String tipoDebito, EntityManager em) throws ValidationGenerazioneIUVException {

		// cerco l'intestatario

		Intestatari inte = getIntestatarioByIdentificativoFiscale(senderId, em);
		// cerco l'ente
		Enti ente = getEnteByCodEnte(inte.getIntestatario(), em);

		// cerco il tributo
		// ------------------------------------
		TributiEntiPK tributiEntiPk = new TributiEntiPK();
		tributiEntiPk.setCdTrbEnte(tipoDebito);
		tributiEntiPk.setIdEnte(ente.getIdEnte());

		TributiEnti t = (TributiEnti) em.find(TributiEnti.class, tributiEntiPk);

		if (t == null) {
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.TIPO_DEBITO_SCONOSCIUTO);
		}

		if (t.getNdpIuvStartNum() == null || t.getNdpIuvStartNum().equals(0))
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.TIPO_TRIBUTO_SENZA_RANGE);
		
		if (t.getNdpAuxDigit() == null || !AUX_DIGIT_VALIDI.contains(t.getNdpAuxDigit()))
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.AUX_DIGIT_NON_VALIDO);
		
		if (t.getNdpCodStazPa() == null)
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.COD_STAZ_PA_ASSENTE);
		
		return t;
	}
	
	

	
	
	public static Enti getEnteByCodEnte(String codEnte, EntityManager em) throws ValidationGenerazioneIUVException {
		String qEnti = " from Enti where intestatario=:cdEnte and stato='A'";
		Query q = em.createQuery(qEnti);
		q.setParameter("cdEnte", codEnte);
		List<Enti> enti=q.getResultList();
		if (enti==null || enti.size()==0) {
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.SENDER_ID_INESISTENTE);
		} 
		Enti ente= enti.get(0);
		return ente;
	}
	
	private static Intestatari getIntestatarioByIdentificativoFiscale(String identificativo, EntityManager em)throws ValidationGenerazioneIUVException{
		Query q =em.createNamedQuery("IntestatarioByLapl");
		q.setParameter("lapl", identificativo);
		List<Intestatari> l =q.getResultList();
		if (l==null || l.size()==0) {
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.SENDER_ID_INESISTENTE);
		} 
		Intestatari inte= l.get(0);
		return inte;
	}
	
	private static void checkNotNull(String nomeCampo, String campo) throws ValidationGenerazioneIUVException {
		if(campo == null || campo.equals("")) {
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.ERRORE_SCHEMA_FILE,"Campo obbligatorio assente: '"+nomeCampo+"'");
		}
	}
	
	
	
	private static void checkNotNull(String nomeCampo, int campo) throws ValidationGenerazioneIUVException {
		if(campo == 0) {
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.ERRORE_SCHEMA_FILE,"Campo obbligatorio assente: '"+nomeCampo+"'");
		}
	}
	
}
