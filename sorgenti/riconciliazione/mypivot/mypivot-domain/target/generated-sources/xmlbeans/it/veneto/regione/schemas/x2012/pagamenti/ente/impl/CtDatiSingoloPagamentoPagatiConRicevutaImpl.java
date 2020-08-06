/*
 * XML Type:  ctDatiSingoloPagamentoPagatiConRicevuta
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagatiConRicevuta
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDatiSingoloPagamentoPagatiConRicevuta(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDatiSingoloPagamentoPagatiConRicevutaImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagatiConRicevuta
{
    
    public CtDatiSingoloPagamentoPagatiConRicevutaImpl(org.apache.xmlbeans.SchemaType sType)
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
    private static final javax.xml.namespace.QName INDICEDATISINGOLOPAGAMENTO$14 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "indiceDatiSingoloPagamento");
    private static final javax.xml.namespace.QName COMMISSIONIAPPLICATEPSP$16 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "commissioniApplicatePSP");
    private static final javax.xml.namespace.QName ALLEGATORICEVUTA$18 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "allegatoRicevuta");
    
    
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
    
    /**
     * Gets the "indiceDatiSingoloPagamento" element
     */
    public int getIndiceDatiSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDICEDATISINGOLOPAGAMENTO$14, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "indiceDatiSingoloPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StIndice xgetIndiceDatiSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StIndice target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StIndice)get_store().find_element_user(INDICEDATISINGOLOPAGAMENTO$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "indiceDatiSingoloPagamento" element
     */
    public boolean isSetIndiceDatiSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INDICEDATISINGOLOPAGAMENTO$14) != 0;
        }
    }
    
    /**
     * Sets the "indiceDatiSingoloPagamento" element
     */
    public void setIndiceDatiSingoloPagamento(int indiceDatiSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDICEDATISINGOLOPAGAMENTO$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDICEDATISINGOLOPAGAMENTO$14);
            }
            target.setIntValue(indiceDatiSingoloPagamento);
        }
    }
    
    /**
     * Sets (as xml) the "indiceDatiSingoloPagamento" element
     */
    public void xsetIndiceDatiSingoloPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StIndice indiceDatiSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StIndice target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StIndice)get_store().find_element_user(INDICEDATISINGOLOPAGAMENTO$14, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StIndice)get_store().add_element_user(INDICEDATISINGOLOPAGAMENTO$14);
            }
            target.set(indiceDatiSingoloPagamento);
        }
    }
    
    /**
     * Unsets the "indiceDatiSingoloPagamento" element
     */
    public void unsetIndiceDatiSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INDICEDATISINGOLOPAGAMENTO$14, 0);
        }
    }
    
    /**
     * Gets the "commissioniApplicatePSP" element
     */
    public java.math.BigDecimal getCommissioniApplicatePSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONIAPPLICATEPSP$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "commissioniApplicatePSP" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetCommissioniApplicatePSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(COMMISSIONIAPPLICATEPSP$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "commissioniApplicatePSP" element
     */
    public boolean isSetCommissioniApplicatePSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMMISSIONIAPPLICATEPSP$16) != 0;
        }
    }
    
    /**
     * Sets the "commissioniApplicatePSP" element
     */
    public void setCommissioniApplicatePSP(java.math.BigDecimal commissioniApplicatePSP)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONIAPPLICATEPSP$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMISSIONIAPPLICATEPSP$16);
            }
            target.setBigDecimalValue(commissioniApplicatePSP);
        }
    }
    
    /**
     * Sets (as xml) the "commissioniApplicatePSP" element
     */
    public void xsetCommissioniApplicatePSP(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto commissioniApplicatePSP)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(COMMISSIONIAPPLICATEPSP$16, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().add_element_user(COMMISSIONIAPPLICATEPSP$16);
            }
            target.set(commissioniApplicatePSP);
        }
    }
    
    /**
     * Unsets the "commissioniApplicatePSP" element
     */
    public void unsetCommissioniApplicatePSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMMISSIONIAPPLICATEPSP$16, 0);
        }
    }
    
    /**
     * Gets the "allegatoRicevuta" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta getAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta)get_store().find_element_user(ALLEGATORICEVUTA$18, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "allegatoRicevuta" element
     */
    public boolean isSetAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ALLEGATORICEVUTA$18) != 0;
        }
    }
    
    /**
     * Sets the "allegatoRicevuta" element
     */
    public void setAllegatoRicevuta(it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta allegatoRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta)get_store().find_element_user(ALLEGATORICEVUTA$18, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta)get_store().add_element_user(ALLEGATORICEVUTA$18);
            }
            target.set(allegatoRicevuta);
        }
    }
    
    /**
     * Appends and returns a new empty "allegatoRicevuta" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta addNewAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta)get_store().add_element_user(ALLEGATORICEVUTA$18);
            return target;
        }
    }
    
    /**
     * Unsets the "allegatoRicevuta" element
     */
    public void unsetAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ALLEGATORICEVUTA$18, 0);
        }
    }
}
