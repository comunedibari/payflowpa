/**
 * PagamentiTelematiciRPTserviceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT;

public class PagamentiTelematiciRPTserviceLocator extends org.apache.axis.client.Service implements it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTservice {

    public PagamentiTelematiciRPTserviceLocator() {
    }


    public PagamentiTelematiciRPTserviceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PagamentiTelematiciRPTserviceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PagamentiTelematiciRPTPort
    private java.lang.String PagamentiTelematiciRPTPort_address = "http://NodoPagamentiSPC.spcoop.gov.it/";

    public java.lang.String getPagamentiTelematiciRPTPortAddress() {
        return PagamentiTelematiciRPTPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PagamentiTelematiciRPTPortWSDDServiceName = "PagamentiTelematiciRPTPort";

    public java.lang.String getPagamentiTelematiciRPTPortWSDDServiceName() {
        return PagamentiTelematiciRPTPortWSDDServiceName;
    }

    public void setPagamentiTelematiciRPTPortWSDDServiceName(java.lang.String name) {
        PagamentiTelematiciRPTPortWSDDServiceName = name;
    }

    public it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPT getPagamentiTelematiciRPTPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PagamentiTelematiciRPTPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPagamentiTelematiciRPTPort(endpoint);
    }

    public it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPT getPagamentiTelematiciRPTPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTbindingStub _stub = new it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTbindingStub(portAddress, this);
            _stub.setPortName(getPagamentiTelematiciRPTPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPagamentiTelematiciRPTPortEndpointAddress(java.lang.String address) {
        PagamentiTelematiciRPTPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPT.class.isAssignableFrom(serviceEndpointInterface)) {
                it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTbindingStub _stub = new it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTbindingStub(new java.net.URL(PagamentiTelematiciRPTPort_address), this);
                _stub.setPortName(getPagamentiTelematiciRPTPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("PagamentiTelematiciRPTPort".equals(inputPortName)) {
            return getPagamentiTelematiciRPTPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://NodoPagamentiSPC.spcoop.gov.it/servizi/PagamentiTelematiciRPT", "PagamentiTelematiciRPTservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://NodoPagamentiSPC.spcoop.gov.it/servizi/PagamentiTelematiciRPT", "PagamentiTelematiciRPTPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PagamentiTelematiciRPTPort".equals(portName)) {
            setPagamentiTelematiciRPTPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
