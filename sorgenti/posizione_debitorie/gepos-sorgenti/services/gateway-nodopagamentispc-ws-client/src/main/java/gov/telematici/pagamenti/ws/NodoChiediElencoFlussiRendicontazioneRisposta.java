/**
 * NodoChiediElencoFlussiRendicontazioneRisposta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoChiediElencoFlussiRendicontazioneRisposta  extends gov.telematici.pagamenti.ws.Risposta  implements java.io.Serializable {
    private gov.telematici.pagamenti.ws.TipoElencoFlussiRendicontazione elencoFlussiRendicontazione;

    public NodoChiediElencoFlussiRendicontazioneRisposta() {
    }

    public NodoChiediElencoFlussiRendicontazioneRisposta(
           gov.telematici.pagamenti.ws.FaultBean fault,
           gov.telematici.pagamenti.ws.TipoElencoFlussiRendicontazione elencoFlussiRendicontazione) {
        super(
            fault);
        this.elencoFlussiRendicontazione = elencoFlussiRendicontazione;
    }


    /**
     * Gets the elencoFlussiRendicontazione value for this NodoChiediElencoFlussiRendicontazioneRisposta.
     * 
     * @return elencoFlussiRendicontazione
     */
    public gov.telematici.pagamenti.ws.TipoElencoFlussiRendicontazione getElencoFlussiRendicontazione() {
        return elencoFlussiRendicontazione;
    }


    /**
     * Sets the elencoFlussiRendicontazione value for this NodoChiediElencoFlussiRendicontazioneRisposta.
     * 
     * @param elencoFlussiRendicontazione
     */
    public void setElencoFlussiRendicontazione(gov.telematici.pagamenti.ws.TipoElencoFlussiRendicontazione elencoFlussiRendicontazione) {
        this.elencoFlussiRendicontazione = elencoFlussiRendicontazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoChiediElencoFlussiRendicontazioneRisposta)) return false;
        NodoChiediElencoFlussiRendicontazioneRisposta other = (NodoChiediElencoFlussiRendicontazioneRisposta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.elencoFlussiRendicontazione==null && other.getElencoFlussiRendicontazione()==null) || 
             (this.elencoFlussiRendicontazione!=null &&
              this.elencoFlussiRendicontazione.equals(other.getElencoFlussiRendicontazione())));
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
        if (getElencoFlussiRendicontazione() != null) {
            _hashCode += getElencoFlussiRendicontazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoChiediElencoFlussiRendicontazioneRisposta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoFlussiRendicontazioneRisposta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoFlussiRendicontazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elencoFlussiRendicontazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoElencoFlussiRendicontazione"));
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
