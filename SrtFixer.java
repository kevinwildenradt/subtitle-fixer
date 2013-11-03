// Kevin Wildenradt
// 11/2/13
//
import java.io.*;
import java.util.*;

public class SrtFixer {
    
    // Class constants
    public static final int MILLISECONDS_IN_A_SECOND = 1000;
    public static final int SECONDS_IN_A_MINUTE  = 60;
    public static final int MINUTES_IN_AN_HOUR = 60;
    public static final String TIME_SEPARATOR = " --> ";

    
    public static void main(String[] args) throws FileNotFoundException {

        Scanner subtitles = new Scanner(new File(args[0]));
        Long millisecondShift = Long.valueOf(args[1]);
        

        // read input into an ArrayList
        List<String> lineList = new ArrayList<String>();
        while (subtitles.hasNextLine())
            lineList.add(subtitles.nextLine());
        
        // starting at the first time window line, add the specified number
        // of milliseconds to start and end time
        
        for (String line : lineList) {
            if (line.contains(TIME_SEPARATOR)) {
		  		String[] times=line.split(TIME_SEPARATOR);
                ArrayList<String> newLine = new ArrayList<String>(2);
            
                for (String time : times) {
                    String[] timeComponents = time.split(":");
                
                    Long hours = Long.valueOf(timeComponents[0]);
                    Long minutes = Long.valueOf(timeComponents[1]);
                    Long seconds = Long.valueOf((timeComponents[2].split(","))[0]);
                    Long milliseconds = Long.valueOf((timeComponents[2].split(","))[1]);
                    Long newTotalMilliseconds = (milliseconds + (seconds * MILLISECONDS_IN_A_SECOND) + (minutes * SECONDS_IN_A_MINUTE * MILLISECONDS_IN_A_SECOND) + (hours * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE * MILLISECONDS_IN_A_SECOND) + millisecondShift);
                
                    milliseconds = newTotalMilliseconds % (MILLISECONDS_IN_A_SECOND);
                    seconds = (newTotalMilliseconds % (MILLISECONDS_IN_A_SECOND * SECONDS_IN_A_MINUTE)) / MILLISECONDS_IN_A_SECOND;
                    minutes = (newTotalMilliseconds % (MILLISECONDS_IN_A_SECOND * SECONDS_IN_A_MINUTE * MINUTES_IN_AN_HOUR)) / (MILLISECONDS_IN_A_SECOND * SECONDS_IN_A_MINUTE);
                    hours = (newTotalMilliseconds / (MILLISECONDS_IN_A_SECOND * SECONDS_IN_A_MINUTE * MINUTES_IN_AN_HOUR));
                
                    newLine.add("" + String.format("%02d" ,hours) + ":" + String.format("%02d" ,minutes) + ":" + String.format("%02d" ,seconds) + "," + String.format("%03d" ,milliseconds));
                }
                System.out.println(newLine.get(0) + TIME_SEPARATOR + newLine.get(1));
                newLine.clear();
            }
            else
                System.out.println(line);
        }
    }
}
