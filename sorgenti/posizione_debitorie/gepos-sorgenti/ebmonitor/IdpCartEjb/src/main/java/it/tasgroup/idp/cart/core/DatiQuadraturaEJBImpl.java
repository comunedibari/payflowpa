package it.tasgroup.idp.cart.core;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart.core.quadratura.DatiQuadratura;
import it.tasgroup.idp.cart.core.quadratura.DatiRiepilogo;
import it.tasgroup.idp.cart.core.quadratura.IDatiQuadratura;
import it.tasgroup.idp.cart.core.quadratura.pdf.PdfSerializer;

@Stateless
public class DatiQuadraturaEJBImpl implements DatiQuadraturaEJB{


	private Log log;
	
	@EJB(beanName = "DatiQuadraturaBuilder")
	private IDatiQuadratura builder = null;

	public DatiQuadraturaEJBImpl() {
		this.log = LogFactory.getLog(this.getClass());
		//this.builder = new DatiQuadraturaBuilder();
	}
	@Override
	public DatiQuadratura getDatiQuadratura(String soggetto, String sil, Date inizio, Date fine) {
		DatiQuadratura reportTotale = new DatiQuadratura(soggetto, sil, inizio, fine);
		try{
			this.builder.creaDatiQuadratura(reportTotale);

			return reportTotale;
		}catch(Exception e	){
			this.log.error("Si e' verificato un errore durante la creazione dei dati quadratura: " + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public byte[] getDatiQuadraturaPdf(String soggetto, String sil, Date inizio, Date fine) {
		DatiQuadratura reportTotale = new DatiQuadratura(soggetto, sil, inizio,fine);
		try{
			this.builder.creaDatiQuadratura(reportTotale);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			PdfSerializer.esportaPdf(baos, reportTotale,this.log);
			
			return baos.toByteArray();
		}catch(Exception e	){
			this.log.error("Si e' verificato un errore durante la creazione dei dati quadratura: " + e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public DatiRiepilogo getDatiRiepilogo(String soggetto, String sil, long tempoInattivitaAP, int sogliaFallimentiAPE,
			int sogliaFallimentiIP) {
		DatiRiepilogo dati = null;
		
		try{
			dati = this.builder.getRiepilogo(soggetto, sil, tempoInattivitaAP, sogliaFallimentiAPE, sogliaFallimentiIP);

			return dati;
		}catch(Exception e	){
			this.log.error("Si e' verificato un errore durante la creazione dei dati quadratura: " + e.getMessage(), e);
			return null;
		}
	}
}
