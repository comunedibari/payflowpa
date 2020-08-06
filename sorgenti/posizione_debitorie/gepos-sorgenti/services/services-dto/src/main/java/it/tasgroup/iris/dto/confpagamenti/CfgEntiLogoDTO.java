/**
 * 
 */
package it.tasgroup.iris.dto.confpagamenti;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 * @author pazzik
 *
 */
public class CfgEntiLogoDTO implements Serializable {

	private String idEnte;
	
	private String nomeFile;
	
	private String opInserimento;
	
	private String opAggiornamento;
	
	private byte[] fileContent;
	
	private InputStream istream; 
	public String getIdEnte() {
		return idEnte;
	}
	
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}
	
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	public byte[] getFileContent() {
		return fileContent;
	}
	
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}
	
	public InputStream getStream(){
		if (fileContent!=null)
		   return new ByteArrayInputStream(fileContent);
		else 
		   return istream;
	}
	
	public void setStream(InputStream is) {
		istream = is;
	}
}
