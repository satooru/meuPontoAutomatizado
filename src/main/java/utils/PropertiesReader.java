package utils;

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesReader {
    private static ResourceBundle pontoProperties = ResourceBundle.getBundle("ponto");
    private static String pontoPropertiesPath = System.getProperty("user.dir")
                                              + java.io.File.separator
                                              + "src"
                                              + java.io.File.separator
                                              + "main"
                                              + java.io.File.separator
                                              + "resources"
                                              + java.io.File.separator;

    public static String getPontoProperties(String key)  {
        String value ="";
        try {
            value = pontoProperties.getString(key);
        }
        catch(MissingResourceException e) {
            throw new RuntimeException("chave \"" + key + "\" nao existe no ponto.properties");
        }
        if (value.equals("")) System.out.println("chave \"" + key + "\" possui valor vazio");
        return value;
    }
    
    public static void setPontoProperties(String key, String value) {
        String[] lines = FileManipulator.readFileFromPath(pontoPropertiesPath + "ponto.properties").split("[\n|\r]");
        ArrayList<String> newContent = new ArrayList<String>();
        String newLine = "";
        for (String line : lines) {
            if(line.split("=")[0].trim().equals(key)) {
                newLine = key + " = " + value;
                newContent.add(newLine);
            }
            else {
                newContent.add(line);
            }
        }
        if(!newLine.equals("")) {
            FileManipulator.writeToFile(pontoPropertiesPath + "ponto.properties", newContent.toArray(new String [newContent.size()]));
            System.out.println("ponto.properties alterado. linha alterada: " + newLine);
            pontoProperties = ResourceBundle.getBundle("ponto");
        }
        else {
            System.out.println("chave \"" + key + "\" nao encontrada em ponto.properties");
        }
    }
}
