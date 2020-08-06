package it.tasgroup.iris.facade.ejb.restapi;

import it.tasgroup.idp.rs.enums.EnumModelloPagamento;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;
import it.tasgroup.idp.rs.model.CondizionePagamento;
import it.tasgroup.idp.rs.model.InfoAperturaPagamento;
import it.tasgroup.idp.rs.model.PrestatoreServizio;
import it.tasgroup.idp.rs.model.ProcessoPagamento;
import it.tasgroup.idp.rs.model.RichiestaAperturaSessioneGateway;
import it.tasgroup.idp.rs.model.RispostaAperturaSessioneGateway;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.GatewayAuthenticationException;
import it.tasgroup.iris.dto.exception.InvalidInputException;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public interface PaymentApiFacade {

    public List<CondizionePagamento> getCondizioniFull(String idCreditore, String enumTipoDebito, String idDebito, String idPagamento, String identificativoUnivocoVersamento, String codiceFiscaleDebitore, EnumStatoPagamento enumStatoPagamento, String flagIncasso, String dataOraPagamento);

    public List<CondizionePagamento> getCondizioni(String iuv, String codEnte, String codCategoriaTributo, String codiceFiscale) throws BusinessConstraintException;

    public List<PrestatoreServizio> getListaPSP(EnumTipoVersamento tipoVersamento,
                                                List<EnumModelloPagamento> modelloPagamentoList,
                                                List<String> idCondizionePagamentoList) throws Exception;

    public ProcessoPagamento getProcessoPagamento(String codPagamento, String codiceFiscale);

    ProcessoPagamento paga(InfoAperturaPagamento infoAperturaPagamento) throws InvalidInputException;

    public List<EnumStatoPagamentoCondizione> getStatiCondizioni(List<String> ids);

    CondizionePagamento getCondizioneQRCode(String codFiscaleCreditore, String idPagamentoEnte, BigDecimal importo);

	RispostaAperturaSessioneGateway aperturaSessioneGateway(RichiestaAperturaSessioneGateway richiestaAperturaDTO)
			throws GatewayAuthenticationException;
}
