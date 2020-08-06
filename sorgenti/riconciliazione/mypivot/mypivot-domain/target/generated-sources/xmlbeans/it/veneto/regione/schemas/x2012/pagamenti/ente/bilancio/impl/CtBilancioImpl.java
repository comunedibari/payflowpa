/*
 * XML Type:  ctBilancio
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.impl;
/**
 * An XML ctBilancio(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/).
 *
 * This is a complex type.
 */
public class CtBilancioImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio
{
    
    public CtBilancioImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CAPITOLO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/", "capitolo");
    
    
    /**
     * Gets array of all "capitolo" elements
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo[] getCapitoloArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CAPITOLO$0, targetList);
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo[] result = new it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "capitolo" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo getCapitoloArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo)get_store().find_element_user(CAPITOLO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "capitolo" element
     */
    public int sizeOfCapitoloArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAPITOLO$0);
        }
    }
    
    /**
     * Sets array of all "capitolo" element
     */
    public void setCapitoloArray(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo[] capitoloArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(capitoloArray, CAPITOLO$0);
        }
    }
    
    /**
     * Sets ith "capitolo" element
     */
    public void setCapitoloArray(int i, it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo capitolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo)get_store().find_element_user(CAPITOLO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(capitolo);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "capitolo" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo insertNewCapitolo(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo)get_store().insert_element_user(CAPITOLO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "capitolo" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo addNewCapitolo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo)get_store().add_element_user(CAPITOLO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "capitolo" element
     */
    public void removeCapitolo(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAPITOLO$0, i);
        }
    }
}
