package it.tasgroup.crypt.url;

import java.util.ArrayList;
import java.util.List;

public class ManageDDPParametersEncrypter {
	
	private String codFiscale;
	private String docId;
	private String azienda;

	public ManageDDPParametersEncrypter() {
	}

	public ManageDDPParametersEncrypter(String codFiscale, String docId, String azienda) {
		this.codFiscale=codFiscale;
		this.azienda=azienda;
		this.docId=docId;
	}
	
	public String getCodFiscale() {
		return codFiscale;
	}

	public String getDocId() {
		return docId;
	}

	public String getAzienda() {
		return azienda;
	}

	public String encrypt() {
		List<String> parameters= new ArrayList<String>();
		parameters.add(codFiscale);
		parameters.add(docId);
		parameters.add(azienda);
		URLParametersEncrypter e;
		try {
			e = new URLParametersEncrypter();
			return e.cryptParameters(parameters);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Problemi encrypt");
		}
	}
	
	public void decrypt(String encrypted)  {
		try {
			URLParametersEncrypter e = new URLParametersEncrypter();
			List<String> parameters= e.decryptParameters(encrypted);
			this.codFiscale=parameters.get(0);
			this.docId=parameters.get(1);
			this.azienda=parameters.get(2);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Problemi decrypt");
		}
	}
	
}
