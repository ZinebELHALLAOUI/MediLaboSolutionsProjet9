package io.zineb.provider;

import io.zineb.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "note-service", url = "${note-service.url}")
public interface NoteProvider {

    @GetMapping("notes/query")
    List<Note> getNotes(@RequestParam long patId);

}
