import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class LogReader {

    private final String lineSplitter = "=================================================================================================================";
    private void readLogFile(String logFileName)
    {
        Set<MOModel> moModelSet = new HashSet<>();

        try{
            FileInputStream fstream = new FileInputStream(logFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            boolean newModel = false;
            int lineSplitterCount = 0;
            MOModel moModel = new MOModel();
            int reservedByArraySize = 0;
            int rfBranchRefArraySize = 0;
            StringBuilder reservedByStringBuilder = new StringBuilder();
            StringBuilder rfBranchRefStringBuilder = new StringBuilder();

            while ((strLine = br.readLine()) != null)   {
                String newLine = strLine.trim();
                if(newLine.contains("Test Construction OK"))
                    lineSplitterCount = 0;
                if(lineSplitter.equalsIgnoreCase(newLine)){
                    lineSplitterCount++;
                }

                if(lineSplitterCount==3){
                    moModelSet.add(moModel);
                    lineSplitterCount = 1;
                    newModel = true;
                    moModel = new MOModel();
                }

                if(strLine.contains("MO   ")){
                    System.out.println();
                    String name = getKeyValueProperty("MO",newLine);
                    if(name.equalsIgnoreCase("SubNetwork=ONRM_ROOT_MO_R,SubNetwork=MKT_030,MeContext=030619_DUTTON_ROAD,ManagedElement=1,SystemFunctions=1,Ncli=1")){
                        System.out.println();
                    }
                    moModel.name = name;
                }
                if(strLine.contains("SectorEquipmentFunctionId       ")){
                    String SectorEquipmentFunctionId = getKeyValueProperty("SectorEquipmentFunctionId", newLine);
                    if (SectorEquipmentFunctionId != null && SectorEquipmentFunctionId.length() > 0) {
                        moModel.SectorEquipmentFunctionId = SectorEquipmentFunctionId;
                    }
                }

                if(strLine.contains("administrativeState     ")){
                    String administrativeState = getKeyValueProperty("administrativeState", newLine);
                    if (administrativeState != null && administrativeState.length() > 0) {
                        moModel.administrativeState = administrativeState;
                    }
                }

                if(strLine.contains("availabilityStatus     ")){
                    String availabilityStatus = getKeyValueProperty("availabilityStatus", newLine);
                    if (availabilityStatus != null && availabilityStatus.length() > 0) {
                        moModel.availabilityStatus = availabilityStatus;
                    }
                }

                if(strLine.contains("availableHwOutputPower     ")){
                    String availableHwOutputPower = getKeyValueProperty("availableHwOutputPower", newLine);
                    if (availableHwOutputPower != null && availableHwOutputPower.length() > 0) {
                        moModel.availableHwOutputPower = availableHwOutputPower;
                    }
                }

                if(strLine.contains("eUtranFqBands")){
                    String eUtranFqBands = getKeyValuePropertyContainsKey("eUtranFqBands", newLine);
                    if (eUtranFqBands != null && eUtranFqBands.length() > 0) {
                        moModel.eUtranFqBands = eUtranFqBands;
                    }
                }

                if(strLine.contains("mixedModeRadio   ")){
                    String mixedModeRadio = getKeyValueProperty("mixedModeRadio", newLine);
                    if (mixedModeRadio != null && mixedModeRadio.length() > 0) {
                        moModel.mixedModeRadio = mixedModeRadio;
                    }
                }

                if(strLine.contains("operationalState   ")){
                    String operationalState = getKeyValueProperty("operationalState", newLine);
                    if (operationalState != null && operationalState.length() > 0) {
                        moModel.operationalState = operationalState;
                    }
                }

                if(strLine.contains("reservedBy[")){
                    reservedByArraySize = getArraySize("reservedBy",newLine);
                }

                if(strLine.contains("rfBranchRef[")){
                    rfBranchRefArraySize = getArraySize("rfBranchRef",newLine);
                }

                if(strLine.contains(">>> reservedBy")){
                    String reservedByString = strLine.substring(">>> rfBranchRef = ".length());
                    reservedByStringBuilder.append(reservedByString+" ");
                    reservedByArraySize--;
                    if(reservedByArraySize==0){
                        moModel.reservedBy = reservedByStringBuilder.toString().trim();
                        reservedByStringBuilder = new StringBuilder();
                    }
                }

                if(strLine.contains(">>> rfBranchRef")){
                    String rfBranchRefString = strLine.substring(">>> rfBranchRef = ".length());
                    rfBranchRefStringBuilder.append(rfBranchRefString+" ");
                    rfBranchRefArraySize--;
                    if(rfBranchRefArraySize==0){
                        moModel.rfBranchRef = rfBranchRefStringBuilder.toString().trim();
                        rfBranchRefStringBuilder = new StringBuilder();
                    }
                }


                System.out.println (strLine);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println(moModelSet);

    }

    private int getArraySize(String key, String newLine) {
        String[] split = newLine.split(" ");
        String label = split[0];
        boolean isSame = label.contains(key);
        if(isSame){
            String newString = label.substring(key.length());
            newString = newString.substring(1,newString.length()-1);
            try {
                return Integer.parseInt(newString);
            }
            catch (Exception ex){
                return 0;
            }

        }
        return 0;
    }

    private String getKeyValueProperty(String key, String newLine) {
        StringBuilder moName = new StringBuilder();
        String[] split = newLine.split(" ");
        if(split[0].equalsIgnoreCase(key)){
            int i = 1;
            while (split[i].equalsIgnoreCase("")){
                i++;
            }

            while (i<split.length){
                moName.append(split[i]+" ");
                i++;
            }
        }
        return moName.toString().trim();
    }

    private String getKeyValuePropertyContainsKey(String key, String newLine) {
        StringBuilder moName = new StringBuilder();
        String[] split = newLine.split(" ");
        String label = split[0];
        boolean isSame = label.contains(key);
        if(isSame){
            int i = 1;
            while (split[i].contains(key)){
                i++;
            }

            while (i<split.length){
                moName.append(split[i]+" ");
                i++;
            }
        }
        return moName.toString().trim();
    }

    public static void main(String[] args) {
        LogReader logReader = new LogReader();
        logReader.readLogFile("030619_DUTTON_ROAD.log");
    }
}
