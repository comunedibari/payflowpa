/*
 * XML Type:  ctDatiSingoloPagamentoRT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctDatiSingoloPagamentoRT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtDatiSingoloPagamentoRTImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT
{
    private static final long serialVersionUID = 1L;
    
    public CtDatiSingoloPagamentoRTImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SINGOLOIMPORTOPAGATO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "singoloImportoPagato");
    private static final javax.xml.namespace.QName ESITOSINGOLOPAGAMENTO$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "esitoSingoloPagamento");
    private static final javax.xml.namespace.QName DATAESITOSINGOLOPAGAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "dataEsitoSingoloPagamento");
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCORISCOSSIONE$6 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoUnivocoRiscossione");
    private static final javax.xml.namespace.QName CAUSALEVERSAMENTO$8 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "causaleVersamento");
    private static final javax.xml.namespace.QName DATISPECIFICIRISCOSSIONE$10 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "datiSpecificiRiscossione");
    private static final javax.xml.namespace.QName COMMISSIONIAPPLICATEPSP$12 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "commissioniApplicatePSP");
    private static final javax.xml.namespace.QName ALLEGATORICEVUTA$14 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "allegatoRicevuta");
    
    
    /**
     * Gets the "singoloImportoPagato" element
     */
    public java.math.BigDecimal getSingoloImportoPagato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SINGOLOIMPORTOPAGATO$0, 0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StImporto xgetSingoloImportoPagato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImporto target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().find_element_user(SINGOLOIMPORTOPAGATO$0, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SINGOLOIMPORTOPAGATO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SINGOLOIMPORTOPAGATO$0);
            }
            target.setBigDecimalValue(singoloImportoPagato);
        }
    }
    
    /**
     * Sets (as xml) the "singoloImportoPagato" element
     */
    public void xsetSingoloImportoPagato(it.gov.digitpa.schemas.x2011.pagamenti.StImporto singoloImportoPagato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImporto target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().find_element_user(SINGOLOIMPORTOPAGATO$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().add_element_user(SINGOLOIMPORTOPAGATO$0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ESITOSINGOLOPAGAMENTO$2, 0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetEsitoSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(ESITOSINGOLOPAGAMENTO$2, 0);
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
            return get_store().count_elements(ESITOSINGOLOPAGAMENTO$2) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ESITOSINGOLOPAGAMENTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ESITOSINGOLOPAGAMENTO$2);
            }
            target.setStringValue(esitoSingoloPagamento);
        }
    }
    
    /**
     * Sets (as xml) the "esitoSingoloPagamento" element
     */
    public void xsetEsitoSingoloPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StText35 esitoSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(ESITOSINGOLOPAGAMENTO$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(ESITOSINGOLOPAGAMENTO$2);
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
            get_store().remove_element(ESITOSINGOLOPAGAMENTO$2, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAESITOSINGOLOPAGAMENTO$4, 0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StISODate xgetDataEsitoSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODate target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().find_element_user(DATAESITOSINGOLOPAGAMENTO$4, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAESITOSINGOLOPAGAMENTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAESITOSINGOLOPAGAMENTO$4);
            }
            target.setCalendarValue(dataEsitoSingoloPagamento);
        }
    }
    
    /**
     * Sets (as xml) the "dataEsitoSingoloPagamento" element
     */
    public void xsetDataEsitoSingoloPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StISODate dataEsitoSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODate target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().find_element_user(DATAESITOSINGOLOPAGAMENTO$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().add_element_user(DATAESITOSINGOLOPAGAMENTO$4);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$6, 0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoUnivocoRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$6);
            }
            target.setStringValue(identificativoUnivocoRiscossione);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoUnivocoRiscossione" element
     */
    public void xsetIdentificativoUnivocoRiscossione(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoUnivocoRiscossione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$6, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(IDENTIFICATIVOUNIVOCORISCOSSIONE$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAUSALEVERSAMENTO$8, 0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StText140 xgetCausaleVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText140 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText140)get_store().find_element_user(CAUSALEVERSAMENTO$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAUSALEVERSAMENTO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAUSALEVERSAMENTO$8);
            }
            target.setStringValue(causaleVersamento);
        }
    }
    
    /**
     * Sets (as xml) the "causaleVersamento" element
     */
    public void xsetCausaleVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StText140 causaleVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText140 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText140)get_store().find_element_user(CAUSALEVERSAMENTO$8, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText140)get_store().add_element_user(CAUSALEVERSAMENTO$8);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$10, 0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione xgetDatiSpecificiRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$10, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATISPECIFICIRISCOSSIONE$10);
            }
            target.setStringValue(datiSpecificiRiscossione);
        }
    }
    
    /**
     * Sets (as xml) the "datiSpecificiRiscossione" element
     */
    public void xsetDatiSpecificiRiscossione(it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione datiSpecificiRiscossione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$10, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione)get_store().add_element_user(DATISPECIFICIRISCOSSIONE$10);
            }
            target.set(datiSpecificiRiscossione);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONIAPPLICATEPSP$12, 0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StImporto xgetCommissioniApplicatePSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImporto target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().find_element_user(COMMISSIONIAPPLICATEPSP$12, 0);
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
            return get_store().count_elements(COMMISSIONIAPPLICATEPSP$12) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONIAPPLICATEPSP$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMISSIONIAPPLICATEPSP$12);
            }
            target.setBigDecimalValue(commissioniApplicatePSP);
        }
    }
    
    /**
     * Sets (as xml) the "commissioniApplicatePSP" element
     */
    public void xsetCommissioniApplicatePSP(it.gov.digitpa.schemas.x2011.pagamenti.StImporto commissioniApplicatePSP)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImporto target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().find_element_user(COMMISSIONIAPPLICATEPSP$12, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().add_element_user(COMMISSIONIAPPLICATEPSP$12);
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
            get_store().remove_element(COMMISSIONIAPPLICATEPSP$12, 0);
        }
    }
    
    /**
     * Gets the "allegatoRicevuta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta getAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta)get_store().find_element_user(ALLEGATORICEVUTA$14, 0);
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
            return get_store().count_elements(ALLEGATORICEVUTA$14) != 0;
        }
    }
    
    /**
     * Sets the "allegatoRicevuta" element
     */
    public void setAllegatoRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta allegatoRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta)get_store().find_element_user(ALLEGATORICEVUTA$14, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta)get_store().add_element_user(ALLEGATORICEVUTA$14);
            }
            target.set(allegatoRicevuta);
        }
    }
    
    /**
     * Appends and returns a new empty "allegatoRicevuta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta addNewAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta)get_store().add_element_user(ALLEGATORICEVUTA$14);
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
            get_store().remove_element(ALLEGATORICEVUTA$14, 0);
        }
    }
}
