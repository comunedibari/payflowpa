- Installare il client Maven recuperabile all'URL "http://maven.apache.org/download.html"
- Configurare la variabile d'ambiente "M2_HOME" con il percorso di installazione di Maven
- Configurare la variabile d'ambiente "PATH" aggiungendo il percorso "%M2_HOME%\bin"
- Copiare il file "settings.xml" nella cartella "%USER_HOME%\.m2"
- Modificare il file "%USER_HOME%\.m2\settings.xml" con le proprie credenziali del proxy
- Nella cartella "E45/trunk/Software/mygov-payment/pa/pa/maven" lanciare il comando "install-jars.bat"
- Nella cartella "E45/trunk/Software/mygov-payment/pa" lanciare i comandi "mvn clean install" e "mvn eclipse:clean eclipse:eclipse"
