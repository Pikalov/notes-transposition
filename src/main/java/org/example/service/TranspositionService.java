package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Note;
import org.example.util.TranspositionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TranspositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranspositionService.class);

    private final SerializationService notesSerializationService;

    public void runTransposition(Integer numberOfSemitonesToTranspose) {
        // reading
        List<Note> originalNotes = notesSerializationService.readNotes();
        LOGGER.info("Original notes has been successfully read.");

        // transposing
        LOGGER.info("Starting transposition of notes...");
        List<Note> transposedNotes = transposeNotes(originalNotes, numberOfSemitonesToTranspose);
        LOGGER.info("Notes has been successfully transposed.");

        // writing
        notesSerializationService.writeNotes(transposedNotes);
        LOGGER.info("Transposed notes has been successfully written.");
    }

    private static List<Note> transposeNotes(List<Note> originalNotes, Integer semitonesNumber) {
        return originalNotes.stream()
                .map(note -> TranspositionUtils.transpose(note, semitonesNumber))
                .collect(Collectors.toList());
    }

}
