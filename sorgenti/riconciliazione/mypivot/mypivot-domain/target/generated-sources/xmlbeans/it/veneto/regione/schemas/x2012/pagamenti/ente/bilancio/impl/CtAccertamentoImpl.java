/*
 * XML Type:  ctAccertamento
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.impl;
/**
 * An XML ctAccertamento(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/).
 *
 * This is a complex type.
 */
public class CtAccertamentoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento
{
    
    public CtAccertamentoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODACCERTAMENTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/", "codAccertamento");
    private static final javax.xml.namespace.QName IMPORTO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/", "importo");
    
    
    /**
     * Gets the "codAccertamento" element
     */
    public java.lang.String getCodAccertamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODACCERTAMENTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "codAccertamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText35 xgetCodAccertamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText35)get_store().find_element_user(CODACCERTAMENTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codAccertamento" element
     */
    public void setCodAccertamento(java.lang.String codAccertamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODACCERTAMENTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODACCERTAMENTO$0);
            }
            target.setStringValue(codAccertamento);
        }
    }
    
    /**
     * Sets (as xml) the "codAccertamento" element
     */
    public void xsetCodAccertamento(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText35 codAccertamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText35)get_store().find_element_user(CODACCERTAMENTO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText35)get_store().add_element_user(CODACCERTAMENTO$0);
            }
            target.set(codAccertamento);
        }
    }
    
    /**
     * Gets the "importo" element
     */
    public java.math.BigDecimal getImporto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "importo" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StImporto xgetImporto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StImporto)get_store().find_element_user(IMPORTO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "importo" element
     */
    public void setImporto(java.math.BigDecimal importo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTO$2);
            }
            target.setBigDecimalValue(importo);
        }
    }
    
    /**
     * Sets (as xml) the "importo" element
     */
    public void xsetImporto(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StImporto importo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StImporto)get_store().find_element_user(IMPORTO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StImporto)get_store().add_element_user(IMPORTO$2);
            }
            target.set(importo);
        }
    }
}
