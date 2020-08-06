package it.nch.is.fo.tributi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import it.tasgroup.iris.domain.BaseEntity;


@Entity
@Table(name="CFG_TRIBUTIENTE_PLUGIN")
public class CfgTributoEntePlugin extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** PK Reference ***/
	private JltentrId tribEnId;
	
	private String cdPlugin;
	private byte[] dati;
	
	private String opInserimento;
	private Date tsInserimento;
	private String opAggiornamento;
	private Date tsAggiornamento;


    public CfgTributoEntePlugin(){
    	this.tribEnId = new JltentrId();
    }

    public CfgTributoEntePlugin(JltentrId id){
    	this.tribEnId = id;
    }


    @Id
	public JltentrId getTribEnId() {
		return tribEnId;
	}
	public void setTribEnId(JltentrId tribEnId) {
		this.tribEnId = tribEnId;
	}

	@Column(name="CD_PLUGIN")
	public String getCdPlugin() {
		return cdPlugin;
	}

	public void setCdPlugin(String codPlugin) {
		this.cdPlugin = codPlugin;
	}

	@Lob
	@Column(name = "DATI")
	public byte[] getDati() {
		return dati;
	}

	public void setDati(byte[] dati) {
		this.dati = dati;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO")
	public Date getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Date tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Date getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Date tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	@Column(name="ID_ENTE",insertable=false,updatable=false)
	public String getIdEnte() {
		return this.tribEnId.getIdEntePk();
	}
	public void setIdEnte(String idEnte) {
		this.tribEnId.setIdEntePk(idEnte);
	}

	@Column(name="CD_TRB_ENTE",insertable=false,updatable=false)
	public String getCdTrbEnte() {
		return tribEnId.getCdTrbEntePk();
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.tribEnId.setCdTrbEntePk(cdTrbEnte);
	}

	
}
