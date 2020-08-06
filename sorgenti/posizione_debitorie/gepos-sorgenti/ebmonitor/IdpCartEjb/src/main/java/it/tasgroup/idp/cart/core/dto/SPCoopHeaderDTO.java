package it.tasgroup.idp.cart.core.dto;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;

/**
 * 
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: SPCoopHeaderDTO.java 358 2013-05-22 15:32:32Z nardi $
 *
 */
public class SPCoopHeaderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String soapAction;
	private String spcoopTipoMittente;
	private String spcoopTipoDestinatario;
	private String spcoopTipoServizio;
	private String spcoopAzione;
	private String spcoopDestinatario;
	private String spcoopServizioApplicativo;
	private String spcoopId;
	private String spcoopServizio;
	private String spcoopMittente;
	private Short httpResponseCode;
	private String httpResponseMessage;
	private Date data;
	
	public SPCoopHeaderDTO(){
		
	}
	
	public SPCoopHeaderDTO(HttpServletRequest req, Log log) {

		log.debug("HTTP Request Headers:");

		Enumeration<?> headers = req.getHeaderNames();
		while(headers.hasMoreElements()){
			String header = (String) headers.nextElement();
			log.debug("\t[" + header +"] : [" + req.getHeader(header) + "]");
		}

		setSoapAction(req.getHeader("SOAPAction"));
		setSpcoopTipoMittente(req.getHeader("SPCoopTipoMittente"));
		setSpcoopMittente(req.getHeader("SPCoopMittente"));
		setSpcoopTipoDestinatario(req.getHeader("SPCoopTipoDestinatario"));
		setSpcoopDestinatario(req.getHeader("SPCoopDestinatario"));
		setSpcoopTipoServizio(req.getHeader("SPCoopTipoServizio"));
		setSpcoopServizio(req.getHeader("SPCoopServizio"));
		setSpcoopAzione(req.getHeader("SPCoopAzione"));
		setSpcoopServizioApplicativo(req.getHeader("SPCoopServizioApplicativo"));
		setSpcoopId(req.getHeader("SPCoopID"));

	}
	
	public SPCoopHeaderDTO(HttpURLConnection httpConn, Log log, String id) {
		String idPref = id!=null ? "[" + id + "] " : "";
		log.debug(idPref + "HTTP Forward Headers:");

		Map<String, List<String>> httpHeaders = httpConn.getHeaderFields();
		Set<String> httpHeadersName = httpHeaders.keySet();
		Iterator<String> itHeader = httpHeadersName.iterator();

		while(itHeader.hasNext()){
			String headerName = (String) itHeader.next();
			List<String> httpHeaderValues = httpHeaders.get(headerName);
			Iterator<String> itValues = httpHeaderValues.iterator();
			while(itValues.hasNext())
				log.debug(idPref + "\t[" + headerName +"] : [" + itValues.next() + "]");
		}

		setSpcoopTipoMittente(httpConn.getHeaderField("SPCoopTipoMittente"));
		setSpcoopMittente(httpConn.getHeaderField("SPCoopMittente"));
		setSpcoopTipoDestinatario(httpConn.getHeaderField("SPCoopTipoDestinatario"));
		setSpcoopDestinatario(httpConn.getHeaderField("SPCoopDestinatario"));
		setSpcoopTipoServizio(httpConn.getHeaderField("SPCoopTipoServizio"));
		setSpcoopServizio(httpConn.getHeaderField("SPCoopServizio"));
		setSpcoopAzione(httpConn.getHeaderField("SPCoopAzione"));
		setSpcoopId(httpConn.getHeaderField("SPCoopID"));
	}

	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("soapAction: ").append(soapAction).append("\n");
		sb.append("spcoopTipoMittente: ").append(spcoopTipoMittente).append("\n");
		sb.append("spcoopTipoDestinatario: ").append(spcoopTipoDestinatario).append("\n");
		sb.append("spcoopAzione: ").append(spcoopAzione).append("\n");
		sb.append("spcoopTipoServizio: ").append(spcoopTipoServizio).append("\n");
		sb.append("spcoopDestinatario: ").append(spcoopDestinatario).append("\n");
		sb.append("spcoopServizioApplicativo: ").append(spcoopServizioApplicativo).append("\n");
		sb.append("spcoopId: ").append(spcoopId).append("\n");
		sb.append("spcoopServizio: ").append(spcoopServizio).append("\n");
		sb.append("spcoopMittente: ").append(spcoopMittente).append("\n");
		sb.append("httpResponseCode: ").append(httpResponseCode).append("\n");
		sb.append("httpResponseMessage: ").append(httpResponseMessage);
		
		return sb.toString();
	}

	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	public String getSpcoopTipoMittente() {
		return spcoopTipoMittente;
	}

	public void setSpcoopTipoMittente(String spcoopTipoMittente) {
		this.spcoopTipoMittente = spcoopTipoMittente;
	}

	public String getSpcoopTipoDestinatario() {
		return spcoopTipoDestinatario;
	}

	public void setSpcoopTipoDestinatario(String spcoopTipoDestinatario) {
		this.spcoopTipoDestinatario = spcoopTipoDestinatario;
	}

	public String getSpcoopServizioApplicativo() {
		return spcoopServizioApplicativo;
	}

	public void setSpcoopServizioApplicativo(String spcoopServizioApplicativo) {
		this.spcoopServizioApplicativo = spcoopServizioApplicativo;
	}

	public String getSpcoopAzione() {
		return spcoopAzione;
	}

	public void setSpcoopAzione(String spcoopAzione) {
		this.spcoopAzione = spcoopAzione;
	}

	public String getSpcoopDestinatario() {
		return spcoopDestinatario;
	}

	public void setSpcoopDestinatario(String spcoopDestinatario) {
		this.spcoopDestinatario = spcoopDestinatario;
	}

	public String getSpcoopServizio() {
		return spcoopServizio;
	}

	public void setSpcoopServizio(String spcoopServizio) {
		this.spcoopServizio = spcoopServizio;
	}

	public String getSpcoopId() {
		return spcoopId;
	}

	public void setSpcoopId(String spcoopId) {
		this.spcoopId = spcoopId;
	}

	public String getSpcoopMittente() {
		return spcoopMittente;
	}

	public void setSpcoopMittente(String spcoopMittente) {
		this.spcoopMittente = spcoopMittente;
	}

	public String getSpcoopTipoServizio() {
		return spcoopTipoServizio;
	}

	public void setSpcoopTipoServizio(String spcoopTipoServizio) {
		this.spcoopTipoServizio = spcoopTipoServizio;
	}

	public Short getHttpResponseCode() {
		return httpResponseCode;
	}

	public void setHttpResponseCode(Short httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}

	public String getHttpResponseMessage() {
		return httpResponseMessage;
	}

	public void setHttpResponseMessage(String httpResponseMessage) {
		this.httpResponseMessage = httpResponseMessage;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
