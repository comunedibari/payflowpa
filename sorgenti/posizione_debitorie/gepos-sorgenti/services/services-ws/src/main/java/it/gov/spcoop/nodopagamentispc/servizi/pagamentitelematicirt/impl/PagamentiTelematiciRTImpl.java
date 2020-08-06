package it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirt.impl;

import gov.telematici.pagamenti.ws.EsitoPaaInviaRT;
import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.PaaInviaRT;
import gov.telematici.pagamenti.ws.PaaInviaRTRisposta;
import gov.telematici.pagamenti.ws.TipoInviaEsitoStornoRisposta;
import gov.telematici.pagamenti.ws.TipoInviaRichiestaRevocaRisposta;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;

import it.gov.digitpa.schemas._2011.pagamenti.CtDatiSingoloPagamentoRT;
import it.gov.digitpa.schemas._2011.pagamenti.CtDatiVersamentoRT;
import it.gov.digitpa.schemas._2011.pagamenti.CtRicevutaTelematica;

import it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirt.PagamentiTelematiciRT;

import it.nch.pagamenti.nodopagamentispc.DataMismatchException;
import it.nch.pagamenti.nodopagamentispc.DataRichiestaRevocaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DatiRicevutaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DatiSingolaRevocaVO;
import it.nch.pagamenti.nodopagamentispc.DatiSingoloPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DomainNotFoundException;
import it.nch.pagamenti.nodopagamentispc.DuplicatedRptException;
import it.nch.pagamenti.nodopagamentispc.RTNotFoundException;
import it.nch.pagamenti.nodopagamentispc.RptNotFoundException;
import it.nch.pagamenti.nodopagamentispc.SemanticException;
import it.nch.pagamenti.nodopagamentispc.WrongRptException;


import it.tasgroup.idp.revoca.CtDatiSingolaRevoca;
import it.tasgroup.idp.revoca.CtRichiestaRevoca;

import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessLocal;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacadeLocal;
import it.tasgroup.iris.jaxb.util.JAXBMarshalling;
import it.tasgroup.iris.paymentws.dto.builder.GiornaleEventiExtDtoBuilder;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import org.apache.commons.lang.StringUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;

import java.sql.Timestamp;

import javax.ejb.EJB;

import javax.jws.WebService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;


@WebService(serviceName = "PagamentiTelematiciRTservice", endpointInterface = "it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirt.PagamentiTelematiciRT", targetNamespace = "http://NodoPagamentiSPC.spcoop.gov.it/servizi/PagamentiTelematiciRT", portName = "PPTPort")
public class PagamentiTelematiciRTImpl implements PagamentiTelematiciRT {
    private static final Logger log = LogManager.getLogger(PagamentiTelematiciRTImpl.class);
    private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance(
            "nodopagamenti.properties");
    @EJB(name = "DistintePagamentoFacade")
    private DistintePagamentoFacadeLocal distintePagamentoBean;
    @EJB(name = "GiornaleEventiBusiness")
    private GiornaleEventiBusinessLocal giornaleEventiBean;

    public PaaInviaRTRisposta paaInviaRT(PaaInviaRT bodyrichiesta,
        IntestazionePPT header) {
        log.info(
            "[PagamentiTelematiciRTImpl::paaInviaRT] Executing operation paaInviaRT");

        if (log.isInfoEnabled()) {
            String x = getStringValue(bodyrichiesta);
            log.info("*****paaInviaRT body ****\n " + x +
                "\n*********************");
            x = getStringValue(header);
            log.info("*****paaInviaRT header ****\n " + x +
                "\n*********************");
        }

        GiornaleEventiExtDTO g = GiornaleEventiExtDtoBuilder.createGiornaleEvDTO(bodyrichiesta,
                header);
        giornaleEventiBean.save(g);

        PaaInviaRTRisposta risposta = new PaaInviaRTRisposta();
        EsitoPaaInviaRT esito = new EsitoPaaInviaRT();
        esito.setEsito("OK");

        try {
            String rtXmlString = new String(bodyrichiesta.getRt());
            log.debug("[PagamentiTelematiciRTImpl::paaInviaRT] RT ricevuta");
            log.debug(rtXmlString);

            //
            // unmarshall di RT
            //
            JAXBElement<CtRicevutaTelematica> rtJaxb = (JAXBElement<CtRicevutaTelematica>) JAXBMarshalling.getObject("it.gov.digitpa.schemas._2011.pagamenti",
                    rtXmlString);
            CtRicevutaTelematica rt = rtJaxb.getValue();
            CtDatiVersamentoRT datiVersamento = rt.getDatiPagamento();

            //
            // creazione VO per richiesta di aggiornamento
            //
            DatiRicevutaPagamentoVO datiRicevuta = new DatiRicevutaPagamentoVO();
            datiRicevuta.setRiferimentoMessaggioRichiesta(rt.getRiferimentoMessaggioRichiesta());
            datiRicevuta.setRiferimentoDataRichiesta(rt.getRiferimentoDataRichiesta()
                                                       .toGregorianCalendar()
                                                       .getTime());
            datiRicevuta.setCodiceEsitoPagamento(datiVersamento.getCodiceEsitoPagamento());
            datiRicevuta.setIdentificativoUnivocoVersamento(datiVersamento.getIdentificativoUnivocoVersamento());
            datiRicevuta.setCodiceContestoPagamento(datiVersamento.getCodiceContestoPagamento());
            datiRicevuta.setIdentificativoFiscaleCreditore(rt.getDominio()
                                                             .getIdentificativoDominio());

            String codiceIdentificativoUnivocoPSP = rt.getIstitutoAttestante()
                                                      .getIdentificativoUnivocoAttestante()
                                                      .getCodiceIdentificativoUnivoco();
            String tipoIdentificativoPSP = rt.getIstitutoAttestante()
                                             .getIdentificativoUnivocoAttestante()
                                             .getTipoIdentificativoUnivoco()
                                             .name();
            String descrizionePSPattestante = rt.getIstitutoAttestante()
                                                .getDenominazioneAttestante();

            log.debug(
                "[PagamentiTelematiciRTImpl::paaInviaRT] RT ricevuta codiceIdentificativoUnivocoPSP = '" +
                codiceIdentificativoUnivocoPSP +
                "' , tipoIdentificativoPSP = '" + tipoIdentificativoPSP + "' ");

            datiRicevuta.setCodiceIdentificativoUnivocoPSP(codiceIdentificativoUnivocoPSP);
            datiRicevuta.setTipoIdentificativoPSP(tipoIdentificativoPSP);
            datiRicevuta.setDescrizionePSP(descrizionePSPattestante.toUpperCase());

            for (CtDatiSingoloPagamentoRT ctDatiSingoloPagamentoRT : datiVersamento.getDatiSingoloPagamento()) {
                DatiSingoloPagamentoVO datiSingoloPagamento = new DatiSingoloPagamentoVO();
                datiSingoloPagamento.setIdentificativoUnivocoRiscossione(ctDatiSingoloPagamentoRT.getIdentificativoUnivocoRiscossione());
                datiSingoloPagamento.setEsitoSingoloPagamento(ctDatiSingoloPagamentoRT.getEsitoSingoloPagamento());
                datiSingoloPagamento.setSingoloImportoPagato(ctDatiSingoloPagamentoRT.getSingoloImportoPagato());
                datiSingoloPagamento.setDataEsitoSingoloPagamento(ctDatiSingoloPagamentoRT.getDataEsitoSingoloPagamento()
                                                                                          .toGregorianCalendar()
                                                                                          .getTime());
                datiSingoloPagamento.setCommissioniApplicatePSP(ctDatiSingoloPagamentoRT.getCommissioniApplicatePSP());

                if (ctDatiSingoloPagamentoRT.getAllegatoRicevuta() != null) {
                    datiSingoloPagamento.setTipoAllegatoRicevuta(ctDatiSingoloPagamentoRT.getAllegatoRicevuta()
                                                                                         .getTipoAllegatoRicevuta()
                                                                                         .toString());
                    datiSingoloPagamento.setAllegatoRicevuta(ctDatiSingoloPagamentoRT.getAllegatoRicevuta()
                                                                                     .getTestoAllegato());

                    if ("BD".equals(ctDatiSingoloPagamentoRT.getAllegatoRicevuta()
                                                                .getTipoAllegatoRicevuta()
                                                                .toString())) {
                        String alleg = new String(ctDatiSingoloPagamentoRT.getAllegatoRicevuta()
                                                                          .getTestoAllegato());
                        datiSingoloPagamento.setDatiricevutaMBD(parseAllegatoMBD(
                                alleg));
                    }
                }

                datiRicevuta.addDatiSingoloVersamento(datiSingoloPagamento);
            }

            //
            // aggiornamento dati distinta e pagamenti
            //
            distintePagamentoBean.aggiornaEsitoDaRT(datiRicevuta);
            log.debug(
                "[PagamentiTelematiciRTImpl::paaInviaRT] Aggiornamento dati terminato con successo");
        } catch (DuplicatedRptException d) {
            esito.setEsito("KO");

            FaultBean fb = new FaultBean();
            esito.setFault(fb);
            fb.setFaultCode("PAA_RT_DUPLICATA");
            fb.setDescription("Dominio non censito");
            log.error(
                "[PagamentiTelematiciRTImpl::paaInviaRT] PAA_RT_DUPLICATA ");
        } catch (DomainNotFoundException d) {
            esito.setEsito("KO");

            FaultBean fb = new FaultBean();
            esito.setFault(fb);
            fb.setFaultCode("PAA_ID_DOMINIO_ERRATO");
            fb.setDescription("Dominio non censito");
            log.error(
                "[PagamentiTelematiciRTImpl::paaInviaRT] PAA_ID_DOMINIO_ERRATO");
        } catch (RptNotFoundException r) {
            esito.setEsito("KO");

            FaultBean fb = new FaultBean();
            esito.setFault(fb);
            fb.setFaultCode("PAA_RPT_SCONOSCIUTA");
            fb.setDescription("RPT non trovata");
            log.error(
                "[PagamentiTelematiciRTImpl::paaInviaRT] PAA_RPT_SCONOSCIUTA");
        } catch (Throwable e) {
            esito.setEsito("KO");

            FaultBean fb = new FaultBean();
            esito.setFault(fb);
            fb.setFaultCode("PAA_SYSTEM_ERROR");
            fb.setDescription("Errore generico");
            log.error("[PagamentiTelematiciRTImpl::paaInviaRT] PAA_SYSTEM_ERROR",
                e);
        }

        risposta.setPaaInviaRTRisposta(esito);
        g = GiornaleEventiExtDtoBuilder.createGiornaleEvDTO(risposta, g);
        giornaleEventiBean.save(g);

        if (log.isInfoEnabled()) {
            String x = getStringValue(risposta);
            log.info("*****paaInviaRT****\n " + x + "\n*********************");
        }

        log.info(
            "[PagamentiTelematiciRTImpl::paaInviaRT] operation paaInviaRT terminata");

        return risposta;
    }

    /**
     * @param allegatotestoMBD
     * @return
     * @throws Exception
     */
    private String parseAllegatoMBD(String allegatotestoMBD)
        throws Exception {

        String out = "";

        return out;
    }

    @Override
    public TipoInviaEsitoStornoRisposta paaInviaEsitoStorno(
        String identificativoIntermediarioPA,
        String identificativoStazioneIntermediarioPA,
        String identificativoDominio, String identificativoUnivocoVersamento,
        String codiceContestoPagamento, byte[] er) {
        // TODO Auto-generated method stub
        return null;
    }

    private String getStringValue(Object o) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            JAXBContext jaxbContext = null;
            Marshaller jaxbMarshaller = null; //jaxbContext.createMarshaller();

            if (o instanceof PaaInviaRT) {
                jaxbContext = JAXBContext.newInstance(PaaInviaRT.class);
                jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
                jaxbMarshaller.marshal(new JAXBElement<PaaInviaRT>(
                        new QName("uri", "local"), PaaInviaRT.class,
                        (PaaInviaRT) o), os);
            } else if (o instanceof IntestazionePPT) {
                jaxbContext = JAXBContext.newInstance(IntestazionePPT.class);
                jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
                jaxbMarshaller.marshal(new JAXBElement<IntestazionePPT>(
                        new QName("uri", "local"), IntestazionePPT.class,
                        (IntestazionePPT) o), os);
            } else if (o instanceof PaaInviaRTRisposta) {
                jaxbContext = JAXBContext.newInstance(PaaInviaRTRisposta.class);
                jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
                jaxbMarshaller.marshal(new JAXBElement<PaaInviaRTRisposta>(
                        new QName("uri", "local"), PaaInviaRTRisposta.class,
                        (PaaInviaRTRisposta) o), os);
            }

            return os.toString();
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public TipoInviaRichiestaRevocaRisposta paaInviaRichiestaRevoca(
        String identificativoDominio, String identificativoUnivocoVersamento,
        String codiceContestoPagamento, byte[] rr) {
        log.info(
            "[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] Executing operation paaInviaRichiestaRevoca");

        TipoInviaRichiestaRevocaRisposta risposta = new TipoInviaRichiestaRevocaRisposta();

        if (log.isInfoEnabled()) {
            log.info(
                "*****paaInviaRichiestaRevoca************** \n identificativoDominio = " +
                identificativoDominio + "\n" + " identificativoDominio = " +
                identificativoDominio + "\n" +
                " identificativoUnivocoVersamento = " +
                identificativoUnivocoVersamento + "\n" +
                " codiceContestoPagamento = " + codiceContestoPagamento + "\n" +
                "************************************************************");
        }

        GiornaleEventiExtDTO g = GiornaleEventiExtDtoBuilder.createGiornaleEvRichiestaRevocaDTO(identificativoDominio,
                identificativoUnivocoVersamento, codiceContestoPagamento, rr);
        giornaleEventiBean.save(g);

        if (conf.getBooleanProperty(
                    "nodopagamentispc.richiestaRevoca.unsupported")) {
            log.error(
                "[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] Revoca RT non supportata!!!");
            throw new RuntimeException("Revoca RT: Operazione non supportata");
        }

        try {
            String rrXmlString = new String(rr);
            log.debug(
                "[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] RR ricevuta");
            log.debug(rrXmlString);

            //
            // unmarshall di RT
            //
            JAXBElement<CtRichiestaRevoca> rrJaxb = (JAXBElement<CtRichiestaRevoca>) JAXBMarshalling.getObject("it.tasgroup.idp.revoca",
                    rrXmlString);
            CtRichiestaRevoca r = rrJaxb.getValue();

            if (!r.getDatiRevoca().getCodiceContestoPagamento()
                      .equals(codiceContestoPagamento) ||
                    !r.getDatiRevoca().getIdentificativoUnivocoVersamento()
                          .equals(identificativoUnivocoVersamento) ||
                    !r.getDominio().getIdentificativoDominio()
                          .equals(identificativoDominio)) {
                throw new DataMismatchException();
            }

            // riverso dati richiesta in un VO
            DataRichiestaRevocaPagamentoVO vo = new DataRichiestaRevocaPagamentoVO();
            vo.setIdentificativoDominio(identificativoDominio);
            vo.setCodiceContestoPagamento(codiceContestoPagamento);
            vo.setIdentificativoUnivocoVersamento(identificativoUnivocoVersamento);
            vo.setIdentificativoMessaggioRevoca(r.getIdentificativoMessaggioRevoca());
            vo.setTipoRevoca((r.getDatiRevoca().getTipoRevoca() != null)
                ? r.getDatiRevoca().getTipoRevoca() : "1");
            vo.setDataOraRichiestaRevoca(new Timestamp(
                    r.getDataOraMessaggioRevoca().toGregorianCalendar()
                     .getTimeInMillis()));

            for (CtDatiSingolaRevoca c : r.getDatiRevoca().getDatiSingolaRevoca()) {
                DatiSingolaRevocaVO d = new DatiSingolaRevocaVO();
                d.setCausaleRevoca(c.getCausaleRevoca());
                d.setDatiAggiuntiviRevoca(c.getDatiAggiuntiviRevoca());
                d.setIdentificativoUnivocoRiscossione(c.getIdentificativoUnivocoRiscossione());
                vo.getDatiRevoca().add(d);
            }

            distintePagamentoBean.manageRichiestaRevoca(vo);
            log.debug(
                "[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] Aggiornamento dati terminato con successo");
            // 
            risposta.setEsito("OK");
        } catch (JAXBException j) {
            risposta.setEsito("KO");

            FaultBean fault = new FaultBean();
            risposta.setFault(fault);
            fault.setFaultCode("PAA_SINTASSI_XSD");
            fault.setDescription("Sintassi XSD Errata");
            fault.setFaultString("Sintassi XSD Errata");
            log.error(
                "[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] PAA_SINTASSI_XSD ");
        } catch (DataMismatchException j) {
            risposta.setEsito("KO");

            FaultBean fault = new FaultBean();
            risposta.setFault(fault);
            fault.setFaultCode("PAA_SINTASSI_EXTRAXSD");
            fault.setDescription("Sintassi Extra XSD Errata");
            fault.setFaultString("Sintassi Extra XSD Errata");
            log.error(
                "[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] PAA_SINTASSI_EXTRAXSD ");
        } catch (RTNotFoundException r) {
            risposta.setEsito("KO");

            FaultBean fault = new FaultBean();
            risposta.setFault(fault);
            fault.setFaultCode("PAA_RT_SCONOSCIUTA");
            fault.setDescription("RT non trovata");
            fault.setFaultString("RT non trovata");
            log.error(
                "[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] PAA_RT_SCONOSCIUTA");
        } catch (SemanticException w) {
            risposta.setEsito("KO");

            FaultBean fault = new FaultBean();
            risposta.setFault(fault);
            fault.setFaultCode("PAA_SEMANTICA");

            if (StringUtils.isNotBlank(w.getReason())) {
                fault.setDescription(w.getReason());
                fault.setFaultString(w.getReason());
            } else {
                fault.setDescription(
                    "Importo Totale non coincidente con somma importi");
                fault.setFaultString(
                    "Importo Totale non coincidente con somma importi");
            }

            log.error("[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] PAA_SEMANTICA",
                w);
        } catch (Throwable e) {
            risposta.setEsito("KO");

            FaultBean fault = new FaultBean();
            risposta.setFault(fault);
            fault.setFaultCode("PAA_SEMANTICA");
            fault.setDescription("Impossibile revocare la RT");
            fault.setFaultString("Impossibile revocare la RT");
            log.error("[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] PAA_SEMANTICA",
                e);
        }

        g = GiornaleEventiExtDtoBuilder.createGiornaleEvRichiestaRevocaDTO(g,
                risposta.getEsito(), risposta.getFault());
        giornaleEventiBean.save(g);

        if (log.isInfoEnabled()) {
            log.info("*****paaInviaRichiestaRevoca****\n " + "esito = " +
                risposta.getEsito() + "\n*********************");
        }

        log.info(
            "[PagamentiTelematiciRTImpl::paaInviaRichiestaRevoca] operation paaInviaRichiestaRevoca terminata");

        return risposta;
    }
}
