package it.tasgroup.ge.util;

import it.tasgroup.idp.util.IrisProperties;

public class PropertyUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println("max rows = " + getMaxRows());

		assertEquals(divideAndRoundUp(10, 2), 5);
		assertEquals(divideAndRoundUp(10, 3), 4);
		assertEquals(divideAndRoundUp(10, 10), 1);
		assertEquals(divideAndRoundUp(10, 11), 1);
		assertEquals(divideAndRoundUp(10, 1000), 1);
	}

	private static void assertEquals(int i, int j) {
		if (i != j) {
			throw new RuntimeException(i + " è diverso da " + j);
		}
	}

	

	public static int divideAndRoundUp(int dividendo, int divisore) {
		int quoziente = dividendo / divisore;
		int resto = dividendo % divisore;

		if (resto == 0) {
			// La divisione è esatta
		} else {
			// La divisione non è esatta, il quoziente è arrotondato per
			// difetto, aggiungo 1
			quoziente++;
		}

		return quoziente;
	}

	public static int getDimensioneLotto() {
		try{
			String s = IrisProperties.getProperty("GESTORE_EVENTI_DIMENSIONE_LOTTO", "100", true);
			Integer x = new Integer(s);
			return x.intValue();
		}catch(Exception e){
			e.printStackTrace();
			return 100;
		}
	}
	public static int getNumeroTentativi() {
		try{
			String s = IrisProperties.getProperty("GESTORE_EVENTI_NUM_TENTATIVI", "5", true);
			Integer x = new Integer(s);
			return x.intValue();
		}catch(Exception e){
			e.printStackTrace();
			return 5;
		}
	}

}