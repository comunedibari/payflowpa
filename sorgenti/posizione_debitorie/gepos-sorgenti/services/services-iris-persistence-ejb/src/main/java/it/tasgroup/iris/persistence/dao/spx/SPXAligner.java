package it.tasgroup.iris.persistence.dao.spx;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SPXAligner {

	private static final Logger LOGGER = LogManager.getLogger(SPXAligner.class);

	/*
	 * Funzione di allineamento della base dati dello SmartProxy. La funzione,
	 * che agisce solo se � abilitata la property
	 * "smartproxy.automaticalignement.enabled" di iris_be. Provvede ad
	 * alimentare le tabelle di profilo dello Smart Proxy sulla base dei dati
	 * passati come parametro: codiceEnte codiceSIL codiceTributo
	 * 
	 * La funzione controlla gli allineamenti in modo da non censire dati che
	 * sono gi� presenti nel db smartproxy.
	 * 
	 * La funzione non procede volutamente a cancellare record dal db di
	 * profilazione dello smart proxy. Questo perch� lo smart proxy � un
	 * "convertitore evoluto" da CSV a XML. l'XML viene poi inviato a iris, che
	 * effettua una validazione di merito in fase di caricamento. Quindi se nel
	 * profilo SmartProxy ci sono censimenti in pi� rispetto a quanto
	 * effettivamente attivo su iris, allora l'unico effetto � che avremo
	 * degli errori di validazione sul back end di IRIS invece che direttamente
	 * sullo smart proxy.
	 */

	@SuppressWarnings("unchecked")
	public static void AlignSPX(String codiceEnte, String codiceTributo, String codiceSIL, EntityManager em) throws Exception {

		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-be.properties");
		boolean spxAlignmentEnabled = cpl.getBooleanProperty("iris.smartproxy.automaticalign.enabled");

		if (!spxAlignmentEnabled) {
			LOGGER.debug("[SPXAligner::AlignSPX] SPX Alignment non abilitato. Nessun allineamento");
			return;
		}

		if (codiceEnte == null) {
			LOGGER.debug("[SPXAligner::AlignSPX] Manca il codiceEnte. Allineamento SPX non eseguito");
			return;
		}

		/*
		 * RIALLINEAMENTO CODICE ENTE con spx principal e soggetto
		 */

		// Controllo se il censimento � gi� stato fatto
		String principal = codiceEnte;

		Query queryExistingPrincipal = em.createNativeQuery("select principal_utente from SPX_UTENTI where principal_utente = :principal ");
		queryExistingPrincipal.setParameter("principal", principal);
		List<Object[]> existingPrincipal = queryExistingPrincipal.getResultList();

		if (existingPrincipal != null && existingPrincipal.size() > 0) {
			LOGGER.debug("[SPXAligner::AlignSPX] Principal Trovato. Allineamento SPX non necessario");
		} else {
			LOGGER.debug("[SPXAligner::AlignSPX] Principal non trovato. Allineamento SPX");
			Query q1 = em.createNativeQuery("INSERT INTO spx_utenti (principal_utente) VALUES (:principal)");
			q1.setParameter("principal", principal);
			int resultq1 = q1.executeUpdate();
			LOGGER.debug("[SPXAligner::AlignSPX] Principal creato: result =" + resultq1);
			Query q2 = em
					.createNativeQuery("INSERT INTO spx_utenti_plugins (id_utente, id_plugin, default_plugin) VALUES ( (SELECT id FROM spx_utenti WHERE principal_utente=:principal),1,1)");
			q2.setParameter("principal", principal);
			int resultq2 = q2.executeUpdate();
			em.flush();
			LOGGER.debug("[SPXAligner::AlignSPX] Principal plugin creato: result =" + resultq2);
		}

		Query queryExistingSoggetto = em.createNativeQuery("select nome_soggetto from SPX_SOGGETTI where nome_soggetto = :principal ");
		queryExistingSoggetto.setParameter("principal", principal);
		List<Object[]> existingSoggetto = queryExistingSoggetto.getResultList();

		if (existingSoggetto != null && existingSoggetto.size() > 0) {
			LOGGER.debug("[SPXAligner::AlignSPX] Soggetto trovato. Allineamento SPX non necessario");
		} else {
			LOGGER.debug("[SPXAligner::AlignSPX] Soggetto trovato. Allineamento SPX non necessario");
			Query q3 = em.createNativeQuery("INSERT INTO spx_soggetti (nome_soggetto, principal_soggetto) VALUES (:principal, '')");
			q3.setParameter("principal", principal);
			int resultq3 = q3.executeUpdate();
			em.flush();
			LOGGER.debug("[SPXAligner::AlignSPX] Soggetto creato: result =" + resultq3);
		}

		if (codiceSIL == null) {
			LOGGER.debug("[SPXAligner::AlignSPX] Manca il codiceSIL. Allineamento SPX di spx_sil non eseguito");
			return;
		}

		/**
		 * Riallineamento SIL su spx_sil
		 */
		String sil = codiceSIL;

		Query queryExistingSIL = em
				.createNativeQuery("select sil from spx_sil  where id_soggetto = (SELECT id FROM spx_soggetti WHERE nome_soggetto = :principal)  and sil = :sil ");
		queryExistingSIL.setParameter("principal", principal);
		queryExistingSIL.setParameter("sil", sil);
		List<Object[]> existingSIL = queryExistingSIL.getResultList();

		if (existingSIL != null && existingSIL.size() > 0) {
			LOGGER.debug("[SPXAligner::AlignSPX] SIL trovato. Allineamento SPX non necessario");
		} else {
			// I need to align
			LOGGER.debug("[SPXAligner::AlignSPX] SIL non trovato. Allineamento SPX  necessario");
			Query q1 = em
					.createNativeQuery("INSERT INTO spx_sil (sil, nome_cart127, nome_cart145, id_soggetto, id_utente) VALUES (:sil, :sil127 , :sil145 , (SELECT id FROM spx_soggetti WHERE nome_soggetto = :principal) , (SELECT id FROM spx_utenti WHERE principal_utente=:principal))");
			q1.setParameter("principal", principal);
			q1.setParameter("sil", sil);
			q1.setParameter("sil127", principal + "." + sil);
			q1.setParameter("sil145", principal + "." + sil);

			int resultq1 = q1.executeUpdate();
			em.flush();
			LOGGER.debug("[SPXAligner::AlignSPX] SIL creato: result =" + resultq1);
		}

		if (codiceTributo == null) {
			LOGGER.debug("[SPXAligner::AlignSPX] Manca il codiceTributo. Allineamento SPX di spx_tipi_debito e spx_debiti_soggetti non eseguito");
			return;
		}

		/**
		 * Allineamento codici tributo su spx
		 */

		String tributo = codiceTributo;

		//
		// Controllo se il censimento e' gia' stato fatto
		//
		Query queryExistingTributo = em.createNativeQuery("select tipo_debito  from spx_tipi_debiti   where tipo_debito = :tributo ");
		queryExistingTributo.setParameter("tributo", tributo);
		List<Object[]> existingTributo = queryExistingSIL.getResultList();

		if (existingTributo != null && existingTributo.size() > 0) {
			LOGGER.debug("[SPXAligner::AlignSPX] Tributo trovato. Allineamento SPX non necessario");
		} else {
			// I need to align
			LOGGER.debug("[SPXAligner::AlignSPX] Tributo non trovato. Allineamento SPX necessario");
			Query q1 = em.createNativeQuery("INSERT INTO spx_tipi_debiti (tipo_debito, descrizione) VALUES (:tributo, '-')");
			q1.setParameter("tributo", tributo);
			int resultq1 = q1.executeUpdate();
			em.flush();
			LOGGER.debug("[SPXAligner::AlignSPX] Tributo creato: result =" + resultq1);
		}

		//
		// Controllo se il censimento del collegamento tributo-sil e' gia'
		// presente.
		//
		Query queryExistingLink = em
				.createNativeQuery("select * from spx_debiti_sil where id_sil=(select sil.id from spx_sil sil, spx_soggetti sog where sil.id_soggetto=sog.id and sog.nome_soggetto=:principal and sil.sil=:sil) and id_tipo_debito=(SELECT id FROM spx_tipi_debiti WHERE tipo_debito =:tributo)");
		queryExistingLink.setParameter("tributo", tributo);
		queryExistingLink.setParameter("sil", sil);
		queryExistingLink.setParameter("principal", principal);

		List<Object[]> existingLink = queryExistingSIL.getResultList();

		if (existingLink != null && existingLink.size() > 0) {
			LOGGER.debug("[SPXAligner::AlignSPX] Link Tributo-Sil trovato. Allineamento SPX non necessario");
		} else {
			// I need to align
			LOGGER.debug("[SPXAligner::AlignSPX] Link Tributo-Sil NON trovato. Allineamento SPX non necessario");

			// Se cambi SIL ad un tributo devi fare update e non insert.
			Query queryExistingLinkToUpdate = em
					.createNativeQuery("select id_sil,id_tipo_debito, id_soggetto from spx_debiti_sil  where id_soggetto=(select id from spx_soggetti sog where sog.nome_soggetto=:principal) and id_tipo_debito=(SELECT id FROM spx_tipi_debiti WHERE tipo_debito =:tributo)");
			queryExistingLinkToUpdate.setParameter("tributo", tributo);
			queryExistingLinkToUpdate.setParameter("principal", principal);
			List<Object[]> existingLinkToUpdate = queryExistingLinkToUpdate.getResultList();

			if (existingLinkToUpdate != null && existingLinkToUpdate.size() > 0) {

				Object[] record = (Object[]) existingLinkToUpdate.iterator().next();

				// Long id_old_sil = ((Number)record[0]).longValue();
				Long id_tipo_debito = ((Number) record[1]).longValue();
				Long id_soggetto = ((Number) record[2]).longValue();

				LOGGER.debug("[SPXAligner::AlignSPX] Link Tributo-Sil precedentemente  associato. Update Necessario, per indici su soggetto, tributo che sarebbero violati");

				// Recupero l'id del nuovo SIL

				Query queryNewSIL = em
						.createNativeQuery("select id from spx_sil  where id_soggetto = (SELECT id FROM spx_soggetti WHERE nome_soggetto = :principal)  and sil=:sil");
				queryNewSIL.setParameter("principal", principal);
				queryNewSIL.setParameter("sil", sil);
				List<Object[]> newSIL = queryNewSIL.getResultList();

				Long id_new_sil = ((Number) newSIL.iterator().next()[0]).longValue();

				Query q01 = em.createNativeQuery("UPDATE spx_debiti_sil set id_sil=:id_sil where id_tipo_debito=:id_tipo_debito and id_soggetto=:id_soggetto");
				q01.setParameter("id_sil", id_new_sil);
				q01.setParameter("id_tipo_debito", id_tipo_debito);
				q01.setParameter("id_soggetto", id_soggetto);
				int resultq01 = q01.executeUpdate();

				LOGGER.debug("[SPXAligner::AlignSPX] Link Tributo-SIL updated: result =" + resultq01);

			} else {
				Query q1 = em
						.createNativeQuery("INSERT INTO spx_debiti_sil (id_sil, id_tipo_debito, id_soggetto) values ((select sil.id from spx_sil sil, spx_soggetti sog where sil.id_soggetto=sog.id and sog.nome_soggetto=:principal and sil.sil=:sil ) , (SELECT id FROM spx_tipi_debiti WHERE tipo_debito = :tributo), (SELECT id FROM spx_soggetti WHERE nome_soggetto = :principal))");
				q1.setParameter("sil", sil);
				q1.setParameter("tributo", tributo);
				q1.setParameter("principal", principal);
				int resultq1 = q1.executeUpdate();

				LOGGER.debug("[SPXAligner::AlignSPX] Link Tributo-SIL created: result =" + resultq1);
			}
			em.flush();

		}

	}
}