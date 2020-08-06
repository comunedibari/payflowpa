/*
 * XML Type:  tipoPSP
 * Namespace: http://www.agenziaentrate.gov.it/2014/MarcaDaBollo
 * Java type: it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP
 *
 * Automatically generated - do not modify.
 */
package it.gov.agenziaentrate.x2014.marcaDaBollo.impl;
/**
 * An XML tipoPSP(@http://www.agenziaentrate.gov.it/2014/MarcaDaBollo).
 *
 * This is a complex type.
 */
public class TipoPSPImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP
{
    private static final long serialVersionUID = 1L;
    
    public TipoPSPImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICEFISCALE$0 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "CodiceFiscale");
    private static final javax.xml.namespace.QName DENOMINAZIONE$2 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "Denominazione");
    
    
    /**
     * Gets the "CodiceFiscale" element
     */
    public java.lang.String getCodiceFiscale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CodiceFiscale" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT xgetCodiceFiscale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().find_element_user(CODICEFISCALE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CodiceFiscale" element
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEFISCALE$0);
            }
            target.setStringValue(codiceFiscale);
        }
    }
    
    /**
     * Sets (as xml) the "CodiceFiscale" element
     */
    public void xsetCodiceFiscale(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT codiceFiscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().find_element_user(CODICEFISCALE$0, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().add_element_user(CODICEFISCALE$0);
            }
            target.set(codiceFiscale);
        }
    }
    
    /**
     * Gets the "Denominazione" element
     */
    public java.lang.String getDenominazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Denominazione" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT xgetDenominazione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().find_element_user(DENOMINAZIONE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Denominazione" element
     */
    public void setDenominazione(java.lang.String denominazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMINAZIONE$2);
            }
            target.setStringValue(denominazione);
        }
    }
    
    /**
     * Sets (as xml) the "Denominazione" element
     */
    public void xsetDenominazione(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT denominazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().find_element_user(DENOMINAZIONE$2, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT)get_store().add_element_user(DENOMINAZIONE$2);
            }
            target.set(denominazione);
        }
    }
}
