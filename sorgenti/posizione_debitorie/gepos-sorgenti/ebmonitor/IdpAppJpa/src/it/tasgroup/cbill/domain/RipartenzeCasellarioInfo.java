package it.tasgroup.cbill.domain;

import it.tasgroup.iris2.pagamenti.CasellarioInfo;
import it.tasgroup.iris2.pagamenti.Rendicontazioni;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the RIPARTENZE_CASELLARIO_INFO database table.
 * 
 */
@Entity
@Table(name="RIPARTENZE_CASELLARIO_INFO")
@NamedQuery(name="RipartenzeCasellarioInfo.findAll", query="SELECT r FROM RipartenzeCasellarioInfo r")
public class RipartenzeCasellarioInfo extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal importo;
	private int numRiconciliati;
	private int tentativi;
	private int slice;
	private int sliceSize;
	private CasellarioInfo casellarioInfo;
	private Rendicontazioni rendicontazioni;

	public RipartenzeCasellarioInfo() {
	}
	
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="ripartenze_casellario_pk_gen")	
	@SequenceGenerator(
	    name="ripartenze_casellario_pk_gen",
	    sequenceName="RIPARTENZE_CASELLARIO_I_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	@Column(name="NUM_RICONCILIATI")
	public int getNumRiconciliati() {
		return this.numRiconciliati;
	}

	public void setNumRiconciliati(int numRiconciliati) {
		this.numRiconciliati = numRiconciliati;
	}


	public int getTentativi() {
		return this.tentativi;
	}

	public void setTentativi(int tentativi) {
		this.tentativi = tentativi;
	}


	//bi-directional many-to-one association to CasellarioInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CASELLARIO")
	public CasellarioInfo getCasellarioInfo() {
		return this.casellarioInfo;
	}

	public void setCasellarioInfo(CasellarioInfo casellarioInfo) {
		this.casellarioInfo = casellarioInfo;
	}


	//bi-directional many-to-one association to Rendicontazioni
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RENDICONTAZIONE")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}

	@Column(name="SLICE")
	public int getSlice() {
		return slice;
	}


	public void setSlice(int slice) {
		this.slice = slice;
	}


	@Column(name="SLICE_SIZE")
	public int getSliceSize() {
		return sliceSize;
	}


	public void setSliceSize(int sliceSize) {
		this.sliceSize = sliceSize;
	}
	
	

}