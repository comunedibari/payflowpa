/**
 * LogMessaggioType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.tasgroup.iris.comunication.ws.impl;

public class LogMessaggioType  implements java.io.Serializable {
    private java.math.BigInteger id;

    private java.util.Calendar data;

    private it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType stato;

    private it.tasgroup.iris.comunication.ws.impl.CanaleType canale;

    private it.tasgroup.iris.comunication.ws.impl.UtenteType utente;

    private it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType messaggioLogico;

    private java.lang.String sortingField;

    private java.lang.String sortingDir;

    public LogMessaggioType() {
    }

    public LogMessaggioType(
           java.math.BigInteger id,
           java.util.Calendar data,
           it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType stato,
           it.tasgroup.iris.comunication.ws.impl.CanaleType canale,
           it.tasgroup.iris.comunication.ws.impl.UtenteType utente,
           it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType messaggioLogico,
           java.lang.String sortingField,
           java.lang.String sortingDir) {
           this.id = id;
           this.data = data;
           this.stato = stato;
           this.canale = canale;
           this.utente = utente;
           this.messaggioLogico = messaggioLogico;
           this.sortingField = sortingField;
           this.sortingDir = sortingDir;
    }


    /**
     * Gets the id value for this LogMessaggioType.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this LogMessaggioType.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the data value for this LogMessaggioType.
     * 
     * @return data
     */
    public java.util.Calendar getData() {
        return data;
    }


    /**
     * Sets the data value for this LogMessaggioType.
     * 
     * @param data
     */
    public void setData(java.util.Calendar data) {
        this.data = data;
    }


    /**
     * Gets the stato value for this LogMessaggioType.
     * 
     * @return stato
     */
    public it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this LogMessaggioType.
     * 
     * @param stato
     */
    public void setStato(it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType stato) {
        this.stato = stato;
    }


    /**
     * Gets the canale value for this LogMessaggioType.
     * 
     * @return canale
     */
    public it.tasgroup.iris.comunication.ws.impl.CanaleType getCanale() {
        return canale;
    }


    /**
     * Sets the canale value for this LogMessaggioType.
     * 
     * @param canale
     */
    public void setCanale(it.tasgroup.iris.comunication.ws.impl.CanaleType canale) {
        this.canale = canale;
    }


    /**
     * Gets the utente value for this LogMessaggioType.
     * 
     * @return utente
     */
    public it.tasgroup.iris.comunication.ws.impl.UtenteType getUtente() {
        return utente;
    }


    /**
     * Sets the utente value for this LogMessaggioType.
     * 
     * @param utente
     */
    public void setUtente(it.tasgroup.iris.comunication.ws.impl.UtenteType utente) {
        this.utente = utente;
    }


    /**
     * Gets the messaggioLogico value for this LogMessaggioType.
     * 
     * @return messaggioLogico
     */
    public it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType getMessaggioLogico() {
        return messaggioLogico;
    }


    /**
     * Sets the messaggioLogico value for this LogMessaggioType.
     * 
     * @param messaggioLogico
     */
    public void setMessaggioLogico(it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType messaggioLogico) {
        this.messaggioLogico = messaggioLogico;
    }


    /**
     * Gets the sortingField value for this LogMessaggioType.
     * 
     * @return sortingField
     */
    public java.lang.String getSortingField() {
        return sortingField;
    }


    /**
     * Sets the sortingField value for this LogMessaggioType.
     * 
     * @param sortingField
     */
    public void setSortingField(java.lang.String sortingField) {
        this.sortingField = sortingField;
    }


    /**
     * Gets the sortingDir value for this LogMessaggioType.
     * 
     * @return sortingDir
     */
    public java.lang.String getSortingDir() {
        return sortingDir;
    }


    /**
     * Sets the sortingDir value for this LogMessaggioType.
     * 
     * @param sortingDir
     */
    public void setSortingDir(java.lang.String sortingDir) {
        this.sortingDir = sortingDir;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogMessaggioType)) return false;
        LogMessaggioType other = (LogMessaggioType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData()))) &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato()))) &&
            ((this.canale==null && other.getCanale()==null) || 
             (this.canale!=null &&
              this.canale.equals(other.getCanale()))) &&
            ((this.utente==null && other.getUtente()==null) || 
             (this.utente!=null &&
              this.utente.equals(other.getUtente()))) &&
            ((this.messaggioLogico==null && other.getMessaggioLogico()==null) || 
             (this.messaggioLogico!=null &&
              this.messaggioLogico.equals(other.getMessaggioLogico()))) &&
            ((this.sortingField==null && other.getSortingField()==null) || 
             (this.sortingField!=null &&
              this.sortingField.equals(other.getSortingField()))) &&
            ((this.sortingDir==null && other.getSortingDir()==null) || 
             (this.sortingDir!=null &&
              this.sortingDir.equals(other.getSortingDir())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        if (getCanale() != null) {
            _hashCode += getCanale().hashCode();
        }
        if (getUtente() != null) {
            _hashCode += getUtente().hashCode();
        }
        if (getMessaggioLogico() != null) {
            _hashCode += getMessaggioLogico().hashCode();
        }
        if (getSortingField() != null) {
            _hashCode += getSortingField().hashCode();
        }
        if (getSortingDir() != null) {
            _hashCode += getSortingDir().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogMessaggioType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "LogMessaggioType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("canale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Canale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "CanaleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Utente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggioLogico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MessaggioLogico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "MessaggioLogicoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sortingField");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sortingField"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sortingDir");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sortingDir"));
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
