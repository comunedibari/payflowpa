/*
 * XML Type:  ctAllegatoRicevuta
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.impl;
/**
 * An XML ctAllegatoRicevuta(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public class CtAllegatoRicevutaImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.veneto.regione.schemas.x2012.pagamenti.ente.CtAllegatoRicevuta
{
    
    public CtAllegatoRicevutaImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOALLEGATORICEVUTA$0 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "tipoAllegatoRicevuta");
    private static final javax.xml.namespace.QName TESTOALLEGATO$2 = 
        new javax.xml.namespace.QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/", "testoAllegato");
    
    
    /**
     * Gets the "tipoAllegatoRicevuta" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta.Enum getTipoAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOALLEGATORICEVUTA$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "tipoAllegatoRicevuta" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta xgetTipoAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta)get_store().find_element_user(TIPOALLEGATORICEVUTA$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tipoAllegatoRicevuta" element
     */
    public void setTipoAllegatoRicevuta(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta.Enum tipoAllegatoRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOALLEGATORICEVUTA$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOALLEGATORICEVUTA$0);
            }
            target.setEnumValue(tipoAllegatoRicevuta);
        }
    }
    
    /**
     * Sets (as xml) the "tipoAllegatoRicevuta" element
     */
    public void xsetTipoAllegatoRicevuta(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta tipoAllegatoRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta)get_store().find_element_user(TIPOALLEGATORICEVUTA$0, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoAllegatoRicevuta)get_store().add_element_user(TIPOALLEGATORICEVUTA$0);
            }
            target.set(tipoAllegatoRicevuta);
        }
    }
    
    /**
     * Gets the "testoAllegato" element
     */
    public byte[] getTestoAllegato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TESTOALLEGATO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getByteArrayValue();
        }
    }
    
    /**
     * Gets (as xml) the "testoAllegato" element
     */
    public it.veneto.regione.schemas.x2012.pagamenti.ente.StBase64 xgetTestoAllegato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StBase64 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StBase64)get_store().find_element_user(TESTOALLEGATO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "testoAllegato" element
     */
    public void setTestoAllegato(byte[] testoAllegato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TESTOALLEGATO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TESTOALLEGATO$2);
            }
            target.setByteArrayValue(testoAllegato);
        }
    }
    
    /**
     * Sets (as xml) the "testoAllegato" element
     */
    public void xsetTestoAllegato(it.veneto.regione.schemas.x2012.pagamenti.ente.StBase64 testoAllegato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.veneto.regione.schemas.x2012.pagamenti.ente.StBase64 target = null;
            target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StBase64)get_store().find_element_user(TESTOALLEGATO$2, 0);
            if (target == null)
            {
                target = (it.veneto.regione.schemas.x2012.pagamenti.ente.StBase64)get_store().add_element_user(TESTOALLEGATO$2);
            }
            target.set(testoAllegato);
        }
    }
}
