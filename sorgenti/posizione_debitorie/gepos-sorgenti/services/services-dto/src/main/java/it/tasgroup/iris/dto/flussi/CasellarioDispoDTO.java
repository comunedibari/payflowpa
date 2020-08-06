package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;

public class CasellarioDispoDTO extends CasellarioDTO implements Serializable{

	private byte[] flussoCBI;
	private DistintePagamentoDTOLight distintePagamento;
	private DistinteRiaccreditoDTO distinteRaccredito;
	
	public byte[] getFlussoCBI() {
		return flussoCBI;
	}
	public void setFlussoCBI(byte[] flussoCBI) {
		this.flussoCBI = flussoCBI;
	}
	public DistintePagamentoDTOLight getDistintePagamento() {
		return distintePagamento;
	}
	public void setDistintePagamento(DistintePagamentoDTOLight distintePagamento) {
		this.distintePagamento = distintePagamento;
	}
	public void setDistinteRaccredito(DistinteRiaccreditoDTO distinteRaccredito) {
		this.distinteRaccredito = distinteRaccredito;
	}
	public DistinteRiaccreditoDTO getDistinteRaccredito() {
		return distinteRaccredito;
	}
	
	

}