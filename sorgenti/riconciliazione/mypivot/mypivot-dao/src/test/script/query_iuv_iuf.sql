SELECT codice_ipa_ente, riga.nome_flusso_import_ente, riga.riga_flusso_import_ente, riga.
       codice_iud, riga.codice_iuv, riga.versione_oggetto, riga.identificativo_dominio, riga.
       identificativo_stazione_richiedente, riga.identificativo_messaggio_ricevuta, riga.
       data_ora_messaggio_ricevuta, riga.riferimento_messaggio_richiesta, riga.
       riferimento_data_richiesta, riga.tipo_identificativo_univoco_attestante, riga.
       codice_identificativo_univoco_attestante, riga.denominazione_attestante, riga.
       codice_unit_oper_attestante, riga.denom_unit_oper_attestante, riga.indirizzo_attestante, riga.
       civico_attestante, riga.cap_attestante, riga.localita_attestante, riga.provincia_attestante, riga.
       nazione_attestante, riga.tipo_identificativo_univoco_beneficiario, riga.
       codice_identificativo_univoco_beneficiario, riga.denominazione_beneficiario, riga.
       codice_unit_oper_beneficiario, riga.denom_unit_oper_beneficiario, riga.
       indirizzo_beneficiario, riga.civico_beneficiario, riga.cap_beneficiario, riga.
       localita_beneficiario, riga.provincia_beneficiario, riga.nazione_beneficiario, riga.
       tipo_identificativo_univoco_versante, riga.codice_identificativo_univoco_versante, riga.
       anagrafica_versante, riga.indirizzo_versante, riga.civico_versante, riga.cap_versante, riga.
       localita_versante, riga.provincia_versante, riga.nazione_versante, riga.email_versante, riga.
       tipo_identificativo_univoco_pagatore, riga.codice_identificativo_univoco_pagatore, riga.
       anagrafica_pagatore, riga.indirizzo_pagatore, riga.civico_pagatore, riga.cap_pagatore, riga.
       localita_pagatore, riga.provincia_pagatore, riga.nazione_pagatore, riga.email_pagatore, riga.
       codice_esito_pagamento, riga.importo_totale_pagato, riga.identificativo_univoco_versamento, riga.
       codice_contesto_pagamento, riga.singolo_importo_pagato, riga.esito_singolo_pagamento, riga.
       data_esito_singolo_pagamento, riga.identificativo_univoco_riscossione, riga.
       causale_versamento, riga.dati_specifici_riscossione, riga.tipo_dovuto, riga.
       identificativo_flusso_rendicontazione, riga.data_ora_flusso_rendicontazione, riga.
       identificativo_univoco_regolamento, riga.data_regolamento, riga.numero_totale_pagamenti, riga.
       importo_totale_pagamenti, riga.data_acquisizione
     , sum(riga.singolo_importo_pagato) OVER (PARTITION BY riga.tipo_dovuto) as importo_totale_per_tipo
  FROM mygov_export_rendicontazione riga
 WHERE nome_flusso_import_ente in (?)
    OR codice_iuv in (?)