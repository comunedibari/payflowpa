/*
 * XML Type:  ctPagatiConRicevuta
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente;


/**
 * An XML ctPagatiConRicevuta(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public interface CtPagatiConRicevuta extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtPagatiConRicevuta.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctpagaticonricevuta5cfdtype");
    
    /**
     * Gets the "versioneOggetto" element
     */
    java.lang.String getVersioneOggetto();
    
    /**
     * Gets (as xml) the "versioneOggetto" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 xgetVersioneOggetto();
    
    /**
     * Sets the "versioneOggetto" element
     */
    void setVersioneOggetto(java.lang.String versioneOggetto);
    
    /**
     * Sets (as xml) the "versioneOggetto" element
     */
    void xsetVersioneOggetto(it.veneto.regione.schemas.x2012.pagamenti.ente.StText16 versioneOggetto);
    
    /**
     * Gets the "dominio" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio getDominio();
    
    /**
     * Sets the "dominio" element
     */
    void setDominio(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio dominio);
    
    /**
     * Appends and returns a new empty "dominio" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDominio addNewDominio();
    
    /**
     * Gets the "identificativoMessaggioRicevuta" element
     */
    java.lang.String getIdentificativoMessaggioRicevuta();
    
    /**
     * Gets (as xml) the "identificativoMessaggioRicevuta" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoMessaggioRicevuta();
    
    /**
     * Sets the "identificativoMessaggioRicevuta" element
     */
    void setIdentificativoMessaggioRicevuta(java.lang.String identificativoMessaggioRicevuta);
    
    /**
     * Sets (as xml) the "identificativoMessaggioRicevuta" element
     */
    void xsetIdentificativoMessaggioRicevuta(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoMessaggioRicevuta);
    
    /**
     * Gets the "dataOraMessaggioRicevuta" element
     */
    java.util.Calendar getDataOraMessaggioRicevuta();
    
    /**
     * Gets (as xml) the "dataOraMessaggioRicevuta" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime xgetDataOraMessaggioRicevuta();
    
    /**
     * Sets the "dataOraMessaggioRicevuta" element
     */
    void setDataOraMessaggioRicevuta(java.util.Calendar dataOraMessaggioRicevuta);
    
    /**
     * Sets (as xml) the "dataOraMessaggioRicevuta" element
     */
    void xsetDataOraMessaggioRicevuta(it.veneto.regione.schemas.x2012.pagamenti.ente.StISODateTime dataOraMessaggioRicevuta);
    
    /**
     * Gets the "riferimentoMessaggioRichiesta" element
     */
    java.lang.String getRiferimentoMessaggioRichiesta();
    
    /**
     * Gets (as xml) the "riferimentoMessaggioRichiesta" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetRiferimentoMessaggioRichiesta();
    
    /**
     * Sets the "riferimentoMessaggioRichiesta" element
     */
    void setRiferimentoMessaggioRichiesta(java.lang.String riferimentoMessaggioRichiesta);
    
    /**
     * Sets (as xml) the "riferimentoMessaggioRichiesta" element
     */
    void xsetRiferimentoMessaggioRichiesta(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 riferimentoMessaggioRichiesta);
    
    /**
     * Gets the "riferimentoDataRichiesta" element
     */
    java.util.Calendar getRiferimentoDataRichiesta();
    
    /**
     * Gets (as xml) the "riferimentoDataRichiesta" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate xgetRiferimentoDataRichiesta();
    
    /**
     * Sets the "riferimentoDataRichiesta" element
     */
    void setRiferimentoDataRichiesta(java.util.Calendar riferimentoDataRichiesta);
    
    /**
     * Sets (as xml) the "riferimentoDataRichiesta" element
     */
    void xsetRiferimentoDataRichiesta(it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate riferimentoDataRichiesta);
    
    /**
     * Gets the "istitutoAttestante" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante getIstitutoAttestante();
    
    /**
     * Sets the "istitutoAttestante" element
     */
    void setIstitutoAttestante(it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante istitutoAttestante);
    
    /**
     * Appends and returns a new empty "istitutoAttestante" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtIstitutoAttestante addNewIstitutoAttestante();
    
    /**
     * Gets the "enteBeneficiario" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario getEnteBeneficiario();
    
    /**
     * Sets the "enteBeneficiario" element
     */
    void setEnteBeneficiario(it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario enteBeneficiario);
    
    /**
     * Appends and returns a new empty "enteBeneficiario" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtEnteBeneficiario addNewEnteBeneficiario();
    
    /**
     * Gets the "soggettoPagatore" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore getSoggettoPagatore();
    
    /**
     * Sets the "soggettoPagatore" element
     */
    void setSoggettoPagatore(it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore soggettoPagatore);
    
    /**
     * Appends and returns a new empty "soggettoPagatore" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtSoggettoPagatore addNewSoggettoPagatore();
    
    /**
     * Gets the "datiPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagatiConRicevuta getDatiPagamento();
    
    /**
     * Sets the "datiPagamento" element
     */
    void setDatiPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagatiConRicevuta datiPagamento);
    
    /**
     * Appends and returns a new empty "datiPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagatiConRicevuta addNewDatiPagamento();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtPagatiConRicevuta) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
