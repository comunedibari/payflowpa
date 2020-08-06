package it.tasgroup.services.util.filter;

import java.util.LinkedHashSet;
import java.util.Set;

public class Filters {

	/**
	 * @param prefix
	 * @param input
	 * @return
	 * 
	 * @author follia
	 *
	 * Overloading method
	 */
	private static Set<String> caseToogle(String prefix, String input) {
		return caseToogle(prefix, input, "", "");
	}
	
	/**
	 * @param prefix
	 * @param input
	 * @param preWildCard
	 * @param postWildCard
	 * @return
	 * 
	 * @author follia
	 * 
	 * This recursive method convert to upperCase/lowerCase the characters contained in the input string, 
	 * append them to the prefix and surround the resulting string with thw wildcards.
	 * The resulting string is stored in the returned Set  
	 */
	private static Set<String> caseToogle(String prefix, String input, String preWildCard, String postWildCard) {
		Set<String> filters = new LinkedHashSet<String>();
		if (input!=null && input.length() > 0) {			
//			System.out.println("Prefix: " + prefix);
//			System.out.println("Input: " + input);
			char[] inputCharacters = input.toCharArray();
			for (int n = 0; n < inputCharacters.length; n ++)  {
				char[] chars = inputCharacters.clone();	
				if (Character.getType(chars[n]) == Character.UPPERCASE_LETTER) {
					chars[n] = Character.toLowerCase(inputCharacters[n]);
					filters.add(preWildCard+prefix+String.valueOf(chars)+postWildCard);
					if (n < chars.length-1) {
						String newPrefix = prefix+String.valueOf(chars, 0, n+1);
						String newContent = String.valueOf(chars, (n+1), (chars.length-n-1));
//						System.out.println("Upper New prefix: " + newPrefix);
//						System.out.println("Upper New content: " + newContent);						
						filters.addAll(caseToogle(newPrefix, newContent, preWildCard, postWildCard));
					}
				} else if (Character.getType(chars[n]) == Character.LOWERCASE_LETTER) {
					chars[n] = Character.toUpperCase(inputCharacters[n]);
					filters.add(preWildCard+prefix+String.valueOf(chars)+postWildCard);
					if (n < chars.length-1) {
						String newPrefix = prefix+String.valueOf(chars, 0, n+1);
						String newContent = String.valueOf(chars, (n+1), (chars.length-n-1));
//						System.out.println("Lower New prefix: " + newPrefix);
//						System.out.println("Lower New content: " + newContent);						
						filters.addAll(caseToogle(newPrefix, newContent, preWildCard, postWildCard));
					}
				}
			}
		} //else if (input!=null) {
			//filters.add(input);
		//}
		return filters;
	}
	
	
	/**
	 * @param input
	 * @return
	 * 
	 * @author follia
	 * 
	 * Overloading method
	 */
	public static Set<String> convertCase(String input) {
		return convertCase(null, input, null, null);
	}

	/**
	 * @param input
	 * @param preWildCard
	 * @param postWildCard
	 * @return
	 * 
	 * @author follia
	 * 
	 * Overloading method
	 */
	public static Set<String> convertCase(String input, String preWildCard, String postWildCard) {
		return convertCase(null, input, preWildCard, postWildCard);
	}
	
	/**
	 * @param prefix
	 * @param input
	 * @return
	 * 
	 * @author follia
	 * 
	 * Overloading method
	 */
	public static Set<String> convertCase(String prefix,String input) {
		return convertCase(prefix, input, null, null);
	}
	
	/**
	 * @param prefix
	 * @param input
	 * @param preWildCard
	 * @param postWildCard
	 * @return
	 * @author follia
	 * 
	 * This method creates all the upperCase/lowerCase combinations for the input string, 
	 * append them to the prefix and surround the resulting string with thw wildcards.
	 * The resulting combinations are stored in the returned Set.
	 * Then convert to lowerCase/upperCase the prefix and repet the whole work. 
	 * The resulting combinations are added to the prevois Set.
	 */
	public static Set<String> convertCase(String prefix, String input, 
			String preWildCard, String postWildCard) {
		Set<String> filters = new LinkedHashSet<String>();
		String content = null;						

		if (input!=null && input.length()>0) {
			
			preWildCard = (preWildCard==null) ? "" : preWildCard;
			postWildCard = (postWildCard==null) ? "" : postWildCard;

			if (prefix!=null && prefix.length()>0) {
				content = input;
				filters.add(preWildCard+prefix+content+postWildCard);
			} else {
				if (input.length()>1) {
					prefix = input.substring(0,1);				
					content = input.substring(1);
					filters.add(preWildCard+prefix+content+postWildCard);
				} else {
					filters.add(preWildCard+input.toLowerCase()+postWildCard);
					filters.add(preWildCard+input.toUpperCase()+postWildCard);
				}
			}

			if (content != null) {
				filters.addAll(caseToogle(prefix,content, preWildCard, postWildCard));
				if (prefix.toUpperCase().equals(prefix)) {
					filters.add(preWildCard+prefix.toLowerCase()+content+postWildCard);
					filters.addAll(caseToogle(prefix.toLowerCase(),content, preWildCard, postWildCard));
				} else {
					filters.add(preWildCard+prefix.toUpperCase()+content+postWildCard);
					filters.addAll(caseToogle(prefix.toUpperCase(), content, preWildCard, postWildCard));
				}
			}
		} 
		return filters;
	}

	public static void main (String ... args) {
		if (args.length==0) {
			System.out.println("Please insert arguments:");
			System.out.println("1. Filter (mandatory)");
			System.out.println("2. Prefix (optional)");
			System.out.println("3. Pre WildCard (optional)");
			System.out.println("4. Post WildCard (optional)");			
		} else {
			String filter = args[0];
			String prefix = "";
			String preWildCard = "";
			String postWildCard = "";
			if (args.length>1) {
				prefix = args[1];
			} 
			if (args.length>2) {
				preWildCard = args[2];
			} 
			if (args.length>3) {
				postWildCard = args[3];
			}

			Set<String> filters = convertCase(prefix, filter, preWildCard, postWildCard);

			for (String element : filters) {
				System.out.println("..."+element+"...");
			}		
			System.out.println("Combination: " + filters.size());
		}
	}
	
}
