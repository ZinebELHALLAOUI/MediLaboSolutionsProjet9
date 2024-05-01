package io.zineb.service;

import io.zineb.model.Note;
import io.zineb.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> findNotesByPatient(long id) {
        return noteRepository.findNotesByPatient(id);
    }

    public void addNote(Note noteToAdd) {
        noteRepository.addNote(noteToAdd);
    }
}
