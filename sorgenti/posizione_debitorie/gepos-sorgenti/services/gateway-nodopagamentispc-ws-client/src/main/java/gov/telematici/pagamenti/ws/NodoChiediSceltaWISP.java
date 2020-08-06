/**
 * NodoChiediSceltaWISP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoChiediSceltaWISP  implements java.io.Serializable {
    private java.lang.String identificativoIntermediarioPA;

    private java.lang.String identificativoStazioneIntermediarioPA;

    private java.lang.String password;

    private java.lang.String identificativoDominio;

    private java.lang.String keyPA;

    private java.lang.String keyWISP;

    public NodoChiediSceltaWISP() {
    }

    public NodoChiediSceltaWISP(
           java.lang.String identificativoIntermediarioPA,
           java.lang.String identificativoStazioneIntermediarioPA,
           java.lang.String password,
           java.lang.String identificativoDominio,
           java.lang.String keyPA,
           java.lang.String keyWISP) {
           this.identificativoIntermediarioPA = identificativoIntermediarioPA;
           this.identificativoStazioneIntermediarioPA = identificativoStazioneIntermediarioPA;
           this.password = password;
           this.identificativoDominio = identificativoDominio;
           this.keyPA = keyPA;
           this.keyWISP = keyWISP;
    }


    /**
     * Gets the identificativoIntermediarioPA value for this NodoChiediSceltaWISP.
     * 
     * @return identificativoIntermediarioPA
     */
    public java.lang.String getIdentificativoIntermediarioPA() {
        return identificativoIntermediarioPA;
    }


    /**
     * Sets the identificativoIntermediarioPA value for this NodoChiediSceltaWISP.
     * 
     * @param identificativoIntermediarioPA
     */
    public void setIdentificativoIntermediarioPA(java.lang.String identificativoIntermediarioPA) {
        this.identificativoIntermediarioPA = identificativoIntermediarioPA;
    }


    /**
     * Gets the identificativoStazioneIntermediarioPA value for this NodoChiediSceltaWISP.
     * 
     * @return identificativoStazioneIntermediarioPA
     */
    public java.lang.String getIdentificativoStazioneIntermediarioPA() {
        return identificativoStazioneIntermediarioPA;
    }


    /**
     * Sets the identificativoStazioneIntermediarioPA value for this NodoChiediSceltaWISP.
     * 
     * @param identificativoStazioneIntermediarioPA
     */
    public void setIdentificativoStazioneIntermediarioPA(java.lang.String identificativoStazioneIntermediarioPA) {
        this.identificativoStazioneIntermediarioPA = identificativoStazioneIntermediarioPA;
    }


    /**
     * Gets the password value for this NodoChiediSceltaWISP.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this NodoChiediSceltaWISP.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the identificativoDominio value for this NodoChiediSceltaWISP.
     * 
     * @return identificativoDominio
     */
    public java.lang.String getIdentificativoDominio() {
        return identificativoDominio;
    }


    /**
     * Sets the identificativoDominio value for this NodoChiediSceltaWISP.
     * 
     * @param identificativoDominio
     */
    public void setIdentificativoDominio(java.lang.String identificativoDominio) {
        this.identificativoDominio = identificativoDominio;
    }


    /**
     * Gets the keyPA value for this NodoChiediSceltaWISP.
     * 
     * @return keyPA
     */
    public java.lang.String getKeyPA() {
        return keyPA;
    }


    /**
     * Sets the keyPA value for this NodoChiediSceltaWISP.
     * 
     * @param keyPA
     */
    public void setKeyPA(java.lang.String keyPA) {
        this.keyPA = keyPA;
    }


    /**
     * Gets the keyWISP value for this NodoChiediSceltaWISP.
     * 
     * @return keyWISP
     */
    public java.lang.String getKeyWISP() {
        return keyWISP;
    }


    /**
     * Sets the keyWISP value for this NodoChiediSceltaWISP.
     * 
     * @param keyWISP
     */
    public void setKeyWISP(java.lang.String keyWISP) {
        this.keyWISP = keyWISP;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoChiediSceltaWISP)) return false;
        NodoChiediSceltaWISP other = (NodoChiediSceltaWISP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identificativoIntermediarioPA==null && other.getIdentificativoIntermediarioPA()==null) || 
             (this.identificativoIntermediarioPA!=null &&
              this.identificativoIntermediarioPA.equals(other.getIdentificativoIntermediarioPA()))) &&
            ((this.identificativoStazioneIntermediarioPA==null && other.getIdentificativoStazioneIntermediarioPA()==null) || 
             (this.identificativoStazioneIntermediarioPA!=null &&
              this.identificativoStazioneIntermediarioPA.equals(other.getIdentificativoStazioneIntermediarioPA()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.identificativoDominio==null && other.getIdentificativoDominio()==null) || 
             (this.identificativoDominio!=null &&
              this.identificativoDominio.equals(other.getIdentificativoDominio()))) &&
            ((this.keyPA==null && other.getKeyPA()==null) || 
             (this.keyPA!=null &&
              this.keyPA.equals(other.getKeyPA()))) &&
            ((this.keyWISP==null && other.getKeyWISP()==null) || 
             (this.keyWISP!=null &&
              this.keyWISP.equals(other.getKeyWISP())));
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
        if (getIdentificativoIntermediarioPA() != null) {
            _hashCode += getIdentificativoIntermediarioPA().hashCode();
        }
        if (getIdentificativoStazioneIntermediarioPA() != null) {
            _hashCode += getIdentificativoStazioneIntermediarioPA().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getIdentificativoDominio() != null) {
            _hashCode += getIdentificativoDominio().hashCode();
        }
        if (getKeyPA() != null) {
            _hashCode += getKeyPA().hashCode();
        }
        if (getKeyWISP() != null) {
            _hashCode += getKeyWISP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoChiediSceltaWISP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediSceltaWISP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoIntermediarioPA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoIntermediarioPA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoStazioneIntermediarioPA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoStazioneIntermediarioPA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoDominio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoDominio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keyPA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "keyPA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keyWISP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "keyWISP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
