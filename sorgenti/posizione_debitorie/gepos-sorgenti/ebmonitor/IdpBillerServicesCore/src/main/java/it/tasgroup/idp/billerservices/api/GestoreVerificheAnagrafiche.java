package it.tasgroup.idp.billerservices.api;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.tasgroup.idp.domain.bonifici.Intestatari;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.Sil;
import it.tasgroup.idp.domain.enti.SilPK;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;

public class GestoreVerificheAnagrafiche {
	
	
	public static EnumReturnValues checkAnagrafica(String senderId, String senderSys, String tipoDebito, EntityManager em) throws LoaderException {

		Enti ente = getEnteByCodEnte(senderId,em);
		
		// Se arrivo qui l'Ente è ok
		// Cerco il SIL
		//------------------------------------
		SilPK  silPk  = new SilPK();
		silPk.setIdEnte(ente.getIdEnte());
		silPk.setIdSystem(senderSys);
		
		Sil sil = em.find(Sil.class, silPk);

		if (sil==null) {
			return EnumReturnValues.SENDER_SYS_INESISTENTE;
		}
		
		if (!"A".equals(sil.getStato())) {
			return EnumReturnValues.SENDER_SYS_DISABILITATO;
		}
		
		// Se arrivo qui il SIl è OK
		// cerco il tributo
		//------------------------------------
		TributiEntiPK tributiEntiPk = new TributiEntiPK();
		tributiEntiPk.setCdTrbEnte(tipoDebito);
		tributiEntiPk.setIdEnte(ente.getIdEnte());
		
		TributiEnti t = (TributiEnti) em.find(TributiEnti.class, tributiEntiPk);
		
		if (t==null) {
			return EnumReturnValues.TIPO_DEBITO_SCONOSCIUTO;
		} else if (!"A".equals(t.getStato())) {
			return EnumReturnValues.TIPO_DEBITO_DISABILITATO;
		} else if (!t.getIdSystem().equals(sil.getId().getIdSystem())) {
			return EnumReturnValues.TIPO_DEBITO_NON_COERENTE_SENDER_SYS;
		}
		
		
		return EnumReturnValues.OK;
	}
	
	
	public static Sil getSilForEnteAndTipoDebito(String codiceEnte, String codiceTributoEnte, EntityManager em) throws LoaderException {
		Enti ente= getEnteByCodEnte(codiceEnte,em);  
		TributiEnti tributoEnte = getTributoEnteByIdEnteCodTributo(ente.getIdEnte(), codiceTributoEnte, em); // no exceptions = Ok.   
		String silId = tributoEnte.getIdSystem();
		SilPK pk = new SilPK();
		pk.setIdEnte(ente.getIdEnte());
		pk.setIdSystem(tributoEnte.getIdSystem());
		Sil aSil = em.find(Sil.class, pk);
		return aSil;
	}

	public static List<Enti> getEntiByCodEnte(String codEnte, EntityManager em) {
		String qEnti = " from Enti where cdEnte=:cdEnte and stato='A'";
		Query q = em.createQuery(qEnti);
		q.setParameter("cdEnte", codEnte);
		return q.getResultList();
		
	}
	
	public static Enti getEnteByCodEnte(String codEnte, EntityManager em) throws LoaderException {
		List<Enti> enti = getEntiByCodEnte(codEnte, em);
		if (enti==null || enti.size()==0) {
			throw new LoaderException(EnumReturnValues.SENDER_ID_INESISTENTE);
		} else if (enti.size()>1) {
			// Non dovrebbe mai succedere
			throw new LoaderException(EnumReturnValues.SENDER_ID_NON_UNIVOCO);
		}
		Enti ente= enti.get(0);
		if (!"A".equals(ente.getStato())) {
			throw new LoaderException(EnumReturnValues.SENDER_ID_DISABILITATO);
		}		
		return ente;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Intestatari> getIntestatarioByFiscalCodeAndLapl(String fiscalCode, String lapl, EntityManager em) {
		return (List<Intestatari>)em.createNamedQuery("IntestatarioByLaplAndCF")
				   .setParameter("lapl", lapl)
				   .setParameter("fiscalCode", fiscalCode)
				   .getResultList();
	}
	
	public static Intestatari getIntestatarioById(String idIntestatario, EntityManager em) {
		return em.find(Intestatari.class, idIntestatario);
	}
	
	public static TributiEnti getTributoEnteByIdEnteCodTributo(String idEnte, String codTributoEnte, EntityManager em) throws LoaderException {
		TributiEntiPK pk  = new TributiEntiPK();
		pk.setIdEnte(idEnte);
		pk.setCdTrbEnte(codTributoEnte);
		TributiEnti tributoEnte = em.find(TributiEnti.class, pk);
		if (tributoEnte==null) {
			throw new LoaderException(EnumReturnValues.TIPO_DEBITO_SCONOSCIUTO);
		}
		if (!"A".equals(tributoEnte.getStato())) {
			throw new LoaderException(EnumReturnValues.TIPO_DEBITO_DISABILITATO);
		}
		return tributoEnte;
	
	}
	
}
