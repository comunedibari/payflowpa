package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface GestioneFlussiDao extends Dao<GestioneFlussi> {

	public GestioneFlussi getDistintaByMsgId(String idDistinta);

	public GestioneFlussi insertFlusso(GestioneFlussi gf);

	public GestioneFlussi getDistintaById(Long flussoId);

	public GestioneFlussi saveFlusso(GestioneFlussi flusso);

	public GestioneFlussi createDP(DistintePagamentoDTO dto);

	public List<GestioneFlussi> getGestioneFlussiAll(ContainerDTO inputDTO);

	public List<GestioneFlussi> getDistintaByCodTrasazione(String msgId);

	public GestioneFlussi getAnonymousPaymentByToken(String token);

	public GestioneFlussi aggiornamentoStatoFlusso(long idFlusso, StatiPagamentiIris stato, String tranId, String deOperazione, String tipoIdentifAttestante, String identifAttestante, String descrizAttestante );

	public GestioneFlussi getDistintePagamento(String codPagamento);

	public GestioneFlussi getDistintePagamento(String codPagamento, String idFlusso, String codPagante);

	public GestioneFlussi getDistintaPagamento(String codPagamento, String codTransazione, Date dataCreazione);

	public List<GestioneFlussi> getDistinteNDP(ContainerDTO containerDTO);


	public List<GestioneFlussi> getDistinteByFilterParameters(ContainerDTO containerDTO);

	public List<GestioneFlussi> getDistintaPagamento(ContainerDTO dtoIn);


	public GestioneFlussi getDistintaPagamentoByIdfiscCredIUVContpagamento(
			String idFiscaleCreditore, String IUV, String codContestoPagemento);

	public List<GestioneFlussi> getDistinteByIdCondizionePagamento(
			String idCondizionePagamento);

	public List<GestioneFlussi> getByCodPagamentoCodiceFiscale(String codPagamento, String codFiscale);
	
	public List<GestioneFlussi> getDistinteByIdGruppo(String idGruppo);

	public List<GestioneFlussi> getByCodPagamento(String codPagamento);
	
	public List<GestioneFlussi> getDistintaByIUVIdFiscCred(String idFiscaleCreditore, String IUV);
		
	public List<Map> readAnonymousPaymentByCondizione(String idCondizione);
	
	public boolean annullaOperatoreByIdDistinta(Long idDistinta) throws Exception;
}
