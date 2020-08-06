/**
 * SearchLogMessaggiType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.tasgroup.iris.comunication.ws.impl;

public class SearchLogMessaggiType  implements java.io.Serializable {
    private it.tasgroup.iris.comunication.ws.impl.CanaleType canale;

    private it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType stato;

    private java.util.Calendar dataDa;

    private java.util.Calendar dataA;

    private java.lang.String utente;

    private it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType tipoMessaggio;

    public SearchLogMessaggiType() {
    }

    public SearchLogMessaggiType(
           it.tasgroup.iris.comunication.ws.impl.CanaleType canale,
           it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType stato,
           java.util.Calendar dataDa,
           java.util.Calendar dataA,
           java.lang.String utente,
           it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType tipoMessaggio) {
           this.canale = canale;
           this.stato = stato;
           this.dataDa = dataDa;
           this.dataA = dataA;
           this.utente = utente;
           this.tipoMessaggio = tipoMessaggio;
    }


    /**
     * Gets the canale value for this SearchLogMessaggiType.
     * 
     * @return canale
     */
    public it.tasgroup.iris.comunication.ws.impl.CanaleType getCanale() {
        return canale;
    }


    /**
     * Sets the canale value for this SearchLogMessaggiType.
     * 
     * @param canale
     */
    public void setCanale(it.tasgroup.iris.comunication.ws.impl.CanaleType canale) {
        this.canale = canale;
    }


    /**
     * Gets the stato value for this SearchLogMessaggiType.
     * 
     * @return stato
     */
    public it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this SearchLogMessaggiType.
     * 
     * @param stato
     */
    public void setStato(it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType stato) {
        this.stato = stato;
    }


    /**
     * Gets the dataDa value for this SearchLogMessaggiType.
     * 
     * @return dataDa
     */
    public java.util.Calendar getDataDa() {
        return dataDa;
    }


    /**
     * Sets the dataDa value for this SearchLogMessaggiType.
     * 
     * @param dataDa
     */
    public void setDataDa(java.util.Calendar dataDa) {
        this.dataDa = dataDa;
    }


    /**
     * Gets the dataA value for this SearchLogMessaggiType.
     * 
     * @return dataA
     */
    public java.util.Calendar getDataA() {
        return dataA;
    }


    /**
     * Sets the dataA value for this SearchLogMessaggiType.
     * 
     * @param dataA
     */
    public void setDataA(java.util.Calendar dataA) {
        this.dataA = dataA;
    }


    /**
     * Gets the utente value for this SearchLogMessaggiType.
     * 
     * @return utente
     */
    public java.lang.String getUtente() {
        return utente;
    }


    /**
     * Sets the utente value for this SearchLogMessaggiType.
     * 
     * @param utente
     */
    public void setUtente(java.lang.String utente) {
        this.utente = utente;
    }


    /**
     * Gets the tipoMessaggio value for this SearchLogMessaggiType.
     * 
     * @return tipoMessaggio
     */
    public it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType getTipoMessaggio() {
        return tipoMessaggio;
    }


    /**
     * Sets the tipoMessaggio value for this SearchLogMessaggiType.
     * 
     * @param tipoMessaggio
     */
    public void setTipoMessaggio(it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType tipoMessaggio) {
        this.tipoMessaggio = tipoMessaggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchLogMessaggiType)) return false;
        SearchLogMessaggiType other = (SearchLogMessaggiType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.canale==null && other.getCanale()==null) || 
             (this.canale!=null &&
              this.canale.equals(other.getCanale()))) &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato()))) &&
            ((this.dataDa==null && other.getDataDa()==null) || 
             (this.dataDa!=null &&
              this.dataDa.equals(other.getDataDa()))) &&
            ((this.dataA==null && other.getDataA()==null) || 
             (this.dataA!=null &&
              this.dataA.equals(other.getDataA()))) &&
            ((this.utente==null && other.getUtente()==null) || 
             (this.utente!=null &&
              this.utente.equals(other.getUtente()))) &&
            ((this.tipoMessaggio==null && other.getTipoMessaggio()==null) || 
             (this.tipoMessaggio!=null &&
              this.tipoMessaggio.equals(other.getTipoMessaggio())));
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
        if (getCanale() != null) {
            _hashCode += getCanale().hashCode();
        }
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        if (getDataDa() != null) {
            _hashCode += getDataDa().hashCode();
        }
        if (getDataA() != null) {
            _hashCode += getDataA().hashCode();
        }
        if (getUtente() != null) {
            _hashCode += getUtente().hashCode();
        }
        if (getTipoMessaggio() != null) {
            _hashCode += getTipoMessaggio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchLogMessaggiType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "SearchLogMessaggiType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "CanaleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "StatoMessaggioType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataDa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoMessaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoMessaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "TipoMessaggioType"));
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
