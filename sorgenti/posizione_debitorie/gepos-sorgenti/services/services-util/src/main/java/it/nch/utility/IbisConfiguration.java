package it.nch.utility;

import it.nch.utility.exception.LogicalOperationalException;
import it.nch.utility.exception.NotFoundException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Singleton che implementa un file ini: ogni sezione corrisponde
 * ad un file properties di configurazione.
 * Le sezioni sono implementate tramite hash table
 */
public class IbisConfiguration implements IConfigObserver{

	private static IbisConfiguration Current = null;
	private Hashtable sections = null;
	private String workingDirectory;
	private static String fileSeparator = System.getProperty("file.separator");

	public static String ABI_GRUPPO_BT= "01030,03400,05024,03210,05040";
	public static String ABI_BIPOP= "05437";
	public static String ABI_POSTE= "07601";
	
	
	private IbisConfiguration()	{
		sections = new Hashtable();	
	}

	private static void initIbisConfiguration(){
		if (Current==null){
			Current = new IbisConfiguration();
			// Registrazione
			ObservableConfigReloader ocr = ObservableConfigReloader.getInstance();
			ocr.addConfigObserver(Current);	// Si registra un'istanza di IbisConfiguration.		
		}	
	}
	
	public void setWorkingDir(String workingDirectory)	{
		this.workingDirectory = workingDirectory;
	}
	
	public String getWorkingDir()	{
		return this.workingDirectory;
	}
		
	private void initialise(String section) throws NotFoundException	{
		String fileName = section + ".properties";
		Properties p = new Properties();
		try	{
			p.load(new FileInputStream(workingDirectory + fileSeparator +"conf"+ fileSeparator + fileName));	
			sections.put(section,p);
		}
		catch(Exception e) {
			//it.nch.utility.IbisDebugger.println(e.toString());
			throw new NotFoundException(e);
		}
	}
	
	private void save(Properties p, String section) throws LogicalOperationalException	{
		String fileName=section+".properties";
		try	{
			String workingDirectory = this.getWorkingDir();
			p.store(new FileOutputStream(workingDirectory + fileSeparator +"conf"+ fileSeparator + fileName), "Aggiornato il " + new Date());	
		}
		catch(Exception e) {
			//Tracer.debug(this.getClass().getName(), "save", e.toString());
			throw new LogicalOperationalException(e);
		}
	}

	/**
	 * Restituisce la classe Prperties relativa alla sezione desiderata del file ini 
	 **/
	public static Properties GetCurrentSection(String section) throws NotFoundException	{
//		if (Current==null)
//			Current = new IbisConfiguration();
		initIbisConfiguration();			
		if (!(Current.sections.containsKey(section)))
			Current.initialise(section);
		return (Properties)Current.sections.get(section); 
	}
	
	/** Restituisce il valore di una chiave key per la sezione section 
	 **/
	public static String Get(String section, String key) throws NotFoundException	{
		Properties p = GetCurrentSection(section);
		if (p.containsKey(key))
			return (String)p.get(key);
		else
			throw new NotFoundException(null, "", "Parametro di configurazione " + key + " non trovato nel file " + section + ".properties");
	}
	
	/** Restituisce true se la sezione section contiene la chiave key 
	 **/
	public static boolean SectionHasKey(String section, String key) throws NotFoundException	{
		Properties p = GetCurrentSection(section);
		return  (p.containsKey(key));
	}
	
	/** Restituisce true se la sezione section contiene il valore val 
	 **/
	public static boolean SectionHasValue(String section, String val) throws NotFoundException	{
		Properties p = GetCurrentSection(section);
		return  (p.contains(val));
	}
	
	
	/** depricated!	
	 * per mantenere compatibilit� con versione precedente: chiamata
	 * default per sezione ibis
	 */
	public static Properties getCurrent() throws NotFoundException	{
		return GetCurrentSection("Ibis");
	}
	
	public static void Save(Properties p, String section) throws LogicalOperationalException	{
//		if (Current==null)
//			Current = new IbisConfiguration();
		initIbisConfiguration();
		Current.save(p,section);
	}
	
	public static String GetWorkingDir() {
//		if (Current==null)	{
//			Current = new IbisConfiguration();
//		}
		initIbisConfiguration();
		return Current.getWorkingDir();
	}

	public static void SetWorkingDir(String workingDirectory) {
//		if (Current==null)	{
//			Current = new IbisConfiguration();
//		}
		initIbisConfiguration();
		Current.setWorkingDir(workingDirectory);
	}

	/**
	 * cartella condivisa (se specificata lo zip viene spostato in questa cartella) 
	 */
	public static String GetDIRECTORY_ZIP_ESPORTAZIONI_SHARED() {
		try {
			return Get("Ibis", "DIRECTORY_ZIP_ESPORTAZIONI_SHARED").trim();						
		} catch(Exception e) {
			return ""; // default "" = non si effettua lo spostamento
		}
	}

	/**
	 * cartella condivisa per l'esportazione di dati pregressi per Cedac e simili
	 */
	public static String GetDIRECTORY_ESPORTAZIONI_REMOTA() {
		try {
			return Get("Ibis", "DIRECTORY_ESPORTAZIONI_REMOTA").trim();						
		} catch(Exception e) {
			return ""; // default "" = non si effettua lo spostamento
		}
	}

	/**
	 * cartella condivisa per l'esportazione di destinatari per Cedac e simili
	 */
	public static String GetDIRECTORY_ESPORTAZIONI_REMOTA_DEST() {
		try {
			return Get("Ibis", "DIRECTORY_ESPORTAZIONI_REMOTA_DEST").trim();						
		} catch(Exception e) {
			return GetDIRECTORY_ESPORTAZIONI_REMOTA(); // default uguale a quella sopra
		}
	}

	public static void Reset()	{
		Current=null;
	}
	
	/**
	 * MODO MULTIBANCA 
	 */
	public static boolean IsMultibancaActive(){
		try{
			return (Integer.parseInt(Get( "Ibis", "MULTIBANCA" ).trim()) == 1) ? true : false;						
		}catch(Exception e){
			return false;//default = non attivo
		}
	}

	/**
	 * MODO MULTIAZIENDA
	 */
	public static boolean IsMultiaziendaActive() throws LogicalOperationalException 	{
		try{
			return (Integer.parseInt(Get( "Ibis", "MULTIAZIENDA" ).trim()) == 1) ? true : false;						
		}catch(Exception e){
			return false;//default = non attivo
		}
	}    
	
	/**
	 * MODO PASSWORD DISPOSITIVA
	 */
	public static boolean IsPasswordDispositivaActive() throws LogicalOperationalException 	{
		try{
			return (Integer.parseInt(Get( "Ibis", "PasswordDispositiva" ).trim()) == 0) ? true : false;						
		}catch(Exception e){
			return false;//default = non attivo
		}
	}   

	/**
	 * ABI INSTALLAZIONE 
	 */
	public static String GetAbiInstallazione() throws Exception{
		try{
			return Get( "Ibis", "ABI_INSTALLAZIONE" ).trim();						
		}catch(Exception e){
			throw new Exception ( "ABI_INSTALLAZIONE NON PRESENTE IN Ibis.properties");
		}
	}

	public static boolean isAbiInstallazioneInGruppoBT() throws Exception{
		try{
			return (new String(ABI_GRUPPO_BT).indexOf(GetAbiInstallazione()) != -1);						
		}catch(Exception e){
			throw new Exception ( "ABI_INSTALLAZIONE NON PRESENTE IN Ibis.properties");
		}
	}

	public static boolean isAbiInstallazioneBipop() throws Exception{
		try{
			return GetAbiInstallazione().equals(ABI_BIPOP);						
		}catch(Exception e){
			throw new Exception ( "ABI_INSTALLAZIONE NON PRESENTE IN Ibis.properties");
		}
	}
	
	public static boolean isAbiInstallazionePoste(){
		try{
			return GetAbiInstallazione().equals(ABI_POSTE);						
		}catch(Exception e){
			return false; // default
		}
	}

	public static boolean isAnagraficaNew(){
		try{
			return (Integer.parseInt(Get( "Ibis", "ANAGRAFICA_NEW" ).trim()) == 1) ? true : false;						
		}catch(Exception e){
			return false;//default = non attivo
		}
	}
	
	public static boolean isFlussoCbi2Compressed(){
		try{
			return (Integer.parseInt(Get( "Ibis", "isFlussoCbi2Compressed" ).trim()) == 1) ? true : false;						
		}catch(Exception e){
			return false;//default = non compresso per Poste
		}
	}
		
	/**
	 *
	 */
	public static String GetFunzioniAbilitabiliOperatoriFlussi() throws NotFoundException {
		try {
			return Get("Ibis", "FUNZIONI_OPERATORE_FLUSSI").trim();
		} catch (Exception e) {
			throw new NotFoundException(null, "", "Parametro di configurazione FUNZIONI_OPERATORE_FLUSSI non trovato nel file Ibis.properties");
		}
	}

	/**
	 * Metodo invocato da 'ObservableConfigReloader' per implementare il
	 * reload dei parametri di configurazione letti dai file di property 
	 * e memorizzati in cache (attributi statici). 
	 * 
	 */
	public void reloadConfiguration() {
		
		// La sezione 'paginazioneDinamica' non deve essere aggiornata poich� l'attuale implementazione 
		// della paginazione (con cache) non � compatibile con il reload delle configurazioni a runtime.
		// Un effetto riscontrato � quello per cui le pagine gi� caricate, continuano a mostrare un numero
		// di elementi pari al vecchio valore; le pagine da caricare invece, se selezionate, mostrano un 
		// numero di elementi pari al nuovo valore.
		if (Current.sections.containsKey("paginazioneDinamica")){
			Properties prop = (Properties)Current.sections.get("paginazioneDinamica");
			sections = new Hashtable();
			sections.put("paginazioneDinamica",prop); 		
		}
		else{
			sections = new Hashtable();
		}

	}	


}