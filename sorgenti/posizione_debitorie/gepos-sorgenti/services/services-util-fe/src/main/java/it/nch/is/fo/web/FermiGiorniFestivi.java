package it.nch.is.fo.web;

import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class FermiGiorniFestivi {

	
	public static ClosingDetails getClosingDetails(HttpServletRequest request) throws ParseException {
		String methodName = "getClosingDetails";
		
		ClosingDetails closingDetails = null;
		
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("fermigiornalieri.properties");
		String abilitaControlloChiusura = cpl.getProperty("abilita.controllo.chiusura");
		if (Tracer.isInfoEnabled("FermiGiorniFestivi")) {
			Tracer.info("FermiGiorniFestivi", methodName, "controllo chiusura abilitato ? " + abilitaControlloChiusura);
		}
		if (abilitaControlloChiusura != null && "true".equalsIgnoreCase(abilitaControlloChiusura.trim())) {
			String iniziochiusura = cpl.getProperty("chiusuragiornaliera.inizio");
			String finechiusura = cpl.getProperty("chiusuragiornaliera.fine");
			String festivi = cpl.getProperty("festivi");
			boolean controllo = controllaFile(iniziochiusura, finechiusura, festivi);
			if (Tracer.isInfoEnabled("FermiGiorniFestivi")) {
				Tracer.info("FermiGiorniFestivi", methodName, "inizio chiusura ..... " + iniziochiusura);
				Tracer.info("FermiGiorniFestivi", methodName, "fine chiusura ....... " + finechiusura);
				Tracer.info("FermiGiorniFestivi", methodName, "festivi ............. " + festivi);
				Tracer.info("FermiGiorniFestivi", methodName, "Risultato: bloccato ? " + controllo);
			}
			if (controllo) {
				closingDetails = new ClosingDetails();
				closingDetails.startOpen = finechiusura;
				closingDetails.endOpen = iniziochiusura;
			}
		}
		return closingDetails;
	}

	public static boolean isNowClosed_OLD(HttpServletRequest request) throws ParseException {
		String methodName = "isNowClosed";
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("fermigiornalieri.properties");
		String abilitaControlloChiusura = cpl.getProperty("abilita.controllo.chiusura");
		if (Tracer.isInfoEnabled("FermiGiorniFestivi")) {
			Tracer.info("FermiGiorniFestivi", methodName, "controllo chiusura abilitato ? " + abilitaControlloChiusura);
		}
		if (abilitaControlloChiusura != null && "true".equalsIgnoreCase(abilitaControlloChiusura.trim())) {
			String iniziochiusura = cpl.getProperty("chiusuragiornaliera.inizio");
			String finechiusura = cpl.getProperty("chiusuragiornaliera.fine");
			String festivi = cpl.getProperty("festivi");
			boolean controllo = controllaFile(iniziochiusura, finechiusura, festivi);
			if (Tracer.isInfoEnabled("FermiGiorniFestivi")) {
				Tracer.info("FermiGiorniFestivi", methodName, "inizio chiusura ..... " + iniziochiusura);
				Tracer.info("FermiGiorniFestivi", methodName, "fine chiusura ....... " + finechiusura);
				Tracer.info("FermiGiorniFestivi", methodName, "festivi ............. " + festivi);
				Tracer.info("FermiGiorniFestivi", methodName, "Risultato: bloccato ? " + controllo);
			}
			if (controllo) {
				request.setAttribute("startopen", finechiusura);
				request.setAttribute("endopen", iniziochiusura);
				return true;
			}
		}
		return false;
	}

	public static boolean isNowClosed(HttpServletRequest request) throws ParseException {
		String methodName = "isNowClosed";
		
		ClosingDetails closingDetails = getClosingDetails(request);
		
		if (Tracer.isInfoEnabled("FermiGiorniFestivi")) {
			Tracer.info("FermiGiorniFestivi", methodName, "closingDetails != null ? " + (closingDetails != null));
		}
		if (closingDetails != null) {
			if (Tracer.isInfoEnabled("FermiGiorniFestivi")) {
				Tracer.info("FermiGiorniFestivi", methodName, "Dettaglio apertura sito: " + closingDetails);
			}
			request.setAttribute("startopen", closingDetails.startOpen);
			request.setAttribute("endopen", closingDetails.endOpen);
			return true;
		}
		return false;
	}

	private static boolean controllaFile(String iniziochiusura,
			String finechiusura, String festivi) throws ParseException {
		Calendar rightNow = Calendar.getInstance();

		String[] valueArray = festivi.split(",");
		//Calendar holiday = Calendar.getInstance();

		if (rightNow.get(Calendar.DAY_OF_WEEK) == (Calendar.SUNDAY)) {
			return true;
		}

		if (valueArray.length > 0) {
			// SimpleDateFormat df = new SimpleDateFormat("dd/mm");
			for (int i = 0; i < valueArray.length; i++) {
				String[] distr = valueArray[i].split("/");
				int d = Integer.parseInt(distr[0]);
				int m = Integer.parseInt(distr[1]);
				if (rightNow.get(Calendar.DAY_OF_MONTH) == d
						&& rightNow.get(Calendar.MONTH) == m - 1)
					return true;
			}
		}

		SimpleDateFormat dfh = new SimpleDateFormat("HH.mm");
		Date di = dfh.parse(iniziochiusura);
		Date df = dfh.parse(finechiusura);

		Date h = dfh.parse(dfh.format(rightNow.getTime()));
		if (di.compareTo(df) > 0) {
			if (h.compareTo(di) > 0 || (h.compareTo(df) < 0)) {
				return true;
			}
		} else {
			if (h.compareTo(di) > 0 && h.compareTo(df) < 0) {
				return true;
			}
		}
		return false;
	}
	
	
	public static void main(String[] args) throws ParseException {
		String iniziochiusura = "10.00";
		String finechiusura = "12.50";
		String festivi = "25/12,26/12";
		
		boolean isInCalendar = controllaFile(iniziochiusura, finechiusura, festivi);
		System.out.println(new Date() + " --> " + isInCalendar);
	}
}
