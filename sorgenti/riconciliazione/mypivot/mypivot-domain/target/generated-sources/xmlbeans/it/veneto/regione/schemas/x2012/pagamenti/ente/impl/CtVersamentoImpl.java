/*
 * XML Type:  ctVersamento
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctVersamento(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtVersamentoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtVersamento
{
    
    public CtVersamentoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VERSIONEOGGETTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "versioneOggetto");
    private static final javax.xml.namespace.QName SOGGETTOPAGATORE$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "soggettoPagatore");
    private static final javax.xml.namespace.QName DATIVERSAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiVersamento");
    private static final javax.xml.namespace.QName AZIONE$6 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "azione");
    
    
    /**
     * Gets the "versioneOggetto" element
     */
    public java.lang.String getVersioneOggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "versioneOggetto" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 xgetVersioneOggetto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "versioneOggetto" element
     */
    public void setVersioneOggetto(java.lang.String versioneOggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VERSIONEOGGETTO$0);
            }
            target.setStringValue(versioneOggetto);
        }
    }
    
    /**
     * Sets (as xml) the "versioneOggetto" element
     */
    public void xsetVersioneOggetto(it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 versioneOggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().find_element_user(VERSIONEOGGETTO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText16)get_store().add_element_user(VERSIONEOGGETTO$0);
            }
            target.set(versioneOggetto);
        }
    }
    
    /**
     * Gets the "soggettoPagatore" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore getSoggettoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore)get_store().find_element_user(SOGGETTOPAGATORE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "soggettoPagatore" element
     */
    public boolean isSetSoggettoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SOGGETTOPAGATORE$2) != 0;
        }
    }
    
    /**
     * Sets the "soggettoPagatore" element
     */
    public void setSoggettoPagatore(it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore soggettoPagatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore)get_store().find_element_user(SOGGETTOPAGATORE$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore)get_store().add_element_user(SOGGETTOPAGATORE$2);
            }
            target.set(soggettoPagatore);
        }
    }
    
    /**
     * Appends and returns a new empty "soggettoPagatore" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore addNewSoggettoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore)get_store().add_element_user(SOGGETTOPAGATORE$2);
            return target;
        }
    }
    
    /**
     * Unsets the "soggettoPagatore" element
     */
    public void unsetSoggettoPagatore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SOGGETTOPAGATORE$2, 0);
        }
    }
    
    /**
     * Gets the "datiVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento getDatiVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento)get_store().find_element_user(DATIVERSAMENTO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "datiVersamento" element
     */
    public void setDatiVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento datiVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento)get_store().find_element_user(DATIVERSAMENTO$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento)get_store().add_element_user(DATIVERSAMENTO$4);
            }
            target.set(datiVersamento);
        }
    }
    
    /**
     * Appends and returns a new empty "datiVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento addNewDatiVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento)get_store().add_element_user(DATIVERSAMENTO$4);
            return target;
        }
    }
    
    /**
     * Gets the "azione" element
     */
    public java.lang.String getAzione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AZIONE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "azione" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText1 xgetAzione()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText1 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1)get_store().find_element_user(AZIONE$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "azione" element
     */
    public void setAzione(java.lang.String azione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AZIONE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AZIONE$6);
            }
            target.setStringValue(azione);
        }
    }
    
    /**
     * Sets (as xml) the "azione" element
     */
    public void xsetAzione(it.veneto.regione.schemas.x2012.pagamenti.ente.StText1 azione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText1 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1)get_store().find_element_user(AZIONE$6, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText1)get_store().add_element_user(AZIONE$6);
            }
            target.set(azione);
        }
    }
}
