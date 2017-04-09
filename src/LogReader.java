import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LogReader {

    private final String lineSplitter = "=================================================================================================================";
    private void readLogFile(String logFileName)
    {
        try{
            FileInputStream fstream = new FileInputStream(logFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            boolean newModel = false;
            int lineSplitterCount = 0;
            while ((strLine = br.readLine()) != null)   {
                String newLine = strLine.trim();
                if(lineSplitter.equalsIgnoreCase(newLine)){
                    lineSplitterCount++;
                }
                if(strLine.contains("MO   ")){
                    System.out.println();
                    String name = getMOName(newLine);
                }
                System.out.println (strLine);
                if(lineSplitterCount==2){
                    lineSplitterCount = 0;
                    newModel = true;
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    private String getMOName(String newLine) {
        String moName = "";
        String[] split = newLine.split(" ");
        if(split[0].equalsIgnoreCase("MO")){
            int i = 1;
            while (split[i].equalsIgnoreCase("")){
                i++;
            }
            moName = split[i];
        }
        return moName;
    }

    public static void main(String[] args) {
        LogReader logReader = new LogReader();
        logReader.readLogFile("030619_DUTTON_ROAD.log");
    }
}
