package it.tasgroup.iris.business.ejb.delegherid;

import it.nch.is.fo.profilo.Intestatari;
import it.nch.profile.IProfileManager;
import it.nch.utility.GeneratoreIdUnivoci;
import it.tasgroup.iris.business.ejb.client.delegherid.DelegheRidBusinessLocal;
import it.tasgroup.iris.business.ejb.client.delegherid.DelegheRidBusinessRemote;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;
import it.tasgroup.iris.persistence.dao.interfaces.AllineamentiElettroniciArchiviDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.services.util.enumeration.EnumCausale;
import it.tasgroup.services.util.enumeration.EnumStatoAEA;
import it.tasgroup.services.util.enumeration.EnumStatoDP;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "DelegheRidBusiness")
public class DelegheRidBusinessBean implements DelegheRidBusinessLocal, DelegheRidBusinessRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(DelegheRidBusinessBean.class);


	@EJB(name = "AllineamentiElettroniciArchiviDaoImpl")
	AllineamentiElettroniciArchiviDao AEADAO;
	
//	@EJB(name = "DistintePagamentoDaoImpl")
//	DistintePagamentoDao DPDAO;
	
	@EJB(name = "IntestatariDaoImpl")
	IntestatariDAO intestatariDAO;

	@EJB(name = "GestioneFlussiDaoService")
	GestioneFlussiDao gestioneFlussiDao;
	
	@Override
	public List<AllineamentiElettroniciArchivi> readRichiestaDelegheByProfile(ContainerDTO inputDTO)
			throws Exception {
		String causale = EnumCausale.C_90211.getChiave();
		List<AllineamentiElettroniciArchivi> list = AEADAO.listAEAByIntestatario(inputDTO, causale);
		
		return list;
	}

	@Override
	public AllineamentiElettroniciArchivi verificaIbanDuplicato(String iban,String sottoscrittore)throws Exception {
		AllineamentiElettroniciArchivi list = AEADAO.verificaIbanDuplicato(iban,sottoscrittore);
		return list;
	}
	@Override
	public AllineamentiElettroniciArchivi verificaAbiDuplicato(String abi,String sottoscrittore)throws Exception {
		AllineamentiElettroniciArchivi list = AEADAO.verificaAbiDuplicato(abi,sottoscrittore);
		return list;
	}

	@Override
	public AllineamentiElettroniciArchivi readDelegaById(String id)
			throws Exception {
		return AEADAO.retriveAEAById(id);
	}

	@Override
	public AllineamentiElettroniciArchivi createRichiestaDelega(
			IProfileManager profile, AllineamentiElettroniciArchiviDTO dto)
			throws Exception {
		DistintePagamentoDTO dpdto = new DistintePagamentoDTO();
		
		dpdto.setStato(EnumStatoDP.IN_CORSO.getChiave());
		dpdto.setImporto(new BigDecimal("0.00"));
		dpdto.setImportoCommissione(new BigDecimal("0.00"));
		dpdto.setDataCreazione(new Timestamp(System.currentTimeMillis()));
		dpdto.setUtenteCreatore(dto.getCodificaFiscaleSottoscrittore());
		dpdto.setNumDisposizioni(new Integer(1));
//		dpdto.setCodCanale(EnumCanale.IRIS.getChiave());
//		dpdto.setCodDistinta(EnumDistinta.DELEGA_RID.getChiave());
		dpdto.setOperatorUsername(dto.getCodificaFiscaleSottoscrittore());
		
		
		String codice = GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
		System.out.println("codice generato tramite UUID:"+codice+" ("+codice.length()+")");
		dpdto.setCodTransazione(codice);

		dpdto.setIdCfgGateway(new Integer(10));
		
		GestioneFlussi dp = gestioneFlussiDao.createDP(dpdto);
		dto.setIdDistintaPagamento(dp.getId());
		
		dto.setCausale(EnumCausale.C_90211.getChiave());
		dto.setStato(EnumStatoAEA.IN_CORSO.getChiave());
		dto.setDescrizioneStato(EnumStatoAEA.IN_CORSO.getDescrizione());
		dto.setDataRichiesta(new Timestamp(System.currentTimeMillis()));
		dto.setTipoCodIndividuale("3");
		dto.setOperatoreUsername(dto.getCodificaFiscaleSottoscrittore());
//		dto.setAbiBancaAllineamento(CommonConstant.BT_ABI_CONTO_TECNICO);
		dto.setIndirizzoSottoscrittore(buildIndirizzoIntestatario(dto.getIntestatario()));
		
		String codRiferimento = GeneratoreIdUnivoci.GetCurrent().generaCodiceRiferimento("IRIS");
		dto.setCodRiferimento(codRiferimento);
		
		AllineamentiElettroniciArchivi aea = AEADAO.createAEA(dto);
		return aea;
	}

	@Override
	public AllineamentiElettroniciArchivi revocaDelega(IProfileManager profile,
			AllineamentiElettroniciArchiviDTO delegaToRevocatedto) throws Exception {

		// recupero delega che deve essere revocata
		AllineamentiElettroniciArchivi delegaToRevocate = AEADAO.getById(AllineamentiElettroniciArchivi.class, delegaToRevocatedto.getId());
		
		// creazione distinta pagamento
		DistintePagamentoDTO dpdto = new DistintePagamentoDTO();
		dpdto.setStato(EnumStatoDP.IN_CORSO.getChiave());
		dpdto.setImporto(new BigDecimal("0.00"));
		dpdto.setImportoCommissione(new BigDecimal("0.00"));
		dpdto.setDataCreazione(new Timestamp(System.currentTimeMillis()));
		dpdto.setUtenteCreatore(delegaToRevocate.getIdFiscaleSottoscrittore());
		dpdto.setNumDisposizioni(new Integer(1));
//		dpdto.setCodCanale(EnumCanale.IRIS.getChiave());
//		dpdto.setCodDistinta(EnumDistinta.REVOCA_RID.getChiave());
		dpdto.setOperatorUsername(delegaToRevocate.getIdFiscaleSottoscrittore());
		
		String codice = GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
		System.out.println("codice generato tramite UUID:"+codice+" ("+codice.length()+")");
		dpdto.setCodTransazione(codice);
		
		dpdto.setIdCfgGateway(new Integer(10));
		
		GestioneFlussi dp = gestioneFlussiDao.createDP(dpdto);

		AllineamentiElettroniciArchiviDTO revocadto = new AllineamentiElettroniciArchiviDTO();
		
		revocadto.setCittadino(delegaToRevocate.getFlagIniziativa().equals("C"));
		
		revocadto.setDataRichiesta(new Timestamp(System.currentTimeMillis()));
		revocadto.setSiaCreditore(delegaToRevocate.getSiaCreditore());
		revocadto.setProgressivo(new Integer(1));
		revocadto.setDataRichiestaRevoca(new Timestamp(System.currentTimeMillis()));
		revocadto.setCausale(EnumCausale.C_90218.getChiave());
		revocadto.setCodIndividuale(delegaToRevocate.getCodIndividuale());
		revocadto.setStato(EnumStatoAEA.IN_CORSO.getChiave());
		revocadto.setDescrizioneStato(EnumStatoAEA.IN_CORSO.getDescrizione());
		revocadto.setTipoCodIndividuale(delegaToRevocate.getTipoCodIndividuale());
		revocadto.setIbanAddebito(delegaToRevocate.getIbanAddebito());
		revocadto.setAbiBancaAllineamento(delegaToRevocate.getAbiBancaAllineamento());
		revocadto.setIntestatario(delegaToRevocatedto.getIntestatario());
		revocadto.setOperatoreUsername(delegaToRevocatedto.getCodificaFiscaleSottoscrittore());
		revocadto.setIdDistintaPagamento(dp.getId());
		
		revocadto.setCodRiferimento(delegaToRevocate.getCodRiferimento());
		
		// crea revoca
		AllineamentiElettroniciArchivi revoca = AEADAO.createAEA(revocadto);
		
		//EMA: nell'ultima versione diche lo stato sarà aggiornato dal gestore dei flussi
//		delegaToRevocate.setStato(EnumStatoAEA.IN_CORSO.getChiave());
//		delegaToRevocate.setDescrizioneStato(EnumStatoAEA.IN_CORSO.getDescrizione());
		delegaToRevocate.setRevoca(revoca);
//		// update delega da revocare
		delegaToRevocate = AEADAO.updateAEA(delegaToRevocate);
		
		return delegaToRevocate;
	}

	@Override
	public List<AllineamentiElettroniciArchivi> readDelegheAccettateByProfile(
			String sottoscrittore, String intestatario)
			throws Exception {

		if((sottoscrittore == null || sottoscrittore.equals("")) && (intestatario == null || intestatario.equals("")))
			return null;
		
		if(sottoscrittore == null || sottoscrittore.equals(""))
			return AEADAO.listAEAAccettateByIntestatario(intestatario);
		if(intestatario == null || intestatario.equals(""))
			return AEADAO.listAEAAccettateBySottoscrittore(sottoscrittore);
		
		return AEADAO.listAEAAccettateByIntestAndSottosc(intestatario, sottoscrittore);
		}

	private String buildIndirizzoIntestatario(String codIntestatario) throws Exception{
		Intestatari intestatario = intestatariDAO.getById(Intestatari.class, codIntestatario);
		StringBuffer indirizzo = new StringBuffer();
		// append indirizzo
		appendAddress(indirizzo, intestatario.getIndirizzipostaliobj().getAddressIForm(), 30);
		// append cap
		appendAddress(indirizzo, intestatario.getIndirizzipostaliobj().getCapCodeIForm(), 5);
		// append città provincia
		String citta = intestatario.getIndirizzipostaliobj().getCityIForm()!= null && intestatario.getIndirizzipostaliobj().getCityIForm().length() > 22 ?  
				intestatario.getIndirizzipostaliobj().getCityIForm().substring(0,22):intestatario.getIndirizzipostaliobj().getCityIForm();
					
		appendAddress(indirizzo, citta + " " + intestatario.getIndirizzipostaliobj().getProvinceIForm(), 25);
		return indirizzo.toString();
	}
	
	private static void appendAddress(StringBuffer indirizzo, String ind, int length){
		int lung = ind == null ? 0 : ind.length();
		
		if (lung >= length)
			indirizzo.append(ind.substring(0, length));
		
		else {
			ind = StringUtils.rightPad(ind, length," ");
			indirizzo.append(ind);
		}
	}
	
	
}
