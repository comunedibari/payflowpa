/*
 * XML Type:  ctCapitolo
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio;


/**
 * An XML ctCapitolo(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/).
 *
 * This is a complex type.
 */
public interface CtCapitolo extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtCapitolo.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctcapitolobe19type");
    
    /**
     * Gets the "codCapitolo" element
     */
    java.lang.String getCodCapitolo();
    
    /**
     * Gets (as xml) the "codCapitolo" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 xgetCodCapitolo();
    
    /**
     * Sets the "codCapitolo" element
     */
    void setCodCapitolo(java.lang.String codCapitolo);
    
    /**
     * Sets (as xml) the "codCapitolo" element
     */
    void xsetCodCapitolo(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 codCapitolo);
    
    /**
     * Gets the "codUfficio" element
     */
    java.lang.String getCodUfficio();
    
    /**
     * Gets (as xml) the "codUfficio" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 xgetCodUfficio();
    
    /**
     * Sets the "codUfficio" element
     */
    void setCodUfficio(java.lang.String codUfficio);
    
    /**
     * Sets (as xml) the "codUfficio" element
     */
    void xsetCodUfficio(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.StText12 codUfficio);
    
    /**
     * Gets array of all "accertamento" elements
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento[] getAccertamentoArray();
    
    /**
     * Gets ith "accertamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento getAccertamentoArray(int i);
    
    /**
     * Returns number of "accertamento" element
     */
    int sizeOfAccertamentoArray();
    
    /**
     * Sets array of all "accertamento" element
     */
    void setAccertamentoArray(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento[] accertamentoArray);
    
    /**
     * Sets ith "accertamento" element
     */
    void setAccertamentoArray(int i, it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento accertamento);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "accertamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento insertNewAccertamento(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "accertamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento addNewAccertamento();
    
    /**
     * Removes the ith "accertamento" element
     */
    void removeAccertamento(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
