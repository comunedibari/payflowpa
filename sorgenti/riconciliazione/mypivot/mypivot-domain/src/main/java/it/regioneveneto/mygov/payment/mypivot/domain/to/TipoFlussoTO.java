package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class TipoFlussoTO  implements java.io.Serializable {

    public Long id;
    public char codTipo;
    public String deTipo;
    public Date dtCreazione;
    public Date dtUltimaModifica;
    public int version;

    public TipoFlussoTO() {
    }

    public TipoFlussoTO(Long id, char codTipo, String deTipo, Date dtCreazione, Date dtUltimaModifica, int version) {
        super();
        this.id = id;
        this.codTipo = codTipo;
        this.deTipo = deTipo;
        this.dtCreazione = dtCreazione;
        this.dtUltimaModifica = dtUltimaModifica;
        this.version = version;
    }

    public static TipoFlussoTO copyOf(TipoFlussoTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO) o).codTipo,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO) o).deTipo,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO) o).version
                );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(char codTipo) {
        this.codTipo = codTipo;
    }

    public String getDeTipo() {
        return deTipo;
    }

    public void setDeTipo(String deTipo) {
        this.deTipo = deTipo;
    }

    public Date getDtCreazione() {
        return dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
        this.dtCreazione = dtCreazione;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public TipoFlussoTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "TipoFlussoTO["
            + id
            + ", "
            + codTipo
            + ", "
            + deTipo
            + ", "
            + dtCreazione
            + ", "
            + dtUltimaModifica
            + ", "
            + version
            + "]";
    }

}
