package org.example.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Note {

    private final Integer octaveNumber;
    private final Integer noteNumber;

    public boolean isValid() {
        return isValidNoteOfFullOctave() || isValidNoteOfPartialOctave();
    }

    private boolean isValidNoteOfFullOctave() {
        return (this.octaveNumber >= -2 && this.octaveNumber <= 4)
                && (this.noteNumber >= 1 && this.noteNumber <= 12);
    }

    private boolean isValidNoteOfPartialOctave() {
        return (this.octaveNumber == -3 && (this.noteNumber >= 10 && this.noteNumber <= 12))
                || (this.octaveNumber == 5 && this.noteNumber == 1);
    }

}
