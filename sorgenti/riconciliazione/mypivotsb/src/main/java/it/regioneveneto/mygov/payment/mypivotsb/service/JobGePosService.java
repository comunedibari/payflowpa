package it.regioneveneto.mygov.payment.mypivotsb.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.regioneveneto.mygov.payment.mypivot.dao.FlussoExportDao;
import it.regioneveneto.mygov.payment.mypivot.dao.FlussoExportFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.dao.ImportExportRendicontazioneTesoreriaFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExport;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreria;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class JobGePosService {

//	@Autowired
//	private Environment environment;

	@Autowired
	private EnteService enteService;

	@Autowired
	private ImportExportRendicontazioneTesoreriaFunctionDao importExportRendicontazioneTesoreriaFunctionDao;

	@Autowired
	private FlussoExportFunctionDao flussoExportFunctionDao;

	@Autowired
	private FlussoExportDao flussoExportDao;

	//	@Autowired
	//	private EnteService enteService;

	@Value("${myPivot.geposEndpoint}")
	private String gepos_endpoint;

	@Value("${mypivot.geposServizio.attiva}")
	private String gepos_servizio_attiva;

	@Value("${mypivot.geposServizio.rt}")
	private String gepos_servizio_rt;

	@Value("${mypivot.geposServizio.riconcil}")
	private String gepos_servizio_riconcil;


	public void tryToSendRtToGePos() {

		List<EnteTO> enti_l = enteService.getAllEnti();

		if(!enti_l.isEmpty()) {

			for(EnteTO enteTO : enti_l) {


				List<FlussoExport> lista = flussoExportFunctionDao.
						get_flusso_export_to_send_gepos_function(enteTO.getId());

				if(!lista.isEmpty()) {

					for(FlussoExport item : lista) {

						String payload_send = prepare_payload_attiva_rt(item);

						log.info("IUV "+ item.getCodEDatiPagIdUnivocoVersamento() + " - Try to send Attiva RT...");

						RestTemplate restTemplate = new RestTemplate();

						String endPointGepos =  gepos_endpoint+gepos_servizio_attiva;

						String resp_attiva = restTemplate.postForObject(endPointGepos, payload_send, String.class);
						JSONObject attiva_obj = new JSONObject(resp_attiva);

						String esito = attiva_obj.getString("message");
						//				String items = attiva_obj.getString("items");

						log.info("IUV "+ item.getCodEDatiPagIdUnivocoVersamento() + " - Esito invio Attiva RT: " + esito);



						if(esito.equals("OK")) {

							log.info("Try to send RT...");

							endPointGepos =  gepos_endpoint+gepos_servizio_rt;

							String payload_send_rt = prepare_payload_rt(item);

							log.info("IUV "+ item.getCodEDatiPagIdUnivocoVersamento() + " - Try to send RT...");

							restTemplate = new RestTemplate();

							String resp_rt = restTemplate.postForObject(endPointGepos, payload_send_rt, String.class);
							JSONObject rt_obj = new JSONObject(resp_rt);

							esito = rt_obj.getString("message");
							//							items = rt_obj.getString("items");

							if(esito.equals("OK")) 
								item.setRtSend(true);

							log.info("Esito invio RT: " + esito);
						}

						else 
							log.info("Attivazione non riuscita: " + esito);

					}		
				}
			}
		}

	}


	private String prepare_payload_attiva_rt(FlussoExport item) {

		Map<String, String> elements = new HashMap<String, String>();
		ObjectMapper mapperObj = new ObjectMapper();

		Ente ente = item.getEnte();
		elements.put("identificativoDominio", ente.getCodiceFiscaleEnte());

		elements.put("identificativoUnivocoVersamento", item.getCodEDatiPagIdUnivocoVersamento());
		elements.put("codiceContestoPagamento", item.getCodEDatiPagCodiceContestoPagamento());
		elements.put("importoSingoloVersamento", String.valueOf(item.getNumEDatiPagDatiSingPagSingoloImportoPagato()));


		String jsonResp = "";

		try {
			jsonResp = mapperObj.writeValueAsString(elements);
			log.info("Payload send Attiva RT :" + jsonResp);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonResp;
	}


	private String prepare_payload_rt(FlussoExport item) {

		Map<String, String> elements = new HashMap<String, String>();
		ObjectMapper mapperObj = new ObjectMapper();

		Ente ente = item.getEnte();
		elements.put("identificativoDominio", ente.getCodiceFiscaleEnte());

		elements.put("identificativoUnivocoVersamento", item.getCodEDatiPagIdUnivocoVersamento());
		elements.put("codiceContestoPagamento", item.getCodEDatiPagCodiceContestoPagamento());
		elements.put("ricevutaTelematica", item.getBlbRtPaylaod());

		String jsonResp = "";

		try {
			jsonResp = mapperObj.writeValueAsString(elements);
			log.info("Payload send RT :" + jsonResp);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonResp;
	}


	public void tryToSendRiconcilToGePos(){

		List<ImportExportRendicontazioneTesoreria> lista = importExportRendicontazioneTesoreriaFunctionDao.
				get_import_export_rend_tes_to_send_gepos_function();

		if(!lista.isEmpty()) {

			for(ImportExportRendicontazioneTesoreria item : lista) {
				String iud_s = item.getCodiceIud();
				FlussoExport item_fe = flussoExportDao.findByCodIpaEnteIUD(item.getId().getCodiceIpaEnte(), iud_s);
				
				String payload_send = prepare_payload_riconcilazione(item,item_fe);

				log.info("IUV "+ item.getCodIuvKey() + " - Try to send Riconcilazione...");

				RestTemplate restTemplate = new RestTemplate();

				String endPointGepos =  gepos_endpoint+gepos_servizio_riconcil;

				String resp_riconcil = restTemplate.postForObject(endPointGepos, payload_send, String.class);
				JSONObject attiva_obj = new JSONObject(resp_riconcil);

				String esito = attiva_obj.getString("message");
				String items = attiva_obj.getString("items");

				if(esito.equals("OK")) { 	
					String class_s = item.getId().getClassificazioneCompletezza();
					if(class_s.contentEquals("RT_IUF") || class_s.contentEquals("IUD_RT_IUF") 
							|| (class_s.contentEquals("RT_IUF_TES") && !item_fe.getRppSend())
							|| (class_s.contentEquals("IUD_RT_IUF_TES") && !item_fe.getRppSend())				
							)
						item_fe.setRppSend(true);
					else
						item_fe.setGdcSend(true);
				}
				log.info("IUV "+ item.getCodIuvKey() + " - Esito invio Riconcilazione: " + esito + " - " + items);
			}		
		}
	}

	private String prepare_payload_riconcilazione(ImportExportRendicontazioneTesoreria item,FlussoExport item_fe)
	{
		
		Map<String, String> elements = new HashMap<String, String>();
		ObjectMapper mapperObj = new ObjectMapper();
		String data_s = "2099-12-31";
		
		String codIpa = item.getId().getCodiceIpaEnte();
		EnteTO ente = enteService.getByCodIpaEnte(codIpa);
		elements.put("identificativoDominio", ente.getCodiceFiscaleEnte());

		elements.put("identificativoUnivocoVersamento", item.getId().getCodiceIuv());
		elements.put("indiceVersamento", Integer.toString(item.getIndiceDatiSingoloPagamento()));

		elements.put("importoPagamento", item.getImportoTotalePagato());
		
		if(item.getDeDataEsecuzionePagamento().contentEquals("n/a")) {
			data_s = formatDataString(item.getDataOraMessaggioRicevuta());
			elements.put("dataPagamento", data_s);
		}
		else {
			data_s = formatDataString(item.getDeDataEsecuzionePagamento());
			elements.put("dataPagamento", data_s);
		}
		
		if(item.getCodiceIdentificativoUnivocoVersante().contentEquals(""))
			elements.put("codiceFiscaleVersante", item.getCodiceIdentificativoUnivocoPagatore());
		else
			elements.put("codiceFiscaleVersante", item.getCodiceIdentificativoUnivocoVersante());
		
		elements.put("identificativoUnivocoRiscossione", item.getId().getIdentificativoUnivocoRiscossione());
		elements.put("identificativoFlussoRiversamento", item.getId().getIdentificativoFlussoRendicontazione());
		
		if(item.getDeDataContabile().length() > 9) 
			data_s = formatDataString(item.getDeDataRegolamento());
		else
			data_s = "";
		elements.put("dataRegolamento", data_s);
		
		elements.put("trn", item.getIdentificativoUnivocoRegolamento());
		elements.put("importoRegolamento", item.getImportoTotalePagato());
		elements.put("riferimentoContabile", item.getCodDocumento());
		
		if(item.getDeDataContabile().length() > 9) 
			data_s = formatDataString(item.getDeDataContabile());
		else
			data_s = "";
			
		elements.put("dataContabile", data_s);
		
		elements.put("importoMovimentoContabile", item.getDeImporto());

		String class_s = item.getId().getClassificazioneCompletezza();
		if(class_s.contentEquals("RT_IUF") || class_s.contentEquals("IUD_RT_IUF") 
				|| (class_s.contentEquals("RT_IUF_TES") && !item_fe.getRppSend())
				|| (class_s.contentEquals("IUD_RT_IUF_TES") && !item_fe.getRppSend())				
				)
			elements.put("tipoNotifica", "R");
		else
			elements.put("tipoNotifica", "I");

		String jsonResp = "";

		try {
			jsonResp = mapperObj.writeValueAsString(elements);
			log.info("Payload send Riconcil :" + jsonResp);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonResp;
	}


	    private String formatDataString(String dateInString) {
	    
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
	        Date date = null;
			try {
				if(dateInString.length() > 10)
					date = sdf.parse(dateInString+".00");
				else
					date = sdf.parse(dateInString+" 00:00:00.00");
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
	        
//	        log.info("Current date in String Format: " + date);

	        SimpleDateFormat sdfDestination = new SimpleDateFormat();
	        sdfDestination.applyPattern("yyy-MM-dd");
	        
	        String strDate = sdfDestination.format(date);
	        
//	        log.info("Current date in Date Format: " + strDate);
	        return strDate;
	    }

}
