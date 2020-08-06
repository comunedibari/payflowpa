/**
 * 
 */
package it.tasgroup.iris.shared.util.shoppingcart;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe che implementa un carrello per il commercio elettronico in grado di aggiungersi e togliersi elementi, 
 * nonche' di calcolare il totale del suo contenuto. E' adatto ad essere memorizzato in una Web Session.
 * 
 * @author pazzik
 *
 */
public class  SessionShoppingCart <T extends ISessionShoppingCartItem> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1374940340305816473L;
	
	private List<T> itemList = new ArrayList<T>();
	private T massiveCartItem;
	private Date massiveScadenza;
	// "true" "false"
	private Boolean opposizioneInvio730 = null;
	
	private String  tipoSoggettoVers=null; 	 
	private String  anagraficaVers=null;
	private String  indirizzoVers=null; 
	private String  numeroCivicoVers=null; 
	private String  capVers=null; 
	private String  localitaVers=null; 
	private String  provinciaVers=null; 
	private String  nazioneVers=null; 
	private String  emailVers = null;
	private String  codFiscaleVers = null;
	
	private Boolean redirectOnly = null;

	public List<T> getItemList() {
		
		return itemList;
		
	}

	public void setItemList(List<T> itemList) {
		
		this.itemList = itemList;
		
	}
	
	public T getMassiveCartItem() {
        return massiveCartItem;
    }

    public void setMassiveCartItem(T massiveCartItem) {
        this.massiveCartItem = massiveCartItem;
    }

    public void addItem(T item) {
		
		if(!this.containsItem(item.getItemId()))				
			
				itemList.add(item);
		
	}
	
	public void addItemList(List<T> items) {
		
		for(T item: items)
			
			addItem(item);
		
	}
	
	public void removeItem(T item) {
		
		itemList.remove(item);
		
	}
	
	public void removeAllItems() {
		
		itemList.clear();
		
		this.massiveCartItem = null;
		this.massiveScadenza = null;
		this.opposizioneInvio730 = null;
		
	}
	
	public int getCartSize(){
		
		return itemList.size();
		
	}
	
	public BigDecimal getCartTotalAmount(){
		
		BigDecimal totalAmount = new BigDecimal(0);
		
		for(ISessionShoppingCartItem item: itemList){
			
			totalAmount = totalAmount.add(item.getItemAmount());
			
		}
		
		return totalAmount;
		
	}

	public void removeItem(String itemId) {
		
		Iterator<T> it = itemList.iterator();
		
		T vo = null;

		while (it.hasNext()) {
			
			vo = (T) it.next();
			
			if (vo.getItemId().equals(itemId))
				break;
		}
		
		it.remove();
		
	}
	
	public void removeItemList(ArrayList<String> itemsId) {
		for (String itemId : itemsId) {
			removeItem(itemId);
		}
	}
	
	public boolean isEmpty(){
		
		return itemList.isEmpty();
	}

	public String extractUniqueDebtor() {
		
		String uniqueDebtor = null;
		
		for (T item: itemList){
			
			if(uniqueDebtor==null) 
				uniqueDebtor= item.getDebtor();			
			else			
				if (!item.getDebtor().equals(uniqueDebtor))
					return null;				
		}
		
		return uniqueDebtor;
	}
	
	public ISessionShoppingCartItem getCartItem(String itemId){
		
		for (ISessionShoppingCartItem item : itemList) {
			
			if (item.getItemId().equals(itemId)) 
				
				return item;
		}
		
		return null;
	}
	
	public boolean containsItem(String itemId){
		
		return getCartItem(itemId)!=null;
		
	}
	
	public List<String> getItemIdList(){
		
		List<String> idList = new ArrayList<String>();
		
		for(ISessionShoppingCartItem item : itemList){
			
			idList.add(item.getItemId());
			
		}
		
		return idList;
	}
	
	public Boolean getIsMassiveCart(){
	    String tributo = null;
	    
	    Integer massiveCartSize = getMassiveCartSize();
	    
	    if(getItemList().size() < massiveCartSize){
	        massiveScadenza = null;
	        return false;
	    }
	    
	    for(ISessionShoppingCartItem item :getItemList()){
	        if(!(item instanceof SessionShoppingCartItemDTO)){
	            massiveScadenza = null;
                return false;
	        }
	        
            SessionShoppingCartItemDTO cartItemDTO = (SessionShoppingCartItemDTO) item;
            if(tributo == null || tributo.equals(cartItemDTO.getTributo())){
                tributo = cartItemDTO.getTributo();
                
                if(!"tassa automobilistica".equalsIgnoreCase(tributo)){
                    massiveScadenza = null;
                    return false;
                }
                
                if(massiveScadenza == null || cartItemDTO.getScadenza().before(massiveScadenza)){
                    massiveScadenza = cartItemDTO.getScadenza();
                }
            } else {
                massiveScadenza = null;
                return false;
            }
	    }
	    
	    return true;
	}

    public Integer getMassiveCartSize() {
        String size = null;
        
        try{
            ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
            size = props.getProperty("massive.carrello.size");
        } catch (Exception e){
            e.printStackTrace();
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }
        
        Integer massiveCartSize = (size != null && size.matches("\\d") ? Integer.valueOf(size) : 9);
        return massiveCartSize;
    }

    public Date getMassiveScadenza() {
        return massiveScadenza;
    }

    public void setMassiveScadenza(Date massiveScadenza) {
        this.massiveScadenza = massiveScadenza;
    }
    
    public Boolean getOpposizioneInvio730() {
		return opposizioneInvio730;
	}

    public void setOpposizioneInvio730(Boolean opposizioneInvio730) {
		this.opposizioneInvio730 = opposizioneInvio730;
	}

	public static void main(final String[] args)
    {
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
        
//        Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
//        String size = beProperties.getProperty("massive.carrello.size");
        
//        System.out.println(Properties.class.getClassLoader().getResource("").getPath());
//        ConfigurationPropertyLoader pippo = new ConfigurationPropertyLoader();
        
        System.out.println(ConfigurationPropertyLoader.getInstance("iris-fe.properties").getProperty("massive.carrello.size"));
        System.out.println(ConfigurationPropertyLoader.getAbsoluteConfPath());
        
//        System.out.println("size = " + size);
    }

	public String getEmailVers() {
		return emailVers;
	}

	public void setEmailVers(String emailVers) {
		this.emailVers = emailVers;
	}

	public String getCodFiscaleVers() {
		return codFiscaleVers;
	}

	public void setCodFiscaleVers(String codFiscaleVers) {
		this.codFiscaleVers = codFiscaleVers;
	}

	public String getTipoSoggettoVers() {
		return tipoSoggettoVers;
	}

	public void setTipoSoggettoVers(String tipoSoggettoVers) {
		this.tipoSoggettoVers = tipoSoggettoVers;
	}

	public String getAnagraficaVers() {
		return anagraficaVers;
	}

	public void setAnagraficaVers(String anagraficaVers) {
		this.anagraficaVers = anagraficaVers;
	}

	public String getIndirizzoVers() {
		return indirizzoVers;
	}

	public void setIndirizzoVers(String indirizzoVers) {
		this.indirizzoVers = indirizzoVers;
	}

	public String getNumeroCivicoVers() {
		return numeroCivicoVers;
	}

	public void setNumeroCivicoVers(String numeroCivicoVers) {
		this.numeroCivicoVers = numeroCivicoVers;
	}

	public String getCapVers() {
		return capVers;
	}

	public void setCapVers(String capVers) {
		this.capVers = capVers;
	}

	public String getLocalitaVers() {
		return localitaVers;
	}

	public void setLocalitaVers(String localitaVers) {
		this.localitaVers = localitaVers;
	}

	public String getProvinciaVers() {
		return provinciaVers;
	}

	public void setProvinciaVers(String provinciaVers) {
		this.provinciaVers = provinciaVers;
	}

	public String getNazioneVers() {
		return nazioneVers;
	}

	public void setNazioneVers(String nazioneVers) {
		this.nazioneVers = nazioneVers;
	}
	
	public boolean getCfDebitoreAnonimo(){
		String cfAnonimo = ConfigurationPropertyLoader.getInstance( "iris-fe.properties").getProperty("iris.codice.fiscale.anonymous"); 
		
		boolean found = false;
		for (T item : itemList) {
			if(item.getDebtor().equals(cfAnonimo)
					&& !found) {
				found = true;
			}	
		}
		return found;
	}

	public Boolean getRedirectOnly() {
		return redirectOnly;
	}

	public void setRedirectOnly(Boolean redirectOnly) {
		this.redirectOnly = redirectOnly;
	}
	

	
}
