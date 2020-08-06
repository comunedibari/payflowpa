/**
 * StTipoVersamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.telematici.pagamenti.ws;

public class StTipoVersamento implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected StTipoVersamento(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BBT = "BBT";
    public static final java.lang.String _BP = "BP";
    public static final java.lang.String _AD = "AD";
    public static final java.lang.String _CP = "CP";
    public static final java.lang.String _PO = "PO";
    public static final java.lang.String _OBEP = "OBEP";
    public static final StTipoVersamento BBT = new StTipoVersamento(_BBT);
    public static final StTipoVersamento BP = new StTipoVersamento(_BP);
    public static final StTipoVersamento AD = new StTipoVersamento(_AD);
    public static final StTipoVersamento CP = new StTipoVersamento(_CP);
    public static final StTipoVersamento PO = new StTipoVersamento(_PO);
    public static final StTipoVersamento OBEP = new StTipoVersamento(_OBEP);
    public java.lang.String getValue() { return _value_;}
    public static StTipoVersamento fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        StTipoVersamento enumeration = (StTipoVersamento)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static StTipoVersamento fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StTipoVersamento.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "stTipoVersamento"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
