/*
 * XML Type:  ctSoggettoPagatore
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctSoggettoPagatore(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtSoggettoPagatoreImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore
{
    private static final long serialVersionUID = 1L;
    
    public CtSoggettoPagatoreImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOPAGATORE$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoUnivocoPagatore");
    private static final javax.xml.namespace.QName ANAGRAFICAPAGATORE$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "anagraficaPagatore");
    private static final javax.xml.namespace.QName INDIRIZZOPAGATORE$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "indirizzoPagatore");
    private static final javax.xml.namespace.QName CIVICOPAGATORE$6 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "civicoPagatore");
    private static final javax.xml.namespace.QName CAPPAGATORE$8 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "capPagatore");
    private static final javax.xml.namespace.QName LOCALITAPAGATORE$10 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "localitaPagatore");
    private static final javax.xml.namespace.QName PROVINCIAPAGATORE$12 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "provinciaPagatore");
    private static final javax.xml.namespace.QName NAZIONEPAGATORE$14 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "nazionePagatore");
    private static final javax.xml.namespace.QName EMAILPAGATORE$16 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "e-mailPagatore");
    
    
    /**
     * Gets the "identificativoUnivocoPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG getIdentificativoUnivocoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG)get_store().find_element_user(IDENTIFICATIVOUNIVOCOPAGATORE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "identificativoUnivocoPagatore" element
     */
    public void setIdentificativoUnivocoPagatore(it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG identificativoUnivocoPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG)get_store().find_element_user(IDENTIFICATIVOUNIVOCOPAGATORE$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG)get_store().add_element_user(IDENTIFICATIVOUNIVOCOPAGATORE$0);
            }
            target.set(identificativoUnivocoPagatore);
        }
    }
    
    /**
     * Appends and returns a new empty "identificativoUnivocoPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG addNewIdentificativoUnivocoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG)get_store().add_element_user(IDENTIFICATIVOUNIVOCOPAGATORE$0);
            return target;
        }
    }
    
    /**
     * Gets the "anagraficaPagatore" element
     */
    public java.lang.String getAnagraficaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ANAGRAFICAPAGATORE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "anagraficaPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText70 xgetAnagraficaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(ANAGRAFICAPAGATORE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "anagraficaPagatore" element
     */
    public void setAnagraficaPagatore(java.lang.String anagraficaPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ANAGRAFICAPAGATORE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ANAGRAFICAPAGATORE$2);
            }
            target.setStringValue(anagraficaPagatore);
        }
    }
    
    /**
     * Sets (as xml) the "anagraficaPagatore" element
     */
    public void xsetAnagraficaPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StText70 anagraficaPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(ANAGRAFICAPAGATORE$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().add_element_user(ANAGRAFICAPAGATORE$2);
            }
            target.set(anagraficaPagatore);
        }
    }
    
    /**
     * Gets the "indirizzoPagatore" element
     */
    public java.lang.String getIndirizzoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZOPAGATORE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "indirizzoPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText70 xgetIndirizzoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(INDIRIZZOPAGATORE$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "indirizzoPagatore" element
     */
    public boolean isSetIndirizzoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INDIRIZZOPAGATORE$4) != 0;
        }
    }
    
    /**
     * Sets the "indirizzoPagatore" element
     */
    public void setIndirizzoPagatore(java.lang.String indirizzoPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZOPAGATORE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZOPAGATORE$4);
            }
            target.setStringValue(indirizzoPagatore);
        }
    }
    
    /**
     * Sets (as xml) the "indirizzoPagatore" element
     */
    public void xsetIndirizzoPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StText70 indirizzoPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(INDIRIZZOPAGATORE$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().add_element_user(INDIRIZZOPAGATORE$4);
            }
            target.set(indirizzoPagatore);
        }
    }
    
    /**
     * Unsets the "indirizzoPagatore" element
     */
    public void unsetIndirizzoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INDIRIZZOPAGATORE$4, 0);
        }
    }
    
    /**
     * Gets the "civicoPagatore" element
     */
    public java.lang.String getCivicoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICOPAGATORE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "civicoPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText16 xgetCivicoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(CIVICOPAGATORE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "civicoPagatore" element
     */
    public boolean isSetCivicoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIVICOPAGATORE$6) != 0;
        }
    }
    
    /**
     * Sets the "civicoPagatore" element
     */
    public void setCivicoPagatore(java.lang.String civicoPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICOPAGATORE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIVICOPAGATORE$6);
            }
            target.setStringValue(civicoPagatore);
        }
    }
    
    /**
     * Sets (as xml) the "civicoPagatore" element
     */
    public void xsetCivicoPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StText16 civicoPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(CIVICOPAGATORE$6, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().add_element_user(CIVICOPAGATORE$6);
            }
            target.set(civicoPagatore);
        }
    }
    
    /**
     * Unsets the "civicoPagatore" element
     */
    public void unsetCivicoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIVICOPAGATORE$6, 0);
        }
    }
    
    /**
     * Gets the "capPagatore" element
     */
    public java.lang.String getCapPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPPAGATORE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "capPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText16 xgetCapPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(CAPPAGATORE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "capPagatore" element
     */
    public boolean isSetCapPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAPPAGATORE$8) != 0;
        }
    }
    
    /**
     * Sets the "capPagatore" element
     */
    public void setCapPagatore(java.lang.String capPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPPAGATORE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAPPAGATORE$8);
            }
            target.setStringValue(capPagatore);
        }
    }
    
    /**
     * Sets (as xml) the "capPagatore" element
     */
    public void xsetCapPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StText16 capPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(CAPPAGATORE$8, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().add_element_user(CAPPAGATORE$8);
            }
            target.set(capPagatore);
        }
    }
    
    /**
     * Unsets the "capPagatore" element
     */
    public void unsetCapPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAPPAGATORE$8, 0);
        }
    }
    
    /**
     * Gets the "localitaPagatore" element
     */
    public java.lang.String getLocalitaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITAPAGATORE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "localitaPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetLocalitaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(LOCALITAPAGATORE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "localitaPagatore" element
     */
    public boolean isSetLocalitaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALITAPAGATORE$10) != 0;
        }
    }
    
    /**
     * Sets the "localitaPagatore" element
     */
    public void setLocalitaPagatore(java.lang.String localitaPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITAPAGATORE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALITAPAGATORE$10);
            }
            target.setStringValue(localitaPagatore);
        }
    }
    
    /**
     * Sets (as xml) the "localitaPagatore" element
     */
    public void xsetLocalitaPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StText35 localitaPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(LOCALITAPAGATORE$10, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(LOCALITAPAGATORE$10);
            }
            target.set(localitaPagatore);
        }
    }
    
    /**
     * Unsets the "localitaPagatore" element
     */
    public void unsetLocalitaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALITAPAGATORE$10, 0);
        }
    }
    
    /**
     * Gets the "provinciaPagatore" element
     */
    public java.lang.String getProvinciaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIAPAGATORE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "provinciaPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetProvinciaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(PROVINCIAPAGATORE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "provinciaPagatore" element
     */
    public boolean isSetProvinciaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROVINCIAPAGATORE$12) != 0;
        }
    }
    
    /**
     * Sets the "provinciaPagatore" element
     */
    public void setProvinciaPagatore(java.lang.String provinciaPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIAPAGATORE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROVINCIAPAGATORE$12);
            }
            target.setStringValue(provinciaPagatore);
        }
    }
    
    /**
     * Sets (as xml) the "provinciaPagatore" element
     */
    public void xsetProvinciaPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StText35 provinciaPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(PROVINCIAPAGATORE$12, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(PROVINCIAPAGATORE$12);
            }
            target.set(provinciaPagatore);
        }
    }
    
    /**
     * Unsets the "provinciaPagatore" element
     */
    public void unsetProvinciaPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROVINCIAPAGATORE$12, 0);
        }
    }
    
    /**
     * Gets the "nazionePagatore" element
     */
    public java.lang.String getNazionePagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAZIONEPAGATORE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "nazionePagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia xgetNazionePagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().find_element_user(NAZIONEPAGATORE$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "nazionePagatore" element
     */
    public boolean isSetNazionePagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NAZIONEPAGATORE$14) != 0;
        }
    }
    
    /**
     * Sets the "nazionePagatore" element
     */
    public void setNazionePagatore(java.lang.String nazionePagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAZIONEPAGATORE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NAZIONEPAGATORE$14);
            }
            target.setStringValue(nazionePagatore);
        }
    }
    
    /**
     * Sets (as xml) the "nazionePagatore" element
     */
    public void xsetNazionePagatore(it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia nazionePagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().find_element_user(NAZIONEPAGATORE$14, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().add_element_user(NAZIONEPAGATORE$14);
            }
            target.set(nazionePagatore);
        }
    }
    
    /**
     * Unsets the "nazionePagatore" element
     */
    public void unsetNazionePagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NAZIONEPAGATORE$14, 0);
        }
    }
    
    /**
     * Gets the "e-mailPagatore" element
     */
    public java.lang.String getEMailPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILPAGATORE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "e-mailPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StEMail xgetEMailPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StEMail target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StEMail)get_store().find_element_user(EMAILPAGATORE$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "e-mailPagatore" element
     */
    public boolean isSetEMailPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILPAGATORE$16) != 0;
        }
    }
    
    /**
     * Sets the "e-mailPagatore" element
     */
    public void setEMailPagatore(java.lang.String eMailPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILPAGATORE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILPAGATORE$16);
            }
            target.setStringValue(eMailPagatore);
        }
    }
    
    /**
     * Sets (as xml) the "e-mailPagatore" element
     */
    public void xsetEMailPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StEMail eMailPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StEMail target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StEMail)get_store().find_element_user(EMAILPAGATORE$16, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StEMail)get_store().add_element_user(EMAILPAGATORE$16);
            }
            target.set(eMailPagatore);
        }
    }
    
    /**
     * Unsets the "e-mailPagatore" element
     */
    public void unsetEMailPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILPAGATORE$16, 0);
        }
    }
}
