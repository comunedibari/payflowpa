package it.tasgroup.iris.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "CFG_ENTI_TEMA")
@NamedQueries({
        /* ritorna la data dell'ultima modifica (tsAggiornamento)*/
        @NamedQuery(name = "getLastModified", query = "select tsAggiornamento from CfgEntiTema where cdEnte=:cdEnte"),
        
        /* ritorna un oggetto lite (ovvero senza i dati binary) */
        @NamedQuery(name = "getCfgEntiTemaLite", query = "select new it.tasgroup.iris.domain.CfgEntiTema (cdEnte,  nomeImgLogo,  nomeImgHeader,  informazioni, idTema,  opAggiornamento,  opInserimento)\n" +
                "            from CfgEntiTema \n" +
                "            where cdEnte=:cdEnte"),
        
                /* ritorna un oggetto lite (ovvero senza i dati binary) con anche i timestamp di aggiornamento/inserimento */
        @NamedQuery(name = "getCfgEntiTemaLiteWithTS", query = "select new it.tasgroup.iris.domain.CfgEntiTema (cdEnte,  nomeImgLogo,  nomeImgHeader,  informazioni, idTema,  opAggiornamento,  opInserimento, tsAggiornamento, tsInserimento)\n" +
                "            from CfgEntiTema \n" +
                "            where cdEnte=:cdEnte")

})
public class CfgEntiTema extends BaseIdEntity {
    private static final long serialVersionUID = 1L;
    private String cdEnte;
    private byte[] logoBlob;
    private String nomeImgLogo;
    private byte[] headerBlob;
    private String nomeImgHeader;
    private String informazioni;
    private String idTema;
    private String opAggiornamento;
    private String opInserimento;
    private Timestamp tsAggiornamento;
    private Timestamp tsInserimento;

    public CfgEntiTema() {
    }


    /**
     * Consente di costruire un oggetto lite (senza dati binary).
     * E' usato direttamente dalla query JPA mediante costrutto new, per questo motivo il costruttore dichiara
     * parametri Date e non Timestamp, a causa del seguente bug hibernate :
     * [select new fails when data is of type Timestamp] https://hibernate.atlassian.net/browse/HHH-278
     * Il caso in cui venissero passati parametri di tipo != Timestamp viene gestito con la costruzione explicita
     * di un oggetto di tipo Timestamp (a partire dall'oggetto Date passato)
     *
     * @param cdEnte
     * @param nomeImgLogo
     * @param nomeImgHeader
     * @param informazioni
     * @param idTema
     * @param opAggiornamento
     * @param opInserimento
     * @param tsAggiornamento
     * @param tsInserimento
     */
    public CfgEntiTema(String cdEnte, String nomeImgLogo, String nomeImgHeader, String informazioni,
                       String idTema, String opAggiornamento, String opInserimento,
                       Date tsAggiornamento, Date tsInserimento) {
        this.cdEnte = cdEnte;
        this.nomeImgLogo = nomeImgLogo;
        this.nomeImgHeader = nomeImgHeader;
        this.informazioni = informazioni;
        this.idTema = idTema;
        this.opAggiornamento = opAggiornamento;
        this.opInserimento = opInserimento;
        this.tsAggiornamento = tsAggiornamento instanceof Timestamp ? (Timestamp) tsAggiornamento : new Timestamp(tsAggiornamento.getTime());
        this.tsInserimento = tsInserimento instanceof Timestamp ? (Timestamp) tsInserimento : new Timestamp(tsInserimento.getTime());
    }

    public CfgEntiTema(String cdEnte, String nomeImgLogo, String nomeImgHeader, String informazioni,
                       String idTema, String opAggiornamento, String opInserimento
    ) {

        this.cdEnte = cdEnte;
        this.nomeImgLogo = nomeImgLogo;
        this.nomeImgHeader = nomeImgHeader;
        this.informazioni = informazioni;
        this.idTema = idTema;
        this.opAggiornamento = opAggiornamento;
        this.opInserimento = opInserimento;
    }


    @Id
    @Column(name = "CD_ENTE")
    public String getCdEnte() {
        return this.cdEnte;
    }

    public void setCdEnte(String idEnte) {
        this.cdEnte = idEnte;
    }


    @Lob
    @Column(name = "LOGO_BLOB")
    public byte[] getLogoBlob() {
        return this.logoBlob;
    }

    public void setLogoBlob(byte[] logoBlob) {
        this.logoBlob = logoBlob;
    }

    @Lob
    @Column(name = "HEADER_BLOB")
    public byte[] getHeaderBlob() {
        return headerBlob;
    }

    public void setHeaderBlob(byte[] headerBlob) {
        this.headerBlob = headerBlob;
    }

    @Column(name = "NOME_IMG_LOGO")
    public String getNomeImgLogo() {
        return this.nomeImgLogo;
    }

    public void setNomeImgLogo(String nomeFileLogo) {
        this.nomeImgLogo = nomeFileLogo;
    }

    @Column(name = "INFORMAZIONI")
    public String getInformazioni() {
        return informazioni;
    }

    public void setInformazioni(String informazioni) {
        this.informazioni = informazioni;
    }

    @Column(name = "NOME_IMG_HEADER")
    public String getNomeImgHeader() {
        return nomeImgHeader;
    }

    public void setNomeImgHeader(String nomeImgHeader) {
        this.nomeImgHeader = nomeImgHeader;
    }

    @Column(name = "OP_AGGIORNAMENTO")
    public String getOpAggiornamento() {
        return this.opAggiornamento;
    }

    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }


    @Column(name = "OP_INSERIMENTO")
    public String getOpInserimento() {
        return this.opInserimento;
    }

    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }


    @Column(name = "TS_AGGIORNAMENTO")
    public Timestamp getTsAggiornamento() {
        return this.tsAggiornamento;
    }

    public void setTsAggiornamento(Timestamp tsAggiornamento) {
        this.tsAggiornamento = tsAggiornamento;
    }


    @Column(name = "TS_INSERIMENTO")
    public Timestamp getTsInserimento() {
        return this.tsInserimento;
    }

    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }


    @Column(name = "ID_TEMA")
    public String getIdTema() {
        return idTema;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }
}



