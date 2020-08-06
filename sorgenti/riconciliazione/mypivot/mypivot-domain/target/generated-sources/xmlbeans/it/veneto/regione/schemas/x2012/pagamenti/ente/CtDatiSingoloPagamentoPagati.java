/*
 * XML Type:  ctDatiSingoloPagamentoPagati
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente;


/**
 * An XML ctDatiSingoloPagamentoPagati(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public interface CtDatiSingoloPagamentoPagati extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiSingoloPagamentoPagati.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctdatisingolopagamentopagati90bftype");
    
    /**
     * Gets the "identificativoUnivocoDovuto" element
     */
    java.lang.String getIdentificativoUnivocoDovuto();
    
    /**
     * Gets (as xml) the "identificativoUnivocoDovuto" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoUnivocoDovuto();
    
    /**
     * Sets the "identificativoUnivocoDovuto" element
     */
    void setIdentificativoUnivocoDovuto(java.lang.String identificativoUnivocoDovuto);
    
    /**
     * Sets (as xml) the "identificativoUnivocoDovuto" element
     */
    void xsetIdentificativoUnivocoDovuto(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoUnivocoDovuto);
    
    /**
     * Gets the "singoloImportoPagato" element
     */
    java.math.BigDecimal getSingoloImportoPagato();
    
    /**
     * Gets (as xml) the "singoloImportoPagato" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetSingoloImportoPagato();
    
    /**
     * Sets the "singoloImportoPagato" element
     */
    void setSingoloImportoPagato(java.math.BigDecimal singoloImportoPagato);
    
    /**
     * Sets (as xml) the "singoloImportoPagato" element
     */
    void xsetSingoloImportoPagato(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto singoloImportoPagato);
    
    /**
     * Gets the "esitoSingoloPagamento" element
     */
    java.lang.String getEsitoSingoloPagamento();
    
    /**
     * Gets (as xml) the "esitoSingoloPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetEsitoSingoloPagamento();
    
    /**
     * True if has "esitoSingoloPagamento" element
     */
    boolean isSetEsitoSingoloPagamento();
    
    /**
     * Sets the "esitoSingoloPagamento" element
     */
    void setEsitoSingoloPagamento(java.lang.String esitoSingoloPagamento);
    
    /**
     * Sets (as xml) the "esitoSingoloPagamento" element
     */
    void xsetEsitoSingoloPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 esitoSingoloPagamento);
    
    /**
     * Unsets the "esitoSingoloPagamento" element
     */
    void unsetEsitoSingoloPagamento();
    
    /**
     * Gets the "dataEsitoSingoloPagamento" element
     */
    java.util.Calendar getDataEsitoSingoloPagamento();
    
    /**
     * Gets (as xml) the "dataEsitoSingoloPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate xgetDataEsitoSingoloPagamento();
    
    /**
     * Sets the "dataEsitoSingoloPagamento" element
     */
    void setDataEsitoSingoloPagamento(java.util.Calendar dataEsitoSingoloPagamento);
    
    /**
     * Sets (as xml) the "dataEsitoSingoloPagamento" element
     */
    void xsetDataEsitoSingoloPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate dataEsitoSingoloPagamento);
    
    /**
     * Gets the "identificativoUnivocoRiscossione" element
     */
    java.lang.String getIdentificativoUnivocoRiscossione();
    
    /**
     * Gets (as xml) the "identificativoUnivocoRiscossione" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoUnivocoRiscossione();
    
    /**
     * Sets the "identificativoUnivocoRiscossione" element
     */
    void setIdentificativoUnivocoRiscossione(java.lang.String identificativoUnivocoRiscossione);
    
    /**
     * Sets (as xml) the "identificativoUnivocoRiscossione" element
     */
    void xsetIdentificativoUnivocoRiscossione(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoUnivocoRiscossione);
    
    /**
     * Gets the "causaleVersamento" element
     */
    java.lang.String getCausaleVersamento();
    
    /**
     * Gets (as xml) the "causaleVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText140 xgetCausaleVersamento();
    
    /**
     * Sets the "causaleVersamento" element
     */
    void setCausaleVersamento(java.lang.String causaleVersamento);
    
    /**
     * Sets (as xml) the "causaleVersamento" element
     */
    void xsetCausaleVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText140 causaleVersamento);
    
    /**
     * Gets the "datiSpecificiRiscossione" element
     */
    java.lang.String getDatiSpecificiRiscossione();
    
    /**
     * Gets (as xml) the "datiSpecificiRiscossione" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione xgetDatiSpecificiRiscossione();
    
    /**
     * Sets the "datiSpecificiRiscossione" element
     */
    void setDatiSpecificiRiscossione(java.lang.String datiSpecificiRiscossione);
    
    /**
     * Sets (as xml) the "datiSpecificiRiscossione" element
     */
    void xsetDatiSpecificiRiscossione(it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione datiSpecificiRiscossione);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
