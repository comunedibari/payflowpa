package it.regioneveneto.mygov.payment.mypivot.domain.to;

@javax.annotation.Generated("dtonator")
public class EnteTipoDovutoTO  implements java.io.Serializable {

    public EnteTO ente;
    public Long id;
    public String codTipo;
    public String deTipo;

    public EnteTipoDovutoTO() {
    }

    public EnteTipoDovutoTO(EnteTO ente, Long id, String codTipo, String deTipo) {
        super();
        this.ente = ente;
        this.id = id;
        this.codTipo = codTipo;
        this.deTipo = deTipo;
    }

    public static EnteTipoDovutoTO copyOf(EnteTipoDovutoTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO) o).ente),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO) o).codTipo,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO) o).deTipo
                );
    }

    public EnteTO getEnte() {
        return ente;
    }

    public void setEnte(EnteTO ente) {
        this.ente = ente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public String getDeTipo() {
        return deTipo;
    }

    public void setDeTipo(String deTipo) {
        this.deTipo = deTipo;
    }

    public EnteTipoDovutoTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "EnteTipoDovutoTO["
            + ente
            + ", "
            + id
            + ", "
            + codTipo
            + ", "
            + deTipo
            + "]";
    }

}
