package jchess;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by robert on 05.12.14.
 */
public enum StringResources {
    GUI ("jchess.resources.strings.gui"),
    MAIN ("jchess.resources.strings.main");

    private ResourceBundle bundle;
    private String baseName;

    StringResources(String baseName) {
        this.baseName = baseName;
        this.bundle = ResourceBundle.getBundle(baseName);
    }

    public String getString(String key){
        return this.bundle.getString(key);
    }

    public String getString(String key, Locale locale) {
        return ResourceBundle.getBundle(baseName, locale).getString(key);
    }
}
