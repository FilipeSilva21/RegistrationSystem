package com.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/forms")
public class FormsController {

    private final Map<Integer, String> instructions = Map.of(
            1, "Cadastrar novo usuário",
            2, "Listar todos os usuários",
            3, "Cadastrar nova pergunta ao formulário",
            4, "Deletar pergunta do formulário"
    );
}
