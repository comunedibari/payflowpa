package it.tasgroup.crypt.url;

import java.util.ArrayList;
import java.util.List;

public class DocumentoNDPParametersEncrypter {
	
	private String tipo;
	private String idDominio;
	private String iuv;
	private String codContesto;

	public DocumentoNDPParametersEncrypter() {
	}

	public DocumentoNDPParametersEncrypter(String tipo, String idDominio, String iuv, String codContesto) {
		this.tipo=tipo;
		this.idDominio=idDominio;
		this.iuv=iuv;
		this.codContesto=codContesto;
	}

	public String encrypt() {
		
		List<String> parameters= new ArrayList<String>();
		
		parameters.add(tipo);
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
			
			this.tipo=parameters.get(0);
			this.idDominio=parameters.get(1);
			this.iuv=parameters.get(2);
			this.codContesto=parameters.get(3);
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			
			throw new RuntimeException("Problemi decrypt");
		}
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
