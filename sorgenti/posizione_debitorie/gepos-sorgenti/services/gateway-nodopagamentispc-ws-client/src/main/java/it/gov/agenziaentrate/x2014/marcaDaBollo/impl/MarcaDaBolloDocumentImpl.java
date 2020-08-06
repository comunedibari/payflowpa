/*
 * An XML document type.
 * Localname: marcaDaBollo
 * Namespace: http://www.agenziaentrate.gov.it/2014/MarcaDaBollo
 * Java type: it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument
 *
 * Automatically generated - do not modify.
 */
package it.gov.agenziaentrate.x2014.marcaDaBollo.impl;
/**
 * A document containing one marcaDaBollo(@http://www.agenziaentrate.gov.it/2014/MarcaDaBollo) element.
 *
 * This is a complex type.
 */
public class MarcaDaBolloDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument
{
    private static final long serialVersionUID = 1L;
    
    public MarcaDaBolloDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MARCADABOLLO$0 = 
        new javax.xml.namespace.QName("http://www.agenziaentrate.gov.it/2014/MarcaDaBollo", "marcaDaBollo");
    
    
    /**
     * Gets the "marcaDaBollo" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo getMarcaDaBollo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo)get_store().find_element_user(MARCADABOLLO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "marcaDaBollo" element
     */
    public void setMarcaDaBollo(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo marcaDaBollo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo)get_store().find_element_user(MARCADABOLLO$0, 0);
            if (target == null)
            {
                target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo)get_store().add_element_user(MARCADABOLLO$0);
            }
            target.set(marcaDaBollo);
        }
    }
    
    /**
     * Appends and returns a new empty "marcaDaBollo" element
     */
    public it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo addNewMarcaDaBollo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo target = null;
            target = (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo)get_store().add_element_user(MARCADABOLLO$0);
            return target;
        }
    }
}
