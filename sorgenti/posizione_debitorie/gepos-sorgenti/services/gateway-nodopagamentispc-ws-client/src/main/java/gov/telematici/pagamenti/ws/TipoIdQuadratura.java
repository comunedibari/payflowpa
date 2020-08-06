/**
 * TipoIdQuadratura.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class TipoIdQuadratura  implements java.io.Serializable {
    private java.lang.String identificativoFlusso;

    private java.util.Calendar dataOraFlusso;

    public TipoIdQuadratura() {
    }

    public TipoIdQuadratura(
           java.lang.String identificativoFlusso,
           java.util.Calendar dataOraFlusso) {
           this.identificativoFlusso = identificativoFlusso;
           this.dataOraFlusso = dataOraFlusso;
    }


    /**
     * Gets the identificativoFlusso value for this TipoIdQuadratura.
     * 
     * @return identificativoFlusso
     */
    public java.lang.String getIdentificativoFlusso() {
        return identificativoFlusso;
    }


    /**
     * Sets the identificativoFlusso value for this TipoIdQuadratura.
     * 
     * @param identificativoFlusso
     */
    public void setIdentificativoFlusso(java.lang.String identificativoFlusso) {
        this.identificativoFlusso = identificativoFlusso;
    }


    /**
     * Gets the dataOraFlusso value for this TipoIdQuadratura.
     * 
     * @return dataOraFlusso
     */
    public java.util.Calendar getDataOraFlusso() {
        return dataOraFlusso;
    }


    /**
     * Sets the dataOraFlusso value for this TipoIdQuadratura.
     * 
     * @param dataOraFlusso
     */
    public void setDataOraFlusso(java.util.Calendar dataOraFlusso) {
        this.dataOraFlusso = dataOraFlusso;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoIdQuadratura)) return false;
        TipoIdQuadratura other = (TipoIdQuadratura) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identificativoFlusso==null && other.getIdentificativoFlusso()==null) || 
             (this.identificativoFlusso!=null &&
              this.identificativoFlusso.equals(other.getIdentificativoFlusso()))) &&
            ((this.dataOraFlusso==null && other.getDataOraFlusso()==null) || 
             (this.dataOraFlusso!=null &&
              this.dataOraFlusso.equals(other.getDataOraFlusso())));
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
        if (getIdentificativoFlusso() != null) {
            _hashCode += getIdentificativoFlusso().hashCode();
        }
        if (getDataOraFlusso() != null) {
            _hashCode += getDataOraFlusso().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoIdQuadratura.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoIdQuadratura"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoFlusso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoFlusso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataOraFlusso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataOraFlusso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
