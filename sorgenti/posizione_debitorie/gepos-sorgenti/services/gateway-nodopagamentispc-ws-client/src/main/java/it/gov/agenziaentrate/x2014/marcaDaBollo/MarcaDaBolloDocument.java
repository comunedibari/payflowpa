/*
 * An XML document type.
 * Localname: marcaDaBollo
 * Namespace: http://www.agenziaentrate.gov.it/2014/MarcaDaBollo
 * Java type: it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument
 *
 * Automatically generated - do not modify.
 */
package it.gov.agenziaentrate.x2014.marcaDaBollo;


/**
 * A document containing one marcaDaBollo(@http://www.agenziaentrate.gov.it/2014/MarcaDaBollo) element.
 *
 * This is a complex type.
 */
public interface MarcaDaBolloDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MarcaDaBolloDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41F9FA6057C7193A26B79565BC14C042").resolveHandle("marcadabollod10edoctype");
    
    /**
     * Gets the "marcaDaBollo" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo getMarcaDaBollo();
    
    /**
     * Sets the "marcaDaBollo" element
     */
    void setMarcaDaBollo(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo marcaDaBollo);
    
    /**
     * Appends and returns a new empty "marcaDaBollo" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo addNewMarcaDaBollo();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument newInstance() {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
