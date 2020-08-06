/*
 * XML Type:  ctBilancio
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio;


/**
 * An XML ctBilancio(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/).
 *
 * This is a complex type.
 */
public interface CtBilancio extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtBilancio.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctbilancioafd7type");
    
    /**
     * Gets array of all "capitolo" elements
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo[] getCapitoloArray();
    
    /**
     * Gets ith "capitolo" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo getCapitoloArray(int i);
    
    /**
     * Returns number of "capitolo" element
     */
    int sizeOfCapitoloArray();
    
    /**
     * Sets array of all "capitolo" element
     */
    void setCapitoloArray(it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo[] capitoloArray);
    
    /**
     * Sets ith "capitolo" element
     */
    void setCapitoloArray(int i, it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo capitolo);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "capitolo" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo insertNewCapitolo(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "capitolo" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo addNewCapitolo();
    
    /**
     * Removes the ith "capitolo" element
     */
    void removeCapitolo(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
