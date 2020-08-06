/**
 * TipoMessaggioType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.tasgroup.iris.comunication.ws.impl;

public class TipoMessaggioType  implements java.io.Serializable {
    private java.math.BigInteger id;

    private it.tasgroup.iris.comunication.ws.impl.StatoTipoMessaggioType stato;

    private java.lang.String denominazione;

    private java.lang.String sortingField;

    private java.lang.String sortingDir;

    public TipoMessaggioType() {
    }

    public TipoMessaggioType(
           java.math.BigInteger id,
           it.tasgroup.iris.comunication.ws.impl.StatoTipoMessaggioType stato,
           java.lang.String denominazione,
           java.lang.String sortingField,
           java.lang.String sortingDir) {
           this.id = id;
           this.stato = stato;
           this.denominazione = denominazione;
           this.sortingField = sortingField;
           this.sortingDir = sortingDir;
    }


    /**
     * Gets the id value for this TipoMessaggioType.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this TipoMessaggioType.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the stato value for this TipoMessaggioType.
     * 
     * @return stato
     */
    public it.tasgroup.iris.comunication.ws.impl.StatoTipoMessaggioType getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this TipoMessaggioType.
     * 
     * @param stato
     */
    public void setStato(it.tasgroup.iris.comunication.ws.impl.StatoTipoMessaggioType stato) {
        this.stato = stato;
    }


    /**
     * Gets the denominazione value for this TipoMessaggioType.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this TipoMessaggioType.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the sortingField value for this TipoMessaggioType.
     * 
     * @return sortingField
     */
    public java.lang.String getSortingField() {
        return sortingField;
    }


    /**
     * Sets the sortingField value for this TipoMessaggioType.
     * 
     * @param sortingField
     */
    public void setSortingField(java.lang.String sortingField) {
        this.sortingField = sortingField;
    }


    /**
     * Gets the sortingDir value for this TipoMessaggioType.
     * 
     * @return sortingDir
     */
    public java.lang.String getSortingDir() {
        return sortingDir;
    }


    /**
     * Sets the sortingDir value for this TipoMessaggioType.
     * 
     * @param sortingDir
     */
    public void setSortingDir(java.lang.String sortingDir) {
        this.sortingDir = sortingDir;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoMessaggioType)) return false;
        TipoMessaggioType other = (TipoMessaggioType) obj;
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
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
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
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
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
        new org.apache.axis.description.TypeDesc(TipoMessaggioType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "TipoMessaggioType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "StatoTipoMessaggioType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
