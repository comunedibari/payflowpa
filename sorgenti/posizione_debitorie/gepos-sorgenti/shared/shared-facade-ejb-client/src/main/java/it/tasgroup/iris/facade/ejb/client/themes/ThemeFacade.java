package it.tasgroup.iris.facade.ejb.client.themes;


import it.tasgroup.iris.dto.confpagamenti.CfgEntiTemaDTO;

import java.util.Date;

// TODO introdurre caching qui
public interface ThemeFacade {
    /**
     * Ritorna un oggetto completo con i dati delle immagini
     * @param id
     * @return
     */
    CfgEntiTemaDTO getCfgEntiTemaById(String id);

    /**
     * Ritorna un oggetto 'lite' senza i dati binary delle immagini
     * @param cdEnte
     * @return
     */
    CfgEntiTemaDTO getCfgEntiTemaLiteById(String cdEnte);

    CfgEntiTemaDTO createCfgEntiTema(CfgEntiTemaDTO entiLogoDTO);

    CfgEntiTemaDTO updateCfgEntiTema(CfgEntiTemaDTO entiLogoDTO);

    Date getCfgEntiTemaTsAggiornamento(String cdEnte);
}
