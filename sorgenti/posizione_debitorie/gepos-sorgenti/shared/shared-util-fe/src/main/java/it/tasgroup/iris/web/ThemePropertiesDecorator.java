package it.tasgroup.iris.web;

import java.io.IOException;
import java.util.Properties;

/**
 *
 */
public interface ThemePropertiesDecorator {
    Properties getProperties(Properties properties) throws IOException;
}
