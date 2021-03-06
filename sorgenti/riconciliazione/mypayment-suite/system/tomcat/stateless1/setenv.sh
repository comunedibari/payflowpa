# -----------------------------------------------------------------------------
## PERSONALIZZAZIONI MYPAY
##
JAVA_HOME="/opt/mypay/java"; export JAVA_HOME

CATALINA_PID=/var/run/mypay/stateless.pid


        JAVA_OPTS="-server -Xss2M"
	#JAVA_OPTS="$JAVA_OPTS $JPDA_OPTS"
        JAVA_OPTS="$JAVA_OPTS -Xms1800M -Xmx1800M"
        JAVA_OPTS="$JAVA_OPTS -XX:NewSize=256M"
        JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize=400M"
        JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelGC -XX:ParallelGCThreads=2 -XX:-UseConcMarkSweepGC"
	JAVA_OPTS="$JAVA_OPTS -XX:-TraceClassUnloading -XX:-TraceClassLoading"
        JAVA_OPTS="$JAVA_OPTS -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:../logs/gc.`date +"%s"`.log"
	JAVA_OPTS="$JAVA_OPTS -XX:-HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs/java_OOM_DUMP.`date +"%s"`.hprof"
        JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true"
        JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true"
        #
        JAVA_OPTS="$JAVA_OPTS -DJVMROUTE=__TAG_SYSTEM_MYPAY_TOMCAT_STATELESS1_JVMROUTE__"
        JAVA_OPTS="$JAVA_OPTS -DBIND_ADDRESS=__TAG_SYSTEM_MYPAY_TOMCAT_STATELESS1_ADDRESS__"
        #
	##
	## STATELESS : Traslare le porte di +10000
        JAVA_OPTS="$JAVA_OPTS -DSHUTDOWN_PORT=__TAG_SYSTEM_MYPAY_TOMCAT_STATELESS1_SHUTDOWN_PORT__"
	#
        JAVA_OPTS="$JAVA_OPTS -DAJP_PORT=__TAG_SYSTEM_MYPAY_TOMCAT_STATELESS1_AJP_PORT__"
        JAVA_OPTS="$JAVA_OPTS -DAJP_CONNECTION_TIMEOUT=20000"
        JAVA_OPTS="$JAVA_OPTS -DAJP_ACCEPT_COUNT=10"
        JAVA_OPTS="$JAVA_OPTS -DAJP_MAXTHREADS=300"
        JAVA_OPTS="$JAVA_OPTS -DAJP_STRATEGY=ms"
        JAVA_OPTS="$JAVA_OPTS -DAJP_DISABLE_UPLOAD_TIME=true"
        JAVA_OPTS="$JAVA_OPTS -DAJP_EMPTY_SESSION_PATH=true"
        JAVA_OPTS="$JAVA_OPTS -DAJP_ENABLE_LOOKUPS=false"
        #
        JAVA_OPTS="$JAVA_OPTS -DHTTP_PORT=__TAG_SYSTEM_MYPAY_TOMCAT_STATELESS1_HTTP_PORT__"
        JAVA_OPTS="$JAVA_OPTS -DHTTP_CONNECTION_TIMEOUT=20000"
        JAVA_OPTS="$JAVA_OPTS -DHTTP_ACCEPT_COUNT=300"
        JAVA_OPTS="$JAVA_OPTS -DHTTP_MAXTHREADS=300"
        JAVA_OPTS="$JAVA_OPTS -DHTTP_STRATEGY=ms"
        JAVA_OPTS="$JAVA_OPTS -DHTTP_DISABLE_UPLOAD_TIME=true"
        JAVA_OPTS="$JAVA_OPTS -DHTTP_EMPTY_SESSION_PATH=true"
        JAVA_OPTS="$JAVA_OPTS -DHTTP_ENABLE_LOOKUPS=false"
        #
        JAVA_OPTS="$JAVA_OPTS -DHTTPS_PORT=__TAG_SYSTEM_MYPAY_TOMCAT_STATELESS1_HTTPS_PORT__"
        JAVA_OPTS="$JAVA_OPTS -DHTTPS_CONNECTION_TIMEOUT=20000"
        JAVA_OPTS="$JAVA_OPTS -DHTTPS_ACCEPT_COUNT=10"
        JAVA_OPTS="$JAVA_OPTS -DHTTPS_MAXTHREADS=10"
        JAVA_OPTS="$JAVA_OPTS -DHTTPS_STRATEGY=ms"
        JAVA_OPTS="$JAVA_OPTS -DHTTPS_DISABLE_UPLOAD_TIME=true"
        JAVA_OPTS="$JAVA_OPTS -DHTTPS_EMPTY_SESSION_PATH=false"
        JAVA_OPTS="$JAVA_OPTS -DHTTPS_ENABLE_LOOKUPS=false"
	#
        JAVA_OPTS="$JAVA_OPTS -Djavax.servlet.request.encoding=UTF-8"
        JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8"
        #
	#
	#JAVA_OPTS="$JAVA_OPTS -Dhttp.proxyHost=IP_PROXY -Dhttp.proxyPort=PORT_PROXY" 
	#JAVA_OPTS="$JAVA_OPTS -Dhttps.proxyHost=IP_PROXY -Dhttps.proxyPort=PORT_PROXY" 
	#JAVA_OPTS="$JAVA_OPTS '-Dhttp.nonProxyHosts=*.local|*.local1'" 


# -----------------------------------------------------------------------------

