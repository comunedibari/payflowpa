/*
 * XML Type:  ctDatiVersamentoPagati
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDatiVersamentoPagati(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDatiVersamentoPagatiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati
{
    
    public CtDatiVersamentoPagatiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICEESITOPAGAMENTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "codiceEsitoPagamento");
    private static final javax.xml.namespace.QName IMPORTOTOTALEPAGATO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "importoTotalePagato");
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOVERSAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoVersamento");
    private static final javax.xml.namespace.QName CODICECONTESTOPAGAMENTO$6 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "codiceContestoPagamento");
    private static final javax.xml.namespace.QName DATISINGOLOPAGAMENTO$8 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiSingoloPagamento");
    
    
    /**
     * Gets the "codiceEsitoPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento.Enum getCodiceEsitoPagamento()
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
            return (it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "codiceEsitoPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento xgetCodiceEsitoPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento)get_store().find_element_user(CODICEESITOPAGAMENTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codiceEsitoPagamento" element
     */
    public void setCodiceEsitoPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento.Enum codiceEsitoPagamento)
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
    public void xsetCodiceEsitoPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento codiceEsitoPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento)get_store().find_element_user(CODICEESITOPAGAMENTO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento)get_store().add_element_user(CODICEESITOPAGAMENTO$0);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetImportoTotalePagato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(IMPORTOTOTALEPAGATO$2, 0);
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
    public void xsetImportoTotalePagato(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto importoTotalePagato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().find_element_user(IMPORTOTOTALEPAGATO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto)get_store().add_element_user(IMPORTOTOTALEPAGATO$2);
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
     * Gets the "codiceContestoPagamento" element
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
     * Gets (as xml) the "codiceContestoPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetCodiceContestoPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(CODICECONTESTOPAGAMENTO$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "codiceContestoPagamento" element
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
     * Sets (as xml) the "codiceContestoPagamento" element
     */
    public void xsetCodiceContestoPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 codiceContestoPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(CODICECONTESTOPAGAMENTO$6, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(CODICECONTESTOPAGAMENTO$6);
            }
            target.set(codiceContestoPagamento);
        }
    }
    
    /**
     * Gets array of all "datiSingoloPagamento" elements
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati[] getDatiSingoloPagamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DATISINGOLOPAGAMENTO$8, targetList);
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati[] result = new it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "datiSingoloPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati getDatiSingoloPagamentoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati)get_store().find_element_user(DATISINGOLOPAGAMENTO$8, i);
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
    public void setDatiSingoloPagamentoArray(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati[] datiSingoloPagamentoArray)
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
    public void setDatiSingoloPagamentoArray(int i, it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati datiSingoloPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati)get_store().find_element_user(DATISINGOLOPAGAMENTO$8, i);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati insertNewDatiSingoloPagamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati)get_store().insert_element_user(DATISINGOLOPAGAMENTO$8, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "datiSingoloPagamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati addNewDatiSingoloPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati)get_store().add_element_user(DATISINGOLOPAGAMENTO$8);
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
