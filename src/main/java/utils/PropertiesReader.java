package utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesReader {
    private static ResourceBundle pontoProperties = ResourceBundle.getBundle("ponto");

    public static String getPontoProperties(String key)  {
        String value ="";
        try {
            value = pontoProperties.getString(key);
        }
        catch(MissingResourceException e) {
            throw new RuntimeException("chave \"" + key + "\" nao existe no ponto.properties");
        }
        if (value.equals(""))
            System.out.println("chave \"" + key + "\" possui valor vazio");
        return value;
    }

}
