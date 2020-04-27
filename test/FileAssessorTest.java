/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Assert;
import org.junit.Test;
import keeper.FileAssessor;

/**
 *
 * @author happyhappyyay
 */
public class FileAssessorTest {
    
    public FileAssessorTest() {
    }    
//    
//    Return formatted file
//    
    @Test
    public void testReturnFormattedFileStart(){
        String expected = "October 27, 2019:"+System.lineSeparator()+"1          08:22 PM" + System.lineSeparator();
        String[] strings = new String[] {"1572152400000,1572225766798,"};
        String actual = FileAssessor.returnFormattedFile(strings);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testReturnFormattedFileEnd(){
        String expected = "October 27, 2019:"+System.lineSeparator()+"1          08:22 PM - 08:22 PM         0.00hrs" + System.lineSeparator();
        String[] strings = new String[] {"1572152400000,1572225766798,1572225767683"};
        String actual = FileAssessor.returnFormattedFile(strings);
        Assert.assertEquals(expected, actual);
    }
    
        @Test
    public void testReturnFormattedFileEndMultiple(){
        String expected = "October 27, 2019:"+System.lineSeparator()+"1          08:22 PM - 08:22 PM         0.00hrs" + System.lineSeparator()+"2          08:22 PM - 08:22 PM         0.00hrs" + 
                System.lineSeparator()+"Total:\t\t0.00hrs"+System.lineSeparator();
        String[] strings = new String[] {"1572152400000,1572225766798,1572225767683","1572152400000,1572225767685,1572225767699"};
        String actual = FileAssessor.returnFormattedFile(strings);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testReturnFormattedFileNoFile(){
        String expected = "File Not Found";
        String[] strings = new String[] {"File Not Found"};
        String actual = FileAssessor.returnFormattedFile(strings);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testReturnFormattedFileError(){
        String expected = "Error Reading File";
        String[] strings = new String[] {"Error Reading File"};
        String actual = FileAssessor.returnFormattedFile(strings);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testReturnFormattedFileCorrupt(){
        String expected = "Error Reading File";
        String[] strings = new String[] {"9393939392,02020200299,33333"};
        String actual = FileAssessor.returnFormattedFile(strings);
        Assert.assertEquals(expected, actual);
    }
    
//    
//    Calculate week hours
//    
    
    @Test
    public void testCalculateWeekHoursSingleDay(){
        double expected = 0.87;
        long weekStart = 1572152400000L; 
        String[] strings = new String[] {"1572152400000,1572225792842,1572228910827"};
        double actual = FileAssessor.calculateWeekHours(strings,weekStart);
        Assert.assertEquals(expected, actual,0.1);
    }
    
    @Test
    public void testCalculateWeekHoursMultipleSingleDay(){
        double expected = 4.35;
        long weekStart = 1572152400000L; 
        String[] strings = new String[] {"1572152400000,1572225792842,1572228910827",
            "1572152400000,1572225792842,1572228910827",
            "1572152400000,1572225792842,1572228910827",
            "1572152400000,1572225792842,1572228910827",
            "1572152400000,1572225792842,1572228910827"};
        double actual = FileAssessor.calculateWeekHours(strings,weekStart);
        Assert.assertEquals(expected, actual,0.1);
    }
    
    @Test
    public void testCalculateWeekHoursMultipleDaysWithNextWeek(){
        double expected = 4.35;
        long weekStart = 1572152400000L; 
        String[] strings = new String[] {"1572152400000,1572225792842,1572228910827",
            "1572152400000,1572225792842,1572228910827",
            "1572152400000,1572225792842,1572228910827",
            "1572152400000,1572225792842,1572228910827",
            "1572152400000,1572225792842,1572228910827",
            "1572757200000,1572225792842,1572228910827"};
        double actual = FileAssessor.calculateWeekHours(strings,weekStart);
        Assert.assertEquals(expected, actual,0.1);
    }
    
//    
//    Return time
//    
    @Test
    public void testRetrieveTime(){
        long[] expected = new long[] {1572225792842L,1572228910827L};
        long day = 1572152400000L;
        String[] strings = new String[] {"1572152400000,1572225792842,1572228910827"};
        long[] actual = FileAssessor.retrieveTime(strings,day);
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testRetrieveTimePostDayShort(){
        long[] expected = new long[] {0000000000001L};
        long day = 1572238800000L;
        String[] strings = new String[] {"0000000000000,0000000000001,"};
        long[] actual = FileAssessor.retrieveTime(strings,day);
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testRetrieveTimePostDay(){
        long[] expected = new long[] {1572195600000L};
        long day = 1572238800000L;
        String[] strings = new String[] {"1572152400000,1572195600000,"};
        long[] actual = FileAssessor.retrieveTime(strings,day);
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testRetrieveTimeStart(){
        long[] expected = new long[] {1572225792842L};
        long day = 1572152400000L;
        String[] strings = new String[] {"1572152400000,1572225792842,"};
        long[] actual = FileAssessor.retrieveTime(strings,day);
        Assert.assertArrayEquals(expected, actual);
    } 
    
    @Test
    public void testRetrieveTimeEmpty(){
        long[] expected = new long[]{};
        long day = 1572152400000L;
        String[] strings = new String[] {""};
        long[] actual = FileAssessor.retrieveTime(strings,day);
        Assert.assertArrayEquals(expected, actual);
    } 
}
