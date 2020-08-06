/*
 * XML Type:  ctDatiVersamentoRT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti;


/**
 * An XML ctDatiVersamentoRT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public interface CtDatiVersamentoRT extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiVersamentoRT.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41F9FA6057C7193A26B79565BC14C042").resolveHandle("ctdativersamentort7286type");
    
    /**
     * Gets the "codiceEsitoPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento.Enum getCodiceEsitoPagamento();
    
    /**
     * Gets (as xml) the "codiceEsitoPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento xgetCodiceEsitoPagamento();
    
    /**
     * Sets the "codiceEsitoPagamento" element
     */
    void setCodiceEsitoPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento.Enum codiceEsitoPagamento);
    
    /**
     * Sets (as xml) the "codiceEsitoPagamento" element
     */
    void xsetCodiceEsitoPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StCodiceEsitoPagamento codiceEsitoPagamento);
    
    /**
     * Gets the "importoTotalePagato" element
     */
    java.math.BigDecimal getImportoTotalePagato();
    
    /**
     * Gets (as xml) the "importoTotalePagato" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StImporto xgetImportoTotalePagato();
    
    /**
     * Sets the "importoTotalePagato" element
     */
    void setImportoTotalePagato(java.math.BigDecimal importoTotalePagato);
    
    /**
     * Sets (as xml) the "importoTotalePagato" element
     */
    void xsetImportoTotalePagato(it.gov.digitpa.schemas.x2011.pagamenti.StImporto importoTotalePagato);
    
    /**
     * Gets the "identificativoUnivocoVersamento" element
     */
    java.lang.String getIdentificativoUnivocoVersamento();
    
    /**
     * Gets (as xml) the "identificativoUnivocoVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetIdentificativoUnivocoVersamento();
    
    /**
     * Sets the "identificativoUnivocoVersamento" element
     */
    void setIdentificativoUnivocoVersamento(java.lang.String identificativoUnivocoVersamento);
    
    /**
     * Sets (as xml) the "identificativoUnivocoVersamento" element
     */
    void xsetIdentificativoUnivocoVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StText35 identificativoUnivocoVersamento);
    
    /**
     * Gets the "CodiceContestoPagamento" element
     */
    java.lang.String getCodiceContestoPagamento();
    
    /**
     * Gets (as xml) the "CodiceContestoPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetCodiceContestoPagamento();
    
    /**
     * Sets the "CodiceContestoPagamento" element
     */
    void setCodiceContestoPagamento(java.lang.String codiceContestoPagamento);
    
    /**
     * Sets (as xml) the "CodiceContestoPagamento" element
     */
    void xsetCodiceContestoPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StText35 codiceContestoPagamento);
    
    /**
     * Gets array of all "datiSingoloPagamento" elements
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT[] getDatiSingoloPagamentoArray();
    
    /**
     * Gets ith "datiSingoloPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT getDatiSingoloPagamentoArray(int i);
    
    /**
     * Returns number of "datiSingoloPagamento" element
     */
    int sizeOfDatiSingoloPagamentoArray();
    
    /**
     * Sets array of all "datiSingoloPagamento" element
     */
    void setDatiSingoloPagamentoArray(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT[] datiSingoloPagamentoArray);
    
    /**
     * Sets ith "datiSingoloPagamento" element
     */
    void setDatiSingoloPagamentoArray(int i, it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT datiSingoloPagamento);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "datiSingoloPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT insertNewDatiSingoloPagamento(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "datiSingoloPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT addNewDatiSingoloPagamento();
    
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
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT newInstance() {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
