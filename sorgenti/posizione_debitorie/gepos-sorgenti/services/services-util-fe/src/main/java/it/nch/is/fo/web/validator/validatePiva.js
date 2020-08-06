/*****************************************
	Controllo della Partita I.V.A.
	Linguaggio: JavaScript
******************************************/

function ControllaPIVA(pi)
{
	if( pi == '' )  return '';
	if( pi.length != 11 )
		return "La lunghezza della partita IVA non e'\n" +
			"corretta: la partita IVA dovrebbe essere lunga\n" +
			"esattamente 11 caratteri.\n";
	validi = "0123456789";
	for( i = 0; i < 11; i++ ){
		if( validi.indexOf( pi.charAt(i) ) == -1 )
			return "La partita IVA contiene un carattere non valido `" +
				pi.charAt(i) + "'.\nI caratteri validi sono le cifre.\n";
	}
	s = 0;
	for( i = 0; i <= 9; i += 2 )
		s += pi.charCodeAt(i) - '0'.charCodeAt(0);
	for( i = 1; i <= 9; i += 2 ){
		c = 2*( pi.charCodeAt(i) - '0'.charCodeAt(0) );
		if( c > 9 )  c = c - 9;
		s += c;
	}
	if( ( 10 - s%10 )%10 != pi.charCodeAt(10) - '0'.charCodeAt(0) )
		return "La partita IVA non e' valida:\n" +
			"il codice di controllo non corrisponde.\n";
	return '';
}
