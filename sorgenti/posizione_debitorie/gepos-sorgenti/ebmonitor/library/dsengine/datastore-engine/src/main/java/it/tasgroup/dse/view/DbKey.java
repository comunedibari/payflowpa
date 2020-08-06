package it.tasgroup.dse.view;

public class DbKey {
	
	public final static String[] COL_NAMES = {"E2EMSGID","SenderId","SenderSys"};
	private final String[][] keyPairStrings;
	
	public DbKey(String[][] keyPairStrings ) {
		this.keyPairStrings=keyPairStrings;
	}
	
	public String[] getPair(short n){
		return keyPairStrings[n];
	}
	public String[] getValues(){
		int col_number= getNumberOfColumns();
		String[] vals=new String[col_number];
		for(int y=0;y<col_number;y++){
			vals[y]=keyPairStrings[y][0];
		}
		return vals;
	}

	public int getNumberOfColumns() {
		return keyPairStrings.length;
	}
}
