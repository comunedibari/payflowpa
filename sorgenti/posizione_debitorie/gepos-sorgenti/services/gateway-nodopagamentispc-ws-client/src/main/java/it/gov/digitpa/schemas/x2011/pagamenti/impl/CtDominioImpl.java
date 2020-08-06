/*
 * XML Type:  ctDominio
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDominio
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctDominio(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtDominioImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtDominio
{
    private static final long serialVersionUID = 1L;
    
    public CtDominioImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFICATIVODOMINIO$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoDominio");
    private static final javax.xml.namespace.QName IDENTIFICATIVOSTAZIONERICHIEDENTE$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "identificativoStazioneRichiedente");
    
    
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoDominio()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVODOMINIO$0, 0);
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
    public void xsetIdentificativoDominio(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoDominio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVODOMINIO$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(IDENTIFICATIVODOMINIO$0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoStazioneRichiedente()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2, 0);
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
    public void xsetIdentificativoStazioneRichiedente(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoStazioneRichiedente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StText35 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().find_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StText35)get_store().add_element_user(IDENTIFICATIVOSTAZIONERICHIEDENTE$2);
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
