package org.example.util;

import org.example.exception.InvalidNoteException;
import org.example.model.Note;

public final class TranspositionUtils {

    private static final int MAX_NOTE_IN_OCTAVE = 12;
    private static final int MIN_NOTE_IN_OCTAVE = 1;

    public static Note transpose(Note originalNote, Integer numberOfSemitonesToTranspose) {
        Integer originalNoteNum = originalNote.getNoteNumber();
        Integer originalOctaveNum = originalNote.getOctaveNumber();

        Integer transposedOctaveNum = originalOctaveNum;
        Integer transposedNoteNum = originalNoteNum + numberOfSemitonesToTranspose;
        if (transposedNoteNum > MAX_NOTE_IN_OCTAVE) {
            transposedOctaveNum = originalOctaveNum + (transposedNoteNum / MAX_NOTE_IN_OCTAVE);
            transposedNoteNum = transposedNoteNum % MAX_NOTE_IN_OCTAVE;
        } else if (transposedNoteNum < MIN_NOTE_IN_OCTAVE) {
            // transposedNote is zero or negative here
            transposedOctaveNum = originalOctaveNum + (transposedNoteNum / MAX_NOTE_IN_OCTAVE) - 1;
            transposedNoteNum = (MAX_NOTE_IN_OCTAVE + transposedNoteNum) % MAX_NOTE_IN_OCTAVE;
        }

        Note resultNote = new Note(transposedOctaveNum, transposedNoteNum);
        if (!resultNote.isValid()) {
            throw new InvalidNoteException(originalNote, numberOfSemitonesToTranspose, resultNote);
        }
        return resultNote;
    }

}
