/**
 * NodoChiediSceltaWISPRisposta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoChiediSceltaWISPRisposta  extends gov.telematici.pagamenti.ws.Risposta  implements java.io.Serializable {
    private gov.telematici.pagamenti.ws.StEffettuazioneScelta effettuazioneScelta;

    private java.lang.String identificativoPSP;

    private java.lang.String identificativoIntermediarioPSP;

    private java.lang.String identificativoCanale;

    private gov.telematici.pagamenti.ws.StTipoVersamento tipoVersamento;

    public NodoChiediSceltaWISPRisposta() {
    }

    public NodoChiediSceltaWISPRisposta(
           gov.telematici.pagamenti.ws.FaultBean fault,
           gov.telematici.pagamenti.ws.StEffettuazioneScelta effettuazioneScelta,
           java.lang.String identificativoPSP,
           java.lang.String identificativoIntermediarioPSP,
           java.lang.String identificativoCanale,
           gov.telematici.pagamenti.ws.StTipoVersamento tipoVersamento) {
        super(
            fault);
        this.effettuazioneScelta = effettuazioneScelta;
        this.identificativoPSP = identificativoPSP;
        this.identificativoIntermediarioPSP = identificativoIntermediarioPSP;
        this.identificativoCanale = identificativoCanale;
        this.tipoVersamento = tipoVersamento;
    }


    /**
     * Gets the effettuazioneScelta value for this NodoChiediSceltaWISPRisposta.
     * 
     * @return effettuazioneScelta
     */
    public gov.telematici.pagamenti.ws.StEffettuazioneScelta getEffettuazioneScelta() {
        return effettuazioneScelta;
    }


    /**
     * Sets the effettuazioneScelta value for this NodoChiediSceltaWISPRisposta.
     * 
     * @param effettuazioneScelta
     */
    public void setEffettuazioneScelta(gov.telematici.pagamenti.ws.StEffettuazioneScelta effettuazioneScelta) {
        this.effettuazioneScelta = effettuazioneScelta;
    }


    /**
     * Gets the identificativoPSP value for this NodoChiediSceltaWISPRisposta.
     * 
     * @return identificativoPSP
     */
    public java.lang.String getIdentificativoPSP() {
        return identificativoPSP;
    }


    /**
     * Sets the identificativoPSP value for this NodoChiediSceltaWISPRisposta.
     * 
     * @param identificativoPSP
     */
    public void setIdentificativoPSP(java.lang.String identificativoPSP) {
        this.identificativoPSP = identificativoPSP;
    }


    /**
     * Gets the identificativoIntermediarioPSP value for this NodoChiediSceltaWISPRisposta.
     * 
     * @return identificativoIntermediarioPSP
     */
    public java.lang.String getIdentificativoIntermediarioPSP() {
        return identificativoIntermediarioPSP;
    }


    /**
     * Sets the identificativoIntermediarioPSP value for this NodoChiediSceltaWISPRisposta.
     * 
     * @param identificativoIntermediarioPSP
     */
    public void setIdentificativoIntermediarioPSP(java.lang.String identificativoIntermediarioPSP) {
        this.identificativoIntermediarioPSP = identificativoIntermediarioPSP;
    }


    /**
     * Gets the identificativoCanale value for this NodoChiediSceltaWISPRisposta.
     * 
     * @return identificativoCanale
     */
    public java.lang.String getIdentificativoCanale() {
        return identificativoCanale;
    }


    /**
     * Sets the identificativoCanale value for this NodoChiediSceltaWISPRisposta.
     * 
     * @param identificativoCanale
     */
    public void setIdentificativoCanale(java.lang.String identificativoCanale) {
        this.identificativoCanale = identificativoCanale;
    }


    /**
     * Gets the tipoVersamento value for this NodoChiediSceltaWISPRisposta.
     * 
     * @return tipoVersamento
     */
    public gov.telematici.pagamenti.ws.StTipoVersamento getTipoVersamento() {
        return tipoVersamento;
    }


    /**
     * Sets the tipoVersamento value for this NodoChiediSceltaWISPRisposta.
     * 
     * @param tipoVersamento
     */
    public void setTipoVersamento(gov.telematici.pagamenti.ws.StTipoVersamento tipoVersamento) {
        this.tipoVersamento = tipoVersamento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoChiediSceltaWISPRisposta)) return false;
        NodoChiediSceltaWISPRisposta other = (NodoChiediSceltaWISPRisposta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.effettuazioneScelta==null && other.getEffettuazioneScelta()==null) || 
             (this.effettuazioneScelta!=null &&
              this.effettuazioneScelta.equals(other.getEffettuazioneScelta()))) &&
            ((this.identificativoPSP==null && other.getIdentificativoPSP()==null) || 
             (this.identificativoPSP!=null &&
              this.identificativoPSP.equals(other.getIdentificativoPSP()))) &&
            ((this.identificativoIntermediarioPSP==null && other.getIdentificativoIntermediarioPSP()==null) || 
             (this.identificativoIntermediarioPSP!=null &&
              this.identificativoIntermediarioPSP.equals(other.getIdentificativoIntermediarioPSP()))) &&
            ((this.identificativoCanale==null && other.getIdentificativoCanale()==null) || 
             (this.identificativoCanale!=null &&
              this.identificativoCanale.equals(other.getIdentificativoCanale()))) &&
            ((this.tipoVersamento==null && other.getTipoVersamento()==null) || 
             (this.tipoVersamento!=null &&
              this.tipoVersamento.equals(other.getTipoVersamento())));
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
        if (getEffettuazioneScelta() != null) {
            _hashCode += getEffettuazioneScelta().hashCode();
        }
        if (getIdentificativoPSP() != null) {
            _hashCode += getIdentificativoPSP().hashCode();
        }
        if (getIdentificativoIntermediarioPSP() != null) {
            _hashCode += getIdentificativoIntermediarioPSP().hashCode();
        }
        if (getIdentificativoCanale() != null) {
            _hashCode += getIdentificativoCanale().hashCode();
        }
        if (getTipoVersamento() != null) {
            _hashCode += getTipoVersamento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoChiediSceltaWISPRisposta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediSceltaWISPRisposta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effettuazioneScelta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effettuazioneScelta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "stEffettuazioneScelta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoPSP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoPSP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoIntermediarioPSP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoIntermediarioPSP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoCanale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoCanale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoVersamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoVersamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "stTipoVersamento"));
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
