package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class FlussoExportIdTO  implements java.io.Serializable {

    public String codEDatiPagDatiSingPagIdUnivocoRiscoss;
    public String codRpSilinviarpIdUnivocoVersamento;
    public int indiceDatiSingoloPagamento;
    public long mygovEnteId;

    public FlussoExportIdTO() {
    }

    public FlussoExportIdTO(String codEDatiPagDatiSingPagIdUnivocoRiscoss, String codRpSilinviarpIdUnivocoVersamento, int indiceDatiSingoloPagamento, long mygovEnteId) {
        super();
        this.codEDatiPagDatiSingPagIdUnivocoRiscoss = codEDatiPagDatiSingPagIdUnivocoRiscoss;
        this.codRpSilinviarpIdUnivocoVersamento = codRpSilinviarpIdUnivocoVersamento;
        this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
        this.mygovEnteId = mygovEnteId;
    }

    public static FlussoExportIdTO copyOf(FlussoExportIdTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportIdTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportIdTO) o).codEDatiPagDatiSingPagIdUnivocoRiscoss,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportIdTO) o).codRpSilinviarpIdUnivocoVersamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportIdTO) o).indiceDatiSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportIdTO) o).mygovEnteId
                );
    }

    public String getCodEDatiPagDatiSingPagIdUnivocoRiscoss() {
        return codEDatiPagDatiSingPagIdUnivocoRiscoss;
    }

    public void setCodEDatiPagDatiSingPagIdUnivocoRiscoss(String codEDatiPagDatiSingPagIdUnivocoRiscoss) {
        this.codEDatiPagDatiSingPagIdUnivocoRiscoss = codEDatiPagDatiSingPagIdUnivocoRiscoss;
    }

    public String getCodRpSilinviarpIdUnivocoVersamento() {
        return codRpSilinviarpIdUnivocoVersamento;
    }

    public void setCodRpSilinviarpIdUnivocoVersamento(String codRpSilinviarpIdUnivocoVersamento) {
        this.codRpSilinviarpIdUnivocoVersamento = codRpSilinviarpIdUnivocoVersamento;
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

    public FlussoExportIdTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportIdTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "FlussoExportIdTO["
            + codEDatiPagDatiSingPagIdUnivocoRiscoss
            + ", "
            + codRpSilinviarpIdUnivocoVersamento
            + ", "
            + indiceDatiSingoloPagamento
            + ", "
            + mygovEnteId
            + "]";
    }

}
