/*
 * XML Type:  ctPagati
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctPagati(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtPagatiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati
{
    
    public CtPagatiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VERSIONEOGGETTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "versioneOggetto");
    private static final javax.xml.namespace.QName DOMINIO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "dominio");
    private static final javax.xml.namespace.QName IDENTIFICATIVOMESSAGGIORICEVUTA$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoMessaggioRicevuta");
    private static final javax.xml.namespace.QName DATAORAMESSAGGIORICEVUTA$6 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "dataOraMessaggioRicevuta");
    private static final javax.xml.namespace.QName RIFERIMENTOMESSAGGIORICHIESTA$8 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "riferimentoMessaggioRichiesta");
    private static final javax.xml.namespace.QName RIFERIMENTODATARICHIESTA$10 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "riferimentoDataRichiesta");
    private static final javax.xml.namespace.QName ISTITUTOATTESTANTE$12 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "istitutoAttestante");
    private static final javax.xml.namespace.QName ENTEBENEFICIARIO$14 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "enteBeneficiario");
    private static final javax.xml.namespace.QName SOGGETTOPAGATORE$16 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "soggettoPagatore");
    private static final javax.xml.namespace.QName DATIPAGAMENTO$18 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiPagamento");
    
    
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 xgetVersioneOggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
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
    public void xsetVersioneOggetto(it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 versioneOggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().add_element_user(VERSIONEOGGETTO$0);
            }
            target.set(versioneOggetto);
        }
    }
    
    /**
     * Gets the "dominio" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio getDominio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio)get_store().find_element_user(DOMINIO$2, 0);
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
    public void setDominio(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio dominio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio)get_store().find_element_user(DOMINIO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio)get_store().add_element_user(DOMINIO$2);
            }
            target.set(dominio);
        }
    }
    
    /**
     * Appends and returns a new empty "dominio" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio addNewDominio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio)get_store().add_element_user(DOMINIO$2);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoMessaggioRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4, 0);
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
    public void xsetIdentificativoMessaggioRicevuta(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoMessaggioRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOMESSAGGIORICEVUTA$4);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime xgetDataOraMessaggioRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime)get_store().find_element_user(DATAORAMESSAGGIORICEVUTA$6, 0);
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
    public void xsetDataOraMessaggioRicevuta(it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime dataOraMessaggioRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime)get_store().find_element_user(DATAORAMESSAGGIORICEVUTA$6, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime)get_store().add_element_user(DATAORAMESSAGGIORICEVUTA$6);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetRiferimentoMessaggioRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8, 0);
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
    public void xsetRiferimentoMessaggioRichiesta(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 riferimentoMessaggioRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(RIFERIMENTOMESSAGGIORICHIESTA$8);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate xgetRiferimentoDataRichiesta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().find_element_user(RIFERIMENTODATARICHIESTA$10, 0);
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
    public void xsetRiferimentoDataRichiesta(it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate riferimentoDataRichiesta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().find_element_user(RIFERIMENTODATARICHIESTA$10, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().add_element_user(RIFERIMENTODATARICHIESTA$10);
            }
            target.set(riferimentoDataRichiesta);
        }
    }
    
    /**
     * Gets the "istitutoAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante getIstitutoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante)get_store().find_element_user(ISTITUTOATTESTANTE$12, 0);
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
    public void setIstitutoAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante istitutoAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante)get_store().find_element_user(ISTITUTOATTESTANTE$12, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante)get_store().add_element_user(ISTITUTOATTESTANTE$12);
            }
            target.set(istitutoAttestante);
        }
    }
    
    /**
     * Appends and returns a new empty "istitutoAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante addNewIstitutoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante)get_store().add_element_user(ISTITUTOATTESTANTE$12);
            return target;
        }
    }
    
    /**
     * Gets the "enteBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario getEnteBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario)get_store().find_element_user(ENTEBENEFICIARIO$14, 0);
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
    public void setEnteBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario enteBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario)get_store().find_element_user(ENTEBENEFICIARIO$14, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario)get_store().add_element_user(ENTEBENEFICIARIO$14);
            }
            target.set(enteBeneficiario);
        }
    }
    
    /**
     * Appends and returns a new empty "enteBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario addNewEnteBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario)get_store().add_element_user(ENTEBENEFICIARIO$14);
            return target;
        }
    }
    
    /**
     * Gets the "soggettoPagatore" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore getSoggettoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore)get_store().find_element_user(SOGGETTOPAGATORE$16, 0);
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
    public void setSoggettoPagatore(it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore soggettoPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore)get_store().find_element_user(SOGGETTOPAGATORE$16, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore)get_store().add_element_user(SOGGETTOPAGATORE$16);
            }
            target.set(soggettoPagatore);
        }
    }
    
    /**
     * Appends and returns a new empty "soggettoPagatore" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore addNewSoggettoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore)get_store().add_element_user(SOGGETTOPAGATORE$16);
            return target;
        }
    }
    
    /**
     * Gets the "datiPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati getDatiPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati)get_store().find_element_user(DATIPAGAMENTO$18, 0);
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
    public void setDatiPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati datiPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati)get_store().find_element_user(DATIPAGAMENTO$18, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati)get_store().add_element_user(DATIPAGAMENTO$18);
            }
            target.set(datiPagamento);
        }
    }
    
    /**
     * Appends and returns a new empty "datiPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati addNewDatiPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati)get_store().add_element_user(DATIPAGAMENTO$18);
            return target;
        }
    }
}
