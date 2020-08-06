package it.nch.is.fo.business.core.dao;

import it.nch.fwk.fo.core.SessionManagerHibernate;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.is.fo.profilo.Enti;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.Collection;
import java.util.HashMap;

import org.hibernate.Query;

public class SPXAlignHelper {
	
	/*
	 * Funzione di allineamento della base dati dello SmartProxy.
	 * La funzione, che agisce solo se � abilitata la property "smartproxy.automaticalignement.enabled" di iris_be.
	 * Provvede ad alimentare le tabelle di profilo dello Smart Proxy sulla base dei dati passati come parametro:
	 * codiceEnte
	 * codiceSIL
	 * codiceTributo
	 * 
	 * La funzione controlla gli allineamenti in modo da non censire dati che sono gi� presenti nel db smartproxy.
	 * 
	 * La funzione non procede volutamente a cancellare record dal db di profilazione  dello smart proxy.
	 * Questo perch� lo smart proxy � un "convertitore evoluto" da CSV a XML. l'XML viene poi inviato a iris, che effettua
	 * una validazione di merito in fase di caricamento.
	 * Quindi se nel profilo SmartProxy ci sono censimenti in pi� rispetto a quanto effettivamente attivo su iris, allora 
	 * l'unico effetto � che avremo degli errori di validazione sul back end di IRIS invece che direttamente sullo smart proxy.
	 *  
	 */
	public static void AlignSPX(String codiceEnte, String codiceTributo, String codiceSIL, StatelessSessionManager sm) throws Exception {
	
		SessionManagerHibernate smHibernate = (SessionManagerHibernate) sm;
		org.hibernate.Session se = smHibernate.se;
		
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-be.properties");
		boolean spxAlignmentEnabled = cpl.getBooleanProperty("iris.smartproxy.automaticalign.enabled");
		
		
		if (!spxAlignmentEnabled) {
			System.out.println("SPX Alignment non abilitato. Nessun allineamento");
			return;
		}
		

		if (codiceEnte==null) {
			System.out.println("Manca il codiceEnte. Allineamento SPX non eseguito");
			return;
		}	
				

		/*
		 * RIALLINEAMENTO CODICE ENTE con spx principal e soggetto
		 */
				 
		// Controllo se il censimento � gi� stato fatto
		String principal = codiceEnte;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("principal", principal); 
		 
		Collection existingPrincipal = sm.createSQLQuery("select principal_utente from SPX_UTENTI where principal_utente = :principal ", params);

		if (existingPrincipal!=null && existingPrincipal.size()>0) {
				System.out.println("Principal Trovato. Allineamento SPX non necessario");
		}  else {	
				System.out.println("Principal non trovato. Allineamento SPX");
				Query q1 = se.createSQLQuery("INSERT INTO spx_utenti (principal_utente) VALUES (:principal)");
				q1.setString("principal",principal);
				int resultq1  = q1.executeUpdate();
				System.out.println("Principal creato: result ="+resultq1);
				Query q2 = se.createSQLQuery("INSERT INTO spx_utenti_plugins (id_utente, id_plugin, default_plugin) VALUES ( (SELECT id FROM spx_utenti WHERE principal_utente=:principal),1,1)");
				q2.setString("principal",principal);
				int resultq2  = q2.executeUpdate();
				sm.flush();
				System.out.println("Principal plugin creato: result ="+resultq2);			
		}

		Collection existingSoggetto = sm.createSQLQuery("select nome_soggetto from SPX_SOGGETTI where nome_soggetto = :principal ", params);

		if (existingSoggetto!=null && existingSoggetto.size()>0) {
			System.out.println("Soggetto trovato. Allineamento SPX non necessario");
		} else {	
			System.out.println("Soggetto trovato. Allineamento SPX non necessario");
			Query q3 = se.createSQLQuery("INSERT INTO spx_soggetti (nome_soggetto, principal_soggetto) VALUES (:principal, '')");
			q3.setString("principal",principal);
			int resultq3  = q3.executeUpdate();
			sm.flush();
			System.out.println("Soggetto creato: result ="+resultq3);	
		}


		if (codiceSIL==null) {
			System.out.println("Manca il codiceSIL. Allineamento SPX di spx_sil non eseguito");
			return;
		}	
	
		/**
		 * Riallineamento SIL su spx_sil
		 */
		String sil = codiceSIL;
		
		HashMap<String, String> params2 = new HashMap<String, String>();
		params2.put("principal", principal);
		params2.put("sil", sil);
		
		// Controllo se il censimento � gi� stato fatto
		Collection existingSIL = sm.createSQLQuery("select sil from spx_sil  where id_soggetto = (SELECT id FROM spx_soggetti WHERE nome_soggetto = :principal)  and sil=:sil",params2);


		if (existingSIL!=null && existingSIL.size()>0) {
			System.out.println("SIL trovato. Allineamento SPX non necessario");
		}  else {
			// I need to align
			System.out.println("SIL non trovato. Allineamento SPX  necessario");
			Query q1 = se.createSQLQuery("INSERT INTO spx_sil (sil, nome_cart127, nome_cart145, id_soggetto, id_utente) VALUES (:sil, :sil127 , :sil145 , (SELECT id FROM spx_soggetti WHERE nome_soggetto = :principal) , (SELECT id FROM spx_utenti WHERE principal_utente=:principal))");
			q1.setString("principal",principal);
			q1.setString("sil", sil);
			q1.setString("sil127", principal+"."+sil);
			q1.setString("sil145", principal+"."+sil);
			
			int resultq1  = q1.executeUpdate();
			sm.flush();
			System.out.println("SIL creato: result ="+resultq1);
		}


		if (codiceTributo==null) {
			System.out.println("Manca il codiceTributo. Allineamento SPX di spx_tipi_debito e spx_debiti_soggetti non eseguito");
			return;
		}	

		
		/**
		 * Allineamento codici tributo su spx 
		 */
		
		String tributo = codiceTributo;
		
		HashMap<String, String> params3 = new HashMap<String, String>();
		params3.put("tributo", tributo);
		
		// Controllo se il censimento � gi� stato fatto
		Collection existingTributo = sm.createSQLQuery("select tipo_debito  from spx_tipi_debiti   where tipo_debito = :tributo ",params3);
		

		if (existingTributo!=null && existingTributo.size()>0) {
			System.out.println("Tributo trovato. Allineamento SPX non necessario");
		}  else {
			// I need to align
			System.out.println("Tributo non trovato. Allineamento SPX necessario");
			Query q1 = se.createSQLQuery("INSERT INTO spx_tipi_debiti (tipo_debito, descrizione) VALUES (:tributo, '-')");
			q1.setString("tributo",tributo);
			int resultq1  = q1.executeUpdate();
			sm.flush();
			System.out.println("Tributo creato: result ="+resultq1);
		}
		
		// Controllo se il censimento del collegamento tributo-sil � gi� presente.
		
		HashMap<String, String> params4 = new HashMap<String, String>();
		params4.put("tributo", tributo);
		params4.put("sil", sil);
		params4.put("principal",principal);

		Collection existingLink = sm.createSQLQuery("select * from spx_debiti_sil where id_sil=(select sil.id from spx_sil sil, spx_soggetti sog where sil.id_soggetto=sog.id and sog.nome_soggetto=:principal and sil.sil=:sil) and id_tipo_debito=(SELECT id FROM spx_tipi_debiti WHERE tipo_debito =:tributo) ",params4);
		

		if (existingLink!=null && existingLink.size()>0) {
			System.out.println("Link Tributo-Sil trovato. Allineamento SPX non necessario");
		}  else {
			// I need to align
			System.out.println("Link Tributo-Sil NON trovato. Allineamento SPX non necessario");

			HashMap<String, String> params0 = new HashMap<String, String>();
			params0.put("tributo", tributo);
			params0.put("principal",principal);

			
			// Se cambi SIL ad un tributo devi fare update e non insert.
			Collection existingLinkToUpdate = sm.createSQLQuery("select id_sil,id_tipo_debito, id_soggetto from spx_debiti_sil  where id_soggetto=(select id from spx_soggetti sog where sog.nome_soggetto=:principal) and id_tipo_debito=(SELECT id FROM spx_tipi_debiti WHERE tipo_debito =:tributo)",params0);

			if (existingLinkToUpdate!=null && existingLinkToUpdate.size()>0) {
								
				Object[] record = (Object[])existingLinkToUpdate.iterator().next();
				
				Long id_old_sil = ((Number)record[0]).longValue();
				Long id_tipo_debito = ((Number)record[1]).longValue();
				Long id_soggetto = ((Number)record[2]).longValue();
			
				System.out.println("Link Tributo-Sil precedentemente  associato. Update Necessario, per indici su soggetto, tributo che sarebbero violati");
				HashMap<String, String> params02 = new HashMap<String, String>();
				params02.put("principal", principal); 
				params02.put("sil", sil);
				
				// Recupero l'id del  nuovo SIL  
				Collection newSIL = sm.createSQLQuery("select id from spx_sil  where id_soggetto = (SELECT id FROM spx_soggetti WHERE nome_soggetto = :principal)  and sil=:sil",params02);
				Long id_new_sil = ((Number)newSIL.iterator().next()).longValue();				
 
			
				Query q01 = se.createSQLQuery("UPDATE spx_debiti_sil set id_sil=:id_sil where id_tipo_debito=:id_tipo_debito and id_soggetto=:id_soggetto");
				q01.setLong("id_sil", id_new_sil);
				q01.setLong("id_tipo_debito",id_tipo_debito);
				q01.setLong("id_soggetto", id_soggetto); 
				int resultq01  = q01.executeUpdate();
				
				System.out.println("Link Tributo-SIL updated: result ="+resultq01);
				
			}  else {
				Query q1 = se.createSQLQuery("INSERT INTO spx_debiti_sil (id_sil, id_tipo_debito, id_soggetto) values ((select sil.id from spx_sil sil, spx_soggetti sog where sil.id_soggetto=sog.id and sog.nome_soggetto=:principal and sil.sil=:sil ) , (SELECT id FROM spx_tipi_debiti WHERE tipo_debito = :tributo), (SELECT id FROM spx_soggetti WHERE nome_soggetto = :principal))");
				q1.setString("sil", sil);
				q1.setString("tributo",tributo);
				q1.setString("principal", principal);
				int resultq1  = q1.executeUpdate();
				
				System.out.println("Link Tributo-SIL created: result ="+resultq1);
			}
			sm.flush();
			
		}

	}
}