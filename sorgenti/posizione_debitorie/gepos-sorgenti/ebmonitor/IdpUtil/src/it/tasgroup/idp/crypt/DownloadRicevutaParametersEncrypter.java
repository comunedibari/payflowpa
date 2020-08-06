package it.tasgroup.idp.crypt;

import java.util.ArrayList;
import java.util.List;

public class DownloadRicevutaParametersEncrypter {
	
	String codPagamento;
	
	String idFlusso;
	
	String codPagante;

	public DownloadRicevutaParametersEncrypter() {
		
	}

	public DownloadRicevutaParametersEncrypter(String codPagamento, String codPagante, String idFlusso) {
		
		this.codPagamento=codPagamento;
		
		this.codPagante=codPagante;
		
		this.idFlusso=idFlusso;
		
	}
	
	public String getCodPagamento() {
		
		return codPagamento;
		
	}

	public String getIdFlusso() {
		
		return idFlusso;
		
	}

	public String getCodPagante() {
		
		return codPagante;
		
	}

	public String encrypt() {
		
		List<String> parameters= new ArrayList<String>();
		
		parameters.add(codPagamento);
		
		parameters.add(idFlusso);
		
		parameters.add(codPagante);
		
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
			
			List<String> parameters = e.decryptParameters(encrypted);
			
			this.codPagamento = parameters.get(0);
			
			this.idFlusso = parameters.get(1);
			
			this.codPagante = parameters.get(2);
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			
			throw new RuntimeException("Problemi decrypt");
			
		}
	}
	
}
