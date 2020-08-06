/*
 * XML Type:  ctDatiVersamento
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente;


/**
 * An XML ctDatiVersamento(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public interface CtDatiVersamento extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiVersamento.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctdativersamento737ctype");
    
    /**
     * Gets the "dataEsecuzionePagamento" element
     */
    java.util.Calendar getDataEsecuzionePagamento();
    
    /**
     * Gets (as xml) the "dataEsecuzionePagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate xgetDataEsecuzionePagamento();
    
    /**
     * Sets the "dataEsecuzionePagamento" element
     */
    void setDataEsecuzionePagamento(java.util.Calendar dataEsecuzionePagamento);
    
    /**
     * Sets (as xml) the "dataEsecuzionePagamento" element
     */
    void xsetDataEsecuzionePagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StISODate dataEsecuzionePagamento);
    
    /**
     * Gets the "tipoVersamento" element
     */
    java.lang.String getTipoVersamento();
    
    /**
     * Gets (as xml) the "tipoVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText32 xgetTipoVersamento();
    
    /**
     * True if has "tipoVersamento" element
     */
    boolean isSetTipoVersamento();
    
    /**
     * Sets the "tipoVersamento" element
     */
    void setTipoVersamento(java.lang.String tipoVersamento);
    
    /**
     * Sets (as xml) the "tipoVersamento" element
     */
    void xsetTipoVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText32 tipoVersamento);
    
    /**
     * Unsets the "tipoVersamento" element
     */
    void unsetTipoVersamento();
    
    /**
     * Gets the "identificativoUnivocoVersamento" element
     */
    java.lang.String getIdentificativoUnivocoVersamento();
    
    /**
     * Gets (as xml) the "identificativoUnivocoVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoUnivocoVersamento();
    
    /**
     * True if has "identificativoUnivocoVersamento" element
     */
    boolean isSetIdentificativoUnivocoVersamento();
    
    /**
     * Sets the "identificativoUnivocoVersamento" element
     */
    void setIdentificativoUnivocoVersamento(java.lang.String identificativoUnivocoVersamento);
    
    /**
     * Sets (as xml) the "identificativoUnivocoVersamento" element
     */
    void xsetIdentificativoUnivocoVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoUnivocoVersamento);
    
    /**
     * Unsets the "identificativoUnivocoVersamento" element
     */
    void unsetIdentificativoUnivocoVersamento();
    
    /**
     * Gets the "identificativoUnivocoDovuto" element
     */
    java.lang.String getIdentificativoUnivocoDovuto();
    
    /**
     * Gets (as xml) the "identificativoUnivocoDovuto" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoUnivocoDovuto();
    
    /**
     * True if has "identificativoUnivocoDovuto" element
     */
    boolean isSetIdentificativoUnivocoDovuto();
    
    /**
     * Sets the "identificativoUnivocoDovuto" element
     */
    void setIdentificativoUnivocoDovuto(java.lang.String identificativoUnivocoDovuto);
    
    /**
     * Sets (as xml) the "identificativoUnivocoDovuto" element
     */
    void xsetIdentificativoUnivocoDovuto(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoUnivocoDovuto);
    
    /**
     * Unsets the "identificativoUnivocoDovuto" element
     */
    void unsetIdentificativoUnivocoDovuto();
    
    /**
     * Gets the "importoSingoloVersamento" element
     */
    java.math.BigDecimal getImportoSingoloVersamento();
    
    /**
     * Gets (as xml) the "importoSingoloVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetImportoSingoloVersamento();
    
    /**
     * Sets the "importoSingoloVersamento" element
     */
    void setImportoSingoloVersamento(java.math.BigDecimal importoSingoloVersamento);
    
    /**
     * Sets (as xml) the "importoSingoloVersamento" element
     */
    void xsetImportoSingoloVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto importoSingoloVersamento);
    
    /**
     * Gets the "commissioneCaricoPA" element
     */
    java.math.BigDecimal getCommissioneCaricoPA();
    
    /**
     * Gets (as xml) the "commissioneCaricoPA" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetCommissioneCaricoPA();
    
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
    void xsetCommissioneCaricoPA(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto commissioneCaricoPA);
    
    /**
     * Unsets the "commissioneCaricoPA" element
     */
    void unsetCommissioneCaricoPA();
    
    /**
     * Gets the "identificativoTipoDovuto" element
     */
    java.lang.String getIdentificativoTipoDovuto();
    
    /**
     * Gets (as xml) the "identificativoTipoDovuto" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoTipoDovuto();
    
    /**
     * True if has "identificativoTipoDovuto" element
     */
    boolean isSetIdentificativoTipoDovuto();
    
    /**
     * Sets the "identificativoTipoDovuto" element
     */
    void setIdentificativoTipoDovuto(java.lang.String identificativoTipoDovuto);
    
    /**
     * Sets (as xml) the "identificativoTipoDovuto" element
     */
    void xsetIdentificativoTipoDovuto(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoTipoDovuto);
    
    /**
     * Unsets the "identificativoTipoDovuto" element
     */
    void unsetIdentificativoTipoDovuto();
    
    /**
     * Gets the "causaleVersamento" element
     */
    java.lang.String getCausaleVersamento();
    
    /**
     * Gets (as xml) the "causaleVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 xgetCausaleVersamento();
    
    /**
     * True if has "causaleVersamento" element
     */
    boolean isSetCausaleVersamento();
    
    /**
     * Sets the "causaleVersamento" element
     */
    void setCausaleVersamento(java.lang.String causaleVersamento);
    
    /**
     * Sets (as xml) the "causaleVersamento" element
     */
    void xsetCausaleVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText1024 causaleVersamento);
    
    /**
     * Unsets the "causaleVersamento" element
     */
    void unsetCausaleVersamento();
    
    /**
     * Gets the "datiSpecificiRiscossione" element
     */
    java.lang.String getDatiSpecificiRiscossione();
    
    /**
     * Gets (as xml) the "datiSpecificiRiscossione" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione xgetDatiSpecificiRiscossione();
    
    /**
     * True if has "datiSpecificiRiscossione" element
     */
    boolean isSetDatiSpecificiRiscossione();
    
    /**
     * Sets the "datiSpecificiRiscossione" element
     */
    void setDatiSpecificiRiscossione(java.lang.String datiSpecificiRiscossione);
    
    /**
     * Sets (as xml) the "datiSpecificiRiscossione" element
     */
    void xsetDatiSpecificiRiscossione(it.veneto.regione.schemas.x2012.pagamenti.ente.StDatiSpecificiRiscossione datiSpecificiRiscossione);
    
    /**
     * Unsets the "datiSpecificiRiscossione" element
     */
    void unsetDatiSpecificiRiscossione();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
