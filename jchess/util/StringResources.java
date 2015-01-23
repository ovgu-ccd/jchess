package jchess.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by robert on 05.12.14.
 */
public enum StringResources {
    GUI("jchess.resources.strings.gui"),
    MAIN("jchess.resources.strings.main");

    private ResourceBundle bundle;
    private String baseName;

    StringResources(String baseName) {
        this.baseName = baseName;
        this.bundle = ResourceBundle.getBundle(baseName);
    }

    public String getString(String key) {
        String val = this.bundle.getString(key);
        String encoded = "Encoding Error.";
        try {
            return new String(val.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encoded;
    }


    public String getString(String key, Locale locale) {
        String val = ResourceBundle.getBundle(baseName, locale).getString(key);
        String encoded = "Encoding Error.";
        try {
            return new String(val.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encoded;
    }
}
