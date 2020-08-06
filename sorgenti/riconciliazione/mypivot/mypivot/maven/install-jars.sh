#!/bin/sh

JAVA_HOME="/opt/jdk-1.7.0"
export JAVA_HOME

"$M2_HOME"/bin/mvn install:install-file -Dfile=icar-sp-inf3-j2ee-integration-test.jar -DgroupId=it.cefriel.icar -DartifactId=icar-sp-inf3-j2ee-integration-test -Dversion=0.9.5.9 -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=inf3-commons.jar -DgroupId=it.cefriel.icar -DartifactId=inf3-commons -Dversion=0.9.5.9 -Dpackaging=jar

"$M2_HOME"/bin/mvn install:install-file -Dfile=mybox-client-4.0.9.jar -DgroupId=it.regioneveneto.mybox -DartifactId=mybox-client -Dversion=4.0.9 -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=mypivot-apt.jar -DgroupId=mypivot-apt -DartifactId=mypivot-apt -Dversion=1.0 -Dpackaging=jar

"$M2_HOME"/bin/mvn install:install-file -Dfile=xerces-resolver-2.9.1.jar -DgroupId=xerces -DartifactId=xerces-resolver -Dversion=2.9.1 -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=xerces-serializer-2.9.1.jar -DgroupId=xerces -DartifactId=xerces-serializer -Dversion=2.9.1 -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=xerces-xercesImpl-2.9.1.jar -DgroupId=xerces -DartifactId=xerces-xercesImpl -Dversion=2.9.1 -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=xerces-xml-apis-2.9.1.jar -DgroupId=xerces -DartifactId=xerces-xml-apis -Dversion=2.9.1 -Dpackaging=jar
