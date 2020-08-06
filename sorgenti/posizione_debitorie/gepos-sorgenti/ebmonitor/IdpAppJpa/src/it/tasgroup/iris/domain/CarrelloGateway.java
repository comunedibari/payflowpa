package it.tasgroup.iris.domain;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * The persistent class for the IRIS_GATEWAY_SHOP_CART database table.
 * 
 */
@Entity
@Table(name="IRIS_GATEWAY_SHOP_CART")
public class CarrelloGateway extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private CarrelloGatewayPK id;
	private BigDecimal imTotale;
	private SessioneGateway sessioneGateway;
//	private String condizionePagamento;
	
	/*** Check Property Optimistic Locking***/
	private Long version;	

    public CarrelloGateway() {
    }


	@EmbeddedId
	public CarrelloGatewayPK getId() {
		return this.id;
	}

	public void setId(CarrelloGatewayPK id) {
		this.id = id;
	}
	
	
	//bi-directional many-to-one association to SessioneGateway
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="token", insertable = false, updatable = false)
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
	 * 
	 * @OneToOne (fetch=FetchType.LAZY)
	 * 
	 */
//	@OneToOne 
//	@JoinColumn(name = "ID_CONDIZIONE", insertable = false, updatable = false)
//	public CondizioniPagamento getCondizionePagamento() {
//		return condizionePagamento;
//	}
//
//
//	/**
//	 * @param condizionePagamento the condizionePagamento to set
//	 */
//	public void setCondizionePagamento(CondizioniPagamento condizionePagamento) {
//		this.condizionePagamento = condizionePagamento;
//	}
	
//	@Column(name="ID_CONDIZIONE")
//	public String getCondizionePagamento() {
//		return this.condizionePagamento;
//	}
//
//	public void setCondizionePagamento(String condizionePagamento) {
//		this.condizionePagamento = condizionePagamento;
//	}	
//
	
	@Version
	public Long getVersion() {
		return this.version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}	
}