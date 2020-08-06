/**
 * UtenteCanaleType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.tasgroup.iris.comunication.ws.impl;

public class UtenteCanaleType  implements java.io.Serializable {
    private java.lang.String id_utente;

    private it.tasgroup.iris.comunication.ws.impl.CanaleType canale;

    private boolean is_anonymous;

    private java.lang.String stato;

    private java.lang.String configurazione;

    public UtenteCanaleType() {
    }

    public UtenteCanaleType(
           java.lang.String id_utente,
           it.tasgroup.iris.comunication.ws.impl.CanaleType canale,
           boolean is_anonymous,
           java.lang.String stato,
           java.lang.String configurazione) {
           this.id_utente = id_utente;
           this.canale = canale;
           this.is_anonymous = is_anonymous;
           this.stato = stato;
           this.configurazione = configurazione;
    }


    /**
     * Gets the id_utente value for this UtenteCanaleType.
     * 
     * @return id_utente
     */
    public java.lang.String getId_utente() {
        return id_utente;
    }


    /**
     * Sets the id_utente value for this UtenteCanaleType.
     * 
     * @param id_utente
     */
    public void setId_utente(java.lang.String id_utente) {
        this.id_utente = id_utente;
    }


    /**
     * Gets the canale value for this UtenteCanaleType.
     * 
     * @return canale
     */
    public it.tasgroup.iris.comunication.ws.impl.CanaleType getCanale() {
        return canale;
    }


    /**
     * Sets the canale value for this UtenteCanaleType.
     * 
     * @param canale
     */
    public void setCanale(it.tasgroup.iris.comunication.ws.impl.CanaleType canale) {
        this.canale = canale;
    }


    /**
     * Gets the is_anonymous value for this UtenteCanaleType.
     * 
     * @return is_anonymous
     */
    public boolean isIs_anonymous() {
        return is_anonymous;
    }


    /**
     * Sets the is_anonymous value for this UtenteCanaleType.
     * 
     * @param is_anonymous
     */
    public void setIs_anonymous(boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }


    /**
     * Gets the stato value for this UtenteCanaleType.
     * 
     * @return stato
     */
    public java.lang.String getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this UtenteCanaleType.
     * 
     * @param stato
     */
    public void setStato(java.lang.String stato) {
        this.stato = stato;
    }


    /**
     * Gets the configurazione value for this UtenteCanaleType.
     * 
     * @return configurazione
     */
    public java.lang.String getConfigurazione() {
        return configurazione;
    }


    /**
     * Sets the configurazione value for this UtenteCanaleType.
     * 
     * @param configurazione
     */
    public void setConfigurazione(java.lang.String configurazione) {
        this.configurazione = configurazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtenteCanaleType)) return false;
        UtenteCanaleType other = (UtenteCanaleType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id_utente==null && other.getId_utente()==null) || 
             (this.id_utente!=null &&
              this.id_utente.equals(other.getId_utente()))) &&
            ((this.canale==null && other.getCanale()==null) || 
             (this.canale!=null &&
              this.canale.equals(other.getCanale()))) &&
            this.is_anonymous == other.isIs_anonymous() &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato()))) &&
            ((this.configurazione==null && other.getConfigurazione()==null) || 
             (this.configurazione!=null &&
              this.configurazione.equals(other.getConfigurazione())));
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
        if (getId_utente() != null) {
            _hashCode += getId_utente().hashCode();
        }
        if (getCanale() != null) {
            _hashCode += getCanale().hashCode();
        }
        _hashCode += (isIs_anonymous() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        if (getConfigurazione() != null) {
            _hashCode += getConfigurazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtenteCanaleType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteCanaleType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_utente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id_utente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "CanaleType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("is_anonymous");
        elemField.setXmlName(new javax.xml.namespace.QName("", "is_anonymous"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configurazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "configurazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
