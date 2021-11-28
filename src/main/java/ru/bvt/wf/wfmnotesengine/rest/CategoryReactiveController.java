package ru.bvt.wf.wfmnotesengine.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Getter
    private final String defaultCategoryName;

    @Autowired
    public CategoryReactiveController(@Value("${engine.defaults.category}") String defaultCategoryName, ReactiveCategoryRepository repository) {
        this.defaultCategoryName = defaultCategoryName;
        this.repository = repository;
    }

    @GetMapping("/flux/category")
    public Flux<Category> all() {
        return repository.findAll();
    }

}


