/*
 * XML Type:  ctDatiVersamentoRPT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctDatiVersamentoRPT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtDatiVersamentoRPTImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT
{
    private static final long serialVersionUID = 1L;
    
    public CtDatiVersamentoRPTImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAESECUZIONEPAGAMENTO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "dataEsecuzionePagamento");
    private static final javax.xml.namespace.QName IMPORTOTOTALEDAVERSARE$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "importoTotaleDaVersare");
    private static final javax.xml.namespace.QName TIPOVERSAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "tipoVersamento");
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOVERSAMENTO$6 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoUnivocoVersamento");
    private static final javax.xml.namespace.QName CODICECONTESTOPAGAMENTO$8 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "codiceContestoPagamento");
    private static final javax.xml.namespace.QName IBANADDEBITO$10 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "ibanAddebito");
    private static final javax.xml.namespace.QName BICADDEBITO$12 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "bicAddebito");
    private static final javax.xml.namespace.QName FIRMARICEVUTA$14 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "firmaRicevuta");
    private static final javax.xml.namespace.QName DATISINGOLOVERSAMENTO$16 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "datiSingoloVersamento");
    
    
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StISODate xgetDataEsecuzionePagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODate target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().find_element_user(DATAESECUZIONEPAGAMENTO$0, 0);
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
    public void xsetDataEsecuzionePagamento(it.gov.digitpa.schemas.x2011.pagamenti.StISODate dataEsecuzionePagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StISODate target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().find_element_user(DATAESECUZIONEPAGAMENTO$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StISODate)get_store().add_element_user(DATAESECUZIONEPAGAMENTO$0);
            }
            target.set(dataEsecuzionePagamento);
        }
    }
    
    /**
     * Gets the "importoTotaleDaVersare" element
     */
    public java.math.BigDecimal getImportoTotaleDaVersare()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOTOTALEDAVERSARE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "importoTotaleDaVersare" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero xgetImportoTotaleDaVersare()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().find_element_user(IMPORTOTOTALEDAVERSARE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "importoTotaleDaVersare" element
     */
    public void setImportoTotaleDaVersare(java.math.BigDecimal importoTotaleDaVersare)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOTOTALEDAVERSARE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOTOTALEDAVERSARE$2);
            }
            target.setBigDecimalValue(importoTotaleDaVersare);
        }
    }
    
    /**
     * Sets (as xml) the "importoTotaleDaVersare" element
     */
    public void xsetImportoTotaleDaVersare(it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero importoTotaleDaVersare)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().find_element_user(IMPORTOTOTALEDAVERSARE$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero)get_store().add_element_user(IMPORTOTOTALEDAVERSARE$2);
            }
            target.set(importoTotaleDaVersare);
        }
    }
    
    /**
     * Gets the "tipoVersamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento.Enum getTipoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOVERSAMENTO$4, 0);
            if (target == null)
            {
                return null;
            }
            return (it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "tipoVersamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento xgetTipoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento)get_store().find_element_user(TIPOVERSAMENTO$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tipoVersamento" element
     */
    public void setTipoVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento.Enum tipoVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOVERSAMENTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOVERSAMENTO$4);
            }
            target.setEnumValue(tipoVersamento);
        }
    }
    
    /**
     * Sets (as xml) the "tipoVersamento" element
     */
    public void xsetTipoVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento tipoVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento)get_store().find_element_user(TIPOVERSAMENTO$4, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento)get_store().add_element_user(TIPOVERSAMENTO$4);
            }
            target.set(tipoVersamento);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$6, 0);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$6);
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
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$6, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICECONTESTOPAGAMENTO$8, 0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetCodiceContestoPagamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(CODICECONTESTOPAGAMENTO$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICECONTESTOPAGAMENTO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICECONTESTOPAGAMENTO$8);
            }
            target.setStringValue(codiceContestoPagamento);
        }
    }
    
    /**
     * Sets (as xml) the "codiceContestoPagamento" element
     */
    public void xsetCodiceContestoPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StText35 codiceContestoPagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(CODICECONTESTOPAGAMENTO$8, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(CODICECONTESTOPAGAMENTO$8);
            }
            target.set(codiceContestoPagamento);
        }
    }
    
    /**
     * Gets the "ibanAddebito" element
     */
    public java.lang.String getIbanAddebito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IBANADDEBITO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ibanAddebito" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier xgetIbanAddebito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().find_element_user(IBANADDEBITO$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "ibanAddebito" element
     */
    public boolean isSetIbanAddebito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IBANADDEBITO$10) != 0;
        }
    }
    
    /**
     * Sets the "ibanAddebito" element
     */
    public void setIbanAddebito(java.lang.String ibanAddebito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IBANADDEBITO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IBANADDEBITO$10);
            }
            target.setStringValue(ibanAddebito);
        }
    }
    
    /**
     * Sets (as xml) the "ibanAddebito" element
     */
    public void xsetIbanAddebito(it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier ibanAddebito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().find_element_user(IBANADDEBITO$10, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier)get_store().add_element_user(IBANADDEBITO$10);
            }
            target.set(ibanAddebito);
        }
    }
    
    /**
     * Unsets the "ibanAddebito" element
     */
    public void unsetIbanAddebito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IBANADDEBITO$10, 0);
        }
    }
    
    /**
     * Gets the "bicAddebito" element
     */
    public java.lang.String getBicAddebito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BICADDEBITO$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "bicAddebito" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier xgetBicAddebito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().find_element_user(BICADDEBITO$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "bicAddebito" element
     */
    public boolean isSetBicAddebito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BICADDEBITO$12) != 0;
        }
    }
    
    /**
     * Sets the "bicAddebito" element
     */
    public void setBicAddebito(java.lang.String bicAddebito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BICADDEBITO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BICADDEBITO$12);
            }
            target.setStringValue(bicAddebito);
        }
    }
    
    /**
     * Sets (as xml) the "bicAddebito" element
     */
    public void xsetBicAddebito(it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier bicAddebito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().find_element_user(BICADDEBITO$12, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier)get_store().add_element_user(BICADDEBITO$12);
            }
            target.set(bicAddebito);
        }
    }
    
    /**
     * Unsets the "bicAddebito" element
     */
    public void unsetBicAddebito()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BICADDEBITO$12, 0);
        }
    }
    
    /**
     * Gets the "firmaRicevuta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta.Enum getFirmaRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRMARICEVUTA$14, 0);
            if (target == null)
            {
                return null;
            }
            return (it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "firmaRicevuta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta xgetFirmaRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta)get_store().find_element_user(FIRMARICEVUTA$14, 0);
            return target;
        }
    }
    
    /**
     * Sets the "firmaRicevuta" element
     */
    public void setFirmaRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta.Enum firmaRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRMARICEVUTA$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRMARICEVUTA$14);
            }
            target.setEnumValue(firmaRicevuta);
        }
    }
    
    /**
     * Sets (as xml) the "firmaRicevuta" element
     */
    public void xsetFirmaRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta firmaRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta)get_store().find_element_user(FIRMARICEVUTA$14, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta)get_store().add_element_user(FIRMARICEVUTA$14);
            }
            target.set(firmaRicevuta);
        }
    }
    
    /**
     * Gets array of all "datiSingoloVersamento" elements
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT[] getDatiSingoloVersamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DATISINGOLOVERSAMENTO$16, targetList);
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT[] result = new it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "datiSingoloVersamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT getDatiSingoloVersamentoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT)get_store().find_element_user(DATISINGOLOVERSAMENTO$16, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "datiSingoloVersamento" element
     */
    public int sizeOfDatiSingoloVersamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISINGOLOVERSAMENTO$16);
        }
    }
    
    /**
     * Sets array of all "datiSingoloVersamento" element
     */
    public void setDatiSingoloVersamentoArray(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT[] datiSingoloVersamentoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(datiSingoloVersamentoArray, DATISINGOLOVERSAMENTO$16);
        }
    }
    
    /**
     * Sets ith "datiSingoloVersamento" element
     */
    public void setDatiSingoloVersamentoArray(int i, it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT datiSingoloVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT)get_store().find_element_user(DATISINGOLOVERSAMENTO$16, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(datiSingoloVersamento);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "datiSingoloVersamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT insertNewDatiSingoloVersamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT)get_store().insert_element_user(DATISINGOLOVERSAMENTO$16, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "datiSingoloVersamento" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT addNewDatiSingoloVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT)get_store().add_element_user(DATISINGOLOVERSAMENTO$16);
            return target;
        }
    }
    
    /**
     * Removes the ith "datiSingoloVersamento" element
     */
    public void removeDatiSingoloVersamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISINGOLOVERSAMENTO$16, i);
        }
    }
}
