package FileHandlers;
import java.io.*;
import javax.swing.JOptionPane;

public class SettingsLoader {

    private String setting;
    String defaultPath = getDefaultPath();

    public String getSettingFile() {
        //String s = getDefaultPath() + "\\Matthas\\DXFReader\\Settings.txt";
        return getDefaultPath() + "\\Matthas\\DXFReader\\Settings.txt";
    }

    public String getDefaultPath() {
        String username = System.getProperty("user.name");
        String appDataRoaming = System.getenv("APPDATA");

        if (username != null && appDataRoaming != null){
            return appDataRoaming;
        } else {
            // Throw an exception and display a message box
            String errorMessage = "Error: Unable to determine default path.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(errorMessage);
        }
    }
    public boolean doesFileExist(String fileName) {
        File file = new File(defaultPath + fileName);

        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
    private static void createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    // System.out.println("File created: " + filePath);
                } else {
                    System.err.println("Error creating file: " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Error creating file: " + filePath);
                e.printStackTrace();
            }
        }
    }
    private static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                //System.out.println("Folder created: " + folderPath);
            } else {
                System.err.println("Error creating folder: " + folderPath);
            }
        }
    }
    private static void copySettingsContents(String sourceFilePath, String targetFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(targetFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            //System.out.println("Contents copied from " + sourceFilePath + " to " + targetFilePath);
        } catch (IOException e) {
            System.err.println("Error copying contents: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //main function to run to check if there are setting preloaded in user folder
    public static void main(String[] args) {
        SettingsLoader loader = new SettingsLoader();
        String fileName = "Settings/Settings.txt"; // Replace with the actual file name you want to check
        boolean fileExists = loader.doesFileExist("\\Matthas\\DXFReader\\"+fileName);


        if (!fileExists) {
            SettingsLoader.createFolder(loader.defaultPath + "\\Matthas");
            SettingsLoader.createFolder(loader.defaultPath + "\\Matthas\\DXFReader");
            SettingsLoader.createFile(loader.defaultPath + "\\Matthas\\DXFReader\\Settings.txt");
            SettingsLoader.copySettingsContents("Settings/Settings/Settings",loader.defaultPath +"\\Matthas\\DXFReader\\Settings.txt");
        }
    }
}