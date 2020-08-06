/**
 * NodoChiediListaPendentiRPTRisposta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoChiediListaPendentiRPTRisposta  extends gov.telematici.pagamenti.ws.Risposta  implements java.io.Serializable {
    private gov.telematici.pagamenti.ws.TipoListaRPTPendenti listaRPTPendenti;

    public NodoChiediListaPendentiRPTRisposta() {
    }

    public NodoChiediListaPendentiRPTRisposta(
           gov.telematici.pagamenti.ws.FaultBean fault,
           gov.telematici.pagamenti.ws.TipoListaRPTPendenti listaRPTPendenti) {
        super(
            fault);
        this.listaRPTPendenti = listaRPTPendenti;
    }


    /**
     * Gets the listaRPTPendenti value for this NodoChiediListaPendentiRPTRisposta.
     * 
     * @return listaRPTPendenti
     */
    public gov.telematici.pagamenti.ws.TipoListaRPTPendenti getListaRPTPendenti() {
        return listaRPTPendenti;
    }


    /**
     * Sets the listaRPTPendenti value for this NodoChiediListaPendentiRPTRisposta.
     * 
     * @param listaRPTPendenti
     */
    public void setListaRPTPendenti(gov.telematici.pagamenti.ws.TipoListaRPTPendenti listaRPTPendenti) {
        this.listaRPTPendenti = listaRPTPendenti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoChiediListaPendentiRPTRisposta)) return false;
        NodoChiediListaPendentiRPTRisposta other = (NodoChiediListaPendentiRPTRisposta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.listaRPTPendenti==null && other.getListaRPTPendenti()==null) || 
             (this.listaRPTPendenti!=null &&
              this.listaRPTPendenti.equals(other.getListaRPTPendenti())));
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
        if (getListaRPTPendenti() != null) {
            _hashCode += getListaRPTPendenti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoChiediListaPendentiRPTRisposta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediListaPendentiRPTRisposta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaRPTPendenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaRPTPendenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoListaRPTPendenti"));
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
