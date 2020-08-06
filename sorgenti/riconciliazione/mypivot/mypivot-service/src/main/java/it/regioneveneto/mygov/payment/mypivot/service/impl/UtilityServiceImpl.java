package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.ErrorTypeDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.StatoAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.service.UtilityService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

@Service
public class UtilityServiceImpl implements UtilityService {

//	@Autowired
//	private EnteDao enteDao;
	

	@Resource
	private Environment env;

	public List<ErrorTypeDto> getAllClassificazioni(boolean flgPagati, boolean flgTesoreria, String tipoVisualizzazione) {

		
		List<ErrorTypeDto> errorTypes = new ArrayList<ErrorTypeDto>();

		if (tipoVisualizzazione.equals(Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue())) {
			if (flgPagati && flgTesoreria) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUD_RT_IUF_TES,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUD_RT_IUF_TES));
			}

			if (flgTesoreria) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_IUF_TES,
						"mypivot.classificazioni." + Constants.COD_ERRORE_RT_IUF_TES));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_TES,
						"mypivot.classificazioni." + Constants.COD_ERRORE_RT_TES));
			}

			errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_IUF,
					"mypivot.classificazioni." + Constants.COD_ERRORE_RT_IUF));

			if (flgPagati) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUD_RT_IUF,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUD_RT_IUF));
			}
		} else if (tipoVisualizzazione.equals(Constants.TIPO_VISUALIZZAZIONE.ANOMALIE.getValue())) {
			if (flgPagati) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUD_NO_RT,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUD_NO_RT));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_NO_IUD,
						"mypivot.classificazioni." + Constants.COD_ERRORE_RT_NO_IUD));
			}

			errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_NO_IUF,
					"mypivot.classificazioni." + Constants.COD_ERRORE_RT_NO_IUF));
			errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUV_NO_RT,
					"mypivot.classificazioni." + Constants.COD_ERRORE_IUV_NO_RT));

			if (flgTesoreria) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUF_NO_TES,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUF_NO_TES));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUF_TES_DIV_IMP,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUF_TES_DIV_IMP));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_TES_NO_IUF_OR_IUV,
						"mypivot.classificazioni." + Constants.COD_ERRORE_TES_NO_IUF_OR_IUV));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_TES_NO_MATCH,
						"mypivot.classificazioni." + Constants.COD_ERRORE_TES_NO_MATCH));
			}
		}

		return errorTypes;
		
	}


	public List<StatoAccertamentoDto> getStatiAccertamento() {

		
		List<StatoAccertamentoDto> listaStatoAccertamento = new ArrayList<StatoAccertamentoDto>();


		listaStatoAccertamento.add(new StatoAccertamentoDto(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO,
					"mypivot.accertamento.stato." + Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO));

		listaStatoAccertamento.add(new StatoAccertamentoDto(Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO,
				"mypivot.accertamento.stato." + Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO));

		listaStatoAccertamento.add(new StatoAccertamentoDto(Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO,
				"mypivot.accertamento.stato." + Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO));

		return listaStatoAccertamento;
		
	}

	public Boolean verificaClassificazione(String codClass, boolean flgPagati, boolean flgTesoreria, String tipoVisualizzazione) {
		
		List<ErrorTypeDto> lista_c = getAllClassificazioni(flgPagati, flgTesoreria, tipoVisualizzazione);
		
		
		for(ErrorTypeDto item : lista_c) {
			if(item.getCod().equals(codClass))
				return true;
		}		
		return false;
	}
	

}
