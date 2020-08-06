/**
 * TipoElementoListaRPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class TipoElementoListaRPT  implements java.io.Serializable {
    private java.lang.String identificativoDominio;

    private java.lang.String identificativoUnivocoVersamento;

    private java.lang.String codiceContestoPagamento;

    private java.lang.String tipoFirma;

    private byte[] rpt;

    public TipoElementoListaRPT() {
    }

    public TipoElementoListaRPT(
           java.lang.String identificativoDominio,
           java.lang.String identificativoUnivocoVersamento,
           java.lang.String codiceContestoPagamento,
           java.lang.String tipoFirma,
           byte[] rpt) {
           this.identificativoDominio = identificativoDominio;
           this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
           this.codiceContestoPagamento = codiceContestoPagamento;
           this.tipoFirma = tipoFirma;
           this.rpt = rpt;
    }


    /**
     * Gets the identificativoDominio value for this TipoElementoListaRPT.
     * 
     * @return identificativoDominio
     */
    public java.lang.String getIdentificativoDominio() {
        return identificativoDominio;
    }


    /**
     * Sets the identificativoDominio value for this TipoElementoListaRPT.
     * 
     * @param identificativoDominio
     */
    public void setIdentificativoDominio(java.lang.String identificativoDominio) {
        this.identificativoDominio = identificativoDominio;
    }


    /**
     * Gets the identificativoUnivocoVersamento value for this TipoElementoListaRPT.
     * 
     * @return identificativoUnivocoVersamento
     */
    public java.lang.String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }


    /**
     * Sets the identificativoUnivocoVersamento value for this TipoElementoListaRPT.
     * 
     * @param identificativoUnivocoVersamento
     */
    public void setIdentificativoUnivocoVersamento(java.lang.String identificativoUnivocoVersamento) {
        this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }


    /**
     * Gets the codiceContestoPagamento value for this TipoElementoListaRPT.
     * 
     * @return codiceContestoPagamento
     */
    public java.lang.String getCodiceContestoPagamento() {
        return codiceContestoPagamento;
    }


    /**
     * Sets the codiceContestoPagamento value for this TipoElementoListaRPT.
     * 
     * @param codiceContestoPagamento
     */
    public void setCodiceContestoPagamento(java.lang.String codiceContestoPagamento) {
        this.codiceContestoPagamento = codiceContestoPagamento;
    }


    /**
     * Gets the tipoFirma value for this TipoElementoListaRPT.
     * 
     * @return tipoFirma
     */
    public java.lang.String getTipoFirma() {
        return tipoFirma;
    }


    /**
     * Sets the tipoFirma value for this TipoElementoListaRPT.
     * 
     * @param tipoFirma
     */
    public void setTipoFirma(java.lang.String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }


    /**
     * Gets the rpt value for this TipoElementoListaRPT.
     * 
     * @return rpt
     */
    public byte[] getRpt() {
        return rpt;
    }


    /**
     * Sets the rpt value for this TipoElementoListaRPT.
     * 
     * @param rpt
     */
    public void setRpt(byte[] rpt) {
        this.rpt = rpt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoElementoListaRPT)) return false;
        TipoElementoListaRPT other = (TipoElementoListaRPT) obj;
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
            ((this.tipoFirma==null && other.getTipoFirma()==null) || 
             (this.tipoFirma!=null &&
              this.tipoFirma.equals(other.getTipoFirma()))) &&
            ((this.rpt==null && other.getRpt()==null) || 
             (this.rpt!=null &&
              java.util.Arrays.equals(this.rpt, other.getRpt())));
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
        if (getTipoFirma() != null) {
            _hashCode += getTipoFirma().hashCode();
        }
        if (getRpt() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRpt());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRpt(), i);
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
        new org.apache.axis.description.TypeDesc(TipoElementoListaRPT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoElementoListaRPT"));
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
        elemField.setFieldName("tipoFirma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFirma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
