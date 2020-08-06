package it.regioneveneto.mygov.payment.mypivot.dao.specifications;

public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
    private boolean idField;
    private String childPersistent;
    

	public SearchCriteria(String key, String operation, Object value, boolean idField){
    	this.key = key;
    	this.operation = operation;
    	this.value = value;
    	this.idField = idField;
    }
	public SearchCriteria(String key, String operation, Object value, String childPersistent){
    	this.key = key;
    	this.operation = operation;
    	this.value = value;
    	this.idField = false;
    	this.childPersistent = childPersistent;
    }
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Object getValue() {
		return value;
	}
	public boolean isIdField() {
		return idField;
	}
	
	public void setIdField(boolean idField) {
		this.idField = idField;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	public String getChildPersistent() {
		return childPersistent;
	}
	public void setChildPersistent(String childPersistent) {
		this.childPersistent = childPersistent;
	}

}
