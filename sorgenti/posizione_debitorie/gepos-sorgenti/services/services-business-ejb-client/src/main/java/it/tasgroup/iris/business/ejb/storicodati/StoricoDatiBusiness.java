package it.tasgroup.iris.business.ejb.storicodati;

import java.util.List;

import it.tasgroup.iris.domain.SveLog;
import it.tasgroup.iris.domain.SveStato;

public interface StoricoDatiBusiness {

	public List<SveStato> getListaStatoSvecchiamento();
	public List<SveLog> getListaLogSvecchiamento(String nomeProcesso);


} 
