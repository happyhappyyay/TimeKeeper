/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keeper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author happyhappyyay
 */
public class WriteFile {
    final static String FILE = "HourLog.txt";
    
    public static void write(long date, long time, boolean start){
      try
      {
        File file = new File(FILE);
        if(start){
            List<String> contents = new ArrayList<>();

            if(file.exists()){
            String verify;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);            
            while((verify = br.readLine())!=null){
                 contents.add(verify);
            }
            
            br.close();
            }
            
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(date+","+time+",");
            bw.newLine();

            for(String s: contents){
                bw.write(s);
                bw.newLine();
            }
            
            bw.flush();
            bw.close();
        }
        else {
            String verify;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            List<String> contents = new ArrayList<>();
            while((verify = br.readLine())!=null){
                if(verify.contains(date+"")){
                    date=-1;
                    int dateEnd = verify.indexOf(",");
                    int startTimeEnd = verify.indexOf(",",dateEnd+1);
                    String startTimeStr = verify.substring(dateEnd+1,startTimeEnd);
                    String newLine = verify.substring(0,startTimeEnd)
                            .replace(startTimeStr, startTimeStr +","+time);
                    contents.add(newLine);
                }
                else{
                    contents.add(verify);
                }
            }
            br.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s: contents){
                bw.write(s);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }    
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }
    
    public static void convertWrite(String dateLabel, long currentDay, long time){
        DateFormat formatD = new SimpleDateFormat(TimeKeeper.DATE_FORMAT);
        try {
            Date labelDate = formatD.parse(dateLabel);
            write(labelDate.getTime(), time, false);
        } catch (ParseException ex) {
            Logger.getLogger(FileAssessor.class.getName()).log(Level.SEVERE, null, ex);
            write(currentDay, time, false);
        }
    }
}
