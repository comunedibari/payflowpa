/*
 * XML Type:  ctAllegatoRicevuta
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti.impl;
/**
 * An XML ctAllegatoRicevuta(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public class CtAllegatoRicevutaImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta
{
    private static final long serialVersionUID = 1L;
    
    public CtAllegatoRicevutaImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOALLEGATORICEVUTA$0 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "tipoAllegatoRicevuta");
    private static final javax.xml.namespace.QName TESTOALLEGATO$2 = 
        new javax.xml.namespace.QName("http://www.digitpa.gov.it/schemas/2011/Pagamenti/", "testoAllegato");
    
    
    /**
     * Gets the "tipoAllegatoRicevuta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta.Enum getTipoAllegatoRicevuta()
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
            return (it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "tipoAllegatoRicevuta" element
     */
    public it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta xgetTipoAllegatoRicevuta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta)get_store().find_element_user(TIPOALLEGATORICEVUTA$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tipoAllegatoRicevuta" element
     */
    public void setTipoAllegatoRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta.Enum tipoAllegatoRicevuta)
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
    public void xsetTipoAllegatoRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta tipoAllegatoRicevuta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta)get_store().find_element_user(TIPOALLEGATORICEVUTA$0, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StTipoAllegatoRicevuta)get_store().add_element_user(TIPOALLEGATORICEVUTA$0);
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
    public it.gov.digitpa.schemas.x2011.pagamenti.StBase64 xgetTestoAllegato()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StBase64 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StBase64)get_store().find_element_user(TESTOALLEGATO$2, 0);
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
    public void xsetTestoAllegato(it.gov.digitpa.schemas.x2011.pagamenti.StBase64 testoAllegato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.gov.digitpa.schemas.x2011.pagamenti.StBase64 target = null;
            target = (it.gov.digitpa.schemas.x2011.pagamenti.StBase64)get_store().find_element_user(TESTOALLEGATO$2, 0);
            if (target == null)
            {
                target = (it.gov.digitpa.schemas.x2011.pagamenti.StBase64)get_store().add_element_user(TESTOALLEGATO$2);
            }
            target.set(testoAllegato);
        }
    }
}
