package it.tasgroup.crypt.url;

import java.util.ArrayList;
import java.util.List;

public class AllegatoParametersEncrypter {

	private String tipoAllegato;
	private String idAllegato;
	private String idCondizione;
	private String idPendenza;

	public AllegatoParametersEncrypter() {
	}

	public AllegatoParametersEncrypter(String tipoAllegato, String idAllegato, String idCondizione, String idPendenza) {

		this.tipoAllegato=tipoAllegato;
		this.idAllegato=idAllegato;
		this.idCondizione=idCondizione;
		this.idPendenza=idPendenza;
	}
	
	public String getTipoAllegato() {
		return tipoAllegato;
	}
	
	public String getIdAllegato() {
		return idAllegato;
	}

	public String getIdCondizione() {
		return idCondizione;
	}
	
	public String getIdPendenza() {
		return idPendenza;
	}

	public String encrypt() {
		List<String> parameters= new ArrayList<String>();
		parameters.add(tipoAllegato);
		parameters.add(idAllegato);
		parameters.add(idCondizione);
		parameters.add(idPendenza);
		URLParametersEncrypter e;
		try {
			e = new URLParametersEncrypter();
			return e.cryptParameters(parameters);
		} catch (Exception e1) {
			throw new RuntimeException("Problemi encrypt",e1);
		}
	}
	
	public void decrypt(String encrypted)  {
		try {
			URLParametersEncrypter e = new URLParametersEncrypter();
			List<String> parameters= e.decryptParameters(encrypted);
			this.tipoAllegato=parameters.get(0);
			this.idAllegato=parameters.get(1);
			this.idCondizione=parameters.get(2);
			this.idPendenza=parameters.get(3);
		} catch (Exception e1) {
			throw new RuntimeException("Problemi decrypt",e1);
		}
	}
}

