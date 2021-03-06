/*
 * XML Type:  ctIdentificativoUnivoco
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente;


/**
 * An XML ctIdentificativoUnivoco(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public interface CtIdentificativoUnivoco extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtIdentificativoUnivoco.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctidentificativounivoco68e3type");
    
    /**
     * Gets the "tipoIdentificativoUnivoco" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco.Enum getTipoIdentificativoUnivoco();
    
    /**
     * Gets (as xml) the "tipoIdentificativoUnivoco" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco xgetTipoIdentificativoUnivoco();
    
    /**
     * Sets the "tipoIdentificativoUnivoco" element
     */
    void setTipoIdentificativoUnivoco(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco.Enum tipoIdentificativoUnivoco);
    
    /**
     * Sets (as xml) the "tipoIdentificativoUnivoco" element
     */
    void xsetTipoIdentificativoUnivoco(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoIdentificativoUnivoco tipoIdentificativoUnivoco);
    
    /**
     * Gets the "codiceIdentificativoUnivoco" element
     */
    java.lang.String getCodiceIdentificativoUnivoco();
    
    /**
     * Gets (as xml) the "codiceIdentificativoUnivoco" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetCodiceIdentificativoUnivoco();
    
    /**
     * Sets the "codiceIdentificativoUnivoco" element
     */
    void setCodiceIdentificativoUnivoco(java.lang.String codiceIdentificativoUnivoco);
    
    /**
     * Sets (as xml) the "codiceIdentificativoUnivoco" element
     */
    void xsetCodiceIdentificativoUnivoco(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 codiceIdentificativoUnivoco);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtIdentificativoUnivoco) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
