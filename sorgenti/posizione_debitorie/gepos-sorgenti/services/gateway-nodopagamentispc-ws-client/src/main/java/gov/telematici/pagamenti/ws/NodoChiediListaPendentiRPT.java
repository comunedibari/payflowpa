/**
 * NodoChiediListaPendentiRPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoChiediListaPendentiRPT  implements java.io.Serializable {
    private java.lang.String identificativoIntermediarioPA;

    private java.lang.String identificativoStazioneIntermediarioPA;

    private java.lang.String password;

    private java.lang.String identificativoDominio;

    private java.util.Calendar rangeDa;

    private java.util.Calendar rangeA;

    private org.apache.axis.types.PositiveInteger dimensioneLista;

    public NodoChiediListaPendentiRPT() {
    }

    public NodoChiediListaPendentiRPT(
           java.lang.String identificativoIntermediarioPA,
           java.lang.String identificativoStazioneIntermediarioPA,
           java.lang.String password,
           java.lang.String identificativoDominio,
           java.util.Calendar rangeDa,
           java.util.Calendar rangeA,
           org.apache.axis.types.PositiveInteger dimensioneLista) {
           this.identificativoIntermediarioPA = identificativoIntermediarioPA;
           this.identificativoStazioneIntermediarioPA = identificativoStazioneIntermediarioPA;
           this.password = password;
           this.identificativoDominio = identificativoDominio;
           this.rangeDa = rangeDa;
           this.rangeA = rangeA;
           this.dimensioneLista = dimensioneLista;
    }


    /**
     * Gets the identificativoIntermediarioPA value for this NodoChiediListaPendentiRPT.
     * 
     * @return identificativoIntermediarioPA
     */
    public java.lang.String getIdentificativoIntermediarioPA() {
        return identificativoIntermediarioPA;
    }


    /**
     * Sets the identificativoIntermediarioPA value for this NodoChiediListaPendentiRPT.
     * 
     * @param identificativoIntermediarioPA
     */
    public void setIdentificativoIntermediarioPA(java.lang.String identificativoIntermediarioPA) {
        this.identificativoIntermediarioPA = identificativoIntermediarioPA;
    }


    /**
     * Gets the identificativoStazioneIntermediarioPA value for this NodoChiediListaPendentiRPT.
     * 
     * @return identificativoStazioneIntermediarioPA
     */
    public java.lang.String getIdentificativoStazioneIntermediarioPA() {
        return identificativoStazioneIntermediarioPA;
    }


    /**
     * Sets the identificativoStazioneIntermediarioPA value for this NodoChiediListaPendentiRPT.
     * 
     * @param identificativoStazioneIntermediarioPA
     */
    public void setIdentificativoStazioneIntermediarioPA(java.lang.String identificativoStazioneIntermediarioPA) {
        this.identificativoStazioneIntermediarioPA = identificativoStazioneIntermediarioPA;
    }


    /**
     * Gets the password value for this NodoChiediListaPendentiRPT.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this NodoChiediListaPendentiRPT.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the identificativoDominio value for this NodoChiediListaPendentiRPT.
     * 
     * @return identificativoDominio
     */
    public java.lang.String getIdentificativoDominio() {
        return identificativoDominio;
    }


    /**
     * Sets the identificativoDominio value for this NodoChiediListaPendentiRPT.
     * 
     * @param identificativoDominio
     */
    public void setIdentificativoDominio(java.lang.String identificativoDominio) {
        this.identificativoDominio = identificativoDominio;
    }


    /**
     * Gets the rangeDa value for this NodoChiediListaPendentiRPT.
     * 
     * @return rangeDa
     */
    public java.util.Calendar getRangeDa() {
        return rangeDa;
    }


    /**
     * Sets the rangeDa value for this NodoChiediListaPendentiRPT.
     * 
     * @param rangeDa
     */
    public void setRangeDa(java.util.Calendar rangeDa) {
        this.rangeDa = rangeDa;
    }


    /**
     * Gets the rangeA value for this NodoChiediListaPendentiRPT.
     * 
     * @return rangeA
     */
    public java.util.Calendar getRangeA() {
        return rangeA;
    }


    /**
     * Sets the rangeA value for this NodoChiediListaPendentiRPT.
     * 
     * @param rangeA
     */
    public void setRangeA(java.util.Calendar rangeA) {
        this.rangeA = rangeA;
    }


    /**
     * Gets the dimensioneLista value for this NodoChiediListaPendentiRPT.
     * 
     * @return dimensioneLista
     */
    public org.apache.axis.types.PositiveInteger getDimensioneLista() {
        return dimensioneLista;
    }


    /**
     * Sets the dimensioneLista value for this NodoChiediListaPendentiRPT.
     * 
     * @param dimensioneLista
     */
    public void setDimensioneLista(org.apache.axis.types.PositiveInteger dimensioneLista) {
        this.dimensioneLista = dimensioneLista;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoChiediListaPendentiRPT)) return false;
        NodoChiediListaPendentiRPT other = (NodoChiediListaPendentiRPT) obj;
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
            ((this.rangeDa==null && other.getRangeDa()==null) || 
             (this.rangeDa!=null &&
              this.rangeDa.equals(other.getRangeDa()))) &&
            ((this.rangeA==null && other.getRangeA()==null) || 
             (this.rangeA!=null &&
              this.rangeA.equals(other.getRangeA()))) &&
            ((this.dimensioneLista==null && other.getDimensioneLista()==null) || 
             (this.dimensioneLista!=null &&
              this.dimensioneLista.equals(other.getDimensioneLista())));
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
        if (getRangeDa() != null) {
            _hashCode += getRangeDa().hashCode();
        }
        if (getRangeA() != null) {
            _hashCode += getRangeA().hashCode();
        }
        if (getDimensioneLista() != null) {
            _hashCode += getDimensioneLista().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoChiediListaPendentiRPT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediListaPendentiRPT"));
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
        elemField.setFieldName("rangeDa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rangeDa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rangeA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rangeA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensioneLista");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dimensioneLista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
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
