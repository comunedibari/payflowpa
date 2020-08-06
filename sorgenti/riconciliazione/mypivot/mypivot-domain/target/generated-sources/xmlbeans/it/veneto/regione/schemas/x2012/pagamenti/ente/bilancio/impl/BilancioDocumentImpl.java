/*
 * An XML document type.
 * Localname: bilancio
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.BilancioDocument
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.impl;
/**
 * A document containing one bilancio(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/) element.
 *
 * This is a complex type.
 */
public class BilancioDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.BilancioDocument
{
    
    public BilancioDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BILANCIO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/", "bilancio");
    
    
    /**
     * Gets the "bilancio" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio getBilancio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio)get_store().find_element_user(BILANCIO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "bilancio" element
     */
    public void setBilancio(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio bilancio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio)get_store().find_element_user(BILANCIO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio)get_store().add_element_user(BILANCIO$0);
            }
            target.set(bilancio);
        }
    }
    
    /**
     * Appends and returns a new empty "bilancio" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio addNewBilancio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio)get_store().add_element_user(BILANCIO$0);
            return target;
        }
    }
}
