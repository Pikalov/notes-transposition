package org.example.service;

import org.example.model.Note;

import java.util.List;

public interface SerializationService {

    List<Note> readNotes();
    void writeNotes(List<Note> notes);

}
