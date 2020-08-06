/**
 * NodoInviaCarrelloRPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class NodoInviaCarrelloRPT  implements java.io.Serializable {
    private java.lang.String password;

    private java.lang.String identificativoPSP;

    private java.lang.String identificativoIntermediarioPSP;

    private java.lang.String identificativoCanale;

    private gov.telematici.pagamenti.ws.TipoListaRPT listaRPT;

    public NodoInviaCarrelloRPT() {
    }

    public NodoInviaCarrelloRPT(
           java.lang.String password,
           java.lang.String identificativoPSP,
           java.lang.String identificativoIntermediarioPSP,
           java.lang.String identificativoCanale,
           gov.telematici.pagamenti.ws.TipoListaRPT listaRPT) {
           this.password = password;
           this.identificativoPSP = identificativoPSP;
           this.identificativoIntermediarioPSP = identificativoIntermediarioPSP;
           this.identificativoCanale = identificativoCanale;
           this.listaRPT = listaRPT;
    }


    /**
     * Gets the password value for this NodoInviaCarrelloRPT.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this NodoInviaCarrelloRPT.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the identificativoPSP value for this NodoInviaCarrelloRPT.
     * 
     * @return identificativoPSP
     */
    public java.lang.String getIdentificativoPSP() {
        return identificativoPSP;
    }


    /**
     * Sets the identificativoPSP value for this NodoInviaCarrelloRPT.
     * 
     * @param identificativoPSP
     */
    public void setIdentificativoPSP(java.lang.String identificativoPSP) {
        this.identificativoPSP = identificativoPSP;
    }


    /**
     * Gets the identificativoIntermediarioPSP value for this NodoInviaCarrelloRPT.
     * 
     * @return identificativoIntermediarioPSP
     */
    public java.lang.String getIdentificativoIntermediarioPSP() {
        return identificativoIntermediarioPSP;
    }


    /**
     * Sets the identificativoIntermediarioPSP value for this NodoInviaCarrelloRPT.
     * 
     * @param identificativoIntermediarioPSP
     */
    public void setIdentificativoIntermediarioPSP(java.lang.String identificativoIntermediarioPSP) {
        this.identificativoIntermediarioPSP = identificativoIntermediarioPSP;
    }


    /**
     * Gets the identificativoCanale value for this NodoInviaCarrelloRPT.
     * 
     * @return identificativoCanale
     */
    public java.lang.String getIdentificativoCanale() {
        return identificativoCanale;
    }


    /**
     * Sets the identificativoCanale value for this NodoInviaCarrelloRPT.
     * 
     * @param identificativoCanale
     */
    public void setIdentificativoCanale(java.lang.String identificativoCanale) {
        this.identificativoCanale = identificativoCanale;
    }


    /**
     * Gets the listaRPT value for this NodoInviaCarrelloRPT.
     * 
     * @return listaRPT
     */
    public gov.telematici.pagamenti.ws.TipoListaRPT getListaRPT() {
        return listaRPT;
    }


    /**
     * Sets the listaRPT value for this NodoInviaCarrelloRPT.
     * 
     * @param listaRPT
     */
    public void setListaRPT(gov.telematici.pagamenti.ws.TipoListaRPT listaRPT) {
        this.listaRPT = listaRPT;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodoInviaCarrelloRPT)) return false;
        NodoInviaCarrelloRPT other = (NodoInviaCarrelloRPT) obj;
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
            ((this.listaRPT==null && other.getListaRPT()==null) || 
             (this.listaRPT!=null &&
              this.listaRPT.equals(other.getListaRPT())));
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
        if (getListaRPT() != null) {
            _hashCode += getListaRPT().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodoInviaCarrelloRPT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaCarrelloRPT"));
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
        elemField.setFieldName("listaRPT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaRPT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoListaRPT"));
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
