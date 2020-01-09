package com.woc.am.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class WelcomeController {

    @GetMapping("/")
    public ModelAndView loadStartPage(){
        return new ModelAndView("redirect:login.html");
    }

    @GetMapping("/docs")
    public ModelAndView redirectToSwaggerPage() {
        return new ModelAndView("redirect:swagger-ui.html");
    }

}
