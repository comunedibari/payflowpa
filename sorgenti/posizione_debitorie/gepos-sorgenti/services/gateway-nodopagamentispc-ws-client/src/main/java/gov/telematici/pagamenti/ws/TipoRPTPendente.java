/**
 * TipoRPTPendente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class TipoRPTPendente  implements java.io.Serializable {
    private java.lang.String identificativoDominio;

    private java.lang.String identificativoUnivocoVersamento;

    private java.lang.String codiceContestoPagamento;

    private java.lang.String stato;

    public TipoRPTPendente() {
    }

    public TipoRPTPendente(
           java.lang.String identificativoDominio,
           java.lang.String identificativoUnivocoVersamento,
           java.lang.String codiceContestoPagamento,
           java.lang.String stato) {
           this.identificativoDominio = identificativoDominio;
           this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
           this.codiceContestoPagamento = codiceContestoPagamento;
           this.stato = stato;
    }


    /**
     * Gets the identificativoDominio value for this TipoRPTPendente.
     * 
     * @return identificativoDominio
     */
    public java.lang.String getIdentificativoDominio() {
        return identificativoDominio;
    }


    /**
     * Sets the identificativoDominio value for this TipoRPTPendente.
     * 
     * @param identificativoDominio
     */
    public void setIdentificativoDominio(java.lang.String identificativoDominio) {
        this.identificativoDominio = identificativoDominio;
    }


    /**
     * Gets the identificativoUnivocoVersamento value for this TipoRPTPendente.
     * 
     * @return identificativoUnivocoVersamento
     */
    public java.lang.String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }


    /**
     * Sets the identificativoUnivocoVersamento value for this TipoRPTPendente.
     * 
     * @param identificativoUnivocoVersamento
     */
    public void setIdentificativoUnivocoVersamento(java.lang.String identificativoUnivocoVersamento) {
        this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }


    /**
     * Gets the codiceContestoPagamento value for this TipoRPTPendente.
     * 
     * @return codiceContestoPagamento
     */
    public java.lang.String getCodiceContestoPagamento() {
        return codiceContestoPagamento;
    }


    /**
     * Sets the codiceContestoPagamento value for this TipoRPTPendente.
     * 
     * @param codiceContestoPagamento
     */
    public void setCodiceContestoPagamento(java.lang.String codiceContestoPagamento) {
        this.codiceContestoPagamento = codiceContestoPagamento;
    }


    /**
     * Gets the stato value for this TipoRPTPendente.
     * 
     * @return stato
     */
    public java.lang.String getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this TipoRPTPendente.
     * 
     * @param stato
     */
    public void setStato(java.lang.String stato) {
        this.stato = stato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoRPTPendente)) return false;
        TipoRPTPendente other = (TipoRPTPendente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identificativoDominio==null && other.getIdentificativoDominio()==null) || 
             (this.identificativoDominio!=null &&
              this.identificativoDominio.equals(other.getIdentificativoDominio()))) &&
            ((this.identificativoUnivocoVersamento==null && other.getIdentificativoUnivocoVersamento()==null) || 
             (this.identificativoUnivocoVersamento!=null &&
              this.identificativoUnivocoVersamento.equals(other.getIdentificativoUnivocoVersamento()))) &&
            ((this.codiceContestoPagamento==null && other.getCodiceContestoPagamento()==null) || 
             (this.codiceContestoPagamento!=null &&
              this.codiceContestoPagamento.equals(other.getCodiceContestoPagamento()))) &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato())));
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
        if (getIdentificativoDominio() != null) {
            _hashCode += getIdentificativoDominio().hashCode();
        }
        if (getIdentificativoUnivocoVersamento() != null) {
            _hashCode += getIdentificativoUnivocoVersamento().hashCode();
        }
        if (getCodiceContestoPagamento() != null) {
            _hashCode += getCodiceContestoPagamento().hashCode();
        }
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoRPTPendente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoRPTPendente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stato"));
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
