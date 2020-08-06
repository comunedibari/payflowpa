package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class OperatoreEnteTipoDovutoTO  implements java.io.Serializable {

    public EnteTipoDovutoTO enteTipoDovuto;
    public Long id;
    public String codFedUserId;
    public boolean flgAttivo;

    public OperatoreEnteTipoDovutoTO() {
    }

    public OperatoreEnteTipoDovutoTO(EnteTipoDovutoTO enteTipoDovuto, Long id, String codFedUserId, boolean flgAttivo) {
        super();
        this.enteTipoDovuto = enteTipoDovuto;
        this.id = id;
        this.codFedUserId = codFedUserId;
        this.flgAttivo = flgAttivo;
    }

    public static OperatoreEnteTipoDovutoTO copyOf(OperatoreEnteTipoDovutoTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO) o).enteTipoDovuto),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO) o).codFedUserId,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO) o).flgAttivo
                );
    }

    public EnteTipoDovutoTO getEnteTipoDovuto() {
        return enteTipoDovuto;
    }

    public void setEnteTipoDovuto(EnteTipoDovutoTO enteTipoDovuto) {
        this.enteTipoDovuto = enteTipoDovuto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodFedUserId() {
        return codFedUserId;
    }

    public void setCodFedUserId(String codFedUserId) {
        this.codFedUserId = codFedUserId;
    }

    public boolean getFlgAttivo() {
        return flgAttivo;
    }

    public void setFlgAttivo(boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    public OperatoreEnteTipoDovutoTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "OperatoreEnteTipoDovutoTO["
            + enteTipoDovuto
            + ", "
            + id
            + ", "
            + codFedUserId
            + ", "
            + flgAttivo
            + "]";
    }

}
