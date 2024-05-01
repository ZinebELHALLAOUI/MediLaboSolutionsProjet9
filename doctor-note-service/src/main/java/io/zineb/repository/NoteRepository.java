package io.zineb.repository;

import io.zineb.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, Long> {
    List<Note> findByPatId(Long patId);
}