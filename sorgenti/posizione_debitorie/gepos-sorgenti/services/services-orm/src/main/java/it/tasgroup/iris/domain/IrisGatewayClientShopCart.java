package it.tasgroup.iris.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the IRIS_GATEWAY_CLIENT_SHOP_CART database table.
 * 
 */
@Entity
@Table(name="IRIS_GATEWAY_CLIENT_SHOP_CART")
@NamedQueries(
{
	@NamedQuery(
		name="getIrisGatewayClientShopCartBySessionId",
		query="select cartItem from IrisGatewayClientShopCart cartItem where cartItem.sessione.sessionid=:sessionId")				
})
public class IrisGatewayClientShopCart extends BaseIdEntity {
	private static final long serialVersionUID = 1L;
	private BigDecimal imTotale;
	private String opAggiornamento;
	private String opInserimento;
	private String tiSpontaneo;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private CfgIrisGatewayClient cfgIrisGatewayClient;
	private CondizionePagamento condizione;
	private Sessione sessione;
	
    public IrisGatewayClientShopCart() {
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="iris_gateway_client_shop_cart_pk_gen")	
	@SequenceGenerator(
		    name="iris_gateway_client_shop_cart_pk_gen",
		    sequenceName="IRIS_GATEWAY_CLIENT_SH_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 			    
    


	@Column(name="IM_TOTALE")
	public BigDecimal getImTotale() {
		return this.imTotale;
	}

	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
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


	@Column(name="TI_SPONTANEO")
	public String getTiSpontaneo() {
		return this.tiSpontaneo;
	}

	public void setTiSpontaneo(String tiSpontaneo) {
		this.tiSpontaneo = tiSpontaneo;
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

	//bi-directional many-to-one association to CfgIrisGatewayClient
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GTW_CLIENT_ID")
	public CfgIrisGatewayClient getCfgIrisGatewayClient() {
		return this.cfgIrisGatewayClient;
	}

	public void setCfgIrisGatewayClient(CfgIrisGatewayClient cfgIrisGatewayClient) {
		this.cfgIrisGatewayClient = cfgIrisGatewayClient;
	}
	

	//bi-directional many-to-one association to CondizionePagamento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONDIZIONE")
	public CondizionePagamento getCondizionePagamento() {
		return this.condizione;
	}

	public void setCondizionePagamento(CondizionePagamento condizione) {
		this.condizione = condizione;
	}
	
	//bi-directional many-to-one association to Sessione
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SESSION_ID")
	public Sessione getSessione() {
		return this.sessione;
	}

	public void setSessione(Sessione sessione) {
		this.sessione = sessione;
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		IrisGatewayClientShopCart other = (IrisGatewayClientShopCart) obj;
		
		if (condizione == null) {
			
			if (other.condizione != null)
				return false;
			
		} else if (!condizione.equals(other.condizione))
			
			return false;
		
		if (sessione == null) {
			
			if (other.sessione != null)
				
				return false;
			
		} else if (!sessione.equals(other.sessione))
			
			return false;
		
		return true;
	}
	
}