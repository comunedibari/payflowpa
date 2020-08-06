/**
 * NodoChiediQuadraturaPARisposta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoChiediQuadraturaPARisposta  extends gov.telematici.pagamenti.ws.Risposta  implements java.io.Serializable {
    private byte[] xmlQuadratura;

    public NodoChiediQuadraturaPARisposta() {
    }

    public NodoChiediQuadraturaPARisposta(
           gov.telematici.pagamenti.ws.FaultBean fault,
           byte[] xmlQuadratura) {
        super(
            fault);
        this.xmlQuadratura = xmlQuadratura;
    }


    /**
     * Gets the xmlQuadratura value for this NodoChiediQuadraturaPARisposta.
     * 
     * @return xmlQuadratura
     */
    public byte[] getXmlQuadratura() {
        return xmlQuadratura;
    }


    /**
     * Sets the xmlQuadratura value for this NodoChiediQuadraturaPARisposta.
     * 
     * @param xmlQuadratura
     */
    public void setXmlQuadratura(byte[] xmlQuadratura) {
        this.xmlQuadratura = xmlQuadratura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoChiediQuadraturaPARisposta)) return false;
        NodoChiediQuadraturaPARisposta other = (NodoChiediQuadraturaPARisposta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.xmlQuadratura==null && other.getXmlQuadratura()==null) || 
             (this.xmlQuadratura!=null &&
              java.util.Arrays.equals(this.xmlQuadratura, other.getXmlQuadratura())));
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
        if (getXmlQuadratura() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getXmlQuadratura());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getXmlQuadratura(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoChiediQuadraturaPARisposta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediQuadraturaPARisposta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlQuadratura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xmlQuadratura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
