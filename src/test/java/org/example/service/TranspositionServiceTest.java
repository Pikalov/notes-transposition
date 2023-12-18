package org.example.service;

import org.example.model.Note;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TranspositionServiceTest {

    @Mock
    private SerializationService notesSerializationService;

    @InjectMocks
    private TranspositionService transpositionService;

    @Test
    public void shouldRunTransposition() {
        Integer numberOfSemitonesToTranspose = 2;
        List<Note> originalNotes = List.of(new Note(1, 5), new Note(2, 7));
        List<Note> transposedNotes = List.of(new Note(1, 7), new Note(2, 9));
        when(notesSerializationService.readNotes()).thenReturn(originalNotes);

        transpositionService.runTransposition(numberOfSemitonesToTranspose);

        verify(notesSerializationService, times(1)).readNotes();
        verify(notesSerializationService, times(1)).writeNotes(transposedNotes);
    }
}

