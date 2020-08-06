/*
 * XML Type:  ctDatiMarcaBolloDigitale
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctDatiMarcaBolloDigitale(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtDatiMarcaBolloDigitaleImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale
{
    private static final long serialVersionUID = 1L;
    
    public CtDatiMarcaBolloDigitaleImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOBOLLO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "tipoBollo");
    private static final javax.xml.namespace.QName HASHDOCUMENTO$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "hashDocumento");
    private static final javax.xml.namespace.QName PROVINCIARESIDENZA$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "provinciaResidenza");
    
    
    /**
     * Gets the "tipoBollo" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo.Enum getTipoBollo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOBOLLO$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "tipoBollo" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo xgetTipoBollo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo)get_store().find_element_user(TIPOBOLLO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tipoBollo" element
     */
    public void setTipoBollo(it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo.Enum tipoBollo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOBOLLO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOBOLLO$0);
            }
            target.setEnumValue(tipoBollo);
        }
    }
    
    /**
     * Sets (as xml) the "tipoBollo" element
     */
    public void xsetTipoBollo(it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo tipoBollo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo)get_store().find_element_user(TIPOBOLLO$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoBollo)get_store().add_element_user(TIPOBOLLO$0);
            }
            target.set(tipoBollo);
        }
    }
    
    /**
     * Gets the "hashDocumento" element
     */
    public java.lang.String getHashDocumento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HASHDOCUMENTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "hashDocumento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText70 xgetHashDocumento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(HASHDOCUMENTO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "hashDocumento" element
     */
    public void setHashDocumento(java.lang.String hashDocumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HASHDOCUMENTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HASHDOCUMENTO$2);
            }
            target.setStringValue(hashDocumento);
        }
    }
    
    /**
     * Sets (as xml) the "hashDocumento" element
     */
    public void xsetHashDocumento(it.gov.digitpa.schemas.x2011.pagamenti.StText70 hashDocumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText70 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().find_element_user(HASHDOCUMENTO$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText70)get_store().add_element_user(HASHDOCUMENTO$2);
            }
            target.set(hashDocumento);
        }
    }
    
    /**
     * Gets the "provinciaResidenza" element
     */
    public java.lang.String getProvinciaResidenza()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIARESIDENZA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "provinciaResidenza" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia xgetProvinciaResidenza()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().find_element_user(PROVINCIARESIDENZA$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "provinciaResidenza" element
     */
    public void setProvinciaResidenza(java.lang.String provinciaResidenza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIARESIDENZA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROVINCIARESIDENZA$4);
            }
            target.setStringValue(provinciaResidenza);
        }
    }
    
    /**
     * Sets (as xml) the "provinciaResidenza" element
     */
    public void xsetProvinciaResidenza(it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia provinciaResidenza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().find_element_user(PROVINCIARESIDENZA$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StNazioneProvincia)get_store().add_element_user(PROVINCIARESIDENZA$4);
            }
            target.set(provinciaResidenza);
        }
    }
}
