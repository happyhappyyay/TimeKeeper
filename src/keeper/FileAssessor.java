/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 * @author happyhappyyay
 */
public class FileAssessor {
        final static String NOFILE ="File Not Found";
        final static String FILEERROR = "Error Reading File";
        final static String LOGPATTERN = "(\\d{13},\\d{13},$)|(\\d{13},\\d{13},\\d{13})";
        final static long MILLIHOURCONV = 3600000;

        
    public static String returnFormattedFile(String[] log){
        StringBuilder sb = new StringBuilder();
        Long comparisonDateTime = 0L;
        double hours = 0;
        int dateMembers = 0;
        for(int i = 0; i<log.length;i++){
            if(Pattern.matches(LOGPATTERN,log[i])){                
                int dateEnd = log[i].indexOf(",");
                long storedDate = Long.valueOf(log[i].substring(0,dateEnd));
                DateFormat format;
                Date date;
                String dateString;
                if(storedDate != comparisonDateTime){
                    if(i!=0){
                        if(dateMembers>1){
                        String bottomLine = "Total:\t\t"+String.format(Locale.US,"%.2f",hours)+"hrs"+System.lineSeparator()+System.lineSeparator();
                        sb.append(bottomLine);
                        }
                        else{
                            sb.append(System.lineSeparator());
                        }
                    }
                    format = new SimpleDateFormat(TimeKeeper.DATE_FORMAT);
                    date = new Date(storedDate);
                    dateString = format.format(date) + ":" + System.lineSeparator();
                    sb.append(dateString);
                    comparisonDateTime = storedDate;
                    hours = 0;
                    dateMembers=0;
                }
                dateMembers++;
                
                int startEndTime = log[i].lastIndexOf(",");
                long startTime = Long.parseLong(log[i].substring(dateEnd+1,startEndTime));
                format = new SimpleDateFormat(TimeKeeper.TIME_FORMAT);
                date = new Date(startTime);
                dateString = dateMembers+"          "+format.format(date);
                sb.append(dateString);
                
                if(startEndTime+1 != log[i].length()){
                    long endTime = Long.parseLong(log[i].substring(startEndTime+1));
                    double hrs = (endTime-startTime)/(MILLIHOURCONV*1.0);
                    hours+=hrs;
                    date = new Date(endTime);
                    dateString = " - " + format.format(date) + "         " +String.format(Locale.US,"%.2f",hrs)+"hrs";
                    sb.append(dateString);
                    if(i==log.length-1&&dateMembers>1){
                        String bottomLine = System.lineSeparator()+"Total:\t\t"+String.format(Locale.US,"%.2f",hours)+"hrs";
                        sb.append(bottomLine);
                    }
                }
                sb.append(System.lineSeparator());
            }
            else {
                if(log[0].equals(NOFILE)){
                    return(NOFILE);
                }
                else{
                    return(FILEERROR);
                }
            }
        }
        return sb.toString();
    }
        
    
    public static double calculateWeekHours(String[] log,long weekStart){
        final long WEEK = 604800000;
        long weekEnd = weekStart + WEEK;
        double hours = 0;
        for(String s: log){
            if(Pattern.matches(LOGPATTERN,s)){
                boolean hasHours = !(s.substring(s.length()-1).equals(","));
                if(hasHours){
                    int dateEnd = s.indexOf(",");
                    long date = Long.valueOf(s.substring(0,dateEnd));
                    if(date >= weekStart & date < weekEnd){
                         int startEndTime = s.lastIndexOf(",");
                         long startTime = Long.parseLong(s.substring(dateEnd+1,startEndTime));
                         long endTime = Long.parseLong(s.substring(startEndTime+1));
                         double tempHours = (endTime-startTime)/(MILLIHOURCONV*1.0);
                         hours += tempHours;
                    }
                }
            }
        }      
        return hours;
    }
    
    public static long[] retrieveTime(String[] log, long currentStartTime){
        for(String s: log){
            if(Pattern.matches(LOGPATTERN,s)){
                int dateEnd = s.indexOf(",");
                long date = Long.valueOf(s.substring(0,dateEnd));
                int startEndTime = s.lastIndexOf(",");
                long startTime = Long.parseLong(s.substring(dateEnd+1,startEndTime));
                if(startEndTime+1 != s.length()){
                    if(currentStartTime == date){
                        long endTime = Long.parseLong(s.substring(startEndTime+1));
                        return new long[] {startTime,endTime};
                    }
                    break;
                }
                else{
                    return new long[] {startTime};
                    }
            }
        }
        return new long[]{};
    }
}
