package it.tasgroup.idp.configurazioneente.controller;

import javax.ejb.Local;

import it.tasgroup.idp.anagrafica.Indirizzipostali;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.domain.bonifici.Intestatari;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnte;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneTributi;

@Local
public interface ConfigurazioneEnteControllerTransactions {
	
	void insertEnte(IdpConfigurazioneEnte idpConfigurazioneEnte)  throws LoaderException;
	
	void updateEnte(IdpConfigurazioneEnte idpConfigurazioneEnte, Intestatari intestatario, Enti ente, Indirizzipostali indirizzo)  throws LoaderException;
	
	void insertTributo(IdpConfigurazioneTributi idpConfigurazioneTributi, Enti ente) throws LoaderException;
	
	void updateTributo(IdpConfigurazioneTributi idpConfigurazioneTributi, TributiEnti tributoEnte) throws LoaderException;

}
