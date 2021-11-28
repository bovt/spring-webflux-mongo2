package ru.bvt.wf.wfmnotesengine.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bvt.wf.wfmnotesengine.domain.Category;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveCategoryRepository;

import java.time.Duration;

@AllArgsConstructor
@RestController
public class CategoryReactiveController {

    private final ReactiveCategoryRepository repository;

    @GetMapping("/flux/category")
    public Flux<Category> all() {
        return repository.findAll();
    }

}


