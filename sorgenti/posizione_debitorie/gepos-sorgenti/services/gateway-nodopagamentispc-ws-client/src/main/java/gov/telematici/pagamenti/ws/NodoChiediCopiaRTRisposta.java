/**
 * NodoChiediCopiaRTRisposta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoChiediCopiaRTRisposta  extends gov.telematici.pagamenti.ws.Risposta  implements java.io.Serializable {
    private java.lang.String tipoFirma;

    private byte[] rt;

    public NodoChiediCopiaRTRisposta() {
    }

    public NodoChiediCopiaRTRisposta(
           gov.telematici.pagamenti.ws.FaultBean fault,
           java.lang.String tipoFirma,
           byte[] rt) {
        super(
            fault);
        this.tipoFirma = tipoFirma;
        this.rt = rt;
    }


    /**
     * Gets the tipoFirma value for this NodoChiediCopiaRTRisposta.
     * 
     * @return tipoFirma
     */
    public java.lang.String getTipoFirma() {
        return tipoFirma;
    }


    /**
     * Sets the tipoFirma value for this NodoChiediCopiaRTRisposta.
     * 
     * @param tipoFirma
     */
    public void setTipoFirma(java.lang.String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }


    /**
     * Gets the rt value for this NodoChiediCopiaRTRisposta.
     * 
     * @return rt
     */
    public byte[] getRt() {
        return rt;
    }


    /**
     * Sets the rt value for this NodoChiediCopiaRTRisposta.
     * 
     * @param rt
     */
    public void setRt(byte[] rt) {
        this.rt = rt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoChiediCopiaRTRisposta)) return false;
        NodoChiediCopiaRTRisposta other = (NodoChiediCopiaRTRisposta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.tipoFirma==null && other.getTipoFirma()==null) || 
             (this.tipoFirma!=null &&
              this.tipoFirma.equals(other.getTipoFirma()))) &&
            ((this.rt==null && other.getRt()==null) || 
             (this.rt!=null &&
              java.util.Arrays.equals(this.rt, other.getRt())));
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
        if (getTipoFirma() != null) {
            _hashCode += getTipoFirma().hashCode();
        }
        if (getRt() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRt());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRt(), i);
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
        new org.apache.axis.description.TypeDesc(NodoChiediCopiaRTRisposta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediCopiaRTRisposta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFirma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFirma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rt"));
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
