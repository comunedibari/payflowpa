package it.tasgroup.crypt.url;

import java.util.ArrayList;
import java.util.List;

public class EventoNDPParametersEncrypter {
	
	private String idEvento;
	private String idDominio;
	private String iuv;
	private String codContesto;

	public EventoNDPParametersEncrypter() {
	}

	public EventoNDPParametersEncrypter(String idEvento, String idDominio, String iuv, String codContesto) {
		this.idEvento=idEvento;
		this.idDominio=idDominio;
		this.iuv=iuv;
		this.codContesto=codContesto;
	}

	public String encrypt() {
		
		List<String> parameters= new ArrayList<String>();
		
		parameters.add(idEvento);
		parameters.add(idDominio);
		parameters.add(iuv);
		parameters.add(codContesto);
		
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
			
			this.idEvento=parameters.get(0);
			this.idDominio=parameters.get(1);
			this.iuv=parameters.get(2);
			this.codContesto=parameters.get(3);
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			
			throw new RuntimeException("Problemi decrypt");
		}
	}

	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getCodContesto() {
		return codContesto;
	}

	public void setCodContesto(String codContesto) {
		this.codContesto = codContesto;
	}
	
}
