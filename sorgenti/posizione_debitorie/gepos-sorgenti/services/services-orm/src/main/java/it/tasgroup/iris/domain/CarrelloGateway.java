package it.tasgroup.iris.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the IRIS_GATEWAY_SHOP_CART database table.
 * 
 */
@Entity
@Table(name="IRIS_GATEWAY_SHOP_CART")
public class CarrelloGateway extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private CarrelloGatewayPK id;
	private String opAggiornamento;
	private String opInserimento;
	private BigDecimal imTotale;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private SessioneGateway sessioneGateway;
	private CondizionePagamento condizionePagamento;
	
	private String flagOpposizione730;

    public CarrelloGateway() {
    }


	@EmbeddedId
	public CarrelloGatewayPK getId() {
		return this.id;
	}

	public void setId(CarrelloGatewayPK id) {
		this.id = id;
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
	
	//bi-directional many-to-one association to SessioneGateway
	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId(value = "token")
	@JoinColumn(name = "token")
	public SessioneGateway getSessioneGateway() {
		return this.sessioneGateway;
	}

	public void setSessioneGateway(SessioneGateway sessioneGateway) {
		this.sessioneGateway = sessioneGateway;
	}


	/**
	 * @return the imTotale
	 */
	@Column(name="IM_TOTALE")
	public BigDecimal getImTotale() {
		return imTotale;
	}


	/**
	 * @param imTotale the imTotale to set
	 */
	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}

	/**
	 * @return the condizionePagamento
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@MapsId(value = "idCondizione")
	@JoinColumn(name = "ID_CONDIZIONE")
	public CondizionePagamento getCondizionePagamento() {
		return condizionePagamento;
	}

	/**
	 * @param condizionePagamento the condizionePagamento to set
	 */
	public void setCondizionePagamento(CondizionePagamento condizionePagamento) {
		this.condizionePagamento = condizionePagamento;
	}

	@Column(name="FLAG_OPPOSIZIONE_730")
	public String getFlagOpposizione730() {
		return flagOpposizione730;
	}

	public void setFlagOpposizione730(String flagOpposizione730) {
		this.flagOpposizione730 = flagOpposizione730;
	}
	
}