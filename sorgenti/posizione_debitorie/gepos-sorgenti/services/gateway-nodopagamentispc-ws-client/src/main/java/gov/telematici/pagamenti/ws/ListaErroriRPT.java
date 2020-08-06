/**
 * ListaErroriRPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class ListaErroriRPT  implements java.io.Serializable {
    private gov.telematici.pagamenti.ws.FaultBean[] fault;

    public ListaErroriRPT() {
    }

    public ListaErroriRPT(
           gov.telematici.pagamenti.ws.FaultBean[] fault) {
           this.fault = fault;
    }


    /**
     * Gets the fault value for this ListaErroriRPT.
     * 
     * @return fault
     */
    public gov.telematici.pagamenti.ws.FaultBean[] getFault() {
        return fault;
    }


    /**
     * Sets the fault value for this ListaErroriRPT.
     * 
     * @param fault
     */
    public void setFault(gov.telematici.pagamenti.ws.FaultBean[] fault) {
        this.fault = fault;
    }

    public gov.telematici.pagamenti.ws.FaultBean getFault(int i) {
        return this.fault[i];
    }

    public void setFault(int i, gov.telematici.pagamenti.ws.FaultBean _value) {
        this.fault[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaErroriRPT)) return false;
        ListaErroriRPT other = (ListaErroriRPT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fault==null && other.getFault()==null) || 
             (this.fault!=null &&
              java.util.Arrays.equals(this.fault, other.getFault())));
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
        if (getFault() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFault());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFault(), i);
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
        new org.apache.axis.description.TypeDesc(ListaErroriRPT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "listaErroriRPT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fault");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fault"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "faultBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
