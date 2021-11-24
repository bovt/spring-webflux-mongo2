package ru.bvt.wf.wfmnotesengine.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bvt.wf.wfmnotesengine.domain.Category;
import ru.bvt.wf.wfmnotesengine.domain.Note;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveCategoryRepository;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveNoteRepository;
import ru.bvt.wf.wfmnotesengine.service.CategoryMapCook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class NoteReactiveController {

    private final ReactiveNoteRepository repository;
    private final ReactiveCategoryRepository reactiveCategoryRepository;
    private final CategoryMapCook categoryMapCook;

    @GetMapping("/flux/note")
    public Flux<Note> all() {
        return repository.findAll();
    }

    @GetMapping("/flux/note/{noteId}")
    public Mono<ResponseEntity<Note>> getNoteById(@PathVariable String noteId){
        Mono<Note> note = repository.findById(noteId);
        return note
                .map( n -> ResponseEntity.ok(n))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/flux/note")
    public Mono<ResponseEntity<Note>> create(@RequestBody Note note) {
        if (note == null || note.getText() == null || note.getText().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        note.setId(null);
        note.setCategories(categoryMapCook.cookCategoryMap(note.getText()));

        //TODO: Пытаюсь наладить реактивное заполнение категорий в репозитории категорий (categoryMapCook.cookCategoryMapExtra(..)), прошу помочь советом что почитать/погуглить чтобы понять
//        Mono<Note> monoNote = Mono.just(note);
//        monoNote.setId(null);
//        monoNote.setCategories(categoryMapCook.cookCategoryMapExtra(note.getText()));

        return repository.save(note)
                .map(savedNote -> ResponseEntity.ok(savedNote))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
        }


    @PutMapping("/flux/note/{noteId}")
    public Mono<ResponseEntity<Note>> updateUserById(@PathVariable String noteId, @RequestBody Note note){
        if (note == null || note.getText() == null || note.getText().isEmpty() || noteId == null) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        note.setId(noteId);
        return repository.save(note)
                .map(updatedNote -> ResponseEntity.ok(updatedNote))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
   }

    @DeleteMapping("/flux/note/{noteId}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable String noteId){
        return repository.deleteById(noteId)
                .map( r -> ResponseEntity.ok().<Void>build());
    }

}




