package by.neon.travelassistant.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import by.neon.travelassistant.model.Settings;

public class ReportManager {
    public static File writeListAsText(File outputDir, Settings settings) {
        try {
            File file = new File(outputDir, settings.getCity().getName() + ".txt");
            createListAsText(file, settings);
            return file;
        } catch (IOException e) {
            return null;
        }
    }

    private static void createListAsText(File file, Settings settings) throws IOException {
        try (FileWriter writer = new FileWriter(file, false)) {
            for (Settings.Selection selection : settings.getSelections()) {
                writer.write(selection.getCategory() + "\n");
                for (Map.Entry<String, Boolean> entry : selection.getAsBoolean().entrySet()) {
                    writer.write("- " + entry.getKey() + "\n");
                }
            }
            writer.flush();
        }
    }
}
