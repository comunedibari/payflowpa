package it.tasgroup.idp.cart.orm.monitoraggio;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.tasgroup.idp.cart.orm.monitoraggio.enums.EnumTipoMessaggio;

/**
 * The persistent class for the MESSAGGI database table.
 * 
 */
@Entity
@Table(name="ssil_messaggi")
//@NamedQueries({
//    @NamedQuery(name = "getListaMessaggiNonEsitati",
//    		query = "select m from Messaggi m where m.soggetto = :soggetto and m.sil :sil and m.tipoMessaggio :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.richiestaConsegnata = true and m.esitoConsegnato = false order by m.dataCreazione desc"),
//    @NamedQuery(name = "getNumeroRichieste", query = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil :sil and m.tipoMessaggio :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.richiestaConsegnata = true"),
//    @NamedQuery(name = "getNumeroRichiesteFallite", query = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil :sil and m.tipoMessaggio :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.richiestaConsegnata = false"),
//    @NamedQuery(name = "getNumeroEsiti", query = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil :sil and m.tipoMessaggio :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.richiestaConsegnata = true and m.esitoConsegnato = true"),
//    @NamedQuery(name = "getNumeroEsitiFalliti", query = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil :sil and m.tipoMessaggio :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.richiestaConsegnata = true and m.esitoConsegnato = false"),
//    @NamedQuery(name = "getMessaggio", query = "select m from Messaggi m where m.soggetto = :soggetto and m.sil :sil and m.tipoMessaggio :tipoMessaggio and m.msgId : msgId"),
//    @NamedQuery(name = "getIdMessaggio", query = "select m.id from Messaggi m where m.soggetto = :soggetto and m.sil :sil and m.tipoMessaggio :tipoMessaggio and m.msgId : msgId")
//})

public class Messaggi extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/***
	 * 	tipo VARCHAR(255) NOT NULL,
	soggetto VARCHAR(255) NOT NULL,
	sil VARCHAR(255) NOT NULL,
	msg_id VARCHAR(255) NOT NULL,
	data_creazione TIMESTAMP(3) NOT NULL DEFAULT 0,
	data_ultima_consegna_richiesta TIMESTAMP(3) DEFAULT 0,
	richiesta_consegnata  BOOLEAN NOT NULL,
	data_ultima_consegna_esito TIMESTAMP(3) DEFAULT 0,
	esito_consegnato BOOLEAN NOT NULL,
	 -- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- check constraints
    CONSTRAINT chk_messaggi_1 CHECK (tipo IN ('AP','IP')),
    -- unique constraints
    CONSTRAINT unique_lotti_1 UNIQUE (tipo,soggetto,sil,msg_id),
	-- fk/pk keys constraints
    CONSTRAINT pk_messaggi PRIMARY KEY (id)
	 * 
	 * 
	 */
	
	private Set<Gestioni> gestionis;

	private EnumTipoMessaggio tipoMessaggio;
	
	private String soggetto;
	private String sil;
	private String msgId;
	private Timestamp dataCreazione;
	private Timestamp dataUltimaConsegnaRichiesta;
	private Timestamp dataUltimaConsegnaEsito;
	
	private String flRichiestaConsegnataAsChar;
	private String flEsitoConsegnatoAsChar;
	
	
    /*** Id Mapping ***/
    /*** AUTO, works on DB2 with Identity ***/
    /*** AUTO + seqGenerator, works on Oracle with Sequences ***/
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
//    @GeneratedValue(
//                    strategy=GenerationType.AUTO,
//                    generator="messaggi_pk_gen")
//    @SequenceGenerator(
//        name="messaggi_pk_gen",
//        sequenceName="MESSAGGI_ID_SEQ",
//        allocationSize=1)
    @Column(name="id")
    public Long getId() {
            return super.id;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy="messaggi")
	public Set<Gestioni> getGestionis() {
		return gestionis;
	}

	public void setGestionis(Set<Gestioni> gestionis) {
		this.gestionis = gestionis;
	}

	@Column(name="tipo", nullable = false)
	@Enumerated(EnumType.STRING)
	public EnumTipoMessaggio getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(EnumTipoMessaggio tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	@Column(name="soggetto", nullable = false)
	public String getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}

	@Column(name="sil", nullable = false)
	public String getSil() {
		return sil;
	}

	public void setSil(String sil) {
		this.sil = sil;
	}

	@Column(name="msg_id", nullable = false)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name="data_creazione", nullable = false)
	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	@Column(name="data_ultima_consegna_richiesta", nullable = true)
	public Timestamp getDataUltimaConsegnaRichiesta() {
		return dataUltimaConsegnaRichiesta;
	}

	public void setDataUltimaConsegnaRichiesta(Timestamp dataUltimaConsegnaRichiesta) {
		this.dataUltimaConsegnaRichiesta = dataUltimaConsegnaRichiesta;
	}

    @Transient
	public boolean isFlRichiestaConsegnata() {
		return flRichiestaConsegnataAsChar != null ? (flRichiestaConsegnataAsChar.equals("Y") ? Boolean.TRUE : Boolean.FALSE) : Boolean.FALSE;
	}
    
    @Column(name="fl_richiesta_consegnata", nullable = false)
	public String isFlRichiestaConsegnataAsChar() {
		return flRichiestaConsegnataAsChar;
	}

	public void setFlRichiestaConsegnataAsChar(String richiestaConsegnata) {
		flRichiestaConsegnataAsChar = richiestaConsegnata;
	}
	
	public void setFlRichiestaConsegnata(boolean richiestaConsegnata) {
		flRichiestaConsegnataAsChar = richiestaConsegnata == true ? "Y" : "N";
	}
	

	@Column(name="data_ultima_consegna_esito", nullable = true)
	public Timestamp getDataUltimaConsegnaEsito() {
		return dataUltimaConsegnaEsito;
	}

	public void setDataUltimaConsegnaEsito(Timestamp dataUltimaConsegnaEsito) {
		this.dataUltimaConsegnaEsito = dataUltimaConsegnaEsito;
	}

	@Transient
	public boolean isFlEsitoConsegnato() {
		return flEsitoConsegnatoAsChar != null ? (flEsitoConsegnatoAsChar.equals("Y") ? Boolean.TRUE : Boolean.FALSE) : Boolean.FALSE;
	}

	@Column(name="fl_esito_consegnato", nullable = false)
	public String isFlEsitoConsegnatoAsChar() {
		return flEsitoConsegnatoAsChar;
	}
	
	public void setFlEsitoConsegnatoAsChar(String esitoConsegnato) {
		flEsitoConsegnatoAsChar = esitoConsegnato;
	}
	
	public void setFlEsitoConsegnato(boolean esitoConsegnato) {
		flEsitoConsegnatoAsChar = esitoConsegnato == true ? "Y" : "N";
	}
	
	
}
