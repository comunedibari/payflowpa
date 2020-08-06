/*
 * XML Type:  ctDatiMarcaBolloDigitale
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente;


/**
 * An XML ctDatiMarcaBolloDigitale(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public interface CtDatiMarcaBolloDigitale extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiMarcaBolloDigitale.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctdatimarcabollodigitale038ftype");
    
    /**
     * Gets the "tipoBollo" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo.Enum getTipoBollo();
    
    /**
     * Gets (as xml) the "tipoBollo" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo xgetTipoBollo();
    
    /**
     * Sets the "tipoBollo" element
     */
    void setTipoBollo(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo.Enum tipoBollo);
    
    /**
     * Sets (as xml) the "tipoBollo" element
     */
    void xsetTipoBollo(it.veneto.regione.schemas.x2012.pagamenti.ente.StTipoBollo tipoBollo);
    
    /**
     * Gets the "hashDocumento" element
     */
    java.lang.String getHashDocumento();
    
    /**
     * Gets (as xml) the "hashDocumento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 xgetHashDocumento();
    
    /**
     * Sets the "hashDocumento" element
     */
    void setHashDocumento(java.lang.String hashDocumento);
    
    /**
     * Sets (as xml) the "hashDocumento" element
     */
    void xsetHashDocumento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText70 hashDocumento);
    
    /**
     * Gets the "provinciaResidenza" element
     */
    java.lang.String getProvinciaResidenza();
    
    /**
     * Gets (as xml) the "provinciaResidenza" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia xgetProvinciaResidenza();
    
    /**
     * Sets the "provinciaResidenza" element
     */
    void setProvinciaResidenza(java.lang.String provinciaResidenza);
    
    /**
     * Sets (as xml) the "provinciaResidenza" element
     */
    void xsetProvinciaResidenza(it.veneto.regione.schemas.x2012.pagamenti.ente.StNazioneProvincia provinciaResidenza);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiMarcaBolloDigitale) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
