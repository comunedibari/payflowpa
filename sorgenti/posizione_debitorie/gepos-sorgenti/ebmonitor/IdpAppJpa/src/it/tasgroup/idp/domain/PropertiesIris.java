package it.tasgroup.idp.domain;

import java.io.Serializable;

import javax.persistence.*;

//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The persistent class for the PROPERTIESIRIS database table.
 * 
 */
@Entity
@NamedQuery(name="ListaPropertiesIris", 
	query="SELECT t FROM PropertiesIris t where t.nome =:nome"
//	,hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }
		)
//Disabilitare durante l'esecuzione dei test JUnit
//@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL) 
public class PropertiesIris implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String descrizione;

	private String nome;

	private String valore;

	public PropertiesIris() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="DESCRIZIONE")
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name="NOME")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name="VALORE")
	public String getValore() {
		return this.valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}