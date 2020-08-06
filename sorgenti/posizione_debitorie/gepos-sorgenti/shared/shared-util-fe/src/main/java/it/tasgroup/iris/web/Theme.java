package it.tasgroup.iris.web;


import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

public class Theme {

    public static final String DEFAULT_ATTRIBUTE_NAME = "_theme";

    private String prefix = "theme-";

    private Properties themeCodes;

    private Map<String, List<Property>> propertiesByPrefix = new HashMap<String, List<Property>>();


    public Theme(ThemePropertiesDecorator themePropertiesDecorator) throws IOException {
        themeCodes = themePropertiesDecorator.getProperties(new Properties());
    }


    public Theme(String themeName, String prefix, boolean reload, ThemePropertiesDecorator themePropertiesDecorator) throws IOException {

        themeName = getThemeName(themeName);

        if (prefix != null)
            this.prefix = prefix;

        themeName = this.prefix + themeName;

        themeCodes = ConfigurationPropertyLoader.getProperties(themeName + ".properties", reload);

        themeCodes = themePropertiesDecorator.getProperties(themeCodes);
    }

    public Theme(String themeName, String prefix, boolean reload) throws IOException {

        themeName = getThemeName(themeName);

        if (prefix != null)
            this.prefix = prefix;

        themeName = this.prefix + themeName;

        themeCodes = ConfigurationPropertyLoader.getProperties(themeName + ".properties", reload);
    }

    private static String getThemeName(String themeName) {
        if (themeName == null || "".equals(themeName.trim())) {
            themeName = "paytas";
        }
        return themeName;
    }

    public Theme(String themeName, boolean forceReload) throws IOException {
        this(themeName, null, forceReload);
    }

    public Theme(String themeName, boolean forceReload, ThemePropertiesDecorator provider) throws IOException {
        this(themeName, null, forceReload, provider);
    }

    public Theme(String themeName, ThemePropertiesDecorator themePropertiesDecorator) throws IOException {
        this(themeName, null, false, themePropertiesDecorator);
    }


    public static Theme getTheme(HttpServletRequest request) {

        // return first Theme searching from request->session->application

        if (request.getAttribute(Theme.DEFAULT_ATTRIBUTE_NAME) == null) {

            if (request.getSession().getAttribute(Theme.DEFAULT_ATTRIBUTE_NAME) == null)

                return (Theme) request.getSession().getServletContext().getAttribute(Theme.DEFAULT_ATTRIBUTE_NAME);

            return (Theme) request.getSession().getAttribute(Theme.DEFAULT_ATTRIBUTE_NAME);

        }

        return (Theme) request.getAttribute(Theme.DEFAULT_ATTRIBUTE_NAME);

    }

    public String getPrefix() {
        return this.prefix;
    }

    public Theme(String themeName) throws IOException {
        this(themeName, null, false);
    }

    public String getContextCode(String name, HttpServletRequest request) {
        return request.getContextPath() + themeCodes.getProperty(name);
    }

    public String getCode(String name) {
        return themeCodes.getProperty(name);
    }

    public String getName() {
        return getCode("name");
    }

    public Properties getThemeCodes() {
        return this.themeCodes;
    }

    public List<Property> getPropertiesListByPrefix(String prefix) {
        List<Property> propertyList;
        Enumeration<String> propertyNames = (Enumeration<String>) themeCodes.propertyNames();
        if (!propertiesByPrefix.containsKey(prefix)) {
            propertyList = new ArrayList<Property>();
            String s = prefix + ".";
            while (propertyNames.hasMoreElements()) {
                String propertyName = propertyNames.nextElement();
                if (propertyName.startsWith(s)) {
                    propertyList.add(new Property(propertyName, themeCodes.getProperty(propertyName)));
                    propertiesByPrefix.put("prefix", propertyList);
                }

            }
        } else {
            propertyList = propertiesByPrefix.get(prefix);
        }

        return propertyList;
    }

    public class Property implements Map.Entry<String, String> {


        private String key;
        private String value;

        public Property(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Property() {
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String setValue(String s) {
            return this.value;
        }
    }

}
