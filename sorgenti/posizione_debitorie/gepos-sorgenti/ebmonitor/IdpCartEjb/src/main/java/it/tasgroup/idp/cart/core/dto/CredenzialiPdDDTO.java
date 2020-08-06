package it.tasgroup.idp.cart.core.dto;

/** Classe per la gestione delle credenziali di autenticazione alla PortaDelegata.
 *
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: CredenzialiPdDDTO.java 358 2013-05-22 15:32:32Z nardi $
 */
public class CredenzialiPdDDTO {
	/** Username per l'autenticazione del ProxyIRIS alla Porta Delegata. */
	private String username;
	/** Password per l'autenticazione del ProxyIRIS alla Porta Delegata. */
	private String password;
	/** Profilo di Collaborazione. */
	private boolean oneway;
	private String soggetto;

	/**
	 * Imposta il nome utente.
	 * @param username Nome utente per l'autenticazione
	 */
	public final void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Imposta la password.
	 * @param password Password per l'autenticazione
	 */

	public final void setPassword(final String password) {
		this.password = password;
	}
	
	/**
	 * Imposta il profilo.
	 * @param oneway True se il profilo e' oneway, false se asincrono
	 */

	public final void setOneway(final boolean oneway) {
		this.oneway = oneway;
	}

	/**
	 * Restisuisce il nome utente.
	 * @return username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * Restisuisce la password.
	 * @return password
	 */
	public final String getPassword() {
		return password;
	}
	
	/**
	 * Restisuisce true se il profilo e' oneway.
	 * @return true profilo e' oneway
	 */
	public final boolean getOneway() {
		return oneway;
	}

	public String getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}
}
