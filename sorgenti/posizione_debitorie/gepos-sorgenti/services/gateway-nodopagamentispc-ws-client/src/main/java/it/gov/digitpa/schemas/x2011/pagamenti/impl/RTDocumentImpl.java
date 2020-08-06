/*
 * An XML document type.
 * Localname: RT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.RTDocument
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * A document containing one RT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/) element.
 *
 * This is a complex type.
 */
public class RTDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.RTDocument
{
    private static final long serialVersionUID = 1L;
    
    public RTDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RT$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "RT");
    
    
    /**
     * Gets the "RT" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica getRT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica)get_store().find_element_user(RT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RT" element
     */
    public void setRT(it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica rt)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica)get_store().find_element_user(RT$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica)get_store().add_element_user(RT$0);
            }
            target.set(rt);
        }
    }
    
    /**
     * Appends and returns a new empty "RT" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica addNewRT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica)get_store().add_element_user(RT$0);
            return target;
        }
    }
}
