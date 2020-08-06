package it.regioneveneto.mygov.payment.mypivot.dao;


import java.util.List;

/**
 * 
 * @author Alessandro Paolillo
 *
 */
public interface DettaglioCruscottoIncassiDao {

	/**
	 * Recupera l'elenco di IUD relative le RT che hanno prodotto il dato statistico.  
	 * 
	 * @param {@link Long}    enteId,    	   Identificativo dell'ente
	 * @param {@link Integer} anno,       	   L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} mese, 	 	   Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} giorno,      	   Il giorno per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio, 	   Codice dell'ufficio
	 * @param {@link String}  codTipoDovuto,   Codice del tipo dovuto
	 * @param {@link String}  codCapitolo, 	   Codice del capitolo
	 * @param {@link String}  codAccertamento, Codice dell'accertamento
	 * 
	 * @return {@link List<String}
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	public List<String> findListaPagamentiByFilter(Long enteId, Integer anno, Integer mese, Integer giorno, String codUfficio, String codTipoDovuto,
			String codCapitolo, String codAccertamento) throws Exception;
}