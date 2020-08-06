package it.nch.fwk.fo.pager;

import java.io.Serializable;


public class Page implements Serializable{  
    private String label;
    private boolean isSelected;
    private int position;
    private boolean isPage;
    private int numPage;

    public Page(){
        this.isSelected = false;
        this.isPage = true;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setIsPage(boolean isPage) {
        this.isPage = isPage;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition() {
        return position;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public boolean getIsPage() {
        return isPage;
    }

	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}


}
