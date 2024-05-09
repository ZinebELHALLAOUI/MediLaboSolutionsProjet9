package io.zineb.repository;

import io.zineb.model.Note;
import io.zineb.provider.NoteProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NoteRepositoryImpl implements NoteRepository {

    private final NoteProvider noteProvider;

    @Override
    public List<Note> findNotesByPatient(long id) {
        return noteProvider.getNotes(id);
    }

}
