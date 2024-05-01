package io.zineb.controller;

import io.zineb.model.Note;
import io.zineb.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notes")
@AllArgsConstructor
public class NoteController {

    private final NoteRepository noteRepository;

    @GetMapping("query")
    public ResponseEntity<List<Note>> findNotes(@RequestParam Long patId) {
        return ResponseEntity.ok(noteRepository.findByPatId(patId));
    }

    @PostMapping
    public ResponseEntity<Note> saveNote(@Validated @RequestBody Note note) {
        return ResponseEntity.ok(noteRepository.save(note));
    }
}
