package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.NoteSerializationException;
import org.example.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonFileSerializationService implements SerializationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileSerializationService.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String TARGET_FILE_NAME = "/transposed-notes.json";

    private final File sourceFile;
    private final File targetFile;

    public JsonFileSerializationService(String sourceFilePath) {
        this.sourceFile = new File(sourceFilePath);
        String workingDirectoryPath = sourceFile.getParentFile().getPath();
        String targetFilePath = workingDirectoryPath + TARGET_FILE_NAME;
        this.targetFile = new File(targetFilePath);
    }

    @Override
    public List<Note> readNotes() {
        LOGGER.info(String.format("Reading original notes from file: %s", sourceFile.getPath()));
        try {
            Integer[][] jsonData = OBJECT_MAPPER.readValue(sourceFile, Integer[][].class);
            return Stream.of(jsonData)
                    // note represented as array of 2 digits: octave number and note number
                    .map(note -> new Note(note[0], note[1]))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new NoteSerializationException(String.format("Cannot read notes from file: %s", sourceFile.getPath()), e);
        }
    }

    @Override
    public void writeNotes(List<Note> notes) {
        LOGGER.info(String.format("Writing transposed notes to file: %s", targetFile.getPath()));
        List<Integer[]> preparedToWriteNotes = notes.stream()
                .map(note -> new Integer[]{
                        note.getOctaveNumber(), note.getNoteNumber()
                })
                .collect(Collectors.toList());
        try {
            OBJECT_MAPPER.writeValue(targetFile, preparedToWriteNotes);
        } catch (IOException e) {
            throw new NoteSerializationException(String.format("Cannot write notes to file: %s", targetFile.getPath()), e);
        }
    }
}
