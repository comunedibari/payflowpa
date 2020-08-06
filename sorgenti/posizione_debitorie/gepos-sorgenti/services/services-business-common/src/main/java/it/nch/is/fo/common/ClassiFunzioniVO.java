package it.nch.is.fo.common;

import java.util.Collection;

import it.nch.fwk.fo.dto.business.Pojo;

public class ClassiFunzioniVO implements  Pojo {


    private String Applicazione;
    public String getClasse() {
		return Classe;
	}
	public void setClasse(String classe) {
		Classe = classe;
	}
	public Collection getListaFunzioni() {
		return listaFunzioni;
	}
	public void setListaFunzioni(Collection listaFunzioni) {
		this.listaFunzioni = listaFunzioni;
	}
	private String Classe;
    private Collection listaClassi;
    private Collection listaFunzioni;
    
	public String getApplicazione() {
		return Applicazione;
	}
	public void setApplicazione(String applicazione) {
		Applicazione = applicazione;
	}
	public Collection getListaClassi() {
		return listaClassi;
	}
	public void setListaClassi(Collection listaClassi) {
		this.listaClassi = listaClassi;
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}
    
    
	
	
}
