/*
 * XML Type:  ctDatiSingoloPagamentoRT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti;


/**
 * An XML ctDatiSingoloPagamentoRT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public interface CtDatiSingoloPagamentoRT extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiSingoloPagamentoRT.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41F9FA6057C7193A26B79565BC14C042").resolveHandle("ctdatisingolopagamentort66e9type");
    
    /**
     * Gets the "singoloImportoPagato" element
     */
    java.math.BigDecimal getSingoloImportoPagato();
    
    /**
     * Gets (as xml) the "singoloImportoPagato" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StImporto xgetSingoloImportoPagato();
    
    /**
     * Sets the "singoloImportoPagato" element
     */
    void setSingoloImportoPagato(java.math.BigDecimal singoloImportoPagato);
    
    /**
     * Sets (as xml) the "singoloImportoPagato" element
     */
    void xsetSingoloImportoPagato(it.gov.digitpa.schemas.x2011.pagamenti.StImporto singoloImportoPagato);
    
    /**
     * Gets the "esitoSingoloPagamento" element
     */
    java.lang.String getEsitoSingoloPagamento();
    
    /**
     * Gets (as xml) the "esitoSingoloPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetEsitoSingoloPagamento();
    
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
    void xsetEsitoSingoloPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StText35 esitoSingoloPagamento);
    
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
    it.gov.digitpa.schemas.x2011.pagamenti.StISODate xgetDataEsitoSingoloPagamento();
    
    /**
     * Sets the "dataEsitoSingoloPagamento" element
     */
    void setDataEsitoSingoloPagamento(java.util.Calendar dataEsitoSingoloPagamento);
    
    /**
     * Sets (as xml) the "dataEsitoSingoloPagamento" element
     */
    void xsetDataEsitoSingoloPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StISODate dataEsitoSingoloPagamento);
    
    /**
     * Gets the "identificativoUnivocoRiscossione" element
     */
    java.lang.String getIdentificativoUnivocoRiscossione();
    
    /**
     * Gets (as xml) the "identificativoUnivocoRiscossione" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoUnivocoRiscossione();
    
    /**
     * Sets the "identificativoUnivocoRiscossione" element
     */
    void setIdentificativoUnivocoRiscossione(java.lang.String identificativoUnivocoRiscossione);
    
    /**
     * Sets (as xml) the "identificativoUnivocoRiscossione" element
     */
    void xsetIdentificativoUnivocoRiscossione(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoUnivocoRiscossione);
    
    /**
     * Gets the "causaleVersamento" element
     */
    java.lang.String getCausaleVersamento();
    
    /**
     * Gets (as xml) the "causaleVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText140 xgetCausaleVersamento();
    
    /**
     * Sets the "causaleVersamento" element
     */
    void setCausaleVersamento(java.lang.String causaleVersamento);
    
    /**
     * Sets (as xml) the "causaleVersamento" element
     */
    void xsetCausaleVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StText140 causaleVersamento);
    
    /**
     * Gets the "datiSpecificiRiscossione" element
     */
    java.lang.String getDatiSpecificiRiscossione();
    
    /**
     * Gets (as xml) the "datiSpecificiRiscossione" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione xgetDatiSpecificiRiscossione();
    
    /**
     * Sets the "datiSpecificiRiscossione" element
     */
    void setDatiSpecificiRiscossione(java.lang.String datiSpecificiRiscossione);
    
    /**
     * Sets (as xml) the "datiSpecificiRiscossione" element
     */
    void xsetDatiSpecificiRiscossione(it.gov.digitpa.schemas.x2011.pagamenti.StDatiSpecificiRiscossione datiSpecificiRiscossione);
    
    /**
     * Gets the "commissioniApplicatePSP" element
     */
    java.math.BigDecimal getCommissioniApplicatePSP();
    
    /**
     * Gets (as xml) the "commissioniApplicatePSP" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StImporto xgetCommissioniApplicatePSP();
    
    /**
     * True if has "commissioniApplicatePSP" element
     */
    boolean isSetCommissioniApplicatePSP();
    
    /**
     * Sets the "commissioniApplicatePSP" element
     */
    void setCommissioniApplicatePSP(java.math.BigDecimal commissioniApplicatePSP);
    
    /**
     * Sets (as xml) the "commissioniApplicatePSP" element
     */
    void xsetCommissioniApplicatePSP(it.gov.digitpa.schemas.x2011.pagamenti.StImporto commissioniApplicatePSP);
    
    /**
     * Unsets the "commissioniApplicatePSP" element
     */
    void unsetCommissioniApplicatePSP();
    
    /**
     * Gets the "allegatoRicevuta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta getAllegatoRicevuta();
    
    /**
     * True if has "allegatoRicevuta" element
     */
    boolean isSetAllegatoRicevuta();
    
    /**
     * Sets the "allegatoRicevuta" element
     */
    void setAllegatoRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta allegatoRicevuta);
    
    /**
     * Appends and returns a new empty "allegatoRicevuta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtAllegatoRicevuta addNewAllegatoRicevuta();
    
    /**
     * Unsets the "allegatoRicevuta" element
     */
    void unsetAllegatoRicevuta();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT newInstance() {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
