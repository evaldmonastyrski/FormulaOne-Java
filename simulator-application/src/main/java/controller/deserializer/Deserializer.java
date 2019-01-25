package controller.deserializer;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Deserializer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Deserializer.class);
    @NotNull private static final String FILE = "MarketData.txt";

    public void readTextFile() {
        LOGGER.info("Reading Market Data...");
        try {
            File file = new File(FILE);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                LOGGER.info(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("No {} found", FILE);
        }
    }
}
