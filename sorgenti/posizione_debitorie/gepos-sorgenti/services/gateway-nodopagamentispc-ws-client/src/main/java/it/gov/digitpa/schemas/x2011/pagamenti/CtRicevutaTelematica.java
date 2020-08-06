/*
 * XML Type:  ctRicevutaTelematica
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti;


/**
 * An XML ctRicevutaTelematica(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public interface CtRicevutaTelematica extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtRicevutaTelematica.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41F9FA6057C7193A26B79565BC14C042").resolveHandle("ctricevutatelematicaa128type");
    
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
     * Gets the "identificativoMessaggioRicevuta" element
     */
    java.lang.String getIdentificativoMessaggioRicevuta();
    
    /**
     * Gets (as xml) the "identificativoMessaggioRicevuta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoMessaggioRicevuta();
    
    /**
     * Sets the "identificativoMessaggioRicevuta" element
     */
    void setIdentificativoMessaggioRicevuta(java.lang.String identificativoMessaggioRicevuta);
    
    /**
     * Sets (as xml) the "identificativoMessaggioRicevuta" element
     */
    void xsetIdentificativoMessaggioRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoMessaggioRicevuta);
    
    /**
     * Gets the "dataOraMessaggioRicevuta" element
     */
    java.util.Calendar getDataOraMessaggioRicevuta();
    
    /**
     * Gets (as xml) the "dataOraMessaggioRicevuta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime xgetDataOraMessaggioRicevuta();
    
    /**
     * Sets the "dataOraMessaggioRicevuta" element
     */
    void setDataOraMessaggioRicevuta(java.util.Calendar dataOraMessaggioRicevuta);
    
    /**
     * Sets (as xml) the "dataOraMessaggioRicevuta" element
     */
    void xsetDataOraMessaggioRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StISODateTime dataOraMessaggioRicevuta);
    
    /**
     * Gets the "riferimentoMessaggioRichiesta" element
     */
    java.lang.String getRiferimentoMessaggioRichiesta();
    
    /**
     * Gets (as xml) the "riferimentoMessaggioRichiesta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetRiferimentoMessaggioRichiesta();
    
    /**
     * Sets the "riferimentoMessaggioRichiesta" element
     */
    void setRiferimentoMessaggioRichiesta(java.lang.String riferimentoMessaggioRichiesta);
    
    /**
     * Sets (as xml) the "riferimentoMessaggioRichiesta" element
     */
    void xsetRiferimentoMessaggioRichiesta(it.gov.digitpa.schemas.x2011.pagamenti.StText35 riferimentoMessaggioRichiesta);
    
    /**
     * Gets the "riferimentoDataRichiesta" element
     */
    java.util.Calendar getRiferimentoDataRichiesta();
    
    /**
     * Gets (as xml) the "riferimentoDataRichiesta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StISODate xgetRiferimentoDataRichiesta();
    
    /**
     * Sets the "riferimentoDataRichiesta" element
     */
    void setRiferimentoDataRichiesta(java.util.Calendar riferimentoDataRichiesta);
    
    /**
     * Sets (as xml) the "riferimentoDataRichiesta" element
     */
    void xsetRiferimentoDataRichiesta(it.gov.digitpa.schemas.x2011.pagamenti.StISODate riferimentoDataRichiesta);
    
    /**
     * Gets the "istitutoAttestante" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante getIstitutoAttestante();
    
    /**
     * Sets the "istitutoAttestante" element
     */
    void setIstitutoAttestante(it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante istitutoAttestante);
    
    /**
     * Appends and returns a new empty "istitutoAttestante" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtIstitutoAttestante addNewIstitutoAttestante();
    
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
     * Gets the "datiPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT getDatiPagamento();
    
    /**
     * Sets the "datiPagamento" element
     */
    void setDatiPagamento(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT datiPagamento);
    
    /**
     * Appends and returns a new empty "datiPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT addNewDatiPagamento();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica newInstance() {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
