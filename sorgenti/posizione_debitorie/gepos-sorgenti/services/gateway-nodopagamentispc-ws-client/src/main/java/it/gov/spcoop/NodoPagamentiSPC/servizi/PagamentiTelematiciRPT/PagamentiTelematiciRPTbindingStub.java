/**
 * PagamentiTelematiciRPTbindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT;

public class PagamentiTelematiciRPTbindingStub extends org.apache.axis.client.Stub implements it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPT {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[14];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediStatoRPT");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediStatoRPT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediStatoRPT"), gov.telematici.pagamenti.ws.NodoChiediStatoRPT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediStatoRPTRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediStatoRPTRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediListaPendentiRPT");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediListaPendentiRPT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediListaPendentiRPT"), gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediListaPendentiRPTRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPTRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediListaPendentiRPTRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoInviaRPT");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRPT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRPT"), gov.telematici.pagamenti.ws.NodoInviaRPT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", "intestazionePPT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", ">intestazionePPT"), gov.telematici.pagamenti.ws.ppthead.IntestazionePPT.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRPTRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoInviaRPTRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRPTRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoInviaCarrelloRPT");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaCarrelloRPT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaCarrelloRPT"), gov.telematici.pagamenti.ws.NodoInviaCarrelloRPT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", "intestazioneCarrelloPPT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", ">intestazioneCarrelloPPT"), gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaCarrelloRPTRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaCarrelloRPTRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediCopiaRT");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediCopiaRT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediCopiaRT"), gov.telematici.pagamenti.ws.NodoChiediCopiaRT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediCopiaRTRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediCopiaRTRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediInformativaPSP");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediInformativaPSP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediInformativaPSP"), gov.telematici.pagamenti.ws.NodoChiediInformativaPSP.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediInformativaPSPRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediInformativaPSPRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoPAChiediInformativaPA");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoPAChiediInformativaPA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoPAChiediInformativaPA"), gov.telematici.pagamenti.ws.NodoPAChiediInformativaPA.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoPAChiediInformativaPARisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoPAChiediInformativaPARisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoPAChiediInformativaPARisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediElencoQuadraturePA");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoQuadraturePA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoQuadraturePA"), gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePA.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoQuadraturePARisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePARisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoQuadraturePARisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediQuadraturaPA");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediQuadraturaPA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediQuadraturaPA"), gov.telematici.pagamenti.ws.NodoChiediQuadraturaPA.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediQuadraturaPARisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediQuadraturaPARisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediQuadraturaPARisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediElencoFlussiRendicontazione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoFlussiRendicontazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoFlussiRendicontazione"), gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazione.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoFlussiRendicontazioneRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoFlussiRendicontazioneRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediFlussoRendicontazione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediFlussoRendicontazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediFlussoRendicontazione"), gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazione.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediFlussoRendicontazioneRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediFlussoRendicontazioneRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoInviaRichiestaStorno");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRichiestaStorno"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRichiestaStorno"), gov.telematici.pagamenti.ws.NodoInviaRichiestaStorno.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRichiestaStornoRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoInviaRichiestaStornoRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRichiestaStornoRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoInviaRispostaRevoca");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRispostaRevoca"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRispostaRevoca"), gov.telematici.pagamenti.ws.NodoInviaRispostaRevoca.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRispostaRevocaRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRispostaRevocaRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodoChiediSceltaWISP");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediSceltaWISP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediSceltaWISP"), gov.telematici.pagamenti.ws.NodoChiediSceltaWISP.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediSceltaWISPRisposta"));
        oper.setReturnClass(gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediSceltaWISPRisposta"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

    }

    public PagamentiTelematiciRPTbindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public PagamentiTelematiciRPTbindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public PagamentiTelematiciRPTbindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", ">mustUnderstand");
            cachedSerQNames.add(qName);
            cls = boolean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", ">intestazioneCarrelloPPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", ">intestazionePPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.ppthead.IntestazionePPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/ppthead", "stText35");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "esitoChiediStatoRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.EsitoChiediStatoRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "faultBean");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.FaultBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "listaErroriRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.ListaErroriRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediCopiaRT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediCopiaRT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediCopiaRTRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoFlussiRendicontazione");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoFlussiRendicontazioneRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoQuadraturePA");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePA.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediElencoQuadraturePARisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePARisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediFlussoRendicontazione");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediFlussoRendicontazioneRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediInformativaPSP");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediInformativaPSP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediInformativaPSPRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediListaPendentiRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediListaPendentiRPTRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPTRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediQuadraturaPA");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediQuadraturaPA.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediQuadraturaPARisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediQuadraturaPARisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediSceltaWISP");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediSceltaWISP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediSceltaWISPRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediStatoRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediStatoRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoChiediStatoRPTRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaCarrelloRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoInviaCarrelloRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaCarrelloRPTRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRichiestaStorno");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoInviaRichiestaStorno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRichiestaStornoRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoInviaRichiestaStornoRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRispostaRevoca");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoInviaRispostaRevoca.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRispostaRevocaRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoInviaRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoInviaRPTRisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoInviaRPTRisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoPAChiediInformativaPA");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoPAChiediInformativaPA.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "nodoPAChiediInformativaPARisposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.NodoPAChiediInformativaPARisposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "risposta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.Risposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "stEffettuazioneScelta");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.StEffettuazioneScelta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "stPassword");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "stText35");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "stText40");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "stTipoVersamento");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.StTipoVersamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoElementoListaRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoElementoListaRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoElencoFlussiRendicontazione");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoElencoFlussiRendicontazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoIdQuadratura");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoIdQuadratura.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoIdRendicontazione");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoIdRendicontazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoListaQuadrature");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoListaQuadrature.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoListaRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoListaRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoListaRPTPendenti");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoListaRPTPendenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoRPTPendente");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoRPTPendente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoStoricoRPT");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoStoricoRPT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.pagamenti.telematici.gov/", "tipoStoricoVersamento");
            cachedSerQNames.add(qName);
            cls = gov.telematici.pagamenti.ws.TipoStoricoVersamento.class;
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

    public gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta nodoChiediStatoRPT(gov.telematici.pagamenti.ws.NodoChiediStatoRPT bodyrichiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediStatoRPT");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediStatoRPT"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPTRisposta nodoChiediListaPendentiRPT(gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPT bodyrichiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediListaPendentiRPT");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediListaPendentiRPT"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPTRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPTRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPTRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoInviaRPTRisposta nodoInviaRPT(gov.telematici.pagamenti.ws.NodoInviaRPT bodyrichiesta, gov.telematici.pagamenti.ws.ppthead.IntestazionePPT header) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoInviaRPT");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoInviaRPT"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta, header});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoInviaRPTRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoInviaRPTRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoInviaRPTRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta nodoInviaCarrelloRPT(gov.telematici.pagamenti.ws.NodoInviaCarrelloRPT bodyrichiesta, gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT header) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoInviaCarrelloRPT");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoInviaCarrelloRPT"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta, header});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta nodoChiediCopiaRT(gov.telematici.pagamenti.ws.NodoChiediCopiaRT bodyrichiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediCopiaRT");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediCopiaRT"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta nodoChiediInformativaPSP(gov.telematici.pagamenti.ws.NodoChiediInformativaPSP bodyrichiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediInformativaPSP");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediInformativaPSP"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoPAChiediInformativaPARisposta nodoPAChiediInformativaPA(gov.telematici.pagamenti.ws.NodoPAChiediInformativaPA bodyrichiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoPAChiediInformativaPA");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoPAChiediInformativaPA"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoPAChiediInformativaPARisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoPAChiediInformativaPARisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoPAChiediInformativaPARisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePARisposta nodoChiediElencoQuadraturePA(gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePA bodyrichiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediElencoQuadraturePA");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediElencoQuadraturePA"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePARisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePARisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePARisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoChiediQuadraturaPARisposta nodoChiediQuadraturaPA(gov.telematici.pagamenti.ws.NodoChiediQuadraturaPA parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediQuadraturaPA");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediQuadraturaPA"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediQuadraturaPARisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediQuadraturaPARisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediQuadraturaPARisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta nodoChiediElencoFlussiRendicontazione(gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazione parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediElencoFlussiRendicontazione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediElencoFlussiRendicontazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta nodoChiediFlussoRendicontazione(gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazione parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediFlussoRendicontazione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediFlussoRendicontazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoInviaRichiestaStornoRisposta nodoInviaRichiestaStorno(gov.telematici.pagamenti.ws.NodoInviaRichiestaStorno parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoInviaRichiestaStorno");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoInviaRichiestaStorno"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoInviaRichiestaStornoRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoInviaRichiestaStornoRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoInviaRichiestaStornoRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta nodoInviaRispostaRevoca(gov.telematici.pagamenti.ws.NodoInviaRispostaRevoca parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoInviaRispostaRevoca");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoInviaRispostaRevoca"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta nodoChiediSceltaWISP(gov.telematici.pagamenti.ws.NodoChiediSceltaWISP bodyrichiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("nodoChiediSceltaWISP");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "nodoChiediSceltaWISP"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bodyrichiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta) _resp;
            } catch (java.lang.Exception _exception) {
                return (gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta) org.apache.axis.utils.JavaUtils.convert(_resp, gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
