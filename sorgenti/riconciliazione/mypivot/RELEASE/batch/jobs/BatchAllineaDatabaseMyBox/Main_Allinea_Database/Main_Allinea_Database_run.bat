%~d0
cd %~dp0
java -Xms256M -Xmx1024M -cp .;../lib/routines.jar;../lib/log4j-1.2.16.jar;../lib/talendcsv.jar;../lib/talend-log4j-config-util.jar;../lib/talend_file_enhanced_20070724.jar;../lib/dom4j-1.6.1.jar;../lib/log4j-1.2.15.jar;../lib/postgresql-9.4-1201.jdbc41.jar;../lib/commons-lang-2.6.jar;main_allinea_database_0_1.jar;log_nagios_0_1.jar;allinea_0_1.jar; allinea_database_mybox.main_allinea_database_0_1.Main_Allinea_Database --context=Default %* 