/*
 * XML Type:  ctRichiestaPagamentoTelematico
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctRichiestaPagamentoTelematico(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtRichiestaPagamentoTelematicoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico
{
    private static final long serialVersionUID = 1L;
    
    public CtRichiestaPagamentoTelematicoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VERSIONEOGGETTO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "versioneOggetto");
    private static final javax.xml.namespace.QName DOMINIO$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "dominio");
    private static final javax.xml.namespace.QName IDENTIFICATIVOMESSAGGIORICHIESTA$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoMessaggioRichiesta");
    private static final javax.xml.namespace.QName DATAORAMESSAGGIORICHIESTA$6 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "dataOraMessaggioRichiesta");
    private static final javax.xml.namespace.QName AUTENTICAZIONESOGGETTO$8 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "autenticazioneSoggetto");
    private static final javax.xml.namespace.QName SOGGETTOVERSANTE$10 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "soggettoVersante");
    private static final javax.xml.namespace.QName SOGGETTOPAGATORE$12 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "soggettoPagatore");
    private static final javax.xml.namespace.QName ENTEBENEFICIARIO$14 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "enteBeneficiario");
    private static final javax.xml.namespace.QName DATIVERSAMENTO$16 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "datiVersamento");
    
    
    /**
     * Gets the "versioneOggetto" element
     */
    public java.lang.String getVersioneOggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "versioneOggetto" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText16 xgetVersioneOggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "versioneOggetto" element
     */
    public void setVersioneOggetto(java.lang.String versioneOggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VERSIONEOGGETTO$0);
            }
            target.setStringValue(versioneOggetto);
        }
    }
    
    /**
     * Sets (as xml) the "versioneOggetto" element
     */
    public void xsetVersioneOggetto(it.gov.digitpa.schemas.x2011.pagamenti.StText16 versioneOggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().add_element_user(VERSIONEOGGETTO$0);
            }
            target.set(versioneOggetto);
        }
    }
    
    /**
     * Gets the "dominio" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDominio getDominio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDominio target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDominio)get_store().find_element_user(DOMINIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "dominio" element
     */
    public void setDominio(it.gov.digitpa.schemas.x2011.pagamenti.CtDominio dominio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDominio target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDominio)get_store().find_element_user(DOMINIO$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDominio)get_store().add_element_user(DOMINIO$2);
            }
            target.set(dominio);
        }
    }
    
    /**
     * Appends and returns a new empty "dominio" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDominio addNewDominio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDominio target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDominio)get_store().add_element_user(DOMINIO$2);
            return target;
        }
    }
    
    /**
     * Gets the "identificativoMessaggioRichiesta" element
     */
    public java.lang.String getIdentificativoMessaggioRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICHIESTA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "identificativoMessaggioRichiesta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoMessaggioRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICHIESTA$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "identificativoMessaggioRichiesta" element
     */
    public void setIdentificativoMessaggioRichiesta(java.lang.String identificativoMessaggioRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICHIESTA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOMESSAGGIORICHIESTA$4);
            }
            target.setStringValue(identificativoMessaggioRichiesta);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoMessaggioRichiesta" element
     */
    public void xsetIdentificativoMessaggioRichiesta(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoMessaggioRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICHIESTA$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(IDENTIFICATIVOMESSAGGIORICHIESTA$4);
            }
            target.set(identificativoMessaggioRichiesta);
        }
    }
    
    /**
     * Gets the "dataOraMessaggioRichiesta" element
     */
    public java.util.Calendar getDataOraMessaggioRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAORAMESSAGGIORICHIESTA$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "dataOraMessaggioRichiesta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime xgetDataOraMessaggioRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime)get_store().find_element_user(DATAORAMESSAGGIORICHIESTA$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "dataOraMessaggioRichiesta" element
     */
    public void setDataOraMessaggioRichiesta(java.util.Calendar dataOraMessaggioRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAORAMESSAGGIORICHIESTA$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAORAMESSAGGIORICHIESTA$6);
            }
            target.setCalendarValue(dataOraMessaggioRichiesta);
        }
    }
    
    /**
     * Sets (as xml) the "dataOraMessaggioRichiesta" element
     */
    public void xsetDataOraMessaggioRichiesta(it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime dataOraMessaggioRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime)get_store().find_element_user(DATAORAMESSAGGIORICHIESTA$6, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime)get_store().add_element_user(DATAORAMESSAGGIORICHIESTA$6);
            }
            target.set(dataOraMessaggioRichiesta);
        }
    }
    
    /**
     * Gets the "autenticazioneSoggetto" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto.Enum getAutenticazioneSoggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTENTICAZIONESOGGETTO$8, 0);
            if (target == null)
            {
                return null;
            }
            return (it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "autenticazioneSoggetto" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto xgetAutenticazioneSoggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto)get_store().find_element_user(AUTENTICAZIONESOGGETTO$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "autenticazioneSoggetto" element
     */
    public void setAutenticazioneSoggetto(it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto.Enum autenticazioneSoggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTENTICAZIONESOGGETTO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUTENTICAZIONESOGGETTO$8);
            }
            target.setEnumValue(autenticazioneSoggetto);
        }
    }
    
    /**
     * Sets (as xml) the "autenticazioneSoggetto" element
     */
    public void xsetAutenticazioneSoggetto(it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto autenticazioneSoggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto)get_store().find_element_user(AUTENTICAZIONESOGGETTO$8, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto)get_store().add_element_user(AUTENTICAZIONESOGGETTO$8);
            }
            target.set(autenticazioneSoggetto);
        }
    }
    
    /**
     * Gets the "soggettoVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante getSoggettoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante)get_store().find_element_user(SOGGETTOVERSANTE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "soggettoVersante" element
     */
    public boolean isSetSoggettoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SOGGETTOVERSANTE$10) != 0;
        }
    }
    
    /**
     * Sets the "soggettoVersante" element
     */
    public void setSoggettoVersante(it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante soggettoVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante)get_store().find_element_user(SOGGETTOVERSANTE$10, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante)get_store().add_element_user(SOGGETTOVERSANTE$10);
            }
            target.set(soggettoVersante);
        }
    }
    
    /**
     * Appends and returns a new empty "soggettoVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante addNewSoggettoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante)get_store().add_element_user(SOGGETTOVERSANTE$10);
            return target;
        }
    }
    
    /**
     * Unsets the "soggettoVersante" element
     */
    public void unsetSoggettoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SOGGETTOVERSANTE$10, 0);
        }
    }
    
    /**
     * Gets the "soggettoPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore getSoggettoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore)get_store().find_element_user(SOGGETTOPAGATORE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "soggettoPagatore" element
     */
    public void setSoggettoPagatore(it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore soggettoPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore)get_store().find_element_user(SOGGETTOPAGATORE$12, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore)get_store().add_element_user(SOGGETTOPAGATORE$12);
            }
            target.set(soggettoPagatore);
        }
    }
    
    /**
     * Appends and returns a new empty "soggettoPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore addNewSoggettoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore)get_store().add_element_user(SOGGETTOPAGATORE$12);
            return target;
        }
    }
    
    /**
     * Gets the "enteBeneficiario" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario getEnteBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario)get_store().find_element_user(ENTEBENEFICIARIO$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "enteBeneficiario" element
     */
    public void setEnteBeneficiario(it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario enteBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario)get_store().find_element_user(ENTEBENEFICIARIO$14, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario)get_store().add_element_user(ENTEBENEFICIARIO$14);
            }
            target.set(enteBeneficiario);
        }
    }
    
    /**
     * Appends and returns a new empty "enteBeneficiario" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario addNewEnteBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario)get_store().add_element_user(ENTEBENEFICIARIO$14);
            return target;
        }
    }
    
    /**
     * Gets the "datiVersamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT getDatiVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT)get_store().find_element_user(DATIVERSAMENTO$16, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "datiVersamento" element
     */
    public void setDatiVersamento(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT datiVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT)get_store().find_element_user(DATIVERSAMENTO$16, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT)get_store().add_element_user(DATIVERSAMENTO$16);
            }
            target.set(datiVersamento);
        }
    }
    
    /**
     * Appends and returns a new empty "datiVersamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT addNewDatiVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT)get_store().add_element_user(DATIVERSAMENTO$16);
            return target;
        }
    }
}
