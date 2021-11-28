package ru.bvt.wf.wfmnotesengine.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bvt.wf.wfmnotesengine.domain.Category;
import ru.bvt.wf.wfmnotesengine.domain.Note;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveCategoryRepository;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveNoteRepository;

import java.util.Arrays;

@AllArgsConstructor
@RestController
public class NoteReactiveController {

    private final ReactiveNoteRepository repository;
    private final ReactiveCategoryRepository reactiveCategoryRepository;
    private final CategoryReactiveController categoryReactiveController;

    @GetMapping("/flux/note")
    public Flux<Note> all() {
        return repository.findAll();
    }

    @GetMapping("/flux/note/{noteId}")
    public Mono<ResponseEntity<Note>> getNoteById(@PathVariable String noteId) {
        Mono<Note> note = repository.findById(noteId);
        return note
                .map(n -> ResponseEntity.ok(n))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/flux/note")
    public Mono<ResponseEntity<Note>> create(@RequestBody Note note) {
        if (note == null || note.getText() == null || note.getText().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        // Вычленяем из заметки категорию указанную в виде хэштега, при наличии
        String categoryName = note.getText();
        if (categoryName.indexOf('#') != -1) {
            categoryName = categoryName.substring(categoryName.indexOf('#') + 1, categoryName.length());
            var nums = new int[]{
                    categoryName.indexOf(' ') > 0 ? categoryName.indexOf(' ') : categoryName.length(),
                    categoryName.indexOf('.') > 0 ? categoryName.indexOf('.') : categoryName.length(),
                    categoryName.indexOf(',') > 0 ? categoryName.indexOf(',') : categoryName.length(),
                    categoryName.indexOf(';') > 0 ? categoryName.indexOf(';') : categoryName.length(),
                    categoryName.length()};
            var min = Arrays.stream(nums).min();
            categoryName = categoryName.substring(0, min.isPresent() ? min.getAsInt() : categoryName.length());
        } else {
            categoryName = categoryReactiveController.getDefaultCategoryName();
        }
        ;

        return reactiveCategoryRepository.findByName(categoryName)
                .switchIfEmpty(reactiveCategoryRepository.save(new Category(categoryName)))
                .single()
                .map(c -> {
                    note.setCategory(c);
                    note.setId(null);
                    return note;
                })
                .flatMap(n -> repository.save(n))
                .map(savedNote -> ResponseEntity.ok(savedNote))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @PutMapping("/flux/note/{noteId}")
    public Mono<ResponseEntity<Note>> updateUserById(@PathVariable String noteId, @RequestBody Note note) {
        if (note == null || note.getText() == null || note.getText().isEmpty() || noteId == null) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        // Вычленяем из заметки категорию указанную в виде хэштега, при наличии
        String categoryName = note.getText();
        if (categoryName.indexOf('#') != -1) {
            categoryName = categoryName.substring(categoryName.indexOf('#') + 1, categoryName.length());
            var nums = new int[]{
                    categoryName.indexOf(' ') > 0 ? categoryName.indexOf(' ') : categoryName.length(),
                    categoryName.indexOf('.') > 0 ? categoryName.indexOf('.') : categoryName.length(),
                    categoryName.indexOf(',') > 0 ? categoryName.indexOf(',') : categoryName.length(),
                    categoryName.indexOf(';') > 0 ? categoryName.indexOf(';') : categoryName.length(),
                    categoryName.length()};
            var min = Arrays.stream(nums).min();
            categoryName = categoryName.substring(0, min.isPresent() ? min.getAsInt() : categoryName.length());
        } else {
            categoryName = categoryReactiveController.getDefaultCategoryName();
        }
        ;

        return reactiveCategoryRepository.findByName(categoryName)
                .switchIfEmpty(reactiveCategoryRepository.save(new Category(categoryName)))
                .single()
                .map(c -> {
                    note.setCategory(c);
                    note.setId(noteId);
                    return note;
                })
                .flatMap(n -> repository.save(n))
                .map(updatedNote -> ResponseEntity.ok(updatedNote))
                .defaultIfEmpty(ResponseEntity.badRequest().build());

    }

    @DeleteMapping("/flux/note/{noteId}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable String noteId) {
        return repository.deleteById(noteId)
                .map(r -> ResponseEntity.ok().<Void>build());
    }

}




