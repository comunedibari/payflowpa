/*
 * XML Type:  tipoMarcaDaBollo
 * Namespace: http://www.agenziaentrate.gov.it/2014/MarcaDaBollo
 * Java type: it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo
 *
 * Automatically generated - do not modify.
 */
package it.gov.agenziaentrate.x2014.marcaDaBollo;


/**
 * An XML tipoMarcaDaBollo(@http://www.agenziaentrate.gov.it/2014/MarcaDaBollo).
 *
 * This is a complex type.
 */
public interface TipoMarcaDaBollo extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(TipoMarcaDaBollo.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41F9FA6057C7193A26B79565BC14C042").resolveHandle("tipomarcadabollocbcatype");
    
    /**
     * Gets the "PSP" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP getPSP();
    
    /**
     * Sets the "PSP" element
     */
    void setPSP(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP psp);
    
    /**
     * Appends and returns a new empty "PSP" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoPSP addNewPSP();
    
    /**
     * Gets the "IUBD" element
     */
    java.lang.String getIUBD();
    
    /**
     * Gets (as xml) the "IUBD" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT xgetIUBD();
    
    /**
     * Sets the "IUBD" element
     */
    void setIUBD(java.lang.String iubd);
    
    /**
     * Sets (as xml) the "IUBD" element
     */
    void xsetIUBD(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTXT iubd);
    
    /**
     * Gets the "OraAcquisto" element
     */
    java.util.Calendar getOraAcquisto();
    
    /**
     * Gets (as xml) the "OraAcquisto" element
     */
    org.apache.xmlbeans.XmlDateTime xgetOraAcquisto();
    
    /**
     * Sets the "OraAcquisto" element
     */
    void setOraAcquisto(java.util.Calendar oraAcquisto);
    
    /**
     * Sets (as xml) the "OraAcquisto" element
     */
    void xsetOraAcquisto(org.apache.xmlbeans.XmlDateTime oraAcquisto);
    
    /**
     * Gets the "Importo" element
     */
    java.math.BigDecimal getImporto();
    
    /**
     * Gets (as xml) the "Importo" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto xgetImporto();
    
    /**
     * Sets the "Importo" element
     */
    void setImporto(java.math.BigDecimal importo);
    
    /**
     * Sets (as xml) the "Importo" element
     */
    void xsetImporto(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImporto importo);
    
    /**
     * Gets the "TipoBollo" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo.Enum getTipoBollo();
    
    /**
     * Gets (as xml) the "TipoBollo" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo xgetTipoBollo();
    
    /**
     * Sets the "TipoBollo" element
     */
    void setTipoBollo(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo.Enum tipoBollo);
    
    /**
     * Sets (as xml) the "TipoBollo" element
     */
    void xsetTipoBollo(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoTipoBollo tipoBollo);
    
    /**
     * Gets the "ImprontaDocumento" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta getImprontaDocumento();
    
    /**
     * Sets the "ImprontaDocumento" element
     */
    void setImprontaDocumento(it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta improntaDocumento);
    
    /**
     * Appends and returns a new empty "ImprontaDocumento" element
     */
    it.gov.agenziaentrate.x2014.marcaDaBollo.TipoImpronta addNewImprontaDocumento();
    
    /**
     * Gets the "Signature" element
     */
    org.w3.x2000.x09.xmldsig.SignatureType getSignature();
    
    /**
     * Sets the "Signature" element
     */
    void setSignature(org.w3.x2000.x09.xmldsig.SignatureType signature);
    
    /**
     * Appends and returns a new empty "Signature" element
     */
    org.w3.x2000.x09.xmldsig.SignatureType addNewSignature();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo newInstance() {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
