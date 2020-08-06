/*
 * XML Type:  ctIdentificativoUnivoco
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctIdentificativoUnivoco(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtIdentificativoUnivocoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco
{
    
    public CtIdentificativoUnivocoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOIDENTIFICATIVOUNIVOCO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "tipoIdentificativoUnivoco");
    private static final javax.xml.namespace.QName CODICEIDENTIFICATIVOUNIVOCO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "codiceIdentificativoUnivoco");
    
    
    /**
     * Gets the "tipoIdentificativoUnivoco" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco.Enum getTipoIdentificativoUnivoco()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOIDENTIFICATIVOUNIVOCO$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "tipoIdentificativoUnivoco" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco xgetTipoIdentificativoUnivoco()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco)get_store().find_element_user(TIPOIDENTIFICATIVOUNIVOCO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tipoIdentificativoUnivoco" element
     */
    public void setTipoIdentificativoUnivoco(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco.Enum tipoIdentificativoUnivoco)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOIDENTIFICATIVOUNIVOCO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOIDENTIFICATIVOUNIVOCO$0);
            }
            target.setEnumValue(tipoIdentificativoUnivoco);
        }
    }
    
    /**
     * Sets (as xml) the "tipoIdentificativoUnivoco" element
     */
    public void xsetTipoIdentificativoUnivoco(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco tipoIdentificativoUnivoco)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco)get_store().find_element_user(TIPOIDENTIFICATIVOUNIVOCO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco)get_store().add_element_user(TIPOIDENTIFICATIVOUNIVOCO$0);
            }
            target.set(tipoIdentificativoUnivoco);
        }
    }
    
    /**
     * Gets the "codiceIdentificativoUnivoco" element
     */
    public java.lang.String getCodiceIdentificativoUnivoco()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEIDENTIFICATIVOUNIVOCO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codiceIdentificativoUnivoco" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetCodiceIdentificativoUnivoco()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(CODICEIDENTIFICATIVOUNIVOCO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codiceIdentificativoUnivoco" element
     */
    public void setCodiceIdentificativoUnivoco(java.lang.String codiceIdentificativoUnivoco)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEIDENTIFICATIVOUNIVOCO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEIDENTIFICATIVOUNIVOCO$2);
            }
            target.setStringValue(codiceIdentificativoUnivoco);
        }
    }
    
    /**
     * Sets (as xml) the "codiceIdentificativoUnivoco" element
     */
    public void xsetCodiceIdentificativoUnivoco(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 codiceIdentificativoUnivoco)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(CODICEIDENTIFICATIVOUNIVOCO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(CODICEIDENTIFICATIVOUNIVOCO$2);
            }
            target.set(codiceIdentificativoUnivoco);
        }
    }
}
