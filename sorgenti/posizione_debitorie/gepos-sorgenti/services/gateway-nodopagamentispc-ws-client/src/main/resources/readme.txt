Note per rigenerare:

a) Decommentare i plugin xmlbeans nel pom.xml
b) Cancellare la directory schemaorg_apache_xmlbeans (committarne la cancellazione)
c) lanciare mvn clean install (dopo aver aggiornato i wsdl/xsd del caso)
d) copiare la nuova cartella schemaorg_apache_xmlbeans da target/generated-classes/xmlbeans (ricommittarla)
