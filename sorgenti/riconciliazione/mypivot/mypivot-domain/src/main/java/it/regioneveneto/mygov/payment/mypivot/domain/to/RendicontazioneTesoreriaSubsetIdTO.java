package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class RendicontazioneTesoreriaSubsetIdTO  implements java.io.Serializable {

    public String codiceIpaEnte;
    public String identificativoFlussoRendicontazione;

    protected RendicontazioneTesoreriaSubsetIdTO() {
    }

    public RendicontazioneTesoreriaSubsetIdTO(String codiceIpaEnte, String identificativoFlussoRendicontazione) {
        super();
        this.codiceIpaEnte = codiceIpaEnte;
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
    }

    public static RendicontazioneTesoreriaSubsetIdTO copyOf(RendicontazioneTesoreriaSubsetIdTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetIdTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetIdTO) o).codiceIpaEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetIdTO) o).identificativoFlussoRendicontazione
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

    public RendicontazioneTesoreriaSubsetIdTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetIdTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "RendicontazioneTesoreriaSubsetIdTO["
            + codiceIpaEnte
            + ", "
            + identificativoFlussoRendicontazione
            + "]";
    }

}
