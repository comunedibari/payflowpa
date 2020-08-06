/*
 * XML Type:  ctDatiVersamentoPagati
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente;


/**
 * An XML ctDatiVersamentoPagati(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public interface CtDatiVersamentoPagati extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiVersamentoPagati.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctdativersamentopagati18dctype");
    
    /**
     * Gets the "codiceEsitoPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento.Enum getCodiceEsitoPagamento();
    
    /**
     * Gets (as xml) the "codiceEsitoPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento xgetCodiceEsitoPagamento();
    
    /**
     * Sets the "codiceEsitoPagamento" element
     */
    void setCodiceEsitoPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento.Enum codiceEsitoPagamento);
    
    /**
     * Sets (as xml) the "codiceEsitoPagamento" element
     */
    void xsetCodiceEsitoPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StCodiceEsitoPagamento codiceEsitoPagamento);
    
    /**
     * Gets the "importoTotalePagato" element
     */
    java.math.BigDecimal getImportoTotalePagato();
    
    /**
     * Gets (as xml) the "importoTotalePagato" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto xgetImportoTotalePagato();
    
    /**
     * Sets the "importoTotalePagato" element
     */
    void setImportoTotalePagato(java.math.BigDecimal importoTotalePagato);
    
    /**
     * Sets (as xml) the "importoTotalePagato" element
     */
    void xsetImportoTotalePagato(it.veneto.regione.schemas.x2012.pagamenti.ente.StImporto importoTotalePagato);
    
    /**
     * Gets the "identificativoUnivocoVersamento" element
     */
    java.lang.String getIdentificativoUnivocoVersamento();
    
    /**
     * Gets (as xml) the "identificativoUnivocoVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetIdentificativoUnivocoVersamento();
    
    /**
     * Sets the "identificativoUnivocoVersamento" element
     */
    void setIdentificativoUnivocoVersamento(java.lang.String identificativoUnivocoVersamento);
    
    /**
     * Sets (as xml) the "identificativoUnivocoVersamento" element
     */
    void xsetIdentificativoUnivocoVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 identificativoUnivocoVersamento);
    
    /**
     * Gets the "codiceContestoPagamento" element
     */
    java.lang.String getCodiceContestoPagamento();
    
    /**
     * Gets (as xml) the "codiceContestoPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 xgetCodiceContestoPagamento();
    
    /**
     * Sets the "codiceContestoPagamento" element
     */
    void setCodiceContestoPagamento(java.lang.String codiceContestoPagamento);
    
    /**
     * Sets (as xml) the "codiceContestoPagamento" element
     */
    void xsetCodiceContestoPagamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText35 codiceContestoPagamento);
    
    /**
     * Gets array of all "datiSingoloPagamento" elements
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati[] getDatiSingoloPagamentoArray();
    
    /**
     * Gets ith "datiSingoloPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati getDatiSingoloPagamentoArray(int i);
    
    /**
     * Returns number of "datiSingoloPagamento" element
     */
    int sizeOfDatiSingoloPagamentoArray();
    
    /**
     * Sets array of all "datiSingoloPagamento" element
     */
    void setDatiSingoloPagamentoArray(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati[] datiSingoloPagamentoArray);
    
    /**
     * Sets ith "datiSingoloPagamento" element
     */
    void setDatiSingoloPagamentoArray(int i, it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati datiSingoloPagamento);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "datiSingoloPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati insertNewDatiSingoloPagamento(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "datiSingoloPagamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloPagamentoPagati addNewDatiSingoloPagamento();
    
    /**
     * Removes the ith "datiSingoloPagamento" element
     */
    void removeDatiSingoloPagamento(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoPagati) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
