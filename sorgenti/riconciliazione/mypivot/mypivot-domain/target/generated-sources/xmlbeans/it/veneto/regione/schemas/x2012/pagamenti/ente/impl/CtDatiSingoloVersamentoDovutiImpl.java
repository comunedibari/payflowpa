/*
 * XML Type:  ctDatiSingoloVersamentoDovuti
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDatiSingoloVersamentoDovuti(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDatiSingoloVersamentoDovutiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti
{
    
    public CtDatiSingoloVersamentoDovutiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCODOVUTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoDovuto");
    private static final javax.xml.namespace.QName IMPORTOSINGOLOVERSAMENTO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "importoSingoloVersamento");
    private static final javax.xml.namespace.QName COMMISSIONECARICOPA$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "commissioneCaricoPA");
    private static final javax.xml.namespace.QName IDENTIFICATIVOTIPODOVUTO$6 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoTipoDovuto");
    private static final javax.xml.namespace.QName CAUSALEVERSAMENTO$8 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "causaleVersamento");
    private static final javax.xml.namespace.QName DATISPECIFICIRISCOSSIONE$10 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiSpecificiRiscossione");
    private static final javax.xml.namespace.QName DATIMARCABOLLODIGITALE$12 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiMarcaBolloDigitale");
    
    
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
     * Gets the "importoSingoloVersamento" element
     */
    public java.math.BigDecimal getImportoSingoloVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$2, 0);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetImportoSingoloVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$2, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOSINGOLOVERSAMENTO$2);
            }
            target.setBigDecimalValue(importoSingoloVersamento);
        }
    }
    
    /**
     * Sets (as xml) the "importoSingoloVersamento" element
     */
    public void xsetImportoSingoloVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto importoSingoloVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().add_element_user(IMPORTOSINGOLOVERSAMENTO$2);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONECARICOPA$4, 0);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetCommissioneCaricoPA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(COMMISSIONECARICOPA$4, 0);
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
            return get_store().count_elements(COMMISSIONECARICOPA$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONECARICOPA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMISSIONECARICOPA$4);
            }
            target.setBigDecimalValue(commissioneCaricoPA);
        }
    }
    
    /**
     * Sets (as xml) the "commissioneCaricoPA" element
     */
    public void xsetCommissioneCaricoPA(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto commissioneCaricoPA)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(COMMISSIONECARICOPA$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().add_element_user(COMMISSIONECARICOPA$4);
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
            get_store().remove_element(COMMISSIONECARICOPA$4, 0);
        }
    }
    
    /**
     * Gets the "identificativoTipoDovuto" element
     */
    public java.lang.String getIdentificativoTipoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOTIPODOVUTO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "identificativoTipoDovuto" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoTipoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOTIPODOVUTO$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "identificativoTipoDovuto" element
     */
    public void setIdentificativoTipoDovuto(java.lang.String identificativoTipoDovuto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOTIPODOVUTO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOTIPODOVUTO$6);
            }
            target.setStringValue(identificativoTipoDovuto);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoTipoDovuto" element
     */
    public void xsetIdentificativoTipoDovuto(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoTipoDovuto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOTIPODOVUTO$6, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOTIPODOVUTO$6);
            }
            target.set(identificativoTipoDovuto);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 xgetCausaleVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024)get_store().find_element_user(CAUSALEVERSAMENTO$8, 0);
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
    public void xsetCausaleVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 causaleVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024)get_store().find_element_user(CAUSALEVERSAMENTO$8, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024)get_store().add_element_user(CAUSALEVERSAMENTO$8);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione xgetDatiSpecificiRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$10, 0);
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
    public void xsetDatiSpecificiRiscossione(it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione datiSpecificiRiscossione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$10, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().add_element_user(DATISPECIFICIRISCOSSIONE$10);
            }
            target.set(datiSpecificiRiscossione);
        }
    }
    
    /**
     * Gets the "datiMarcaBolloDigitale" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale getDatiMarcaBolloDigitale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale)get_store().find_element_user(DATIMARCABOLLODIGITALE$12, 0);
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
            return get_store().count_elements(DATIMARCABOLLODIGITALE$12) != 0;
        }
    }
    
    /**
     * Sets the "datiMarcaBolloDigitale" element
     */
    public void setDatiMarcaBolloDigitale(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale datiMarcaBolloDigitale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale)get_store().find_element_user(DATIMARCABOLLODIGITALE$12, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale)get_store().add_element_user(DATIMARCABOLLODIGITALE$12);
            }
            target.set(datiMarcaBolloDigitale);
        }
    }
    
    /**
     * Appends and returns a new empty "datiMarcaBolloDigitale" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale addNewDatiMarcaBolloDigitale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale)get_store().add_element_user(DATIMARCABOLLODIGITALE$12);
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
            get_store().remove_element(DATIMARCABOLLODIGITALE$12, 0);
        }
    }
}
