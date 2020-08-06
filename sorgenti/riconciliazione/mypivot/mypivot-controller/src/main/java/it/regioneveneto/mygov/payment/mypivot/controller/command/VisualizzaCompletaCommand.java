/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller.command;

public class VisualizzaCompletaCommand extends VisualizzaCommand {

	private String conto;
	private String data_contabile_da;
	private String data_contabile_a;
	private String data_valuta_da;
	private String data_valuta_a;
	private String importo;
	private String codOr1;
	private String data_valuta_check;
	private String data_esito_check;
	private String data_contabile_check;
	private String data_regolamento_check;
	private String data_esecuzione_check;
	private String data_esecuzione_singolo_pagamento_da;
	private String data_esecuzione_singolo_pagamento_a;
	private String tipoErrore;
	private String data_ultimo_aggiornamento_check;
	private String data_ultimo_aggiornamento_da;
	private String data_ultimo_aggiornamento_a;
	private String tipoVisualizzazione;

	public String getTipoErrore() {
		return tipoErrore;
	}

	public void setTipoErrore(String tipoErrore) {
		this.tipoErrore = tipoErrore;
	}

	public String getData_valuta_check() {
		return data_valuta_check;
	}

	public void setData_valuta_check(String data_valuta_check) {
		this.data_valuta_check = data_valuta_check;
	}

	public String getData_esito_check() {
		return data_esito_check;
	}

	public void setData_esito_check(String data_esito_check) {
		this.data_esito_check = data_esito_check;
	}

	public String getData_contabile_check() {
		return data_contabile_check;
	}

	public void setData_contabile_check(String data_contabile_check) {
		this.data_contabile_check = data_contabile_check;
	}

	public String getData_regolamento_check() {
		return data_regolamento_check;
	}

	public void setData_regolamento_check(String data_regolamento_check) {
		this.data_regolamento_check = data_regolamento_check;
	}

	public String getCodOr1() {
		return codOr1;
	}

	public void setCodOr1(String or1_code) {
		this.codOr1 = or1_code;
	}

	public String getConto() {
		return conto;
	}

	public void setConto(String conto) {
		this.conto = conto;
	}

	public String getData_contabile_da() {
		return data_contabile_da;
	}

	public void setData_contabile_da(String data_contabile_da) {
		this.data_contabile_da = data_contabile_da;
	}

	public String getData_contabile_a() {
		return data_contabile_a;
	}

	public void setData_contabile_a(String data_contabile_a) {
		this.data_contabile_a = data_contabile_a;
	}

	public String getData_valuta_da() {
		return data_valuta_da;
	}

	public void setData_valuta_da(String data_valuta_da) {
		this.data_valuta_da = data_valuta_da;
	}

	public String getData_valuta_a() {
		return data_valuta_a;
	}

	public void setData_valuta_a(String data_valuta_a) {
		this.data_valuta_a = data_valuta_a;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getData_esecuzione_check() {
		return data_esecuzione_check;
	}

	public void setData_esecuzione_check(String data_esecuzione_check) {
		this.data_esecuzione_check = data_esecuzione_check;
	}

	public String getData_esecuzione_singolo_pagamento_da() {
		return data_esecuzione_singolo_pagamento_da;
	}

	public void setData_esecuzione_singolo_pagamento_da(String data_esecuzione_singolo_pagamento_da) {
		this.data_esecuzione_singolo_pagamento_da = data_esecuzione_singolo_pagamento_da;
	}

	public String getData_esecuzione_singolo_pagamento_a() {
		return data_esecuzione_singolo_pagamento_a;
	}

	public void setData_esecuzione_singolo_pagamento_a(String data_esecuzione_singolo_pagamento_a) {
		this.data_esecuzione_singolo_pagamento_a = data_esecuzione_singolo_pagamento_a;
	}

	public String getData_ultimo_aggiornamento_check() {
		return data_ultimo_aggiornamento_check;
	}

	public void setData_ultimo_aggiornamento_check(String data_ultimo_aggiornamento_check) {
		this.data_ultimo_aggiornamento_check = data_ultimo_aggiornamento_check;
	}

	public String getData_ultimo_aggiornamento_da() {
		return data_ultimo_aggiornamento_da;
	}

	public void setData_ultimo_aggiornamento_da(String data_ultimo_aggiornamento_da) {
		this.data_ultimo_aggiornamento_da = data_ultimo_aggiornamento_da;
	}

	public String getData_ultimo_aggiornamento_a() {
		return data_ultimo_aggiornamento_a;
	}

	public void setData_ultimo_aggiornamento_a(String data_ultimo_aggiornamento_a) {
		this.data_ultimo_aggiornamento_a = data_ultimo_aggiornamento_a;
	}

	public String getTipoVisualizzazione() {
		return tipoVisualizzazione;
	}

	public void setTipoVisualizzazione(String tipoVisualizzazione) {
		this.tipoVisualizzazione = tipoVisualizzazione;
	}

}
