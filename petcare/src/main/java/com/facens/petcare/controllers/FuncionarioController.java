package com.facens.petcare.controllers;

import com.facens.petcare.dto.FuncionarioRequest;
import com.facens.petcare.models.Funcionario;
import com.facens.petcare.services.FuncionarioService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public List<Funcionario> listar() {
        return funcionarioService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario cadastrar(@RequestBody FuncionarioRequest request) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(request.nome());
        funcionario.setEmail(request.email());
        funcionario.setCargo(request.cargo());
        return funcionarioService.cadastrar(funcionario, request.setorId());
    }
}
