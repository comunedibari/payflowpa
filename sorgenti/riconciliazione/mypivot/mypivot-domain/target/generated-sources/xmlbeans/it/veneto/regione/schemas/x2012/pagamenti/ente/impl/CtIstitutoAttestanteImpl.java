/*
 * XML Type:  ctIstitutoAttestante
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctIstitutoAttestante(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtIstitutoAttestanteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante
{
    
    public CtIstitutoAttestanteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOATTESTANTE$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoAttestante");
    private static final javax.xml.namespace.QName DENOMINAZIONEATTESTANTE$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "denominazioneAttestante");
    private static final javax.xml.namespace.QName CODICEUNITOPERATTESTANTE$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "codiceUnitOperAttestante");
    private static final javax.xml.namespace.QName DENOMUNITOPERATTESTANTE$6 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "denomUnitOperAttestante");
    private static final javax.xml.namespace.QName INDIRIZZOATTESTANTE$8 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "indirizzoAttestante");
    private static final javax.xml.namespace.QName CIVICOATTESTANTE$10 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "civicoAttestante");
    private static final javax.xml.namespace.QName CAPATTESTANTE$12 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "capAttestante");
    private static final javax.xml.namespace.QName LOCALITAATTESTANTE$14 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "localitaAttestante");
    private static final javax.xml.namespace.QName PROVINCIAATTESTANTE$16 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "provinciaAttestante");
    private static final javax.xml.namespace.QName NAZIONEATTESTANTE$18 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "nazioneAttestante");
    
    
    /**
     * Gets the "identificativoUnivocoAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco getIdentificativoUnivocoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco)get_store().find_element_user(IDENTIFICATIVOUNIVOCOATTESTANTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "identificativoUnivocoAttestante" element
     */
    public void setIdentificativoUnivocoAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco identificativoUnivocoAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco)get_store().find_element_user(IDENTIFICATIVOUNIVOCOATTESTANTE$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco)get_store().add_element_user(IDENTIFICATIVOUNIVOCOATTESTANTE$0);
            }
            target.set(identificativoUnivocoAttestante);
        }
    }
    
    /**
     * Appends and returns a new empty "identificativoUnivocoAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco addNewIdentificativoUnivocoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco)get_store().add_element_user(IDENTIFICATIVOUNIVOCOATTESTANTE$0);
            return target;
        }
    }
    
    /**
     * Gets the "denominazioneAttestante" element
     */
    public java.lang.String getDenominazioneAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONEATTESTANTE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "denominazioneAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 xgetDenominazioneAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(DENOMINAZIONEATTESTANTE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "denominazioneAttestante" element
     */
    public void setDenominazioneAttestante(java.lang.String denominazioneAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONEATTESTANTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMINAZIONEATTESTANTE$2);
            }
            target.setStringValue(denominazioneAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "denominazioneAttestante" element
     */
    public void xsetDenominazioneAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 denominazioneAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(DENOMINAZIONEATTESTANTE$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().add_element_user(DENOMINAZIONEATTESTANTE$2);
            }
            target.set(denominazioneAttestante);
        }
    }
    
    /**
     * Gets the "codiceUnitOperAttestante" element
     */
    public java.lang.String getCodiceUnitOperAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEUNITOPERATTESTANTE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codiceUnitOperAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetCodiceUnitOperAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(CODICEUNITOPERATTESTANTE$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "codiceUnitOperAttestante" element
     */
    public boolean isSetCodiceUnitOperAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CODICEUNITOPERATTESTANTE$4) != 0;
        }
    }
    
    /**
     * Sets the "codiceUnitOperAttestante" element
     */
    public void setCodiceUnitOperAttestante(java.lang.String codiceUnitOperAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEUNITOPERATTESTANTE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEUNITOPERATTESTANTE$4);
            }
            target.setStringValue(codiceUnitOperAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "codiceUnitOperAttestante" element
     */
    public void xsetCodiceUnitOperAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 codiceUnitOperAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(CODICEUNITOPERATTESTANTE$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(CODICEUNITOPERATTESTANTE$4);
            }
            target.set(codiceUnitOperAttestante);
        }
    }
    
    /**
     * Unsets the "codiceUnitOperAttestante" element
     */
    public void unsetCodiceUnitOperAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CODICEUNITOPERATTESTANTE$4, 0);
        }
    }
    
    /**
     * Gets the "denomUnitOperAttestante" element
     */
    public java.lang.String getDenomUnitOperAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMUNITOPERATTESTANTE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "denomUnitOperAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 xgetDenomUnitOperAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(DENOMUNITOPERATTESTANTE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "denomUnitOperAttestante" element
     */
    public boolean isSetDenomUnitOperAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DENOMUNITOPERATTESTANTE$6) != 0;
        }
    }
    
    /**
     * Sets the "denomUnitOperAttestante" element
     */
    public void setDenomUnitOperAttestante(java.lang.String denomUnitOperAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMUNITOPERATTESTANTE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMUNITOPERATTESTANTE$6);
            }
            target.setStringValue(denomUnitOperAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "denomUnitOperAttestante" element
     */
    public void xsetDenomUnitOperAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 denomUnitOperAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(DENOMUNITOPERATTESTANTE$6, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().add_element_user(DENOMUNITOPERATTESTANTE$6);
            }
            target.set(denomUnitOperAttestante);
        }
    }
    
    /**
     * Unsets the "denomUnitOperAttestante" element
     */
    public void unsetDenomUnitOperAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DENOMUNITOPERATTESTANTE$6, 0);
        }
    }
    
    /**
     * Gets the "indirizzoAttestante" element
     */
    public java.lang.String getIndirizzoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZOATTESTANTE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "indirizzoAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 xgetIndirizzoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(INDIRIZZOATTESTANTE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "indirizzoAttestante" element
     */
    public boolean isSetIndirizzoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INDIRIZZOATTESTANTE$8) != 0;
        }
    }
    
    /**
     * Sets the "indirizzoAttestante" element
     */
    public void setIndirizzoAttestante(java.lang.String indirizzoAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZOATTESTANTE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZOATTESTANTE$8);
            }
            target.setStringValue(indirizzoAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "indirizzoAttestante" element
     */
    public void xsetIndirizzoAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 indirizzoAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(INDIRIZZOATTESTANTE$8, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().add_element_user(INDIRIZZOATTESTANTE$8);
            }
            target.set(indirizzoAttestante);
        }
    }
    
    /**
     * Unsets the "indirizzoAttestante" element
     */
    public void unsetIndirizzoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INDIRIZZOATTESTANTE$8, 0);
        }
    }
    
    /**
     * Gets the "civicoAttestante" element
     */
    public java.lang.String getCivicoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICOATTESTANTE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "civicoAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 xgetCivicoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(CIVICOATTESTANTE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "civicoAttestante" element
     */
    public boolean isSetCivicoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIVICOATTESTANTE$10) != 0;
        }
    }
    
    /**
     * Sets the "civicoAttestante" element
     */
    public void setCivicoAttestante(java.lang.String civicoAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICOATTESTANTE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIVICOATTESTANTE$10);
            }
            target.setStringValue(civicoAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "civicoAttestante" element
     */
    public void xsetCivicoAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 civicoAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(CIVICOATTESTANTE$10, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().add_element_user(CIVICOATTESTANTE$10);
            }
            target.set(civicoAttestante);
        }
    }
    
    /**
     * Unsets the "civicoAttestante" element
     */
    public void unsetCivicoAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIVICOATTESTANTE$10, 0);
        }
    }
    
    /**
     * Gets the "capAttestante" element
     */
    public java.lang.String getCapAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPATTESTANTE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "capAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 xgetCapAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(CAPATTESTANTE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "capAttestante" element
     */
    public boolean isSetCapAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAPATTESTANTE$12) != 0;
        }
    }
    
    /**
     * Sets the "capAttestante" element
     */
    public void setCapAttestante(java.lang.String capAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPATTESTANTE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAPATTESTANTE$12);
            }
            target.setStringValue(capAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "capAttestante" element
     */
    public void xsetCapAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 capAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(CAPATTESTANTE$12, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().add_element_user(CAPATTESTANTE$12);
            }
            target.set(capAttestante);
        }
    }
    
    /**
     * Unsets the "capAttestante" element
     */
    public void unsetCapAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAPATTESTANTE$12, 0);
        }
    }
    
    /**
     * Gets the "localitaAttestante" element
     */
    public java.lang.String getLocalitaAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITAATTESTANTE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "localitaAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetLocalitaAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(LOCALITAATTESTANTE$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "localitaAttestante" element
     */
    public boolean isSetLocalitaAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALITAATTESTANTE$14) != 0;
        }
    }
    
    /**
     * Sets the "localitaAttestante" element
     */
    public void setLocalitaAttestante(java.lang.String localitaAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITAATTESTANTE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALITAATTESTANTE$14);
            }
            target.setStringValue(localitaAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "localitaAttestante" element
     */
    public void xsetLocalitaAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 localitaAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(LOCALITAATTESTANTE$14, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(LOCALITAATTESTANTE$14);
            }
            target.set(localitaAttestante);
        }
    }
    
    /**
     * Unsets the "localitaAttestante" element
     */
    public void unsetLocalitaAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALITAATTESTANTE$14, 0);
        }
    }
    
    /**
     * Gets the "provinciaAttestante" element
     */
    public java.lang.String getProvinciaAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIAATTESTANTE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "provinciaAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetProvinciaAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(PROVINCIAATTESTANTE$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "provinciaAttestante" element
     */
    public boolean isSetProvinciaAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROVINCIAATTESTANTE$16) != 0;
        }
    }
    
    /**
     * Sets the "provinciaAttestante" element
     */
    public void setProvinciaAttestante(java.lang.String provinciaAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIAATTESTANTE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROVINCIAATTESTANTE$16);
            }
            target.setStringValue(provinciaAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "provinciaAttestante" element
     */
    public void xsetProvinciaAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 provinciaAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(PROVINCIAATTESTANTE$16, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(PROVINCIAATTESTANTE$16);
            }
            target.set(provinciaAttestante);
        }
    }
    
    /**
     * Unsets the "provinciaAttestante" element
     */
    public void unsetProvinciaAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROVINCIAATTESTANTE$16, 0);
        }
    }
    
    /**
     * Gets the "nazioneAttestante" element
     */
    public java.lang.String getNazioneAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAZIONEATTESTANTE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "nazioneAttestante" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione xgetNazioneAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione)get_store().find_element_user(NAZIONEATTESTANTE$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "nazioneAttestante" element
     */
    public boolean isSetNazioneAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NAZIONEATTESTANTE$18) != 0;
        }
    }
    
    /**
     * Sets the "nazioneAttestante" element
     */
    public void setNazioneAttestante(java.lang.String nazioneAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAZIONEATTESTANTE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NAZIONEATTESTANTE$18);
            }
            target.setStringValue(nazioneAttestante);
        }
    }
    
    /**
     * Sets (as xml) the "nazioneAttestante" element
     */
    public void xsetNazioneAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione nazioneAttestante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione)get_store().find_element_user(NAZIONEATTESTANTE$18, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazione)get_store().add_element_user(NAZIONEATTESTANTE$18);
            }
            target.set(nazioneAttestante);
        }
    }
    
    /**
     * Unsets the "nazioneAttestante" element
     */
    public void unsetNazioneAttestante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NAZIONEATTESTANTE$18, 0);
        }
    }
}
