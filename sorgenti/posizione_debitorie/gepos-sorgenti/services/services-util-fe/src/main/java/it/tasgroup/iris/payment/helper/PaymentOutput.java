package it.tasgroup.iris.payment.helper;

import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;

/**
 *
 */
public class PaymentOutput {


    private String faultNodoSPC;
    private String msgNodoSpc;
    private String idSessioneNDP;
    private String urlToRedirect;
    private DistintaCartaCreditoVO distintaPagamento;
    private String forward;
    private String codPagamento;

    public void setFaultNodoSPC(String faultNodoSPC) {
        this.faultNodoSPC = faultNodoSPC;
    }

    public String getFaultNodoSPC() {
        return faultNodoSPC;
    }

    public void setMsgNodoSpc(String msgNodoSpc) {
        this.msgNodoSpc = msgNodoSpc;
    }

    public String getMsgNodoSpc() {
        return msgNodoSpc;
    }

    public void setIdSessioneNDP(String idSessioneNDP) {
        this.idSessioneNDP = idSessioneNDP;
    }

    public String getIdSessioneNDP() {
        return idSessioneNDP;
    }

    public void setUrlToRedirect(String urlToRedirect) {
        this.urlToRedirect = urlToRedirect;
    }

    public String getUrlToRedirect() {
        return urlToRedirect;
    }

    public void setDistintaPagamento(DistintaCartaCreditoVO distintaPagamento) {
        this.distintaPagamento = distintaPagamento;
    }

    public DistintaCartaCreditoVO getDistintaPagamento() {
        return distintaPagamento;
    }


    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getForward() {
        return forward;
    }

    public String getCodPagamento() {
        return codPagamento;
    }

    public void setCodPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
    }
}
