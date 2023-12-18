package org.example.exception;

import org.example.model.Note;

public class InvalidNoteException extends TranspositionServiceException {

    public static final String MSG = "Cannot transpose note: %s to %s semitones. Transposed note falls out of keyboard range: %s";

    public InvalidNoteException(Note originalNote, Integer numberOfSemitonesToTranspose, Note transposedNote) {
        super(String.format(MSG, originalNote, numberOfSemitonesToTranspose, transposedNote));
    }

}
