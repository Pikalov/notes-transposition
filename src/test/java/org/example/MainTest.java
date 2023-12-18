package org.example;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.InvalidInputException;
import org.example.logs.LogsInMemoryAppender;
import org.example.model.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    private static final String SOURCE_FILE_PATH = "src/test/resources/test-data/original-notes.json";
    private static final String TARGET_FILE_PATH = "src/test/resources/test-data/transposed-notes.json";
    private static final String CORRECTLY_TRANSPOSED_FILE_PATH = "src/test/resources/test-data/correct-transposed-notes.json";

    private LogsInMemoryAppender inMemoryLogs;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(Main.class);
        inMemoryLogs = new LogsInMemoryAppender();
        inMemoryLogs.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(inMemoryLogs);
        inMemoryLogs.start();
    }

    @AfterEach
    public void tearDown() {
        File target = new File(TARGET_FILE_PATH);
        if (target.exists()) {
            target.delete();
        }
    }

    @Test
    public void shouldLogInvalidInputExceptionWhenArgsIsNull() {
        Main.main(null);
        assertTrue(inMemoryLogs.contains(
                String.format(InvalidInputException.MSG, "Input parameters are absent"), Level.ERROR));
    }

    @Test
    public void shouldLogInvalidInputExceptionWhenNoParameters() {
        Main.main(new String[]{});
        assertTrue(inMemoryLogs.contains(
                String.format(InvalidInputException.MSG, "Invalid number of input parameters"), Level.ERROR));
    }

    @Test
    public void shouldLogInvalidInputExceptionWhenFilePathIsEmptyString() {
        Main.main(new String[] {"", "-3"});
        assertTrue(inMemoryLogs.contains(
                String.format(InvalidInputException.MSG, "Invalid number of input parameters"), Level.ERROR));
    }

    @Test
    public void shouldLogInvalidInputExceptionWhenNumberOfSemitonesIsEmptyString() {
        Main.main(new String[] {SOURCE_FILE_PATH, ""});
        assertTrue(inMemoryLogs.contains(
                String.format(InvalidInputException.MSG, "Invalid number of input parameters"), Level.ERROR));
    }

    @Test
    public void shouldLogInvalidInputExceptionWhenNumberOfSemitonesIsNotNumber() {
        Main.main(new String[] {SOURCE_FILE_PATH, "A"});
        assertTrue(inMemoryLogs.contains(
                String.format(InvalidInputException.MSG, "Second parameter is not an Integer number"), Level.ERROR));
    }

    @Test
    public void shouldSuccessfullyRunTransposition() throws IOException {
        Main.main(new String[] {SOURCE_FILE_PATH, "-3"});

        List<Note> correctlyTransposedNotes = readNotes(CORRECTLY_TRANSPOSED_FILE_PATH);
        List<Note> resultNotes = readNotes(TARGET_FILE_PATH);
        assertEquals(correctlyTransposedNotes, resultNotes);
    }

    private static List<Note> readNotes(String filePath) throws IOException {
        Integer[][] jsonData = new ObjectMapper().readValue(new File(filePath), Integer[][].class);
        return Stream.of(jsonData)
                // note represented as array of 2 digits: octave number and note number
                .map(note -> new Note(note[0], note[1]))
                .collect(Collectors.toList());
    }
}
