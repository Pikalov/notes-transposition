package org.example.util;

import org.example.exception.InvalidNoteException;
import org.example.model.Note;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TranspositionUtilsTest {

    @Test
    public void shouldTransposeWithPositiveSemitone() {
        Note originalNote = new Note(3, 5);
        Note transposedNote = TranspositionUtils.transpose(originalNote, 2);
        assertEquals(new Note(3, 7), transposedNote);
    }

    @Test
    public void shouldTransposeWithNegativeSemitone() {
        Note originalNote = new Note(4, 9);
        Note transposedNote = TranspositionUtils.transpose(originalNote, -3);
        assertEquals(new Note(4, 6), transposedNote);
    }

    @Test
    public void shouldTransposeWithOverflow() {
        Note originalNote = new Note(1, 10);
        Note transposedNote = TranspositionUtils.transpose(originalNote, 3);
        assertEquals(new Note(2, 1), transposedNote);
    }

    @Test
    public void shouldTransposeWithUnderflow() {
        Note originalNote = new Note(2, 2);
        Note transposedNote = TranspositionUtils.transpose(originalNote, -3);
        assertEquals(new Note(1, 11), transposedNote);
    }

    @Test
    public void shouldThrowInvalidNoteExceptionWhenResultWhenNoteIsTooBig() {
        Note originalNote = new Note(4, 8);
        assertThrows(InvalidNoteException.class, () -> {
            TranspositionUtils.transpose(originalNote, 6);
        });
    }

    @Test
    public void shouldThrowInvalidNoteExceptionWhenResultWhenNoteIsTooSmall() {
        Note originalNote = new Note(-3, 12);
        assertThrows(InvalidNoteException.class, () -> {
            TranspositionUtils.transpose(originalNote, -5);
        });
    }
}

