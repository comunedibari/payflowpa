/**
 * ComunicazioneServiceImplServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.tasgroup.iris.comunication.ws.impl;

public class ComunicazioneServiceImplServiceSoapBindingStub extends org.apache.axis.client.Stub implements it.tasgroup.iris.comunication.ws.impl.ComunicationPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[11];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCanaliComunicazione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Utente"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteType"), it.tasgroup.iris.comunication.ws.impl.UtenteType.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteCanaleType"));
        oper.setReturnClass(it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "UtenteCanaleResponse"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sendMessageAgain");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Messaggio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "MessaggioType"), it.tasgroup.iris.comunication.ws.impl.MessaggioType[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCanaliConfigurazione");
        oper.setReturnType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "CanaleType"));
        oper.setReturnClass(it.tasgroup.iris.comunication.ws.impl.CanaleType[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "CanaleResponse"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("subscribeCanali");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Subscriber"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteType"), it.tasgroup.iris.comunication.ws.impl.UtenteType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TipoCanaleResponse"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "TipoCanaleType"), it.tasgroup.iris.comunication.ws.impl.TipoCanaleType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ParametroCanale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "ParametroCanaleType"), it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getTipoMessaggio");
        oper.setReturnType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "TipoMessaggioType"));
        oper.setReturnClass(it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "TipoMessaggioResponse"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateTipoMessaggio");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TipoMessaggio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "TipoMessaggioType"), it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("elencoLogMessaggi");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "searchLogMessaggi"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "SearchLogMessaggiType"), it.tasgroup.iris.comunication.ws.impl.SearchLogMessaggiType.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "LogMessaggioType"));
        oper.setReturnClass(it.tasgroup.iris.comunication.ws.impl.LogMessaggioType[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "LogMessaggiResponse"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validaConfigurazioneCanale");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Canale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "CanaleType"), it.tasgroup.iris.comunication.ws.impl.CanaleType.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "ValidoType"));
        oper.setReturnClass(it.tasgroup.iris.comunication.ws.impl.ValidoType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ValidoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("unsubscribeCanali");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Subscriber"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteType"), it.tasgroup.iris.comunication.ws.impl.UtenteType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "TipoCanaleResponse"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "TipoCanaleType"), it.tasgroup.iris.comunication.ws.impl.TipoCanaleType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ParametroCanale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "ParametroCanaleType"), it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateCanaliComunicazione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Canale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "CanaleType"), it.tasgroup.iris.comunication.ws.impl.CanaleType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sendMessage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "MessaggioLogico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "MessaggioLogicoType"), it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Utenti"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteType"), it.tasgroup.iris.comunication.ws.impl.UtenteType[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

    }

    public ComunicazioneServiceImplServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ComunicazioneServiceImplServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ComunicazioneServiceImplServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.1");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "CanaleType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.CanaleType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "LogMessaggioType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.LogMessaggioType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "MessaggioLogicoType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "MessaggioType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.MessaggioType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "ParametroCanaleType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "SearchLogMessaggiType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.SearchLogMessaggiType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "StatoCanaleType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.StatoCanaleType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "StatoMessaggioType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.StatoMessaggioType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "StatoTipoMessaggioType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.StatoTipoMessaggioType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "TipoCanaleType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.TipoCanaleType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "TipoMessaggioType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteCanaleType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "UtenteType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.UtenteType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "ValidoType");
            cachedSerQNames.add(qName);
            cls = it.tasgroup.iris.comunication.ws.impl.ValidoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType[] getCanaliComunicazione(it.tasgroup.iris.comunication.ws.impl.UtenteType utente) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/getCanaliComunicazione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "getCanaliComunicazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {utente});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void sendMessageAgain(it.tasgroup.iris.comunication.ws.impl.MessaggioType[] messaggio) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/sendMessageAgain");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "sendMessageAgain"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {messaggio});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.tasgroup.iris.comunication.ws.impl.CanaleType[] getCanaliConfigurazione() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/getCanaliConfigurazione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "getCanaliConfigurazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.tasgroup.iris.comunication.ws.impl.CanaleType[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.tasgroup.iris.comunication.ws.impl.CanaleType[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.tasgroup.iris.comunication.ws.impl.CanaleType[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void subscribeCanali(it.tasgroup.iris.comunication.ws.impl.UtenteType subscriber, it.tasgroup.iris.comunication.ws.impl.TipoCanaleType tipoCanaleResponse, it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType parametroCanale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/subscribeCanali");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "subscribeCanali"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {subscriber, tipoCanaleResponse, parametroCanale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType[] getTipoMessaggio() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/getTipoMessaggio");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "getTipoMessaggio"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void updateTipoMessaggio(it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType tipoMessaggio) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/updateTipoMessaggio");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "updateTipoMessaggio"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {tipoMessaggio});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.tasgroup.iris.comunication.ws.impl.LogMessaggioType[] elencoLogMessaggi(it.tasgroup.iris.comunication.ws.impl.SearchLogMessaggiType searchLogMessaggi) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/elencoLogMessaggi");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "elencoLogMessaggi"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {searchLogMessaggi});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.tasgroup.iris.comunication.ws.impl.LogMessaggioType[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.tasgroup.iris.comunication.ws.impl.LogMessaggioType[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.tasgroup.iris.comunication.ws.impl.LogMessaggioType[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.tasgroup.iris.comunication.ws.impl.ValidoType validaConfigurazioneCanale(it.tasgroup.iris.comunication.ws.impl.CanaleType canale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/validaConfigurazioneCanale");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "validaConfigurazioneCanale"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {canale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.tasgroup.iris.comunication.ws.impl.ValidoType) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.tasgroup.iris.comunication.ws.impl.ValidoType) org.apache.axis.utils.JavaUtils.convert(_resp, it.tasgroup.iris.comunication.ws.impl.ValidoType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void unsubscribeCanali(it.tasgroup.iris.comunication.ws.impl.UtenteType subscriber, it.tasgroup.iris.comunication.ws.impl.TipoCanaleType tipoCanaleResponse, it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType parametroCanale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/unsubscribeCanali");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "unsubscribeCanali"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {subscriber, tipoCanaleResponse, parametroCanale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void updateCanaliComunicazione(it.tasgroup.iris.comunication.ws.impl.CanaleType canale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/updateCanaliComunicazione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "updateCanaliComunicazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {canale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void sendMessage(it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType messaggioLogico, it.tasgroup.iris.comunication.ws.impl.UtenteType[] utenti) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://localhost:8080/comunicationws/services/sendMessage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://impl.ws.comunication.iris.tasgroup.it/", "sendMessage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {messaggioLogico, utenti});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }
}