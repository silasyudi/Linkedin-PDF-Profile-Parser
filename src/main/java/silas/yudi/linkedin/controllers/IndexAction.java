package silas.yudi.linkedin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexAction {

    @GetMapping("/")
    public String index() {
        return "templates/index.html";
    }
}
