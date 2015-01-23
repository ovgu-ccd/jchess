package jchess.tests;

import jchess.util.Logging;
import jchess.util.StringResources;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class StringResourcesTest {
    private static final String GREETING_EN = "Hello World!";
    private static final String GREETING_DE = "Hallo Welt!";


    @BeforeClass
    public static void setup() {
        Logging.setup();
    }

    @Test
    public void testGetNativeString() {
        String greeting;
        String expected = GREETING_EN;
        if (Locale.getDefault().equals(Locale.GERMANY)) {
            expected = GREETING_DE;
        }
        greeting = StringResources.MAIN.getString("StringResources.GREETING");
        assertEquals(expected, greeting);
    }

    @Test
    public void testGetTranslatedString() {
        String greeting;
        String expected = GREETING_DE;
        Locale locale = Locale.GERMANY;
        if (Locale.getDefault().equals(Locale.GERMANY)) {
            locale = Locale.US;
            expected = GREETING_EN;
        }
        greeting = StringResources.MAIN.getString("StringResources.GREETING", locale);
        assertEquals(expected, greeting);
    }

}