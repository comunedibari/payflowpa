Per preparare la distribuzione:

  - copiare il template "configure.txt" nel file "configure.<env>.txt" per le
    configurazioni dell'ambiente target <env>
  - compilare il file "configure.<env>.txt"
  - aggiungere le risorse di impianto degli Enti (script SQL "db/ente/", loghi
    "httpd/ente/" e "RESOURCES/loghiEnti/")
  - eseguire il comando "configure.sh --build --config configure.<env>.txt" per
    applicare le configurazioni al template; il template istanziato è copiato
    sotto la cartella "build/<env>/"
  - eseguire il comando "configure.sh --dist --config configure.<env>.txt" per
    pacchettizzare in un archivio compresso la distribuzione; l'archivio è
    copiato sotto la cartella "dist/"
  - seguire le istruzioni del documento
    "NT_E45_MyPay_Riuso_Installazione_v02.0.pdf" per installare la suite
    nell'ambiente target <env>
