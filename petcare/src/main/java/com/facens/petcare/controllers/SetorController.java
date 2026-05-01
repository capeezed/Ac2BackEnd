package com.facens.petcare.controllers;

import com.facens.petcare.dto.SetorRequest;
import com.facens.petcare.models.Setor;
import com.facens.petcare.services.SetorService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setores")
@CrossOrigin
public class SetorController {
    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @GetMapping
    public List<Setor> listarComFuncionarios() {
        return setorService.listarComFuncionarios();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Setor cadastrar(@RequestBody SetorRequest request) {
        Setor setor = new Setor();
        setor.setNome(request.nome());
        setor.setDescricao(request.descricao());
        return setorService.cadastrar(setor);
    }

    @GetMapping("/{id}")
    public Setor buscar(@PathVariable Long id) {
        return setorService.buscar(id);
    }
}
