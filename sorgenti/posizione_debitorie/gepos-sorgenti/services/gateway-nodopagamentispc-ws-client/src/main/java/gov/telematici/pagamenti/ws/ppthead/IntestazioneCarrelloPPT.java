/**
 * IntestazioneCarrelloPPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws.ppthead;

public class IntestazioneCarrelloPPT  implements java.io.Serializable {
    private java.lang.String identificativoIntermediarioPA;

    private java.lang.String identificativoStazioneIntermediarioPA;

    private java.lang.String identificativoCarrello;

    private org.apache.axis.types.URI actor;  // attribute

    private java.lang.Boolean mustUnderstand;  // attribute

    public IntestazioneCarrelloPPT() {
    }

    public IntestazioneCarrelloPPT(
           java.lang.String identificativoIntermediarioPA,
           java.lang.String identificativoStazioneIntermediarioPA,
           java.lang.String identificativoCarrello,
           org.apache.axis.types.URI actor,
           java.lang.Boolean mustUnderstand) {
           this.identificativoIntermediarioPA = identificativoIntermediarioPA;
           this.identificativoStazioneIntermediarioPA = identificativoStazioneIntermediarioPA;
           this.identificativoCarrello = identificativoCarrello;
           this.actor = actor;
           this.mustUnderstand = mustUnderstand;
    }


    /**
     * Gets the identificativoIntermediarioPA value for this IntestazioneCarrelloPPT.
     * 
     * @return identificativoIntermediarioPA
     */
    public java.lang.String getIdentificativoIntermediarioPA() {
        return identificativoIntermediarioPA;
    }


    /**
     * Sets the identificativoIntermediarioPA value for this IntestazioneCarrelloPPT.
     * 
     * @param identificativoIntermediarioPA
     */
    public void setIdentificativoIntermediarioPA(java.lang.String identificativoIntermediarioPA) {
        this.identificativoIntermediarioPA = identificativoIntermediarioPA;
    }


    /**
     * Gets the identificativoStazioneIntermediarioPA value for this IntestazioneCarrelloPPT.
     * 
     * @return identificativoStazioneIntermediarioPA
     */
    public java.lang.String getIdentificativoStazioneIntermediarioPA() {
        return identificativoStazioneIntermediarioPA;
    }


    /**
     * Sets the identificativoStazioneIntermediarioPA value for this IntestazioneCarrelloPPT.
     * 
     * @param identificativoStazioneIntermediarioPA
     */
    public void setIdentificativoStazioneIntermediarioPA(java.lang.String identificativoStazioneIntermediarioPA) {
        this.identificativoStazioneIntermediarioPA = identificativoStazioneIntermediarioPA;
    }


    /**
     * Gets the identificativoCarrello value for this IntestazioneCarrelloPPT.
     * 
     * @return identificativoCarrello
     */
    public java.lang.String getIdentificativoCarrello() {
        return identificativoCarrello;
    }


    /**
     * Sets the identificativoCarrello value for this IntestazioneCarrelloPPT.
     * 
     * @param identificativoCarrello
     */
    public void setIdentificativoCarrello(java.lang.String identificativoCarrello) {
        this.identificativoCarrello = identificativoCarrello;
    }


    /**
     * Gets the actor value for this IntestazioneCarrelloPPT.
     * 
     * @return actor
     */
    public org.apache.axis.types.URI getActor() {
        return actor;
    }


    /**
     * Sets the actor value for this IntestazioneCarrelloPPT.
     * 
     * @param actor
     */
    public void setActor(org.apache.axis.types.URI actor) {
        this.actor = actor;
    }


    /**
     * Gets the mustUnderstand value for this IntestazioneCarrelloPPT.
     * 
     * @return mustUnderstand
     */
    public java.lang.Boolean getMustUnderstand() {
        return mustUnderstand;
    }


    /**
     * Sets the mustUnderstand value for this IntestazioneCarrelloPPT.
     * 
     * @param mustUnderstand
     */
    public void setMustUnderstand(java.lang.Boolean mustUnderstand) {
        this.mustUnderstand = mustUnderstand;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IntestazioneCarrelloPPT)) return false;
        IntestazioneCarrelloPPT other = (IntestazioneCarrelloPPT) obj;
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
            ((this.identificativoCarrello==null && other.getIdentificativoCarrello()==null) || 
             (this.identificativoCarrello!=null &&
              this.identificativoCarrello.equals(other.getIdentificativoCarrello()))) &&
            ((this.actor==null && other.getActor()==null) || 
             (this.actor!=null &&
              this.actor.equals(other.getActor()))) &&
            ((this.mustUnderstand==null && other.getMustUnderstand()==null) || 
             (this.mustUnderstand!=null &&
              this.mustUnderstand.equals(other.getMustUnderstand())));
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
        if (getIdentificativoCarrello() != null) {
            _hashCode += getIdentificativoCarrello().hashCode();
        }
        if (getActor() != null) {
            _hashCode += getActor().hashCode();
        }
        if (getMustUnderstand() != null) {
            _hashCode += getMustUnderstand().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IntestazioneCarrelloPPT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", ">intestazioneCarrelloPPT"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("actor");
        attrField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "actor"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("mustUnderstand");
        attrField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", ">mustUnderstand"));
        typeDesc.addFieldDesc(attrField);
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
        elemField.setFieldName("identificativoCarrello");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoCarrello"));
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
