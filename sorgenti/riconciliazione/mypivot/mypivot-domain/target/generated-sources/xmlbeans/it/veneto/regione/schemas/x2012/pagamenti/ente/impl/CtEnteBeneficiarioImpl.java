/*
 * XML Type:  ctEnteBeneficiario
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctEnteBeneficiario(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtEnteBeneficiarioImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario
{
    
    public CtEnteBeneficiarioImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOBENEFICIARIO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoBeneficiario");
    private static final javax.xml.namespace.QName DENOMINAZIONEBENEFICIARIO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "denominazioneBeneficiario");
    private static final javax.xml.namespace.QName CODICEUNITOPERBENEFICIARIO$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "codiceUnitOperBeneficiario");
    private static final javax.xml.namespace.QName DENOMUNITOPERBENEFICIARIO$6 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "denomUnitOperBeneficiario");
    private static final javax.xml.namespace.QName INDIRIZZOBENEFICIARIO$8 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "indirizzoBeneficiario");
    private static final javax.xml.namespace.QName CIVICOBENEFICIARIO$10 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "civicoBeneficiario");
    private static final javax.xml.namespace.QName CAPBENEFICIARIO$12 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "capBeneficiario");
    private static final javax.xml.namespace.QName LOCALITABENEFICIARIO$14 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "localitaBeneficiario");
    private static final javax.xml.namespace.QName PROVINCIABENEFICIARIO$16 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "provinciaBeneficiario");
    private static final javax.xml.namespace.QName NAZIONEBENEFICIARIO$18 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "nazioneBeneficiario");
    
    
    /**
     * Gets the "identificativoUnivocoBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG getIdentificativoUnivocoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG)get_store().find_element_user(IDENTIFICATIVOUNIVOCOBENEFICIARIO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "identificativoUnivocoBeneficiario" element
     */
    public void setIdentificativoUnivocoBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG identificativoUnivocoBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG)get_store().find_element_user(IDENTIFICATIVOUNIVOCOBENEFICIARIO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG)get_store().add_element_user(IDENTIFICATIVOUNIVOCOBENEFICIARIO$0);
            }
            target.set(identificativoUnivocoBeneficiario);
        }
    }
    
    /**
     * Appends and returns a new empty "identificativoUnivocoBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG addNewIdentificativoUnivocoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivocoPersonaG)get_store().add_element_user(IDENTIFICATIVOUNIVOCOBENEFICIARIO$0);
            return target;
        }
    }
    
    /**
     * Gets the "denominazioneBeneficiario" element
     */
    public java.lang.String getDenominazioneBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONEBENEFICIARIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "denominazioneBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 xgetDenominazioneBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(DENOMINAZIONEBENEFICIARIO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "denominazioneBeneficiario" element
     */
    public void setDenominazioneBeneficiario(java.lang.String denominazioneBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONEBENEFICIARIO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMINAZIONEBENEFICIARIO$2);
            }
            target.setStringValue(denominazioneBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "denominazioneBeneficiario" element
     */
    public void xsetDenominazioneBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 denominazioneBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(DENOMINAZIONEBENEFICIARIO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().add_element_user(DENOMINAZIONEBENEFICIARIO$2);
            }
            target.set(denominazioneBeneficiario);
        }
    }
    
    /**
     * Gets the "codiceUnitOperBeneficiario" element
     */
    public java.lang.String getCodiceUnitOperBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEUNITOPERBENEFICIARIO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codiceUnitOperBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetCodiceUnitOperBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(CODICEUNITOPERBENEFICIARIO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "codiceUnitOperBeneficiario" element
     */
    public boolean isSetCodiceUnitOperBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CODICEUNITOPERBENEFICIARIO$4) != 0;
        }
    }
    
    /**
     * Sets the "codiceUnitOperBeneficiario" element
     */
    public void setCodiceUnitOperBeneficiario(java.lang.String codiceUnitOperBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEUNITOPERBENEFICIARIO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEUNITOPERBENEFICIARIO$4);
            }
            target.setStringValue(codiceUnitOperBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "codiceUnitOperBeneficiario" element
     */
    public void xsetCodiceUnitOperBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 codiceUnitOperBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(CODICEUNITOPERBENEFICIARIO$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(CODICEUNITOPERBENEFICIARIO$4);
            }
            target.set(codiceUnitOperBeneficiario);
        }
    }
    
    /**
     * Unsets the "codiceUnitOperBeneficiario" element
     */
    public void unsetCodiceUnitOperBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CODICEUNITOPERBENEFICIARIO$4, 0);
        }
    }
    
    /**
     * Gets the "denomUnitOperBeneficiario" element
     */
    public java.lang.String getDenomUnitOperBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMUNITOPERBENEFICIARIO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "denomUnitOperBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 xgetDenomUnitOperBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(DENOMUNITOPERBENEFICIARIO$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "denomUnitOperBeneficiario" element
     */
    public boolean isSetDenomUnitOperBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DENOMUNITOPERBENEFICIARIO$6) != 0;
        }
    }
    
    /**
     * Sets the "denomUnitOperBeneficiario" element
     */
    public void setDenomUnitOperBeneficiario(java.lang.String denomUnitOperBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMUNITOPERBENEFICIARIO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMUNITOPERBENEFICIARIO$6);
            }
            target.setStringValue(denomUnitOperBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "denomUnitOperBeneficiario" element
     */
    public void xsetDenomUnitOperBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 denomUnitOperBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(DENOMUNITOPERBENEFICIARIO$6, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().add_element_user(DENOMUNITOPERBENEFICIARIO$6);
            }
            target.set(denomUnitOperBeneficiario);
        }
    }
    
    /**
     * Unsets the "denomUnitOperBeneficiario" element
     */
    public void unsetDenomUnitOperBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DENOMUNITOPERBENEFICIARIO$6, 0);
        }
    }
    
    /**
     * Gets the "indirizzoBeneficiario" element
     */
    public java.lang.String getIndirizzoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZOBENEFICIARIO$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "indirizzoBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 xgetIndirizzoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(INDIRIZZOBENEFICIARIO$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "indirizzoBeneficiario" element
     */
    public boolean isSetIndirizzoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INDIRIZZOBENEFICIARIO$8) != 0;
        }
    }
    
    /**
     * Sets the "indirizzoBeneficiario" element
     */
    public void setIndirizzoBeneficiario(java.lang.String indirizzoBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZOBENEFICIARIO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZOBENEFICIARIO$8);
            }
            target.setStringValue(indirizzoBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "indirizzoBeneficiario" element
     */
    public void xsetIndirizzoBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 indirizzoBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(INDIRIZZOBENEFICIARIO$8, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().add_element_user(INDIRIZZOBENEFICIARIO$8);
            }
            target.set(indirizzoBeneficiario);
        }
    }
    
    /**
     * Unsets the "indirizzoBeneficiario" element
     */
    public void unsetIndirizzoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INDIRIZZOBENEFICIARIO$8, 0);
        }
    }
    
    /**
     * Gets the "civicoBeneficiario" element
     */
    public java.lang.String getCivicoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICOBENEFICIARIO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "civicoBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 xgetCivicoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(CIVICOBENEFICIARIO$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "civicoBeneficiario" element
     */
    public boolean isSetCivicoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIVICOBENEFICIARIO$10) != 0;
        }
    }
    
    /**
     * Sets the "civicoBeneficiario" element
     */
    public void setCivicoBeneficiario(java.lang.String civicoBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICOBENEFICIARIO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIVICOBENEFICIARIO$10);
            }
            target.setStringValue(civicoBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "civicoBeneficiario" element
     */
    public void xsetCivicoBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 civicoBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(CIVICOBENEFICIARIO$10, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().add_element_user(CIVICOBENEFICIARIO$10);
            }
            target.set(civicoBeneficiario);
        }
    }
    
    /**
     * Unsets the "civicoBeneficiario" element
     */
    public void unsetCivicoBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIVICOBENEFICIARIO$10, 0);
        }
    }
    
    /**
     * Gets the "capBeneficiario" element
     */
    public java.lang.String getCapBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPBENEFICIARIO$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "capBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 xgetCapBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(CAPBENEFICIARIO$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "capBeneficiario" element
     */
    public boolean isSetCapBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAPBENEFICIARIO$12) != 0;
        }
    }
    
    /**
     * Sets the "capBeneficiario" element
     */
    public void setCapBeneficiario(java.lang.String capBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPBENEFICIARIO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAPBENEFICIARIO$12);
            }
            target.setStringValue(capBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "capBeneficiario" element
     */
    public void xsetCapBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 capBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(CAPBENEFICIARIO$12, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().add_element_user(CAPBENEFICIARIO$12);
            }
            target.set(capBeneficiario);
        }
    }
    
    /**
     * Unsets the "capBeneficiario" element
     */
    public void unsetCapBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAPBENEFICIARIO$12, 0);
        }
    }
    
    /**
     * Gets the "localitaBeneficiario" element
     */
    public java.lang.String getLocalitaBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITABENEFICIARIO$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "localitaBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetLocalitaBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(LOCALITABENEFICIARIO$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "localitaBeneficiario" element
     */
    public boolean isSetLocalitaBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALITABENEFICIARIO$14) != 0;
        }
    }
    
    /**
     * Sets the "localitaBeneficiario" element
     */
    public void setLocalitaBeneficiario(java.lang.String localitaBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITABENEFICIARIO$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALITABENEFICIARIO$14);
            }
            target.setStringValue(localitaBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "localitaBeneficiario" element
     */
    public void xsetLocalitaBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 localitaBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(LOCALITABENEFICIARIO$14, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(LOCALITABENEFICIARIO$14);
            }
            target.set(localitaBeneficiario);
        }
    }
    
    /**
     * Unsets the "localitaBeneficiario" element
     */
    public void unsetLocalitaBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALITABENEFICIARIO$14, 0);
        }
    }
    
    /**
     * Gets the "provinciaBeneficiario" element
     */
    public java.lang.String getProvinciaBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIABENEFICIARIO$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "provinciaBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetProvinciaBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(PROVINCIABENEFICIARIO$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "provinciaBeneficiario" element
     */
    public boolean isSetProvinciaBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROVINCIABENEFICIARIO$16) != 0;
        }
    }
    
    /**
     * Sets the "provinciaBeneficiario" element
     */
    public void setProvinciaBeneficiario(java.lang.String provinciaBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIABENEFICIARIO$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROVINCIABENEFICIARIO$16);
            }
            target.setStringValue(provinciaBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "provinciaBeneficiario" element
     */
    public void xsetProvinciaBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 provinciaBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(PROVINCIABENEFICIARIO$16, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(PROVINCIABENEFICIARIO$16);
            }
            target.set(provinciaBeneficiario);
        }
    }
    
    /**
     * Unsets the "provinciaBeneficiario" element
     */
    public void unsetProvinciaBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROVINCIABENEFICIARIO$16, 0);
        }
    }
    
    /**
     * Gets the "nazioneBeneficiario" element
     */
    public java.lang.String getNazioneBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAZIONEBENEFICIARIO$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "nazioneBeneficiario" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione xgetNazioneBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione)get_store().find_element_user(NAZIONEBENEFICIARIO$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "nazioneBeneficiario" element
     */
    public boolean isSetNazioneBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NAZIONEBENEFICIARIO$18) != 0;
        }
    }
    
    /**
     * Sets the "nazioneBeneficiario" element
     */
    public void setNazioneBeneficiario(java.lang.String nazioneBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAZIONEBENEFICIARIO$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NAZIONEBENEFICIARIO$18);
            }
            target.setStringValue(nazioneBeneficiario);
        }
    }
    
    /**
     * Sets (as xml) the "nazioneBeneficiario" element
     */
    public void xsetNazioneBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione nazioneBeneficiario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione)get_store().find_element_user(NAZIONEBENEFICIARIO$18, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione)get_store().add_element_user(NAZIONEBENEFICIARIO$18);
            }
            target.set(nazioneBeneficiario);
        }
    }
    
    /**
     * Unsets the "nazioneBeneficiario" element
     */
    public void unsetNazioneBeneficiario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NAZIONEBENEFICIARIO$18, 0);
        }
    }
}
