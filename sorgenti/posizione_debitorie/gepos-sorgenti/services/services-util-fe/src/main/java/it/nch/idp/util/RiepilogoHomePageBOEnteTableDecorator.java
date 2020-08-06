package it.nch.idp.util;

import it.tasgroup.iris.dto.CruscottoHomePageDTO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.displaytag.decorator.TableDecorator;

public class RiepilogoHomePageBOEnteTableDecorator extends TableDecorator {

    public String getTipoDebito() {
		String tipoDebito = "";
		try {
		    CruscottoHomePageDTO dto = (CruscottoHomePageDTO) getCurrentRowObject();
		    String tipo = dto.getTipoDebito();
			if(tipo != null && tipo.equalsIgnoreCase("Totali")){
			    tipoDebito = "<span style='font-weight: bold'>" + tipo + "</span>";
			} else {
			    tipoDebito = tipo;
			}
		} catch (Exception e) {
			return "";
		}
		
		return tipoDebito;	
	}
    
    public String getNumero() {
        String numero = "";
        try {
            CruscottoHomePageDTO dto = (CruscottoHomePageDTO) getCurrentRowObject();
            String tipo = dto.getTipoDebito();
            if(tipo != null && tipo.equalsIgnoreCase("Totali")){
                numero = "<span style='font-weight: bold'>" + dto.getNumero() + "</span>";
            } else {
                numero += dto.getNumero(); 
            }
        } catch (Exception e) {
            return "";
        }
        
        return numero;  
    }
    
    public String getImporto() {
        String importo = "";
        try {
            CruscottoHomePageDTO dto = (CruscottoHomePageDTO) getCurrentRowObject();
            String tipo = dto.getTipoDebito();
            
            NumberFormat df = NumberFormat.getNumberInstance(Locale.ITALIAN);
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            df.setGroupingUsed(true);

            String imp = df.format(dto.getImporto());
            
            String formattedImp = imp + "&nbsp;&euro;";
            if(tipo != null && tipo.equalsIgnoreCase("Totali")){
                importo = "<span style='font-weight: bold'>" + formattedImp + "</span>";
            } else {
                importo =  formattedImp;
            }
        } catch (Exception e) {
            return "";
        }
        
        return importo;  
    }

	
}
