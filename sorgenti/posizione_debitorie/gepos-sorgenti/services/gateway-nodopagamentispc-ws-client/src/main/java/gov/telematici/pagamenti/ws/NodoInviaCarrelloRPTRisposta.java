/**
 * NodoInviaCarrelloRPTRisposta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoInviaCarrelloRPTRisposta  extends gov.telematici.pagamenti.ws.Risposta  implements java.io.Serializable {
    private java.lang.String esitoComplessivoOperazione;

    private java.lang.String url;

    private gov.telematici.pagamenti.ws.ListaErroriRPT listaErroriRPT;

    public NodoInviaCarrelloRPTRisposta() {
    }

    public NodoInviaCarrelloRPTRisposta(
           gov.telematici.pagamenti.ws.FaultBean fault,
           java.lang.String esitoComplessivoOperazione,
           java.lang.String url,
           gov.telematici.pagamenti.ws.ListaErroriRPT listaErroriRPT) {
        super(
            fault);
        this.esitoComplessivoOperazione = esitoComplessivoOperazione;
        this.url = url;
        this.listaErroriRPT = listaErroriRPT;
    }


    /**
     * Gets the esitoComplessivoOperazione value for this NodoInviaCarrelloRPTRisposta.
     * 
     * @return esitoComplessivoOperazione
     */
    public java.lang.String getEsitoComplessivoOperazione() {
        return esitoComplessivoOperazione;
    }


    /**
     * Sets the esitoComplessivoOperazione value for this NodoInviaCarrelloRPTRisposta.
     * 
     * @param esitoComplessivoOperazione
     */
    public void setEsitoComplessivoOperazione(java.lang.String esitoComplessivoOperazione) {
        this.esitoComplessivoOperazione = esitoComplessivoOperazione;
    }


    /**
     * Gets the url value for this NodoInviaCarrelloRPTRisposta.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this NodoInviaCarrelloRPTRisposta.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }


    /**
     * Gets the listaErroriRPT value for this NodoInviaCarrelloRPTRisposta.
     * 
     * @return listaErroriRPT
     */
    public gov.telematici.pagamenti.ws.ListaErroriRPT getListaErroriRPT() {
        return listaErroriRPT;
    }


    /**
     * Sets the listaErroriRPT value for this NodoInviaCarrelloRPTRisposta.
     * 
     * @param listaErroriRPT
     */
    public void setListaErroriRPT(gov.telematici.pagamenti.ws.ListaErroriRPT listaErroriRPT) {
        this.listaErroriRPT = listaErroriRPT;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoInviaCarrelloRPTRisposta)) return false;
        NodoInviaCarrelloRPTRisposta other = (NodoInviaCarrelloRPTRisposta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.esitoComplessivoOperazione==null && other.getEsitoComplessivoOperazione()==null) || 
             (this.esitoComplessivoOperazione!=null &&
              this.esitoComplessivoOperazione.equals(other.getEsitoComplessivoOperazione()))) &&
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl()))) &&
            ((this.listaErroriRPT==null && other.getListaErroriRPT()==null) || 
             (this.listaErroriRPT!=null &&
              this.listaErroriRPT.equals(other.getListaErroriRPT())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getEsitoComplessivoOperazione() != null) {
            _hashCode += getEsitoComplessivoOperazione().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        if (getListaErroriRPT() != null) {
            _hashCode += getListaErroriRPT().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoInviaCarrelloRPTRisposta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaCarrelloRPTRisposta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esitoComplessivoOperazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esitoComplessivoOperazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaErroriRPT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaErroriRPT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "listaErroriRPT"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
