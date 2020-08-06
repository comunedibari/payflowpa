package it.tasgroup.iris.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the PROVINCE database table.
 * 
 */
@Entity
@Table(name="PROVINCE")
@NamedQueries(
{
	@NamedQuery(name="provinceAttive", 
			query="select p from Province p order by p.descProvincia asc")
})
public class Province extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_PROVINCIA")
	private String codProvincia;

	@Column(name="DESC_PROVINCIA")
	private String descProvincia;

	@Column(name="OP_AGGIORNAMENTO")
	private String opAggiornamento;

	@Column(name="OP_INSERIMENTO")
	private String opInserimento;

	@Column(name="TS_AGGIORNAMENTO")
	private Timestamp tsAggiornamento;

	@Column(name="TS_INSERIMENTO")
	private Timestamp tsInserimento;

    public Province() {
    }

	public String getCodProvincia() {
		return this.codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getDescProvincia() {
		return this.descProvincia;
	}

	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}

	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

}