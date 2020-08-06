package it.nch.is.fo.tributi;

public class SelectedItemsFormImpl implements ISelectedItemsForm {

	private String [] selectedItems;
	
	@Override
	public String[] getSelectedItems() {
		return this.selectedItems;
	}

	@Override
	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;

	}

}
