/*
 * XML Type:  tipoImpronta
 * Namespace: http://www.agenziaentrate.gov.it/2014/MarcaDaBollo
 * Java type: it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta
 *
 * Automatically generated - do not modify.
 */
package it.gov.agenziaentrate.x2014.marcaDaBollo.impl;
/**
 * An XML tipoImpronta(@http://www.agenziaentrate.gov.it/2014/MarcaDaBollo).
 *
 * This is a complex type.
 */
public class TipoImprontaImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta
{
    private static final long serialVersionUID = 1L;
    
    public TipoImprontaImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DIGESTMETHOD$0 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "DigestMethod");
    private static final javax.xml.namespace.QName DIGESTVALUE$2 = 
        new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
    
    
    /**
     * Gets the "DigestMethod" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType getDigestMethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType)get_store().find_element_user(DIGESTMETHOD$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DigestMethod" element
     */
    public void setDigestMethod(it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType digestMethod)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType)get_store().find_element_user(DIGESTMETHOD$0, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType)get_store().add_element_user(DIGESTMETHOD$0);
            }
            target.set(digestMethod);
        }
    }
    
    /**
     * Appends and returns a new empty "DigestMethod" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType addNewDigestMethod()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.DDigestMethodType)get_store().add_element_user(DIGESTMETHOD$0);
            return target;
        }
    }
    
    /**
     * Gets the "DigestValue" element
     */
    public byte[] getDigestValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIGESTVALUE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getByteArrayValue();
        }
    }
    
    /**
     * Gets (as xml) the "DigestValue" element
     */
    public org.w3.x2000.x09.xmldsig.DigestValueType xgetDigestValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.x2000.x09.xmldsig.DigestValueType target = null;
            target = (org.w3.x2000.x09.xmldsig.DigestValueType)get_store().find_element_user(DIGESTVALUE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DigestValue" element
     */
    public void setDigestValue(byte[] digestValue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIGESTVALUE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DIGESTVALUE$2);
            }
            target.setByteArrayValue(digestValue);
        }
    }
    
    /**
     * Sets (as xml) the "DigestValue" element
     */
    public void xsetDigestValue(org.w3.x2000.x09.xmldsig.DigestValueType digestValue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.x2000.x09.xmldsig.DigestValueType target = null;
            target = (org.w3.x2000.x09.xmldsig.DigestValueType)get_store().find_element_user(DIGESTVALUE$2, 0);
            if (target == null)
            {
                target = (org.w3.x2000.x09.xmldsig.DigestValueType)get_store().add_element_user(DIGESTVALUE$2);
            }
            target.set(digestValue);
        }
    }
}
