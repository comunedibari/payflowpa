package it.nch.is.fo.exportfile;

import it.nch.fwk.fo.dto.business.Pojo;

import java.util.Date;

/**
 * 
 * @author sadpa
 * Created 03/07/2007
 */
public class ExportFileContentVo implements Pojo {

	/**
	 * PK - it is also a FK to FILEID of ESPORTAZIONI
	 */
	private String fileIdentifier;
	
	/**
	 * Creation date of current request
	 */
	private Date requestDate;
	
	/**
	 * Name of the file (without .zip extension)
	 */
	private byte[] fileContent;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String NL = "\r\n";
		
		sb.append(NL);
		sb.append("fileIdentifier .................... " + fileIdentifier);
		sb.append(NL);
		sb.append("requestDate ....................... " + requestDate);
		sb.append(NL);
		if (fileContent != null)
			sb.append("fileContent (size) ................. " + fileContent.length);
		else
			sb.append("fileContent (size) ................. " + "<EMPTY>");

		return sb.toString();
	}

	// ==============================================
	//         GETTERS AND SETTERS
	// ==============================================
	
	public String getFileIdentifier() {
		return fileIdentifier;
	}

	public void setFileIdentifier(String fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	// ==== POJO ====
	public Long getId() {
		return null;
	}

	public void setId(Long id) {
		return;
	}
		
}
