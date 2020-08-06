/**
 * FaultBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class FaultBean  implements java.io.Serializable {
    private java.lang.String faultCode;

    private java.lang.String faultString;

    private java.lang.String id;

    private java.lang.String description;

    private java.lang.Integer serial;

    private java.lang.String originalFaultCode;

    private java.lang.String originalFaultString;

    private java.lang.String originalDescription;

    public FaultBean() {
    }

    public FaultBean(
           java.lang.String faultCode,
           java.lang.String faultString,
           java.lang.String id,
           java.lang.String description,
           java.lang.Integer serial,
           java.lang.String originalFaultCode,
           java.lang.String originalFaultString,
           java.lang.String originalDescription) {
           this.faultCode = faultCode;
           this.faultString = faultString;
           this.id = id;
           this.description = description;
           this.serial = serial;
           this.originalFaultCode = originalFaultCode;
           this.originalFaultString = originalFaultString;
           this.originalDescription = originalDescription;
    }


    /**
     * Gets the faultCode value for this FaultBean.
     * 
     * @return faultCode
     */
    public java.lang.String getFaultCode() {
        return faultCode;
    }


    /**
     * Sets the faultCode value for this FaultBean.
     * 
     * @param faultCode
     */
    public void setFaultCode(java.lang.String faultCode) {
        this.faultCode = faultCode;
    }


    /**
     * Gets the faultString value for this FaultBean.
     * 
     * @return faultString
     */
    public java.lang.String getFaultString() {
        return faultString;
    }


    /**
     * Sets the faultString value for this FaultBean.
     * 
     * @param faultString
     */
    public void setFaultString(java.lang.String faultString) {
        this.faultString = faultString;
    }


    /**
     * Gets the id value for this FaultBean.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this FaultBean.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the description value for this FaultBean.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this FaultBean.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the serial value for this FaultBean.
     * 
     * @return serial
     */
    public java.lang.Integer getSerial() {
        return serial;
    }


    /**
     * Sets the serial value for this FaultBean.
     * 
     * @param serial
     */
    public void setSerial(java.lang.Integer serial) {
        this.serial = serial;
    }


    /**
     * Gets the originalFaultCode value for this FaultBean.
     * 
     * @return originalFaultCode
     */
    public java.lang.String getOriginalFaultCode() {
        return originalFaultCode;
    }


    /**
     * Sets the originalFaultCode value for this FaultBean.
     * 
     * @param originalFaultCode
     */
    public void setOriginalFaultCode(java.lang.String originalFaultCode) {
        this.originalFaultCode = originalFaultCode;
    }


    /**
     * Gets the originalFaultString value for this FaultBean.
     * 
     * @return originalFaultString
     */
    public java.lang.String getOriginalFaultString() {
        return originalFaultString;
    }


    /**
     * Sets the originalFaultString value for this FaultBean.
     * 
     * @param originalFaultString
     */
    public void setOriginalFaultString(java.lang.String originalFaultString) {
        this.originalFaultString = originalFaultString;
    }


    /**
     * Gets the originalDescription value for this FaultBean.
     * 
     * @return originalDescription
     */
    public java.lang.String getOriginalDescription() {
        return originalDescription;
    }


    /**
     * Sets the originalDescription value for this FaultBean.
     * 
     * @param originalDescription
     */
    public void setOriginalDescription(java.lang.String originalDescription) {
        this.originalDescription = originalDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FaultBean)) return false;
        FaultBean other = (FaultBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.faultCode==null && other.getFaultCode()==null) || 
             (this.faultCode!=null &&
              this.faultCode.equals(other.getFaultCode()))) &&
            ((this.faultString==null && other.getFaultString()==null) || 
             (this.faultString!=null &&
              this.faultString.equals(other.getFaultString()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.serial==null && other.getSerial()==null) || 
             (this.serial!=null &&
              this.serial.equals(other.getSerial()))) &&
            ((this.originalFaultCode==null && other.getOriginalFaultCode()==null) || 
             (this.originalFaultCode!=null &&
              this.originalFaultCode.equals(other.getOriginalFaultCode()))) &&
            ((this.originalFaultString==null && other.getOriginalFaultString()==null) || 
             (this.originalFaultString!=null &&
              this.originalFaultString.equals(other.getOriginalFaultString()))) &&
            ((this.originalDescription==null && other.getOriginalDescription()==null) || 
             (this.originalDescription!=null &&
              this.originalDescription.equals(other.getOriginalDescription())));
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
        if (getFaultCode() != null) {
            _hashCode += getFaultCode().hashCode();
        }
        if (getFaultString() != null) {
            _hashCode += getFaultString().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getSerial() != null) {
            _hashCode += getSerial().hashCode();
        }
        if (getOriginalFaultCode() != null) {
            _hashCode += getOriginalFaultCode().hashCode();
        }
        if (getOriginalFaultString() != null) {
            _hashCode += getOriginalFaultString().hashCode();
        }
        if (getOriginalDescription() != null) {
            _hashCode += getOriginalDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FaultBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "faultBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultString");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faultString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originalFaultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "originalFaultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originalFaultString");
        elemField.setXmlName(new javax.xml.namespace.QName("", "originalFaultString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originalDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "originalDescription"));
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
