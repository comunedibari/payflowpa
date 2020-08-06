/*
 * XML Type:  ctDovuti
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDovuti(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDovutiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDovuti
{
    
    public CtDovutiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VERSIONEOGGETTO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "versioneOggetto");
    private static final javax.xml.namespace.QName SOGGETTOPAGATORE$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "soggettoPagatore");
    private static final javax.xml.namespace.QName DATIVERSAMENTO$4 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "datiVersamento");
    
    
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
     * Gets the "datiVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti getDatiVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti)get_store().find_element_user(DATIVERSAMENTO$4, 0);
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
    public void setDatiVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti datiVersamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti)get_store().find_element_user(DATIVERSAMENTO$4, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti)get_store().add_element_user(DATIVERSAMENTO$4);
            }
            target.set(datiVersamento);
        }
    }
    
    /**
     * Appends and returns a new empty "datiVersamento" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti addNewDatiVersamento()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti)get_store().add_element_user(DATIVERSAMENTO$4);
            return target;
        }
    }
}
