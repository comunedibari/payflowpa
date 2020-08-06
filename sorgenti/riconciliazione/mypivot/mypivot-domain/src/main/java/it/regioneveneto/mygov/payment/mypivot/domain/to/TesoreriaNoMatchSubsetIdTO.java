package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class TesoreriaNoMatchSubsetIdTO  implements java.io.Serializable {

    public String codBolletta;
    public String codiceIpaEnte;
    public String deAnnoBolletta;

    protected TesoreriaNoMatchSubsetIdTO() {
    }

    public TesoreriaNoMatchSubsetIdTO(String codBolletta, String codiceIpaEnte, String deAnnoBolletta) {
        super();
        this.codBolletta = codBolletta;
        this.codiceIpaEnte = codiceIpaEnte;
        this.deAnnoBolletta = deAnnoBolletta;
    }

    public static TesoreriaNoMatchSubsetIdTO copyOf(TesoreriaNoMatchSubsetIdTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetIdTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetIdTO) o).codBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetIdTO) o).codiceIpaEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetIdTO) o).deAnnoBolletta
                );
    }

    public String getCodBolletta() {
        return codBolletta;
    }

    public void setCodBolletta(String codBolletta) {
        this.codBolletta = codBolletta;
    }

    public String getCodiceIpaEnte() {
        return codiceIpaEnte;
    }

    public void setCodiceIpaEnte(String codiceIpaEnte) {
        this.codiceIpaEnte = codiceIpaEnte;
    }

    public String getDeAnnoBolletta() {
        return deAnnoBolletta;
    }

    public void setDeAnnoBolletta(String deAnnoBolletta) {
        this.deAnnoBolletta = deAnnoBolletta;
    }

    public TesoreriaNoMatchSubsetIdTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetIdTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "TesoreriaNoMatchSubsetIdTO["
            + codBolletta
            + ", "
            + codiceIpaEnte
            + ", "
            + deAnnoBolletta
            + "]";
    }

}
