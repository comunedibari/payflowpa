/*
 * XML Type:  ctDatiVersamentoDovuti
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDatiVersamentoDovuti(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDatiVersamentoDovutiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti
{
    
    public CtDatiVersamentoDovutiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOVERSAMENTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "tipoVersamento");
    private static final javax.xml.namespace.QName IDENTIFICATIVOUNIVOCOVERSAMENTO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoUnivocoVersamento");
    private static final javax.xml.namespace.QName DATISINGOLOVERSAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiSingoloVersamento");
    
    
    /**
     * Gets the "tipoVersamento" element
     */
    public java.lang.String getTipoVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOVERSAMENTO$0, 0);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText32)get_store().find_element_user(TIPOVERSAMENTO$0, 0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOVERSAMENTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOVERSAMENTO$0);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText32)get_store().find_element_user(TIPOVERSAMENTO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText32)get_store().add_element_user(TIPOVERSAMENTO$0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$2, 0);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$2, 0);
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
            return get_store().count_elements(IDENTIFICATIVOUNIVOCOVERSAMENTO$2) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$2);
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
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOUNIVOCOVERSAMENTO$2);
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
            get_store().remove_element(IDENTIFICATIVOUNIVOCOVERSAMENTO$2, 0);
        }
    }
    
    /**
     * Gets array of all "datiSingoloVersamento" elements
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti[] getDatiSingoloVersamentoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DATISINGOLOVERSAMENTO$4, targetList);
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti[] result = new it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "datiSingoloVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti getDatiSingoloVersamentoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti)get_store().find_element_user(DATISINGOLOVERSAMENTO$4, i);
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
            return get_store().count_elements(DATISINGOLOVERSAMENTO$4);
        }
    }
    
    /**
     * Sets array of all "datiSingoloVersamento" element
     */
    public void setDatiSingoloVersamentoArray(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti[] datiSingoloVersamentoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(datiSingoloVersamentoArray, DATISINGOLOVERSAMENTO$4);
        }
    }
    
    /**
     * Sets ith "datiSingoloVersamento" element
     */
    public void setDatiSingoloVersamentoArray(int i, it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti datiSingoloVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti)get_store().find_element_user(DATISINGOLOVERSAMENTO$4, i);
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
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti insertNewDatiSingoloVersamento(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti)get_store().insert_element_user(DATISINGOLOVERSAMENTO$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "datiSingoloVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti addNewDatiSingoloVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti)get_store().add_element_user(DATISINGOLOVERSAMENTO$4);
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
            get_store().remove_element(DATISINGOLOVERSAMENTO$4, i);
        }
    }
}
