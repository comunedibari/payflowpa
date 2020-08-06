/**
 * TipoListaRPTPendenti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class TipoListaRPTPendenti  implements java.io.Serializable {
    private int totRestituiti;

    private gov.telematici.pagamenti.ws.TipoRPTPendente[] rptPendente;

    public TipoListaRPTPendenti() {
    }

    public TipoListaRPTPendenti(
           int totRestituiti,
           gov.telematici.pagamenti.ws.TipoRPTPendente[] rptPendente) {
           this.totRestituiti = totRestituiti;
           this.rptPendente = rptPendente;
    }


    /**
     * Gets the totRestituiti value for this TipoListaRPTPendenti.
     * 
     * @return totRestituiti
     */
    public int getTotRestituiti() {
        return totRestituiti;
    }


    /**
     * Sets the totRestituiti value for this TipoListaRPTPendenti.
     * 
     * @param totRestituiti
     */
    public void setTotRestituiti(int totRestituiti) {
        this.totRestituiti = totRestituiti;
    }


    /**
     * Gets the rptPendente value for this TipoListaRPTPendenti.
     * 
     * @return rptPendente
     */
    public gov.telematici.pagamenti.ws.TipoRPTPendente[] getRptPendente() {
        return rptPendente;
    }


    /**
     * Sets the rptPendente value for this TipoListaRPTPendenti.
     * 
     * @param rptPendente
     */
    public void setRptPendente(gov.telematici.pagamenti.ws.TipoRPTPendente[] rptPendente) {
        this.rptPendente = rptPendente;
    }

    public gov.telematici.pagamenti.ws.TipoRPTPendente getRptPendente(int i) {
        return this.rptPendente[i];
    }

    public void setRptPendente(int i, gov.telematici.pagamenti.ws.TipoRPTPendente _value) {
        this.rptPendente[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoListaRPTPendenti)) return false;
        TipoListaRPTPendenti other = (TipoListaRPTPendenti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.totRestituiti == other.getTotRestituiti() &&
            ((this.rptPendente==null && other.getRptPendente()==null) || 
             (this.rptPendente!=null &&
              java.util.Arrays.equals(this.rptPendente, other.getRptPendente())));
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
        if (getRptPendente() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRptPendente());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRptPendente(), i);
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
        new org.apache.axis.description.TypeDesc(TipoListaRPTPendenti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoListaRPTPendenti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totRestituiti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totRestituiti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rptPendente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rptPendente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoRPTPendente"));
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
