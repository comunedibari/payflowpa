package it.regioneveneto.mygov.payment.mypivot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.google.gson.Gson;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants.VERSIONE_TRACCIATO_EXPORT;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class Utilities {

	public static String formatDate(Date date, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		return fmt.print(new DateTime(date));
	}

	public static void copyFile(InputStream input, OutputStream output, int bufferSize) throws IOException {
		byte[] buf = new byte[bufferSize];
		int bytesRead = input.read(buf);
		while (bytesRead != -1) {
			output.write(buf, 0, bytesRead);
			bytesRead = input.read(buf);
		}
		output.flush();
	}

	/*
	 * Converts java.util.Date to javax.xml.datatype.XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
		GregorianCalendar gCalendar = new GregorianCalendar();
		XMLGregorianCalendar xmlCalendar = null;
		try {
			gCalendar.setTime(date);
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
		} catch (Exception ex) {
		}
		return xmlCalendar;
	}

	/*
	 * Converts XMLGregorianCalendar to java.util.Date in Java
	 */
	public static Date toDate(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	public static boolean checkValiditaClassificazione(String classificazione) {
		switch (classificazione) {
		case Constants.COD_ERRORE_IUD_RT_IUF_TES:
		case Constants.COD_ERRORE_RT_IUF_TES:
		case Constants.COD_ERRORE_RT_IUF:
		case Constants.COD_ERRORE_IUD_NO_RT:
		case Constants.COD_ERRORE_RT_NO_IUD:
		case Constants.COD_ERRORE_IUD_RT_IUF:
		case Constants.COD_ERRORE_RT_NO_IUF:
		case Constants.COD_ERRORE_IUV_NO_RT:
		case Constants.COD_ERRORE_IUF_NO_TES:
		case Constants.COD_ERRORE_TES_NO_IUF_OR_IUV:
		case Constants.COD_ERRORE_TES_NO_MATCH:
		case Constants.COD_ERRORE_RT_TES:
		case Constants.COD_ERRORE_IUF_TES_DIV_IMP:
			return true;
		default:
			return false;
		}
	}

	public static boolean checkAbilitazioneClassificazionePerEnte(String classificazione, boolean flgPagati,
			boolean flgTesoreria) {

		switch (classificazione) {
		case Constants.COD_ERRORE_IUD_RT_IUF_TES:
			return (flgPagati && flgTesoreria) ? true : false;
		case Constants.COD_ERRORE_RT_IUF_TES:
			return (flgTesoreria) ? true : false;
		case Constants.COD_ERRORE_RT_IUF:
			return true;
		case Constants.COD_ERRORE_IUD_NO_RT:
			return (flgPagati) ? true : false;
		case Constants.COD_ERRORE_RT_NO_IUD:
			return (flgPagati) ? true : false;
		case Constants.COD_ERRORE_IUD_RT_IUF:
			return (flgPagati) ? true : false;
		case Constants.COD_ERRORE_RT_NO_IUF:
			return true;
		case Constants.COD_ERRORE_IUV_NO_RT:
			return true;
		case Constants.COD_ERRORE_IUF_NO_TES:
			return (flgTesoreria) ? true : false;
		case Constants.COD_ERRORE_TES_NO_IUF_OR_IUV:
			return (flgTesoreria) ? true : false;
		case Constants.COD_ERRORE_TES_NO_MATCH:
			return (flgTesoreria) ? true : false;
		case Constants.COD_ERRORE_RT_TES:
			return (flgTesoreria) ? true : false;
		case Constants.COD_ERRORE_IUF_TES_DIV_IMP:
			return (flgTesoreria) ? true : false;
		}

		return false;
	}

	public static boolean validaVersioneTracciatoExport(String versioneTracciato) {

		if (StringUtils.isBlank(versioneTracciato))
			throw new IllegalArgumentException("Versione tracciato nulla");

		for (VERSIONE_TRACCIATO_EXPORT vte : VERSIONE_TRACCIATO_EXPORT.values())
			if (vte.value.equals(versioneTracciato))
				return true;
		return false;
	}

	public static List<String> versioneTracciatoToList() {
		List<String> lista = new ArrayList<String>();
		for (VERSIONE_TRACCIATO_EXPORT vte : VERSIONE_TRACCIATO_EXPORT.values())
			lista.add(vte.value);
		Collections.reverse(lista);
		return lista;
	}

	public static Date removeTime(Date date) {
		if (date == null)
			throw new IllegalArgumentException();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static XMLGregorianCalendar toXMLGregorianCalendarWithoutTimezone(Date date) {
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
			xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException ex) {
		}
		return xmlCalendar;
	}

	public static boolean isAllOperatoreEnteTipoDovutoDisabilitati(List<OperatoreEnteTipoDovutoTO> lista) {
		Assert.notNull(lista, "Lista degli operatori per ente tipo dovuto nulla");

		if (CollectionUtils.isEmpty(lista))
			return true;

		for (OperatoreEnteTipoDovutoTO oetd : lista)
			if (oetd.getFlgAttivo())
				return false;
		return true;
	}

	public static boolean isWSUSer(String codFedUserId) {
		return (codFedUserId.endsWith("-WS_USER"));
	}

	/**
	 * traduce un codice il qui valore è di uso anche descrittivo e può assumere
	 * anche il valore N/A, che verrà considerato come nullo
	 * 
	 * @param codice
	 * @return
	 */
	public static final String translateDescToCodNotAvaliable(String codice) {
		return (StringUtils.isBlank(codice) || Constants.CODICE_NOT_AVAILABLE.equalsIgnoreCase(codice)) ? null
				: codice.trim();
	}

	/**
	 * Operazione inversa, traduce un codice in descrizione
	 * 
	 * @param codice
	 * @return
	 */
	public static final String translateCodToDescNotAvaliable(String codice) {
		return (StringUtils.isEmpty(codice)) ? Constants.CODICE_NOT_AVAILABLE : codice;
	}

	public static final <T> PageDto<T> getPageDtoInstance(final List<T> list, final int page, final int pageSize,
			final int totalPages, final long totalElements) {
		PageDto<T> pageDto = new PageDto<>();
		pageDto.setList(list);
		pageDto.setNextPage(page < totalPages);
		pageDto.setPage(page);
		pageDto.setPageSize(pageSize);
		pageDto.setPreviousPage(page > 1);
		pageDto.setTotalPages(totalPages);
		pageDto.setTotalRecords(totalElements);
		return pageDto;
	}

	/**
	 * Esporta l'oggetto report generato in formato PDF e scrive i risultati sul
	 * flusso di output.
	 * 
	 * @param {@link
	 * 			String} reportSource, Path file jrxml
	 * @param {@link
	 * 			Map<String, Object>} parameters, Parametri d'input per il
	 *            report
	 * @param {@link
	 * 			Connection} jrDataSource, Connessione al database
	 * @param {@link
	 * 			OutputStream} reportStream, Flusso di output
	 * 
	 * @throws JRException
	 * @author Marianna Memoli
	 */
	public static void exportToPdf(final String reportSource, Map<String, Object> parameters, Connection connection,
			OutputStream reportStream) throws JRException {
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);

		if (connection == null)
			throw new JRException("Coonnection database is null");

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

		JasperExportManager.exportReportToPdfStream(jasperPrint, reportStream);
	}

	public static boolean hasFlussoTesoreriaIUF(final FlussoTesoreriaTO flussoTesoreriaTO) {
		Assert.notNull(flussoTesoreriaTO, "flussoTesoreriaTO nullo");
		if (StringUtils.isNotBlank(flussoTesoreriaTO.getCodIdUnivocoFlusso()))
			return true;
		return false;
	}

	public static boolean hasFlussoTesoreriaIUV(final FlussoTesoreriaTO flussoTesoreriaTO) {
		Assert.notNull(flussoTesoreriaTO, "flussoTesoreriaTO nullo");
		if (StringUtils.isNotBlank(flussoTesoreriaTO.getCodIdUnivocoVersamento()))
			return true;
		return false;
	}

	public static BigDecimal parseImportoString(String importo) throws Exception {

		String pattern = "^(([0-9]){1,9})$|^(([0-9]){1,9})\\,([0-9]){1,2}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(importo);

		if (m.find()) {

			NumberFormat number_fmt_IT = NumberFormat.getNumberInstance(Locale.ITALIAN);
			Number number = number_fmt_IT.parse(importo);
			BigDecimal impBigDecimal = new BigDecimal(number.doubleValue());

			if (impBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
				throw new Exception("Importo non valido");
			}

			return impBigDecimal;

		} else {
			throw new Exception("Importo non valido");
		}

	}

	public static String parseImportoString(BigDecimal importo) {

		NumberFormat number_fmt_IT = NumberFormat.getNumberInstance(Locale.ITALIAN);
		String importoString = number_fmt_IT.format(importo);
		if (!importoString.contains(",")) {
			importoString = importoString + ",00";
		} else {
			if (importoString.split(",")[1].length() == 1) {
				importoString = importoString + "0";
			}
		}
		return importoString;

	}

	public static ResponseEntity<String> createJsonResponse(Object o, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json;charset=utf-8");
		if (o == null) {
			return new ResponseEntity<String>(null, headers, status);
		}
		Gson gson = new Gson();
		String json = gson.toJson(o);
		return new ResponseEntity<String>(json, headers, status);
	}

	public static ResponseEntity<String> createJsonResponseFromJsonString(String jsonString, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json;charset=utf-8");
		return new ResponseEntity<String>(jsonString, headers, status);
	}
	
	/**
	 * Alla data in ingresso aggiunge/sottrae un certo valore all'anno/mese/giorno.
	 * 
	 * @param {@link Date} data
	 * @param {@link int}  field, Campo(mese anno o giorno) a cui aggiungere o sotrarre
	 * @param {@link int}  value, Il valore da aggiungere o sottrarre
	 * 
	 * @return {@link Date}
	 * @author Marianna Memoli
	 */
	public static Date addOrSubtractTime(Date date, int field, int value) {
		Calendar c = Calendar.getInstance();

		c.setTime(date); 	c.add(field, value);

		return c.getTime();
	}

	private static String concatString(List<String> list) {
		String finalString = "";
		for (String s : list) {
			finalString += "'" + s + "', ";
		}
		finalString = finalString.trim();
		if (finalString.endsWith(","))
			finalString = finalString.substring(0, finalString.length() - 1);
		if (finalString.equals(""))
			finalString = "'" + finalString + "'";
		return finalString;
	}

	public static boolean isTipoDovutoAbilitatoPerClassificazione(String classificazioneCompletezza) {
		switch (classificazioneCompletezza) {
		case Constants.COD_ERRORE_RT_NO_IUF:
			return true;
		case Constants.COD_ERRORE_IUF_NO_TES:
			return true;
		case Constants.COD_ERRORE_TES_NO_IUF_OR_IUV:
			return false;
		case Constants.COD_ERRORE_IUV_NO_RT:
			return false;
		case Constants.COD_ERRORE_IUD_NO_RT:
			return true;
		case Constants.COD_ERRORE_RT_NO_IUD:
			return true;
		case Constants.COD_ERRORE_IUD_RT_IUF_TES:
			return true;
		case Constants.COD_ERRORE_RT_IUF:
			return true;
		case Constants.COD_ERRORE_RT_IUF_TES:
			return true;
		case Constants.COD_ERRORE_IUD_RT_IUF:
			return true;
		case Constants.COD_ERRORE_TES_NO_MATCH:
			return false;
		case Constants.COD_ERRORE_IUF_TES_DIV_IMP:
			return false;
		case Constants.COD_ERRORE_RT_TES:
			return true;
		default:
			return false;
		}
	}
}
