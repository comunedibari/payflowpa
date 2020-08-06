/**
 * UtenteType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.tasgroup.iris.comunication.ws.impl;

public class UtenteType  implements java.io.Serializable {
    private java.lang.String id_utente;

    private boolean is_anonymous;

    private java.lang.String scopo_messaggio;

    public UtenteType() {
    }

    public UtenteType(
           java.lang.String id_utente,
           boolean is_anonymous,
           java.lang.String scopo_messaggio) {
           this.id_utente = id_utente;
           this.is_anonymous = is_anonymous;
           this.scopo_messaggio = scopo_messaggio;
    }


    /**
     * Gets the id_utente value for this UtenteType.
     * 
     * @return id_utente
     */
    public java.lang.String getId_utente() {
        return id_utente;
    }


    /**
     * Sets the id_utente value for this UtenteType.
     * 
     * @param id_utente
     */
    public void setId_utente(java.lang.String id_utente) {
        this.id_utente = id_utente;
    }


    /**
     * Gets the is_anonymous value for this UtenteType.
     * 
     * @return is_anonymous
     */
    public boolean isIs_anonymous() {
        return is_anonymous;
    }


    /**
     * Sets the is_anonymous value for this UtenteType.
     * 
     * @param is_anonymous
     */
    public void setIs_anonymous(boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }


    /**
     * Gets the scopo_messaggio value for this UtenteType.
     * 
     * @return scopo_messaggio
     */
    public java.lang.String getScopo_messaggio() {
        return scopo_messaggio;
    }


    /**
     * Sets the scopo_messaggio value for this UtenteType.
     * 
     * @param scopo_messaggio
     */
    public void setScopo_messaggio(java.lang.String scopo_messaggio) {
        this.scopo_messaggio = scopo_messaggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtenteType)) return false;
        UtenteType other = (UtenteType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id_utente==null && other.getId_utente()==null) || 
             (this.id_utente!=null &&
              this.id_utente.equals(other.getId_utente()))) &&
            this.is_anonymous == other.isIs_anonymous() &&
            ((this.scopo_messaggio==null && other.getScopo_messaggio()==null) || 
             (this.scopo_messaggio!=null &&
              this.scopo_messaggio.equals(other.getScopo_messaggio())));
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
        if (getId_utente() != null) {
            _hashCode += getId_utente().hashCode();
        }
        _hashCode += (isIs_anonymous() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getScopo_messaggio() != null) {
            _hashCode += getScopo_messaggio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtenteType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_utente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id_utente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("is_anonymous");
        elemField.setXmlName(new javax.xml.namespace.QName("", "is_anonymous"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scopo_messaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scopo_messaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
