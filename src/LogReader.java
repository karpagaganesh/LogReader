import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LogReader {

    private void readLogFile(String logFileName)
    {
        try{
            FileInputStream fstream = new FileInputStream(logFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null)   {

                System.out.println (strLine);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    public static void main(String[] args) {
        LogReader logReader = new LogReader();
        logReader.readLogFile("030619_DUTTON_ROAD.log");
    }
}
