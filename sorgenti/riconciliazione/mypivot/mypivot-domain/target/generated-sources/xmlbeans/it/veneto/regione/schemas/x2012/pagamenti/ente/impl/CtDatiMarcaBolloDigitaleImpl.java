/*
 * XML Type:  ctDatiMarcaBolloDigitale
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDatiMarcaBolloDigitale(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDatiMarcaBolloDigitaleImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale
{
    
    public CtDatiMarcaBolloDigitaleImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOBOLLO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "tipoBollo");
    private static final javax.xml.namespace.QName HASHDOCUMENTO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "hashDocumento");
    private static final javax.xml.namespace.QName PROVINCIARESIDENZA$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "provinciaResidenza");
    
    
    /**
     * Gets the "tipoBollo" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo.Enum getTipoBollo()
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
            return (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "tipoBollo" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo xgetTipoBollo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo)get_store().find_element_user(TIPOBOLLO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tipoBollo" element
     */
    public void setTipoBollo(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo.Enum tipoBollo)
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
    public void xsetTipoBollo(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo tipoBollo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo)get_store().find_element_user(TIPOBOLLO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo)get_store().add_element_user(TIPOBOLLO$0);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 xgetHashDocumento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(HASHDOCUMENTO$2, 0);
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
    public void xsetHashDocumento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 hashDocumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().find_element_user(HASHDOCUMENTO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText70)get_store().add_element_user(HASHDOCUMENTO$2);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia xgetProvinciaResidenza()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia)get_store().find_element_user(PROVINCIARESIDENZA$4, 0);
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
    public void xsetProvinciaResidenza(it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia provinciaResidenza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia)get_store().find_element_user(PROVINCIARESIDENZA$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia)get_store().add_element_user(PROVINCIARESIDENZA$4);
            }
            target.set(provinciaResidenza);
        }
    }
}
