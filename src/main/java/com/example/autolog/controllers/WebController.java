package com.example.autolog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Rene
 */

/*
Controlador apenas para a integração com Front-end
Os arquivos do frontend estarão na src/main/resources/static/**
 */
@Controller
public class WebController {

    @GetMapping("/{path:[^\\.]*}")
    public String forward() {
        return "forward:/index.html";
    }
}
