/*
 * An XML document type.
 * Localname: Dovuti
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.DovutiDocument
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * A document containing one Dovuti(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/) element.
 *
 * This is a complex type.
 */
public class DovutiDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.DovutiDocument
{
    
    public DovutiDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DOVUTI$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "Dovuti");
    
    
    /**
     * Gets the "Dovuti" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti getDovuti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti)get_store().find_element_user(DOVUTI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Dovuti" element
     */
    public void setDovuti(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti dovuti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti)get_store().find_element_user(DOVUTI$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti)get_store().add_element_user(DOVUTI$0);
            }
            target.set(dovuti);
        }
    }
    
    /**
     * Appends and returns a new empty "Dovuti" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti addNewDovuti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti)get_store().add_element_user(DOVUTI$0);
            return target;
        }
    }
}
