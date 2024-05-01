package io.zineb.repository;

import io.zineb.gateway.GatewayProvider;
import io.zineb.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NoteRepositoryImpl implements NoteRepository {

    private final GatewayProvider gatewayProvider;

    @Override
    public List<Note> findNotesByPatient(long id) {
        return gatewayProvider.getNotes(id);
    }

    @Override
    public void addNote(Note note) {
        gatewayProvider.addNote(note);
    }
}
