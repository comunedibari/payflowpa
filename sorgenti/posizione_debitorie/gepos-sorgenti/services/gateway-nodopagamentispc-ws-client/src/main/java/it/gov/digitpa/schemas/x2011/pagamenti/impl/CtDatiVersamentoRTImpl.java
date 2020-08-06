/*
 * XML Type:  ctDatiVersamentoRT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctDatiVersamentoRT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtDatiVersamentoRTImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT
{
    private static final long serialVersionUID = 1L;
    
    public CtDatiVersamentoRTImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICEESITOPAGAMENTO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "codiceEsitoPagamento");
    private static final javax.xml.namespace.QName IMPORTOTOTALEPAGATO$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "importoTotalePagato");
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOVERSAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoUnivocoVersamento");
    private static final javax.xml.namespace.QName CODICECONTESTOPAGAMENTO$6 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "CodiceContestoPagamento");
    private static final javax.xml.namespace.QName DATISINGOLOPAGAMENTO$8 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "datiSingoloPagamento");
    
    
    /**
     * Gets the "codiceEsitoPagamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento.Enum getCodiceEsitoPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEESITOPAGAMENTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "codiceEsitoPagamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento xgetCodiceEsitoPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento)get_store().find_element_user(CODICEESITOPAGAMENTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codiceEsitoPagamento" element
     */
    public void setCodiceEsitoPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento.Enum codiceEsitoPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEESITOPAGAMENTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEESITOPAGAMENTO$0);
            }
            target.setEnumValue(codiceEsitoPagamento);
        }
    }
    
    /**
     * Sets (as xml) the "codiceEsitoPagamento" element
     */
    public void xsetCodiceEsitoPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento codiceEsitoPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento)get_store().find_element_user(CODICEESITOPAGAMENTO$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento)get_store().add_element_user(CODICEESITOPAGAMENTO$0);
            }
            target.set(codiceEsitoPagamento);
        }
    }
    
    /**
     * Gets the "importoTotalePagato" element
     */
    public java.math.BigDecimal getImportoTotalePagato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOTOTALEPAGATO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "importoTotalePagato" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StImporto xgetImportoTotalePagato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImporto target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().find_element_user(IMPORTOTOTALEPAGATO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "importoTotalePagato" element
     */
    public void setImportoTotalePagato(java.math.BigDecimal importoTotalePagato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOTOTALEPAGATO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOTOTALEPAGATO$2);
            }
            target.setBigDecimalValue(importoTotalePagato);
        }
    }
    
    /**
     * Sets (as xml) the "importoTotalePagato" element
     */
    public void xsetImportoTotalePagato(it.gov.digitpa.schemas.x2011.pagamenti.StImporto importoTotalePagato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImporto target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().find_element_user(IMPORTOTOTALEPAGATO$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StImporto)get_store().add_element_user(IMPORTOTOTALEPAGATO$2);
            }
            target.set(importoTotalePagato);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoUnivocoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4, 0);
            return target;
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
    public void xsetIdentificativoUnivocoVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoUnivocoVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$4);
            }
            target.set(identificativoUnivocoVersamento);
        }
    }
    
    /**
     * Gets the "CodiceContestoPagamento" element
     */
    public java.lang.String getCodiceContestoPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICECONTESTOPAGAMENTO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CodiceContestoPagamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetCodiceContestoPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(CODICECONTESTOPAGAMENTO$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CodiceContestoPagamento" element
     */
    public void setCodiceContestoPagamento(java.lang.String codiceContestoPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICECONTESTOPAGAMENTO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICECONTESTOPAGAMENTO$6);
            }
            target.setStringValue(codiceContestoPagamento);
        }
    }
    
    /**
     * Sets (as xml) the "CodiceContestoPagamento" element
     */
    public void xsetCodiceContestoPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StText35 codiceContestoPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(CODICECONTESTOPAGAMENTO$6, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(CODICECONTESTOPAGAMENTO$6);
            }
            target.set(codiceContestoPagamento);
        }
    }
    
    /**
     * Gets array of all "datiSingoloPagamento" elements
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT[] getDatiSingoloPagamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DATISINGOLOPAGAMENTO$8, targetList);
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT[] result = new it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "datiSingoloPagamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT getDatiSingoloPagamentoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT)get_store().find_element_user(DATISINGOLOPAGAMENTO$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "datiSingoloPagamento" element
     */
    public int sizeOfDatiSingoloPagamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISINGOLOPAGAMENTO$8);
        }
    }
    
    /**
     * Sets array of all "datiSingoloPagamento" element
     */
    public void setDatiSingoloPagamentoArray(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT[] datiSingoloPagamentoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(datiSingoloPagamentoArray, DATISINGOLOPAGAMENTO$8);
        }
    }
    
    /**
     * Sets ith "datiSingoloPagamento" element
     */
    public void setDatiSingoloPagamentoArray(int i, it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT datiSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT)get_store().find_element_user(DATISINGOLOPAGAMENTO$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(datiSingoloPagamento);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "datiSingoloPagamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT insertNewDatiSingoloPagamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT)get_store().insert_element_user(DATISINGOLOPAGAMENTO$8, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "datiSingoloPagamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT addNewDatiSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT)get_store().add_element_user(DATISINGOLOPAGAMENTO$8);
            return target;
        }
    }
    
    /**
     * Removes the ith "datiSingoloPagamento" element
     */
    public void removeDatiSingoloPagamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISINGOLOPAGAMENTO$8, i);
        }
    }
}
