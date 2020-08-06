/**
 * TipoListaQuadrature.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class TipoListaQuadrature  implements java.io.Serializable {
    private int totRestituiti;

    private gov.telematici.pagamenti.ws.TipoIdQuadratura[] idQuadratura;

    public TipoListaQuadrature() {
    }

    public TipoListaQuadrature(
           int totRestituiti,
           gov.telematici.pagamenti.ws.TipoIdQuadratura[] idQuadratura) {
           this.totRestituiti = totRestituiti;
           this.idQuadratura = idQuadratura;
    }


    /**
     * Gets the totRestituiti value for this TipoListaQuadrature.
     * 
     * @return totRestituiti
     */
    public int getTotRestituiti() {
        return totRestituiti;
    }


    /**
     * Sets the totRestituiti value for this TipoListaQuadrature.
     * 
     * @param totRestituiti
     */
    public void setTotRestituiti(int totRestituiti) {
        this.totRestituiti = totRestituiti;
    }


    /**
     * Gets the idQuadratura value for this TipoListaQuadrature.
     * 
     * @return idQuadratura
     */
    public gov.telematici.pagamenti.ws.TipoIdQuadratura[] getIdQuadratura() {
        return idQuadratura;
    }


    /**
     * Sets the idQuadratura value for this TipoListaQuadrature.
     * 
     * @param idQuadratura
     */
    public void setIdQuadratura(gov.telematici.pagamenti.ws.TipoIdQuadratura[] idQuadratura) {
        this.idQuadratura = idQuadratura;
    }

    public gov.telematici.pagamenti.ws.TipoIdQuadratura getIdQuadratura(int i) {
        return this.idQuadratura[i];
    }

    public void setIdQuadratura(int i, gov.telematici.pagamenti.ws.TipoIdQuadratura _value) {
        this.idQuadratura[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoListaQuadrature)) return false;
        TipoListaQuadrature other = (TipoListaQuadrature) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.totRestituiti == other.getTotRestituiti() &&
            ((this.idQuadratura==null && other.getIdQuadratura()==null) || 
             (this.idQuadratura!=null &&
              java.util.Arrays.equals(this.idQuadratura, other.getIdQuadratura())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getTotRestituiti();
        if (getIdQuadratura() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIdQuadratura());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIdQuadratura(), i);
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
        new org.apache.axis.description.TypeDesc(TipoListaQuadrature.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoListaQuadrature"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totRestituiti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totRestituiti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idQuadratura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idQuadratura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoIdQuadratura"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
