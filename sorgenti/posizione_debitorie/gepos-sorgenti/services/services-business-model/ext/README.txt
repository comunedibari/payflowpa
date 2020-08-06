This folder contains the jar to access the external system named 'FlowManager'.
When a new version is relaesed by the external system, overwrite the jar
with the new one, update the version attribute in def.bat and run:
- deploy.bat to deploy on local Maven repository
- publish.bat to publish on company Maven repository

Update the dependency version on pom.xml

======= VERSION HISTORY ========

18/05/2007	1.1.0	New jndi name of EJB
28/05/2007	1.2.0	Included stub for 'sped' too