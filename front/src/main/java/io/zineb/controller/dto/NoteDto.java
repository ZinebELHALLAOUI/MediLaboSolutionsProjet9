package io.zineb.controller.dto;

import io.zineb.model.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.HtmlUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

    private Long patId;
    private String patient;
    private String note;

    public static NoteDto toDto(Note note) {
        return new NoteDto(
                note.patId(),
                note.patient(),
                HtmlUtils.htmlEscape(note.note()).replaceAll("\r\n", "<br>")
        );
    }
}
