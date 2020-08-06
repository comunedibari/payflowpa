package it.nch.is.fo.profilo;

import it.tasgroup.iris.domain.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CLASSIABIL")
@NamedQueries({
	@NamedQuery(name = "getFunzioniAbilitateByClasseEApplicazione",
		    query = "select ca.id.funzione from ClassiAbilitazioni ca where ca.id.nomeClasse = :nomeClasse and ca.id.applicazione = :applicazione"),
	@NamedQuery(name = "getNomiClasseByApplicazione",
		    // "select distinct(coalesce(nomeclasse,'Default')) from classiabil where applicazione = ? and nomeclasse != 'null'"
    		query = "select distinct coalesce(ca.id.nomeClasse, 'Default') from ClassiAbilitazioni ca where ca.id.applicazione = :applicazione and ca.id.nomeClasse != 'null'")
})
public class ClassiAbilitazioni extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private ClassiAbilitazioniId id;
    
    private String descrizione;
    private Integer prVersione;
    
    private String opInserimento;
    private Timestamp tsInserimento;
    private String opAggiornamento;
    private Timestamp tsAggiornamento;
    
    @Id
	public ClassiAbilitazioniId getId() {
		return id;
	}
	public void setId(ClassiAbilitazioniId id) {
		this.id = id;
	}
	
	@Column(name="DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@Column(name="PR_VERSIONE")
	public Integer getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(Integer prVersione) {
		this.prVersione = prVersione;
	}
    
    
	@Column(name="OP_AGGIORNAMENTO", length=40)
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO", nullable=false, length=40)
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}



	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
   

}
