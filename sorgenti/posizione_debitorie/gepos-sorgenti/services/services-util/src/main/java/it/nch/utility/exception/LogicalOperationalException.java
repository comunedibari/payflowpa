package it.nch.utility.exception;


public class LogicalOperationalException extends Exception
{
	public LogicalOperationalException(Exception ex, String codice, String messaggio)
	{
	}
	
	public LogicalOperationalException(Exception ex)
	{
		super(ex);
	}
}
