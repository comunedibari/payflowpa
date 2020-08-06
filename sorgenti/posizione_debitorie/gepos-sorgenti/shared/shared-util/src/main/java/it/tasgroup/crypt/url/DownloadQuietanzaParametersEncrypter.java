package it.tasgroup.crypt.url;

import java.util.ArrayList;
import java.util.List;

public class DownloadQuietanzaParametersEncrypter {
	
	private String codPagamento;
	private String idPagamento;
	private String codPagante;

	public DownloadQuietanzaParametersEncrypter() {
	}

	public DownloadQuietanzaParametersEncrypter(String codPagamento,  String codPagante, String idPagamento) {
		this.codPagamento=codPagamento;
		this.idPagamento=idPagamento;
		this.codPagante=codPagante;
	}
	
	public String getCodPagamento() {
		return codPagamento;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public String getCodPagante() {
		return codPagante;
	}

	public String encrypt() {
		List<String> parameters= new ArrayList<String>();
		parameters.add(codPagamento);
		parameters.add(idPagamento);
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
			List<String> parameters= e.decryptParameters(encrypted);
			this.codPagamento=parameters.get(0);
			this.idPagamento=parameters.get(1);
			this.codPagante=parameters.get(2);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Problemi decrypt");
		}
	}
	
	public static void main(String[] args) throws Exception {

		DownloadQuietanzaParametersEncrypter p = new DownloadQuietanzaParametersEncrypter("1234567812345678","1234","RPTSFN70M14A390N");
		String encrypted=p.encrypt();
		System.out.println(encrypted);
		DownloadQuietanzaParametersEncrypter p1 = new DownloadQuietanzaParametersEncrypter();
		//p1.decrypt(URLDecoder.decode(encrypted,"UTF-8"));
		p1.decrypt(encrypted);
		
		System.out.println(p1.codPagamento+"-"+p1.idPagamento+"-"+p1.codPagante);
		
		//String simpleEncryption=Long.toString(Long.parseLong(p.codPagamento),32);		
		//String crc = URLParametersEncrypter.calculateControlRedundancyCode(simpleEncryption);
		//simpleEncryption=crc+simpleEncryption;
		//System.out.println("SimpleEncryptor="+simpleEncryption);
	}
	
}
