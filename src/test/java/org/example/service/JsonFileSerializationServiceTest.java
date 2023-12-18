package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.NoteSerializationException;
import org.example.model.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonFileSerializationServiceTest {

    private static final String TEST_SOURCE_FILE_NAME = "test-original-notes.json";
    private static final String TEST_TARGET_FILE_NAME = "transposed-notes.json";

    @TempDir
    private Path tempDir;

    private File sourceFile;
    private File targetFile;

    @BeforeEach
    void setUp() throws Exception {
        // Create a temporary source file for testing
        sourceFile = new File(tempDir.toFile(), TEST_SOURCE_FILE_NAME);
        List<Integer[]> testData = List.of(new Integer[]{1, 5}, new Integer[]{2, 7});
        new ObjectMapper().writeValue(sourceFile, testData);
        targetFile = new File(tempDir.toFile(), TEST_TARGET_FILE_NAME);
    }

    @AfterEach
    void tearDown() {
        // Clean up the temporary file
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
        // Clean up the temporary target file
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }

    @Test
    void shouldReadNotes() {
        SerializationService notesSerializationService = new JsonFileSerializationService(sourceFile.getPath());

        List<Note> notes = notesSerializationService.readNotes();

        assertEquals(2, notes.size());
        assertEquals(new Note(1, 5), notes.get(0));
        assertEquals(new Note(2, 7), notes.get(1));
    }

    @Test
    void shouldWriteNotes() {
        SerializationService notesSerializationService = new JsonFileSerializationService(sourceFile.getPath());
        List<Note> testNotes = List.of(new Note(3, 8), new Note(4, 10));

        notesSerializationService.writeNotes(testNotes);

        assertTrue(targetFile.exists());
    }

    @Test
    void shouldReadNotesWithIOException() {
        File nonExistentFile = new File(tempDir.toFile(), "non-existent-file.json");
        SerializationService serviceWithNonExistentFile = new JsonFileSerializationService(nonExistentFile.getPath());

        assertThrows(NoteSerializationException.class, serviceWithNonExistentFile::readNotes);
    }

    @Test
    void shouldWriteNotesWithIOException() {
        File nonWritableFile = new File("/non-writable-directory/non-writable-file.json");
        SerializationService serviceWithNonWritableFile = new JsonFileSerializationService(nonWritableFile.getPath());

        assertThrows(NoteSerializationException.class, () -> serviceWithNonWritableFile.writeNotes(List.of(new Note(1, 2))));
    }
}


