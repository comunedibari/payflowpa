package it.tasgroup.iris.navigation;

import java.io.Serializable;
import java.util.List;

public class CarouselConfiguration implements Serializable {
	
	private boolean enabled;
	
	private List<CarouselItem> items;
	
	public boolean isEnabled() { 
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<CarouselItem> getItems() {
		return items;
	}
	
	public void setItems(List<CarouselItem> items) {
		this.items = items;
	}
	
}
