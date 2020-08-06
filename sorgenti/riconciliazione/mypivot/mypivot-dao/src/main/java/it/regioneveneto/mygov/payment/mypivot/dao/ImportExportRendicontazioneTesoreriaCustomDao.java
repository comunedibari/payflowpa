package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreria;

public interface ImportExportRendicontazioneTesoreriaCustomDao {
	
	@Deprecated
	List<ImportExportRendicontazioneTesoreria> provaFinderNative();
}
