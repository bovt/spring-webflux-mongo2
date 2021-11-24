package ru.bvt.wf.wfmnotesengine.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class PagesController {
    @GetMapping("/index")
    public String indexPage(Model model) {
        model.addAttribute("keywords", "main page");
        return "index";
    }

}


