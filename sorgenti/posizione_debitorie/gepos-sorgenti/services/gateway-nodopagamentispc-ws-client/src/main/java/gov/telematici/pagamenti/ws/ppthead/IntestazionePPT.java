/**
 * IntestazionePPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws.ppthead;

public class IntestazionePPT  implements java.io.Serializable {
    private java.lang.String identificativoIntermediarioPA;

    private java.lang.String identificativoStazioneIntermediarioPA;

    private java.lang.String identificativoDominio;

    private java.lang.String identificativoUnivocoVersamento;

    private java.lang.String codiceContestoPagamento;

    private org.apache.axis.types.URI actor;  // attribute

    private java.lang.Boolean mustUnderstand;  // attribute

    public IntestazionePPT() {
    }

    public IntestazionePPT(
           java.lang.String identificativoIntermediarioPA,
           java.lang.String identificativoStazioneIntermediarioPA,
           java.lang.String identificativoDominio,
           java.lang.String identificativoUnivocoVersamento,
           java.lang.String codiceContestoPagamento,
           org.apache.axis.types.URI actor,
           java.lang.Boolean mustUnderstand) {
           this.identificativoIntermediarioPA = identificativoIntermediarioPA;
           this.identificativoStazioneIntermediarioPA = identificativoStazioneIntermediarioPA;
           this.identificativoDominio = identificativoDominio;
           this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
           this.codiceContestoPagamento = codiceContestoPagamento;
           this.actor = actor;
           this.mustUnderstand = mustUnderstand;
    }


    /**
     * Gets the identificativoIntermediarioPA value for this IntestazionePPT.
     * 
     * @return identificativoIntermediarioPA
     */
    public java.lang.String getIdentificativoIntermediarioPA() {
        return identificativoIntermediarioPA;
    }


    /**
     * Sets the identificativoIntermediarioPA value for this IntestazionePPT.
     * 
     * @param identificativoIntermediarioPA
     */
    public void setIdentificativoIntermediarioPA(java.lang.String identificativoIntermediarioPA) {
        this.identificativoIntermediarioPA = identificativoIntermediarioPA;
    }


    /**
     * Gets the identificativoStazioneIntermediarioPA value for this IntestazionePPT.
     * 
     * @return identificativoStazioneIntermediarioPA
     */
    public java.lang.String getIdentificativoStazioneIntermediarioPA() {
        return identificativoStazioneIntermediarioPA;
    }


    /**
     * Sets the identificativoStazioneIntermediarioPA value for this IntestazionePPT.
     * 
     * @param identificativoStazioneIntermediarioPA
     */
    public void setIdentificativoStazioneIntermediarioPA(java.lang.String identificativoStazioneIntermediarioPA) {
        this.identificativoStazioneIntermediarioPA = identificativoStazioneIntermediarioPA;
    }


    /**
     * Gets the identificativoDominio value for this IntestazionePPT.
     * 
     * @return identificativoDominio
     */
    public java.lang.String getIdentificativoDominio() {
        return identificativoDominio;
    }


    /**
     * Sets the identificativoDominio value for this IntestazionePPT.
     * 
     * @param identificativoDominio
     */
    public void setIdentificativoDominio(java.lang.String identificativoDominio) {
        this.identificativoDominio = identificativoDominio;
    }


    /**
     * Gets the identificativoUnivocoVersamento value for this IntestazionePPT.
     * 
     * @return identificativoUnivocoVersamento
     */
    public java.lang.String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }


    /**
     * Sets the identificativoUnivocoVersamento value for this IntestazionePPT.
     * 
     * @param identificativoUnivocoVersamento
     */
    public void setIdentificativoUnivocoVersamento(java.lang.String identificativoUnivocoVersamento) {
        this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }


    /**
     * Gets the codiceContestoPagamento value for this IntestazionePPT.
     * 
     * @return codiceContestoPagamento
     */
    public java.lang.String getCodiceContestoPagamento() {
        return codiceContestoPagamento;
    }


    /**
     * Sets the codiceContestoPagamento value for this IntestazionePPT.
     * 
     * @param codiceContestoPagamento
     */
    public void setCodiceContestoPagamento(java.lang.String codiceContestoPagamento) {
        this.codiceContestoPagamento = codiceContestoPagamento;
    }


    /**
     * Gets the actor value for this IntestazionePPT.
     * 
     * @return actor
     */
    public org.apache.axis.types.URI getActor() {
        return actor;
    }


    /**
     * Sets the actor value for this IntestazionePPT.
     * 
     * @param actor
     */
    public void setActor(org.apache.axis.types.URI actor) {
        this.actor = actor;
    }


    /**
     * Gets the mustUnderstand value for this IntestazionePPT.
     * 
     * @return mustUnderstand
     */
    public java.lang.Boolean getMustUnderstand() {
        return mustUnderstand;
    }


    /**
     * Sets the mustUnderstand value for this IntestazionePPT.
     * 
     * @param mustUnderstand
     */
    public void setMustUnderstand(java.lang.Boolean mustUnderstand) {
        this.mustUnderstand = mustUnderstand;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IntestazionePPT)) return false;
        IntestazionePPT other = (IntestazionePPT) obj;
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
            ((this.identificativoDominio==null && other.getIdentificativoDominio()==null) || 
             (this.identificativoDominio!=null &&
              this.identificativoDominio.equals(other.getIdentificativoDominio()))) &&
            ((this.identificativoUnivocoVersamento==null && other.getIdentificativoUnivocoVersamento()==null) || 
             (this.identificativoUnivocoVersamento!=null &&
              this.identificativoUnivocoVersamento.equals(other.getIdentificativoUnivocoVersamento()))) &&
            ((this.codiceContestoPagamento==null && other.getCodiceContestoPagamento()==null) || 
             (this.codiceContestoPagamento!=null &&
              this.codiceContestoPagamento.equals(other.getCodiceContestoPagamento()))) &&
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
        if (getIdentificativoDominio() != null) {
            _hashCode += getIdentificativoDominio().hashCode();
        }
        if (getIdentificativoUnivocoVersamento() != null) {
            _hashCode += getIdentificativoUnivocoVersamento().hashCode();
        }
        if (getCodiceContestoPagamento() != null) {
            _hashCode += getCodiceContestoPagamento().hashCode();
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
        new org.apache.axis.description.TypeDesc(IntestazionePPT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", ">intestazionePPT"));
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
        elemField.setFieldName("identificativoDominio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoDominio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoUnivocoVersamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoUnivocoVersamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceContestoPagamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceContestoPagamento"));
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
