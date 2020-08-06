/**
 * EsitoChiediStatoRPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class EsitoChiediStatoRPT  implements java.io.Serializable {
    private java.lang.String stato;

    private java.lang.Integer redirect;

    private java.lang.String url;

    private gov.telematici.pagamenti.ws.TipoStoricoRPT[] elementoStoricoRPT;

    private gov.telematici.pagamenti.ws.TipoStoricoVersamento[] elementoStoricoVersamento;

    public EsitoChiediStatoRPT() {
    }

    public EsitoChiediStatoRPT(
           java.lang.String stato,
           java.lang.Integer redirect,
           java.lang.String url,
           gov.telematici.pagamenti.ws.TipoStoricoRPT[] elementoStoricoRPT,
           gov.telematici.pagamenti.ws.TipoStoricoVersamento[] elementoStoricoVersamento) {
           this.stato = stato;
           this.redirect = redirect;
           this.url = url;
           this.elementoStoricoRPT = elementoStoricoRPT;
           this.elementoStoricoVersamento = elementoStoricoVersamento;
    }


    /**
     * Gets the stato value for this EsitoChiediStatoRPT.
     * 
     * @return stato
     */
    public java.lang.String getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this EsitoChiediStatoRPT.
     * 
     * @param stato
     */
    public void setStato(java.lang.String stato) {
        this.stato = stato;
    }


    /**
     * Gets the redirect value for this EsitoChiediStatoRPT.
     * 
     * @return redirect
     */
    public java.lang.Integer getRedirect() {
        return redirect;
    }


    /**
     * Sets the redirect value for this EsitoChiediStatoRPT.
     * 
     * @param redirect
     */
    public void setRedirect(java.lang.Integer redirect) {
        this.redirect = redirect;
    }


    /**
     * Gets the url value for this EsitoChiediStatoRPT.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this EsitoChiediStatoRPT.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }


    /**
     * Gets the elementoStoricoRPT value for this EsitoChiediStatoRPT.
     * 
     * @return elementoStoricoRPT
     */
    public gov.telematici.pagamenti.ws.TipoStoricoRPT[] getElementoStoricoRPT() {
        return elementoStoricoRPT;
    }


    /**
     * Sets the elementoStoricoRPT value for this EsitoChiediStatoRPT.
     * 
     * @param elementoStoricoRPT
     */
    public void setElementoStoricoRPT(gov.telematici.pagamenti.ws.TipoStoricoRPT[] elementoStoricoRPT) {
        this.elementoStoricoRPT = elementoStoricoRPT;
    }

    public gov.telematici.pagamenti.ws.TipoStoricoRPT getElementoStoricoRPT(int i) {
        return this.elementoStoricoRPT[i];
    }

    public void setElementoStoricoRPT(int i, gov.telematici.pagamenti.ws.TipoStoricoRPT _value) {
        this.elementoStoricoRPT[i] = _value;
    }


    /**
     * Gets the elementoStoricoVersamento value for this EsitoChiediStatoRPT.
     * 
     * @return elementoStoricoVersamento
     */
    public gov.telematici.pagamenti.ws.TipoStoricoVersamento[] getElementoStoricoVersamento() {
        return elementoStoricoVersamento;
    }


    /**
     * Sets the elementoStoricoVersamento value for this EsitoChiediStatoRPT.
     * 
     * @param elementoStoricoVersamento
     */
    public void setElementoStoricoVersamento(gov.telematici.pagamenti.ws.TipoStoricoVersamento[] elementoStoricoVersamento) {
        this.elementoStoricoVersamento = elementoStoricoVersamento;
    }

    public gov.telematici.pagamenti.ws.TipoStoricoVersamento getElementoStoricoVersamento(int i) {
        return this.elementoStoricoVersamento[i];
    }

    public void setElementoStoricoVersamento(int i, gov.telematici.pagamenti.ws.TipoStoricoVersamento _value) {
        this.elementoStoricoVersamento[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EsitoChiediStatoRPT)) return false;
        EsitoChiediStatoRPT other = (EsitoChiediStatoRPT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato()))) &&
            ((this.redirect==null && other.getRedirect()==null) || 
             (this.redirect!=null &&
              this.redirect.equals(other.getRedirect()))) &&
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl()))) &&
            ((this.elementoStoricoRPT==null && other.getElementoStoricoRPT()==null) || 
             (this.elementoStoricoRPT!=null &&
              java.util.Arrays.equals(this.elementoStoricoRPT, other.getElementoStoricoRPT()))) &&
            ((this.elementoStoricoVersamento==null && other.getElementoStoricoVersamento()==null) || 
             (this.elementoStoricoVersamento!=null &&
              java.util.Arrays.equals(this.elementoStoricoVersamento, other.getElementoStoricoVersamento())));
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
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        if (getRedirect() != null) {
            _hashCode += getRedirect().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        if (getElementoStoricoRPT() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElementoStoricoRPT());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElementoStoricoRPT(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getElementoStoricoVersamento() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElementoStoricoVersamento());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElementoStoricoVersamento(), i);
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
        new org.apache.axis.description.TypeDesc(EsitoChiediStatoRPT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "esitoChiediStatoRPT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("redirect");
        elemField.setXmlName(new javax.xml.namespace.QName("", "redirect"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elementoStoricoRPT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elementoStoricoRPT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoStoricoRPT"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elementoStoricoVersamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elementoStoricoVersamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoStoricoVersamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
