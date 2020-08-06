/**
 * 
 */
package it.tasgroup.iris.util;

import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.dto.exception.CheckAdmittedValuesException;
import it.tasgroup.iris.dto.exception.CheckCSVException;
import it.tasgroup.iris.dto.exception.CheckValuesUniquenessException;
import it.tasgroup.iris.dto.exception.FileSizeException;
import it.tasgroup.iris.dto.exception.FileUploadException;
import it.tasgroup.iris.dto.exception.MalformedCSVException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Classe di utilità per controllare il contenuto dei file CSV prima di inviarli allo SmartProxy.
 * 
 * @author pazzik
 *
 */
public class CSVParser {
	
	
	 /**
	 * @param fileName: il nome del file da esaminare
	 * @param checkValuesMap: la mappa delle colonne da controllare
	 * @return
	 * @throws CheckCSVException
	 * @throws FileUploadException
	 */
	public static Map<String,String> checkColumnValues(String fileName, Map<String, List> checkValuesMap, String columnSeparator) throws CheckCSVException, FileUploadException {
		 
			BufferedReader br = null;
			
			String line = null;
			
			String csvSplitBy = "\\"+columnSeparator;
			
			Map<String,String> univocityCheckMap = new HashMap<String, String>();
			
			try {
		 
				br = new BufferedReader(new FileReader(fileName));
				
				Map<Integer, List> columnPositionMap = null;
				
				int rowCounter = 0;
				
				// leggo prima a parte la riga di intestazione per estrapolare la posizione della colonna cercata
				if ((line = br.readLine()) != null)
					
					columnPositionMap= getColumnPosition(checkValuesMap, line, csvSplitBy);
					
				else 
					throw new IllegalStateException("Trovato File Vuoto!");
				
				
				// si processano le righe successive alla prima, cioè il body del CSV
				while ((line = br.readLine()) != null) {
		 	
					checkRow(rowCounter, columnPositionMap, line, csvSplitBy, univocityCheckMap);
					
					rowCounter++;
					
				}
				
			} catch (CheckCSVException e) {
				
				throw e;
		 
			} catch (IOException e) {
				
				throw new FileUploadException();
		 
				
			} finally {
				
				if (br != null) {
					
					try {
						
						br.close();
						
					} catch (IOException e) {
						
						Tracer.error(CSVParser.class.getName(), "checkColumnValues", "Eccezione: ", e);
						
					}
				}
			}
		 
			Tracer.debug(CSVParser.class.getName(), "checkColumnValues", "END with result "+univocityCheckMap);
			
			return univocityCheckMap;
			
		  }
	 
	 /**
	 * Metodo di test che verifica che nel CSV si trovi sempre 
	 * CREDITORE="ASL8Arezzo" e TIPO_DEBITO="TRIBUTO_TEST_1".
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
			
			String fileName = "D:\\TAS\\MY\\DOC\\BANCA_ETRURIA\\insertNew.csv";
			
			Map<String,List> checkValuesMap = new HashMap<String,List>();
			
			List<String> billerParams = new ArrayList<String>();
			
			billerParams.add("ASL8Arezzo");
			
			checkValuesMap.put("CREDITORE",billerParams);
			
			List<String> billTypeParams = new ArrayList<String>();
			
			billTypeParams.add("TRIBUTO_TEST_1");
			
			checkValuesMap.put("TIPO_DEBITO",billTypeParams);
			
			try {
				
				checkColumnValues(fileName, checkValuesMap, ";");
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	 
	/**
	 * Associa ad ogni colonna la sua posizione sulla riga di intesatzione del CSV.
	 * 
	 * @param checkValuesMap : la mappa contenente i nomi delle colonne da controllare
	 * @param line : la riga di intestazione del CSV
	 * @param cvsSplitBy : il separatore delle colonne del CSV
	 * @return una mappa contenente per ogni colonna, la sua posizione nella riga di intestazione.
	 * @throws IOException
	 */
	private static Map<Integer, List>  getColumnPosition(Map<String, List> checkValuesMap, String line, String cvsSplitBy) throws IOException{
		 
		 Map<Integer, List> newCheckValuesMap = new HashMap<Integer, List>();
		 		 
		 for(String columnToFind : checkValuesMap.keySet()){
			 
			 	int columnCounter = 0;
				
				String[] splitHeader = line.split(cvsSplitBy);
				
				for (String columnName : splitHeader) {
					
					if (columnName.equals(columnToFind))
						break;
					
					columnCounter ++;	
				}
				
				List params = checkValuesMap.get(columnToFind);
				
				params.add(0, columnToFind);
				
				newCheckValuesMap.put(columnCounter, params);
				
			}
					
		return newCheckValuesMap;
	 }
	 
	/**
	 * Controlla ogni singola riga del CSV.
	 * 
	 * @param rowCounter: il contatore delle righe (parte da 0)
	 * @param columnPositionMap: la mappa che associa ad ogni colonna la sua posizione nella riga di intestazione.
	 * @param line: la riga corrente
	 * @param csvSplitBy: il separatore delle colonne sul CSV.
	 * @param univocityCheckMap: la mappa dei controlli di univocità.
	 * @throws CheckCSVException
	 */
	private static void checkRow(int rowCounter, Map<Integer, List> columnPositionMap, String line, String csvSplitBy, Map<String, String> univocityCheckMap) throws CheckCSVException {
		 
		String[] splitLine = line.split(csvSplitBy,-1);
		
		
		for(Integer columnPosition : columnPositionMap.keySet()){
			
			if (splitLine.length -1 < columnPosition){
				
				Object[] parameters = new Object[4];
				
				parameters[0] = csvSplitBy.charAt(csvSplitBy.length()-1);
				
				throw new MalformedCSVException(parameters);
			}
			
			String targetColumnValue = splitLine[columnPosition];
			
			List checkColumnParams = columnPositionMap.get(columnPosition);
			
			String columnName = (String) checkColumnParams.get(0);
			
			boolean isUnivocityCheck = (Boolean) checkColumnParams.get(1);
			
			List<String> admittedValues = checkColumnParams.subList(2, checkColumnParams.size());
			
			boolean isValidColumn = false;
			
			for (String admittedValue : admittedValues){
				
				if(targetColumnValue.equals(admittedValue)){
					
					isValidColumn= true;
					
					if (isUnivocityCheck && !univocityCheckMap.containsKey(columnName))
						
						univocityCheckMap.put(columnName, targetColumnValue);
					
					else if (isUnivocityCheck && !univocityCheckMap.get(columnName).equals(targetColumnValue)){
						
						Object[] parameters = new Object[4];
						
						parameters[0] = columnName;
						
						parameters[2] = rowCounter;
								
						throw new CheckValuesUniquenessException(parameters);
						
					}
					
					break;
				}
										
			}
			
			if (!isValidColumn){
				
				Object[] parameters = new Object[4];
				
				parameters[0] = columnName;
				
				parameters[1] = targetColumnValue;
				
				parameters[2] = rowCounter;
						
				throw new CheckAdmittedValuesException(parameters);
			}
		
		}
			
		
	 }
	
	public static int countLines(InputStream inputStream) throws IOException   
    {  
    	int lineCount = 0; 
    	
        if (inputStream != null) { 
        	
            String line = null;
  
           try {
        	   
               BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
                
               while ((line = reader.readLine()) != null)   
                
            	   lineCount ++; 
               
            } finally {  
            	
                inputStream.close();  
                
           }  
   
        }  
               
        return lineCount;   
        
    }
	
	public static void checkFileNumRows(File file, long maxNumRows) throws NumberFormatException, FileSizeException, IOException{
		
		InputStream is = new FileInputStream(file);
		
		Object[] params = {maxNumRows};
    	
    	if (countLines(is) > maxNumRows)
    							throw new FileSizeException(params);
    	
	}
}


