package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FileManipulator {

    public static String readFileFromPath(String path) {
        return readFile(path);
    }

    private static String readFile(String filename) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line;

            //Dados
            while ((line = bufferedReader.readLine()) != null) sb.append(line+"\n");

            bufferedReader.close();
            return sb.toString();
        }
        catch( Exception e ) {
            throw new RuntimeException( e.getMessage() );
        }
    }

    public static void writeToFile(String filepath, String content) throws IOException{
        Path path = Paths.get(filepath);
        if (!Files.exists(path.getParent())) path.getParent().toFile().mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(content);
        writer.close();
    }

    //TODO streamline essa parte aqui. pra que ter 2 metodos que fazem praticamente a mesma coisa
    public static void writeToFile(String filepath, String[] lines) {
        try {
            String content = "";
            for (int i = 0; i < lines.length; i++) {
                content = i == lines.length ? content.substring(0, content.length() -2) : content + lines[i] + "\r\n";
            }
            Path path = Paths.get(filepath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            writer.write(content);
            writer.close();
        }
        catch( Exception e ) {
            throw new RuntimeException( e.getMessage() );
        }
    }

    public static File[] listFilesUsingFilter(String dir, String... filters) throws Exception {
        File folder = new File(dir);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new Exception("O diretorio " + dir + " nao existe ou nao e diretorio");
        }
        if(filters != null && filters.length > 0){
            FileFilter fileFilters = new WildcardFileFilter(filters);
            return folder.listFiles(fileFilters);
        }
        else
            return folder.listFiles();
    }

    public static boolean doesPathExist(String path){
        Path paths = Paths.get(path);
        return Files.exists(paths);
    }

    public static void makeDir(String path) throws Exception {
        Path paths = Paths.get(path);
        makeDir(paths);
    }

    public static void makeDir(Path path) {
        if(Files.exists(path)) System.out.println("diretorio " + path + " ja existe");
        else{
            try{
                Files.createDirectory(path);
                System.out.println("diretorio " + path + " criado");
            }
            catch(Exception e){
                System.out.println("erro ao criar diretorio: " + path);
                e.printStackTrace();
            }
        }
    }

    public static boolean moveFile(String source, String target, String... filters) throws Exception{
        boolean operation = false;
        Path sourcePath = Paths.get(source); 
        if(sourcePath.toFile().exists()){
            if (filters != null && filters.length > 0){
                File[] files = listFilesUsingFilter(source, filters);
                if(files.length > 0){
                    for(File file : files)
                        Files.move(file.toPath(), Paths.get(target + file.getName()));
                    for(String filter : filters)
                        System.out.println("movido arquivo(s) " + source + filter + "  -> " + target);
                    operation = true;
                }
            }
            else{
                Files.move(Paths.get(source), Paths.get(target));
                operation = true;
            }
        }
        else System.out.println("arquivo ou diretorio " + source + " nao encontrado");
        return operation;
    }

    public static long getFileSize(String filepath){
        Path file = Paths.get(filepath);
        return file.toFile().length();
    }
}
