package it.regioneveneto.mygov.payment.mypivotsb.constant;

public class Response {
	private Response() {}
	public static final String INSERT_OK 					= "Inserimento effettuato correttamente";
	public static final String DELETE_OK 					= "Cancellazione effettuata correttamente";
	public static final String UPDATE_OK 					= "Aggiornamento effettuato correttamente";
	public static final String RESPONSE_KO 					= "Operazione non effettuata: %s";
	public static final String OPERATION_OK 				= "Operazione effettuata correttamente";
	public static final String RESPONSE_ENTITY_NOT_FOUND 	= "Non è stato possibile trovare %s con identificativo %s";
}