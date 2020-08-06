/*
 * XML Type:  ctDatiVersamento
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDatiVersamento(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDatiVersamentoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento
{
    
    public CtDatiVersamentoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAESECUZIONEPAGAMENTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "dataEsecuzionePagamento");
    private static final javax.xml.namespace.QName TIPOVERSAMENTO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "tipoVersamento");
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOVERSAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoVersamento");
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCODOVUTO$6 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoDovuto");
    private static final javax.xml.namespace.QName IMPORTOSINGOLOVERSAMENTO$8 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "importoSingoloVersamento");
    private static final javax.xml.namespace.QName COMMISSIONECARICOPA$10 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "commissioneCaricoPA");
    private static final javax.xml.namespace.QName IDENTIFICATIVOTIPODOVUTO$12 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoTipoDovuto");
    private static final javax.xml.namespace.QName CAUSALEVERSAMENTO$14 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "causaleVersamento");
    private static final javax.xml.namespace.QName DATISPECIFICIRISCOSSIONE$16 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiSpecificiRiscossione");
    
    
    /**
     * Gets the "dataEsecuzionePagamento" element
     */
    public java.util.Calendar getDataEsecuzionePagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAESECUZIONEPAGAMENTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "dataEsecuzionePagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate xgetDataEsecuzionePagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().find_element_user(DATAESECUZIONEPAGAMENTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "dataEsecuzionePagamento" element
     */
    public void setDataEsecuzionePagamento(java.util.Calendar dataEsecuzionePagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAESECUZIONEPAGAMENTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAESECUZIONEPAGAMENTO$0);
            }
            target.setCalendarValue(dataEsecuzionePagamento);
        }
    }
    
    /**
     * Sets (as xml) the "dataEsecuzionePagamento" element
     */
    public void xsetDataEsecuzionePagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate dataEsecuzionePagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().find_element_user(DATAESECUZIONEPAGAMENTO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate)get_store().add_element_user(DATAESECUZIONEPAGAMENTO$0);
            }
            target.set(dataEsecuzionePagamento);
        }
    }
    
    /**
     * Gets the "tipoVersamento" element
     */
    public java.lang.String getTipoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOVERSAMENTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "tipoVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText32 xgetTipoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText32 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText32)get_store().find_element_user(TIPOVERSAMENTO$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "tipoVersamento" element
     */
    public boolean isSetTipoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIPOVERSAMENTO$2) != 0;
        }
    }
    
    /**
     * Sets the "tipoVersamento" element
     */
    public void setTipoVersamento(java.lang.String tipoVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOVERSAMENTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOVERSAMENTO$2);
            }
            target.setStringValue(tipoVersamento);
        }
    }
    
    /**
     * Sets (as xml) the "tipoVersamento" element
     */
    public void xsetTipoVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText32 tipoVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText32 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText32)get_store().find_element_user(TIPOVERSAMENTO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText32)get_store().add_element_user(TIPOVERSAMENTO$2);
            }
            target.set(tipoVersamento);
        }
    }
    
    /**
     * Unsets the "tipoVersamento" element
     */
    public void unsetTipoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIPOVERSAMENTO$2, 0);
        }
    }
    
    /**
     * Gets the "identificativoUnivocoVersamento" element
     */
    public java.lang.String getIdentificativoUnivocoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "identificativoUnivocoVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoUnivocoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "identificativoUnivocoVersamento" element
     */
    public boolean isSetIdentificativoUnivocoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDENTIFICATIVOUNIVOCOVERSAMENTO$4) != 0;
        }
    }
    
    /**
     * Sets the "identificativoUnivocoVersamento" element
     */
    public void setIdentificativoUnivocoVersamento(java.lang.String identificativoUnivocoVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4);
            }
            target.setStringValue(identificativoUnivocoVersamento);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoUnivocoVersamento" element
     */
    public void xsetIdentificativoUnivocoVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoUnivocoVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4);
            }
            target.set(identificativoUnivocoVersamento);
        }
    }
    
    /**
     * Unsets the "identificativoUnivocoVersamento" element
     */
    public void unsetIdentificativoUnivocoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDENTIFICATIVOUNIVOCOVERSAMENTO$4, 0);
        }
    }
    
    /**
     * Gets the "identificativoUnivocoDovuto" element
     */
    public java.lang.String getIdentificativoUnivocoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCODOVUTO$6, 0);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCODOVUTO$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "identificativoUnivocoDovuto" element
     */
    public boolean isSetIdentificativoUnivocoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDENTIFICATIVOUNIVOCODOVUTO$6) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCODOVUTO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOUNIVOCODOVUTO$6);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCODOVUTO$6, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOUNIVOCODOVUTO$6);
            }
            target.set(identificativoUnivocoDovuto);
        }
    }
    
    /**
     * Unsets the "identificativoUnivocoDovuto" element
     */
    public void unsetIdentificativoUnivocoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDENTIFICATIVOUNIVOCODOVUTO$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$8, 0);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOSINGOLOVERSAMENTO$8);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(IMPORTOSINGOLOVERSAMENTO$8, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().add_element_user(IMPORTOSINGOLOVERSAMENTO$8);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONECARICOPA$10, 0);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(COMMISSIONECARICOPA$10, 0);
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
            return get_store().count_elements(COMMISSIONECARICOPA$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMISSIONECARICOPA$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMISSIONECARICOPA$10);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(COMMISSIONECARICOPA$10, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().add_element_user(COMMISSIONECARICOPA$10);
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
            get_store().remove_element(COMMISSIONECARICOPA$10, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOTIPODOVUTO$12, 0);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOTIPODOVUTO$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "identificativoTipoDovuto" element
     */
    public boolean isSetIdentificativoTipoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDENTIFICATIVOTIPODOVUTO$12) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOTIPODOVUTO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOTIPODOVUTO$12);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOTIPODOVUTO$12, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOTIPODOVUTO$12);
            }
            target.set(identificativoTipoDovuto);
        }
    }
    
    /**
     * Unsets the "identificativoTipoDovuto" element
     */
    public void unsetIdentificativoTipoDovuto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDENTIFICATIVOTIPODOVUTO$12, 0);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 xgetCausaleVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024)get_store().find_element_user(CAUSALEVERSAMENTO$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "causaleVersamento" element
     */
    public boolean isSetCausaleVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAUSALEVERSAMENTO$14) != 0;
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
    public void xsetCausaleVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 causaleVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024)get_store().find_element_user(CAUSALEVERSAMENTO$14, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024)get_store().add_element_user(CAUSALEVERSAMENTO$14);
            }
            target.set(causaleVersamento);
        }
    }
    
    /**
     * Unsets the "causaleVersamento" element
     */
    public void unsetCausaleVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAUSALEVERSAMENTO$14, 0);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione xgetDatiSpecificiRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "datiSpecificiRiscossione" element
     */
    public boolean isSetDatiSpecificiRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISPECIFICIRISCOSSIONE$16) != 0;
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
    public void xsetDatiSpecificiRiscossione(it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione datiSpecificiRiscossione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().find_element_user(DATISPECIFICIRISCOSSIONE$16, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione)get_store().add_element_user(DATISPECIFICIRISCOSSIONE$16);
            }
            target.set(datiSpecificiRiscossione);
        }
    }
    
    /**
     * Unsets the "datiSpecificiRiscossione" element
     */
    public void unsetDatiSpecificiRiscossione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISPECIFICIRISCOSSIONE$16, 0);
        }
    }
}
