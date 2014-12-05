package jchess.resources.strings;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by robert on 05.12.14.
 */
public enum StringResources {
    GUI (ResourceBundle.getBundle("jchess.resources.strings.gui")),
    MAIN (ResourceBundle.getBundle("jchess.resources.strings.main"));

    private ResourceBundle bundle;

    StringResources(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getString(String key){
        return this.bundle.getString(key);
    }
}
