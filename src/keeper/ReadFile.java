/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keeper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author happyhappyyay
 */
public class ReadFile {
    final static String FILE = "HourLog.txt";
    private final String[] log;
    
    public ReadFile(){
        log = readFile();
    }
    
    private String[] readFile(){
        try{
            FileReader fileReader = new FileReader(FILE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<String> lines= new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
            bufferedReader.close();
            String[] fileLines = new String[lines.size()];
            return lines.toArray(fileLines); 
        }
        catch(FileNotFoundException e){
            return new String[] {"File Not Found"}; 
        }
        catch(IOException e){
            return new String[] {"Error Reading File"};
        }
    }

    public String[] getLog() {
        return log;
    }
}
