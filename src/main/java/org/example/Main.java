package org.example;

import org.example.exception.InvalidInputException;
import org.example.exception.TranspositionServiceException;
import org.example.service.JsonFileSerializationService;
import org.example.service.TranspositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            validateInputParameters(args);
            String filePath = args[0];
            Integer semitonesNumber = Integer.valueOf(args[1]);

            // since there is no IoC/DI framework used (for simplicity's sake), creating and injecting dependencies manually
            JsonFileSerializationService notesSerializationService = new JsonFileSerializationService(filePath);
            TranspositionService transpositionService = new TranspositionService(notesSerializationService);

            transpositionService.runTransposition(semitonesNumber);
        } catch (TranspositionServiceException e) {
            LOGGER.error(e.getMessage());
        }
    }


    private static void validateInputParameters(String[] args) {
        if (Objects.isNull(args)) {
            throw new InvalidInputException("Input parameters are absent");
        }
        if (args.length != 2 || args[0].isBlank() || args[1].isBlank()) {
            throw new InvalidInputException("Invalid number of input parameters");
        }
        try {
            Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Second parameter is not an Integer number");
        }
    }
}
