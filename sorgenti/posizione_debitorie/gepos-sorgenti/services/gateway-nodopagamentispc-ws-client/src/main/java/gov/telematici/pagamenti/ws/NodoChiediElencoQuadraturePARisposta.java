/**
 * NodoChiediElencoQuadraturePARisposta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoChiediElencoQuadraturePARisposta  extends gov.telematici.pagamenti.ws.Risposta  implements java.io.Serializable {
    private gov.telematici.pagamenti.ws.TipoListaQuadrature listaQuadrature;

    public NodoChiediElencoQuadraturePARisposta() {
    }

    public NodoChiediElencoQuadraturePARisposta(
           gov.telematici.pagamenti.ws.FaultBean fault,
           gov.telematici.pagamenti.ws.TipoListaQuadrature listaQuadrature) {
        super(
            fault);
        this.listaQuadrature = listaQuadrature;
    }


    /**
     * Gets the listaQuadrature value for this NodoChiediElencoQuadraturePARisposta.
     * 
     * @return listaQuadrature
     */
    public gov.telematici.pagamenti.ws.TipoListaQuadrature getListaQuadrature() {
        return listaQuadrature;
    }


    /**
     * Sets the listaQuadrature value for this NodoChiediElencoQuadraturePARisposta.
     * 
     * @param listaQuadrature
     */
    public void setListaQuadrature(gov.telematici.pagamenti.ws.TipoListaQuadrature listaQuadrature) {
        this.listaQuadrature = listaQuadrature;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoChiediElencoQuadraturePARisposta)) return false;
        NodoChiediElencoQuadraturePARisposta other = (NodoChiediElencoQuadraturePARisposta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.listaQuadrature==null && other.getListaQuadrature()==null) || 
             (this.listaQuadrature!=null &&
              this.listaQuadrature.equals(other.getListaQuadrature())));
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
        if (getListaQuadrature() != null) {
            _hashCode += getListaQuadrature().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoChiediElencoQuadraturePARisposta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoQuadraturePARisposta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaQuadrature");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaQuadrature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoListaQuadrature"));
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
