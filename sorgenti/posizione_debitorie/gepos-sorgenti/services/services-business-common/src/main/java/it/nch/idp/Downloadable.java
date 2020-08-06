/**
 *
 */
package it.nch.idp;

import it.nch.fwk.fo.util.Tracer;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;

/**
 * Classe di ogni oggetto in grado di trasformare campi BLOB in
 * array di bytes.
 *
 * @author PazziK
 *
 */
public class Downloadable implements Serializable{


	/**
	 * Trasforma un Blob in un array di bytes.
	 *
	 * @param blob un Blob
	 *
	 * @return l'array di bytes corrispondente al Blob in ingresso.
	 */
	public byte[] setDownloadableStream(Blob blob){

		byte[] blobAsBytes = null;

		if (blob != null)
		{// riempio il byte array con il contenuto del blob
			try
			{
				int lunghezza = ((int)blob.length());
				blobAsBytes = new byte[lunghezza];
				InputStream in = blob.getBinaryStream();
				in.read(blobAsBytes,0,lunghezza);
			}
			catch(Exception ex)
			{
				Tracer.error("Downloadable","setDownloadableStream","Errore durante la conversione in bytes del blob ... ",ex);
				ex.printStackTrace();
			}

		}
		return blobAsBytes;
	}

}
