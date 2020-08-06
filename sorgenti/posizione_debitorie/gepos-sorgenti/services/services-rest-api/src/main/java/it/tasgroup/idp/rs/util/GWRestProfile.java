package it.tasgroup.idp.rs.util;

import it.nch.profile.GatewayProfileManager;

/**
 *
 */
public class GWRestProfile extends GatewayProfileManager {


    private String lapl;
    private String userName;
    private String codiceFiscale;

    public void setLapl(String lapl) {
        this.lapl = lapl;
    }

    @Override
    public String getLapl() {
        return this.lapl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getCodFiscalePagante() {
        return codiceFiscale;
    }

    @Override
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodFiscalePagante(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
}
