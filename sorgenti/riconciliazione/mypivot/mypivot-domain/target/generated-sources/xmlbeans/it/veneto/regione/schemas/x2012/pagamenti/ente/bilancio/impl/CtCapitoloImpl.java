/*
 * XML Type:  ctCapitolo
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.impl;
/**
 * An XML ctCapitolo(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/).
 *
 * This is a complex type.
 */
public class CtCapitoloImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo
{
    
    public CtCapitoloImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODCAPITOLO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/", "codCapitolo");
    private static final javax.xml.namespace.QName CODUFFICIO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/", "codUfficio");
    private static final javax.xml.namespace.QName ACCERTAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/", "accertamento");
    
    
    /**
     * Gets the "codCapitolo" element
     */
    public java.lang.String getCodCapitolo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODCAPITOLO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codCapitolo" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 xgetCodCapitolo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12)get_store().find_element_user(CODCAPITOLO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codCapitolo" element
     */
    public void setCodCapitolo(java.lang.String codCapitolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODCAPITOLO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODCAPITOLO$0);
            }
            target.setStringValue(codCapitolo);
        }
    }
    
    /**
     * Sets (as xml) the "codCapitolo" element
     */
    public void xsetCodCapitolo(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 codCapitolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12)get_store().find_element_user(CODCAPITOLO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12)get_store().add_element_user(CODCAPITOLO$0);
            }
            target.set(codCapitolo);
        }
    }
    
    /**
     * Gets the "codUfficio" element
     */
    public java.lang.String getCodUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODUFFICIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codUfficio" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 xgetCodUfficio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12)get_store().find_element_user(CODUFFICIO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codUfficio" element
     */
    public void setCodUfficio(java.lang.String codUfficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODUFFICIO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODUFFICIO$2);
            }
            target.setStringValue(codUfficio);
        }
    }
    
    /**
     * Sets (as xml) the "codUfficio" element
     */
    public void xsetCodUfficio(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 codUfficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12)get_store().find_element_user(CODUFFICIO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12)get_store().add_element_user(CODUFFICIO$2);
            }
            target.set(codUfficio);
        }
    }
    
    /**
     * Gets array of all "accertamento" elements
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento[] getAccertamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ACCERTAMENTO$4, targetList);
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento[] result = new it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "accertamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento getAccertamentoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento)get_store().find_element_user(ACCERTAMENTO$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "accertamento" element
     */
    public int sizeOfAccertamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACCERTAMENTO$4);
        }
    }
    
    /**
     * Sets array of all "accertamento" element
     */
    public void setAccertamentoArray(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento[] accertamentoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(accertamentoArray, ACCERTAMENTO$4);
        }
    }
    
    /**
     * Sets ith "accertamento" element
     */
    public void setAccertamentoArray(int i, it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento accertamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento)get_store().find_element_user(ACCERTAMENTO$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(accertamento);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "accertamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento insertNewAccertamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento)get_store().insert_element_user(ACCERTAMENTO$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "accertamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento addNewAccertamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento)get_store().add_element_user(ACCERTAMENTO$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "accertamento" element
     */
    public void removeAccertamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACCERTAMENTO$4, i);
        }
    }
}
