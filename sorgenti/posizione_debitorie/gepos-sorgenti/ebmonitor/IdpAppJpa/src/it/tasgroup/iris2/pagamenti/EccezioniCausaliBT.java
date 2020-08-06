package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseIdEntity;

@Entity
@Table(name="GDC_ECCEZIONI_CAUSALI_BT")
@NamedQueries ({
	@NamedQuery (name="findEccezioniByCodiceBT",
				query="SELECT ecbt FROM EccezioniCausaliBT ecbt WHERE codiceBT = :codiceBT"
	)
})
public class EccezioniCausaliBT extends BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String codiceBT;
	private String regularExpression;
	
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="gdc_eccezioni_causali_bt_pk_gen")
	@SequenceGenerator(
	    name="gdc_eccezioni_causali_bt_pk_gen",
	    sequenceName="GDC_ECCEZIONICAUSALI_BT_ID_SEQ",
	    allocationSize=1)
	public Long getId() {
		return id;
	}	

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="CODICE_BT")
	public String getCodiceBT() {
		return codiceBT;
	}
	public void setCodiceBT(String codiceBT) {
		this.codiceBT = codiceBT;
	}
	@Column(name="REGULAR_EXPRESSION")
	public String getRegularExpression() {
		return regularExpression;
	}
	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}

	
	
}
