/*
 * XML Type:  ctDatiVersamentoDovuti
 * Namespace: http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/
 * Java type: it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti
 *
 * Automatically generated - do not modify.
 */
package it.veneto.regione.schemas.x2012.pagamenti.ente;


/**
 * An XML ctDatiVersamentoDovuti(@http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/).
 *
 * This is a complex type.
 */
public interface CtDatiVersamentoDovuti extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiVersamentoDovuti.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0F51B12AC1CBE4913FDD884AB1BC8308").resolveHandle("ctdativersamentodovuti3a5btype");
    
    /**
     * Gets the "tipoVersamento" element
     */
    java.lang.String getTipoVersamento();
    
    /**
     * Gets (as xml) the "tipoVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.StText32 xgetTipoVersamento();
    
    /**
     * Sets the "tipoVersamento" element
     */
    void setTipoVersamento(java.lang.String tipoVersamento);
    
    /**
     * Sets (as xml) the "tipoVersamento" element
     */
    void xsetTipoVersamento(it.veneto.regione.schemas.x2012.pagamenti.ente.StText32 tipoVersamento);
    
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
     * Gets array of all "datiSingoloVersamento" elements
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti[] getDatiSingoloVersamentoArray();
    
    /**
     * Gets ith "datiSingoloVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti getDatiSingoloVersamentoArray(int i);
    
    /**
     * Returns number of "datiSingoloVersamento" element
     */
    int sizeOfDatiSingoloVersamentoArray();
    
    /**
     * Sets array of all "datiSingoloVersamento" element
     */
    void setDatiSingoloVersamentoArray(it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti[] datiSingoloVersamentoArray);
    
    /**
     * Sets ith "datiSingoloVersamento" element
     */
    void setDatiSingoloVersamentoArray(int i, it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti datiSingoloVersamento);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "datiSingoloVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti insertNewDatiSingoloVersamento(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "datiSingoloVersamento" element
     */
    it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiSingoloVersamentoDovuti addNewDatiSingoloVersamento();
    
    /**
     * Removes the ith "datiSingoloVersamento" element
     */
    void removeDatiSingoloVersamento(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti newInstance() {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.veneto.regione.schemas.x2012.pagamenti.ente.CtDatiVersamentoDovuti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
