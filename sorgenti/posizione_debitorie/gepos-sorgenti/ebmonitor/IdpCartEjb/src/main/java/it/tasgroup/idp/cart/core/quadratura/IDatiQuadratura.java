package it.tasgroup.idp.cart.core.quadratura;

import javax.ejb.Local;

@Local
public interface IDatiQuadratura {

	public void creaDatiQuadratura(DatiQuadratura datiQuadratura) throws Exception;
	
	public DatiRiepilogo getRiepilogo(String soggetto, String sil, long tempoInattivitaAP, int sogliaFallimentiAPE,
			int sogliaFallimentiIP) throws Exception;
}
