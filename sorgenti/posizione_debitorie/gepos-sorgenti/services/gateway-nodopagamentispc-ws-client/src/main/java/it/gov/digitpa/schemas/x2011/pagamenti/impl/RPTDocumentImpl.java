/*
 * An XML document type.
 * Localname: RPT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.RPTDocument
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * A document containing one RPT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/) element.
 *
 * This is a complex type.
 */
public class RPTDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.RPTDocument
{
    private static final long serialVersionUID = 1L;
    
    public RPTDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RPT$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "RPT");
    
    
    /**
     * Gets the "RPT" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico getRPT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico)get_store().find_element_user(RPT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RPT" element
     */
    public void setRPT(it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico rpt)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico)get_store().find_element_user(RPT$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico)get_store().add_element_user(RPT$0);
            }
            target.set(rpt);
        }
    }
    
    /**
     * Appends and returns a new empty "RPT" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico addNewRPT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico)get_store().add_element_user(RPT$0);
            return target;
        }
    }
}
