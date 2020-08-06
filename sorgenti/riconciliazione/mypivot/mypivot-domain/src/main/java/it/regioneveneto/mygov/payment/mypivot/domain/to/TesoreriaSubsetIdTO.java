package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class TesoreriaSubsetIdTO  implements java.io.Serializable {

    public String codiceIpaEnte;
    public String codiceIuv;
    public String identificativoFlussoRendicontazione;

    protected TesoreriaSubsetIdTO() {
    }

    public TesoreriaSubsetIdTO(String codiceIpaEnte, String codiceIuv, String identificativoFlussoRendicontazione) {
        super();
        this.codiceIpaEnte = codiceIpaEnte;
        this.codiceIuv = codiceIuv;
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
    }

    public static TesoreriaSubsetIdTO copyOf(TesoreriaSubsetIdTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaSubsetIdTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaSubsetIdTO) o).codiceIpaEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaSubsetIdTO) o).codiceIuv,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaSubsetIdTO) o).identificativoFlussoRendicontazione
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

    public String getIdentificativoFlussoRendicontazione() {
        return identificativoFlussoRendicontazione;
    }

    public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
    }

    public TesoreriaSubsetIdTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaSubsetIdTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "TesoreriaSubsetIdTO["
            + codiceIpaEnte
            + ", "
            + codiceIuv
            + ", "
            + identificativoFlussoRendicontazione
            + "]";
    }

}
