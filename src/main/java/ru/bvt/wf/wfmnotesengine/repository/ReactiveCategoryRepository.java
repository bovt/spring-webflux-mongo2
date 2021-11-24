package ru.bvt.wf.wfmnotesengine.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bvt.wf.wfmnotesengine.domain.Category;

public interface ReactiveCategoryRepository extends ReactiveMongoRepository<Category, String> {

    Flux<Category> findAll();

    Mono<Category> findById(String id);

    Mono<Category> save(Mono<Category> category);

    Flux<Category> findByName(String name);

    Flux<Category> findAllByName(String name);

}

