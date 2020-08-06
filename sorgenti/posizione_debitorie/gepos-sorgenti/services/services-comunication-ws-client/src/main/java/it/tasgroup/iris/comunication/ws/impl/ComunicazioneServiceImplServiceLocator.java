/**
 * ComunicazioneServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.tasgroup.iris.comunication.ws.impl;

public class ComunicazioneServiceImplServiceLocator extends org.apache.axis.client.Service implements it.tasgroup.iris.comunication.ws.impl.ComunicazioneServiceImplService {

    public ComunicazioneServiceImplServiceLocator() {
    }


    public ComunicazioneServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ComunicazioneServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ComunicazioneServiceImplPort
    private java.lang.String ComunicazioneServiceImplPort_address = "http://localhost:8080/comunicationws/services";

    public java.lang.String getComunicazioneServiceImplPortAddress() {
        return ComunicazioneServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ComunicazioneServiceImplPortWSDDServiceName = "ComunicazioneServiceImplPort";

    public java.lang.String getComunicazioneServiceImplPortWSDDServiceName() {
        return ComunicazioneServiceImplPortWSDDServiceName;
    }

    public void setComunicazioneServiceImplPortWSDDServiceName(java.lang.String name) {
        ComunicazioneServiceImplPortWSDDServiceName = name;
    }

    public it.tasgroup.iris.comunication.ws.impl.ComunicationPortType getComunicazioneServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ComunicazioneServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getComunicazioneServiceImplPort(endpoint);
    }

    public it.tasgroup.iris.comunication.ws.impl.ComunicationPortType getComunicazioneServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.tasgroup.iris.comunication.ws.impl.ComunicazioneServiceImplServiceSoapBindingStub _stub = new it.tasgroup.iris.comunication.ws.impl.ComunicazioneServiceImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getComunicazioneServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setComunicazioneServiceImplPortEndpointAddress(java.lang.String address) {
        ComunicazioneServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.tasgroup.iris.comunication.ws.impl.ComunicationPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.tasgroup.iris.comunication.ws.impl.ComunicazioneServiceImplServiceSoapBindingStub _stub = new it.tasgroup.iris.comunication.ws.impl.ComunicazioneServiceImplServiceSoapBindingStub(new java.net.URL(ComunicazioneServiceImplPort_address), this);
                _stub.setPortName(getComunicazioneServiceImplPortWSDDServiceName());
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
        if ("ComunicazioneServiceImplPort".equals(inputPortName)) {
            return getComunicazioneServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "ComunicazioneServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "ComunicazioneServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ComunicazioneServiceImplPort".equals(portName)) {
            setComunicazioneServiceImplPortEndpointAddress(address);
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
