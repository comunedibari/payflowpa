package it.tasgroup.idp.cart.core;

import java.util.Date;

import javax.ejb.Local;

import it.tasgroup.idp.cart.core.quadratura.DatiQuadratura;
import it.tasgroup.idp.cart.core.quadratura.DatiRiepilogo;

/***
 * 
 * @author pintori
 *
 */

@Local
public interface DatiQuadraturaEJB {


   public DatiQuadratura getDatiQuadratura(String soggetto, String sil, Date inizio, Date fine);
   public byte[] getDatiQuadraturaPdf(String soggetto, String sil, Date inizio, Date fine);
   public DatiRiepilogo getDatiRiepilogo(String soggetto, String sil,long tempoInattivitaAP, int sogliaFallimentiAPE, int sogliaFallimentiIP);
}
