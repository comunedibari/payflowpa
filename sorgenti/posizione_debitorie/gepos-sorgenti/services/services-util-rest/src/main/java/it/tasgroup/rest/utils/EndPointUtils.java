package it.tasgroup.rest.utils;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

import java.util.List;


public class EndPointUtils {
    public static final long ONE_DAY = 1000 * 60 * 60 * 24;


    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static boolean isEmpty(Object o) {
        return o == null;
    }


    public static boolean allNulls(Object... parameters) {
        boolean allNulls = true;
        for (Object p : parameters) {
            if (p != null){
                allNulls = false;
                break;
            }
        }

        return allNulls;
    }

    public static boolean checkAllowedURIs(List<String> allowedURIs, String requestURI) {
        boolean allowedURI = false;
        int allowedURIsSize = allowedURIs.size();
        for (int i = 0; i < allowedURIsSize && !allowedURI; i++) {
            String uri = allowedURIs.get(i);
            if (requestURI.endsWith(uri)) {
                allowedURI = true;
            }
        }
        return allowedURI;
    }


}
