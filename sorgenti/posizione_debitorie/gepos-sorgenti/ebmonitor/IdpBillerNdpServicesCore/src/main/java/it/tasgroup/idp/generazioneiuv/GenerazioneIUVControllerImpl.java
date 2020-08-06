package it.tasgroup.idp.generazioneiuv;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.billerservices.util.NumeroAvvisoUtils;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.iuvgenerator.IUVGeneratorLocal;
import it.tasgroup.idp.util.EnumTipiErrori;
import it.tasgroup.idp.util.ServiceLocalMapper;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GenerazioneIUVControllerImpl implements GenerazioneIUVController {

	@PersistenceContext(unitName = ServiceLocalMapper.IdpBTJta)
	private EntityManager em;

	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(name = "IUVGenerator")
	private IUVGeneratorLocal iuvGenerator;

	@Override
	public GeneraLottoIUVResponseType generaLottoIUV(GeneraLottoIUVRequest parameters) {

		logger.info(" Inizio elaborazione generaLottoIUV");
		String codiceTributo = parameters.getTipoDebito();
		int dimensioneLotto = parameters.getDimensioneLotto();
		String codiceEnte = parameters.getIdentificativoDominio();
		GeneraLottoIUVResponseType response = new GeneraLottoIUVResponseType();

		try {
			GestoreGenerazioneIUV.validaRichiestaGenerazioneIUV(codiceEnte, codiceTributo, dimensioneLotto, em);
			TributiEnti trib = GestoreGenerazioneIUV.getTributoEnte(codiceEnte, codiceTributo, em);
			List<String> iuvList = iuvGenerator.IUVGeneratorByTributoEnte(trib, dimensioneLotto);
			GeneraLottoIUVResponseBodyType bodyResponse = creaRispostaLottoIUV(iuvList, codiceEnte, codiceTributo, dimensioneLotto, trib);
			response.setBody(bodyResponse);

		} catch (ValidationGenerazioneIUVException e) {
			logger.error("Errore durante la generazione di un lotto IUV", e);
			FaultType fault = new FaultType();
			fault.setFaultCode(e.getErrorCode().getKey());
			fault.setFaultDescription(e.getErrorCode().getDescription());
			fault.setFaultString(e.getErrorCode().getDescription());
			response.setFault(fault);
			// GeneraLottoIUVResponseBodyType bodyResponse = creaRispostaLottoIUV(null,codiceEnte,codiceTributo,dimensioneLotto,null);
			// response.setBody(bodyResponse);
		} catch (Exception e) {
			logger.error("Errore generico durante la generazione di lotto di IUV", e);
			FaultType fault = new FaultType();
			fault.setFaultCode(EnumTipiErrori.ERRORE_GENERICO.getKey());
			fault.setFaultDescription("Errore generico");
			fault.setFaultString(EnumTipiErrori.ERRORE_GENERICO.toString());
			response.setFault(fault);
			// GeneraLottoIUVResponseBodyType bodyResponse = creaRispostaLottoIUV(null,codiceEnte,codiceTributo,dimensioneLotto,null);
			// response.setBody(bodyResponse);
		}
		logger.info(" Fine elaborazione generaLottoIUV");
		return response;

	}

	@Override
	public GeneraIUVResponseType generaIUV(GeneraIUVRequest parameters) {
		logger.info(" Inizio elaborazione generaLottoIUV");
		String codiceTributo = parameters.getTipoDebito();
		int dimensioneLotto = 1;
		GeneraIUVResponseType response = new GeneraIUVResponseType();
		String codiceEnte = parameters.getIdentificativoDominio();

		try {
			GestoreGenerazioneIUV.validaRichiestaGenerazioneIUV(codiceEnte, codiceTributo, dimensioneLotto, em);
			TributiEnti trib = GestoreGenerazioneIUV.getTributoEnte(codiceEnte, codiceTributo, em);
			List<String> iuvList = iuvGenerator.IUVGeneratorByTributoEnte(trib, dimensioneLotto);
			GeneraIUVResponseBodyType responseBody = creaRispostaIUV(iuvList, codiceEnte, codiceTributo, dimensioneLotto, trib);
			response.setBody(responseBody);

		} catch (ValidationGenerazioneIUVException e) {
			logger.error("Errore durante la generazione di uno IUV", e);
			FaultType fault = new FaultType();
			fault.setFaultCode(e.getErrorCode().getKey());
			fault.setFaultDescription(e.getErrorCode().getDescription());
			fault.setFaultString(e.getErrorCode().getDescription());
			response.setFault(fault);
			// GeneraIUVResponseBodyType responseBody = creaRispostaIUV(null,codiceEnte,codiceTributo,dimensioneLotto,null);
			// response.setBody(responseBody);
		} catch (Exception e) {
			logger.error("Errore generico durante la generazione di uno IUV", e);
			FaultType fault = new FaultType();
			fault.setFaultCode(EnumTipiErrori.ERRORE_GENERICO.getKey());
			fault.setFaultDescription("Errore generico");
			fault.setFaultString(EnumTipiErrori.ERRORE_GENERICO.toString());
			response.setFault(fault);
			// GeneraIUVResponseBodyType responseBody = creaRispostaIUV(null,codiceEnte,codiceTributo,dimensioneLotto,null);
			// response.setBody(responseBody);
		}
		logger.info(" Fine elaborazione generaLottoIUV");
		return response;
	}

	private GeneraIUVResponseBodyType creaRispostaIUV(List<String> iuvList, String codiceEnte, String codiceTributo, int dimensioneLotto, TributiEnti trib) {

		GeneraIUVResponseBodyType bodyResponse = new GeneraIUVResponseBodyType();

		if (iuvList != null) {
			Iterator<String> iuvListIter = iuvList.iterator();
			String iuv = iuvListIter.next();
			ElencoIdentificativiType identificativo = new ElencoIdentificativiType();
			identificativo.setIdentificativoUnivocoVersamento(iuv);
			String numeroAvviso = NumeroAvvisoUtils.calculateNumeroAvviso(trib, iuv, false);
			identificativo.setNumeroAvviso(numeroAvviso);
			bodyResponse.setElencoIdentificativi(identificativo);
		}

		bodyResponse.setIdentificativoDominio(codiceEnte);
		bodyResponse.setTipoDebito(codiceTributo);
		return bodyResponse;
	}

	private GeneraLottoIUVResponseBodyType creaRispostaLottoIUV(List<String> iuvList, String codiceEnte, String codiceTributo, int dimensioneLotto, TributiEnti trib) {

		GeneraLottoIUVResponseBodyType bodyResponse = new GeneraLottoIUVResponseBodyType();

		if (iuvList != null) {
			Iterator<String> iuvListIter = iuvList.iterator();

			while (iuvListIter.hasNext()) {

				String iuv = iuvListIter.next();
				ElencoIdentificativiType identificativo = new ElencoIdentificativiType();
				identificativo.setIdentificativoUnivocoVersamento(iuv);
				String numeroAvviso = NumeroAvvisoUtils.calculateNumeroAvviso(trib, iuv, false);
				identificativo.setNumeroAvviso(numeroAvviso);
				bodyResponse.getElencoIdentificativi().add(identificativo);

			}

		}
		bodyResponse.setIdentificativoDominio(codiceEnte);
		bodyResponse.setTipoDebito(codiceTributo);
		bodyResponse.setDimensioneLotto(dimensioneLotto);
		return bodyResponse;
	}
}
