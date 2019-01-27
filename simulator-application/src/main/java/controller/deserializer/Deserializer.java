package controller.deserializer;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Deserializer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Deserializer.class);
    @NotNull private static final String FILE = "MarketData.txt";

    @NotNull private String[] gpStages = new String[0];

    public Deserializer() {
        LOGGER.info("Reading Market Data...");
        try {
            File file = new File(FILE);
            Scanner scanner = new Scanner(file);
            extractGPStages(scanner);

            while (scanner.hasNextLine()) {
                LOGGER.info(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("No {} found", FILE);
        }
    }

    @NotNull
    public String[] getGPStages() {
        return gpStages;
    }

    private void extractGPStages(Scanner scanner) {
        String[] gpStagesCollection = Arrays.stream(scanner.nextLine().split("[ ]"))
                .filter(str -> !str.isEmpty())
                .toArray(String[]::new);

        gpStages = new String[gpStagesCollection.length];

        int counter  = 0;
        for (String gpStage : gpStagesCollection) {
            gpStages[counter] = gpStage.chars().map(ch -> {
                if ((char) ch == '_') {
                    return ' ';
                } else {
                    return ch;
                }
            }).collect(StringBuilder::new,
                    StringBuilder::appendCodePoint, StringBuilder::append).toString();
            counter++;
        }
    }
}
