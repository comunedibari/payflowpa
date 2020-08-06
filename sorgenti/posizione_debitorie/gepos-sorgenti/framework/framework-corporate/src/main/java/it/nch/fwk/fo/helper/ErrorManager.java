/*
 * Creato il 2-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.helper;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface ErrorManager {
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno del DTO a partire
	 * da un'eccezione runtime
	 * @param dto Parametro in ingresso che viene arricchito di un oggetto info e restituito
	 * @param re  Eccezione il cui message viene inserito nelle info del dto
	 * @return Il DTO arricchito con le info
	 */
	public abstract DTO menageException(DTO dto, RuntimeException re);
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno del DTO a partire
	 * da un'eccezione runtime
	 * @param dto Parametro in ingresso che viene arricchito di un oggetto info e restituito
	 * @param re  Eccezione il cui message viene inserito nelle info del dto
	 * @param customMessage Messaggio custom da fornire al DTOInfo
	 * @return Il DTO arricchito con le info
	 */
	public abstract DTO menageException(DTO dto, RuntimeException re, String customMessage);

	/**
	 * @author EE10057
	 *
	 * Metodo di utilità per sollevate un'eccezione runtime generica
	 */
	public abstract void throwRuntime(String msg);
		
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno del DTOCollection a partire
	 * da un'eccezione runtime
	 * 
	 * @param dtoCollection
	 * @param re
	 * @return La DTOCollection arricchita dalle info
	 */
	public DTOCollection menageException(DTOCollection dtoCollection, RuntimeException re);
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno del DTOCollection a partire
	 * da un'eccezione runtime
	 * 
	 * @param dtoCollection
	 * @param re
	 * @param customMessage Messaggio custom da fornire al DTOInfo
	 * @return La DTOCollection arricchita dalle info
	 */
	public DTOCollection menageException(DTOCollection dtoCollection, RuntimeException re, String customMessage);
	
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno del DTO a partire
	 * da una checked Exception,una severity ed un codice di errore nel formato "token1.token2.token3...."
	 * @param dto Parametro in ingresso che viene arricchito di un oggetto info e restituito
	 * @param e  Eccezione il cui message viene inserito nelle info del dto
	 * @param severity  La severity dell'errore
	 * @param code Il codice di errore che costituisce la chiave nella hashmap dell'ErrorMapper
	 * @return Il DTO arricchito delle info
	 */
	public DTO menageException(DTO dto, Exception e,int severity,String code);
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno della DTOCollection a partire
	 * da una checked Exception,una severity ed un codice di errore nel formato "token1.token2.token3...."
	 * @param dto Parametro in ingresso che viene arricchito di un oggetto info e restituito
	 * @param e  Eccezione il cui message viene inserito nelle info del dto
	 * @param severity  La severity dell'errore
	 * @param code Il codice di errore che costituisce la chiave nella hashmap dell'ErrorMapper
	 * @return La DTOCollection arricchito delle info
	 */
	public DTOCollection menageException(DTOCollection dtoCollection, Exception e,int severity,String code);
	
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno del DTO a partire
	 * da una checked Exception ed un codice di errore nel formato "token1.token2.token3....". 
	 * La severity dell'errore viene ricavata dal codice.
	 * Se il codice contiene un token "error" la severity viene settata a DTOInfoInterface.SEVERITY_ERROR
	 * Se il codice contiene un token "warning" la severity viene settata a DTOInfoInterface.SEVERITY_WARNIG
	 * Se il codice contiene un token "info" la severity viene settata a DTOInfoInterface.SEVERITY_INFO
	 * Se più di un token significativo viene trovato vince il primo in ordine da sinistra a destra
	 * Se nessun token significativo viene trovato la severity viene settata a DTOInfoInterface.SEVERITY_GENERIC
	 * @param dto Parametro in ingresso che viene arricchito di un oggetto info e restituito
	 * @param e  Eccezione il cui message viene inserito nelle info del dto
	 * @param code Il codice di errore che costituisce la chiave nella hashmap dell'ErrorMapper
	 * @return Il DTO arricchito delle info
	 */
	public DTO menageException(DTO dto, Exception e,String code);
	
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno della DTOCollection a partire
	 * da una checked Exception ed un codice di errore nel formato "token1.token2.token3....". 
	 * La severity dell'errore viene ricavata dal codice.
	 * Se il codice contiene un token "error" la severity viene settata a DTOInfoInterface.SEVERITY_ERROR
	 * Se il codice contiene un token "warning" la severity viene settata a DTOInfoInterface.SEVERITY_WARNIG
	 * Se il codice contiene un token "info" la severity viene settata a DTOInfoInterface.SEVERITY_INFO
	 * Se più di un token significativo viene trovato vince il primo in ordine da sinistra a destra
	 * Se nessun token significativo viene trovato la severity viene settata a DTOInfoInterface.SEVERITY_GENERIC
	 * @param dtoCollection Parametro in ingresso che viene arricchito di un oggetto info e restituito
	 * @param e  Eccezione il cui message viene inserito nelle info del dto
	 * @param code Il codice di errore che costituisce la chiave nella hashmap dell'ErrorMapper
	 * @return La DTOCollection arricchito delle info
	 */
	public DTOCollection menageException(DTOCollection dtoCollection, Exception e,String code);
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno del DTO a partire
	 * da una checked Exception ed un codice di errore nel formato "token1.token2.token3....". 
	 * La severity dell'errore viene ricavata dal codice.
	 * Se il codice contiene un token "error" la severity viene settata a DTOInfoInterface.SEVERITY_ERROR
	 * Se il codice contiene un token "warning" la severity viene settata a DTOInfoInterface.SEVERITY_WARNIG
	 * Se il codice contiene un token "info" la severity viene settata a DTOInfoInterface.SEVERITY_INFO
	 * Se più di un token significativo viene trovato vince il primo in ordine da sinistra a destra
	 * Se nessun token significativo viene trovato la severity viene settata a DTOInfoInterface.SEVERITY_GENERIC
	 * @param dto Parametro in ingresso che viene arricchito di un oggetto info e restituito
	 * @param code Il codice di errore che costituisce la chiave nella hashmap dell'ErrorMapper
	 * @return Il DTO arricchito delle info
	 */
	public DTO menageException(DTO dto,String code);
	
	
	/**
	 * Metodo che gestisce la valorizzazione di un DTOInfo all'interno della DTOCollection a partire
	 * da una checked Exception ed un codice di errore nel formato "token1.token2.token3....". 
	 * La severity dell'errore viene ricavata dal codice.
	 * Se il codice contiene un token "error" la severity viene settata a DTOInfoInterface.SEVERITY_ERROR
	 * Se il codice contiene un token "warning" la severity viene settata a DTOInfoInterface.SEVERITY_WARNIG
	 * Se il codice contiene un token "info" la severity viene settata a DTOInfoInterface.SEVERITY_INFO
	 * Se più di un token significativo viene trovato vince il primo in ordine da sinistra a destra
	 * Se nessun token significativo viene trovato la severity viene settata a DTOInfoInterface.SEVERITY_GENERIC
	 * @param dtoCollection Parametro in ingresso che viene arricchito di un oggetto info e restituito
	 * @param code Il codice di errore che costituisce la chiave nella hashmap dell'ErrorMapper
	 * @return La DTOCollection arricchito delle info
	 */
	public DTOCollection menageException(DTOCollection dtoCollection,String code);
	
	
	
	
	
}