package ru.bvt.wf.wfmnotesengine.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveNoteRepository;

@AllArgsConstructor
@Controller
public class NotePagesControler {

    private final ReactiveNoteRepository noteRepository;

    @GetMapping(value = {"/nindex"})
    public String nlistPage(Model model) {
        model.addAttribute("keywords", "list notes");
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(noteRepository.findAll(), 1);
        model.addAttribute("notes", reactiveDataDrivenMode);
        return "nindex";
    }

    @GetMapping(value = {"/nedit/{noteId}"})
    public String neditPage(@PathVariable("noteId") String noteId, Model model) {
        model.addAttribute("keywords", "edit note");
        model.addAttribute("add", false);
        model.addAttribute("idInModel", noteId);

        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(noteRepository.findById(noteId).flux(), 1);
        model.addAttribute("notesInModel", reactiveDataDrivenMode);
        return "nedit";
    }

    @GetMapping(value = {"/nadd"})
    public String naddPage(Model model) {
        model.addAttribute("keywords", "add note");
        model.addAttribute("add", true);
        return "nedit";
    }


}
