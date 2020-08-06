/*
 * XML Type:  ctRichiestaPagamentoTelematico
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti;


/**
 * An XML ctRichiestaPagamentoTelematico(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public interface CtRichiestaPagamentoTelematico extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtRichiestaPagamentoTelematico.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41F9FA6057C7193A26B79565BC14C042").resolveHandle("ctrichiestapagamentotelematicoa3cftype");
    
    /**
     * Gets the "versioneOggetto" element
     */
    java.lang.String getVersioneOggetto();
    
    /**
     * Gets (as xml) the "versioneOggetto" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText16 xgetVersioneOggetto();
    
    /**
     * Sets the "versioneOggetto" element
     */
    void setVersioneOggetto(java.lang.String versioneOggetto);
    
    /**
     * Sets (as xml) the "versioneOggetto" element
     */
    void xsetVersioneOggetto(it.gov.digitpa.schemas.x2011.pagamenti.StText16 versioneOggetto);
    
    /**
     * Gets the "dominio" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDominio getDominio();
    
    /**
     * Sets the "dominio" element
     */
    void setDominio(it.gov.digitpa.schemas.x2011.pagamenti.CtDominio dominio);
    
    /**
     * Appends and returns a new empty "dominio" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDominio addNewDominio();
    
    /**
     * Gets the "identificativoMessaggioRichiesta" element
     */
    java.lang.String getIdentificativoMessaggioRichiesta();
    
    /**
     * Gets (as xml) the "identificativoMessaggioRichiesta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoMessaggioRichiesta();
    
    /**
     * Sets the "identificativoMessaggioRichiesta" element
     */
    void setIdentificativoMessaggioRichiesta(java.lang.String identificativoMessaggioRichiesta);
    
    /**
     * Sets (as xml) the "identificativoMessaggioRichiesta" element
     */
    void xsetIdentificativoMessaggioRichiesta(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoMessaggioRichiesta);
    
    /**
     * Gets the "dataOraMessaggioRichiesta" element
     */
    java.util.Calendar getDataOraMessaggioRichiesta();
    
    /**
     * Gets (as xml) the "dataOraMessaggioRichiesta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime xgetDataOraMessaggioRichiesta();
    
    /**
     * Sets the "dataOraMessaggioRichiesta" element
     */
    void setDataOraMessaggioRichiesta(java.util.Calendar dataOraMessaggioRichiesta);
    
    /**
     * Sets (as xml) the "dataOraMessaggioRichiesta" element
     */
    void xsetDataOraMessaggioRichiesta(it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime dataOraMessaggioRichiesta);
    
    /**
     * Gets the "autenticazioneSoggetto" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto.Enum getAutenticazioneSoggetto();
    
    /**
     * Gets (as xml) the "autenticazioneSoggetto" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto xgetAutenticazioneSoggetto();
    
    /**
     * Sets the "autenticazioneSoggetto" element
     */
    void setAutenticazioneSoggetto(it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto.Enum autenticazioneSoggetto);
    
    /**
     * Sets (as xml) the "autenticazioneSoggetto" element
     */
    void xsetAutenticazioneSoggetto(it.gov.digitpa.schemas.x2011.pagamenti.StAutenticazioneSoggetto autenticazioneSoggetto);
    
    /**
     * Gets the "soggettoVersante" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante getSoggettoVersante();
    
    /**
     * True if has "soggettoVersante" element
     */
    boolean isSetSoggettoVersante();
    
    /**
     * Sets the "soggettoVersante" element
     */
    void setSoggettoVersante(it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante soggettoVersante);
    
    /**
     * Appends and returns a new empty "soggettoVersante" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante addNewSoggettoVersante();
    
    /**
     * Unsets the "soggettoVersante" element
     */
    void unsetSoggettoVersante();
    
    /**
     * Gets the "soggettoPagatore" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore getSoggettoPagatore();
    
    /**
     * Sets the "soggettoPagatore" element
     */
    void setSoggettoPagatore(it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore soggettoPagatore);
    
    /**
     * Appends and returns a new empty "soggettoPagatore" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore addNewSoggettoPagatore();
    
    /**
     * Gets the "enteBeneficiario" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario getEnteBeneficiario();
    
    /**
     * Sets the "enteBeneficiario" element
     */
    void setEnteBeneficiario(it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario enteBeneficiario);
    
    /**
     * Appends and returns a new empty "enteBeneficiario" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtEnteBeneficiario addNewEnteBeneficiario();
    
    /**
     * Gets the "datiVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT getDatiVersamento();
    
    /**
     * Sets the "datiVersamento" element
     */
    void setDatiVersamento(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT datiVersamento);
    
    /**
     * Appends and returns a new empty "datiVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT addNewDatiVersamento();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico newInstance() {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRichiestaPagamentoTelematico) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
