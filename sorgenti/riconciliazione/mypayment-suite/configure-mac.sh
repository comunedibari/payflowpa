#!/bin/bash
CONF_FILE=""
CONFIGURE_ENV="prod" ## commenta per cancellare la cartella di lavoro .tmp
		     ## test per lasciare .tmp e _tobuild
DIRS_SYSTEM="system"
DIRS_BATCH=""
DIRS_WEB="doc db httpd DATA MANAGE tomcat webapps"
WPATH=`pwd`

DIRS_ALL="$DIRS_SYSTEM $DIRS_BATCH $DIRS_WEB"

_help(){
cat<<EOT
	----------------------------------------------------------------------
	--build --config configure.live.txt   : configura tutto (webapp, batches, database) a partire
						dal file configure.live.txt (build).
						Crea la cartella build/live e vi copia i pacchetti
						compilati.
	--check --config configure.live.txt : verifica che siano stati sostituiti tutti i place holders
						presenti nel file config.live.txt nella build/live 
	--clean-all		            : Pulisce le cartelle [$DIRS_ALL] (path corrente)
	--dist --config configure.live.txt  : Eseque il pacchetto del contenuto della build/live

EOT
}

_valSed(){
	VAL_SED="$1"
	VAL_SED=$(echo "$VAL_SED"|gsed -e 's/\//\\\//g')
	VAL_SED=$(echo "$VAL_SED"|gsed -e 's/\./\\./g')
	VAL_SED=$(echo "$VAL_SED"|gsed -e 's/\@/\\@/g')
	VAL_SED=$(echo "$VAL_SED"|gsed -e 's/\-/\\-/g')
	VAL_SED=$(echo "$VAL_SED"|gsed -e 's/\[/\\[/g')
	VAL_SED=$(echo "$VAL_SED"|gsed -e 's/\]/\\]/g')
}

_build(){
	f=`echo "$CONF_FILE" | ggrep "/"`
	#echo "CONF_FILE=[$CONF_FILE]"
	case $f in '');;*) echo -e "\nERRORE:\n\tcarattere '/' non consentito nel nome file\n";exit;;esac
	DIST=`echo "$CONF_FILE"|gsed -e 's/configure\.//g'|gsed -e 's/\.txt$//g'`
	case $DIST in
	'')
		echo -e "\nERRORE: nome file [$CONF_FILE] errato"
		echo -e "\tFormato Nome : configure.AMBIENTE.txt"
		exit
	;;
	esac
	#echo "DIST=[$DIST]"
}

_integrityCheck(){
	i=0
	checkStatus=0;k=0;
	PS_ARR=""
	for line in `ggrep -v "^#" $CONF_FILE|tr -d " "|ggrep -v "^$"`
	do
		PS=`echo "$line"|gawk -F '=' '{print $1}'`
		VAL=`echo "$line"|gawk -F '=' '{print $2}'`
		case $VAL in '')
			#echo -e "\n*** ERRORE ***"
			#echo -e "\til PlaceHolder [$PS] non e' stato valorizzato"
			#echo -e "\te' necessario valorizzarlo nel file [$CONF_FILE]"
			#echo -e "\toppure commentarlo [#$PS]"
			checkStatus=1
			PS_ARR="$PS_ARR $PS"
			k=$((k+1))
		;;
		esac
		i=$((i+1))
	done
	case $checkStatus in 
	'1')
		echo -e "\n\n ***** Controllo Integrita' file: [$CONF_FILE] *****"
		if [ $i -gt 1 ] 
			then testo="c'e un Place Holder (PH) non valorizzato"
			else testo"ci sono $i Place Holder (PH) non valorizzati"
		fi
		echo -e "\nERRORE: $testo"
		echo -e "\t(e' necessario valorizzare ogni PH, oppure commentarli"
		echo -e "\t nel file $CONF_FILE, anteponendo il carattere #)"
		echo -e "Elenco PH da valorizzare/commentare:"
		t=1;for p in $PS_ARR
		do
			echo -e "\t\t[$t/$k] $p"
			t=$((t+1))
		done
		exit
	;;
	esac
	
}

_search(){

	if [ ! -f $CONF_FILE ]
	then
		echo -e "\nERRORE:"
		echo -e "\til file [./$CONF_FILE] non esiste !"
		exit
	else
		_build
	fi
	para=$1; 
	#echo "para=[$para]";read
	case $para in
	'--web') DIRS="$DIRS_WEB" ;;
	'--system') DIRS="$DIRS_SYSTEM" ;;
	'--batch') DIRS="$DIRS_BATCH" ;;
	'--build') DIRS="$DIRS_ALL" ;;
	*);;
	esac

	for dir in $DIRS
	do
		if [ -d build/$DIST/$dir ]; then rm -rf build/$DIST/$dir;fi
		mkdir -p build/$DIST/$dir
		touch $dir/_tobuild
		echo -e "\tcp -r $dir/* build/$DIST/$dir/."
		\cp -r $dir/* build/$DIST/$dir/.
	done

	#### DEVO SISTEMARE ###
	#### Copiare i sorgenti solo della build d'interesse
	#DIRS="$DIRS_ALL"


	if [ ! -d .tmp ] 
		then mkdir .tmp 
		else rm -rf .tmp/*
	fi
	#
	_integrityCheck
	#
	i=1;
	tot=`ggrep -v "^#" $CONF_FILE|ggrep -v "^$"|wc -l|gawk '{print $1}'`
	for line in `ggrep -v "^#" $CONF_FILE|ggrep -v "^$"|tr -d " "`
	do
		PS=`echo "$line"|gawk -F '=' '{print $1}'`
		VAL=`echo "$line"|gawk -F '=' '{print $2}'`
		_valSed "$VAL" # la funzione restituisce VAL_SED


		echo -e "[$i/$tot] - RICERCA di : $PS"
		cat /dev/null > .tmp/$PS
		for dir in $DIRS
		do
			#echo "**** ggrep -rl -w \"$PS\" dist/$DIST/$dir/* ***"
			FILES=`ggrep -rl -w "$PS" build/$DIST/$dir/*`
			for file in $FILES
			do
				echo "gsed -i 's/$PS/$VAL_SED/g' $file" >> .tmp/$PS
			done
		done
		i=$((i+1))
	done
	for v in `ls .tmp`
	do
		/bin/sh .tmp/$v
	done

	find build/$DIST -type f -name _tobuild -exec rm -f {} \;
	case $CONFIGURE_ENV in
	'test');;
	*) if [ -d .tmp ]; then rm -rf .tmp;fi
	   for val in `find . -name _tobuild`
	   do
		if [ -f $val ]; then rm -f $val;fi
	   done
	;;
	esac

}

_check(){
	_build
	echo -e "Ricerca PLACEHOLDER non 'gestiti' nella build $DIST"
	FILES=`ggrep -lr "_TAG_" build/$DIST/*`
	case $FILES in '') echo -e "\tCheck Positivo, tutti i TAG valorizzati sono stati sostituiti\n";;
	*)	echo -e "\nErrore:\nAlcuni TAG non sono stati sostituiti, seguono dettagli:"
		for f in $FILES 
		do
			echo -e "\n\t** file [$f]"
			ggrep "__TAG_" $f| while read t
			do
				echo -e "\t\t $t"
			done
			echo -e "\t----------------------------------------------------"
		done
	;;
	esac
	
}
_dist(){
	_build #per ricavare la build 	==> variabile DIST
	if [ ! -d dist ]; then mkdir dist;fi
	if [ -d dist/$DIST ]; then rm -rf dist/$DIST;fi
	cp -rp build/$DIST  dist/$DIST
	cd dist/$DIST/webapps/all/
	for val in `ls -F | ggrep "/"|tr -d "/"`
	do
		cd $val
		zip -q -r $val.war .
		mv $val.war ../.
		cd ..
		if [ -d $val ]; then rm -rf ./$val;fi
	done
	cd $WPATH
	cd dist/$DIST/webapps/stateless/
	for val in `ls -F | ggrep "/"|tr -d "/"`
	do
		cd $val
		zip -q -r $val.war .
		mv $val.war ../.
		cd ..
		if [ -d $val ]; then rm -rf ./$val;fi
	done
	cd $WPATH
	cd dist/$DIST
	tar cfz mypay_suite.$DIST.tar.gz *
	mv mypay_suite.$DIST.tar.gz ../.
	cd .. ; rm -rf ./$DIST
	echo -e "\nCreata dist:  dist/mypay_suite.$DIST.tar.gz"
}
	
_cleanall(){
	echo -e "Cancellazione ambiente corrente"
	for val in $DIRS_ALL
	do
		if [ -d $val ]
		then
			case $val in 'system');;
			*)
			echo -e "\tCancellazione [$val]"
			rm -rf $val
			;;esac
		fi
	done
	if [ -f RELEASE-NOTES.txt ]; then rm -f RELEASE-NOTES.txt;fi
	if [ -f VERSION.txt ]; then rm -f VERSION.txt;fi

	echo -e "\nCancellazione terminata."
}

_main(){
# Dare in Input $@
while [ "$1" != "" ]; do
    case $1 in
        '--web'|'--batch'|'--system'|'--build'|'--dist'|'--check')           
		toSearch=$1
                shift
		sw=$1
		case $1 in
		'--config')
			case $2 in
			'')echo -e "\nERRORE :inserire nome file (configure.ambiente.txt)"
			exit;;
			*) CONF_FILE=$2
			;;
			esac
		;;
		*)
			echo -e "\nERRORE :inserire switch --config configure.ambiente.txt"
			exit
		;;
		esac
		#echo "CONF_FILE=[$CONF_FILE]"
		case $toSearch in
		'--dist')
			_build
			echo -e "Esecuzione package (della dist $DIST basato sulla build $DIST) (attendere il tempo dell'archiviazione)"
			_dist
			exit
		;;
		esac
		case $toSearch in
		'--check')
			_check
			exit
		;;
		esac


	 	_search $toSearch
		exit
	;;
        '--config')    
                CONF_FILE=$2
                shift
		case $1 in
        	'--web'|'--batch'|'--system'|'--build'|'--dist')           
	 		_search $1
			exit
		;;
		*)
			echo -e "\nERRORE : inserire (--build|--check|--dist)"
			echo -e "\nERRORE :inserire switch --config configure.ambiente.txt"
			exit
		;;
		esac
	;;
        '-h' | '--help' ) 
                _help
                shift
                exit
        ;;
	'--clean-all')
		_cleanall
	;;
	*)	echo -e "\nERRORE:\n\t valore [$@] non gestito"
                _help
                exit 1
        ;;
    esac
    shift
done
}

########################################################
if [  $# -eq 0 ]
then
	echo -e "\nERRORE:\nEsempi d'uso:"
	echo -e "\t[1] sh $0 --build --config configure.example.txt"
	echo -e "\t[5] sh $0 --dist --config configure.example.txt"
	echo -e "\t[6] sh $0 --check --config configure.example.txt"
	echo -e "\t[7] sh $0 --help "
	_help
	exit
fi

_main $@


