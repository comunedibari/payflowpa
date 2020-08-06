/*
 * XML Type:  ctDominio
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctDominio(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtDominioImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio
{
    
    public CtDominioImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFICATIVODOMINIO$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoDominio");
    private static final javax.xml.namespace.QName IDENTIFICATIVOSTAZIONERICHIEDENTE$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "identificativoStazioneRichiedente");
    
    
    /**
     * Gets the "identificativoDominio" element
     */
    public java.lang.String getIdentificativoDominio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVODOMINIO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "identificativoDominio" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoDominio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVODOMINIO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "identificativoDominio" element
     */
    public void setIdentificativoDominio(java.lang.String identificativoDominio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVODOMINIO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVODOMINIO$0);
            }
            target.setStringValue(identificativoDominio);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoDominio" element
     */
    public void xsetIdentificativoDominio(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoDominio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVODOMINIO$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVODOMINIO$0);
            }
            target.set(identificativoDominio);
        }
    }
    
    /**
     * Gets the "identificativoStazioneRichiedente" element
     */
    public java.lang.String getIdentificativoStazioneRichiedente()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "identificativoStazioneRichiedente" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoStazioneRichiedente()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "identificativoStazioneRichiedente" element
     */
    public boolean isSetIdentificativoStazioneRichiedente()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDENTIFICATIVOSTAZIONERICHIEDENTE$2) != 0;
        }
    }
    
    /**
     * Sets the "identificativoStazioneRichiedente" element
     */
    public void setIdentificativoStazioneRichiedente(java.lang.String identificativoStazioneRichiedente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2);
            }
            target.setStringValue(identificativoStazioneRichiedente);
        }
    }
    
    /**
     * Sets (as xml) the "identificativoStazioneRichiedente" element
     */
    public void xsetIdentificativoStazioneRichiedente(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoStazioneRichiedente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().find_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StText35)get_store().add_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2);
            }
            target.set(identificativoStazioneRichiedente);
        }
    }
    
    /**
     * Unsets the "identificativoStazioneRichiedente" element
     */
    public void unsetIdentificativoStazioneRichiedente()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDENTIFICATIVOSTAZIONERICHIEDENTE$2, 0);
        }
    }
}
