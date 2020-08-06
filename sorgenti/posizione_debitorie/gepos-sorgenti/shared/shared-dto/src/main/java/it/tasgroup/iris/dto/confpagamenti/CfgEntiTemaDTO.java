package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;
import java.sql.Timestamp;


public class CfgEntiTemaDTO implements Serializable {
    private String idEnte;
    private String idTema;
    private byte[] logoBlob;
    private String nomeImgLogo;
    private byte[] headerBlob;
    private String nomeImgHeader;
    private String informazioni;

    private Timestamp tsAggiornamento;
    private Timestamp tsInserimento;

    private String opAggiornamento;
    private String opInserimento;

    public String getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(String idEnte) {
        this.idEnte = idEnte;
    }

    public byte[] getLogoBlob() {
        return logoBlob;
    }

    public void setLogoBlob(byte[] logoBlob) {
        this.logoBlob = logoBlob;
    }

    public String getNomeImgLogo() {
        return nomeImgLogo;
    }

    public void setNomeImgLogo(String nomeImgLogo) {
        this.nomeImgLogo = nomeImgLogo;
    }

    public byte[] getHeaderBlob() {
        return headerBlob;
    }

    public void setHeaderBlob(byte[] headerBlob) {
        this.headerBlob = headerBlob;
    }

    public String getNomeImgHeader() {
        return nomeImgHeader;
    }

    public void setNomeImgHeader(String nomeImgHeader) {
        this.nomeImgHeader = nomeImgHeader;
    }

    public String getInformazioni() {
        return informazioni;
    }

    public void setInformazioni(String informazioni) {
        this.informazioni = informazioni;
    }

    public String getIdTema() {
        return idTema;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }

    public String getOpAggiornamento() {
        return opAggiornamento;
    }

    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }

    public String getOpInserimento() {
        return opInserimento;
    }

    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }

    public Timestamp getTsAggiornamento() {
        return tsAggiornamento;
    }

    public void setTsAggiornamento(Timestamp tsAggiornamento) {
        this.tsAggiornamento = tsAggiornamento;
    }

    public Timestamp getTsInserimento() {
        return tsInserimento;
    }

    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }

}
