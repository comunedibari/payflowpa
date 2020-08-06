/*
 * XML Type:  ctDatiVersamentoRPT
 * Namespace: http://www.digitpa.gov.it/schemas/2011/Pagamenti/
 * Java type: it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT
 *
 * Automatically generated - do not modify.
 */
package it.gov.digitpa.schemas.x2011.pagamenti;


/**
 * An XML ctDatiVersamentoRPT(@http://www.digitpa.gov.it/schemas/2011/Pagamenti/).
 *
 * This is a complex type.
 */
public interface CtDatiVersamentoRPT extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CtDatiVersamentoRPT.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s41F9FA6057C7193A26B79565BC14C042").resolveHandle("ctdativersamentorptcd02type");
    
    /**
     * Gets the "dataEsecuzionePagamento" element
     */
    java.util.Calendar getDataEsecuzionePagamento();
    
    /**
     * Gets (as xml) the "dataEsecuzionePagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StISODate xgetDataEsecuzionePagamento();
    
    /**
     * Sets the "dataEsecuzionePagamento" element
     */
    void setDataEsecuzionePagamento(java.util.Calendar dataEsecuzionePagamento);
    
    /**
     * Sets (as xml) the "dataEsecuzionePagamento" element
     */
    void xsetDataEsecuzionePagamento(it.gov.digitpa.schemas.x2011.pagamenti.StISODate dataEsecuzionePagamento);
    
    /**
     * Gets the "importoTotaleDaVersare" element
     */
    java.math.BigDecimal getImportoTotaleDaVersare();
    
    /**
     * Gets (as xml) the "importoTotaleDaVersare" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero xgetImportoTotaleDaVersare();
    
    /**
     * Sets the "importoTotaleDaVersare" element
     */
    void setImportoTotaleDaVersare(java.math.BigDecimal importoTotaleDaVersare);
    
    /**
     * Sets (as xml) the "importoTotaleDaVersare" element
     */
    void xsetImportoTotaleDaVersare(it.gov.digitpa.schemas.x2011.pagamenti.StImportoDiversoDaZero importoTotaleDaVersare);
    
    /**
     * Gets the "tipoVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento.Enum getTipoVersamento();
    
    /**
     * Gets (as xml) the "tipoVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento xgetTipoVersamento();
    
    /**
     * Sets the "tipoVersamento" element
     */
    void setTipoVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento.Enum tipoVersamento);
    
    /**
     * Sets (as xml) the "tipoVersamento" element
     */
    void xsetTipoVersamento(it.gov.digitpa.schemas.x2011.pagamenti.StTipoVersamento tipoVersamento);
    
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
     * Gets the "codiceContestoPagamento" element
     */
    java.lang.String getCodiceContestoPagamento();
    
    /**
     * Gets (as xml) the "codiceContestoPagamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StText35 xgetCodiceContestoPagamento();
    
    /**
     * Sets the "codiceContestoPagamento" element
     */
    void setCodiceContestoPagamento(java.lang.String codiceContestoPagamento);
    
    /**
     * Sets (as xml) the "codiceContestoPagamento" element
     */
    void xsetCodiceContestoPagamento(it.gov.digitpa.schemas.x2011.pagamenti.StText35 codiceContestoPagamento);
    
    /**
     * Gets the "ibanAddebito" element
     */
    java.lang.String getIbanAddebito();
    
    /**
     * Gets (as xml) the "ibanAddebito" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier xgetIbanAddebito();
    
    /**
     * True if has "ibanAddebito" element
     */
    boolean isSetIbanAddebito();
    
    /**
     * Sets the "ibanAddebito" element
     */
    void setIbanAddebito(java.lang.String ibanAddebito);
    
    /**
     * Sets (as xml) the "ibanAddebito" element
     */
    void xsetIbanAddebito(it.gov.digitpa.schemas.x2011.pagamenti.StIBANIdentifier ibanAddebito);
    
    /**
     * Unsets the "ibanAddebito" element
     */
    void unsetIbanAddebito();
    
    /**
     * Gets the "bicAddebito" element
     */
    java.lang.String getBicAddebito();
    
    /**
     * Gets (as xml) the "bicAddebito" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier xgetBicAddebito();
    
    /**
     * True if has "bicAddebito" element
     */
    boolean isSetBicAddebito();
    
    /**
     * Sets the "bicAddebito" element
     */
    void setBicAddebito(java.lang.String bicAddebito);
    
    /**
     * Sets (as xml) the "bicAddebito" element
     */
    void xsetBicAddebito(it.gov.digitpa.schemas.x2011.pagamenti.StBICIdentifier bicAddebito);
    
    /**
     * Unsets the "bicAddebito" element
     */
    void unsetBicAddebito();
    
    /**
     * Gets the "firmaRicevuta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta.Enum getFirmaRicevuta();
    
    /**
     * Gets (as xml) the "firmaRicevuta" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta xgetFirmaRicevuta();
    
    /**
     * Sets the "firmaRicevuta" element
     */
    void setFirmaRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta.Enum firmaRicevuta);
    
    /**
     * Sets (as xml) the "firmaRicevuta" element
     */
    void xsetFirmaRicevuta(it.gov.digitpa.schemas.x2011.pagamenti.StFirmaRicevuta firmaRicevuta);
    
    /**
     * Gets array of all "datiSingoloVersamento" elements
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT[] getDatiSingoloVersamentoArray();
    
    /**
     * Gets ith "datiSingoloVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT getDatiSingoloVersamentoArray(int i);
    
    /**
     * Returns number of "datiSingoloVersamento" element
     */
    int sizeOfDatiSingoloVersamentoArray();
    
    /**
     * Sets array of all "datiSingoloVersamento" element
     */
    void setDatiSingoloVersamentoArray(it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT[] datiSingoloVersamentoArray);
    
    /**
     * Sets ith "datiSingoloVersamento" element
     */
    void setDatiSingoloVersamentoArray(int i, it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT datiSingoloVersamento);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "datiSingoloVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT insertNewDatiSingoloVersamento(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "datiSingoloVersamento" element
     */
    it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloVersamentoRPT addNewDatiSingoloVersamento();
    
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
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT newInstance() {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRPT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
