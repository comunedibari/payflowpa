/*
 * An XML document type.
 * Localname: PagatiConRicevuta
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.PagatiConRicevutaDocument
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * A document containing one PagatiConRicevuta(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/) element.
 *
 * This is a complex type.
 */
public class PagatiConRicevutaDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.PagatiConRicevutaDocument
{
    
    public PagatiConRicevutaDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PAGATICONRICEVUTA$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "PagatiConRicevuta");
    
    
    /**
     * Gets the "PagatiConRicevuta" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta getPagatiConRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta)get_store().find_element_user(PAGATICONRICEVUTA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "PagatiConRicevuta" element
     */
    public void setPagatiConRicevuta(it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta pagatiConRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta)get_store().find_element_user(PAGATICONRICEVUTA$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta)get_store().add_element_user(PAGATICONRICEVUTA$0);
            }
            target.set(pagatiConRicevuta);
        }
    }
    
    /**
     * Appends and returns a new empty "PagatiConRicevuta" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta addNewPagatiConRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta)get_store().add_element_user(PAGATICONRICEVUTA$0);
            return target;
        }
    }
}
