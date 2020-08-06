/*
 * XML Type:  ctDatiSingoloPagamentoPagati
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDatiSingoloPagamentoPagati(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDatiSingoloPagamentoPagatiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati
{
    
    public CtDatiSingoloPagamentoPagatiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCODOVUTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoDovuto");
    private static final javax.xml.namespace.QName SINGOLOIMPORTOPAGATO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "singoloImportoPagato");
    private static final javax.xml.namespace.QName ESITOSINGOLOPAGAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "esitoSingoloPagamento");
    private static final javax.xml.namespace.QName DATAESITOSINGOLOPAGAMENTO$6 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "dataEsitoSingoloPagamento");
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCORISCOSSIONE$8 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoRiscossione");
    private static final javax.xml.namespace.QName CAUSALEVERSAMENTO$10 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "causaleVersamento");
    private static final javax.xml.namespace.QName DATISPECIFICIRISCOSSIONE$12 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiSpecificiRiscossione");
    
    
    /**
     * Gets the "identificativoUnivocoDovuto" element
     */
    public java.lang.String getIdentificativoUnivocoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCODOVUTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "identificativoUnivocoDovuto" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoUnivocoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCODOVUTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "identificativoUnivocoDovuto" element
     */
    public void setIdentificativoUnivocoDovuto(java.lang.String identificativoUnivocoDovuto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCODOVUTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOUNIVOCODOVUTO$0);
            }
            target.setStringValue(identificativoUnivocoDovuto);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoUnivocoDovuto" element
     */
    public void xsetIdentificativoUnivocoDovuto(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoUnivocoDovuto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCODOVUTO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOUNIVOCODOVUTO$0);
            }
            target.set(identificativoUnivocoDovuto);
        }
    }
    
    /**
     * Gets the "singoloImportoPagato" element
     */
    public java.math.BigDecimal getSingoloImportoPagato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SINGOLOIMPORTOPAGATO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "singoloImportoPagato" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetSingoloImportoPagato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(SINGOLOIMPORTOPAGATO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "singoloImportoPagato" element
     */
    public void setSingoloImportoPagato(java.math.BigDecimal singoloImportoPagato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SINGOLOIMPORTOPAGATO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SINGOLOIMPORTOPAGATO$2);
            }
            target.setBigDecimalValue(singoloImportoPagato);
        }
    }
    
    /**
     * Sets (as xml) the "singoloImportoPagato" element
     */
    public void xsetSingoloImportoPagato(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto singoloImportoPagato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(SINGOLOIMPORTOPAGATO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().add_element_user(SINGOLOIMPORTOPAGATO$2);
            }
            target.set(singoloImportoPagato);
        }
    }
    
    /**
     * Gets the "esitoSingoloPagamento" element
     */
    public java.lang.String getEsitoSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ESITOSINGOLOPAGAMENTO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "esitoSingoloPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetEsitoSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(ESITOSINGOLOPAGAMENTO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "esitoSingoloPagamento" element
     */
    public boolean isSetEsitoSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ESITOSINGOLOPAGAMENTO$4) != 0;
        }
    }
    
    /**
     * Sets the "esitoSingoloPagamento" element
     */
    public void setEsitoSingoloPagamento(java.lang.String esitoSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ESITOSINGOLOPAGAMENTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ESITOSINGOLOPAGAMENTO$4);
            }
            target.setStringValue(esitoSingoloPagamento);
        }
    }
    
    /**
     * Sets (as xml) the "esitoSingoloPagamento" element
     */
    public void xsetEsitoSingoloPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 esitoSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(ESITOSINGOLOPAGAMENTO$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(ESITOSINGOLOPAGAMENTO$4);
            }
            target.set(esitoSingoloPagamento);
        }
    }
    
    /**
     * Unsets the "esitoSingoloPagamento" element
     */
    public void unsetEsitoSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ESITOSINGOLOPAGAMENTO$4, 0);
        }
    }
    
    /**
     * Gets the "dataEsitoSingoloPagamento" element
     */
    public java.util.Calendar getDataEsitoSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAESITOSINGOLOPAGAMENTO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "dataEsitoSingoloPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate xgetDataEsitoSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().find_element_user(DATAESITOSINGOLOPAGAMENTO$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "dataEsitoSingoloPagamento" element
     */
    public void setDataEsitoSingoloPagamento(java.util.Calendar dataEsitoSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAESITOSINGOLOPAGAMENTO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAESITOSINGOLOPAGAMENTO$6);
            }
            target.setCalendarValue(dataEsitoSingoloPagamento);
        }
    }
    
    /**
     * Sets (as xml) the "dataEsitoSingoloPagamento" element
     */
    public void xsetDataEsitoSingoloPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate dataEsitoSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().find_element_user(DATAESITOSINGOLOPAGAMENTO$6, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().add_element_user(DATAESITOSINGOLOPAGAMENTO$6);
            }
            target.set(dataEsitoSingoloPagamento);
        }
    }
    
    /**
     * Gets the "identificativoUnivocoRiscossione" element
     */
    public java.lang.String getIdentificativoUnivocoRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "identificativoUnivocoRiscossione" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoUnivocoRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "identificativoUnivocoRiscossione" element
     */
    public void setIdentificativoUnivocoRiscossione(java.lang.String identificativoUnivocoRiscossione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$8);
            }
            target.setStringValue(identificativoUnivocoRiscossione);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoUnivocoRiscossione" element
     */
    public void xsetIdentificativoUnivocoRiscossione(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoUnivocoRiscossione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$8, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$8);
            }
            target.set(identificativoUnivocoRiscossione);
        }
    }
    
    /**
     * Gets the "causaleVersamento" element
     */
    public java.lang.String getCausaleVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAUSALEVERSAMENTO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "causaleVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText140 xgetCausaleVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText140 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText140)get_store().find_element_user(CAUSALEVERSAMENTO$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "causaleVersamento" element
     */
    public void setCausaleVersamento(java.lang.String causaleVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAUSALEVERSAMENTO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAUSALEVERSAMENTO$10);
            }
            target.setStringValue(causaleVersamento);
        }
    }
    
    /**
     * Sets (as xml) the "causaleVersamento" element
     */
    public void xsetCausaleVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText140 causaleVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText140 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText140)get_store().find_element_user(CAUSALEVERSAMENTO$10, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText140)get_store().add_element_user(CAUSALEVERSAMENTO$10);
            }
            target.set(causaleVersamento);
        }
    }
    
    /**
     * Gets the "datiSpecificiRiscossione" element
     */
    public java.lang.String getDatiSpecificiRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "datiSpecificiRiscossione" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione xgetDatiSpecificiRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "datiSpecificiRiscossione" element
     */
    public void setDatiSpecificiRiscossione(java.lang.String datiSpecificiRiscossione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATISPECIFICIRISCOSSIONE$12);
            }
            target.setStringValue(datiSpecificiRiscossione);
        }
    }
    
    /**
     * Sets (as xml) the "datiSpecificiRiscossione" element
     */
    public void xsetDatiSpecificiRiscossione(it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione datiSpecificiRiscossione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$12, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().add_element_user(DATISPECIFICIRISCOSSIONE$12);
            }
            target.set(datiSpecificiRiscossione);
        }
    }
}
