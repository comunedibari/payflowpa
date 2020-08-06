/**
 * NodoInviaRPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoInviaRPT  implements java.io.Serializable {
    private java.lang.String password;

    private java.lang.String identificativoPSP;

    private java.lang.String identificativoIntermediarioPSP;

    private java.lang.String identificativoCanale;

    private java.lang.String tipoFirma;

    private byte[] rpt;

    public NodoInviaRPT() {
    }

    public NodoInviaRPT(
           java.lang.String password,
           java.lang.String identificativoPSP,
           java.lang.String identificativoIntermediarioPSP,
           java.lang.String identificativoCanale,
           java.lang.String tipoFirma,
           byte[] rpt) {
           this.password = password;
           this.identificativoPSP = identificativoPSP;
           this.identificativoIntermediarioPSP = identificativoIntermediarioPSP;
           this.identificativoCanale = identificativoCanale;
           this.tipoFirma = tipoFirma;
           this.rpt = rpt;
    }


    /**
     * Gets the password value for this NodoInviaRPT.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this NodoInviaRPT.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the identificativoPSP value for this NodoInviaRPT.
     * 
     * @return identificativoPSP
     */
    public java.lang.String getIdentificativoPSP() {
        return identificativoPSP;
    }


    /**
     * Sets the identificativoPSP value for this NodoInviaRPT.
     * 
     * @param identificativoPSP
     */
    public void setIdentificativoPSP(java.lang.String identificativoPSP) {
        this.identificativoPSP = identificativoPSP;
    }


    /**
     * Gets the identificativoIntermediarioPSP value for this NodoInviaRPT.
     * 
     * @return identificativoIntermediarioPSP
     */
    public java.lang.String getIdentificativoIntermediarioPSP() {
        return identificativoIntermediarioPSP;
    }


    /**
     * Sets the identificativoIntermediarioPSP value for this NodoInviaRPT.
     * 
     * @param identificativoIntermediarioPSP
     */
    public void setIdentificativoIntermediarioPSP(java.lang.String identificativoIntermediarioPSP) {
        this.identificativoIntermediarioPSP = identificativoIntermediarioPSP;
    }


    /**
     * Gets the identificativoCanale value for this NodoInviaRPT.
     * 
     * @return identificativoCanale
     */
    public java.lang.String getIdentificativoCanale() {
        return identificativoCanale;
    }


    /**
     * Sets the identificativoCanale value for this NodoInviaRPT.
     * 
     * @param identificativoCanale
     */
    public void setIdentificativoCanale(java.lang.String identificativoCanale) {
        this.identificativoCanale = identificativoCanale;
    }


    /**
     * Gets the tipoFirma value for this NodoInviaRPT.
     * 
     * @return tipoFirma
     */
    public java.lang.String getTipoFirma() {
        return tipoFirma;
    }


    /**
     * Sets the tipoFirma value for this NodoInviaRPT.
     * 
     * @param tipoFirma
     */
    public void setTipoFirma(java.lang.String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }


    /**
     * Gets the rpt value for this NodoInviaRPT.
     * 
     * @return rpt
     */
    public byte[] getRpt() {
        return rpt;
    }


    /**
     * Sets the rpt value for this NodoInviaRPT.
     * 
     * @param rpt
     */
    public void setRpt(byte[] rpt) {
        this.rpt = rpt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoInviaRPT)) return false;
        NodoInviaRPT other = (NodoInviaRPT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.identificativoPSP==null && other.getIdentificativoPSP()==null) || 
             (this.identificativoPSP!=null &&
              this.identificativoPSP.equals(other.getIdentificativoPSP()))) &&
            ((this.identificativoIntermediarioPSP==null && other.getIdentificativoIntermediarioPSP()==null) || 
             (this.identificativoIntermediarioPSP!=null &&
              this.identificativoIntermediarioPSP.equals(other.getIdentificativoIntermediarioPSP()))) &&
            ((this.identificativoCanale==null && other.getIdentificativoCanale()==null) || 
             (this.identificativoCanale!=null &&
              this.identificativoCanale.equals(other.getIdentificativoCanale()))) &&
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
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
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
        new org.apache.axis.description.TypeDesc(NodoInviaRPT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRPT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoPSP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoPSP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoIntermediarioPSP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoIntermediarioPSP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoCanale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoCanale"));
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
