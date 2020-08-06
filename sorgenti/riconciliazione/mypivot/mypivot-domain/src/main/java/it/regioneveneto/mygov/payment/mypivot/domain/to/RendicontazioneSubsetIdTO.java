package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class RendicontazioneSubsetIdTO  implements java.io.Serializable {

    public String codiceIpaEnte;
    public String identificativoFlussoRendicontazione;

    public RendicontazioneSubsetIdTO() {
    }

    public RendicontazioneSubsetIdTO(String codiceIpaEnte, String identificativoFlussoRendicontazione) {
        super();
        this.codiceIpaEnte = codiceIpaEnte;
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
    }

    public static RendicontazioneSubsetIdTO copyOf(RendicontazioneSubsetIdTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetIdTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetIdTO) o).codiceIpaEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetIdTO) o).identificativoFlussoRendicontazione
                );
    }

    public String getCodiceIpaEnte() {
        return codiceIpaEnte;
    }

    public void setCodiceIpaEnte(String codiceIpaEnte) {
        this.codiceIpaEnte = codiceIpaEnte;
    }

    public String getIdentificativoFlussoRendicontazione() {
        return identificativoFlussoRendicontazione;
    }

    public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
    }

    public RendicontazioneSubsetIdTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetIdTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "RendicontazioneSubsetIdTO["
            + codiceIpaEnte
            + ", "
            + identificativoFlussoRendicontazione
            + "]";
    }

}
