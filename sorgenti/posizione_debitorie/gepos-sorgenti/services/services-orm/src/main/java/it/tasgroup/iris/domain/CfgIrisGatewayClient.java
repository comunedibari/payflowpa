package it.tasgroup.iris.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the CFG_IRIS_GATEWAY_CLIENT database table.
 * 
 */
@Entity
@Table(name="CFG_IRIS_GATEWAY_CLIENT")
@NamedQueries(
{
	@NamedQuery(
			name="getCfgIrisGtwClientByAppIDAndSysID",
			query="select gtwClients from CfgIrisGatewayClient gtwClients "+
				  "where gtwClients.applicationId=:appID and gtwClients.systemId=:sysID")
	}
)
public class CfgIrisGatewayClient extends BaseIdEntity {
	private static final long serialVersionUID = 1L;

	private String applicationId;
	private short authenticated;
	private String descrizione;
	private String opAggiornamento;
	private String opInserimento;
	private String systemId;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private String urlAnnulla;
	private String urlKo;
	private String urlOk;

    public CfgIrisGatewayClient() {
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="cfg_iris_gateway_client_pk_gen")	
	@SequenceGenerator(
		    name="cfg_iris_gateway_client_pk_gen",
		    sequenceName="CFG_IRIS_GATEWAY_CLIE_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	        
	public void setId(Long id) {		
		this.id = id;	 
	} 		


	@Column(name="APPLICATION_ID")
	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}


	public short getAuthenticated() {
		return this.authenticated;
	}

	public void setAuthenticated(short authenticated) {
		this.authenticated = authenticated;
	}


	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="SYSTEM_ID")
	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	@Column(name="URL_ANNULLA")
	public String getUrlAnnulla() {
		return this.urlAnnulla;
	}

	public void setUrlAnnulla(String urlAnnulla) {
		this.urlAnnulla = urlAnnulla;
	}


	@Column(name="URL_KO")
	public String getUrlKo() {
		return this.urlKo;
	}

	public void setUrlKo(String urlKo) {
		this.urlKo = urlKo;
	}


	@Column(name="URL_OK")
	public String getUrlOk() {
		return this.urlOk;
	}

	public void setUrlOk(String urlOk) {
		this.urlOk = urlOk;
	}

	@Transient
	public boolean isAuthenticationRequired() {
		
		return this.authenticated > 0;
	}

}