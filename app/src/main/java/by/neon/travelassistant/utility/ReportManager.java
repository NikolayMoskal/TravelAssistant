package by.neon.travelassistant.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import by.neon.travelassistant.model.Settings;

/**
 * Creates the reports in any file formats.
 */
public class ReportManager {
    /**
     * Creates the report for a list of recommendations in TXT format.
     *
     * @param outputDir the output directory for report file.
     * @param settings  the content for creating the report.
     * @return the report file.
     */
    public static File writeListAsText(File outputDir, Settings settings) {
        try {
            File file = new File(outputDir, settings.getCity().getName() + ".txt");
            createListAsText(file, settings);
            return file;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Writes the content in the report file.
     *
     * @param file     the report file.
     * @param settings the content for a report.
     * @throws IOException when an error occurred while creating the report.
     */
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
