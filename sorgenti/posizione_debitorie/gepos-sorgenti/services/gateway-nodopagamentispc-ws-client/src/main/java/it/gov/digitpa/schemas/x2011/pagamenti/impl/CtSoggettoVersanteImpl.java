/*
 * XML Type:  ctSoggettoVersante
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctSoggettoVersante(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtSoggettoVersanteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante
{
    private static final long serialVersionUID = 1L;
    
    public CtSoggettoVersanteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOVERSANTE$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoUnivocoVersante");
    private static final javax.xml.namespace.QName ANAGRAFICAVERSANTE$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "anagraficaVersante");
    private static final javax.xml.namespace.QName INDIRIZZOVERSANTE$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "indirizzoVersante");
    private static final javax.xml.namespace.QName CIVICOVERSANTE$6 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "civicoVersante");
    private static final javax.xml.namespace.QName CAPVERSANTE$8 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "capVersante");
    private static final javax.xml.namespace.QName LOCALITAVERSANTE$10 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "localitaVersante");
    private static final javax.xml.namespace.QName PROVINCIAVERSANTE$12 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "provinciaVersante");
    private static final javax.xml.namespace.QName NAZIONEVERSANTE$14 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "nazioneVersante");
    private static final javax.xml.namespace.QName EMAILVERSANTE$16 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "e-mailVersante");
    
    
    /**
     * Gets the "identificativoUnivocoVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG getIdentificativoUnivocoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSANTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "identificativoUnivocoVersante" element
     */
    public void setIdentificativoUnivocoVersante(it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG identificativoUnivocoVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSANTE$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSANTE$0);
            }
            target.set(identificativoUnivocoVersante);
        }
    }
    
    /**
     * Appends and returns a new empty "identificativoUnivocoVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG addNewIdentificativoUnivocoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivocoPersonaFG)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSANTE$0);
            return target;
        }
    }
    
    /**
     * Gets the "anagraficaVersante" element
     */
    public java.lang.String getAnagraficaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ANAGRAFICAVERSANTE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "anagraficaVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText70 xgetAnagraficaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(ANAGRAFICAVERSANTE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "anagraficaVersante" element
     */
    public void setAnagraficaVersante(java.lang.String anagraficaVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ANAGRAFICAVERSANTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ANAGRAFICAVERSANTE$2);
            }
            target.setStringValue(anagraficaVersante);
        }
    }
    
    /**
     * Sets (as xml) the "anagraficaVersante" element
     */
    public void xsetAnagraficaVersante(it.gov.digitpa.schemas.x2011.pagamenti.StText70 anagraficaVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(ANAGRAFICAVERSANTE$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().add_element_user(ANAGRAFICAVERSANTE$2);
            }
            target.set(anagraficaVersante);
        }
    }
    
    /**
     * Gets the "indirizzoVersante" element
     */
    public java.lang.String getIndirizzoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZOVERSANTE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "indirizzoVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText70 xgetIndirizzoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(INDIRIZZOVERSANTE$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "indirizzoVersante" element
     */
    public boolean isSetIndirizzoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INDIRIZZOVERSANTE$4) != 0;
        }
    }
    
    /**
     * Sets the "indirizzoVersante" element
     */
    public void setIndirizzoVersante(java.lang.String indirizzoVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZOVERSANTE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZOVERSANTE$4);
            }
            target.setStringValue(indirizzoVersante);
        }
    }
    
    /**
     * Sets (as xml) the "indirizzoVersante" element
     */
    public void xsetIndirizzoVersante(it.gov.digitpa.schemas.x2011.pagamenti.StText70 indirizzoVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(INDIRIZZOVERSANTE$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().add_element_user(INDIRIZZOVERSANTE$4);
            }
            target.set(indirizzoVersante);
        }
    }
    
    /**
     * Unsets the "indirizzoVersante" element
     */
    public void unsetIndirizzoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INDIRIZZOVERSANTE$4, 0);
        }
    }
    
    /**
     * Gets the "civicoVersante" element
     */
    public java.lang.String getCivicoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICOVERSANTE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "civicoVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText16 xgetCivicoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(CIVICOVERSANTE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "civicoVersante" element
     */
    public boolean isSetCivicoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIVICOVERSANTE$6) != 0;
        }
    }
    
    /**
     * Sets the "civicoVersante" element
     */
    public void setCivicoVersante(java.lang.String civicoVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICOVERSANTE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIVICOVERSANTE$6);
            }
            target.setStringValue(civicoVersante);
        }
    }
    
    /**
     * Sets (as xml) the "civicoVersante" element
     */
    public void xsetCivicoVersante(it.gov.digitpa.schemas.x2011.pagamenti.StText16 civicoVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(CIVICOVERSANTE$6, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().add_element_user(CIVICOVERSANTE$6);
            }
            target.set(civicoVersante);
        }
    }
    
    /**
     * Unsets the "civicoVersante" element
     */
    public void unsetCivicoVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIVICOVERSANTE$6, 0);
        }
    }
    
    /**
     * Gets the "capVersante" element
     */
    public java.lang.String getCapVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPVERSANTE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "capVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText16 xgetCapVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(CAPVERSANTE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "capVersante" element
     */
    public boolean isSetCapVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAPVERSANTE$8) != 0;
        }
    }
    
    /**
     * Sets the "capVersante" element
     */
    public void setCapVersante(java.lang.String capVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAPVERSANTE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAPVERSANTE$8);
            }
            target.setStringValue(capVersante);
        }
    }
    
    /**
     * Sets (as xml) the "capVersante" element
     */
    public void xsetCapVersante(it.gov.digitpa.schemas.x2011.pagamenti.StText16 capVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText16 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().find_element_user(CAPVERSANTE$8, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText16)get_store().add_element_user(CAPVERSANTE$8);
            }
            target.set(capVersante);
        }
    }
    
    /**
     * Unsets the "capVersante" element
     */
    public void unsetCapVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAPVERSANTE$8, 0);
        }
    }
    
    /**
     * Gets the "localitaVersante" element
     */
    public java.lang.String getLocalitaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITAVERSANTE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "localitaVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetLocalitaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(LOCALITAVERSANTE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "localitaVersante" element
     */
    public boolean isSetLocalitaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALITAVERSANTE$10) != 0;
        }
    }
    
    /**
     * Sets the "localitaVersante" element
     */
    public void setLocalitaVersante(java.lang.String localitaVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITAVERSANTE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALITAVERSANTE$10);
            }
            target.setStringValue(localitaVersante);
        }
    }
    
    /**
     * Sets (as xml) the "localitaVersante" element
     */
    public void xsetLocalitaVersante(it.gov.digitpa.schemas.x2011.pagamenti.StText35 localitaVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(LOCALITAVERSANTE$10, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(LOCALITAVERSANTE$10);
            }
            target.set(localitaVersante);
        }
    }
    
    /**
     * Unsets the "localitaVersante" element
     */
    public void unsetLocalitaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALITAVERSANTE$10, 0);
        }
    }
    
    /**
     * Gets the "provinciaVersante" element
     */
    public java.lang.String getProvinciaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIAVERSANTE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "provinciaVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetProvinciaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(PROVINCIAVERSANTE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "provinciaVersante" element
     */
    public boolean isSetProvinciaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROVINCIAVERSANTE$12) != 0;
        }
    }
    
    /**
     * Sets the "provinciaVersante" element
     */
    public void setProvinciaVersante(java.lang.String provinciaVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIAVERSANTE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROVINCIAVERSANTE$12);
            }
            target.setStringValue(provinciaVersante);
        }
    }
    
    /**
     * Sets (as xml) the "provinciaVersante" element
     */
    public void xsetProvinciaVersante(it.gov.digitpa.schemas.x2011.pagamenti.StText35 provinciaVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(PROVINCIAVERSANTE$12, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(PROVINCIAVERSANTE$12);
            }
            target.set(provinciaVersante);
        }
    }
    
    /**
     * Unsets the "provinciaVersante" element
     */
    public void unsetProvinciaVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROVINCIAVERSANTE$12, 0);
        }
    }
    
    /**
     * Gets the "nazioneVersante" element
     */
    public java.lang.String getNazioneVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAZIONEVERSANTE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "nazioneVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia xgetNazioneVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().find_element_user(NAZIONEVERSANTE$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "nazioneVersante" element
     */
    public boolean isSetNazioneVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NAZIONEVERSANTE$14) != 0;
        }
    }
    
    /**
     * Sets the "nazioneVersante" element
     */
    public void setNazioneVersante(java.lang.String nazioneVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAZIONEVERSANTE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NAZIONEVERSANTE$14);
            }
            target.setStringValue(nazioneVersante);
        }
    }
    
    /**
     * Sets (as xml) the "nazioneVersante" element
     */
    public void xsetNazioneVersante(it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia nazioneVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().find_element_user(NAZIONEVERSANTE$14, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().add_element_user(NAZIONEVERSANTE$14);
            }
            target.set(nazioneVersante);
        }
    }
    
    /**
     * Unsets the "nazioneVersante" element
     */
    public void unsetNazioneVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NAZIONEVERSANTE$14, 0);
        }
    }
    
    /**
     * Gets the "e-mailVersante" element
     */
    public java.lang.String getEMailVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILVERSANTE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "e-mailVersante" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StEMail xgetEMailVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StEMail target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StEMail)get_store().find_element_user(EMAILVERSANTE$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "e-mailVersante" element
     */
    public boolean isSetEMailVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILVERSANTE$16) != 0;
        }
    }
    
    /**
     * Sets the "e-mailVersante" element
     */
    public void setEMailVersante(java.lang.String eMailVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILVERSANTE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILVERSANTE$16);
            }
            target.setStringValue(eMailVersante);
        }
    }
    
    /**
     * Sets (as xml) the "e-mailVersante" element
     */
    public void xsetEMailVersante(it.gov.digitpa.schemas.x2011.pagamenti.StEMail eMailVersante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StEMail target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StEMail)get_store().find_element_user(EMAILVERSANTE$16, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StEMail)get_store().add_element_user(EMAILVERSANTE$16);
            }
            target.set(eMailVersante);
        }
    }
    
    /**
     * Unsets the "e-mailVersante" element
     */
    public void unsetEMailVersante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILVERSANTE$16, 0);
        }
    }
}
