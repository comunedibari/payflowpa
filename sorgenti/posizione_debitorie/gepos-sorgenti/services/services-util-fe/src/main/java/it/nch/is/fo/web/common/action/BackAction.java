package it.nch.is.fo.web.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BackAction extends Action {

	protected String elementsChecked = "elementsChecked";
	protected String tableName = "tableName";
	protected String pageNum = "pageNum";
	protected String elementsCheckedFlux = "elementsCheckedFlux";
	protected String tableNameFlux = "tableNameFlux";
	protected String pageNumFlux = "pageNumFlux";

	protected String[] listOfParameters = { elementsChecked, tableName, pageNum, elementsCheckedFlux, tableNameFlux, pageNumFlux };

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String queryString = request.getQueryString();

		String lastPath = null;
		// find last path
		if (BackBaseAction.isBackPossible(request))
			lastPath = BackBaseAction.pop(request, true);
		else
			lastPath = BackBaseAction.pop(request, false);

		//
		// Controllo se ci sono i valori per la riselezione delle tabelle
		// in tal caso devo sostituire i vecchi valori con i nuovi e non concatenarli.
		//
		lastPath = modifyLastPath(request, queryString, lastPath);

		return new ActionForward(lastPath, true);
	}

	protected String modifyLastPath(HttpServletRequest request, String queryString, String lastPath) {
		if (queryString != null) {
			for (int i = 0; i < listOfParameters.length; i++) {
				// Concateno l'uguale per evitare che vengano processati parametri con radici uguali
				if (lastPath.indexOf(listOfParameters[i] + "=") >= 0 && queryString.indexOf(listOfParameters[i] + "=") >= 0) {
					// se il campo è presente in tutte e due le url 
					// allora sostituisco il valore in lastPath con quello di queryString
					// e tolgo da quesyString il parametro, altrimenti verrebbe riconcatenato.

					// ottengo il vaolre valido
					String valoreNuovo = getValore(queryString, listOfParameters[i]);
					// ottengo il vaolre valido
					String valoreVecchio = getValore(lastPath, listOfParameters[i]);
					// Sostituisco in lastPath il valore valido
					String tmp1 = listOfParameters[i] + "=" + valoreVecchio;
					String tmp2 = listOfParameters[i] + "=" + valoreNuovo;
					lastPath = lastPath.replaceAll(tmp1, tmp2);
					// rimuovo chiave e valore del parametro da queryString
					queryString = rimuoviParametro(queryString, listOfParameters[i]);
				}
			}
		}

		String backStatus = request.getParameter("backRequest");
		if (backStatus != null && backStatus.trim().length() > 0) {
			if (lastPath.indexOf("?") > -1)
				lastPath = lastPath + "&backRequest=" + backStatus;
			else
				lastPath = lastPath + "?backRequest=" + backStatus;
		}
		if (queryString != null && !queryString.equals("")) {
			if (lastPath.indexOf("?") > -1)
				lastPath = lastPath + "&" + queryString;
			else
				lastPath = lastPath + "?" + queryString;
		}
		return lastPath;
	}

	/**
	 * Rimuove parametro e valore dal path
	 * 
	 * @param path
	 * @param parameter
	 * @return
	 */
	private String rimuoviParametro(String path, String parameter) {
		int[] arr = getInizioFineParametro(path, parameter + "=" + getValore(path, parameter));
		String sub1 = "";
		String sub2 = "";
		if (arr[0] != 0) {
			sub1 = path.substring(0, arr[0]);
		}
		if (arr[1] == path.length()) {
			sub2 = path.substring(arr[1]);
		} else {
			sub2 = path.substring(arr[1] + 1);
		}
		return sub1 + sub2;
	}

	/**
	 * Restituisce il valore del parametro contenuto nel path.
	 * 
	 * @param path
	 * @param parameter
	 * @return
	 */
	private String getValore(String path, String parameter) {
		int[] arr = getInizioFineValore(path, parameter);
		return path.substring(arr[0], arr[1]);
	}

	/**
	 * Restituisce gli indici di inizio e fine del valore del parametro
	 * contenuto nel path
	 * 
	 * @param path
	 * @param parameter
	 * @return
	 */
	private int[] getInizioFineValore(String path, String parameter) {
		int indS = path.indexOf(parameter) + parameter.length() + 1;
		int indE = path.indexOf("&", indS);
		// se era l'ultimo parametro allora la & non verrà trovata quindi imporo la lungezza dell'url come fine.
		if (indE < 0) {
			indE = path.length();
		}
		int[] arr = { indS, indE };
		return arr;
	}

	/**
	 * Restituisce gli indici di inizio e fine parametro e valore contenuto nel
	 * path
	 * 
	 * @param path
	 * @param parameter
	 * @return
	 */
	private int[] getInizioFineParametro(String path, String parameter) {
		int indS = path.indexOf(parameter);
		int indE = path.indexOf("&", indS);
		// se era l'ultimo parametro allora la & non verrà trovata quindi imporo la lungezza dell'url come fine.
		if (indE < 0) {
			indE = path.length();
		}
		int[] arr = { indS, indE };
		return arr;
	}
}