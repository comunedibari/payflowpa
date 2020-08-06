/*
 * XML Type:  ctRicevutaTelematica
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctRicevutaTelematica(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtRicevutaTelematicaImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica
{
    private static final long serialVersionUID = 1L;
    
    public CtRicevutaTelematicaImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VERSIONEOGGETTO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "versioneOggetto");
    private static final javax.xml.namespace.QName DOMINIO$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "dominio");
    private static final javax.xml.namespace.QName IDENTIFICATIVOMESSAGGIORICEVUTA$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoMessaggioRicevuta");
    private static final javax.xml.namespace.QName DATAORAMESSAGGIORICEVUTA$6 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "dataOraMessaggioRicevuta");
    private static final javax.xml.namespace.QName RIFERIMENTOMESSAGGIORICHIESTA$8 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "riferimentoMessaggioRichiesta");
    private static final javax.xml.namespace.QName RIFERIMENTODATARICHIESTA$10 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "riferimentoDataRichiesta");
    private static final javax.xml.namespace.QName ISTITUTOATTESTANTE$12 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "istitutoAttestante");
    private static final javax.xml.namespace.QName ENTEBENEFICIARIO$14 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "enteBeneficiario");
    private static final javax.xml.namespace.QName SOGGETTOVERSANTE$16 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "soggettoVersante");
    private static final javax.xml.namespace.QName SOGGETTOPAGATORE$18 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "soggettoPagatore");
    private static final javax.xml.namespace.QName DATIPAGAMENTO$20 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "datiPagamento");
    
    
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
     * Gets the "identificativoMessaggioRicevuta" element
     */
    public java.lang.String getIdentificativoMessaggioRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "identificativoMessaggioRicevuta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoMessaggioRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "identificativoMessaggioRicevuta" element
     */
    public void setIdentificativoMessaggioRicevuta(java.lang.String identificativoMessaggioRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4);
            }
            target.setStringValue(identificativoMessaggioRicevuta);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoMessaggioRicevuta" element
     */
    public void xsetIdentificativoMessaggioRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoMessaggioRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4);
            }
            target.set(identificativoMessaggioRicevuta);
        }
    }
    
    /**
     * Gets the "dataOraMessaggioRicevuta" element
     */
    public java.util.Calendar getDataOraMessaggioRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAORAMESSAGGIORICEVUTA$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "dataOraMessaggioRicevuta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime xgetDataOraMessaggioRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime)get_store().find_element_user(DATAORAMESSAGGIORICEVUTA$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "dataOraMessaggioRicevuta" element
     */
    public void setDataOraMessaggioRicevuta(java.util.Calendar dataOraMessaggioRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAORAMESSAGGIORICEVUTA$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAORAMESSAGGIORICEVUTA$6);
            }
            target.setCalendarValue(dataOraMessaggioRicevuta);
        }
    }
    
    /**
     * Sets (as xml) the "dataOraMessaggioRicevuta" element
     */
    public void xsetDataOraMessaggioRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime dataOraMessaggioRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime)get_store().find_element_user(DATAORAMESSAGGIORICEVUTA$6, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime)get_store().add_element_user(DATAORAMESSAGGIORICEVUTA$6);
            }
            target.set(dataOraMessaggioRicevuta);
        }
    }
    
    /**
     * Gets the "riferimentoMessaggioRichiesta" element
     */
    public java.lang.String getRiferimentoMessaggioRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "riferimentoMessaggioRichiesta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetRiferimentoMessaggioRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "riferimentoMessaggioRichiesta" element
     */
    public void setRiferimentoMessaggioRichiesta(java.lang.String riferimentoMessaggioRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8);
            }
            target.setStringValue(riferimentoMessaggioRichiesta);
        }
    }
    
    /**
     * Sets (as xml) the "riferimentoMessaggioRichiesta" element
     */
    public void xsetRiferimentoMessaggioRichiesta(it.gov.digitpa.schemas.x2011.pagamenti.StText35 riferimentoMessaggioRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8);
            }
            target.set(riferimentoMessaggioRichiesta);
        }
    }
    
    /**
     * Gets the "riferimentoDataRichiesta" element
     */
    public java.util.Calendar getRiferimentoDataRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RIFERIMENTODATARICHIESTA$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "riferimentoDataRichiesta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StISODate xgetRiferimentoDataRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODate target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().find_element_user(RIFERIMENTODATARICHIESTA$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "riferimentoDataRichiesta" element
     */
    public void setRiferimentoDataRichiesta(java.util.Calendar riferimentoDataRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RIFERIMENTODATARICHIESTA$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RIFERIMENTODATARICHIESTA$10);
            }
            target.setCalendarValue(riferimentoDataRichiesta);
        }
    }
    
    /**
     * Sets (as xml) the "riferimentoDataRichiesta" element
     */
    public void xsetRiferimentoDataRichiesta(it.gov.digitpa.schemas.x2011.pagamenti.StISODate riferimentoDataRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODate target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().find_element_user(RIFERIMENTODATARICHIESTA$10, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().add_element_user(RIFERIMENTODATARICHIESTA$10);
            }
            target.set(riferimentoDataRichiesta);
        }
    }
    
    /**
     * Gets the "istitutoAttestante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante getIstitutoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante)get_store().find_element_user(ISTITUTOATTESTANTE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "istitutoAttestante" element
     */
    public void setIstitutoAttestante(it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante istitutoAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante)get_store().find_element_user(ISTITUTOATTESTANTE$12, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante)get_store().add_element_user(ISTITUTOATTESTANTE$12);
            }
            target.set(istitutoAttestante);
        }
    }
    
    /**
     * Appends and returns a new empty "istitutoAttestante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante addNewIstitutoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante)get_store().add_element_user(ISTITUTOATTESTANTE$12);
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
     * Gets the "soggettoVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante getSoggettoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante)get_store().find_element_user(SOGGETTOVERSANTE$16, 0);
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
            return get_store().count_elements(SOGGETTOVERSANTE$16) != 0;
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante)get_store().find_element_user(SOGGETTOVERSANTE$16, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante)get_store().add_element_user(SOGGETTOVERSANTE$16);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante)get_store().add_element_user(SOGGETTOVERSANTE$16);
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
            get_store().remove_element(SOGGETTOVERSANTE$16, 0);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore)get_store().find_element_user(SOGGETTOPAGATORE$18, 0);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore)get_store().find_element_user(SOGGETTOPAGATORE$18, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore)get_store().add_element_user(SOGGETTOPAGATORE$18);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore)get_store().add_element_user(SOGGETTOPAGATORE$18);
            return target;
        }
    }
    
    /**
     * Gets the "datiPagamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT getDatiPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT)get_store().find_element_user(DATIPAGAMENTO$20, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "datiPagamento" element
     */
    public void setDatiPagamento(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT datiPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT)get_store().find_element_user(DATIPAGAMENTO$20, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT)get_store().add_element_user(DATIPAGAMENTO$20);
            }
            target.set(datiPagamento);
        }
    }
    
    /**
     * Appends and returns a new empty "datiPagamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT addNewDatiPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT)get_store().add_element_user(DATIPAGAMENTO$20);
            return target;
        }
    }
}
