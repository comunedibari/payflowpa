/*
 * Created on 9-giu-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.pagamenti.creditcard;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Simone
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DisposizioneCartaCreditoVO implements Serializable {

	private String disposizione;
	private Integer idPagamento;
	private String businessService;
	private BigDecimal importoPagato;
	private Object pagamentoVO;
	private String descrizione;
	private String operatore;
	private DistintaCartaCreditoVO distinta;

	public boolean validaDisposizione() {

//		boolean ret = idPagamento != null && idPagamento > 0 
//			&& businessService != null && businessService.length() > 2 && importoPagato != null
//				&& importoPagato.compareTo(new BigDecimal(0)) > 0;
		boolean ret =  businessService != null && businessService.length() > 2 && importoPagato != null
			&& importoPagato.compareTo(new BigDecimal(0)) > 0;

		return ret;

	}

	/**
	 * @return
	 */
	public String getBusinessService() {
		return businessService;
	}

	/**
	 * @return
	 */
	public String getDisposizione() {
		return disposizione;
	}

	/**
	 * @return
	 */
	public Integer getIdPagamento() {
		return idPagamento;
	}

	/**
	 * @return
	 */
	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	/**
	 * @param string
	 */
	public void setBusinessService(String string) {
		businessService = string;
	}

	/**
	 * @param string
	 */
	public void setDisposizione(String string) {
		disposizione = string;
	}

	/**
	 * @param string
	 */
	public void setIdPagamento(Integer id) {
		idPagamento = id;
	}

	/**
	 * @param decimal
	 */
	public void setImportoPagato(BigDecimal decimal) {
		importoPagato = decimal;
	}

	/**
	 * @return
	 */
	public Object getPagamentoVO() {
		return pagamentoVO;
	}

	/**
	 * @param object
	 */
	public void setPagamentoVO(Object object) {
		pagamentoVO = object;
	}

	/**
	 * @return
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param string
	 */
	public void setDescrizione(String string) {
		descrizione = string;
	}

	/**
	 * @return
	 */
	public String getOperatore() {
		return operatore;
	}

	/**
	 * @param string
	 */
	public void setOperatore(String string) {
		operatore = string;
	}

	/**
	 * @return
	 */
	public DistintaCartaCreditoVO getDistinta() {
		return distinta;
	}

	/**
	 * @param creditoVO
	 */
	public void setDistinta(DistintaCartaCreditoVO creditoVO) {
		distinta = creditoVO;
	}

}
