package io.zineb.repository;

import io.zineb.model.Note;

import java.util.List;

public interface NoteRepository {
    List<Note> findNotesByPatient(long id);

    void addNote(Note noteToAdd);
}
