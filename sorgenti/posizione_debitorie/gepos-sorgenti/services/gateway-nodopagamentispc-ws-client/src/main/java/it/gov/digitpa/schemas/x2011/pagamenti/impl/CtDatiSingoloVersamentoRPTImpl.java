/*
 * XML Type:  ctDatiSingoloVersamentoRPT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctDatiSingoloVersamentoRPT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtDatiSingoloVersamentoRPTImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT
{
    private static final long serialVersionUID = 1L;
    
    public CtDatiSingoloVersamentoRPTImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IMPORTOSINGOLOVERSAMENTO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "importoSingoloVersamento");
    private static final javax.xml.namespace.QName COMMISSIONECARICOPA$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "commissioneCaricoPA");
    private static final javax.xml.namespace.QName IBANACCREDITO$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "ibanAccredito");
    private static final javax.xml.namespace.QName BICACCREDITO$6 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "bicAccredito");
    private static final javax.xml.namespace.QName IBANAPPOGGIO$8 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "ibanAppoggio");
    private static final javax.xml.namespace.QName BICAPPOGGIO$10 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "bicAppoggio");
    private static final javax.xml.namespace.QName CREDENZIALIPAGATORE$12 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "credenzialiPagatore");
    private static final javax.xml.namespace.QName CAUSALEVERSAMENTO$14 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "causaleVersamento");
    private static final javax.xml.namespace.QName DATISPECIFICIRISCOSSIONE$16 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "datiSpecificiRiscossione");
    private static final javax.xml.namespace.QName DATIMARCABOLLODIGITALE$18 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "datiMarcaBolloDigitale");
    
    
    /**
     * Gets the "importoSingoloVersamento" element
     */
    public java.math.BigDecimal getImportoSingoloVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "importoSingoloVersamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero xgetImportoSingoloVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "importoSingoloVersamento" element
     */
    public void setImportoSingoloVersamento(java.math.BigDecimal importoSingoloVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOSINGOLOVERSAMENTO$0);
            }
            target.setBigDecimalValue(importoSingoloVersamento);
        }
    }
    
    /**
     * Sets (as xml) the "importoSingoloVersamento" element
     */
    public void xsetImportoSingoloVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero importoSingoloVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().add_element_user(IMPORTOSINGOLOVERSAMENTO$0);
            }
            target.set(importoSingoloVersamento);
        }
    }
    
    /**
     * Gets the "commissioneCaricoPA" element
     */
    public java.math.BigDecimal getCommissioneCaricoPA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONECARICOPA$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "commissioneCaricoPA" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero xgetCommissioneCaricoPA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().find_element_user(COMMISSIONECARICOPA$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "commissioneCaricoPA" element
     */
    public boolean isSetCommissioneCaricoPA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMMISSIONECARICOPA$2) != 0;
        }
    }
    
    /**
     * Sets the "commissioneCaricoPA" element
     */
    public void setCommissioneCaricoPA(java.math.BigDecimal commissioneCaricoPA)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONECARICOPA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMISSIONECARICOPA$2);
            }
            target.setBigDecimalValue(commissioneCaricoPA);
        }
    }
    
    /**
     * Sets (as xml) the "commissioneCaricoPA" element
     */
    public void xsetCommissioneCaricoPA(it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero commissioneCaricoPA)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().find_element_user(COMMISSIONECARICOPA$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().add_element_user(COMMISSIONECARICOPA$2);
            }
            target.set(commissioneCaricoPA);
        }
    }
    
    /**
     * Unsets the "commissioneCaricoPA" element
     */
    public void unsetCommissioneCaricoPA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMMISSIONECARICOPA$2, 0);
        }
    }
    
    /**
     * Gets the "ibanAccredito" element
     */
    public java.lang.String getIbanAccredito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IBANACCREDITO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ibanAccredito" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier xgetIbanAccredito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().find_element_user(IBANACCREDITO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "ibanAccredito" element
     */
    public boolean isSetIbanAccredito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IBANACCREDITO$4) != 0;
        }
    }
    
    /**
     * Sets the "ibanAccredito" element
     */
    public void setIbanAccredito(java.lang.String ibanAccredito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IBANACCREDITO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IBANACCREDITO$4);
            }
            target.setStringValue(ibanAccredito);
        }
    }
    
    /**
     * Sets (as xml) the "ibanAccredito" element
     */
    public void xsetIbanAccredito(it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier ibanAccredito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().find_element_user(IBANACCREDITO$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().add_element_user(IBANACCREDITO$4);
            }
            target.set(ibanAccredito);
        }
    }
    
    /**
     * Unsets the "ibanAccredito" element
     */
    public void unsetIbanAccredito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IBANACCREDITO$4, 0);
        }
    }
    
    /**
     * Gets the "bicAccredito" element
     */
    public java.lang.String getBicAccredito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BICACCREDITO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "bicAccredito" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier xgetBicAccredito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().find_element_user(BICACCREDITO$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "bicAccredito" element
     */
    public boolean isSetBicAccredito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BICACCREDITO$6) != 0;
        }
    }
    
    /**
     * Sets the "bicAccredito" element
     */
    public void setBicAccredito(java.lang.String bicAccredito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BICACCREDITO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BICACCREDITO$6);
            }
            target.setStringValue(bicAccredito);
        }
    }
    
    /**
     * Sets (as xml) the "bicAccredito" element
     */
    public void xsetBicAccredito(it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier bicAccredito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().find_element_user(BICACCREDITO$6, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().add_element_user(BICACCREDITO$6);
            }
            target.set(bicAccredito);
        }
    }
    
    /**
     * Unsets the "bicAccredito" element
     */
    public void unsetBicAccredito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BICACCREDITO$6, 0);
        }
    }
    
    /**
     * Gets the "ibanAppoggio" element
     */
    public java.lang.String getIbanAppoggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IBANAPPOGGIO$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ibanAppoggio" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier xgetIbanAppoggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().find_element_user(IBANAPPOGGIO$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "ibanAppoggio" element
     */
    public boolean isSetIbanAppoggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IBANAPPOGGIO$8) != 0;
        }
    }
    
    /**
     * Sets the "ibanAppoggio" element
     */
    public void setIbanAppoggio(java.lang.String ibanAppoggio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IBANAPPOGGIO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IBANAPPOGGIO$8);
            }
            target.setStringValue(ibanAppoggio);
        }
    }
    
    /**
     * Sets (as xml) the "ibanAppoggio" element
     */
    public void xsetIbanAppoggio(it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier ibanAppoggio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().find_element_user(IBANAPPOGGIO$8, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().add_element_user(IBANAPPOGGIO$8);
            }
            target.set(ibanAppoggio);
        }
    }
    
    /**
     * Unsets the "ibanAppoggio" element
     */
    public void unsetIbanAppoggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IBANAPPOGGIO$8, 0);
        }
    }
    
    /**
     * Gets the "bicAppoggio" element
     */
    public java.lang.String getBicAppoggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BICAPPOGGIO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "bicAppoggio" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier xgetBicAppoggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().find_element_user(BICAPPOGGIO$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "bicAppoggio" element
     */
    public boolean isSetBicAppoggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BICAPPOGGIO$10) != 0;
        }
    }
    
    /**
     * Sets the "bicAppoggio" element
     */
    public void setBicAppoggio(java.lang.String bicAppoggio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BICAPPOGGIO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BICAPPOGGIO$10);
            }
            target.setStringValue(bicAppoggio);
        }
    }
    
    /**
     * Sets (as xml) the "bicAppoggio" element
     */
    public void xsetBicAppoggio(it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier bicAppoggio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().find_element_user(BICAPPOGGIO$10, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().add_element_user(BICAPPOGGIO$10);
            }
            target.set(bicAppoggio);
        }
    }
    
    /**
     * Unsets the "bicAppoggio" element
     */
    public void unsetBicAppoggio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BICAPPOGGIO$10, 0);
        }
    }
    
    /**
     * Gets the "credenzialiPagatore" element
     */
    public java.lang.String getCredenzialiPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CREDENZIALIPAGATORE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "credenzialiPagatore" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetCredenzialiPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(CREDENZIALIPAGATORE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "credenzialiPagatore" element
     */
    public boolean isSetCredenzialiPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CREDENZIALIPAGATORE$12) != 0;
        }
    }
    
    /**
     * Sets the "credenzialiPagatore" element
     */
    public void setCredenzialiPagatore(java.lang.String credenzialiPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CREDENZIALIPAGATORE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CREDENZIALIPAGATORE$12);
            }
            target.setStringValue(credenzialiPagatore);
        }
    }
    
    /**
     * Sets (as xml) the "credenzialiPagatore" element
     */
    public void xsetCredenzialiPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StText35 credenzialiPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(CREDENZIALIPAGATORE$12, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(CREDENZIALIPAGATORE$12);
            }
            target.set(credenzialiPagatore);
        }
    }
    
    /**
     * Unsets the "credenzialiPagatore" element
     */
    public void unsetCredenzialiPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CREDENZIALIPAGATORE$12, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAUSALEVERSAMENTO$14, 0);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText140)get_store().find_element_user(CAUSALEVERSAMENTO$14, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAUSALEVERSAMENTO$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAUSALEVERSAMENTO$14);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText140)get_store().find_element_user(CAUSALEVERSAMENTO$14, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText140)get_store().add_element_user(CAUSALEVERSAMENTO$14);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$16, 0);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$16, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATISPECIFICIRISCOSSIONE$16);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$16, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione)get_store().add_element_user(DATISPECIFICIRISCOSSIONE$16);
            }
            target.set(datiSpecificiRiscossione);
        }
    }
    
    /**
     * Gets the "datiMarcaBolloDigitale" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale getDatiMarcaBolloDigitale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale)get_store().find_element_user(DATIMARCABOLLODIGITALE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "datiMarcaBolloDigitale" element
     */
    public boolean isSetDatiMarcaBolloDigitale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIMARCABOLLODIGITALE$18) != 0;
        }
    }
    
    /**
     * Sets the "datiMarcaBolloDigitale" element
     */
    public void setDatiMarcaBolloDigitale(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale datiMarcaBolloDigitale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale)get_store().find_element_user(DATIMARCABOLLODIGITALE$18, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale)get_store().add_element_user(DATIMARCABOLLODIGITALE$18);
            }
            target.set(datiMarcaBolloDigitale);
        }
    }
    
    /**
     * Appends and returns a new empty "datiMarcaBolloDigitale" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale addNewDatiMarcaBolloDigitale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale)get_store().add_element_user(DATIMARCABOLLODIGITALE$18);
            return target;
        }
    }
    
    /**
     * Unsets the "datiMarcaBolloDigitale" element
     */
    public void unsetDatiMarcaBolloDigitale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIMARCABOLLODIGITALE$18, 0);
        }
    }
}
