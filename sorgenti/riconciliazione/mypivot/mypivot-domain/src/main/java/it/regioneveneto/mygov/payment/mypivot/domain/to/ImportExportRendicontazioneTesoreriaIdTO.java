package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class ImportExportRendicontazioneTesoreriaIdTO  implements java.io.Serializable {

    public String classificazioneCompletezza;
    public String codiceIpaEnte;
    public String codiceIuv;
    public String identificativoFlussoRendicontazione;
    public String identificativoUnivocoRiscossione;

    protected ImportExportRendicontazioneTesoreriaIdTO() {
    }

    public ImportExportRendicontazioneTesoreriaIdTO(String classificazioneCompletezza, String codiceIpaEnte, String codiceIuv, String identificativoFlussoRendicontazione, String identificativoUnivocoRiscossione) {
        super();
        this.classificazioneCompletezza = classificazioneCompletezza;
        this.codiceIpaEnte = codiceIpaEnte;
        this.codiceIuv = codiceIuv;
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
        this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
    }

    public static ImportExportRendicontazioneTesoreriaIdTO copyOf(ImportExportRendicontazioneTesoreriaIdTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO) o).classificazioneCompletezza,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO) o).codiceIpaEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO) o).codiceIuv,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO) o).identificativoFlussoRendicontazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO) o).identificativoUnivocoRiscossione
                );
    }

    public String getClassificazioneCompletezza() {
        return classificazioneCompletezza;
    }

    public void setClassificazioneCompletezza(String classificazioneCompletezza) {
        this.classificazioneCompletezza = classificazioneCompletezza;
    }

    public String getCodiceIpaEnte() {
        return codiceIpaEnte;
    }

    public void setCodiceIpaEnte(String codiceIpaEnte) {
        this.codiceIpaEnte = codiceIpaEnte;
    }

    public String getCodiceIuv() {
        return codiceIuv;
    }

    public void setCodiceIuv(String codiceIuv) {
        this.codiceIuv = codiceIuv;
    }

    public String getIdentificativoFlussoRendicontazione() {
        return identificativoFlussoRendicontazione;
    }

    public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
    }

    public String getIdentificativoUnivocoRiscossione() {
        return identificativoUnivocoRiscossione;
    }

    public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
        this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
    }

    public ImportExportRendicontazioneTesoreriaIdTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "ImportExportRendicontazioneTesoreriaIdTO["
            + classificazioneCompletezza
            + ", "
            + codiceIpaEnte
            + ", "
            + codiceIuv
            + ", "
            + identificativoFlussoRendicontazione
            + ", "
            + identificativoUnivocoRiscossione
            + "]";
    }

}
