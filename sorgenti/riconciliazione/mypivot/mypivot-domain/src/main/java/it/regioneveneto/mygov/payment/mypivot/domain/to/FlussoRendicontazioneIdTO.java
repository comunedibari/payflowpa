package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class FlussoRendicontazioneIdTO  implements java.io.Serializable {

    public String codDatiSingPagamIdentificativoUnivocoRiscossione;
    public String codDatiSingPagamIdentificativoUnivocoVersamento;
    public int indiceDatiSingoloPagamento;
    public long mygovEnteId;

    protected FlussoRendicontazioneIdTO() {
    }

    public FlussoRendicontazioneIdTO(String codDatiSingPagamIdentificativoUnivocoRiscossione, String codDatiSingPagamIdentificativoUnivocoVersamento, int indiceDatiSingoloPagamento, long mygovEnteId) {
        super();
        this.codDatiSingPagamIdentificativoUnivocoRiscossione = codDatiSingPagamIdentificativoUnivocoRiscossione;
        this.codDatiSingPagamIdentificativoUnivocoVersamento = codDatiSingPagamIdentificativoUnivocoVersamento;
        this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
        this.mygovEnteId = mygovEnteId;
    }

    public static FlussoRendicontazioneIdTO copyOf(FlussoRendicontazioneIdTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneIdTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneIdTO) o).codDatiSingPagamIdentificativoUnivocoRiscossione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneIdTO) o).codDatiSingPagamIdentificativoUnivocoVersamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneIdTO) o).indiceDatiSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneIdTO) o).mygovEnteId
                );
    }

    public String getCodDatiSingPagamIdentificativoUnivocoRiscossione() {
        return codDatiSingPagamIdentificativoUnivocoRiscossione;
    }

    public void setCodDatiSingPagamIdentificativoUnivocoRiscossione(String codDatiSingPagamIdentificativoUnivocoRiscossione) {
        this.codDatiSingPagamIdentificativoUnivocoRiscossione = codDatiSingPagamIdentificativoUnivocoRiscossione;
    }

    public String getCodDatiSingPagamIdentificativoUnivocoVersamento() {
        return codDatiSingPagamIdentificativoUnivocoVersamento;
    }

    public void setCodDatiSingPagamIdentificativoUnivocoVersamento(String codDatiSingPagamIdentificativoUnivocoVersamento) {
        this.codDatiSingPagamIdentificativoUnivocoVersamento = codDatiSingPagamIdentificativoUnivocoVersamento;
    }

    public int getIndiceDatiSingoloPagamento() {
        return indiceDatiSingoloPagamento;
    }

    public void setIndiceDatiSingoloPagamento(int indiceDatiSingoloPagamento) {
        this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
    }

    public long getMygovEnteId() {
        return mygovEnteId;
    }

    public void setMygovEnteId(long mygovEnteId) {
        this.mygovEnteId = mygovEnteId;
    }

    public FlussoRendicontazioneIdTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneIdTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "FlussoRendicontazioneIdTO["
            + codDatiSingPagamIdentificativoUnivocoRiscossione
            + ", "
            + codDatiSingPagamIdentificativoUnivocoVersamento
            + ", "
            + indiceDatiSingoloPagamento
            + ", "
            + mygovEnteId
            + "]";
    }

}
