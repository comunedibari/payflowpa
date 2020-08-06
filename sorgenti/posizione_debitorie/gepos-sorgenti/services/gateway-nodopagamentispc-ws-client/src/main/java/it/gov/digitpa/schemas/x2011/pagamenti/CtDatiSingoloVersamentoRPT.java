/*
 * XML Type:  ctDatiSingoloVersamentoRPT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti;


/**
 * An XML ctDatiSingoloVersamentoRPT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public interface CtDatiSingoloVersamentoRPT extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiSingoloVersamentoRPT.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41F9FA6057C7193A26B79565BC14C042").resolveHandle("ctdatisingoloversamentorpt76d7type");
    
    /**
     * Gets the "importoSingoloVersamento" element
     */
    java.math.BigDecimal getImportoSingoloVersamento();
    
    /**
     * Gets (as xml) the "importoSingoloVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero xgetImportoSingoloVersamento();
    
    /**
     * Sets the "importoSingoloVersamento" element
     */
    void setImportoSingoloVersamento(java.math.BigDecimal importoSingoloVersamento);
    
    /**
     * Sets (as xml) the "importoSingoloVersamento" element
     */
    void xsetImportoSingoloVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero importoSingoloVersamento);
    
    /**
     * Gets the "commissioneCaricoPA" element
     */
    java.math.BigDecimal getCommissioneCaricoPA();
    
    /**
     * Gets (as xml) the "commissioneCaricoPA" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero xgetCommissioneCaricoPA();
    
    /**
     * True if has "commissioneCaricoPA" element
     */
    boolean isSetCommissioneCaricoPA();
    
    /**
     * Sets the "commissioneCaricoPA" element
     */
    void setCommissioneCaricoPA(java.math.BigDecimal commissioneCaricoPA);
    
    /**
     * Sets (as xml) the "commissioneCaricoPA" element
     */
    void xsetCommissioneCaricoPA(it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero commissioneCaricoPA);
    
    /**
     * Unsets the "commissioneCaricoPA" element
     */
    void unsetCommissioneCaricoPA();
    
    /**
     * Gets the "ibanAccredito" element
     */
    java.lang.String getIbanAccredito();
    
    /**
     * Gets (as xml) the "ibanAccredito" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier xgetIbanAccredito();
    
    /**
     * True if has "ibanAccredito" element
     */
    boolean isSetIbanAccredito();
    
    /**
     * Sets the "ibanAccredito" element
     */
    void setIbanAccredito(java.lang.String ibanAccredito);
    
    /**
     * Sets (as xml) the "ibanAccredito" element
     */
    void xsetIbanAccredito(it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier ibanAccredito);
    
    /**
     * Unsets the "ibanAccredito" element
     */
    void unsetIbanAccredito();
    
    /**
     * Gets the "bicAccredito" element
     */
    java.lang.String getBicAccredito();
    
    /**
     * Gets (as xml) the "bicAccredito" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier xgetBicAccredito();
    
    /**
     * True if has "bicAccredito" element
     */
    boolean isSetBicAccredito();
    
    /**
     * Sets the "bicAccredito" element
     */
    void setBicAccredito(java.lang.String bicAccredito);
    
    /**
     * Sets (as xml) the "bicAccredito" element
     */
    void xsetBicAccredito(it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier bicAccredito);
    
    /**
     * Unsets the "bicAccredito" element
     */
    void unsetBicAccredito();
    
    /**
     * Gets the "ibanAppoggio" element
     */
    java.lang.String getIbanAppoggio();
    
    /**
     * Gets (as xml) the "ibanAppoggio" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier xgetIbanAppoggio();
    
    /**
     * True if has "ibanAppoggio" element
     */
    boolean isSetIbanAppoggio();
    
    /**
     * Sets the "ibanAppoggio" element
     */
    void setIbanAppoggio(java.lang.String ibanAppoggio);
    
    /**
     * Sets (as xml) the "ibanAppoggio" element
     */
    void xsetIbanAppoggio(it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier ibanAppoggio);
    
    /**
     * Unsets the "ibanAppoggio" element
     */
    void unsetIbanAppoggio();
    
    /**
     * Gets the "bicAppoggio" element
     */
    java.lang.String getBicAppoggio();
    
    /**
     * Gets (as xml) the "bicAppoggio" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier xgetBicAppoggio();
    
    /**
     * True if has "bicAppoggio" element
     */
    boolean isSetBicAppoggio();
    
    /**
     * Sets the "bicAppoggio" element
     */
    void setBicAppoggio(java.lang.String bicAppoggio);
    
    /**
     * Sets (as xml) the "bicAppoggio" element
     */
    void xsetBicAppoggio(it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier bicAppoggio);
    
    /**
     * Unsets the "bicAppoggio" element
     */
    void unsetBicAppoggio();
    
    /**
     * Gets the "credenzialiPagatore" element
     */
    java.lang.String getCredenzialiPagatore();
    
    /**
     * Gets (as xml) the "credenzialiPagatore" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetCredenzialiPagatore();
    
    /**
     * True if has "credenzialiPagatore" element
     */
    boolean isSetCredenzialiPagatore();
    
    /**
     * Sets the "credenzialiPagatore" element
     */
    void setCredenzialiPagatore(java.lang.String credenzialiPagatore);
    
    /**
     * Sets (as xml) the "credenzialiPagatore" element
     */
    void xsetCredenzialiPagatore(it.gov.digitpa.schemas.x2011.pagamenti.StText35 credenzialiPagatore);
    
    /**
     * Unsets the "credenzialiPagatore" element
     */
    void unsetCredenzialiPagatore();
    
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
     * Gets the "datiMarcaBolloDigitale" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale getDatiMarcaBolloDigitale();
    
    /**
     * True if has "datiMarcaBolloDigitale" element
     */
    boolean isSetDatiMarcaBolloDigitale();
    
    /**
     * Sets the "datiMarcaBolloDigitale" element
     */
    void setDatiMarcaBolloDigitale(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale datiMarcaBolloDigitale);
    
    /**
     * Appends and returns a new empty "datiMarcaBolloDigitale" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiMarcaBolloDigitale addNewDatiMarcaBolloDigitale();
    
    /**
     * Unsets the "datiMarcaBolloDigitale" element
     */
    void unsetDatiMarcaBolloDigitale();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT newInstance() {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
