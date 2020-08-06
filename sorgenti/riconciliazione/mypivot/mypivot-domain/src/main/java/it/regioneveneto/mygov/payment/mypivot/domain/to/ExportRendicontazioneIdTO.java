package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class ExportRendicontazioneIdTO  implements java.io.Serializable {

    public String codiceIpaEnte;
    public String codiceIuv;
    public String identificativoUnivocoRiscossione;

    protected ExportRendicontazioneIdTO() {
    }

    public ExportRendicontazioneIdTO(String codiceIpaEnte, String codiceIuv, String identificativoUnivocoRiscossione) {
        super();
        this.codiceIpaEnte = codiceIpaEnte;
        this.codiceIuv = codiceIuv;
        this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
    }

    public static ExportRendicontazioneIdTO copyOf(ExportRendicontazioneIdTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.ExportRendicontazioneIdTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ExportRendicontazioneIdTO) o).codiceIpaEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ExportRendicontazioneIdTO) o).codiceIuv,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ExportRendicontazioneIdTO) o).identificativoUnivocoRiscossione
                );
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

    public String getIdentificativoUnivocoRiscossione() {
        return identificativoUnivocoRiscossione;
    }

    public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
        this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
    }

    public ExportRendicontazioneIdTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.ExportRendicontazioneIdTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "ExportRendicontazioneIdTO["
            + codiceIpaEnte
            + ", "
            + codiceIuv
            + ", "
            + identificativoUnivocoRiscossione
            + "]";
    }

}
