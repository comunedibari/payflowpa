package it.tasgroup.ge.opentoscana;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;


public class RTMessage implements JSONString {

	public enum Mode {

		BYTOPIC("ByTopic"), BYIDENTITY("ByIdentity"), BYROLE("ByRole");
		
		private String valore;
		
		private Mode(String valore) {
			this.valore = valore;
		}
		
		public String getValore() {
			return this.valore;
		}

	}
	
	String body = null; // (o) Corpo del messaggio che verra' visualizzato
	String subject = null; // (o) Il titolo del messaggio
	String altBody = null; // (f) Body da visualizzare nella notifica. Se non indicato verra' utilizzato il campo body. Da utilizzare quando ci sono informazioni di privacy che non devono esserevisualizzate nel campo notifica.
	String altSubject = null; // (f) Subject da visualizzare nella notifica. Se non indicato verra'	utilizzato il campo subject. Da utilizzare quando ci sono informazioni di privacy che non devono essere visualizzate nel campo notifica.
	String template = null; // (f) E' la modalita' attraverso la quale visualizzare la notifica	all'interno dell'applicazione. Se non indicata verra' utilizzata quella standard. I template devono essere concordati tra mittente e destinatario
	Mode mode = null; // (o) E' la modalita' di invio della notifica. Valori ammessi:	ByTopic, ByIdentity, ByRole
	List<String> recipients = null; // (o) La lista dei destinatari in base al mode selezionato
	Date expiration = null; // (f) Data ed ora, in formato UTC, entro la quale il messaggio risultera' essere valido. Se non indicato il sistema
	Date notBefore = null; // (f)	Data ed ora, in formato UTC, dopo il quale inviare il messaggio. Se non indicato il messaggio verra' notificato	immediatamente.
	HashMap<String, String> meta = null; // (f) Attributi opzionali

	String codiceEvento = null;
	
	public RTMessage(String body, String subject, String altBody, String altSubject, String template, Mode mode, List<String> recipients, Date expiration, Date notBefore, HashMap<String, String> meta, String codiceEvento) {
		super();
		
		this.body = body;
		this.subject = subject;
		this.altBody = altBody;
		this.altSubject = altSubject;
		this.template = template;
		this.mode = mode;
		this.recipients = recipients;
		this.expiration = expiration;
		this.notBefore = notBefore;
		this.meta = meta;
		
		this.codiceEvento = codiceEvento;

	}

	public String getAltBody() {
		return altBody;
	}

	public void setAltBody(String altBody) {
		this.altBody = altBody;
	}

	public String getAltSubject() {
		return altSubject;
	}

	public void setAltSubject(String altSubject) {
		this.altSubject = altSubject;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Date getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(Date notBefore) {
		this.notBefore = notBefore;
	}

	public HashMap<String, String> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, String> meta) {
		this.meta = meta;
	}

	public String getBody() {
		return body;
	}

	public String getSubject() {
		return subject;
	}

	public Mode getMode() {
		return mode;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public String getCodiceEvento() {
		return codiceEvento;
	}
	
	public String prettyPrint() {
		return "RTMessage [body=" + body + ", subject=" + subject + ", altBody=" + altBody + ", altSubject=" + altSubject + ", template=" + template + ", mode=" + mode + ", recipients=" + recipients + ", expiration=" + expiration + ", notBefore=" + notBefore + ", meta=" + meta + "]";
	}

	@Override
	public String toJSONString() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		JSONObject ret = new JSONObject();
		ret.put("body", this.body);
		ret.put("subject", this.subject);
		if (altBody != null) {
			ret.put("altBody", this.altBody);
		}
		if (altSubject != null) {
			ret.put("altSubject", this.altSubject);
		}
		if (template != null) {
			ret.put("template", this.template);
		}
		ret.put("app", "open-toscana"); // dal 20190508 #3617
		ret.put("mode", this.mode.getValore());
		JSONArray recipients = new JSONArray(this.recipients);
		ret.put("recipients", recipients);
		if (expiration != null) {
			ret.put("expiration", sdf.format(expiration));
		}
		if (notBefore != null) {
			ret.put("notBefore", sdf.format(notBefore));
		}
		if (this.meta != null) {
			JSONObject meta = new JSONObject();
			for(String key : this.meta.keySet()) {
				meta.put(key, this.meta.get(key));
			}
			ret.put("meta", meta);
		}
		
		return ret.toString();
	}

}
