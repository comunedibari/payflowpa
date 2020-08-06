package it.tasgroup.iris.dto.ddp;

import java.io.Serializable;

/**
 * @author pazzik
 *
 */
public class DocumentoRepositoryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileExtension;
	private Long id;
	private byte[] content;
	private byte[] rtAGID;
	private Integer size;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public byte[] getRtAGID() {
		return rtAGID;
	}
	
	public void setRtAGID(byte[] rtAGID) {
		this.rtAGID = rtAGID;
	}

}
