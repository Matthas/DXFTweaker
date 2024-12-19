package FileHandlers;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Settings {
    private static Settings instance;
    //private String userHome;
    private static String settingsPath;
    private static String defaultSettingsPath;
    static Map<String,String> settingsMap;
    static Map<String,String> defaultSettingsMap;

    public Settings()  {
        String userHome = System.getProperty("user.home");
        settingsPath = userHome + "\\AppData\\Roaming\\Matthas\\DXFReader\\Settings.txt";
        defaultSettingsPath = "/Settings/Settings.txt";
        settingsMap = new HashMap<>();
        defaultSettingsMap = new LinkedHashMap<>();
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
            ensureDirectoriesExist();
            LoadDefaultSettings();
            LoadOrCreateUserSettings();
            CompareSettings();
        }
        return instance;
    }
    private static void ensureDirectoriesExist() {
        File settingsDir = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Matthas\\DXFReader");
        if (!settingsDir.exists()) {
            if (settingsDir.mkdirs()) {
                System.out.println("Created directories: " + settingsDir.getAbsolutePath());
            } else {
                System.out.println("Failed to create directories: " + settingsDir.getAbsolutePath());
            }
        }
    }
    private static void LoadDefaultSettings() {
        try (InputStream in = Settings.class.getResourceAsStream(defaultSettingsPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println("Reading line from JAR file: " + line);
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    defaultSettingsMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot open JAR file: " + defaultSettingsPath);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void LoadOrCreateUserSettings() {
        File localFile = new File(settingsPath);
        if (localFile.exists()) { //check if user settings file exists
            //populate settingsMap with based on user Settings file
            try (InputStream in = new FileInputStream(localFile);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        settingsMap.put(parts[0].trim(), parts[1].trim());
                    }
                }
            } catch (IOException e) {
                System.out.println("Cannot open local file: " + settingsPath);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else { //if file doesnt exist create new file
            try (InputStream in = Settings.class.getResourceAsStream(defaultSettingsPath);
                 OutputStream out = new FileOutputStream(settingsPath)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Copied default settings to: " + settingsPath);
        }
    }

    private static void CompareSettings() {
        Map<String,String> toAdd = new LinkedHashMap<>();
        for (String defalt : defaultSettingsMap.keySet()) {
            boolean found = false;
            for (String current : settingsMap.keySet()) {
                System.out.println("Comparing: " + defalt + " with " + current );
                if (defalt.equals(current)) {
                    found = true;
                    if (settingsMap.get(current) == null || settingsMap.get(current).isEmpty()) {
                        settingsMap.put(current,defaultSettingsMap.get(defalt));
                        System.out.println("Naprawiono ustawienia - " + defalt);
                    }
                    break;
                }
                if (!found) {
                    if (!settingsMap.containsKey(defalt)) {
                        System.out.println("Dodano ustawienie - " + defalt);
                    }
                    toAdd.put(defalt, settingsMap.get(defalt));
                }
            }
        }
        for (String key : toAdd.keySet()){
            settingsMap.put(key, toAdd.get(key));
        }
        if (toAdd.size() > 0) {
            modifySettingsFile(settingsPath, settingsMap);
        }
    }
    private static void modifySettingsFile(String settingsPath, Map<String, String> settingsMap) {
        // Create a temporary file to write the updated settings
        File tempFile = new File(settingsPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (String setting : settingsMap.keySet()) {
                // Write each key-value pair to the temporary file
                if ("SettingsFilePath".equals(setting)) {
                    writer.write(setting + "=" + settingsPath);
                } else {
                    writer.write(setting + "=" + settingsMap.get(setting));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating settings file: " + settingsPath);
            e.printStackTrace();
        }
        try {
            // Ensure the original file exists or create it
            File originalFile = new File(settingsPath);
            if (!originalFile.exists()) {
                originalFile.createNewFile();
            }

            // Replace the original settings file with the updated temporary file
            Files.move(tempFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Updated settings file: " + originalFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error updating settings file: " + settingsPath);
            e.printStackTrace();
        }
    }

    // Get value for a specific key
    public String getValue(String key) {
        return settingsMap.get(key);
    }
}