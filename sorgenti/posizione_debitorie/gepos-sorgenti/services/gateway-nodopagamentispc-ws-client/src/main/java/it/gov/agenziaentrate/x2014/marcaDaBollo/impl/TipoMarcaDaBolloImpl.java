/*
 * XML Type:  tipoMarcaDaBollo
 * Namespace: http://www.agenziaentrate.gov.it/2014/MarcaDaBollo
 * Java type: it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo
 *
 * Automatically generated - do not modify.
 */
package it.gov.agenziaentrate.x2014.marcaDaBollo.impl;
/**
 * An XML tipoMarcaDaBollo(@http://www.agenziaentrate.gov.it/2014/MarcaDaBollo).
 *
 * This is a complex type.
 */
public class TipoMarcaDaBolloImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo
{
    private static final long serialVersionUID = 1L;
    
    public TipoMarcaDaBolloImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PSP$0 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "PSP");
    private static final javax.xml.namespace.QName IUBD$2 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "IUBD");
    private static final javax.xml.namespace.QName ORAACQUISTO$4 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "OraAcquisto");
    private static final javax.xml.namespace.QName IMPORTO$6 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "Importo");
    private static final javax.xml.namespace.QName TIPOBOLLO$8 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "TipoBollo");
    private static final javax.xml.namespace.QName IMPRONTADOCUMENTO$10 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "ImprontaDocumento");
    private static final javax.xml.namespace.QName SIGNATURE$12 = 
        new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "Signature");
    
    
    /**
     * Gets the "PSP" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP getPSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP)get_store().find_element_user(PSP$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "PSP" element
     */
    public void setPSP(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP psp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP)get_store().find_element_user(PSP$0, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP)get_store().add_element_user(PSP$0);
            }
            target.set(psp);
        }
    }
    
    /**
     * Appends and returns a new empty "PSP" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP addNewPSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP)get_store().add_element_user(PSP$0);
            return target;
        }
    }
    
    /**
     * Gets the "IUBD" element
     */
    public java.lang.String getIUBD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IUBD$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "IUBD" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT xgetIUBD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().find_element_user(IUBD$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IUBD" element
     */
    public void setIUBD(java.lang.String iubd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IUBD$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IUBD$2);
            }
            target.setStringValue(iubd);
        }
    }
    
    /**
     * Sets (as xml) the "IUBD" element
     */
    public void xsetIUBD(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT iubd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().find_element_user(IUBD$2, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().add_element_user(IUBD$2);
            }
            target.set(iubd);
        }
    }
    
    /**
     * Gets the "OraAcquisto" element
     */
    public java.util.Calendar getOraAcquisto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORAACQUISTO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "OraAcquisto" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetOraAcquisto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(ORAACQUISTO$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "OraAcquisto" element
     */
    public void setOraAcquisto(java.util.Calendar oraAcquisto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORAACQUISTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORAACQUISTO$4);
            }
            target.setCalendarValue(oraAcquisto);
        }
    }
    
    /**
     * Sets (as xml) the "OraAcquisto" element
     */
    public void xsetOraAcquisto(org.apache.xmlbeans.XmlDateTime oraAcquisto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(ORAACQUISTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(ORAACQUISTO$4);
            }
            target.set(oraAcquisto);
        }
    }
    
    /**
     * Gets the "Importo" element
     */
    public java.math.BigDecimal getImporto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "Importo" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto xgetImporto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto)get_store().find_element_user(IMPORTO$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Importo" element
     */
    public void setImporto(java.math.BigDecimal importo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTO$6);
            }
            target.setBigDecimalValue(importo);
        }
    }
    
    /**
     * Sets (as xml) the "Importo" element
     */
    public void xsetImporto(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto importo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto)get_store().find_element_user(IMPORTO$6, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto)get_store().add_element_user(IMPORTO$6);
            }
            target.set(importo);
        }
    }
    
    /**
     * Gets the "TipoBollo" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo.Enum getTipoBollo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOBOLLO$8, 0);
            if (target == null)
            {
                return null;
            }
            return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TipoBollo" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo xgetTipoBollo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo)get_store().find_element_user(TIPOBOLLO$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "TipoBollo" element
     */
    public void setTipoBollo(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo.Enum tipoBollo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOBOLLO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOBOLLO$8);
            }
            target.setEnumValue(tipoBollo);
        }
    }
    
    /**
     * Sets (as xml) the "TipoBollo" element
     */
    public void xsetTipoBollo(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo tipoBollo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo)get_store().find_element_user(TIPOBOLLO$8, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo)get_store().add_element_user(TIPOBOLLO$8);
            }
            target.set(tipoBollo);
        }
    }
    
    /**
     * Gets the "ImprontaDocumento" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta getImprontaDocumento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta)get_store().find_element_user(IMPRONTADOCUMENTO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ImprontaDocumento" element
     */
    public void setImprontaDocumento(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta improntaDocumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta)get_store().find_element_user(IMPRONTADOCUMENTO$10, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta)get_store().add_element_user(IMPRONTADOCUMENTO$10);
            }
            target.set(improntaDocumento);
        }
    }
    
    /**
     * Appends and returns a new empty "ImprontaDocumento" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta addNewImprontaDocumento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta)get_store().add_element_user(IMPRONTADOCUMENTO$10);
            return target;
        }
    }
    
    /**
     * Gets the "Signature" element
     */
    public org.w3.x2000.x09.xmldsig.SignatureType getSignature()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.x2000.x09.xmldsig.SignatureType target = null;
            target = (org.w3.x2000.x09.xmldsig.SignatureType)get_store().find_element_user(SIGNATURE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Signature" element
     */
    public void setSignature(org.w3.x2000.x09.xmldsig.SignatureType signature)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.x2000.x09.xmldsig.SignatureType target = null;
            target = (org.w3.x2000.x09.xmldsig.SignatureType)get_store().find_element_user(SIGNATURE$12, 0);
            if (target == null)
            {
                target = (org.w3.x2000.x09.xmldsig.SignatureType)get_store().add_element_user(SIGNATURE$12);
            }
            target.set(signature);
        }
    }
    
    /**
     * Appends and returns a new empty "Signature" element
     */
    public org.w3.x2000.x09.xmldsig.SignatureType addNewSignature()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.x2000.x09.xmldsig.SignatureType target = null;
            target = (org.w3.x2000.x09.xmldsig.SignatureType)get_store().add_element_user(SIGNATURE$12);
            return target;
        }
    }
}
