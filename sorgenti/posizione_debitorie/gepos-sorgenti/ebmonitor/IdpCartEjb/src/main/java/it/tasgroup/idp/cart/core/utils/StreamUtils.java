package it.tasgroup.idp.cart.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utilities per la gestione degli stream.
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: StreamUtils.java 358 2013-05-22 15:32:32Z nardi $
 */
public final class StreamUtils {
	/**
	 * Costruttore privato.
	 */

	private StreamUtils() { }
	/**
	 * Estrae i dati dallo stream e crea una Stringa.
	 * @param is Stream dei dati
	 * @return Stringa
	 * @throws IOException in caso di errore di accesso allo stream
	 */

	public static String convertStreamToString(final InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            	throw e;
            }
        }
        return sb.toString();
    }
}
