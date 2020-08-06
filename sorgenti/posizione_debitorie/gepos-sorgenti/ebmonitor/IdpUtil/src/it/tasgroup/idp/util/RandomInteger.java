package it.tasgroup.idp.util;

import java.util.Random;

/** Generate 10 random integers in the range 0..99. */
public final class RandomInteger {
  
  public static synchronized int getNumber(int maxValue){
    log("Generating 10 random integers in range 0..99.");
    
    int randomInt = 0;
    //note a single Random object is reused here
    Random randomGenerator = new Random();
//    for (int idx = 1; idx <= 10; ++idx){
      randomInt = randomGenerator.nextInt(maxValue);
      log("Generated : " + randomInt);
//    }
    
    
    log("Done.");
    return randomInt;
  }
  
  private static void log(String aMessage){
    System.out.println(aMessage);
  }
}

