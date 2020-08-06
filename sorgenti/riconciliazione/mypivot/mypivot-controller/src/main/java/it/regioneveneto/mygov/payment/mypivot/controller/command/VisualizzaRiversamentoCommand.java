package it.regioneveneto.mygov.payment.mypivot.controller.command;

import org.springframework.web.multipart.MultipartFile;

public class VisualizzaRiversamentoCommand {

	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}
