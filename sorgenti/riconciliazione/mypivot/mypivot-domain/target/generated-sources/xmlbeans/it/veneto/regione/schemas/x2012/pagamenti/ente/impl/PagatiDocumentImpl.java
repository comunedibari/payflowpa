/*
 * An XML document type.
 * Localname: Pagati
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.PagatiDocument
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * A document containing one Pagati(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/) element.
 *
 * This is a complex type.
 */
public class PagatiDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.PagatiDocument
{
    
    public PagatiDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PAGATI$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "Pagati");
    
    
    /**
     * Gets the "Pagati" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati getPagati()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati)get_store().find_element_user(PAGATI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Pagati" element
     */
    public void setPagati(it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati pagati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati)get_store().find_element_user(PAGATI$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati)get_store().add_element_user(PAGATI$0);
            }
            target.set(pagati);
        }
    }
    
    /**
     * Appends and returns a new empty "Pagati" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati addNewPagati()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagati)get_store().add_element_user(PAGATI$0);
            return target;
        }
    }
}
