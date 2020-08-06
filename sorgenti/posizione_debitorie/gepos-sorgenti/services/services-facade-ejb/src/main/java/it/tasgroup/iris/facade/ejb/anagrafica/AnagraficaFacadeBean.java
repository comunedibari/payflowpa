package it.tasgroup.iris.facade.ejb.anagrafica;

import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.Operatori;
import it.tasgroup.iris.business.ejb.client.IrisCacheSingletonLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.OperatoriBusinessLocal;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.Province;
import it.tasgroup.iris.dto.anagrafica.ContoTecnicoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.ProvinceDTO;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaFacadeRemote;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "AnagraficaFacade")
public class AnagraficaFacadeBean implements AnagraficaFacadeLocal, AnagraficaFacadeRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(AnagraficaFacadeBean.class);	
	
	@EJB(name = "IntestatariBusiness")
	private IntestatariBusinessLocal intestatariBusinessBean;

	@EJB(name = "OperatoriBusiness")
	private OperatoriBusinessLocal operatoriBusinessBean;
	
	@EJB
	private IrisCacheSingletonLocal irisCacheSingletonBean;
	
	
	@Override
	public IntestatarioDTO readIntestatario(String idFiscale) {
		
		Intestatari intestatario = intestatariBusinessBean.readIntestatarioByLapl(idFiscale, true);
		
		IntestatarioDTO intestatarioDTO = AnagraficaDTOBuilder.fillIntestatarioIndirizzoDTO(intestatario);
		
		return intestatarioDTO;
			
	}
	
	public ContoTecnicoDTO readContoTecnico(){
		
		ContoTecnico contoTecnico = irisCacheSingletonBean.getContoTecnico();
		
		ContoTecnicoDTO contoTecnicoDTO = AnagraficaDTOBuilder.fillContoTecnicoDTO(contoTecnico);
		
		return contoTecnicoDTO;
		
	}
	
	public IntestatarioDTO readAnagraficaRT(){
		
		Intestatari intestatario = irisCacheSingletonBean.getAnagraficaRT();
		
		IntestatarioDTO intestatarioDTO = AnagraficaDTOBuilder.fillIntestatarioIndirizzoDTO(intestatario);
		
		return intestatarioDTO;
		
	}
	
	@Override
	public List<ProvinceDTO> readListaProvince() {
		
		List<Province> province = irisCacheSingletonBean.getProvinceItaliane();
		
		List<ProvinceDTO> provinceDTO = AnagraficaDTOBuilder.fillListaProvinceDTO(province);
		
		return provinceDTO;

	}
	
	
	@Override
	public String getChiaveOperatoreGestoreComunicazioni(
			String codiceFiscaleOperatore, String tipoOperatore) {

		String result=null;
		
		Operatori oper = operatoriBusinessBean.getOperatoreByCodFiscale(codiceFiscaleOperatore);
		Intestatari inte = intestatariBusinessBean.readIntestatarioByLapl(codiceFiscaleOperatore, false);
		
		if (oper!=null && inte!=null) {
			if ("S".equals(inte.getFlagComunicazioni())) {
				result=inte.getCorporate()+"-"+oper.getOperatore()+"-"+tipoOperatore;  // Il tipo operatore non può che valere OP se si fa un pagamento.
			}
		}
		
		return result;
		
	}
	
	@Override
	public IntestatarioDTO readIntestatarioById(String intestatario){
		
		Intestatari inte = intestatariBusinessBean.readIntestatarioById(intestatario);
	
		IntestatarioDTO intestatarioDTO = AnagraficaDTOBuilder.fillIntestatarioIndirizzoDTO(inte);
		
		return intestatarioDTO;
	}
	
}

