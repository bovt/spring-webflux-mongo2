package ru.bvt.wf.wfmnotesengine.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveCategoryRepository;

@AllArgsConstructor
@Controller
public class CategoryPagesController {
    private final ReactiveCategoryRepository categoryRepository;

    @GetMapping("/cindex")
    public String clistPage(Model model) {
        model.addAttribute("keywords", "list categories");
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(categoryRepository.findAll(), 1);
        model.addAttribute("categories", reactiveDataDrivenMode);
        return "cindex";
    }

}


