/**
 * TipoElencoFlussiRendicontazione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class TipoElencoFlussiRendicontazione  implements java.io.Serializable {
    private int totRestituiti;

    private gov.telematici.pagamenti.ws.TipoIdRendicontazione[] idRendicontazione;

    public TipoElencoFlussiRendicontazione() {
    }

    public TipoElencoFlussiRendicontazione(
           int totRestituiti,
           gov.telematici.pagamenti.ws.TipoIdRendicontazione[] idRendicontazione) {
           this.totRestituiti = totRestituiti;
           this.idRendicontazione = idRendicontazione;
    }


    /**
     * Gets the totRestituiti value for this TipoElencoFlussiRendicontazione.
     * 
     * @return totRestituiti
     */
    public int getTotRestituiti() {
        return totRestituiti;
    }


    /**
     * Sets the totRestituiti value for this TipoElencoFlussiRendicontazione.
     * 
     * @param totRestituiti
     */
    public void setTotRestituiti(int totRestituiti) {
        this.totRestituiti = totRestituiti;
    }


    /**
     * Gets the idRendicontazione value for this TipoElencoFlussiRendicontazione.
     * 
     * @return idRendicontazione
     */
    public gov.telematici.pagamenti.ws.TipoIdRendicontazione[] getIdRendicontazione() {
        return idRendicontazione;
    }


    /**
     * Sets the idRendicontazione value for this TipoElencoFlussiRendicontazione.
     * 
     * @param idRendicontazione
     */
    public void setIdRendicontazione(gov.telematici.pagamenti.ws.TipoIdRendicontazione[] idRendicontazione) {
        this.idRendicontazione = idRendicontazione;
    }

    public gov.telematici.pagamenti.ws.TipoIdRendicontazione getIdRendicontazione(int i) {
        return this.idRendicontazione[i];
    }

    public void setIdRendicontazione(int i, gov.telematici.pagamenti.ws.TipoIdRendicontazione _value) {
        this.idRendicontazione[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoElencoFlussiRendicontazione)) return false;
        TipoElencoFlussiRendicontazione other = (TipoElencoFlussiRendicontazione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.totRestituiti == other.getTotRestituiti() &&
            ((this.idRendicontazione==null && other.getIdRendicontazione()==null) || 
             (this.idRendicontazione!=null &&
              java.util.Arrays.equals(this.idRendicontazione, other.getIdRendicontazione())));
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
        if (getIdRendicontazione() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIdRendicontazione());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIdRendicontazione(), i);
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
        new org.apache.axis.description.TypeDesc(TipoElencoFlussiRendicontazione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoElencoFlussiRendicontazione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totRestituiti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totRestituiti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRendicontazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRendicontazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoIdRendicontazione"));
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
