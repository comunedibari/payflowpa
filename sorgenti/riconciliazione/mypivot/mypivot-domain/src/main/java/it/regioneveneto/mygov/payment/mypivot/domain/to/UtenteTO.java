package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class UtenteTO  implements java.io.Serializable {

    public Long id;
    public String codCodiceFiscaleUtente;
    public String codFedUserId;
    public String deEmailAddress;
    public String deFirstname;
    public String deLastname;
    public Date dtUltimoLogin;
    public int version;

    public UtenteTO() {
    }

    public UtenteTO(Long id, String codCodiceFiscaleUtente, String codFedUserId, String deEmailAddress, String deFirstname, String deLastname, Date dtUltimoLogin, int version) {
        super();
        this.id = id;
        this.codCodiceFiscaleUtente = codCodiceFiscaleUtente;
        this.codFedUserId = codFedUserId;
        this.deEmailAddress = deEmailAddress;
        this.deFirstname = deFirstname;
        this.deLastname = deLastname;
        this.dtUltimoLogin = dtUltimoLogin;
        this.version = version;
    }

    public static UtenteTO copyOf(UtenteTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO) o).codCodiceFiscaleUtente,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO) o).codFedUserId,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO) o).deEmailAddress,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO) o).deFirstname,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO) o).deLastname,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO) o).dtUltimoLogin,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO) o).version
                );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCodiceFiscaleUtente() {
        return codCodiceFiscaleUtente;
    }

    public void setCodCodiceFiscaleUtente(String codCodiceFiscaleUtente) {
        this.codCodiceFiscaleUtente = codCodiceFiscaleUtente;
    }

    public String getCodFedUserId() {
        return codFedUserId;
    }

    public void setCodFedUserId(String codFedUserId) {
        this.codFedUserId = codFedUserId;
    }

    public String getDeEmailAddress() {
        return deEmailAddress;
    }

    public void setDeEmailAddress(String deEmailAddress) {
        this.deEmailAddress = deEmailAddress;
    }

    public String getDeFirstname() {
        return deFirstname;
    }

    public void setDeFirstname(String deFirstname) {
        this.deFirstname = deFirstname;
    }

    public String getDeLastname() {
        return deLastname;
    }

    public void setDeLastname(String deLastname) {
        this.deLastname = deLastname;
    }

    public Date getDtUltimoLogin() {
        return dtUltimoLogin;
    }

    public void setDtUltimoLogin(Date dtUltimoLogin) {
        this.dtUltimoLogin = dtUltimoLogin;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public UtenteTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "UtenteTO["
            + id
            + ", "
            + codCodiceFiscaleUtente
            + ", "
            + codFedUserId
            + ", "
            + deEmailAddress
            + ", "
            + deFirstname
            + ", "
            + deLastname
            + ", "
            + dtUltimoLogin
            + ", "
            + version
            + "]";
    }

}
