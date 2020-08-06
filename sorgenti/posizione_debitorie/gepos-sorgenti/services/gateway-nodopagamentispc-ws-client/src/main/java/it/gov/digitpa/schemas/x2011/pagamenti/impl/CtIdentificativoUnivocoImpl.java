/*
 * XML Type:  ctIdentificativoUnivoco
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivoco
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctIdentificativoUnivoco(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtIdentificativoUnivocoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtIdentificativoUnivoco
{
    private static final long serialVersionUID = 1L;
    
    public CtIdentificativoUnivocoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOIDENTIFICATIVOUNIVOCO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "tipoIdentificativoUnivoco");
    private static final javax.xml.namespace.QName CODICEIDENTIFICATIVOUNIVOCO$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "codiceIdentificativoUnivoco");
    
    
    /**
     * Gets the "tipoIdentificativoUnivoco" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco.Enum getTipoIdentificativoUnivoco()
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
            return (it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "tipoIdentificativoUnivoco" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco xgetTipoIdentificativoUnivoco()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco)get_store().find_element_user(TIPOIDENTIFICATIVOUNIVOCO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tipoIdentificativoUnivoco" element
     */
    public void setTipoIdentificativoUnivoco(it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco.Enum tipoIdentificativoUnivoco)
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
    public void xsetTipoIdentificativoUnivoco(it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco tipoIdentificativoUnivoco)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco)get_store().find_element_user(TIPOIDENTIFICATIVOUNIVOCO$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoIdentificativoUnivoco)get_store().add_element_user(TIPOIDENTIFICATIVOUNIVOCO$0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetCodiceIdentificativoUnivoco()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(CODICEIDENTIFICATIVOUNIVOCO$2, 0);
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
    public void xsetCodiceIdentificativoUnivoco(it.gov.digitpa.schemas.x2011.pagamenti.StText35 codiceIdentificativoUnivoco)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(CODICEIDENTIFICATIVOUNIVOCO$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(CODICEIDENTIFICATIVOUNIVOCO$2);
            }
            target.set(codiceIdentificativoUnivoco);
        }
    }
}
