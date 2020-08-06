/*
 * An XML document type.
 * Localname: Versamento
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.VersamentoDocument
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * A document containing one Versamento(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/) element.
 *
 * This is a complex type.
 */
public class VersamentoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.VersamentoDocument
{
    
    public VersamentoDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VERSAMENTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "Versamento");
    
    
    /**
     * Gets the "Versamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento getVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento)get_store().find_element_user(VERSAMENTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Versamento" element
     */
    public void setVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento versamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento)get_store().find_element_user(VERSAMENTO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento)get_store().add_element_user(VERSAMENTO$0);
            }
            target.set(versamento);
        }
    }
    
    /**
     * Appends and returns a new empty "Versamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento addNewVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento)get_store().add_element_user(VERSAMENTO$0);
            return target;
        }
    }
}
