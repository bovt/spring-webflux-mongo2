package ru.bvt.wf.wfmnotesengine.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bvt.wf.wfmnotesengine.domain.Note;

@Repository
public interface ReactiveNoteRepository extends ReactiveMongoRepository<Note, String> {

    Flux<Note> findAll();

    Mono<Note> findById(String id);

    Mono<Note> save(Note note);

    Flux<Note> findAllByText(String text);

}

